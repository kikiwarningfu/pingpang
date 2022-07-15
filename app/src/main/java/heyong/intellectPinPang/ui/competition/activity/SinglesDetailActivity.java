package heyong.intellectPinPang.ui.competition.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.nanchen.compresshelper.CompressHelper;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xq.fasterdialog.dialog.CustomDialog;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.SinglesDetailAdapter;
import heyong.intellectPinPang.bean.competition.QueryChessInfoBean;
import heyong.intellectPinPang.databinding.ActivitySinglesDetailBinding;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.handong.framework.utils.ToastUtils;
import heyong.intellectPinPang.utils.RxBusSubscriber;
import heyong.intellectPinPang.utils.SoftKeyBoardListener;
import heyong.intellectPinPang.widget.PhotoSelDialog;
import heyong.lzy.imagepicker.ImagePicker;
import heyong.lzy.imagepicker.bean.ImageItem;
import heyong.lzy.imagepicker.ui.ImageGridActivity;

/**
 * 单打比赛成绩  结束比赛 一直在底部    一直高亮状态   还有 可能有很多局  需要写活
 */
public class SinglesDetailActivity extends BaseActivity<ActivitySinglesDetailBinding, ClubViewModel> implements View.OnClickListener,PhotoSelDialog.OnSelectListener {
    public static final String CONTEST_ARRANGE_ID = "contest_arrangeId";
    private String strContestArrangeId = "";
    public static final String CLICK_TYPE = "clickType";
    private String clickType = "";
    private SinglesDetailAdapter singlesDetailAdapter;

    QueryChessInfoBean queryChessData;

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
    public static final String TAG = SinglesDetailActivity.class.getSimpleName();

    private boolean isChange = false;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_singles_detail;
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

                mBinding.rvList.scrollToPosition(singlesDetailAdapter.getData().size()-1);
            }

            @Override
            public void keyBoardHide(int height) {

            }
        });
        singlesDetailAdapter = new SinglesDetailAdapter(true);
        mBinding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvList.setAdapter(singlesDetailAdapter);

        mViewModel.getUploadTokenLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
