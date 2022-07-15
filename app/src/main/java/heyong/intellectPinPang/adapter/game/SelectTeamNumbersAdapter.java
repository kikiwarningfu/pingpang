package heyong.intellectPinPang.adapter.game;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.club.ClubMemberShipListAdapter;
import heyong.intellectPinPang.bean.competition.Contact;
import heyong.intellectPinPang.cn.CNPinyin;
import heyong.intellectPinPang.cn.ClubChoiceHeaderHolder;
import heyong.intellectPinPang.cn.SelectTeamNumberHolder;
import heyong.intellectPinPang.cn.stickyheader.StickyHeaderAdapter;

/**
 * 选择参赛队员
 */
public class SelectTeamNumbersAdapter extends RecyclerView.Adapter<SelectTeamNumberHolder> implements StickyHeaderAdapter<ClubChoiceHeaderHolder> {
    public static final String TAG = ClubMemberShipListAdapter.class.getSimpleName();
    private final List<CNPinyin<Contact>> cnPinyinList;

    public SelectTeamNumbersAdapter(List<CNPinyin<Contact>> cnPinyinList) {
        this.cnPinyinList = cnPinyinList;
    }

    public int getNum() {
        int num = 0;
        List<CNPinyin<Contact>> cnPinyinList = this.cnPinyinList;
        for (int j = 0; j < cnPinyinList.size(); j++) {
            Contact data = cnPinyinList.get(j).data;
            int selectType = data.getSelectType();
            if (selectType == 2) {
                num = num + 1;
            }

        }
        return num;
    }

    @Override
    public int getItemCount() {
        return cnPinyinList.size();
    }

    @Override
    public SelectTeamNumberHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectTeamNumberHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_select_team_numbers, parent, false));
    }


    @Override
    public void onBindViewHolder(SelectTeamNumberHolder holder, int position) {
        Contact contact = cnPinyinList.get(position).data;
//        holder.iv_header.setImageResource(contact.imgUrl);
        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnListener(position, contact);
                }
            }
        });
        int selectType = contact.getSelectType();
        if (selectType == 0) {
            holder.rlContainer.setEnabled(false);
            holder.tvSelect.setText("选择");
            holder.tvSelect.setTextColor(Color.parseColor("#FFFFFF"));
            holder.tvSelect.setBackgroundResource(R.drawable.shape_no_select);
        } else if (selectType == 1) {
            holder.rlContainer.setEnabled(true);
            holder.tvSelect.setText("选择");
            holder.tvSelect.setTextColor(Color.parseColor("#FF4795ED"));
            holder.tvSelect.setBackgroundResource(R.drawable.shape_solid_blue_3);
        } else {
            holder.rlContainer.setEnabled(true);
            holder.tvSelect.setText("已选");
            holder.tvSelect.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvSelect.setBackgroundResource(R.drawable.shape_club_blue_3);
        }

        holder.tv_name.setText(contact.name);
    }

    public List<CNPinyin<Contact>> getDatas() {
        return cnPinyinList;
    }

    @Override
    public long getHeaderId(int childAdapterPosition) {
        return cnPinyinList.get(childAdapterPosition).getFirstChar();
    }

    @Override
    public void onBindHeaderViewHolder(ClubChoiceHeaderHolder holder, int childAdapterPosition) {
        holder.tv_header.setText(String.valueOf(cnPinyinList.get(childAdapterPosition).getFirstChar()));
    }

    @Override
    public ClubChoiceHeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new ClubChoiceHeaderHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_club_choice_city_header, parent, false));
    }

    /**
     * 定义一个接口
     */
    public interface onMyListener {
        void OnListener(int position, Contact contact);
    }

    /**
     * 定义一个变量储存数据
     */
    private onMyListener listener;

    /**
     * 提供公共的方法,并且初始化接口类型的数据
     */
    public void setListener(onMyListener listener) {
        this.listener = listener;
    }

}
