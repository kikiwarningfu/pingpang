package heyong.intellectPinPang.ui.competition.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.elvishew.xlog.XLog;
import com.google.gson.Gson;
import com.xq.fasterdialog.dialog.CustomDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.AddRefereeAdapter;
import heyong.intellectPinPang.adapter.game.ChiefJudgeConsoleAdapter;
import heyong.intellectPinPang.adapter.game.TimeListTimeSlotAdapter;
import heyong.intellectPinPang.adapter.game.TimeListTimeSortAdapter;
import heyong.intellectPinPang.bean.competition.RefereeChooseDataBean;
import heyong.intellectPinPang.bean.competition.RefereeConsoleBean;
import heyong.intellectPinPang.bean.competition.RefreerConsoleSortBean;
import heyong.intellectPinPang.databinding.ActivityChiefJudgeConsoleBinding;
import heyong.intellectPinPang.ui.club.activity.TeamResultsOfficialActivity;
import heyong.intellectPinPang.ui.competition.CompetitionViewModel;
import heyong.intellectPinPang.ui.homepage.activity.GameDetailActivity;
import heyong.intellectPinPang.ui.mine.activity.ReferenceAllMatchActivity;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MyDateUtils;
import heyong.intellectPinPang.widget.HorizontalItemDecoration;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;
import heyong.intellectPinPang.widget.popupwindow.CommonPopupWindow;

/**
 * 裁判长控制台 41.4
 */
public class ChiefJudgeConsoleActivity extends BaseActivity<ActivityChiefJudgeConsoleBinding, CompetitionViewModel> implements View.OnClickListener {
    TimeListTimeSortAdapter timeListTimeSortAdapter;
    TimeListTimeSlotAdapter timeListTimeSlotAdapter;

    ChiefJudgeConsoleAdapter chiefJudgeConsoleAdapter;
    MyDividerItemDecoration mSixDiD;
    private CommonPopupWindow commonPopupWindow;
    public static final String MATCHID = "";
    private String matchId = "";

    private String beginTime = "";
    private String projectType = "";
    private String timeCode = "";
    private String year = "";
    private int width;
    private String title = "";
    private String time = "";

    private String showTime = "";
    private String timeCodeDisplay = "";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_chief_judge_console;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        matchId = getIntent().getStringExtra(MATCHID);
        mViewModel.refereerConsoleSort(matchId);
        mBinding.setListener(this);

