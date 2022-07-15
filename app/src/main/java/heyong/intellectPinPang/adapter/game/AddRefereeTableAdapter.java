package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.AddRefereeBean;
import heyong.intellectPinPang.databinding.ItemAddRefereeBinding;

import static heyong.intellectPinPang.adapter.game.SelectCoachAdapter.dip2px;

/**
 * 裁判长控制台 添加的适配器
 */
public class AddRefereeTableAdapter extends BaseQuickAdapter<AddRefereeBean, BaseViewHolder> {
    private int width;
    private Context context;

    public AddRefereeTableAdapter(int width, Context context) {
        super(R.layout.item_add_referee);
        this.width = width;
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AddRefereeBean item) {
        ItemAddRefereeBinding binding = DataBindingUtil.bind(helper.getConvertView());
        TextView tvText = helper.getView(R.id.tv_text);
        tvText.setText("" + item.getTableNum());

        if (item.isCanClickable()) {
            //可以点击
            helper.addOnClickListener(R.id.tv_text);
            if (item.isSelect()) {
                tvText.setBackgroundResource(R.drawable.shape_club_blue);
                tvText.setTextColor(Color.parseColor("#FFFFFF"));
            } else {
                tvText.setBackgroundResource(R.drawable.shape_solid_blue);
                tvText.setTextColor(Color.parseColor("#4795ED"));
            }
        } else {
            tvText.setBackgroundResource(R.drawable.shape_gray_solid_corners_5);
            tvText.setTextColor(Color.parseColor("#FFFFFF"));
        }
        tvText.setLayoutParams(new LinearLayout.LayoutParams((width - dip2px(context, 70)) / 5, ViewGroup.LayoutParams.WRAP_CONTENT));
//            ImageLoader.loadRoundImage1(ivLogo, (String) item, R.drawable.ic_default_image);
    }
}
