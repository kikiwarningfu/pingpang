package heyong.intellectPinPang.live.activity;

import android.content.Context;
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
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
//import com.tendcloud.tenddata.TCAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.RefereeDisplayAdapter;
import heyong.intellectPinPang.adapter.game.ReferenceVisitorMatchAdapter;
import heyong.intellectPinPang.bean.competition.DateLimitBean;
import heyong.intellectPinPang.bean.competition.TableNumArrangeBean;
import heyong.intellectPinPang.bean.gsonbean.RefereeDisplayTimeBean;
import heyong.intellectPinPang.bean.live.LiveMatchStatusBean;
import heyong.intellectPinPang.databinding.ActivityReferenceVisitorMatchBinding;
import heyong.intellectPinPang.ui.club.activity.TeamResultsScoreOfficialActivity;
import heyong.intellectPinPang.ui.competition.activity.DoubleDetailScoreOfficialActivity;
import heyong.intellectPinPang.ui.competition.activity.SinglesDetailScoreOfficialActivity;
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel;
import heyong.intellectPinPang.utils.MyDateUtils;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;

/**
 * 游客的比赛列表   有直播的适配器的比赛列表的界面
 */
public class ReferenceVisitorMatchActivity extends BaseActivity<ActivityReferenceVisitorMatchBinding, HomePageViewModel> implements View.OnClickListener, OnRefreshListener {

    ReferenceVisitorMatchAdapter referenceAllMatchAdapter;
    public static final String TAG = ReferenceVisitorMatchActivity.class.getSimpleName();
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

    List<TableNumArrangeBean.ArrangesBean> list = new ArrayList<>();
    private int pageNum = 1;
    private int pageSize = 5;
    private boolean haveMore = true;
    Calendar beginCalendar;
    Calendar endCalendar;
    RefereeDisplayAdapter refereeDisplayAdapter;

