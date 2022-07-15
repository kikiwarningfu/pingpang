package heyong.intellectPinPang.ui.competition.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

//import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;
import com.xq.fasterdialog.dialog.CustomDialog;

import java.util.HashMap;
import java.util.Map;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.CheckUserClunRoleBean;
import heyong.intellectPinPang.bean.competition.GameMatchBean;
import heyong.intellectPinPang.bean.competition.RefereeApplyMatchBean;
import heyong.intellectPinPang.databinding.ActivityEventRegisterBinding;
import heyong.intellectPinPang.ui.competition.CompetitionViewModel;
import heyong.intellectPinPang.ui.mine.activity.RealNameImageActivity;
import heyong.intellectPinPang.ui.mine.activity.RefereeDetailAcitivty;
import heyong.intellectPinPang.ui.mine.activity.WebViewActivity;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.AppManager;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.utils.MessageDialogTitleBuilder;
import heyong.intellectPinPang.utils.MessageDialogTitleSpannerBuilder;

/**
 * 赛事报名界面
 */
public class EventRegisterActivity extends BaseActivity<ActivityEventRegisterBinding, CompetitionViewModel> implements View.OnClickListener {
    GameMatchBean gameMatchBean;
    private int clickType = 0;//1 红的 2  运动员向俱乐部 3  俱乐部管理人员报名通道  4裁判员

