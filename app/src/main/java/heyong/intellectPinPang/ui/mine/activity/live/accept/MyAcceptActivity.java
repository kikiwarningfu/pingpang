package heyong.intellectPinPang.ui.mine.activity.live.accept;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.live.LiveAcceptAdapter;
import heyong.intellectPinPang.bean.live.LiveMatchByBean;
import heyong.intellectPinPang.bean.live.LiveUserOrderListBean;
import heyong.intellectPinPang.bean.live.LiveWxPayBean;
import heyong.intellectPinPang.databinding.ActivityMyAcceptBinding;
import heyong.intellectPinPang.factory.PayFactory;
import heyong.intellectPinPang.bean.pay.WXPayBean;
import heyong.intellectPinPang.live.activity.BindZFBActivity;
import heyong.intellectPinPang.live.model.LiveViewModel;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;
import heyong.intellectPinPang.wxapi.WXPayEntryActivity;

public class MyAcceptActivity extends BaseActivity<ActivityMyAcceptBinding, LiveViewModel> implements View.OnClickListener, OnRefreshListener, WXPayEntryActivity.WXListener {

    private int orderType = 0;// 1全部  2未支付 3 已支付 4 已完成 5 退款    6 自拍全部  7 自拍未支付  8自拍待直播  9自怕已完成
    private int myOwnerType = 1;//1 我发布的  2我接取的
    public static final String TAG = MyAcceptActivity.class.getSimpleName();

    MyRefreshAnimHeader mRefreshAnimHeader;
    private boolean haveMore = true;
    private int pageNum = 1;
    private int pageSize = 10;
    List<LiveUserOrderListBean.ListBean> list = new ArrayList<>();

    //MyPublishAdapter   LiveAcceptAdapter
    LiveAcceptAdapter liveAcceptAdapter;

    MyDividerItemDecoration mSixDiD;
    String orderId = "";
    private String strRequestId = "";


    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_my_accept;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);


        WXPayEntryActivity.setWXListener(this);

        orderType = 1;//全部
        myOwnerType = 1;

        liveAcceptAdapter = new LiveAcceptAdapter();

        mBinding.mRecyclerView.removeItemDecoration(mSixDiD);
        mSixDiD = new MyDividerItemDecoration(this, 10,
                getResources().getColor(R.color.color_f5));
        mBinding.mRecyclerView.addItemDecoration(mSixDiD);
        mBinding.mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.mRecyclerView.setHasFixedSize(true);
        ((SimpleItemAnimator) mBinding.mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mBinding.mRecyclerView.setAdapter(liveAcceptAdapter);
        mRefreshAnimHeader = new MyRefreshAnimHeader(MyAcceptActivity.this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
        mViewModel.liveMatchCheckOrderLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    new MessageDialogBuilder(MyAcceptActivity.this)
                            .setMessage("")
                            .setTvTitle("支付成功")
                            .setTvSure("确定")
                            .setBtnCancleHint(true)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getData();
                                }
                            })
                            .show();
                } else {
                    new MessageDialogBuilder(MyAcceptActivity.this)
                            .setMessage("")
                            .setTvTitle("是否支付成功？")
                            .setTvCancel("取消")
                            .setTvSure("确定")
                            .setBtnCancleHint(false)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getData();
                                }
                            })
                            .show();
                }
            }
        });

        //接收的适配器   接单人的删除
        liveAcceptAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    LiveUserOrderListBean.ListBean listBean = liveAcceptAdapter.getData().get(position);
                    strRequestId = "" + listBean.getRequestId();
                    switch (view.getId()) {
//                    case R.id.tv_give_up_no_start://放弃悬赏   调用 接单人 的取消悬赏
//                        jieDanCancel("" + listBean.getRequestId());
//                        break;
//                    case R.id.tv_start_live_no_start://开始直播   跳转到直播的界面
//                        //判断是否获取了权限
//                        boolean checkResult = PermissionUtils.checkPermissionsGroup(MyAcceptActivity.this, permission);
//                        if (!checkResult) {
//                            PermissionUtils.requestPermissions(MyAcceptActivity.this, permission, PERMISSION_REQUEST_CODE);
//                        } else {
//                            goLiveActivity();
//                        }
//                        break;
//                    case R.id.tv_see_comment_accept_complete://查看评价
//                    case R.id.tv_see_comment_with_draw_ing:
//                    case R.id.tv_see_commtent_success:
//                    case R.id.tv_see_with_draw_fail:
//                        String strName = "";
//                        List<LiveUserOrderListBean.ListBean.LeftBean> left = listBean.getLeft();
//                        List<LiveUserOrderListBean.ListBean.RightBean> right = listBean.getRight();
//                        if (left.size() == 1) {
//                            strName = left.get(0).getName() + "  VS  " + right.get(0).getName();
//                        } else if (left.size() == 2) {
//                            strName = left.get(0).getName() + " " + left.get(1).getName() + "  VS  "
//                                    + right.get(0).getName() + " " + right.get(1).getName();
//                        }
//                        EvaluationRewardDetailActivity.goEvaluationRewardActivity(MyAcceptActivity.this,
//                                "" + listBean.getRequestId(), "" + listBean.getMatchName(), ""
//                                        + listBean.getTitle(), "" + strName);
//
//                        break;
//                    case R.id.tv_see_huifang_complete://查看回放
//                    case R.id.tv_see_huifang_unfinished:
//                        AliPlayVideoActivity.goAliPlayVideoActivity(MyAcceptActivity.this, "" + listBean.getRequestId()
//                                , "LUBO");
//                        break;
//                    case R.id.tv_tixian_money://提现赏金     缺少个弹窗
//                    case R.id.tv_again_tixian_draw_fail://再次提现
//                        showTixianDialog("" + listBean.getMoney(), "" + listBean.getRequestId());
//                        break;
//                    case R.id.tv_detail_with_draw_ing://提现详情
//                    case R.id.tv_detail_success:
//                    case R.id.tv_detail_with_draw_fail:
//                        WithDrawDetailActivity.goWithDrawDetail(MyAcceptActivity.this, "" + listBean.getRequestId());
////                        goActivity(WithDrawDetailActivity.class);
//                        break;
//                    case R.id.tv_delete_order_unfinish://删除订单
//                    case R.id.tv_delete_order_success:
//                    case R.id.tv_delte_order:
//                        deleteOrderMethod("" + listBean.getRequestId());
//                        break;
//                    case R.id.tv_refund_detail_unfinish://退款详情
//                    case R.id.tv_refund_detail_complete:
//                        RefundDetailActivity.goRefundDetailActivity(MyAcceptActivity.this, "" + listBean.getRequestId());
//                        break;
                    }
                }
            }
        });
