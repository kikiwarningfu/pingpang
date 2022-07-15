package heyong.intellectPinPang.adapter.utli;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.CityListBean;

public class CityAdapter extends BaseQuickAdapter<CityListBean, BaseViewHolder> {
    public CityAdapter() {
        super(R.layout.item_province_and_city);
    }

    @Override
    protected void convert(BaseViewHolder helper, CityListBean item) {
        TextView tvCityName=helper.getView(R.id.tv_city_name);
        tvCityName.setText(""+item.getName());
        if (item.isSelect()) {
            tvCityName.setBackgroundResource(R.color.blue_select);
        } else {
            tvCityName.setBackground(null);
        }
    }
}
