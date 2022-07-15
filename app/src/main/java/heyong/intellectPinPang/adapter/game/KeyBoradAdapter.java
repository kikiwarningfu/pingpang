package heyong.intellectPinPang.adapter.game;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.XlEquipmentBrandListBean;
import heyong.intellectPinPang.databinding.ItemFillMatchFormBinding;

public class KeyBoradAdapter extends BaseQuickAdapter<XlEquipmentBrandListBean.ListBean, BaseViewHolder> {

    public KeyBoradAdapter() {
        super(R.layout.item_fill_match_form);
    }

    @Override
    protected void convert(BaseViewHolder helper, XlEquipmentBrandListBean.ListBean item) {
        ItemFillMatchFormBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvName.setText("" + item.getTitle());
    }


}
