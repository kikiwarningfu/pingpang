package heyong.intellectPinPang.adapter.game;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ItemEventListBinding;

/**
 * 获取赛事列表
 */
public class EventListAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
    public EventListAdapter() {
        super(R.layout.item_event_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        ItemEventListBinding binding= DataBindingUtil.bind(helper.getConvertView());
    }
}
