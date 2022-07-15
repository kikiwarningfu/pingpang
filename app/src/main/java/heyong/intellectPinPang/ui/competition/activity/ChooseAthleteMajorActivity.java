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

import com.google.gson.Gson;
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
import heyong.intellectPinPang.bean.competition.SelectPeopleProjectBean;
import heyong.intellectPinPang.bean.competition.SelectPeopleProjectItemBean;
import heyong.intellectPinPang.adapter.game.ChooseAthleteMajorAdapter;
import heyong.intellectPinPang.databinding.ActivityChooseAthleteMajorBinding;
import heyong.intellectPinPang.ui.competition.CompetitionViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MapRemoveNullUtil;
//专业   双打 男子双打  男团选   女子双打 女团选   必须是同一个团体   （左右俩人）  页面重新搞  男团/女团/2
//       混双  一男 一女  从同组别的男团和组团选   （男团 和女团 少的那个人数为准 男的10个人 女的9个人 只能有9组）


//业余  双打  男子双打 女子双打 不区分团体  符合年龄条件和性别条件  所有符合的人数总和/2
//  混合  混合双打  所有符合年龄/2
//   混双    一男一女  但是没有 从团体选  人数最少的性别

/**
 * 选择运动员 专业选择  专业双打    双打 男子 女子   混双
 */
public class ChooseAthleteMajorActivity extends BaseActivity<ActivityChooseAthleteMajorBinding, CompetitionViewModel> implements View.OnClickListener {
    public static final String OWNER_TYPE = "owner_type";
    public static final String MATCH_TITLE = "match_title";
    private int ownerType = 0;
    List<SelectPeopleProjectItemBean> manList;//男子列表数据
    List<SelectPeopleProjectItemBean> womanList;//女子列表数据
    public static final String CLICK_POSITION = "click_position";
    private int clickPosition = 0;
    ChooseAthleteMajorAdapter chooseAthleteMajorAdapter;
    public int waiPositions = 0;
    public int neiPositions = 0;
    public int myOwnerTypes = 0;
    public int leftTypes = 0;
    private String matchTitle = "";
    List<String> menuList = new ArrayList<>();
    List<Long> idsList = new ArrayList<>();
    List<String> sexList = new ArrayList<>();
    public static final String TAG = ChooseAthleteMajorActivity.class.getSimpleName();

    public static final String MATCH_ID = "match_id";
    private String strMatchId = "";
    public static final String CLUB_ID = "club_id";
    private String strClubId = "";
    public static final String PROJECT_ID = "project_id";
    private String strProjectId = "";
    public static final String SEX_TYPE = "sex_type";
    private String strSexType = "";
    private List<Long> userIdList = new ArrayList<>();
    List<String> projectList = new ArrayList<>();

    List<SelectPeopleProjectBean> selectPeopleProjectBeans;
    public static final String CLICK_POJECT_ID = "click_project_id";
    private String clickProjectId = "";

