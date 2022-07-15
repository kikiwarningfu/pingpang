package heyong.intellectPinPang.ui.competition.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.DashPathEffect;
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
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.handong.framework.utils.ToastUtils;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.SinglesDetailOfficialAdapter;
import heyong.intellectPinPang.bean.competition.BeginArrangeBean;
import heyong.intellectPinPang.bean.competition.LoginIdentifyBean;
import heyong.intellectPinPang.databinding.ActivityDoubleDetailOfficialBinding;
import heyong.intellectPinPang.event.SwipMatchEvent;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.utils.SoftKeyBoardListener;

/**
 * 正式的双打比赛成绩界面UI
 * 双打比赛输入
 */
public class DoubleDetailOfficialActivity extends BaseActivity<ActivityDoubleDetailOfficialBinding, ClubViewModel> implements View.OnClickListener {
    private SinglesDetailOfficialAdapter singlesDetailAdapter;
    public static final String CODE_TYPE = "code_type";
    private String strCodeType = "";
    private int matchCount = -1;

    private int leftWaverType = -1;
    private int rightWaverType = -1;

    CountDownTimer timer;
    CustomDialog customDialogConfirm;
    String leftName;
    String rightName;

    private int pauseType = -1;

    private int EndPosition = -1;
    private boolean isSuccess = false;
    private int leftScore = -1;
    private int rightScore = -1;
    BeginArrangeBean data1;
    BeginArrangeBean.ChessBean myItem;
    private String clickPosition = "";

    private int leftAllWinCount;
    private int rightAllWinCount;
    private boolean isChange = false;

    public static final String ITEM_SHOW = "item_show_give_up";
    private String strShowGivenUp = "";


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

    @Override
    public int getLayoutRes() {
        return R.layout.activity_double_detail_official;
    }

    private String ids = "";

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        ids = getIntent().getStringExtra("ids");
        clickPosition = getIntent().getStringExtra("cliclkPosition");
        strCodeType = getIntent().getStringExtra(CODE_TYPE);
        strShowGivenUp = getIntent().getStringExtra(ITEM_SHOW);
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                mBinding.rvList.scrollToPosition(singlesDetailAdapter.getData().size() - 1);
            }

            @Override
            public void keyBoardHide(int height) {

            }
        });

        if (strShowGivenUp == null) {
            strShowGivenUp = "";
        }
        if (strCodeType == null) {
            strCodeType = "";
        }
        getData();


        singlesDetailAdapter = new SinglesDetailOfficialAdapter(true);
        mBinding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvList.setAdapter(singlesDetailAdapter);
