package heyong.intellectPinPang.ui.club.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import java.util.List;

import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.ClubContestTeamArrangeBean;
import heyong.intellectPinPang.databinding.ActivityTeamResultsScoreBinding;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.ui.competition.activity.DoubleDetailScoreActivity;
import heyong.intellectPinPang.ui.competition.activity.SinglesDetailScoreActivity;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogBuilder;

/**
 * 团体比赛成绩   对内比赛
 */
public class TeamResultsScoreActivity extends BaseActivity<ActivityTeamResultsScoreBinding, ClubViewModel> implements View.OnClickListener {
    public static final String CONTESTID = "contestId";
    private String strContestId = "";
    ClubContestTeamArrangeBean.ClubContestTeamsBean clubContestTeamsBean;
    ClubContestTeamArrangeBean.ClubContestTeamsBean clubContestTeamsBean1;
    private int leftAllWinCount = 0;
    private int rightAllWinCount = 0;
    ClubContestTeamArrangeBean clubContestTeamArrangeBean;
    /*teamtype 1是主对  2是客队*/
    List<ClubContestTeamArrangeBean.ContestArrangesBean> contestArranges;
    public static final String TEAM_STATUS = "team_status";
    private int teamStatus = 0;
    public static final String MATCH_TITLE = "match_title";
    private String strMatchTitle = "";

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.getClubContestTeamArrange(strContestId);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_team_results_score;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        teamStatus = getIntent().getIntExtra(TEAM_STATUS, 0);
        strMatchTitle = getIntent().getStringExtra(MATCH_TITLE);


        mBinding.ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        strContestId = getIntent().getStringExtra(CONTESTID);
        /*团队结束比赛*/
        mViewModel.updateXlClubContestInfoLiveData.observe(this, responseBean -> {
            if (responseBean.getErrorCode().equals("200")) {
                toast("提交成功");
                finish();
            } else {
                new MessageDialogBuilder(TeamResultsScoreActivity.this)
                        .setMessage("")
                        .setTvTitle("比赛还未完成，是否强制结束比赛?")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(v1 ->
                                mViewModel.updateXlClubContestInfo("" + strContestId,true)
                        )
                        .show();
                //toast("" + responseBean.getMessage());
            }
        });

