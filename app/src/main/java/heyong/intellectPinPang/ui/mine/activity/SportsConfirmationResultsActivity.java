package heyong.intellectPinPang.ui.mine.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xq.fasterdialog.dialog.CustomDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.SinglesScoreDetailOfficialAdapter;
import heyong.intellectPinPang.bean.competition.XlMatchInfoArrangeChessBean;
import heyong.intellectPinPang.databinding.ActivityConfirmationResultsSportsBinding;
import heyong.intellectPinPang.event.SwipMessageEvent;
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;

/**
 * 教练员   确认比赛成绩界面UI
 */
public class SportsConfirmationResultsActivity extends BaseActivity<ActivityConfirmationResultsSportsBinding, HomePageViewModel> {
    private String ids = "";
    private String relationId = "";
    private String message = "";
    private String time = "";
    private String title = "";
    private boolean isShowRefeeOne = false;
    private boolean isShowSignOne = false;
    private SinglesScoreDetailOfficialAdapter singlesDetailOneAdapter;
    private boolean isShowRefee = false;
    private boolean isShowSign = false;
    private SinglesScoreDetailOfficialAdapter singlesDetailAdapter;
    private String status = "";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_confirmation_results_sports;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        ids = getIntent().getStringExtra("ids");
        relationId = getIntent().getStringExtra("relationId");
        message = getIntent().getStringExtra("message");
        time = getIntent().getStringExtra("updateTime");
        title = getIntent().getStringExtra("title");
        status = getIntent().getStringExtra("status");

        mBinding.tvContent.setText("" + message);
        mBinding.tvTime.setText("" + time);
        mBinding.tvTitle.setText("" + title);

        mBinding.llOneContent.setVisibility(View.GONE);
        mBinding.llTwoContent.setVisibility(View.GONE);
        mViewModel.arrangeChess(relationId);

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

