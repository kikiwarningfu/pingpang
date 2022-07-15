package heyong.intellectPinPang.adapter.game;

import android.content.Context;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.CreateXlClubContestInfoBean;
import heyong.intellectPinPang.databinding.ItemBsResponityBinding;

/**
 * Create On 2019/8/21 17:37
 * Copyrights 2019/8/21 handongkeji All rights reserved
 * ClassName:BsResponityAdapter
 * PackageName:com.hejiumao.app.business.mine.adapter
 * Site:http://www.handongkeji.com
 * Author: wanghongfu
 * Date: 2019/8/21 17:37
 * Description:库存
 */
public class BsResponityAdapter extends BaseQuickAdapter<CreateXlClubContestInfoBean.XlClubContestTeamsBean, BaseViewHolder> {
    private Context context;
    RsResponityChildAdapter responityChildAdapter;
    public OnItemChildClickListener listener;

    public BsResponityAdapter(Context context) {
        super(R.layout.item_bs_responity);
        this.context = context;
    }

    public void setOnItemChildListener(OnItemChildClickListener listener) {
        this.listener = listener;
    }


    @Override
    protected void convert(BaseViewHolder helper, CreateXlClubContestInfoBean.XlClubContestTeamsBean item) {
        ItemBsResponityBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> data =getData().get(helper.getAdapterPosition()).getContestTeamMembers();
//                List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> data = responityChildAdapter.getData();
                if (data != null && data.size() > 0) {
                    boolean close = data.get(0).isClose();
                    if (close) {
                        //显示了隐藏按钮
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setClose(false);
                        }
                        binding.tvEdit.setText("编辑");
                    } else {
                        //刚开始一定是隐藏的状态 那么需要加一个判断
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setClose(true);
                        }
                        binding.tvEdit.setText("完成");
                    }
//                    responityChildAdapter.setNewData(data);
                    item.setContestTeamMembers(data);
                    notifyItemChanged(helper.getAdapterPosition(),item);
                } else {
                    if(data!=null&&data.size()>0) {
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setClose(false);
                        }
                        binding.tvEdit.setText("编辑");
                    }
                }

            }
        });
        RecyclerView rvItemList = helper.getView(R.id.rv_responity);
        TextView tvPosition = helper.getView(R.id.tv_position);
        LinearLayout llSelectPeople = helper.getView(R.id.ll_select_people);
        ImageView ivdel = helper.getView(R.id.iv_del);
        View divider = helper.getView(R.id.view_divider);
        rvItemList.setLayoutManager(new GridLayoutManager(context, 4));
        if (item.getContestTeamMembers() != null && item.getContestTeamMembers().size() > 0) {
            responityChildAdapter = new RsResponityChildAdapter(item.getContestTeamMembers().size());
        } else {
            responityChildAdapter = new RsResponityChildAdapter(0);
        }
        int width = rvItemList.getWidth();
        responityChildAdapter.setWidth(width);
        rvItemList.setAdapter(responityChildAdapter);

        List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> contestTeamMembers = item.getContestTeamMembers();
//        if (contestTeamMembers != null && contestTeamMembers.size() > 0) {
//            for (int i = 0; i < contestTeamMembers.size(); i++) {
//                contestTeamMembers.get(i).setClose(false);
//            }
//        }
        responityChildAdapter.setNewData(contestTeamMembers);
        responityChildAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_close_blue://红色的关闭按钮  移除这条数据 然后刷新适配器
                        responityChildAdapter.remove(position);
                        responityChildAdapter.notifyItemChanged(position);
                        break;
                }
            }
        });
        int size = getData().size();
        if (helper.getAdapterPosition() == size - 1) {
            divider.setVisibility(View.GONE);
        } else {
            divider.setVisibility(View.VISIBLE);
        }
        tvPosition.setText("" + (helper.getAdapterPosition() + 1));
        ivdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onDelClick(helper.getAdapterPosition());
            }
        });
        llSelectPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onSelectPeopleGroup(helper.getAdapterPosition(), item);
            }
        });

    }

    public void setShowRefund(boolean showRefund) {
//        this.showRefund = showRefund;
        notifyDataSetChanged();
    }

    public interface OnItemChildClickListener {
        void onSelectPeopleGroup(int position, CreateXlClubContestInfoBean.XlClubContestTeamsBean item);

        void onDelClick(int position);//删除


    }
}
