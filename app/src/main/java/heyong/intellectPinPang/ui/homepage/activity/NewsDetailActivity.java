package heyong.intellectPinPang.ui.homepage.activity;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.NewsDetailAdapter;
import heyong.intellectPinPang.bean.homepage.NewsDetailBean;
import heyong.intellectPinPang.databinding.ActivityNewsDetailBinding;

/**
 * 新闻详情
 */
public class NewsDetailActivity extends BaseActivity<ActivityNewsDetailBinding, BaseViewModel> implements View.OnClickListener {
    //    NewsDetailBean
    List<NewsDetailBean> newData = new ArrayList<>();
    private NewsDetailAdapter newsDetailAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        newData.add(new NewsDetailBean(0));
        newData.add(new NewsDetailBean(1));
        newData.add(new NewsDetailBean(2));
        newsDetailAdapter = new NewsDetailAdapter();
        mBinding.rvNewsDetail.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvNewsDetail.setAdapter(newsDetailAdapter);
        newsDetailAdapter.setNewData(newData);


    }

    @Override
    public void onClick(View v) {

    }
}