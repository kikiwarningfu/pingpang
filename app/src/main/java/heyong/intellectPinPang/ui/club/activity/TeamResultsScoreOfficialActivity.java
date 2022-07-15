package heyong.intellectPinPang.ui.club.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import java.util.List;

import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.ClubContestTeamArrangeBean;
import heyong.intellectPinPang.bean.competition.XlMatchInfoArrangeChessBean;
import heyong.intellectPinPang.databinding.ActivityTeamResultsScoreBinding;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.intellectPinPang.ui.competition.activity.DoubleDetailScoreOfficialActivity;
import heyong.intellectPinPang.ui.competition.activity.SinglesDetailScoreOfficialActivity;
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogBuilder;

/**
 * 团体比赛成绩   对内比赛
 */
public class TeamResultsScoreOfficialActivity extends BaseActivity<ActivityTeamResultsScoreBinding, HomePageViewModel> implements View.OnClickListener {
    public static final String IDS = "ids";
    private String ids = "";
    private boolean isShowRefee = false;
    private boolean isShowSign = false;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_team_results_score;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        ids = getIntent().getStringExtra(IDS);

        mViewModel.arrangeChess(ids);
        mBinding.ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewModel.arrangeChessLiveData.observe(this, new Observer<ResponseBean<XlMatchInfoArrangeChessBean>>() {
            @Override
            public void onChanged(ResponseBean<XlMatchInfoArrangeChessBean> xlMatchInfoArrangeChessBeanResponseBean) {
                XlMatchInfoArrangeChessBean data = xlMatchInfoArrangeChessBeanResponseBean.getData();
                if (data != null) {
                    int leftCount = 0;
                    int rightCount = 0;
                    try {
                        leftCount = Integer.parseInt(data.getLeftCount());
                    } catch (Exception e) {
                    }
                    try {
                        rightCount = Integer.parseInt(data.getRightCount());
                    } catch (Exception e) {
                    }
                    mBinding.tvTeamTitle.setText("" + data.getTitle());
                    mBinding.tvLeftCount.setText("" + leftCount);
                    mBinding.tvRightCount.setText("" + rightCount);
                    if (leftCount == rightCount) {
                        mBinding.tvLeftWinnerStatus.setText("平");
                        mBinding.tvRightWinnerStatus.setText("平");
                    } else if (leftCount >= rightCount) {
                        mBinding.tvLeftWinnerStatus.setText("胜");
                        mBinding.tvRightWinnerStatus.setText("负");
                    } else {
                        mBinding.tvLeftWinnerStatus.setText("负");
                        mBinding.tvRightWinnerStatus.setText("胜");
                    }
                    String club1Name = data.getClub1Name();
                    String club2Name = data.getClub2Name();
                    String headImg1 = data.getHeadImg1();
                    String headImg3 = data.getHeadImg3();
                    if (!TextUtils.isEmpty(data.getClub1Name())) {
                        mBinding.tvClubLeftName.setText("" + club1Name);
                        mBinding.tvRightClubName.setText("" + club2Name);
                        mBinding.tvLeftName.setText("" + club1Name);
                        mBinding.tvRightName.setText("" + club2Name);
                    } else {
                        mBinding.tvClubLeftName.setText("" + data.getPlayer1());
                        mBinding.tvRightClubName.setText("" + data.getPlayer3());
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
                    if (data.getChess().size() == 3) {
                        mBinding.llContainerCompetition.setVisibility(View.GONE);
                    } else {
                        mBinding.llContainerCompetition.setVisibility(View.VISIBLE);
                    }


                    if (data.getChess().size() > 0) {
                        int size = data.getChess().size();
                        List<XlMatchInfoArrangeChessBean.ChessBean> chess = data.getChess();
                        /*小于5*/
                        if (chess.size() == 1) {
                            chess.add(new XlMatchInfoArrangeChessBean.ChessBean());
                            chess.add(new XlMatchInfoArrangeChessBean.ChessBean());
                            chess.add(new XlMatchInfoArrangeChessBean.ChessBean());
                            chess.add(new XlMatchInfoArrangeChessBean.ChessBean());
                            mBinding.llTwo.setVisibility(View.INVISIBLE);
                            mBinding.llThree.setVisibility(View.INVISIBLE);
                            mBinding.llFour.setVisibility(View.INVISIBLE);
                            mBinding.llFive.setVisibility(View.INVISIBLE);
                        } else if (chess.size() == 2) {
                            chess.add(new XlMatchInfoArrangeChessBean.ChessBean());
                            chess.add(new XlMatchInfoArrangeChessBean.ChessBean());
                            chess.add(new XlMatchInfoArrangeChessBean.ChessBean());
                            mBinding.llThree.setVisibility(View.INVISIBLE);
                            mBinding.llFour.setVisibility(View.INVISIBLE);
                            mBinding.llFive.setVisibility(View.INVISIBLE);
                        } else if (chess.size() == 3) {
                            chess.add(new XlMatchInfoArrangeChessBean.ChessBean());
                            chess.add(new XlMatchInfoArrangeChessBean.ChessBean());
                            mBinding.llFour.setVisibility(View.INVISIBLE);
                            mBinding.llFive.setVisibility(View.INVISIBLE);
                        } else if (chess.size() == 4) {
                            chess.add(new XlMatchInfoArrangeChessBean.ChessBean());
                            mBinding.llFive.setVisibility(View.INVISIBLE);
                        }

                        for (int i = 0; i < 5; i++) {
                            XlMatchInfoArrangeChessBean.ChessBean chessBean = chess.get(i);
                            if (chessBean != null) {
                                int hitType = Integer.parseInt(chess.get(i).getHitType());

                                String status = chess.get(i).getStatus();//null没打  1是打了  2不用打了
                                String player1Set = chess.get(i).getPlayer1Set();
                                String player2Set = chess.get(i).getPlayer2Set();
                                String player3Set = chess.get(i).getPlayer3Set();
                                String player4Set = chess.get(i).getPlayer4Set();
                                String player1 = chess.get(i).getPlayer1();
                                String player2 = chess.get(i).getPlayer2();
                                String player3 = chess.get(i).getPlayer3();
                                String player4 = chess.get(i).getPlayer4();
                                int leftCount1 = 0;
                                try {
                                    leftCount1 = Integer.parseInt(chess.get(i).getLeftCount());
                                } catch (Exception e) {

                                }

                                int rightCount1 = 0;
                                try {
                                    rightCount1 = Integer.parseInt(chess.get(i).getRightCount());
                                } catch (Exception e) {

                                }


                                int leftWavier = Integer.parseInt(chess.get(i).getLeftWavier());
                                int rightWavier = Integer.parseInt(chess.get(i).getRightWavier());

                                if (TextUtils.isEmpty(status) || status.equals("0")) {
                                    /*没打*/
                                    if (i == 0) {
                                        mBinding.tvScoreOne.setVisibility(View.GONE);
                                        mBinding.ivCloseMatchOne.setVisibility(View.GONE);
                                    } else if (i == 1) {
                                        mBinding.tvScoreTwo.setVisibility(View.GONE);
                                        mBinding.ivCloseMatchTwo.setVisibility(View.GONE);
                                    } else if (i == 2) {
                                        mBinding.tvThreeScore.setVisibility(View.GONE);
                                        mBinding.ivCloseMatchThree.setVisibility(View.GONE);

                                    } else if (i == 3) {
                                        mBinding.tvFourScore.setVisibility(View.GONE);
                                        mBinding.ivCloseMatchFour.setVisibility(View.GONE);
                                    } else {
                                        mBinding.tvFifthScore.setVisibility(View.GONE);
                                        mBinding.ivCloseMatchFive.setVisibility(View.GONE);
                                    }
                                    String leftOneName = "";
                                    String rightOneName = "";
                                    /*打了*/
                                    if (hitType == 1) {
                                        /*单打*/
                                        leftOneName = player1 + " " + player1Set;
                                        rightOneName = player3Set + " " + player3;
                                    } else {
                                        /*双打*/
                                        leftOneName = player1 + " " + player1Set + "\n" + player2 + " " + player2Set;
                                        rightOneName = player3Set + " " + player3 + "\n" + player4Set + " " + player4;
                                    }
                                    if (i == 0) {
                                        mBinding.tvOneLeftName.setText("" + leftOneName);
                                        mBinding.tvRightOneName.setText("" + rightOneName);
                                    } else if (i == 1) {
                                        mBinding.tvTwoLeftName.setText("" + leftOneName);
                                        mBinding.tvTwoRightName.setText("" + rightOneName);
                                    } else if (i == 2) {
                                        mBinding.tvThreeLeftName.setText("" + leftOneName);
                                        mBinding.tvThreeRightName.setText("" + rightOneName);

                                    } else if (i == 3) {
                                        mBinding.tvFourLeftName.setText("" + leftOneName);
                                        mBinding.tvFourRightName.setText("" + rightOneName);
                                    } else {
                                        mBinding.tvFifthLeftName.setText("" + leftOneName);
                                        mBinding.tvFifthRightName.setText("" + rightOneName);
                                    }
                                } else {
                                    int mStatus = Integer.parseInt(status);
                                    String leftOneName = "";
                                    String rightOneName = "";
                                    if (hitType == 1) {
                                        /*单打*/
                                        leftOneName = player1 + " " + player1Set;
                                        rightOneName = player3Set + " " + player3;
                                    } else {
                                        /*双打*/
                                        leftOneName = player1 + " " + player1Set + "\n" + player2 + " " + player2Set;
                                        rightOneName = player3Set + " " + player3 + "\n" + player4Set + " " + player4;
                                    }
                                    if (i == 0) {
                                        mBinding.tvOneLeftName.setText("" + leftOneName);
                                        mBinding.tvRightOneName.setText("" + rightOneName);
                                    } else if (i == 1) {
                                        mBinding.tvTwoLeftName.setText("" + leftOneName);
                                        mBinding.tvTwoRightName.setText("" + rightOneName);
                                    } else if (i == 2) {
                                        mBinding.tvThreeLeftName.setText("" + leftOneName);
                                        mBinding.tvThreeRightName.setText("" + rightOneName);

                                    } else if (i == 3) {
                                        mBinding.tvFourLeftName.setText("" + leftOneName);
                                        mBinding.tvFourRightName.setText("" + rightOneName);
                                    } else {
                                        mBinding.tvFifthLeftName.setText("" + leftOneName);
                                        mBinding.tvFifthRightName.setText("" + rightOneName);
                                    }
                                    if (mStatus == 1) {
                                        /*打了*/


                                        switch (i) {
                                            case 0:
                                                if (leftWavier == 1 && rightWavier == 1) {
                                                    mBinding.tvScoreOne.setText("W-" + leftCount1 + "  :  W-" + rightCount1);
                                                } else {
                                                    //判断是否弃权
                                                    if (leftWavier == 1) {
                                                        /*左边弃权*/
                                                        mBinding.tvScoreOne.setText("W-" + leftCount1 + "  :  " + rightCount1);
                                                    } else if (rightWavier == 1) {
                                                        /*右边弃权*/
                                                        mBinding.tvScoreOne.setText("" + leftCount1 + "  :  W-" + rightCount1);
                                                    } else {
                                                        mBinding.tvScoreOne.setText("" + leftCount1 + "  :  " + rightCount1);
                                                    }
                                                }
                                                mBinding.tvScoreOne.setVisibility(View.VISIBLE);
                                                mBinding.tvScoreOne.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        String ids = "" + chessBean.getId();
                                                        String status = chessBean.getStatus();
                                                        String contestType = chessBean.getHitType();
                                                        switch (contestType) {
                                                            case "1":
                                                                goSingleDetail(ids, status);

                                                                break;
                                                            case "2":
                                                                goDoubleDetail(ids, status);

                                                                break;
                                                            default:

                                                                break;
                                                        }
                                                    }
                                                });
                                                mBinding.ivCloseMatchOne.setVisibility(View.GONE);
                                                break;
                                            case 1:
                                                if (leftWavier == 1 && rightWavier == 1) {
                                                    mBinding.tvScoreTwo.setText("W-" + leftCount1 + "  :  W-" + rightCount1);
                                                } else {
                                                    //判断是否弃权
                                                    if (leftWavier == 1) {
                                                        /*左边弃权*/
                                                        mBinding.tvScoreTwo.setText("W-" + leftCount1 + "  :  " + rightCount1);
                                                    } else if (rightWavier == 1) {
                                                        /*右边弃权*/
                                                        mBinding.tvScoreTwo.setText("" + leftCount1 + "  :  W-" + rightCount1);
                                                    } else {
                                                        mBinding.tvScoreTwo.setText("" + leftCount1 + "  :  " + rightCount1);
                                                    }
                                                }
                                                mBinding.tvScoreTwo.setVisibility(View.VISIBLE);
                                                mBinding.ivCloseMatchTwo.setVisibility(View.GONE);
                                                mBinding.tvScoreTwo.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        String ids = "" + chessBean.getId();
                                                        String status = chessBean.getStatus();
                                                        String contestType = chessBean.getHitType();
                                                        switch (contestType) {
                                                            case "1":
                                                                goSingleDetail(ids, status);

                                                                break;
                                                            case "2":
                                                                goDoubleDetail(ids, status);

                                                                break;
                                                            default:

                                                                break;
                                                        }
                                                    }
                                                });
                                                break;
                                            case 2:
                                                if (leftWavier == 1 && rightWavier == 1) {
                                                    mBinding.tvThreeScore.setText("W-" + leftCount1 + "  :  W-" + rightCount1);
                                                } else {
                                                    //判断是否弃权
                                                    if (leftWavier == 1) {
                                                        /*左边弃权*/
                                                        mBinding.tvThreeScore.setText("W-" + leftCount1 + "  :  " + rightCount1);
                                                    } else if (rightWavier == 1) {
                                                        /*右边弃权*/
                                                        mBinding.tvThreeScore.setText("" + leftCount1 + "  :  W-" + rightCount1);
                                                    } else {
                                                        mBinding.tvThreeScore.setText("" + leftCount1 + "  :  " + rightCount1);
                                                    }
                                                }
                                                mBinding.tvThreeScore.setVisibility(View.VISIBLE);
                                                mBinding.ivCloseMatchThree.setVisibility(View.GONE);
                                                mBinding.tvThreeScore.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        String ids = "" + chessBean.getId();
                                                        String status = chessBean.getStatus();
                                                        String contestType = chessBean.getHitType();
                                                        switch (contestType) {
                                                            case "1":

                                                                goSingleDetail(ids, status);

                                                                break;
                                                            case "2":
                                                                goDoubleDetail(ids, status);

                                                                break;
                                                            default:
                                                                break;
                                                        }
                                                    }
                                                });
                                                break;
                                            case 3:
                                                if (leftWavier == 1 && rightWavier == 1) {
                                                    mBinding.tvFourScore.setText("W-" + leftCount1 + "  :  W-" + rightCount1);
                                                } else {
                                                    //判断是否弃权
                                                    if (leftWavier == 1) {
                                                        /*左边弃权*/
                                                        mBinding.tvFourScore.setText("W-" + leftCount1 + "  :  " + rightCount1);
                                                    } else if (rightWavier == 1) {
                                                        /*右边弃权*/
                                                        mBinding.tvFourScore.setText("" + leftCount1 + "  :  W-" + rightCount1);
                                                    } else {
                                                        mBinding.tvFourScore.setText("" + leftCount1 + "  :  " + rightCount1);
                                                    }
                                                }
                                                mBinding.tvFourScore.setVisibility(View.VISIBLE);
                                                mBinding.ivCloseMatchFour.setVisibility(View.GONE);
                                                mBinding.tvFourScore.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        String ids = "" + chessBean.getId();
                                                        String status = chessBean.getStatus();
                                                        String contestType = chessBean.getHitType();
                                                        switch (contestType) {
                                                            case "1":

                                                                goSingleDetail(ids, status);

                                                                break;
                                                            case "2":
                                                                goDoubleDetail(ids, status);

                                                                break;
                                                            default:
                                                                break;
                                                        }
                                                    }
                                                });
                                                break;
                                            case 4:
                                                if (leftWavier == 1 && rightWavier == 1) {
                                                    mBinding.tvFifthScore.setText("W-" + leftCount1 + "  :  W-" + rightCount1);
                                                } else {
                                                    //判断是否弃权
                                                    if (leftWavier == 1) {
                                                        /*左边弃权*/
                                                        mBinding.tvFifthScore.setText("W-" + leftCount1 + "  :  " + rightCount1);
                                                    } else if (rightWavier == 1) {
                                                        /*右边弃权*/
                                                        mBinding.tvFifthScore.setText("" + leftCount1 + "  :  W-" + rightCount1);
                                                    } else {
                                                        mBinding.tvFifthScore.setText("" + leftCount1 + "  :  " + rightCount1);
                                                    }
                                                }
                                                mBinding.tvFifthScore.setVisibility(View.VISIBLE);
                                                mBinding.ivCloseMatchFive.setVisibility(View.GONE);
                                                mBinding.tvFifthScore.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        String ids = "" + chessBean.getId();
                                                        String status = chessBean.getStatus();
                                                        String contestType = chessBean.getHitType();
                                                        switch (contestType) {
                                                            case "1":

                                                                goSingleDetail(ids, status);

                                                                break;
                                                            case "2":
                                                                goDoubleDetail(ids, status);

                                                                break;
                                                            default:
                                                                break;
                                                        }
                                                    }
                                                });
                                                break;
                                        }

                                    } else if (mStatus == 2) {
                                        /*不用打了*/
                                        if (hitType == 1) {
                                            /*单打*/
                                            leftOneName = player1 + " " + player1Set;
                                            rightOneName = player3Set + " " + player3;
                                        } else {
                                            /*双打*/
                                            leftOneName = player1 + " " + player1Set + "\n" + player2 + " " + player2Set;
                                            rightOneName = player3Set + " " + player3 + "\n" + player4Set + " " + player4;
                                        }
                                        if (i == 0) {
                                            mBinding.tvOneLeftName.setText("" + leftOneName);
                                            mBinding.tvRightOneName.setText("" + rightOneName);
                                        } else if (i == 1) {
                                            mBinding.tvTwoLeftName.setText("" + leftOneName);
                                            mBinding.tvTwoRightName.setText("" + rightOneName);
                                        } else if (i == 2) {
                                            mBinding.tvThreeLeftName.setText("" + leftOneName);
                                            mBinding.tvThreeRightName.setText("" + rightOneName);

                                        } else if (i == 3) {
                                            mBinding.tvFourLeftName.setText("" + leftOneName);
                                            mBinding.tvFourRightName.setText("" + rightOneName);
                                        } else {
                                            mBinding.tvFifthLeftName.setText("" + leftOneName);
                                            mBinding.tvFifthRightName.setText("" + rightOneName);
                                        }
                                        switch (i) {
                                            case 0:
                                                mBinding.tvScoreOne.setVisibility(View.GONE);
                                                mBinding.ivCloseMatchOne.setVisibility(View.VISIBLE);
                                                break;
                                            case 1:
                                                mBinding.tvScoreTwo.setVisibility(View.GONE);
                                                mBinding.ivCloseMatchTwo.setVisibility(View.VISIBLE);
                                                break;
                                            case 2:
                                                mBinding.tvThreeScore.setVisibility(View.GONE);
                                                mBinding.ivCloseMatchThree.setVisibility(View.VISIBLE);
                                                break;
                                            case 3:
                                                mBinding.tvFourScore.setVisibility(View.GONE);
                                                mBinding.ivCloseMatchFour.setVisibility(View.VISIBLE);
                                                break;
                                            case 4:
                                                mBinding.tvFifthScore.setVisibility(View.GONE);
                                                mBinding.ivCloseMatchFive.setVisibility(View.VISIBLE);

                                                break;
                                        }

                                    }
                                }
                            }
                        }
                    } else {
                        mBinding.llOne.setVisibility(View.INVISIBLE);
                        mBinding.llTwo.setVisibility(View.INVISIBLE);
                        mBinding.llThree.setVisibility(View.INVISIBLE);
                        mBinding.llFour.setVisibility(View.INVISIBLE);
                        mBinding.llFive.setVisibility(View.INVISIBLE);
                    }