        mViewModel.getClubContestTeamArrangeLiveData.observe(
                this, new Observer<ResponseBean<ClubContestTeamArrangeBean>>() {
                    @Override
                    public void onChanged(ResponseBean<ClubContestTeamArrangeBean> responseBean) {
                        if (responseBean.getErrorCode().equals("200")) {
                            clubContestTeamArrangeBean = responseBean.getData();
                            initData(responseBean);
                        } else {
                            toast("" + responseBean.getMessage());
                        }

                    }
                });

    }

    /*初始化数据*/
    private void initData(ResponseBean<ClubContestTeamArrangeBean> responseBean) {
        if (clubContestTeamArrangeBean != null) {
            leftAllWinCount = 0;
            rightAllWinCount = 0;
            List<ClubContestTeamArrangeBean.ClubContestTeamsBean> clubContestTeams = clubContestTeamArrangeBean.getClubContestTeams();
            contestArranges = clubContestTeamArrangeBean.getContestArranges();
            String contestStatus = clubContestTeamArrangeBean.getContestStatus();//比赛的状态  1是未结束  2是已结束
            mBinding.tvTeamTitle.setText("" + clubContestTeamArrangeBean.getContestTitle());
            if (teamStatus == 1) {
                mBinding.tvEndCompetition.setVisibility(View.GONE);
            } else {
                if (Integer.parseInt(contestStatus) == 1) {
//                                    /*未结束*/
                    mBinding.tvEndCompetition.setVisibility(View.VISIBLE);
                } else {
                    /*  2是已结束*/
                    mBinding.tvEndCompetition.setVisibility(View.GONE);
                }
            }

            if (clubContestTeams.size() == 2) {
                clubContestTeamsBean = clubContestTeams.get(0);
                clubContestTeamsBean1 = clubContestTeams.get(1);

                if (Integer.parseInt(clubContestTeamsBean.getTeamType()) == 1) {
                    /*主队*/
                    mBinding.tvLeftName.setText("红队");
                } else {
                    mBinding.tvLeftName.setText("蓝队");
                }
                if (Integer.parseInt(clubContestTeamsBean1.getTeamType()) == 1) {
                    /*主队*/
                    mBinding.tvRightName.setText("红队");
                } else {
                    mBinding.tvRightName.setText("蓝队");
                }
                int winCountLeft = clubContestTeamsBean.getWinCount();
                mBinding.tvLeftCount.setText("" + winCountLeft);
                int winCountRight = clubContestTeamsBean1.getWinCount();
                mBinding.tvRightCount.setText("" + winCountRight);
                if (winCountLeft == rightAllWinCount) {
                    mBinding.tvLeftWinnerStatus.setText("平");
                    mBinding.tvRightWinnerStatus.setText("平");
                } else if (winCountLeft > rightAllWinCount) {
                    mBinding.tvLeftWinnerStatus.setText("胜");
                    mBinding.tvRightWinnerStatus.setText("负");
                } else {
                    mBinding.tvLeftWinnerStatus.setText("负");
                    mBinding.tvRightWinnerStatus.setText("胜");
                }

            }

            if (contestArranges != null && contestArranges.size() > 0) {
                if (contestArranges.size() == 3) {
                    mBinding.llContainerCompetition.setVisibility(View.GONE);
                } else {
                    mBinding.llContainerCompetition.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < contestArranges.size(); i++) {
                    int status = Integer.parseInt(contestArranges.get(i).getStatus());//
                    int leftWinCount = contestArranges.get(i).getLeftWinCount();
                    int rightWinCount = contestArranges.get(i).getRightWinCount();
                    String leftName = "" + contestArranges.get(i).getLeft1Name();
                    String leftName1 = "" + contestArranges.get(i).getLeft2Name();
                    String rightName = "" + contestArranges.get(i).getRight1Name();
                    String rightName2 = "" + contestArranges.get(i).getRight2Name();
                    if (i == 0) {
                        String contestType = contestArranges.get(i).getContestType();
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
                        mBinding.tvScoreOne.setVisibility(View.VISIBLE);
                        mBinding.tvScoreTwo.setVisibility(View.VISIBLE);
                        mBinding.tvThreeScore.setVisibility(View.VISIBLE);
                        mBinding.tvFourScore.setVisibility(View.VISIBLE);
                        mBinding.tvFifthScore.setVisibility(View.VISIBLE);
//                        if (status == 0) {
//                            mBinding.tvScoreOne.setVisibility(View.GONE);
//                            mBinding.tvStartOne.setVisibility(View.VISIBLE);
//                            mBinding.tvStartOne.setOnClickListener(TeamResultsScoreActivity.this);
//                            mBinding.ivCloseMatchOne.setVisibility(View.GONE);
//                        } else if (status == 1 || status == 3) {
//                            if (contestArranges.get(i).getLeftWavier() == 1 && contestArranges.get(i).getRightWavier() == 1) {
                        mBinding.tvScoreOne.setText("" + leftWinCount + "  :  " + rightWinCount);
                        mBinding.tvScoreOne.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ClubContestTeamArrangeBean.ContestArrangesBean contestArrangesBean = contestArranges.get(0);
                                String ids = "" + contestArrangesBean.getId();
                                String status = contestArrangesBean.getStatus();
                                String contestType = contestArrangesBean.getContestType();
                                switch (contestType) {
                                    case "1":
                                        goSingle(ids, status);

                                        break;
                                    case "2":
                                        goDouble(ids, status);

                                        break;
                                }
                            }
                        });
//                            } else {
//                                //判断是否弃权
//                                if (contestArranges.get(i).getLeftWavier() == 1) {
//                                    /*左边弃权*/
//                                    mBinding.tvScoreOne.setText("" + leftWinCount + "  :  " + rightWinCount);
//                                } else if (contestArranges.get(i).getRightWavier() == 1) {
//                                    /*右边弃权*/
//                                    mBinding.tvScoreOne.setText("" + leftWinCount + "  :  " + rightWinCount);
//                                } else {
//                                    mBinding.tvScoreOne.setText("" + leftWinCount + "  :  " + rightWinCount);
//                                }
//                            }
//                            if (leftWinCount > rightWinCount) {
//                                leftAllWinCount = leftAllWinCount + 1;
//                            } else {
//                                rightAllWinCount = rightAllWinCount + 1;
//                            }
//                            mBinding.tvScoreOne.setVisibility(View.VISIBLE);
//                            mBinding.tvStartOne.setVisibility(View.GONE);
//                            mBinding.ivCloseMatchOne.setVisibility(View.GONE);
//
//                        } else if (status == 2) {
//                            /*显示ChaCha*/
//                            mBinding.tvScoreOne.setVisibility(View.GONE);
//                            mBinding.tvStartOne.setVisibility(View.GONE);
//                            mBinding.ivCloseMatchOne.setVisibility(View.VISIBLE);
//                        }
                    } else if (i == 1) {
                        String contestType = contestArranges.get(i).getContestType();
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

//                        if (status == 0) {
//                            mBinding.tvScoreTwo.setVisibility(View.GONE);
//                            mBinding.tvStartTwo.setVisibility(View.VISIBLE);
//                            mBinding.ivCloseMatchTwo.setVisibility(View.GONE);
//                            mBinding.tvStartTwo.setOnClickListener(TeamResultsScoreActivity.this);
//
//                        } else if (status == 1 || status == 3) {
//                            if (contestArranges.get(i).getLeftWavier() == 1 && contestArranges.get(i).getRightWavier() == 1) {
                        mBinding.tvScoreTwo.setText("" + leftWinCount + "  :  " + rightWinCount);
                        mBinding.tvScoreTwo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ClubContestTeamArrangeBean.ContestArrangesBean contestArrangesBean = contestArranges.get(1);
                                String ids = "" + contestArrangesBean.getId();
                                String status = contestArrangesBean.getStatus();
                                String contestType = contestArrangesBean.getContestType();
                                switch (contestType) {
                                    case "1":
                                        goSingle(ids, status);

                                        break;
                                    case "2":
                                        goDouble(ids, status);

                                        break;
                                }
                            }
                        });