    /*传递sexType*/
    @Override
    public int getLayoutRes() {
        return R.layout.activity_choose_athlete_major;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        matchTitle = getIntent().getStringExtra(MATCH_TITLE);
        mBinding.tvMatchTitle.setText("" + matchTitle);
        strMatchId = getIntent().getStringExtra(MATCH_ID);
        strClubId = getIntent().getStringExtra(CLUB_ID);
        strProjectId = getIntent().getStringExtra(PROJECT_ID);
        strSexType = getIntent().getStringExtra(SEX_TYPE);
        projectList = (List<String>) getIntent().getSerializableExtra("projectList");

        ownerType = getIntent().getIntExtra(OWNER_TYPE, 0);
        clickPosition = getIntent().getIntExtra(CLICK_POSITION, 0);
        manList = (List<SelectPeopleProjectItemBean>) getIntent().getSerializableExtra("manList");
        womanList = (List<SelectPeopleProjectItemBean>) getIntent().getSerializableExtra("womanList");


        selectPeopleProjectBeans = (List<SelectPeopleProjectBean>) getIntent().getSerializableExtra("selectPeopleProjectBeans");
        clickProjectId = getIntent().getStringExtra(CLICK_POJECT_ID);


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

        List<SelectPeopleProjectItemBean.Players> manRemoveList = new ArrayList<>();
        if(manList!=null) {
            for (int mmm = 0; mmm < manList.size(); mmm++) {
                SelectPeopleProjectItemBean selectPeopleProjectItemBean = manList.get(mmm);
                List<SelectPeopleProjectItemBean.Players> playersList = selectPeopleProjectItemBean.getPlayersList();


                for (int nnn = 0; nnn < playersList.size(); nnn++) {
                    SelectPeopleProjectItemBean.Players players = playersList.get(nnn);
                    for (int pp = 0; pp < userIdsListRemove.size(); pp++) {
                        if (players.getLeftUserId() == userIdsListRemove.get(pp) || players.getRightUserId() == userIdsListRemove.get(pp)) {
                            manRemoveList.add(playersList.get(nnn));
                        }
                    }
                }
                Log.e(TAG, "initView: remove 移除===" + new Gson().toJson(manRemoveList));
                manList.get(mmm).getPlayersList().removeAll(manRemoveList);
            }
        }


        List<SelectPeopleProjectItemBean.Players> womanRemoveList = new ArrayList<>();
        if(womanList!=null) {
            for (int mmm = 0; mmm < womanList.size(); mmm++) {
                SelectPeopleProjectItemBean selectPeopleProjectItemBean = womanList.get(mmm);
                List<SelectPeopleProjectItemBean.Players> playersList = selectPeopleProjectItemBean.getPlayersList();


                for (int nnn = 0; nnn < playersList.size(); nnn++) {
                    SelectPeopleProjectItemBean.Players players = playersList.get(nnn);
                    for (int pp = 0; pp < userIdsListRemove.size(); pp++) {
                        if (players.getLeftUserId() == userIdsListRemove.get(pp) || players.getRightUserId() == userIdsListRemove.get(pp)) {
                            womanRemoveList.add(playersList.get(nnn));
                        }
                    }
                }
                Log.e(TAG, "initView: remove 移除===" + new Gson().toJson(womanRemoveList));
                womanList.get(mmm).getPlayersList().removeAll(womanRemoveList);
            }
        }


        chooseAthleteMajorAdapter = new ChooseAthleteMajorAdapter(ChooseAthleteMajorActivity.this);
        chooseAthleteMajorAdapter.setListener(new ChooseAthleteMajorAdapter.onMyListener() {
            @Override
            public void OnMyListener(int myOwnerType, List<SelectPeopleProjectItemBean.Players> manPlayers,
                                     int leftType, int waiPosition, int neiPosition) {
                waiPositions = waiPosition;
                neiPositions = neiPosition;
                myOwnerTypes = myOwnerType;
                menuList.clear();
                sexList.clear();
                idsList.clear();
                leftTypes = leftType;
                for (int i = 0; i < manPlayers.size(); i++) {
                    menuList.add(manPlayers.get(i).getUserName());
                    idsList.add(manPlayers.get(i).getUserId());
                    sexList.add(manPlayers.get(i).getSex());
                }
                BottomMenu.show(ChooseAthleteMajorActivity.this, menuList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
//专业   双打 男子双打  男团选   女子双打 女团选   必须是同一个团体   （左右俩人）  页面重新搞  男团/女团/2
//       混双  一男 一女  从同组别的男团和组团选   （男团 和女团 少的那个人数为准 男的10个人 女的9个人 只能有9组）
                        List<SelectPeopleProjectItemBean> data = chooseAthleteMajorAdapter.getData();

                        List<SelectPeopleProjectItemBean.Players> playersList = data.get(waiPosition).getPlayersList();
                        SelectPeopleProjectItemBean.Players players = playersList.get(neiPosition);


                        long selectIds = idsList.get(index);
                        boolean isContainers = false;
                        for (int i = 0; i < playersList.size(); i++) {
                            long userLeftId = players.getLeftUserId();
                            if (selectIds==userLeftId) {
                                isContainers = true;
                            }

                            long userRightId = players.getRightUserId();
                            if (selectIds==userRightId) {
                                isContainers = true;
                            }
                        }
                        if (isContainers) {
                            toast("不能选择重复的人");
                            return;
                        } else {

                            long selectUserId = idsList.get(index);
                            List<SelectPeopleProjectItemBean.Players> newList = new ArrayList<>();
                            for (int i = 0; i < playersList.size(); i++) {
                                if (!TextUtils.isEmpty(playersList.get(i).getLeftOneName()) || !TextUtils.isEmpty(playersList.get(i).getRightOneName())) {
                                    newList.add(playersList.get(i));
                                }
                            }
                            boolean isHaveRepetition = false;
                            for (int m = 0; m < newList.size(); m++) {
                                if (selectUserId==newList.get(m).getLeftUserId()) {
                                    isHaveRepetition = true;
                                }
                                if (selectUserId==newList.get(m).getRightUserId()) {
                                    isHaveRepetition = true;
                                }

                            }
                            if (isHaveRepetition) {
                                toast("不能选择重复的人");
                                return;
                            } else {
                                if (leftType == 1) {
                                    /*左边的*/
                                    players.setLeftUserId( idsList.get(index));
                                    players.setLeftOneName("" + text);
                                    players.setLeftSex("" + sexList.get(index));
                                    players.setSex("" + sexList.get(index));
                                } else {
                                    /*右边的*/
                                    players.setRightUserId(idsList.get(index));
                                    players.setRightOneName("" + text);
                                    players.setRightSex(sexList.get(index));
                                    players.setSex("" + sexList.get(index));
                                }
                            }
                        }
//                        data.get(waiPosition).setPlayersList(playersList);
                        chooseAthleteMajorAdapter.setNewData(data);
                        chooseAthleteMajorAdapter.notifyDataSetChanged();
                    }
                });


            }


        });
        if (ownerType == 1) {
            /*男子双打*/
            if (manList != null && manList.size() > 0) {
                mBinding.rvShuangda.setAdapter(chooseAthleteMajorAdapter);
                mBinding.rvShuangda.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
                /*判断一下 */
                //专业   双打 男子双打  男团选   女子双打 女团选   必须是同一个团体   （左右俩人）  页面重新搞  男团/女团/2
                //      混双  一男 一女  从同组别的男团和组团选   （男团 和女团 少的那个人数为准 男的10个人 女的9个人 只能有9组）
                chooseAthleteMajorAdapter.setNewData(manList);
                chooseAthleteMajorAdapter.setList(manList, new ArrayList<>());

            }
        } else if (ownerType == 2) {
            /*女子双打*/
            if (womanList != null && womanList.size() > 0) {
                mBinding.rvShuangda.setAdapter(chooseAthleteMajorAdapter);
                mBinding.rvShuangda.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
                chooseAthleteMajorAdapter.setNewData(womanList);
                chooseAthleteMajorAdapter.setList(new ArrayList<>(), womanList);

            }

        } else if (ownerType == 3) {
            /*混双*/
            if (manList != null && manList.size() > 0 && womanList != null && womanList.size() > 0) {
                mBinding.rvShuangda.setAdapter(chooseAthleteMajorAdapter);
                mBinding.rvShuangda.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
                chooseAthleteMajorAdapter.setNewData(womanList);
                chooseAthleteMajorAdapter.setList(manList, womanList);
            }
        }
        chooseAthleteMajorAdapter.setOwnerType(ownerType);

        mViewModel.judgeUserShiFouChongFuLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
