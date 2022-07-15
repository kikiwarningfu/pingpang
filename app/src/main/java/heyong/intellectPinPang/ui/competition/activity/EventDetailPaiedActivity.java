package heyong.intellectPinPang.ui.competition.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.TeamRegisterSubmitAdapter;
import heyong.intellectPinPang.bean.competition.MatchOrderInfoBean;
import heyong.intellectPinPang.bean.competition.QueryMatchApplyInfoBean;
import heyong.intellectPinPang.databinding.ActivityEventDetailPaied2Binding;
import heyong.intellectPinPang.ui.mine.MineViewModel;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;

/**
 * 报名详情
 */
public class EventDetailPaiedActivity extends BaseActivity<ActivityEventDetailPaied2Binding, MineViewModel> implements View.OnClickListener {
    private WindowManager wm;
    private int width;
    MyDividerItemDecoration mSixDiD;
    MyDividerItemDecoration mMySixDid;

    public static final String TAG = EventDetailPaiedActivity.class.getSimpleName();
    private TeamRegisterSubmitAdapter teamRegisterSubmitAdapter;
    public static final String BAOMING_ID = "baoming_id";
    public static final String MATCH_ID = "match_id";
    private String strMatchId = "";
    private String strSignUpId = "";

    int replaceChargeEat;//是否需要食宿费 1 是需要  2 是不需要
    int replaceCharge;//是否代收报名费  1 是需要  2 是不需要
    int allMoney = 0;
    QueryMatchApplyInfoBean queryMatchApplyInfoBean;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_event_detail_paied2;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        wm = getWindowManager();
        // 重设高度
        width = wm.getDefaultDisplay().getWidth();
        strSignUpId = getIntent().getStringExtra(BAOMING_ID);
        strMatchId = getIntent().getStringExtra(MATCH_ID);

        mViewModel.queryMatchApplyInfo(strMatchId);


        Log.e(TAG, "initView: ids===" + strSignUpId);
        mViewModel.queryMatchApplyInfoLiveData.observe(this, new Observer<ResponseBean<QueryMatchApplyInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<QueryMatchApplyInfoBean> responseBean) {
                if (responseBean.getData() != null) {
                    queryMatchApplyInfoBean = responseBean.getData();
                }
            }
        });
