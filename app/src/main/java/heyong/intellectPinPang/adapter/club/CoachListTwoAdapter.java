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
public class CoachListTwoAdapter extends BaseQuickAdapter<FillInMatchBaseInfoBean.PlayersBean, BaseViewHolder> {
    private Context context;
    private int width;

    public CoachListTwoAdapter(Context context, int wdith) {
        super(R.layout.item_coach_list);
        this.context = context;
        this.width = wdith;
    }

    @Override
    protected void convert(BaseViewHolder helper, FillInMatchBaseInfoBean.PlayersBean item) {
        ItemCoachListBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.llContent.setLayoutParams(new LinearLayout.LayoutParams((width - dip2px(context, 70)) / 5, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (item.getName().length() == 2) {
            binding.tvContent.setText("  " + item.getName() + "  ");
        } else {
            binding.tvContent.setText("" + item.getName());
        }
    }
}
