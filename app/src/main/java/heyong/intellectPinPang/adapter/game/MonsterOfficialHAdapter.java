package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dnwalter.formlayoutmanager.adapter.BaseHMyFormAdapter;
import com.dnwalter.formlayoutmanager.entity.Monster;

import java.text.SimpleDateFormat;
import java.util.List;

import heyong.intellectPinPang.R;

public class MonsterOfficialHAdapter extends BaseHMyFormAdapter<Monster> {

    public void setMyData(List<Monster> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    int size = 0;
    public static final String TAG = MonsterOfficialHAdapter.class.getSimpleName();
    List<Monster> list;
    int columnCount = 0;

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

//    public MonsterOfficialHAdapter(Context context, List<Monster> list) {
//        super(context, list);
//        this.list = list;
//    }

    @Override
    public int getItemViewType(int position) {
        try {
            return list.get(position).getShowType();

        } catch (Exception e) {
            return 2;
        }
    }

    public MonsterOfficialHAdapter(Context context, List<Monster> list, int sizes, int columnCount) {
        super(context, list, sizes);
        this.list = list;
        this.columnCount = columnCount;
        this.size = sizes;
    }

    public void setList(List<Monster> list, int sizes, int columnCount) {
        this.list = list;
        setHangSize(sizes);
        this.columnCount = columnCount;
        notifyDataSetChanged();
    }

    @Override
    protected View createView(ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == 2) {
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_monster_form_item_wavier_official,
                    viewGroup, false);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_monster_top_title_official,
                    viewGroup, false);
        }
        return view;
    }


    @Override
    protected RecyclerView.ViewHolder createViewHolder(View view, int viewType) {
        RecyclerView.ViewHolder viewHolder = new ItemHolder(view);
//        viewHolder.itemView.setLayoutParams(new ViewGroup.LayoutParams(180, 88));
        viewHolder.itemView.post(new Runnable() {
            @Override
            public void run() {
                int height = viewHolder.itemView.getHeight();
            }
        });
        return viewHolder;
    }


    @Override
    protected void setData(RecyclerView.ViewHolder viewHolder, int rowIndex, int columnIndex, String content,
                           int position) {
        ItemHolder holder = (ItemHolder) viewHolder;
        try {
            int type = list.get(position).getType();

            Monster monster = list.get(position);
            switch (type) {
                case -1://积分
                    /*判断是积分 计算 还是名次*/
                    if (holder.tvScore != null) {
                        holder.tvScore.setText("" + monster.getIntergal());
                    }
                    if (holder.tvWavierTop != null) {
                        holder.llNotStartGame.setVisibility(View.GONE);
                        holder.ivGift.setVisibility(View.GONE);
                        holder.llContent.setVisibility(View.VISIBLE);
                        /*判断是积分 计算 还是名次*/
                        holder.tvWavierTop.setText("" + monster.getIntergal());
                        holder.tvWavierTop.setTextColor(Color.parseColor("#062A74"));
                        holder.tvWavierTop.setTextSize(15);
                        holder.tvViewDivider.setVisibility(View.GONE);
                        holder.tvWavierBottom.setVisibility(View.GONE);
                    }
                    break;
                case -2://计算
                    /*判断是积分 计算 还是名次*/
                    if (holder.tvScore != null) {
                        holder.tvScore.setText("" + monster.getResult());
                        holder.tvScore.setTextColor(Color.parseColor("#ff4795ed"));
                        holder.tvScore.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                        holder.tvScore.getPaint().setAntiAlias(true);//抗锯齿
                    }
                    if (holder.rlContentScore != null) {
                        holder.rlContentScore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null) {
                                    listener.OnListener(monster, position, -2);
                                }
                            }
                        });
                    }

                    if (holder.tvWavierTop != null) {
                        holder.llNotStartGame.setVisibility(View.GONE);
                        holder.ivGift.setVisibility(View.GONE);
                        holder.llContent.setVisibility(View.VISIBLE);
                        /*判断是积分 计算 还是名次*/
                        holder.tvWavierTop.setText("" + monster.getResult());
                        holder.tvWavierTop.setTextColor(Color.parseColor("#ff4795ed"));
                        holder.tvWavierTop.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                        holder.tvWavierTop.getPaint().setAntiAlias(true);//抗锯齿
                        holder.tvWavierTop.setTextSize(15);
                        holder.tvViewDivider.setVisibility(View.GONE);
                        holder.tvWavierBottom.setVisibility(View.GONE);

                        holder.tvWavierTop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null) {
                                    listener.OnListener(monster, position, -2);
                                }
                            }
                        });

                    }
                    break;
                case -3://名次
                    String ranking = monster.getRanking();
                    if (holder.tvScore != null) {
                        if (TextUtils.isEmpty(ranking)) {
                            holder.tvScore.setText("--");
                        } else {
                            int mRanking = Integer.parseInt(ranking);
                            if(monster.getQianNum()==0)
                            {

                            }else {
                                if (mRanking <= monster.getQianNum()) {
                                    //取前几
                                    holder.tvScore.setTextColor(Color.parseColor("#062A74"));
                                } else {
                                    holder.tvScore.setTextColor(Color.parseColor("#FB2A2A"));
                                }
                            }
                            holder.tvScore.setText("" + monster.getRanking());
                        }
                    }
                    if (holder.tvWavierTop != null) {
                        holder.llNotStartGame.setVisibility(View.GONE);
                        holder.ivGift.setVisibility(View.GONE);
                        holder.llContent.setVisibility(View.VISIBLE);

                        if (TextUtils.isEmpty(ranking)) {
                            holder.tvWavierTop.setText("--");
                        } else {
                            int mRanking = Integer.parseInt(ranking);
                            if(monster.getQianNum()==0)
                            {

                            }else {
                                if (mRanking <= monster.getQianNum()) {
                                    //取前几
                                    holder.tvWavierTop.setTextColor(Color.parseColor("#062A74"));
                                } else {
                                    holder.tvWavierTop.setTextColor(Color.parseColor("#FB2A2A"));
                                }
                            }
                            holder.tvWavierTop.setText("" + monster.getRanking());
                        }
                        holder.tvWavierTop.setTextColor(Color.parseColor("#062A74"));
                        holder.tvWavierTop.setTextSize(15);
                        holder.tvViewDivider.setVisibility(View.GONE);
                        holder.tvWavierBottom.setVisibility(View.GONE);
                    }
                    break;
                case 0://惊喜
                    holder.ivGift.setVisibility(View.VISIBLE);
                    holder.llContent.setVisibility(View.GONE);
                    holder.llNotStartGame.setVisibility(View.GONE);
                    holder.ivGift.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.OnListener(monster, position, 0);
                            }
                        }
                    });

                    break;
                case 1://未开始 显示tableNum
                    holder.ivGift.setVisibility(View.GONE);
                    holder.llContent.setVisibility(View.GONE);
                    holder.llNotStartGame.setVisibility(View.VISIBLE);
                    holder.tvDay.setText("" + monster.getDay() + "日");
                    SimpleDateFormat sdr = new SimpleDateFormat("HH:mm:ss");
                    holder.tvDay.setTextSize(11);
                    holder.tvTime.setTextSize(11);
                    holder.tvTableNum.setTextSize(11);

                    holder.tvTime.setText("" + monster.getMinute());
                    holder.tvTableNum.setText("" + monster.getTableNum() + "台");
                    holder.rlContent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.OnListener(monster, position, 1);
                            }
                        }
                    });
                    break;
                case 2://左边赢
                    holder.ivGift.setVisibility(View.GONE);
                    holder.llContent.setVisibility(View.VISIBLE);
                    holder.llNotStartGame.setVisibility(View.GONE);

                    holder.tvWavierTop.setTextColor(Color.parseColor("#FB2A2A"));
                    holder.tvWavierTop.setText("" + monster.getScore());
                    holder.tvWavierTop.setTextSize(14);
                    holder.tvViewDivider.setVisibility(View.VISIBLE);
                    holder.tvViewDivider.setBackgroundColor(Color.parseColor("#FB2A2A"));
                    holder.tvWavierBottom.setVisibility(View.VISIBLE);
                    holder.tvWavierBottom.setText("2");
                    holder.tvWavierBottom.setTextColor(Color.parseColor("#FB2A2A"));
                    holder.tvWavierBottom.setTextSize(12);
                    holder.llContent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.OnListener(monster, position, 2);
                            }
                        }
                    });
                    break;
                case 9://平局或者刚开始
                    holder.tvViewDivider.setBackgroundColor(Color.parseColor("#ff4795ed"));
                    holder.tvWavierTop.setTextColor(Color.parseColor("#ff4795ed"));
                    holder.tvWavierBottom.setTextColor(Color.parseColor("#ff4795ed"));
                    holder.tvWavierBottom.setVisibility(View.GONE);
                    holder.ivGift.setVisibility(View.GONE);
                    holder.llNotStartGame.setVisibility(View.GONE);
                    holder.llContent.setVisibility(View.VISIBLE);
                    holder.tvWavierTop.setText("" + monster.getScore());
                    holder.tvWavierTop.setTextSize(16);
                    holder.tvViewDivider.setVisibility(View.GONE);
                    holder.tvWavierBottom.setVisibility(View.GONE);

                    holder.llContent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.OnListener(monster, position, 9);
                            }
                        }
                    });
                    break;
                case 5://左边赢  右边弃权  //判断这个人的id 是否是自己 然后显示黑色
                    int showScore5 = monster.getShowScore();
                    if (showScore5 == 2) {
                        //显示红色
                        //显示黑色
                        holder.tvWavierTop.setTextColor(Color.parseColor("#FB2A2A"));
                        holder.tvViewDivider.setBackgroundColor(Color.parseColor("#FB2A2A"));
                        holder.tvWavierBottom.setTextColor(Color.parseColor("#FB2A2A"));
                    } else {
                        //显示黑色
                        holder.tvWavierTop.setTextColor(Color.parseColor("#333333"));
                        holder.tvViewDivider.setBackgroundColor(Color.parseColor("#333333"));
                        holder.tvWavierBottom.setTextColor(Color.parseColor("#333333"));
                    }
                    holder.ivGift.setVisibility(View.GONE);
                    holder.llNotStartGame.setVisibility(View.GONE);
                    holder.llContent.setVisibility(View.VISIBLE);
                    holder.tvWavierTop.setText("" + monster.getScore());
                    holder.tvWavierTop.setTextSize(14);
                    holder.tvViewDivider.setVisibility(View.VISIBLE);
                    holder.tvWavierBottom.setVisibility(View.VISIBLE);
                    holder.tvWavierBottom.setText("" + showScore5);
                    holder.tvWavierBottom.setTextSize(12);
                    holder.llContent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.OnListener(monster, position, 5);
                            }
                        }
                    });
                    break;
                case 3://右边赢
                    holder.ivGift.setVisibility(View.GONE);
                    holder.llNotStartGame.setVisibility(View.GONE);
                    holder.llContent.setVisibility(View.VISIBLE);
                    holder.tvWavierTop.setTextColor(Color.parseColor("#333333"));
                    holder.tvWavierTop.setText("" + monster.getScore());
                    holder.tvWavierTop.setTextSize(14);
                    holder.tvViewDivider.setVisibility(View.VISIBLE);
                    holder.tvViewDivider.setBackgroundColor(Color.parseColor("#333333"));

                    holder.tvViewDivider.setBackgroundColor(Color.parseColor("#333333"));

                    holder.tvWavierBottom.setVisibility(View.VISIBLE);
                    holder.tvWavierBottom.setText("1");
                    holder.tvWavierBottom.setTextColor(Color.parseColor("#333333"));
                    holder.tvWavierBottom.setTextSize(12);
                    holder.llContent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.OnListener(monster, position, 3);
                            }
                        }
                    });
                    break;
                case 4:////右边赢  左边弃权
                    int showScore4 = monster.getShowScore();
                    if (showScore4 == 2) {
                        //显示红色
                        //显示黑色
                        holder.tvWavierTop.setTextColor(Color.parseColor("#FB2A2A"));
                        holder.tvViewDivider.setBackgroundColor(Color.parseColor("#FB2A2A"));
                        holder.tvWavierBottom.setTextColor(Color.parseColor("#FB2A2A"));
                    } else {
                        //显示黑色
                        holder.tvWavierTop.setTextColor(Color.parseColor("#333333"));
                        holder.tvViewDivider.setBackgroundColor(Color.parseColor("#333333"));
                        holder.tvWavierBottom.setTextColor(Color.parseColor("#333333"));
                    }

                    holder.llNotStartGame.setVisibility(View.GONE);
                    holder.ivGift.setVisibility(View.GONE);
                    holder.llContent.setVisibility(View.VISIBLE);
                    holder.tvWavierTop.setText("" + monster.getScore());
                    holder.tvWavierTop.setTextSize(14);
                    holder.tvViewDivider.setVisibility(View.VISIBLE);
                    holder.tvWavierBottom.setVisibility(View.VISIBLE);
                    holder.tvWavierBottom.setText("" + showScore4);
                    holder.tvWavierBottom.setTextSize(12);
                    holder.llContent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.OnListener(monster, position, 4);
                            }
                        }
                    });
                    break;
                case 6://两边都弃权
                    holder.ivGift.setVisibility(View.GONE);
                    holder.llNotStartGame.setVisibility(View.GONE);
                    holder.llContent.setVisibility(View.VISIBLE);
                    holder.tvWavierTop.setTextColor(Color.parseColor("#333333"));
                    holder.tvWavierTop.setText("" + monster.getScore());
                    holder.tvWavierTop.setTextSize(12);
                    holder.tvViewDivider.setVisibility(View.GONE);
                    holder.tvWavierBottom.setVisibility(View.GONE);
                    holder.tvWavierBottom.setText("0");
                    holder.tvViewDivider.setBackgroundColor(Color.parseColor("#333333"));
                    holder.tvWavierBottom.setTextColor(Color.parseColor("#333333"));
                    holder.tvWavierBottom.setTextSize(11);
                    holder.llContent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.OnListener(monster, position, 6);
                            }
                        }
                    });
                    break;
                default:
                    holder.llNotStartGame.setVisibility(View.GONE);
                    break;
            }
        } catch (Exception e) {
        }
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        //        public TextView tvItem;
        public TextView tvWavierTop;
        public TextView tvWavierBottom;
        public View tvViewDivider;
        public ImageView ivGift;
        private LinearLayout llContent;
        private LinearLayout llNotStartGame;
        private TextView tvDay;
        private TextView tvTime;
        private TextView tvTableNum;
        private RelativeLayout rlContent;
        private TextView tvScore;
        private RelativeLayout rlContentScore;

        public ItemHolder(View itemView) {
            super(itemView);
//            tvItem = itemView.findViewById(R.id.tv_item);
            tvWavierTop = itemView.findViewById(R.id.tv_wavier_top);
            tvScore = itemView.findViewById(R.id.tv_score);
            rlContentScore = itemView.findViewById(R.id.rl_content_score);

            tvWavierBottom = itemView.findViewById(R.id.tv_wavier_bottom);
            rlContent = itemView.findViewById(R.id.rl_contenet);
            tvViewDivider = itemView.findViewById(R.id.tv_wavier_divider);
            llContent = itemView.findViewById(R.id.ll_content);
            llNotStartGame = itemView.findViewById(R.id.ll_not_start);
            tvDay = itemView.findViewById(R.id.tv_day);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvTableNum = itemView.findViewById(R.id.tv_table_num);
            ivGift = itemView.findViewById(R.id.iv_gift);
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
