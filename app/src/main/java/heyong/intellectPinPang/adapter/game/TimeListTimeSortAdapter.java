package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.RefreerConsoleSortBean;
import heyong.intellectPinPang.databinding.ItemMyTimeListBinding;

//时间
public class TimeListTimeSortAdapter extends BaseQuickAdapter<RefreerConsoleSortBean.TimeSortBean, BaseViewHolder> {
    private int width;
    private Context context;

    public TimeListTimeSortAdapter(int width, Context context) {
        super(R.layout.item_my_time_list);
        this.width = width;
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RefreerConsoleSortBean.TimeSortBean item) {
        ItemMyTimeListBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvContent.setText("" + item.getTimeName());
//                android:background="@drawable/shape_club_blue"
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
