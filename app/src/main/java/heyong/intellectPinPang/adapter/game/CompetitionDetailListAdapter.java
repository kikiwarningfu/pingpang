package heyong.intellectPinPang.adapter.game;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ItemCompetitionDetailListBinding;

public class CompetitionDetailListAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public CompetitionDetailListAdapter() {
        super(R.layout.item_competition_detail_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        ItemCompetitionDetailListBinding binding = DataBindingUtil.bind(helper.getConvertView());
    }
}
