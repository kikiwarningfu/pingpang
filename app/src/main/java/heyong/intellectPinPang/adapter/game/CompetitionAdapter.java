package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;


import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.SearchMathListBean;
import heyong.intellectPinPang.databinding.ItemCompetitionBinding;

/**
 * 赛事界面的适配器
 */
public class CompetitionAdapter extends BaseQuickAdapter<SearchMathListBean.ListBean, BaseViewHolder> {

    private Context context;

    public CompetitionAdapter(Context context) {
        super(R.layout.item_competition);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchMathListBean.ListBean item) {
        ItemCompetitionBinding binding = DataBindingUtil.bind(helper.getConvertView());
        ImageLoader.displayCornersImage(context, item.getMatchImgUrl(), binding.ivLogo, 5);
        int matchStatus = Integer.parseInt(item.getMatchStatus());
        binding.tvClubTitle.setText("" + item.getMatchTitle());
        setTextBold(binding.tvClubTitle);
        helper.addOnClickListener(R.id.tv_live_order);
        helper.addOnClickListener(R.id.tv_competition_detail);
        binding.tvZhubanfang.setText("主办方：" + item.getSponsor());
        binding.tvCompetionTime.setText("比赛时间：" + item.getBeginTime());
        binding.tvAddress.setText("" + item.getHoldCity() + " " + item.getVenue());
        if (matchStatus == 1) {
            //报名中
            binding.tvStatusText.setText("报名中");
            binding.llStatusContainer.setBackgroundResource(R.drawable.shape_blue_corners_13);
            ImageLoader.loadImage(binding.ivStatus, R.drawable.img_under_way_going);
        } else if (matchStatus == 2) {
            binding.tvStatusText.setText("进行中");
            binding.llStatusContainer.setBackgroundResource(R.drawable.shape_yellow_corners_13);
            ImageLoader.loadImage(binding.ivStatus, R.drawable.img_under_way);
        } else {
            binding.tvStatusText.setText("已结束");
            binding.llStatusContainer.setBackgroundResource(R.drawable.shape_gray_solid_13);
            ImageLoader.loadImage(binding.ivStatus, R.drawable.img_under_way_close);
        }

    }

    private void setTextBold(TextView textView) {
        //android中为textview动态设置字体为粗体
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    private void setTextUnBold(TextView textView) {
        //设置不为加粗
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
    }
}
