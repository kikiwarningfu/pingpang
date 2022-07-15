package heyong.intellectPinPang.ui.competition.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import heyong.intellectPinPang.adapter.game.MyEventAdapter;
import heyong.intellectPinPang.bean.competition.MyMatchBean;
import heyong.intellectPinPang.databinding.ActivityMyEventBinding;
import heyong.intellectPinPang.live.activity.MyGameActivity;
import heyong.intellectPinPang.ui.mine.MineViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;

/**
 * 我的赛事
 */
public class MyEventActivity extends BaseActivity<ActivityMyEventBinding, MineViewModel> implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, OnRefreshListener {
    MyEventAdapter myEventAdapter;
    MyRefreshAnimHeader mRefreshAnimHeader;
    public static final String TAG = MyEventActivity.class.getSimpleName();
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean haveMore = true;
    List<MyMatchBean.ListBean> list = new ArrayList<>();
    private int status = 1;//状态  1 进行中   2已结束
    private int role = -1;//1 教练员  2运动员  3裁判员  4裁判长

    @Override
    public int getLayoutRes() {
        return R.layout.activity_my_event;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        status = 1;
        role = 2;
        mBinding.llUnderWay.setBackgroundResource(R.drawable.shape_left_corners_blue);
        mBinding.llFinished.setBackgroundResource(R.drawable.shape_right_corners_gray_e5);
        mBinding.tvUnderWay.setTextColor(Color.parseColor("#FFFFFF"));
        mBinding.tvFinished.setTextColor(Color.parseColor("#666666"));
        getData();
        myEventAdapter = new MyEventAdapter(MyEventActivity.this);
        myEventAdapter.setRole(2);
        mRefreshAnimHeader = new MyRefreshAnimHeader(MyEventActivity.this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
        list = new ArrayList<>();
//        mBinding.swFresh.setRefreshHeader(mRefreshAnimHeader);
        mBinding.rvMineEvent.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvMineEvent.setAdapter(myEventAdapter);
        myEventAdapter.setOnItemChildClickListener(this);
        myEventAdapter.setOnItemClickListener(this);
        mBinding.swFresh.setOnRefreshListener(this);
        mViewModel.signOutMatchOrderLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    new MessageDialogBuilder(MyEventActivity.this)
                            .setMessage("")
                            .setTvTitle("弃权成功")
                            .setTvCancel("取消")
                            .setTvSure("确定")
                            .setBtnCancleHint(true)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    pageNum = 1;
                                    getData();
                                }
                            })
                            .show();

                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mBinding.swFresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (haveMore) {
                    pageNum++;
                    getData();
                } else {
                    mBinding.swFresh.finishLoadmore();
                }
            }
        });
        mViewModel.getMyMatchLiveData.observe(this, new Observer<ResponseBean<MyMatchBean>>() {
            @Override
            public void onChanged(ResponseBean<MyMatchBean> loginBeanResponseBean) {
                    if(loginBeanResponseBean.getErrorCode().equals("200")){
                        if (pageNum == 1) {
                        list.clear();
                        if (mBinding.swFresh != null) {
                            mBinding.swFresh.finishRefresh();
                        }
                    } else {
                        mBinding.swFresh.finishLoadmore();
                    }

                    if (list != null) {
                        list.addAll(loginBeanResponseBean.getData().getList());
                        if (list != null && list.size() < pageSize) {
                            haveMore = false;
                            mBinding.swFresh.setEnableLoadmore(false);
                        }
                        myEventAdapter.setNewData(list);
                        Log.e(TAG, "onChanged: " + myEventAdapter.getData().size());
                    }
                }
                if (myEventAdapter.getData().size() > 0) {
                    mBinding.llEmptyView.setVisibility(View.GONE);
                } else {
                    mBinding.llEmptyView.setVisibility(View.VISIBLE);
                }
                Log.e(TAG, "initView: size===" + myEventAdapter.getData().size());
            }

        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_sports://运动员
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Log.e(TAG, "onClick: 运动员" );
                role = 2;
                myEventAdapter.setRole(2);
                mBinding.viewSports.setVisibility(View.VISIBLE);
                mBinding.tvSports.setTextColor(Color.parseColor("#4795ED"));
                mBinding.viewJudge.setVisibility(View.INVISIBLE);
                mBinding.tvJudge.setTextColor(Color.parseColor("#666666"));
                mBinding.viewReferee.setVisibility(View.INVISIBLE);
                mBinding.tvReferee.setTextColor(Color.parseColor("#666666"));
                mBinding.viewCoach.setVisibility(View.INVISIBLE);
                mBinding.tvCoach.setTextColor(Color.parseColor("#666666"));
                status = 1;
                pageNum = 1;
                haveMore=true;
                mBinding.llUnderWay.setBackgroundResource(R.drawable.shape_left_corners_blue);
                mBinding.llFinished.setBackgroundResource(R.drawable.shape_right_corners_gray_e5);
                mBinding.tvUnderWay.setTextColor(Color.parseColor("#FFFFFF"));
                mBinding.tvFinished.setTextColor(Color.parseColor("#666666"));
                mBinding.swFresh.setEnableLoadmore(true);

                getData();
                break;
            case R.id.rl_judge://裁判员
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                role = 3;
                myEventAdapter.setRole(3);
                mBinding.viewSports.setVisibility(View.INVISIBLE);
                mBinding.tvSports.setTextColor(Color.parseColor("#666666"));
                mBinding.viewJudge.setVisibility(View.VISIBLE);
                mBinding.tvJudge.setTextColor(Color.parseColor("#4795ED"));
                mBinding.viewReferee.setVisibility(View.INVISIBLE);
                mBinding.tvReferee.setTextColor(Color.parseColor("#666666"));
                mBinding.viewCoach.setVisibility(View.INVISIBLE);
                mBinding.tvCoach.setTextColor(Color.parseColor("#666666"));
                status = 1;
                mBinding.llUnderWay.setBackgroundResource(R.drawable.shape_left_corners_blue);
                mBinding.llFinished.setBackgroundResource(R.drawable.shape_right_corners_gray_e5);
                mBinding.tvUnderWay.setTextColor(Color.parseColor("#FFFFFF"));
                mBinding.tvFinished.setTextColor(Color.parseColor("#666666"));
                pageNum = 1;
                haveMore=true;
                mBinding.swFresh.setEnableLoadmore(true);

                getData();
                break;
            case R.id.rl_referee://裁判长 1
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                role = 4;
                myEventAdapter.setRole(4);
                mBinding.viewSports.setVisibility(View.INVISIBLE);
                mBinding.tvSports.setTextColor(Color.parseColor("#666666"));
                mBinding.viewJudge.setVisibility(View.INVISIBLE);
                mBinding.tvJudge.setTextColor(Color.parseColor("#666666"));
                mBinding.viewReferee.setVisibility(View.VISIBLE);
                mBinding.tvReferee.setTextColor(Color.parseColor("#4795ED"));
                mBinding.viewCoach.setVisibility(View.INVISIBLE);
                mBinding.tvCoach.setTextColor(Color.parseColor("#666666"));
                status = 1;
                mBinding.llUnderWay.setBackgroundResource(R.drawable.shape_left_corners_blue);
                mBinding.llFinished.setBackgroundResource(R.drawable.shape_right_corners_gray_e5);
                mBinding.tvUnderWay.setTextColor(Color.parseColor("#FFFFFF"));
                mBinding.tvFinished.setTextColor(Color.parseColor("#666666"));
                pageNum = 1;
                haveMore=true;
                mBinding.swFresh.setEnableLoadmore(true);

                getData();
                break;
            case R.id.rl_coach://教练员
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                role = 1;
                myEventAdapter.setRole(1);
                mBinding.viewSports.setVisibility(View.INVISIBLE);
                mBinding.tvSports.setTextColor(Color.parseColor("#666666"));
                mBinding.viewJudge.setVisibility(View.INVISIBLE);
                mBinding.tvJudge.setTextColor(Color.parseColor("#666666"));
                mBinding.viewReferee.setVisibility(View.INVISIBLE);
                mBinding.tvReferee.setTextColor(Color.parseColor("#666666"));
                mBinding.viewCoach.setVisibility(View.VISIBLE);
                mBinding.tvCoach.setTextColor(Color.parseColor("#4795ED"));
                status = 1;
                mBinding.llUnderWay.setBackgroundResource(R.drawable.shape_left_corners_blue);
                mBinding.llFinished.setBackgroundResource(R.drawable.shape_right_corners_gray_e5);
                mBinding.tvUnderWay.setTextColor(Color.parseColor("#FFFFFF"));
                mBinding.tvFinished.setTextColor(Color.parseColor("#666666"));
                pageNum = 1;
                haveMore=true;
                mBinding.swFresh.setEnableLoadmore(true);

                getData();
                break;

            case R.id.ll_under_way://进行中
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                status = 1;
                mBinding.llUnderWay.setBackgroundResource(R.drawable.shape_left_corners_blue);
                mBinding.llFinished.setBackgroundResource(R.drawable.shape_right_corners_gray_e5);
                mBinding.tvUnderWay.setTextColor(Color.parseColor("#FFFFFF"));
                mBinding.tvFinished.setTextColor(Color.parseColor("#666666"));
                pageNum = 1;
                haveMore=true;
                mBinding.swFresh.setEnableLoadmore(true);

                getData();
                break;
            case R.id.ll_finished://已结束
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                status = 2;
                mBinding.llUnderWay.setBackgroundResource(R.drawable.shape_left_corners_gray_e5);
                mBinding.llFinished.setBackgroundResource(R.drawable.shape_right_corners_blue);
                mBinding.tvUnderWay.setTextColor(Color.parseColor("#666666"));
                mBinding.tvFinished.setTextColor(Color.parseColor("#FFFFFF"));
                pageNum = 1;
                haveMore=true;
                mBinding.swFresh.setEnableLoadmore(true);
                getData();

                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        boolean fastClick = isFastClick();
        if (!fastClick) {
            switch (view.getId()) {
                case R.id.tv_give_up_competition:
                    MyMatchBean.ListBean listBean = myEventAdapter.getData().get(position);

                    new MessageDialogBuilder(MyEventActivity.this)
                            .setMessage("您将弃权本次报名的所有比赛")
                            .setTvTitle("是否弃权本次比赛？")
                            .setTvCancel("取消")
                            .setTvSure("确定")
                            .setBtnCancleHint(false)
                            .setSureListener(v1 ->
                                    getQData("" + listBean.getId())
                            )
                            .show();
                    break;
                case R.id.tv_competition_detail://比赛详情
                    long id = myEventAdapter.getData().get(position).getId();

                    if (role == 1) {
                        Intent intent = new Intent(MyEventActivity.this, CoachTeamDetailActivity.class);
                        intent.putExtra(CoachTeamDetailActivity.MATCH_ID, "" + id);
                        startActivity(intent);
                    } else if (role == 2)//运动员
                    {
                        MyGameActivity.goMatchActivity(MyEventActivity.this,
                                "", "" + myEventAdapter.getData().get(position).getId(), "", "2");
                    } else if (role == 3)//裁判员
                    {
                        Intent intent = new Intent();
                        intent.setClass(MyEventActivity.this, RefereeCompetitionInterfaceActivity.class);
                        intent.putExtra(RefereeCompetitionInterfaceActivity.CODE, "2");
                        intent.putExtra(RefereeCompetitionInterfaceActivity.MATCH_ID, "" + id);
                        startActivity(intent);
                    } else if (role == 4)//裁判长
                    {
                        Intent intent = new Intent();
                        intent.setClass(MyEventActivity.this, ChiefJudgeConsoleActivity.class);
                        intent.putExtra(ChiefJudgeConsoleActivity.MATCHID, "" + id);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MyEventActivity.this, CoachTeamDetailActivity.class);
                        intent.putExtra(CoachTeamDetailActivity.MATCH_ID, "" + id);
                        startActivity(intent);
                    }
                    break;
            }
        }
    }

    private void getQData(String ids) {
        mViewModel.signOutMatchOrder("" + ids);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        boolean fastClick = isFastClick();
        if (!fastClick) {
            long id = myEventAdapter.getData().get(position).getId();
//        private int role = -1;//1 教练员  2运动员  3裁判员  4裁判长
            if (role == 1) {
                Intent intent = new Intent(MyEventActivity.this, CoachTeamDetailActivity.class);
                intent.putExtra(CoachTeamDetailActivity.MATCH_ID, "" + id);
                startActivity(intent);
            } else if (role == 2)//运动员
            {
                //运动员详情

                MyGameActivity.goMatchActivity(MyEventActivity.this,
                        "", "" + myEventAdapter.getData().get(position).getId(), "", "2");
            } else if (role == 3)//裁判员
            {
                Intent intent = new Intent();
                intent.setClass(MyEventActivity.this, RefereeCompetitionInterfaceActivity.class);
                intent.putExtra(RefereeCompetitionInterfaceActivity.CODE, "2");
                intent.putExtra(RefereeCompetitionInterfaceActivity.MATCH_ID, "" + id);
                startActivity(intent);
            } else if (role == 4) {
                Intent intent = new Intent();
                intent.setClass(MyEventActivity.this, ChiefJudgeConsoleActivity.class);
                intent.putExtra(ChiefJudgeConsoleActivity.MATCHID, "" + id);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MyEventActivity.this, CoachTeamDetailActivity.class);
                intent.putExtra(CoachTeamDetailActivity.MATCH_ID, "" + id);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mBinding.swFresh.setEnableLoadmore(true);
        haveMore = true;
        pageNum = 1;
        getData();

    }

    private void getData() {
        mViewModel.getMyMatch("" + pageNum, "" + pageSize, "" + role, "" + status);
    }
}