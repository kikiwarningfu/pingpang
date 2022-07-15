package heyong.intellectPinPang.adapter.game;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ItemCompetitionNewsBinding;

/**
 * 赛事新闻的适配器
 */
public class CompetitionNewsAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
    public CompetitionNewsAdapter() {
        super(R.layout.item_competition_news);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        ItemCompetitionNewsBinding binding= DataBindingUtil.bind(helper.getConvertView());

    }
}
