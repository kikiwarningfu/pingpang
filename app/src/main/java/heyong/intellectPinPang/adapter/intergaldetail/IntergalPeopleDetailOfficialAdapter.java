package heyong.intellectPinPang.adapter.intergaldetail;

import android.graphics.Color;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.QueryComputeInfoBean;
import heyong.intellectPinPang.databinding.ItemIntergalPeopleDetailBinding;

/**
 * 积分计算过程 的适配器
 */
public class IntergalPeopleDetailOfficialAdapter extends BaseQuickAdapter<QueryComputeInfoBean.FirstBean, BaseViewHolder> {
    private String itemType;
    int mItemType;

    public IntergalPeopleDetailOfficialAdapter(String itemType) {
        super(R.layout.item_intergal_people_detail);
        this.itemType = itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, QueryComputeInfoBean.FirstBean item) {
        ItemIntergalPeopleDetailBinding binding = DataBindingUtil.bind(helper.getConvertView());
        try {
            mItemType = Integer.parseInt(itemType);
        } catch (Exception e) {
            mItemType = 1;
        }
        String name = "";
        if (mItemType == 1) {
            /*团体*/
            name = "" + item.getPlayer1();
        } else if (mItemType == 2) {
            /*单打*/
            name = "" + item.getPlayer1();
        } else {
            /*双打*/
            name = "" + item.getPlayer1() + "/" + item.getPlayer2();
        }
        if (item.isSelect()) {
            binding.tvName.setText("" + name);
            binding.tvName.setBackgroundResource(R.drawable.shape_club_blue);
            binding.tvName.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            binding.tvName.setText("" + name);
            binding.tvName.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
            binding.tvName.setTextColor(Color.parseColor("#666666"));
        }


    }
}
