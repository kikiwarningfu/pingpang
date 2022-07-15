package heyong.intellectPinPang.ui.competition.activity;

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
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.xq.fasterdialog.dialog.CustomDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.SinglesDetailOfficialAdapter;
import heyong.intellectPinPang.bean.competition.LoginIdentifyBean;
import heyong.intellectPinPang.bean.competition.BeginArrangeBean;
import heyong.intellectPinPang.databinding.ActivitySinglesDetailOfficialBinding;
import heyong.intellectPinPang.event.SwipMatchEvent;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.handong.framework.utils.ToastUtils;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.utils.SoftKeyBoardListener;

/**
 * 单打比赛输入
 * 单打比赛正式成绩  需要判断教练员身份 如果是裁判员 只能再次进入修改一次
 * 如果是裁判长可以进入多次更改
 * 正式 单打可输入成绩
 */
public class SinglesDetailOfficialActivity extends BaseActivity<ActivitySinglesDetailOfficialBinding, ClubViewModel> implements View.OnClickListener {
    public static final String CLICK_TYPE = "clickType";
    private SinglesDetailOfficialAdapter singlesDetailAdapter;
    private int pauseType = -1;

    public static final String CODE_TYPE = "code_type";
    public static final String ITEM_SHOW = "item_show_give_up";
    private String strCodeType = "";
    private String strShowGivenUp = "";
    private int matchCount = -1;

    private int leftWaverType = -1;
    private int rightWaverType = -1;

    CountDownTimer timer;
    CustomDialog customDialogConfirm;
    String leftName;
    String rightName;
    BeginArrangeBean data1;

    private int EndPosition = -1;
    private boolean isSuccess = false;
    BeginArrangeBean.ChessBean myItem;
    private int leftScore = -1;
    private int rightScore = -1;
    public static final String TAG = SinglesDetailActivity.class.getSimpleName();
    private String clickPosition = "";

    private String ids = "";
    private int leftAllWinCount;
    private int rightAllWinCount;
    private boolean isChange = false;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_singles_detail_official;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        ids = getIntent().getStringExtra("ids");
        clickPosition = getIntent().getStringExtra("cliclkPosition");
        strCodeType = getIntent().getStringExtra(CODE_TYPE);
        strShowGivenUp = getIntent().getStringExtra(ITEM_SHOW);
        if (strShowGivenUp == null) {
            strShowGivenUp = "";
        }
        if (strCodeType == null) {
            strCodeType = "";
        }
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

