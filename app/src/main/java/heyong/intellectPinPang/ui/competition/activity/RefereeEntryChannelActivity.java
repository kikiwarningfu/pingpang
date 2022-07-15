package heyong.intellectPinPang.ui.competition.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.RefereeApplyMatchBean;
import heyong.intellectPinPang.databinding.ActivityRefereeEntryChannelBinding;
import heyong.intellectPinPang.ui.competition.CompetitionViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogTitleSpannerBuilder;

/**
 * 裁判员  报名通道
 */
public class RefereeEntryChannelActivity extends BaseActivity<ActivityRefereeEntryChannelBinding, CompetitionViewModel> implements View.OnClickListener {
    public static final String MATCHID = "match_id";
    private String matchId = "";

    public static void goActivity(String matchId, Context context) {
        Intent intent = new Intent(context, RefereeEntryChannelActivity.class);
        intent.putExtra(MATCHID, matchId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_referee_entry_channel;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        matchId = getIntent().getStringExtra(MATCHID);
        mViewModel.refereeApplyMatch(matchId);
        mViewModel.refereeApplyMatchLiveData.observe(this, new Observer<ResponseBean<RefereeApplyMatchBean>>() {
            @Override
            public void onChanged(ResponseBean<RefereeApplyMatchBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    RefereeApplyMatchBean data = responseBean.getData();
                    mBinding.tvMatchTitle.setText("" + data.getMatchTitle());
                    mBinding.tvName.setText("" + data.getName());
                    mBinding.tvSex.setText(""+data.getSex());
                    mBinding.tvCaipanLevel.setText("" + data.getRoleName());
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

        mViewModel.refereeApplyToMatchLiveData.observe(this, new Observer<ResponseBean<RefereeApplyMatchBean>>() {
            @Override
            public void onChanged(ResponseBean<RefereeApplyMatchBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    SpannableString spannableString = new SpannableString("您已成功向主办方提交比赛裁判员报名申请\n请注意关注  我的-消息  界面查看审核结果");
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff4795ed")), 21, spannableString.length() - 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    new MessageDialogTitleSpannerBuilder(RefereeEntryChannelActivity.this)
                            .setMessage(spannableString)
                            .setTvCancel("取消")
                            .setBtnCancleHint(true)
                            .setTvSure("确定")
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            })
                            .show();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mViewModel.refereeApplyToMatch(matchId);
                break;
        }
    }
}