package heyong.intellectPinPang.live.activity;

import android.content.Context;
import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.RefereeDisplayAdapter;
import heyong.intellectPinPang.adapter.game.ReferenceMyMatchAdapter;
import heyong.intellectPinPang.bean.competition.MyGameListBean;
import heyong.intellectPinPang.bean.competition.DateLimitBean;
import heyong.intellectPinPang.bean.gsonbean.RefereeDisplayTimeBean;
import heyong.intellectPinPang.bean.live.LiveMatchStatusBean;
import heyong.intellectPinPang.databinding.ActivityMyGameBinding;
import heyong.intellectPinPang.ui.club.activity.TeamResultsScoreOfficialActivity;
import heyong.intellectPinPang.ui.competition.activity.DoubleDetailScoreOfficialActivity;
import heyong.intellectPinPang.ui.competition.activity.SinglesDetailScoreOfficialActivity;
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel;
import heyong.intellectPinPang.utils.MyDateUtils;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;

/**
 * 我的赛事
 */
public class MyGameActivity extends BaseActivity<ActivityMyGameBinding, HomePageViewModel> implements View.OnClickListener, OnRefreshListener {

    ReferenceMyMatchAdapter referenceAllMatchAdapter;
    public static final String TAG = MyGameActivity.class.getSimpleName();
    MyDividerItemDecoration mSixDiD;
    public static final String CODE = "code";
    private String code = "";
    public static final String TABLE_NUM = "table_num";
    private String strTableNum = "";
    public static final String MATCH_ID = "match_id";
    private String strMatchId = "";
    public static final String IDS = "id";
    private String mIds = "";
    private String status = "";
    private String times = "";
    MyRefreshAnimHeader mRefreshAnimHeader;
    private String matchArrangeId = "";
    private int hitType = 0;//1单打 2 双打
    String matchTitle = "";

    List<MyGameListBean> list = new ArrayList<>();
    private int pageNum = 1;
    private int pageSize = 5;
    private boolean haveMore = true;
    List<MyGameListBean>  tableNumArrangeBean;

    RefereeDisplayAdapter refereeDisplayAdapter;
    Calendar beginCalendar;
    Calendar endCalendar;