//        myEventAdapter.setOnItemChildClickListener(this);
//        myEventAdapter.setOnItemClickListener(this);
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
        //接单的
        mViewModel.liveUserOrderReceiveLiveData.observe(this, new Observer<ResponseBean<LiveUserOrderListBean>>() {
            @Override
            public void onChanged(ResponseBean<LiveUserOrderListBean> loginBeanResponseBean) {
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
                        list.addAll(loginBeanResponseBean.getData().getList());
                        if (list != null && list.size() < pageSize) {
                            haveMore = false;
                            mBinding.swFresh.setEnableLoadmore(false);
                        }
                        if (list != null && list.size() > 0) {
                            for (int i = 0; i < list.size(); i++) {
                                LiveUserOrderListBean.ListBean listBean = list.get(i);
                                if (orderType == 1)//全部
                                {
                                    int payStatus = listBean.getPayStatus();
                                    int receiveStatus = listBean.getReceiveStatus();
                                    int isAppraise = listBean.getIsAppraise();
                                    int isWithDraw = listBean.getIsWithDraw();
                                    if ((payStatus == 1 && receiveStatus == 1) || (payStatus == 0 && receiveStatus == 1)) {
                                        listBean.setShowOwnerStatus(1);//待直播  接单成功
                                    }
                                    if (payStatus == 4) {//已完成
                                        listBean.setShowOwnerStatus(2);//已完成
                                    } else if (receiveStatus == 2) {
                                        //未完成
                                        listBean.setShowOwnerStatus(3);
                                    }
                                    if (payStatus == 4 && isWithDraw == 1) {//可以体现
                                        listBean.setShowOwnerStatus(4);
                                    } else if (payStatus == 4 && isWithDraw == 3) {//体现成功
                                        listBean.setShowOwnerStatus(4);
                                    } else if (payStatus == 4 && isWithDraw == 4) {//提现失败
                                        listBean.setShowOwnerStatus(4);
                                    }
                                } else if (orderType == 2)//待直播
                                {
                                    listBean.setShowOwnerStatus(1);
                                } else if (orderType == 3)//未完成
                                {
                                    listBean.setShowOwnerStatus(3);
                                } else if (orderType == 4)//已完成
                                {
                                    listBean.setShowOwnerStatus(2);
                                } else//已提现
                                {
                                    listBean.setShowOwnerStatus(4);
                                }
                            }
                        }
                        liveAcceptAdapter.setNewData(list);
                    }
                } else {
                    toast("" + loginBeanResponseBean.getMessage());
                }
                if (liveAcceptAdapter.getData().size() > 0) {
                    mBinding.llEmptyView.setVisibility(View.GONE);
                } else {
                    if (myOwnerType == 1) {
                        mBinding.tvJoinTypeText.setText("暂时没有发布直播悬赏令");
                    } else {
                        mBinding.tvJoinTypeText.setText("暂时没有接取直播悬赏令");
                    }
                    mBinding.llEmptyView.setVisibility(View.VISIBLE);
                }

            }
        });

        //再去支付
        mViewModel.liveMatchRepayLiveData.observe(this, new Observer<ResponseBean<LiveMatchByBean>>() {
            @Override
            public void onChanged(ResponseBean<LiveMatchByBean> responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    LiveMatchByBean data = responseBean.getData();
                    if (data != null) {
                        orderId = data.getOrderId();
                        String payStr = data.getPayStr();
                        try {
                            LiveWxPayBean liveWxPayBean = new Gson().fromJson(payStr, LiveWxPayBean.class);
                            //微信
                            PayFactory payFactory = new PayFactory();
                            WXPayBean wxPayBean = new WXPayBean();
                            wxPayBean.setAppid("" + liveWxPayBean.getAppid());
                            wxPayBean.setNoncestr("" + liveWxPayBean.getNoncestr());
                            wxPayBean.setPackagestr("" + liveWxPayBean.getPackageX());
                            wxPayBean.setPartnerid("" + liveWxPayBean.getPartnerid());
                            wxPayBean.setPrepayid("" + liveWxPayBean.getPrepayid());
                            wxPayBean.setSign("" + liveWxPayBean.getSign());
                            wxPayBean.setTimestamp("" + liveWxPayBean.getTimestamp());
                            payFactory.WxPay(MyAcceptActivity.this, wxPayBean);
                        } catch (Exception e) {
                            PayFactory payFactory = new PayFactory();
                            payFactory.AliPay(payStr, MyAcceptActivity.this, new PayFactory.PayListener() {
                                public void onPayListener(String s, boolean isOk) {
                                    if (isOk) {
                                        mViewModel.liveMatchCheckOrder("" + orderId);
                                    } else {
                                        toast("" + s);
                                    }
                                }
                            });
                        }

                    } else {
                        toast("" + responseBean.getMessage());
                    }
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        //删除订单
        mViewModel.liveUserOrderReceiveDeleteLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    toast("修改成功");
                    getData();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        //删除发布人的订单   订单
        mViewModel.liveUserOrderPublishDeleteLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    toast("修改成功");
                    getData();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

        mViewModel.liveUserWithDrawlData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    toast("申请成功");
                    getData();
                } else if (responseBean.getErrorCode().equals("300000-100012")) {
                    new MessageDialogBuilder(MyAcceptActivity.this)
                            .setMessage("")
                            .setTvTitle("您还未绑定支付宝账户，不可提现，\n" +
                                    "是否绑定支付宝账户？\n")
                            .setTvCancel("取消")
                            .setBtnCancleHint(false)
                            .setTvSure("去绑定")
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goActivity(BindZFBActivity.class);
                                }
                            })
                            .show();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.liveUserOrderPublishData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    toast("取消成功");
                    getData();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.liveUserOrderCancelData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    toast("取消成功");
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

            case R.id.ll_accept_to_live://待直播
                initClearView();
                mBinding.tvAcceptToLive.setTextColor(Color.parseColor("#4795ED"));
                mBinding.viewAcceptToLive.setVisibility(View.VISIBLE);
                orderType = 1;
                myOwnerType = 2;
