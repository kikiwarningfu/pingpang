package heyong.intellectPinPang.ui.club.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.UpdateXlClubContestTeamBean;
import heyong.intellectPinPang.bean.competition.XlClubContestTeamBean;
import heyong.intellectPinPang.databinding.ActivityDrawLotsHostAndGuestBinding;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;

/**
 * 主客队抽签   队内比赛抽签
 */
public class DrawLotsHostAndGuestActivity extends BaseActivity<ActivityDrawLotsHostAndGuestBinding, ClubViewModel> implements View.OnClickListener {
    //teamType  1是主队    id  判断 那个和主队的id相同
    public static final String IDS = "clubContestInfoId";
    public static final String TAG = DrawLotsHostAndGuestActivity.class.getSimpleName();
    private long ids = 0;
    private String myIds = "";
    private String leftTeamId = "";
    private String rightTeamId = "";
    XlClubContestTeamBean.TeamsBean teamsleftBean;
    XlClubContestTeamBean.TeamsBean teamsrightBean;
    private String contestId = "";
    private int showEndType = 0;
    String contestTitle = "";

    private int zhuTime = -1;
    private int keTime = -1;
    public static DrawLotsHostAndGuestActivity instance = null;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_draw_lots_host_and_guest;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        instance = this;

        ids = getIntent().getLongExtra(IDS, 0);
        contestId = "" + ids;
        mViewModel.getXlClubContestTeam("" + ids);
        mViewModel.getXlClubContestTeamLiveData.observe(this, new Observer<ResponseBean<List<XlClubContestTeamBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<XlClubContestTeamBean>> listResponseBean) {
                if (listResponseBean.getErrorCode().equals("200")) {

                    List<XlClubContestTeamBean> data = listResponseBean.getData();
                    if (data != null && data.size() > 0) {
                        XlClubContestTeamBean xlClubContestTeamBean = data.get(0);
                        contestTitle = xlClubContestTeamBean.getContestTitle();
                        mBinding.tvTitleName.setText("" + contestTitle);
                        myIds = "" + xlClubContestTeamBean.getId();
                        List<XlClubContestTeamBean.TeamsBean> teams = xlClubContestTeamBean.getTeams();
                        if (teams.size() >= 2) {
                            teamsleftBean = teams.get(0);
                            teamsrightBean = teams.get(1);
                            leftTeamId = "" + teamsleftBean.getId();
                            rightTeamId = "" + teamsrightBean.getId();
                            mBinding.tvLeftClubName.setText("" + teamsleftBean.getTeamNum());
                            mBinding.tvRightName.setText("" + teamsrightBean.getTeamNum());
                        }
                    } else {
                        toast("" + listResponseBean.getMessage());
                    }

                } else {
                    toast("" + listResponseBean.getMessage());
                }

            }
        });

        mViewModel.updateXlClubContestTeamLiveData.observe(this, new Observer<ResponseBean<UpdateXlClubContestTeamBean>>() {
            @Override
            public void onChanged(ResponseBean<UpdateXlClubContestTeamBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    UpdateXlClubContestTeamBean data = responseBean.getData();
                    if (data != null) {
                        int angle = data.getAngle();
                        ObjectrotationAnim(angle + 360 * 8 + 180, data);
                    }
                } else {
                    toast("" + responseBean.getMessage());
                }

            }
        });


    }

    //旋转动画
    //实现先顺时针360度旋转然后逆时针360度旋转动画功能
    private void ObjectrotationAnim(int angle, UpdateXlClubContestTeamBean data) {
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
                long id = data.getId();
                if (String.valueOf(id).equals("" + leftTeamId)) {
                    mBinding.tvLeftBottomName.setText("" + teamsleftBean.getTeamNum());
                    mBinding.tvRightBottomName.setText("" + teamsrightBean.getTeamNum());
                } else {
                    mBinding.tvLeftBottomName.setText("" + teamsrightBean.getTeamNum());
                    mBinding.tvRightBottomName.setText("" + teamsleftBean.getTeamNum());
                }
                mBinding.tvStart.setEnabled(false);
                showEndType = 1;


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left_click://主俱乐部
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (showEndType == 1) {
                    String strHostName = mBinding.tvLeftBottomName.getText().toString();
                    Intent intent = new Intent(DrawLotsHostAndGuestActivity.this, FillMatchFormActivity.class);
                    intent.putExtra(FillMatchFormActivity.CONTESTID, contestId);
                    intent.putExtra(FillMatchFormActivity.TEAM_ID, "" + teamsleftBean.getId());
                    intent.putExtra(FillMatchFormActivity.TEAM_TITLE, contestTitle);
                    intent.putExtra(FillMatchFormActivity.HOST_NAME, strHostName);
                    intent.putExtra(FillMatchFormActivity.ORITION_TYPE, 0);
                    intent.putExtra(FillMatchFormActivity.TEAM_NAME, strHostName);
                    intent.putExtra(FillMatchFormActivity.TEAM_TYPE, "主队");
                    startActivityForResult(intent, 1);
                } else {
                    toast("请先抽签");
                }
                break;
            case R.id.ll_right_click://客俱乐部
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (showEndType == 1) {
                    String strHostName = mBinding.tvRightBottomName.getText().toString();
                    Intent intent = new Intent(DrawLotsHostAndGuestActivity.this, FillMatchFormActivity.class);
                    intent.putExtra(FillMatchFormActivity.CONTESTID, contestId);
                    intent.putExtra(FillMatchFormActivity.TEAM_ID, "" + teamsrightBean.getId());
                    intent.putExtra(FillMatchFormActivity.TEAM_TITLE, contestTitle);
                    intent.putExtra(FillMatchFormActivity.HOST_NAME, strHostName);
                    intent.putExtra(FillMatchFormActivity.ORITION_TYPE, 1);
                    intent.putExtra(FillMatchFormActivity.TEAM_NAME, strHostName);
                    intent.putExtra(FillMatchFormActivity.TEAM_TYPE, "客队");
                    startActivityForResult(intent, 2);
                } else {
                    toast("请先抽签");
                }
                break;
            case R.id.tv_start:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mViewModel.updateXlClubContestTeam("" + ids, "" + leftTeamId,
                        "" + rightTeamId);
                break;
            case R.id.tv_start_competition_start://开始比赛
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
//                判断 一下 如果 两个type都为1
                if (zhuTime == 1 && keTime == 1) {
                    DrawLotsHostAndGuestActivity.instance.finish();
                    Intent intent = new Intent(DrawLotsHostAndGuestActivity.this, TeamResultsActivity.class);
                    intent.putExtra(TeamResultsActivity.CONTESTID, contestId);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                zhuTime = 1;
                mBinding.tvLeftBottomName.setTextColor(Color.parseColor("#333333"));
                if (zhuTime == 1 && keTime == 1) {
                    mBinding.tvStartCompetitionStart.setEnabled(true);
                    mBinding.tvStartCompetitionStart.setBackgroundResource(R.drawable.shape_club_blue);
                    mBinding.tvStartCompetitionStart.setTextColor(Color.parseColor("#ffffff"));
                }
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                keTime = 1;
                mBinding.tvRightBottomName.setTextColor(Color.parseColor("#333333"));
                if (zhuTime == 1 && keTime == 1) {
                    mBinding.tvStartCompetitionStart.setEnabled(true);
                    mBinding.tvStartCompetitionStart.setBackgroundResource(R.drawable.shape_club_blue);
                    mBinding.tvStartCompetitionStart.setTextColor(Color.parseColor("#ffffff"));
                }
            }
        }
    }
}