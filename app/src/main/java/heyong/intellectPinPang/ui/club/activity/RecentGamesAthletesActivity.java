package heyong.intellectPinPang.ui.club.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.RecentGamesAthletesAdapter;
import heyong.intellectPinPang.bean.competition.MatchExperienceBean;
import heyong.intellectPinPang.databinding.ActivityRecentGamesAthletesBinding;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;

/**
 * 运动员比赛经历
 */
public class RecentGamesAthletesActivity extends BaseActivity<ActivityRecentGamesAthletesBinding, ClubViewModel> implements View.OnClickListener {
    RecentGamesAthletesAdapter recentGamesAthletesAdapter;
    public static final String SPORTS_ID = "sports_id";
    private String mSportsId = "";
    private int dayType = 1;
    MyRefreshAnimHeader mRefreshAnimHeader;

    private int pageNumSports = 1;
    private int pageSizeSports = 10;
    private boolean haveMoreSports = true;
    MyRefreshAnimHeader mRefreshAnimHeaderSports;

    public static final String TAG = RecentGamesAthletesActivity.class.getSimpleName();


    @Override
    public int getLayoutRes() {
        return R.layout.activity_recent_games_athletes;
    }

    @Override
    protected void onResume() {
        super.onResume();
        pageNumSports = 1;
        getSportsData("" + pageNumSports, "" + pageSizeSports);
    }
    List<MatchExperienceBean.ListBean> list=new ArrayList<>();

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        mSportsId = getIntent().getStringExtra(SPORTS_ID);
        recentGamesAthletesAdapter = new RecentGamesAthletesAdapter();
        mRefreshAnimHeader = new MyRefreshAnimHeader(this);

        mBinding.mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.mRecyclerView.setAdapter(recentGamesAthletesAdapter);
//        mBinding.swFresh.setRefreshHeader(mRefreshAnimHeader);

        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
//        mBinding.swFresh.setRefreshHeader(mRefreshAnimHeaderSports);

        mViewModel.matchExperienceLiveData.observe(this, new Observer<ResponseBean<MatchExperienceBean>>() {
            @Override
            public void onChanged(ResponseBean<MatchExperienceBean> xlUserInfoListBeanResponseBean) {
//                if(xlUserInfoListBeanResponseBean.getErrorCode().equals("200")){

//                    if (xlUserInfoListBeanResponseBean.isSuccess()) {
//                    if (pageNumSports == 1) {
//                        if (mBinding.swFresh != null) {
//                            mBinding.swFresh.finishRefresh();
//                        }
//                    } else {
//                        mBinding.swFresh.finishLoadmore();
//                    }
//                    List<MatchExperienceBean.ListBean> list = xlUserInfoListBeanResponseBean.getData().getList();
//                    if (list != null) {
//
//                        if (list != null && list.size() < pageSizeSports) {
//                            haveMoreSports = false;
//                            mBinding.swFresh.setEnableLoadmore(false);
//                        }
//                        recentGamesAthletesAdapter.setNewData(list);
//                    }
//                    int size = recentGamesAthletesAdapter.getData().size();
//                    if (size > 0) {
//                        mBinding.mRecyclerView.setVisibility(View.VISIBLE);
//                        mBinding.tvClubEmpty.setVisibility(View.GONE);
//                    } else {
//                        mBinding.mRecyclerView.setVisibility(View.GONE);
//                        mBinding.tvClubEmpty.setVisibility(View.VISIBLE);
//                    }
//
                    if(xlUserInfoListBeanResponseBean.getErrorCode().equals("200")){

                        if (pageNumSports == 1) {
                            list.clear();
                            if ( mBinding.swFresh != null) {
                                mBinding.swFresh.finishRefresh();
                            }
                        } else {
                            mBinding.swFresh.finishLoadmore();
                        }
                        if (list != null) {
                            list.addAll(xlUserInfoListBeanResponseBean.getData().getList());
                            if (list != null && list.size() < pageSizeSports) {
                                haveMoreSports = false;
                                mBinding.swFresh.setEnableLoadmore(false);
                            }
                            recentGamesAthletesAdapter.setNewData(list);

                            int size = recentGamesAthletesAdapter.getData().size();
                            if (size > 0) {
                                mBinding.mRecyclerView.setVisibility(View.VISIBLE);
                                mBinding.tvClubEmpty.setVisibility(View.GONE);
                            } else {
                                mBinding.mRecyclerView.setVisibility(View.GONE);
                                mBinding.tvClubEmpty.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {

                    }
//                }
            }
        });
        mBinding.swFresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mBinding.swFresh.setEnableLoadmore(true);
                haveMoreSports = true;
                pageNumSports = 1;
                getSportsData("" + pageNumSports, "" + pageSizeSports);

            }
        });
        mBinding.swFresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (haveMoreSports) {
                    pageNumSports++;
                    getSportsData("" + pageNumSports, "" + pageSizeSports);
                } else {
                    mBinding.swFresh.finishLoadmore();
                }
            }
        });
        recentGamesAthletesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                XlUserInfoListBean.ListBean listBean = recentGamesAthletesAdapter.getData().get(position);
