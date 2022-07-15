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
import heyong.intellectPinPang.bean.competition.XlMatchInfoArrangeChessBean;
import heyong.intellectPinPang.databinding.ActivityTeamResultsScoreBinding;
import heyong.intellectPinPang.event.BeginArrangeDisplayEvent;
import heyong.intellectPinPang.event.EndTeamEvent;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
//{"arrangeId":0,"chess":[{"canChange":false,"club1Name":"小亮俱乐部","club2Name":"超级战神队","head1Img":"https://gimg2.baidu.com/image_search/src\u003dhttp%3A%2F%2Fimg.hao661.com%2Fqq.hao661.com%2Fuploads%2Fallimg%2Fc160128%2F1453a4B243K0-29325.jpg\u0026refer\u003dhttp%3A%2F%2Fimg.hao661.com\u0026app\u003d2002\u0026size\u003df9999,10000\u0026q\u003da80\u0026n\u003d0\u0026g\u003d0n\u0026fmt\u003djpeg?sec\u003d1620446250\u0026t\u003d9c4efa6b44ee982314ce9b771c551294","head2Img":"https://gimg2.baidu.com/image_search/src\u003dhttp%3A%2F%2Fnews.cctv.com%2Fsports%2Faoyun%2Fother%2F20071205%2Fimages%2F20071205103621_Img253810452.jpg\u0026refer\u003dhttp%3A%2F%2Fnews.cctv.com\u0026app\u003d2002\u0026size\u003df9999,10000\u0026q\u003da80\u0026n\u003d0\u0026g\u003d0n\u0026fmt\u003djpeg?sec\u003d1620446147\u0026t\u003de7faf4470780c406ed4b0faa476a428d","id":0,"isFirst":false,"isSubmit":false,"isTextSubmit":false,"leftCount":"2","leftWavier":0,"littleChessBeans":[{"canChange":false,"id":0,"isFirst":false,"isSubmit":false,"isTextSubmit":false,"leftCount":"11","leftWavier":0,"rightCount":"3","rightWavier":0,"status":"1"},{"canChange":false,"id":0,"isFirst":false,"isSubmit":false,"isTextSubmit":false,"leftCount":"11","leftWavier":0,"rightCount":"4","rightWavier":0,"status":"1"},{"canChange":false,"id":0,"isFirst":false,"isSubmit":false,"isTextSubmit":false,"leftCount":"","leftWavier":0,"rightCount":"","rightWavier":0,"status":"1"}],"player1":"吴彦祖","player1Set":"A","player3":"邓亚萍","player3Set":"X","rightCount":"0","rightWavier":0,"status":"1"},{"canChange":false,"club1Name":"小亮俱乐部","club2Name":"超级战神队","head1Img":"https://img0.baidu.com/it/u\u003d1065954494,2306398903\u0026fm\u003d26\u0026fmt\u003dauto\u0026gp\u003d0.jpg","head2Img":"https://gimg2.baidu.com/image_search/src\u003dhttp%3A%2F%2Fimg.hao661.com%2Fzt.hao661.com%2Fuploads%2Fallimg%2Fc141007%2F1412E14603H30-aI3.jpg\u0026refer\u003dhttp%3A%2F%2Fimg.hao661.com\u0026app\u003d2002\u0026size\u003df9999,10000\u0026q\u003da80\u0026n\u003d0\u0026g\u003d0n\u0026fmt\u003djpeg?sec\u003d1620446165\u0026t\u003d6675663d1fe3cd540434b88574fff224","id":0,"isFirst":false,"isSubmit":false,"isTextSubmit":false,"leftCount":"2","leftWavier":0,"littleChessBeans":[{"canChange":false,"id":0,"isFirst":false,"isSubmit":false,"isTextSubmit":false,"leftCount":"11","leftWavier":0,"rightCount":"3","rightWavier":0,"status":"1"},{"canChange":false,"id":0,"isFirst":false,"isSubmit":false,"isTextSubmit":false,"leftCount":"11","leftWavier":0,"rightCount":"4","rightWavier":0,"status":"1"},{"canChange":false,"id":0,"isFirst":false,"isSubmit":false,"isTextSubmit":false,"leftCount":"","leftWavier":0,"rightCount":"","rightWavier":0,"status":"1"}],"player1":"彭于晏","player1Set":"B","player3":"周笔畅","player3Set":"Y","rightCount":"0","rightWavier":0,"status":"1"},{"canChange":false,"club1Name":"小亮俱乐部","club2Name":"超级战神队","head1Img":"https://gimg2.baidu.com/image_search/src\u003dhttp%3A%2F%2Fwww.17qq.com%2Fimg_biaoqing%2F63919825.jpeg\u0026refer\u003dhttp%3A%2F%2Fwww.17qq.com\u0026app\u003d2002\u0026size\u003df9999,10000\u0026q\u003da80\u0026n\u003d0\u0026g\u003d0n\u0026fmt\u003djpeg?sec\u003d1620446286\u0026t\u003d21e5dbf62ddd054adb759447cffc64d8","head2Img":"https://gimg2.baidu.com/image_search/src\u003dhttp%3A%2F%2Fweiyinyue.music.sina.com.cn%2Fsonglist_imgs%2F233344_420420.jpg\u0026refer\u003dhttp%3A%2F%2Fweiyinyue.music.sina.com.cn\u0026app\u003d2002\u0026size\u003df9999,10000\u0026q\u003da80\u0026n\u003d0\u0026g\u003d0n\u0026fmt\u003djpeg?sec\u003d1620446195\u0026t\u003da58546777170676e13add9209b641bf0","id":0,"isFirst":false,"isSubmit":false,"isTextSubmit":false,"leftCount":"2","leftWavier":0,"littleChessBeans":[{"canChange":false,"id":0,"isFirst":false,"isSubmit":false,"isTextSubmit":false,"leftCount":"11","leftWavier":0,"rightCount":"3","rightWavier":0,"status":"1"},{"canChange":false,"id":0,"isFirst":false,"isSubmit":false,"isTextSubmit":false,"leftCount":"11","leftWavier":0,"rightCount":"4","rightWavier":0,"status":"1"},{"canChange":false,"id":0,"isFirst":false,"isSubmit":false,"isTextSubmit":false,"leftCount":"","leftWavier":0,"rightCount":"","rightWavier":0,"status":"1"}],"player1":"乔杉","player1Set":"C","player3":"李宇春","player3Set":"Z","rightCount":"0","rightWavier":0,"status":"1"},{"canChange":false,"club1Name":"小亮俱乐部","club2Name":"超级战神队","head1Img":"https://gimg2.baidu.com/image_search/src\u003dhttp%3A%2F%2Fimg.hao661.com%2Fqq.hao661.com%2Fuploads%2Fallimg%2Fc160128%2F1453a4B243K0-29325.jpg\u0026refer\u003dhttp%3A%2F%2Fimg.hao661.com\u0026app\u003d2002\u0026size\u003df9999,10000\u0026q\u003da80\u0026n\u003d0\u0026g\u003d0n\u0026fmt\u003djpeg?sec\u003d1620446250\u0026t\u003d9c4efa6b44ee982314ce9b771c551294","head2Img":"https://gimg2.baidu.com/image_search/src\u003dhttp%3A%2F%2Fimg.hao661.com%2Fzt.hao661.com%2Fuploads%2Fallimg%2Fc141007%2F1412E14603H30-aI3.jpg\u0026refer\u003dhttp%3A%2F%2Fimg.hao661.com\u0026app\u003d2002\u0026size\u003df9999,10000\u0026q\u003da80\u0026n\u003d0\u0026g\u003d0n\u0026fmt\u003djpeg?sec\u003d1620446165\u0026t\u003d6675663d1fe3cd540434b88574fff224","id":0,"isFirst":false,"isSubmit":false,"isTextSubmit":false,"leftWavier":0,"player1":"吴彦祖","player1Set":"A","player3":"周笔畅","player3Set":"Y","rightWavier":0,"status":"2"},{"canChange":false,"club1Name":"小亮俱乐部","club2Name":"超级战神队","head1Img":"https://img0.baidu.com/it/u\u003d1065954494,2306398903\u0026fm\u003d26\u0026fmt\u003dauto\u0026gp\u003d0.jpg","head2Img":"https://gimg2.baidu.com/image_search/src\u003dhttp%3A%2F%2Fnews.cctv.com%2Fsports%2Faoyun%2Fother%2F20071205%2Fimages%2F20071205103621_Img253810452.jpg\u0026refer\u003dhttp%3A%2F%2Fnews.cctv.com\u0026app\u003d2002\u0026size\u003df9999,10000\u0026q\u003da80\u0026n\u003d0\u0026g\u003d0n\u0026fmt\u003djpeg?sec\u003d1620446147\u0026t\u003de7faf4470780c406ed4b0faa476a428d","id":0,"isFirst":false,"isSubmit":false,"isTextSubmit":false,"leftWavier":0,"player1":"彭于晏","player1Set":"B","player3":"邓亚萍","player3Set":"X","rightWavier":0,"status":"2"}],"club1Name":"小亮俱乐部","club2Name":"超级战神队","leftCount":0,"leftSupend":0,"leftWaiver":0,"refereeInArrange":false,"rightCount":0,"rightSupend":0,"rightWaiver":0}
//左边弃权
//右边弃权
//双弃权
public class DisplayTeamResultsOfficialScoreActivity extends BaseActivity<ActivityTeamResultsScoreBinding, BaseViewModel> implements View.OnClickListener {

