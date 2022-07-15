package heyong.intellectPinPang.ui.competition.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.math.BigDecimal;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.CompetitionItemBean;
import heyong.intellectPinPang.bean.competition.GameMatchBean;
import heyong.intellectPinPang.bean.competition.MatchPersonalOrderInfoBean;
import heyong.intellectPinPang.bean.competition.OrderIdBean;
import heyong.intellectPinPang.bean.pay.WXPayBean;
import heyong.intellectPinPang.databinding.ActivitySelectionsOfEventBinding;
import heyong.intellectPinPang.factory.PayFactory;
import heyong.intellectPinPang.live.adapter.MyTagRegisterAdapter;
import heyong.intellectPinPang.ui.competition.CompetitionViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.utils.MessageDialogHaveTitleSpannerBuilder;
import heyong.intellectPinPang.utils.MessageDialogTitleSpannerBuilder;
import heyong.intellectPinPang.wxapi.WXPayEntryActivity;

/**
 * 选择比赛项目
 */
public class SelectionsOfEventActivity extends BaseActivity<ActivitySelectionsOfEventBinding, CompetitionViewModel> implements View.OnClickListener, WXPayEntryActivity.WXListener {
    GameMatchBean gameMatchBean;
    private String projectItem = "";
    private int roomNeedType = -1;
    private int allMoney = 0;
    int replaceChargeEat;//是否需要食宿费 1 是需要  2 是不需要
    int replaceCharge;//是否代收报名费  1 是需要  2 是不需要


    private String projectItemId;
    private String projectId;
    private int clickType;
    private int freeType = 0;
    private boolean isClick = true;
    public static final String ORDER_ID = "orderId";
    public static final String MATCH_ID = "matchid";

    private String orderId = "";
    private String matchId = "";
    private String danda = "";
    private int commitSaveOrder = 0;
    private long commitOrderId = 0;

