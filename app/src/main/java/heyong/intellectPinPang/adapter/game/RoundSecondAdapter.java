package heyong.intellectPinPang.adapter.game;

import android.graphics.Color;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elvishew.xlog.XLog;
import com.google.gson.Gson;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MatchScreenBean;
import heyong.intellectPinPang.bean.competition.MatchScreenFormatBean;
import heyong.intellectPinPang.databinding.ItemGroupSelectBinding;
import heyong.intellectPinPang.databinding.ItemGroupSelectXlBinding;

/**
 * 比赛性别的适配器
 */
public class RoundSecondAdapter extends BaseQuickAdapter<MatchScreenFormatBean.MatchTypeBean.InfoBean, BaseViewHolder> {

    public RoundSecondAdapter() {
        super(R.layout.item_group_select_xl);
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchScreenFormatBean.MatchTypeBean.InfoBean item) {
        ItemGroupSelectXlBinding binding= DataBindingUtil.bind(helper.getConvertView());
        binding.tvText.setText("" + item.getTitleName());
        XLog.e("position==="+helper.getAdapterPosition()+"    "+new Gson().toJson(item));
        if (item.isSelect()) {
            binding.tvText.setBackgroundResource(R.drawable.shape_group_solid_stroke);
            binding.tvText.setTextColor(Color.parseColor("#ff4895ed"));
        } else {
            binding.tvText.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
            binding.tvText.setTextColor(Color.parseColor("#333333"));
        }

    }
}
