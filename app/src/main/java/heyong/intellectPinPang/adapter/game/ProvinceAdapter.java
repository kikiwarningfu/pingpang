package heyong.intellectPinPang.adapter.game;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.ProvinceListBean;

public class ProvinceAdapter extends BaseQuickAdapter<ProvinceListBean, BaseViewHolder> {

    public ProvinceAdapter() {
        super(R.layout.item_province_and_city);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProvinceListBean item) {
        TextView tvCityName = helper.getView(R.id.tv_city_name);
        tvCityName.setText("" + item.getName());
        if (item.isSelect()) {
            tvCityName.setBackgroundResource(R.color.blue_select);
        } else {
            tvCityName.setBackground(null);
        }
    }
}
