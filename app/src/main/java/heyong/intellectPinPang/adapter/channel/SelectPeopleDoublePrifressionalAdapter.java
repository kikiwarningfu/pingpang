package heyong.intellectPinPang.adapter.channel;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.SelectPeopleProjectItemBean;
import heyong.intellectPinPang.databinding.ItemSelectPeopleDoublePrifressionalItemBinding;
//双打 专业
public class SelectPeopleDoublePrifressionalAdapter extends BaseQuickAdapter<SelectPeopleProjectItemBean, BaseViewHolder> {
    private Context context;
    private int width;

    public SelectPeopleDoublePrifressionalAdapter(Context context, int width) {
        super(R.layout.item_select_people_double_prifressional_item);
        this.context = context;
        this.width = width;
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectPeopleProjectItemBean item) {
        ItemSelectPeopleDoublePrifressionalItemBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvGroupName.setText("" + item.getName());
        if (helper.getAdapterPosition() == getData().size() - 1) {
            binding.viewDivider.setVisibility(View.GONE);
        } else {
            binding.viewDivider.setVisibility(View.VISIBLE);
        }
        RecyclerView rvList = helper.getView(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        MatchPlayerDoubleProAdapter matchPlayerAdapter = new MatchPlayerDoubleProAdapter();
        rvList.setAdapter(matchPlayerAdapter);
        List<SelectPeopleProjectItemBean.Players> playersList = item.getPlayersList();
        matchPlayerAdapter.setNewData(playersList);
    }

}