//                mBinding.tvMyAcceptAll.setTextColor(Color.parseColor("#4795ED"));
//                mBinding.viewMyAcceptAll.setVisibility(View.VISIBLE);
//                /*请求接口*/
//                mBinding.mRecyclerView.setAdapter(liveAcceptAdapter);
                getData();

                break;

            case R.id.ll_accept_not_complete://未完成
                initClearView();
                mBinding.tvAcceptUnfinished.setTextColor(Color.parseColor("#4795ED"));
                mBinding.viewAcceptUnfinished.setVisibility(View.VISIBLE);
                orderType = 2;
                myOwnerType = 2;
//                mBinding.tvMyAcceptAll.setTextColor(Color.parseColor("#4795ED"));
//                mBinding.viewMyAcceptAll.setVisibility(View.VISIBLE);
//                /*请求接口*/
//                mBinding.mRecyclerView.setAdapter(liveAcceptAdapter);
                getData();

                break;
            case R.id.ll_accept_completed://已完成
                initClearView();
                mBinding.tvAcceptCompleted.setTextColor(Color.parseColor("#4795ED"));
                mBinding.viewAcceptCompleted.setVisibility(View.VISIBLE);
                orderType = 3;
                myOwnerType = 2;
//                mBinding.tvMyAcceptAll.setTextColor(Color.parseColor("#4795ED"));
//                mBinding.viewMyAcceptAll.setVisibility(View.VISIBLE);
//                /*请求接口*/
//                mBinding.mRecyclerView.setAdapter(liveAcceptAdapter);
                getData();

                break;
            case R.id.ll_accept_with_draw://可提现
                initClearView();
                mBinding.tvAcceptWithDraw.setTextColor(Color.parseColor("#4795ED"));
                mBinding.viewAcceptWithDraw.setVisibility(View.VISIBLE);
                orderType = 4;
                myOwnerType = 2;
