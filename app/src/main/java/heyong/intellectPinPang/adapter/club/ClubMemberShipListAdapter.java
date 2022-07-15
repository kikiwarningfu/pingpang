package heyong.intellectPinPang.adapter.club;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.Contact;
import heyong.intellectPinPang.cn.CNPinyin;
import heyong.intellectPinPang.cn.ContactHolder;
import heyong.intellectPinPang.cn.HeaderHolder;
import heyong.intellectPinPang.cn.stickyheader.StickyHeaderAdapter;

/**
 * 俱乐部会员成员列表
 */
public class ClubMemberShipListAdapter extends RecyclerView.Adapter<ContactHolder> implements StickyHeaderAdapter<HeaderHolder> {
    public static final String TAG = ClubMemberShipListAdapter.class.getSimpleName();
    private final List<CNPinyin<Contact>> cnPinyinList;

    public ClubMemberShipListAdapter(List<CNPinyin<Contact>> cnPinyinList) {
        this.cnPinyinList = cnPinyinList;
    }

    @Override
    public int getItemCount() {
        return cnPinyinList.size();
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_club_member_ship, parent, false));
    }


    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        Contact contact = cnPinyinList.get(position).data;
//        holder.iv_header.setImageResource(contact.imgUrl);
        holder.tv_name.setText(contact.name);
    }

    @Override
    public long getHeaderId(int childAdapterPosition) {
        return cnPinyinList.get(childAdapterPosition).getFirstChar();
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder holder, int childAdapterPosition) {
        holder.tv_header.setText(String.valueOf(cnPinyinList.get(childAdapterPosition).getFirstChar()));
//        Log.e(TAG, "onBindHeaderViewHolder: " + String.valueOf(cnPinyinList.get(childAdapterPosition).getFirstChar()));
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new HeaderHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_header_club_member_ship, parent, false));
    }

}