//                            } else {
//                                //判断是否弃权
//                                if (contestArranges.get(i).getLeftWavier() == 1) {
//                                    /*左边弃权*/
//                                    mBinding.tvScoreTwo.setText("" + leftWinCount + "  :  " + rightWinCount);
//                                } else if (contestArranges.get(i).getRightWavier() == 1) {
//                                    /*右边弃权*/
//                                    mBinding.tvScoreTwo.setText("" + leftWinCount + "  :  " + rightWinCount);
//                                } else {
//                                    mBinding.tvScoreTwo.setText("" + leftWinCount + "  :  " + rightWinCount);
//                                }
//                            }
//                            if (leftWinCount > rightWinCount) {
//                                leftAllWinCount = leftAllWinCount + 1;
//                            } else {
//                                rightAllWinCount = rightAllWinCount + 1;
//                            }
//                            mBinding.tvScoreTwo.setVisibility(View.VISIBLE);
//                            mBinding.tvStartTwo.setVisibility(View.GONE);
//                            mBinding.ivCloseMatchTwo.setVisibility(View.GONE);
//
//                        } else if (status == 2) {
//                            /*显示查查*/
//                            mBinding.tvScoreTwo.setVisibility(View.GONE);
//                            mBinding.tvStartTwo.setVisibility(View.GONE);
//                            mBinding.ivCloseMatchTwo.setVisibility(View.VISIBLE);
//
//                        }
                    } else if (i == 2) {
                        String contestType = contestArranges.get(i).getContestType();
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

//                        if (status == 0) {
//                            mBinding.tvThreeScore.setVisibility(View.GONE);
//                            mBinding.tvThreeStart.setVisibility(View.VISIBLE);
//                            mBinding.ivCloseMatchThree.setVisibility(View.GONE);
//                            mBinding.tvThreeStart.setOnClickListener(TeamResultsScoreActivity.this);
//
//                        } else if (status == 1 || status == 3) {
//                            if (contestArranges.get(i).getLeftWavier() == 1 && contestArranges.get(i).getRightWavier() == 1) {
                        mBinding.tvThreeScore.setText("" + leftWinCount + "  :  " + rightWinCount);
                        mBinding.tvThreeScore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ClubContestTeamArrangeBean.ContestArrangesBean contestArrangesBean = contestArranges.get(2);
                                String ids = "" + contestArrangesBean.getId();
                                String status = contestArrangesBean.getStatus();
                                String contestType = contestArrangesBean.getContestType();
                                switch (contestType) {
                                    case "1":
                                        goSingle(ids, status);

                                        break;
                                    case "2":
                                        goDouble(ids, status);

                                        break;
                                }
                            }
                        });
