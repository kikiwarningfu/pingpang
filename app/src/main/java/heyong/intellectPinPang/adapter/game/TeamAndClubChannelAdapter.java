package heyong.intellectPinPang.adapter.game;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.CheckUserClunRoleBean;
import heyong.intellectPinPang.databinding.ItemTeamAndClubBinding;

/**
 * 选择俱乐部和团体
 */
public class TeamAndClubChannelAdapter extends BaseQuickAdapter<CheckUserClunRoleBean.ClubInfoBean, BaseViewHolder> {
    public TeamAndClubChannelAdapter() {
        super(R.layout.item_team_and_club);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckUserClunRoleBean.ClubInfoBean item) {
        ItemTeamAndClubBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvClubTitle.setText("" + item.getTeamTitle());

    }
}
