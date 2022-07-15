package heyong.intellectPinPang.adapter.teamregister;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MatchOrderInfoBean;

import static heyong.intellectPinPang.adapter.club.CoachAdapter.dip2px;

/**
 * 俱乐部报名  ---单打人员
 */
public class TeanRegisterSingleOneAdapter extends BaseQuickAdapter<MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean, BaseViewHolder> {
    private int width;
    private Context context;

    public TeanRegisterSingleOneAdapter(Context context, int width) {
        super(R.layout.item_team_register_single);
        this.context = context;
        this.width = width;
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean item) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(6);
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
//            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
//.apply(options)
//            LinearLayout ll_content = helper.getView(R.id.ll_content);
        TextView tvText = helper.getView(R.id.tv_text_six_module);
        tvText.setText("" + item.getUserName());
        tvText.setLayoutParams(new RelativeLayout.LayoutParams((width - dip2px(context, 70)) / 5, ViewGroup.LayoutParams.WRAP_CONTENT));
//            ImageLoader.loadRoundImage1(ivLogo, (String) item, R.drawable.ic_default_image);
    }
}