//                            } else {
//                                //判断是否弃权
//                                if (contestArranges.get(i).getLeftWavier() == 1) {
//                                    /*左边弃权*/
//                                    mBinding.tvThreeScore.setText("" + leftWinCount + "  :  " + rightWinCount);
//                                } else if (contestArranges.get(i).getRightWavier() == 1) {
//                                    /*右边弃权*/
//                                    mBinding.tvThreeScore.setText("" + leftWinCount + "  :  " + rightWinCount);
//                                } else {
//                                    mBinding.tvThreeScore.setText("" + leftWinCount + "  :  " + rightWinCount);
//                                }
//                            }
//                            if (leftWinCount > rightWinCount) {
//                                leftAllWinCount = leftAllWinCount + 1;
//                            } else {
//                                rightAllWinCount = rightAllWinCount + 1;
//                            }
//                            mBinding.tvThreeScore.setVisibility(View.VISIBLE);
//                            mBinding.tvThreeStart.setVisibility(View.GONE);
//                            mBinding.ivCloseMatchThree.setVisibility(View.GONE);
//
//                        } else if (status == 2) {
//                            /*chacha*/
//                            mBinding.tvThreeScore.setVisibility(View.GONE);
//                            mBinding.tvThreeStart.setVisibility(View.GONE);
//                            mBinding.ivCloseMatchThree.setVisibility(View.VISIBLE);
//
//                        }

                    } else if (i == 3) {
                        String contestType = contestArranges.get(i).getContestType();
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

//                        if (status == 0) {
//                            mBinding.tvFourScore.setVisibility(View.GONE);
//                            mBinding.tvFourStart.setVisibility(View.VISIBLE);
//                            mBinding.ivCloseMatchFour.setVisibility(View.GONE);
//                            mBinding.tvFourStart.setOnClickListener(TeamResultsScoreActivity.this);
//
//                        } else if (status == 1 || status == 3) {
//                            if (contestArranges.get(i).getLeftWavier() == 1 && contestArranges.get(i).getRightWavier() == 1) {
                        mBinding.tvFourScore.setText("" + leftWinCount + "  :  " + rightWinCount);
                        mBinding.tvFourScore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ClubContestTeamArrangeBean.ContestArrangesBean contestArrangesBean = contestArranges.get(3);
                                String ids = "" + contestArrangesBean.getId();
                                String status = contestArrangesBean.getStatus();
                                String contestType = contestArrangesBean.getContestType();
                                switch (contestType) {
                                    case "1":
                                        goSingle(ids, status);

                                        break;
                                    case "2":
                                        goDouble(ids, status);

                                        break;
                                }
                            }
                        });
