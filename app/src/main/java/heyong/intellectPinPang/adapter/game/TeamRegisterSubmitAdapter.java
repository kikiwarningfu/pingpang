package heyong.intellectPinPang.adapter.game;

import android.content.Context;
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

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.teamregister.TeamZhuanyeDaAdapter;
import heyong.intellectPinPang.adapter.teamregister.TeanRegisterShuangDaAdapter;
import heyong.intellectPinPang.adapter.teamregister.TeanRegisterShuangDaYeyuAdapter;
import heyong.intellectPinPang.adapter.teamregister.TeanRegisterSingleAdapter;
import heyong.intellectPinPang.adapter.teamregister.TeanRegisterTuantiAdapter;
import heyong.intellectPinPang.bean.competition.MatchOrderInfoBean;
import heyong.intellectPinPang.databinding.ItemRegisterSubmitDandaBinding;
import heyong.intellectPinPang.databinding.ItemRegisterSubmitDandaBindingImpl;
import heyong.intellectPinPang.databinding.ItemRegisterSubmitDoubleBinding;
import heyong.intellectPinPang.databinding.ItemRegisterSubmitLeaderBinding;
import heyong.intellectPinPang.databinding.ItemRegisterSubmitRefreeBinding;
import heyong.intellectPinPang.databinding.ItemRegisterSubmitTeamBinding;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;


public class TeamRegisterSubmitAdapter extends BaseQuickAdapter<MatchOrderInfoBean, BaseViewHolder> {
    private Context context;
    int width;
    MyDividerItemDecoration mSixDiD;

