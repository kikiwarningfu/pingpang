package heyong.intellectPinPang.adapter.club;

import android.graphics.Color;
import android.text.TextUtils;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.TeamArrangePlayBean;
import heyong.intellectPinPang.databinding.ItemFillMatchFormBinding;

public class CoachFillMatchFormAdapter extends BaseQuickAdapter<TeamArrangePlayBean, BaseViewHolder> {

    public CoachFillMatchFormAdapter() {
        super(R.layout.item_fill_match_form);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeamArrangePlayBean item) {
        ItemFillMatchFormBinding binding = DataBindingUtil.bind(helper.getConvertView());
        if (item.getName().equals("取消选择")) {
            binding.tvName.setText("" + "取消选择");
            binding.tvName.setTextColor(Color.parseColor("#BABABA"));
        } else {
            if (TextUtils.isEmpty(item.getName())) {
                binding.tvName.setText("请选择");
            } else {
                binding.tvName.setText("" + item.getName());
            }
            binding.tvName.setTextColor(Color.parseColor("#FF0E3078"));
        }
    }


}
