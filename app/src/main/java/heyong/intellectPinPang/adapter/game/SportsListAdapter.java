package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ItemSportsListBinding;

import static heyong.intellectPinPang.adapter.game.SelectCoachAdapter.dip2px;

/**
 * 运动员列表
 */
public class SportsListAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
    private int width;
    private Context context;

    public SportsListAdapter(int width, Context context) {
        super(R.layout.item_sports_list);
        this.width = width;
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        ItemSportsListBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.llContent.setLayoutParams(new LinearLayout.LayoutParams((width - dip2px(context, 70)) / 5, ViewGroup.LayoutParams.WRAP_CONTENT));

    }
}