    private int leftAllWinCount = 0;
    private int rightAllWinCount = 0;

    List<DisplayBeginArrangeBean.ChessBean> chess;
    private int pageClickType = 0;
    private int scoreClickType = 0;
    int role = 0;
    DisplayBeginArrangeBean beginArrangeBean;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_team_results_score;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        beginArrangeBean = (DisplayBeginArrangeBean) getIntent().getSerializableExtra("data");
//        littleChess = new ArrayList<>();
//        littleChess.clear();
//
        Log.e(TAG, "initView: "+new Gson().toJson(beginArrangeBean) );
        leftAllWinCount = 0;
        rightAllWinCount = 0;
        mBinding.tvTeamTitle.setText("成人组男子团体【第一阶段A组】");
//        //主队
        ImageLoader.loadImages(mBinding.ivZhu, R.drawable.img_team_host);
        ImageLoader.loadImages(mBinding.ivKe, R.drawable.img_team_customer);
//
        ImageLoader.loadImages(mBinding.ivLeftLogo, R.drawable.img_xiaoliang);
        ImageLoader.loadImages(mBinding.ivRightLogo, R.drawable.img_chaoshen);
//
        mBinding.tvLeftName.setText("小亮俱乐部");
        mBinding.tvRightName.setText("超级战神队");
//

//
        mBinding.ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        

