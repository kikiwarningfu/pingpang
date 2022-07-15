package heyong.intellectPinPang.ui.competition.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.google.gson.Gson;
import com.xq.fasterdialog.dialog.CustomDialog;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.SinglesDetailAdapter;
import heyong.intellectPinPang.bean.competition.QueryChessInfoBean;
import heyong.intellectPinPang.databinding.ActivityDoubleDetailBinding;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.handong.framework.utils.ToastUtils;
import heyong.intellectPinPang.utils.SoftKeyBoardListener;

/**
 * 双打比赛成绩界面UI
 */
public class DoubleDetailActivity extends BaseActivity<ActivityDoubleDetailBinding, ClubViewModel> implements View.OnClickListener {
    public static final String CONTEST_ARRANGE_ID = "contest_arrangeId";
    private String strContestArrangeId = "";
    public static final String CLICK_TYPE = "clickType";
    private String clickType = "";
    private SinglesDetailAdapter singlesDetailAdapter;

    QueryChessInfoBean data1;

    private int matchCount = -1;

    private int leftWaverType = 0;
    private int rightWaverType = 0;

    CountDownTimer timer;
    CustomDialog customDialogConfirm;
    String leftName;
    String rightName;

    private int pauseType = -1;

    private int EndPosition = -1;
    private boolean isSuccess = false;
    QueryChessInfoBean.XlClubContestArrangeChessesBean myItem;
    private int leftScore = -1;
    private int rightScore = -1;

    private boolean isChange = false;

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
        return R.layout.activity_double_detail;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        strContestArrangeId = getIntent().getStringExtra(CONTEST_ARRANGE_ID);
        clickType = getIntent().getStringExtra(CLICK_TYPE);
        getData();
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                Log.e(TAG, "keyBoardShow: " );
                mBinding.rvList.scrollToPosition(singlesDetailAdapter.getData().size()-1);
            }

            @Override
            public void keyBoardHide(int height) {
                Log.e(TAG, "keyBoardHide: " );
            }
        });
        singlesDetailAdapter = new SinglesDetailAdapter(true);
        mBinding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvList.setAdapter(singlesDetailAdapter);
        mViewModel.suspendWaiverLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                /*判断点击的是不是暂停*/
