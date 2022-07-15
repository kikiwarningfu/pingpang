package heyong.intellectPinPang.ui.club.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.FillInMatchListBean;
import heyong.intellectPinPang.bean.competition.MatchBaseInfoBean;
import heyong.intellectPinPang.databinding.ActivityCoachDrawLotsHostAndGusetBinding;
import heyong.intellectPinPang.ui.MipushTestActivity;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;

/**
 * 教练的 主客队抽签
 */
public class CoachDrawLotsHostAndGusetActivity extends BaseActivity<ActivityCoachDrawLotsHostAndGusetBinding, ClubViewModel> implements View.OnClickListener {
    private String ids = "";

    FillInMatchListBean data;
    MatchBaseInfoBean matchBaseInfoBean;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_coach_draw_lots_host_and_guset;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        ids = getIntent().getStringExtra("ids");
        mBinding.setListener(this);
        mViewModel.fillInMatchList(ids);

        mViewModel.fillInMatchListLiveData.observe(this, new Observer<ResponseBean<FillInMatchListBean>>() {
            @Override
            public void onChanged(ResponseBean<FillInMatchListBean> fillInMatchListBeanResponseBean) {
                if (fillInMatchListBeanResponseBean.getErrorCode().equals("200")) {
                    data = fillInMatchListBeanResponseBean.getData();
                    mBinding.tvTitleName.setText(""+data.getTitle());
                    matchBaseInfoBean = new MatchBaseInfoBean("" + data.getArrangeId(), "" + data.getMatchId(), "" + data.getTitle(), "" + data.getAngle(),
                            "" + data.getLeftClubId(), "" + data.getLeftTeamId(), "" + data.getLeftClubType(),
                            "" + data.getLeftClubName(), "" + data.getLeftClubImg(),
                            "" + data.getRightClubId(), "" + data.getRightTeamId(), "" + data.getRightClubType(),
                            "" + data.getRightClubName(), "" + data.getRightClubImg(),
                            "" + data.getLeftOrRight(), "" + data.getAppOperationLogId());

                    int angle = Integer.parseInt(data.getAngle());
                    ImageLoader.loadImage(mBinding.ivLeftClubLogo, data.getLeftClubImg(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(mBinding.ivRightClubLogo, data.getRightClubImg(), R.drawable.img_default_avatar,
                            R.drawable.img_default_avatar);
                    mBinding.tvLeftClubName.setText("" + data.getLeftClubName());
                    mBinding.tvRightClubName.setText("" + data.getRightClubName());
                    int i = Integer.parseInt(data.getLeftOrRight());
                    if (i == 1) {
                        /*左边的是自己*/
                        mBinding.tvHostName.setText("" + data.getLeftClubName());
                    } else {
                        mBinding.tvHostName.setText("" + data.getRightClubName());

                    }
                    ObjectrotationAnim(angle + 360 * 8 + 180, data);

                } else {
                    toast("" + fillInMatchListBeanResponseBean.getMessage());
                }
            }
        });

    }

    //旋转动画
    //实现先顺时针360度旋转然后逆时针360度旋转动画功能
    private void ObjectrotationAnim(int angle, FillInMatchListBean data) {
        Float aFloat = Float.valueOf(String.valueOf(angle));
        //构造ObjectAnimator对象的方法
        ObjectAnimator animator = ObjectAnimator.ofFloat(mBinding.imgZhizhen, "rotation",
                0.0F, aFloat
        );//设置先顺时针360度旋转然后逆时针360度旋转动画
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(1000);//设置旋转时间
        animator.start();//开始执行动画（顺时针旋转动画）
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                int i = Integer.parseInt(data.getLeftOrRight());
                mBinding.tvHost.setVisibility(View.VISIBLE);

                if (i == 1) {
//                    /*左边的是自己*/
                    int leftClubType = Integer.parseInt(data.getLeftClubType());
                    mBinding.tvHostName.setText("" + data.getLeftClubName());

                    if (leftClubType == 1) {
                        /*1是主  2 试课*/
                        mBinding.tvHost.setText("主");
                        mBinding.tvGustTishi.setText("您的队伍为主队，请填写对阵名单");
                    } else {
                        mBinding.tvHost.setText("客");
                        mBinding.tvGustTishi.setText("您的队伍为客队，请填写对阵名单");
                    }
                } else {
                    mBinding.tvHostName.setText("" + data.getRightClubName());

                    int rightClubType = Integer.parseInt(data.getRightClubType());
                    if (rightClubType == 1) {
                        mBinding.tvHost.setText("主");
                        mBinding.tvGustTishi.setText("您的队伍为主队，请填写对阵名单");
                    } else {
                        mBinding.tvHost.setText("客");
                        mBinding.tvGustTishi.setText("您的队伍为客队，请填写对阵名单");

                    }
//
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_fill_match_info:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (MipushTestActivity.instance != null) {
                    MipushTestActivity.instance.finish();//调用
                }
                if (data != null) {
                    CoachFillMatchFormActivity.goActivity(CoachDrawLotsHostAndGusetActivity.this, matchBaseInfoBean);
                    finish();
                } else {
                    toast("已经有裁判员在操作");
                }

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (MipushTestActivity.instance != null) {
            MipushTestActivity.instance.finish();//调用
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (MipushTestActivity.instance != null) {
            MipushTestActivity.instance.finish();//调用
        }


    }

}