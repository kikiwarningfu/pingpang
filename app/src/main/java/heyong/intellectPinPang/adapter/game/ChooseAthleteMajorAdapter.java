package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.SelectPeopleProjectItemBean;
import heyong.intellectPinPang.databinding.ItemChooseAthleteMajorBinding;

import static heyong.intellectPinPang.ui.BaseActivity.isFastClick;

/**
 * 专业双打
 */
public class ChooseAthleteMajorAdapter extends BaseQuickAdapter<SelectPeopleProjectItemBean, BaseViewHolder> {
    private Context context;
    List<SelectPeopleProjectItemBean> manList;
    List<SelectPeopleProjectItemBean> womanList;
    private int ownerType;
    List<SelectPeopleProjectItemBean.Players> newPlayList = new ArrayList<>();

    public ChooseAthleteMajorAdapter(Context context) {
        super(R.layout.item_choose_athlete_major);
        this.context = context;
    }

    public void setList(List<SelectPeopleProjectItemBean> manList, List<SelectPeopleProjectItemBean> womanList) {
        this.manList = manList;
        this.womanList = womanList;
        notifyDataSetChanged();
    }

    public void setOwnerType(int ownerType) {
        this.ownerType = ownerType;
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectPeopleProjectItemBean item) {
        ItemChooseAthleteMajorBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvDuiwuName.setText("" + item.getGroupName() + " " + item.getName().replace("团体","双打"));
        RecyclerView rvTeamList = helper.getView(R.id.rv_team_list);
        rvTeamList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        ItemChooseAthleteMajorListAdapter itemChooseAthleteMajorListAdapter = new ItemChooseAthleteMajorListAdapter();
        rvTeamList.setAdapter(itemChooseAthleteMajorListAdapter);
        newPlayList.clear();
        /*判断  一下 */
        if (ownerType == 1) {
            /*男子双打*/
            SelectPeopleProjectItemBean selectPeopleProjectItemBean = manList.get(helper.getAdapterPosition());
            List<SelectPeopleProjectItemBean.Players> playersList = selectPeopleProjectItemBean.getPlayersList();
            int newPlaySize = playersList.size() / 2;
            for (int i = 0; i < newPlaySize; i++) {
                newPlayList.add(new SelectPeopleProjectItemBean.Players());
            }
            itemChooseAthleteMajorListAdapter.setNewData(newPlayList);
            itemChooseAthleteMajorListAdapter.setManList(item.getPlayersList(), ownerType);
        } else if (ownerType == 2) {
            /*女子双打*/
            SelectPeopleProjectItemBean selectPeopleProjectItemBean = womanList.get(helper.getAdapterPosition());
            List<SelectPeopleProjectItemBean.Players> playersList = selectPeopleProjectItemBean.getPlayersList();
            int newPlaySize2 = playersList.size() / 2;
            for (int i = 0; i < newPlaySize2; i++) {
                newPlayList.add(new SelectPeopleProjectItemBean.Players());
            }
            itemChooseAthleteMajorListAdapter.setNewData(newPlayList);
            itemChooseAthleteMajorListAdapter.setWoManList(item.getPlayersList(), ownerType);

        } else {
            /*混双  也是有多个的 怎么处理*/
            List<SelectPeopleProjectItemBean.Players> manPlayerList = manList.get(helper.getAdapterPosition()).getPlayersList();
            List<SelectPeopleProjectItemBean.Players> woManPlayerList = womanList.get(helper.getAdapterPosition()).getPlayersList();
            if (manPlayerList.size() >= woManPlayerList.size()) {
                for (int i = 0; i < manPlayerList.size(); i++) {
                    newPlayList.add(new SelectPeopleProjectItemBean.Players());
                }
                itemChooseAthleteMajorListAdapter.setNewData(newPlayList);
            } else {
                for (int i = 0; i < woManPlayerList.size(); i++) {
                    newPlayList.add(new SelectPeopleProjectItemBean.Players());
                }
                itemChooseAthleteMajorListAdapter.setNewData(newPlayList);
            }
            itemChooseAthleteMajorListAdapter.setList(manPlayerList, woManPlayerList, ownerType);
        }
        /*判断点击事件*/
        itemChooseAthleteMajorListAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    List<SelectPeopleProjectItemBean.Players> manPlayers = itemChooseAthleteMajorListAdapter.getManPlayers();
                    List<SelectPeopleProjectItemBean.Players> womanPlayers = itemChooseAthleteMajorListAdapter.getWomanPlayers();
                    newPlayList.clear();
                    int leftType = 0;
                    switch (view.getId()) {
                        case R.id.ll_man_name_left://左边第一单打
                            leftType = 1;
                            if (ownerType == 1) {
                                /*男子双打*/
                                if (listener != null) {
                                    listener.OnMyListener(ownerType, manPlayers, leftType, helper.getAdapterPosition(), position);
                                }
                            } else if (ownerType == 2) {
                                /*女子双打*/
                                if (listener != null) {
                                    listener.OnMyListener(ownerType, womanPlayers, leftType, helper.getAdapterPosition(), position);
                                }
                            } else if (ownerType == 3) {
                                /*混双  也是有多个的 怎么处理*/
                                if (listener != null) {
                                    listener.OnMyListener(ownerType, manPlayers, leftType, helper.getAdapterPosition(), position);
                                }
                            }

                            break;
                        case R.id.ll_man_name_right://右边的选项
                            leftType = 2;
                            if (ownerType == 1) {
                                /*男子双打*/
                                if (listener != null) {
                                    listener.OnMyListener(ownerType, manPlayers, leftType, helper.getAdapterPosition(), position);
                                }
                            } else if (ownerType == 2) {
                                /*女子双打*/
                                if (listener != null) {
                                    listener.OnMyListener(ownerType, womanPlayers, leftType, helper.getAdapterPosition(), position);
                                }
                            } else if (ownerType == 3) {
                                /*混双  也是有多个的 怎么处理*/
                                if (listener != null) {
                                    listener.OnMyListener(ownerType, womanPlayers, leftType, helper.getAdapterPosition(), position);
                                }
                            }
                            break;
                    }
                }
            }
        });
    }

    /**
     * 定义一个接口
     */
    public interface onMyListener {
        void OnMyListener(int ownerType, List<SelectPeopleProjectItemBean.Players> manPlayers, int leftType,
                          int waiPosition, int neiPosition);
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
