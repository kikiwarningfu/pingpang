package heyong.intellectPinPang.adapter.game;

import android.content.Context;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.SimpleCalculationInfoBean;
import heyong.intellectPinPang.databinding.ItemIntergalCalculateItemBinding;

public class IntegralCalculateItemAdapter extends BaseQuickAdapter<SimpleCalculationInfoBean.ListInfoBean, BaseViewHolder> {
    private Context context;

    public IntegralCalculateItemAdapter(Context context) {
        super(R.layout.item_intergal_calculate_item);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, SimpleCalculationInfoBean.ListInfoBean item) {
        ItemIntergalCalculateItemBinding binding= DataBindingUtil.bind(helper.getConvertView());
        binding.tvDuishou.setText(""+item.getName());
        binding.tvSheng.setText(""+item.getWinCount());
        binding.tvFu.setText(""+item.getLoseCount());
        binding.tvJisuan.setText(""+item.getResult());

    }
}
