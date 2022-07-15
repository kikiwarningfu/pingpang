package heyong.intellectPinPang.ui.mine.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.AwardWinningScoreAdapter;
import heyong.intellectPinPang.bean.competition.AwardsMathListBean;
import heyong.intellectPinPang.databinding.ActivityAwardWinningScoreBinding;
import heyong.intellectPinPang.ui.mine.MineViewModel;

/**
 * 获奖成绩
 */
public class AwardWinningScoreActivity extends BaseActivity<ActivityAwardWinningScoreBinding, MineViewModel> implements View.OnClickListener {
    AwardWinningScoreAdapter awardWinningScoreAdapter;
    public static final String RANKING = "ranking";
    private String strRanking = "";
    public static final String ROLE = "role";
    private String strRole = "";
    public static final String ITEM = "item";
    private String strItem = "";
    int socreType = 2;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_award_winning_score;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);

        strRanking = getIntent().getStringExtra(RANKING);
        strRole = getIntent().getStringExtra(ROLE);
        strItem = getIntent().getStringExtra(ITEM);

        mViewModel.awardsMatchList("" + strRole, "" + socreType, "" + strRanking, "" + strItem);
        awardWinningScoreAdapter = new AwardWinningScoreAdapter();
        mBinding.rvAwardWinningScore.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvAwardWinningScore.setAdapter(awardWinningScoreAdapter);


        mViewModel.awardsMatchListLiveData.observe(this, new Observer<ResponseBean<List<AwardsMathListBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<AwardsMathListBean>> responseBean) {
                if (responseBean.getData() != null && responseBean.getData().size() > 0) {
                    awardWinningScoreAdapter.setNewData(responseBean.getData());
                    if (responseBean.getData().size() > 0) {
                        mBinding.tvMatchTitle.setText("" + responseBean.getData().get(0).getMathTitle());
                    }
                } else {
                    if(!responseBean.getErrorCode().equals("200")) {
                        toast("" + responseBean.getMessage());
                    }
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