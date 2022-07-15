package heyong.intellectPinPang.adapter.live;


import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.live.LiveListBean;
import heyong.intellectPinPang.databinding.ItemLiveAllBinding;

/**
 * 所有录播的适配器
 */
public class AllLuBoChildAdapter extends BaseQuickAdapter<LiveListBean.ListBean, BaseViewHolder> {

    public AllLuBoChildAdapter() {
        super(R.layout.item_live_all);
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveListBean.ListBean item) {
        ItemLiveAllBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvTime.setText("" + item.getGameTime());
        binding.tvStatus.setText("回放");
        binding.viewLive.setVisibility(View.GONE);
//        if (item.getLeft().size() == 0) {
//            binding.tvName.setText("");
//        } else if (item.getLeft().size() == 1) {
//            binding.tvName.setText("" + item.getLeft().get(0).getName() + "VS" + item.getRight().get(0).getName());
//        } else if (item.getLeft().size() == 2) {
//            binding.tvName.setText("" + item.getLeft().get(0).getName() + " " + item.getLeft().get(1).getName() + "VS"
//                    + item.getRight().get(0).getName() + " " + item.getRight().get(1).getName());
//        }
    }
}
