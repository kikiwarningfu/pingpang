package heyong.intellectPinPang.ui.competition.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.TeamRegisterSubmitAdapter;
import heyong.intellectPinPang.bean.competition.MatchOrderInfoBean;
import heyong.intellectPinPang.bean.competition.QueryMatchApplyInfoBean;
import heyong.intellectPinPang.databinding.ActivityEventDetailToPayBinding;
import heyong.intellectPinPang.factory.PayFactory;
import heyong.intellectPinPang.bean.pay.WXPayBean;
import heyong.intellectPinPang.ui.mine.MineViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.utils.MessageDialogHaveTitleSpannerBuilder;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;
import heyong.intellectPinPang.wxapi.WXPayEntryActivity;

/**
 * 报名详情  去支付
 */
public class EventDetailToPayActivity extends BaseActivity<ActivityEventDetailToPayBinding, MineViewModel> implements View.OnClickListener, WXPayEntryActivity.WXListener {
    private WindowManager wm;
    private int width;
    MyDividerItemDecoration mSixDiD;
    MyDividerItemDecoration mMySixDid;

    public static final String TAG = EventDetailToPayActivity.class.getSimpleName();
    private TeamRegisterSubmitAdapter teamRegisterSubmitAdapter;
    public static final String ORDER_ID = "baoming_id";
    public static final String MATCH_ID = "match_id";
    private String strMatchId = "";
    private String strMatchTitle = "";
    private String strOrderId = "";

    int replaceChargeEat;//是否需要食宿费 1 是需要  2 是不需要
    int replaceCharge;//是否代收报名费  1 是需要  2 是不需要
    int allMoney = 0;
    private int baomingNum = 0;
    QueryMatchApplyInfoBean queryMatchApplyInfoBean;
    private int baomingMoney = 0;
    private int commitSaveOrder = 0;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_event_detail_to_pay;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        wm = getWindowManager();
        // 重设高度
        width = wm.getDefaultDisplay().getWidth();
        strOrderId = getIntent().getStringExtra(ORDER_ID);
        strMatchId = getIntent().getStringExtra(MATCH_ID);
        WXPayEntryActivity.setWXListener(this);

        mViewModel.queryMatchApplyInfo(strMatchId);