    //    yyyy-MM-dd
    public static void goMatchActivity(Context context, String strTableNum, String strMatchId, String mIds, String code) {
        Intent intent = new Intent(context, MyGameActivity.class);
        intent.putExtra(TABLE_NUM, strTableNum);
        intent.putExtra(MATCH_ID, strMatchId);
        intent.putExtra(IDS, mIds);
        intent.putExtra(CODE, code);
        context.startActivity(intent);

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_my_game;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        mRefreshAnimHeader = new MyRefreshAnimHeader(MyGameActivity.this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
//        mBinding.swFresh.setRefreshHeader(mRefreshAnimHeader);
        mBinding.swFresh.setOnRefreshListener(this);
        status = "";
        mBinding.ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mIds = getIntent().getStringExtra(IDS);//编排id
        strMatchId = getIntent().getStringExtra(MATCH_ID);
        strTableNum = getIntent().getStringExtra(TABLE_NUM);
        code = getIntent().getStringExtra(CODE);//  2
        mBinding.swFresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (haveMore) {
                    pageNum++;
                    mViewModel.wodeMatchs("" + strMatchId, "" + times,
                            "" + pageNum, "" + pageSize);
                } else {
                    mBinding.swFresh.finishLoadmore();
                }
            }
        });

        mViewModel.dateLimit("" + strMatchId);
        mViewModel.dateLimitLiveData.observe(this, new Observer<ResponseBean<DateLimitBean>>() {
            @Override
            public void onChanged(ResponseBean<DateLimitBean> dateLimitBeanResponseBean) {
                if (dateLimitBeanResponseBean.getErrorCode().equals("200")) {
                    DateLimitBean data = dateLimitBeanResponseBean.getData();
                    if (data != null) {
                        long beginTime = data.getBeginTime();
                        long endTime = data.getEndTime();
                        String beginStrTime = MyDateUtils.getDateToString(beginTime);
                        String endStrTime = MyDateUtils.getDateToString(endTime);
                        Log.e(TAG, "onChanged: beginTime==" + beginStrTime + "===endTime" + endStrTime);
                        beginCalendar = MyDateUtils.getCalendar(beginStrTime);
                        endCalendar = MyDateUtils.getCalendar(endStrTime);
                        List<Date> datesBetweenTwoDate;
                        if (beginCalendar.equals(endCalendar)) {
                            datesBetweenTwoDate = new ArrayList<>();
                            datesBetweenTwoDate.add(beginCalendar.getTime());
                        } else {
                            datesBetweenTwoDate = MyDateUtils.getDatesBetweenTwoDate(beginCalendar.getTime(), endCalendar.getTime());
                        }
                        times = beginStrTime;

                        List<RefereeDisplayTimeBean> refereeDisplayTimeBeanList = new ArrayList<>();
                        for (int i = 0; i < datesBetweenTwoDate.size(); i++) {
                            RefereeDisplayTimeBean refereeDisplayTimeBean = new RefereeDisplayTimeBean();
                            Date date = datesBetweenTwoDate.get(i);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String transformDate = simpleDateFormat.format(date);
                            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM月dd日");
                            String transformDate2 = simpleDateFormat2.format(date);
                            if (i == 0) {
                                refereeDisplayTimeBean.setSelect(true);
                            } else {
                                refereeDisplayTimeBean.setSelect(false);
                            }
                            refereeDisplayTimeBean.setTime(transformDate2);
                            refereeDisplayTimeBean.setUpTime(transformDate);
                            refereeDisplayTimeBeanList.add(refereeDisplayTimeBean);
                        }
                        refereeDisplayAdapter.setNewData(refereeDisplayTimeBeanList);
                    }
                } else {
                    toast("" + dateLimitBeanResponseBean.getMessage());
                }
                showLoading();
                mViewModel.wodeMatchs("" + strMatchId, "" + times,
                        "" + pageNum, "" + pageSize);

            }
        });
        refereeDisplayAdapter = new RefereeDisplayAdapter();
        mBinding.rvTime.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mBinding.rvTime.setAdapter(refereeDisplayAdapter);
        refereeDisplayAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    List<RefereeDisplayTimeBean> data1 = refereeDisplayAdapter.getData();
                    for (int i = 0; i < refereeDisplayAdapter.getData().size(); i++) {

                        RefereeDisplayTimeBean refereeDisplayTimeBean1 = data1.get(i);
                        refereeDisplayTimeBean1.setSelect(false);
                        data1.set(i, refereeDisplayTimeBean1);
                    }
                    data1.get(position).setSelect(true);
                    refereeDisplayAdapter.setNewData(data1);
                    times = data1.get(position).getUpTime();
                    haveMore = true;
                    pageNum = 1;
                    pageSize = 8;
                    showLoading();
                    mViewModel.wodeMatchs("" + strMatchId, "" + times,
                            "" + pageNum, "" + pageSize);
                }

            }
        });
        referenceAllMatchAdapter = new ReferenceMyMatchAdapter();
        mBinding.rvRefereeAllMatch.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvRefereeAllMatch.setAdapter(referenceAllMatchAdapter);

        mSixDiD = new MyDividerItemDecoration(MyGameActivity.this, 5,
                getResources().getColor(R.color.white));

        referenceAllMatchAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    switch (view.getId()) {
                        case R.id.ll_score:
                            long id1 = referenceAllMatchAdapter.getData().get(position).getId();

                            String itemType = referenceAllMatchAdapter.getData().get(position).getItemType();
                            if (itemType.equals("1")) {
                                Intent intent = new Intent();
                                intent.setClass(MyGameActivity.this, TeamResultsScoreOfficialActivity.class);
                                intent.putExtra("ids", "" + id1);
                                startActivity(intent);
                            } else if (itemType.equals("2")) {
                                Intent intent2 = new Intent();
                                intent2.setClass(MyGameActivity.this, SinglesDetailScoreOfficialActivity.class);
                                intent2.putExtra("ids", "" + id1);
                                startActivity(intent2);
                            } else {
                                Intent intent3 = new Intent();
                                intent3.setClass(MyGameActivity.this, DoubleDetailScoreOfficialActivity.class);
                                intent3.putExtra("ids", "" + id1);
                                startActivity(intent3);
                            }

                            break;
                        case R.id.ll_can_live:
                            //掉接口  判断是否能够直播
                            long id = referenceAllMatchAdapter.getData().get(position).getId();
                            matchArrangeId = "" + id;
                            hitType = Integer.parseInt(referenceAllMatchAdapter.getData().get(position).getItemType());
                            mViewModel.liveMatchStatus("" + id);
                            break;
                    }
                }
            }
        });

        mViewModel.liveMatchStatusLiveData.observe(this, new Observer<ResponseBean<LiveMatchStatusBean>>() {
            @Override
            public void onChanged(ResponseBean<LiveMatchStatusBean> liveMatchStatusBeanResponseBean) {
                if (liveMatchStatusBeanResponseBean.getErrorCode().equals("200")) {
                    LiveMatchStatusBean data = liveMatchStatusBeanResponseBean.getData();
                    if (data != null) {
                        int status = data.getStatus();
                        if (status == 0) {
                            Intent intent = new Intent();
                            intent.setClass(MyGameActivity.this, PublishRewardActivity.class);
                            intent.putExtra(PublishRewardActivity.IDS, "" + matchArrangeId);
                            startActivity(intent);
                        } else {
                            if (liveMatchStatusBeanResponseBean.getErrorCode().equals("200")) {
                                toast("已经下过单了");
                            } else {
                                toast("" + liveMatchStatusBeanResponseBean.getMessage());
                            }
                        }
                    }
                } else {
                    toast("" + liveMatchStatusBeanResponseBean.getMessage());
                }
            }
        });
        mViewModel.wodeMatchsLiveData.observe(this, new Observer<ResponseBean<List<MyGameListBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<MyGameListBean>> responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    tableNumArrangeBean  = responseBean.getData();
                    if (tableNumArrangeBean != null) {

                        if (tableNumArrangeBean != null && tableNumArrangeBean.size() > 0) {
                            if (pageNum == 1) {
                                list.clear();
                            }
                            if (tableNumArrangeBean != null) {
                                list.addAll(tableNumArrangeBean);
                                if (tableNumArrangeBean != null && tableNumArrangeBean.size() < pageSize) {
                                    haveMore = false;
                                    mBinding.swFresh.setEnableLoadmore(false);
                                } else {
                                    mBinding.swFresh.setEnableLoadmore(true);
                                }
                                referenceAllMatchAdapter.setNewData(list);
                            }
                            if (mBinding.swFresh != null) {
                                mBinding.swFresh.finishRefresh();
                                mBinding.swFresh.finishLoadmore();
                            }
                        } else {
                            if (pageNum == 1) {
                                list.clear();
                            }
                            mBinding.swFresh.finishRefresh();
                            mBinding.swFresh.finishLoadmore();

                            referenceAllMatchAdapter.setNewData(list);
                        }
                    } else {
                        toast("" + responseBean.getMessage());
                        if (pageNum == 1) {
                            list.clear();
                        }
                        referenceAllMatchAdapter.setNewData(list);
                    }
                } else {
                    toast("" + responseBean.getMessage());
                    if (pageNum == 1) {
                        list.clear();
                    }
                    referenceAllMatchAdapter.setNewData(list);
                }
                if (mBinding.swFresh != null) {
                    mBinding.swFresh.finishRefresh();
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
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        haveMore = true;
        pageNum = 1;
        showLoading();
        mViewModel.wodeMatchs("" + strMatchId, "" + times,
                "" + pageNum, "" + pageSize);


    }
}