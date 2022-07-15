package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextPaint;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.RefreerConsoleSortBean;
import heyong.intellectPinPang.databinding.ItemMyTimeListBinding;

public class TimeListItemAdapter extends BaseQuickAdapter<RefreerConsoleSortBean.ItemBean, BaseViewHolder> {
    private int width;
    private Context context;

    public TimeListItemAdapter(int width, Context context) {
        super(R.layout.item_my_time_list);
        this.width = width;
        this.context = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, RefreerConsoleSortBean.ItemBean item) {
        ItemMyTimeListBinding binding = DataBindingUtil.bind(helper.getConvertView());
//        TextView tvContent = helper.getView(R.id.tv_content);
//        tvContent.setLayoutParams(new LinearLayout.LayoutParams((width - dip2px(context, 10)) /2, ViewGroup.LayoutParams.WRAP_CONTENT));
        binding.tvContent.setText("" + item.getItemTypeName());

//        llContainer.setLayoutParams(new LinearLayout.LayoutParams(width / 4, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (item.isSelect()) {
            binding.tvContent.setTextColor(Color.parseColor("#FFFFFF"));
            binding.tvContent.setBackgroundResource(R.drawable.shape_club_blue);
//            binding.tvContent.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        } else {
            binding.tvContent.setTextColor(Color.parseColor("#333333"));
            binding.tvContent.setBackground(null);
//            binding.tvContent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
    }
}
