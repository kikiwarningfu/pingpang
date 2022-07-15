package heyong.intellectPinPang.adapter.game;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ItemRankingListBinding;

/**
 * 排行榜列表的适配器
 */
public class RankingListAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
    public RankingListAdapter() {
        super(R.layout.item_ranking_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        ItemRankingListBinding binding= DataBindingUtil.bind(helper.getConvertView());
    }
}
