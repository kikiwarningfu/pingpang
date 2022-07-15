package heyong.intellectPinPang.adapter.intergaldetail;

import android.graphics.Color;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.CalculationInfoBean;
import heyong.intellectPinPang.databinding.ItemIntergalPeopleDetailBinding;

/**
 * 积分计算过程 的适配器
 */
public class IntergalPeopleDetailAdapter extends BaseQuickAdapter<CalculationInfoBean.FirstBean, BaseViewHolder> {

    public IntergalPeopleDetailAdapter() {
        super(R.layout.item_intergal_people_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, CalculationInfoBean.FirstBean item) {
        ItemIntergalPeopleDetailBinding binding = DataBindingUtil.bind(helper.getConvertView());
        if (item.isSelect()) {
            binding.tvName.setText(""+item.getName());
            binding.tvName.setBackgroundResource(R.drawable.shape_club_blue);
            binding.tvName.setTextColor(Color.parseColor("#FFFFFF"));

        } else {
            binding.tvName.setText(""+item.getName());
            binding.tvName.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
            binding.tvName.setTextColor(Color.parseColor("#666666"));

        }


    }
}
