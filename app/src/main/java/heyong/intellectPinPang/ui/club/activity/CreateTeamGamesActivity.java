package heyong.intellectPinPang.ui.club.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.TimeSelectCompleteView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.BsResponityAdapter;
import heyong.intellectPinPang.adapter.game.CreateTeamBlueAdapter;
import heyong.intellectPinPang.adapter.game.CreateTeamRedAdapter;
import heyong.intellectPinPang.bean.competition.CreateXlClubContestInfoBean;
import heyong.intellectPinPang.databinding.ActivityCreateTeamGamesBinding;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MyDateUtils;

import static heyong.intellectPinPang.ui.club.activity.CreateClubActivity.getTime;

/**
 * 创建队内比赛  1是单打 2是团队   人数有一个人的时候可以 空的时候不可以
 */
public class CreateTeamGamesActivity extends BaseActivity<ActivityCreateTeamGamesBinding, ClubViewModel> implements View.OnClickListener {
    BsResponityAdapter adapter;
    public static final String CLUB_ID = "club_id";
    public static CreateTeamGamesActivity instance = null;
    public static final String TAG = CreateTeamGamesActivity.class.getSimpleName();
    List<CreateXlClubContestInfoBean.XlClubContestTeamsBean> responityListBeans;
    private String strClubId = "";

