package heyong.intellectPinPang.adapter.intergaldetail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.dnwalter.formlayoutmanager.adapter.BaseHFormAdapter;

import java.util.List;

import heyong.intellectPinPang.R;
import com.dnwalter.formlayoutmanager.entity.Monster;

/**
 * 积分的适配器
 */
public class MonsterIntergalDetailAdapter extends BaseHFormAdapter<Monster> {

    public MonsterIntergalDetailAdapter(Context context) {
        super(context);
    }
    List<Monster> list;
    @Override
    protected String getRowData(Monster model, int index) {
        String result = "";
        switch (index) {
            case 0:
                result = model.getLeft1Name();
                break;
            case 1:
                result = model.getLeft1Name();
                break;
            case 2:
                result = model.getLeft1Name();
                break;
            case 3:
                result = model.getLeft1Name();
                break;
            case 4:
                result = model.getLeft1Name();
                break;
            case 5:
                result = model.getLeft1Name();
                break;
            case 6:
                result = model.getLeft1Name();
                break;
            case 7:
                result = model.getLeft1Name();
                break;
        }

        return result;
    }

    public MonsterIntergalDetailAdapter(Context context, List<Monster> list) {
        super(context, list);
        this.list = list;
        Log.e(TAG, "MonsterHAdapter: total size===" + list.size());
    }

    @Override
    protected View createView(ViewGroup viewGroup, int viewType) {
        View view;
//        R.layout.item_monster_intergal_detail
//        if (viewType == 0) {
        view =LayoutInflater.from(mContext).inflate(R.layout.item_monster_intergal_detail,
                viewGroup, false);
//        }else {
//            view =LayoutInflater.from(mContext).inflate(R.layout.adapter_monster_form_item_attribute,
//                    viewGroup, false);
//            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
//            layoutParams.width = 400;
//            view.setLayoutParams(layoutParams);
//        }

        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolder(View view, int viewType) {
        RecyclerView.ViewHolder viewHolder = new ItemHolder(view);

        return viewHolder;
    }

    @Override
    protected void setData(RecyclerView.ViewHolder viewHolder, int position) {
        super.setData(viewHolder, position);
        ItemHolder holder = (ItemHolder) viewHolder;
        Monster monster = list.get(position);
        holder.tvItem.setText(monster.getIntergal());
    }

    @Override
    protected void setData(RecyclerView.ViewHolder viewHolder, int rowIndex, int columnIndex, Monster content) {
        ItemHolder holder = (ItemHolder) viewHolder;

        if (columnIndex != 1) {
            holder.tvItem.setText(content.getIntergal());
        }
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        public TextView tvItem;

        public ItemHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_desc);
        }
    }
}
