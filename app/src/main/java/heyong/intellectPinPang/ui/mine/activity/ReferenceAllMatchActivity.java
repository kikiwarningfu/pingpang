package heyong.intellectPinPang.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.RefereeDisplayAdapter;
import heyong.intellectPinPang.adapter.game.ReferenceAllMatchAdapter;
import heyong.intellectPinPang.bean.competition.DateLimitBean;
import heyong.intellectPinPang.bean.competition.TableNumArrangeBean;
import heyong.intellectPinPang.bean.gsonbean.RefereeDisplayTimeBean;
import heyong.intellectPinPang.databinding.ActivityReferenceAllMatchBinding;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.intellectPinPang.ui.club.activity.TeamResultsOfficialActivity;
import heyong.intellectPinPang.ui.competition.activity.DoubleDetailOfficialActivity;
import heyong.intellectPinPang.ui.competition.activity.SinglesDetailOfficialActivity;
import heyong.intellectPinPang.ui.homepage.activity.GameDetailActivity;
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MyDateUtils;
import heyong.intellectPinPang.widget.MatchRadioView;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;

import static heyong.intellectPinPang.ui.club.activity.CreateClubActivity.getYearDay;

/**
 * 裁判长-所有比赛场次
 */
public class ReferenceAllMatchActivity extends BaseActivity<ActivityReferenceAllMatchBinding, HomePageViewModel> implements View.OnClickListener, OnRefreshListener {
    public static final String TAG = ReferenceAllMatchActivity.class.getSimpleName();

    MyRefreshAnimHeader mRefreshAnimHeader;
    ReferenceAllMatchAdapter referenceAllMatchAdapter;
    MyDividerItemDecoration mSixDiD;

    public static final String TABLE_NUM = "table_num";
    public static final String MATCH_ID = "match_id";
    public static final String IDS = "id";
    public static final String CODE = "code";
    public static final String STATUS = "status";
    public static final String TIME_CODE_DISPLAY = "time_code_display";
    private String strTableNum = "";
    private String strMatchId = "";
    private String code = "";
    private String mIds = "";
    private String status = "";
    private String times = "";
    private String matchTitle = "";
    private String myOwnMatchId = "";
    private String myOwnId = "";
    private String timeCodeDisplay = "";
    List<TableNumArrangeBean.ArrangesBean> list = new ArrayList<>();
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean haveMore = true;

    Calendar beginCalendar;
    Calendar endCalendar;
    RefereeDisplayAdapter refereeDisplayAdapter;


