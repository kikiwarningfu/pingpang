package heyong.intellectPinPang.ui.club.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.utli.AlphabetSortAdapter;
import heyong.intellectPinPang.adapter.game.MyHeaderAdapter;
import heyong.intellectPinPang.adapter.game.MyHeaderCoachAdapter;
import heyong.intellectPinPang.bean.competition.QueryNowUserIsChargeBean;
import heyong.intellectPinPang.bean.competition.SortModel;
import heyong.intellectPinPang.bean.competition.XlClubMemberListBean;
import heyong.intellectPinPang.databinding.ActivityClubMemberBinding;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.widget.memberslitter.CharacterParser;
import heyong.intellectPinPang.widget.memberslitter.SideBarView;

/**
 * 俱乐部会员
 */
public class ClubMemberActivity extends BaseActivity<ActivityClubMemberBinding, ClubViewModel> implements View.OnClickListener {
    private static final int MSG_HIDE_DIALOG = 0;
    public static final String CLUB_MEMBER_ID = "club_member_id";
    private String memberId = "";
    private boolean isInputMethodShow;
    private PinyinComparator mPinyinComparator;
    private AlphabetSortAdapter mAlphabetAadpter;
    private H mHandle = new H(ClubMemberActivity.this);

    // 中文转拼音工具栏（字母列表实现的核心工具栏）
    private CharacterParser mCharacterParser;

    private TextView tv_dialog;
    private ListView sortListView;
    private SideBarView SideBarView;
    public static final String CLUB_USER_ID = "club_user_id";
    private String clubUserId = "";
    private String userDetailId = "";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        memberId = getIntent().getStringExtra(CLUB_MEMBER_ID);
        mViewModel.getXlClubMemberList(memberId);

