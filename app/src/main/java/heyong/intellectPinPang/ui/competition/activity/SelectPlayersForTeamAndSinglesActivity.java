package heyong.intellectPinPang.ui.competition.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.SelectPlayersForTeamAndSinglesAdapter;
import heyong.intellectPinPang.bean.competition.ChooseJoinMatchUserBean;
import heyong.intellectPinPang.bean.competition.SelectPeopleProjectBean;
import heyong.intellectPinPang.bean.competition.SelectPeopleProjectItemBean;
import heyong.intellectPinPang.bean.competition.SelectPlayerForTeamBean;
import heyong.intellectPinPang.databinding.ActivitySelectPlayersForTeamAndSinglesBinding;
import heyong.intellectPinPang.ui.competition.CompetitionViewModel;
import heyong.intellectPinPang.utils.MapRemoveNullUtil;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;

/**
 * 团体和单打  选择运动员
 */
public class SelectPlayersForTeamAndSinglesActivity extends BaseActivity<ActivitySelectPlayersForTeamAndSinglesBinding, CompetitionViewModel> implements View.OnClickListener {
    SelectPlayersForTeamAndSinglesAdapter selectPlayersForTeamAndSinglesAdapter;
    MyDividerItemDecoration mSixDiD;
    List<ChooseJoinMatchUserBean.PlayersBean> playersList;
    public static final String DUIWU_NAME = "team_name";
    public static final String TEAM_TITLE = "team_title";
    public static final String DUIWU_POSITION = "duwiu_position";
    public static final String CLICK_POSITION = "click_position";
    public static final String CLICK_POJECT_ID = "click_project_id";
    private String clickProjectId = "";
    private int duiwuPosition = 0;
    private int clickPosition = 0;

    public static final String TAG = SelectPlayersForTeamAndSinglesActivity.class.getSimpleName();
    private String duiwuName;
    private String teamTitle;

    public static final String MATCH_ID = "match_id";
    private String strMatchId = "";
    public static final String CLUB_ID = "club_id";
    private String strClubId = "";
    public static final String PROJECT_ID = "project_id";
    private String strProjectId = "";
    private List<String> userIdList = new ArrayList<>();
    private String strSexType = "";
    List<SelectPlayerForTeamBean.SelectBean> myPlayList = new ArrayList<>();
    List<String> projectList = new ArrayList<>();
    List<SelectPeopleProjectBean> selectPeopleProjectBeans;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_select_players_for_team_and_singles;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        mSixDiD = new MyDividerItemDecoration(SelectPlayersForTeamAndSinglesActivity.this, 10,
                getResources().getColor(R.color.white));
        playersList = (List<ChooseJoinMatchUserBean.PlayersBean>) getIntent().getSerializableExtra("data");
        selectPeopleProjectBeans = (List<SelectPeopleProjectBean>) getIntent().getSerializableExtra("selectPeopleProjectBeans");

        clickProjectId = getIntent().getStringExtra(CLICK_POJECT_ID);


//        Map<String, List<SelectPeopleProjectBean>> collect = selectPeopleProjectBeans.stream().collect(Collectors.groupingBy
//                (SelectPeopleProjectBean::getItemProjectId));

        Map<String, List<SelectPeopleProjectBean>> map = new HashMap<>();
        for (SelectPeopleProjectBean selectPeopleProjectBean : selectPeopleProjectBeans) {
            String key = selectPeopleProjectBean.getItemProjectId();
            if (map.containsKey(key)) {
                //map中存在以此id作为的key，将数据存放当前key的map中
                map.get(key).add(selectPeopleProjectBean);
            } else {
                //map中不存在以此id作为的key，新建key用来存放数据
                List<SelectPeopleProjectBean> userList = new ArrayList<>();
                userList.add(selectPeopleProjectBean);
                map.put(key, userList);
            }
        }
        Log.e(TAG, "initView: size1===" + map.size());
        MapRemoveNullUtil.removeNullKey(map);


