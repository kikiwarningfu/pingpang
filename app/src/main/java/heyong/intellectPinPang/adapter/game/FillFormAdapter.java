package heyong.intellectPinPang.adapter.game;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MatchAgainstDataBean;
import heyong.intellectPinPang.databinding.ItemFillFormBinding;

/**
 * 填写对阵名单适配器
 */
public class FillFormAdapter extends BaseQuickAdapter<MatchAgainstDataBean.OpponentBean, BaseViewHolder> {

    public FillFormAdapter() {
        super(R.layout.item_fill_form);
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchAgainstDataBean.OpponentBean item) {
        ItemFillFormBinding binding = DataBindingUtil.bind(helper.getConvertView());
//        if (helper.getAdapterPosition() == getData().size() - 1) {
//            binding.viewDivider.setVisibility(View.GONE);
//        } else {
//            binding.viewDivider.setVisibility(View.VISIBLE);
//        }
        binding.tvDate.setText(""+item.getMatchDate());
        binding.tvTime.setText(""+item.getMatchTime());
        binding.tvDuishou.setText(""+item.getName());
        binding.tvStatus.setText(""+item.getStatus());
        binding.tvTaihao.setText(""+item.getTableNum());
    }
}
