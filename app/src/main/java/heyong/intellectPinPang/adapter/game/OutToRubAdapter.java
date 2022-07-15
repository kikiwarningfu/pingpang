package heyong.intellectPinPang.adapter.game;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ItemOutToRubBinding;

/**
 * 抢号  128 进 64  的适配器
 */
public class OutToRubAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public OutToRubAdapter() {
        super(R.layout.item_out_to_rub);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        ItemOutToRubBinding binding= DataBindingUtil.bind(helper.getConvertView());
        TextView tvOutText=helper.getView(R.id.tv_text);
        tvOutText.setText(""+item);
    }
}
