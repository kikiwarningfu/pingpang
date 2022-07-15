package heyong.intellectPinPang.adapter.channel;


import android.text.TextUtils;
import android.util.Log;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.SelectPeopleProjectItemBean;
import heyong.intellectPinPang.databinding.ItemMatchPlayerBinding;
import heyong.intellectPinPang.utils.CommonUtilis;

/**
 * 比赛打法  M1男子团体-队伍2
 */
public class MatchPlayerDoubleProAdapter extends BaseQuickAdapter<SelectPeopleProjectItemBean.Players, BaseViewHolder> {
    public MatchPlayerDoubleProAdapter() {
        super(R.layout.item_match_player);
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectPeopleProjectItemBean.Players item) {
        ItemMatchPlayerBinding binding = DataBindingUtil.bind(helper.getConvertView());
        String round = CommonUtilis.numberToChinese(helper.getAdapterPosition() + 1);
        binding.tvGroupName.setText("第" + round + "双打");
        String leftOneName = item.getLeftOneName();
        String rightOneName = item.getRightOneName();
        if (TextUtils.isEmpty(leftOneName)) {
            binding.tvLeftGroupName.setText("");
        } else {
            binding.tvLeftGroupName.setText("" + leftOneName);
        }
        if (TextUtils.isEmpty(rightOneName)) {
            binding.tvRightGroupName.setText("");
        } else {
            binding.tvRightGroupName.setText("" + rightOneName);
        }
        String leftSex = item.getLeftSex();
        String rightSex = item.getRightSex();
        if (leftSex.equals("1")) {
            ImageLoader.loadImage(binding.ivLeftLogo, R.drawable.img_sex_little_blue);
        } else {
            ImageLoader.loadImage(binding.ivLeftLogo, R.drawable.img_blue_woman);
        }

        if (rightSex.equals("1")) {
            ImageLoader.loadImage(binding.ivRightLogo, R.drawable.img_sex_little_blue);
        } else {
            ImageLoader.loadImage(binding.ivRightLogo, R.drawable.img_blue_woman);
        }

    }
}