        width = getWindowManager().getDefaultDisplay().getWidth();
        mViewModel.refereerConsoleLiveData.observe(this, new Observer<ResponseBean<List<RefereeConsoleBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<RefereeConsoleBean>> listResponseBean) {
                dismissLoading();

                if (listResponseBean.getErrorCode().equals("200")) {
                    List<RefereeConsoleBean> data = listResponseBean.getData();
                    if (data != null && data.size() > 0) {
                        chiefJudgeConsoleAdapter.setNewData(data);
                    } else {
                        chiefJudgeConsoleAdapter.setNewData(new ArrayList<>());
                    }
                } else {
                    toast("" + listResponseBean.getMessage());
                }
            }
        });
        mViewModel.refereeChooseDataLiveData.observe(this, new Observer<ResponseBean<List<RefereeChooseDataBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<RefereeChooseDataBean>> listResponseBean) {

                if (listResponseBean != null && listResponseBean.getData().size() > 0) {
                    List<RefereeChooseDataBean> data = listResponseBean.getData();
                    showAddRefree(data);
                } else {
                    if (!listResponseBean.getErrorCode().equals("200")) {
                        toast("" + listResponseBean.getMessage());
                    } else {
                        toast("暂无裁判员");
                    }
                }
            }
        });
        mBinding.mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果是开
                    //关   那么要打开
                    mBinding.mSwitch.setChecked(true);
                    Log.e(TAG, "onCheckedChanged: 开启比赛");
                    mBinding.tvStatusTextShow.setText("开始");
                    showLoading();
                    mViewModel.startMatch("" + matchId, "5");
                } else {
                    mBinding.mSwitch.setChecked(false);
                    Log.e(TAG, "onCheckedChanged: 关闭比赛");
                    mBinding.tvStatusTextShow.setText("暂停");
                    showLoading();
                    mViewModel.startMatch("" + matchId, "7");

                }
            }
        });
        mViewModel.refereerConsoleSortLiveData.observe(this, new Observer<ResponseBean<RefreerConsoleSortBean>>() {
            @Override
            public void onChanged(ResponseBean<RefreerConsoleSortBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    RefreerConsoleSortBean data = responseBean.getData();
                    if (data != null) {
                        year = data.getYear() + "年";
                        String status = data.getStatus();
                        if (TextUtils.isEmpty(status)) {
                            //为空展示大的
                            mBinding.tvStartGame.setVisibility(View.VISIBLE);
                            mBinding.rlLittileGameStatus.setVisibility(View.GONE);
                        } else {
                            int mStatus = Integer.parseInt(status);
                            if (mStatus == 6) {
                                mBinding.tvStartGame.setVisibility(View.GONE);
                                mBinding.rlLittileGameStatus.setVisibility(View.GONE);
                            } else if (mStatus == 5) {
                                //显示开
                                mBinding.tvStartGame.setVisibility(View.GONE);
                                mBinding.rlLittileGameStatus.setVisibility(View.VISIBLE);
                                mBinding.mSwitch.setChecked(true);
                                mBinding.tvStatusTextShow.setText("开始");
                            } else if (mStatus == 7) {
                                //显示关
                                mBinding.tvStartGame.setVisibility(View.GONE);
                                mBinding.rlLittileGameStatus.setVisibility(View.VISIBLE);
                                mBinding.mSwitch.setChecked(false);
                                mBinding.tvStatusTextShow.setText("暂停");
                            } else {
                                mBinding.tvStartGame.setVisibility(View.GONE);
                                mBinding.rlLittileGameStatus.setVisibility(View.GONE);
                            }
                        }
                        List<RefreerConsoleSortBean.TimeSortBean> timeSort = data.getTimeSort();//时间
                        if (timeSort != null && timeSort.size() > 0) {
                            for (int i = 0; i < timeSort.size(); i++) {
                                if (i == 0) {
                                    timeSort.get(0).setSelect(true);
                                    beginTime = timeSort.get(0).getTimeName();
                                    showTime = timeSort.get(0).getTime();


                                    title = year + beginTime + "比赛";
                                    time = year + beginTime;
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                                    Calendar calendar = Calendar.getInstance();
                                    try {
                                        calendar.setTime(sdf.parse(time));
                                        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                                        String dayWeek = dayNames[dayOfWeek];
//                                        mBinding.tvWeek.setText("" + dayWeek);
//                                        mBinding.tvMatchTitle.setText("" + title);


                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
//                                    mBinding.tvMatchTitle.setText("" + title);
                                } else {
                                    timeSort.get(i).setSelect(false);
                                }
                            }
                            timeListTimeSortAdapter.setNewData(timeSort);
                        }
                        List<RefreerConsoleSortBean.TimeSlotBean> timeSlot = data.getTimeSlot();//时间段 code
                        if (timeSlot != null && timeSlot.size() > 0) {
                            for (int i = 0; i < timeSlot.size(); i++) {
                                if (i == 0) {
                                    timeSlot.get(0).setSelect(true);
                                    timeCode = timeSlot.get(0).getCode();
                                } else {
                                    timeSlot.get(i).setSelect(false);
                                }
                            }
                            timeListTimeSlotAdapter.setNewData(timeSlot);
                        }

                        showLoading();
                        mViewModel.refereerConsole(matchId, "" + showTime, "" + timeCode);


                    } else {
                        toast("" + responseBean.getMessage());
                    }
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        timeListTimeSortAdapter = new TimeListTimeSortAdapter(wdith, ChiefJudgeConsoleActivity.this);
        mBinding.rvTimeList.addItemDecoration(new HorizontalItemDecoration(40, this));//10表示10dp
        mBinding.rvTimeList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mBinding.rvTimeList.setAdapter(timeListTimeSortAdapter);
        timeListTimeSortAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    List<RefreerConsoleSortBean.TimeSortBean> data = timeListTimeSortAdapter.getData();
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setSelect(false);
                    }
                    data.get(position).setSelect(true);
                    timeListTimeSortAdapter.setNewData(data);
                    beginTime = data.get(position).getTimeName();
                    showTime = data.get(position).getTime();

                    String matchTime = "";
                    try {
                        matchTime = MyDateUtils.getMatchTime("yyyy-MM-dd HH:mm:ss", "MM月dd日",showTime+" "+"00:00:00" );
                    } catch (Exception e) {
                        matchTime = data.get(position).getTimeName();
                    }

                    timeCodeDisplay = matchTime;
                    XLog.e("okhttp timeCodeDisplay==="+timeCodeDisplay);


                    Log.e(TAG, "okhttp  2    onItemClick: ");
                    title = year + beginTime + "比赛";
                    time = year + beginTime;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                    Calendar calendar = Calendar.getInstance();
                    try {
                        calendar.setTime(sdf.parse(time));
                        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                        String dayWeek = dayNames[dayOfWeek];
//                        mBinding.tvWeek.setText("" + dayWeek);
//                        mBinding.tvMatchTitle.setText("" + title);


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, "okhttp  1    onItemClick: ");
                    showLoading();
                    mViewModel.refereerConsole(matchId, "" + showTime, "" + timeCode);
                }
            }
        });
        timeListTimeSlotAdapter = new TimeListTimeSlotAdapter(wdith, ChiefJudgeConsoleActivity.this);
        mBinding.rvAfternoonAndTime.addItemDecoration(new HorizontalItemDecoration(40, this));//10表示10dp
        mBinding.rvAfternoonAndTime.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mBinding.rvAfternoonAndTime.setAdapter(timeListTimeSlotAdapter);
        timeListTimeSlotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    List<RefreerConsoleSortBean.TimeSlotBean> data = timeListTimeSlotAdapter.getData();
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setSelect(false);
                    }
                    data.get(position).setSelect(true);
                    timeListTimeSlotAdapter.setNewData(data);

                    timeCode = data.get(position).getCode();

                    showLoading();
                    mViewModel.refereerConsole(matchId, "" + showTime, "" + timeCode);
                }
            }
        });


        mSixDiD = new MyDividerItemDecoration(ChiefJudgeConsoleActivity.this, 5,
                getResources().getColor(R.color.white));
        //获奖成绩
        chiefJudgeConsoleAdapter = new ChiefJudgeConsoleAdapter();
        mBinding.rvAwardWinningScore.setAdapter(chiefJudgeConsoleAdapter);
        mBinding.rvAwardWinningScore.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        chiefJudgeConsoleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    String tableNum1 = chiefJudgeConsoleAdapter.getData().get(position).getTableNum();
                    String refereeImg = chiefJudgeConsoleAdapter.getData().get(position).getRefereeImg();
                    String refereeId = chiefJudgeConsoleAdapter.getData().get(position).getRefereeId();
                    String refereeName = chiefJudgeConsoleAdapter.getData().get(position).getRefereeName();
                    RefereeConsoleBean refereeConsoleBean = chiefJudgeConsoleAdapter.getData().get(position);

                    switch (view.getId()) {

                        case R.id.tv_wancheng://结束
                            if (!tableNum1.equals("合计")) {
                                ReferenceAllMatchActivity.goMatchActivity(ChiefJudgeConsoleActivity.this, "" + tableNum1,
                                        "" + matchId, "", "2", "2",timeCodeDisplay);
                            }

                            break;
                        case R.id.tv_daisai://待赛
                            if (!tableNum1.equals("合计")) {
                                ReferenceAllMatchActivity.goMatchActivity(ChiefJudgeConsoleActivity.this, "" + tableNum1,
                                        "" + matchId, "", "2", "1",timeCodeDisplay);
                            }
                            break;

                        case R.id.tv_bisai_ing://进行中
                            RefereeConsoleBean refereeConsoleBean1 = chiefJudgeConsoleAdapter.getData().get(position);
                            RefereeConsoleBean.InHandDtoBean inHandDto = refereeConsoleBean1.getInHandDto();
                            long id = inHandDto.getId();
                            String type = inHandDto.getItemType();
                            //1团体 2  单打 3 4 混双
                            if (type.equals("1")) {

                                Intent intent = new Intent();
                                intent.setClass(ChiefJudgeConsoleActivity.this, TeamResultsOfficialActivity.class);
                                intent.putExtra("ids", "" + id);
                                startActivity(intent);

                            } else if (type.equals("2")) {
                                Intent intent2 = new Intent();
                                intent2.setClass(ChiefJudgeConsoleActivity.this, SinglesDetailOfficialActivity.class);
                                intent2.putExtra("ids", "" + id);
                                startActivity(intent2);
                            } else {
                                Intent intent3 = new Intent();
                                intent3.setClass(ChiefJudgeConsoleActivity.this, DoubleDetailOfficialActivity.class);
                                intent3.putExtra("ids", "" + id);
                                startActivity(intent3);
                            }


                            break;


                        case R.id.iv_add://添加
                            ownTableNum = tableNum1;
                            mViewModel.refereeChooseData("" + matchId, "" + tableNum1);

                            break;
                        case R.id.tv_right_name://踢出

                            showDelRefree(refereeConsoleBean);
                            break;
                    }
                }
            }
        });
        Log.e(TAG, "okhttp  4    onItemClick: ");
        Log.e(TAG, "okhttpinitView:  4  beginTime=== " + beginTime);


        mViewModel.deleteRefereeLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    /*重新请求数据*/
                    Log.e(TAG, "okhttp  5    onItemClick: ");
                    showLoading();
                    mViewModel.refereerConsole(matchId, "" + showTime, "" + timeCode);
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        /*更新裁判员*/
        mViewModel.updateRefereeLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    /*重新请求数据*/
                    Log.e(TAG, "okhttp  6    onItemClick: ");
                    showLoading();
                    mViewModel.refereerConsole(matchId, "" + showTime, "" + timeCode);
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        /*开始比赛*/
        mViewModel.startMatchLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    /*重新请求数据*/
                    Log.e(TAG, "okhttp  7    onItemClick: ");
