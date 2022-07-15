package heyong.intellectPinPang.ui.mine.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.MessageAdapter;
import heyong.intellectPinPang.bean.competition.MessageInfoListBean;
import heyong.intellectPinPang.databinding.ActivityMyMessageBinding;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;
import heyong.intellectPinPang.ui.mine.MineViewModel;
import heyong.intellectPinPang.utils.MessageDialogBuilder;

/**
 * 我的消息
 */
public class MyMessageActivity extends BaseActivity<ActivityMyMessageBinding, MineViewModel> implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, OnRefreshListener {
    MessageAdapter messageAdapter;
    private String msgType = "1";
    MyRefreshAnimHeader mRefreshAnimHeader;
    public static final String TAG = MyMessageActivity.class.getSimpleName();
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean haveMore = true;
    List<MessageInfoListBean.MessagesBean> list = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
        getData();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_my_message;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

        mBinding.setListener(this);
        mBinding.rlSystemMessageContainer.setBackgroundResource(R.drawable.shape_left_corners_blue);
        mBinding.rlClubMessageContainer.setBackgroundResource(R.drawable.shape_right_corners_gray);
        mBinding.tvSystemMessageTitle.setTextColor(Color.parseColor("#FFFFFF"));
        mBinding.tvClubMessageTitle.setTextColor(Color.parseColor("#666666"));