    /**
     * 请求接口 判断是否有食宿费  还有接口 获取比赛项目
     */
    @Override
    public int getLayoutRes() {
        return R.layout.activity_selections_of_event;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        gameMatchBean = (GameMatchBean) getIntent().getSerializableExtra("data");
        danda = getIntent().getStringExtra("danda");
        WXPayEntryActivity.setWXListener(this);
        mBinding.rvCompetitionProject.setVisibility(View.GONE);
        mViewModel.getOrderId();
        mBinding.llPayNormal.setVisibility(View.GONE);
        mBinding.llPayOrder.setVisibility(View.GONE);
        mBinding.tvSelectItem.setVisibility(View.GONE);
        if (gameMatchBean != null) {
            isClick = true;
            mBinding.tvMatchTitle.setText("" + gameMatchBean.getMatchTitle());
            mViewModel.competitionItem("" + gameMatchBean.getId(), "" + danda);
            replaceChargeEat = gameMatchBean.getReplaceChargeEat();//是否需要食宿费
            if (TextUtils.isEmpty(gameMatchBean.getReplaceCharge())) {
                replaceCharge = 0;
            } else {
                replaceCharge = Integer.parseInt(gameMatchBean.getReplaceCharge());//是否代收报名费  1 是需要 0 是不需要
            }
            mBinding.llPayNormal.setVisibility(View.VISIBLE);

            if (replaceCharge != 1 && replaceChargeEat != 1) {
                mBinding.llHavePayWay.setVisibility(View.GONE);
                mBinding.llNoPayWay.setVisibility(View.VISIBLE);
            } else {
                mBinding.llHavePayWay.setVisibility(View.VISIBLE);
                mBinding.llNoPayWay.setVisibility(View.GONE);
                //需要食宿费
                if (replaceChargeEat == 1) {
                    mBinding.tvNoShisu.setVisibility(View.GONE);
                    mBinding.rlHaveRoomBoard.setVisibility(View.VISIBLE);
                    mBinding.llPayWay.setVisibility(View.VISIBLE);
                    mBinding.rlHavePay.setVisibility(View.VISIBLE);
                    /*计算一下选中效果*/
                } else {
                    //不需要食宿费
                    mBinding.tvNoShisu.setVisibility(View.VISIBLE);
                    mBinding.rlHaveRoomBoard.setVisibility(View.GONE);
                    /*直接设置钱显示*/
                }
                //是否代收报名费
                if (replaceCharge == 1) {
                    mBinding.rlContainerYesBaoming.setVisibility(View.VISIBLE);
                    mBinding.rlContainerNoBaoming.setVisibility(View.GONE);
                    mBinding.llPayWay.setVisibility(View.VISIBLE);
                    mBinding.rlHavePay.setVisibility(View.VISIBLE);
                } else {
                    mBinding.rlContainerYesBaoming.setVisibility(View.GONE);
                    mBinding.rlContainerNoBaoming.setVisibility(View.VISIBLE);
                }
            }

            allMoney = gameMatchBean.getOwnFree();
            mBinding.tvMoney.setText("¥" + allMoney);
            mBinding.tvBaomingFei.setText("¥" + gameMatchBean.getOwnFree() + "/人");
            mBinding.tvEatFree.setText("¥" + gameMatchBean.getEatFree() + "/人/天;" + "共" + gameMatchBean.getDaysCount() + "天");
        } else {
            orderId = getIntent().getStringExtra(ORDER_ID);
            matchId = getIntent().getStringExtra(MATCH_ID);

            isClick = false;
            mBinding.llNeedConfirm.setVisibility(View.GONE);
            mBinding.llPayOrder.setVisibility(View.VISIBLE);

            mViewModel.gameMatch(matchId);

        }
        mViewModel.deleteXlEnrollMatchLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    toast("取消成功");
                    finish();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

        mViewModel.gameMatchLiveData.observe(this, new Observer<ResponseBean<GameMatchBean>>() {
            @Override
            public void onChanged(ResponseBean<GameMatchBean> gameMatchBeanResponseBean) {
                gameMatchBean = gameMatchBeanResponseBean.getData();
                mViewModel.matchPersonalOrderInfo("" + orderId);
                mBinding.tvMatchTitle.setText("" + gameMatchBean.getMatchTitle());
                replaceChargeEat = gameMatchBean.getReplaceChargeEat();//是否需要食宿费
                if (TextUtils.isEmpty(gameMatchBean.getReplaceCharge())) {
                    replaceCharge = 0;//是否代收报名费  1 是需要 0 是不需要
                } else {
                    replaceCharge = Integer.parseInt(gameMatchBean.getReplaceCharge());//是否代收报名费  1 是需要 0 是不需要
                }

                if (replaceCharge != 1 && replaceChargeEat != 1) {
                    mBinding.llHavePayWay.setVisibility(View.GONE);
                    mBinding.llNoPayWay.setVisibility(View.VISIBLE);
                    mBinding.tvIsAlreadyPay.setVisibility(View.VISIBLE);
                    mBinding.llPayOrder.setVisibility(View.GONE);
                    mBinding.llPayNormal.setVisibility(View.GONE);
                } else {
                    mBinding.llHavePayWay.setVisibility(View.VISIBLE);
                    mBinding.llNoPayWay.setVisibility(View.GONE);
                    //需要食宿费
                    if (replaceChargeEat == 1) {
                        mBinding.tvNoShisu.setVisibility(View.GONE);
                        mBinding.rlHaveRoomBoard.setVisibility(View.VISIBLE);
                        mBinding.llPayWay.setVisibility(View.VISIBLE);
                        mBinding.rlHavePay.setVisibility(View.VISIBLE);
                        /*计算一下选中效果*/
                    } else {
                        //不需要食宿费
                        mBinding.tvNoShisu.setVisibility(View.VISIBLE);
                        mBinding.rlHaveRoomBoard.setVisibility(View.GONE);
                        /*直接设置钱显示*/
                    }
                    //是否代收报名费
                    if (replaceCharge == 1) {
                        mBinding.rlContainerYesBaoming.setVisibility(View.VISIBLE);
                        mBinding.rlContainerNoBaoming.setVisibility(View.GONE);
                        mBinding.llPayWay.setVisibility(View.VISIBLE);
                        mBinding.rlHavePay.setVisibility(View.VISIBLE);
                    } else {
                        mBinding.rlContainerYesBaoming.setVisibility(View.GONE);
                        mBinding.rlContainerNoBaoming.setVisibility(View.VISIBLE);
                    }
                }
                mBinding.tvBaomingFei.setText("¥" + gameMatchBean.getOwnFree() + "/人");
                mBinding.tvEatFree.setText("¥" + gameMatchBean.getEatFree() + "/人/天;" + "共" + gameMatchBean.getDaysCount() + "天");

            }
        });
        /*个人的订单详情*/
        mViewModel.matchPersonalOrderLiveData.observe(this, new Observer<ResponseBean<MatchPersonalOrderInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<MatchPersonalOrderInfoBean> matchPersonalOrderInfoBeanResponseBean) {
                if (matchPersonalOrderInfoBeanResponseBean.getData() != null) {
                    MatchPersonalOrderInfoBean data = matchPersonalOrderInfoBeanResponseBean.getData();
                    int allFree = data.getAllFree();
                    mBinding.tvSelectItem.setVisibility(View.VISIBLE);
                    mBinding.tvSelectItem.setText("" + data.getItemTitle());
                    mBinding.tvMoney.setText("¥" + allFree);

                }
            }
        });

        mViewModel.competitionItemLiveData.observe(this, new Observer<ResponseBean<List<CompetitionItemBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<CompetitionItemBean>> responseBean) {
                mBinding.rvCompetitionProject.setVisibility(View.VISIBLE);
                if (responseBean.getData() != null && responseBean.getData().size() > 0) {
                    List<CompetitionItemBean> data = responseBean.getData();

                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setSelect(false);
                    }
                    MyTagRegisterAdapter myTagRegisterAdapter = new MyTagRegisterAdapter(SelectionsOfEventActivity.this, data);
                    mBinding.rvCompetitionProject.setAdapter(myTagRegisterAdapter);
                    mBinding.rvCompetitionProject.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {
                            for (int i = 0; i < data.size(); i++) {
                                data.get(i).setSelect(false);
                            }
                            projectItem = data.get(position).getProjectItem();
                            data.get(position).setSelect(true);
                            myTagRegisterAdapter.notifyDatas(data);

                            projectItemId = "" + data.get(position).getId();
                            projectId = "" + data.get(position).getProjectId();
                            return false;
                        }
                    });

                }
            }
        });

        mViewModel.registrantsMacthLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("100000-100030")) {
//                    toast("您已经报名了");
                    SpannableString spannableString = new SpannableString("您已经报名本次比赛，请到 我的-" +
                            "我的报名 查看报名详情");
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff4795ed")), 12,
                            spannableString.length() - 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    new MessageDialogTitleSpannerBuilder(SelectionsOfEventActivity.this)
                            .setMessage(spannableString)
                            .setTvCancel("取消")
                            .setBtnCancleHint(true)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .setTvSure("好的")
                            .show();

                } else {
                    if (responseBean.getErrorCode().equals("200")) {
                        SpannableString spannableString = new SpannableString("可在 我的-我的报名 查看或提交保存的报名，请在" +
                                "比赛报名截止前支付提交报名");
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff4795ed")), 2,
                                spannableString.length() - 26, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        new MessageDialogHaveTitleSpannerBuilder(SelectionsOfEventActivity.this)
                                .setMessage(spannableString)
                                .setTvTitle("保存成功")
                                .setCancelAble(false)
                                .setTvCancel("取消")
                                .setBtnCancleHint(true)
                                .setSureListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                    }
                                })
                                .setTvSure("确定")
                                .show();
                    } else {
                        toast("" + responseBean.getMessage());
                    }
                }

            }

        });
        mViewModel.zfbPayMacthLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    PayFactory payFactory = new PayFactory();
                    payFactory.AliPay((String) responseBean.getData(), SelectionsOfEventActivity.this, new PayFactory.PayListener() {
                        public void onPayListener(String s, boolean isOk) {
                            if (isOk) {
                                new MessageDialogBuilder(SelectionsOfEventActivity.this)
                                        .setMessage("您已成功支付提交报名申请，预祝您在本次比赛" +
                                                "取得一个好成绩")
                                        .setTvTitle("支付成功")
                                        .setCancelAble(false)
                                        .setTvCancel("取消")
                                        .setBtnCancleHint(false)
                                        .setTvSure("确定")
                                        .setSureListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                finish();
                                            }
                                        })
                                        .show();
                            } else {
                                finish();
                            }
                        }
                    });

                } else {
                    toast("" + responseBean.getMessage());
                }
            }

        });
        mViewModel.wxPayMacthLiveData.observe(this, new Observer<ResponseBean<WXPayBean>>() {
            @Override
            public void onChanged(ResponseBean<WXPayBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    PayFactory payFactory = new PayFactory();
                    payFactory.WxPay(SelectionsOfEventActivity.this, responseBean.getData());
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

        mViewModel.patEnrollMatchZFBLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    PayFactory payFactory = new PayFactory();
                    payFactory.AliPay((String) responseBean.getData(), SelectionsOfEventActivity.this, new PayFactory.PayListener() {
                        public void onPayListener(String s, boolean isOk) {
                            if (isOk) {
                                new MessageDialogBuilder(SelectionsOfEventActivity.this)
                                        .setMessage("您已成功支付提交报名申请，预祝您在本次比赛" +
                                                "取得一个好成绩")
                                        .setTvTitle("支付成功")
                                        .setCancelAble(false)
                                        .setTvCancel("取消")
                                        .setBtnCancleHint(false)
                                        .setTvSure("确定")
                                        .setSureListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                finish();
                                            }
                                        })
                                        .show();
                            } else {
                                finish();
                            }
                        }
                    });
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.wxPayMacthLiveData.observe(this, new Observer<ResponseBean<WXPayBean>>() {
            @Override
            public void onChanged(ResponseBean<WXPayBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    PayFactory payFactory = new PayFactory();
                    payFactory.WxPay(SelectionsOfEventActivity.this, responseBean.getData());
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.patEnrollMatchwxLiveData.observe(this, new Observer<ResponseBean<WXPayBean>>() {
            @Override
            public void onChanged(ResponseBean<WXPayBean> wxPayBeanResponseBean) {
                if (wxPayBeanResponseBean.getErrorCode().equals("200")) {
                    PayFactory payFactory = new PayFactory();
                    payFactory.WxPay(SelectionsOfEventActivity.this, wxPayBeanResponseBean.getData());
                } else {
                    toast("" + wxPayBeanResponseBean.getMessage());
                }
            }
        });
        mViewModel.getSaveOrderIdLiveData.observe(this, new Observer<OrderIdBean>() {
            @Override
            public void onChanged(OrderIdBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200000-100001") || responseBean.getErrorCode().equals("200000-100002")) {
                    showOut("token已过期，请重新登录");
                } else {
                    Log.e(TAG, "onChanged: oldId===" + responseBean.getData());
                    BigDecimal bigDecimal = new BigDecimal(String.valueOf(responseBean.getData()));
                    long ids = responseBean.getData();
                    Log.e(TAG, "onChanged: NewId====" + ids);
                    Log.e(TAG, "onChanged: myId====" + String.valueOf(responseBean.getData()));
                    if (clickType == 1) {
                        if (replaceChargeEat == 1) {

                            mViewModel.registrantsMatch("" + gameMatchBean.getId(), "" + projectItemId, "" + projectItem, "" + projectId,
                                    "" + clickType, "", "" + roomNeedType, "" + ids);
                        } else {
                            if (roomNeedType == -1) {
                                mViewModel.registrantsMatch("" + gameMatchBean.getId(), "" + projectItemId, "" + projectItem, "" + projectId,
                                        "" + clickType, "", "" + 0, "" + ids);
                            }
                        }
                    }
                }
            }
        });

        mViewModel.getOrderIdLiveData.observe(this, new Observer<OrderIdBean>() {
            @Override
            public void onChanged(OrderIdBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200000-100001") || responseBean.getErrorCode().equals("200000-100002")) {
                    showOut("token已过期，请重新登录");
                } else {
                    Log.e(TAG, "onChanged: oldId===" + responseBean.getData());
                    BigDecimal bigDecimal = new BigDecimal(String.valueOf(responseBean.getData()));
                    commitOrderId = responseBean.getData();
//                    Log.e(TAG, "onChanged: NewId====" + ids);
                    Log.e(TAG, "onChanged: myId====" + String.valueOf(responseBean.getData()));
                }


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_room_money_need:
            case R.id.cb_room_money_need:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.cbRoomMoneyNeed.setChecked(true);
                mBinding.cbRoomMoneyNoneed.setChecked(false);
                roomNeedType = 1;
                if (gameMatchBean != null) {
                    String eatAllFree = gameMatchBean.getEatAllFree();
                    int ownFree = gameMatchBean.getOwnFree();
                    allMoney = (Integer.parseInt(eatAllFree) + ownFree);
                    mBinding.tvMoney.setText("¥" + (Integer.parseInt(eatAllFree) + ownFree));
                }

                break;
            case R.id.ll_room_money_noneed:
            case R.id.cb_room_money_noneed:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;

                mBinding.cbRoomMoneyNeed.setChecked(false);
                mBinding.cbRoomMoneyNoneed.setChecked(true);
                roomNeedType = 0;
                if (gameMatchBean != null) {
                    int ownFree2 = gameMatchBean.getOwnFree();
                    allMoney = ownFree2;
                    mBinding.tvMoney.setText("¥" + ownFree2);
                }

                break;

            case R.id.cb_pay_zfb://支付宝
            case R.id.ll_cb_pay_zfb:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.cbPayZfb.setChecked(true);
                mBinding.cbPayWx.setChecked(false);
                freeType = 2;
                break;
            case R.id.cb_pay_wx://微信
            case R.id.ll_cb_pay_wx:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.cbPayZfb.setChecked(false);
                mBinding.cbPayWx.setChecked(true);
                freeType = 1;
                break;

            case R.id.tv_save_sign_up://保存报名
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                clickType = 1;
                if (gameMatchBean != null) {
                    if (TextUtils.isEmpty(projectItemId)) {
                        toast("请选择比赛项目");
                        return;
                    }
                    new MessageDialogBuilder(SelectionsOfEventActivity.this)
                            .setMessage("支付提交前可随时修改报名")
                            .setTvTitle("是否保存报名表？")
                            .setCancelAble(false)
                            .setTvCancel("取消")
                            .setBtnCancleHint(false)
                            .setTvSure("确定")
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    commitSaveOrder = 1;
                                    showLoading();
                                    mViewModel.getSaveOrderId();
                                }
                            })
                            .show();


                }

                break;
            case R.id.tv_pay_and_submit://支付提交
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (gameMatchBean != null) {
                    /*弹窗*/
                    new MessageDialogBuilder(SelectionsOfEventActivity.this)
                            .setMessage("报名费用一旦提交，报名表无法修改，如有人员" +
                                    "调整，请在比赛开始当天之前在“我的-我的报名”" +
                                    "选择“我要退赛”并重新报名，报名费会在24小" +
                                    "时内退还给您")
                            .setTvTitle("是否确认支付提交报名？")
                            .setCancelAble(false)
                            .setTvCancel("取消")
                            .setBtnCancleHint(false)
                            .setTvSure("确定")
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    commitSaveOrder = 2;
                                    showLoading();

                                    if (replaceCharge == 0 && replaceChargeEat == 0) {//不需要支付东西
                                        clickType = 1;
                                        if (commitOrderId == 0) {
                                            mViewModel.getOrderId();
                                            toast("请稍后重试");
                                            return;
                                        }
                                        //判断是否需要报名费 和吃猪肺
                                        mViewModel.registrantsMatch("" + gameMatchBean.getId(), "" + projectItemId, "" + projectItem, "" + projectId,
                                                "" + clickType, "", "", "" + commitOrderId);
                                    } else {
                                        clickType = 2;
                                        /*判断需要报名费*/
                                        //判断是否需要报名费 和食宿费
                                        if (replaceChargeEat == 1) {//需要支付
                                            if (commitOrderId == 0) {
                                                mViewModel.getOrderId();
                                                toast("请稍后重试");
                                                return;
                                            }
                                            /*如果需要食宿费   报名费 需要手选  所以直接传freeType就行*/
                                            mViewModel.registrantsMatch("" + gameMatchBean.getId(), "" + projectItemId, "" + projectItem, "" + projectId,
                                                    "" + clickType, "" + freeType, "" + roomNeedType, "" + commitOrderId);
                                        } else {
                                            /*不需要食宿费  需要报名费*/
                                            if (roomNeedType == -1) {
                                                if (commitOrderId == 0) {
                                                    mViewModel.getOrderId();
                                                    toast("请稍后重试");
                                                    return;
                                                }
                                                mViewModel.registrantsMatch("" + gameMatchBean.getId(), "" + projectItemId, "" + projectItem, "" + projectId,
                                                        "" + clickType, "" + freeType, "" + roomNeedType, "" + commitOrderId);
                                            }
                                        }
                                    }
                                }
                            })
                            .show();

                }
                break;

            case R.id.tv_save_cancel://取消报名
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mViewModel.deleteXlEnrollMatch("" + orderId);
                break;
            case R.id.tv_pay://支付再去提交
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (freeType == 0) {
                    toast("请选择支付方式");
                    return;
                }
                new MessageDialogBuilder(SelectionsOfEventActivity.this)
                        .setMessage("报名费用一旦提交，报名表无法修改，如有人员" +
                                "调整，请在比赛开始当天之前在“我的-我的报名”" +
                                "选择“我要退赛”并重新报名，报名费会在24小" +
                                "时内退还给您")
                        .setTvTitle("是否确认支付提交报名？")
                        .setCancelAble(false)
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                commitSaveOrder = 2;
                                mViewModel.patEnrollMatch(orderId, "" + freeType);
                            }
                        })
                        .show();

                break;

        }
    }

    @Override
    public void wxCallBack(int code) {
        switch (code) {
            case 0:
                new MessageDialogBuilder(SelectionsOfEventActivity.this)
                        .setMessage("您已成功支付提交报名申请，预祝您在本次比赛" +
                                "取得一个好成绩")
                        .setTvTitle("支付成功")
                        .setCancelAble(false)
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        })
                        .show();
                break;
            case -1:
                toast("支付异常");

                break;
            case -2:
                toast("用户取消");

                break;
        }
    }
}