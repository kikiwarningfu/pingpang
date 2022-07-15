package heyong.intellectPinPang.adapter.game;

import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MatchArrangeMatchScoreBean;
import heyong.intellectPinPang.databinding.ItemMatchScoreItemBinding;

/**
 * item 比分的适配器
 */

public class MatchScoreItemAdapter extends BaseQuickAdapter<MatchArrangeMatchScoreBean.ResultListBean.ResultInfoBean, BaseViewHolder> {

    private int types;

    ////1 团体 2 单打 3 双打 4混双
    public MatchScoreItemAdapter(int types) {
        super(R.layout.item_match_score_item);
        this.types = types;
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchArrangeMatchScoreBean.ResultListBean.ResultInfoBean item) {
        ItemMatchScoreItemBinding binding = DataBindingUtil.bind(helper.getConvertView());
        //name 是简称
        if (item != null) {
            if (types == 1) {
                /*团体*/
                binding.tvTopName.setText("" + item.getClubName());
                if (TextUtils.isEmpty(item.getClubTitle())) {
                    binding.tvClubName.setVisibility(View.GONE);
                }
                binding.tvClubName.setText("" + item.getClubTitle());
            } else if (types == 2) {
                /*单打*/
                binding.tvTopName.setText("" + item.getPlayer1Name());
                binding.tvClubName.setText("" + item.getClubTitle());
                if (TextUtils.isEmpty(item.getClubTitle())) {
                    binding.tvClubName.setVisibility(View.GONE);
                }
            } else {
                binding.tvTopName.setText("" + item.getPlayer1Name() + "/" + item.getPlayer2Name());
                binding.tvClubName.setText("" + item.getClubTitle());
                if (TextUtils.isEmpty(item.getClubTitle())) {
                    binding.tvClubName.setVisibility(View.GONE);
                }
            }

            if (helper.getAdapterPosition() == getData().size() - 1) {
                binding.viewDivider.setVisibility(View.GONE);
            } else {
                binding.viewDivider.setVisibility(View.VISIBLE);
            }
        } else {
            binding.tvTopName.setText("");
            binding.tvClubName.setText("");
        }
    }
}
