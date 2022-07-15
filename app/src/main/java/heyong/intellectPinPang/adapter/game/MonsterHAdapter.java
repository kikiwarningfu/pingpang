package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dnwalter.formlayoutmanager.adapter.BaseHFormAdapter;


import java.util.List;

import heyong.intellectPinPang.R;

import com.dnwalter.formlayoutmanager.entity.Monster;

public class MonsterHAdapter extends BaseHFormAdapter<Monster> {

    private int color062A74;
    private int color4795ED;
    private int color666666;
    private int colorFB2A2A;
    private int color333333;

    public MonsterHAdapter(Context context, List<Monster> list) {
        super(context, list);
        this.list = list;
        loadColorRes();
    }

    public static final String TAG = MonsterHAdapter.class.getSimpleName();
    List<Monster> list;
    private int types = 1;

    private void loadColorRes() {
        color062A74 = mContext.getResources().getColor(R.color.color_062A74);
        color4795ED = mContext.getResources().getColor(R.color.color_4795ED);
        color666666 = mContext.getResources().getColor(R.color.color_666666);
        colorFB2A2A = mContext.getResources().getColor(R.color.color_FB2A2A);
        color333333 = mContext.getResources().getColor(R.color.color_333333);
    }

    @Override
    protected String getRowData(Monster model, int index) {
        String result = "";
        switch (index) {
            case 0:
            case 7:
            case 6:
            case 5:
            case 4:
            case 3:
            case 2:
            case 1:
                result = model.getLeft1Name();
                break;
        }

        return result;
    }

    public void setType(int type) {
        this.types = type;
        notifyDataSetChanged();
    }

