package heyong.intellectPinPang.ui.competition.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.v3.BottomMenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.ChooseNoAthleteMajorAdapter;
import heyong.intellectPinPang.bean.competition.ChooseNoAthleteMajorManBean;
import heyong.intellectPinPang.bean.competition.ChooseJoinMatchUserBean;
import heyong.intellectPinPang.bean.competition.SelectPeopleProjectBean;
import heyong.intellectPinPang.bean.competition.SelectPeopleProjectItemBean;
import heyong.intellectPinPang.databinding.ActivityChooseNoAthleteMajorBinding;
import heyong.intellectPinPang.ui.competition.CompetitionViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MapRemoveNullUtil;

/**
 * 业余 11       intent2.putExtra("selectPeopleProjectBeans", (Serializable) selectPeopleProjectBeans);//10
 * intent2.putExtra(SelectAthletesSingleActivity.CLICK_POJECT_ID, itemProjectId);
 */
public class ChooseNoAthleteMajorActivity extends BaseActivity<ActivityChooseNoAthleteMajorBinding, CompetitionViewModel> implements View.OnClickListener {
    ChooseNoAthleteMajorAdapter chooseNoAthleteMajorAdapter;
    //   业余  双打  男子双打 女子双打 不区分团体  符合年龄条件和性别条件  所有符合的人数总和/2
    //  混合  混合双打  所有符合年龄/2
    //   混双    一男一女  但是没有 从团体选  人数最少的性别
    public static final String TAG = ChooseNoAthleteMajorActivity.class.getSimpleName();
    public static final String USER_SEX_TYPE = "user_sex_type";
    public static final String ITEAM_TITLE = "";
    private String newStrTitle = "";
    private String userSexType = "";
    List<ChooseJoinMatchUserBean.PlayersBean> players = new ArrayList<>();
    List<ChooseJoinMatchUserBean.PlayersBean> newPlayers = new ArrayList<>();
    List<ChooseJoinMatchUserBean.PlayersBean> manPlayers = new ArrayList<>();
    List<ChooseJoinMatchUserBean.PlayersBean> womanPlayers = new ArrayList<>();
    public static final String GASONSTR = "gsonStr";
    public static final String CLICK_POSITION = "clickPosition";
    private int clickPosition = 0;
    List<String> menuManList = new ArrayList<>();
    List<ChooseNoAthleteMajorManBean> chooseNoAthleteMajorManBeanList = new ArrayList<>();
    List<ChooseNoAthleteMajorManBean> chooseNoAthleteMajorManBeanWomanList = new ArrayList<>();

    List<String> menuWomanList = new ArrayList<>();

    public static final String MATCH_ID = "match_id";
    private String strMatchId = "";
    public static final String CLUB_ID = "club_id";
    private String strClubId = "";
    public static final String PROJECT_ID = "project_id";
    private String strProjectId = "";
    private List<Long> userIdList = new ArrayList<>();
    private String strGson;
    List<String> projectList = new ArrayList<>();
    List<ChooseJoinMatchUserBean.PlayersBean> manPlayerAll = new ArrayList<>();
    List<ChooseJoinMatchUserBean.PlayersBean> womanPlayerAll = new ArrayList<>();

    List<ChooseJoinMatchUserBean.PlayersBean> adapterManPlayList = new ArrayList<>();
    List<ChooseJoinMatchUserBean.PlayersBean> adapterWomanPlayList = new ArrayList<>();

    List<SelectPeopleProjectBean> selectPeopleProjectBeans;
    public static final String CLICK_POJECT_ID = "click_project_id";
    private String clickProjectId = "";