        List<SelectPeopleProjectBean> removeAfterList = map.remove(clickProjectId);
        Log.e(TAG, "initView: size2====" + map.size());
        List<Long> userIdsListRemove = new ArrayList<>();
        if (map != null && map.size() > 0) {


            for (Map.Entry<String, List<SelectPeopleProjectBean>> entry : map.entrySet()) {

                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                List<SelectPeopleProjectBean> value = entry.getValue();
                if (value != null && value.size() > 0) {
                    for (int m = 0; m < value.size(); m++) {
                        List<SelectPeopleProjectItemBean> newList = value.get(m).getNewList();
                        for (int n = 0; n < newList.size(); n++) {
                            List<SelectPeopleProjectItemBean.Players> playersList = newList.get(n).getPlayersList();
                            if (playersList.size() > 0) {
                                for (int mm = 0; mm < playersList.size(); mm++) {
                                    userIdsListRemove.add(playersList.get(mm).getUserId());
                                }
                            }
                        }
                    }
                }
            }


        }
        Log.e(TAG, "initView: userIdsListRemove===" + new Gson().toJson(userIdsListRemove));

//        for(int i=0;i>removeAfterList.size();i++)
//        {
//            SelectPeopleProjectBean selectPeopleProjectBean = removeAfterList.get(i);
//            List<SelectPeopleProjectItemBean> newList = selectPeopleProjectBean.getNewList();
//            for(int m=0;m<newList.size();m++)
//            {
//                SelectPeopleProjectItemBean selectPeopleProjectItemBean = newList.get(m);
//
//            }
//        }

        Log.e(TAG, "initView: " + new Gson().toJson(removeAfterList));


        duiwuName = getIntent().getStringExtra(DUIWU_NAME);
        teamTitle = getIntent().getStringExtra(TEAM_TITLE);
        duiwuPosition = getIntent().getIntExtra(DUIWU_POSITION, 0);
        clickPosition = getIntent().getIntExtra(CLICK_POSITION, 0);

        strMatchId = getIntent().getStringExtra(MATCH_ID);
        strClubId = getIntent().getStringExtra(CLUB_ID);
        strProjectId = getIntent().getStringExtra(PROJECT_ID);
        strSexType = getIntent().getStringExtra("sex_type");
        projectList = (List<String>) getIntent().getSerializableExtra("projectList");


        Log.e(TAG, "initView: " + duiwuName);
        Log.e(TAG, "initView: " + teamTitle);
        selectPlayersForTeamAndSinglesAdapter = new SelectPlayersForTeamAndSinglesAdapter(
                SelectPlayersForTeamAndSinglesActivity.this, mSixDiD);
        mBinding.rvPlayersSelect.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvPlayersSelect.setAdapter(selectPlayersForTeamAndSinglesAdapter);
        List<SelectPlayerForTeamBean.SelectBean> selectBeanList = new ArrayList<>();
        int mm = 0;
        if (playersList != null && playersList.size() > 0) {
            for (int i = 0; i < playersList.size(); i++) {
                selectBeanList.add(new SelectPlayerForTeamBean.SelectBean("" + playersList.get(i).getUserName(), playersList.get(i).isSelect(), "" + playersList.get(i).getSex(),
                        playersList.get(i).getUserId(), "" + playersList.get(i).getUserName())
                );
            }
            SelectPlayerForTeamBean selectPlayerForTeamBean = new SelectPlayerForTeamBean("" + teamTitle, "" + duiwuName);
            selectPlayerForTeamBean.setDataList(selectBeanList);
            List<SelectPlayerForTeamBean> dataList = new ArrayList<>();
            dataList.add(selectPlayerForTeamBean);
            //移除其他组的元素的id
            Log.e(TAG, "initView:size===1=== " + dataList.size());
            Log.e(TAG, "initView: remove 移除 dataList===" + new Gson().toJson(dataList));
            Log.e(TAG, "initView: remove 移除 userIdsListRemove===" + new Gson().toJson(userIdsListRemove));

            List<SelectPlayerForTeamBean.SelectBean> removeDataList = new ArrayList<>();
            for (int mmm = 0; mmm < dataList.size(); mmm++) {
                List<SelectPlayerForTeamBean.SelectBean> dataList1 = dataList.get(mmm).getDataList();
                for (int nnn = 0; nnn < dataList1.size(); nnn++) {
                    SelectPlayerForTeamBean.SelectBean selectBean = dataList1.get(nnn);
                    for (int pp = 0; pp < userIdsListRemove.size(); pp++) {
                        if (selectBean.getUserId() == userIdsListRemove.get(pp)) {
                            Log.e(TAG, "initView: remove 移除" + selectBean.getUserId());
                            removeDataList.add(dataList1.get(nnn));
                        }
                    }
                }
                Log.e(TAG, "initView: remove 移除===" + new Gson().toJson(removeDataList));
                dataList1.removeAll(removeDataList);
            }
            Log.e(TAG, "initView:size===2=== " + new Gson().toJson(dataList));
            selectPlayersForTeamAndSinglesAdapter.setNewData(dataList);


        }
//        selectPlayersForTeamAndSinglesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                SelectPlayerForTeamBean selectPlayerForTeamBean = selectPlayersForTeamAndSinglesAdapter.getData().get(position);
//                selectPlayerForTeamBean.setSelect(!selectPlayerForTeamBean.isSelect());
//                selectPlayersForTeamAndSinglesAdapter.setData(position, selectPlayerForTeamBean);
//                List<SelectPlayerForTeamBean> data = selectPlayersForTeamAndSinglesAdapter.getData();
//                int m = 0;
//                for (int i = 0; i < data.size(); i++) {
//                    if (data.get(i).isSelect()) {
//                        m++;
//                    }
//                }
//                mBinding.tvAlreadySelectNum.setText("" + m);
//            }
//        });

        mViewModel.judgeUserShiFouChongFuLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
//                    Intent intent = new Intent();
//                    intent.putExtra("listbean", (Serializable) myPlayList);
//                    intent.putExtra("duiwuposition", duiwuPosition);
//                    intent.putExtra("clickposition", clickPosition);
//                    intent.putExtra("sex_type", "" + strSexType);
//                    setResult(RESULT_OK, intent);
//                    finish();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

        mBinding.tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SelectPlayerForTeamBean selectPlayerForTeamBean = selectPlayersForTeamAndSinglesAdapter.getData().get(0);
                    List<SelectPlayerForTeamBean.SelectBean> dataList = selectPlayerForTeamBean.getDataList();
                    SelectPlayerForTeamBean.SelectBean selectBean = null;
                    myPlayList.clear();
                    boolean isSelect = false;
                    for (int i = 0; i < dataList.size(); i++) {
                        if (dataList.get(i).isSelect()) {
                            /*选中了*/
                            isSelect = true;
                            selectBean = dataList.get(i);
                            myPlayList.add(selectBean);

                        }
                    }
                    if (isSelect) {
                        userIdList.clear();
                        for (int m = 0; m < myPlayList.size(); m++) {
                            userIdList.add("" + myPlayList.get(m).getUserId());
                        }
                        Intent intent = new Intent();
                        intent.putExtra("listbean", (Serializable) myPlayList);
                        intent.putExtra("duiwuposition", duiwuPosition);
                        intent.putExtra("clickposition", clickPosition);
                        intent.putExtra("sex_type", "" + strSexType);
                        setResult(RESULT_OK, intent);
                        finish();
//                        mViewModel.judgeUserShiFouChongFu("" + strMatchId, "" + strClubId, "" + strProjectId, userIdList, projectList);
                    } else {
                        finish();
                    }
                } catch (Exception e) {
                    toast("请选择人数");
                }

            }
        });

    }

    //并集
    //                resultList.addAll(resultListBeanList);
    //                Log.e(TAG, "initMatchScore: 并集"+resultList.size() );
    //交集
    //                resultList.retainAll(resultListBeanList);
    //                Log.e(TAG, "initMatchScore: 交集"+resultList.size() );
    //差集
    //                list1.removeAll(list2);
    //                resultList.removeAll(resultListBeanList);
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_finish:
                finish();


                break;
        }
    }
}