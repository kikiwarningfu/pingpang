package heyong.intellectPinPang.ui.competition.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.SinglesScoreDetailOfficialAdapter;
import heyong.intellectPinPang.bean.competition.XlMatchInfoArrangeChessBean;
import heyong.intellectPinPang.databinding.ActivitySinglesScoreDetailOfficialBinding;
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel;

/**
 * 新单打成绩-游客
 */
public class SinglesDetailScoreNewActivity extends BaseActivity<ActivitySinglesScoreDetailOfficialBinding, HomePageViewModel> implements View.OnClickListener {
    private SinglesScoreDetailOfficialAdapter singlesDetailAdapter;

    public static final String IDS = "ids";
    private String ids = "";
    private boolean isShowRefee = false;
    private boolean isShowSign = false;
    private int leftAllWinCount = 0;
    private int rightAllWinCount = 0;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_singles_score_detail_official;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        ids = getIntent().getStringExtra(IDS);


        mViewModel.arrangeChess(ids);

        singlesDetailAdapter = new SinglesScoreDetailOfficialAdapter(true);
        mBinding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvList.setAdapter(singlesDetailAdapter);

        mViewModel.arrangeChessLiveData.observe(this, new Observer<ResponseBean<XlMatchInfoArrangeChessBean>>() {
            @Override
            public void onChanged(ResponseBean<XlMatchInfoArrangeChessBean> xlMatchInfoArrangeChessBeanResponseBean) {
//                if (xlMatchInfoArrangeChessBeanResponseBean.isSuccess()) {
                if(xlMatchInfoArrangeChessBeanResponseBean.getErrorCode().equals("200")){

                    XlMatchInfoArrangeChessBean data = xlMatchInfoArrangeChessBeanResponseBean.getData();
                    if (data != null) {
                        ImageLoader.loadImage(mBinding.viewLeftOval, data.getHeadImg1(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
                        ImageLoader.loadImage(mBinding.viewRightOval, data.getHeadImg3(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
                        mBinding.tvLeftName.setText("" + data.getPlayer1());
                        mBinding.tvRightName.setText("" + data.getPlayer3());
                        mBinding.tvLeftWinCount.setText("" + data.getLeftCount());
                        mBinding.tvRightWinCount.setText("" + data.getRightCount());
                        mBinding.tvLeftClubName.setText("" + data.getClub1Name());
                        mBinding.tvRightClubName.setText("" + data.getClub2Name());
                        mBinding.tvLeftClubNameTwo.setText("" + data.getClub1Name());
                        mBinding.tvRightClubNameTwo.setText("" + data.getClub2Name());

                        List<XlMatchInfoArrangeChessBean.ChessBean> xlClubContestArrangeChesses = data.getChess();
                        if (xlClubContestArrangeChesses != null && xlClubContestArrangeChesses.size() > 0) {
                            singlesDetailAdapter.setNewData(xlClubContestArrangeChesses);
                        } else {
                            singlesDetailAdapter.setNewData(new ArrayList<>());
                        }
                        leftAllWinCount = Integer.parseInt(data.getLeftCount());
                        rightAllWinCount = Integer.parseInt(data.getRightCount());

                        mBinding.tvLeftWinCount.setTextColor(Color.parseColor("#333333"));
                        mBinding.tvRightWinCount.setTextColor(Color.parseColor("#333333"));
                        if (leftAllWinCount > rightAllWinCount)//大于是红色 的 其他的是黑色
                        {
                            mBinding.tvLeftWinCount.setTextColor(Color.parseColor("#EB2F2F"));
                            mBinding.tvRightWinCount.setTextColor(Color.parseColor("#333333"));
                        }
                        if (rightAllWinCount > leftAllWinCount) {
                            mBinding.tvRightWinCount.setTextColor(Color.parseColor("#EB2F2F"));
                            mBinding.tvLeftWinCount.setTextColor(Color.parseColor("#333333"));
                        }
                        mBinding.tvLeftWinCount.setText("" + leftAllWinCount);
                        mBinding.tvRightWinCount.setText("" + rightAllWinCount);
//                        int status = Integer.parseInt(data.getStatus());
//                        if (status == 3) {//左边已确认成绩
//                            isShowSign = false;
//                            ImageLoader.loadImage(mBinding.ivSignOne, data.getPlayer1Q());
//                        } else if (status == 4) {//右边已经确认
//                            isShowSign = false;
//                            ImageLoader.loadImage(mBinding.ivSignThree, data.getPlayer3Q());
//                        } else if (status == 5) {//两边都确认
//                            isShowSign = true;
//                            ImageLoader.loadImage(mBinding.ivSignOne, data.getPlayer1Q());
//                            ImageLoader.loadImage(mBinding.ivSignThree, data.getPlayer3Q());
//                        } else {
//                            isShowSign = false;
//                        }
//                        if (!TextUtils.isEmpty(data.getRefeeQ()) || !data.getRefeeQ().equals("2")) {
//                            ImageLoader.loadImage(mBinding.ivSignTwo, data.getRefeeQ());
//                            isShowRefee = true;
//                        } else {
//                            isShowRefee = false;
//                        }
//
//                        if (isShowSign && isShowRefee) {
//                            ImageLoader.loadImage(mBinding.ivZhang, data.getZhangImg());
//                        }else
//                        {
//                            mBinding.ivZhang.setVisibility(View.GONE);
//                        }


                    } else {
                        toast("" + xlMatchInfoArrangeChessBeanResponseBean.getMessage());
                    }
                } else {
                    toast("" + xlMatchInfoArrangeChessBeanResponseBean.getMessage());
                }
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_finish:

                finish();
                break;

        }
    }
}