    //    yyyy-MM-dd
    public static void goMatchActivity(Context context, String strTableNum, String strMatchId, String mIds, String code) {
        Intent intent = new Intent(context, ReferenceVisitorMatchActivity.class);
        intent.putExtra(TABLE_NUM, strTableNum);
        intent.putExtra(MATCH_ID, strMatchId);
        intent.putExtra(IDS, mIds);
        intent.putExtra(CODE, code);
        context.startActivity(intent);

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_reference_visitor_match;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        mRefreshAnimHeader = new MyRefreshAnimHeader(ReferenceVisitorMatchActivity.this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
//        mBinding.swFresh.setRefreshHeader(mRefreshAnimHeader);
        mBinding.swFresh.setOnRefreshListener(this);
        status = "";
        mIds = getIntent().getStringExtra(IDS);//编排id
        strMatchId = getIntent().getStringExtra(MATCH_ID);
        strTableNum = getIntent().getStringExtra(TABLE_NUM);
        mBinding.tvTaishu.setText("" + strTableNum + " 台");
        code = getIntent().getStringExtra(CODE);//  2
        mBinding.swFresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (haveMore) {
                    pageNum++;
                    mViewModel.tableMyNumArrange("" + strTableNum, "" + strMatchId,
                            "" + mIds, "" + times, "" + status, "" + code,
                            "" + pageNum, "" + pageSize);
                } else {
                    mBinding.swFresh.finishLoadmore();
                }
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

                    pageNum = 1;
                    haveMore = true;
                    showLoading();
                    mViewModel.tableMyNumArrange("" + strTableNum, "" + strMatchId, "" + mIds,
                            "" + times, ""+status, "" + code,
                            "" + pageNum, "" + pageSize);
                }
                }
        });

        mViewModel.dateLimit("" + strMatchId);
        referenceAllMatchAdapter = new ReferenceVisitorMatchAdapter();
        mBinding.rvRefereeAllMatch.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvRefereeAllMatch.setAdapter(referenceAllMatchAdapter);

        mSixDiD = new MyDividerItemDecoration(ReferenceVisitorMatchActivity.this, 5,
                getResources().getColor(R.color.white));

        referenceAllMatchAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    switch (view.getId()) {
                        case R.id.ll_score:
                            Map<String, Object> gamegrade = new HashMap<String, Object>();
                            gamegrade.put("ReferenceVisitorMatch_scsore", "点击成绩");//自定义参数：音乐类型，值：流行
                            MobclickAgent.onEventObject(ReferenceVisitorMatchActivity.this, "点击成绩", gamegrade);

//                            TCAgent.onEvent(ReferenceVisitorMatchActivity.this, "ReferenceVisitorMatch_scsore");

                            long id1 = referenceAllMatchAdapter.getData().get(position).getId();
                            String itemType = referenceAllMatchAdapter.getData().get(position).getItemType();
                            if (itemType.equals("1")) {
                                Intent intent = new Intent();
                                intent.setClass(ReferenceVisitorMatchActivity.this, TeamResultsScoreOfficialActivity.class);
                                intent.putExtra("ids", "" + id1);
                                startActivity(intent);
                            } else if (itemType.equals("2")) {
                                Intent intent2 = new Intent();
                                intent2.setClass(ReferenceVisitorMatchActivity.this, SinglesDetailScoreOfficialActivity.class);
                                intent2.putExtra("ids", "" + id1);
                                startActivity(intent2);
                            } else {
                                Intent intent3 = new Intent();
                                intent3.setClass(ReferenceVisitorMatchActivity.this, DoubleDetailScoreOfficialActivity.class);
                                intent3.putExtra("ids", "" + id1);
                                startActivity(intent3);
                            }

                            break;
                        case R.id.ll_can_live:
                            //掉接口  判断是否能够直播
//                        long id = referenceAllMatchAdapter.getData().get(position).getId();
//                        matchArrangeId = "" + id;
//                        hitType = Integer.parseInt(referenceAllMatchAdapter.getData().get(position).getItemType());
//                        mViewModel.liveMatchStatus("" + id);
                            toast("暂未开放");
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
                            intent.setClass(ReferenceVisitorMatchActivity.this, PublishRewardActivity.class);
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
        mViewModel.tableNumArrangeLiveData.observe(this, new Observer<ResponseBean<TableNumArrangeBean>>() {
            @Override
            public void onChanged(ResponseBean<TableNumArrangeBean> responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {

                    TableNumArrangeBean tableNumArrangeBean = responseBean.getData();
                    if (tableNumArrangeBean != null) {
                        int total = tableNumArrangeBean.getTotal();
                        int endCount = tableNumArrangeBean.getEndCount();
                        int notBeginCount = tableNumArrangeBean.getNotBeginCount();
                        mBinding.tvAll.setText("总场次"+"("+total+")");
                        mBinding.tvDaisai.setText("待赛"+"("+notBeginCount+")");
                        mBinding.tvEnd.setText("已结束"+"("+endCount+")");

                        String matchTitle = tableNumArrangeBean.getMatchTitle();
                        List<TableNumArrangeBean.ArrangesBean> arranges = tableNumArrangeBean.getArranges();
                        mBinding.tvCenter.setText("" + matchTitle);
                        if (arranges != null && arranges.size() > 0) {
                            if (pageNum == 1) {
                                list.clear();
                            }
                            if (arranges != null) {
                                list.addAll(arranges);
                                if (arranges != null && arranges.size() < pageSize) {
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
                        Log.e(TAG, "onChanged: " + new Gson().toJson(datesBetweenTwoDate));
                        Log.e(TAG, "onChanged: beginCalendar===" + beginCalendar
                                + "endCalendar===" + endCalendar);
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

                        pageNum = 1;
                        haveMore = true;
                        showLoading();
                        mViewModel.tableMyNumArrange("" + strTableNum, "" + strMatchId, "" + mIds,
                                "" + times, ""+status, "" + code,
                                "" + pageNum, "" + pageSize);
                    }
                } else {
                    toast("" + dateLimitBeanResponseBean.getMessage());
                }


            }
        });


    }

//    private void initData() {
//        Calendar c = Calendar.getInstance();//
//        int mYear = c.get(Calendar.YEAR); // 获取当前年份
//        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
//        int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
//        String currentYear = mYear + "-" + mMonth + "-" + mDay;
//        mBinding.tvTimes.setText("" + currentYear);
//        String week = MyDateUtils.week(currentYear);
//        mBinding.tvWeek.setText("" + week);
//        times = currentYear;
//        showLoading();
//        mViewModel.tableMyNumArrange("" + strTableNum, "" + strMatchId, "" + mIds, "" + times, "", "" + code,
//                "" + pageNum, "" + pageSize);
//
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_all:
                pageNum = 1;
                haveMore = true;
                mBinding.viewAll.setVisibility(View.VISIBLE);
                mBinding.tvAll.setTextColor(Color.parseColor("#4795ED"));
                mBinding.viewDaisai.setVisibility(View.INVISIBLE);
                mBinding.viewEnd.setVisibility(View.INVISIBLE);
                mBinding.tvDaisai.setTextColor(Color.parseColor("#666666"));
                mBinding.tvEnd.setTextColor(Color.parseColor("#666666"));
                status = "";
                showLoading();
                mViewModel.tableMyNumArrange("" + strTableNum, "" + strMatchId,
                        "" + mIds, "" + times, ""+status, "" + code, "" + pageNum, "" + pageSize);


                break;

            case R.id.ll_end:
                status = "2";
                pageNum = 1;
                haveMore = true;
                mBinding.viewAll.setVisibility(View.INVISIBLE);
                mBinding.tvAll.setTextColor(Color.parseColor("#666666"));
                mBinding.viewDaisai.setVisibility(View.INVISIBLE);
                mBinding.viewEnd.setVisibility(View.VISIBLE);
                mBinding.tvDaisai.setTextColor(Color.parseColor("#666666"));
                mBinding.tvEnd.setTextColor(Color.parseColor("#4795ED"));
                showLoading();
                mViewModel.tableMyNumArrange("" + strTableNum, "" + strMatchId,
                        "" + mIds, "" + times, "" + status, "" + code, "" + pageNum, "" + pageSize);


                break;
            case R.id.ll_daisai:
                status = "1";
                pageNum = 1;
                haveMore = true;
                mBinding.viewAll.setVisibility(View.INVISIBLE);
                mBinding.tvAll.setTextColor(Color.parseColor("#666666"));
                mBinding.viewDaisai.setVisibility(View.VISIBLE);
                mBinding.viewEnd.setVisibility(View.INVISIBLE);
                mBinding.tvDaisai.setTextColor(Color.parseColor("#4795ED"));
                mBinding.tvEnd.setTextColor(Color.parseColor("#666666"));
                showLoading();
                mViewModel.tableMyNumArrange("" + strTableNum, "" + strMatchId,
                        "" + mIds, "" + times, "" + status, "" + code, "" + pageNum, "" + pageSize);


                break;

            case R.id.iv_finish:

                finish();
                break;
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNum = 1;
        haveMore = true;
        showLoading();
        mViewModel.tableMyNumArrange("" + strTableNum, "" + strMatchId, "" + mIds, "" + times, ""+status, "" + code,
                "" + pageNum, "" + pageSize);


    }
}