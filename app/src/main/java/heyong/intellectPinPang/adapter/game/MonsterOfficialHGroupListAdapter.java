package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dnwalter.formlayoutmanager.adapter.BaseHMyFormAdapter;
import com.dnwalter.formlayoutmanager.entity.Monster;
import com.google.gson.Gson;

import java.util.List;

import heyong.intellectPinPang.R;

public class MonsterOfficialHGroupListAdapter extends BaseHMyFormAdapter<Monster> {

    public MonsterOfficialHGroupListAdapter(Context context) {
        super(context);
    }

    public static final String TAG = MonsterOfficialHGroupListAdapter.class.getSimpleName();
    List<Monster> list;
    int columnCount = 0;
    public int widths = -1;

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

    public void setItemWidth(int itemWidth) {
        this.widths = itemWidth;
        notifyDataSetChanged();
    }

    public MonsterOfficialHGroupListAdapter(Context context, List<Monster> list, int hangSize, int columnCOunt) {
        super(context, list, hangSize);
        this.list = list;
        this.columnCount = columnCOunt;

    }

    @Override
    protected View createView(ViewGroup viewGroup, int viewType) {
        View view;

        view = LayoutInflater.from(mContext).inflate(R.layout.adapter_monster_official_group_list,
                viewGroup, false);
        return view;
    }


    @Override
    protected RecyclerView.ViewHolder createViewHolder(View view, int viewType) {
        RecyclerView.ViewHolder viewHolder = new ItemHolder(view);

        return viewHolder;
    }

    @Override
    protected void setData(RecyclerView.ViewHolder viewHolder, int rowIndex, int columnIndex, String content, int position) {
        ItemHolder holder = (ItemHolder) viewHolder;
//        holder.rlContent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        if (rowIndex != 1) {
        Log.e(TAG, "setDatarowIndex:====" + rowIndex + "columnIndx====" + columnIndex);
        Monster monster = list.get(position);
        Log.e(TAG, "setData: list=====  " + new Gson().toJson(list));
        int type1 = list.get(position).getType();
//        Log.e(TAG, "setData: type1=====" + type1);
        if (type1 == 1) {
            holder.tvGroupName.setText("" + list.get(position).getShowPlayerName());
            holder.tvGroupClubName.setVisibility(View.GONE);
        } else {
            holder.tvGroupName.setText("" + list.get(position).getShowPlayerName());
            holder.tvGroupClubName.setVisibility(View.VISIBLE);
            holder.tvGroupClubName.setText("" + list.get(position).getClubName());
        }
        if (widths != -1) {
            ViewGroup.LayoutParams layoutParams = holder.llContent.getLayoutParams();
            layoutParams.width = widths / 2;
            holder.llContent.setLayoutParams(layoutParams);
        }
        holder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnListener("" + list.get(position).getId(), "" + list.get(position).getMatchId(),
                            "" + list.get(position).getClubId(),""+list.get(position).getShowPlayerName());
                }
            }
        });

    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }


    private class ItemHolder extends RecyclerView.ViewHolder {
        //        public TextView tvItem;
        public TextView tvGroupName;
        public TextView tvGroupClubName;

        public LinearLayout llContent;

        public ItemHolder(View itemView) {
            super(itemView);
//            tvItem = itemView.findViewById(R.id.tv_item);
            tvGroupName = itemView.findViewById(R.id.tv_group_name);
            tvGroupClubName = itemView.findViewById(R.id.tv_group_club_name);
            llContent = itemView.findViewById(R.id.ll_content);
        }
    }

    /**
     * 定义一个接口
     */
    public interface onListener {
        void OnListener(String id, String matchId, String clubId,String name);
    }

    /**
     * 定义一个变量储存数据
     */
    private onListener listener;

    /**
     * 提供公共的方法,并且初始化接口类型的数据
     */
    public void setListener(onListener listener) {
        this.listener = listener;
    }
}