//       订单详情
        mViewModel.matchOrderInfo(strSignUpId);
        mSixDiD = new MyDividerItemDecoration(EventDetailPaiedActivity.this, 10,
                getResources().getColor(R.color.color_f5));
        mMySixDid = new MyDividerItemDecoration(EventDetailPaiedActivity.this, 10,
                getResources().getColor(R.color.white));
        mBinding.rvSubmitApply.removeItemDecoration(mSixDiD);

        mBinding.rvSubmitApply.addItemDecoration(mSixDiD);
        teamRegisterSubmitAdapter = new TeamRegisterSubmitAdapter(
                EventDetailPaiedActivity.this,
                width, mMySixDid);
        mBinding.rvSubmitApply.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvSubmitApply.setAdapter(teamRegisterSubmitAdapter);
        List<MatchOrderInfoBean> teamRegisterSubmitBeanList = new ArrayList<>();
        mViewModel.matchOrderInfoLiveData.observe(this, new Observer<ResponseBean<MatchOrderInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<MatchOrderInfoBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    MatchOrderInfoBean data = responseBean.getData();
                    List<MatchOrderInfoBean.PlayersBean> players = data.getPlayers();
                    MatchOrderInfoBean.LeaderBean leader = data.getLeader();
                    List<MatchOrderInfoBean.RefereeBean> referee = data.getReferee();
                    mBinding.tvMatchTitle.setText("" + data.getMatchTitle());
                    mBinding.tvStartTime.setText("" + data.getBeginTime());
                    mBinding.tvEndTime.setText("" + data.getEndTime());
                    if (TextUtils.isEmpty(data.getNeedEat())) {
                        replaceChargeEat = 0;//是否需要食宿费  needeat
                    } else {
                        replaceChargeEat = Integer.parseInt(data.getNeedEat());//是否需要食宿费  needeat
                    }
                    if (TextUtils.isEmpty(data.getNeedEnrolFree())) {
                        replaceCharge = 0;//是否代收报名费  1 是需要 0 是不需要
                    } else {
                        replaceCharge = Integer.parseInt(data.getNeedEnrolFree());//是否代收报名费  1 是需要 0 是不需要
                    }


                    if (replaceCharge != 1 && replaceChargeEat != 1) {
                        mBinding.llHavePayWay.setVisibility(View.GONE);
                        mBinding.llNoPayWay.setVisibility(View.VISIBLE);
                    } else {
                        mBinding.llHavePayWay.setVisibility(View.VISIBLE);
                        mBinding.llNoPayWay.setVisibility(View.GONE);
                        Log.e(TAG, "onChanged: replaceChargeEat===" + replaceChargeEat + "    replaceCharge===" + replaceCharge);
                        //需要食宿费
                        if (replaceChargeEat == 1) {
                            mBinding.tvNoShisu.setVisibility(View.GONE);
                            mBinding.rlHaveRoomBoard.setVisibility(View.VISIBLE);
                            mBinding.llPayWay.setVisibility(View.VISIBLE);
                            mBinding.rlHavePay.setVisibility(View.VISIBLE);
                            /*计算一下选中效果*/
                        } else {
                            //不需要食宿费
                            mBinding.tvNoShisu.setVisibility(View.VISIBLE);
                            mBinding.rlHaveRoomBoard.setVisibility(View.GONE);
                            mBinding.llPayWay.setVisibility(View.GONE);
                            /*直接设置钱显示*/
                        }
                        //是否代收报名费
                        if (replaceCharge == 1) {
                            mBinding.rlContainerYesBaoming.setVisibility(View.VISIBLE);
                            mBinding.rlContainerNoBaoming.setVisibility(View.GONE);
                            mBinding.llPayWay.setVisibility(View.VISIBLE);
                            mBinding.rlHavePay.setVisibility(View.VISIBLE);
                            if (replaceChargeEat == 0) {
                                mBinding.llPayWay.setVisibility(View.GONE);
                            }
                        } else {
                            mBinding.rlContainerYesBaoming.setVisibility(View.GONE);
                            mBinding.rlContainerNoBaoming.setVisibility(View.VISIBLE);
                            mBinding.llPayWay.setVisibility(View.GONE);
                        }
                    }

                    allMoney = data.getAllFree();
                    mBinding.tvMoney.setText("¥" + allMoney);
                    mBinding.tvBaomingFei.setText("¥" + data.getEnrollFree() + "/人");
                    mBinding.tvBaomingPeople.setText("共"+data.getPersonCount()+"人");
                    mBinding.tvEatFree.setText("¥" + data.getEatFree() + "/人/天;" + "共" + data.getEatCount() + "天");


                    if (leader != null) {
                        MatchOrderInfoBean teamRegisterSubmitBean = new MatchOrderInfoBean(0);
                        teamRegisterSubmitBean.setLeader(leader);
                        teamRegisterSubmitBeanList.add(teamRegisterSubmitBean);

                    }
                    if (referee != null) {
                        MatchOrderInfoBean teamRegisterSubmitBean1 = new MatchOrderInfoBean(1);
                        teamRegisterSubmitBean1.setReferee(referee);
                        teamRegisterSubmitBean1.setMatchTitle("带队教练员");
                        teamRegisterSubmitBeanList.add(teamRegisterSubmitBean1);
                    }
                    if(leader==null&&referee==null)
                    {
                        mBinding.rvSubmitApply.setVisibility(View.GONE);
                        mBinding.llDandaCompetition.setVisibility(View.VISIBLE);
                        mBinding.tvDandaTitle.setText(""+data.getItemTitle());
                        mBinding.tvUserName.setText(""+data.getName());
                    }
                    MatchOrderInfoBean teamRegisterSubmitBean2 = null;
                    if (players != null && players.size() > 0) {
                        for (int i = 0; i < players.size(); i++) {
                            int cardType = 0;

                            String type = players.get(i).getType();
                            MatchOrderInfoBean.PlayersBean playersBean = players.get(i);
                            List<MatchOrderInfoBean.PlayersBean> playersBeans = new ArrayList<>();
                            switch (type) {
                                case "1"://团体
                                    cardType = 2;
                                    break;
                                case "2"://单打
                                    cardType = 3;
                                    break;
                                case "3"://双打
                                    cardType = 4;
                                    break;
                                case "4"://混双
                                    cardType = 5;
                                    break;
                                case "0":
                                    cardType = 7;
                                    break;
                            }
                            if (cardType == 3) {
                                List<MatchOrderInfoBean.RefereeBean> refereeBeanList = new ArrayList<>();
                                List<MatchOrderInfoBean.PlayersBean.TeamsBean> teams;
                                try {
                                    teams = players.get(i).getTeams();
                                } catch (Exception e) {
                                    teams = players.get(0).getTeams();
                                }
                                List<MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean> player = teams.get(0).getPlayer();
                                Log.e(TAG, "onChanged: userName===" + player.get(0).getUserName());
                                for (int m = 0; m < player.size(); m++) {
                                    MatchOrderInfoBean.RefereeBean refereeBean = new MatchOrderInfoBean.RefereeBean();
                                    refereeBean.setUserName("" + player.get(m).getUserName());
                                    refereeBean.setUserId(player.get(m).getUserId());
                                    refereeBean.setSex("" + player.get(m).getSex());
                                    refereeBeanList.add(refereeBean);
                                }
                                teamRegisterSubmitBean2 = new MatchOrderInfoBean(cardType);
                                teamRegisterSubmitBean2.setMatchTitle("" + players.get(i).getItemTitle());
                                teamRegisterSubmitBean2.setReferee(refereeBeanList);
                            } else {
                                teamRegisterSubmitBean2 = new MatchOrderInfoBean(cardType);
                                teamRegisterSubmitBean2.setMatchTitle("" + players.get(i).getItemTitle());
                                playersBeans.add(playersBean);
                                teamRegisterSubmitBean2.setPlayers(playersBeans);
                            }
                            teamRegisterSubmitBeanList.add(teamRegisterSubmitBean2);
                            Log.e(TAG, "onChanged: " + new Gson().toJson(teamRegisterSubmitBean2));
                        }
                    }
                    for (int i = 0; i < teamRegisterSubmitBeanList.size(); i++) {
                        int cardType1 = teamRegisterSubmitBeanList.get(i).getCardType();
                        if (cardType1 == 7) {
                            teamRegisterSubmitBeanList.remove(i);
                        }
                    }
//                    teamRegisterSubmitBeanList.add(new MatchOrderInfoBean());
//                    teamRegisterSubmitBeanList.add(new MatchOrderInfoBean());
//                    teamRegisterSubmitBeanList.add(new MatchOrderInfoBean());
                    //1 团体 2 单打  3 双打   4 混双（和业余双打一样）
                    teamRegisterSubmitAdapter.setNewData(teamRegisterSubmitBeanList);
                    if (teamRegisterSubmitBeanList != null && teamRegisterSubmitBeanList.size() == 0) {
                        mBinding.viewDivider.setVisibility(View.GONE);
                        mBinding.viewDividerTwo.setVisibility(View.GONE);
                    }
                    Log.e(TAG, "onChanged: teamRegisterSubmitBeanList===" + new Gson().toJson(teamRegisterSubmitBeanList));
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mBinding.setListener(this);
        mBinding.llSubmitApply.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }


}