//                    tv_start_game
                    mBinding.tvStartGame.setVisibility(View.GONE);
                    showLoading();
                    mViewModel.refereerConsole(matchId, "" + showTime, "" + timeCode);
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });


    }

    String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    /*展示删除裁判员的window*/
    private void showDelRefree(RefereeConsoleBean refereeConsoleBean) {
        CustomDialog customDialogConfirm = new CustomDialog(ChiefJudgeConsoleActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_del_refree);
        customDialogConfirm.show();
        View customView = customDialogConfirm.getCustomView();
        CircleImageView ivLogo = customView.findViewById(R.id.iv_logo);
        TextView tvTableNum = customView.findViewById(R.id.tv_table_num);
        ImageLoader.loadImage(ivLogo, refereeConsoleBean.getRefereeImg(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
        tvTableNum.setText("" + refereeConsoleBean.getTableNum() + "台" + "裁判员：" + refereeConsoleBean.getRefereeName());
        TextView tvcancel = customView.findViewById(R.id.tv_cancel);
        TextView tvSubmit = customView.findViewById(R.id.tv_submit);
        tvcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                customDialogConfirm.dismiss();
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                /*interface*/
                mViewModel.deleteReferee("" + matchId, "" + refereeConsoleBean.getTableNum());
                customDialogConfirm.dismiss();
            }
        });

    }

    private String ownMatchId = "";
    private String ownTableNum = "";
    private String ownUserId = "";

    public void showAddRefree(List<RefereeChooseDataBean> data) {

        commonPopupWindow = new CommonPopupWindow.Builder(ChiefJudgeConsoleActivity.this)
                .setView(R.layout.pop_add_referee)
                .setBackGroundLevel(0.5f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        if (AntiShakeUtils.isInvalidClick(view))
                            return;
                        TextView tvSure = view.findViewById(R.id.tv_sure);
                        TextView tvCancel = view.findViewById(R.id.tv_cancel);
                        tvSure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (AntiShakeUtils.isInvalidClick(v))
                                    return;
                                mViewModel.updateReferee("" + ownMatchId, "" + ownTableNum, "" + ownUserId);
                                commonPopupWindow.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (AntiShakeUtils.isInvalidClick(v))
                                    return;
                                commonPopupWindow.dismiss();
                            }
                        });
                        RecyclerView rvAddPeople = view.findViewById(R.id.rv_add_people);
                        rvAddPeople.setLayoutManager(new GridLayoutManager(ChiefJudgeConsoleActivity.this, 4));
                        AddRefereeAdapter addRefereeAdapter = new AddRefereeAdapter(width, ChiefJudgeConsoleActivity.this);
                        rvAddPeople.setAdapter(addRefereeAdapter);
                        rvAddPeople.removeItemDecoration(mSixDiD);
                        rvAddPeople.addItemDecoration(mSixDiD);

                        addRefereeAdapter.setNewData(data);
                        addRefereeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                            @Override
                            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                boolean fastClick = isFastClick();
                                if (!fastClick) {
                                    switch (view.getId()) {
                                        case R.id.tv_text:
                                            RefereeChooseDataBean refereeChooseDataBean = addRefereeAdapter.getData().get(position);
                                            ownMatchId = "" + refereeChooseDataBean.getMatchId();
//                                        ownTableNum = "" + refereeChooseDataBean.getTableNum();
                                            ownUserId = "" + refereeChooseDataBean.getUserId();
                                            List<RefereeChooseDataBean> data1 = addRefereeAdapter.getData();
                                            for (int i = 0; i < data1.size(); i++) {
                                                data1.get(i).setSelect(false);
                                            }
                                            data1.get(position).setSelect(true);
                                            addRefereeAdapter.setNewData(data1);
//                                        mViewModel.updateReferee("" + matchId, "" + tableNum, "" + userId);
                                            break;
                                    }
                                }
                            }
                        });
                    }
                })
                .setOutsideTouchable(false)//设置外层不可点击
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                .create();
        View view2 = View.inflate(ChiefJudgeConsoleActivity.this, R.layout.activity_chief_judge_console, null);
        commonPopupWindow.showAtLocation(view2, Gravity.CENTER, 0, 0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Intent intent = new Intent(ChiefJudgeConsoleActivity.this, GameDetailActivity.class);
                intent.putExtra(GameDetailActivity.MATCH_ID, "" + matchId);
                intent.putExtra(GameDetailActivity.MATCH_TITLE, "" + title);
                startActivity(intent);

                break;
            case R.id.tv_start_game://开始比赛
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mViewModel.startMatch("" + matchId, "5");

                break;
            case R.id.iv_finish:

                finish();
                break;
        }
    }
}