//                mBinding.tvMyAcceptAll.setTextColor(Color.parseColor("#4795ED"));
//                mBinding.viewMyAcceptAll.setVisibility(View.VISIBLE);
//                /*请求接口*/
//                mBinding.mRecyclerView.setAdapter(liveAcceptAdapter);
                getData();


                break;
            case R.id.ll_accept_unusual_order://异常订单

                initClearView();
                mBinding.tvAcceptUnusualOrder.setTextColor(Color.parseColor("#4795ED"));
                mBinding.viewAcceptUnusualOrder.setVisibility(View.VISIBLE);
                orderType = 5;
                myOwnerType = 2;
//                mBinding.tvMyAcceptAll.setTextColor(Color.parseColor("#4795ED"));
//                mBinding.viewMyAcceptAll.setVisibility(View.VISIBLE);
//                /*请求接口*/
//                mBinding.mRecyclerView.setAdapter(liveAcceptAdapter);
                getData();

                break;
        }
    }

    private void initClearView() {
        mBinding.viewAcceptToLive.setVisibility(View.INVISIBLE);
        mBinding.viewAcceptUnfinished.setVisibility(View.INVISIBLE);
        mBinding.viewAcceptCompleted.setVisibility(View.INVISIBLE);
        mBinding.viewAcceptWithDraw.setVisibility(View.INVISIBLE);
        mBinding.viewAcceptUnusualOrder.setVisibility(View.INVISIBLE);

        mBinding.tvAcceptToLive.setTextColor(Color.parseColor("#666666"));
        mBinding.tvAcceptUnfinished.setTextColor(Color.parseColor("#666666"));
        mBinding.tvAcceptCompleted.setTextColor(Color.parseColor("#666666"));
        mBinding.tvAcceptWithDraw.setTextColor(Color.parseColor("#666666"));
        mBinding.tvAcceptUnusualOrder.setTextColor(Color.parseColor("#666666"));

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mBinding.swFresh.setEnableLoadmore(true);
        haveMore = true;
        pageNum = 1;
        getData();
    }

    @Override
    public void wxCallBack(int code) {
        switch (code) {
            case 0:
                mViewModel.liveMatchCheckOrder("" + orderId);
                break;
            case -1:
                toast("支付异常");
//                finish();
                break;
            case -2:
                toast("用户取消");
//                finish();
                break;
        }
    }

    private void getData() {
        mViewModel.LiveOrderList("" + pageNum, "" + 10, orderType, myOwnerType);
    }

}