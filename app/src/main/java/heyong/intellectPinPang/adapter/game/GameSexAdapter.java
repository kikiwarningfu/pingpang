package heyong.intellectPinPang.adapter.game;

import android.graphics.Color;
import android.util.Log;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MatchScreenBean;
import heyong.intellectPinPang.databinding.ItemGroupSelectBinding;

/**
 * 比赛性别的适配器
 */
public class GameSexAdapter extends BaseQuickAdapter<MatchScreenBean.ProjectBean.SexBean, BaseViewHolder> {

    public GameSexAdapter() {
        super(R.layout.item_group_select);
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchScreenBean.ProjectBean.SexBean item) {
        ItemGroupSelectBinding binding= DataBindingUtil.bind(helper.getConvertView());
        binding.tvText.setText("" + item.getSexName());
        if (item.isSelect()) {
            binding.tvText.setBackgroundResource(R.drawable.shape_group_solid_stroke);
            binding.tvText.setTextColor(Color.parseColor("#ff4895ed"));
        } else {
            binding.tvText.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
            binding.tvText.setTextColor(Color.parseColor("#333333"));
        }

    }
}