    @Override
    public int getLayoutRes() {
        return R.layout.activity_event_register;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        try {
            AppManager.getAppManager().addActivity(this);
        } catch (Exception e) {

        }
        gameMatchBean = (GameMatchBean) getIntent().getSerializableExtra("data");
        String ownRegistration = gameMatchBean.getOwnRegistration();
        if (Integer.parseInt(ownRegistration) == 1) {
            //允许个人报名
            mBinding.rlIndividualEntry.setVisibility(View.VISIBLE);
        } else {
            mBinding.rlIndividualEntry.setVisibility(View.GONE);
        }
        mBinding.tvMatchTitle.setText("" + gameMatchBean.getMatchTitle());
        ImageLoader.loadImage(mBinding.ivSaishiBg, gameMatchBean.getMatchImgUrl());
        String replaceCharge = gameMatchBean.getReplaceCharge();
        int iReplaceCharge = Integer.parseInt(replaceCharge);


        if (iReplaceCharge == 0) {
            //不收报名费
            mBinding.tvSaishiFree.setText("报名费：免费");
        } else {
            //收报名费
            int ownFree = gameMatchBean.getOwnFree();
            if (ownFree == 0) {
                mBinding.tvSaishiFree.setText("报名费：由主办方收取");
            } else {
                mBinding.tvSaishiFree.setText("报名费：¥" + "" + ownFree + "/人");
            }
        }
        mViewModel.joinTwoClubLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    String matchTitle = gameMatchBean.getMatchTitle();
                    String id = gameMatchBean.getId();
                    Intent intent = new Intent();
                    intent.setClass(EventRegisterActivity.this, SportsPersonalChannelActivity.class);
                    intent.putExtra(SportsPersonalChannelActivity.MATCH_TITLE, "" + matchTitle);
                    intent.putExtra(SportsPersonalChannelActivity.MATCH_ID, "" + id);
                    startActivity(intent);
                } else {
                    toast("请先加入俱乐部");
                }
            }
        });
        /*判断是否有加入俱乐部*/
        mViewModel.joinClubLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();

                if (responseBean.getErrorCode().equals("200")) {

                    //判断是否向裁判员报名
                    String errorCode = responseBean.getErrorCode();
                    if (errorCode.equals("100000-100027")) {
                        //请先加入俱乐部
                        new MessageDialogTitleBuilder(EventRegisterActivity.this)
                                .setMessage("由于本次比赛规定要由俱乐部报名，您还\n" +
                                        "未加入任何俱乐部，请先加入俱乐部，再\n" +
                                        "进行个人报名。\n" +
                                        "                            ")
                                .setTvCancel("取消")
                                .setBtnCancleHint(true)
                                .setTvSure("去加入俱乐部")
                                .setBtnCancleHint(false)
                                .setSureListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .show();
                    } else {
                        /*调用接口*/
                        mViewModel.refereeMacth();
                    }
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

        mViewModel.refereeMacthLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {

                    String errorCode = responseBean.getErrorCode();
                    if (errorCode.equals("100000-100028")) {
                        new MessageDialogTitleBuilder(EventRegisterActivity.this)
                                .setMessage("您已经向本次比赛主办方提出裁判员报\n" +
                                        "名申请，无法再进行参赛报名\n" +
                                        "                            ")
                                .setTvCancel("取消")
                                .setBtnCancleHint(true)
                                .setTvSure("确定")
                                .setBtnCancleHint(false)
                                .setSureListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .show();
                    } else {
                        Intent intent = new Intent(EventRegisterActivity.this, SelectionsOfEventActivity.class);
                        intent.putExtra("data", gameMatchBean);
                        intent.putExtra("danda", "2");
                        startActivity(intent);
                    }

                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

        mViewModel.refereeApplyMatchLiveData.observe(this, new Observer<ResponseBean<RefereeApplyMatchBean>>() {
            @Override
            public void onChanged(ResponseBean<RefereeApplyMatchBean> responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("100000-100029")) {//还不是裁判员
                    SpannableString spannableString = new SpannableString("您还不是裁判员，请在  我的-裁判员 界面认证裁判员身份，系统审核成功后可报名成为本次比赛的裁判员");
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff4795ed")), 10,
                            spannableString.length() - 31, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    new MessageDialogTitleSpannerBuilder(EventRegisterActivity.this)
                            .setMessage(spannableString)
                            .setTvCancel("取消")
                            .setBtnCancleHint(true)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goActivity(RefereeDetailAcitivty.class);
                                }
                            })
                            .setTvSure("去认证")
                            .show();
                } else if (responseBean.getErrorCode().equals("100000-100030")) {//已经报名过
                    new MessageDialogBuilder(EventRegisterActivity.this)
                            .setMessage("")
                            .setTvTitle("您已经向本次比赛主办方提出参赛报\n" +
                                    "名申请，无法再进行裁判员报名\n" +
                                    "                            ")
                            .setTvCancel("取消")
                            .setBtnCancleHint(true)
                            .setTvSure("确定")
                            .show();
                } else if (responseBean.getErrorCode().equals("200")) {
                    RefereeEntryChannelActivity.goActivity(gameMatchBean.getId(), EventRegisterActivity.this);
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

        mViewModel.checkUserClunRoleLiveData.observe(this, new Observer<ResponseBean<CheckUserClunRoleBean>>() {
            @Override
            public void onChanged(ResponseBean<CheckUserClunRoleBean> responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("100000-100011")) {
                    //请先进行实名认证
                    new MessageDialogBuilder(EventRegisterActivity.this)
                            .setMessage("")
                            .setTvTitle("本次比赛主办方要求进行实名\n" +
                                    "认证，您还没有进行实名认证，请先\n" +
                                    "去实名认证")
                            .setTvCancel("取消")
                            .setTvSure("去认证")
                            .setBtnCancleHint(true)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goActivity(RealNameImageActivity.class);
                                }
                            })
                            .show();

                } else if (responseBean.getErrorCode().equals("100000-100032"))
                //你还不是除自由团类类型外的任何俱乐部的负责人和教练员
                {
                    new MessageDialogBuilder(EventRegisterActivity.this)
                            .setMessage("")
                            .setTvTitle("俱乐部/团体报名通道需要报名人为俱\n" +
                                    "乐部的负责人或教练员，请先在首页\n" +
                                    "创建俱乐部或加入俱乐部成为教练员\n" +
                                    "                            ")
                            .setTvCancel("取消")
                            .setBtnCancleHint(true)
                            .setTvSure("去创建/加入俱乐部")
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //跳转到首页
//                                    RxBus.getDefault().post(new JumpSelectTionEvent(0));

                                    AppManager.getAppManager().removeActivity(EventRegisterActivity.this);
                                    AppManager.getAppManager().finishActivity(EventSignUpActivity.class);
                                    finish();

                                }
                            })
                            .show();
                } else {
                    Intent intent = new Intent();
                    intent.setClass(EventRegisterActivity.this, ClubTeamRegisterChannelActivity.class);
                    intent.putExtra(ClubTeamRegisterChannelActivity.MATCH_ID, "" + gameMatchBean.getId());
                    intent.putExtra(ClubTeamRegisterChannelActivity.MATCH_TITLE, "" + gameMatchBean.getMatchTitle());
                    startActivity(intent);
                }
            }
        });
        mViewModel.judgeUserJoinMatchLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    switch (clickType) {
                        case 1:
                            //先判断是否 完善信息   然后判断是否实名认证  再去判断 是否有俱乐部
//                            showLoading();
//                            mViewModel.joinClub();
                            /*调用接口*/
                            mViewModel.refereeMacth();
                            break;
                        case 2:
                            showLoading();
                            mViewModel.joinTwoClub();
//                            String matchTitle = gameMatchBean.getMatchTitle();
//                            String id = gameMatchBean.getId();
//                            Intent intent = new Intent();
//                            intent.setClass(EventRegisterActivity.this, SportsPersonalChannelActivity.class);
//                            intent.putExtra(SportsPersonalChannelActivity.MATCH_TITLE, "" + matchTitle);
//                            intent.putExtra(SportsPersonalChannelActivity.MATCH_ID, "" + id);
//                            startActivity(intent);
                            break;
                        case 3:
                            showLoading();
                            mViewModel.checkUserClunRole(gameMatchBean.getId());

                            break;
                        case 4:

                            showLoading();
                            mViewModel.refereeApplyMatch(gameMatchBean.getId());

                            break;
                    }


                } else if (responseBean.getMessage().equals("您已经报名了")) {
                    if (clickType == 4)//裁判员第四通道
                    {
                        SpannableString spannableString = new SpannableString("您已经报名本次比赛，请到 我的-" +
                                "我的报名 查看报名详情");
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff4795ed")), 12,
                                spannableString.length() - 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        new MessageDialogTitleSpannerBuilder(EventRegisterActivity.this)
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
                        SpannableString spannableString = new SpannableString("您已经报过名了，请勿重复提交，若想修改报名信息，请联系教练员");
//                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff4795ed")), 12,
//                                spannableString.length() - 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        new MessageDialogTitleSpannerBuilder(EventRegisterActivity.this)
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
            case R.id.rl_individual_entry://仅代表个人报名参赛   第一个通道
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                clickType = 1;
                showLoading();
                Map<String, Object> gamegrade = new HashMap<String, Object>();
                gamegrade.put("eventRegisterToPersonal", "第一通道(仅代表个人报名)");//自定义参数：音乐类型，值：流行
                MobclickAgent.onEventObject(EventRegisterActivity.this, "第一通道(仅代表个人报名)", gamegrade);
//                TCAgent.onEvent(EventRegisterActivity.this, "eventRegisterToPersonal");
                mViewModel.judgeUserJoinMatch(gameMatchBean.getId());


                break;
//            case R.id.rl_personal_to_club://个人代表俱乐部报名参赛   第二个通道
//                if (AntiShakeUtils.isInvalidClick(v))
//                    return;
//                clickType = 2;
//                showLoading();
//                TCAgent.onEvent(EventRegisterActivity.this, "eventRegisterToClub");
//                mViewModel.judgeUserJoinMatch(gameMatchBean.getId());
//
//
//                break;
            case R.id.tv_sign_up_xuzhi://报名须知
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Intent intentWeb = new Intent(this, WebViewActivity.class);
                intentWeb.putExtra(WebViewActivity.TITLE, "报名须知");
                intentWeb.putExtra(WebViewActivity.URLS, "http://47.93.151.11:9590/index.html");
                startActivity(intentWeb);
                break;
            case R.id.rl_club_manager_channel://  俱乐部（管理人员）报名通道   第三通道
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showLoading();
                Map<String, Object> gamethree = new HashMap<String, Object>();
                gamethree.put("eventRegisterManager", " 第三通道");//自定义参数：音乐类型，值：流行
                MobclickAgent.onEventObject(EventRegisterActivity.this, "第三通道", gamethree);
//                TCAgent.onEvent(EventRegisterActivity.this, "eventRegisterManager");
                mViewModel.checkUserClunRole(gameMatchBean.getId());
                break;
            case R.id.rl_referee_registration_channel://裁判员报名通道（免报名费）  第四通道
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                clickType = 4;
                showLoading();
                Map<String, Object> gameFour = new HashMap<String, Object>();
                gameFour.put("eventRegister_to_referee", " 裁判员报名");//自定义参数：音乐类型，值：流行
                MobclickAgent.onEventObject(EventRegisterActivity.this, "裁判员报名", gameFour);
//                TCAgent.onEvent(EventRegisterActivity.this, "eventRegisterManager");

                mViewModel.judgeUserJoinMatch(gameMatchBean.getId());


                break;
            case R.id.ll_notes_registration://报名须知
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                registerRuleDialog();

                break;

        }
    }


    private void registerRuleDialog() {
        CustomDialog customDialogConfirm = new CustomDialog(EventRegisterActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_register_rule);
        customDialogConfirm.show();
        View customView = customDialogConfirm.getCustomView();
        showAskConfirmView(customView, customDialogConfirm);
    }

    private void showAskConfirmView(View customView, CustomDialog customDialog) {

        ImageView ivClose = customView.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialog != null) {
                    customDialog.dismiss();
                }

            }
        });

    }
}