                mBinding.rvList.scrollToPosition(singlesDetailAdapter.getData().size()-1);
            }

            @Override
            public void keyBoardHide(int height) {

            }
        });
        Log.e(TAG, "initView: clickPosition===" + clickPosition);
        getData();

        mViewModel.beginArrangeLiveData.observe(this, new Observer<ResponseBean<BeginArrangeBean>>() {
            @Override
            public void onChanged(ResponseBean<BeginArrangeBean> beginArrangeBeanResponseBean) {
                dismissLoading();
                mViewModel.getLoginInentify("" + ids);
                if (beginArrangeBeanResponseBean.getErrorCode().equals("200")) {
                    data1 = beginArrangeBeanResponseBean.getData();
                    try {
                        ImageLoader.loadImage(mBinding.viewLeftOval, data1.getHeadImg1(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                        ImageLoader.loadImage(mBinding.viewRightOval, data1.getHeadImg3(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);

                    }catch (Exception e)
                    {

                    }
                    mBinding.tvLeftName.setText("" + data1.getPlayer1());
                    mBinding.tvRightName.setText("" + data1.getPlayer3());
                    mBinding.tvLeftClubName.setText("" + data1.getClub1Name());
                    mBinding.tvRightClubName.setText("" + data1.getClub2Name());
////                private int gameType = -1;//1 是三局两胜  2是五局三胜  3 是七局四胜
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
//                    if (strShowGivenUp.equals("false")) {
//                        mBinding.tvLeftWaiver.setVisibility(View.GONE);
//                        mBinding.tvRightWavier.setVisibility(View.GONE);
//                    } else {
//                        int leftWavier = data1.getLeftWaiver();
//                        if (leftWavier == 1) {
//                            leftWaverType = 1;
//                            mBinding.tvLeftWaiver.setEnabled(false);
//                            mBinding.tvLeftWaiver.setBackgroundResource(R.drawable.img_left_gray_rect);
//                        }
//                        int rightWavier = data1.getRightWaiver();
//                        if (rightWavier == 1) {
//                            rightWaverType = 1;
//                            mBinding.tvRightWavier.setEnabled(false);
//                            mBinding.tvRightWavier.setBackgroundResource(R.drawable.img_right_gray_rect);
//                        }
//                    }
                    int leftSuspend = data1.getLeftSupend();
                    if (leftSuspend == 1) {

                        mBinding.tvLeftPause.setBackgroundColor(Color.parseColor("#CCCCCC"));
                        mBinding.tvLeftPause.setEnabled(false);
                    } else {
                        mBinding.tvLeftPause.setBackgroundColor(Color.parseColor("#FFCD66"));
                        mBinding.tvLeftPause.setEnabled(true);
                    }
                    int rightSuspend = data1.getLeftSupend();
                    if (rightSuspend == 1) {
                        mBinding.tvRightPause.setBackgroundColor(Color.parseColor("#CCCCCC"));
                        mBinding.tvRightPause.setEnabled(false);
                    } else {
                        mBinding.tvRightPause.setBackgroundColor(Color.parseColor("#FFCD66"));
                        mBinding.tvRightPause.setEnabled(true);
                    }

                    List<BeginArrangeBean.ChessBean> xlClubContestArrangeChesses = data1.getChess();
                    if (xlClubContestArrangeChesses != null && xlClubContestArrangeChesses.size() > 0) {
                        singlesDetailAdapter.setNewData(xlClubContestArrangeChesses);
                    } else {
                        singlesDetailAdapter.setNewData(new ArrayList<>());
                    }
                    int leftWinCount = 0;
                    int rightWinCount = 0;
                    for (int i = 0; i < xlClubContestArrangeChesses.size(); i++) {
                        BeginArrangeBean.ChessBean data = xlClubContestArrangeChesses.get(i);
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
                        BeginArrangeBean.ChessBean xlClubContestArrangeChessesBean = xlClubContestArrangeChesses.get(m);
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
                            BeginArrangeBean.ChessBean xlClubContestArrangeChessesBean2 = xlClubContestArrangeChesses.get(n);
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
                                BeginArrangeBean.ChessBean chessBean = xlClubContestArrangeChesses.get(i);
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
                } else {
                    toast("" + beginArrangeBeanResponseBean.getMessage());
                }
            }
        });
        mViewModel.getLoginInentifyLiveData.observe(this, new Observer<LoginIdentifyBean>() {
            @Override
            public void onChanged(LoginIdentifyBean loginIdentifyBean) {
//                if (strShowGivenUp.equals("false")) {
////                    mBinding.tvLeftWaiver.setVisibility(View.GONE);
////                    mBinding.tvRightWavier.setVisibility(View.GONE);
//
//                    mBinding.tvLeftPause.setBackgroundColor(Color.parseColor("#CCCCCC"));
//                    mBinding.tvLeftPause.setEnabled(false);
//                    mBinding.tvRightPause.setBackgroundColor(Color.parseColor("#CCCCCC"));
//                    mBinding.tvRightPause.setEnabled(false);
//                } else {
                    if (loginIdentifyBean.isData()) {
//                        mBinding.tvLeftWaiver.setEnabled(false);
//                        mBinding.tvLeftWaiver.setBackgroundResource(R.drawable.img_left_gray_rect);
//                        mBinding.tvRightWavier.setEnabled(false);
//                        mBinding.tvRightWavier.setBackgroundResource(R.drawable.img_right_gray_rect);
                        mBinding.tvLeftPause.setBackgroundColor(Color.parseColor("#CCCCCC"));
                        mBinding.tvLeftPause.setEnabled(false);
                        mBinding.tvRightPause.setBackgroundColor(Color.parseColor("#CCCCCC"));
                        mBinding.tvRightPause.setEnabled(false);
                    }
//                }
            }
        });
        singlesDetailAdapter = new SinglesDetailOfficialAdapter(true);
        mBinding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvList.setAdapter(singlesDetailAdapter);


        mViewModel.coachSuspendLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean beginArrangeBeanResponseBean) {
                if (beginArrangeBeanResponseBean.getErrorCode().equals("200")) {
                    if (pauseType == 1) {
                        pauseType = -1;
                        showOneKeepGame();
                    } else {
                        getData();
                        toast("操作成功");
                    }
                } else if (beginArrangeBeanResponseBean.getErrorCode().equals("100000-100047")) {
                    toast("" + beginArrangeBeanResponseBean.getMessage());
                } else {
                    toast("" + beginArrangeBeanResponseBean.getMessage());
                }
            }
        });


        mViewModel.endArrangeLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                customDialogConfirmshowOneConfirmExit.dismiss();
                if (responseBean.getErrorCode().equals("200")) {
                    EventBus.getDefault().post(new SwipMatchEvent(clickPosition, leftWaverType, rightWaverType, leftAllWinCount, rightAllWinCount));
                    toast("提交成功");
                    finish();
                } else {
                    toast("" + responseBean.getMessage());
                    BeginArrangeBean.ChessBean xlClubContestArrangeChessesBean = singlesDetailAdapter.getData().get(EndPosition);
                    xlClubContestArrangeChessesBean.setCanChange(isSuccess);
                    singlesDetailAdapter.setData(EndPosition, xlClubContestArrangeChessesBean);
                }
            }
        });
        mViewModel.waiverChessLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    toast("操作成功");
                    getData();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

        mViewModel.updateChessScoreLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
