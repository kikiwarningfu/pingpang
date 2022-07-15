package heyong.intellectPinPang.adapter.game;

import android.graphics.Color;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MatchScreenFormatBean;
import heyong.intellectPinPang.databinding.ItemGroupSelectLittleBinding;

/**
 * 小组组别的适配器
 */
public class GameGroupLittleAdapter extends BaseQuickAdapter<MatchScreenFormatBean.MatchTypeBean.InfoBean, BaseViewHolder> {
    public GameGroupLittleAdapter() {
        super(R.layout.item_group_select_little);
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchScreenFormatBean.MatchTypeBean.InfoBean item) {
        ItemGroupSelectLittleBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvText.setText("" + item.getTitleName());
        if (item.isSelect()) {
            binding.tvText.setBackgroundResource(R.drawable.shape_group_solid_stroke);
            binding.tvText.setTextColor(Color.parseColor("#ff4895ed"));
        } else {
            binding.tvText.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
            binding.tvText.setTextColor(Color.parseColor("#333333"));
        }


    }
}
