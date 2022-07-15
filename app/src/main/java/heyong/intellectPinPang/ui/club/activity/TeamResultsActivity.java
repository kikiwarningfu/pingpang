package heyong.intellectPinPang.ui.club.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.List;

import heyong.intellectPinPang.bean.competition.ClubContestStatisticsBean;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.ClubContestTeamArrangeBean;
import heyong.intellectPinPang.databinding.ActivityTeamResultsBinding;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.ui.competition.activity.DoubleDetailActivity;
import heyong.intellectPinPang.ui.competition.activity.SinglesDetailActivity;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogBuilder;

/**
 * 团体比赛成绩   对内比赛
 */
public class TeamResultsActivity extends BaseActivity<ActivityTeamResultsBinding, ClubViewModel> implements View.OnClickListener {
    public static final String CONTESTID = "contestId";
    public static final String CLUB_ID = "club_id";
    private String clubId;
    private String strContestId = "";
    ClubContestTeamArrangeBean.ClubContestTeamsBean clubContestTeamsBean;
    ClubContestTeamArrangeBean.ClubContestTeamsBean clubContestTeamsBean1;
    private int leftAllWinCount = 0;
    private int rightAllWinCount = 0;
    ClubContestTeamArrangeBean clubContestTeamArrangeBean;
    /*teamtype 1是主对  2是客队*/
    List<ClubContestTeamArrangeBean.ContestArrangesBean> contestArranges;
    ClubContestStatisticsBean contestStatBean;

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.getClubContestTeamArrange(strContestId);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_team_results;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        mBinding.ivFinish.setOnClickListener(v -> finish());
        strContestId = getIntent().getStringExtra(CONTESTID);
        clubId = getIntent().getStringExtra(CLUB_ID);
        mViewModel.getClubContestStatistics(strContestId, clubId);

        mViewModel.ClubContestStatisticsLiveData.observe(this, responseBean -> {
            if (responseBean.getErrorCode().equals("200")) {
                contestStatBean = responseBean.getData();
                mBinding.tvEndCompetition.setVisibility(contestStatBean.isEndButton() ? View.VISIBLE : View.GONE);//结束比赛按钮的显示逻辑
            } else {
                Log.d(TAG, "ClubContestStatisticsLiveData: " + responseBean.getMessage());
            }
        });
        /*团队结束比赛*/
        mViewModel.updateXlClubContestInfoLiveData.observe(this, responseBean -> {
            if(responseBean.getErrorCode().equals("200")) {
                toast("提交成功");
                finish();
            } else {
                new MessageDialogBuilder(TeamResultsActivity.this)
                        .setMessage("")
                        .setTvTitle("比赛还未完成，是否要强制结束比赛?")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(v1 ->
                                mViewModel.updateXlClubContestInfo("" + strContestId, true)
                        )
                        .show();
                //toast("" + responseBean.getMessage());
            }

        });