    public TeamRegisterSubmitAdapter(Context context, int width, MyDividerItemDecoration mSixDiD) {
        super(0);
        this.context = context;
        this.width = width;
        this.mSixDiD = mSixDiD;
        MultiTypeDelegate<MatchOrderInfoBean> multiTypeDelegate = new MultiTypeDelegate<MatchOrderInfoBean>() {
            @Override
            protected int getItemType(MatchOrderInfoBean o) {
                return o.getCardType();
            }
        };
        multiTypeDelegate.registerItemType(0, R.layout.item_register_submit_leader);////领队
        multiTypeDelegate.registerItemType(1, R.layout.item_register_submit_refree);//教练员
        multiTypeDelegate.registerItemType(2, R.layout.item_register_submit_team);//团体
        multiTypeDelegate.registerItemType(3, R.layout.item_register_submit_refree);//单打
        multiTypeDelegate.registerItemType(4, R.layout.item_register_submit_double);//双打
        multiTypeDelegate.registerItemType(5, R.layout.item_register_submit_double);//混双
        multiTypeDelegate.registerItemType(7, R.layout.item_register_submit_no_view);//混双
        setMultiTypeDelegate(multiTypeDelegate);
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchOrderInfoBean item) {
        switch (helper.getItemViewType()) {
            case 7:


                break;
            case 0://领队   item_register_submit_leader
                try{
                    ItemRegisterSubmitLeaderBinding itemRegisterSubmitLeaderBinding = DataBindingUtil.bind(helper.getConvertView());
                    MatchOrderInfoBean.LeaderBean leader = item.getLeader();
                    if(leader!=null) {
                        itemRegisterSubmitLeaderBinding.tvAlreadySelectNum.setText("1");

                        String userName = leader.getUserName();
                        itemRegisterSubmitLeaderBinding.tvUserName.setText("" + userName);
                    }
                }catch (Exception e)
                {
                }


                break;
            case 1://带队教练 或者 单打   item_register_submit_refree
            case 3:
                ItemRegisterSubmitRefreeBinding itemRegisterSubmitRefreeBinding = DataBindingUtil.bind(helper.getConvertView());
                TeanRegisterSingleAdapter teanRegisterSingleAdapter = new TeanRegisterSingleAdapter(context, width);
                GridLayoutManager gridLayoutManagerCoach = new GridLayoutManager(context, 5);
                itemRegisterSubmitRefreeBinding.rvItemRegisterTwo.removeItemDecoration(mSixDiD);
                itemRegisterSubmitRefreeBinding.rvItemRegisterTwo.addItemDecoration(mSixDiD);
                TextView tvTeamTitle = helper.getView(R.id.tv_team_title);
                tvTeamTitle.setText("" + item.getMatchTitle());
                //绑定manager
                itemRegisterSubmitRefreeBinding.rvItemRegisterTwo.setLayoutManager(gridLayoutManagerCoach);
                itemRegisterSubmitRefreeBinding.rvItemRegisterTwo.setAdapter(teanRegisterSingleAdapter);
                List<MatchOrderInfoBean.RefereeBean> referee = item.getReferee();
                if (referee.size() > 0) {
                    teanRegisterSingleAdapter.setNewData(referee);
                    itemRegisterSubmitRefreeBinding.tvAlreadySelectNum.setText("" + referee.size());
                } else {
                    teanRegisterSingleAdapter.setNewData(new ArrayList<>());
                }
                break;
            case 2://团体 布局  recyclerView需要遍历一下   item_register_submit_team
                try
                {
                    ItemRegisterSubmitTeamBinding itemRegisterSubmitTeamBinding = DataBindingUtil.bind(helper.getConvertView());
                    List<MatchOrderInfoBean.PlayersBean> players = item.getPlayers();
                    TeanRegisterTuantiAdapter teanRegisterTuantiAdapter = null;
                    if (players != null && players.size() > 0) {
                        teanRegisterTuantiAdapter = new TeanRegisterTuantiAdapter(context, width, mSixDiD, "" + item.getMatchTitle());
                        itemRegisterSubmitTeamBinding.rvItemRegisterThree.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                        itemRegisterSubmitTeamBinding.rvItemRegisterThree.setAdapter(teanRegisterTuantiAdapter);
                        MatchOrderInfoBean.PlayersBean playersBean = players.get(0);
                        List<MatchOrderInfoBean.PlayersBean.TeamsBean> teams = playersBean.getTeams();
                        teanRegisterTuantiAdapter.setNewData(teams);
                    } else {
                        teanRegisterTuantiAdapter.setNewData(new ArrayList<>());
                    }
                }catch (Exception e)
                {
                }

                break;
//            case 3://单打
//                Log.e(TAG, "convert: position==3======" + new Gson().toJson(item));
//                Log.e(TAG, "convert: players==3===" + new Gson().toJson(item.getPlayers()));
//                ItemRegisterSubmitDandaBindingImpl binding=DataBindingUtil.bind(helper.getConvertView());
//
//                try {
//                    String matchTitle = item.getMatchTitle();
//                    List<MatchOrderInfoBean.RefereeBean> referee2 = new ArrayList<>();
////                TextView tvMatchTitle = helper.getView(R.id.tv_team_title);
////                tvMatchTitle.setText("" + item.getMatchTitle());
//                    List<MatchOrderInfoBean.PlayersBean> players3 = item.getPlayers();
//                    for (int i = 0; i < players3.size(); i++) {
//                        if (players3.get(i).getItemTitle().equals(matchTitle)) {
//                            MatchOrderInfoBean.PlayersBean.TeamsBean teamsBean = players3.get(i).getTeams().get(0);
//                            List<MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean> player = teamsBean.getPlayer();
//                            for (int m = 0; m < player.size(); m++) {
//                                MatchOrderInfoBean.RefereeBean refereeBean = new MatchOrderInfoBean.RefereeBean();
//                                refereeBean.setSex("" + player.get(m).getSex());
//                                refereeBean.setUserId(player.get(m).getUserId());
//                                refereeBean.setUserName(player.get(m).getUserName());
//                                referee2.add(refereeBean);
//                            }
//                            binding.tvTeamTitle.setText(""+item.getMatchTitle());
//                            TeanRegisterSingleAdapter teanRegisterSingleAdapter2 = new TeanRegisterSingleAdapter(context, width);
//                            GridLayoutManager gridLayoutManagerCoach2 = new GridLayoutManager(context, 5);
//                            binding.rvItemRegisterTwo.removeItemDecoration(mSixDiD);
//                            binding.rvItemRegisterTwo.addItemDecoration(mSixDiD);
////                //绑定manager
//                            binding.rvItemRegisterTwo.setLayoutManager(gridLayoutManagerCoach2);
//                            binding.rvItemRegisterTwo.setAdapter(teanRegisterSingleAdapter2);
//                            if (referee2.size() > 0) {
//                                teanRegisterSingleAdapter2.setNewData(referee2);
//                            } else {
//                                teanRegisterSingleAdapter2.setNewData(new ArrayList<>());
//                            }
//                        }
//                    }
//                }catch (Exception e)
//                {
//                    Log.e(TAG, "convert: "+e.getMessage() );
//                }



//                break;
            case 4://双打   item_register_submit_double
                List<MatchOrderInfoBean.PlayersBean> players1 = item.getPlayers();
                TeamZhuanyeDaAdapter shuangDaAdapter = null;
                ItemRegisterSubmitDoubleBinding itemRegisterSubmitDoubleBinding = DataBindingUtil.bind(helper.getConvertView());

                if (players1.size() > 0) {
                    itemRegisterSubmitDoubleBinding.rvItemRegisterFour.setVisibility(View.VISIBLE);

                    MatchOrderInfoBean.PlayersBean playersBean = players1.get(0);
                    shuangDaAdapter = new TeamZhuanyeDaAdapter(context, width, mSixDiD, "" + playersBean.getItemTitle());
                    itemRegisterSubmitDoubleBinding.rvItemRegisterFour.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                    itemRegisterSubmitDoubleBinding.rvItemRegisterFour.setAdapter(shuangDaAdapter);
                    if(playersBean.getTeams().size()>0) {
                        itemRegisterSubmitDoubleBinding.rvItemRegisterFour.setVisibility(View.VISIBLE);
                        shuangDaAdapter.setNewData(playersBean.getTeams());
                    }else
                    {
                        itemRegisterSubmitDoubleBinding.rvItemRegisterFour.setVisibility(View.GONE);
                    }
                } else {
                    shuangDaAdapter.setNewData(new ArrayList<>());
//                    itemRegisterSubmitDoubleBinding.rvItemRegisterFour.setVisibility(View.GONE);
                }


                break;
            case 5://混双    item_register_submit_double
                List<MatchOrderInfoBean.PlayersBean> players2 = item.getPlayers();
                TeanRegisterShuangDaYeyuAdapter shuangDaAdapter2 = null;
                if (players2.size() > 0) {
                    MatchOrderInfoBean.PlayersBean playersBean = players2.get(0);
                    ItemRegisterSubmitDoubleBinding itemRegisterSubmitDoubleBinding2 = DataBindingUtil.bind(helper.getConvertView());
                    shuangDaAdapter2 = new TeanRegisterShuangDaYeyuAdapter(context, width, mSixDiD, "" + playersBean.getItemTitle());
                    itemRegisterSubmitDoubleBinding2.rvItemRegisterFour.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                    itemRegisterSubmitDoubleBinding2.rvItemRegisterFour.setAdapter(shuangDaAdapter2);
                    shuangDaAdapter2.setNewData(playersBean.getTeams());
                } else {
                    shuangDaAdapter2.setNewData(new ArrayList<>());
                }
                break;
        }
    }
}