    @Override
    protected View createView(ViewGroup viewGroup, int viewType) {
        View view;

        view = LayoutInflater.from(mContext).inflate(R.layout.adapter_monster_form_item_wavier,
                viewGroup, false);
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

    //台数跳转到 那场比赛
    @Override
    protected void setData(RecyclerView.ViewHolder viewHolder, int position) {
        ItemHolder holder = (ItemHolder) viewHolder;
//        if (rowIndex != 1) {
        int type = list.get(position).getType();
        Monster monster = list.get(position);
        switch (type) {
            case -1://积分
                /*判断是积分 计算 还是名次*/
                holder.tvWavierTop.setText(monster.getIntergal());
                holder.tvWavierTop.setTextColor(color062A74);
                holder.tvWavierTop.setTextSize(15);
                holder.tvWavierTop.getPaint().setFakeBoldText(false);
                holder.tvViewDivider.setVisibility(View.GONE);
                holder.tvWavierBottom.setVisibility(View.GONE);
                if(types==1) {
                    ViewGroup.LayoutParams layoutParams = holder.llContent.getLayoutParams();
                    layoutParams.width = 180;
                    holder.llContent.setLayoutParams(layoutParams);
                }
                break;
            case -2://计算
                /*判断是积分 计算 还是名次*/
                holder.tvWavierTop.setText(monster.getResult());
                holder.tvWavierTop.setTextColor(color4795ED);
                holder.tvWavierTop.setTextSize(15);
                holder.tvWavierTop.getPaint().setFakeBoldText(false);
                holder.tvViewDivider.setVisibility(View.GONE);
                holder.tvWavierBottom.setVisibility(View.GONE);
                holder.tvWavierTop.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.OnListener(monster, position, -2);
                    }
                });
                if(types==1) {
                    ViewGroup.LayoutParams layoutParams1 = holder.llContent.getLayoutParams();
                    layoutParams1.width = 180;
                    holder.llContent.setLayoutParams(layoutParams1);
                }
                break;
            case -3://名次
                String ranking = monster.getRanking();
                if (TextUtils.isEmpty(ranking)) {
                    holder.tvWavierTop.setText("--");
                } else {
                    holder.tvWavierTop.setText(ranking);
                }
                holder.tvWavierTop.setTextColor(color062A74);
                holder.tvWavierTop.setTextSize(18);
                holder.tvWavierTop.getPaint().setFakeBoldText(true);
                holder.tvViewDivider.setVisibility(View.GONE);
                holder.tvWavierBottom.setVisibility(View.GONE);
                if(types==1) {
                    ViewGroup.LayoutParams layoutParams2 = holder.llContent.getLayoutParams();
                    layoutParams2.width = 180;
                    holder.llContent.setLayoutParams(layoutParams2);
                }
                break;
            case 0://惊喜
                holder.tvWavierTop.setText("--");
                holder.tvWavierTop.setTextColor(color666666);
                holder.tvWavierTop.setTextSize(12);
                holder.tvWavierTop.getPaint().setFakeBoldText(false);
                holder.tvViewDivider.setVisibility(View.GONE);
                holder.tvWavierBottom.setVisibility(View.GONE);
                break;
            case 1://未开始 显示tableNum
                //holder.tvWavierTop.setText("" + monster.getTableNum() + "台");
                holder.tvWavierTop.setText("录入");
                holder.tvWavierTop.setTextColor(color4795ED);
                holder.tvWavierTop.setTextSize(15);
                holder.tvWavierTop.getPaint().setFakeBoldText(false);
                holder.tvViewDivider.setVisibility(View.GONE);
                holder.tvWavierBottom.setVisibility(View.GONE);
                holder.llContent.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.OnListener(monster, position, 1);
                    }
                });
                break;
            case 2://左边赢
                holder.tvWavierTop.setTextColor(colorFB2A2A);
                holder.tvWavierTop.setText(monster.getScore());
                holder.tvWavierTop.setTextSize(14);
                holder.tvWavierTop.getPaint().setFakeBoldText(true);
                holder.tvViewDivider.setVisibility(View.VISIBLE);
                holder.tvViewDivider.setBackgroundColor(colorFB2A2A);
                holder.tvWavierBottom.setVisibility(View.VISIBLE);
                holder.tvWavierBottom.setText("2");
                holder.tvWavierBottom.setTextColor(colorFB2A2A);
                holder.tvWavierBottom.setTextSize(11);
                holder.llContent.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.OnListener(monster, position, 2);
                    }
                });
                break;
            case 5://左边赢  右边弃权  //判断这个人的id 是否是自己 然后显示黑色
                int showScore5 = monster.getShowScore();

                if (showScore5 == 2) {
                    //显示红色
                    //显示黑色
                    holder.tvWavierTop.setTextColor(colorFB2A2A);
                    holder.tvViewDivider.setBackgroundColor(colorFB2A2A);
                    holder.tvWavierBottom.setTextColor(colorFB2A2A);
                } else {
                    //显示黑色
                    holder.tvWavierTop.setTextColor(color333333);
                    holder.tvViewDivider.setBackgroundColor(color333333);
                    holder.tvWavierBottom.setTextColor(color333333);
                }
                holder.tvWavierTop.setText(monster.getScore());
                holder.tvWavierTop.setTextSize(14);
                holder.tvWavierTop.getPaint().setFakeBoldText(true);
                holder.tvViewDivider.setVisibility(View.VISIBLE);
                holder.tvWavierBottom.setVisibility(View.VISIBLE);
                holder.tvWavierBottom.setText("" + showScore5);
                holder.tvWavierBottom.setTextSize(11);
                holder.llContent.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.OnListener(monster, position, 5);
                    }
                });
                break;
            case 3://右边赢
                holder.tvWavierTop.setTextColor(color333333);
                holder.tvWavierTop.setText(monster.getScore());
                holder.tvWavierTop.setTextSize(14);
                holder.tvWavierTop.getPaint().setFakeBoldText(true);
                holder.tvViewDivider.setVisibility(View.VISIBLE);
                holder.tvViewDivider.setBackgroundColor(color333333);

                holder.tvViewDivider.setBackgroundColor(color333333);

                holder.tvWavierBottom.setVisibility(View.VISIBLE);
                holder.tvWavierBottom.setText("1");
                holder.tvWavierBottom.setTextColor(color333333);
                holder.tvWavierBottom.setTextSize(11);
                holder.llContent.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.OnListener(monster, position, 3);
                    }
                });
                break;
            case 4:////右边赢  左边弃权
                int showScore4 = monster.getShowScore();

                if (showScore4 == 2) {
                    //显示红色
                    //显示黑色
                    holder.tvWavierTop.setTextColor(colorFB2A2A);
                    holder.tvViewDivider.setBackgroundColor(colorFB2A2A);
                    holder.tvWavierBottom.setTextColor(colorFB2A2A);
                } else {
                    //显示黑色
                    holder.tvWavierTop.setTextColor(color333333);
                    holder.tvViewDivider.setBackgroundColor(color333333);
                    holder.tvWavierBottom.setTextColor(color333333);
                }
                holder.tvWavierTop.setText(monster.getScore());
                holder.tvWavierTop.setTextSize(14);
                holder.tvWavierTop.getPaint().setFakeBoldText(true);
                holder.tvViewDivider.setVisibility(View.VISIBLE);
                holder.tvWavierBottom.setVisibility(View.VISIBLE);
                holder.tvWavierBottom.setText("" + showScore4);
                holder.tvWavierBottom.setTextSize(11);
                holder.llContent.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.OnListener(monster, position, 4);
                    }
                });
                break;
            case 6://两边都弃权
                holder.tvWavierTop.setTextColor(color333333);
                holder.tvWavierTop.setText(monster.getScore());
                holder.tvWavierTop.setTextSize(14);
                holder.tvWavierTop.getPaint().setFakeBoldText(true);
                holder.tvViewDivider.setVisibility(View.VISIBLE);
                holder.tvWavierBottom.setVisibility(View.VISIBLE);
                holder.tvWavierBottom.setText("0");
                holder.tvWavierBottom.setTextColor(color333333);
                holder.tvViewDivider.setBackgroundColor(color333333);
                holder.tvWavierBottom.setTextSize(11);
                holder.llContent.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.OnListener(monster, position, 6);
                    }
                });
                break;
            default:
                Log.e(TAG, "setData: type===" + monster.getType() + "oldPosition===" + position);
                break;
        }

    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        //        public TextView tvItem;
        public TextView tvWavierTop;
        public TextView tvWavierBottom;
        public View tvViewDivider;
        public LinearLayout llContent;

        public ItemHolder(View itemView) {
            super(itemView);
//            tvItem = itemView.findViewById(R.id.tv_item);
            tvWavierTop = itemView.findViewById(R.id.tv_wavier_top);
            tvWavierBottom = itemView.findViewById(R.id.tv_wavier_bottom);
            tvViewDivider = itemView.findViewById(R.id.tv_wavier_divider);
            llContent = itemView.findViewById(R.id.ll_content);
        }
    }

    /**
     * 定义一个接口
     */
    public interface onListener {
        void OnListener(Monster monster, int position, int type);
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
