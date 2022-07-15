package heyong.intellectPinPang.adapter.game;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import heyong.handong.framework.utils.ToastUtils;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.ChooseJoinMatchUserBean;
import heyong.intellectPinPang.bean.competition.SelectPeopleProjectItemBean;
import heyong.intellectPinPang.adapter.channel.MatchPlayerAdapter;
import heyong.intellectPinPang.adapter.channel.SelectPeopleDoublePrifressionalAdapter;
import heyong.intellectPinPang.adapter.channel.SelectPeopleTeamAdapter;
import heyong.intellectPinPang.bean.competition.SelectPeopleProjectBean;
import heyong.intellectPinPang.databinding.ItemSelectLeadCoachBinding;
import heyong.intellectPinPang.databinding.ItemSelectPeopleDoubleNoProfressionalBinding;
import heyong.intellectPinPang.databinding.ItemSelectPeopleDoubleProfressionalBinding;
import heyong.intellectPinPang.ui.competition.activity.ClubTeamRegisterChannelActivity;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;


/**
 * 选择队员和项目
 */
public class SelectPeopleProjectAdapter extends BaseQuickAdapter<SelectPeopleProjectBean, BaseViewHolder> {
    private Context context;
    private int width;
    MyDividerItemDecoration mSixDiD;
    public static final String TAG = SelectPeopleProjectAdapter.class.getSimpleName();

