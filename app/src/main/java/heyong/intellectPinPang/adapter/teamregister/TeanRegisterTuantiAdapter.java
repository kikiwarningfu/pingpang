package heyong.intellectPinPang.adapter.teamregister;

import android.content.Context;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MatchOrderInfoBean;
import heyong.intellectPinPang.databinding.ItemTeamRegisterTuantiBinding;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;

/**
 * 俱乐部报名团体人员
 */
public class TeanRegisterTuantiAdapter extends BaseQuickAdapter<MatchOrderInfoBean.PlayersBean.TeamsBean, BaseViewHolder> {
    private int width;
    private Context context;
    MyDividerItemDecoration mSixDiD;
    private String teamTitles;

    public TeanRegisterTuantiAdapter(Context context, int width, MyDividerItemDecoration mSixDiD, String teamTitle) {
        super(R.layout.item_team_register_tuanti);
        this.context = context;
        this.width = width;
        this.mSixDiD = mSixDiD;
        this.teamTitles = teamTitle;
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchOrderInfoBean.PlayersBean.TeamsBean item) {
        ItemTeamRegisterTuantiBinding binding = DataBindingUtil.bind(helper.getConvertView());
        String teamTitle = item.getTeamTitle();
        View viewDivider = helper.getView(R.id.view_divider);
        binding.tvLeftName.setText("" + teamTitles);
        List<MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean> player = item.getPlayer();
        binding.tvDuiwuName.setText("" + item.getTeamTitle());
        if (player!=null&&player.size() > 0) {
            binding.tvAlreadySelectNum.setText("" + player.size());
        } else {
            binding.tvAlreadySelectNum.setText("" + 0);
        }
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(6);
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
//            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
//.apply(options)
//            LinearLayout ll_content = helper.getView(R.id.ll_content);
//        TextView tvText = helper.getView(R.id.tv_text_six_module);
//        tvText.setText("张三" );
//        tvText.setLayoutParams(new RelativeLayout.LayoutParams((width - dip2px(context, 70)) / 5, ViewGroup.LayoutParams.WRAP_CONTENT));
//            ImageLoader.loadRoundImage1(ivLogo, (String) item, R.drawable.ic_default_image);
        TeanRegisterSingleOneAdapter teanRegisterSingleAdapter = new TeanRegisterSingleOneAdapter(context, width);
        binding.rvTuanti.setAdapter(teanRegisterSingleAdapter);
        GridLayoutManager gridLayoutManagerCoach = new GridLayoutManager(context, 5);
        binding.rvTuanti.removeItemDecoration(mSixDiD);
        binding.rvTuanti.addItemDecoration(mSixDiD);
        binding.rvTuanti.setLayoutManager(gridLayoutManagerCoach);
        if (player!=null&&player.size() > 0) {
            teanRegisterSingleAdapter.setNewData(player);

        } else {
            teanRegisterSingleAdapter.setNewData(new ArrayList<>());
        }

        if (helper.getAdapterPosition() == getData().size() - 1) {
            viewDivider.setVisibility(View.GONE);
        } else {
            viewDivider.setVisibility(View.VISIBLE);
        }

    }
}
