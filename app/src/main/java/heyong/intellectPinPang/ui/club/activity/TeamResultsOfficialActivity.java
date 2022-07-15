package heyong.intellectPinPang.ui.club.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.xq.fasterdialog.dialog.CustomDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.BeginArrangeBean;
import heyong.intellectPinPang.bean.competition.ChooseTeamFirstBean;
import heyong.intellectPinPang.bean.competition.JudgeJoinMatchStatusBean;
import heyong.intellectPinPang.databinding.ActivityTeamResultsOfficialBinding;
import heyong.intellectPinPang.event.SwipMatchEvent;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.intellectPinPang.ui.coachdisplay.WavierTeamResultActivity;
import heyong.intellectPinPang.ui.competition.activity.DoubleDetailOfficialActivity;
import heyong.intellectPinPang.ui.competition.activity.SinglesDetailOfficialActivity;
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogBuilder;

/**
 * 团体比赛成绩   对内比赛
 */
public class TeamResultsOfficialActivity extends BaseActivity<ActivityTeamResultsOfficialBinding, HomePageViewModel> implements View.OnClickListener {
    private int leftAllWinCount = 0;
    private int rightAllWinCount = 0;

    private String ids = "";
    List<BeginArrangeBean.ChessBean> chess;
    private int pageClickType = 0;
    private int scoreClickType = 0;
    int role = 0;
    private String clickPosition = "";