//                if (responseBean.isSuccess()) {
                if (responseBean.getErrorCode().equals("200")) {

                    UploadManager uploadManager = new UploadManager();
                    // 设置图片名字
                    uploadImage(picPath, responseBean.getData().toString(), uploadManager, key);
                } else {
                    toast("暂无权限");
                }
            }
        });
        mViewModel.suspendWaiverLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                /*判断点击的是不是暂停*/
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
                customDialogConfirm11.dismiss();
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    toast("提交成功");
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();

                } else {
                    toast("" + responseBean.getMessage());
                    QueryChessInfoBean.XlClubContestArrangeChessesBean xlClubContestArrangeChessesBean = singlesDetailAdapter.getData().get(EndPosition);
                    xlClubContestArrangeChessesBean.setCanChange(isSuccess);
                    singlesDetailAdapter.setData(EndPosition, xlClubContestArrangeChessesBean);
                }
            }
        });
        mViewModel.uploadContestImgLiveData.observe(this, responseBean -> {
            if (responseBean.getErrorCode().equals("200")) {
                toast("图片上传成功");
            } else {
                toast("图片上传失败：" + responseBean.getMessage());
            }
        });
        mViewModel.updateXlClubContestArrangeChessLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {


                if (responseBean.getErrorCode().equals("200")) {
                    if (isChange) {
                        toast("修改成功");
                    } else {
                        toast("操作成功");
                    }

                    /*調用接口*/
                    List<QueryChessInfoBean.XlClubContestArrangeChessesBean> data2 = singlesDetailAdapter.getData();
                    QueryChessInfoBean.XlClubContestArrangeChessesBean xlClubContestArrangeChessesBean2 = data2.get(EndPosition);
                    xlClubContestArrangeChessesBean2.setTextSubmit(false);
                    xlClubContestArrangeChessesBean2.setFirst(false);
                    Log.e(TAG, "onChanged: 结束的点击的这个默认设为false" );
                    xlClubContestArrangeChessesBean2.setLeftIntegral("" + leftScore);
                    xlClubContestArrangeChessesBean2.setRightIntegral("" + rightScore);
                    data2.set(EndPosition, xlClubContestArrangeChessesBean2);
                    Log.e(TAG, "onClick: data===" + new Gson().toJson(data2));
                    Log.e(TAG, "notifyData: 确定");


                    int leftWinCount = 0;
                    int rightWinCount = 0;
                    for (int i = 0; i < data2.size(); i++) {
                        QueryChessInfoBean.XlClubContestArrangeChessesBean data = data2.get(i);
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


                    for (int m = 0; m < data2.size(); m++) {
                        QueryChessInfoBean.XlClubContestArrangeChessesBean xlClubContestArrangeChessesBean = data2.get(m);
                        if (!TextUtils.isEmpty(xlClubContestArrangeChessesBean.getLeftIntegral()) || !TextUtils.isEmpty(xlClubContestArrangeChessesBean.getRightIntegral())) {
                            xlClubContestArrangeChessesBean.setTextSubmit(false);
                            xlClubContestArrangeChessesBean.setFirst(false);
                            Log.e(TAG, "onChanged: 积分都有了设为true  position===="+m );
                        } else {
                            xlClubContestArrangeChessesBean.setTextSubmit(true);
                            xlClubContestArrangeChessesBean.setFirst(true);
                            Log.e(TAG, "onChanged: 积分都没有 设为true" );
                        }
                        data2.set(m, xlClubContestArrangeChessesBean);
                    }
                    if (leftWinCount == matchCount || rightWinCount == matchCount) {

                    } else {
                        if (updatePosition != data2.size() - 1) {
                            for (int i = updatePosition; i < data2.size(); i++) {
                                QueryChessInfoBean.XlClubContestArrangeChessesBean xlClubContestArrangeChessesBeanmn = data2.get(i);
                                if (i == updatePosition + 1) {
                                    //判断是否相等
                                    Log.e(TAG, "onChanged: 判断是否相等 如果相等 设为true" );
                                    xlClubContestArrangeChessesBeanmn.setFirst(true);
                                    data2.set(i, xlClubContestArrangeChessesBeanmn);
                                } else {
                                    //判断是否相等
                                    Log.e(TAG, "onChanged: 不相等 设为false" );
                                    xlClubContestArrangeChessesBeanmn.setFirst(false);
                                    data2.set(i, xlClubContestArrangeChessesBeanmn);
                                }
                            }
                        }
                    }
//                    Log.e(TAG, "onChanged: 修改后"+new Gson().toJson(data2));

                    if (data2 != null && data2.size() > 0) {
                        if (leftWinCount == matchCount || rightWinCount == matchCount) {
                            for (int i = matchCount; i < data2.size(); i++) {
                                QueryChessInfoBean.XlClubContestArrangeChessesBean xlClubContestArrangeChessesBean = data2.get(i);
                                xlClubContestArrangeChessesBean.setFirst(false);
                                Log.e(TAG, "onChanged: 如果有一方赢了  设为false" );
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
        mViewModel.queryChessInfoBeanLiveData.observe(this, new Observer<ResponseBean<QueryChessInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<QueryChessInfoBean> queryChessInfoBeanResponseBean) {
//                if (queryChessInfoBeanResponseBean.isSuccess()) {
                if (queryChessInfoBeanResponseBean.getErrorCode().equals("200")) {

                    queryChessData = queryChessInfoBeanResponseBean.getData();
                    if (queryChessData != null) {
                        Log.e(TAG, "onChanged: 请求数据");
                        showPicViewForContest(queryChessData.getImgUrl());
                        ImageLoader.loadImage(mBinding.viewLeftOval, queryChessData.getLeft1HeadImg(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                        ImageLoader.loadImage(mBinding.viewRightOval, queryChessData.getRight1HeadImg(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);


                        mBinding.tvLeftName.setText("" + queryChessData.getLeft1Name());
                        mBinding.tvRightName.setText("" + queryChessData.getRight1Name());

                        mBinding.tvLeftClubName.setText("" + queryChessData.getLeftTeamNum());
                        mBinding.tvRightClubName.setText("" + queryChessData.getRightTeamNum());

//                        private int gameType = -1;//1 是三局两胜  2是五局三胜  3 是七局四胜
                        switch (queryChessData.getMatchMethod()) {
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
                        int leftWavier = queryChessData.getLeftWavier();
                        if (leftWavier == 1) {
                            leftWaverType = 1;
                            mBinding.tvLeftWaiver.setEnabled(false);
                            mBinding.tvLeftWaiver.setBackgroundResource(R.drawable.img_left_gray_rect);
                        }
                        int rightWavier = queryChessData.getRightWavier();
                        if (rightWavier == 1) {
                            rightWaverType = 1;
                            mBinding.tvRightWavier.setEnabled(false);
                            mBinding.tvRightWavier.setBackgroundResource(R.drawable.img_right_gray_rect);
                        }
                        int leftSuspend = queryChessData.getLeftSuspend();
                        if (leftSuspend == 1) {

                            mBinding.tvLeftPause.setBackgroundColor(Color.parseColor("#CCCCCC"));
                            mBinding.tvLeftPause.setEnabled(false);
                        } else {
                            mBinding.tvLeftPause.setBackgroundColor(Color.parseColor("#FFCD66"));
                            mBinding.tvLeftPause.setEnabled(true);
                        }
                        int rightSuspend = queryChessData.getRightSuspend();
                        if (rightSuspend == 1) {
                            mBinding.tvRightPause.setBackgroundColor(Color.parseColor("#CCCCCC"));
                            mBinding.tvRightPause.setEnabled(false);
                        } else {
                            mBinding.tvRightPause.setBackgroundColor(Color.parseColor("#FFCD66"));
                            mBinding.tvRightPause.setEnabled(true);
                        }
                        List<QueryChessInfoBean.XlClubContestArrangeChessesBean> xlClubContestArrangeChesses = queryChessData.getXlClubContestArrangeChesses();

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
//                        for (int m1 = 0; m1 < xlClubContestArrangeChesses.size(); m1++) {
//                            QueryChessInfoBean.XlClubContestArrangeChessesBean xlClubContestArrangeChessesBean1 = xlClubContestArrangeChesses.get(m1);
//                            if (!TextUtils.isEmpty(xlClubContestArrangeChessesBean1.getLeftIntegral()) || !TextUtils.isEmpty(xlClubContestArrangeChessesBean1.getRightIntegral())) {
//                                xlClubContestArrangeChessesBean1.setTextSubmit(false);
//                                xlClubContestArrangeChessesBean1.setFirst(false);
//                            } else {
//                                if (m1 == 0) {
//                                    xlClubContestArrangeChessesBean1.setFirst(true);
//                                } else {
//                                    xlClubContestArrangeChessesBean1.setFirst(false);
//                                }
//                                xlClubContestArrangeChessesBean1.setTextSubmit(true);
//                            }
//                            xlClubContestArrangeChesses.set(m1, xlClubContestArrangeChessesBean1);
//                        }
                        int emptyFirstPosition = -1;
                        boolean isFirst = true;
                        for (int m = 0; m < xlClubContestArrangeChesses.size(); m++) {
                            QueryChessInfoBean.XlClubContestArrangeChessesBean xlClubContestArrangeChessesBean = xlClubContestArrangeChesses.get(m);
//                            xlClubContestArrangeChessesBean.setFirst(true);
                            if (!TextUtils.isEmpty(xlClubContestArrangeChessesBean.getLeftIntegral()) || !TextUtils.isEmpty(xlClubContestArrangeChessesBean.getRightIntegral())) {
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
                                QueryChessInfoBean.XlClubContestArrangeChessesBean xlClubContestArrangeChessesBean2 = xlClubContestArrangeChesses.get(n);

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

                                    QueryChessInfoBean.XlClubContestArrangeChessesBean xlClubContestArrangeChessesBean = xlClubContestArrangeChesses.get(i);
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
                        ToastUtils.showToast(SinglesDetailActivity.this, "比分填写不规范，请重新填写");

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
                            ToastUtils.showToast(SinglesDetailActivity.this, "比分填写不规范，请重新填写");

                        }
                    }
                }

            }


        });
    }

    private int updatePosition = 0;

    //    private
    private void notifyData(int scoreOne, int scoreTwo, QueryChessInfoBean.XlClubContestArrangeChessesBean item, int position, String textStatus, boolean isFirst) {
        myItem = item;
        EndPosition = position;
        Log.e(TAG, "notifyData: textStatus===" + textStatus);
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

    /*点击确定和修改比分弹窗*/
    private void showOneConfirm(int score1, int score2,
                                QueryChessInfoBean.XlClubContestArrangeChessesBean item,
                                int position, String textStatus, boolean isFirst) {
        CustomDialog customDialogConfirm = new CustomDialog(SinglesDetailActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_bureau_scores_single);
        customDialogConfirm.show();
        View customView = customDialogConfirm.getCustomView();
        TextView tvSure = customView.findViewById(R.id.tv_sure);
//        TextView tvScore = customView.findViewById(R.id.tv_score);
        TextView tvLeftName = customView.findViewById(R.id.tv_left_name);
        TextView tvRightName = customView.findViewById(R.id.tv_right_name);
        ImageView ivleftLogo = customView.findViewById(R.id.iv_left_logo);
        ImageView ivRightLogo = customView.findViewById(R.id.iv_right_logo);
        ImageLoader.loadImage(ivleftLogo, queryChessData.getLeft1HeadImg(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
        ImageLoader.loadImage(ivRightLogo, queryChessData.getRight1HeadImg(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
//        tvScore.setText("" + score1 + ":" + score2);
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
        tvLeftName.setText("" + queryChessData.getLeft1Name());
        tvRightName.setText("" + queryChessData.getRight1Name());
        Log.e(TAG, "showOneConfirm: TextSubmit===" + item.isTextSubmit());

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialogConfirm != null) {
                    customDialogConfirm.dismiss();
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
                if (customDialogConfirm != null) {
                    customDialogConfirm.dismiss();
                }

            }
        });
    }

    CustomDialog customDialogConfirm11;

    /*总的结束比赛的方法*/
    private void showOneConfirmExit(int leftAllCount, int rightAllCount) {
        try {


            customDialogConfirm11 = new CustomDialog(SinglesDetailActivity.this);
            customDialogConfirm11.setCustomView(R.layout.pop_end_game_single);
            customDialogConfirm11.show();
            View customView = customDialogConfirm11.getCustomView();
            /*先判断总体Count 两边那个等于Count*/
            TextView tvLeftName = customView.findViewById(R.id.tv_left_name);
            TextView tvRightName = customView.findViewById(R.id.tv_right_name);
            TextView tvLeftWinner = customView.findViewById(R.id.tv_left_winner);
            TextView tvRightWinner = customView.findViewById(R.id.tv_right_winner);
            CircleImageView imgLeftLogo = customView.findViewById(R.id.img_left_logo);
            CircleImageView imgRightLogo = customView.findViewById(R.id.img_right_logo);

            ImageLoader.loadImage(imgLeftLogo, queryChessData.getLeft1HeadImg(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
            ImageLoader.loadImage(imgRightLogo, queryChessData.getRight1HeadImg(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);

            leftName = queryChessData.getLeft1Name();
            rightName = queryChessData.getRight1Name();
            tvLeftName.setText("" + leftName);
            tvRightName.setText("" + rightName);

//        TextView tvScore = customView.findViewById(R.id.tv_score);
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
                    if (customDialogConfirm11 != null) {
                        if (queryChessData != null) {
                            showLoading();
                            if (!TextUtils.isEmpty(imgUrl)) {
                                mViewModel.uploadContestImg(strContestArrangeId, imgUrl);
                            }
                            if (leftWaverType == 1 && rightWaverType == 1) {
                                mViewModel.waiverContest("" + queryChessData.getId(), "", "", "",
                                        "" + 0, "" + 0, "" + leftWaverType, "" + rightWaverType);
                            } else {
                                if (leftAllCount > rightAllCount) {
                                    mViewModel.waiverContest("" + queryChessData.getId(), "" + queryChessData.getLeft1Id(), "",
                                            "" + queryChessData.getTeamLeftId(),
                                            "" + leftAllCount, "" + rightAllCount
                                            , "" + leftWaverType, "" + rightWaverType);
                                } else if (leftAllCount < rightAllCount) {
                                    mViewModel.waiverContest("" + queryChessData.getId(), "" + queryChessData.getRight1Id(), "", "" + queryChessData.getTeamRightId(),
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
                    if (customDialogConfirm11 != null) {
                        customDialogConfirm11.dismiss();
                    }

                }
            });
        } catch (Exception e) {

        }
    }


    /*暂停*/
    private void showOneKeepGame() {
        customDialogConfirm = new CustomDialog(SinglesDetailActivity.this);
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
            case R.id.cl_pre_add_pic:
            case R.id.atv_reload_pic:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                PhotoSelDialog dialog = new PhotoSelDialog(this);
                dialog.setClickListener(SinglesDetailActivity.this);
                dialog.show();
                break;
            case R.id.tv_confirm_grade_end://结束比赛 掉接口
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                leftName = queryChessData.getLeft1Name();
                rightName = queryChessData.getRight1Name();
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
                new MessageDialogBuilder(SinglesDetailActivity.this)
                        .setMessage("")
                        .setTvTitle("此运动员确认要暂停吗?")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (queryChessData != null) {
                                    pauseType = 1;
                                    mViewModel.suspendWaiver("" + queryChessData.getId(), "", "", "", "" + 1);
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
                new MessageDialogBuilder(SinglesDetailActivity.this)
                        .setMessage("")
                        .setTvTitle("此运动员确认要暂停吗?")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (queryChessData != null) {
                                    pauseType = 1;
                                    mViewModel.suspendWaiver("" + queryChessData.getId(), "", "", "" + 1, "");
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
                new MessageDialogBuilder(SinglesDetailActivity.this)
                        .setMessage("弃权后代表选手放弃本盘比赛剩余的所有场数")
                        .setTvTitle("您确认此运动员" + queryChessData.getLeft1Name() + "要弃权吗？")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (queryChessData != null) {
                                    leftWaverType = 1;
                                    clickType = "2";
                                    mBinding.tvLeftWaiver.setEnabled(false);
                                    mBinding.tvLeftWaiver.setBackgroundResource(R.drawable.img_left_gray_rect);
                                    mViewModel.suspendWaiver("" + queryChessData.getId(), "" + leftWaverType, "", "", "");
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
                new MessageDialogBuilder(SinglesDetailActivity.this)
                        .setMessage("弃权后代表选手放弃本盘比赛剩余的所有场数")
                        .setTvTitle("您确认此运动员" + queryChessData.getRight1Name() + "要弃权吗？")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (queryChessData != null) {
                                    mBinding.tvRightWavier.setEnabled(false);
                                    mBinding.tvRightWavier.setBackgroundResource(R.drawable.img_right_gray_rect);
                                    rightWaverType = 1;
                                    clickType = "2";
                                    mViewModel.suspendWaiver("" + queryChessData.getId(), "", "" + rightWaverType, "", "");
                                } else {
                                    toast("页面异常");
                                }
                            }
                        })
                        .show();

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

    private static final int IMAGE_PICKER = 10001;
    private static final int REQUEST_CODE_SELECT = 10002;

    @Override
    public void takePhoto() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new RxBusSubscriber<Boolean>() {
                    @Override
                    protected void onEvent(Boolean aBoolean) {
                        if (aBoolean) {
                            //申请的权限全部允许
                            //图片- 拍照
                            Intent intent = new Intent(SinglesDetailActivity.this, ImageGridActivity.class);
                            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                            startActivityForResult(intent, REQUEST_CODE_SELECT);
//                            ImagePicker imagePicker = ImagePicker.getInstance();
//                            imagePicker.setMultiMode(false);
//                            imagePicker.setShowCamera(false);
//                            imagePicker.setCrop(false);
//                            imagePicker.setFocusHeight(SystemHelper.getScreenInfo(PersonalInformationActivity.this).heightPixels);
//                            imagePicker.setFocusWidth(SystemHelper.getScreenInfo(PersonalInformationActivity.this).widthPixels);
//                            imagePickerUtil.takePhoto(PersonalInformationActivity.this, REQUEST_CODE_TAKE_PHOTO, PersonalInformationActivity.this);
                        } else {
                            //只要有一个权限被拒绝，就会执行
                            toast("未授权权限，部分功能不能使用");
                        }
                    }
                });
    }
    @Override
    public void selectPhoto() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        ImagePicker.getInstance().setMultiMode(false).setCrop(false);
        startActivityForResult(intent, IMAGE_PICKER);
    }

    /**
     * 比赛图片views相关展示逻辑
     */
    private void showPicViewForContest(String imgUrl) {
        if (!TextUtils.isEmpty(imgUrl)) {
            mBinding.clPreAddPic.setVisibility(View.GONE);
            mBinding.aivPicContest.setVisibility(View.VISIBLE);
            mBinding.atvReloadPic.setVisibility(View.VISIBLE);
            ImageLoader.loadImage(mBinding.aivPicContest, imgUrl);
        } else {
            mBinding.clPreAddPic.setVisibility(View.VISIBLE);
            mBinding.aivPicContest.setVisibility(View.GONE);
            mBinding.atvReloadPic.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                //选择相册
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Log.e(TAG, "onActivityResult:1111===== " + images.size());
                showPicViewForContest(images.get(0).path);

                String path = images.get(0).path;
//                File file2 = BitmapUtils.compressImageToSD(path, 30);
                File file2 = CompressHelper.getDefault(this).compressToFile(new File(path));
                Log.e(TAG, "onActivityResult: "+file2.length() );
                uploadImg2QiNiu(file2.getPath());
            } else if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Log.e(TAG, "onActivityResult:2222===== " + images.size());
                showPicViewForContest(images.get(0).path);

                String path = images.get(0).path;
                File file2 = CompressHelper.getDefault(this).compressToFile(new File(path));
                Log.e(TAG, "onActivityResult: "+file2.length() );

//                File file2 = BitmapUtils.compressImageToSD(path, 30);
                uploadImg2QiNiu(file2.getPath());
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
    String key = "";
    String picPath = "";
    String imgUrl = "";
    private void uploadImg2QiNiu(String localpicPath) {
        // 设置图片名字
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 3) {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set) {
            sb.append("" + i);
        }
        key = "android_img_" + sdf.format(new Date()) + sb.toString();
        picPath = localpicPath;
        Log.e(TAG, "uploadImg2QiNiu: localPath===" + localpicPath);
        mViewModel.getUploadToken(key);
    }

    private void uploadImage(String picPath, String token, UploadManager uploadManager, String key) {
        Log.e(TAG, "uploadImage: picPath===" + picPath);
        Log.e(TAG, "uploadImage: token===" + token);
        Log.e(TAG, "uploadImage:key ===" + key);
//        uploadManager.put(picPath, key, Auth.create(Constants.AccessKey, Constants.SecretKey).uploadToken("xlttsports"), new UpCompletionHandler() {
        uploadManager.put(picPath, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                // info.error中包含了错误信息，可打印调试
                dismissLoading();
                // 上传成功后将key值上传到自己的服务器
                if (info.isOK()) {
                    Log.e(TAG, "token===" + token);
                    String headpicPath = "http://images.xlttsports.com/" + key;
                    Log.e(TAG, "complete: " + headpicPath);

                    imgUrl = "" + headpicPath;
                } else {
                    toast("上传失败");
                    Log.e(TAG, "complete:上传失败 error===" + new Gson().toJson(info));
                }
                Log.e(TAG, "complete: error===" + new Gson().toJson(info));

            }
        }, null);
    }
}