//        //触摸recyclerView的监听
//        mBinding.rvList.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                //隐藏键盘
//                hideSoftInput(DoubleDetailOfficialActivity.this, editText_message);
//                return false;
//            }
//        });


        mViewModel.endArrangeLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                customDialogConfirmOneConfirmExit.dismiss();
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
        mViewModel.updateChessScoreLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                customDialogConfirm11.dismiss();
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
//                    }
                }
            }
        });

        mViewModel.beginArrangeLiveData.observe(this, new Observer<ResponseBean<BeginArrangeBean>>() {
            @Override
            public void onChanged(ResponseBean<BeginArrangeBean> beginArrangeBeanResponseBean) {
                dismissLoading();
                mViewModel.getLoginInentify("" + ids);

                if (beginArrangeBeanResponseBean.getErrorCode().equals("200")) {
                    data1 = beginArrangeBeanResponseBean.getData();
                    if (data1 != null) {
//                        ImageLoader.loadImage(mBinding.ivLeft1Logo,queryChessData.getlef);
                        mBinding.tvLeft1Name.setText("" + data1.getPlayer1());
                        mBinding.tvLeft2Name.setText("" + data1.getPlayer2());
                        mBinding.tvRight1Name.setText("" + data1.getPlayer3());
                        mBinding.tvRight2Name.setText("" + data1.getPlayer4());
                        ImageLoader.loadImage(mBinding.ivLeft1Logo, "" + data1.getHeadImg1());
                        ImageLoader.loadImage(mBinding.ivLeft2Logo, "" + data1.getHeadImg2());
                        ImageLoader.loadImage(mBinding.ivRight1Logo, "" + data1.getHeadImg3());
                        ImageLoader.loadImage(mBinding.ivRight2Logo, "" + data1.getHeadImg4());
//                        private int gameType = -1;//1 是三局两胜  2是五局三胜  3 是七局四胜
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
//                        if(strShowGivenUp.equals("false"))
//                        {
//                            mBinding.tvLeftWaiver.setVisibility(View.GONE);
//                            mBinding.tvRightWavier.setVisibility(View.GONE);
//                        }else {
//                            int leftWavier = data1.getLeftWaiver();
//                            if (leftWavier == 1) {
//                                leftWaverType = 1;
//                                mBinding.tvLeftWaiver.setEnabled(false);
//                                mBinding.tvLeftWaiver.setBackgroundResource(R.drawable.img_left_gray_rect);
//                            }
//                            int rightWavier = data1.getRightWaiver();
//                            if (rightWavier == 1) {
//                                rightWaverType = 1;
//                                mBinding.tvRightWavier.setEnabled(false);
//                                mBinding.tvRightWavier.setBackgroundResource(R.drawable.img_right_gray_rect);
//                            }
//                        }

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
//                            xlClubContestArrangeChessesBean.setFirst(true);
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
                                    Log.e(TAG, "onChanged: 设置为true  position===" + n);
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
                                    BeginArrangeBean.ChessBean xlClubContestArrangeChessesBean = xlClubContestArrangeChesses.get(i);
                                    xlClubContestArrangeChessesBean.setFirst(false);
                                    xlClubContestArrangeChesses.set(i, xlClubContestArrangeChessesBean);
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
                } else {
                    toast("" + beginArrangeBeanResponseBean.getMessage());
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


        singlesDetailAdapter.setMyListener(new SinglesDetailOfficialAdapter.onMyListener() {
            @Override
            public void OnMyListener(int scoreOne, int scoreTwo,
                                     BeginArrangeBean.ChessBean item,
                                     int position, String textStatus, boolean isFirst) {

                if ((scoreOne >= 11 && scoreTwo >= 11) || (scoreOne > 11 || scoreTwo > 11)) {
                    /*两边都大于11  需要比分判断*/
                    if (scoreOne - scoreTwo > 2 || scoreTwo - scoreOne > 2) {
                        ToastUtils.showToast(DoubleDetailOfficialActivity.this, "比分填写不规范，请重新填写");
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
                            ToastUtils.showToast(DoubleDetailOfficialActivity.this, "比分填写不规范，请重新填写");
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
        customDialogConfirm11 = new CustomDialog(DoubleDetailOfficialActivity.this);
        customDialogConfirm11.setCustomView(R.layout.pop_bureau_scores_double);
        customDialogConfirm11.show();
        View customView = customDialogConfirm11.getCustomView();
        TextView tvSure = customView.findViewById(R.id.tv_sure);
        TextView tvleftScore = customView.findViewById(R.id.tv_left_score);
        TextView tvRightScore = customView.findViewById(R.id.tv_right_score);
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
        ImageView ivLeftLogo1 = customView.findViewById(R.id.img_left1_logo);
        ImageView ivLeftLogo2 = customView.findViewById(R.id.img_left2_logo);
        ImageView ivRight1Logo = customView.findViewById(R.id.img_right1_logo);
        ImageView ivRight2Logo = customView.findViewById(R.id.img_right2_logo);
        TextView tvLeft1Name = customView.findViewById(R.id.tv_left1_name);
        TextView tvLeft2Name = customView.findViewById(R.id.tv_left2_name);
        TextView tvRight1Name = customView.findViewById(R.id.tv_right1_name);
        TextView tvRight2Name = customView.findViewById(R.id.tv_right2_name);

        tvLeft1Name.setText("" + data1.getPlayer1());
        tvLeft2Name.setText("" + data1.getPlayer2());
        tvRight1Name.setText("" + data1.getPlayer3());
        tvRight2Name.setText("" + data1.getPlayer4());
        ImageLoader.loadImage(ivLeftLogo1, data1.getHeadImg1());
        ImageLoader.loadImage(ivLeftLogo2, data1.getHeadImg2());
        ImageLoader.loadImage(ivRight1Logo, data1.getHeadImg3());
        ImageLoader.loadImage(ivRight2Logo, data1.getHeadImg4());
        item.setCanChange(false);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialogConfirm11 != null) {

                    leftScore = score1;
                    rightScore = score2;
                    EndId = item.getId();
                    EndPosition = position;
                    showLoading();
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

    CustomDialog customDialogConfirmOneConfirmExit;

    //弹出键盘
    public static void showSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    //隐藏键盘
    public static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    /*总的结束比赛的方法*/
    private void showOneConfirmExit(int leftAllCount, int rightAllCount) {
        try {

            customDialogConfirmOneConfirmExit = new CustomDialog(DoubleDetailOfficialActivity.this);
            customDialogConfirmOneConfirmExit.setCustomView(R.layout.pop_end_game_double_official);
            customDialogConfirmOneConfirmExit.show();
            View customView = customDialogConfirmOneConfirmExit.getCustomView();
            /*先判断总体Count 两边那个等于Count*/

            TextView tvLeftWinner = customView.findViewById(R.id.tv_winner_left);
            TextView tvRightWinner = customView.findViewById(R.id.tv_winner_right);
//        TextView tvScore = customView.findViewById(R.id.tv_score);


            TextView tvLeft1Name = customView.findViewById(R.id.tv_left1_name);
            TextView tvLeft2Name = customView.findViewById(R.id.tv_left2_name);

            TextView tvRight1Name = customView.findViewById(R.id.tv_right1_name);
            TextView tvRight2Name = customView.findViewById(R.id.tv_right2_name);

            tvLeft1Name.setText("" + data1.getPlayer1());
            tvLeft2Name.setText("" + data1.getPlayer2());
            tvRight1Name.setText("" + data1.getPlayer3());
            tvRight2Name.setText("" + data1.getPlayer4());


            CircleImageView imgLeft1Logo = customView.findViewById(R.id.img_left1_logo);
            CircleImageView imgLeft2Logo = customView.findViewById(R.id.img_left2_logo);
            CircleImageView imgRight1Logo = customView.findViewById(R.id.img_right1_logo);
            CircleImageView imgRight2Logo = customView.findViewById(R.id.img_right2_logo);

            ImageLoader.loadImage(imgLeft1Logo, data1.getHeadImg1());
            ImageLoader.loadImage(imgLeft2Logo, data1.getHeadImg2());
            ImageLoader.loadImage(imgRight1Logo, data1.getHeadImg3());
            ImageLoader.loadImage(imgRight2Logo, data1.getHeadImg4());

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

//
//        if (leftWaverType == 1 && rightWaverType != 1) {
//            /*左边弃权*/
//            tvScore.setText("W-" + leftAllCount + ":" + rightAllCount);
//        } else if (leftWaverType == 1 && rightWaverType == 1) {
//            /*两边都弃权*/
//            tvScore.setText("W-" + leftAllCount + ":W-" + rightAllCount);
//        } else if (leftWaverType != 1 && rightWaverType == 1) {
//            tvScore.setText("" + leftAllCount + ":W-" + rightAllCount);
//            /*右边弃权*/
//        } else {
//        tvScore.setText("" + leftAllCount + ":" + rightAllCount);
            /*两边都没弃权*/
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
            leftName = data1.getPlayer1();
            rightName = data1.getPlayer3();

            TextView tvSure = customView.findViewById(R.id.tv_sure);
            tvSure.setEnabled(true);
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AntiShakeUtils.isInvalidClick(v))
                        return;
                    if (customDialogConfirmOneConfirmExit != null) {
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
                    if (customDialogConfirmOneConfirmExit != null) {
                        customDialogConfirmOneConfirmExit.dismiss();
                    }

                }
            });
        } catch (Exception e) {
            Log.e(TAG, "showOneConfirmExit: " + e.getMessage());

        }
    }


    /*暂停*/
    private void showOneKeepGame() {
        customDialogConfirm = new CustomDialog(DoubleDetailOfficialActivity.this);
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

                new MessageDialogBuilder(DoubleDetailOfficialActivity.this)
                        .setMessage("")
                        .setTvTitle("此运动员确认要暂停吗?")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                /*先掉接口*/
                                if (data1 != null) {
                                    pauseType = 1;

                                    mViewModel.coachSuspend("" + data1.getArrangeId(), "left");
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

                new MessageDialogBuilder(DoubleDetailOfficialActivity.this)
                        .setMessage("")
                        .setTvTitle("此运动员确认要暂停吗?")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                /*先掉接口*/
                                if (data1 != null) {
                                    pauseType = 1;
                                    mViewModel.coachSuspend("" + data1.getArrangeId(), "right");
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

                new MessageDialogBuilder(DoubleDetailOfficialActivity.this)
                        .setMessage("弃权后代表选手放弃本盘比赛剩余的所有场数")
                        .setTvTitle("您确认此运动员" + data1.getPlayer1() + "-" + data1.getPlayer3() + "要弃权吗？")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (data1 != null) {
                                    leftWaverType = 1;
//                                    mBinding.tvLeftWaiver.setEnabled(false);
//                                    mBinding.tvLeftWaiver.setBackgroundResource(R.drawable.img_left_gray_rect);
                                    mViewModel.waiverChess("" + data1.getArrangeId(), 1);
                                }
                            }
                        })
                        .show();


                break;

            case R.id.tv_right_wavier://右边的弃权按钮
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                new MessageDialogBuilder(DoubleDetailOfficialActivity.this)
                        .setMessage("弃权后代表选手放弃本盘比赛剩余的所有场数")
                        .setTvTitle("您确认此运动员" + data1.getPlayer2() + "-" + data1.getPlayer4() + "要弃权吗？")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (data1 != null) {
                                    rightWaverType = 1;
//                                    mBinding.tvRightWavier.setEnabled(false);
//                                    mBinding.tvRightWavier.setBackgroundResource(R.drawable.img_right_gray_rect);
                                    mViewModel.waiverChess("" + data1.getArrangeId(), 2);
                                }
                            }
                        })
                        .show();


                break;
        }
    }


}