    @Override
    protected void onResume() {
        super.onResume();

        mViewModel.judgeJoinMatchStatus(ids);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_team_results_official;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        ids = getIntent().getStringExtra("ids");
        clickPosition = getIntent().getStringExtra("cliclkPosition");


        mViewModel.judgeMatchStatusLiveData.observe(this, new Observer<ResponseBean<JudgeJoinMatchStatusBean>>() {
            @Override
            public void onChanged(ResponseBean<JudgeJoinMatchStatusBean> judgeJoinMatchStatusBeanResponseBean) {
                if (judgeJoinMatchStatusBeanResponseBean.getErrorCode().equals("200")) {
                    role = Integer.parseInt(judgeJoinMatchStatusBeanResponseBean.getData().getRole());
                }
                mViewModel.beginArrange(ids);
            }
        });

        mViewModel.beginArrangeLiveData.observe(this, new Observer<ResponseBean<BeginArrangeBean>>() {
            @Override
            public void onChanged(ResponseBean<BeginArrangeBean> beginArrangeBeanResponseBean) {
                //请求数据
                if (beginArrangeBeanResponseBean.getErrorCode().equals("200")) {
                    BeginArrangeBean data = beginArrangeBeanResponseBean.getData();
                    if (data != null) {

                        if (data != null) {
                            leftAllWinCount = 0;
                            rightAllWinCount = 0;
                            chess = data.getChess();
                            String contestStatus = data.getStatus();//比赛的状态  1是已结束  2是未结束
                            mBinding.tvTeamTitle.setText("" + data.getTitle());
                            if (Integer.parseInt(contestStatus) == 1) {
                                mBinding.tvEndCompetition.setVisibility(View.GONE);
                            } else {
                                mBinding.tvEndCompetition.setVisibility(View.VISIBLE);
                            }
                            String club1Name = data.getClub1Name();
                            String club2Name = data.getClub2Name();
                            String headImg1 = data.getHeadImg1();
                            String headImg3 = data.getHeadImg3();

                            if (!TextUtils.isEmpty(data.getClub1Name())) {
                                mBinding.tvLeftName.setText("" + club1Name);
                                mBinding.tvRightName.setText("" + club2Name);
                            } else {
                                mBinding.tvLeftName.setText("" + data.getPlayer1());
                                mBinding.tvRightName.setText("" + data.getPlayer3());
                            }
                            if (data.getLeftTeamType().equals("2")) {
                                //客队
                                ImageLoader.loadImages(mBinding.ivZhu, R.drawable.img_team_customer);
                                ImageLoader.loadImages(mBinding.ivKe, R.drawable.img_team_host);
                            } else {
                                //主队
                                ImageLoader.loadImages(mBinding.ivZhu, R.drawable.img_team_host);
                                ImageLoader.loadImages(mBinding.ivKe, R.drawable.img_team_customer);

                            }
                            ImageLoader.loadImage(mBinding.ivLeftLogo, headImg1, R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                            ImageLoader.loadImage(mBinding.ivRightLogo, headImg3, R.drawable.img_default_avatar, R.drawable.img_default_avatar);


                            if (chess != null && chess.size() > 0) {
                                if (chess.size() == 3) {
                                    mBinding.llContainerCompetition.setVisibility(View.GONE);
                                } else {
                                    mBinding.llContainerCompetition.setVisibility(View.VISIBLE);
                                }
                                for (int i = 0; i < chess.size(); i++) {
                                    int status = 0;//
                                    int leftWinCount = 0;
                                    try {
                                        status = Integer.parseInt(chess.get(i).getStatus());
                                        leftWinCount = Integer.parseInt(chess.get(i).getLeftCount());
                                    } catch (Exception e) {

                                    }
                                    int rightWinCount = 0;
                                    try {
                                        rightWinCount = Integer.parseInt(chess.get(i).getRightCount());
                                    } catch (Exception e) {
                                    }

                                    String leftName = "" + chess.get(i).getPlayer1() + " " + chess.get(i).getPlayer1Set();
                                    String leftName1 = "" + chess.get(i).getPlayer2() + " " + chess.get(i).getPlayer2Set();
                                    String rightName = "" + chess.get(i).getPlayer3Set() + " " + chess.get(i).getPlayer3();
                                    String rightName2 = "" + chess.get(i).getPlayer4Set() + " " + chess.get(i).getPlayer4();
                                    if (i == 0) {
                                        String contestType = chess.get(i).getHitType();
                                        {
                                            switch (contestType) {
                                                case "1"://单打
                                                    mBinding.tvOneLeftName.setText(leftName);
                                                    mBinding.tvRightOneName.setText(rightName);
                                                    break;
                                                case "2":
                                                    mBinding.tvOneLeftName.setText(leftName + "\n" + leftName1);
                                                    mBinding.tvRightOneName.setText(rightName + "\n" + rightName2);
                                                    break;
                                            }
                                        }
                                        //0是没打  1 是大了  2是不用打了
                                        if (status == 0) {
                                            mBinding.tvScoreOne.setVisibility(View.GONE);
                                            mBinding.tvStartOne.setVisibility(View.VISIBLE);
                                            mBinding.tvStartOne.setOnClickListener(TeamResultsOfficialActivity.this);
                                            mBinding.ivCloseMatchOne.setVisibility(View.GONE);
                                        } else if (status == 1 || status == 3) {
                                            //                                            if (chess.get(i).getLeftWavier() == 1 && chess.get(i).getRightWavier() == 1) {
                                            //                                                mBinding.tvScoreOne.setText("W-" + leftWinCount + "  :  W-" + rightWinCount);
                                            //                                            } else {
                                            //                                                //判断是否弃权
                                            //                                                if (chess.get(i).getLeftWavier() == 1) {
                                            //                                                    /*左边弃权*/
                                            //                                                    mBinding.tvScoreOne.setText("W-" + leftWinCount + "  :  " + rightWinCount);
                                            //                                                } else if (chess.get(i).getRightWavier() == 1) {
                                            //                                                    /*右边弃权*/
                                            //                                                    mBinding.tvScoreOne.setText("" + leftWinCount + "  :  W-" + rightWinCount);
                                            //                                                } else {
                                            mBinding.tvScoreOne.setText("" + leftWinCount + "  :  " + rightWinCount);
                                            //                                                }
                                            //                                            }
                                            if (leftWinCount > rightWinCount) {
                                                leftAllWinCount = leftAllWinCount + 1;
                                            } else {
                                                rightAllWinCount = rightAllWinCount + 1;
                                            }
                                            mBinding.tvScoreOne.setVisibility(View.VISIBLE);
                                            mBinding.tvStartOne.setVisibility(View.GONE);
                                            mBinding.ivCloseMatchOne.setVisibility(View.GONE);

                                        } else if (status == 2) {
                                            /*显示ChaCha*/
                                            mBinding.tvScoreOne.setVisibility(View.GONE);
                                            mBinding.tvStartOne.setVisibility(View.GONE);
                                            mBinding.ivCloseMatchOne.setVisibility(View.VISIBLE);
                                        }
                                    } else if (i == 1) {
                                        String contestType = chess.get(i).getHitType();
                                        {
                                            switch (contestType) {
                                                case "1"://单打
                                                    mBinding.tvTwoLeftName.setText(leftName);
                                                    mBinding.tvTwoRightName.setText(rightName);
                                                    break;
                                                case "2":
                                                    mBinding.tvTwoLeftName.setText(leftName + "\n" + leftName1);
                                                    mBinding.tvTwoRightName.setText(rightName + "\n" + rightName2);
                                                    break;
                                            }
                                        }

                                        if (status == 0) {
                                            mBinding.tvScoreTwo.setVisibility(View.GONE);
                                            mBinding.tvStartTwo.setVisibility(View.VISIBLE);
                                            mBinding.ivCloseMatchTwo.setVisibility(View.GONE);
                                            mBinding.tvStartTwo.setOnClickListener(TeamResultsOfficialActivity.this);

                                        } else if (status == 1 || status == 3) {
                                            //                                            if (chess.get(i).getLeftWavier() == 1 && chess.get(i).getRightWavier() == 1) {
                                            //                                                mBinding.tvScoreTwo.setText("W-" + leftWinCount + "  :  W-" + rightWinCount);
                                            //                                            } else {
                                            //                                                //判断是否弃权
                                            //                                                if (chess.get(i).getLeftWavier() == 1) {
                                            //                                                    /*左边弃权*/
                                            //                                                    mBinding.tvScoreTwo.setText("W-" + leftWinCount + "  :  " + rightWinCount);
                                            //                                                } else if (chess.get(i).getRightWavier() == 1) {
                                            //                                                    /*右边弃权*/
                                            //                                                    mBinding.tvScoreTwo.setText("" + leftWinCount + "  :  W-" + rightWinCount);
                                            //                                                } else {
                                            mBinding.tvScoreTwo.setText("" + leftWinCount + "  :  " + rightWinCount);
                                            //                                                }
                                            //                                            }
                                            if (leftWinCount > rightWinCount) {
                                                leftAllWinCount = leftAllWinCount + 1;
                                            } else {
                                                rightAllWinCount = rightAllWinCount + 1;
                                            }
                                            mBinding.tvScoreTwo.setVisibility(View.VISIBLE);
                                            mBinding.tvStartTwo.setVisibility(View.GONE);
                                            mBinding.ivCloseMatchTwo.setVisibility(View.GONE);

                                        } else if (status == 2) {
                                            /*显示查查*/
                                            mBinding.tvScoreTwo.setVisibility(View.GONE);
                                            mBinding.tvStartTwo.setVisibility(View.GONE);
                                            mBinding.ivCloseMatchTwo.setVisibility(View.VISIBLE);

                                        }
                                    } else if (i == 2) {
                                        String contestType = chess.get(i).getHitType();
                                        {
                                            switch (contestType) {
                                                case "1"://单打
                                                    mBinding.tvThreeLeftName.setText(leftName);
                                                    mBinding.tvThreeRightName.setText(rightName);
                                                    break;
                                                case "2":
                                                    mBinding.tvThreeLeftName.setText(leftName + "\n" + leftName1);
                                                    mBinding.tvThreeRightName.setText(rightName + "\n" + rightName2);
                                                    break;
                                            }
                                        }

                                        if (status == 0) {
                                            mBinding.tvThreeScore.setVisibility(View.GONE);
                                            mBinding.tvThreeStart.setVisibility(View.VISIBLE);
                                            mBinding.ivCloseMatchThree.setVisibility(View.GONE);
                                            mBinding.tvThreeStart.setOnClickListener(TeamResultsOfficialActivity.this);

                                        } else if (status == 1 || status == 3) {
                                            if (chess.get(i).getLeftWavier() == 1 && chess.get(i).getRightWavier() == 1) {
                                                mBinding.tvThreeScore.setText("W-" + leftWinCount + "  :  W-" + rightWinCount);
                                            } else {
                                                //判断是否弃权
                                                //                                                if (chess.get(i).getLeftWavier() == 1) {
                                                //                                                    /*左边弃权*/
                                                //                                                    mBinding.tvThreeScore.setText("W-" + leftWinCount + "  :  " + rightWinCount);
                                                //                                                } else if (chess.get(i).getRightWavier() == 1) {
                                                //                                                    /*右边弃权*/
                                                //                                                    mBinding.tvThreeScore.setText("" + leftWinCount + "  :  W-" + rightWinCount);
                                                //                                                } else {
                                                mBinding.tvThreeScore.setText("" + leftWinCount + "  :  " + rightWinCount);
                                                //                                                }
                                            }
                                            if (leftWinCount > rightWinCount) {
                                                leftAllWinCount = leftAllWinCount + 1;
                                            } else {
                                                rightAllWinCount = rightAllWinCount + 1;
                                            }
                                            mBinding.tvThreeScore.setVisibility(View.VISIBLE);
                                            mBinding.tvThreeStart.setVisibility(View.GONE);
                                            mBinding.ivCloseMatchThree.setVisibility(View.GONE);

                                        } else if (status == 2) {
                                            /*chacha*/
                                            mBinding.tvThreeScore.setVisibility(View.GONE);
                                            mBinding.tvThreeStart.setVisibility(View.GONE);
                                            mBinding.ivCloseMatchThree.setVisibility(View.VISIBLE);

                                        }

                                    } else if (i == 3) {
                                        String contestType = chess.get(i).getHitType();
                                        {
                                            switch (contestType) {
                                                case "1"://单打
                                                    mBinding.tvFourLeftName.setText(leftName);
                                                    mBinding.tvFourRightName.setText(rightName);
                                                    break;
                                                case "2":
                                                    mBinding.tvFourLeftName.setText(leftName + "\n" + leftName1);
                                                    mBinding.tvFourRightName.setText(rightName + "\n" + rightName2);
                                                    break;
                                            }
                                        }

                                        if (status == 0) {
                                            mBinding.tvFourScore.setVisibility(View.GONE);
                                            mBinding.tvFourStart.setVisibility(View.VISIBLE);
                                            mBinding.ivCloseMatchFour.setVisibility(View.GONE);
                                            mBinding.tvFourStart.setOnClickListener(TeamResultsOfficialActivity.this);

                                        } else if (status == 1 || status == 3) {
                                            //                                            if (chess.get(i).getLeftWavier() == 1 && chess.get(i).getRightWavier() == 1) {
                                            //                                                mBinding.tvFourScore.setText("W-" + leftWinCount + "  :  W-" + rightWinCount);
                                            //                                            } else {
                                            //                                                //判断是否弃权
                                            //                                                if (chess.get(i).getLeftWavier() == 1) {
                                            //                                                    /*左边弃权*/
                                            //                                                    mBinding.tvFourScore.setText("W-" + leftWinCount + "  :  " + rightWinCount);
                                            //                                                } else if (chess.get(i).getRightWavier() == 1) {
                                            //                                                    /*右边弃权*/
                                            //                                                    mBinding.tvFourScore.setText("" + leftWinCount + "  :  W-" + rightWinCount);
                                            //                                                } else {
                                            mBinding.tvFourScore.setText("" + leftWinCount + "  :  " + rightWinCount);
                                            //                                                }
                                            //                                            }
                                            if (leftWinCount > rightWinCount) {
                                                leftAllWinCount = leftAllWinCount + 1;
                                            } else {
                                                rightAllWinCount = rightAllWinCount + 1;
                                            }
                                            mBinding.tvFourScore.setVisibility(View.VISIBLE);
                                            mBinding.tvFourStart.setVisibility(View.GONE);
                                            mBinding.ivCloseMatchFour.setVisibility(View.GONE);

                                        } else if (status == 2) {
                                            /*叉叉*/
                                            mBinding.tvFourScore.setVisibility(View.GONE);
                                            mBinding.tvFourStart.setVisibility(View.GONE);
                                            mBinding.ivCloseMatchFour.setVisibility(View.VISIBLE);
                                        }

                                    } else if (i == 4) {
                                        String contestType = chess.get(i).getHitType();
                                        {
                                            switch (contestType) {
                                                case "1"://单打
                                                    mBinding.tvFifthLeftName.setText(leftName);
                                                    mBinding.tvFifthRightName.setText(rightName);
                                                    break;
                                                case "2":
                                                    mBinding.tvFifthLeftName.setText(leftName + "\n" + leftName1);
                                                    mBinding.tvFifthRightName.setText(rightName + "\n" + rightName2);
                                                    break;
                                            }
                                        }
                                        if (status == 0) {
                                            mBinding.tvFifthScore.setVisibility(View.GONE);
                                            mBinding.tvFifthStart.setVisibility(View.VISIBLE);
                                            mBinding.ivCloseMatchFive.setVisibility(View.GONE);
                                            mBinding.tvFifthStart.setOnClickListener(TeamResultsOfficialActivity.this);

                                        } else if (status == 1 || status == 3) {
                                            //                                            if (chess.get(i).getLeftWavier() == 1 && chess.get(i).getRightWavier() == 1) {
                                            //                                                mBinding.tvFifthScore.setText("W-" + leftWinCount + "  :  W-" + rightWinCount);
                                            //                                            } else {
                                            //                                                //判断是否弃权
                                            //                                                if (chess.get(i).getLeftWavier() == 1) {
                                            //                                                    /*左边弃权*/
                                            //                                                    mBinding.tvFifthScore.setText("W-" + leftWinCount + "  :  " + rightWinCount);
                                            //                                                } else if (chess.get(i).getRightWavier() == 1) {
                                            //                                                    /*右边弃权*/
                                            //                                                    mBinding.tvFifthScore.setText("" + leftWinCount + "  :  W-" + rightWinCount);
                                            //                                                } else {
                                            mBinding.tvFifthScore.setText("" + leftWinCount + "  :  " + rightWinCount);
                                            //                                                }
                                            //                                            }
                                            if (leftWinCount > rightWinCount) {
                                                leftAllWinCount = leftAllWinCount + 1;
                                            } else {
                                                rightAllWinCount = rightAllWinCount + 1;
                                            }
                                            mBinding.tvFifthScore.setVisibility(View.VISIBLE);
                                            mBinding.tvFifthStart.setVisibility(View.GONE);
                                            mBinding.ivCloseMatchFive.setVisibility(View.GONE);

                                        } else if (status == 2) {
                                            /*chacha*/
                                            mBinding.tvFifthScore.setVisibility(View.GONE);
                                            mBinding.tvFifthStart.setVisibility(View.GONE);
                                            mBinding.ivCloseMatchFive.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                            }

                            mBinding.tvLeftCount.setText("" + leftAllWinCount);
                            mBinding.tvRightCount.setText("" + rightAllWinCount);
                            if (leftAllWinCount > rightAllWinCount) {
                                mBinding.tvLeftWinnerStatus.setText("胜");
                                mBinding.tvRightWinnerStatus.setText("负");
                            } else if (leftAllWinCount < rightAllWinCount) {
                                mBinding.tvLeftWinnerStatus.setText("负");
                                mBinding.tvRightWinnerStatus.setText("胜");
                            } else {
                                mBinding.tvLeftWinnerStatus.setText("平");
                                mBinding.tvRightWinnerStatus.setText("平");
                            }
                        } else {
                            toast("" + beginArrangeBeanResponseBean.getMessage());
                        }
                    } else {
                        toast("" + beginArrangeBeanResponseBean.getMessage());
                    }
                } else {
                    toast("" + beginArrangeBeanResponseBean.getMessage());
                }
            }
        });

        mBinding.ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mViewModel.chooseTeamFirstLiveData.observe(this, new Observer<ResponseBean<ChooseTeamFirstBean>>() {
            @Override
            public void onChanged(ResponseBean<ChooseTeamFirstBean> chooseTeamFirstBeanResponseBean) {
                if (chooseTeamFirstBeanResponseBean.getErrorCode().equals("200")) {
                    ChooseTeamFirstBean data = chooseTeamFirstBeanResponseBean.getData();
                    if (data != null) {
                        int angle = data.getAngle();
                        ObjectrotationAnim(angle + 360 * 8 + 180);
                    } else {
                        toast("" + chooseTeamFirstBeanResponseBean.getMessage());
                    }
                } else {
                    toast("" + chooseTeamFirstBeanResponseBean.getMessage());
                }
            }
        });
        mViewModel.judgeRefereeUpdateScoreLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("100000-100048")) {
                    toast("已修改过一次，无法再次修改，请联系裁判长修改");
                } else if (responseBean.getErrorCode().equals("200")) {
                    dealData();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.endTeamArrangeLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    EventBus.getDefault().post(new SwipMatchEvent(clickPosition, 0, 0, leftAllWinCount, rightAllWinCount));
                    toast("提交成功");
                    finish();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*需要判断是单打还是双打*/
            case R.id.tv_start_one://第一场比赛开始
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (chess != null && chess.size() > 0) {
                    pageClickType = 0;

                    showPopWinwo();
                }


                break;
            case R.id.tv_start_two://第二场比赛开始
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (chess != null && chess.size() > 0) {
                    pageClickType = 1;

                    showPopWinwo();

                }

                break;
            case R.id.tv_three_start://第三场比赛开始
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (chess != null && chess.size() > 0) {
                    pageClickType = 2;

                    showPopWinwo();

                }

                break;
            case R.id.tv_four_start://第四场比赛开始
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (chess != null && chess.size() > 0) {
                    pageClickType = 3;


                    showPopWinwo();
                }

                break;
            case R.id.tv_fifth_start://第五场比赛开始
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (chess != null && chess.size() > 0) {
                    pageClickType = 4;

                    showPopWinwo();

                }

                break;
            case R.id.tv_score_one://单打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                scoreClickType = 0;
                dealData();

                break;
            case R.id.tv_score_two://单打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                scoreClickType = 1;
                dealData();
                break;
            case R.id.tv_three_score:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                scoreClickType = 2;
                dealData();
                break;
            case R.id.tv_four_score:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                scoreClickType = 3;
                dealData();
                break;
            case R.id.tv_fifth_score:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                scoreClickType = 4;
                dealData();
                break;
            case R.id.tv_end_competition://结束比赛
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                new MessageDialogBuilder(TeamResultsOfficialActivity.this)
                        .setMessage("")
                        .setTvTitle("是否结束比赛?")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(v1 ->
                                endGame()
                        )
                        .show();

                break;
            case R.id.tv_right:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                new MessageDialogBuilder(TeamResultsOfficialActivity.this)
                        .setMessage("当审判长同意申请后，您才可以修改本场比\n" +
                                "赛成绩！\n" +
                                "                            ")
                        .setTvTitle("是否向审判长申请修改比分？")
                        .setTvSure("确定")
                        .setBtnCancleHint(false)
                        .setSureListener(v1 ->
                                toast("ahahah")
                        )
                        .show();
                break;

            case R.id.tv_give_up:

                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                //                ids
                Intent intent = new Intent(TeamResultsOfficialActivity.this, WavierTeamResultActivity.class);
                intent.putExtra(WavierTeamResultActivity.ARRANGE_ID, "" + ids);
                startActivity(intent);
                break;


        }
    }

    private void dealData() {
        BeginArrangeBean.ChessBean chessBean = chess.get(scoreClickType);
        long id = chessBean.getId();
        if (role == 3) {
            /*裁判员  */
            mViewModel.judgeRefereeUpdateScore("" + chessBean.getId());
        } else {
            /*裁判长*/
            int hitType = Integer.parseInt(chessBean.getHitType());
            switch (hitType) {
                case 1:
                    goSingleDetail("" + id);
                    break;
                case 2:
                    goDoubleDetail("" + id);
                    break;
                default:

                    break;
            }
        }
    }

    TextView tvStart;
    BeginArrangeBean.ChessBean myChessBean;
    ImageView ivZheng;
    TextView tvSubmit;

    private void showPopWinwo() {
        CustomDialog customDialogConfirm = new CustomDialog(TeamResultsOfficialActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_grade_team_draw_lot);
        customDialogConfirm.show();
        myChessBean = chess.get(pageClickType);

        View customView = customDialogConfirm.getCustomView();
        tvStart = customView.findViewById(R.id.tv_start);
        tvSubmit = customView.findViewById(R.id.tv_submit);
        ivZheng = customView.findViewById(R.id.img_zhizhen);
        TextView tvOneLeftName = customView.findViewById(R.id.tv_left_one_name);
        TextView tvTwoLeftName = customView.findViewById(R.id.tv_left_two_name);
        TextView tvOneRightName = customView.findViewById(R.id.tv_right_one_name);
        TextView tvTwoRightName = customView.findViewById(R.id.tv_right_two_name);


        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                customDialogConfirm.dismiss();
                goAnotherActivity();
            }
        });
        int mHitType = Integer.parseInt(myChessBean.getHitType());
        if (mHitType == 1) {
            //单打
            tvOneLeftName.setVisibility(View.GONE);
            tvOneRightName.setVisibility(View.GONE);
            tvTwoLeftName.setText("" + myChessBean.getPlayer1());
            tvTwoRightName.setText("" + myChessBean.getPlayer3());
        } else {
            //双打
            tvOneLeftName.setVisibility(View.VISIBLE);
            tvOneRightName.setVisibility(View.VISIBLE);
            tvOneLeftName.setText("" + myChessBean.getPlayer2());
            tvOneRightName.setText("" + myChessBean.getPlayer4());
            tvTwoLeftName.setText("" + myChessBean.getPlayer1());
            tvTwoRightName.setText("" + myChessBean.getPlayer3());
        }


        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvStart.setVisibility(View.GONE);
                //                调用接口
                mViewModel.chooseTeamFirst("" + myChessBean.getId(),
                        "" + myChessBean.getPlayer1(), "" + myChessBean.getPlayer2(),
                        "" + myChessBean.getPlayer3(), "" + myChessBean.getPlayer4());
            }
        });

    }

    //旋转动画
    //实现先顺时针360度旋转然后逆时针360度旋转动画功能
    private void ObjectrotationAnim(int angle) {
        Float aFloat = Float.valueOf(String.valueOf(angle));
        //构造ObjectAnimator对象的方法
        ObjectAnimator animator = ObjectAnimator.ofFloat(ivZheng, "rotation",
                0.0F, aFloat
        );//设置先顺时针360度旋转然后逆时针360度旋转动画
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(1000);//设置旋转时间
        animator.start();//开始执行动画（顺时针旋转动画）
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //动画结束
                tvSubmit.setVisibility(View.VISIBLE);

            }
        });
    }

    private void goAnotherActivity() {

        String ids = "" + myChessBean.getId();
        String contestType = myChessBean.getHitType();
        switch (contestType) {
            case "1":
                goSingleDetail(ids);
                break;
            case "2":
                goDoubleDetail(ids);

                break;
            default:

                break;
        }



    }
    /*结束比赛*/
    private void endGame() {
        /*判断满足局数*/
        /*判断满足局数*/
        if (leftAllWinCount > rightAllWinCount) {
            if (leftAllWinCount >= 3) {
                mViewModel.endTeamArrange("" + ids,
                        leftAllWinCount, rightAllWinCount, 1);
            } else {
                toast("比赛未决出胜负");
            }
        } else if (leftAllWinCount < rightAllWinCount) {
            if (rightAllWinCount >= 3) {
                mViewModel.endTeamArrange("" + ids,
                        leftAllWinCount, rightAllWinCount, 2);
            } else {
                toast("比赛未决出胜负");
            }
        } else {
            toast("比赛未决出胜负");
            //            mViewModel.endTeamArrange("" + ids,
            //                    leftAllWinCount, rightAllWinCount, 1);
        }
    }


    private void goDoubleDetail(String ids) {
        Intent intent = new Intent();
        intent.setClass(TeamResultsOfficialActivity.this, DoubleDetailOfficialActivity.class);
        intent.putExtra("ids", "" + ids);
//        intent.putExtra(DoubleDetailOfficialActivity.ITEM_SHOW, "false");
        intent.putExtra(DoubleDetailOfficialActivity.CODE_TYPE, "1");
        startActivity(intent);
    }

    private void goSingleDetail(String ids) {
        Intent intent = new Intent();
        intent.setClass(TeamResultsOfficialActivity.this, SinglesDetailOfficialActivity.class);
        intent.putExtra("ids", "" + ids);
//        intent.putExtra(SinglesDetailOfficialActivity.ITEM_SHOW, "false");
        intent.putExtra(SinglesDetailOfficialActivity.CODE_TYPE, "1");

        startActivity(intent);
    }


}