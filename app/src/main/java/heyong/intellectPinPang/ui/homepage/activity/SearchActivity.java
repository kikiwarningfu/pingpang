package heyong.intellectPinPang.ui.homepage.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.homepage.AgainstRecordAdapter;
import heyong.intellectPinPang.adapter.club.ClubAdapter;
import heyong.intellectPinPang.adapter.game.SearchSportsPeopleAdapter;
import heyong.intellectPinPang.bean.competition.XlClubInfoListBean;
import heyong.intellectPinPang.bean.competition.XlUserInfoListBean;
import heyong.intellectPinPang.databinding.ActivitySearchBinding;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;
import heyong.intellectPinPang.ui.club.activity.ClubDetailActivity;
import heyong.intellectPinPang.ui.club.activity.MemberShipDetailsActivity;
import heyong.intellectPinPang.ui.homepage.viewmodel.SearchViewModel;
import heyong.intellectPinPang.utils.CommonUtilis;
import heyong.handong.framework.utils.ToastUtils;
import heyong.intellectPinPang.widget.SpaceItemDecoration;

/**
 * 搜索
 */
public class SearchActivity extends BaseActivity<ActivitySearchBinding, SearchViewModel> implements View.OnClickListener {
    SearchSportsPeopleAdapter searchSportsPeopleAdapter;
    ClubAdapter clubAdapter;//俱乐部的适配器
    private SpaceItemDecoration mPopulardidLike;
    AgainstRecordAdapter againstRecordAdapter;//对战记录

