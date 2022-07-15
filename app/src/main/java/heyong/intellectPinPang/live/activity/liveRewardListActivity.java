package heyong.intellectPinPang.live.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.LiveRewardListAdapter;
import heyong.intellectPinPang.bean.competition.LiveMatchPublishListBean;
import heyong.intellectPinPang.databinding.ActivityLiveRewardListBinding;
import heyong.intellectPinPang.live.model.LiveViewModel;

/**
 * 直播悬赏榜单
 */
public class liveRewardListActivity extends BaseActivity<ActivityLiveRewardListBinding, LiveViewModel> implements View.OnClickListener {
    public static final String MATCH_ID = "match_id";
    private String strMatchId = "";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_live_reward_list;
    }

    @Override
    protected void onResume() {
        super.onResume();

        mViewModel.liveMatchPublishList(strMatchId);
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        strMatchId = getIntent().getStringExtra(MATCH_ID);


        mViewModel.liveMatchPublishListLiveData.observe(this, new Observer<ResponseBean<LiveMatchPublishListBean>>() {
            @Override
            public void onChanged(ResponseBean<LiveMatchPublishListBean> liveMatchPublishListBeanResponseBean) {
                if (liveMatchPublishListBeanResponseBean.getErrorCode().equals("200")) {
                    LiveMatchPublishListBean data = liveMatchPublishListBeanResponseBean.getData();
                    if (data != null) {
                        List<LiveMatchPublishListBean.LiveMatchsBean> liveMatchs = data.getLiveMatchs();
                        String matchName = data.getMatchName();

                        mBinding.tvMatchTitle.setText("" + matchName);
                        if (liveMatchs != null && liveMatchs.size() > 0) {
                            LiveRewardListAdapter liveRewardListAdapter = new LiveRewardListAdapter();
                            mBinding.rvLiveRewardList.setAdapter(liveRewardListAdapter);
                            mBinding.rvLiveRewardList.setLayoutManager(new LinearLayoutManager(liveRewardListActivity.this, RecyclerView.VERTICAL, false));
                            liveRewardListAdapter.setNewData(liveMatchs);
                            liveRewardListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    boolean fastClick = isFastClick();
                                    if (!fastClick) {
                                        LiveMatchPublishListBean.LiveMatchsBean liveMatchsBean = liveRewardListAdapter.getData().get(position);
                                        int requestId = liveMatchsBean.getRequestId();
                                        Intent intent = new Intent(liveRewardListActivity.this, DetailOfOfferActivity.class);
                                        intent.putExtra(DetailOfOfferActivity.REQUEST_ID, "" + requestId);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }

                    }
                } else {
                    toast("" + liveMatchPublishListBeanResponseBean.getMessage());
                }
            }
        });


    }

    @Override
    public void onClick(View v) {

    }
}