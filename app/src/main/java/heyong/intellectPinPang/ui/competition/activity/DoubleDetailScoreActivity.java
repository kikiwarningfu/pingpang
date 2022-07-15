package heyong.intellectPinPang.ui.competition.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.SinglesScoreDetailAdapter;
import heyong.intellectPinPang.bean.competition.QueryChessInfoBean;
import heyong.intellectPinPang.databinding.ActivityDoubleScoreDetailBinding;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;

/**
 * 队伍内部查看比赛   查看单打比赛成绩界面UI
 */
public class DoubleDetailScoreActivity extends BaseActivity<ActivityDoubleScoreDetailBinding, ClubViewModel> implements View.OnClickListener {
    public static final String CONTEST_ARRANGE_ID = "contest_arrangeId";
    private String strContestArrangeId = "";
    public static final String CLICK_TYPE = "clickType";
    private String clickType = "";
    private SinglesScoreDetailAdapter singlesDetailAdapter;
    QueryChessInfoBean queryChessData;

    private int leftAllWinCount = 0;
    private int rightAllWinCount = 0;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_double_score_detail;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        strContestArrangeId = getIntent().getStringExtra(CONTEST_ARRANGE_ID);
        clickType = getIntent().getStringExtra(CLICK_TYPE);

        getData();

        singlesDetailAdapter = new SinglesScoreDetailAdapter(true);
        mBinding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvList.setAdapter(singlesDetailAdapter);


        mViewModel.queryChessInfoBeanLiveData.observe(this, new Observer<ResponseBean<QueryChessInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<QueryChessInfoBean> queryChessInfoBeanResponseBean) {
//                if (queryChessInfoBeanResponseBean.isSuccess()) {
                if (queryChessInfoBeanResponseBean.getErrorCode().equals("200")) {

                    queryChessData = queryChessInfoBeanResponseBean.getData();
                    if (queryChessData != null) {
                        mBinding.tvLeft1Name.setText("" + queryChessData.getLeft1Name());
                        mBinding.tvLeft2Name.setText("" + queryChessData.getLeft2Name());
                        mBinding.tvRight1Name.setText("" + queryChessData.getRight1Name());
                        mBinding.tvRight2Name.setText("" + queryChessData.getRight2Name());
                        List<QueryChessInfoBean.XlClubContestArrangeChessesBean> xlClubContestArrangeChesses = queryChessData.getXlClubContestArrangeChesses();
                        if (xlClubContestArrangeChesses != null && xlClubContestArrangeChesses.size() > 0) {
                            singlesDetailAdapter.setNewData(xlClubContestArrangeChesses);
                        } else {
                            singlesDetailAdapter.setNewData(new ArrayList<>());
                        }
                        leftAllWinCount = queryChessData.getLeftWinCount();
                        rightAllWinCount = queryChessData.getRightWinCount();

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


                    }
                } else {
                    toast("" + queryChessInfoBeanResponseBean.getMessage());
                }
            }
        });


    }

    private void getData() {
        mViewModel.queryChessInfo(strContestArrangeId, "" + clickType);
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