    private int showType = 0;
    String etSportsName;//输入运动员的名字
    String etClubName;//输入运动员的名字
    private int pageNumSports = 1;
    private int pageSizeSports = 10;
    private boolean haveMoreSports = true;
    MyRefreshAnimHeader mRefreshAnimHeaderSports;
    MyRefreshAnimHeader mRefreshAnimHeaderClub;
    public static final String TAG = SearchActivity.class.getSimpleName();
    private int pageNumClub = 1;
    private int pageSizeClub = 10;
    private boolean haveMoreClub = true;


    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_sports_name, R.id.et_club_name};
        return ids;
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (showType) {
            case 0://运动员
                pageNumSports = 1;
                getSportsData("" + pageNumSports, "" + pageSizeSports);

                break;
            case 1://俱乐部
                pageNumClub = 1;
                getClubData("" + pageNumClub, "" + pageSizeClub);
                break;

            case 2://对战


                break;
        }
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_search;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        mBinding.tvSportsMan.setBackgroundResource(R.drawable.shape_left_corners_blue);
        mBinding.viewSportMan.setVisibility(View.GONE);
        mBinding.tvClub.setBackgroundResource(R.drawable.shape_corners_gray);
        mBinding.viewFight.setVisibility(View.VISIBLE);
        mBinding.tvFight.setBackgroundResource(R.drawable.shape_right_corners_gray);
        mBinding.llSportsMan.setVisibility(View.VISIBLE);
        mBinding.tvSportsManEmpty.setVisibility(View.GONE);

        mBinding.tvSportsMan.setTextColor(Color.parseColor("#FFFFFF"));
        mBinding.tvClub.setTextColor(Color.parseColor("#666666"));
        mBinding.tvFight.setTextColor(Color.parseColor("#666666"));

        initSports();
        initClub();
        initAgainstRecord();

    }

    /*对战记录*/
    private void initAgainstRecord() {
        againstRecordAdapter = new AgainstRecordAdapter();
        mBinding.rvAgainstRecord.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvAgainstRecord.setAdapter(againstRecordAdapter);
        againstRecordAdapter.setNewData(Arrays.asList("1", "1", "1"));
        mBinding.llFight.setVisibility(View.GONE);
        mBinding.tvAgainstRecordEmpty.setVisibility(View.GONE);
    }

    private void initClub() {
        clubAdapter = new ClubAdapter(SearchActivity.this);
        mBinding.rvClub.removeItemDecoration(mPopulardidLike);
        mPopulardidLike = new SpaceItemDecoration(20);

        if (mBinding.swFreshClub.isRefreshing()) {
            mBinding.swFreshClub.finishRefresh();
        }
        mRefreshAnimHeaderClub = new MyRefreshAnimHeader(SearchActivity.this);

//        mBinding.swFreshClub.setRefreshHeader(mRefreshAnimHeaderClub);
        mBinding.rvClub.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
        mBinding.rvClub.addItemDecoration(mPopulardidLike);
        mBinding.rvClub.setAdapter(clubAdapter);
        mBinding.etClubName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //关闭软键盘
                    CommonUtilis.hideSoftKeyboard(SearchActivity.this);
                    getClubData("" + pageNumSports, "" + pageSizeSports);
                    return true;
                }
                return false;
            }
        });
        mBinding.etClubName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    mBinding.rvClub.setVisibility(View.VISIBLE);
                    mBinding.tvClubEmpty.setVisibility(View.GONE);
                }
            }
        });
        /*俱乐部的搜索*/
        mBinding.ivClubSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etClubName = mBinding.etClubName.getText().toString();

                if (TextUtils.isEmpty(etClubName)) {
                    getClubData("" + pageNumSports, "" + pageSizeSports);
                } else {
                    getClubData("" + pageNumSports, "" + pageSizeSports);
                }
            }
        });
        clubAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        clubAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    long id = clubAdapter.getData().get(position).getId();
                    Intent intent = new Intent(SearchActivity.this, ClubDetailActivity.class);
                    intent.putExtra(ClubDetailActivity.CLUB_ID, "" + id);
                    startActivity(intent);
                }
            }
        });
        mBinding.swFreshClub.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mBinding.swFreshClub.setEnableLoadmore(true);
                haveMoreClub = true;
                pageNumClub = 1;
                getClubData("" + pageNumClub, "" + pageSizeClub);
            }
        });
        mBinding.swFreshClub.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (haveMoreClub) {
                    pageNumClub++;
                    getClubData("" + pageNumClub, "" + pageSizeClub);
                } else {
                    mBinding.swFreshClub.finishLoadmore();
                }
            }
        });
        List<XlClubInfoListBean.ListBean> clubInfoList = new ArrayList<>();
        mViewModel.getXlClubInfoListLiveData.observe(this, new Observer<ResponseBean<XlClubInfoListBean>>() {
            @Override
            public void onChanged(ResponseBean<XlClubInfoListBean> loginBeanResponseBean) {
                dismissLoading();
//                if (loginBeanResponseBean.isSuccess()) {
                if (loginBeanResponseBean.getErrorCode().equals("200")) {

                    if (pageNumClub == 1) {
                        clubInfoList.clear();
                        if (mBinding.swFreshClub != null) {
                            mBinding.swFreshClub.finishRefresh();
                            mBinding.swFreshClub.finishLoadmore();
                        }
                    } else {
                        mBinding.swFreshClub.finishLoadmore();
                    }
                    List<XlClubInfoListBean.ListBean> list = loginBeanResponseBean.getData().getList();
                    if (list != null) {
                        if (list != null && list.size() < pageSizeClub) {
                            haveMoreClub = false;
                            mBinding.swFreshClub.setEnableLoadmore(false);
                        }
                        clubInfoList.addAll(list);
                        clubAdapter.setNewData(clubInfoList);
                    }
                    if (!TextUtils.isEmpty(etClubName)) {
                        int size = clubAdapter.getData().size();
                        if (size > 0) {
                            mBinding.rvClub.setVisibility(View.VISIBLE);
                            mBinding.tvClubEmpty.setVisibility(View.GONE);
                        } else {
                            mBinding.rvClub.setVisibility(View.GONE);
                            mBinding.tvClubEmpty.setVisibility(View.VISIBLE);
                        }
                    }

                }
            }
        });

    }

    /*初始化运动员*/
    private void initSports() {
        searchSportsPeopleAdapter = new SearchSportsPeopleAdapter();
        mBinding.rvSportsMan.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvSportsMan.setAdapter(searchSportsPeopleAdapter);

        if (mBinding.swFreshSports.isRefreshing()) {
            mBinding.swFreshSports.finishRefresh();
        }
        mRefreshAnimHeaderSports = new MyRefreshAnimHeader(SearchActivity.this);

//        mBinding.swFreshSports.setRefreshHeader(mRefreshAnimHeaderSports);
        mBinding.etSportsName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //关闭软键盘
                    CommonUtilis.hideSoftKeyboard(SearchActivity.this);
                    getSportsData("" + pageNumSports, "" + pageSizeSports);
                    return true;
                }
                return false;
            }
        });

        mBinding.etSportsName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    mBinding.rvSportsMan.setVisibility(View.VISIBLE);
                    mBinding.tvSportsManEmpty.setVisibility(View.GONE);
                }
            }
        });
        /*搜索运动的搜索*/
        mBinding.ivSportsSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSportsName = mBinding.etSportsName.getText().toString();
                if (TextUtils.isEmpty(etSportsName)) {
                    getSportsData("" + pageNumSports, "" + pageSizeSports);
                } else {
                    getSportsData("" + pageNumSports, "" + pageSizeSports);
                }
            }
        });
        List<XlUserInfoListBean.ListBean> userInfoList=new ArrayList<>();
        mViewModel.getXlUserInfoListLiveData.observe(this, new Observer<ResponseBean<XlUserInfoListBean>>() {
            @Override
            public void onChanged(ResponseBean<XlUserInfoListBean> xlUserInfoListBeanResponseBean) {
//                if (xlUserInfoListBeanResponseBean.isSuccess()) {
                dismissLoading();
                if (xlUserInfoListBeanResponseBean.getErrorCode().equals("200")) {
                    if (pageNumSports == 1) {
                        userInfoList.clear();
                        if (mBinding.swFreshSports != null) {
                            mBinding.swFreshSports.finishRefresh();
                            mBinding.swFreshSports.finishLoadmore();
                        }
                    } else {
                        mBinding.swFreshSports.finishLoadmore();
                    }
                    List<XlUserInfoListBean.ListBean> list = xlUserInfoListBeanResponseBean.getData().getList();
                    if (list != null) {

                        if (list != null && list.size() < pageSizeSports) {
                            haveMoreSports = false;
                            mBinding.swFreshSports.setEnableLoadmore(false);
                        }
                        userInfoList.addAll(list);
                        searchSportsPeopleAdapter.setNewData(userInfoList);
                    }
                    if (!TextUtils.isEmpty(etSportsName)) {
                        int size = searchSportsPeopleAdapter.getData().size();
                        if (size > 0) {
                            mBinding.rvSportsMan.setVisibility(View.VISIBLE);
                            mBinding.tvSportsManEmpty.setVisibility(View.GONE);
                        } else {
                            mBinding.rvSportsMan.setVisibility(View.GONE);
                            mBinding.tvSportsManEmpty.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
        mBinding.swFreshSports.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mBinding.swFreshSports.setEnableLoadmore(true);
                haveMoreSports = true;
                pageNumSports = 1;
                getSportsData("" + pageNumSports, "" + pageSizeSports);

            }
        });
        mBinding.swFreshSports.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (haveMoreSports) {
                    pageNumSports++;
                    getSportsData("" + pageNumSports, "" + pageSizeSports);
                } else {
                    mBinding.swFreshSports.finishLoadmore();
                }
            }
        });
        searchSportsPeopleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    XlUserInfoListBean.ListBean listBean = searchSportsPeopleAdapter.getData().get(position);

                    MemberShipDetailsActivity.goActivity(SearchActivity.this,
                            "" + listBean.getId(), 1);
                }
            }
        });
        searchSportsPeopleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

    }

    /*获取运动员的数据*/
    private void getSportsData(String s, String s2) {
        if (etSportsName != null) {
            if (etSportsName.equals("null")) {
                mViewModel.getXlUserInfoList(s, s2, "");
            } else {
                mViewModel.getXlUserInfoList(s, s2, "" + etSportsName);
            }
        } else {
            mViewModel.getXlUserInfoList(s, s2, "");

        }
    }

    /*获取俱乐部的数据*/
    private void getClubData(String s, String s1) {
        if (etClubName != null) {
            if (etClubName.equals("null")) {
                mViewModel.getXlClubInfoList("" + s, "" + s1, "", "", "");
            } else {
                mViewModel.getXlClubInfoList("" + s, "" + s1, "" + etClubName, "", "");
            }
        } else {
            mViewModel.getXlClubInfoList("" + s, "" + s1, "", "", "");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_sports_man://运动员
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.tvSportsMan.setBackgroundResource(R.drawable.shape_left_corners_blue);
                mBinding.viewSportMan.setVisibility(View.GONE);
                mBinding.tvClub.setBackgroundResource(R.drawable.shape_corners_gray);
                mBinding.viewFight.setVisibility(View.VISIBLE);
                mBinding.tvFight.setBackgroundResource(R.drawable.shape_right_corners_gray);
                mBinding.llSportsMan.setVisibility(View.VISIBLE);
                mBinding.tvSportsManEmpty.setVisibility(View.GONE);
                mBinding.tvSportsMan.setTextColor(Color.parseColor("#FFFFFF"));
                mBinding.tvClub.setTextColor(Color.parseColor("#666666"));
                mBinding.tvFight.setTextColor(Color.parseColor("#666666"));

                mBinding.llClub.setVisibility(View.GONE);
                mBinding.tvClubEmpty.setVisibility(View.GONE);

                mBinding.llFight.setVisibility(View.GONE);
                mBinding.tvAgainstRecordEmpty.setVisibility(View.GONE);


                pageNumSports = 1;
                haveMoreSports = true;
                getSportsData("" + pageNumSports, "" + pageSizeSports);

                break;

            case R.id.tv_club://俱乐部
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.tvSportsMan.setBackgroundResource(R.drawable.shape_left_corners_gray);
                mBinding.viewSportMan.setVisibility(View.GONE);
                mBinding.tvClub.setBackgroundResource(R.drawable.shape_corners_blue);
                mBinding.viewFight.setVisibility(View.GONE);
                mBinding.tvFight.setBackgroundResource(R.drawable.shape_right_corners_gray);

                mBinding.tvSportsMan.setTextColor(Color.parseColor("#666666"));
                mBinding.tvClub.setTextColor(Color.parseColor("#FFFFFF"));
                mBinding.tvFight.setTextColor(Color.parseColor("#666666"));

                mBinding.llSportsMan.setVisibility(View.GONE);
                mBinding.tvSportsManEmpty.setVisibility(View.GONE);

                mBinding.llClub.setVisibility(View.VISIBLE);
                mBinding.tvClubEmpty.setVisibility(View.GONE);

                mBinding.llFight.setVisibility(View.GONE);
                mBinding.tvAgainstRecordEmpty.setVisibility(View.GONE);


                pageNumClub = 1;
                haveMoreClub = true;
                getClubData("" + pageNumClub, "" + pageSizeClub);

                break;

            case R.id.tv_fight://对战
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ToastUtils.showToast(SearchActivity.this, "暂未开放");
//                mBinding.tvSportsMan.setBackgroundResource(R.drawable.shape_left_corners_gray);
//                mBinding.viewSportMan.setVisibility(View.VISIBLE);
//                mBinding.tvClub.setBackgroundResource(R.drawable.shape_corners_gray);
//                mBinding.viewFight.setVisibility(View.GONE);
//                mBinding.tvFight.setBackgroundResource(R.drawable.shape_right_corners_blue);
//                mBinding.llSportsMan.setVisibility(View.GONE);
//                mBinding.tvSportsManEmpty.setVisibility(View.GONE);
//                mBinding.tvSportsMan.setTextColor(Color.parseColor("#666666"));
//                mBinding.tvClub.setTextColor(Color.parseColor("#666666"));
//                mBinding.tvFight.setTextColor(Color.parseColor("#FFFFFF"));
//
//
//                mBinding.llSportsMan.setVisibility(View.GONE);
//                mBinding.tvSportsManEmpty.setVisibility(View.GONE);
//
//                mBinding.llClub.setVisibility(View.GONE);
//                mBinding.tvClubEmpty.setVisibility(View.GONE);
//
//                mBinding.llFight.setVisibility(View.VISIBLE);
//                mBinding.tvAgainstRecordEmpty.setVisibility(View.GONE);
                break;
        }
    }
}