package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.XlClubMemberListBean;

/**
 * 负责人的适配器
 */
public class MyHeaderAdapter extends BaseAdapter {

    public static final String TAG = MyHeaderAdapter.class.getSimpleName();
    private List<XlClubMemberListBean.ChargeListBean> list = null;
    private Context mContext;

    public MyHeaderAdapter(Context mContext, List<XlClubMemberListBean.ChargeListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }


    public int getCount() {
        Log.e(TAG, "getCount: listsize---" + list.size());
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // 更新ListView
    public void updateListView(List<XlClubMemberListBean.ChargeListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    final static class ViewHolder {
        TextView tv_fistletters;
        TextView tv_info;
        TextView tvIdentify;
        LinearLayout llContent;
        CircleImageView imageView;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getFistLetter().charAt(0);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        Log.e(TAG, "getView: position===" + position);
        final XlClubMemberListBean.ChargeListBean chargeListBean = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.my_header_view, null);
            viewHolder.tv_fistletters = (TextView) view.findViewById(R.id.tv_fistletters);
            viewHolder.tv_info = (TextView) view.findViewById(R.id.tv_info);
            viewHolder.tvIdentify = (TextView) view.findViewById(R.id.tv_identify);
            viewHolder.llContent = (LinearLayout) view.findViewById(R.id.ll_content);
            viewHolder.imageView = view.findViewById(R.id.iv_header);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        Log.e(TAG, "getView: fitstLetter===" + list.get(position).getFistLetter());
        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tv_fistletters.setVisibility(View.VISIBLE);
            viewHolder.tv_fistletters.setText(chargeListBean.getFistLetter());
        } else {
            viewHolder.tv_fistletters.setVisibility(View.GONE);
        }
        viewHolder.tv_info.setText(this.list.get(position).getName());
        ImageLoader.loadImage(viewHolder.imageView, list.get(position).getHeadImg(), R.drawable.img_default_avatar
                , R.drawable.img_default_avatar);
        viewHolder.tvIdentify.setText("负责人");
        viewHolder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    long id = list.get(position).getId();
                    listener.OnMyListener("" + id, "" + list.get(position).getUserId());
                }
            }
        });
        return view;
    }

    /**
     * 获取第一次出现该首字母的List所在的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getFistLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 定义一个接口
     */
    public interface onMyListener {
        void OnMyListener(String ids, String sportsUserId);
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
