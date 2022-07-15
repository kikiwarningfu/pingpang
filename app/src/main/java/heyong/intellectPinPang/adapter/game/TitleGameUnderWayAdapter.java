package heyong.intellectPinPang.adapter.game;

import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.TitleBean;

/**
 * @author ousiyuan
 * @date 2019/12/2
 */
public class TitleGameUnderWayAdapter extends RecyclerView.Adapter<TitleGameUnderWayAdapter.ItemHolder> {
    private List<TitleBean> mList;
    public static final String TAG = TitleGameUnderWayAdapter.class.getSimpleName();

    public TitleGameUnderWayAdapter(List<TitleBean> list) {
        mList = list;
    }

    private int types = 1;

    @NonNull
    @Override
    public TitleGameUnderWayAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;

//        if (i == -1) {
//            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_game_under_way_title_normal, viewGroup, false);
//        } else {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_game_under_way_title_pressed, viewGroup, false);
//        }
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    public void setType(int type) {
        this.types = type;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull TitleGameUnderWayAdapter.ItemHolder viewHolder, int position) {
        ItemHolder holder = (ItemHolder) viewHolder;
        int itemViewType = getItemViewType(position);
        if (itemViewType == -1 || itemViewType == -2 || itemViewType == -3) {
            holder.tvItem.setText(mList.get(position).getTitle());
            holder.tvItem.setTextSize(15);
            TextPaint paint = holder.tvItem.getPaint();
            paint.setFakeBoldText(true);
            holder.flContent.setVisibility(View.GONE);
        } else {
            holder.tvItem.setTextSize(13);
            holder.tvItem.setText(mList.get(position).getTitle());
            TextPaint paint = holder.tvItem.getPaint();
            paint.setFakeBoldText(false);
            holder.flContent.setVisibility(View.VISIBLE);
            holder.tvPosition.setText("" + (position + 1));
        }
//        if (position>=5||position==0) {
//            holder.tvItem.setText(mList.get(position));
//        }else {
//            holder.tvItem.setText(mList.get(position));
//            holder.tvDec.setText("北京小亮队1");
//        }
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
        public FrameLayout flContent;
        public TextView tvPosition;
        private RelativeLayout rlContent;

        public ItemHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
            flContent = itemView.findViewById(R.id.fl_content);
            tvPosition = itemView.findViewById(R.id.tv_position);
            rlContent = itemView.findViewById(R.id.rl_content);
        }
    }
}
