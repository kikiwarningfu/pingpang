package heyong.intellectPinPang.adapter.game;

import android.graphics.Color;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MatchScreenBean;
import heyong.intellectPinPang.databinding.ItemGroupSelectBinding;

/**
 * 比赛组别的适配器
 */
public class GameGroupAdapter extends BaseQuickAdapter<MatchScreenBean.ProjectBean, BaseViewHolder> {

    public GameGroupAdapter() {
        super(R.layout.item_group_select);
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchScreenBean.ProjectBean item) {
        ItemGroupSelectBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvText.setText("" + item.getProjectTitle());

        if (item.isSelect()) {
            binding.tvText.setBackgroundResource(R.drawable.shape_group_solid_stroke);
            binding.tvText.setTextColor(Color.parseColor("#ff4895ed"));
        } else {
            binding.tvText.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
            binding.tvText.setTextColor(Color.parseColor("#333333"));
        }

    }
}
