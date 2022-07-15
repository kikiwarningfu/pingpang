package heyong.intellectPinPang.adapter.game;

import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.Log;
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
 * @author 循环赛 顶部的适配器
 * @date 2019/12/2
 */
public class TitleGroupTopListAdapter extends RecyclerView.Adapter<TitleGroupTopListAdapter.ItemHolder> {
    private List<TitleBean> mList;
    public static final String TAG = TitleGroupTopListAdapter.class.getSimpleName();

    public TitleGroupTopListAdapter(List<TitleBean> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        Log.e(TAG, "viewType onCreateViewHolder: " + viewType);
        if (viewType == 555) {//积分  计算 dp_69
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_group_left_title_calculate, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_group_left_title, viewGroup, false);
        }
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getShowType();
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder viewHolder, int position) {
        ItemHolder holder = (ItemHolder) viewHolder;
        int itemViewType = getItemViewType(position);
        int itemType = Integer.parseInt(mList.get(position).getItemType());
        holder.rlLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myListener != null) {
                    myListener.OnMyListener(itemType, mList.get(position));
                }
            }
        });
        if (itemViewType == -1 || itemViewType == -2 || itemViewType == -3) {
            /*1 团体  2 单打  其他双打*/
            holder.tvClubName.setVisibility(View.GONE);
            holder.tvItem.setText(mList.get(position).getTitle());
            holder.tvItem.setTextSize(15);
            TextPaint paint = holder.tvItem.getPaint();
            paint.setFakeBoldText(true);
            holder.flContent.setVisibility(View.GONE);
        }/**/ else {
            if (itemType == 1) {
                /*团体*/
                holder.tvClubName.setVisibility(View.GONE);
            } else if (itemType == 2) {
                holder.tvClubName.setVisibility(View.GONE);
                holder.tvClubName.setText("" + mList.get(position).getClubName());
            } else {
                holder.tvClubName.setVisibility(View.VISIBLE);
                holder.tvClubName.setText("" + mList.get(position).getClubName());
            }
            holder.tvItem.setText(mList.get(position).getTitle());
            TextPaint paint = holder.tvItem.getPaint();
            paint.setFakeBoldText(false);
            if (mList.get(position).getTitle().equals("积分") || mList.get(position).getTitle().equals("计算") || mList.get(position).getTitle().equals("名次")) {
                holder.tvItem.setTextSize(15);
                holder.flContent.setVisibility(View.GONE);
                setTextBold(holder.tvItem);
            } else {
                holder.tvItem.setTextSize(13);
                holder.flContent.setVisibility(View.VISIBLE);
                setTextUnBold(holder.tvItem);
            }
            holder.tvPosition.setText("" + (position + 1));
        }
    }

    private void setTextBold(TextView textView) {
        //android中为textview动态设置字体为粗体
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    private void setTextUnBold(TextView textView) {
        //设置不为加粗
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
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
        public TextView tvClubName;
        private RelativeLayout rlLeftTitle;

        public ItemHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
            flContent = itemView.findViewById(R.id.fl_content);
            tvPosition = itemView.findViewById(R.id.tv_position);
            tvClubName = itemView.findViewById(R.id.tv_club_name);
            rlLeftTitle = itemView.findViewById(R.id.rl_left_title);
        }
    }

    /**
     * 定义一个接口
     */
    public interface onOwnMyListener {
        void OnMyListener(int itemType, TitleBean titleBean);
    }

    /**
     * 定义一个变量储存数据
     */
    private onOwnMyListener myListener;

    /**
     * 提供公共的方法,并且初始化接口类型的数据
     */
    public void setMyListener(onOwnMyListener listener) {
        this.myListener = listener;
    }
}
