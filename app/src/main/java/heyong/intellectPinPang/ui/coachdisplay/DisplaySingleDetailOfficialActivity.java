package heyong.intellectPinPang.ui.coachdisplay;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.xq.fasterdialog.dialog.CustomDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.display.DisplaySinglesDetailOfficialAdapter;
import heyong.intellectPinPang.bean.competition.DisplayBeginArrangeBean;
import heyong.intellectPinPang.databinding.ActivitySinglesDetailOfficialBinding;
import heyong.intellectPinPang.event.BeginArrangeDisplayChouEvent;
import heyong.intellectPinPang.event.BeginArrangeDisplayEvent;
import heyong.handong.framework.utils.ToastUtils;
import heyong.intellectPinPang.utils.AntiShakeUtils;

/**
 * 展示单打的 地方  录入 输入成绩
 */
public class DisplaySingleDetailOfficialActivity extends BaseActivity<ActivitySinglesDetailOfficialBinding, BaseViewModel> implements View.OnClickListener {
    private DisplaySinglesDetailOfficialAdapter singlesDetailAdapter;
    private int matchCount = 2;
    private boolean isChange = false;
    DisplayBeginArrangeBean data1;
    private int leftWaverType = -1;
    private int rightWaverType = -1;
    private int leftScore = -1;
    private int rightScore = -1;

    CountDownTimer timer;
    CustomDialog customDialogConfirm;
    String leftName;
    String rightName;
    private int pauseLeftType = -1;
    private int pauseRightType = -1;

    private int leftAllWinCount;
    private int rightAllWinCount;

    private int clickPosition = 0;
    private String chouqian = "";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_singles_detail_official;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        singlesDetailAdapter = new DisplaySinglesDetailOfficialAdapter(true);
        mBinding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvList.setAdapter(singlesDetailAdapter);
        data1 = (DisplayBeginArrangeBean) getIntent().getSerializableExtra("data");
        clickPosition = getIntent().getIntExtra("clickPosition", 1);
        chouqian = getIntent().getStringExtra("chouqian");

