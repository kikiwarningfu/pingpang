package heyong.intellectPinPang.adapter.game;

import android.text.TextUtils;

import androidx.databinding.DataBindingUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.SingleHitDataBean;
import heyong.intellectPinPang.databinding.ItemGameUnderWayBinding;

/**
 * 比赛进行中的适配器
 */
public class GameUnderWayAdapter extends BaseQuickAdapter<SingleHitDataBean.ContestArrangeListBeanX.ContestArrangeListBean, BaseViewHolder> {
    public GameUnderWayAdapter() {
        super(R.layout.item_game_under_way);
    }

    @Override
    protected void convert(BaseViewHolder helper, SingleHitDataBean.ContestArrangeListBeanX.ContestArrangeListBean item) {

        ItemGameUnderWayBinding binding = DataBindingUtil.bind(helper.itemView);
        helper.addOnClickListener(R.id.tv_update_status);
        //binding.tvTaishu.setText("" + item.getTableNum() + "台");
        binding.tvRound.setText(""+item.getSessions());
        if (TextUtils.isEmpty(item.getStatus())) {
            binding.tvUpdateStatus.setText("开始");
            binding.tvUpdateStatus.setBackgroundResource(R.drawable.shape_club_blue_15);
        } else {
            binding.tvUpdateStatus.setText("修改");
            binding.tvUpdateStatus.setBackgroundResource(R.drawable.shape_club_red_15);

        }
        binding.tcScore.setText(""+item.getLeftWinCount()+":"+item.getRightWinCount());
        binding.tvNameOne.setText(""+item.getLeft1Name());
        binding.tvNameTwo.setText(""+item.getRight1Name());
//        binding.tvRound.setText(""+item.getRounds());
    }
}