//                if (responseBean.isSuccess()) {
                if (responseBean.getErrorCode().equals("200")) {
                    if (isChange) {
                        toast("修改成功");
                    } else {
                        toast("操作成功");
                    }

                    /*調用接口*/
                    List<BeginArrangeBean.ChessBean> data2 = singlesDetailAdapter.getData();
                    BeginArrangeBean.ChessBean xlClubContestArrangeChessesBean2 = data2.get(EndPosition);
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
                        BeginArrangeBean.ChessBean data = data2.get(i);
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
                        BeginArrangeBean.ChessBean xlClubContestArrangeChessesBean = data2.get(m);
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
                                BeginArrangeBean.ChessBean xlClubContestArrangeChessesBeanmn = data2.get(i);
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

//                    for (int m = 0; m < data2.size(); m++) {
//                        BeginArrangeBean.ChessBean xlClubContestArrangeChessesBean = data2.get(m);
//                        if (!TextUtils.isEmpty(xlClubContestArrangeChessesBean.getLeftCount()) || !TextUtils.isEmpty(xlClubContestArrangeChessesBean.getRightCount())) {
//                            xlClubContestArrangeChessesBean.setTextSubmit(false);
//                            xlClubContestArrangeChessesBean.setFirst(false);
//                        } else {
//                            xlClubContestArrangeChessesBean.setTextSubmit(true);
//                            xlClubContestArrangeChessesBean.setFirst(true);
//                        }
//                        data2.set(m, xlClubContestArrangeChessesBean);
//
//                    }
                    if (data2 != null && data2.size() > 0) {
                        if (leftWinCount == matchCount || rightWinCount == matchCount) {
                            for (int i = matchCount; i < data2.size(); i++) {
                                BeginArrangeBean.ChessBean xlClubContestArrangeChessesBean = data2.get(i);
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
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });


        singlesDetailAdapter.setMyListener(new SinglesDetailOfficialAdapter.onMyListener() {
            @Override
            public void OnMyListener(int scoreOne, int scoreTwo,
                                     BeginArrangeBean.ChessBean item,
                                     int position, String textStatus, boolean isFirst) {
                if ((scoreOne >= 11 && scoreTwo >= 11) || (scoreOne > 11 || scoreTwo > 11)) {
                    /*两边都大于11  需要比分判断*/
                    if (scoreOne - scoreTwo > 2 || scoreTwo - scoreOne > 2) {
                        ToastUtils.showToast(SinglesDetailOfficialActivity.this, "比分填写不规范，请重新填写");

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
                            ToastUtils.showToast(SinglesDetailOfficialActivity.this, "比分填写不规范，请重新填写");

                        }
                    }
                }
            }


        });
    }

    private int updatePosition = 0;

    private void notifyData(int scoreOne, int scoreTwo, BeginArrangeBean.ChessBean item, int position, String textStatus, boolean isFirst) {
        myItem = item;
        EndPosition = position;
        Log.e(TAG, "notifyData: textStatus===" + textStatus);
        switch (textStatus) {
            case "修改":

                List<BeginArrangeBean.ChessBean> data = singlesDetailAdapter.getData();
                BeginArrangeBean.ChessBean xlClubContestArrangeChessesBean = data.get(position);
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

    private void getData() {
        showLoading();
        mViewModel.beginArrange(ids, strCodeType);

    }

    long EndId;
    CustomDialog customDialogConfirm11;

    /*点击确定和修改比分弹窗*/
    private void showOneConfirm(int score1, int score2,
                                BeginArrangeBean.ChessBean item,
                                int position, String textStatus, boolean isFirst) {
        customDialogConfirm11 = new CustomDialog(SinglesDetailOfficialActivity.this);
        customDialogConfirm11.setCustomView(R.layout.pop_bureau_scores_single);
        customDialogConfirm11.show();
        View customView = customDialogConfirm11.getCustomView();
        TextView tvSure = customView.findViewById(R.id.tv_sure);
//        TextView tvScore = customView.findViewById(R.id.tv_score);
        ImageView ivLeftLogo = customView.findViewById(R.id.iv_left_logo);
        ImageView ivRightLogo = customView.findViewById(R.id.iv_right_logo);
        ImageLoader.loadImage(ivLeftLogo, data1.getHeadImg1(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
        ImageLoader.loadImage(ivRightLogo, data1.getHeadImg3(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);

//        tvScore.setText("" + score1 + ":" + score2);
        TextView tvleftScore = customView.findViewById(R.id.tv_left_score);
        TextView tvRightScore = customView.findViewById(R.id.tv_right_score);
        TextView tvLeftName = customView.findViewById(R.id.tv_left_name);
        TextView tvRightName = customView.findViewById(R.id.tv_right_name);
        if (data1 != null) {
            tvLeftName.setText("" + data1.getPlayer1());
            tvRightName.setText("" + data1.getPlayer3());
        }

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
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialogConfirm11 != null) {
                    customDialogConfirm11.dismiss();
                    leftScore = score1;
                    rightScore = score2;
                    EndId = item.getId();
                    EndPosition = position;
                    mViewModel.updateChessScore("" + EndId, "" + score1, "" + score2);

                }
            }
        });
        TextView tvCancel = customView.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialogConfirm11 != null) {
                    customDialogConfirm11.dismiss();
                }

            }
        });
    }

    CustomDialog customDialogConfirmshowOneConfirmExit;

    /*总的结束比赛的方法*/
    private void showOneConfirmExit(int leftAllCount, int rightAllCount) {
        try {


            customDialogConfirmshowOneConfirmExit = new CustomDialog(SinglesDetailOfficialActivity.this);
            customDialogConfirmshowOneConfirmExit.setCustomView(R.layout.pop_end_game_single_official);
            customDialogConfirmshowOneConfirmExit.show();
            View customView = customDialogConfirmshowOneConfirmExit.getCustomView();
            /*先判断总体Count 两边那个等于Count*/
            TextView tvLeftName = customView.findViewById(R.id.tv_left_name);
            TextView tvRightName = customView.findViewById(R.id.tv_right_name);
            TextView tvLeftWinner = customView.findViewById(R.id.tv_left_winner);
            TextView tvRightWinner = customView.findViewById(R.id.tv_right_winner);
//        TextView tvScore = customView.findViewById(R.id.tv_score);

            CircleImageView imgLeftLogo = customView.findViewById(R.id.img_left_logo);
            CircleImageView imgRightLogo = customView.findViewById(R.id.img_right_logo);
            ImageLoader.loadImage(imgLeftLogo, data1.getHeadImg1(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
            ImageLoader.loadImage(imgRightLogo, data1.getHeadImg3(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
            if (data1 != null) {
                leftName = data1.getPlayer1();
                rightName = data1.getPlayer3();
                tvLeftName.setText("" + leftName);
                tvRightName.setText("" + rightName);
            }


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
                    if (AntiShakeUtils.isInvalidClick(v))
                        return;
                    if (customDialogConfirmshowOneConfirmExit != null) {
                        if (data1 != null) {
                            showLoading();
                            if (leftWaverType == 1 && rightWaverType == 1) {
                                leftAllWinCount = 0;
                                rightAllWinCount = 0;
                                if (strCodeType.equals("1")) {
                                    mViewModel.endArrange("" + ids,
                                            0, 0, 1);
                                } else {
                                    mViewModel.endArrange("" + data1.getArrangeId(),
                                            0, 0, 1);
                                }
                            } else {
                                leftAllWinCount = leftAllCount;
                                rightAllWinCount = rightAllCount;
                                if (leftAllCount > rightAllCount) {
                                    if (strCodeType.equals("1")) {
                                        mViewModel.endArrange("" + ids,
                                                leftAllCount, rightAllCount, 1);
                                    } else {
                                        mViewModel.endArrange("" + data1.getArrangeId(),
                                                leftAllCount, rightAllCount, 1);
                                    }
                                } else if (leftAllCount < rightAllCount) {
                                    if (strCodeType.equals("1")) {
                                        mViewModel.endArrange("" + ids,
                                                leftAllCount, rightAllCount, 2);
                                    } else {
                                        mViewModel.endArrange("" + data1.getArrangeId(),
                                                leftAllCount, rightAllCount, 2);
                                    }
                                }
                            }
                        } else {
                            getData();
                        }

                    }
                }
            });
            TextView tvCancel = customView.findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AntiShakeUtils.isInvalidClick(v))
                        return;
                    if (customDialogConfirmshowOneConfirmExit != null) {
                        customDialogConfirmshowOneConfirmExit.dismiss();
                    }

                }
            });
        } catch (Exception e) {

        }
    }


    /*暂停*/
    private void showOneKeepGame() {
        customDialogConfirm = new CustomDialog(SinglesDetailOfficialActivity.this);
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
//                tvSure.setEnabled(false);
//                tvSure.setBackgroundResource(R.drawable.shape_club_gray);

            }

            @Override
            public void onFinish() {
//                tvSure.setText("继续比赛");
                tvDownTime.setVisibility(View.GONE);
//                tvSure.setEnabled(true);
//                tvSure.setBackgroundResource(R.drawable.shape_club_blue);
            }
        }.start();

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialogConfirm != null) {
                    customDialogConfirm.dismiss();
                    getData();
                    /*掉接口*/
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
                if (data1 != null) {
                    leftName = data1.getPlayer1();
                    rightName = data1.getPlayer3();
                }
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
                new MessageDialogBuilder(SinglesDetailOfficialActivity.this)
                        .setMessage("")
                        .setTvTitle("此运动员确认要暂停吗?")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (data1 != null) {
                                    pauseType = 1;
                                    if (strCodeType.equals("1")) {
                                        mViewModel.coachSuspend("" + ids, "left");
                                    } else {
                                        mViewModel.coachSuspend("" + data1.getArrangeId(), "left");
                                    }
                                    mBinding.tvLeftPause.setBackgroundColor(Color.parseColor("#CCCCCC"));
                                    mBinding.tvLeftPause.setEnabled(false);


                                } else {
                                    toast("页面异常");
                                }
                            }
                        })
                        .show();


                break;
            case R.id.tv_right_pause://右边的暂停按钮
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                /*先掉接口*/
                new MessageDialogBuilder(SinglesDetailOfficialActivity.this)
                        .setMessage("")
                        .setTvTitle("此运动员确认要暂停吗?")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (data1 != null) {
                                    pauseType = 1;


                                    if (strCodeType.equals("1")) {
                                        mViewModel.coachSuspend("" + ids, "right");
                                    } else {
                                        mViewModel.coachSuspend("" + data1.getArrangeId(), "right");
                                    }
                                    mBinding.tvRightPause.setBackgroundColor(Color.parseColor("#CCCCCC"));
                                    mBinding.tvRightPause.setEnabled(false);
                                } else {
                                    toast("页面异常");
                                }
                            }
                        })
                        .show();
                break;
            case R.id.tv_left_waiver://左边的弃权按钮
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (rightWaverType == 1) {
                    toast("本局比赛仅支持单弃权");
                } else {
                    new MessageDialogBuilder(SinglesDetailOfficialActivity.this)
                            .setMessage("弃权后代表选手放弃本盘比赛剩余的所有场数")
                            .setTvTitle("您确认此运动员" + data1.getPlayer1() + "要弃权吗？")
                            .setTvCancel("取消")
                            .setBtnCancleHint(false)
                            .setTvSure("确定")
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (data1 != null) {
                                        leftWaverType = 1;
//                                        mBinding.tvLeftWaiver.setEnabled(false);
//                                        mBinding.tvLeftWaiver.setBackgroundResource(R.drawable.img_left_gray_rect);
                                        if (strCodeType.equals("1")) {
                                            mViewModel.waiverChess("" + ids, 1);
                                        } else {
                                            mViewModel.waiverChess("" + data1.getArrangeId(), 1);
                                        }
                                    }
                                }
                            })
                            .show();
                }

                break;

            case R.id.tv_right_wavier://右边的弃权按钮
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (leftWaverType == 1) {
                    toast("本局比赛仅支持单弃权");
                } else {
                    new MessageDialogBuilder(SinglesDetailOfficialActivity.this)
                            .setMessage("弃权后代表选手放弃本盘比赛剩余的所有场数")
                            .setTvTitle("您确认此运动员" + data1.getPlayer2() + "要弃权吗？")
                            .setTvCancel("取消")
                            .setBtnCancleHint(false)
                            .setTvSure("确定")
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (data1 != null) {
                                        rightWaverType = 1;
//                                        mBinding.tvRightWavier.setEnabled(false);
//                                        mBinding.tvRightWavier.setBackgroundResource(R.drawable.img_right_gray_rect);
                                        if (strCodeType.equals("1")) {
                                            mViewModel.waiverChess("" + ids, 2);
                                        } else {
                                            mViewModel.waiverChess("" + data1.getArrangeId(), 2);
                                        }

                                    }
                                }
                            })
                            .show();
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


    //    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:
//                return false;//拦截事件
//            case KeyEvent.KEYCODE_MENU:
//                break;
//            case KeyEvent.KEYCODE_HOME:
//                break;
//            case KeyEvent.KEYCODE_APP_SWITCH:
//                break;
//            default:
//                break;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}