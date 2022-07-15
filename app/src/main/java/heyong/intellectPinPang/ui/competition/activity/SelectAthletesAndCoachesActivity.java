package heyong.intellectPinPang.ui.competition.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.SelectAthletesAndCoachesAdapter;
import heyong.intellectPinPang.adapter.game.SelectLeaderAdapter;
import heyong.intellectPinPang.bean.competition.ChooseJoinMatchUserBean;
import heyong.intellectPinPang.databinding.ActivitySelectAthletesAndCoachesBinding;
import heyong.intellectPinPang.ui.competition.CompetitionViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;

/**
 * 选择参赛人员   领队和教练员可以共用这个界面
 */
public class SelectAthletesAndCoachesActivity extends BaseActivity<ActivitySelectAthletesAndCoachesBinding, CompetitionViewModel> implements View.OnClickListener {
    public static final String OWN_ER_TYPE = "owner_type";//区分是选一个 还是选两个
    public static final String OWN_IN_TYPE = "own_in_type";//判断身份
    public static final String OWN_POSITION = "own_position";//选中坐标记录
    private int ownPosition = 0;
    private int type = 0;//0 是选择领队 只能选择1个  1 是选择教练员 可以选择多个
    private int ownerType = 0;
    //    1 领队  来返回数据  2 教练员   3  混合单打  4 专业双打   5 业余双打
    MyDividerItemDecoration mSixDiD;
    SelectLeaderAdapter selectLeaderAdapter;
    SelectAthletesAndCoachesAdapter selectAthletesAndCoachesAdapter;
    public static final String TAG = SelectAthletesAndCoachesActivity.class.getSimpleName();
    List<ChooseJoinMatchUserBean.PlayersBean> allcoachPlayers;

    public static final String MATCH_ID = "match_id";
    private String strMatchId = "";
    public static final String CLUB_ID = "club_id";
    private String strClubId = "";
    public static final String PROJECT_ID = "project_id";
    private String strProjectId = "";
    private List<String> userIdList = new ArrayList<>();

    List<ChooseJoinMatchUserBean.PlayersBean> myPlayList = new ArrayList<>();
    List<ChooseJoinMatchUserBean.PlayersBean> selectList = new ArrayList<>();

    //领队的适配器SelectLeaderAdapter        SelectAthletesAndCoachesAdapter
    @Override
    public int getLayoutRes() {
        return R.layout.activity_select_athletes_and_coaches;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        type = getIntent().getIntExtra(OWN_ER_TYPE, 0);
        ownerType = getIntent().getIntExtra(OWN_IN_TYPE, 0);
        ownPosition = getIntent().getIntExtra(OWN_POSITION, 0);

        strMatchId = getIntent().getStringExtra(MATCH_ID);
        strClubId = getIntent().getStringExtra(CLUB_ID);
        strProjectId = getIntent().getStringExtra(PROJECT_ID);


        allcoachPlayers = (List<ChooseJoinMatchUserBean.PlayersBean>) getIntent().getSerializableExtra("data");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SelectAthletesAndCoachesActivity.this, 5);
        mBinding.rvCoach.removeItemDecoration(mSixDiD);
        mSixDiD = new MyDividerItemDecoration(SelectAthletesAndCoachesActivity.this, 10,
                getResources().getColor(R.color.white));
        mBinding.rvCoach.addItemDecoration(mSixDiD);
        //绑定manager
        mBinding.rvCoach.setLayoutManager(gridLayoutManager);

//        if (allcoachPlayers != null && allcoachPlayers.size() > 0) {
//            for (int i = 0; i < allcoachPlayers.size(); i++) {
//                allcoachPlayers.get(i).setSelect(false);
//            }
//        }
        /*设置适配器*/
        if (type == 0) {


            selectLeaderAdapter = new SelectLeaderAdapter(SelectAthletesAndCoachesActivity.this, wdith);
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
            int m = 0;
            for(int mmm=0;mmm<allcoachPlayers.size();mmm++)
            {
                if (allcoachPlayers.get(mmm).isSelect()) {
                    m++;
                }
                mBinding.tvAlreadySelectNum.setText("" + m);
            }

            Log.e(TAG, "initView: " + selectLeaderAdapter.getData().size());
        } else {
            selectAthletesAndCoachesAdapter = new SelectAthletesAndCoachesAdapter(SelectAthletesAndCoachesActivity.this, wdith);
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
            int m = 0;
            for(int mmm=0;mmm<allcoachPlayers.size();mmm++)
            {
                if (allcoachPlayers.get(mmm).isSelect()) {
                    m++;
                }
                mBinding.tvAlreadySelectNum.setText("" + m);
            }
        }

        mViewModel.judgeUserShiFouChongFuLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    if (type == 0) {
                        Intent intent = new Intent();
                        intent.putExtra("listbean", (Serializable) myPlayList);
                        intent.putExtra("ownPosition", ownPosition);
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
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
                    }
                } else {
                    toast(""+responseBean.getMessage());
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
                /*设置回调*/
                if (type == 0) {
                    myPlayList.clear();
                    /*单选*/
                    List<ChooseJoinMatchUserBean.PlayersBean> data = selectLeaderAdapter.getData();
                    ChooseJoinMatchUserBean.PlayersBean playersBean = null;
                    boolean isSelect = false;
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).isSelect()) {
                            /*选中了*/
                            isSelect = true;
                            playersBean = data.get(i);
                        }
                    }
                    if (isSelect) {
                        myPlayList.add(playersBean);
//                        if (myPlayList != null && myPlayList.size() > 0) {
//                            userIdList.add("" + myPlayList.get(0).getUserId());
//                        }
//                        mViewModel.judgeUserShiFouChongFu("" + strMatchId, "" + strClubId, "" + strProjectId, userIdList);
                        Intent intent = new Intent();
                        intent.putExtra("listbean", (Serializable) myPlayList);
                        intent.putExtra("ownPosition", ownPosition);
                        setResult(RESULT_OK, intent);
                        finish();

                    } else {
                        finish();
                    }
                } else {
                    selectList.clear();
                    userIdList.clear();
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
                        /*多选*/
//                        for (int i = 0; i < selectList.size(); i++) {
//                            userIdList.add("" + selectList.get(i).getUserId());
//                        }
//                        mViewModel.judgeUserShiFouChongFu(""+strMatchId, ""+strClubId, ""+strProjectId, userIdList);
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
                }


                break;
            case R.id.iv_finish:

                finish();
                break;
        }
    }
}