        initViews();
        initValues();
        initListeners();
        mViewModel.queryNowUserIsChargeLiveData.observe(this, new Observer<ResponseBean<QueryNowUserIsChargeBean>>() {
            @Override
            public void onChanged(ResponseBean<QueryNowUserIsChargeBean> responseBean) {

                if (responseBean.getErrorCode().equals("200")) {
                    QueryNowUserIsChargeBean data = responseBean.getData();
                    boolean inCharge = data.isInCharge();
                    if (inCharge) {
                        MemberShipDetailsActivity.goActivity(ClubMemberActivity.this,
                                "" + clubUserId,2);
                    } else {
                        MemberShipDetailsActivity.goActivity(ClubMemberActivity.this,
                                "" + userDetailId,1);
                    }

                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

        mViewModel.getXlClubMemberListLiveData.observe(this, new Observer<ResponseBean<XlClubMemberListBean>>() {
            @Override
            public void onChanged(ResponseBean<XlClubMemberListBean> xlClubMemberListBeanResponseBean) {
                XlClubMemberListBean data = xlClubMemberListBeanResponseBean.getData();
                if (data != null) {

                    mCharacterParser = new CharacterParser();
                    mPinyinComparator = new PinyinComparator();
                    List<XlClubMemberListBean.ChargeListBean> others = xlClubMemberListBeanResponseBean.getData().getOthers();
                    for (int i = 0; i < others.size(); i++) {
                        others.get(i).setFistLetter(mCharacterParser.getSortKey(others.get(i).getName()));
                    }
                    // 根据a-z进行排序源数据
                    Collections.sort(others, mPinyinComparator);
                    mAlphabetAadpter = new AlphabetSortAdapter(ClubMemberActivity.this, others);
                    sortListView.setAdapter(mAlphabetAadpter);
                    mAlphabetAadpter.setListener(new AlphabetSortAdapter.onMyListener() {
                        @Override
                        public void OnMyListener(String ids, String userId) {
                            getQueryIdentify();
                            clubUserId = ""+ids;
                            userDetailId = ""+userId;
//                            MemberShipDetailsActivity.goActivity(ClubMemberActivity.this,
//                                    "" + ids);
                        }
                    });
                    View decorView = getWindow().getDecorView();
                    View view = LayoutInflater.from(ClubMemberActivity.this).inflate(R.layout.header_view, (ViewGroup) decorView, false);
                    ListView myListViewCoach = view.findViewById(R.id.my_list_view);
                    ListView myListfuzeren = view.findViewById(R.id.my_list_view_fuzeren);
                    myListViewCoach.setVisibility(View.VISIBLE);
                    myListfuzeren.setVisibility(View.VISIBLE);

                    sortListView.addHeaderView(view);
                    List<XlClubMemberListBean.ChargeListBean> chargeList = xlClubMemberListBeanResponseBean.getData().getChargeList();
                    List<XlClubMemberListBean.ChargeListBean> chargeListFuzeren = new ArrayList<>();
                    List<XlClubMemberListBean.ChargeListBean> chargeListCoach = new ArrayList<>();
                    for (int i = 0; i < chargeList.size(); i++) {
                        int i1 = Integer.parseInt(chargeList.get(i).getMemberType());
                        if (i1 == 1) {
                            XlClubMemberListBean.ChargeListBean chargeListBean1 = chargeList.get(i);
                            chargeListBean1.setFistLetter("负责人");
                            chargeListFuzeren.add(chargeListBean1);

                        } else {
                            XlClubMemberListBean.ChargeListBean chargeListBean2 = chargeList.get(i);
                            chargeListBean2.setFistLetter("教练员1");
                            chargeListCoach.add(chargeListBean2);

                        }
                    }
                    if (chargeListFuzeren.size() > 0) {
                        MyHeaderAdapter myHeaderAdapter = new MyHeaderAdapter(ClubMemberActivity.this, chargeListFuzeren);
                        myListfuzeren.setVisibility(View.VISIBLE);
                        myListfuzeren.setAdapter(myHeaderAdapter);
                        myListfuzeren.setVerticalScrollBarEnabled(false);

                        View listItemfuzeren = myHeaderAdapter.getView(0, null, myListfuzeren);
                        myHeaderAdapter.setListener(new MyHeaderAdapter.onMyListener() {
                            @Override
                            public void OnMyListener(String ids, String userId) {
//                                MemberShipDetailsActivity.goActivity(ClubMemberActivity.this,
//                                        "" + ids);
                                clubUserId = ""+ids;
                                userDetailId = ""+userId;
                                getQueryIdentify();
                            }
                        });
                        listItemfuzeren.measure(0, 0);
                        int totalHeifuzeren = (listItemfuzeren.getMeasuredHeight() + myListfuzeren.getDividerHeight() - 15) * (chargeListFuzeren.size());
                        if (myListfuzeren != null) {
                            if (chargeListFuzeren.size() > 1) {
                                ViewGroup.LayoutParams layoutParams = myListfuzeren.getLayoutParams();
                                layoutParams.height = totalHeifuzeren;
                            }
                        } else {
                            Log.e(TAG, "initValues: myListView 为打工人null ");
                        }
                    } else {
                        myListfuzeren.setVisibility(View.GONE);
                    }

                    if (chargeListCoach.size() > 0) {
                        myListViewCoach.setVisibility(View.VISIBLE);
                        MyHeaderCoachAdapter myHeaderCoachAdapter = new MyHeaderCoachAdapter(ClubMemberActivity.this, chargeListCoach);
                        myListViewCoach.setAdapter(myHeaderCoachAdapter);
                        myListViewCoach.setVerticalScrollBarEnabled(false);
                        View listItem = myHeaderCoachAdapter.getView(0, null, myListViewCoach);
                        myHeaderCoachAdapter.setListener(new MyHeaderCoachAdapter.onMyListener() {
                            @Override
                            public void OnMyListener(String ids, String userId) {
                                getQueryIdentify();
//                                MemberShipDetailsActivity.goActivity(ClubMemberActivity.this,
//                                        "" + ids);
                                clubUserId = ""+ids;
                                userDetailId = ""+userId;
                            }
                        });
                        listItem.measure(0, 0);
                        int totalHei = (listItem.getMeasuredHeight() + myListViewCoach.getDividerHeight() - 55) * (chargeListCoach.size());
                        if (myListViewCoach != null) {
                            if (chargeListCoach.size() > 1) {
                                ViewGroup.LayoutParams layoutParams = myListViewCoach.getLayoutParams();
                                layoutParams.height = totalHei;
                            }
                        } else {
                            Log.e(TAG, "initValues: myListView 为打工人null ");
                        }
                    } else {
                        myListViewCoach.setVisibility(View.GONE);

                    }
                    Log.e(TAG, "onChanged: 教练员的人数大小===" + chargeListCoach.size());
                    Log.e(TAG, "onChanged: 负责人的人数大小===" + chargeListFuzeren.size());

                } else {
//                    mBinding.tvAllNum.setText("共" + 0 + "人");
//                    clubMemberAdapter.setNewData(new ArrayList<>());
                }
            }
        });

    }

    // 根据a-z进行排序
    public class PinyinComparator implements Comparator<XlClubMemberListBean.ChargeListBean> {

        public int compare(XlClubMemberListBean.ChargeListBean o1, XlClubMemberListBean.ChargeListBean o2) {

            if (o1.getFistLetter().equals("#")) {
                return -1;
            } else {
                return o1.getFistLetter().compareTo(o2.getFistLetter());
            }
        }
    }

    private static class H extends Handler {
        WeakReference<ClubMemberActivity> mActivity;

        public H(ClubMemberActivity activity) {
            mActivity = new WeakReference<ClubMemberActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_HIDE_DIALOG:
                    mActivity.get().hideDialog();
                    break;
            }
        }
    }

    // 只显示 1 秒的字母对话框
    private void showDialog() {
        tv_dialog.setVisibility(View.VISIBLE);
        mHandle.removeMessages(MSG_HIDE_DIALOG);
        mHandle.sendEmptyMessageDelayed(MSG_HIDE_DIALOG, 1000);
    }

    public void getQueryIdentify() {
        mViewModel.queryNowUserIsCharge(memberId);
    }

    private void hideDialog() {
        tv_dialog.setVisibility(View.GONE);
    }

    public static final String TAG = ClubMemberActivity.class.getSimpleName();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_club_member;
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void initViews() {
        sortListView = (ListView) findViewById(R.id.name_listview);
        SideBarView = (SideBarView) findViewById(R.id.sidrbar);
        tv_dialog = (TextView) findViewById(R.id.tv_dialog);
    }

    private void initValues() {

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initListeners() {
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
                try {
                    Toast.makeText(getApplication(),
                            ((SortModel) mAlphabetAadpter.getItem(position)).info,
                            Toast.LENGTH_SHORT).show();
                }catch (Exception e)
                {

                }

            }
        });

        sortListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        //手指用力滑动
                        //手指离开listview后由于惯性继续滑动
                        showDialog();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
            }
        });

        sortListView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                try {
                    int positon = sortListView.getFirstVisiblePosition();
                    Log.e(TAG, "onScrollChange: json==="+new Gson().toJson(mAlphabetAadpter.getListData()));
                    String alpha = mAlphabetAadpter.getAlpha(positon);
                    SideBarView.setCurrCharacter(alpha);
                    tv_dialog.setText(alpha);
                    Log.e(TAG, "onScrollChange positon:" + positon + ", alpha:" + alpha);
                }catch (Exception e)
                {
                    Log.e(TAG, "onScrollChange: "+e.getMessage() );
                }

            }
        });

        sortListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });

        // 设置右侧触摸监听
        SideBarView.setOnTouchingLetterChangedListener(new heyong.intellectPinPang.widget.memberslitter.SideBarView.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                Log.d(TAG, "onTouchingLetterChanged:" + s);
                int position = mAlphabetAadpter.getPositionForSection(s.charAt(0));
                tv_dialog.setText(s);
                showDialog();
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });


    }


    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public void hideKeyboard() {
        if (getCurrentFocus() == null || !isInputMethodShow) {
            return;
        }
        isInputMethodShow = false;
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}