        ImageLoader.loadImage(mBinding.viewLeftOval, data1.getHeadImg1(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
        ImageLoader.loadImage(mBinding.viewRightOval, data1.getHeadImg3(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
        mBinding.tvLeftName.setText("" + data1.getPlayer1());
        mBinding.tvRightName.setText("" + data1.getPlayer3());
        mBinding.tvLeftClubName.setText("" + data1.getClub1Name());
        mBinding.tvRightClubName.setText("" + data1.getClub2Name());

        switch (data1.getMethond()) {
            case "1":
                matchCount = 2;
                break;
            case "2":
                matchCount = 3;
                break;
            case "3":
                matchCount = 4;
                break;
        }


        int leftSuspend = data1.getLeftSupend();
        if (leftSuspend == 1) {

            mBinding.tvLeftPause.setBackgroundColor(Color.parseColor("#CCCCCC"));
            mBinding.tvLeftPause.setEnabled(false);
        } else {
            mBinding.tvLeftPause.setBackgroundColor(Color.parseColor("#FFCD66"));
            mBinding.tvLeftPause.setEnabled(true);
        }
        int rightSuspend = data1.getRightSupend();
        if (rightSuspend == 1) {
            mBinding.tvRightPause.setBackgroundColor(Color.parseColor("#CCCCCC"));
            mBinding.tvRightPause.setEnabled(false);
        } else {
            mBinding.tvRightPause.setBackgroundColor(Color.parseColor("#FFCD66"));
            mBinding.tvRightPause.setEnabled(true);
        }
        showData();


        singlesDetailAdapter.setMyListener(new DisplaySinglesDetailOfficialAdapter.onMyListener() {
            @Override
            public void OnMyListener(int scoreOne, int scoreTwo,
                                     DisplayBeginArrangeBean.ChessBean item,
                                     int position, String textStatus, boolean isFirst) {
                if ((scoreOne >= 11 && scoreTwo >= 11) || (scoreOne > 11 || scoreTwo > 11)) {
                    /*两边都大于11  需要比分判断*/
                    if (scoreOne - scoreTwo > 2 || scoreTwo - scoreOne > 2) {
                        ToastUtils.showToast(DisplaySingleDetailOfficialActivity.this, "比分填写不规范，请重新填写");

                    } else {
                        notifyData(scoreOne, scoreTwo, item, position, textStatus, isFirst);
                    }
                } else {
                    /*一边大于11  需要判断比分  如果是11*/
                    if ((scoreOne >= 11 && scoreTwo < 11) || (scoreTwo >= 11 && scoreOne < 11)) {
                        /*11 和10 输入不行   11和9 输入没问题 11 和0 没问题  11和4 没问题*/
                        if (scoreOne - scoreTwo >= 2 || (scoreTwo - scoreOne >= 2)) {
                            notifyData(scoreOne, scoreTwo, item, position, textStatus, isFirst);
                        } else {
                            ToastUtils.showToast(DisplaySingleDetailOfficialActivity.this, "比分填写不规范，请重新填写");

                        }
                    }
                }
            }


        });


    }

    private void showData() {
        List<DisplayBeginArrangeBean.ChessBean> xlClubContestArrangeChesses = data1.getChess();
        if (xlClubContestArrangeChesses != null && xlClubContestArrangeChesses.size() > 0) {
            singlesDetailAdapter.setNewData(xlClubContestArrangeChesses);
        } else {
            singlesDetailAdapter.setNewData(new ArrayList<>());
        }
        int leftWinCount = 0;
        int rightWinCount = 0;
        for (int i = 0; i < xlClubContestArrangeChesses.size(); i++) {
            DisplayBeginArrangeBean.ChessBean data = xlClubContestArrangeChesses.get(i);
            if (!TextUtils.isEmpty(data.getLeftCount()) && !TextUtils.isEmpty(data.getRightCount())) {
                if (Integer.parseInt(data.getLeftCount()) > Integer.parseInt(data.getRightCount())) {
                    leftWinCount = leftWinCount + 1;
                } else if (Integer.parseInt(data.getLeftCount()) < Integer.parseInt(data.getRightCount())) {
                    rightWinCount = rightWinCount + 1;
                }
            }
        }

        int emptyFirstPosition = -1;
        boolean isFirst = true;
        for (int m = 0; m < xlClubContestArrangeChesses.size(); m++) {
            DisplayBeginArrangeBean.ChessBean xlClubContestArrangeChessesBean = xlClubContestArrangeChesses.get(m);
            if (!TextUtils.isEmpty(xlClubContestArrangeChessesBean.getLeftCount()) || !TextUtils.isEmpty(xlClubContestArrangeChessesBean.getRightCount())) {
                xlClubContestArrangeChessesBean.setTextSubmit(false);
                xlClubContestArrangeChessesBean.setFirst(false);
                isChange = true;
            } else {
                if (isFirst) {
                    emptyFirstPosition = m;
                    isFirst = false;
                    Log.e(TAG, "onChanged: " + emptyFirstPosition);
                }
                xlClubContestArrangeChessesBean.setTextSubmit(true);
                xlClubContestArrangeChessesBean.setFirst(true);
            }
            xlClubContestArrangeChesses.set(m, xlClubContestArrangeChessesBean);

        }
        if (emptyFirstPosition != -1) {
            for (int n = 0; n < xlClubContestArrangeChesses.size(); n++) {
                DisplayBeginArrangeBean.ChessBean xlClubContestArrangeChessesBean2 = xlClubContestArrangeChesses.get(n);
                if (emptyFirstPosition == n) {
                    xlClubContestArrangeChessesBean2.setFirst(true);
                } else {
                    xlClubContestArrangeChessesBean2.setFirst(false);
                }
                xlClubContestArrangeChesses.set(n, xlClubContestArrangeChessesBean2);
            }
        }

        if (xlClubContestArrangeChesses != null && xlClubContestArrangeChesses.size() > 0) {

            if (leftWinCount == matchCount || rightWinCount == matchCount) {
                for (int i = matchCount; i < xlClubContestArrangeChesses.size(); i++) {
                    DisplayBeginArrangeBean.ChessBean chessBean = xlClubContestArrangeChesses.get(i);
                    chessBean.setFirst(false);
                    xlClubContestArrangeChesses.set(i, chessBean);
                    Log.e(TAG, "onChanged: position==" + i);
                    singlesDetailAdapter.setNewData(xlClubContestArrangeChesses);
                }
            } else {

                singlesDetailAdapter.setNewData(xlClubContestArrangeChesses);
            }
        } else {
            singlesDetailAdapter.setNewData(new ArrayList<>());
        }

        mBinding.tvLeftWinCount.setTextColor(Color.parseColor("#333333"));
        mBinding.tvRightWinCount.setTextColor(Color.parseColor("#333333"));
        if (leftWinCount > rightWinCount)//大于是红色 的 其他的是黑色
        {
            mBinding.tvLeftWinCount.setTextColor(Color.parseColor("#EB2F2F"));
            mBinding.tvRightWinCount.setTextColor(Color.parseColor("#333333"));
        }
        if (rightWinCount > leftWinCount) {
            mBinding.tvRightWinCount.setTextColor(Color.parseColor("#EB2F2F"));
            mBinding.tvLeftWinCount.setTextColor(Color.parseColor("#333333"));
        }
        mBinding.tvLeftWinCount.setText("" + leftWinCount);
        mBinding.tvRightWinCount.setText("" + rightWinCount);
    }

    long EndId;
    private int updatePosition = 0;
    DisplayBeginArrangeBean.ChessBean myItem;
    private int EndPosition = -1;

    private void notifyData(int scoreOne, int scoreTwo, DisplayBeginArrangeBean.ChessBean item, int position, String textStatus, boolean isFirst) {
        myItem = item;
        EndPosition = position;
        Log.e(TAG, "notifyData: textStatus===" + textStatus);
        switch (textStatus) {
            case "修改":

                List<DisplayBeginArrangeBean.ChessBean> data = singlesDetailAdapter.getData();
                DisplayBeginArrangeBean.ChessBean xlClubContestArrangeChessesBean = data.get(position);
                xlClubContestArrangeChessesBean.setTextSubmit(true);
                xlClubContestArrangeChessesBean.setFirst(true);
                data.set(position, xlClubContestArrangeChessesBean);
                singlesDetailAdapter.setNewData(data);
                break;
            case "确定":
                updatePosition = position;
                showOneConfirm(scoreOne, scoreTwo, item, position, textStatus, isFirst);

                break;
        }

    }

    /*点击确定和修改比分弹窗*/
    private void showOneConfirm(int score1, int score2,
                                DisplayBeginArrangeBean.ChessBean item,
                                int position, String textStatus, boolean isFirst) {
        CustomDialog customDialogConfirm = new CustomDialog(DisplaySingleDetailOfficialActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_bureau_scores_single);
        customDialogConfirm.show();
        View customView = customDialogConfirm.getCustomView();
        TextView tvSure = customView.findViewById(R.id.tv_sure);
//        TextView tvScore = customView.findViewById(R.id.tv_score);
        ImageView ivLeftLogo = customView.findViewById(R.id.iv_left_logo);
        ImageView ivRightLogo = customView.findViewById(R.id.iv_right_logo);
        ImageLoader.loadImage(ivLeftLogo, data1.getHeadImg1());
        ImageLoader.loadImage(ivRightLogo, data1.getHeadImg3());

        TextView tvleftScore = customView.findViewById(R.id.tv_left_score);
        TextView tvRightScore = customView.findViewById(R.id.tv_right_score);
        TextView tvLeftName = customView.findViewById(R.id.tv_left_name);
        TextView tvRightName = customView.findViewById(R.id.tv_right_name);
        tvLeftName.setText("" + data1.getPlayer1());
        tvRightName.setText("" + data1.getPlayer3());
        tvleftScore.setTextColor(Color.parseColor("#333333"));
        tvRightScore.setTextColor(Color.parseColor("#333333"));
        if (score1 > score2) {
            tvleftScore.setTextColor(Color.parseColor("#EB4430"));
        }
        if (score1 < score2) {
            tvRightScore.setTextColor(Color.parseColor("#EB4430"));
        }
        tvleftScore.setText("" + score1);
        tvRightScore.setText("" + score2);
        item.setCanChange(false);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customDialogConfirm != null) {
                    customDialogConfirm.dismiss();
                    leftScore = score1;
                    rightScore = score2;
                    EndId = item.getId();
                    EndPosition = position;

                    if (isChange) {
                        toast("修改成功");
                    } else {
                        toast("操作成功");
                    }

                    /*調用接口*/
                    List<DisplayBeginArrangeBean.ChessBean> data2 = singlesDetailAdapter.getData();
                    DisplayBeginArrangeBean.ChessBean xlClubContestArrangeChessesBean2 = data2.get(EndPosition);
                    xlClubContestArrangeChessesBean2.setTextSubmit(false);
                    xlClubContestArrangeChessesBean2.setFirst(false);
                    xlClubContestArrangeChessesBean2.setLeftCount("" + leftScore);
                    xlClubContestArrangeChessesBean2.setRightCount("" + rightScore);
                    data2.set(EndPosition, xlClubContestArrangeChessesBean2);
                    Log.e(TAG, "onClick: data===" + new Gson().toJson(data2));
                    Log.e(TAG, "notifyData: 确定");


                    int leftWinCount = 0;
                    int rightWinCount = 0;
                    for (int i = 0; i < data2.size(); i++) {
                        DisplayBeginArrangeBean.ChessBean data = data2.get(i);
                        if (!TextUtils.isEmpty(data.getLeftCount()) && !TextUtils.isEmpty(data.getRightCount())) {
                            if (Integer.parseInt(data.getLeftCount()) > Integer.parseInt(data.getRightCount())) {
                                leftWinCount = leftWinCount + 1;
                            } else if (Integer.parseInt(data.getLeftCount()) < Integer.parseInt(data.getRightCount())) {
                                rightWinCount = rightWinCount + 1;
                            }
                        }
                    }
                    mBinding.tvLeftWinCount.setTextColor(Color.parseColor("#333333"));
                    mBinding.tvRightWinCount.setTextColor(Color.parseColor("#333333"));
                    if (leftWinCount > rightWinCount)//大于是红色 的 其他的是黑色
                    {
                        mBinding.tvLeftWinCount.setTextColor(Color.parseColor("#EB2F2F"));
                        mBinding.tvRightWinCount.setTextColor(Color.parseColor("#333333"));
                    }
                    if (rightWinCount > leftWinCount) {
                        mBinding.tvRightWinCount.setTextColor(Color.parseColor("#EB2F2F"));
                        mBinding.tvLeftWinCount.setTextColor(Color.parseColor("#333333"));
                    }
                    mBinding.tvLeftWinCount.setText("" + leftWinCount);
                    mBinding.tvRightWinCount.setText("" + rightWinCount);

                    for (int m = 0; m < data2.size(); m++) {
                        DisplayBeginArrangeBean.ChessBean xlClubContestArrangeChessesBean = data2.get(m);
                        if (!TextUtils.isEmpty(xlClubContestArrangeChessesBean.getLeftCount()) || !TextUtils.isEmpty(xlClubContestArrangeChessesBean.getRightCount())) {
                            xlClubContestArrangeChessesBean.setTextSubmit(false);
                            xlClubContestArrangeChessesBean.setFirst(false);
                        } else {
                            xlClubContestArrangeChessesBean.setTextSubmit(true);
                            xlClubContestArrangeChessesBean.setFirst(true);
                        }
                        data2.set(m, xlClubContestArrangeChessesBean);
                    }
                    if (leftWinCount == matchCount || rightWinCount == matchCount) {

                    } else {
                        if (updatePosition != data2.size() - 1) {
                            for (int i = updatePosition; i < data2.size(); i++) {
                                DisplayBeginArrangeBean.ChessBean xlClubContestArrangeChessesBeanmn = data2.get(i);
                                if (i == updatePosition + 1) {
                                    //判断是否相等
                                    xlClubContestArrangeChessesBeanmn.setFirst(true);
                                    data2.set(i, xlClubContestArrangeChessesBeanmn);
                                } else {
                                    //判断是否相等
                                    xlClubContestArrangeChessesBeanmn.setFirst(false);
                                    data2.set(i, xlClubContestArrangeChessesBeanmn);
                                }
                            }
                        }
                    }


                    if (data2 != null && data2.size() > 0) {
                        if (leftWinCount == matchCount || rightWinCount == matchCount) {
                            for (int i = matchCount; i < data2.size(); i++) {
                                DisplayBeginArrangeBean.ChessBean xlClubContestArrangeChessesBean = data2.get(i);
                                xlClubContestArrangeChessesBean.setFirst(false);
                                data2.set(i, xlClubContestArrangeChessesBean);
                                Log.e(TAG, "onChanged: position==" + i);
                                singlesDetailAdapter.setNewData(data2);
                            }
                            showOneConfirmExit(leftWinCount, rightWinCount);
                        } else {
                            singlesDetailAdapter.setNewData(data2);
                        }
                    } else {
                        singlesDetailAdapter.setNewData(new ArrayList<>());
                    }


                }
            }
        });
        TextView tvCancel = customView.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customDialogConfirm != null) {
                    customDialogConfirm.dismiss();
                }

            }
        });
    }

    /*总的结束比赛的方法*/
    private void showOneConfirmExit(int leftAllCount, int rightAllCount) {
        CustomDialog customDialogConfirm = new CustomDialog(DisplaySingleDetailOfficialActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_end_game_single_official);
        customDialogConfirm.show();
        View customView = customDialogConfirm.getCustomView();
        /*先判断总体Count 两边那个等于Count*/
        TextView tvLeftName = customView.findViewById(R.id.tv_left_name);
        TextView tvRightName = customView.findViewById(R.id.tv_right_name);
        TextView tvLeftWinner = customView.findViewById(R.id.tv_left_winner);
        TextView tvRightWinner = customView.findViewById(R.id.tv_right_winner);
//        TextView tvScore = customView.findViewById(R.id.tv_score);

        CircleImageView imgLeftLogo = customView.findViewById(R.id.img_left_logo);
        CircleImageView imgRightLogo = customView.findViewById(R.id.img_right_logo);
        ImageLoader.loadImage(imgLeftLogo, data1.getHeadImg1());
        ImageLoader.loadImage(imgRightLogo, data1.getHeadImg3());
        leftName = data1.getPlayer1();
        rightName = data1.getPlayer3();
        tvLeftName.setText("" + leftName);
        tvRightName.setText("" + rightName);

//
//        if (leftWaverType == 1 && rightWaverType != 1) {
//            /*左边弃权*/
//            tvScore.setText("W-" + leftAllCount + ":" + rightAllCount);
//        } else if (leftWaverType == 1 && rightWaverType == 1) {
//            /*两边都弃权*/
//
//            tvScore.setText("W-" + leftAllCount + ":W-" + rightAllCount);
//        } else if (leftWaverType != 1 && rightWaverType == 1) {
//            tvScore.setText("" + leftAllCount + ":W-" + rightAllCount);
//            /*右边弃权*/
//        } else {
//        tvScore.setText("" + leftAllCount + ":" + rightAllCount);
        /*两边都没弃权*/
        TextView tvleftScore = customView.findViewById(R.id.tv_left_score);
        TextView tvRightScore = customView.findViewById(R.id.tv_right_score);
        tvleftScore.setTextColor(Color.parseColor("#333333"));
        tvRightScore.setTextColor(Color.parseColor("#333333"));
        if (leftAllCount > rightAllCount) {
            tvleftScore.setTextColor(Color.parseColor("#EB4430"));
        }
        if (leftAllCount < rightAllCount) {
            tvRightScore.setTextColor(Color.parseColor("#EB4430"));
        }
        tvleftScore.setText("" + leftAllCount);
        tvRightScore.setText("" + rightAllCount);

//        }
        if (leftWaverType == 1 && rightWaverType == 1) {
            tvLeftWinner.setVisibility(View.GONE);
            tvRightWinner.setVisibility(View.GONE);
        } else {
            if (leftAllCount > rightAllCount) {
                tvLeftWinner.setText("胜");
                tvLeftWinner.setTextColor(Color.parseColor("#fffb312f"));
                tvRightWinner.setTextColor(Color.parseColor("#ff4795ed"));
                tvRightWinner.setText("负");
            } else {
                tvLeftWinner.setText("负");
                tvLeftWinner.setTextColor(Color.parseColor("#ff4795ed"));
                tvRightWinner.setTextColor(Color.parseColor("#fffb312f"));
                tvRightWinner.setText("胜");
            }
        }


        TextView tvSure = customView.findViewById(R.id.tv_sure);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customDialogConfirm != null) {
                    customDialogConfirm.dismiss();


                    if (leftWaverType == 1 && rightWaverType == 1) {
                        leftAllWinCount = 0;
                        rightAllWinCount = 0;
                        List<DisplayBeginArrangeBean.ChessBean> data = singlesDetailAdapter.getData();
                        data1.setChess(data);
                        data1.setLeftCount(leftAllWinCount);
                        data1.setRightCount(rightAllWinCount);
                        String compareDatas = new Gson().toJson(data1);
                        EventBus.getDefault().post(new BeginArrangeDisplayEvent(compareDatas, clickPosition));
                        finish();

                    } else {
                        leftAllWinCount = leftAllCount;
                        rightAllWinCount = rightAllCount;
                        if (leftAllCount > rightAllCount) {
                            List<DisplayBeginArrangeBean.ChessBean> data = singlesDetailAdapter.getData();
                            data1.setChess(data);
                            data1.setLeftCount(leftAllWinCount);
                            data1.setRightCount(rightAllWinCount);
                            String compareDatas = new Gson().toJson(data1);
                            if (chouqian == null) {
                                chouqian = "";
                            }
                            if (chouqian.equals("chouqian")) {
                                EventBus.getDefault().post(new BeginArrangeDisplayChouEvent(compareDatas, clickPosition));

                            } else {
                                EventBus.getDefault().post(new BeginArrangeDisplayEvent(compareDatas, clickPosition));
                            }
                            finish();
                        } else if (leftAllCount < rightAllCount) {
                            List<DisplayBeginArrangeBean.ChessBean> data = singlesDetailAdapter.getData();
                            data1.setChess(data);
                            data1.setLeftCount(leftAllWinCount);
                            data1.setRightCount(rightAllWinCount);
                            String compareDatas = new Gson().toJson(data1);
                            if (chouqian == null) {
                                chouqian = "";
                            }
                            if (chouqian.equals("chouqian")) {
                                EventBus.getDefault().post(new BeginArrangeDisplayChouEvent(compareDatas, clickPosition));
                            } else {
                                EventBus.getDefault().post(new BeginArrangeDisplayEvent(compareDatas, clickPosition));
                            }
                            finish();
                        }
                    }


                }
            }
        });
        TextView tvCancel = customView.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customDialogConfirm != null) {
                    customDialogConfirm.dismiss();
                }

            }
        });

    }

    /*暂停*/
    private void showOneKeepGame() {
        customDialogConfirm = new CustomDialog(DisplaySingleDetailOfficialActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_count_down);
        customDialogConfirm.show();
        customDialogConfirm.setDisconViewOnDismiss(true);
        customDialogConfirm.setCancelable(false);
        customDialogConfirm.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

                    return true;

                }
                if (customDialogConfirm.isShowing()) {
                    return true;
                }
                return false;
            }
        });
        customDialogConfirm.setCanceledOnTouchOutside(true);
        View customView = customDialogConfirm.getCustomView();
        TextView tvSure = customView.findViewById(R.id.tv_cancel);
        TextView tvDownTime = customView.findViewById(R.id.tv_down_time);
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvDownTime.setText("" + millisUntilFinished / 1000 + "S");
                tvSure.setEnabled(false);
                tvSure.setBackgroundResource(R.drawable.shape_club_gray);

            }

            @Override
            public void onFinish() {
                tvSure.setText("继续比赛");
                tvDownTime.setVisibility(View.GONE);
                tvSure.setEnabled(true);
                tvSure.setBackgroundResource(R.drawable.shape_club_blue);
            }
        }.start();

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customDialogConfirm != null) {
                    customDialogConfirm.dismiss();
                    /*修改弃权的状态*/
                    /*掉接口*/
                    if (pauseLeftType == 1) {
                        mBinding.tvLeftPause.setBackgroundColor(Color.parseColor("#CCCCCC"));
                        mBinding.tvLeftPause.setEnabled(false);
                    }
                    if (pauseRightType == 1) {
                        mBinding.tvRightPause.setBackgroundColor(Color.parseColor("#CCCCCC"));
                        mBinding.tvRightPause.setEnabled(false);
                    }
                    if (timer != null) {
                        timer.cancel();
                    }
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm_grade_end://结束比赛 掉接口
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                leftName = data1.getPlayer1();
                rightName = data1.getPlayer3();
                if (leftWaverType == 1 && rightWaverType == 1) {
                    showOneConfirmExit(0, 0);
                } else {

                    /*比赛未决出胜负*/
                    int leftAllCount = Integer.parseInt(mBinding.tvLeftWinCount.getText().toString());
                    int rightAllCount = Integer.parseInt(mBinding.tvRightWinCount.getText().toString());
                    if (leftWaverType == 1 || rightWaverType == 1) {
                        showOneConfirmExit(leftAllCount, rightAllCount);
                    } else {
                        if (leftAllCount >= matchCount || rightAllCount >= matchCount) {
                            showOneConfirmExit(leftAllCount, rightAllCount);
                        } else {
                            toast("比赛未决出胜负");
                        }
                    }

                }
                break;
            case R.id.tv_left_pause://左边的暂停按钮
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (data1 != null) {
                    pauseLeftType = 1;
                    showOneKeepGame();
                    data1.setLeftSupend(1);
//                    mViewModel.coachSuspend("" + data1.getArrangeId(), "left");
                } else {
                    toast("页面异常");
                }
                break;
            case R.id.tv_right_pause://右边的暂停按钮
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (data1 != null) {
                    pauseRightType = 1;
                    showOneKeepGame();
                    data1.setRightSupend(1);
//                    mViewModel.coachSuspend("" + data1.getArrangeId(), "right");
                } else {
                    toast("页面异常");
                }
                break;
            case R.id.tv_left_waiver://左边的弃权按钮
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (data1 != null) {
                    leftWaverType = 1;
                    data1.setLeftWaiver(1);
//                    mBinding.tvLeftWaiver.setEnabled(false);
//                    mBinding.tvLeftWaiver.setBackgroundResource(R.drawable.img_left_gray_rect);
                    //修改数据
                    List<DisplayBeginArrangeBean.ChessBean> chess = data1.getChess();
                    //所有对局
                    int matchCount = 0;
                    for (int m = 0; m < chess.size(); m++) {
                        String leftCount = chess.get(m).getLeftCount();
                        String rightCount = chess.get(m).getRightCount();
                        if (!TextUtils.isEmpty(leftCount) || !TextUtils.isEmpty(rightCount)) {
                            matchCount++;
                        }
                    }

                    if (matchCount == 0) {
                        //一句没打
                        DisplayBeginArrangeBean.ChessBean chessBean = chess.get(0);
                        DisplayBeginArrangeBean.ChessBean chessBean1 = chess.get(1);
                        if (rightWaverType == 1) {
                            chessBean.setLeftCount("0");
                            chessBean.setRightCount("0");
                            chessBean1.setLeftCount("0");
                            chessBean1.setRightCount("0");
                        } else {
                            chessBean.setLeftCount("0");
                            chessBean.setRightCount("11");
                            chessBean1.setLeftCount("0");
                            chessBean1.setRightCount("11");
                        }
                        chess.set(0, chessBean);
                        chess.set(1, chessBean1);
                    } else if (matchCount == 1) {
                        //打了一句
                        DisplayBeginArrangeBean.ChessBean chessBean = chess.get(0);
                        DisplayBeginArrangeBean.ChessBean chessBean1 = chess.get(1);
                        DisplayBeginArrangeBean.ChessBean chessBean2 = chess.get(2);
                        int leftCount = Integer.parseInt(chessBean.getLeftCount());
                        int rightCount = Integer.parseInt(chessBean.getRightCount());
                        //判断是左边赢还是右边赢 如果是左边赢 放一个  如果是右边赢  放两个
                        if (leftCount > rightCount) {
                            //左边弃权放俩
                            chessBean1.setLeftCount("0");
                            chessBean1.setRightCount("11");
                            chessBean2.setLeftCount("0");
                            chessBean2.setRightCount("11");
                        } else if (leftCount < rightCount) {
                            //左边弃权放一个
                            chessBean1.setLeftCount("0");
                            chessBean1.setLeftCount("11");
                        }
                        chess.set(0, chessBean);
                        chess.set(1, chessBean1);
                        chess.set(2, chessBean2);
                    } else {
                        //打了两局了  无法弃权
                        toast("无法弃权 比赛已经出胜负");
                    }
                    data1.setLeftWaiver(1);
                    data1.setChess(chess);
                    showData();

                }
                break;

            case R.id.tv_right_wavier://右边的弃权按钮
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (data1 != null) {
                    rightWaverType = 1;
                    data1.setRightWaiver(1);
//                    mBinding.tvRightWavier.setEnabled(false);
//                    mBinding.tvRightWavier.setBackgroundResource(R.drawable.img_right_gray_rect);

                    //修改数据
                    List<DisplayBeginArrangeBean.ChessBean> chess = data1.getChess();
                    //所有对局
                    int matchCount = 0;
                    for (int m = 0; m < chess.size(); m++) {
                        String leftCount = chess.get(m).getLeftCount();
                        String rightCount = chess.get(m).getRightCount();
                        if (!TextUtils.isEmpty(leftCount) || !TextUtils.isEmpty(rightCount)) {
                            matchCount++;
                        }
                    }

                    if (matchCount == 0) {
                        //一句没打
                        DisplayBeginArrangeBean.ChessBean chessBean = chess.get(0);
                        DisplayBeginArrangeBean.ChessBean chessBean1 = chess.get(1);
                        if (leftWaverType == 1) {
                            chessBean.setLeftCount("0");
                            chessBean.setRightCount("0");
                            chessBean1.setLeftCount("0");
                            chessBean1.setRightCount("0");
                        } else {
                            chessBean.setLeftCount("11");
                            chessBean.setRightCount("0");
                            chessBean1.setLeftCount("11");
                            chessBean1.setRightCount("0");
                        }
                        chess.set(0, chessBean);
                        chess.set(1, chessBean1);
                    } else if (matchCount == 1) {
                        //打了一句
                        DisplayBeginArrangeBean.ChessBean chessBean = chess.get(0);
                        DisplayBeginArrangeBean.ChessBean chessBean1 = chess.get(1);
                        DisplayBeginArrangeBean.ChessBean chessBean2 = chess.get(2);
                        int leftCount = Integer.parseInt(chessBean.getLeftCount());
                        int rightCount = Integer.parseInt(chessBean.getRightCount());
                        //判断是左边赢还是右边赢 如果是左边赢 放一个  如果是右边赢  放两个
                        if (leftCount > rightCount) {
                            //左边弃权放俩
                            chessBean1.setLeftCount("11");
                            chessBean1.setLeftCount("0");
                        } else if (leftCount < rightCount) {
                            //左边弃权放一个
                            chessBean1.setLeftCount("11");
                            chessBean1.setRightCount("0");
                            chessBean2.setLeftCount("11");
                            chessBean2.setRightCount("0");
                        }
                        chess.set(0, chessBean);
                        chess.set(1, chessBean1);
                        chess.set(2, chessBean2);
                    } else {
                        //打了两局了  无法弃权
                        toast("无法弃权 比赛已经出胜负");
                    }
                    data1.setRightWaiver(1);
                    data1.setChess(chess);
                    showData();
                }
                break;
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            //当isShouldHideInput(v, ev)为true时，表示的是点击输入框区域，则需要显示键盘，同时显示光标，反之，需要隐藏键盘、光标
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //处理Editext的光标隐藏、显示逻辑
                    v.clearFocus();
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
