package heyong.intellectPinPang.adapter.game;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.ModelDataBrandIdBean;
import heyong.intellectPinPang.databinding.ItemFillMatchFormBinding;

public class KeyBoradXinghaoAdapter extends BaseQuickAdapter<ModelDataBrandIdBean, BaseViewHolder> {

    public KeyBoradXinghaoAdapter() {
        super(R.layout.item_fill_match_form);
    }

    @Override
    protected void convert(BaseViewHolder helper, ModelDataBrandIdBean item) {
        ItemFillMatchFormBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvName.setText("" + item.getTitle());
    }


}
