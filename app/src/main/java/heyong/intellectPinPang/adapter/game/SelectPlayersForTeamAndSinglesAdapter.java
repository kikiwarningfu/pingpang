package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.SelectPlayerForTeamBean;
import heyong.intellectPinPang.databinding.ItemSelectPlayersForTeamAndSingleBinding;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;

/**
 * 选择运动员
 */
public class SelectPlayersForTeamAndSinglesAdapter extends BaseQuickAdapter<SelectPlayerForTeamBean, BaseViewHolder> {

    private Context context;
    MyDividerItemDecoration mSixDiD;

    public SelectPlayersForTeamAndSinglesAdapter(Context context, MyDividerItemDecoration mSixDiD) {
        super(R.layout.item_select_players_for_team_and_single);
        this.context = context;
        this.mSixDiD = mSixDiD;
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectPlayerForTeamBean item) {
        ItemSelectPlayersForTeamAndSingleBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvName.setText("" + item.getTeamName());
        binding.tvDuiwuName.setText("" + item.getDuiwuName());
        RecyclerView rvList = helper.getView(R.id.rv_list);
        List<SelectPlayerForTeamBean.SelectBean> dataList = item.getDataList();
        TextView tvNum = helper.getView(R.id.tv_num);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 5);
        rvList.removeItemDecoration(mSixDiD);
        mSixDiD = new MyDividerItemDecoration(context, 10,
                context.getResources().getColor(R.color.white));
        rvList.addItemDecoration(mSixDiD);
        //绑定manager
        rvList.setLayoutManager(gridLayoutManager);

        SelectPlayersForTeamAndSinglesItemAdapter selectPlayersForTeamAndSinglesItemAdapter = new
                SelectPlayersForTeamAndSinglesItemAdapter(context);
        rvList.setAdapter(selectPlayersForTeamAndSinglesItemAdapter);
        selectPlayersForTeamAndSinglesItemAdapter.setNewData(dataList);
        int m = 0;
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).isSelect()) {
                m++;
            }
        }
        tvNum.setText("" + m);
        selectPlayersForTeamAndSinglesItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                    List<SelectPlayerForTeamBean.SelectBean> data = selectPlayersForTeamAndSinglesItemAdapter.getData();
                    SelectPlayerForTeamBean.SelectBean selectBean = data.get(position);
                    selectBean.setSelect(!data.get(position).isSelect());
                    selectPlayersForTeamAndSinglesItemAdapter.setData(position, selectBean);
                    int m = 0;
                    for (int i = 0; i < selectPlayersForTeamAndSinglesItemAdapter.getData().size(); i++) {
                        if (selectPlayersForTeamAndSinglesItemAdapter.getData().get(i).isSelect()) {
                            m++;
                        }
                    }
                    tvNum.setText("" + m);

            }
        });
    }
}