//
//                MemberShipDetailsActivity.goActivity(RecentGamesAthletesActivity.this,
//                        1,""+listBean.getId());
            }
        });
        recentGamesAthletesAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

    }

    private void getSportsData(String pageNumSports, String pageSize) {
        mViewModel.matchExperience(mSportsId, pageNumSports, pageSize, dayType);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sports_man://一周内
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.tvSportsMan.setBackgroundResource(R.drawable.shape_left_corners_blue);
                mBinding.viewSportMan.setVisibility(View.GONE);
                mBinding.tvClub.setBackgroundResource(R.drawable.shape_corners_gray);
                mBinding.viewFight.setVisibility(View.VISIBLE);
                mBinding.tvFight.setBackgroundResource(R.drawable.shape_right_corners_gray);

                mBinding.tvSportsMan.setTextColor(Color.parseColor("#FFFFFF"));
                mBinding.tvClub.setTextColor(Color.parseColor("#666666"));
                mBinding.tvFight.setTextColor(Color.parseColor("#666666"));
                dayType = 1;
                pageNumSports = 1;
                haveMoreSports = true;
                getSportsData("" + pageNumSports, "" + pageSizeSports);

                break;

            case R.id.tv_club://一月内
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.tvSportsMan.setBackgroundResource(R.drawable.shape_left_corners_gray);
                mBinding.viewSportMan.setVisibility(View.GONE);
                mBinding.tvClub.setBackgroundResource(R.drawable.shape_corners_blue);
                mBinding.viewFight.setVisibility(View.GONE);
                mBinding.tvFight.setBackgroundResource(R.drawable.shape_right_corners_gray);

                mBinding.tvSportsMan.setTextColor(Color.parseColor("#666666"));
                mBinding.tvClub.setTextColor(Color.parseColor("#FFFFFF"));
                mBinding.tvFight.setTextColor(Color.parseColor("#666666"));
                dayType = 2;
                pageNumSports = 1;
                haveMoreSports = true;
                getSportsData("" + pageNumSports, "" + pageSizeSports);
                break;

            case R.id.tv_fight://一年内
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.tvSportsMan.setBackgroundResource(R.drawable.shape_left_corners_gray);
                mBinding.viewSportMan.setVisibility(View.VISIBLE);
                mBinding.tvClub.setBackgroundResource(R.drawable.shape_corners_gray);
                mBinding.viewFight.setVisibility(View.GONE);
                mBinding.tvFight.setBackgroundResource(R.drawable.shape_right_corners_blue);

                mBinding.tvSportsMan.setTextColor(Color.parseColor("#666666"));
                mBinding.tvClub.setTextColor(Color.parseColor("#666666"));
                mBinding.tvFight.setTextColor(Color.parseColor("#FFFFFF"));
                dayType = -1;
                pageNumSports = 1;
                haveMoreSports = true;
                getSportsData("" + pageNumSports, "" + pageSizeSports);
                break;
        }
    }
}