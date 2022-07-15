package heyong.intellectPinPang.adapter.game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.TitleBean;

/**
 * @author 循环赛左侧的适配器
 * @date 2019/12/2
 */
public class GameDetailLeftGroupListAdapter extends RecyclerView.Adapter<GameDetailLeftGroupListAdapter.ItemHolder> {
    private List<TitleBean> mList;

    public GameDetailLeftGroupListAdapter(List<TitleBean> list) {
        mList = list;
    }

    @NonNull
    @Override
    public GameDetailLeftGroupListAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_game_detail_left_group_list, viewGroup, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder viewHolder, int position) {
        ItemHolder holder = (ItemHolder) viewHolder;

        String leftTilte = mList.get(position).getLeftTilte();
        holder.tvItem.setText("" + leftTilte);
    }

    // 刷新数据
    public void refreshData(List<TitleBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public TextView tvItem;


        public ItemHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_left_title);

        }
    }
}
