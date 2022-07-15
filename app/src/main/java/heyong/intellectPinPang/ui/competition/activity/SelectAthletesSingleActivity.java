package heyong.intellectPinPang.ui.competition.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.SelectAthletesAndCoachesAdapter;
import heyong.intellectPinPang.adapter.game.SelectLeaderAdapter;
import heyong.intellectPinPang.bean.competition.ChooseJoinMatchUserBean;
import heyong.intellectPinPang.bean.competition.SelectPeopleProjectBean;
import heyong.intellectPinPang.bean.competition.SelectPeopleProjectItemBean;
import heyong.intellectPinPang.databinding.ActivitySelectAthletesAndCoachesBinding;
import heyong.intellectPinPang.ui.competition.CompetitionViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MapRemoveNullUtil;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;

/**
 * 单打  需要判断组别 的要求
 */
public class SelectAthletesSingleActivity extends BaseActivity<ActivitySelectAthletesAndCoachesBinding, CompetitionViewModel> implements View.OnClickListener {
    public static final String OWN_ER_TYPE = "owner_type";//区分是选一个 还是选两个
    public static final String OWN_IN_TYPE = "own_in_type";//判断身份
    public static final String OWN_POSITION = "own_position";//选中坐标记录
    public static final String ITEM_TITLE = "item_title";
    public static final String CLICK_POJECT_ID = "click_project_id";

    private String clickProjectId = "";


    private String itemTitle = "";
    private int ownPosition = 0;
    private int type = 0;//0 是选择领队 只能选择1个  1 是选择教练员 可以选择多个
    private int ownerType = 0;
    //    1 领队  来返回数据  2 教练员   3  混合单打  4 专业双打   5 业余双打
    MyDividerItemDecoration mSixDiD;
    SelectLeaderAdapter selectLeaderAdapter;
    SelectAthletesAndCoachesAdapter selectAthletesAndCoachesAdapter;
    public static final String TAG = SelectAthletesSingleActivity.class.getSimpleName();
    List<ChooseJoinMatchUserBean.PlayersBean> allcoachPlayers;


    public static final String MATCH_ID = "match_id";
    private String strMatchId = "";
    public static final String CLUB_ID = "club_id";
    private String strClubId = "";
    public static final String PROJECT_ID = "project_id";
    private String strProjectId = "";
    private List<String> userIdList = new ArrayList<>();
    List<ChooseJoinMatchUserBean.PlayersBean> selectList = new ArrayList<>();

    List<String> projectList = new ArrayList<>();
    List<SelectPeopleProjectBean> selectPeopleProjectBeans;

    //领队的适配器SelectLeaderAdapter        SelectAthletesAndCoachesAdapter
    @Override
    public int getLayoutRes() {
        return R.layout.activity_select_athletes_and_coaches;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        type = getIntent().getIntExtra(OWN_ER_TYPE, 0);
        ownerType = getIntent().getIntExtra(OWN_IN_TYPE, 0);
        ownPosition = getIntent().getIntExtra(OWN_POSITION, 0);
        strMatchId = getIntent().getStringExtra(MATCH_ID);
        strClubId = getIntent().getStringExtra(CLUB_ID);
        strProjectId = getIntent().getStringExtra(PROJECT_ID);
        itemTitle = getIntent().getStringExtra(ITEM_TITLE);
        mBinding.tvLeftTitle.setText("" + itemTitle);

        allcoachPlayers = (List<ChooseJoinMatchUserBean.PlayersBean>) getIntent().getSerializableExtra("data");
        projectList = (List<String>) getIntent().getSerializableExtra("projectList");
        selectPeopleProjectBeans = (List<SelectPeopleProjectBean>) getIntent().getSerializableExtra("selectPeopleProjectBeans");

        clickProjectId = getIntent().getStringExtra(CLICK_POJECT_ID);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SelectAthletesSingleActivity.this, 5);
        mBinding.rvCoach.removeItemDecoration(mSixDiD);
        mSixDiD = new MyDividerItemDecoration(SelectAthletesSingleActivity.this, 10,
                getResources().getColor(R.color.white));
        mBinding.rvCoach.addItemDecoration(mSixDiD);
        //绑定manager
        mBinding.rvCoach.setLayoutManager(gridLayoutManager);


        int mmmm = 0;
        for (int i = 0; i < allcoachPlayers.size(); i++) {
            if (allcoachPlayers.get(i).isSelect()) {
                mmmm++;
            }
        }
        mBinding.tvAlreadySelectNum.setText("" + mmmm);


//        Map<String, List<SelectPeopleProjectBean>> collect = selectPeopleProjectBeans.stream().collect(Collectors.groupingBy
//                (SelectPeopleProjectBean::getItemProjectId));
//        Log.e(TAG, "initView: size1===" + collect.size());
        Map<String, List<SelectPeopleProjectBean>> collect = new HashMap<>();
        for(SelectPeopleProjectBean selectPeopleProjectBean : selectPeopleProjectBeans) {
            String key = selectPeopleProjectBean.getItemProjectId();
            if(collect.containsKey(key)) {
                //map中存在以此id作为的key，将数据存放当前key的map中
                collect.get(key).add(selectPeopleProjectBean);
            } else {
                //map中不存在以此id作为的key，新建key用来存放数据
                List<SelectPeopleProjectBean> userList = new ArrayList<>();
                userList.add(selectPeopleProjectBean);
                collect.put(key, userList);
            }
        }
        MapRemoveNullUtil.removeNullKey(collect);


