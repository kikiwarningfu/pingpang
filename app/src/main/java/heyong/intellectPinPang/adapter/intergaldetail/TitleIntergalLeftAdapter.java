package heyong.intellectPinPang.adapter.intergaldetail;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import heyong.intellectPinPang.R;

/**
 * 积分的左侧布局
 */
public class TitleIntergalLeftAdapter extends RecyclerView.Adapter<TitleIntergalLeftAdapter.ItemHolder> {
    private List<String> mList;


    public void setNewDatas(List<String> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TitleIntergalLeftAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
//        if (i==0){
//            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_title, viewGroup, false);
//        }else {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_title_intergal_left, viewGroup, false);
//        }
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= 5 || position == 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull TitleIntergalLeftAdapter.ItemHolder viewHolder, int position) {
        ItemHolder holder = (ItemHolder) viewHolder;
        if (position >= 5 || position == 0) {
            holder.tvItem.setText(mList.get(position));
        } else {
            holder.tvItem.setText(mList.get(position));
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

        public ItemHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_desc);
        }
    }
}
