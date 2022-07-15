package heyong.intellectPinPang.adapter.game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
public class GameDetailTopGroupTopAdapter extends RecyclerView.Adapter<GameDetailTopGroupTopAdapter.ItemHolder> {
    private List<TitleBean> mList;
    public int widths = -1;

    public GameDetailTopGroupTopAdapter(List<TitleBean> list) {
        mList = list;
    }

    public void setitemwitdh(int itemWidth) {
        this.widths = itemWidth;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameDetailTopGroupTopAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_game_detail_top_group_list, viewGroup, false);
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
        holder.tvItem.setText("" + mList.get(position).getTopTitle());
        if (widths != -1) {
            ViewGroup.LayoutParams layoutParams = holder.rlContent.getLayoutParams();
            layoutParams.width = widths / 2;
            holder.rlContent.setLayoutParams(layoutParams);
        }

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
        public RelativeLayout rlContent;

        public ItemHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_top_name);
            rlContent = itemView.findViewById(R.id.rl_content);

        }
    }
}