    @Override
    public int getLayoutRes() {
        return R.layout.activity_choose_no_athlete_major;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        players = (List<ChooseJoinMatchUserBean.PlayersBean>) getIntent().getSerializableExtra("data");
        strGson = getIntent().getStringExtra(GASONSTR);
        mBinding.ivFinish.setVisibility(View.GONE);
        userSexType = getIntent().getStringExtra(USER_SEX_TYPE);
        clickPosition = getIntent().getIntExtra(CLICK_POSITION, 0);
        newStrTitle = getIntent().getStringExtra(ITEAM_TITLE);
        mBinding.tvSelectTitle.setText("" + newStrTitle);
        strMatchId = getIntent().getStringExtra(MATCH_ID);
        strClubId = getIntent().getStringExtra(CLUB_ID);
        strProjectId = getIntent().getStringExtra(PROJECT_ID);
        projectList = (List<String>) getIntent().getSerializableExtra("projectList");

        List<ChooseJoinMatchUserBean.PlayersBean> totalList = new Gson().fromJson(strGson, new TypeToken<List<ChooseJoinMatchUserBean.PlayersBean>>() {
        }.getType());

        selectPeopleProjectBeans = (List<SelectPeopleProjectBean>) getIntent().getSerializableExtra("selectPeopleProjectBeans");
        clickProjectId = getIntent().getStringExtra(CLICK_POJECT_ID);
//        Map<String, List<SelectPeopleProjectBean>> collect = selectPeopleProjectBeans.stream().collect(Collectors.groupingBy
//                (SelectPeopleProjectBean::getItemProjectId));
//        Log.e(TAG, "initView: size1===" + collect.size());
        Map<String, List<SelectPeopleProjectBean>> collect = new HashMap<>();
        for (SelectPeopleProjectBean selectPeopleProjectBean : selectPeopleProjectBeans) {
            String key = selectPeopleProjectBean.getItemProjectId();
            if (collect.containsKey(key)) {
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
        for (int r = 0; r < userIdsListRemove.size(); r++) {
            Log.e(TAG, "initView: 初始化 ids===" + Long.valueOf(userIdsListRemove.get(r)));
        }

        List<ChooseJoinMatchUserBean.PlayersBean> RemoveList = new ArrayList<>();
//        Log.e(TAG, "initView: players Size====" + new Gson().toJson(players));
        int sizse = 0;
        for (int mmm = 0; mmm < players.size(); mmm++) {
            ChooseJoinMatchUserBean.PlayersBean playersBean = players.get(mmm);
            sizse++;
            Log.e(TAG, "initView: getUserId====" + playersBean.getUserId() + "name=====" + playersBean.getUserName() + " current size=====" + sizse);
            for (int pp = 0; pp < userIdsListRemove.size(); pp++) {
                if (String.valueOf(playersBean.getUserId()).equals(String.valueOf(userIdsListRemove.get(pp)))) {
                    Log.e(TAG, "initView: Activity 添加数据====" + userIdsListRemove.get(pp));
                    RemoveList.add(playersBean);
                }

                if (playersBean.getUserLeftId() != 0) {
                    if (playersBean.getUserLeftId() == userIdsListRemove.get(pp)) {
                        RemoveList.add(playersBean);
                    }
                }
                if (playersBean.getUserRightId() != 0) {
                    if (playersBean.getUserRightId() == userIdsListRemove.get(pp)) {
                        RemoveList.add(playersBean);
                    }
                }
            }

            Log.e(TAG, "initView: remove 移除===" + new Gson().toJson(RemoveList));
        }
        Log.e(TAG, "initView: 移除前" + players.size());
        players.removeAll(RemoveList);
        Log.e(TAG, "initView: 移除后" + players.size());
        totalList.removeAll(RemoveList);

        chooseNoAthleteMajorAdapter = new ChooseNoAthleteMajorAdapter();
        mBinding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvList.setAdapter(chooseNoAthleteMajorAdapter);
        for (int i = 0; i < players.size(); i++) {
            adapterManPlayList.add(new ChooseJoinMatchUserBean.PlayersBean());
            adapterWomanPlayList.add(new ChooseJoinMatchUserBean.PlayersBean());
        }
        int size = players.size();
        int iSize = size / 2;
        for (int i = 0; i < iSize; i++) {
            newPlayers.add(players.get(i));
        }
//        Log.e(TAG, "initView: 初始化数据===" + new Gson().toJson(chooseNoAthleteMajorAdapter.getManPlayers()));
        manPlayerAll.add(0, new ChooseJoinMatchUserBean.PlayersBean(0, "请选择", ""));
        womanPlayerAll.add(0, new ChooseJoinMatchUserBean.PlayersBean(0, "请选择", ""));
        //1 男 2女 3 混合 4混双
        if (userSexType.equals("1") || userSexType.equals("2")) {
            chooseNoAthleteMajorAdapter.setNewData(newPlayers);
            chooseNoAthleteMajorAdapter.setList(adapterManPlayList, adapterWomanPlayList);
            for (int mm = 0; mm < totalList.size(); mm++) {
                manPlayerAll.add(totalList.get(mm));
            }
        } else if (userSexType.equals("3")) {
            //混合双打
            /*可以 两难 可以两女  总人数/2*/
            chooseNoAthleteMajorAdapter.setNewData(newPlayers);
            chooseNoAthleteMajorAdapter.setList(adapterManPlayList, adapterWomanPlayList);
            for (int mm = 0; mm < totalList.size(); mm++) {
                manPlayerAll.add(totalList.get(mm));
            }

        } else if (userSexType.equals("4")) {
            for (int mm = 0; mm < totalList.size(); mm++) {
                if (totalList.get(mm).getSex().equals("1")) {
                    manPlayerAll.add(totalList.get(mm));
                }
                if (totalList.get(mm).getSex().equals("2")) {
                    womanPlayerAll.add(totalList.get(mm));
                }
            }
            /*必须一男一女 */
            for (int i = 0; i < totalList.size(); i++) {
                if (totalList.get(i).getSex().equals("1")) {
                    manPlayers.add(new ChooseJoinMatchUserBean.PlayersBean());
                }
                if (totalList.get(i).getSex().equals("2")) {
                    womanPlayers.add(new ChooseJoinMatchUserBean.PlayersBean());
                }
            }
            if (manPlayers.size() >= womanPlayers.size()) {
                chooseNoAthleteMajorAdapter.setNewData(manPlayers);
            } else {
                chooseNoAthleteMajorAdapter.setNewData(womanPlayers);
            }
            chooseNoAthleteMajorAdapter.setList(adapterManPlayList, adapterWomanPlayList);
        }
//        Log.e(TAG, "initView: datainit===" + new Gson().toJson(chooseNoAthleteMajorAdapter.getData()));
        List<ChooseJoinMatchUserBean.PlayersBean> data = chooseNoAthleteMajorAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            ChooseJoinMatchUserBean.PlayersBean playersBean = data.get(i);

            playersBean.setUserName("");
            playersBean.setUserId(0);
            playersBean.setSex("");
            data.set(i, playersBean);
        }
        chooseNoAthleteMajorAdapter.setNewData(data);


//        manPlayerAll = chooseNoAthleteMajorAdapter.getManPlayers();
//        womanPlayerAll = chooseNoAthleteMajorAdapter.getWomanPlayers();


        mViewModel.judgeUserShiFouChongFuLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
//                    List<ChooseJoinMatchUserBean.PlayersBean> data = chooseNoAthleteMajorAdapter.getData();
//                    for (int i = 0; i < data.size(); i++) {
//                        ChooseJoinMatchUserBean.PlayersBean playersBean = data.get(i);
//                        if (TextUtils.isEmpty(playersBean.getUserLeftName()) || TextUtils.isEmpty(playersBean.getUserRightName())
//                                || playersBean.getUserLeftName().equals("请选择") || playersBean.getUserRightName().equals("请选择")) {
//                            playersBean.setUserLeftId(0);
//                            playersBean.setUserLeftName("");
//                            playersBean.setLeftSex("");
//                            playersBean.setUserRightId(0);
//                            playersBean.setUserRightName("");
//                            playersBean.setRightSex("");
//                            data.set(i, playersBean);
//                        }
//                    }
//                    if (data.size() > 0) {
//
//                        Intent intent = new Intent();
//                        intent.putExtra("bean", (Serializable) data);
//                        intent.putExtra(CLICK_POSITION, clickPosition);
//                        setResult(RESULT_OK, intent);
//                        finish();
//                    } else {
//                        toast("请选择赛事人员");
//                    }
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        chooseNoAthleteMajorAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                menuManList.clear();
                chooseNoAthleteMajorManBeanList.clear();
                chooseNoAthleteMajorManBeanWomanList.clear();
                menuWomanList.clear();
                if (userSexType.equals("1") || userSexType.equals("2") || userSexType.equals("3")) {
                    for (int i = 0; i < manPlayerAll.size(); i++) {
                        if (manPlayerAll.get(i).getUserId() == 0) {
                            chooseNoAthleteMajorManBeanList.add(new ChooseNoAthleteMajorManBean("" + "" + manPlayerAll.get(i).getUserName(),
                                    0, "" + manPlayerAll.get(i).getSex()));
                        } else {
                            chooseNoAthleteMajorManBeanList.add(new ChooseNoAthleteMajorManBean("" + "" + manPlayerAll.get(i).getUserName(),
                                    manPlayerAll.get(i).getUserId(), "" + manPlayerAll.get(i).getSex()));
                        }
                        menuManList.add("" + manPlayerAll.get(i).getUserName());
                    }
                    switch (view.getId()) {
                        case R.id.ll_left_one://第一双打的左边

                            BottomMenu.show(ChooseNoAthleteMajorActivity.this, menuManList, new OnMenuItemClickListener() {
                                @Override
                                public void onClick(String text, int index) {
                                    List<ChooseJoinMatchUserBean.PlayersBean> data1 = chooseNoAthleteMajorAdapter.getData();
                                    ChooseJoinMatchUserBean.PlayersBean playersBean = data1.get(position);
                                    String userName = chooseNoAthleteMajorManBeanList.get(index).getUserName();
                                    if (userName.equals("请选择")) {
                                        Log.e(TAG, "onClick: playBeanName===" + playersBean.getUserLeftName());

                                        if (!TextUtils.isEmpty(playersBean.getUserLeftName())) {
                                            menuManList.add(0, "请选择");
                                            manPlayerAll.add(1, new ChooseJoinMatchUserBean.PlayersBean(playersBean.getUserId(),
                                                    "" + playersBean.getUserLeftName(), "" + playersBean.getLeftSex()));
                                            Log.e(TAG, "onClick:position=== " + position);
                                            playersBean.setUserLeftId(0);
                                            playersBean.setUserLeftName("请选择");
                                            playersBean.setLeftSex("" + chooseNoAthleteMajorManBeanList.get(index).getUserSex());
                                            chooseNoAthleteMajorAdapter.setNewData(data1);

                                        } else {
                                            playersBean.setUserLeftId(0);
                                            playersBean.setUserLeftName("请选择");
                                            playersBean.setLeftSex("" + chooseNoAthleteMajorManBeanList.get(index).getUserSex());
                                            chooseNoAthleteMajorAdapter.setNewData(data1);
                                        }
                                    } else {

                                        if (playersBean.getUserName().equals(text)) {
                                            playersBean.setUserLeftId(chooseNoAthleteMajorManBeanList.get(index).getUsetId());
                                            playersBean.setUserLeftName("" + chooseNoAthleteMajorManBeanList.get(index).getUserName());
                                            playersBean.setLeftSex("" + chooseNoAthleteMajorManBeanList.get(index).getUserSex());
                                            chooseNoAthleteMajorAdapter.setNewData(data1);
                                            manPlayerAll.remove(index);
                                            menuManList.remove(index);
                                        } else {
                                            playersBean.setUserLeftId(chooseNoAthleteMajorManBeanList.get(index).getUsetId());
                                            playersBean.setUserLeftName("" + chooseNoAthleteMajorManBeanList.get(index).getUserName());
                                            playersBean.setLeftSex("" + chooseNoAthleteMajorManBeanList.get(index).getUserSex());
                                            chooseNoAthleteMajorAdapter.setNewData(data1);
//                                                menuManList.add(2 * (position + 1) - 1, "" + playersBean.getUserLeftName());
//                                                manPlayerAll.add(2 * (position + 1) - 1, new ChooseJoinMatchUserBean.PlayersBean(playersBean.getUserId(),
//                                                        "" + playersBean.getUserLeftName(), "" + playersBean.getLeftSex()));
                                            manPlayerAll.remove(index);
                                            menuManList.remove(index);
                                        }

                                    }
                                }
                            });


                            break;
                        case R.id.ll_right_one:
                            //男  女  混合

                            Log.e(TAG, "onItemChildClick: " + menuManList.size());
                            BottomMenu.show(ChooseNoAthleteMajorActivity.this, menuManList, new OnMenuItemClickListener() {
                                @Override
                                public void onClick(String text, int index) {
                                    List<ChooseJoinMatchUserBean.PlayersBean> data1 = chooseNoAthleteMajorAdapter.getData();
                                    ChooseJoinMatchUserBean.PlayersBean playersBean = data1.get(position);
                                    String userName = chooseNoAthleteMajorManBeanList.get(index).getUserName();
                                    if (userName.equals("请选择")) {
                                        Log.e(TAG, "onClick: playBeanName===" + playersBean.getUserRightName());
                                        if (!TextUtils.isEmpty(playersBean.getUserRightName())) {
                                            menuManList.add(0, "请选择");
                                            manPlayerAll.add(1, new ChooseJoinMatchUserBean.PlayersBean(playersBean.getUserId(),
                                                    "" + playersBean.getUserRightName(), "" + playersBean.getRightSex()));
                                            Log.e(TAG, "onClick:position=== " + position);
                                            playersBean.setUserRightId(0);
                                            playersBean.setUserRightName("请选择");
                                            playersBean.setRightSex("" + chooseNoAthleteMajorManBeanList.get(index).getUserSex());
                                            chooseNoAthleteMajorAdapter.setNewData(data1);
                                        } else {
                                            playersBean.setUserRightId(0);
                                            playersBean.setUserRightName("请选择");
                                            playersBean.setRightSex("" + chooseNoAthleteMajorManBeanList.get(index).getUserSex());
                                            chooseNoAthleteMajorAdapter.setNewData(data1);
                                        }
                                    } else {
                                        if (playersBean.getUserName().equals(text)) {
                                            playersBean.setUserRightId(chooseNoAthleteMajorManBeanList.get(index).getUsetId());
                                            playersBean.setUserRightName("" + chooseNoAthleteMajorManBeanList.get(index).getUserName());
                                            playersBean.setRightSex("" + chooseNoAthleteMajorManBeanList.get(index).getUserSex());
                                            chooseNoAthleteMajorAdapter.setNewData(data1);
                                            manPlayerAll.remove(index);
                                            menuManList.remove(index);
                                        } else {
                                            playersBean.setUserRightId(chooseNoAthleteMajorManBeanList.get(index).getUsetId());
                                            playersBean.setUserRightName("" + chooseNoAthleteMajorManBeanList.get(index).getUserName());
                                            playersBean.setRightSex("" + chooseNoAthleteMajorManBeanList.get(index).getUserSex());
                                            chooseNoAthleteMajorAdapter.setNewData(data1);
                                            manPlayerAll.remove(index);
                                            menuManList.remove(index);
                                        }
                                    }
                                }
                            });
                            break;
                    }


                } else {
                    //遍历人

                    for (int i = 0; i < manPlayerAll.size(); i++) {
                        if (manPlayerAll.get(i).getUserId() == 0) {
                            chooseNoAthleteMajorManBeanList.add(new ChooseNoAthleteMajorManBean("" + "" + manPlayerAll.get(i).getUserName(),
                                    0, "" + manPlayerAll.get(i).getSex()));
                        } else {
                            chooseNoAthleteMajorManBeanList.add(new ChooseNoAthleteMajorManBean("" + "" + manPlayerAll.get(i).getUserName(),
                                    manPlayerAll.get(i).getUserId(), "" + manPlayerAll.get(i).getSex()));
                        }
                        menuManList.add("" + manPlayerAll.get(i).getUserName());
                    }


                    for (int i = 0; i < womanPlayerAll.size(); i++) {
                        if (womanPlayerAll.get(i).getUserId() == 0) {
                            chooseNoAthleteMajorManBeanWomanList.add(new ChooseNoAthleteMajorManBean("" + "" + womanPlayerAll.get(i).getUserName(),
                                    0, "" + womanPlayerAll.get(i).getSex()));
                        } else {
                            chooseNoAthleteMajorManBeanWomanList.add(new ChooseNoAthleteMajorManBean("" + "" + womanPlayerAll.get(i).getUserName(),
                                    womanPlayerAll.get(i).getUserId(), "" + womanPlayerAll.get(i).getSex()));
                        }
                        menuWomanList.add("" + womanPlayerAll.get(i).getUserName());
                    }


                    switch (view.getId()) {
                        case R.id.ll_left_one://第一双打的左边
                            BottomMenu.show(ChooseNoAthleteMajorActivity.this, menuManList, new OnMenuItemClickListener() {
                                @Override
                                public void onClick(String text, int index) {
                                    List<ChooseJoinMatchUserBean.PlayersBean> data1 = chooseNoAthleteMajorAdapter.getData();
                                    ChooseJoinMatchUserBean.PlayersBean playersBean = data1.get(position);
                                    String userName = chooseNoAthleteMajorManBeanList.get(index).getUserName();
                                    if (userName.equals("请选择")) {
                                        Log.e(TAG, "onClick: playBeanName===" + playersBean.getUserLeftName());

                                        if (!TextUtils.isEmpty(playersBean.getUserLeftName())) {
                                            menuManList.add(0, "请选择");
                                            manPlayerAll.add(1, new ChooseJoinMatchUserBean.PlayersBean(playersBean.getUserId(),
                                                    "" + playersBean.getUserLeftName(), "" + playersBean.getLeftSex()));
                                            Log.e(TAG, "onClick:position=== " + position);
                                            playersBean.setUserLeftId(0);
                                            playersBean.setUserLeftName("请选择");
                                            playersBean.setLeftSex("" + chooseNoAthleteMajorManBeanList.get(index).getUserSex());
                                            chooseNoAthleteMajorAdapter.setNewData(data1);

                                        } else {
                                            playersBean.setUserLeftId(0);
                                            playersBean.setUserLeftName("请选择");
                                            playersBean.setLeftSex("" + chooseNoAthleteMajorManBeanList.get(index).getUserSex());
                                            chooseNoAthleteMajorAdapter.setNewData(data1);
                                        }
                                    } else {

                                        if (playersBean.getUserName().equals(text)) {
                                            playersBean.setUserLeftId(chooseNoAthleteMajorManBeanList.get(index).getUsetId());
                                            playersBean.setUserLeftName("" + chooseNoAthleteMajorManBeanList.get(index).getUserName());
                                            playersBean.setLeftSex("" + chooseNoAthleteMajorManBeanList.get(index).getUserSex());
                                            chooseNoAthleteMajorAdapter.setNewData(data1);
                                            manPlayerAll.remove(index);
                                            menuManList.remove(index);
                                        } else {
                                            playersBean.setUserLeftId(chooseNoAthleteMajorManBeanList.get(index).getUsetId());
                                            playersBean.setUserLeftName("" + chooseNoAthleteMajorManBeanList.get(index).getUserName());
                                            playersBean.setLeftSex("" + chooseNoAthleteMajorManBeanList.get(index).getUserSex());
                                            chooseNoAthleteMajorAdapter.setNewData(data1);
//                                                menuManList.add(2 * (position + 1) - 1, "" + playersBean.getUserLeftName());
//                                                manPlayerAll.add(2 * (position + 1) - 1, new ChooseJoinMatchUserBean.PlayersBean(playersBean.getUserId(),
//                                                        "" + playersBean.getUserLeftName(), "" + playersBean.getLeftSex()));
                                            manPlayerAll.remove(index);
                                            menuManList.remove(index);
                                        }

                                    }
                                }
                            });


                            break;
                        case R.id.ll_right_one:
                            //男  女  混合
                            Log.e(TAG, "onItemChildClick: " + menuWomanList.size());
                            BottomMenu.show(ChooseNoAthleteMajorActivity.this, menuWomanList, new OnMenuItemClickListener() {
                                @Override
                                public void onClick(String text, int index) {
                                    List<ChooseJoinMatchUserBean.PlayersBean> data1 = chooseNoAthleteMajorAdapter.getData();
                                    ChooseJoinMatchUserBean.PlayersBean playersBean = data1.get(position);
                                    String userName = chooseNoAthleteMajorManBeanWomanList.get(index).getUserName();
                                    if (userName.equals("请选择")) {
                                        Log.e(TAG, "onClick: playBeanName===" + playersBean.getUserRightName());
                                        if (!TextUtils.isEmpty(playersBean.getUserRightName())) {
                                            menuWomanList.add(0, "请选择");
                                            womanPlayerAll.add(1, new ChooseJoinMatchUserBean.PlayersBean(playersBean.getUserId(),
                                                    "" + playersBean.getUserRightName(), "" + playersBean.getRightSex()));
                                            Log.e(TAG, "onClick:position=== " + position);
                                            playersBean.setUserRightId(0);
                                            playersBean.setUserRightName("请选择");
                                            playersBean.setRightSex("" + chooseNoAthleteMajorManBeanWomanList.get(index).getUserSex());
                                            chooseNoAthleteMajorAdapter.setNewData(data1);
                                        } else {
                                            playersBean.setUserRightId(0);
                                            playersBean.setUserRightName("请选择");
                                            playersBean.setRightSex("" + chooseNoAthleteMajorManBeanWomanList.get(index).getUserSex());
                                            chooseNoAthleteMajorAdapter.setNewData(data1);
                                        }
                                    } else {
                                        if (playersBean.getUserName().equals(text)) {
                                            playersBean.setUserRightId(chooseNoAthleteMajorManBeanWomanList.get(index).getUsetId());
                                            playersBean.setUserRightName("" + chooseNoAthleteMajorManBeanWomanList.get(index).getUserName());
                                            playersBean.setRightSex("" + chooseNoAthleteMajorManBeanWomanList.get(index).getUserSex());
                                            chooseNoAthleteMajorAdapter.setNewData(data1);
                                            womanPlayerAll.remove(index);
                                            menuWomanList.remove(index);
                                        } else {
                                            playersBean.setUserRightId(chooseNoAthleteMajorManBeanWomanList.get(index).getUsetId());
                                            playersBean.setUserRightName("" + chooseNoAthleteMajorManBeanWomanList.get(index).getUserName());
                                            playersBean.setRightSex("" + chooseNoAthleteMajorManBeanWomanList.get(index).getUserSex());
                                            chooseNoAthleteMajorAdapter.setNewData(data1);
                                            womanPlayerAll.remove(index);
                                            menuWomanList.remove(index);
                                        }
                                    }
                                }
                            });
                            break;
                    }
                }

            }
        });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_right:

                finish();
                break;
            case R.id.tv_submit:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                userIdList.clear();
                List<ChooseJoinMatchUserBean.PlayersBean> data = chooseNoAthleteMajorAdapter.getData();