        mViewModel.arrangeChessLiveData.observe(this, new Observer<ResponseBean<XlMatchInfoArrangeChessBean>>() {
            @Override
            public void onChanged(ResponseBean<XlMatchInfoArrangeChessBean> xlMatchInfoArrangeChessBeanResponseBean) {
//                if (xlMatchInfoArrangeChessBeanResponseBean.isSuccess()) {
                if (xlMatchInfoArrangeChessBeanResponseBean.getErrorCode().equals("200")) {

                    XlMatchInfoArrangeChessBean data = xlMatchInfoArrangeChessBeanResponseBean.getData();
                    int itemType = Integer.parseInt(data.getItemType());
                    if (itemType == 1) {

                    } else if (itemType == 2) {
                        singlesDetailOneAdapter = new SinglesScoreDetailOfficialAdapter(true);
                        mBinding.rvOneContentList.setLayoutManager(new LinearLayoutManager(SportsConfirmationResultsActivity.this, RecyclerView.VERTICAL, false));
                        mBinding.rvOneContentList.setAdapter(singlesDetailOneAdapter);
                        //单打
                        mBinding.llOneContent.setVisibility(View.VISIBLE);
                        if (data != null) {
                            List<XlMatchInfoArrangeChessBean.ChessBean> chess = data.getChess();
                            ImageLoader.loadImage(mBinding.viewLeftOval, data.getHeadImg1(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                            ImageLoader.loadImage(mBinding.viewRightOval, data.getHeadImg3(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                            mBinding.tvLeftName.setText("" + data.getPlayer1());
                            mBinding.tvRightName.setText("" + data.getPlayer3());
                            mBinding.tvOneContentLeftWinCount.setText("" + data.getLeftCount());
                            mBinding.tvOneContentRightWinCount.setText("" + data.getRightCount());
                            if (!TextUtils.isEmpty(data.getClub1Name())) {
                                mBinding.tvOneContentLeftClubName.setText("" + data.getPlayer1());
                                mBinding.tvOneContentRightClubName.setText("" + data.getPlayer3());
                                mBinding.tvLeftClubNameTwo.setText("" + data.getPlayer1());
                                mBinding.tvRightClubNameTwo.setText("" + data.getPlayer3());
                            } else {
                                mBinding.tvOneContentLeftClubName.setText("" + data.getPlayer1());
                                mBinding.tvOneContentRightClubName.setText("" + data.getPlayer3());
                                mBinding.tvLeftClubNameTwo.setText("" + data.getPlayer1());
                                mBinding.tvRightClubNameTwo.setText("" + data.getPlayer3());
                            }

                            List<XlMatchInfoArrangeChessBean.ChessBean> xlClubContestArrangeChesses = data.getChess();
                            if (xlClubContestArrangeChesses != null && xlClubContestArrangeChesses.size() > 0) {
                                ViewGroup.LayoutParams layoutParams = mBinding.rvOneContentList.getLayoutParams();
                                layoutParams.height = 200 * xlClubContestArrangeChesses.size();
                                mBinding.rvOneContentList.setLayoutParams(layoutParams);
                                singlesDetailOneAdapter.setNewData(xlClubContestArrangeChesses);
                            } else {
                                singlesDetailOneAdapter.setNewData(new ArrayList<>());
                            }
//                            int status = Integer.parseInt(data.getStatus());
//                            if (status == 3) {//左边已确认成绩
//                                isShowSignOne = false;
//                                ImageLoader.loadImage(mBinding.ivSignOne, data.getPlayer1Q());
//                            } else if (status == 4) {//右边已经确认
//                                isShowSignOne = false;
//                                ImageLoader.loadImage(mBinding.ivSignThree, data.getPlayer3Q());
//                            } else if (status == 5) {//两边都确认
//                                isShowSignOne = true;
//                                ImageLoader.loadImage(mBinding.ivSignOne, data.getPlayer1Q());
//                                ImageLoader.loadImage(mBinding.ivSignThree, data.getPlayer3Q());
//                            } else {
//                                isShowSignOne = false;
//                            }
//                            if (!TextUtils.isEmpty(data.getRefeeQ()) || !data.getRefeeQ().equals("2")) {
//                                ImageLoader.loadImage(mBinding.ivSignTwo, data.getRefeeQ());
//                                isShowRefeeOne = true;
//                            } else {
//                                isShowRefeeOne = false;
//                            }
//                            if (isShowSignOne && isShowRefeeOne) {
//                                ImageLoader.loadImage(mBinding.ivOneContentZhang, data.getZhangImg());
//                            } else {
//                                mBinding.ivOneContentZhang.setVisibility(View.GONE);
//                            }


                        } else {
                            toast("" + xlMatchInfoArrangeChessBeanResponseBean.getMessage());
                        }
                    } else if (itemType == 3 || itemType == 4) {
                        //双打和混双
                        singlesDetailAdapter = new SinglesScoreDetailOfficialAdapter(true);
                        mBinding.rvList.setLayoutManager(new LinearLayoutManager(SportsConfirmationResultsActivity.this, RecyclerView.VERTICAL, false));
                        mBinding.rvList.setAdapter(singlesDetailAdapter);
                        mBinding.llTwoContent.setVisibility(View.VISIBLE);
                        if (data != null) {

                            List<XlMatchInfoArrangeChessBean.ChessBean> chess = data.getChess();
                            ImageLoader.loadImage(mBinding.ivLeft1Logo, data.getHeadImg1());
                            ImageLoader.loadImage(mBinding.ivLeft2Logo, data.getHeadImg3());
                            mBinding.tvLeft1Name.setText("" + data.getPlayer1());
                            mBinding.tvLeft2Name.setText("" + data.getPlayer3());

                            ImageLoader.loadImage(mBinding.ivRight1Logo, data.getHeadImg2());
                            ImageLoader.loadImage(mBinding.ivRight2Logo, data.getHeadImg4());
                            mBinding.tvLeft1Name.setText("" + data.getPlayer2());
                            mBinding.tvLeft2Name.setText("" + data.getPlayer4());


                            mBinding.tvLeftWinCount.setText("" + data.getLeftCount());
                            mBinding.tvRightWinCount.setText("" + data.getRightCount());

                            List<XlMatchInfoArrangeChessBean.ChessBean> xlClubContestArrangeChesses = data.getChess();
                            if (xlClubContestArrangeChesses != null && xlClubContestArrangeChesses.size() > 0) {
                                ViewGroup.LayoutParams layoutParams = mBinding.rvList.getLayoutParams();
                                layoutParams.height = 200 * xlClubContestArrangeChesses.size();
                                mBinding.rvList.setLayoutParams(layoutParams);
                                singlesDetailAdapter.setNewData(xlClubContestArrangeChesses);
                            } else {
                                singlesDetailAdapter.setNewData(new ArrayList<>());
                            }
                            int status = Integer.parseInt(data.getStatus());
                            if (status == 3) {//左边已确认成绩
                                isShowSign = false;
                                ImageLoader.loadImage(mBinding.ivLeftOne, data.getPlayer1Q());
                                ImageLoader.loadImage(mBinding.ivLeftTwo, data.getPlayer2Q());


                            } else if (status == 4) {//右边已经确认
                                isShowSign = false;
                                ImageLoader.loadImage(mBinding.ivRightOne, data.getPlayer3Q());
                                ImageLoader.loadImage(mBinding.ivRightTwo, data.getPlayer4Q());
                            } else if (status == 5) {//两边都确认
                                isShowSign = true;
                                ImageLoader.loadImage(mBinding.ivLeftOne, data.getPlayer1Q());
                                ImageLoader.loadImage(mBinding.ivLeftTwo, data.getPlayer2Q());
                                ImageLoader.loadImage(mBinding.ivRightOne, data.getPlayer3Q());
                                ImageLoader.loadImage(mBinding.ivRightTwo, data.getPlayer4Q());
                            } else {
                                isShowSign = false;
                            }
                            if (!TextUtils.isEmpty(data.getRefeeQ()) || !data.getRefeeQ().equals("2")) {
                                ImageLoader.loadImage(mBinding.ivRefree, data.getRefeeQ());
                                isShowRefee = true;
                            } else {
                                isShowRefee = false;
                            }

                            if (isShowSign && isShowRefee) {
                                ImageLoader.loadImage(mBinding.ivZhang, data.getZhangImg());
                            }
                            mBinding.tvLeftClubName.setText("" + data.getClub1Name());
                            mBinding.tvRightClubName.setText("" + data.getClub2Name());
                            if (isShowSign && isShowRefee) {
                                ImageLoader.loadImage(mBinding.ivZhang, data.getZhangImg());
                            } else {
                                mBinding.ivZhang.setVisibility(View.GONE);
                            }
                        } else {
                            toast("" + xlMatchInfoArrangeChessBeanResponseBean.getMessage());
                        }


                    }

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
        customDialogConfirm = new CustomDialog(SportsConfirmationResultsActivity.this);
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
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                score = 3;
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