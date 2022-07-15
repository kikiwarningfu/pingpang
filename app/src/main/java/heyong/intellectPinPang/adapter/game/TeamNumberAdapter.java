package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.TeamMemberByMatchBean;
import heyong.intellectPinPang.databinding.ItemTeachNumberBinding;

import static heyong.intellectPinPang.adapter.club.CoachAdapter.dip2px;

public class TeamNumberAdapter extends BaseQuickAdapter<TeamMemberByMatchBean.PlayersBean, BaseViewHolder> {
    private Context context;
    private int width;

    public TeamNumberAdapter(Context context, int width) {
        super(R.layout.item_teach_number);
        this.context = context;
        this.width = width;
    }

    @Override
    protected void convert(BaseViewHolder helper, TeamMemberByMatchBean.PlayersBean item) {
        ItemTeachNumberBinding binding= DataBindingUtil.bind(helper.getConvertView());
        LinearLayout llContent = helper.getView(R.id.ll_content);
        llContent.setLayoutParams(new LinearLayout.LayoutParams((width - dip2px(context, 30)) / 5, ViewGroup.LayoutParams.WRAP_CONTENT));
        ImageLoader.loadImage(binding.ivLogo, item.getHeadImg(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);

        binding.tvName.setText("" + item.getName());
    }
}
