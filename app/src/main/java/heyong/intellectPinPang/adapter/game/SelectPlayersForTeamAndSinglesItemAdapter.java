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
import heyong.intellectPinPang.bean.competition.SelectPlayerForTeamBean;
import heyong.intellectPinPang.databinding.ItemSelectPlayersForTeamAndSingleItemBinding;

/**
 * 选择运动员
 */
public class SelectPlayersForTeamAndSinglesItemAdapter extends BaseQuickAdapter<SelectPlayerForTeamBean.SelectBean, BaseViewHolder> {

    private Context context;

    public SelectPlayersForTeamAndSinglesItemAdapter(Context context) {
        super(R.layout.item_select_players_for_team_and_single_item);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectPlayerForTeamBean.SelectBean item) {
        ItemSelectPlayersForTeamAndSingleItemBinding binding = DataBindingUtil.bind(helper.getConvertView());
        TextView tvText = helper.getView(R.id.tv_text_six_module);
        ImageView ivSex = helper.getView(R.id.iv_sex);
        RelativeLayout rlContainer = helper.getView(R.id.rl_container);
        tvText.setText("" + item.getName());
        if (item.getSex().equals("1")) {
            ivSex.setBackgroundResource(R.drawable.img_sex_little_blue);
        } else {
            ivSex.setBackgroundResource(R.drawable.img_blue_woman);
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