    public static void goMatchActivity(Context context, String strTableNum, String strMatchId, String mIds, String code, String status, String timeCodeDisplay) {
        Intent intent = new Intent(context, ReferenceAllMatchActivity.class);
        intent.putExtra(TABLE_NUM, strTableNum);
        intent.putExtra(MATCH_ID, strMatchId);
        intent.putExtra(IDS, mIds);
        intent.putExtra(CODE, code);
        intent.putExtra(STATUS, status);
        intent.putExtra(TIME_CODE_DISPLAY, timeCodeDisplay);
        context.startActivity(intent);

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_reference_all_match;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        Log.e(TAG, "okhttp  initView: ReferenceAllMatchActivity");

        mIds = getIntent().getStringExtra(IDS);
        strMatchId = getIntent().getStringExtra(MATCH_ID);
        strTableNum = getIntent().getStringExtra(TABLE_NUM);
        code = getIntent().getStringExtra(CODE);
        status = getIntent().getStringExtra(STATUS);
        timeCodeDisplay = getIntent().getStringExtra(TIME_CODE_DISPLAY);


        mBinding.tvTaishu.setText("" + strTableNum + " 台");

        mRefreshAnimHeader = new MyRefreshAnimHeader(ReferenceAllMatchActivity.this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
        mBinding.swFresh.setOnRefreshListener(this);
        mBinding.swFresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (haveMore) {
                    pageNum++;
                    mViewModel.tableNumArrangeChief("" + strTableNum, "" + strMatchId,
                            "" + mIds, "" + times, "" + status, "" + code, "" + pageNum, "" + pageSize);
                } else {
                    mBinding.swFresh.finishLoadmore();
                }
            }
        });


        mViewModel.dateLimit("" + strMatchId);

        referenceAllMatchAdapter = new ReferenceAllMatchAdapter();
        mBinding.rvRefereeAllMatch.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvRefereeAllMatch.setAdapter(referenceAllMatchAdapter);

        mSixDiD = new MyDividerItemDecoration(ReferenceAllMatchActivity.this, 5,
                getResources().getColor(R.color.white));
        referenceAllMatchAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    switch (view.getId()) {
                        case R.id.tv_game_end_status:
                        case R.id.ll_score:

                            TableNumArrangeBean.ArrangesBean arrangesBean = referenceAllMatchAdapter.getData().get(position);
                            int status = Integer.parseInt(arrangesBean.getStatus());//1待赛  2已结束
                            int itemType = Integer.parseInt(arrangesBean.getItemType());
                            //                        null未开始,1已结束，2进行中，3左边已确认成绩4右边已确认成绩6双方都确认成绩7团队左方填完对阵表8团队右边填完对阵表9，双方都填完10，虚位待赛
                            if (status == 0) {//未开始
                                myOwnMatchId = strMatchId;
                                myOwnId = "" + arrangesBean.getId();

                                mViewModel.queryTable("" + strMatchId);
                            } else if (status == 9 || status == 1 || status == 3 || status == 4 || status == 5 || status == 6
                                    || status == 2 || status == 7 || status == 8) {
                                /*跳转到  修改比分的界面  分跳转界面*/
                                switch (itemType) {
                                    case 1://团体

                                        Intent intent = new Intent();
                                        intent.setClass(ReferenceAllMatchActivity.this, TeamResultsOfficialActivity.class);
                                        intent.putExtra("ids", "" + arrangesBean.getId());
                                        startActivity(intent);
                                        break;
                                    case 2://单打

                                        Intent intent2 = new Intent();
                                        intent2.setClass(ReferenceAllMatchActivity.this, SinglesDetailOfficialActivity.class);
                                        intent2.putExtra("ids", "" + arrangesBean.getId());
                                        startActivity(intent2);
                                        break;
                                    case 3://双打 和混双
                                    case 4:
                                        Intent intent3 = new Intent();
                                        intent3.setClass(ReferenceAllMatchActivity.this, DoubleDetailOfficialActivity.class);
                                        intent3.putExtra("ids", "" + arrangesBean.getId());
                                        startActivity(intent3);

                                        break;
                                }

                            } else {//已结束{
                                toast("状态返回的啥" + status);
                            }

                            break;
                    }
                }

            }
        });
        mViewModel.queryTableLiveData.observe(this, new Observer<ResponseBean<List<String>>>() {
            @Override
            public void onChanged(ResponseBean<List<String>> listResponseBean) {
                if (listResponseBean.getErrorCode().equals("200")) {
                    if (listResponseBean.getData() != null && listResponseBean.getData().size() > 0) {
                        showPopWindow(listResponseBean.getData());
                    }
                } else {
                    toast("" + listResponseBean.getMessage());
                }

            }
        });
        mViewModel.tuningStationLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    toast("调台成功");
                    mViewModel.tableNumArrangeChief("" + strTableNum, "" + strMatchId, "" + mIds,
                            "" + times + " 00:00:00", "" + status, "" + code, "" + pageNum, "" + pageSize);
                } else {
                    toast("" + responseBean.getMessage());
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
                        List<RefereeDisplayTimeBean> refereeDisplayTimeBeanList = new ArrayList<>();
                        for (int i = 0; i < datesBetweenTwoDate.size(); i++) {
                            RefereeDisplayTimeBean refereeDisplayTimeBean = new RefereeDisplayTimeBean();
                            Date date = datesBetweenTwoDate.get(i);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String transformDate = simpleDateFormat.format(date);
                            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM月dd日");
                            String transformDate2 = simpleDateFormat2.format(date);
                            if (timeCodeDisplay != null && !TextUtils.isEmpty(timeCodeDisplay)) {
                                if (transformDate2.equals(timeCodeDisplay)) {
                                    times = transformDate;
                                    refereeDisplayTimeBean.setSelect(true);
                                } else {
                                    refereeDisplayTimeBean.setSelect(false);
                                }
                            } else {
                                if (i == 0) {
                                    times = transformDate;

                                    refereeDisplayTimeBean.setSelect(true);
                                } else {
                                    refereeDisplayTimeBean.setSelect(false);
                                }
                            }
                            refereeDisplayTimeBean.setTime(transformDate2);
                            refereeDisplayTimeBean.setUpTime(transformDate);
                            refereeDisplayTimeBeanList.add(refereeDisplayTimeBean);
                        }
                        refereeDisplayAdapter.setNewData(refereeDisplayTimeBeanList);

                        pageNum = 1;
                        haveMore = true;
                        showLoading();
                        mViewModel.tableNumArrangeChief("" + strTableNum, "" + strMatchId,
                                "" + mIds, "" + times, "" + status, "" + code,
                                "" + pageNum, "" + pageSize);
                    }
                } else {
                    toast("" + dateLimitBeanResponseBean.getMessage());
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
                    mViewModel.tableNumArrangeChief("" + strTableNum, "" + strMatchId,
                            "" + mIds, "" + times, "" + status, "" + code, "" + pageNum, "" + pageSize);
                }
            }
        });

        mViewModel.tableNumArrangeChiefLiveData.observe(this, new Observer<ResponseBean<TableNumArrangeBean>>() {
            @Override
            public void onChanged(ResponseBean<TableNumArrangeBean> responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    TableNumArrangeBean tableNumArrangeBean = responseBean.getData();
                    if (tableNumArrangeBean != null) {

                        matchTitle = tableNumArrangeBean.getMatchTitle();
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

    }

    private void showTime(Calendar beginDate, Calendar endDate) {
        TimePickerView pvTime = new TimePickerView.Builder(ReferenceAllMatchActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date dateMax, View v) {
                Log.e(TAG, "onTimeSelect: " + dateMax);
                String time = getYearDay(dateMax);
                times = time;
                //                mBinding.tvTimes.setText("" + time);
                String week = MyDateUtils.week(time);
                //                mBinding.tvWeek.setText("" + week);
                haveMore = true;
                pageNum = 1;
                mViewModel.tableNumArrangeChief("" + strTableNum, "" + strMatchId, "" + mIds,
                        "" + times + " 00:00:00", "" + status, "" + code, "" + pageNum, "" + pageSize);


            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(20)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                //                        .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                //                        .setRangDate()
                .isCyclic(true)//是否循环滚动
                .setRangDate(beginDate, endDate)
                .setTextColorCenter(Color.parseColor("#000000"))//设置选中项的颜色
                .setTextColorOut(Color.parseColor("#666666"))
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setLoop(false)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                //                        .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Intent intent = new Intent(ReferenceAllMatchActivity.this, GameDetailActivity.class);
                intent.putExtra(GameDetailActivity.MATCH_ID, "" + strMatchId);
                intent.putExtra(GameDetailActivity.MATCH_TITLE, "" + matchTitle);
                startActivity(intent);
                break;
            case R.id.iv_finish:

                finish();

                break;
            //            case R.id.ll_all:
            //                if (AntiShakeUtils.isInvalidClick(v))
            //                    return;
            //                haveMore = true;
            //                pageNum = 1;
            //                showLoading();
            //                mBinding.viewAll.setVisibility(View.VISIBLE);
            //                mBinding.tvAll.setTextColor(Color.parseColor("#4795ED"));
            //                mBinding.viewDaisai.setVisibility(View.INVISIBLE);
            //                mBinding.viewEnd.setVisibility(View.INVISIBLE);
            //                mBinding.tvDaisai.setTextColor(Color.parseColor("#666666"));
            //                mBinding.tvEnd.setTextColor(Color.parseColor("#666666"));
            //                status = "";
            //                mViewModel.tableNumArrangeChief("" + strTableNum, "" + strMatchId,
            //                        "" + mIds, "" + times + " 00:00:00", "" + status, "" + code, "" + pageNum, "" + pageSize);
            //
            //                break;

            //            case R.id.ll_end:
            //                if (AntiShakeUtils.isInvalidClick(v))
            //                    return;
            //                haveMore = true;
            //                pageNum = 1;
            //                showLoading();
            //                mBinding.viewAll.setVisibility(View.INVISIBLE);
            //                mBinding.tvAll.setTextColor(Color.parseColor("#666666"));
            //                mBinding.viewDaisai.setVisibility(View.INVISIBLE);
            //                mBinding.viewEnd.setVisibility(View.VISIBLE);
            //                mBinding.tvDaisai.setTextColor(Color.parseColor("#666666"));
            //                mBinding.tvEnd.setTextColor(Color.parseColor("#4795ED"));
            //                status = "2";
            //                mViewModel.tableNumArrangeChief("" + strTableNum, "" + strMatchId, "" + mIds,
            //                        "" + times + " 00:00:00", "" + status, "" + code, "" + pageNum, "" + pageSize);
            //
            //                break;
            //            case R.id.ll_daisai:
            //                if (AntiShakeUtils.isInvalidClick(v))
            //                    return;
            //                status = "1";
            //                haveMore = true;
            //                pageNum = 1;
            //                showLoading();
            //                mBinding.viewAll.setVisibility(View.INVISIBLE);
            //                mBinding.tvAll.setTextColor(Color.parseColor("#666666"));
            //                mBinding.viewDaisai.setVisibility(View.VISIBLE);
            //                mBinding.viewEnd.setVisibility(View.INVISIBLE);
            //                mBinding.tvDaisai.setTextColor(Color.parseColor("#4795ED"));
            //                mBinding.tvEnd.setTextColor(Color.parseColor("#666666"));
            //                mViewModel.tableNumArrangeChief("" + strTableNum, "" + strMatchId,
            //                        "" + mIds, "" + times + " 00:00:00", "" + status, "" + code, "" + pageNum, "" + pageSize);
            //
            //
            //                break;
            //            case R.id.ll_time:
            //                showTime(beginCalendar, endCalendar);
            //                /*调用接口*/
            ////                mViewModel.dateLimit("" + strMatchId);
            //                break;
        }
    }

    private void showPopWindow(List<String> data) {

        MatchRadioView pvTime = new MatchRadioView.Builder(strTableNum, data, mSixDiD, wdith, ReferenceAllMatchActivity.this, new MatchRadioView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(String time, View v, String item) {
                Log.e(TAG, "onTimeSelect: " + item);
                //                String time = getTimeSecond(dateMax);
                Log.e(TAG, "onTimeSelect: " + time);
                if (TextUtils.isEmpty(item) || item == null) {
                    toast("请选择台号");
                } else {
                    mViewModel.tuningStation("" + myOwnMatchId, times + " "
                            + time + ":00", "" + myOwnId, "" + item);

                }


            }
        })
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(13)//滚轮文字大小
                .setTitleSize(13)//标题文字大小
                //                        .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTextColorCenter(Color.parseColor("#333333"))//设置选中项的颜色
                .setTextColorOut(Color.parseColor("#666666"))
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                //                        .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        haveMore = true;
        pageNum = 1;
        mViewModel.tableNumArrangeChief("" + strTableNum, "" + strMatchId,
                "" + mIds, "" + times, "" + status, "" + code, "" + pageNum, "" + pageSize);
        //        initData();
        //        mBinding.viewAll.setVisibility(View.VISIBLE);
        //        mBinding.tvAll.setTextColor(Color.parseColor("#4795ED"));
        //        mBinding.viewDaisai.setVisibility(View.INVISIBLE);
        //        mBinding.viewEnd.setVisibility(View.INVISIBLE);
        //        mBinding.tvDaisai.setTextColor(Color.parseColor("#666666"));
        //        mBinding.tvEnd.setTextColor(Color.parseColor("#666666"));
    }
}