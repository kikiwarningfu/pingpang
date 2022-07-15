package heyong.intellectPinPang.ui.club.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.InternalGameListAdapter;
import heyong.intellectPinPang.bean.competition.XlClubContestInfoListBean;
import heyong.intellectPinPang.databinding.ActivityInternalGameListBinding;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;

/**
 * 对内比赛列表
 */
public class InternalGameListActivity extends BaseActivity<ActivityInternalGameListBinding, ClubViewModel> implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, OnRefreshListener {
    InternalGameListAdapter internalGameListAdapter;
    public static final String CLUB_ID = "clubId";
    private String strClubId = "";

    private int pageNum = 1;
    private int pageSize = 10;
    private boolean haveMore = true;
    MyRefreshAnimHeader mRefreshAnimHeader;
    public static final String TAG = InternalGameListActivity.class.getSimpleName();
    List<XlClubContestInfoListBean.ListBean> list;

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.getXlClubContestInfoList(strClubId, "" + pageNum, "" + pageSize);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_internal_game_list;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        strClubId = getIntent().getStringExtra(CLUB_ID);

        mRefreshAnimHeader = new MyRefreshAnimHeader(InternalGameListActivity.this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
//        mBinding.swFresh.setRefreshHeader(mRefreshAnimHeader);
        internalGameListAdapter = new InternalGameListAdapter();
        list = new ArrayList<>();
        mBinding.rvGameList.setAdapter(internalGameListAdapter);
        mBinding.rvGameList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        internalGameListAdapter.setOnItemChildClickListener(this);
        internalGameListAdapter.setOnItemClickListener(this);
        mBinding.swFresh.setOnRefreshListener(this);
        mBinding.swFresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (haveMore) {
                    pageNum++;
                    mViewModel.getXlClubContestInfoList(strClubId, "" + pageNum, "" + pageSize);
                } else {
                    mBinding.swFresh.finishLoadmore();
                }
            }
        });
        mViewModel.getXlClubContestInfoListLiveData.observe(this, new Observer<ResponseBean<XlClubContestInfoListBean>>() {
            @Override
            public void onChanged(ResponseBean<XlClubContestInfoListBean> loginBeanResponseBean) {
                if(loginBeanResponseBean.getErrorCode().equals("200"))
                {
                        if (pageNum == 1) {
                            list.clear();
                            if (mBinding.swFresh != null) {
                                mBinding.swFresh.finishRefresh();
                            }
                        } else {
                            mBinding.swFresh.finishLoadmore();
                        }

                        if (loginBeanResponseBean.getData().getList() != null) {
                            list.addAll(loginBeanResponseBean.getData().getList());
                            if (list != null && list.size() < pageSize) {
                                haveMore = false;
                                mBinding.swFresh.setEnableLoadmore(false);
                            }
                            internalGameListAdapter.setNewData(list);
                            Log.e(TAG, "onChanged: size===" + internalGameListAdapter.getData().size());
                        }

                }

            }
        });
        mViewModel.getXlClubContestInfoList(strClubId, "" + pageNum, "" + pageSize);

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        boolean fastClick = isFastClick();
        if (!fastClick) {
            /*判断跳转*/
            List<XlClubContestInfoListBean.ListBean> data = internalGameListAdapter.getData();
            String matchType = data.get(position).getMatchType();
            String contestStatus = data.get(position).getContestStatus();
            if ("1".equals(matchType)) {//单打：1进行中 2已结束
                Class pendingClass = "1".equals(contestStatus) ? GameUnderWayActivity.class : GameUnderWayScoreActivity.class;
                Intent intent = new Intent(InternalGameListActivity.this, pendingClass);
                intent.putExtra(GameUnderWayScoreActivity.CONTEST_ID, "" + data.get(position).getId());
                intent.putExtra(GameUnderWayScoreActivity.MATCH_TITLE, "" + data.get(position).getContestTitle());
                intent.putExtra(GameUnderWayActivity.CLUB_ID, strClubId);
                startActivity(intent);
            } else if("2".equals(matchType)) {//团体：1进行中 2已结束 3 未开始
                Intent pendingTeamIntent;
                switch (contestStatus) {
                    case "1":
                        pendingTeamIntent = new Intent(InternalGameListActivity.this, TeamResultsActivity.class);
                        pendingTeamIntent.putExtra(TeamResultsActivity.CONTESTID, "" + data.get(position).getId());
                        pendingTeamIntent.putExtra(TeamResultsActivity.CLUB_ID, strClubId);
                        break;
                    case "2":
                        pendingTeamIntent = new Intent(InternalGameListActivity.this, TeamResultsScoreActivity.class);
                        pendingTeamIntent.putExtra(TeamResultsScoreActivity.CONTESTID, "" + data.get(position).getId());
                        pendingTeamIntent.putExtra(TeamResultsScoreActivity.TEAM_STATUS, 1);
                        break;
                    case "3":
                        pendingTeamIntent = new Intent(InternalGameListActivity.this, DrawLotsHostAndGuestActivity.class);
                        pendingTeamIntent.putExtra(DrawLotsHostAndGuestActivity.IDS, data.get(position).getId());
                        break;
                    default:
                        throw new IllegalStateException("Unexpected contestStatus: " + contestStatus);
                }
                startActivity(pendingTeamIntent);
            }
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mBinding.swFresh.setEnableLoadmore(true);
        haveMore = true;
        pageNum = 1;
        mViewModel.getXlClubContestInfoList(strClubId, "" + pageNum, "" + pageSize);
    }
}