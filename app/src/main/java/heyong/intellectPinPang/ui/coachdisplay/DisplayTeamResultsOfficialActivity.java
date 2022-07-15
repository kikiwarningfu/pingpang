package heyong.intellectPinPang.ui.coachdisplay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.xq.fasterdialog.dialog.CustomDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.DisplayBeginArrangeBean;
import heyong.intellectPinPang.databinding.ActivityTeamResultsOfficialBinding;
import heyong.intellectPinPang.event.BeginArrangeDisplayEvent;
import heyong.intellectPinPang.event.EndTeamEvent;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogBuilder;

public class DisplayTeamResultsOfficialActivity extends BaseActivity<ActivityTeamResultsOfficialBinding, BaseViewModel> implements View.OnClickListener {

    private int leftAllWinCount = 0;
    private int rightAllWinCount = 0;

    List<DisplayBeginArrangeBean.ChessBean> chess;
    private int pageClickType = 0;
    private int scoreClickType = 0;
    DisplayBeginArrangeBean beginArrangeBean;
    List<DisplayBeginArrangeBean.ChessBean> littleChess;//小分List集合

    @Override
    public int getLayoutRes() {
        return R.layout.activity_team_results_official;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        beginArrangeBean = (DisplayBeginArrangeBean) getIntent().getSerializableExtra("data");
        littleChess = new ArrayList<>();
        littleChess.clear();

        leftAllWinCount = 0;
        rightAllWinCount = 0;
        mBinding.tvTeamTitle.setText("成人组男子团体【第一阶段A组】");
        //主队
        ImageLoader.loadImages(mBinding.ivZhu, R.drawable.img_team_host);
        ImageLoader.loadImages(mBinding.ivKe, R.drawable.img_team_customer);

        ImageLoader.loadImages(mBinding.ivLeftLogo, R.drawable.img_xiaoliang);
        ImageLoader.loadImages(mBinding.ivRightLogo, R.drawable.img_chaoshen);

        mBinding.tvLeftName.setText("小亮俱乐部");
        mBinding.tvRightName.setText("超级战神队");

        String contestStatus = beginArrangeBean.getStatus();//比赛的状态  1是已结束  2是未结束
        if (Integer.parseInt(contestStatus) == 1) {
            mBinding.tvEndCompetition.setVisibility(View.GONE);
        } else {
            mBinding.tvEndCompetition.setVisibility(View.VISIBLE);
        }
        dealWithData();


        mBinding.ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        mViewModel.chooseTeamFirstLiveData.observe(this, new Observer<ResponseBean<ChooseTeamFirstBean>>() {
//            @Override
//            public void onChanged(ResponseBean<ChooseTeamFirstBean> chooseTeamFirstBeanResponseBean) {
//                if (chooseTeamFirstBeanResponseBean.getErrorCode().equals("200")) {
//                    ChooseTeamFirstBean data = chooseTeamFirstBeanResponseBean.getData();
//                    if (data != null) {
//                        int angle = data.getAngle();
//                        ObjectrotationAnim(90 + 360 * 4 + 180);
//                    } else {
//                        toast("" + chooseTeamFirstBeanResponseBean.getMessage());
//                    }
//                } else {
//                    toast("" + chooseTeamFirstBeanResponseBean.getMessage());
//                }
//            }
//        });
//        mViewModel.judgeRefereeUpdateScoreLiveData.observe(this, new Observer<ResponseBean>() {
//            @Override
//            public void onChanged(ResponseBean responseBean) {
//                if (responseBean.getErrorCode().equals("100000-100048")) {
//                    toast("已修改过一次，无法再次修改，请联系裁判长修改");
//                } else if (responseBean.getErrorCode().equals("200")) {
//                    dealData();
//                } else {
//                    toast("" + responseBean.getMessage());
//                }
//            }
//        });
//        mViewModel.endTeamArrangeLiveData.observe(this, new Observer<ResponseBean>() {
//            @Override
//            public void onChanged(ResponseBean responseBean) {
//                if (responseBean.getErrorCode().equals("200")) {
//                    EventBus.getDefault().post(new SwipMatchEvent(clickPosition, 0, 0, leftAllWinCount, rightAllWinCount));
//                    toast("提交成功");
//                    finish();
//                } else {
//                    toast("" + responseBean.getMessage());
//                }
//            }
//        });
    }

    private void dealWithData() {
        leftAllWinCount = 0;
        rightAllWinCount = 0;
        chess = beginArrangeBean.getChess();

        if (chess != null && chess.size() > 0) {
            for (int i = 0; i < chess.size(); i++) {
                int status = Integer.parseInt(chess.get(i).getStatus());//
                int leftWinCount = 0;
                try {
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

                    mBinding.tvOneLeftName.setText(leftName);
                    mBinding.tvRightOneName.setText(rightName);

                    //0是没打  1 是大了  2是不用打了
                    if (status == 0) {
                        mBinding.tvScoreOne.setVisibility(View.GONE);
                        mBinding.tvStartOne.setVisibility(View.VISIBLE);
                        mBinding.tvStartOne.setOnClickListener(DisplayTeamResultsOfficialActivity.this);
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

                    mBinding.tvTwoLeftName.setText(leftName);
                    mBinding.tvTwoRightName.setText(rightName);
                    if (status == 0) {
                        mBinding.tvScoreTwo.setVisibility(View.GONE);
                        mBinding.tvStartTwo.setVisibility(View.VISIBLE);
                        mBinding.ivCloseMatchTwo.setVisibility(View.GONE);
                        mBinding.tvStartTwo.setOnClickListener(DisplayTeamResultsOfficialActivity.this);

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

                    mBinding.tvThreeLeftName.setText(leftName);
                    mBinding.tvThreeRightName.setText(rightName);


                    if (status == 0) {
                        mBinding.tvThreeScore.setVisibility(View.GONE);
                        mBinding.tvThreeStart.setVisibility(View.VISIBLE);
                        mBinding.ivCloseMatchThree.setVisibility(View.GONE);
                        mBinding.tvThreeStart.setOnClickListener(DisplayTeamResultsOfficialActivity.this);

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

                    mBinding.tvFourLeftName.setText(leftName);
                    mBinding.tvFourRightName.setText(rightName);


                    if (status == 0) {
                        mBinding.tvFourScore.setVisibility(View.GONE);
                        mBinding.tvFourStart.setVisibility(View.VISIBLE);
                        mBinding.ivCloseMatchFour.setVisibility(View.GONE);
                        mBinding.tvFourStart.setOnClickListener(DisplayTeamResultsOfficialActivity.this);

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

                    mBinding.tvFifthLeftName.setText(leftName);
                    mBinding.tvFifthRightName.setText(rightName);

                    if (status == 0) {
                        mBinding.tvFifthScore.setVisibility(View.GONE);
                        mBinding.tvFifthStart.setVisibility(View.VISIBLE);
                        mBinding.ivCloseMatchFive.setVisibility(View.GONE);
                        mBinding.tvFifthStart.setOnClickListener(DisplayTeamResultsOfficialActivity.this);

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
    }

    TextView tvStart;
    DisplayBeginArrangeBean.ChessBean myChessBean;
    ImageView ivZheng;
    TextView tvSubmit;

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

    private void showPopWinwo(int position) {
        CustomDialog customDialogConfirm = new CustomDialog(DisplayTeamResultsOfficialActivity.this);
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
                customDialogConfirm.dismiss();
                goAnotherActivity(myChessBean, position);
            }
        });

        //单打
        tvOneLeftName.setVisibility(View.GONE);
        tvOneRightName.setVisibility(View.GONE);
        tvTwoLeftName.setText("" + myChessBean.getPlayer1());
        tvTwoRightName.setText("" + myChessBean.getPlayer3());


        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvStart.setVisibility(View.GONE);
                ObjectrotationAnim(90 + 360 * 8 + 180);
            }
        });

    }

    List<DisplayBeginArrangeBean.ChessBean> chessBeanList = new ArrayList<>();

    private void goAnotherActivity(DisplayBeginArrangeBean.ChessBean myChessBean, int clickPosition) {

        chessBeanList.clear();
        for (int i = 0; i < 3; i++) {
            DisplayBeginArrangeBean.ChessBean chessBean = new DisplayBeginArrangeBean.ChessBean();
            chessBean.setRightCount("");
            chessBean.setLeftCount("");
            chessBean.setLeftWavier(0);
            chessBean.setRightWavier(0);
            chessBean.setStatus("1");
            chessBeanList.add(chessBean);
        }
        DisplayBeginArrangeBean beginArrangeBean = new DisplayBeginArrangeBean();
        beginArrangeBean.setClub1Name("" + myChessBean.getClub1Name());
        beginArrangeBean.setPlayer1("" + myChessBean.getPlayer1());
        beginArrangeBean.setPlayer3("" + myChessBean.getPlayer3());
        beginArrangeBean.setHeadImg1("" + myChessBean.getHead1Img());
        beginArrangeBean.setHeadImg3("" + myChessBean.getHead2Img());
        beginArrangeBean.setChess(chessBeanList);
        beginArrangeBean.setLeftCount(0);
        beginArrangeBean.setRightCount(0);
        beginArrangeBean.setLeftWaiver(0);
        beginArrangeBean.setLeftSupend(0);
        beginArrangeBean.setRightSupend(0);
        beginArrangeBean.setStatus("1");
        beginArrangeBean.setRightWaiver(0);
        beginArrangeBean.setClub2Name("" + myChessBean.getClub2Name());
        beginArrangeBean.setMethond("1");
        Intent intent = new Intent(DisplayTeamResultsOfficialActivity.this, DisplaySingleDetailOfficialActivity.class);
        intent.putExtra("data", beginArrangeBean);
        intent.putExtra("clickPosition", clickPosition);
        startActivity(intent);
    }

    //修改  单打的数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BeginArrangeDisplayEvent(BeginArrangeDisplayEvent event) {
        String beginData = event.getBeginData();
        int clickPosition = event.getClickPosition();
        DisplayBeginArrangeBean.ChessBean chessBean2 = beginArrangeBean.getChess().get(clickPosition);
        Log.e(TAG, "BeginArrangeDisplayEvent: 传递过来的这个界面的bean" + new Gson().toJson(chessBean2));


        DisplayBeginArrangeBean beginArrangeBean1 = new Gson().fromJson(beginData, DisplayBeginArrangeBean.class);
        Log.e(TAG, "BeginArrangeDisplayEvent: 小句分数:" + beginData);
        List<DisplayBeginArrangeBean.ChessBean> chess1 = beginArrangeBean1.getChess();
        List<DisplayBeginArrangeBean.ChessBean.LittleChessBean> littleChessBeans = new ArrayList<>();
        for (int i = 0; i < chess1.size(); i++) {
            DisplayBeginArrangeBean.ChessBean.LittleChessBean littleChessBean = new DisplayBeginArrangeBean.ChessBean.LittleChessBean();
            littleChessBean.setLeftCount(chess1.get(i).getLeftCount());
            littleChessBean.setLeftWavier(chess1.get(i).getLeftWavier());
            littleChessBean.setRightCount(chess1.get(i).getRightCount());
            littleChessBean.setRightWavier(chess1.get(i).getRightWavier());
            littleChessBean.setStatus(chess1.get(i).getStatus());
            littleChessBeans.add(littleChessBean);
        }

        chessBean2.setStatus("" + beginArrangeBean1.getStatus());
        chessBean2.setLeftWavier(beginArrangeBean1.getLeftWaiver());
        chessBean2.setRightWavier(beginArrangeBean1.getRightWaiver());
        chessBean2.setLeftCount("" + beginArrangeBean1.getLeftCount());
        chessBean2.setRightCount("" + beginArrangeBean1.getRightCount());
        chessBean2.setLittleChessBeans(littleChessBeans);
        beginArrangeBean.getChess().set(clickPosition, chessBean2);
        dealWithData();


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

                    showPopWinwo(0);
                }


                break;
            case R.id.tv_start_two://第二场比赛开始
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (chess != null && chess.size() > 0) {
                    pageClickType = 1;
                    showPopWinwo(1);

                }

                break;
            case R.id.tv_three_start://第三场比赛开始
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (chess != null && chess.size() > 0) {
                    pageClickType = 2;
                    showPopWinwo(2);

                }

                break;
            case R.id.tv_four_start://第四场比赛开始
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (chess != null && chess.size() > 0) {
                    pageClickType = 3;
                    showPopWinwo(3);
                }

                break;
            case R.id.tv_fifth_start://第五场比赛开始
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (chess != null && chess.size() > 0) {
                    pageClickType = 4;

                    showPopWinwo(4);

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
                new MessageDialogBuilder(DisplayTeamResultsOfficialActivity.this)
                        .setMessage("")
                        .setTvTitle("是否结束比赛?")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                List<DisplayBeginArrangeBean.ChessBean> chess = beginArrangeBean.getChess();
                                for (int i = 0; i < chess.size(); i++) {
                                    if (TextUtils.isEmpty(chess.get(i).getLeftCount()) || TextUtils.isEmpty(chess.get(i).getRightCount())) {
                                        chess.get(i).setStatus("2");
                                    }
                                }
                                beginArrangeBean.setChess(chess);
                                String strJson = new Gson().toJson(beginArrangeBean);
                                if (leftAllWinCount >= 3 || rightAllWinCount >= 3) {
                                    if (((leftAllWinCount - rightAllWinCount) >= 2) || ((rightAllWinCount - leftAllWinCount) >= 2)) {
                                        if (leftAllWinCount > rightAllWinCount) {
                                            beginArrangeBean.setStatus("1");
                                            EventBus.getDefault().post(new EndTeamEvent(strJson, leftAllWinCount, rightAllWinCount));
                                            finish();
                                        } else if (rightAllWinCount > leftAllWinCount) {
                                            beginArrangeBean.setStatus("1");
                                            EventBus.getDefault().post(new EndTeamEvent(strJson, leftAllWinCount, rightAllWinCount));
                                            finish();
                                        } else {
                                            toast("还没有决出胜负");
                                        }
                                    } else {
                                        if((leftAllWinCount+rightAllWinCount)==5)
                                        {
                                            if(leftAllWinCount>rightAllWinCount)
                                            {
                                                beginArrangeBean.setStatus("1");
                                                EventBus.getDefault().post(new EndTeamEvent(strJson, leftAllWinCount, rightAllWinCount));
                                                finish();
                                            }else
                                            {
                                                beginArrangeBean.setStatus("1");
                                                EventBus.getDefault().post(new EndTeamEvent(strJson, leftAllWinCount, rightAllWinCount));
                                                finish();
                                            }
                                        }else {
                                            toast("还没有决出胜负");
                                        }
                                    }
                                } else {
                                    toast("还没有决出胜负");
                                }
                            }
                        })
//                                endGame()

                        .show();

                break;
            case R.id.tv_right:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                new MessageDialogBuilder(DisplayTeamResultsOfficialActivity.this)
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


    private void dealData() {
        DisplayBeginArrangeBean.ChessBean chessBean = chess.get(scoreClickType);
        chessBeanList.clear();
        List<DisplayBeginArrangeBean.ChessBean.LittleChessBean> littleChessBeans = chessBean.getLittleChessBeans();
        for (int m = 0; m < littleChessBeans.size(); m++) {
            DisplayBeginArrangeBean.ChessBean chessBean2 = new DisplayBeginArrangeBean.ChessBean();
            chessBean2.setRightCount("" + littleChessBeans.get(m).getRightCount());
            chessBean2.setLeftCount("" + littleChessBeans.get(m).getLeftCount());
            chessBean2.setLeftWavier(littleChessBeans.get(m).getLeftWavier());
            chessBean2.setRightWavier(littleChessBeans.get(m).getRightWavier());
            chessBean2.setStatus("" + littleChessBeans.get(m).getStatus());
            chessBeanList.add(chessBean2);
        }
//        for (int i = 0; i < 3; i++) {
//            DisplayBeginArrangeBean.ChessBean chessBean = new DisplayBeginArrangeBean.ChessBean();
//            chessBean.setRightCount("");
//            chessBean.setLeftCount("");
//            chessBean.setLeftWavier(0);
//            chessBean.setRightWavier(0);
//            chessBean.setStatus("1");
//            chessBeanList.add(chessBean);
//        }
        DisplayBeginArrangeBean beginArrangeBean = new DisplayBeginArrangeBean();
        beginArrangeBean.setClub1Name("" + chessBean.getClub1Name());
        beginArrangeBean.setPlayer1("" + chessBean.getPlayer1());
        beginArrangeBean.setPlayer3("" + chessBean.getPlayer3());
        beginArrangeBean.setHeadImg1("" + chessBean.getHead1Img());
        beginArrangeBean.setHeadImg3("" + chessBean.getHead2Img());
        beginArrangeBean.setChess(chessBeanList);
        beginArrangeBean.setLeftCount(0);
        beginArrangeBean.setRightCount(0);
        beginArrangeBean.setLeftWaiver(0);
        beginArrangeBean.setLeftSupend(0);
        beginArrangeBean.setRightSupend(0);
        beginArrangeBean.setStatus("1");
        beginArrangeBean.setRightWaiver(0);
        beginArrangeBean.setClub2Name("" + chessBean.getClub2Name());
        beginArrangeBean.setMethond("1");
        Intent intent = new Intent(DisplayTeamResultsOfficialActivity.this, DisplaySingleDetailOfficialActivity.class);
        intent.putExtra("data", beginArrangeBean);
        intent.putExtra("clickPosition", scoreClickType);
        startActivity(intent);


    }

}
