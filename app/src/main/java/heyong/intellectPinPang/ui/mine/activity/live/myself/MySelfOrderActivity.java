package heyong.intellectPinPang.ui.mine.activity.live.myself;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.live.HisShootingAdapter;
import heyong.intellectPinPang.bean.live.LiveMatchByBean;
import heyong.intellectPinPang.bean.live.LiveUserOrderListBean;
import heyong.intellectPinPang.bean.live.LiveWxPayBean;
import heyong.intellectPinPang.databinding.ActivityMySelfOrderAcitivityBinding;
import heyong.intellectPinPang.factory.PayFactory;
import heyong.intellectPinPang.bean.pay.WXPayBean;
import heyong.intellectPinPang.live.activity.BindZFBActivity;
import heyong.intellectPinPang.live.model.LiveViewModel;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;
import heyong.intellectPinPang.wxapi.WXPayEntryActivity;

public class MySelfOrderActivity extends BaseActivity<ActivityMySelfOrderAcitivityBinding, LiveViewModel> implements View.OnClickListener, OnRefreshListener, WXPayEntryActivity.WXListener {

    private int orderType = 0;// 1全部  2未支付 3 已支付 4 已完成 5 退款    6 自拍全部  7 自拍未支付  8自拍待直播  9自怕已完成
    private int myOwnerType = 1;//1 我发布的  2我接取的
    public static final String TAG = MySelfOrderActivity.class.getSimpleName();

    MyRefreshAnimHeader mRefreshAnimHeader;
    private boolean haveMore = true;
    private int pageNum = 1;
    private int pageSize = 10;
    List<LiveUserOrderListBean.ListBean> list = new ArrayList<>();

    //MyPublishAdapter   LiveAcceptAdapter
    HisShootingAdapter hisShootingAdapter;

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
        return R.layout.activity_my_self_order_acitivity;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);


        WXPayEntryActivity.setWXListener(this);

        orderType = 1;//全部
        myOwnerType = 1;


        hisShootingAdapter = new HisShootingAdapter();

        mBinding.mRecyclerView.removeItemDecoration(mSixDiD);
        mSixDiD = new MyDividerItemDecoration(this, 10,
                getResources().getColor(R.color.color_f5));
        mBinding.mRecyclerView.addItemDecoration(mSixDiD);
        mBinding.mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.mRecyclerView.setHasFixedSize(true);
        ((SimpleItemAnimator) mBinding.mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mBinding.mRecyclerView.setAdapter(hisShootingAdapter);
        mRefreshAnimHeader = new MyRefreshAnimHeader(MySelfOrderActivity.this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
        mViewModel.liveMatchCheckOrderLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    new MessageDialogBuilder(MySelfOrderActivity.this)
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
                    new MessageDialogBuilder(MySelfOrderActivity.this)
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
                            payFactory.WxPay(MySelfOrderActivity.this, wxPayBean);
                        } catch (Exception e) {
                            PayFactory payFactory = new PayFactory();
                            payFactory.AliPay(payStr, MySelfOrderActivity.this, new PayFactory.PayListener() {
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
                    new MessageDialogBuilder(MySelfOrderActivity.this)
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
            case R.id.ll_my_self_to_live://待直播
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    initClearView();
                    mBinding.tvMyselfToLive.setTextColor(Color.parseColor("#4795ED"));
                    mBinding.viewMySelfToLive.setVisibility(View.VISIBLE);
                    orderType = 1;
                    myOwnerType = 3;
//                mBinding.tvMyAcceptAll.setTextColor(Color.parseColor("#4795ED"));
//                mBinding.viewMyAcceptAll.setVisibility(View.VISIBLE);
//                /*请求接口*/
//                mBinding.mRecyclerView.setAdapter(liveAcceptAdapter);
                    getData();
                }


                break;
            case R.id.ll_myself_not_complete://已完成
                boolean fastClick1 = isFastClick();
                if (!fastClick1) {
                    initClearView();
                    mBinding.tvMyselfNotComplete.setTextColor(Color.parseColor("#4795ED"));
                    mBinding.viewMyselfNotComplete.setVisibility(View.VISIBLE);
                    orderType = 2;
                    myOwnerType = 3;
//                mBinding.tvMyAcceptAll.setTextColor(Color.parseColor("#4795ED"));
//                mBinding.viewMyAcceptAll.setVisibility(View.VISIBLE);
//                /*请求接口*/
//                mBinding.mRecyclerView.setAdapter(liveAcceptAdapter);
                    getData();
                }

                break;
        }
    }

    /**/
    private void initClearView() {
        mBinding.viewMySelfToLive.setVisibility(View.INVISIBLE);
        mBinding.viewMyselfNotComplete.setVisibility(View.INVISIBLE);
        mBinding.tvMyselfToLive.setTextColor(Color.parseColor("#666666"));
        mBinding.tvMyselfNotComplete.setTextColor(Color.parseColor("#666666"));
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