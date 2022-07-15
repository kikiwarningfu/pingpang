package heyong.intellectPinPang.adapter.club;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.FillInMatchBaseInfoBean;
import heyong.intellectPinPang.databinding.ItemCoachListBinding;

import static heyong.intellectPinPang.adapter.game.SelectCoachAdapter.dip2px;

/**
 * 教练员列表
 */
public class CoachListAdapter extends BaseQuickAdapter<FillInMatchBaseInfoBean.CoachBean, BaseViewHolder> {
    private Context context;
    private int width;

    public CoachListAdapter(Context context, int wdith) {
        super(R.layout.item_coach_list);
        this.context = context;
        this.width = wdith;
    }

    @Override
    protected void convert(BaseViewHolder helper, FillInMatchBaseInfoBean.CoachBean item) {
        ItemCoachListBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.llContent.setLayoutParams(new LinearLayout.LayoutParams((width - dip2px(context, 70)) / 5, ViewGroup.LayoutParams.WRAP_CONTENT));
        binding.tvContent.setText("" + item.getName());
    }
}