//                Intent intent = new Intent();
//                intent.putExtra("bean", (Serializable) data);
//                intent.putExtra(CLICK_POSITION, clickPosition);
//                setResult(RESULT_OK, intent);
//                finish();
                for (int i = 0; i < data.size(); i++) {
                    ChooseJoinMatchUserBean.PlayersBean playersBean = data.get(i);
                    if (!TextUtils.isEmpty(playersBean.getUserLeftName())) {
                        if (playersBean.getUserLeftId() != 0) {
                            userIdList.add(playersBean.getUserLeftId());
                        }
                    }
                    if (!TextUtils.isEmpty(playersBean.getUserRightName())) {
                        if (playersBean.getUserRightId() != 0) {
                            userIdList.add(playersBean.getUserRightId());
                        }
                    }

                }
                List<ChooseJoinMatchUserBean.PlayersBean> chooseNoAthleteMajorAdapterdata = chooseNoAthleteMajorAdapter.getData();
                for (int i = 0; i < chooseNoAthleteMajorAdapterdata.size(); i++) {
                    ChooseJoinMatchUserBean.PlayersBean playersBean = chooseNoAthleteMajorAdapterdata.get(i);
                    if (TextUtils.isEmpty(playersBean.getUserLeftName()) || TextUtils.isEmpty(playersBean.getUserRightName())
                            || playersBean.getUserLeftName().equals("请选择") || playersBean.getUserRightName().equals("请选择")) {
                        playersBean.setUserLeftId(0);
                        playersBean.setUserLeftName("");
                        playersBean.setLeftSex("");
                        playersBean.setUserRightId(0);
                        playersBean.setUserRightName("");
                        playersBean.setRightSex("");
                        chooseNoAthleteMajorAdapterdata.set(i, playersBean);
                    }
                }
                if (chooseNoAthleteMajorAdapterdata.size() > 0) {

                    Intent intent = new Intent();
                    intent.putExtra("bean", (Serializable) chooseNoAthleteMajorAdapterdata);
                    intent.putExtra(CLICK_POSITION, clickPosition);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    toast("请选择赛事人员");
                }
//                mViewModel.judgeUserShiFouChongFu("" + strMatchId, "" + strClubId, "" + strProjectId, userIdList, projectList);
                break;
        }
    }
}