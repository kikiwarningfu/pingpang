package heyong.intellectPinPang.adapter.friendcircle;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.friend.MyPoiItems;

public class FriendLocationSearchAdapter extends BaseQuickAdapter<MyPoiItems, BaseViewHolder> {

    public FriendLocationSearchAdapter() {
        super(R.layout.item_friend_location_search);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyPoiItems item) {
        RelativeLayout lLContentTwo = helper.getView(R.id.ll_content_two);
        lLContentTwo.setVisibility(View.VISIBLE);
        TextView tvName = helper.getView(R.id.tv_name);
        TextView tvAddress = helper.getView(R.id.tv_address);
        String title = item.getTitle();
        tvName.setText("" + title);
        String snippet = item.getDetailAddress();
        String provinceName = item.getProviceName();
        String cityName = item.getCityName();
        String adName = item.getAreName();
        if (provinceName.equals(cityName)) {
            tvAddress.setText("" + provinceName + adName + snippet);
        } else {
            tvAddress.setText("" + provinceName + cityName + adName + snippet);
        }


    }
}
