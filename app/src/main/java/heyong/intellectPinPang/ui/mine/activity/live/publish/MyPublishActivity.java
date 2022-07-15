package heyong.intellectPinPang.ui.mine.activity.live.publish;

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
import heyong.intellectPinPang.adapter.live.MyPublishAdapter;
import heyong.intellectPinPang.bean.live.LiveMatchByBean;
import heyong.intellectPinPang.bean.live.LiveUserOrderListBean;
import heyong.intellectPinPang.bean.live.LiveWxPayBean;
import heyong.intellectPinPang.databinding.ActivityMyPublishBinding;
import heyong.intellectPinPang.factory.PayFactory;
import heyong.intellectPinPang.bean.pay.WXPayBean;
import heyong.intellectPinPang.live.activity.BindZFBActivity;
import heyong.intellectPinPang.live.model.LiveViewModel;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;
import heyong.intellectPinPang.wxapi.WXPayEntryActivity;

public class MyPublishActivity extends BaseActivity <ActivityMyPublishBinding, LiveViewModel> implements View.OnClickListener, OnRefreshListener, WXPayEntryActivity.WXListener {
    private int orderType = 0;// 1全部  2未支付 3 已支付 4 已完成 5 退款    6 自拍全部  7 自拍未支付  8自拍待直播  9自怕已完成
    private int myOwnerType = 1;//1 我发布的  2我接取的

    MyRefreshAnimHeader mRefreshAnimHeader;
    private boolean haveMore = true;
    private int pageNum = 1;
    private int pageSize = 10;
    List<LiveUserOrderListBean.ListBean> list = new ArrayList<>();