    public SelectPeopleProjectAdapter(Context context, int width, MyDividerItemDecoration mSixDiD) {
        super(0);
        this.context = context;
        this.width = width;
        this.mSixDiD = mSixDiD;
        MultiTypeDelegate<SelectPeopleProjectBean> multiTypeDelegate = new MultiTypeDelegate<SelectPeopleProjectBean>() {
            @Override
            protected int getItemType(SelectPeopleProjectBean o) {
                return o.getCardType();
            }
        };
        multiTypeDelegate.registerItemType(0, R.layout.item_select_lead_group);//0 领队
        multiTypeDelegate.registerItemType(1, R.layout.item_select_lead_coach);//1 教练员
        multiTypeDelegate.registerItemType(2, R.layout.item_select_people_team);//2 团体
        multiTypeDelegate.registerItemType(3, R.layout.item_select_people_single);//3  混合单打
        multiTypeDelegate.registerItemType(4, R.layout.item_select_people_double_profressional);//双打专业
        multiTypeDelegate.registerItemType(5, R.layout.item_select_people_double_no_profressional);//双打非专业
        setMultiTypeDelegate(multiTypeDelegate);
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectPeopleProjectBean item) {
        switch (helper.getItemViewType()) {
            case 0://0  领队  item_select_lead_group
                RecyclerView recyclerView = helper.getView(R.id.rv_lead_group);
                helper.addOnClickListener(R.id.tv_mixed_singles_lead_group);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 5);
                recyclerView.removeItemDecoration(mSixDiD);
                recyclerView.addItemDecoration(mSixDiD);
                //绑定manager
                recyclerView.setLayoutManager(gridLayoutManager);
                ClubTeamRegisterChannelActivity.SixModelAdapter sixModelAdapter = new ClubTeamRegisterChannelActivity.SixModelAdapter(context, width);
                recyclerView.setAdapter(sixModelAdapter);
                try {
                    List<SelectPeopleProjectItemBean.Players> playersList1 = item.getNewList().get(0).getPlayersList();
                    if (playersList1.size() > 0) {
                        sixModelAdapter.setNewData(playersList1);
                    } else {
                        sixModelAdapter.setNewData(new ArrayList<>());
                    }

                } catch (Exception e) {
                }


                break;
            case 1://教练员  item_select_lead_coach
                ItemSelectLeadCoachBinding binding1 = DataBindingUtil.bind(helper.getConvertView());
                RecyclerView recyclerViewCoach = helper.getView(R.id.rv_lead_coach);
                helper.addOnClickListener(R.id.tv_mixed_single_coach);
                helper.addOnClickListener(R.id.tv_mixed_singles_lead_group);
                GridLayoutManager gridLayoutManager2 = new GridLayoutManager(context, 5);
                recyclerViewCoach.removeItemDecoration(mSixDiD);
                recyclerViewCoach.addItemDecoration(mSixDiD);
                //绑定manager
                recyclerViewCoach.setLayoutManager(gridLayoutManager2);
                ClubTeamRegisterChannelActivity.SixModelAdapter sixModelAdapter2 = new ClubTeamRegisterChannelActivity.SixModelAdapter(context, width);
                recyclerViewCoach.setAdapter(sixModelAdapter2);

                List<SelectPeopleProjectItemBean.Players> playersList2 = item.getNewList().get(0).getPlayersList();
                if (playersList2.size() > 0) {
                    sixModelAdapter2.setNewData(playersList2);
                } else {
                    sixModelAdapter2.setNewData(new ArrayList<>());
                }
                binding1.tvSum.setText("" + playersList2.size());

                break;
            case 2://团体  item_select_people_team
                RecyclerView rvSelectPeopleTeam = helper.getView(R.id.rv_select_people_team);
                rvSelectPeopleTeam.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                SelectPeopleTeamAdapter selectPeopleTeamAdapter = new SelectPeopleTeamAdapter(context, width, mSixDiD);
                List<SelectPeopleProjectItemBean> newList = item.getNewList();
                String itemProjectName = item.getProjectName();

                rvSelectPeopleTeam.setAdapter(selectPeopleTeamAdapter);
                selectPeopleTeamAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int positions) {

                        switch (view.getId()) {
                            case R.id.tv_select_add_join:

                                if (listener != null) {
                                    SelectPeopleProjectItemBean selectPeopleProjectItemBean = selectPeopleTeamAdapter.getData().get(positions);
                                    listener.OnListener(positions, selectPeopleProjectItemBean, helper.getAdapterPosition(), item);
                                }
                                break;
                            case R.id.ll_add_team://添加队伍
                                List<SelectPeopleProjectItemBean> data = selectPeopleTeamAdapter.getData();
                                List<ChooseJoinMatchUserBean.PlayersBean> tuantiOtherPlayers = item.getTuantiOtherPlayers();
                                List<SelectPeopleProjectItemBean.Players> allSelectPlayList = new ArrayList<>();
                                for (int m = 0; m < item.getNewList().size(); m++) {
                                    List<SelectPeopleProjectItemBean.Players> playersList = item.getNewList().get(m).getPlayersList();
                                    allSelectPlayList.addAll(playersList);
                                }
                                if (allSelectPlayList.size() == 0) {
                                    int size = tuantiOtherPlayers.size();

                                    if (data.size() <= (size / 3) - 1) {
                                        if (tuantiOtherPlayers.size() - allSelectPlayList.size() >= 6) {
                                            if (data.size() > 0) {
                                                String name = data.get(0).getName();
                                                int type = data.get(0).getType();
                                                SelectPeopleProjectItemBean selectPeopleProjectItemBean = new SelectPeopleProjectItemBean();
                                                selectPeopleProjectItemBean.setPlayersList(new ArrayList<>());
                                                selectPeopleProjectItemBean.setName("" + name);
                                                selectPeopleProjectItemBean.setGroupName("" + data.get(0).getGroupName());
                                                selectPeopleProjectItemBean.setType(type);
                                                selectPeopleProjectItemBean.setDuiwuPosition("1");
                                                data.add(selectPeopleProjectItemBean);
                                            }
                                            selectPeopleTeamAdapter.setNewData(data);
                                        } else {
                                            ToastUtils.showToast(context, "人数无法满足新增队伍条件");
                                        }
                                    } else {
                                        ToastUtils.showToast(context, "人数无法满足新增队伍条件");
                                    }
                                } else {
                                    int size = tuantiOtherPlayers.size();
                                    if (data.size() <= (size / 3) - 1) {
                                        if (tuantiOtherPlayers.size() - allSelectPlayList.size() >= 3) {
                                            if (data.size() > 0) {
                                                String name = data.get(0).getName();
                                                int type = data.get(0).getType();
                                                SelectPeopleProjectItemBean selectPeopleProjectItemBean = new SelectPeopleProjectItemBean();
                                                selectPeopleProjectItemBean.setPlayersList(new ArrayList<>());
                                                selectPeopleProjectItemBean.setName("" + name);
                                                selectPeopleProjectItemBean.setGroupName("" + data.get(0).getGroupName());
                                                selectPeopleProjectItemBean.setType(type);
                                                selectPeopleProjectItemBean.setDuiwuPosition("" + data.size());
                                                data.add(selectPeopleProjectItemBean);
                                            }
                                            selectPeopleTeamAdapter.setNewData(data);
                                        } else {
                                            ToastUtils.showToast(context, "人数无法满足新增队伍条件");
                                        }
                                    } else {
                                        ToastUtils.showToast(context, "人数无法满足新增队伍条件");
                                    }


                                }
//                                    List<SelectPeopleProjectItemBean> data2 = selectPeopleTeamAdapter.getData();


                                Log.e(TAG, "onItemChildClick: " + new Gson().toJson(selectPeopleTeamAdapter.getData()));
                                break;
                            case R.id.ll_del_team://删除队伍
                                List<SelectPeopleProjectItemBean> data1 = selectPeopleTeamAdapter.getData();
                                if (data1.size() > 1) {
                                    SelectPeopleProjectItemBean selectPeopleProjectItemBean = data1.get(positions);

                                    List<SelectPeopleProjectItemBean.Players> playersList = selectPeopleProjectItemBean.getPlayersList();
                                    if (playersList.size() > 0) {
                                        List<Long> userIdsLists = new ArrayList<>();
                                        for (int mm = 0; mm < playersList.size(); mm++) {
                                            long userId = playersList.get(mm).getUserId();
                                            userIdsLists.add(userId);
                                        }
//                                            if (listener != null) {
//                                                listener.onDelGroup(selectPeopleProjectItemBean, userIdsLists, item);
//                                            }
                                    }
                                    data1.remove(positions);
                                    selectPeopleTeamAdapter.setNewData(data1);
                                } else {
                                    if (listener != null) {
                                        listener.onDel();
                                    }
                                }
                                break;
                        }

                    }
                });
                if (newList.size() > 0) {
                    selectPeopleTeamAdapter.setNewData(newList);
                } else {
                    SelectPeopleProjectItemBean selectPeopleProjectItemBean = new SelectPeopleProjectItemBean();
                    selectPeopleProjectItemBean.setPlayersList(new ArrayList<>());
                    newList.add(selectPeopleProjectItemBean);
                }
                break;
            case 3:// w1 混合单打   item_select_people_single
                TextView tvProjectTitle = helper.getView(R.id.tv_project_title);
                tvProjectTitle.setText("" + item.getProjectName() + " " + item.getItemTitle());
                helper.addOnClickListener(R.id.tv_mixed_hunhe_danda);
                RecyclerView rvMixSingles = helper.getView(R.id.rv_mixed_singles);
                rvMixSingles.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                GridLayoutManager gridLayoutManager1 = new GridLayoutManager(context, 5);
                rvMixSingles.removeItemDecoration(mSixDiD);
                rvMixSingles.addItemDecoration(mSixDiD);
                rvMixSingles.setLayoutManager(gridLayoutManager1);
                ClubTeamRegisterChannelActivity.SixModelAdapter coachAdapters = new ClubTeamRegisterChannelActivity.SixModelAdapter(context, width);
                rvMixSingles.setAdapter(coachAdapters);
                if (item.getNewList().size() > 0) {
                    coachAdapters.setNewData(item.getNewList().get(0).getPlayersList());
                    TextView tvNum = helper.getView(R.id.tv_num);
                    tvNum.setText("" + item.getNewList().get(0).getPlayersList().size());
                } else {
                    coachAdapters.setNewData(new ArrayList<>());
                }
                break;
            case 4:// //双打--->专业   item_select_people_double_profressional
                /*团体增加 一个 双打 就增加一个  团体减少一个  双打 就减少一个*/
                helper.addOnClickListener(R.id.tv_select_people_double_profressional);
                ItemSelectPeopleDoubleProfressionalBinding binding2 = DataBindingUtil.bind(helper.getConvertView());
                binding2.tvItemTitle.setText("" + item.getProjectName() + " " + item.getItemTitle());
                SelectPeopleDoublePrifressionalAdapter matchPlayerAdapter = new SelectPeopleDoublePrifressionalAdapter(context, width);
                List<SelectPeopleProjectItemBean> newList1 = item.getNewList();

                binding2.rvTeamList.setAdapter(matchPlayerAdapter);
                binding2.rvTeamList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                if (newList1.size() > 0) {
                    matchPlayerAdapter.setNewData(newList1);
                } else {
                    matchPlayerAdapter.setNewData(new ArrayList<>());
                }

                break;

            case 5:////双打非专业   item_select_people_double_no_profressional
                ItemSelectPeopleDoubleNoProfressionalBinding binding3 = DataBindingUtil.bind(helper.getConvertView());
                if (TextUtils.isEmpty(item.getProjectName())) {
                    binding3.tvItemTitle.setText(item.getItemTitle());
                } else {
                    binding3.tvItemTitle.setText("" + item.getProjectName() + " " + item.getItemTitle());
                }
                helper.addOnClickListener(R.id.tv_feizhuanye_shuangda);
                MatchPlayerAdapter matchPlayerAdapter1 = new MatchPlayerAdapter();
                binding3.rvTeamListNoProfressional.setAdapter(matchPlayerAdapter1);
                binding3.rvTeamListNoProfressional.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                List<SelectPeopleProjectItemBean> newList2 = item.getNewList();
                if (newList2.size() > 0) {
                    List<SelectPeopleProjectItemBean.Players> playersList = newList2.get(0).getPlayersList();
                    matchPlayerAdapter1.setNewData(playersList);
                } else {
                    matchPlayerAdapter1.setNewData(new ArrayList<>());
                }

                break;
        }
    }


    /**
     * 定义一个接口
     */
    public interface onMyListener {
        void OnListener(int position, SelectPeopleProjectItemBean selectPeopleProjectItemBean, int waiPosition, SelectPeopleProjectBean waiprojectBean);

        void onDel();

        void onAdd(int position, SelectPeopleProjectItemBean selectPeopleProjectItemBean, int waiPosition, SelectPeopleProjectBean waiprojectBean);

        void notifyGroupData(List<SelectPeopleProjectBean> allData);

        void onDelGroup(SelectPeopleProjectItemBean selectPeopleProjectItemBean, List<String> userIdsLists, SelectPeopleProjectBean selectPeopleProjectBean);
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