        mViewModel.getClubContestTeamArrangeLiveData.observe(
                this, new Observer<ResponseBean<ClubContestTeamArrangeBean>>() {
                    @Override
                    public void onChanged(ResponseBean<ClubContestTeamArrangeBean> responseBean) {
                        if(responseBean.getErrorCode().equals("200")){

//                            if (responseBean.isSuccess()) {
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
//            if (Integer.parseInt(contestStatus) == 1) {
//                                    已结束
                //mBinding.tvEndCompetition.setVisibility(View.VISIBLE);
//            } else {
                /*未结束*/
//                mBinding.tvEndCompetition.setVisibility(View.GONE);
//            }

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
            }
            if (contestArranges != null && contestArranges.size() > 0) {
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
                        if (status == 0 || status == 5) {
                            mBinding.tvScoreOne.setVisibility(View.GONE);
                            mBinding.tvStartOne.setVisibility(View.VISIBLE);

                            mBinding.ivCloseMatchOne.setVisibility(View.GONE);
                            if (status == 0) {
                                mBinding.tvStartOne.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startCompetitionTwo(0, 0);
                                    }
                                });
                            } else {
                                mBinding.tvStartOne.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startCompetitionTwo(0, 5);
                                    }
                                });
                            }
                        } else if (status == 1 || status == 3) {
                            if (contestArranges.get(i).getLeftWavier() == 1 && contestArranges.get(i).getRightWavier() == 1) {
                                mBinding.tvScoreOne.setText("" + leftWinCount + "  :  " + rightWinCount);
                            } else {
                                //判断是否弃权
                                if (contestArranges.get(i).getLeftWavier() == 1) {
                                    /*左边弃权*/
                                    mBinding.tvScoreOne.setText("" + leftWinCount + "  :  " + rightWinCount);
                                } else if (contestArranges.get(i).getRightWavier() == 1) {
                                    /*右边弃权*/
                                    mBinding.tvScoreOne.setText("" + leftWinCount + "  :  " + rightWinCount);
                                } else {
                                    mBinding.tvScoreOne.setText("" + leftWinCount + "  :  " + rightWinCount);
                                }
                            }
                            if (leftWinCount > rightWinCount) {
                                leftAllWinCount = leftAllWinCount + 1;
                            } else {
                                rightAllWinCount = rightAllWinCount + 1;
                            }
                            mBinding.tvScoreOne.setVisibility(View.VISIBLE);
                            mBinding.tvStartOne.setVisibility(View.GONE);
                            mBinding.ivCloseMatchOne.setVisibility(View.GONE);
                            mBinding.tvScoreOne.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startCompetitionTwo(0, 0);
                                }
                            });
                        } else if (status == 2) {
                            /*显示ChaCha*/
                            mBinding.tvScoreOne.setVisibility(View.GONE);
                            mBinding.tvStartOne.setVisibility(View.GONE);
                            mBinding.ivCloseMatchOne.setVisibility(View.VISIBLE);
                        }
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

                        if (status == 0||status==5) {
                            mBinding.tvScoreTwo.setVisibility(View.GONE);
                            mBinding.tvStartTwo.setVisibility(View.VISIBLE);
                            mBinding.ivCloseMatchTwo.setVisibility(View.GONE);
                            mBinding.tvStartTwo.setOnClickListener(TeamResultsActivity.this);
                            if (status == 0) {
                                mBinding.tvStartTwo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startCompetitionTwo(1, 0);
                                    }
                                });
                            } else {
                                mBinding.tvStartTwo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startCompetitionTwo(1, 5);
                                    }
                                });
                            }
                        } else if (status == 1 || status == 3) {
                            if (contestArranges.get(i).getLeftWavier() == 1 && contestArranges.get(i).getRightWavier() == 1) {
                                mBinding.tvScoreTwo.setText("" + leftWinCount + "  :  " + rightWinCount);
                            } else {
                                //判断是否弃权
                                if (contestArranges.get(i).getLeftWavier() == 1) {
                                    /*左边弃权*/
                                    mBinding.tvScoreTwo.setText("" + leftWinCount + "  :  " + rightWinCount);
                                } else if (contestArranges.get(i).getRightWavier() == 1) {
                                    /*右边弃权*/
                                    mBinding.tvScoreTwo.setText("" + leftWinCount + "  :  " + rightWinCount);
                                } else {
                                    mBinding.tvScoreTwo.setText("" + leftWinCount + "  :  " + rightWinCount);
                                }
                            }
                            if (leftWinCount > rightWinCount) {
                                leftAllWinCount = leftAllWinCount + 1;
                            } else {
                                rightAllWinCount = rightAllWinCount + 1;
                            }
                            mBinding.tvScoreTwo.setVisibility(View.VISIBLE);
                            mBinding.tvStartTwo.setVisibility(View.GONE);
                            mBinding.ivCloseMatchTwo.setVisibility(View.GONE);
                            mBinding.tvScoreTwo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startCompetitionTwo(1,0);
                                }
                            });
                        } else if (status == 2) {
                            /*显示查查*/
                            mBinding.tvScoreTwo.setVisibility(View.GONE);
                            mBinding.tvStartTwo.setVisibility(View.GONE);
                            mBinding.ivCloseMatchTwo.setVisibility(View.VISIBLE);

                        }
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

                        if (status == 0||status==5) {
                            mBinding.tvThreeScore.setVisibility(View.GONE);
                            mBinding.tvThreeStart.setVisibility(View.VISIBLE);
                            mBinding.ivCloseMatchThree.setVisibility(View.GONE);
                            mBinding.tvThreeStart.setOnClickListener(TeamResultsActivity.this);
                            if (status == 0) {
                                mBinding.tvThreeStart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startCompetitionTwo(2, 0);
                                    }
                                });
                            } else {
                                mBinding.tvThreeStart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startCompetitionTwo(2, 5);
                                    }
                                });
                            }
                        } else if (status == 1 || status == 3) {
                            if (contestArranges.get(i).getLeftWavier() == 1 && contestArranges.get(i).getRightWavier() == 1) {
                                mBinding.tvThreeScore.setText("" + leftWinCount + "  :  " + rightWinCount);
                            } else {
                                //判断是否弃权
                                if (contestArranges.get(i).getLeftWavier() == 1) {
                                    /*左边弃权*/
                                    mBinding.tvThreeScore.setText("" + leftWinCount + "  :  " + rightWinCount);
                                } else if (contestArranges.get(i).getRightWavier() == 1) {
                                    /*右边弃权*/
                                    mBinding.tvThreeScore.setText("" + leftWinCount + "  :  " + rightWinCount);
                                } else {
                                    mBinding.tvThreeScore.setText("" + leftWinCount + "  :  " + rightWinCount);
                                }
                            }
                            if (leftWinCount > rightWinCount) {
                                leftAllWinCount = leftAllWinCount + 1;
                            } else {
                                rightAllWinCount = rightAllWinCount + 1;
                            }
                            mBinding.tvThreeScore.setVisibility(View.VISIBLE);
                            mBinding.tvThreeStart.setVisibility(View.GONE);
                            mBinding.ivCloseMatchThree.setVisibility(View.GONE);
                            mBinding.tvThreeScore.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startCompetitionTwo(2,0);
                                }
                            });
                        } else if (status == 2) {
                            /*chacha*/
                            mBinding.tvThreeScore.setVisibility(View.GONE);
                            mBinding.tvThreeStart.setVisibility(View.GONE);
                            mBinding.ivCloseMatchThree.setVisibility(View.VISIBLE);

                        }

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

                        if (status == 0||status==5) {
                            mBinding.tvFourScore.setVisibility(View.GONE);
                            mBinding.tvFourStart.setVisibility(View.VISIBLE);
                            mBinding.ivCloseMatchFour.setVisibility(View.GONE);
                            mBinding.tvFourStart.setOnClickListener(TeamResultsActivity.this);
                            if (status == 0) {
                                mBinding.tvFourStart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startCompetitionTwo(3, 0);
                                    }
                                });
                            } else {
                                mBinding.tvFourStart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startCompetitionTwo(3, 5);
                                    }
                                });
                            }
                        } else if (status == 1 || status == 3) {
                            if (contestArranges.get(i).getLeftWavier() == 1 && contestArranges.get(i).getRightWavier() == 1) {
                                mBinding.tvFourScore.setText("" + leftWinCount + "  :  " + rightWinCount);
                            } else {
                                //判断是否弃权
                                if (contestArranges.get(i).getLeftWavier() == 1) {
                                    /*左边弃权*/
                                    mBinding.tvFourScore.setText("" + leftWinCount + "  :  " + rightWinCount);
                                } else if (contestArranges.get(i).getRightWavier() == 1) {
                                    /*右边弃权*/
                                    mBinding.tvFourScore.setText("" + leftWinCount + "  :  " + rightWinCount);
                                } else {
                                    mBinding.tvFourScore.setText("" + leftWinCount + "  :  " + rightWinCount);
                                }
                            }
                            if (leftWinCount > rightWinCount) {
                                leftAllWinCount = leftAllWinCount + 1;
                            } else {
                                rightAllWinCount = rightAllWinCount + 1;
                            }
                            mBinding.tvFourScore.setVisibility(View.VISIBLE);
                            mBinding.tvFourStart.setVisibility(View.GONE);
                            mBinding.ivCloseMatchFour.setVisibility(View.GONE);
                            mBinding.tvFourScore.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startCompetitionTwo(3,0);
                                }
                            });
                        } else if (status == 2) {
                            /*叉叉*/
                            mBinding.tvFourScore.setVisibility(View.GONE);
                            mBinding.tvFourStart.setVisibility(View.GONE);
                            mBinding.ivCloseMatchFour.setVisibility(View.VISIBLE);
                        }

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
                        if (status == 0||status==5) {
                            mBinding.tvFifthScore.setVisibility(View.GONE);
                            mBinding.tvFifthStart.setVisibility(View.VISIBLE);
                            mBinding.ivCloseMatchFive.setVisibility(View.GONE);
                            mBinding.tvFifthStart.setOnClickListener(TeamResultsActivity.this);
                            if (status == 0) {
                                mBinding.tvFifthStart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startCompetitionTwo(4, 0);
                                    }
                                });
                            } else {
                                mBinding.tvFifthStart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startCompetitionTwo(4, 5);
                                    }
                                });
                            }
                        } else if (status == 1 || status == 3) {
                            if (contestArranges.get(i).getLeftWavier() == 1 && contestArranges.get(i).getRightWavier() == 1) {
                                mBinding.tvFifthScore.setText("" + leftWinCount + "  :  " + rightWinCount);
                            } else {
                                //判断是否弃权
                                if (contestArranges.get(i).getLeftWavier() == 1) {
                                    /*左边弃权*/
                                    mBinding.tvFifthScore.setText("" + leftWinCount + "  :  " + rightWinCount);
                                } else if (contestArranges.get(i).getRightWavier() == 1) {
                                    /*右边弃权*/
                                    mBinding.tvFifthScore.setText("" + leftWinCount + "  :  " + rightWinCount);
                                } else {
                                    mBinding.tvFifthScore.setText("" + leftWinCount + "  :  " + rightWinCount);
                                }
                            }
                            if (leftWinCount > rightWinCount) {
                                leftAllWinCount = leftAllWinCount + 1;
                            } else {
                                rightAllWinCount = rightAllWinCount + 1;
                            }
                            mBinding.tvFifthScore.setVisibility(View.VISIBLE);
                            mBinding.tvFifthStart.setVisibility(View.GONE);
                            mBinding.ivCloseMatchFive.setVisibility(View.GONE);
                            mBinding.tvFifthScore.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startCompetitionTwo(4,0);
                                }
                            });
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
            if(leftAllWinCount>rightAllWinCount)
            {
               mBinding.tvLeftWinnerStatus.setText("胜");
               mBinding.tvRightWinnerStatus.setText("负");
            }else if(leftAllWinCount<rightAllWinCount)
            {
                mBinding.tvLeftWinnerStatus.setText("负");
                mBinding.tvRightWinnerStatus.setText("胜");
            }else
            {
                mBinding.tvLeftWinnerStatus.setText("平");
                mBinding.tvRightWinnerStatus.setText("平");
            }
        } else {
            toast("" + responseBean.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*需要判断是单打还是双打*/

            case R.id.tv_end_competition://结束比赛
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                new MessageDialogBuilder(TeamResultsActivity.this)
                        .setMessage("")
                        .setTvTitle("是否结束比赛?")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(v1 ->
                                mViewModel.updateXlClubContestInfo("" + strContestId, false)
                        )
                        .show();

                break;
            case R.id.tv_right:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                new MessageDialogBuilder(TeamResultsActivity.this)
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

    private void startCompetitionTwo(int i, int page) {
        if (!contestStatBean.isEnterButton()) {
            toast("您不是参赛队员，不可录入比分");
            return;
        }

        if (contestArranges != null && contestArranges.size() > 0) {
            ClubContestTeamArrangeBean.ContestArrangesBean contestArrangesBean = contestArranges.get(i);
            String ids = "" + contestArrangesBean.getId();
            String status = contestArrangesBean.getStatus();
            String contestType = contestArrangesBean.getContestType();
            switch (contestType) {
                case "1":
                    if (page == 5)//5 显示那个新的  clickType 传2
                    {
                        goNewSingleDetail(ids, status);
                    } else {
                        goSingleDetail(ids, status);
                    }

                    break;
                case "2":
                    if (page == 5) {
                        goNewDoubleDetail(ids, status);
                    } else {
                        goDoubleDetail(ids, status);
                    }
                    break;
            }

        }
    }

    //clickType;//按钮类型 1是开始，创建初始数据，2是修改，查询数据
    private void goDoubleDetail(String ids, String status) {
        Intent intent = new Intent(TeamResultsActivity.this, DoubleDetailActivity.class);
        intent.putExtra(DoubleDetailActivity.CONTEST_ARRANGE_ID, ids);
        if (!TextUtils.isEmpty(status) && Integer.parseInt(status) != 0) {
            intent.putExtra(DoubleDetailActivity.CLICK_TYPE, "" + 2);
        } else {
            intent.putExtra(DoubleDetailActivity.CLICK_TYPE, "" + 1);

        }
        startActivity(intent);
    }

    private void goSingleDetail(String ids, String status) {
        Intent intent = new Intent(TeamResultsActivity.this, SinglesDetailActivity.class);
        intent.putExtra(SinglesDetailActivity.CONTEST_ARRANGE_ID, ids);
        if (!TextUtils.isEmpty(status) && Integer.parseInt(status) != 0) {
            intent.putExtra(SinglesDetailActivity.CLICK_TYPE, "" + 2);
        } else {
            intent.putExtra(SinglesDetailActivity.CLICK_TYPE, "" + 1);

        }
        startActivity(intent);

    }


    private void goNewDoubleDetail(String ids, String status) {
        Intent intent = new Intent(TeamResultsActivity.this, DoubleDetailActivity.class);
        intent.putExtra(DoubleDetailActivity.CONTEST_ARRANGE_ID, ids);
        intent.putExtra(DoubleDetailActivity.CLICK_TYPE, "" + 2);
        startActivity(intent);
    }

    private void goNewSingleDetail(String ids, String status) {
        Intent intent = new Intent(TeamResultsActivity.this, SinglesDetailActivity.class);
        intent.putExtra(SinglesDetailActivity.CONTEST_ARRANGE_ID, ids);
        intent.putExtra(SinglesDetailActivity.CLICK_TYPE, "" + 2);
        startActivity(intent);

    }

}