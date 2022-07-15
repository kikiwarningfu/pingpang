package heyong.intellectPinPang.adapter.display;

import android.graphics.Color;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.BeginArrangeBean;
import heyong.intellectPinPang.databinding.ItemWavierTeamResultBinding;
import heyong.intellectPinPang.utils.CommonUtilis;

public class WavierTeaqmResultAdapter extends BaseQuickAdapter<BeginArrangeBean.ChessBean, BaseViewHolder> {
    public WavierTeaqmResultAdapter() {
        super(R.layout.item_wavier_team_result);
    }

    @Override
    protected void convert(BaseViewHolder helper, BeginArrangeBean.ChessBean item) {
        ItemWavierTeamResultBinding binding = DataBindingUtil.bind(helper.getConvertView());
        int mStatus = 0;
        try {
            mStatus = Integer.parseInt(item.getStatus());
        } catch (Exception e) {

        }
        String round = CommonUtilis.numberToChinese(helper.getAdapterPosition() + 1);
        String leftName = "" + item.getPlayer1();
        String leftName1 = "" + item.getPlayer2();
        String rightName = "" + item.getPlayer3();
        String rightName2 = "" + item.getPlayer4();
        binding.tvPosition.setText("第" + round + "盘");
        String contestType = item.getHitType();
        String allLeftName = "";
        String allRightName = "";
        switch (contestType) {
            case "1"://单打
                allLeftName = leftName;
                allRightName = rightName;
                break;
            case "2"://双打
                allLeftName = leftName + "\n" + leftName1;
                allRightName = rightName + "\n" + rightName2;
                break;
        }
        binding.tvLeftName.setText("" + allLeftName);
        binding.tvRightName.setText("" + allRightName);
        if (mStatus == 0) {
            binding.tvDoubleGiveUp.setBackgroundResource(R.drawable.shape_blue_stroke_13);
            binding.tvLeftGiveUp.setBackgroundResource(R.drawable.shape_blue_stroke_13);
            binding.tvRightGiveUp.setBackgroundResource(R.drawable.shape_blue_stroke_13);
            binding.tvDoubleGiveUp.setTextColor(Color.parseColor("#FF4795ED"));
            binding.tvLeftGiveUp.setTextColor(Color.parseColor("#FF4795ED"));
            binding.tvRightGiveUp.setTextColor(Color.parseColor("#FF4795ED"));
            helper.addOnClickListener(R.id.tv_double_give_up);
            helper.addOnClickListener(R.id.tv_left_give_up);
            helper.addOnClickListener(R.id.tv_right_give_up);
        } else {

            binding.tvDoubleGiveUp.setBackgroundResource(R.drawable.shape_gray_stroke_13);
            binding.tvLeftGiveUp.setBackgroundResource(R.drawable.shape_gray_stroke_13);
            binding.tvRightGiveUp.setBackgroundResource(R.drawable.shape_gray_stroke_13);
            binding.tvDoubleGiveUp.setTextColor(Color.parseColor("#FF999999"));
            binding.tvLeftGiveUp.setTextColor(Color.parseColor("#FF999999"));
            binding.tvRightGiveUp.setTextColor(Color.parseColor("#FF999999"));

        }

    }
}