        int leftCount = 0;
        int rightCount = 0;
        try {
            leftCount = beginArrangeBean.getLeftCount();
        } catch (Exception e) {
        }
        try {
            rightCount = beginArrangeBean.getRightCount();
        } catch (Exception e) {
        }
        mBinding.tvLeftCount.setText("" + leftCount);
        mBinding.tvRightCount.setText("" + rightCount);


        if (beginArrangeBean.getChess().size() > 0) {
            List<DisplayBeginArrangeBean.ChessBean> chess = beginArrangeBean.getChess();
            /*小于5*/
            if (chess.size() == 1) {
                chess.add(new DisplayBeginArrangeBean.ChessBean());
                chess.add(new DisplayBeginArrangeBean.ChessBean());
                chess.add(new DisplayBeginArrangeBean.ChessBean());
                chess.add(new DisplayBeginArrangeBean.ChessBean());
                mBinding.llTwo.setVisibility(View.INVISIBLE);
                mBinding.llThree.setVisibility(View.INVISIBLE);
                mBinding.llFour.setVisibility(View.INVISIBLE);
                mBinding.llFive.setVisibility(View.INVISIBLE);
            } else if (chess.size() == 2) {
                chess.add(new DisplayBeginArrangeBean.ChessBean());
                chess.add(new DisplayBeginArrangeBean.ChessBean());
                chess.add(new DisplayBeginArrangeBean.ChessBean());
                mBinding.llThree.setVisibility(View.INVISIBLE);
                mBinding.llFour.setVisibility(View.INVISIBLE);
                mBinding.llFive.setVisibility(View.INVISIBLE);
            } else if (chess.size() == 3) {
                chess.add(new DisplayBeginArrangeBean.ChessBean());
                chess.add(new DisplayBeginArrangeBean.ChessBean());
                mBinding.llFour.setVisibility(View.INVISIBLE);
                mBinding.llFive.setVisibility(View.INVISIBLE);
            } else if (chess.size() == 4) {
                chess.add(new DisplayBeginArrangeBean.ChessBean());
                mBinding.llFive.setVisibility(View.INVISIBLE);
            }

            for (int i = 0; i < 5; i++) {
                DisplayBeginArrangeBean.ChessBean chessBean = chess.get(i);
                if (chessBean != null) {
                    int hitType = 1;

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


                    int leftWavier = chess.get(i).getLeftWavier();
                    int rightWavier = chess.get(i).getRightWavier();

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
                                            

                                            goSingleDetail(ids, status,0);


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
                                            

                                            goSingleDetail(ids, status,1);

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
                                            

                                            goSingleDetail(ids, status,2);

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
                                            

                                            goSingleDetail(ids, status,3);

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
                                            

                                            goSingleDetail(ids, status,4);

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
        dealWithData();
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


    }

    private void goSingleDetail(String ids, String status,int position) {
        DisplayBeginArrangeBean beginArrangeBean = this.beginArrangeBean;

        XlMatchInfoArrangeChessBean xlMatchInfoArrangeChessBean = new XlMatchInfoArrangeChessBean();
        Intent intentSingle = new Intent(DisplayTeamResultsOfficialScoreActivity.this, DisplaySinglesDetailScoreOfficialActivity.class);
        DisplayBeginArrangeBean.ChessBean chessBean1 = beginArrangeBean.getChess().get(position);
        List<DisplayBeginArrangeBean.ChessBean.LittleChessBean> littleChessBeans = chessBean1.getLittleChessBeans();


        List<XlMatchInfoArrangeChessBean.ChessBean> chessSingle = new ArrayList<>();
        for (int i = 0; i < littleChessBeans.size(); i++) {
            XlMatchInfoArrangeChessBean.ChessBean chessBean = new XlMatchInfoArrangeChessBean.ChessBean();
            chessBean.setLeftCount(littleChessBeans.get(i).getLeftCount());
            chessBean.setRightCount(littleChessBeans.get(i).getRightCount());
            chessBean.setLeftWavier("" + littleChessBeans.get(i).getLeftWavier());
            chessBean.setRightWavier("" + littleChessBeans.get(i).getRightWavier());
            chessSingle.add(chessBean);
        }
        xlMatchInfoArrangeChessBean.setChess(chessSingle);
        xlMatchInfoArrangeChessBean.setHeadImg1(chessBean1.getHead1Img());
        xlMatchInfoArrangeChessBean.setHeadImg3(chessBean1.getHead2Img());
        xlMatchInfoArrangeChessBean.setPlayer1(chessBean1.getPlayer1());
        xlMatchInfoArrangeChessBean.setPlayer3(chessBean1.getPlayer3());
        xlMatchInfoArrangeChessBean.setLeftCount("" + chessBean1.getLeftCount());
        xlMatchInfoArrangeChessBean.setRightCount("" + chessBean1.getRightCount());
        xlMatchInfoArrangeChessBean.setClub1Name(chessBean1.getClub1Name());
        xlMatchInfoArrangeChessBean.setClub2Name(chessBean1.getClub2Name());
        intentSingle.putExtra("data", xlMatchInfoArrangeChessBean);
        startActivity(intentSingle);

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
                        mBinding.tvStartOne.setOnClickListener(DisplayTeamResultsOfficialScoreActivity.this);
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
                        mBinding.tvStartTwo.setOnClickListener(DisplayTeamResultsOfficialScoreActivity.this);

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
                        mBinding.tvThreeStart.setOnClickListener(DisplayTeamResultsOfficialScoreActivity.this);

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
                        mBinding.tvFourStart.setOnClickListener(DisplayTeamResultsOfficialScoreActivity.this);

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
                        mBinding.tvFifthStart.setOnClickListener(DisplayTeamResultsOfficialScoreActivity.this);

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
        if (leftAllWinCount == rightAllWinCount) {
            mBinding.tvLeftWinnerStatus.setText("平");
            mBinding.tvRightWinnerStatus.setText("平");
        } else if (leftAllWinCount >= rightAllWinCount) {
            mBinding.tvLeftWinnerStatus.setText("胜");
            mBinding.tvRightWinnerStatus.setText("负");
        } else {
            mBinding.tvLeftWinnerStatus.setText("负");
            mBinding.tvRightWinnerStatus.setText("胜");
        }

        mBinding.tvLeftCount.setText("" + leftAllWinCount);
        mBinding.tvRightCount.setText("" + rightAllWinCount);

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
        CustomDialog customDialogConfirm = new CustomDialog(DisplayTeamResultsOfficialScoreActivity.this);
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
        Intent intent = new Intent(DisplayTeamResultsOfficialScoreActivity.this, DisplaySingleDetailOfficialActivity.class);
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
                new MessageDialogBuilder(DisplayTeamResultsOfficialScoreActivity.this)
                        .setMessage("")
                        .setTvTitle("是否结束比赛?")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
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
                                        toast("还没有决出胜负");
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
                new MessageDialogBuilder(DisplayTeamResultsOfficialScoreActivity.this)
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
        Intent intent = new Intent(DisplayTeamResultsOfficialScoreActivity.this, DisplaySingleDetailOfficialActivity.class);
        intent.putExtra("data", beginArrangeBean);
        intent.putExtra("clickPosition", scoreClickType);
        startActivity(intent);


    }

}