//                    List<SelectPeopleProjectItemBean> data = chooseAthleteMajorAdapter.getData();
//                    for (int i = 0; i < data.size(); i++) {
//                        SelectPeopleProjectItemBean selectPeopleProjectItemBean = data.get(i);
//                        List<SelectPeopleProjectItemBean.Players> playersList = selectPeopleProjectItemBean.getPlayersList();
//                        for (int j = 0; j < playersList.size(); j++) {
//                            if (playersList.get(j).getLeftUserId()==0 || playersList.get(j).getRightUserId()==0) {
//                                playersList.remove(j);
//                            }
//                        }
//                        selectPeopleProjectItemBean.setPlayersList(playersList);
//                        data.set(i, selectPeopleProjectItemBean);
//                    }
//                    Intent intent = new Intent();
//                    intent.putExtra("bean", (Serializable) data);
//                    intent.putExtra(CLICK_POSITION, clickPosition);
//                    intent.putExtra(SEX_TYPE, strSexType);
//                    setResult(RESULT_OK, intent);
//                    finish();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_finish:

                finish();
                break;
            case R.id.tv_submit:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                userIdList.clear();
                List<SelectPeopleProjectItemBean> data = chooseAthleteMajorAdapter.getData();
                int count = 0;
                for (int i = 0; i < data.size(); i++) {
                    SelectPeopleProjectItemBean selectPeopleProjectItemBean = data.get(i);
                    List<SelectPeopleProjectItemBean.Players> playersList = selectPeopleProjectItemBean.getPlayersList();
                    for (int j = 0; j < playersList.size(); j++) {
                        boolean isRemove = false;
                        if (TextUtils.isEmpty(playersList.get(j).getLeftOneName()) && TextUtils.isEmpty(playersList.get(j).getRightOneName())) {
                            isRemove = true;
                        }
                        if (isRemove) {
                            playersList.remove(j);
                        }
                    }
                    data.get(i).setPlayersList(playersList);

                    for (int n = 0; n < playersList.size(); n++) {
                        if (!TextUtils.isEmpty(playersList.get(n).getLeftOneName()) && !TextUtils.isEmpty(playersList.get(n).getRightOneName())) {
                            count++;
                        }
                    }
                }
                if (count == 0) {
                    toast("请选择完整的双打");
                    return;
                } else {

                    for (int i = 0; i < data.size(); i++) {
                        SelectPeopleProjectItemBean selectPeopleProjectItemBean = data.get(i);
                        List<SelectPeopleProjectItemBean.Players> playersList = selectPeopleProjectItemBean.getPlayersList();
                        for (int j = 0; j < playersList.size(); j++) {
                            if (playersList.get(j).getLeftUserId()!=0) {
                                userIdList.add(playersList.get(j).getLeftUserId());
                            }
                            if (playersList.get(j).getRightUserId()!=0) {
                                userIdList.add(playersList.get(j).getRightUserId());
                            }
                        }
                    }

//                    mViewModel.judgeUserShiFouChongFu("" + strMatchId, "" + strClubId, "" + strProjectId,
//                            userIdList, projectList);
                    List<SelectPeopleProjectItemBean> chooseAthleteMajorAdapterdata = chooseAthleteMajorAdapter.getData();
                    for (int i = 0; i < chooseAthleteMajorAdapterdata.size(); i++) {
                        SelectPeopleProjectItemBean selectPeopleProjectItemBean = chooseAthleteMajorAdapterdata.get(i);
                        List<SelectPeopleProjectItemBean.Players> playersList = selectPeopleProjectItemBean.getPlayersList();
                        for (int j = 0; j < playersList.size(); j++) {
                            if (playersList.get(j).getLeftUserId()==0 || playersList.get(j).getRightUserId()==0) {
                                playersList.remove(j);
                            }
                        }
                        selectPeopleProjectItemBean.setPlayersList(playersList);
                        chooseAthleteMajorAdapterdata.set(i, selectPeopleProjectItemBean);
                    }
                    Intent intent = new Intent();
                    intent.putExtra("bean", (Serializable) chooseAthleteMajorAdapterdata);
                    intent.putExtra(CLICK_POSITION, clickPosition);
                    intent.putExtra(SEX_TYPE, strSexType);
                    setResult(RESULT_OK, intent);
                    finish();
                }


                break;
        }
    }
}