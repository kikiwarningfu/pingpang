package heyong.intellectPinPang.adapter.live;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;


import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.live.LiveHallBean;
import heyong.intellectPinPang.bean.live.LiveListBean;
import heyong.intellectPinPang.databinding.ItemToBuyBinding;
import heyong.intellectPinPang.utils.Utils;

public class ToBuyAdapter extends BaseQuickAdapter<LiveHallBean.ListBean, BaseViewHolder> {
    Context context;

    public ToBuyAdapter(Context context) {
        super(R.layout.item_to_buy);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveHallBean.ListBean item) {
//        ImageLoader.displayLocalCornersImage(getActivity(),data.getMatchImgUrl(),binding.ivLogo,5);
        ItemToBuyBinding binding = DataBindingUtil.bind(helper.getConvertView());
//        1未开始2进行中3已结束（回放）
//        ImageLoader.loadImage(binding.ivLogo,item.getimg);
        binding.tvTime.setText("" + item.getMatchTime());
        binding.tvPeopleNum.setText("" + Utils.getShowVisitor(Double.parseDouble(String.valueOf(item.getOnLineCount()))));
        String leftName = "";
        String rightName = "";
        if (!TextUtils.isEmpty(item.getFengmian())) {
            ImageLoader.loadImage(binding.ivLogo,item.getFengmian(),R.drawable.img_live_moren,R.drawable.img_live_moren);
        }
        if (item.getItemType().equals("1") || item.getItemType().equals("2")) {
            if (TextUtils.isEmpty(item.getPlayer1())) {
                leftName = "未知";
            } else {
                leftName = item.getPlayer1();
            }
            if (TextUtils.isEmpty(item.getPlayer3())) {
                rightName = "未知";
            } else {
                rightName = item.getPlayer3();
            }

        } else {
            if (TextUtils.isEmpty(item.getPlayer1())) {
                leftName = "未知";
            } else {
                leftName = item.getPlayer1() + "/n" + item.getPlayer2();
            }
            if (TextUtils.isEmpty(item.getPlayer3())) {
                rightName = "未知";
            } else {
                rightName = item.getPlayer3() + "/n" + item.getPlayer4();
            }
        }
        binding.tvItemTitle.setText("" + item.getItemTitle());
        binding.tvPlayerName.setText("" + leftName + "  " + "VS" + "  " + rightName);
        if (item.getStatus().equals("1")) {
            binding.llLiveStatus.setVisibility(View.GONE);
        } else if (item.getStatus().equals("2")) {
            binding.llLiveStatus.setVisibility(View.VISIBLE);
            binding.llJinxingzhong.setVisibility(View.VISIBLE);
            binding.tvHuifang.setVisibility(View.GONE);

        } else if (item.getStatus().equals("3")) {
            binding.llLiveStatus.setVisibility(View.VISIBLE);
            binding.llJinxingzhong.setVisibility(View.GONE);
            binding.tvHuifang.setVisibility(View.VISIBLE);

        } else {
            binding.llLiveStatus.setVisibility(View.GONE);

        }


    }
}