    private String strBeginTime = "";
    private String strEndTime = "";
    private String saishiTime = "";
    private int soloType = -1;//团体类别   1是单打 2 是团体
    private int gameType = -1;//1 是三局两胜  2是五局三胜  3 是七局四胜
    CreateXlClubContestInfoBean createXlClubContestInfoBean;
    CreateTeamRedAdapter createTeamRedAdapter;
    CreateTeamBlueAdapter createTeamBlueAdapter;
    List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> dataList;
    private String matchMethod = "";

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_saishi_name, R.id.et_qiutai_num};
        return ids;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_create_team_games;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        instance = this;
        mBinding.setListener(this);
        strClubId = getIntent().getStringExtra(CLUB_ID);
        createXlClubContestInfoBean = new CreateXlClubContestInfoBean();
        responityListBeans = new ArrayList<>();
        responityListBeans.add(new CreateXlClubContestInfoBean.XlClubContestTeamsBean());
        createXlClubContestInfoBean.setXlClubContestTeams(responityListBeans);
        initRecyclerView(createXlClubContestInfoBean);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        createTeamRedAdapter = new CreateTeamRedAdapter(width, CreateTeamGamesActivity.this);
        mBinding.rvTeamNumberRed.setLayoutManager(new GridLayoutManager(CreateTeamGamesActivity.this, 2));
        mBinding.rvTeamNumberRed.setAdapter(createTeamRedAdapter);
        createTeamRedAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_red_close:
                        boolean fastClick = isFastClick();
                        if (!fastClick) {
                            List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> data = createTeamRedAdapter.getData();
                            data.remove(position);
                            createTeamRedAdapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
        });
        createTeamBlueAdapter = new CreateTeamBlueAdapter(width, CreateTeamGamesActivity.this);
        createTeamBlueAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_close_blue:
                        boolean fastClick = isFastClick();
                        if (!fastClick) {
                            List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> data = createTeamBlueAdapter.getData();
                            data.remove(position);
                            createTeamBlueAdapter.notifyDataSetChanged();
                        }

                        break;
                }
            }
        });
        mBinding.rvTeamNumberBlue.setLayoutManager(new GridLayoutManager(CreateTeamGamesActivity.this, 2));
        mBinding.rvTeamNumberBlue.setAdapter(createTeamBlueAdapter);
        mBinding.llTwoWinner.setEnabled(false);
        mBinding.llFourWinner.setEnabled(false);
        mBinding.llThreeWinner.setEnabled(false);

        mBinding.tvTwoWinners.setTextColor(Color.parseColor("#FFCCCCCC"));
        mBinding.tvThreeWinner.setTextColor(Color.parseColor("#FFCCCCCC"));
        mBinding.tvFourWinner.setTextColor(Color.parseColor("#FFCCCCCC"));

        mBinding.cbTwoWiner.setEnabled(false);
        mBinding.cbThreeWinner.setEnabled(false);
        mBinding.cbFourWinner.setEnabled(false);

        mBinding.llSingle.setVisibility(View.GONE);
        mBinding.llTeam.setVisibility(View.GONE);
        mBinding.rlAdd.setVisibility(View.INVISIBLE);

        mViewModel.createXlClubContestInfoLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();

                if (responseBean.getErrorCode().equals("200")) {
                    BigDecimal bigDecimal = new BigDecimal((String) responseBean.getData());
                    long ids = bigDecimal.longValue();
                    //                跳转到积分的那个界面
                    String contestTitle = createXlClubContestInfoBean.getContestTitle();

                    Intent intent = new Intent(CreateTeamGamesActivity.this, GameUnderWayActivity.class);
                    intent.putExtra(GameUnderWayActivity.CONTEST_ID, "" + ids);
                    intent.putExtra(GameUnderWayActivity.MATCH_TYITLE, "" + contestTitle);
                    startActivity(intent);
                    finish();

                } else {
                    toast("" + responseBean.getMessage());
                }

            }
        });

    }

    private void initRecyclerView(CreateXlClubContestInfoBean createXlClubContestInfoBean) {
        adapter = new BsResponityAdapter(CreateTeamGamesActivity.this);
        mBinding.mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.mRecyclerView.setAdapter(adapter);

        adapter.setNewData(createXlClubContestInfoBean.getXlClubContestTeams());
        adapter.setOnItemChildListener(new BsResponityAdapter.OnItemChildClickListener() {
            @Override
            public void onSelectPeopleGroup(int position, CreateXlClubContestInfoBean.XlClubContestTeamsBean item) {
                //选择参赛人员  点击以后传递数据过去
                List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> contestTeamMembers = new ArrayList<>();
//                List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> contestTeamMembers = item.getContestTeamMembers();
                Intent intent = new Intent(CreateTeamGamesActivity.this, SelectTeamNumberSingleActivity.class);
                List<CreateXlClubContestInfoBean.XlClubContestTeamsBean> data = adapter.getData();
                if (data.size() > 0 && data.get(0).getContestTeamMembers() != null && data.get(0).getContestTeamMembers().size() > 0) {
                    for (int i = 0; i < data.size(); i++) {
                        List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> contestTeamMembers1 = data.get(i).getContestTeamMembers();
                        if (contestTeamMembers1 != null && contestTeamMembers1.size() > 0) {
                            contestTeamMembers.addAll(contestTeamMembers1);
                        }
                    }
                }
                List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> newList = new ArrayList<>(new HashSet<>(contestTeamMembers));

                intent.putExtra("singleData", (Serializable) newList);
                if (newList != null && newList.size() > 0) {
                    Log.e(TAG, "onSelectPeopleGroup: size===" + newList.size());
                } else {
                    Log.e(TAG, "onSelectPeopleGroup: size为0");
                }
                intent.putExtra(SelectTeamNumberSingleActivity.CLUB_ID, strClubId);
                intent.putExtra(SelectTeamNumberSingleActivity.MY_POSITION, position);
                List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> contestTeamMembers1 = adapter.getData().get(position).getContestTeamMembers();
                Log.e(TAG, "onSelectPeopleGroup: 点击的list有多少个===" + new Gson().toJson(contestTeamMembers1));
                Log.e(TAG, "onSelectPeopleGroup: 总的List=" + new Gson().toJson(newList));
                if (contestTeamMembers1 != null && contestTeamMembers1.size() > 0) {
                    intent.putExtra("selectData", (Serializable) contestTeamMembers1);
                }
                startActivityForResult(intent, 2);

            }

            @Override
            public void onDelClick(int position) {
                List<CreateXlClubContestInfoBean.XlClubContestTeamsBean> data1 = adapter.getData();
                data1.remove(position);
                adapter.setNewData(data1);
                adapter.notifyDataSetChanged();
            }

        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                dataList = (List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean>) data.getSerializableExtra("listbean");
                int colorTypes = data.getIntExtra("colorTypes", 0);

                if (dataList.size() < 3 || dataList.size() > 10) {
                    toast("团体每组限制3-10人");
                } else {
                    for (int i = 0; i < dataList.size(); i++) {
                        dataList.get(i).setClose(false);
                    }
                    if (colorTypes == 0) {
                        /*红队*/
                        createTeamRedAdapter.setNewData(dataList);
                    } else {
                        /*蓝队*/
                        createTeamBlueAdapter.setNewData(dataList);
                    }
                }

            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                dataList = (List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean>) data.getSerializableExtra("listbean");
                int oldposition = data.getIntExtra("position", 0);

                CreateXlClubContestInfoBean.XlClubContestTeamsBean xlClubContestTeamsBean = adapter.getData().get(oldposition);
                List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> contestTeamMembers = xlClubContestTeamsBean.getContestTeamMembers();
                if (contestTeamMembers != null && contestTeamMembers.size() > 0) {
                    xlClubContestTeamsBean.setContestTeamMembers(dataList);
//                    contestTeamMembers.addAll(dataList);
                } else {
//                    contestTeamMembers = new ArrayList<>();
//                    contestTeamMembers.addAll(dataList);
                    xlClubContestTeamsBean.setContestTeamMembers(dataList);
                }
                for (int i = 0; i < dataList.size(); i++) {
                    dataList.get(i).setClose(false);
                }
                xlClubContestTeamsBean.setContestTeamMembers(dataList);
                xlClubContestTeamsBean.setTeamNum("" + (oldposition + 1));
                adapter.setData(oldposition, xlClubContestTeamsBean);
                adapter.notifyDataSetChanged();
            }


        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_red_edit://红色编辑
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> data4 = createTeamRedAdapter.getData();
                    boolean close = data4.get(0).isClose();
                    if (close) {
                        for (int i = 0; i < data4.size(); i++) {
                            data4.get(i).setClose(false);
                        }
                        mBinding.tvRedEdit.setText("编辑");
                    } else {
                        mBinding.tvRedEdit.setText("完成");
                        for (int i = 0; i < data4.size(); i++) {
                            data4.get(i).setClose(true);
                        }
                    }
                    createTeamRedAdapter.setNewData(data4);
                    createTeamRedAdapter.notifyDataSetChanged();
                    Log.e(TAG, "onClick: 红色编辑===" + new Gson().toJson(data4));

                } catch (Exception e) {
                    toast("请选择红队");
                }

                break;
            case R.id.tv_blue_edit://蓝色编辑
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> data5 = createTeamBlueAdapter.getData();
                    boolean close5 = data5.get(0).isClose();
                    if (close5) {
                        for (int i = 0; i < data5.size(); i++) {
                            data5.get(i).setClose(false);
                        }
                        mBinding.tvBlueEdit.setText("编辑");
                    } else {
                        mBinding.tvBlueEdit.setText("完成");
                        for (int i = 0; i < data5.size(); i++) {
                            data5.get(i).setClose(true);
                        }
                    }
                    createTeamBlueAdapter.setNewData(data5);
                    Log.e(TAG, "onClick: 蓝色编辑===" + new Gson().toJson(data5));
                    createTeamBlueAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    toast("请选择蓝队");

                }

                break;
            case R.id.ll_blue://蓝队选择人数
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> data = createTeamRedAdapter.getData();
                List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> myList = createTeamBlueAdapter.getData();

                Intent intent = new Intent(CreateTeamGamesActivity.this, SelectTeamNumbersActivity.class);
                intent.putExtra("data", (Serializable) data);
                intent.putExtra("myTeamList", (Serializable) myList);
                intent.putExtra(SelectTeamNumbersActivity.SOLO_TYPE, 1);
                intent.putExtra(SelectTeamNumbersActivity.CLUB_ID, strClubId);
                intent.putExtra(SelectTeamNumbersActivity.COLOR_TYPE, 1);
                startActivityForResult(intent, 1);

                break;
            case R.id.ll_red://红队选择人数
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> data2 = createTeamBlueAdapter.getData();
                List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> myList1 = createTeamRedAdapter.getData();

                Intent intent2 = new Intent(CreateTeamGamesActivity.this, SelectTeamNumbersActivity.class);
                intent2.putExtra("myTeamList", (Serializable) myList1);
                intent2.putExtra("data", (Serializable) data2);
                intent2.putExtra(SelectTeamNumbersActivity.SOLO_TYPE, 1);
                intent2.putExtra(SelectTeamNumbersActivity.CLUB_ID, strClubId);
                intent2.putExtra(SelectTeamNumbersActivity.COLOR_TYPE, 0);

                startActivityForResult(intent2, 1);

                break;
            case R.id.ll_time://选择时间
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                TimePickerView pvTime = new TimePickerView.Builder(CreateTeamGamesActivity.this, (dateMax, v1) -> {
                    //String time = getTimeMinute(dateMax);
                    //mBinding.tvTime.setText(""+time);
                    //String week = MyDateUtils.dataWeek(dateMax);
                   //mBinding.tvWeek.setText(""+week);
//                        mBinding.tvDeadlineForRegistrationTime.setText("" + time);
                    strBeginTime = getTime(dateMax);
                    strEndTime = getTime(dateMax);
                    mBinding.tvStartTime.setText("" + strBeginTime);
                    //mBinding.tvEndTime.setText("" + strEndTime);
                    saishiTime = strBeginTime;
                    mBinding.tvStartTime.setTextColor(Color.parseColor("#666666"));
                    //mBinding.tvEndTime.setTextColor(Color.BLACK);
                })
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
//                        .setTitleText("请选择时间")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.parseColor("#333333"))//设置选中项的颜色
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





                /*TimeSelectCompleteView pvTimeSelectEnd = new TimeSelectCompleteView.Builder(CreateTeamGamesActivity.this, new TimeSelectCompleteView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date dateMax, Date dateMin, View v) {
                        if (MyDateUtils.sameDate(dateMax, dateMin)) {
                            strBeginTime = getTime(dateMin);
                            strEndTime = getTime(dateMax);
                            mBinding.tvStartTime.setText("" + strBeginTime);
                            mBinding.tvEndTime.setText("" + strEndTime);
                            saishiTime = strBeginTime + strEndTime;
                            mBinding.tvStartTime.setTextColor(Color.BLACK);
                            mBinding.tvEndTime.setTextColor(Color.BLACK);
                        } else {
                            boolean after = dateMax.after(dateMin);
                            if (after) {
                                strBeginTime = getTime(dateMin);
                                strEndTime = getTime(dateMax);
                                mBinding.tvStartTime.setText("" + strBeginTime);
                                mBinding.tvEndTime.setText("" + strEndTime);
                                saishiTime = strBeginTime + strEndTime;
                                mBinding.tvStartTime.setTextColor(Color.BLACK);
                                mBinding.tvEndTime.setTextColor(Color.BLACK);
                            } else {
                                toast("开始时间不能大于结束时间");
                            }
                        }


                    }
                })
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
//                      .setTitleText("请选择时间")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.parseColor("#333333"))//设置选中项的颜色
                        .setTextColorOut(Color.parseColor("#333333"))
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .build();
                pvTimeSelectEnd.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTimeSelectEnd.show();*/

                break;
            case R.id.ll_two_winner://三局两胜
            case R.id.cb_two_winer:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                matchMethod = "三局二胜";
                gameType = 1;
                mBinding.cbTwoWiner.setChecked(true);
                mBinding.cbThreeWinner.setChecked(false);
                mBinding.cbFourWinner.setChecked(false);
                break;
            case R.id.ll_three_winner://五局三胜
            case R.id.cb_three_winner:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                matchMethod = "五局三胜";
                gameType = 2;
                mBinding.cbTwoWiner.setChecked(false);
                mBinding.cbThreeWinner.setChecked(true);
                mBinding.cbFourWinner.setChecked(false);
                break;
            case R.id.ll_four_winner://七局四胜
            case R.id.cb_four_winner:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                matchMethod = "七局四胜";
                gameType = 3;
                mBinding.cbTwoWiner.setChecked(false);
                mBinding.cbThreeWinner.setChecked(false);
                mBinding.cbFourWinner.setChecked(true);
                break;
            case R.id.tv_next_step_move://团体的下一步
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                /*判断如果是团体 跳转到选择赛制  如果 是单打 跳转到成绩页面编排*/
                String saiName = mBinding.etSaishiName.getText().toString();//赛事名称
                //String strTableNum = mBinding.etQiutaiNum.getText().toString();//赛事名称
                if (TextUtils.isEmpty(saiName)) {
                    toast("请输入赛事名称");
                    return;
                }
                if (TextUtils.isEmpty(saishiTime)) {
                    toast("请选择时间");
                    return;
                }
                /*if (TextUtils.isEmpty(strTableNum)) {
                    toast("请输入球台数目");
                    return;
                }*/
