package heyong.intellectPinPang.adapter.game;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.LiveMatchPublishListBean;
import heyong.intellectPinPang.databinding.ItemMyLiveRewardListBinding;

public class LiveRewardListAdapter extends BaseQuickAdapter<LiveMatchPublishListBean.LiveMatchsBean, BaseViewHolder> {


    public LiveRewardListAdapter() {
        super(R.layout.item_my_live_reward_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveMatchPublishListBean.LiveMatchsBean item) {
        ItemMyLiveRewardListBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvContent.setText("" + item.getTitle());
    }
}
