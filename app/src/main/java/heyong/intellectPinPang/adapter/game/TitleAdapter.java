package heyong.intellectPinPang.adapter.game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import heyong.intellectPinPang.R;

/**
 * @author ousiyuan
 * @date 2019/12/2
 */
public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.ItemHolder>{
    private List<String> mList;
    public TitleAdapter(List<String> list){
        mList = list;
    }
    @NonNull
    @Override
    public TitleAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;

        if (i==0){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_title, viewGroup, false);
        }else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_title2, viewGroup, false);
        }
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position>=5||position==0){
            return 0;
        }
        return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull TitleAdapter.ItemHolder viewHolder, int position) {
        ItemHolder holder = (ItemHolder) viewHolder;
        if (position>=5||position==0) {
            holder.tvItem.setText(mList.get(position));
        }else {
            holder.tvItem.setText(mList.get(position));
            holder.tvDec.setText("北京小亮队1");
        }
    }

    // 刷新数据
    public void refreshData(List<String> list) {
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
        public TextView tvDec;

        public ItemHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
            tvDec = itemView.findViewById(R.id.tv_dec);
        }
    }
}