//                传递接口 单打是1  团体是2、
                //contest  1是世界乒乓赛制  2是自定义赛制
                if (soloType == 1) {
                    /*单打*/

                }
                if (soloType == 2) {
                    /*团体*/
                    int size = createTeamRedAdapter.getData().size();
                    int size1 = createTeamBlueAdapter.getData().size();
                    if (gameType == -1) {
                        toast("请选择比赛方法");
                        return;
                    }
                    if (size == 0) {
                        toast("请选择红队人数");
                        return;
                    }
                    if (size1 == 0) {
                        toast("请选择蓝队人数");
                        return;
                    }
                    if (size < 3 && size > 10) {
                        toast("红队人数人数为3-10");
                        return;
                    }
                    if (size1 < 3 && size1 > 10) {
                        toast("蓝队人数需要3-10");
                        return;
                    }

                    createXlClubContestInfoBean.setClubId(strClubId);
                    createXlClubContestInfoBean.setContestTitle(saiName);
                    createXlClubContestInfoBean.setBeginTime(strBeginTime);
                    createXlClubContestInfoBean.setEndTiem(strEndTime);
                    //createXlClubContestInfoBean.setTableCount(strTableNum);
                    createXlClubContestInfoBean.setMatchType("" + soloType);
                    createXlClubContestInfoBean.setMatchMethod("" + matchMethod);
                    List<CreateXlClubContestInfoBean.XlClubContestTeamsBean> datalists = new ArrayList<>();
                    List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> data1 = createTeamRedAdapter.getData();
                    CreateXlClubContestInfoBean.XlClubContestTeamsBean xlClubContestTeamsBean = new CreateXlClubContestInfoBean.XlClubContestTeamsBean();
                    xlClubContestTeamsBean.setTeamNum("1");
                    xlClubContestTeamsBean.setContestTeamMembers(data1);
                    datalists.add(xlClubContestTeamsBean);

                    List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> data3 = createTeamBlueAdapter.getData();
                    CreateXlClubContestInfoBean.XlClubContestTeamsBean xlClubContestTeamsBean2 = new CreateXlClubContestInfoBean.XlClubContestTeamsBean();
                    xlClubContestTeamsBean2.setTeamNum("2");
                    xlClubContestTeamsBean2.setContestTeamMembers(data3);
                    datalists.add(xlClubContestTeamsBean2);
                    createXlClubContestInfoBean.setXlClubContestTeams(datalists);

                    Intent intent1 = new Intent(CreateTeamGamesActivity.this, ChooseFormatActivity.class);
                    intent1.putExtra("data", createXlClubContestInfoBean);
                    startActivity(intent1);
                } else {
                    toast("请选择比赛类别");
                    return;
                }
                if (gameType == -1) {
                    toast("请选择比赛方法");
                    return;
                }


                break;
            case R.id.tv_create_and_start://创建完成，开始比赛
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Log.e(TAG, "onClick: soloType===" + soloType);
                if (soloType == 1) {
                    String saiName1 = mBinding.etSaishiName.getText().toString();//赛事名称
                    //String strTableNumm = mBinding.etQiutaiNum.getText().toString();//赛事名称
                    if (TextUtils.isEmpty(saiName1)) {
                        toast("请输入赛事名称");
                        return;
                    }
                    if (TextUtils.isEmpty(saishiTime)) {
                        toast("请选择时间");
                        return;
                    }
                    /*if (TextUtils.isEmpty(strTableNumm)) {
                        toast("请输入球台数目");
                        return;
                    }*/
                    if (gameType == -1) {
                        toast("请选择比赛方法");
                        return;
                    }
                    //String saiName1 = mBinding.etSaishiName.getText().toString();//赛事名称
                    //String strTableNum1 = mBinding.etQiutaiNum.getText().toString();//赛事名称
                    List<CreateXlClubContestInfoBean.XlClubContestTeamsBean> data1 = adapter.getData();
                    if (data1.size() <= 0) {
                        toast("请选择参赛队员");
                        return;
                    }
                    try {
                        boolean isShow = true;
                        for (int i = 0; i < data1.size(); i++) {
                            List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> contestTeamMembers = data1.get(i).getContestTeamMembers();
                            if (contestTeamMembers.size() < 3 || contestTeamMembers.size() > 100) {
                                isShow = false;
                            }
                        }
                        if (!isShow) {
                            toast("单打每组限制3-100人");
                            return;
                        }
                    } catch (Exception e) {
                        toast("请选择参赛队员");
                        return;
                    }

                    boolean isSuccess = true;
                    int myposition = 0;
                    for (int i = 0; i < data1.size(); i++) {
                        List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> contestTeamMembers = data1.get(i).getContestTeamMembers();
                        if (contestTeamMembers != null && contestTeamMembers.size() < 0) {
                            toast("请选择参赛人员");
                            return;
                        } else {
                            if (contestTeamMembers != null && contestTeamMembers.size() > 0) {
                                if (contestTeamMembers.size() < 3 && contestTeamMembers.size() > 10) {
                                    myposition = i;
                                    isSuccess = false;
                                    break;
                                }
                            } else {
                                toast("请选择参赛人员");
                                return;
                            }
                        }
                    }
                    if (isSuccess) {
                        createXlClubContestInfoBean.setClubId(strClubId);
                        createXlClubContestInfoBean.setContestTitle(saiName1);
                        createXlClubContestInfoBean.setBeginTime(strBeginTime);
                        createXlClubContestInfoBean.setEndTiem(strEndTime);
                        createXlClubContestInfoBean.setMatchMethod("" + matchMethod);
                        //createXlClubContestInfoBean.setTableCount(strTableNum1);
                        createXlClubContestInfoBean.setMatchType("" + soloType);
                        List<CreateXlClubContestInfoBean.XlClubContestTeamsBean> data3 = adapter.getData();
                        createXlClubContestInfoBean.setXlClubContestTeams(data3);
                        showLoading();
                        mViewModel.createXlClubContestInfo(createXlClubContestInfoBean);
                    } else {
                        toast("第" + myposition + "组参赛人员不符合条件");
                    }
                } else if (soloType == -1) {
                    toast("请选择比赛方式");
                }

                break;
            case R.id.ll_game_type_team://团体
            case R.id.cb_game_type_team:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.tvNextStepMove.setVisibility(View.VISIBLE);
                mBinding.tvCreateAndStart.setVisibility(View.GONE);
                soloType = 2;
                mBinding.llTwoWinner.setEnabled(true);
                mBinding.llThreeWinner.setEnabled(true);
                mBinding.llFourWinner.setEnabled(false);
                mBinding.tvTwoWinners.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvThreeWinner.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvFourWinner.setTextColor(Color.parseColor("#FFCCCCCC"));
                mBinding.cbTwoWiner.setEnabled(true);
                mBinding.cbThreeWinner.setEnabled(true);
                mBinding.cbFourWinner.setEnabled(false);
                mBinding.cbGameTypeSingle.setChecked(false);
                mBinding.cbGameTypeTeam.setChecked(true);

                mBinding.cbTwoWiner.setChecked(false);
                mBinding.cbThreeWinner.setChecked(false);
                mBinding.cbFourWinner.setChecked(false);
                mBinding.llSingle.setVisibility(View.VISIBLE);
                mBinding.llTeam.setVisibility(View.GONE);
                mBinding.rlAdd.setVisibility(View.INVISIBLE);
                break;
            case R.id.ll_game_type_single://单打
            case R.id.cb_game_type_single:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.tvNextStepMove.setVisibility(View.GONE);
                mBinding.tvCreateAndStart.setVisibility(View.VISIBLE);
                soloType = 1;
                mBinding.llTwoWinner.setEnabled(true);
                mBinding.llThreeWinner.setEnabled(true);
                mBinding.llFourWinner.setEnabled(true);
                mBinding.tvTwoWinners.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvThreeWinner.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvFourWinner.setTextColor(Color.parseColor("#FF666666"));
                mBinding.cbTwoWiner.setEnabled(true);
                mBinding.cbThreeWinner.setEnabled(true);
                mBinding.cbFourWinner.setEnabled(true);
                mBinding.cbGameTypeSingle.setChecked(true);
                mBinding.cbGameTypeTeam.setChecked(false);

                mBinding.cbTwoWiner.setChecked(false);
                mBinding.cbThreeWinner.setChecked(false);
                mBinding.cbFourWinner.setChecked(false);


                mBinding.llTeam.setVisibility(View.VISIBLE);
                mBinding.llSingle.setVisibility(View.GONE);
                mBinding.rlAdd.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_add://新增分组
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                responityListBeans.add(new CreateXlClubContestInfoBean.XlClubContestTeamsBean());
                createXlClubContestInfoBean.setXlClubContestTeams(responityListBeans);
                initRecyclerView(createXlClubContestInfoBean);


                break;
        }
    }
}