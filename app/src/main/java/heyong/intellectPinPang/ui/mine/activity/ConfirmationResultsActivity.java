package heyong.intellectPinPang.ui.mine.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xq.fasterdialog.dialog.CustomDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.XlMatchInfoArrangeChessBean;
import heyong.intellectPinPang.databinding.ActivityConfirmationResultsBinding;
import heyong.intellectPinPang.event.SwipMessageEvent;
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;

/**
 * 教练员   确认比赛成绩界面UI
 */
public class ConfirmationResultsActivity extends BaseActivity<ActivityConfirmationResultsBinding, HomePageViewModel> {
    private String ids = "";
    private String relationId = "";
    private String message = "";
    private String time = "";
    private String title = "";

    private boolean isShowRefee = false;
    private boolean isShowSign = false;
    private String status = "";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_confirmation_results;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        ids = getIntent().getStringExtra("ids");
        relationId = getIntent().getStringExtra("relationId");
        message = getIntent().getStringExtra("message");
        time = getIntent().getStringExtra("updateTime");
        title = getIntent().getStringExtra("title");
        status = getIntent().getStringExtra("status");
        if (status.equals("1")) {
            //不能点击
            mBinding.tvConfirmGrade.setEnabled(false);
            mBinding.tvConfirmGrade.setBackgroundResource(R.drawable.shape_club_gray);
            mBinding.tvConfirmGrade.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            mBinding.tvConfirmGrade.setBackgroundResource(R.drawable.shape_club_blue);
            mBinding.tvConfirmGrade.setTextColor(Color.parseColor("#FFFFFF"));
            mBinding.tvConfirmGrade.setEnabled(true);
            mBinding.tvConfirmGrade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewModel.confirmScore("" + ids, "" + relationId);
                }
            });

        }
        mViewModel.arrangeChess(relationId);
//            public MutableLiveData<ResponseBean<XlMatchInfoArrangeChessBean>> arrangeChessLiveData = new MutableLiveData<>();
        mBinding.tvTime.setText("" + time);
        mBinding.tvMessage.setText("" + message);
        mBinding.tvTitle.setText("" + title);

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
                    if(!TextUtils.isEmpty(club1Name)) {
                        mBinding.tvClubLeftName.setText("" + club1Name);
                        mBinding.tvRightClubName.setText("" + club2Name);
                        mBinding.tvLeftName.setText("" + club1Name);
                        mBinding.tvRightName.setText("" + club2Name);
                    }else
                    {
                        mBinding.tvClubLeftName.setText("" + data.getPlayer1());
                        mBinding.tvRightClubName.setText("" + data.getPlayer3());
                        mBinding.tvLeftName.setText("" + data.getPlayer1());
                        mBinding.tvRightName.setText("" + data.getPlayer3());
                    }
                    ImageLoader.loadImage(mBinding.ivLeftLogo, headImg1, R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(mBinding.ivRightLogo, headImg3, R.drawable.img_default_avatar, R.drawable.img_default_avatar);
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

                                if (TextUtils.isEmpty(status)) {
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

                                } else {
                                    int mStatus = Integer.parseInt(status);
                                    String leftOneName = "";
                                    String rightOneName = "";
                                    if (mStatus == 1) {
                                        /*打了*/
                                        if (hitType == 1) {
                                            /*单打*/
                                            leftOneName = player1 + " " + player1Set;
                                            rightOneName = player3 + " " + player3Set;
                                        } else {
                                            /*双打*/
                                            leftOneName = player1 + " " + player1Set + "\n" + player2 + " " + player2Set;
                                            rightOneName = player3 + " " + player3Set + "\n" + player4 + " " + player4Set;
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
                                                break;
                                        }

                                    } else if (mStatus == 2) {
                                        if (hitType == 1) {
                                            /*单打*/
                                            leftOneName = player1 + " " + player1Set;
                                            rightOneName = player3 + " " + player3Set;
                                        } else {
                                            /*双打*/
                                            leftOneName = player1 + " " + player1Set + "\n" + player2 + " " + player2Set;
                                            rightOneName = player3 + " " + player3Set + "\n" + player4 + " " + player4Set;
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
                                        /*不用打了*/
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
        mViewModel.confirmScoreLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    callPhoneDialog();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.scoringRefereeLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    if (customDialogConfirm != null) {
                        customDialogConfirm.dismiss();
                        toast("评分成功");
                        finish();
                    }
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
    }


    CustomDialog customDialogConfirm;

    private void callPhoneDialog() {
        customDialogConfirm = new CustomDialog(ConfirmationResultsActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_confirm_result);
        customDialogConfirm.show();
        View customView = customDialogConfirm.getCustomView();
        showAskConfirmView(customView, customDialogConfirm);
    }

    int score = 1;

    private void showAskConfirmView(View customView, CustomDialog customDialog) {

        TextView tvConfirm = customView.findViewById(R.id.tv_confirm);
        ImageView ivScoreOne = customView.findViewById(R.id.iv_one_score);
        ImageView ivScoreTwo = customView.findViewById(R.id.iv_two_score);
        ImageView ivScoreThree = customView.findViewById(R.id.iv_three_score);
        ImageLoader.loadImage(ivScoreOne, R.drawable.img_stars_select);
        ImageLoader.loadImage(ivScoreTwo, R.drawable.img_starts_unselect);
        ImageLoader.loadImage(ivScoreThree, R.drawable.img_starts_unselect);
        ivScoreOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                score = 1;
                ImageLoader.loadImage(ivScoreOne, R.drawable.img_stars_select);
                ImageLoader.loadImage(ivScoreTwo, R.drawable.img_starts_unselect);
                ImageLoader.loadImage(ivScoreThree, R.drawable.img_starts_unselect);
            }
        });
        ivScoreTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                score = 2;
                ImageLoader.loadImage(ivScoreOne, R.drawable.img_stars_select);
                ImageLoader.loadImage(ivScoreTwo, R.drawable.img_stars_select);
                ImageLoader.loadImage(ivScoreThree, R.drawable.img_starts_unselect);
            }
        });
        ivScoreThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 3;
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ImageLoader.loadImage(ivScoreOne, R.drawable.img_stars_select);
                ImageLoader.loadImage(ivScoreTwo, R.drawable.img_stars_select);
                ImageLoader.loadImage(ivScoreThree, R.drawable.img_stars_select);
            }
        });


        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialog != null) {
                    customDialog.dismiss();
                    //掉接口
//                    ids = getIntent().getStringExtra("ids");
//                    relationId = getIntent().getStringExtra("relationId");
                    EventBus.getDefault().post(new SwipMessageEvent());
                    mViewModel.scoringReferee("" + ids, "" + relationId, "" + score);
                }

            }
        });

    }
}