//                if (responseBean.isSuccess()) {
                if (responseBean.getErrorCode().equals("200")) {

                    if (pauseType == 1) {
                        pauseType = -1;
                        showOneKeepGame();
                    } else {
                        getData();
                        toast("操作成功");
                    }
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.waiverContestLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                customDialogConfirmConfirmExit.dismiss();
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {

                    toast("提交成功");
                    finish();
                } else {
                    toast("" + responseBean.getMessage());
                    QueryChessInfoBean.XlClubContestArrangeChessesBean xlClubContestArrangeChessesBean = singlesDetailAdapter.getData().get(EndPosition);
                    xlClubContestArrangeChessesBean.setCanChange(isSuccess);
                    singlesDetailAdapter.setData(EndPosition, xlClubContestArrangeChessesBean);
                }
            }
        });
        mViewModel.updateXlClubContestArrangeChessLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                customDialogConfirm11.dismiss();
                if (responseBean.getErrorCode().equals("200")) {
                    if (isChange) {
                        toast("修改成功");
                    } else {
                        toast("操作成功");
                    }
                    /*調用接口*/
                    List<QueryChessInfoBean.XlClubContestArrangeChessesBean> data1 = singlesDetailAdapter.getData();
                    QueryChessInfoBean.XlClubContestArrangeChessesBean xlClubContestArrangeChessesBean2 = data1.get(EndPosition);
                    xlClubContestArrangeChessesBean2.setTextSubmit(false);
                    xlClubContestArrangeChessesBean2.setFirst(false);
                    Log.e(TAG, "onChanged: 更新 默认 设置为false==="+false );
                    xlClubContestArrangeChessesBean2.setLeftIntegral("" + leftScore);
                    xlClubContestArrangeChessesBean2.setRightIntegral("" + rightScore);
                    data1.set(EndPosition, xlClubContestArrangeChessesBean2);
                    Log.e(TAG, "onClick: data===" + new Gson().toJson(data1));
                    Log.e(TAG, "notifyData: 确定");


                    int leftWinCount = 0;
                    int rightWinCount = 0;
                    for (int i = 0; i < data1.size(); i++) {
                        QueryChessInfoBean.XlClubContestArrangeChessesBean data = data1.get(i);
                        if (!TextUtils.isEmpty(data.getLeftIntegral()) && !TextUtils.isEmpty(data.getRightIntegral())) {
                            if (Integer.parseInt(data.getLeftIntegral()) > Integer.parseInt(data.getRightIntegral())) {
                                leftWinCount = leftWinCount + 1;
                            } else if (Integer.parseInt(data.getLeftIntegral()) < Integer.parseInt(data.getRightIntegral())) {
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

                    for (int m = 0; m < data1.size(); m++) {
                        QueryChessInfoBean.XlClubContestArrangeChessesBean xlClubContestArrangeChessesBean = data1.get(m);
                        if (!TextUtils.isEmpty(xlClubContestArrangeChessesBean.getLeftIntegral()) ||
                                !TextUtils.isEmpty(xlClubContestArrangeChessesBean.getRightIntegral())) {
                            xlClubContestArrangeChessesBean.setTextSubmit(false);
                            xlClubContestArrangeChessesBean.setFirst(false);
                            Log.e(TAG, "onChanged: 积分不为空 设置为false" );
                        } else {
                            xlClubContestArrangeChessesBean.setTextSubmit(true);
                            xlClubContestArrangeChessesBean.setFirst(true);
                            Log.e(TAG, "onChanged: 积分为空 设置为true" );
                        }
                        data1.set(m, xlClubContestArrangeChessesBean);
                    }
                    if (leftWinCount == matchCount || rightWinCount == matchCount) {

                    } else {
                        if (updatePosition != data1.size() - 1) {
                            for (int i = updatePosition; i < data1.size(); i++) {
                                QueryChessInfoBean.XlClubContestArrangeChessesBean xlClubContestArrangeChessesBeanmn = data1.get(i);
                                if (i == updatePosition + 1) {
                                    //判断是否相等
                                    xlClubContestArrangeChessesBeanmn.setFirst(true);
                                    Log.e(TAG, "onChanged: 等于我想要输入的这个 设置为true" );
                                    data1.set(i, xlClubContestArrangeChessesBeanmn);
                                } else {
                                    //判断是否相等
                                    xlClubContestArrangeChessesBeanmn.setFirst(false);
                                    Log.e(TAG, "onChanged: 不等于 我想输入的这个设置为fasle" );
                                    data1.set(i, xlClubContestArrangeChessesBeanmn);
                                }
                            }
                        }
                    }


                    if (data1 != null && data1.size() > 0) {

                        if (leftWinCount == matchCount || rightWinCount == matchCount) {
                            for (int i = matchCount; i < data1.size(); i++) {
                                QueryChessInfoBean.XlClubContestArrangeChessesBean xlClubContestArrangeChessesBean = data1.get(i);
                                xlClubContestArrangeChessesBean.setFirst(false);
                                Log.e(TAG, "onChanged: 已经赢了  谁也别输入了" );
                                data1.set(i, xlClubContestArrangeChessesBean);
                                Log.e(TAG, "onChanged: position==" + i);
                                singlesDetailAdapter.setNewData(data1);
                            }

                            showOneConfirmExit(leftWinCount, rightWinCount);

                        } else {
                            singlesDetailAdapter.setNewData(data1);
                        }
                    } else {
                        singlesDetailAdapter.setNewData(new ArrayList<>());
                    }

                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.queryChessInfoBeanLiveData.observe(this, new Observer<ResponseBean<QueryChessInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<QueryChessInfoBean> queryChessInfoBeanResponseBean) {
//                if (queryChessInfoBeanResponseBean.isSuccess()) {
                if (queryChessInfoBeanResponseBean.getErrorCode().equals("200")) {

                    data1 = queryChessInfoBeanResponseBean.getData();
                    if (data1 != null) {
//                        ImageLoader.loadImage(mBinding.ivLeft1Logo,queryChessData.getlef);
                        mBinding.tvLeft1Name.setText("" + data1.getLeft1Name());
                        mBinding.tvLeft2Name.setText("" + data1.getLeft2Name());
                        mBinding.tvRight1Name.setText("" + data1.getRight1Name());
                        mBinding.tvRight2Name.setText("" + data1.getRight2Name());
                        ImageLoader.loadImage(mBinding.ivLeft1Logo, "" + data1.getLeft1HeadImg(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
                        ImageLoader.loadImage(mBinding.ivLeft2Logo, "" + data1.getLeft2HeadImg(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
                        ImageLoader.loadImage(mBinding.ivRight1Logo, "" + data1.getRight1HeadImg(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
                        ImageLoader.loadImage(mBinding.ivRight2Logo, "" + data1.getRight2HeadImg(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
//                        private int gameType = -1;//1 是三局两胜  2是五局三胜  3 是七局四胜
                        switch (data1.getMatchMethod()) {
                            case "三局二胜":
                                matchCount = 2;
                                break;
                            case "五局三胜":
                                matchCount = 3;
                                break;
                            case "七局四胜":
                                matchCount = 4;
                                break;
                        }
                        int leftSuspend = data1.getLeftSuspend();
                        if (leftSuspend == 1) {

                            mBinding.tvLeftPause.setBackgroundColor(Color.parseColor("#CCCCCC"));
                            mBinding.tvLeftPause.setEnabled(false);
                        } else {
                            mBinding.tvLeftPause.setBackgroundColor(Color.parseColor("#FFCD66"));
                            mBinding.tvLeftPause.setEnabled(true);
                        }
                        int rightSuspend = data1.getRightSuspend();
                        if (rightSuspend == 1) {
                            mBinding.tvRightPause.setBackgroundColor(Color.parseColor("#CCCCCC"));
                            mBinding.tvRightPause.setEnabled(false);
                        } else {
                            mBinding.tvRightPause.setBackgroundColor(Color.parseColor("#FFCD66"));
                            mBinding.tvRightPause.setEnabled(true);
                        }



                        List<QueryChessInfoBean.XlClubContestArrangeChessesBean> xlClubContestArrangeChesses = data1.getXlClubContestArrangeChesses();
                        if (xlClubContestArrangeChesses != null && xlClubContestArrangeChesses.size() > 0) {
                            singlesDetailAdapter.setNewData(xlClubContestArrangeChesses);
                        } else {
                            singlesDetailAdapter.setNewData(new ArrayList<>());
                        }
                        int leftWinCount = 0;
                        int rightWinCount = 0;
                        for (int i = 0; i < xlClubContestArrangeChesses.size(); i++) {
                            QueryChessInfoBean.XlClubContestArrangeChessesBean data = xlClubContestArrangeChesses.get(i);
                            if (!TextUtils.isEmpty(data.getLeftIntegral()) && !TextUtils.isEmpty(data.getRightIntegral())) {
                                if (Integer.parseInt(data.getLeftIntegral()) > Integer.parseInt(data.getRightIntegral())) {
                                    leftWinCount = leftWinCount + 1;
                                } else if (Integer.parseInt(data.getLeftIntegral()) < Integer.parseInt(data.getRightIntegral())) {
                                    rightWinCount = rightWinCount + 1;
                                }
                            }
                        }


                        int emptyFirstPosition = -1;
                        boolean isFirst = true;
                        for (int m = 0; m < xlClubContestArrangeChesses.size(); m++) {
                            QueryChessInfoBean.XlClubContestArrangeChessesBean xlClubContestArrangeChessesBean = xlClubContestArrangeChesses.get(m);
//                            xlClubContestArrangeChessesBean.setFirst(true);
                            if (!TextUtils.isEmpty(xlClubContestArrangeChessesBean.getLeftIntegral()) || !TextUtils.isEmpty(xlClubContestArrangeChessesBean.getRightIntegral())) {
                                xlClubContestArrangeChessesBean.setTextSubmit(false);
                                xlClubContestArrangeChessesBean.setFirst(false);
                                Log.e(TAG, "onChanged: 成绩不为空 不能输入" );
                                isChange = true;

                            } else {
                                if (isFirst) {
                                    emptyFirstPosition = m;
                                    isFirst = false;
                                    Log.e(TAG, "onChanged: " + emptyFirstPosition);
                                }
                                xlClubContestArrangeChessesBean.setTextSubmit(true);
                                xlClubContestArrangeChessesBean.setFirst(true);
                                Log.e(TAG, "onChanged: 成绩为空 设置first为true" );
                            }
                            xlClubContestArrangeChesses.set(m, xlClubContestArrangeChessesBean);

                        }
                        if (emptyFirstPosition != -1) {
                            for (int n = 0; n < xlClubContestArrangeChesses.size(); n++) {
                                QueryChessInfoBean.XlClubContestArrangeChessesBean xlClubContestArrangeChessesBean2 = xlClubContestArrangeChesses.get(n);

                                if (emptyFirstPosition == n) {
                                    Log.e(TAG, "onChanged: 设置first为true  position===" + n);
                                    xlClubContestArrangeChessesBean2.setFirst(true);
                                } else {
                                    xlClubContestArrangeChessesBean2.setFirst(false);
                                    Log.e(TAG, "onChanged: 设置first为 false" );
                                }
                                xlClubContestArrangeChesses.set(n, xlClubContestArrangeChessesBean2);
                            }
                        }


                        if (xlClubContestArrangeChesses != null && xlClubContestArrangeChesses.size() > 0) {

                            if (leftWinCount == matchCount || rightWinCount == matchCount) {
                                for (int i = matchCount; i < xlClubContestArrangeChesses.size(); i++) {
                                    QueryChessInfoBean.XlClubContestArrangeChessesBean xlClubContestArrangeChessesBean = xlClubContestArrangeChesses.get(i);
                                    xlClubContestArrangeChessesBean.setFirst(false);
                                    xlClubContestArrangeChesses.set(i, xlClubContestArrangeChessesBean);
                                    Log.e(TAG, "onChanged: 已经赢了 设置为false" );
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
                    toast("" + queryChessInfoBeanResponseBean.getMessage());
                }
            }
        });

        singlesDetailAdapter.setMyListener(new SinglesDetailAdapter.onMyListener() {
            @Override
            public void OnMyListener(int scoreOne, int scoreTwo,
                                     QueryChessInfoBean.XlClubContestArrangeChessesBean item,
                                     int position, String textStatus, boolean isFirst) {
                if ((scoreOne >= 11 && scoreTwo >= 11) || (scoreOne > 11 || scoreTwo > 11)) {
                    /*两边都大于11  需要比分判断*/
                    if (scoreOne - scoreTwo > 2 || scoreTwo - scoreOne > 2) {
                        ToastUtils.showToast(DoubleDetailActivity.this, "比分填写不规范，请重新填写");
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
                            ToastUtils.showToast(DoubleDetailActivity.this, "比分填写不规范，请重新填写");
                        }
                    }
                }
            }


        });
    }

    private int updatePosition = 0;

    private void notifyData(int scoreOne, int scoreTwo, QueryChessInfoBean.XlClubContestArrangeChessesBean item, int position, String textStatus, boolean isFirst) {
        myItem = item;
        EndPosition = position;
        switch (textStatus) {
            case "修改":

                List<QueryChessInfoBean.XlClubContestArrangeChessesBean> data = singlesDetailAdapter.getData();
                QueryChessInfoBean.XlClubContestArrangeChessesBean xlClubContestArrangeChessesBean = data.get(position);
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

    long EndId;

    private void getData() {
        mViewModel.queryChessInfo(strContestArrangeId, "" + clickType);
    }

    CustomDialog customDialogConfirm11;

    /*点击确定和修改比分弹窗*/
    private void showOneConfirm(int score1, int score2,
                                QueryChessInfoBean.XlClubContestArrangeChessesBean item,
                                int position, String textStatus, boolean isFirst) {


        customDialogConfirm11 = new CustomDialog(DoubleDetailActivity.this);
        customDialogConfirm11.setCustomView(R.layout.pop_bureau_scores_double);
        customDialogConfirm11.show();
        View customView = customDialogConfirm11.getCustomView();
        TextView tvSure = customView.findViewById(R.id.tv_sure);

        ImageView ivLeftLogo1 = customView.findViewById(R.id.img_left1_logo);
        ImageView ivLeftLogo2 = customView.findViewById(R.id.img_left2_logo);
        ImageView ivRight1Logo = customView.findViewById(R.id.img_right1_logo);
        ImageView ivRight2Logo = customView.findViewById(R.id.img_right2_logo);
        ImageLoader.loadImage(ivLeftLogo1, data1.getLeft1HeadImg(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
        ImageLoader.loadImage(ivLeftLogo2, data1.getLeft2HeadImg(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
        ImageLoader.loadImage(ivRight1Logo, data1.getRight1HeadImg(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
        ImageLoader.loadImage(ivRight2Logo, data1.getRight2HeadImg(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
        TextView tvLeftName1=customView.findViewById(R.id.tv_left1_name);
        TextView tvLeftName2=customView.findViewById(R.id.tv_left2_name);
        TextView tvRightName1=customView.findViewById(R.id.tv_right1_name);
        TextView tvRightName2=customView.findViewById(R.id.tv_right2_name);

        tvLeftName1.setText(""+data1.getLeft1Name());
        tvLeftName2.setText(""+data1.getLeft2Name());
        tvRightName1.setText(""+data1.getRight1Name());
        tvRightName2.setText(""+data1.getRight2Name());
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
                    mViewModel.updateXlClubContestArrangeChess("" + EndId, "" + leftScore, "" + rightScore);

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

    CustomDialog customDialogConfirmConfirmExit;

    /*总的结束比赛的方法*/
    private void showOneConfirmExit(int leftAllCount, int rightAllCount) {
        try {


            customDialogConfirmConfirmExit = new CustomDialog(DoubleDetailActivity.this);
            customDialogConfirmConfirmExit.setCustomView(R.layout.pop_end_game_double);
            customDialogConfirmConfirmExit.show();
            View customView = customDialogConfirmConfirmExit.getCustomView();
            /*先判断总体Count 两边那个等于Count*/
            TextView tvLeft1Name = customView.findViewById(R.id.tv_left1_name);
            TextView tvLeft2Name = customView.findViewById(R.id.tv_left2_name);

            TextView tvRight1Name = customView.findViewById(R.id.tv_right1_name);
            TextView tvRight2Name = customView.findViewById(R.id.tv_right2_name);
            tvLeft1Name.setText("" + data1.getLeft1Name());
            tvLeft2Name.setText("" + data1.getLeft2Name());
            tvRight1Name.setText("" + data1.getRight1Name());
            tvRight2Name.setText("" + data1.getRight2Name());

            TextView tvLeftWinner = customView.findViewById(R.id.tv_winner_left);
            TextView tvRightWinner = customView.findViewById(R.id.tv_winner_right);

            CircleImageView imgLeft1Logo = customView.findViewById(R.id.img_left1_logo);
            CircleImageView imgLeft2Logo = customView.findViewById(R.id.img_left2_logo);
            CircleImageView imgRight1Logo = customView.findViewById(R.id.img_right1_logo);
            CircleImageView imgRight2Logo = customView.findViewById(R.id.img_right2_logo);

            ImageLoader.loadImage(imgLeft1Logo, data1.getLeft1HeadImg(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
            ImageLoader.loadImage(imgLeft2Logo, data1.getLeft2HeadImg(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
            ImageLoader.loadImage(imgRight1Logo, data1.getRight1HeadImg(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
            ImageLoader.loadImage(imgRight2Logo, data1.getRight2HeadImg(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);

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

//        TextView tvScore = customView.findViewById(R.id.tv_score);
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

            TextView tvSure = customView.findViewById(R.id.tv_sure);
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AntiShakeUtils.isInvalidClick(v))
                        return;
                    if (customDialogConfirmConfirmExit != null) {
                        if (data1 != null) {
                            showLoading();
                            if (leftWaverType == 1 && rightWaverType == 1) {
                                mViewModel.waiverContest("" + data1.getId(), "", "", "",
                                        "" + 0, "" + 0
                                        , "" + leftWaverType, "" + rightWaverType);
                            } else {
                                if (leftAllCount > rightAllCount) {
                                    mViewModel.waiverContest("" + data1.getId(), "" + data1.getLeft1Id(), "",
                                            "" + data1.getTeamLeftId(),
                                            "" + leftAllCount, "" + rightAllCount
                                            , "" + leftWaverType, "" + rightWaverType);
                                } else if (leftAllCount < rightAllCount) {
                                    mViewModel.waiverContest("" + data1.getId(), "" + data1.getRight1Id(), "", "" + data1.getTeamRightId(),
                                            "" + leftAllCount, "" + rightAllCount
                                            , "" + leftWaverType, "" + rightWaverType);
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
                    if (customDialogConfirmConfirmExit != null) {
                        customDialogConfirmConfirmExit.dismiss();
                    }

                }
            });
        } catch (Exception e) {

        }
    }


    /*暂停*/
    private void showOneKeepGame() {
        customDialogConfirm = new CustomDialog(DoubleDetailActivity.this);
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
                leftName = data1.getLeft1Name();
                rightName = data1.getRight1Name();
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
                /*先掉接口*/
                new MessageDialogBuilder(DoubleDetailActivity.this)
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
                                    mViewModel.suspendWaiver("" + data1.getId(), "", "", "", "" + 1);
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
                new MessageDialogBuilder(DoubleDetailActivity.this)
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
                                    mViewModel.suspendWaiver("" + data1.getId(), "", "", "" + 1, "");
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
                new MessageDialogBuilder(DoubleDetailActivity.this)
                        .setMessage("弃权后代表选手放弃本盘比赛剩余的所有场数")
                        .setTvTitle("您确认此运动员" + data1.getLeft1Name() + "-" + data1.getLeft2Name() + "要弃权吗？")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (data1 != null) {
                                    leftWaverType = 1;
                                    clickType = "2";
//                                    mBinding.tvLeftWaiver.setEnabled(false);
//                                    mBinding.tvLeftWaiver.setBackgroundResource(R.drawable.img_left_gray_rect);
                                    mViewModel.suspendWaiver("" + data1.getId(), "" + leftWaverType, "", "", "");
                                } else {
                                    toast("页面异常");
                                }
                            }
                        })
                        .show();


                break;

            case R.id.tv_right_wavier://右边的弃权按钮
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                new MessageDialogBuilder(DoubleDetailActivity.this)
                        .setMessage("弃权后代表选手放弃本盘比赛剩余的所有场数")
                        .setTvTitle("您确认此运动员" + data1.getRight1Name() + "-" + data1.getRight2Name() + "要弃权吗？")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (data1 != null) {
//                                    mBinding.tvRightWavier.setEnabled(false);
//                                    mBinding.tvRightWavier.setBackgroundResource(R.drawable.img_right_gray_rect);
                                    rightWaverType = 1;
                                    clickType = "2";
                                    mViewModel.suspendWaiver("" + data1.getId(), "", "" + rightWaverType, "", "");
                                } else {
                                    toast("页面异常");
                                }
                            }
                        })
                        .show();


                break;
        }
    }


}