//                    int status = Integer.parseInt(data.getStatus());
//                    if (status == 3) {//左边已确认成绩
//                        isShowSign = false;
//                        ImageLoader.loadImage(mBinding.ivSignOne, data.getPlayer1Q());
//                    } else if (status == 4) {//右边已经确认
//                        isShowSign = false;
//                        ImageLoader.loadImage(mBinding.ivSignThree, data.getPlayer3Q());
//                    } else if (status == 5) {//两边都确认
//                        isShowSign = true;
//                        ImageLoader.loadImage(mBinding.ivSignOne, data.getPlayer1Q());
//                        ImageLoader.loadImage(mBinding.ivSignThree, data.getPlayer3Q());
//                    } else {
//                        isShowSign = false;
//                    }
//                    if (!TextUtils.isEmpty(data.getRefeeQ()) || !data.getRefeeQ().equals("2")) {
//                        ImageLoader.loadImage(mBinding.ivSignTwo, data.getRefeeQ());
//                        isShowRefee = true;
//                    } else {
//                        isShowRefee = false;
//                    }
//
//                    if (isShowSign && isShowRefee) {
//                        ImageLoader.loadImage(mBinding.ivZhang, data.getZhangImg());
//                    } else {
//                        mBinding.ivZhang.setVisibility(View.GONE);
//                    }
                } else {
                    toast("" + xlMatchInfoArrangeChessBeanResponseBean.getMessage());
                }
            }
        });


    }

    /*初始化数据*/
    private void initData(ResponseBean<ClubContestTeamArrangeBean> responseBean) {
//        if (clubContestTeamArrangeBean != null) {
//            leftAllWinCount = 0;
//            rightAllWinCount = 0;
//            List<ClubContestTeamArrangeBean.ClubContestTeamsBean> clubContestTeams = clubContestTeamArrangeBean.getClubContestTeams();
//            contestArranges = clubContestTeamArrangeBean.getContestArranges();
//            String contestStatus = clubContestTeamArrangeBean.getContestStatus();//比赛的状态  1是未结束  2是已结束
//            mBinding.tvTeamTitle.setText("" + clubContestTeamArrangeBean.getContestTitle());
//            if (Integer.parseInt(contestStatus) == 1) {
////                                    已结束
//                mBinding.tvEndCompetition.setVisibility(View.VISIBLE);
//            } else {
//                /*未结束*/
//                mBinding.tvEndCompetition.setVisibility(View.GONE);
//            }
//
//            if (clubContestTeams.size() == 2) {
//                clubContestTeamsBean = clubContestTeams.get(0);
//                clubContestTeamsBean1 = clubContestTeams.get(1);
//
//                if (Integer.parseInt(clubContestTeamsBean.getTeamType()) == 1) {
//                    /*主队*/
//                    mBinding.tvLeftName.setText("红队");
//                } else {
//                    mBinding.tvLeftName.setText("蓝队");
//                }
//                if (Integer.parseInt(clubContestTeamsBean1.getTeamType()) == 1) {
//                    /*主队*/
//                    mBinding.tvRightName.setText("红队");
//                } else {
//                    mBinding.tvRightName.setText("蓝队");
//                }
//            }
//            if (contestArranges != null && contestArranges.size() > 0) {
//                for (int i = 0; i < contestArranges.size(); i++) {
//                    int status = Integer.parseInt(contestArranges.get(i).getStatus());//
//                    int leftWinCount = contestArranges.get(i).getLeftWinCount();
//                    int rightWinCount = contestArranges.get(i).getRightWinCount();
//                    String leftName = "" + contestArranges.get(i).getLeft1Name();
//                    String leftName1 = "" + contestArranges.get(i).getLeft2Name();
//                    String rightName = "" + contestArranges.get(i).getRight1Name();
//                    String rightName2 = "" + contestArranges.get(i).getRight2Name();
//                    if (i == 0) {
//                        String contestType = contestArranges.get(i).getContestType();
//                        {
//                            switch (contestType) {
//                                case "1"://单打
//                                    mBinding.tvOneLeftName.setText(leftName);
//                                    mBinding.tvRightOneName.setText(rightName);
//                                    break;
//                                case "2":
//                                    mBinding.tvOneLeftName.setText(leftName + "\n" + leftName1);
//                                    mBinding.tvRightOneName.setText(rightName + "\n" + rightName2);
//                                    break;
//                            }
//                        }
//                        //0是没打  1 是大了  2是不用打了
//                        if (status == 0) {
//                            mBinding.tvScoreOne.setVisibility(View.GONE);
//                            mBinding.tvStartOne.setVisibility(View.VISIBLE);
//                            mBinding.tvStartOne.setOnClickListener(TeamResultsScoreOfficialActivity.this);
//                            mBinding.ivCloseMatchOne.setVisibility(View.GONE);
//                        } else if (status == 1 || status == 3) {
//                            if (contestArranges.get(i).getLeftWavier() == 1 && contestArranges.get(i).getRightWavier() == 1) {
//                                mBinding.tvScoreOne.setText("W-" + leftWinCount + "  :  W-" + rightWinCount);
//                            } else {
//                                //判断是否弃权
//                                if (contestArranges.get(i).getLeftWavier() == 1) {
//                                    /*左边弃权*/
//                                    mBinding.tvScoreOne.setText("W-" + leftWinCount + "  :  " + rightWinCount);
//                                } else if (contestArranges.get(i).getRightWavier() == 1) {
//                                    /*右边弃权*/
//                                    mBinding.tvScoreOne.setText("" + leftWinCount + "  :  W-" + rightWinCount);
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
//                    } else if (i == 1) {
//                        String contestType = contestArranges.get(i).getContestType();
//                        {
//                            switch (contestType) {
//                                case "1"://单打
//                                    mBinding.tvTwoLeftName.setText(leftName);
//                                    mBinding.tvTwoRightName.setText(rightName);
//                                    break;
//                                case "2":
//                                    mBinding.tvTwoLeftName.setText(leftName + "\n" + leftName1);
//                                    mBinding.tvTwoRightName.setText(rightName + "\n" + rightName2);
//                                    break;
//                            }
//                        }
//
//                        if (status == 0) {
//                            mBinding.tvScoreTwo.setVisibility(View.GONE);
//                            mBinding.tvStartTwo.setVisibility(View.VISIBLE);
//                            mBinding.ivCloseMatchTwo.setVisibility(View.GONE);
//                            mBinding.tvStartTwo.setOnClickListener(TeamResultsScoreOfficialActivity.this);
//
//                        } else if (status == 1 || status == 3) {
//                            if (contestArranges.get(i).getLeftWavier() == 1 && contestArranges.get(i).getRightWavier() == 1) {
//                                mBinding.tvScoreTwo.setText("W-" + leftWinCount + "  :  W-" + rightWinCount);
//                            } else {
//                                //判断是否弃权
//                                if (contestArranges.get(i).getLeftWavier() == 1) {
//                                    /*左边弃权*/
//                                    mBinding.tvScoreTwo.setText("W-" + leftWinCount + "  :  " + rightWinCount);
//                                } else if (contestArranges.get(i).getRightWavier() == 1) {
//                                    /*右边弃权*/
//                                    mBinding.tvScoreTwo.setText("" + leftWinCount + "  :  W-" + rightWinCount);
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
//                    } else if (i == 2) {
//                        String contestType = contestArranges.get(i).getContestType();
//                        {
//                            switch (contestType) {
//                                case "1"://单打
//                                    mBinding.tvThreeLeftName.setText(leftName);
//                                    mBinding.tvThreeRightName.setText(rightName);
//                                    break;
//                                case "2":
//                                    mBinding.tvThreeLeftName.setText(leftName + "\n" + leftName1);
//                                    mBinding.tvThreeRightName.setText(rightName + "\n" + rightName2);
//                                    break;
//                            }
//                        }
//
//                        if (status == 0) {
//                            mBinding.tvThreeScore.setVisibility(View.GONE);
//                            mBinding.tvThreeStart.setVisibility(View.VISIBLE);
//                            mBinding.ivCloseMatchThree.setVisibility(View.GONE);
//                            mBinding.tvThreeStart.setOnClickListener(TeamResultsScoreOfficialActivity.this);
//
//                        } else if (status == 1 || status == 3) {
//                            if (contestArranges.get(i).getLeftWavier() == 1 && contestArranges.get(i).getRightWavier() == 1) {
//                                mBinding.tvThreeScore.setText("W-" + leftWinCount + "  :  W-" + rightWinCount);
//                            } else {
//                                //判断是否弃权
//                                if (contestArranges.get(i).getLeftWavier() == 1) {
//                                    /*左边弃权*/
//                                    mBinding.tvThreeScore.setText("W-" + leftWinCount + "  :  " + rightWinCount);
//                                } else if (contestArranges.get(i).getRightWavier() == 1) {
//                                    /*右边弃权*/
//                                    mBinding.tvThreeScore.setText("" + leftWinCount + "  :  W-" + rightWinCount);
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
//
//                    } else if (i == 3) {
//                        String contestType = contestArranges.get(i).getContestType();
//                        {
//                            switch (contestType) {
//                                case "1"://单打
//                                    mBinding.tvFourLeftName.setText(leftName);
//                                    mBinding.tvFourRightName.setText(rightName);
//                                    break;
//                                case "2":
//                                    mBinding.tvFourLeftName.setText(leftName + "\n" + leftName1);
//                                    mBinding.tvFourRightName.setText(rightName + "\n" + rightName2);
//                                    break;
//                            }
//                        }
//
//                        if (status == 0) {
//                            mBinding.tvFourScore.setVisibility(View.GONE);
//                            mBinding.tvFourStart.setVisibility(View.VISIBLE);
//                            mBinding.ivCloseMatchFour.setVisibility(View.GONE);
//                            mBinding.tvFourStart.setOnClickListener(TeamResultsScoreOfficialActivity.this);
//
//                        } else if (status == 1 || status == 3) {
//                            if (contestArranges.get(i).getLeftWavier() == 1 && contestArranges.get(i).getRightWavier() == 1) {
//                                mBinding.tvFourScore.setText("W-" + leftWinCount + "  :  W-" + rightWinCount);
//                            } else {
//                                //判断是否弃权
//                                if (contestArranges.get(i).getLeftWavier() == 1) {
//                                    /*左边弃权*/
//                                    mBinding.tvFourScore.setText("W-" + leftWinCount + "  :  " + rightWinCount);
//                                } else if (contestArranges.get(i).getRightWavier() == 1) {
//                                    /*右边弃权*/
//                                    mBinding.tvFourScore.setText("" + leftWinCount + "  :  W-" + rightWinCount);
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
//
//                    } else if (i == 4) {
//                        String contestType = contestArranges.get(i).getContestType();
//                        {
//                            switch (contestType) {
//                                case "1"://单打
//                                    mBinding.tvFifthLeftName.setText(leftName);
//                                    mBinding.tvFifthRightName.setText(rightName);
//                                    break;
//                                case "2":
//                                    mBinding.tvFifthLeftName.setText(leftName + "\n" + leftName1);
//                                    mBinding.tvFifthRightName.setText(rightName + "\n" + rightName2);
//                                    break;
//                            }
//                        }
//                        if (status == 0) {
//                            mBinding.tvFifthScore.setVisibility(View.GONE);
//                            mBinding.tvFifthStart.setVisibility(View.VISIBLE);
//                            mBinding.ivCloseMatchFive.setVisibility(View.GONE);
//                            mBinding.tvFifthStart.setOnClickListener(TeamResultsScoreOfficialActivity.this);
//
//                        } else if (status == 1 || status == 3) {
//                            if (contestArranges.get(i).getLeftWavier() == 1 && contestArranges.get(i).getRightWavier() == 1) {
//                                mBinding.tvFifthScore.setText("W-" + leftWinCount + "  :  W-" + rightWinCount);
//                            } else {
//                                //判断是否弃权
//                                if (contestArranges.get(i).getLeftWavier() == 1) {
//                                    /*左边弃权*/
//                                    mBinding.tvFifthScore.setText("W-" + leftWinCount + "  :  " + rightWinCount);
//                                } else if (contestArranges.get(i).getRightWavier() == 1) {
//                                    /*右边弃权*/
//                                    mBinding.tvFifthScore.setText("" + leftWinCount + "  :  W-" + rightWinCount);
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
//                    }
//                }
//            }
//
//            mBinding.tvLeftCount.setText("" + leftAllWinCount);
//            mBinding.tvRightCount.setText("" + rightAllWinCount);
//        } else {
//            toast("" + responseBean.getMessage());
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*需要判断是单打还是双打*/
            case R.id.tv_start_one://第一场比赛开始
//                if (contestArranges != null && contestArranges.size() > 0) {
//                    ClubContestTeamArrangeBean.ContestArrangesBean contestArrangesBean = contestArranges.get(0);
//                    String ids = "" + contestArrangesBean.getId();
//                    String status = contestArrangesBean.getStatus();
//                    String contestType = contestArrangesBean.getContestType();
//                    switch (contestType) {
//                        case "1":
//                            goDoubleDetail(ids, status);
//
//                            break;
//                        case "2":
//                            goSingleDetail(ids, status);
//
//                            break;
//                    }
//
//                }


                break;
            case R.id.tv_start_two://第二场比赛开始
//                if (contestArranges != null && contestArranges.size() > 0) {
//                    ClubContestTeamArrangeBean.ContestArrangesBean contestArrangesBean = contestArranges.get(1);
//                    String ids = "" + contestArrangesBean.getId();
//                    String status = contestArrangesBean.getStatus();
//                    String contestType = contestArrangesBean.getContestType();
//                    switch (contestType) {
//                        case "1":
//                            goDoubleDetail(ids, status);
//
//                            break;
//                        case "2":
//                            goSingleDetail(ids, status);
//
//                            break;
//                    }
//
//                }

                break;
            case R.id.tv_three_start://第三场比赛开始
//                if (contestArranges != null && contestArranges.size() > 0) {
//                    ClubContestTeamArrangeBean.ContestArrangesBean contestArrangesBean = contestArranges.get(2);
//                    String ids = "" + contestArrangesBean.getId();
//                    String status = contestArrangesBean.getStatus();
//                    String contestType = contestArrangesBean.getContestType();
//                    switch (contestType) {
//                        case "1":
//                            goDoubleDetail(ids, status);
//
//                            break;
//                        case "2":
//                            goSingleDetail(ids, status);
//
//                            break;
//                    }
//
//                }

                break;
            case R.id.tv_four_start://第四场比赛开始
//                if (contestArranges != null && contestArranges.size() > 0) {
//                    ClubContestTeamArrangeBean.ContestArrangesBean contestArrangesBean = contestArranges.get(3);
//                    String ids = "" + contestArrangesBean.getId();
//                    String status = contestArrangesBean.getStatus();
//                    String contestType = contestArrangesBean.getContestType();
//                    switch (contestType) {
//                        case "1":
//                            goDoubleDetail(ids, status);
//
//                            break;
//                        case "2":
//                            goSingleDetail(ids, status);
//
//                            break;
//                    }
//
//                }

                break;
            case R.id.tv_fifth_start://第五场比赛开始
//                if (contestArranges != null && contestArranges.size() > 0) {
//                    ClubContestTeamArrangeBean.ContestArrangesBean contestArrangesBean = contestArranges.get(4);
//                    String ids = "" + contestArrangesBean.getId();
//                    String status = contestArrangesBean.getStatus();
//                    String contestType = contestArrangesBean.getContestType();
//                    switch (contestType) {
//                        case "1":
//                            goDoubleDetail(ids, status);
//
//                            break;
//                        case "2":
//                            goSingleDetail(ids, status);
//
//                            break;
//                    }
//
//                }

                break;
            case R.id.tv_end_competition://结束比赛
//                new MessageDialogBuilder(TeamResultsScoreOfficialActivity.this)
//                        .setMessage("")
//                        .setTvTitle("是否结束比赛?")
//                        .setTvCancel("取消")
//                        .setBtnCancleHint(false)
//                        .setTvSure("确定")
//                        .setSureListener(v1 ->
//                                endGame()
//                        )
//                        .show();

                break;
            case R.id.tv_right:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                new MessageDialogBuilder(TeamResultsScoreOfficialActivity.this)
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
    }

    private void goDoubleDetail(String ids, String status) {
        Intent intent = new Intent(TeamResultsScoreOfficialActivity.this, DoubleDetailScoreOfficialActivity.class);
        intent.putExtra(DoubleDetailScoreOfficialActivity.IDS, ids);
//        if (!TextUtils.isEmpty(status) && Integer.parseInt(status) != 0) {
//            intent.putExtra(DoubleDetailScoreOfficialActivity.CLICK_TYPE, "" + 2);

//        } else {
//            intent.putExtra(DoubleDetailScoreOfficialActivity.CLICK_TYPE, "" + 1);

//        }
        startActivity(intent);
    }

    private void goSingleDetail(String ids, String status) {//SinglesDetailOfficialActivity
        Intent intent = new Intent(TeamResultsScoreOfficialActivity.this, SinglesDetailScoreOfficialActivity.class);
        intent.putExtra(SinglesDetailScoreOfficialActivity.IDS, ids);
//        if (!TextUtils.isEmpty(status) && Integer.parseInt(status) != 0) {
//            intent.putExtra(SinglesDetailScoreOfficialActivity.CLICK_TYPE, "" + 2);
//
//        } else {
//            intent.putExtra(SinglesDetailScoreOfficialActivity.CLICK_TYPE, "" + 1);
//
//        }
        startActivity(intent);
    }
}