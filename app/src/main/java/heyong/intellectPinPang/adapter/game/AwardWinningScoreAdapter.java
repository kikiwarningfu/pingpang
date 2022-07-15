package heyong.intellectPinPang.adapter.game;

import android.graphics.Color;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.AwardsMathListBean;
import heyong.intellectPinPang.databinding.ItemAwardWinningScoreBinding;

/**
 * 获奖成绩
 */
public class AwardWinningScoreAdapter extends BaseQuickAdapter<AwardsMathListBean, BaseViewHolder> {

    public AwardWinningScoreAdapter() {
        super(R.layout.item_award_winning_score);
    }

    @Override
    protected void convert(BaseViewHolder helper, AwardsMathListBean item) {
        ItemAwardWinningScoreBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvPosition.setText("" + (helper.getAdapterPosition() + 1));
        binding.tvMattchTitle.setText("" + item.getMathTitle());
        binding.tvCompetitionTime.setText("" + item.getBeginTime());
        binding.tvCompetitionAddress.setText("" + item.getCity().replace(" ", "") + item.getLocal());
        if (helper.getAdapterPosition() % 2 == 0) {
            binding.llContainer.setBackgroundColor(Color.parseColor("#F5F5F5"));
        } else {
            binding.llContainer.setBackgroundColor(Color.parseColor("#FFFFFF"));

        }
    }
}