        messageAdapter = new MessageAdapter();
        mRefreshAnimHeader = new MyRefreshAnimHeader(MyMessageActivity.this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
        list = new ArrayList<>();
//        mBinding.swFresh.setRefreshHeader(mRefreshAnimHeader);
        mBinding.rvSystemMessage.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvSystemMessage.setAdapter(messageAdapter);
        messageAdapter.setOnItemChildClickListener(this);
        messageAdapter.setOnItemClickListener(this);
        mBinding.swFresh.setOnRefreshListener(this);
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
        mViewModel.getXlMessageInfoListLiveData.observe(this, new Observer<ResponseBean<MessageInfoListBean>>() {
            @Override
            public void onChanged(ResponseBean<MessageInfoListBean> loginBeanResponseBean) {
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
                        //设置消息数目
                        switch (msgType) {
                            case "1"://系统消息
                                //显示俱乐部消息的数目
                                mBinding.tvClubMessage.setVisibility(View.VISIBLE);
                                mBinding.tvSystemMessage.setVisibility(View.GONE);
                                if (loginBeanResponseBean.getData().getCounts() == 0) {
                                    mBinding.tvClubMessage.setVisibility(View.GONE);
                                } else {
                                    mBinding.tvClubMessage.setText("" + loginBeanResponseBean.getData().getCounts());
                                }
                                break;
                            case "2"://俱乐部消息
                                //显示系统消息的数目
                                mBinding.tvClubMessage.setVisibility(View.GONE);
                                mBinding.tvSystemMessage.setVisibility(View.VISIBLE);
                                if (loginBeanResponseBean.getData().getCounts() == 0) {
                                    mBinding.tvSystemMessage.setVisibility(View.GONE);
                                } else {
                                    mBinding.tvSystemMessage.setText("" + loginBeanResponseBean.getData().getCounts());
                                }
                                break;
                        }
                        list.addAll(loginBeanResponseBean.getData().getMessages());
                        if (list != null && list.size() < pageSize) {
                            haveMore = false;
                            mBinding.swFresh.setEnableLoadmore(false);
                        }
                        messageAdapter.setNewData(list);
                    } else {
                        messageAdapter.setNewData(new ArrayList<>());
                    }
                }
                if (messageAdapter.getData().size() > 0) {
                    mBinding.llSystemEmptyView.setVisibility(View.GONE);
                } else {
                    mBinding.llSystemEmptyView.setVisibility(View.VISIBLE);
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

        mViewModel.updateXlMessageInfoLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
//                if (responseBean.isSuccess()) {
                if (responseBean.getErrorCode().equals("200")) {

                    toast("修改成功");
                    getData();
                } else {
                    toast("" + responseBean.getMessage());

                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_system_message_container://系统消息
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                msgType = "1";
                mBinding.swFresh.setEnableLoadmore(true);
                haveMore = true;
                pageNum = 1;
                mBinding.rlSystemMessageContainer.setBackgroundResource(R.drawable.shape_left_corners_blue);
                mBinding.rlClubMessageContainer.setBackgroundResource(R.drawable.shape_right_corners_gray);
                mBinding.tvSystemMessageTitle.setTextColor(Color.parseColor("#FFFFFF"));
                mBinding.tvClubMessageTitle.setTextColor(Color.parseColor("#666666"));

                getData();

                break;
            case R.id.rl_club_message_container://俱乐部消息
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.swFresh.setEnableLoadmore(true);
                haveMore = true;
                pageNum = 1;
                mBinding.rlSystemMessageContainer.setBackgroundResource(R.drawable.shape_left_corners_gray);
                mBinding.rlClubMessageContainer.setBackgroundResource(R.drawable.shape_right_corners_blue);
                mBinding.tvSystemMessageTitle.setTextColor(Color.parseColor("#666666"));
                mBinding.tvClubMessageTitle.setTextColor(Color.parseColor("#FFFFFF"));

                msgType = "2";
                getData();
                break;
            case R.id.tv_right:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                //清空消息
                new MessageDialogBuilder(MyMessageActivity.this)
                        .setMessage("")
                        .setTvTitle("是否清空消息?")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(v1 ->
                                clearMessage()
                        )
                        .show();
                break;
            case R.id.iv_finish:

                finish();
                break;
        }
    }

    private void clearMessage() {
        mViewModel.updateXlMessageInfo("", "" + msgType,
                "", "1");
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        boolean fastClick = isFastClick();
        if (!fastClick) {
            MessageInfoListBean.MessagesBean messagesBean = messageAdapter.getData().get(position);
            MessageInfoListBean.MessagesBean.TitleBean title = messagesBean.getTitle();
            switch (view.getId()) {
                case R.id.rl_delete:
                    mViewModel.updateXlMessageInfo("" + title.getTitle(), "" + msgType,
                            "", "1");
                    break;
                case R.id.rl_zhiding:
                    /*置顶 取消置顶*/
                    String isTop = title.getIsTop();
                    int i = Integer.parseInt(isTop);
                    if (i == 0) {
                        mViewModel.updateXlMessageInfo("" + title.getTitle(), "" + msgType,
                                "1", "");
                    } else if (i == 1) {
                        mViewModel.updateXlMessageInfo("" + title.getTitle(), "" + msgType,
                                "0", "");
                    }

                    break;
                case R.id.rl_container:

                    MessageInfoListBean.MessagesBean.TitleBean title2 = messageAdapter.getData().get(position).getTitle();
                    Intent intent = new Intent(MyMessageActivity.this, EventMessageActivity.class);
                    intent.putExtra(EventMessageActivity.MSG_TYPE, "" + title2.getMsgType());
                    intent.putExtra(EventMessageActivity.TITLE, "" + title2.getTitle());
                    startActivity(intent);
                    break;
            }
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MessageInfoListBean.MessagesBean.TitleBean title = messageAdapter.getData().get(position).getTitle();
        Intent intent = new Intent(MyMessageActivity.this, EventMessageActivity.class);
        intent.putExtra(EventMessageActivity.MSG_TYPE, "" + title.getMsgType());
        intent.putExtra(EventMessageActivity.TITLE, "" + title.getTitle());
        startActivity(intent);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mBinding.swFresh.setEnableLoadmore(true);
        haveMore = true;
        pageNum = 1;
        getData();
    }

    private void getData() {
        mViewModel.getXlMessageInfoList("" + pageNum, "" + pageSize, "" + msgType);
    }

}