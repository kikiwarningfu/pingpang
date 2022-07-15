package heyong.intellectPinPang.adapter.teamregister;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MatchOrderInfoBean;
import heyong.intellectPinPang.databinding.ItemTeamRegisterShuangdaBinding;
import heyong.intellectPinPang.utils.CommonUtilis;

/**
 * 俱乐部报名  ---单打人员
 */
public class TeanRegisterSingleShuangDaItemAdapter extends BaseQuickAdapter<MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean, BaseViewHolder> {
    private int width;
    private Context context;
    List<MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean> dataList;

    public TeanRegisterSingleShuangDaItemAdapter(Context context, int width) {
        super(R.layout.item_team_register_shuangda);
        this.context = context;
        this.width = width;
    }

    public void setMyPlayerDataList(List<MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean item) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(6);
        ItemTeamRegisterShuangdaBinding binding = DataBindingUtil.bind(helper.getConvertView());
        String round = CommonUtilis.numberToChinese(helper.getAdapterPosition() + 1);
        binding.tvRoundName.setText("第" + round + "双打");
        int mPosition = (helper.getAdapterPosition() + 1) * 2;
        try {
            binding.tvLeftName.setText("" + dataList.get(mPosition - 1).getUserName());
            binding.tvRightName.setText("" + dataList.get(mPosition).getUserName());
        } catch (Exception e) {
            binding.tvLeftName.setText("" + dataList.get(0).getUserName());
            binding.tvRightName.setText("" + dataList.get(1).getUserName());
        }
        if (helper.getAdapterPosition() == getData().size() - 1) {
            binding.viewDivider.setVisibility(View.GONE);
        } else {
            binding.viewDivider.setVisibility(View.VISIBLE);
        }

        /*需要判断*/
//        0  取值 1 和2
//            1  取值  3 和 4
//            2  取值  5和   6
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
//            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
//.apply(options)
//            LinearLayout ll_content = helper.getView(R.id.ll_content);
//        TextView tvText = helper.getView(R.id.tv_text_six_module);
//        tvText.setText("" + item.getUserName());
//        tvText.setLayoutParams(new RelativeLayout.LayoutParams((width - dip2px(context, 70)) / 5, ViewGroup.LayoutParams.WRAP_CONTENT));
//            ImageLoader.loadRoundImage1(ivLogo, (String) item, R.drawable.ic_default_image);
    }
}
