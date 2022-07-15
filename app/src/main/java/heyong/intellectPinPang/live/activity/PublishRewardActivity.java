package heyong.intellectPinPang.live.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.live.LiveMatchByBean;
import heyong.intellectPinPang.bean.live.LiveMatchDetailBean;
import heyong.intellectPinPang.bean.live.LiveWxPayBean;
import heyong.intellectPinPang.databinding.ActivityPublishRewardBinding;
import heyong.intellectPinPang.factory.PayFactory;
import heyong.intellectPinPang.bean.pay.WXPayBean;
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.wxapi.WXPayEntryActivity;

/**
 * 发布悬赏令
 */
public class PublishRewardActivity extends BaseActivity<ActivityPublishRewardBinding, HomePageViewModel> implements View.OnClickListener, WXPayEntryActivity.WXListener {
    public static final String IDS = "ids";
    private String ids;
    private double payMoney = 0;
    private int payWay = 0;
    private int luboType = 0;
    int matchMethod = 0;
    LiveMatchDetailBean data;
    String orderId = "";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_publish_reward;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        ids = getIntent().getStringExtra(IDS);
        mBinding.setListener(this);
        WXPayEntryActivity.setWXListener(this);
        mViewModel.liveMatchDetail("" + ids);
        luboType = 1;
        mViewModel.liveMatchCheckOrderLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    new MessageDialogBuilder(PublishRewardActivity.this)
                            .setMessage("")
                            .setTvTitle("支付成功")
                            .setTvSure("确定")
                            .setBtnCancleHint(true)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            })
                            .show();
                } else {
                    new MessageDialogBuilder(PublishRewardActivity.this)
                            .setMessage("")
                            .setTvTitle("是否支付成功？")
                            .setTvCancel("取消")
                            .setTvSure("确定")
                            .setBtnCancleHint(false)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            })
                            .show();
                }
            }
        });

        mViewModel.liveMatchDetailLiveData.observe(this, new Observer<ResponseBean<LiveMatchDetailBean>>() {
            @Override
            public void onChanged(ResponseBean<LiveMatchDetailBean> liveMatchDetailBeanResponseBean) {
                if (liveMatchDetailBeanResponseBean.getErrorCode().equals("200")) {
                    data = liveMatchDetailBeanResponseBean.getData();
                    if (data != null) {
                        List<LiveMatchDetailBean.LeftBean> left = data.getLeft();
                        List<LiveMatchDetailBean.RightBean> right = data.getRight();
                        mBinding.tvMatchTitle.setText("" + data.getMatchName());
                        mBinding.tvGroupTitle.setText("" + data.getTitle());
                        mBinding.tvBeginTime.setText("比赛时间：" + data.getGameTime());
                        mBinding.tvAddress.setText("比赛地点：" + data.getAddress());
                        mBinding.tvTableNum.setText("比赛球台：" + data.getTableNum() + "台");

                        if (left != null && left.size() == 1) {   //单打
                            mBinding.llOne.setVisibility(View.VISIBLE);
                            mBinding.llTwo.setVisibility(View.GONE);
                            if (left != null && left.size() > 0) {
                                LiveMatchDetailBean.LeftBean leftBean = left.get(0);
                                mBinding.tvLeftName.setText("" + leftBean.getName());
                                ImageLoader.loadImage(mBinding.viewLeftLogo, leftBean.getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                            } else {
                                ImageLoader.loadImage(mBinding.viewLeftLogo, R.drawable.img_default_avatar);
                                mBinding.tvLeftName.setText("");
                            }
                            if (right != null && right.size() > 0) {
                                LiveMatchDetailBean.RightBean rightBean = right.get(0);
                                mBinding.tvRightName.setText("" + rightBean.getName());
                                ImageLoader.loadImage(mBinding.viewRightOval, rightBean.getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                            } else {
                                ImageLoader.loadImage(mBinding.viewRightOval, R.drawable.img_default_avatar);
                                mBinding.tvRightName.setText("");
                            }
                        } else if (left != null && left.size() == 2) {    //双打
                            mBinding.llOne.setVisibility(View.GONE);
                            mBinding.llTwo.setVisibility(View.VISIBLE);
                            try {
                                if (left != null && left.size() >= 2) {
                                    LiveMatchDetailBean.LeftBean leftBean = left.get(0);
                                    LiveMatchDetailBean.LeftBean leftBean1 = left.get(1);
                                    mBinding.tvLeftNameOne.setText("" + leftBean.getName());
                                    mBinding.tvLeftNameTwo.setText("" + leftBean1.getName());
                                    ImageLoader.loadImage(mBinding.ivLeftOneLogo, leftBean.getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                                    ImageLoader.loadImage(mBinding.ivLeftTwoLogo, leftBean1.getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                                } else {
                                    mBinding.tvLeftNameOne.setText("");
                                    mBinding.tvLeftNameTwo.setText("");
                                    ImageLoader.loadImage(mBinding.ivLeftOneLogo, R.drawable.img_default_avatar);
                                    ImageLoader.loadImage(mBinding.ivLeftTwoLogo, R.drawable.img_default_avatar);
                                }
                                if (right != null && right.size() >= 2) {
                                    LiveMatchDetailBean.RightBean rightBean = right.get(0);
                                    LiveMatchDetailBean.RightBean rightBean1 = right.get(1);
                                    mBinding.tvRightNameOne.setText("" + rightBean.getName());
                                    mBinding.tvRightNameTwo.setText("" + rightBean1.getName());
                                    ImageLoader.loadImage(mBinding.ivRightLogoOne, rightBean.getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                                    ImageLoader.loadImage(mBinding.ivRightLogoTwo, rightBean1.getLogo(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                                } else {
                                    mBinding.tvRightNameOne.setText("");
                                    mBinding.tvRightNameTwo.setText("");
                                    ImageLoader.loadImage(mBinding.ivRightLogoOne, R.drawable.img_default_avatar);
                                    ImageLoader.loadImage(mBinding.ivRightLogoTwo, R.drawable.img_default_avatar);
                                }
                            } catch (Exception e) {

                            }
                        } else {     //不可能存在的情况
                            mBinding.llOne.setVisibility(View.GONE);
                            mBinding.llTwo.setVisibility(View.GONE);
                        }
                        matchMethod = data.getMatchMethod();
                        switch (matchMethod) {
                            case 1:
                                mBinding.tvMatchScount.setText("(" + "三局二胜" + ")");
                                payMoney = 50;
                                break;
                            case 2:
                                mBinding.tvMatchScount.setText("(" + "五局三胜" + ")");
                                payMoney = 60;
                                break;
                            case 3:
                                mBinding.tvMatchScount.setText("(" + "七局四胜" + ")");
                                payMoney = 70;
                                break;
                        }
                        payMoney=  data.getPublicMoney();
                        mBinding.tvMoney.setText("¥" + data.getPublicMoney());
                    } else {
                        toast("" + liveMatchDetailBeanResponseBean.getMessage());
                    }

                } else {
                    toast("" + liveMatchDetailBeanResponseBean.getMessage());
                }
            }
        });

        mViewModel.liveMatchPublishLiveData.observe(this, new Observer<ResponseBean<LiveMatchByBean>>() {
            @Override
            public void onChanged(ResponseBean<LiveMatchByBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    LiveMatchByBean data = responseBean.getData();
                    if (data != null) {
                        orderId = data.getOrderId();
                        String payStr = data.getPayStr();
                        if (payWay == 1) {
                            //支付宝
                            PayFactory payFactory = new PayFactory();
                            payFactory.AliPay(payStr, PublishRewardActivity.this, new PayFactory.PayListener() {
                                public void onPayListener(String s, boolean isOk) {
                                    if (isOk) {
                                        mViewModel.liveMatchCheckOrder("" + orderId);
                                    } else {
                                        finish();
                                    }
                                }
                            });
                        } else {
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
                            payFactory.WxPay(PublishRewardActivity.this, wxPayBean);
                        }

                    } else {
                        toast("" + responseBean.getMessage());
                    }
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_my_lubo://自己录播
                ImageLoader.loadImage(mBinding.ivBaomingNeedYes, R.drawable.icon_login_selected);
                ImageLoader.loadImage(mBinding.ivBaomingNeedNo, R.drawable.icon_login_unselected);
                luboType = 2;
                switch (matchMethod) {
                    case 1:
                        mBinding.tvMatchScount.setText("(" + "三局二胜" + ")");
                        payMoney = 10;
                        break;
                    case 2:
                        mBinding.tvMatchScount.setText("(" + "五局三胜" + ")");
                        payMoney = 12;
                        break;
                    case 3:
                        mBinding.tvMatchScount.setText("(" + "七局四胜" + ")");
                        payMoney = 14;
                        break;
                }
                payMoney=  data.getSelfMoney();

                mBinding.tvMoney.setText("¥" + data.getSelfMoney());
                break;
            case R.id.ll_no_lubo://他人录播
                ImageLoader.loadImage(mBinding.ivBaomingNeedYes, R.drawable.icon_login_unselected);
                ImageLoader.loadImage(mBinding.ivBaomingNeedNo, R.drawable.icon_login_selected);
                luboType = 1;
                switch (matchMethod) {
                    case 1:
                        mBinding.tvMatchScount.setText("(" + "三局二胜" + ")");
                        payMoney = 50;
                        break;
                    case 2:
                        mBinding.tvMatchScount.setText("(" + "五局三胜" + ")");
                        payMoney = 60;
                        break;
                    case 3:
                        mBinding.tvMatchScount.setText("(" + "七局四胜" + ")");
                        payMoney = 70;
                        break;
                }
                payMoney=  data.getPublicMoney();
                mBinding.tvMoney.setText("¥" + data.getPublicMoney());
                break;

            case R.id.cb_pay_zfb://支付宝
            case R.id.ll_cb_pay_zfb:
                mBinding.cbPayZfb.setChecked(true);
                mBinding.cbPayWx.setChecked(false);
                payWay = 1;
                break;
            case R.id.cb_pay_wx://微信
            case R.id.ll_cb_pay_wx:
                mBinding.cbPayZfb.setChecked(false);
                mBinding.cbPayWx.setChecked(true);
                payWay = 2;
                break;
            case R.id.tv_commit://liveMatchBuy
                Log.e(TAG, "onClick: "+payMoney );
                if (luboType == 2) {
                    mViewModel.liveMatchPublish("" + payMoney, "" + ids, "" + payWay, "" + luboType);
                } else {
                    mViewModel.liveMatchPublish("" + payMoney, "" + ids, "" + payWay, "" + luboType);
                }
                break;
        }
    }

    @Override
    public void wxCallBack(int code) {
        switch (code) {
            case 0:
                mViewModel.liveMatchCheckOrder("" + orderId);
//                toast("支付成功");
//                finish();
                break;
            case -1:
                toast("支付异常");
                finish();
                break;
            case -2:
                toast("用户取消");
                finish();
                break;
        }
    }
}