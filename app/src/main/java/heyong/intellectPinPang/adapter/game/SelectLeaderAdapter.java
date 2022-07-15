package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.ChooseJoinMatchUserBean;
import heyong.intellectPinPang.databinding.ItemSelectLeaderBinding;

public class SelectLeaderAdapter extends BaseQuickAdapter<ChooseJoinMatchUserBean.PlayersBean, BaseViewHolder> {
    private Context context;
    private int width;

    public SelectLeaderAdapter(Context context, int width) {
        super(R.layout.item_select_leader);
        this.context = context;
        this.width = width;
    }

    @Override
    protected void convert(BaseViewHolder helper, ChooseJoinMatchUserBean.PlayersBean item) {
        ItemSelectLeaderBinding binding= DataBindingUtil.bind(helper.getConvertView());
        TextView tvText = helper.getView(R.id.tv_text_six_module);
        ImageView ivSex = helper.getView(R.id.iv_sex);
        tvText.setText(""+item.getUserName());
        RelativeLayout rlContainer = helper.getView(R.id.rl_container);
        if (item.getSex().equals("2")) {
            /*img_blue_woman*/
            ivSex.setBackgroundResource(R.drawable.img_blue_woman);
        } else {
            ivSex.setBackgroundResource(R.drawable.img_sex_little_blue);
        }
//        tvText.setLayoutParams(new RelativeLayout.LayoutParams((width - dip2px(context, 70)) / 5, ViewGroup.LayoutParams.WRAP_CONTENT));
//            ImageLoader.loadRoundImage1(ivLogo, (String) item, R.drawable.ic_default_image);
//        tvText.setGravity(Gravity.CENTER);
        if (item.isSelect()) {
            rlContainer.setBackgroundResource(R.drawable.shape_club_blue);
            tvText.setTextColor(Color.parseColor("#FFFFFF"));
            /*shape_big_gray_corners*/
        } else {
            rlContainer.setBackgroundResource(R.drawable.shape_big_gray_corners);
            tvText.setTextColor(Color.parseColor("#666666"));
        }
    }
}