        List<SelectPeopleProjectBean> removeAfterList = collect.remove(clickProjectId);
        Log.e(TAG, "initView: size2====" + collect.size());
        List<Long> userIdsListRemove = new ArrayList<>();
        if (collect != null && collect.size() > 0) {


            for (Map.Entry<String, List<SelectPeopleProjectBean>> entry : collect.entrySet()) {

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



        List<ChooseJoinMatchUserBean.PlayersBean> removeDataList = new ArrayList<>();
        for (int mmm = 0; mmm < allcoachPlayers.size(); mmm++) {
            ChooseJoinMatchUserBean.PlayersBean playersBean = allcoachPlayers.get(mmm);
                for (int pp = 0; pp < userIdsListRemove.size(); pp++) {
                    if (playersBean.getUserId()==userIdsListRemove.get(pp)) {
                        Log.e(TAG, "initView: remove 移除" +playersBean.getUserId());
                        removeDataList.add(allcoachPlayers.get(mmm));
                    }
                }
            Log.e(TAG, "initView: remove 移除  removeDataList===" + new Gson().toJson(removeDataList));
        }
        allcoachPlayers.removeAll(removeDataList);
        Log.e(TAG, "initView:size===2=== " + new Gson().toJson(allcoachPlayers));





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
        /*设置适配器*/
        if (type == 0) {


            selectLeaderAdapter = new SelectLeaderAdapter(SelectAthletesSingleActivity.this, wdith);
            mBinding.rvCoach.setAdapter(selectLeaderAdapter);
            selectLeaderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                        List<ChooseJoinMatchUserBean.PlayersBean> data = selectLeaderAdapter.getData();
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setSelect(false);
                        }
                        data.get(position).setSelect(true);
                        selectLeaderAdapter.notifyDataSetChanged();
                        mBinding.tvAlreadySelectNum.setText("" + 1);

                }
            });
            Log.e(TAG, "initView: data size===" + allcoachPlayers.size());





            selectLeaderAdapter.setNewData(allcoachPlayers);
            Log.e(TAG, "initView: " + selectLeaderAdapter.getData().size());

        } else {
            selectAthletesAndCoachesAdapter = new SelectAthletesAndCoachesAdapter(SelectAthletesSingleActivity.this, wdith);
            mBinding.rvCoach.setAdapter(selectAthletesAndCoachesAdapter);
            selectAthletesAndCoachesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                    boolean fastClick = isFastClick();
//                    if (!fastClick) {
                        ChooseJoinMatchUserBean.PlayersBean selectAthletesAndCoachesBean = selectAthletesAndCoachesAdapter.getData().get(position);
                        selectAthletesAndCoachesBean.setSelect(!selectAthletesAndCoachesBean.isSelect());
                        selectAthletesAndCoachesAdapter.setData(position, selectAthletesAndCoachesBean);
                        List<ChooseJoinMatchUserBean.PlayersBean> data = selectAthletesAndCoachesAdapter.getData();
                        int m = 0;
                        for (int i = 0; i < data.size(); i++) {
                            if (data.get(i).isSelect()) {
                                m++;
                            }
                        }
                        mBinding.tvAlreadySelectNum.setText("" + m);
//                    }
                }
            });
            selectAthletesAndCoachesAdapter.setNewData(allcoachPlayers);
        }

        mViewModel.judgeUserShiFouChongFuLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {

                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right://确定
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                selectList.clear();
                /*设置回调*/
                List<ChooseJoinMatchUserBean.PlayersBean> data = selectAthletesAndCoachesAdapter.getData();
                boolean isSelect = false;
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).isSelect()) {
                        isSelect = true;
                        selectList.add(data.get(i));
                    }
                }
                if (isSelect) {
                    Log.e(TAG, "onClick: selectList===" + new Gson().toJson(selectList));
                    userIdList.clear();
                    for (int i = 0; i < selectList.size(); i++) {
                        userIdList.add("" + selectList.get(i).getUserId());
                    }
                    /*多选*/
//                    mViewModel.judgeUserShiFouChongFu("" + strMatchId,
//                            "" + strClubId, "" + strProjectId, userIdList, projectList);
                    if (ownerType == 2) {
                        /*教练员*/
                        Intent intent = new Intent();
                        intent.putExtra("listbean", (Serializable) selectList);
                        intent.putExtra("ownPosition", ownPosition);

                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        /*混合单打*/
                        Intent intent = new Intent();
                        intent.putExtra("listbean", (Serializable) selectList);
                        intent.putExtra("ownPosition", ownPosition);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } else {
                    finish();
                }


                break;
            case R.id.iv_finish:

                finish();
                break;
        }
    }
}