//                            } else {
//                                //判断是否弃权
//                                if (contestArranges.get(i).getLeftWavier() == 1) {
//                                    /*左边弃权*/
//                                    mBinding.tvFourScore.setText("" + leftWinCount + "  :  " + rightWinCount);
//                                } else if (contestArranges.get(i).getRightWavier() == 1) {
//                                    /*右边弃权*/
//                                    mBinding.tvFourScore.setText("" + leftWinCount + "  :  " + rightWinCount);
//                                } else {
//                                    mBinding.tvFourScore.setText("" + leftWinCount + "  :  " + rightWinCount);
//                                }
//                            }
//                            if (leftWinCount > rightWinCount) {
//                                leftAllWinCount = leftAllWinCount + 1;
//                            } else {
//                                rightAllWinCount = rightAllWinCount + 1;
//                            }
//                            mBinding.tvFourScore.setVisibility(View.VISIBLE);
//                            mBinding.tvFourStart.setVisibility(View.GONE);
//                            mBinding.ivCloseMatchFour.setVisibility(View.GONE);
//
//                        } else if (status == 2) {
//                            /*叉叉*/
//                            mBinding.tvFourScore.setVisibility(View.GONE);
//                            mBinding.tvFourStart.setVisibility(View.GONE);
//                            mBinding.ivCloseMatchFour.setVisibility(View.VISIBLE);
//                        }

                    } else if (i == 4) {
                        String contestType = contestArranges.get(i).getContestType();
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
//                        if (status == 0) {
//                            mBinding.tvFifthScore.setVisibility(View.GONE);
//                            mBinding.tvFifthStart.setVisibility(View.VISIBLE);
//                            mBinding.ivCloseMatchFive.setVisibility(View.GONE);
//                            mBinding.tvFifthStart.setOnClickListener(TeamResultsScoreActivity.this);
//
//                        } else if (status == 1 || status == 3) {
//                            if (contestArranges.get(i).getLeftWavier() == 1 && contestArranges.get(i).getRightWavier() == 1) {
                        mBinding.tvFifthScore.setText("" + leftWinCount + "  :  " + rightWinCount);
                        mBinding.tvFifthScore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ClubContestTeamArrangeBean.ContestArrangesBean contestArrangesBean = contestArranges.get(4);
                                String ids = "" + contestArrangesBean.getId();
                                String status = contestArrangesBean.getStatus();
                                String contestType = contestArrangesBean.getContestType();
                                switch (contestType) {
                                    case "1":
                                        goSingle(ids, status);

                                        break;
                                    case "2":
                                        goDouble(ids, status);

                                        break;
                                }
                            }
                        });
//                            } else {
//                                //判断是否弃权
//                                if (contestArranges.get(i).getLeftWavier() == 1) {
//                                    /*左边弃权*/
//                                    mBinding.tvFifthScore.setText("" + leftWinCount + "  :  " + rightWinCount);
//                                } else if (contestArranges.get(i).getRightWavier() == 1) {
//                                    /*右边弃权*/
//                                    mBinding.tvFifthScore.setText("" + leftWinCount + "  :  " + rightWinCount);
//                                } else {
//                                    mBinding.tvFifthScore.setText("" + leftWinCount + "  :  " + rightWinCount);
//                                }
//                            }
//                            if (leftWinCount > rightWinCount) {
//                                leftAllWinCount = leftAllWinCount + 1;
//                            } else {
//                                rightAllWinCount = rightAllWinCount + 1;
//                            }
//                            mBinding.tvFifthScore.setVisibility(View.VISIBLE);
//                            mBinding.tvFifthStart.setVisibility(View.GONE);
//                            mBinding.ivCloseMatchFive.setVisibility(View.GONE);
//
//                        } else if (status == 2) {
//                            /*chacha*/
//                            mBinding.tvFifthScore.setVisibility(View.GONE);
//                            mBinding.tvFifthStart.setVisibility(View.GONE);
//                            mBinding.ivCloseMatchFive.setVisibility(View.VISIBLE);
//                        }
                    }
                }
            }

