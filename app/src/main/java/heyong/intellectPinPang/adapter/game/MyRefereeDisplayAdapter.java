package heyong.intellectPinPang.adapter.game;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.gsonbean.RefereeDisplayTimeBean;
import heyong.intellectPinPang.databinding.ItemDisplayTimeBinding;
import heyong.intellectPinPang.databinding.ItemDisplayTimeMyBinding;

public class MyRefereeDisplayAdapter extends BaseQuickAdapter<RefereeDisplayTimeBean, BaseViewHolder> {


    public MyRefereeDisplayAdapter() {
        super(R.layout.item_display_time_my);
    }

    @Override
    protected void convert(BaseViewHolder helper, RefereeDisplayTimeBean item) {
        ItemDisplayTimeMyBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvTime.setText("" + item.getTime().replace("[","").replace("]",""));
        if (item.isSelect()) {
            binding.tvTime.setTextColor(Color.parseColor("#FF4795ED"));
            binding.viewDivider.setVisibility(View.VISIBLE);
        } else {
            binding.tvTime.setTextColor(Color.parseColor("#FF333333"));
            binding.viewDivider.setVisibility(View.INVISIBLE);
        }


    }
}
