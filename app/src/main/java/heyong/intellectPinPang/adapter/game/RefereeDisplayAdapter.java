package heyong.intellectPinPang.adapter.game;

import android.graphics.Color;
import android.view.View;

import androidx.databinding.DataBindingUtil;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.gsonbean.RefereeDisplayTimeBean;
import heyong.intellectPinPang.databinding.ItemDisplayTimeBinding;

public class RefereeDisplayAdapter extends BaseQuickAdapter<RefereeDisplayTimeBean, BaseViewHolder> {


    public RefereeDisplayAdapter() {
        super(R.layout.item_display_time);
    }

    @Override
    protected void convert(BaseViewHolder helper, RefereeDisplayTimeBean item) {
        ItemDisplayTimeBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvTime.setText("" + item.getTime().replace("[","").replace("]",""));
//        Log.e(TAG, "convert: "+item.getTime().replace("[","").replace("]",""));
//        Log.e(TAG, "convert: data==="+new Gson().toJson(getData()) );
        if (item.isSelect()) {
            binding.tvTime.setTextColor(Color.parseColor("#FF4795ED"));
            binding.viewDivider.setVisibility(View.VISIBLE);
        } else {
            binding.tvTime.setTextColor(Color.parseColor("#FF333333"));
            binding.viewDivider.setVisibility(View.INVISIBLE);
        }


    }
}
