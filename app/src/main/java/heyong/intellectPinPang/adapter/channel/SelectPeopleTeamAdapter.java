package heyong.intellectPinPang.adapter.channel;


import android.content.Context;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.SelectPeopleProjectItemBean;
import heyong.intellectPinPang.adapter.game.SelectPeopleItemAdapter;
import heyong.intellectPinPang.databinding.ItemSelectPeopleTeamItemBinding;
import heyong.intellectPinPang.ui.competition.activity.SelectAthletesAndCoachesActivity;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;

/**
 * 报名 团体的适配器
 */
public class SelectPeopleTeamAdapter extends BaseQuickAdapter<SelectPeopleProjectItemBean, BaseViewHolder> {
    MyDividerItemDecoration mSixDiD;
    SelectPeopleItemAdapter selectAthletesAndCoachesAdapter;
    public static final String TAG = SelectAthletesAndCoachesActivity.class.getSimpleName();
    private Context context;
    private int width;

    public SelectPeopleTeamAdapter(Context context, int width, MyDividerItemDecoration mSixDiD) {
        super(R.layout.item_select_people_team_item);
        this.context = context;
        this.width = width;
        this.mSixDiD = mSixDiD;
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectPeopleProjectItemBean item) {
        ItemSelectPeopleTeamItemBinding binding = DataBindingUtil.bind(helper.getConvertView());
        View viewDivider = helper.getView(R.id.view_divider);
        binding.tvLeftGroupName.setText("" + item.getGroupName() + " " + item.getName());
        binding.tvTeamOneName.setText("-队伍" + (helper.getAdapterPosition() + 1));
        List<SelectPeopleProjectItemBean.Players> playersList = item.getPlayersList();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 5);
        binding.rvList.removeItemDecoration(mSixDiD);
        binding.rvList.addItemDecoration(mSixDiD);
        //绑定manager
        binding.rvList.setLayoutManager(gridLayoutManager);
        selectAthletesAndCoachesAdapter = new SelectPeopleItemAdapter(context, width);
        binding.rvList.setAdapter(selectAthletesAndCoachesAdapter);
        selectAthletesAndCoachesAdapter.setNewData(playersList);
        if (playersList != null) {
            binding.tvSelectNum.setText("" + playersList.size());
        } else {
            binding.tvSelectNum.setText("0");
        }
        if (getData().size() == 0 || helper.getAdapterPosition() == getData().size() - 1) {
            viewDivider.setVisibility(View.GONE);
        } else {
            viewDivider.setVisibility(View.VISIBLE);
        }
        helper.addOnClickListener(R.id.tv_select_add_join);
        helper.addOnClickListener(R.id.ll_add_team);
        helper.addOnClickListener(R.id.ll_del_team);
    }
}