//            mBinding.tvLeftCount.setText("" + leftAllWinCount);
//            mBinding.tvRightCount.setText("" + rightAllWinCount);
        } else {
            toast("" + responseBean.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*需要判断是单打还是双打*/
            case R.id.tv_start_one://第一场比赛开始
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (contestArranges != null && contestArranges.size() > 0) {
                    ClubContestTeamArrangeBean.ContestArrangesBean contestArrangesBean = contestArranges.get(0);
                    String ids = "" + contestArrangesBean.getId();
                    String status = contestArrangesBean.getStatus();
                    String contestType = contestArrangesBean.getContestType();
                    switch (contestType) {
                        case "1":
                            goSingle(ids, status);

                            break;
                        case "2":
                            goDouble(ids, status);

                            break;
                    }

                }


                break;
            case R.id.tv_start_two://第二场比赛开始
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (contestArranges != null && contestArranges.size() > 0) {
                    ClubContestTeamArrangeBean.ContestArrangesBean contestArrangesBean = contestArranges.get(1);
                    String ids = "" + contestArrangesBean.getId();
                    String status = contestArrangesBean.getStatus();
                    String contestType = contestArrangesBean.getContestType();
                    switch (contestType) {
                        case "1":
                            goSingle(ids, status);

                            break;
                        case "2":
                            goDouble(ids, status);

                            break;
                    }

                }

                break;
            case R.id.tv_three_start://第三场比赛开始
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (contestArranges != null && contestArranges.size() > 0) {
                    ClubContestTeamArrangeBean.ContestArrangesBean contestArrangesBean = contestArranges.get(2);
                    String ids = "" + contestArrangesBean.getId();
                    String status = contestArrangesBean.getStatus();
                    String contestType = contestArrangesBean.getContestType();
                    switch (contestType) {
                        case "1":
                            goSingle(ids, status);

                            break;
                        case "2":
                            goDouble(ids, status);

                            break;
                    }

                }

                break;
            case R.id.tv_four_start://第四场比赛开始
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (contestArranges != null && contestArranges.size() > 0) {
                    ClubContestTeamArrangeBean.ContestArrangesBean contestArrangesBean = contestArranges.get(3);
                    String ids = "" + contestArrangesBean.getId();
                    String status = contestArrangesBean.getStatus();
                    String contestType = contestArrangesBean.getContestType();
                    switch (contestType) {
                        case "1":
                            goSingle(ids, status);

                            break;
                        case "2":
                            goDouble(ids, status);

                            break;
                    }

                }

                break;
            case R.id.tv_fifth_start://第五场比赛开始
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (contestArranges != null && contestArranges.size() > 0) {
                    ClubContestTeamArrangeBean.ContestArrangesBean contestArrangesBean = contestArranges.get(4);
                    String ids = "" + contestArrangesBean.getId();
                    String status = contestArrangesBean.getStatus();
                    String contestType = contestArrangesBean.getContestType();
                    switch (contestType) {
                        case "1":
                            goSingle(ids, status);

                            break;
                        case "2":
                            goDouble(ids, status);

                            break;
                    }

                }

                break;
            case R.id.tv_end_competition://结束比赛
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                new MessageDialogBuilder(TeamResultsScoreActivity.this)
                        .setMessage("")
                        .setTvTitle("是否结束比赛?")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(v1 ->
                                mViewModel.updateXlClubContestInfo("" + strContestId,false)
                        )
                        .show();

                break;
            case R.id.tv_right:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                new MessageDialogBuilder(TeamResultsScoreActivity.this)
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

        }
    }

    /*结束比赛*/
    private void endGame() {
        /*判断满足局数*/
        mViewModel.updateXlClubContestInfo("" + strContestId,false);
    }

    private void goSingle(String ids, String status) {
        Intent intent = new Intent(TeamResultsScoreActivity.this, SinglesDetailScoreActivity.class);
        intent.putExtra(SinglesDetailScoreActivity.CONTEST_ARRANGE_ID, ids);
        if (!TextUtils.isEmpty(status) && Integer.parseInt(status) != 0) {
            intent.putExtra(SinglesDetailScoreActivity.CLICK_TYPE, "" + 2);

        } else {
            intent.putExtra(SinglesDetailScoreActivity.CLICK_TYPE, "" + 1);

        }
        startActivity(intent);
    }

    private void goDouble(String ids, String status) {
        Intent intent = new Intent(TeamResultsScoreActivity.this, DoubleDetailScoreActivity.class);
        intent.putExtra(DoubleDetailScoreActivity.CONTEST_ARRANGE_ID, ids);
        if (!TextUtils.isEmpty(status) && Integer.parseInt(status) != 0) {
            intent.putExtra(DoubleDetailScoreActivity.CLICK_TYPE, "" + 2);

        } else {
            intent.putExtra(DoubleDetailScoreActivity.CLICK_TYPE, "" + 1);

        }
        startActivity(intent);
    }
}