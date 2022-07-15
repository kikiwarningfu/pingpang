package heyong.intellectPinPang.adapter.game;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MatchExperienceBean;
import heyong.intellectPinPang.databinding.ItemRecentGamesAthletesBinding;

public class RecentGamesAthletesAdapter extends BaseQuickAdapter<MatchExperienceBean.ListBean, BaseViewHolder> {
    public RecentGamesAthletesAdapter() {
        super(R.layout.item_recent_games_athletes);
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchExperienceBean.ListBean item) {
        ItemRecentGamesAthletesBinding binding= DataBindingUtil.bind(helper.getConvertView());
        binding.tvMatchTitle.setText(""+item.getMatchTitle());
        ImageLoader.loadImage(binding.ivLogo,item.getMatchImgUrl());
        binding.tvTime.setText(""+item.getEndTime());
    }
}
