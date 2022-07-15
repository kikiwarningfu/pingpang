package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MatchScreenFormatBean;

public class FormStageAdapter extends BaseQuickAdapter<MatchScreenFormatBean.MatchTypeBean, BaseViewHolder> {
    private Context context;
    private int width;

    public FormStageAdapter(Context context, int width) {
        super(R.layout.item_form_stage);
        this.context = context;
        this.width = width;
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchScreenFormatBean.MatchTypeBean item) {
        TextView tvOutText = helper.getView(R.id.tv_text);
        tvOutText.setText("" + item.getTitle());
        tvOutText.setLayoutParams(new LinearLayout.LayoutParams((width - dip2px(context, 70)) / 4, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (item.isSelect()) {
            tvOutText.setBackgroundResource(R.drawable.shape_group_solid_stroke);
            tvOutText.setTextColor(Color.parseColor("#ff4895ed"));
        } else {
            tvOutText.setBackgroundResource(R.drawable.shape_gray_corners_stroke);
            tvOutText.setTextColor(Color.parseColor("#333333"));
        }


    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
