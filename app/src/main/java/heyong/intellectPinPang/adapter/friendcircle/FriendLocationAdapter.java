package heyong.intellectPinPang.adapter.friendcircle;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.friend.MyPoiItems;

public class FriendLocationAdapter extends BaseQuickAdapter<MyPoiItems, BaseViewHolder> {

    public FriendLocationAdapter() {
        super(R.layout.item_friend_location);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyPoiItems item) {
        RelativeLayout llConetntOne = helper.getView(R.id.ll_content_one);
        RelativeLayout lLContentTwo = helper.getView(R.id.ll_content_two);
        ImageView ivOneRIght = helper.getView(R.id.iv_one_right);
        ImageView ivTwoRIght = helper.getView(R.id.iv_two_right);
        if (helper.getAdapterPosition() == 0) {
            llConetntOne.setVisibility(View.VISIBLE);
            lLContentTwo.setVisibility(View.GONE);
            boolean select = item.isSelect();
            if (select) {
                ivOneRIght.setVisibility(View.VISIBLE);
            } else {
                ivOneRIght.setVisibility(View.GONE);
            }
            ivTwoRIght.setVisibility(View.GONE);
        } else {
            if (item.isSelect()) {
                ivTwoRIght.setVisibility(View.VISIBLE);
            } else {
                ivTwoRIght.setVisibility(View.GONE);

            }
            ivOneRIght.setVisibility(View.GONE);
            llConetntOne.setVisibility(View.GONE);
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
}
