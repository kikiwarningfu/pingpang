package heyong.intellectPinPang.ui.competition.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.TeamNumberAdapter;
import heyong.intellectPinPang.adapter.game.TeamNumberCoachAdapter;
import heyong.intellectPinPang.bean.competition.TeamMemberByMatchBean;
import heyong.intellectPinPang.databinding.ActivityTeamNumbersBinding;
import heyong.intellectPinPang.ui.club.activity.MemberShipDetailsActivity;
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel;
import heyong.lzy.imagepicker.util.StatusBarUtil;

/**
 * 队伍成员
 */
public class TeamNumbersActivity extends BaseActivity<ActivityTeamNumbersBinding, HomePageViewModel> implements View.OnClickListener {
    private WindowManager wm;
    private int width;

    public static final String IDS = "ids";
    public static final String CLUB_ID = "club_id";
    public static final String MATCH_ID = "match_id";

    private String id;
    private String clubId;
    private String matchId;
    private String clubUserId = "";


    @Override
    public int getLayoutRes() {
        return R.layout.activity_team_numbers;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        id = getIntent().getStringExtra(IDS);
        clubId = getIntent().getStringExtra(CLUB_ID);
        matchId = getIntent().getStringExtra(MATCH_ID);

        mBinding.setListener(this);
        StatusBarUtil.transparencyBar(TeamNumbersActivity.this);
        wm = getWindowManager();
        width = wm.getDefaultDisplay().getWidth();

        mViewModel.teamMemberByMatch("" + id, "" + clubId, "" + matchId);
        TeamNumberAdapter teamNumberAdapter = new TeamNumberAdapter(TeamNumbersActivity.this, width);
        TeamNumberCoachAdapter teamNumberCoachAdapter = new TeamNumberCoachAdapter(TeamNumbersActivity.this, width);
        mBinding.rvTeamNumbers.setAdapter(teamNumberAdapter);
        mBinding.rvTeamNumbers.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        teamNumberAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    long userId = teamNumberAdapter.getData().get(position).getUserId();
                    MemberShipDetailsActivity.goActivity(TeamNumbersActivity.this, "" + userId, 1);
                }
            }
        });
        mBinding.rvCoach.setAdapter(teamNumberCoachAdapter);
        mBinding.rvCoach.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        mViewModel.teamByMatchLiveData.observe(this, new Observer<ResponseBean<TeamMemberByMatchBean>>() {
            @Override
            public void onChanged(ResponseBean<TeamMemberByMatchBean> responseBean) {
                if (responseBean.getData() != null) {
                    TeamMemberByMatchBean data = responseBean.getData();
                    List<TeamMemberByMatchBean.CoachesBean> coaches = data.getCoaches();
                    List<TeamMemberByMatchBean.PlayersBean> players = data.getPlayers();
                    ImageLoader.loadImage(mBinding.ivLogo, data.getLogo(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);

                    mBinding.tvClubName.setText("" + data.getClubName());
                    if (coaches != null && coaches.size() > 0) {
                        teamNumberCoachAdapter.setNewData(coaches);
                    } else {
                        teamNumberCoachAdapter.setNewData(new ArrayList<>());
                    }
                    if (players != null && players.size() > 0) {
                        teamNumberAdapter.setNewData(players);
                    } else {
                        teamNumberAdapter.setNewData(new ArrayList<>());
                    }
                    teamNumberCoachAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            boolean fastClick = isFastClick();
                            if (!fastClick) {
                                long userId = teamNumberCoachAdapter.getData().get(position).getUserId();
                                MemberShipDetailsActivity.goActivity(TeamNumbersActivity.this, "" + userId, 1);
                            }
                        }
                    });

                }
            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_finish:

                finish();
                break;
        }
    }
}