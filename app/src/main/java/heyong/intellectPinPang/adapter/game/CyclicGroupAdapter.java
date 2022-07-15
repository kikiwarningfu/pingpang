package heyong.intellectPinPang.adapter.game;

import android.graphics.Color;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.ClubContestTeamBean;
import heyong.intellectPinPang.databinding.ItemCyclicGroupBinding;

/**
 * 循环分组
 */
public class CyclicGroupAdapter extends BaseQuickAdapter<ClubContestTeamBean, BaseViewHolder> {

    public CyclicGroupAdapter() {
        super(R.layout.item_cyclic_group);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClubContestTeamBean item) {
        ItemCyclicGroupBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvName.setText(item.getTeamNum());
        if (item.isSelect()) {
            binding.tvName.setTextColor(Color.parseColor("#FFFFFF"));
            binding.tvName.setRoundColor(Color.parseColor("#4795ED"));
        } else {
            binding.tvName.setRoundColor(Color.parseColor("#F6F6F6"));
            binding.tvName.setTextColor(Color.parseColor("#333333"));
        }
    }
}
