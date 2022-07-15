package heyong.intellectPinPang.ui.coachdisplay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.xq.fasterdialog.dialog.CustomDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.DisplayBeginArrangeBean;
import heyong.intellectPinPang.bean.competition.SubmitTeamArrangeBean;
import heyong.intellectPinPang.databinding.ActivityDrawForHostAndVistorsBinding;
import heyong.intellectPinPang.event.DisplayCoachMatchFormEvent;
import heyong.intellectPinPang.utils.AntiShakeUtils;

/**
 * 裁判员主客队抽签
 */
public class DisplayDrawForHostAndVistors extends BaseActivity<ActivityDrawForHostAndVistorsBinding, BaseViewModel> implements View.OnClickListener {
    CountDownTimer fillMatchTimer;
    private int lunTimes = 0;
    private int inputType = 0;//7左边  8 右边 9 全部
    List<SubmitTeamArrangeBean.TeamArrangesBean> teamArrangesLeft;
    List<SubmitTeamArrangeBean.TeamArrangesBean> teamArrangesRight;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_draw_for_host_and_vistors;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        teamArrangesLeft = new ArrayList<>();
        teamArrangesRight = new ArrayList<>();
        mBinding.tvTitleName.setText("成人组男子团体【第一阶段A组】");
        ImageLoader.loadImages(mBinding.ivLeftLogo, R.drawable.img_chaoshen);
        ImageLoader.loadImages(mBinding.ivRightLogo, R.drawable.img_xiaoliang);
        mBinding.tvLeftName.setText("超级战神队");
        mBinding.tvRightName.setText("小亮俱乐部");
        mBinding.tvLeftClubName.setText("");
        mBinding.tvRightClubName.setText("");
        mBinding.llLeftCommit.setEnabled(false);
        mBinding.llRightCommit.setEnabled(false);

    }

    //旋转动画
    //实现先顺时针360度旋转然后逆时针360度旋转动画功能
    private void ObjectrotationAnim(int angle) {
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

                showSurprise();

            }
        });
    }

    private void showAskConfirmView(View customView, CustomDialog customDialog) {

        TextView tvSure = customView.findViewById(R.id.tv_sure);
        TextView tvLeftName = customView.findViewById(R.id.tv_left_name);
        TextView tvRightName = customView.findViewById(R.id.tv_right_name);
        //判断是否为1
        tvLeftName.setText("主：小亮俱乐部");
        tvRightName.setText("客：超级战神队");


        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialog != null) {
                    customDialog.dismiss();
                    mBinding.llLeftCommit.setEnabled(true);
                    mBinding.llRightCommit.setEnabled(true);
                    mBinding.tvLeftClubName.setText("小亮俱乐部");
                    mBinding.tvRightClubName.setText("超级战神队");

                    mBinding.tvLeftCommit.setVisibility(View.VISIBLE);
                    mBinding.tvLeftCommit.setText("等待提交");
                    mBinding.tvRightCommit.setText("等待提交");
                    mBinding.tvTopTitle.setText("教练员正在填写对阵表");
                    mBinding.tvCountdownTimer.setText("5:00");
                    mBinding.tvSend.setEnabled(false);
                    mBinding.tvSend.setBackgroundResource(R.drawable.shape_gray_corners_solid);
                    mBinding.tvSend.setTextColor(Color.parseColor("#ffffff"));
                    mBinding.tvRightCommit.setVisibility(View.VISIBLE);

                    fillMatchTimer = new CountDownTimer(5 * 60 * 1000 - 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            {
                                DecimalFormat dec = new DecimalFormat("##.##");
                                int floor = (int) Math.floor(millisUntilFinished / 60000);
                                if (floor == 10) {
                                    mBinding.tvCountdownTimer.setText("" + "10" + ":" + dec.format((millisUntilFinished % 60000) / 1000));
                                } else {
                                    mBinding.tvCountdownTimer.setText("" + timeParse(millisUntilFinished));
                                }
                            }
                        }

                        @Override
                        public void onFinish() {
                            mBinding.tvCountdownTimer.setText("00:00");
                            //去请求接口


                        }
                    }.start();

                }

            }
        });


    }

    public void showSurprise() {
        CustomDialog customDialogConfirm = new CustomDialog(DisplayDrawForHostAndVistors.this);
        customDialogConfirm.setCustomView(R.layout.pop_send_two_coach);
        customDialogConfirm.show();
        View customView = customDialogConfirm.getCustomView();
        showAskConfirmView(customView, customDialogConfirm);
    }

    public static String timeParse(long duration) {
        String time = "";
        long minute = duration / 60000;
        long seconds = duration % 60000;
        long second = Math.round((float) seconds / 1000);
        if (minute < 10) {
            time += "0";
        }
        time += minute + ":";
        if (second < 10) {
            time += "0";
        }
        time += second;
        return time;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ObjectrotationAnim(270 + 360 * 8 + 180);
                break;
            case R.id.tv_send://开始比赛  跳转到团体  造一个假数据 传过去
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Log.e(TAG, "onClick: " + teamArrangesLeft.size());
                Log.e(TAG, "onClick: " + teamArrangesRight.size());
//                Intent intent = new Intent();
//                intent.setClass(this, TeamResultsOfficialActivity.class);
//                intent.putExtra("ids", "" + arrangeIds);
//                intent.putExtra("cliclkPosition", "" + cliclkPosition);
//                startActivity(intent);
//                finish();

                DisplayBeginArrangeBean beginArrangeBean = new DisplayBeginArrangeBean();
                beginArrangeBean.setLeftCount(0);
                beginArrangeBean.setRightCount(0);
                beginArrangeBean.setClub1Name("小亮俱乐部");
                beginArrangeBean.setClub2Name("超级战神队");
                List<DisplayBeginArrangeBean.ChessBean> chessBeanList = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    DisplayBeginArrangeBean.ChessBean chessBean = new DisplayBeginArrangeBean.ChessBean();
                    chessBean.setStatus("0");
                    chessBean.setPlayer1("" + teamArrangesLeft.get(i).getPlayer1Name());
                    chessBean.setPlayer3("" + teamArrangesRight.get(i).getPlayer1Name());
                    chessBean.setPlayer1Set("" + teamArrangesLeft.get(i).getPlayer1Set());
                    chessBean.setPlayer3Set("" + teamArrangesRight.get(i).getPlayer1Set());
                    chessBean.setLeftWavier(0);
                    chessBean.setClub1Name("小亮俱乐部");//邓亚萍 周笔畅 李宇春
                    chessBean.setClub2Name("超级战神队");//吴彦祖  彭于晏  乔杉
                    if (teamArrangesLeft.get(i).getPlayer1Name().equals("吴彦祖")) {
                        chessBean.setHead1Img("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.hao661.com%2Fqq.hao661.com%2Fuploads%2Fallimg%2Fc160128%2F1453a4B243K0-29325.jpg&refer=http%3A%2F%2Fimg.hao661.com&app=2002&siz" +
                                "e=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620446250&t=9c4efa6b44ee982314ce9b771c551294");

                    } else if (teamArrangesLeft.get(i).getPlayer1Name().equals("彭于晏")) {
                        chessBean.setHead1Img("https://img0.baidu.com/it/u=1065954494,2306398903&fm=26&fmt=auto&gp=0.jpg");

                    } else {
                        chessBean.setHead1Img("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.17qq.com%2Fimg_biaoqing%2F63919825.jpeg&refer=http%3A%2F%2Fwww.17qq.com&app=200" +
                                "2&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620446286&t=21e5dbf62ddd054adb759447cffc64d8");
                    }
                    if (teamArrangesRight.get(i).getPlayer1Name().equals("邓亚萍")) {
                        chessBean.setHead2Img("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fnews.cctv.com%2Fsports%2Faoyun%2Fother%2F20" +
                                "071205%2Fimages%2F20071205103621_Img253810452.jpg&refer=http%3A%2F%2Fnews.cctv.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620446147&t=e7faf4470780c406ed4b0faa476a428d");


                    } else if (teamArrangesRight.get(i).getPlayer1Name().equals("周笔畅")) {

                        chessBean.setHead2Img("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.hao661.com%2Fzt.hao661.com%2Fuploads%2Fallimg%2Fc141007%2F1412E14603H30-aI3.jpg&refer=http%3A%2F%2Fimg.hao661.com&app=2002&size=f9999,1000" +
                                "0&q=a80&n=0&g=0n&fmt=jpeg?sec=1620446165&t=6675663d1fe3cd540434b88574fff224");

                    } else {
                        chessBean.setHead2Img("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fweiyinyue.music.sina.com.cn%2Fsonglist_imgs%2F233344_420420.jpg&refer=http%3A%2F%2Fweiyinyue.music.sina.c" +
                                "om.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620446195&t=a58546777170676e13add9209b641bf0");


                    }
                    chessBean.setRightWavier(0);
                    chessBeanList.add(chessBean);
                }
                beginArrangeBean.setChess(chessBeanList);

                Log.e(TAG, "onClick: beginArrangeBean===" + new Gson().toJson(beginArrangeBean));
                Intent intent = new Intent(DisplayDrawForHostAndVistors.this, DisplayTeamResultsOfficialActivity.class);
                intent.putExtra("data", beginArrangeBean);
                startActivity(intent);
                finish();

                break;
            case R.id.ll_left_commit:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                DisplayCoachFillMatchFormActivity.goCoachFormActivity(DisplayDrawForHostAndVistors.this, 1);
                break;
            case R.id.ll_right_commit:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                DisplayCoachFillMatchFormActivity.goCoachFormActivity(DisplayDrawForHostAndVistors.this, 2);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void displayCoach(DisplayCoachMatchFormEvent event) {
        int inputTypeReturn = event.getInputType();
        String jsonStr = event.getJsonStr();
        SubmitTeamArrangeBean submitTeamArrangeBean = new Gson().fromJson(jsonStr, SubmitTeamArrangeBean.class);
        List<SubmitTeamArrangeBean.TeamArrangesBean> teamArranges = submitTeamArrangeBean.getTeamArranges();
        if (inputTypeReturn == 1) {
            //左边的来的数据      private int inputType = 0;//7左边  8 右边 9 全部
            if (inputType == 8) {
                //右边 已经提交了 大兄弟
                teamArrangesLeft.clear();
                teamArrangesLeft.addAll(teamArranges);
                mBinding.tvTopTitle.setText("双方教练员已经提交对阵");
                mBinding.tvCountdownTimer.setText("名单，请开始比赛");
                mBinding.tvLeftCommit.setText("已提交");
                mBinding.tvRightCommit.setText("已提交");
                mBinding.llLeftCommit.setEnabled(false);
                mBinding.llRightCommit.setEnabled(false);
                mBinding.tvSend.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvSend.setEnabled(true);
                lunTimes = 60;
                if (fillMatchTimer != null) {
                    fillMatchTimer.cancel();
                }
            } else {
                teamArrangesLeft.clear();
                inputType = 7;
                mBinding.tvTopTitle.setText("教练员正在填写对阵表");
                mBinding.tvLeftCommit.setText("已提交");
                mBinding.tvRightCommit.setText("等待提交");
                mBinding.llLeftCommit.setEnabled(false);
                mBinding.llRightCommit.setEnabled(true);
                mBinding.tvSend.setEnabled(false);
                teamArrangesLeft.addAll(teamArranges);
            }
        } else if (inputTypeReturn == 2) {
            //右边来的数据
            if (inputType == 7) {
                //左边也提交了大兄弟
                teamArrangesRight.clear();
                teamArrangesRight.addAll(teamArranges);
                mBinding.tvTopTitle.setText("双方教练员已经提交对阵");
                mBinding.tvCountdownTimer.setText("名单，请开始比赛");
                mBinding.tvLeftCommit.setText("已提交");
                mBinding.tvRightCommit.setText("已提交");
                mBinding.llLeftCommit.setEnabled(false);
                mBinding.llRightCommit.setEnabled(false);
                mBinding.tvSend.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvSend.setEnabled(true);
                lunTimes = 60;
                if (fillMatchTimer != null) {
                    fillMatchTimer.cancel();
                }
            } else {
                //左边没提交大兄弟
                teamArrangesRight.clear();
                inputType = 8;
                mBinding.tvTopTitle.setText("教练员正在填写对阵表");
                mBinding.tvLeftCommit.setText("等待提交");
                mBinding.tvRightCommit.setText("已提交");
                mBinding.llLeftCommit.setEnabled(true);
                mBinding.llRightCommit.setEnabled(false);
                mBinding.tvSend.setEnabled(false);
                teamArrangesRight.addAll(teamArranges);
            }
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        lunTimes = 60;
        Log.e(TAG, "onBackPressed: ");
        if (fillMatchTimer != null) {

            fillMatchTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        lunTimes = 60;
        if (fillMatchTimer != null) {
            fillMatchTimer.cancel();
        }
    }
}
