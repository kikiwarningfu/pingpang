package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.CreateXlClubContestInfoBean;
import heyong.intellectPinPang.databinding.ItemCreateTeamBlueBinding;

public class CreateTeamBlueAdapter extends BaseQuickAdapter<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean, BaseViewHolder> {
    private int width;
    private Context context;

    public CreateTeamBlueAdapter(int width, Context context) {
        super(R.layout.item_create_team_blue);
        this.width = width;
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean item) {
        ImageView ivBlueClose = helper.getView(R.id.iv_close_blue);
        ItemCreateTeamBlueBinding binding = DataBindingUtil.bind(helper.getConvertView());
        boolean close = item.isClose();
        if (close) {
            helper.addOnClickListener(R.id.iv_close_blue);
            ivBlueClose.setVisibility(View.VISIBLE);
        } else {
            ivBlueClose.setVisibility(View.GONE);
        }
        TextView tvName = helper.getView(R.id.tv_name);
        tvName.setText("" + item.getUserName());
        ViewGroup.LayoutParams layoutParams = tvName.getLayoutParams();
        layoutParams.width=width/2;
        tvName.setLayoutParams(layoutParams);
    }
}