    MyPublishAdapter myPublishAdapter;

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
        return R.layout.activity_my_publish;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        WXPayEntryActivity.setWXListener(this);
        mBinding.setListener(this);
        myPublishAdapter = new MyPublishAdapter();
        mBinding.mRecyclerView.removeItemDecoration(mSixDiD);
        mSixDiD = new MyDividerItemDecoration(this, 10,
                getResources().getColor(R.color.color_f5));
        mBinding.mRecyclerView.addItemDecoration(mSixDiD);
        mBinding.mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.mRecyclerView.setHasFixedSize(true);
        ((SimpleItemAnimator) mBinding.mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mBinding.mRecyclerView.setAdapter(myPublishAdapter);
        mRefreshAnimHeader = new MyRefreshAnimHeader(MyPublishActivity.this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
        mViewModel.liveMatchCheckOrderLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    new MessageDialogBuilder(MyPublishActivity.this)
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
                    new MessageDialogBuilder(MyPublishActivity.this)
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
        //发布人的删除
        myPublishAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    LiveUserOrderListBean.ListBean listBean = myPublishAdapter.getData().get(position);
                    switch (view.getId()) {
                        case R.id.tv_delete_order_un_pay://删除订单
//                    case R.id.tv_delete_order_refund:
                            deletePublishOrderMethod("" + listBean.getRequestId());
                            break;
                        case R.id.tv_to_pay_un_pay://去支付
                            liveMatchRepayMethod("" + listBean.getOrderId());
                            break;

                        case R.id.tv_start_live_publish://开始观看     跳转到播放界面
//                    case R.id.tv_start_live_complete://观看回放     跳转到 播放界面
//                    case R.id.tv_watch_huifang_refund://开始回放
//                            AliPlayVideoActivity.goAliPlayVideoActivity(MyPublishActivity.this, "" + listBean.getRequestId()
//                                    , "LUBO");

                            break;
                        case R.id.tv_cancel_order_publish://取消订单  调用发布人的取消悬赏
                            fabuCancel("" + listBean.getRequestId());

                            break;
//                    case R.id.tv_apply_refund_complete://申请退款
//                        ApplyRefundActivity.goApplyRefundActivity(MyPublishActivity.this, "" + listBean.getMoney(), "" + listBean.getRequestId());
//                        break;
//                    case R.id.tv_comment_live_complete://评价直播
//                    case R.id.tv_refund_envalite_order:
//                        String strName = "";
//                        List<LiveUserOrderListBean.ListBean.LeftBean> left = listBean.getLeft();
//                        List<LiveUserOrderListBean.ListBean.RightBean> right = listBean.getRight();
//                        if (left.size() == 1) {
//                            strName = left.get(0).getName() + "  VS  " + right.get(0).getName();
//                        } else if (left.size() == 2) {
//                            strName = left.get(0).getName() + " " + left.get(1).getName() + "  VS  "
//                                    + right.get(0).getName() + " " + right.get(1).getName();
//                        }
//                        EvaluationRewardActivity.goEvaluationRewardActivity(MyPublishActivity.this,
//                                "" + listBean.getMatchName(), "" + listBean.getTitle(), "" + strName, "" + listBean.getRequestId());
//
//                        break;
//                    case R.id.tv_refund_detail://退款详情
//                        RefundDetailActivity.goRefundDetailActivity(MyPublishActivity.this, "" + listBean.getRequestId());
//                        break;
//                    case R.id.tv_see_comment:
//                        String strName1 = "";
//                        List<LiveUserOrderListBean.ListBean.LeftBean> left1 = listBean.getLeft();
//                        List<LiveUserOrderListBean.ListBean.RightBean> right1 = listBean.getRight();
//                        if (left1.size() == 1) {
//                            strName1 = left1.get(0).getName() + "  VS  " + right1.get(0).getName();
//                        } else if (left1.size() == 2) {
//                            strName1 = left1.get(0).getName() + " " + left1.get(1).getName() + "  VS  "
//                                    + right1.get(0).getName() + " " + right1.get(1).getName();
//                        }
//                        EvaluationRewardDetailActivity.goEvaluationRewardActivity(MyPublishActivity.this,
//                                "" + listBean.getRequestId(), "" + listBean.getMatchName(), ""
//                                        + listBean.getTitle(), "" + strName1);
//
//                        break;
                    }
                }
            }
        });

        mBinding.mRecyclerView.setAdapter(myPublishAdapter);
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

        //发布的
        mViewModel.liveUserOrderPublishLiveData.observe(this, new Observer<ResponseBean<LiveUserOrderListBean>>() {
            @Override
            public void onChanged(ResponseBean<LiveUserOrderListBean> loginBeanResponseBean) {
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

                        list.addAll(loginBeanResponseBean.getData().getList());
                        if (list != null && list.size() < pageSize) {
                            haveMore = false;
                            mBinding.swFresh.setEnableLoadmore(false);
                        }

                        if (list.size() > 0) {
                            for (int i = 0; i < list.size(); i++) {
                                LiveUserOrderListBean.ListBean listBean = list.get(i);
                                if (orderType == 1) {
                                    if (listBean.getPayStatus() == -1 || listBean.getPayStatus() == 5) {
                                        listBean.setShowOwnerStatus(1);
                                    } else {
                                        int receiveStatus = listBean.getReceiveStatus();
                                        int payStatus = listBean.getPayStatus();
                                        int isAppraise = listBean.getIsAppraise();
                                        if (payStatus == 0 && receiveStatus == -1) {
                                            listBean.setShowOwnerStatus(2);//已支付  直播未开始  未接取
                                        } else if (payStatus == 1 && receiveStatus == 3) {
                                            listBean.setShowOwnerStatus(2);//直播进行中
                                        } else if (payStatus == 1 && receiveStatus == 1) {//已接取（直播未开始）
                                            listBean.setShowOwnerStatus(2);//已 接取  直播未开始
                                        } else if ((payStatus == 4 && isAppraise == 0) || (payStatus == 4 && isAppraise == 1)) {
                                            listBean.setShowOwnerStatus(3);//已完成
                                        } else if (receiveStatus == -1 && payStatus == 3) {
                                            //订单无人接取  退款成功
                                            listBean.setShowOwnerStatus(4);//退款
                                        } else if (payStatus == 2) {
                                            listBean.setShowOwnerStatus(4);//退款中
                                        } else if (payStatus == 3) {
                                            listBean.setShowOwnerStatus(4);//退款成功
                                        } else if (payStatus == 6) {
                                            listBean.setShowOwnerStatus(4);//退款失败
                                        }
                                    }
                                    list.set(i, listBean);
                                } else if (orderType == 2)//未支付
                                {
                                    listBean.setShowOwnerStatus(1);//已支付  直播未开始  未接取
                                } else if (orderType == 3)//已支付
                                {
                                    listBean.setShowOwnerStatus(2);//已支付  直播未开始  未接取
                                } else if (orderType == 4)//已完成
                                {
                                    listBean.setShowOwnerStatus(3);
                                } else   //退款
                                {
                                    listBean.setShowOwnerStatus(4);
                                }
                            }
                        }
                        myPublishAdapter.setNewData(list);
                    }
                }
                if (myPublishAdapter.getData().size() > 0) {
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
                            payFactory.WxPay(MyPublishActivity.this, wxPayBean);
                        } catch (Exception e) {
                            PayFactory payFactory = new PayFactory();
                            payFactory.AliPay(payStr, MyPublishActivity.this, new PayFactory.PayListener() {
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
                    new MessageDialogBuilder(MyPublishActivity.this)
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
        switch (v.getId())
        {

            case R.id.ll_publish_accepted://已被接取
                initClearView();
                mBinding.tvPublishAccepted.setTextColor(Color.parseColor("#4795ED"));
                mBinding.viewPublishAccepted.setVisibility(View.VISIBLE);
                orderType = 0;
                myOwnerType = 1;
//                mBinding.tvMyAcceptAll.setTextColor(Color.parseColor("#4795ED"));
//                mBinding.viewMyAcceptAll.setVisibility(View.VISIBLE);
//                /*请求接口*/
//                mBinding.mRecyclerView.setAdapter(liveAcceptAdapter);
                getData();
                break;
            case R.id.ll_publish_no_accepted://未被截取
                initClearView();
                mBinding.tvPublishNoAccepted.setTextColor(Color.parseColor("#4795ED"));
                mBinding.viewPublishNoAccepted.setVisibility(View.VISIBLE);
                orderType = 1;
                myOwnerType = 1;
                getData();
                break;
            case R.id.ll_publish_completed://已完成
                initClearView();
                mBinding.tvPublishCompleted.setTextColor(Color.parseColor("#4795ED"));
                mBinding.viewPublishCompleted.setVisibility(View.VISIBLE);
                orderType = 2;
                myOwnerType = 1;
                getData();

                break;
            case R.id.ll_publish_refund://退款

                initClearView();
                mBinding.tvPublishRefund.setTextColor(Color.parseColor("#4795ED"));
                mBinding.viewPublishRefund.setVisibility(View.VISIBLE);
                orderType = 3;
                myOwnerType = 1;
                getData();


                break;
        }
    }
    /*初始化VIew*/
    private void initClearView() {
        mBinding.tvPublishNoAccepted.setTextColor(Color.parseColor("#666666"));
        mBinding.tvPublishAccepted.setTextColor(Color.parseColor("#666666"));
        mBinding.tvPublishCompleted.setTextColor(Color.parseColor("#666666"));
        mBinding.tvPublishRefund.setTextColor(Color.parseColor("#666666"));
        mBinding.viewPublishNoAccepted.setVisibility(View.INVISIBLE);
        mBinding.viewPublishAccepted.setVisibility(View.INVISIBLE);
        mBinding.viewPublishCompleted.setVisibility(View.INVISIBLE);
        mBinding.viewPublishRefund.setVisibility(View.INVISIBLE);

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
                break;
            case -2:
                toast("用户取消");
                break;
        }
    }

    private void getData() {
        mViewModel.LiveOrderList("" + pageNum, "" + 10, orderType, myOwnerType);
    }


    //删除发布人的订单
    public void deletePublishOrderMethod(String requestId) {
        showLoading();
        mViewModel.liveUserOrderPublishDelete("" + requestId);

    }
    public void liveMatchRepayMethod(String orderId) {
        showLoading();
        mViewModel.liveMatchRepay(orderId);
    }
    //发布取消
    public void fabuCancel(String requestId) {
        showLoading();
        mViewModel.liveUserOrderPublish("" + requestId);

    }
}