        mViewModel.queryMatchApplyInfoLiveData.observe(this, new Observer<ResponseBean<QueryMatchApplyInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<QueryMatchApplyInfoBean> responseBean) {
                if (responseBean.getData() != null) {
                    queryMatchApplyInfoBean = responseBean.getData();
                    /*判断是否收费*/
                    if (queryMatchApplyInfoBean != null) {
                        replaceChargeEat = queryMatchApplyInfoBean.getReplaceChargeEat();//是否需要食宿费
                        if (TextUtils.isEmpty(queryMatchApplyInfoBean.getReplaceCharge())) {
                            replaceCharge = 0;//是否代收报名费  1 是需要 0 是不需要

                        } else {
                            replaceCharge = Integer.parseInt(queryMatchApplyInfoBean.getReplaceCharge());//是否代收报名费  1 是需要 0 是不需要

                        }
                        mViewModel.matchOrderInfo(strOrderId);
                        strMatchTitle = queryMatchApplyInfoBean.getMatchTitle();
                    } else {
                        toast("返回数据为null");
                    }
                }
            }
        });
        mSixDiD = new MyDividerItemDecoration(EventDetailToPayActivity.this, 10,
                getResources().getColor(R.color.color_f5));
        mMySixDid = new MyDividerItemDecoration(EventDetailToPayActivity.this, 10,
                getResources().getColor(R.color.white));
        mBinding.rvSubmitApply.removeItemDecoration(mSixDiD);

        mBinding.rvSubmitApply.addItemDecoration(mSixDiD);
        teamRegisterSubmitAdapter = new TeamRegisterSubmitAdapter(
                EventDetailToPayActivity.this,
                width, mMySixDid);
        mBinding.rvSubmitApply.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvSubmitApply.setAdapter(teamRegisterSubmitAdapter);
        List<MatchOrderInfoBean> teamRegisterSubmitBeanList = new ArrayList<>();
        /*订单详情*/
        mViewModel.matchOrderInfoLiveData.observe(this, new Observer<ResponseBean<MatchOrderInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<MatchOrderInfoBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    MatchOrderInfoBean data = responseBean.getData();
                    List<MatchOrderInfoBean.PlayersBean> players = data.getPlayers();
                    MatchOrderInfoBean.LeaderBean leader = data.getLeader();
                    List<MatchOrderInfoBean.RefereeBean> referee = data.getReferee();
                    if (TextUtils.isEmpty(data.getNeedEat())) {
                        replaceChargeEat = 0;//是否需要食宿费  needeat
                    } else {
                        replaceChargeEat = Integer.parseInt(data.getNeedEat());//是否需要食宿费  needeat
                    }
                    if (TextUtils.isEmpty(data.getNeedEnrolFree())) {
                        replaceCharge = 0;
                    } else {
                        replaceCharge = Integer.parseInt(data.getNeedEnrolFree());//是否代收报名费  1 是需要 0 是不需要

                    }
                    if (replaceCharge != 1 && replaceChargeEat != 1) {
                        /*应该不存在这种情况*/
                        mBinding.rlHavePayBaoming.setVisibility(View.GONE);
                        mBinding.rlNoHavePayBaoming.setVisibility(View.VISIBLE);
                        mBinding.rlNeedEat.setVisibility(View.GONE);
                        mBinding.rlNotNeedEat.setVisibility(View.VISIBLE);
                        allMoney = 0;
                        mBinding.tvMoney.setText("");
                        mBinding.llPayWay.setVisibility(View.GONE);
                    } else {
                        mBinding.llPayWay.setVisibility(View.VISIBLE);
                        mBinding.rlHavePayBaoming.setVisibility(View.GONE);
                        mBinding.rlNoHavePayBaoming.setVisibility(View.GONE);
                        mBinding.rlNeedEat.setVisibility(View.GONE);
                        mBinding.rlNotNeedEat.setVisibility(View.GONE);
                        /*需要食宿费*/
                        if (replaceChargeEat == 1) {
                            mBinding.rlNeedEat.setVisibility(View.VISIBLE);
                            mBinding.rlNotNeedEat.setVisibility(View.GONE);
                            mBinding.tvShisuMoney.setText("" + "¥" + queryMatchApplyInfoBean.getEatFree() + "/人/天;" + ""
                                    + "共" + queryMatchApplyInfoBean.getDaysCount() + "天");
                            mBinding.llShisuShow.setVerticalGravity(View.GONE);
                        } else {
                            mBinding.rlNotNeedEat.setVisibility(View.VISIBLE);
                            mBinding.rlNeedEat.setVisibility(View.GONE);
                        }
                        /*是否需要待收报名费*/
                        if (replaceCharge == 1) {
                            //还得判断有没有俱乐部交钱（有没有选中俱乐部 新建赛事的时候）   在判断这次支付 俱乐部有没有交钱
                            mBinding.rlHavePayBaoming.setVisibility(View.VISIBLE);
                            mBinding.tvBaomingMoney.setText("¥" + queryMatchApplyInfoBean.getTeamFree() + "/俱乐部;"
                                    + "¥" + queryMatchApplyInfoBean.getOwnFree() + "" + "/人");
                            mBinding.tvBaomingPeople.setText("共" + data.getPersonCount() + "人");
                            baomingMoney = queryMatchApplyInfoBean.getRegistrationCount() + queryMatchApplyInfoBean.getOwnFree() * baomingNum;
                            allMoney = baomingMoney;
                            mBinding.tvMoney.setText("¥" + data.getAllFree());
                        } else {
                            mBinding.rlNoHavePayBaoming.setVisibility(View.VISIBLE);
                            mBinding.rlHavePayBaoming.setVisibility(View.GONE);
                        }
                    }
                    allMoney = data.getEnrollFree();
                    mBinding.tvMoney.setText("¥" + allMoney);
                    mBinding.tvShisuMoney.setText("¥" + data.getEatFree() + "/人/天;" + "共" + data.getEatCount() + "天");


                    if (leader != null) {
                        MatchOrderInfoBean teamRegisterSubmitBean = new MatchOrderInfoBean(0);
                        teamRegisterSubmitBean.setLeader(leader);
                        teamRegisterSubmitBeanList.add(teamRegisterSubmitBean);
                    }
                    if (referee != null) {
                        MatchOrderInfoBean teamRegisterSubmitBean1 = new MatchOrderInfoBean(1);
                        teamRegisterSubmitBean1.setReferee(referee);
                        teamRegisterSubmitBean1.setMatchTitle("带队教练员");
                        teamRegisterSubmitBeanList.add(teamRegisterSubmitBean1);
                    }
                    MatchOrderInfoBean teamRegisterSubmitBean2 = null;
                    int cardType = 0;
                    if (players != null && players.size() > 0) {
                        for (int i = 0; i < players.size(); i++) {
                            String type = players.get(i).getType();
                            MatchOrderInfoBean.PlayersBean playersBean = players.get(i);
                            List<MatchOrderInfoBean.PlayersBean> playersBeans = new ArrayList<>();
                            switch (type) {
                                case "1"://团体
                                    cardType = 2;
                                    break;
                                case "2"://单打
                                    cardType = 3;
                                    break;
                                case "3"://双打
                                    cardType = 4;
                                    break;
                                case "4"://混双
                                    cardType = 5;
                                    break;
                                case "0":
                                    cardType = 7;
                                    break;
                            }
                            if (cardType == 3) {
                                List<MatchOrderInfoBean.RefereeBean> refereeBeanList = new ArrayList<>();
                                List<MatchOrderInfoBean.PlayersBean.TeamsBean> teams = players.get(0).getTeams();
                                List<MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean> player = teams.get(0).getPlayer();
                                MatchOrderInfoBean.RefereeBean refereeBean = new MatchOrderInfoBean.RefereeBean();
                                for (int m = 0; m < player.size(); m++) {
                                    refereeBean.setUserName("" + player.get(m).getUserName());
                                    refereeBean.setUserId(player.get(m).getUserId());
                                    refereeBean.setSex("" + player.get(m).getSex());
                                    refereeBeanList.add(refereeBean);
                                }
                                teamRegisterSubmitBean2 = new MatchOrderInfoBean(cardType);
                                teamRegisterSubmitBean2.setMatchTitle("" + players.get(i).getItemTitle());
                                teamRegisterSubmitBean2.setReferee(refereeBeanList);
                            } else {
                                teamRegisterSubmitBean2 = new MatchOrderInfoBean(cardType);
                                teamRegisterSubmitBean2.setMatchTitle("" + players.get(i).getItemTitle());
                                playersBeans.add(playersBean);
                                teamRegisterSubmitBean2.setPlayers(playersBeans);
                            }
                            teamRegisterSubmitBeanList.add(teamRegisterSubmitBean2);
                            Log.e(TAG, "onChanged: " + new Gson().toJson(teamRegisterSubmitBean2));
                        }
                    }
                    for (int i = 0; i < teamRegisterSubmitBeanList.size(); i++) {
                        int cardType1 = teamRegisterSubmitBeanList.get(i).getCardType();
                        if (cardType1 == 7) {
                            teamRegisterSubmitBeanList.remove(i);
                        }
                    }
                    //1 团体 2 单打  3 双打   4 混双（和业余双打一样）
                    teamRegisterSubmitAdapter.setNewData(teamRegisterSubmitBeanList);
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mBinding.setListener(this);
        mBinding.llSubmitApply.setVisibility(View.VISIBLE);
        mViewModel.patEnrollMatchZFBLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    if (commitSaveOrder == 1) {
                        //保存报名
                        SpannableString spannableString = new SpannableString("可在 我的-我的报名 查看或提交保存的报名，请在" +
                                "比赛报名截止前支付提交报名");
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff4795ed")), 2,
                                spannableString.length() - 26, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        new MessageDialogHaveTitleSpannerBuilder(EventDetailToPayActivity.this)
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
                        PayFactory payFactory = new PayFactory();
                        payFactory.AliPay((String) responseBean.getData(), EventDetailToPayActivity.this, new PayFactory.PayListener() {
                            public void onPayListener(String s, boolean isOk) {
                                if (isOk) {
                                    new MessageDialogBuilder(EventDetailToPayActivity.this)
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
                    }
                } else {
                    toast("" + responseBean.getMessage());
                }
            }

        });
        mViewModel.patEnrollMatchwxLiveData.observe(this, new Observer<ResponseBean<WXPayBean>>() {
            @Override
            public void onChanged(ResponseBean<WXPayBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    PayFactory payFactory = new PayFactory();
                    payFactory.WxPay(EventDetailToPayActivity.this, responseBean.getData());
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.deleteXlEnrollMatchLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    toast("请填写报名信息");
                    Intent intent = new Intent();
                    intent.setClass(EventDetailToPayActivity.this, ClubTeamRegisterChannelActivity.class);
                    intent.putExtra(ClubTeamRegisterChannelActivity.MATCH_ID, "" + strMatchId);
                    intent.putExtra(ClubTeamRegisterChannelActivity.MATCH_TITLE, "" + strMatchTitle);
                    startActivity(intent);
                    finish();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

    }


    private int clickType = -1;
    private int payWay = 0;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_change_sign_up://修改报名
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mViewModel.deleteXlEnrollMatch("" + strOrderId);

                break;
            case R.id.cb_pay_zfb://支付宝
            case R.id.ll_cb_pay_zfb:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.cbPayZfb.setChecked(true);
                mBinding.cbPayWx.setChecked(false);
                payWay = 2;
                break;
            case R.id.cb_pay_wx://微信
            case R.id.ll_cb_pay_wx:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.cbPayZfb.setChecked(false);
                mBinding.cbPayWx.setChecked(true);
                payWay = 1;
                break;
            case R.id.tv_save_sign_up://保存报名
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (replaceChargeEat == 1) {
                    if (clickType == -1) {
                        toast("请选择人数");
                        return;
                    }
                }
                new MessageDialogBuilder(EventDetailToPayActivity.this)
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
                                commitOrder();
                            }
                        })
                        .show();


                break;
            case R.id.tv_pay_commit://支付提交
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                commitSaveOrder = 2;

                if (replaceChargeEat == 1) {
                    if (clickType == -1) {
                        toast("请选择人数");
                        return;
                    }
                }
                if (payWay == 0) {
                    toast("请选择支付方式");
                    return;
                }

                new MessageDialogBuilder(EventDetailToPayActivity.this)
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
                                commitOrder();
                            }
                        })
                        .show();
                break;
        }
    }

    private void commitOrder() {
        mViewModel.patEnrollMatch("" + strOrderId, "" + payWay);
    }


    @Override
    public void wxCallBack(int code) {
        switch (code) {
            case 0:
                new MessageDialogBuilder(EventDetailToPayActivity.this)
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
                finish();
                break;
            case -2:
                toast("用户取消");
                finish();
                break;
        }
    }
}