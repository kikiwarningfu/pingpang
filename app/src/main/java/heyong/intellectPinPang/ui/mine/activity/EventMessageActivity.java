package heyong.intellectPinPang.ui.mine.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.mine.EventMessageAdapter;
import heyong.intellectPinPang.bean.competition.MatchBaseInfoBean;
import heyong.intellectPinPang.bean.competition.TitleListBean;
import heyong.intellectPinPang.databinding.ActivityEventMessageBinding;
import heyong.intellectPinPang.event.SwipMessageEvent;
import heyong.intellectPinPang.ui.club.activity.CoachDrawLotsHostAndGusetActivity;
import heyong.intellectPinPang.ui.mine.MineViewModel;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;

/**
 * 赛事消息   赛事列表
 */
public class EventMessageActivity extends BaseActivity<ActivityEventMessageBinding, MineViewModel> implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, OnRefreshListener {
    public static final String MSG_TYPE = "msg_type";
    private String msgType = "";
    public static final String TITLE = "title";
    private String msgTitle = "";


    EventMessageAdapter eventMessageAdapter;
    MyRefreshAnimHeader mRefreshAnimHeader;
    public static final String TAG = MyMessageActivity.class.getSimpleName();
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean haveMore = true;
    List<TitleListBean.ListBean> list = new ArrayList<>();
    private int clickPosition = 0;
    private int myOwnPosition = 0;
    private int position = 0;
    private String status = "0";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_event_message;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);

        //ConfirmationResultsActivity    教练员   确认比赛成绩界面UI
        //SportsConfirmationResultsActivity  运动员  确认比赛成绩界面UI
        msgType = getIntent().getStringExtra(MSG_TYPE);
        msgTitle = getIntent().getStringExtra(TITLE);

        if (msgType.equals("1") || Integer.parseInt(msgType) == 1) {
            //系统
            mBinding.topbar.setCenterText("赛事消息");
        } else {
            //俱乐部消息
            mBinding.topbar.setCenterText("" + msgTitle);
        }
        getData();
        eventMessageAdapter = new EventMessageAdapter();
        mRefreshAnimHeader = new MyRefreshAnimHeader(EventMessageActivity.this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
        list = new ArrayList<>();
//        mBinding.swFresh.setRefreshHeader(mRefreshAnimHeader);
        mBinding.rvEventMessage.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvEventMessage.setAdapter(eventMessageAdapter);
        eventMessageAdapter.setOnItemChildClickListener(this);
        eventMessageAdapter.setOnItemClickListener(this);
        mBinding.swFresh.setOnRefreshListener(this);
        mBinding.swFresh.setEnableLoadmore(false);
        mBinding.swFresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (haveMore) {
                    pageNum++;
                    getData();
                } else {
                    mBinding.swFresh.finishLoadmore();
                }
            }
        });
        mViewModel.joinBymessageLiveData.observe(this, new Observer<ResponseBean<MatchBaseInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<MatchBaseInfoBean> matchBaseInfoBeanResponseBean) {
                if (matchBaseInfoBeanResponseBean.getErrorCode().equals("200")) {
                    MatchBaseInfoBean data = matchBaseInfoBeanResponseBean.getData();
                    if (data != null) {
                        Intent intent = new Intent();
                        intent.setClass(EventMessageActivity.this, CoachDrawLotsHostAndGusetActivity.class);
                        intent.putExtra("ids", "" + data.getArrangeId());
                        startActivity(intent);
                    } else {
                        toast("" + matchBaseInfoBeanResponseBean.getMessage());
                    }
                } else {
                    toast("" + matchBaseInfoBeanResponseBean.getMessage());
                }
            }
        });
        mViewModel.getTitleListLiveData.observe(this, new Observer<ResponseBean<TitleListBean>>() {
            @Override
            public void onChanged(ResponseBean<TitleListBean> loginBeanResponseBean) {
                dismissLoading();
                //                if (loginBeanResponseBean.isSuccess()) {
                if (loginBeanResponseBean.getErrorCode().equals("200")) {

                    if (pageNum == 1) {
                        list.clear();
                        if (mBinding.swFresh != null) {
                            mBinding.swFresh.finishRefresh();
                        }
                    } else {
                        mBinding.swFresh.finishLoadmore();
                    }
                    if (list != null) {
                        List<TitleListBean.ListBean> newList = loginBeanResponseBean.getData().getList();
                        //                        list.addAll(loginBeanResponseBean.getData().getList());
                        //                        if (list != null && list.size() < pageSize) {
                        //                            haveMore = false;
                        //                            mBinding.swFresh.setEnableLoadmore(false);
                        //                        }
                        Log.e(TAG, "onChanged: 到续签" + new Gson().toJson(newList));
                        //                        Collections.reverse(newList);
                        Log.e(TAG, "onChanged: 倒序后" + new Gson().toJson(newList));
                        list.addAll(0, newList);
                        eventMessageAdapter.setNewData(list);
                        if (mBinding.swFresh != null) {
                            mBinding.swFresh.finishRefresh();
                        }
                        mBinding.swFresh.finishLoadmore();
                        Log.e(TAG, "onChanged: " + eventMessageAdapter.getData().size());
                        if (position == 0) {
                            //                        mBinding.rvEventMessage.scrollToPosition(eventMessageAdapter.getItemCount() - 1);
                            mBinding.rvEventMessage.smoothScrollToPosition(eventMessageAdapter.getData().size());
                            position = position + 1;
                        }
                    }
                }
                if (eventMessageAdapter.getData().size() > 0) {
                    mBinding.rvEventMessage.setVisibility(View.VISIBLE);
                    mBinding.llSystemEmptyView.setVisibility(View.GONE);
                } else {
                    mBinding.llSystemEmptyView.setVisibility(View.VISIBLE);
                    mBinding.rvEventMessage.setVisibility(View.GONE);
                    Log.e(TAG, "onChanged: msgType===" + msgType);
                    switch (msgType) {
                        case "1"://系统消息
                            mBinding.tvContent.setText("暂时没有系统消息");
                            break;
                        case "2"://俱乐部消息
                            mBinding.tvContent.setText("暂时没有俱乐部消息");

                            break;
                    }
                }

            }

        });
        mViewModel.approvalClubApplyLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {

                    TitleListBean.ListBean listBean = eventMessageAdapter.getData().get(myOwnPosition);
                    if (clickPosition == 1) {
                        //同意
                        listBean.setMessage("已同意");
                        listBean.setStatus("1");
                    } else {
                        //拒绝
                        listBean.setMessage("已拒绝");
                        listBean.setStatus("1");
                    }
                    eventMessageAdapter.notifyItemChanged(myOwnPosition);
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_unread://未读
                mBinding.rlUnread.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                mBinding.tvUnread.setTextColor(Color.parseColor("#ff4795ed"));
                mBinding.viewUnread.setVisibility(View.VISIBLE);

                mBinding.viewHaveread.setVisibility(View.GONE);
                mBinding.rlHaveRead.setBackgroundColor(Color.parseColor("#FFE5E5E5"));
                mBinding.tvHaveRead.setTextColor(Color.parseColor("#ff666666"));
                status = "0";
                pageNum = 1;
                showLoading();
                getData();
                break;
            case R.id.rl_have_read://已读
                mBinding.rlUnread.setBackgroundColor(Color.parseColor("#FFE5E5E5"));
                mBinding.tvUnread.setTextColor(Color.parseColor("#ff666666"));
                mBinding.viewUnread.setVisibility(View.GONE);

                mBinding.viewHaveread.setVisibility(View.VISIBLE);
                mBinding.rlHaveRead.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                mBinding.tvHaveRead.setTextColor(Color.parseColor("#ff4795ed"));
                status = "1";
                pageNum = 1;
                showLoading();
                getData();

                break;
        }

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //        TitleListBean.ListBean listBean = eventMessageAdapter.getData().get(position);
        //        int category = Integer.parseInt(listBean.getCategory());
        //        multiTypeDelegate.registerItemType(0, R.layout.item_message_no_button);//没有按钮
        //        multiTypeDelegate.registerItemType(1, R.layout.item_message_apply_club);//申请加入俱乐部
        //        multiTypeDelegate.registerItemType(2, R.layout.item_message_see_result);//查看比赛结果   运动员评分
        //        multiTypeDelegate.registerItemType(4, R.layout.item_message_see_result);//查看比赛结果   教练员评分
        //        multiTypeDelegate.registerItemType(3, R.layout.item_message_have_button);//填写对阵名单
        //        String message = listBean.getMessage();
        //        String updateTime = listBean.getUpdateTime();
        //        String title = listBean.getTitle();
        //        long id = listBean.getId();
        //        long relationId = listBean.getRelationId();
        //        if (category == 0) {
        //
        //        } else if (category == 1) {
        //
        //        } else if (category == 2) {
        //            //跳转到运动员评分界面
        //            Intent intent = new Intent();
        //            intent.setClass(EventMessageActivity.this, SportsConfirmationResultsActivity.class);
        //            intent.putExtra("ids", "" + id);
        //            intent.putExtra("relationId", "" + relationId);
        //            intent.putExtra("message", "" + message);
        //            intent.putExtra("updateTime", "" + updateTime);
        //            intent.putExtra("title", "" + title);
        //            startActivity(intent);
        //        } else if (category == 4) {
        //            //跳转到教练员评分界面
        //            Intent intent = new Intent();
        //            intent.setClass(EventMessageActivity.this, ConfirmationResultsActivity.class);
        //            intent.putExtra("ids", "" + id);
        //            intent.putExtra("relationId", "" + relationId);
        //            intent.putExtra("message", "" + message);
        //            intent.putExtra("updateTime", "" + updateTime);
        //            intent.putExtra("title", "" + title);
        //            startActivity(intent);
        //        } else if (category == 3) {
        //            //跳转到主客队抽签界面   点击填写对阵名单
        //            mViewModel.joinBymessage("" + id, "" + relationId);
        //        }

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        boolean fastClick = isFastClick();
        if (!fastClick) {
            TitleListBean.ListBean listBean = eventMessageAdapter.getData().get(position);
            long relationId = listBean.getRelationId();
            String status = listBean.getStatus();
            switch (view.getId()) {
                case R.id.ll_refuse://拒绝
                    myOwnPosition = position;
                    clickPosition = 2;
                    mViewModel.approvalClubApply("" + relationId, "3");

                    break;
                case R.id.ll_argee_with://同意
                    clickPosition = 1;
                    myOwnPosition = position;
                    mViewModel.approvalClubApply("" + relationId, "2");

                    break;
                case R.id.tv_see_result://查看比赛结果
                    int category = Integer.parseInt(listBean.getCategory());
                    if (category == 2) {
                        Intent intent = new Intent();
                        intent.setClass(EventMessageActivity.this, SportsConfirmationResultsActivity.class);
                        intent.putExtra("ids", "" + listBean.getId());
                        intent.putExtra("relationId", "" + relationId);
                        intent.putExtra("message", "" + listBean.getMessage());
                        intent.putExtra("updateTime", "" + listBean.getUpdateTime());
                        intent.putExtra("title", "" + listBean.getTitle());
                        intent.putExtra("status", "" + listBean.getStatus());
                        startActivity(intent);
                    } else if (category == 4) {
                        Intent intent = new Intent();
                        intent.setClass(EventMessageActivity.this, ConfirmationResultsActivity.class);
                        intent.putExtra("ids", "" + listBean.getId());
                        intent.putExtra("relationId", "" + relationId);
                        intent.putExtra("message", "" + listBean.getMessage());
                        intent.putExtra("updateTime", "" + listBean.getUpdateTime());
                        intent.putExtra("title", "" + listBean.getTitle());
                        intent.putExtra("status", "" + listBean.getStatus());
                        startActivity(intent);
                    }

                    break;
                case R.id.tv_fill_information://填写对阵单
                    if (status.equals("0")) {
                        mViewModel.joinBymessage("" + listBean.getId(), "" + relationId);
                    }

                    break;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEvent(SwipMessageEvent event) {
        Log.e(TAG, "SwipMessageEvent:  刷新数据");
        pageNum = 1;
        getData();

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

        mBinding.swFresh.setEnableLoadmore(true);
        haveMore = true;
        pageNum = 1;
        getData();
    }

    private void getData() {
        mViewModel.getTitleList("" + msgTitle, "" + msgType, "" + pageNum, "" + pageSize, status);
    }

}