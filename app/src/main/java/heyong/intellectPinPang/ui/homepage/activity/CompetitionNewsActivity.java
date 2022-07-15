package heyong.intellectPinPang.ui.homepage.activity;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.Arrays;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.CompetitionNewsAdapter;
import heyong.intellectPinPang.databinding.ActivityCompetitionNewsBinding;

/**
 * 赛事新闻界面
 */
public class CompetitionNewsActivity extends BaseActivity<ActivityCompetitionNewsBinding, BaseViewModel> implements View.OnClickListener {

    CompetitionNewsAdapter competitionAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_competition_news;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        competitionAdapter = new CompetitionNewsAdapter();
        mBinding.rvCompetitionNews.setAdapter(competitionAdapter);
        mBinding.rvCompetitionNews.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        competitionAdapter.setNewData(Arrays.asList("1", "1", "1"));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}