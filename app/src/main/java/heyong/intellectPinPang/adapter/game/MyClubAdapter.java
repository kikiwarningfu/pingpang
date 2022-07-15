package heyong.intellectPinPang.adapter.game;

import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MyClubListBean;
import heyong.intellectPinPang.databinding.ItemMyClubBinding;

/**
 * 我的俱乐部的适配器
 */
public class MyClubAdapter extends BaseQuickAdapter<MyClubListBean.ListBean, BaseViewHolder> {
    public MyClubAdapter() {
        super(R.layout.item_my_club);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyClubListBean.ListBean item) {
        ItemMyClubBinding binding = DataBindingUtil.bind(helper.getConvertView());
        ImageLoader.loadImage(binding.ivLogo, item.getCoverLogo());
        binding.tvTitle.setText("" + item.getTeamTitle());
        String status = item.getStatus();
        if (item.isCharge()) {
            binding.tvCreateTor.setVisibility(View.VISIBLE);
        } else {
            binding.tvCreateTor.setVisibility(View.INVISIBLE);
        }
        binding.tvAddress.setText("" + item.getCity().replace(" ", "") + item.getDetailAddress());
        String clubStatus = item.getClubStatus();//审核状态 1是待审核 2 是审核通过
        int i = Integer.parseInt(clubStatus);
        if (i == 2) {
            switch (status) {
                case "1"://待审核
                    binding.tvJoinStatus.setBackgroundResource(R.drawable.shape_club_yellow_9);
                    binding.tvJoinStatus.setText("审核中");
                    break;
                case "2"://审核通过
                    binding.tvJoinStatus.setBackgroundResource(R.drawable.shape_club_blue_9);
                    binding.tvJoinStatus.setText("已加入");
                    break;
                case "3"://3审核未通过
                    break;
            }
        } else {
            binding.tvJoinStatus.setBackgroundResource(R.drawable.shape_club_yellow_9);
            binding.tvJoinStatus.setText("审核中");
        }
    }
}
