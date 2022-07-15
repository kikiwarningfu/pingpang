package heyong.intellectPinPang.ui.competition.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
//import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;
import com.xq.fasterdialog.dialog.CustomDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ToastUtils;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.SelectPeopleProjectAdapter;
import heyong.intellectPinPang.adapter.game.TeamAndClubChannelAdapter;
import heyong.intellectPinPang.adapter.game.TeamRegisterSubmitAdapter;
import heyong.intellectPinPang.bean.competition.CheckUserClunRoleBean;
import heyong.intellectPinPang.bean.competition.ChooseJoinMatchUserBean;
import heyong.intellectPinPang.bean.competition.ClubApplyMatchBean;
import heyong.intellectPinPang.bean.competition.CompetitionItemBean;
import heyong.intellectPinPang.bean.competition.MatchOrderInfoBean;
import heyong.intellectPinPang.bean.competition.OrderIdBean;
import heyong.intellectPinPang.bean.competition.QueryMatchApplyInfoBean;
import heyong.intellectPinPang.bean.competition.SelectPeopleProjectBean;
import heyong.intellectPinPang.bean.competition.SelectPeopleProjectItemBean;
import heyong.intellectPinPang.bean.competition.SelectPlayerForTeamBean;
import heyong.intellectPinPang.bean.pay.WXPayBean;
import heyong.intellectPinPang.databinding.ActivityClubTeamRegisterChannelBinding;
import heyong.intellectPinPang.factory.PayFactory;
import heyong.intellectPinPang.ui.competition.CompetitionViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.CommonUtilis;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.utils.MessageDialogHaveTitleSpannerBuilder;
import heyong.intellectPinPang.utils.MessageDialogTitleBuilder;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;
import heyong.intellectPinPang.widget.popupwindow.CommonPopupWindow;
import heyong.intellectPinPang.wxapi.WXPayEntryActivity;

/**
 * 俱乐部报名-选择教练员  ---选择队员和项目----提交申请
 */
public class ClubTeamRegisterChannelActivity extends BaseActivity<ActivityClubTeamRegisterChannelBinding, CompetitionViewModel> implements View.OnClickListener, WXPayEntryActivity.WXListener {
    private WindowManager wm;
    private int width;
    public static final String MATCH_ID = "match_id";
    private String matchId = "";
    public static final String MATCH_TITLE = "match_title";
    private String matchTitle = "";

    MyDividerItemDecoration mSixDiD;
    MyDividerItemDecoration mMySixDid;
    MyDividerItemDecoration mSelctPeople;
    CommonPopupWindow mMovieTicketWindow;
    private String teamTitleId;

    List<ChooseJoinMatchUserBean.PlayersBean> allcoachPlayers = new ArrayList<>();
    List<ChooseJoinMatchUserBean.PlayersBean> selectcoachPlayers = new ArrayList<>();
    List<ChooseJoinMatchUserBean.PlayersBean> coachPlayers = new ArrayList<>();
    List<ChooseJoinMatchUserBean.PlayersBean> leaderPlayers = new ArrayList<>();
    public static final String TAG = ClubTeamRegisterChannelActivity.class.getSimpleName();

    CheckUserClunRoleBean checkUserClunRoleBean;
    /*初始化选择队员和项目*/
    List<SelectPeopleProjectBean> selectPeopleProjectBeans = new ArrayList<>();
    QueryMatchApplyInfoBean queryMatchApplyInfoBean;
    SelectPeopleProjectAdapter selectPeopleProjectAdapter;//全局赛事的适配器
    private int clickPosition = 0;//点击请求接口发出的position
    private String clickProjectId = "";


    private int ownerType = 0;
    private int duiwuPosition = 0;

    private int allMoney = 0;
    private int baomingMoney = 0;
    private int eatMoney = 0;
    private int clickType = -1;
    private int payWay = 0;//1 微信 2 支付宝
    private int submitClickType = 0;//1保存 2支付提交
    //报名费 人数 不包含领队和教练员
    //食宿费  为最大值
    private int baomingNum = 0;
    private int shiSuMaxNum = 0;
    private String strTuantiManTypeTitle = "";
    private String strTuantiWomanTypeTitle = "";
    private String strTuantiHunheTypeTitle = "";
    private int commitSaveOrder = 0;
    private int competitionTotalCount = 0;
    private int choosePlayerPosition = 0;
    /**
     * 逻辑  生成一个List集合   然后判断其中的组别的id
     * 逻辑1  生成最后一个List集合  然后将 List集合里面添加 组别  编辑组别 取出值 进行遍历  判断是否有重复的值
     * 逻辑2  选人 那里做操作  在回调里面 判断 是否所有的组别 是否包含了这个数据id（类里面 创建一个Map ）
     * 、、然后map 里面
     *
     * @return
     */
    Set<String> projectIdSet = new HashSet<>();
    List<String> teamList = new ArrayList<>();
    Handler handler = new Handler();
    Runnable showRunnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(showRunnable, 500);//在这里设置延时时间，单位是毫秒
            //在这里进行想做的事
        }
    };
    private int tuantiCount = 0;

    @Override
    protected void onResume() {
        super.onResume();
        teamList.clear();
        //        Log.e(TAG, "onResume: "+new Gson().toJson(selectPeopleProjectBeans) );
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_club_team_register_channel;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        wm = getWindowManager();
        // 重设高度
        width = wm.getDefaultDisplay().getWidth();
        matchId = getIntent().getStringExtra(MATCH_ID);
        matchTitle = getIntent().getStringExtra(MATCH_TITLE);
        WXPayEntryActivity.setWXListener(this);
        mBinding.tvMatchTitle.setText("" + matchTitle);
        mViewModel.checkUserClunRole(matchId);
        mViewModel.queryMatchApplyInfo(matchId);
        mBinding.tvEventInformation.setTextColor(Color.parseColor("#ff4795ed"));
        mBinding.viewEventInformation.setBackgroundResource(R.drawable.shape_competition_blue_circle);
        mBinding.tvChargingSet.setTextColor(Color.parseColor("#333333"));
        mBinding.viewChargingSet.setBackgroundResource(R.drawable.shape_competition_big_gray_circle);
        mBinding.tvCompetitionItem.setTextColor(Color.parseColor("#333333"));
        mBinding.viewCompetitionItem.setBackgroundResource(R.drawable.shape_competition_big_gray_circle);

        mSixDiD = new MyDividerItemDecoration(ClubTeamRegisterChannelActivity.this, 10,
                getResources().getColor(R.color.color_f5));
        mMySixDid = new MyDividerItemDecoration(ClubTeamRegisterChannelActivity.this, 10,
                getResources().getColor(R.color.white));
        mBinding.topbar.getIvFinish().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MessageDialogBuilder(ClubTeamRegisterChannelActivity.this)
                        .setMessage("现在退出不会保存报名信息，下次进入\n" +
                                "将重新报名")
                        .setTvTitle("是否退出报名？")
                        .setTvCancel("取消")
                        .setTvSure("确定")
                        .setBtnCancleHint(false)
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        })
                        .show();
            }
        });
        mViewModel.queryMatchApplyInfoLiveData.observe(this, new Observer<ResponseBean<QueryMatchApplyInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<QueryMatchApplyInfoBean> responseBean) {
                if (responseBean.getData() != null) {
                    queryMatchApplyInfoBean = responseBean.getData();
                }
            }
        });
        mViewModel.getOrderClubIdLiveData.observe(this, new Observer<OrderIdBean>() {
            @Override
            public void onChanged(OrderIdBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200000-100001") || responseBean.getErrorCode().equals("200000-100002")) {
                    showOut("token已过期，请重新登录");
                } else {
                    //                Log.e(TAG, "onChanged: oldId===" + responseBean.getData());
                    //                BigDecimal bigDecimal = new BigDecimal((Double) responseBean.getData());
                    //                long ids = bigDecimal.longValue();
                    Log.e(TAG, "onChanged: NewId====" + responseBean.getData());
                    Log.e(TAG, "onChanged: myId====" + String.valueOf(responseBean.getData()));
                    commitOrder(responseBean.getData());
                }

            }
        });
        mViewModel.checkUserClunRoleLiveData.observe(this, new Observer<ResponseBean<CheckUserClunRoleBean>>() {
            @Override
            public void onChanged(ResponseBean<CheckUserClunRoleBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    checkUserClunRoleBean = responseBean.getData();
                    mBinding.llSelectClub.setVisibility(View.VISIBLE);
                    mBinding.llSelectCoachTeam.setVisibility(View.GONE);
                    mBinding.llSubmitApply.setVisibility(View.GONE);


                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

        mBinding.setListener(this);
        mViewModel.judgeUserShiFouChongFuDeleteAllLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                mViewModel.checkNowApply("" + matchId, "" + teamTitleId);
                //                if (responseBean.getErrorCode().equals("200")) {
                //
                //                } else {
                //                    dismissLoading();
                //                    toast("" + responseBean.getMessage());
                //                }
            }
        });
        mViewModel.checkNowApplyLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    showLoading();
                    mViewModel.competitionItem("" + matchId, "");
                    mBinding.llSelectClub.setVisibility(View.GONE);
                    mBinding.llSelectCoachTeam.setVisibility(View.VISIBLE);
                    mBinding.llSubmitApply.setVisibility(View.GONE);
                    mViewModel.chooseJoinMatchUser("" + matchId, "" + teamTitleId);
                } else if (responseBean.getErrorCode().equals("100000-100033")) {
                    //该俱乐部负责人正在操作
                    toast("该俱乐部负责人正在操作");
                } else if (responseBean.getErrorCode().equals("100000-100035")) {
                    //该俱乐部未支付的报名提交
                    toast("该俱乐部未支付的报名提交");
                } else if (responseBean.getErrorCode().equals("100000-100034")) {
                    //该俱乐部已经报名过
                    new MessageDialogTitleBuilder(ClubTeamRegisterChannelActivity.this)
                            .setMessage("您所选择的俱乐部已经进行过一次报名\n" +
                                    "是否进行新一次报名？"
                            )
                            .setTvCancel("取消")
                            .setBtnCancleHint(false)
                            .setTvSure("确定")
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mBinding.llSelectClub.setVisibility(View.GONE);
                                    mBinding.llSelectCoachTeam.setVisibility(View.VISIBLE);
                                    mBinding.llSubmitApply.setVisibility(View.GONE);
                                    showLoading();
                                    mViewModel.competitionItem("" + matchId, "");
                                    mViewModel.chooseJoinMatchUser("" + matchId, "" + teamTitleId);
                                }
                            })
                            .show();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        /*获取教练员的列表*/
        mViewModel.chooseJoinMatchUserLiveData.observe(this, new Observer<ResponseBean<ChooseJoinMatchUserBean>>() {
            @Override
            public void onChanged(ResponseBean<ChooseJoinMatchUserBean> responseBean) {
                ChooseJoinMatchUserBean data = responseBean.getData();
                if (data != null) {
                    allcoachPlayers.clear();
                    allcoachPlayers = data.getPlayers();
                    selectcoachPlayers.clear();
                    selectcoachPlayers.addAll(allcoachPlayers);
                } else {
                    toast("" + responseBean.getMessage());
                }

            }
        });
        mViewModel.clubApplyMatchLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    SpannableString spannableString = new SpannableString("可在 我的-我的报名 查看或提交保存的报名，请在" +
                            "比赛报名截止前支付提交报名");
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff4795ed")), 2,
                            spannableString.length() - 26, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    new MessageDialogHaveTitleSpannerBuilder(ClubTeamRegisterChannelActivity.this)
                            .setMessage(spannableString)
                            .setTvTitle("保存成功")
                            .setCancelAble(false)
                            .setTvCancel("取消")
                            .setBtnCancleHint(true)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            })
                            .setTvSure("确定")
                            .show();


                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.judgeUserShiFouChongFuDeleteLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
            }
        });

        /*赛事列表*/
        mViewModel.competitionItemLiveData.observe(this, new Observer<ResponseBean<List<CompetitionItemBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<CompetitionItemBean>> listResponseBean) {
                if (listResponseBean.getData() != null && listResponseBean.getData().size() > 0) {
                    if (selectPeopleProjectBeans != null) {
                        selectPeopleProjectBeans.clear();
                    }
                    tuantiCount = 0;
                    //                    领队    教练员
                    List<SelectPeopleProjectItemBean> selectPeopleProjectItemBeans = new ArrayList<>();
                    selectPeopleProjectItemBeans.add(new SelectPeopleProjectItemBean());
                    SelectPeopleProjectBean selectPeopleProjectBean0 = new SelectPeopleProjectBean(0, selectPeopleProjectItemBeans, 0
                            , "", "0", "", "", "", "", "", 0);
                    selectPeopleProjectBeans.add(selectPeopleProjectBean0);

                    /*教练员*/
                    List<SelectPeopleProjectItemBean> selectPeopleProjectItemBeans1 = new ArrayList<>();
                    selectPeopleProjectItemBeans1.add(new SelectPeopleProjectItemBean());
                    SelectPeopleProjectBean selectPeopleProjectBean1 = new SelectPeopleProjectBean(1, selectPeopleProjectItemBeans1, 0
                            , "", "1", "", "", "", "", "", 0);
                    selectPeopleProjectBeans.add(selectPeopleProjectBean1);
                    List<CompetitionItemBean> data = listResponseBean.getData();
                    int sleepMinute = 0;
                    try {
                        for (int i = 0; i < data.size(); i++) {
                            String projectItem = data.get(i).getProjectItem();
                            long id = data.get(i).getId();
                            String itemTitle = data.get(i).getItemTitle();
                            String projectName = data.get(i).getProjectName();
                            int projectItems = Integer.parseInt(projectItem);
                            long id1 = data.get(i).getId();
                            long itemProjectId = data.get(i).getProjectId();
                            projectIdSet.add( ""+itemProjectId);
                            String tuantiType = "";
                            //1 团体 2 单打  3 双打   4 混双（和业余双打一样）
                            int ownerType = 2;
                            //0 领队  1 教练员   2团体  3混合单打  4双打专业  5 双打非专业
                            //0是男  1是女  2 是混合   3 是混双  sexType
                            int sexType = Integer.parseInt(data.get(i).getSexType());// 1男 2 女 3 混合   4混双
                            String matchLevel = "";
                            Log.e(TAG, "onChanged: title===" + itemTitle + projectName + "projectItems====" + projectItems);
                            sleepMinute = sleepMinute + 170;
                            switch (projectItems) {
                                case 1://团体
                                    ownerType = 2;
                                    tuantiType = "2";
                                    long projectId1 = data.get(i).getId();
                                    competitionTotalCount = competitionTotalCount + 1;
                                    if (sexType == 1) {
                                        teamList.add("" + data.get(i).getId());
                                        strTuantiManTypeTitle = itemTitle + projectName;
                                        showLoading();
                                        Log.e(TAG, "onChanged: title 请求接口====" + projectId1 + "====" + strTuantiManTypeTitle + projectId1);
                                        int finalSleepMinute = sleepMinute;

                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    Thread.sleep(finalSleepMinute);
                                                    mViewModel.chooseMan("" + matchId, "" + teamTitleId, "" + projectId1, "" + itemProjectId);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();

                                    } else if (sexType == 2) {
                                        teamList.add("" + data.get(i).getId());
                                        strTuantiWomanTypeTitle = itemTitle + projectName;
                                        showLoading();
                                        int finalSleepMinute = sleepMinute;

                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    Thread.sleep(finalSleepMinute);
                                                    mViewModel.chooseMan("" + matchId, "" + teamTitleId, "" + projectId1, "" + itemProjectId);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                    } else {
                                        teamList.add("" + data.get(i).getId());
                                        strTuantiHunheTypeTitle = itemTitle + projectName;
                                        showLoading();
                                        int finalSleepMinute = sleepMinute;
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    Thread.sleep(finalSleepMinute);
                                                    mViewModel.chooseMan("" + matchId, "" + teamTitleId, "" + projectId1, "" + itemProjectId);

                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                    }
                                    break;
                                case 2://单打
                                    ownerType = 3;
                                    tuantiType = "3";
                                    break;
                                case 3://双打  判断掉接口
                                    if (queryMatchApplyInfoBean != null) {
                                        matchLevel = queryMatchApplyInfoBean.getMatchLevel();//判断专业 还是业余
                                        if (TextUtils.isEmpty(matchLevel)) {
                                            ownerType = 4;
                                            tuantiType = "4";
                                        } else {
                                            if (matchLevel.equals("专业级")) {
                                                /*专业*/
                                                ownerType = 4;
                                                tuantiType = "4";
                                            } else {
                                                /*业余*/
                                                ownerType = 5;
                                                tuantiType = "5";
                                            }
                                        }

                                    }
                                    break;
                                //专业  男子双打   女子双打   混双
                                /**
                                 * //专业   双打 男子双打  男团选   女子双打 女团选   必须是同一个团体   （左右俩人）  页面重新搞  男团/女团/2
                                 * //       混双  一男 一女  从同组别的男团和组团选   （男团 和女团 少的那个人数为准 男的10个人 女的9个人 只能有9组）
                                 *
                                 * //业余  双打  男子双打 女子双打 不区分团体  符合年龄条件和性别条件  所有符合的人数总和/2
                                 * //  混合  混合双打  所有符合年龄/2
                                 * //   混双    一男一女  但是没有 从团体选  人数最少的性别
                                 */


                                //非专业  男子双打  女子双打   混合双打   混双一男一女

                                case 4://混双
                                    if (queryMatchApplyInfoBean != null) {
                                        matchLevel = queryMatchApplyInfoBean.getMatchLevel();//判断专业 还是业余
                                        if (TextUtils.isEmpty(matchLevel)) {
                                            ownerType = 4;
                                            tuantiType = "4";
                                        } else {
                                            if (matchLevel.equals("专业级")) {
                                                /*专业*/
                                                ownerType = 4;
                                                tuantiType = "4";
                                            } else {
                                                /*业余*/
                                                ownerType = 5;
                                                tuantiType = "5";
                                            }
                                        }

                                    }
                                    break;

                            }
                            List<SelectPeopleProjectItemBean> selectPeopleProjectItemBeanss = new ArrayList<>();
                            SelectPeopleProjectItemBean selectPeopleProjectItemBean = new SelectPeopleProjectItemBean();
                            selectPeopleProjectItemBean.setName("" + data.get(i).getItemTitle());
                            selectPeopleProjectItemBean.setGroupName("" + data.get(i).getProjectName());
                            selectPeopleProjectItemBean.setDuiwuPosition("0");
                            selectPeopleProjectItemBeanss.add(selectPeopleProjectItemBean);

                            SelectPeopleProjectBean selectPeopleProjectBean;
                            Log.e(TAG, "onChanged: ===" + projectItems);
                            if (projectItems == 3) {
                                Log.e(TAG, "onChanged: 团体");
                                selectPeopleProjectBean = new SelectPeopleProjectBean(ownerType, new ArrayList<>(), id
                                        , "" + sexType, "" + tuantiType, itemTitle,
                                        "" + id1, "" + itemProjectId, "" + matchLevel, "" + projectName, projectItems);
                            } else if (projectItems == 4) {
                                selectPeopleProjectBean = new SelectPeopleProjectBean(ownerType, new ArrayList<>(), id
                                        , "" + sexType, "" + tuantiType, itemTitle,
                                        "" + id1, "" + itemProjectId, "" + matchLevel, "" + projectName, projectItems);
                            } else {
                                selectPeopleProjectBean = new SelectPeopleProjectBean(ownerType, selectPeopleProjectItemBeanss, id
                                        , "" + sexType, "" + tuantiType, itemTitle, "" + id1,
                                        "" + itemProjectId, "" + matchLevel, "" + projectName, projectItems);
                            }

                            selectPeopleProjectBeans.add(selectPeopleProjectBean);
                            choosePlayerPosition = 1;
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onChanged: ===" + e.getMessage());
                        toast("数据异常");
                        dismissLoading();
                    }
                    //如果只有单打   或者双打 没有团体 隐藏loading
                    int normalCount = 0;
                    for (int m = 0; m < selectPeopleProjectBeans.size(); m++) {
                        if (selectPeopleProjectBeans.get(m).getItemProjectType().equals("2")) {
                            normalCount = normalCount + 1;
                        }
                    }
                    if (normalCount == 0) {
                        dismissLoading();
                    }

                } else {
                    toast("" + listResponseBean.getMessage());
                }

                MyDividerItemDecoration mSixDiD = new MyDividerItemDecoration(ClubTeamRegisterChannelActivity.this, 5,
                        getResources().getColor(R.color.white));
                selectPeopleProjectAdapter = new SelectPeopleProjectAdapter(ClubTeamRegisterChannelActivity
                        .this, width, mSixDiD);
                mBinding.rvSelectPeopleProject.setAdapter(selectPeopleProjectAdapter);
                mBinding.rvSelectPeopleProject.setLayoutManager(new LinearLayoutManager(ClubTeamRegisterChannelActivity.this, RecyclerView.VERTICAL, false));
                mBinding.rvSelectPeopleProject.removeItemDecoration(mSelctPeople);
                mSelctPeople = new MyDividerItemDecoration(ClubTeamRegisterChannelActivity.this, 20,
                        getResources().getColor(R.color.color_f5));
                mBinding.rvSelectPeopleProject.addItemDecoration(mSelctPeople);

                selectPeopleProjectAdapter.setNewData(selectPeopleProjectBeans);
                selectPeopleProjectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                    }
                });
                selectPeopleProjectAdapter.setListener(new SelectPeopleProjectAdapter.onMyListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void OnListener(int position, SelectPeopleProjectItemBean selectPeopleProjectItemBean,
                                           int waiPosition, SelectPeopleProjectBean selectPeopleProjectBean) {

                        if (leaderPlayers != null && leaderPlayers.size() > 0) {
                            if (coachPlayers != null && coachPlayers.size() > 0) {
                                selectTuantiData(position, waiPosition);
                            } else {
                                toast("请先选择教练员");
                            }
                        } else {
                            toast("请先选择领队");

                        }
                    }

                    @Override
                    public void onDel() {
                        toast("不可删除");
                    }


                    /*添加*/
                    @Override
                    public void onAdd(int position, SelectPeopleProjectItemBean
                            selectPeopleProjectItemBean, int waiPosition, SelectPeopleProjectBean
                                              waiprojectBean) {


                    }

                    @Override
                    public void notifyGroupData(List<SelectPeopleProjectBean> allData) {
                        //                        selectPeopleProjectAdapter.setNewData(selectPeopleProjectBeans);

                    }

                    @Override
                    public void onDelGroup(SelectPeopleProjectItemBean selectPeopleProjectItemBean, List<String> userIdsLists, SelectPeopleProjectBean selectPeopleProjectBean) {
                        clickProjectId = selectPeopleProjectBean.getItemProjectId();
                        Log.e(TAG, "onDelGroup: " + new Gson().toJson(selectPeopleProjectBean));
                        //                        intent.putExtra(SelectPlayersForTeamAndSinglesActivity.MATCH_ID, matchId);
                        //                        intent.putExtra(SelectPlayersForTeamAndSinglesActivity.CLUB_ID, teamTitleId);
                        //                        intent.putExtra(SelectPlayersForTeamAndSinglesActivity.PROJECT_ID, clickProjectId);

//                        List<Long> list = new ArrayList<String>(projectIdSet);
//                        showLoading();
//                        mViewModel.judgeUserShiFouChongFuDelete("" + matchId, "" + teamTitleId, "" + clickProjectId, userIdsLists, list);
                    }
//
//                    //删除小组 调用接口  showloading  dissmissloading
//                    @Override
//                    public void onDelGroup(SelectPeopleProjectItemBean selectPeopleProjectItemBean, List<Long> userIdsLists, SelectPeopleProjectBean selectPeopleProjectBean) {
//                        //                        selectPeopleProjectBean.get
//                        clickProjectId = selectPeopleProjectBean.getItemProjectId();
//                        Log.e(TAG, "onDelGroup: " + new Gson().toJson(selectPeopleProjectBean));
//                        //                        intent.putExtra(SelectPlayersForTeamAndSinglesActivity.MATCH_ID, matchId);
//                        //                        intent.putExtra(SelectPlayersForTeamAndSinglesActivity.CLUB_ID, teamTitleId);
//                        //                        intent.putExtra(SelectPlayersForTeamAndSinglesActivity.PROJECT_ID, clickProjectId);
//
//                        List<Long> list = new ArrayList<String>(projectIdSet);
//                        showLoading();
//                        mViewModel.judgeUserShiFouChongFuDelete("" + matchId, "" + teamTitleId, "" + clickProjectId, userIdsLists, list);
//
//
//                    }


                });
                //1 领队  来返回数据  2 教练员   3  混合单打  4 专业双打   5 业余双打  6 团体
                selectPeopleProjectAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view,
                                                 int position) {

                        String clickProjectName = selectPeopleProjectAdapter.getData().get(position).getProjectName();
                        switch (view.getId()) {
                            case R.id.tv_mixed_singles_lead_group://领队
                                List<SelectPeopleProjectItemBean> newList = selectPeopleProjectAdapter.getData().get(position).getNewList();
                                List<SelectPeopleProjectItemBean.Players> playersList1 = newList.get(0).getPlayersList();
                                if (playersList1.size() > 0) {
                                    for (int m = 0; m < playersList1.size(); m++) {
                                        for (int mm = 0; mm < allcoachPlayers.size(); mm++) {
                                            if (playersList1.get(m).getUserId()==allcoachPlayers.get(mm).getUserId()) {
                                                allcoachPlayers.get(mm).setSelect(true);
                                            }
                                        }

                                    }
                                }
                                Intent intent = new Intent(ClubTeamRegisterChannelActivity.this, SelectAthletesAndCoachesActivity.class);
                                intent.putExtra(SelectAthletesAndCoachesActivity.OWN_ER_TYPE, 0);
                                intent.putExtra(SelectAthletesAndCoachesActivity.OWN_IN_TYPE, 1);
                                intent.putExtra(SelectAthletesAndCoachesActivity.OWN_POSITION, position);
                                intent.putExtra(SelectAthletesAndCoachesActivity.CLUB_ID, "" + teamTitleId);//俱乐部id
                                intent.putExtra(SelectAthletesAndCoachesActivity.MATCH_ID, "" + matchId);
                                intent.putExtra(SelectAthletesAndCoachesActivity.PROJECT_ID, "" + clickProjectId);
                                intent.putExtra("data", (Serializable) allcoachPlayers);
                                startActivityForResult(intent, 1);

                                break;
                            case R.id.tv_mixed_single_coach://教练员
                                if (allcoachPlayers.size() > 0) {
                                    List<SelectPeopleProjectItemBean> newList22 = selectPeopleProjectAdapter.getData().get(position).getNewList();

                                    List<SelectPeopleProjectItemBean.Players> playersList22 = newList22.get(0).getPlayersList();
                                    if (playersList22.size() > 0) {
                                        for (int m = 0; m < playersList22.size(); m++) {
                                            for (int mm = 0; mm < allcoachPlayers.size(); mm++) {
                                                if (playersList22.get(m).getUserId()==allcoachPlayers.get(mm).getUserId()) {
                                                    allcoachPlayers.get(mm).setSelect(true);
                                                }
                                            }

                                        }
                                    }
                                    /*判断 领队是否选择  如果选择了 弹出提示*/
                                    Intent intent1 = new Intent(ClubTeamRegisterChannelActivity.this, SelectAthletesAndCoachesActivity.class);
                                    intent1.putExtra(SelectAthletesAndCoachesActivity.OWN_ER_TYPE, 1);
                                    intent1.putExtra(SelectAthletesAndCoachesActivity.OWN_IN_TYPE, 2);
                                    intent1.putExtra(SelectAthletesAndCoachesActivity.OWN_POSITION, position);
                                    intent1.putExtra(SelectAthletesAndCoachesActivity.CLUB_ID, "" + teamTitleId);//俱乐部id
                                    intent1.putExtra(SelectAthletesAndCoachesActivity.MATCH_ID, "" + matchId);
                                    intent1.putExtra(SelectAthletesAndCoachesActivity.PROJECT_ID, "" + clickProjectId);
                                    intent1.putExtra("data", (Serializable) selectcoachPlayers);
                                    startActivityForResult(intent1, 2);
                                } else {
                                    toast("请选择领队");
                                }

                                break;
                            case R.id.tv_mixed_hunhe_danda://混合单打
                                /*判断 领队是否选择   判断教练员 是否选择*/
                                if (leaderPlayers != null && leaderPlayers.size() > 0) {
                                    if (coachPlayers != null && coachPlayers.size() > 0) {
                                        ownerType = 3;
                                        clickPosition = position;
                                        long projectId = selectPeopleProjectAdapter.getData().get(position).getProjectId();
                                        showLoading();
                                        mViewModel.chooseJoinMatchPlayer("" + matchId, "" + teamTitleId, "" + projectId);
                                    } else {
                                        toast("请先选择教练员");
                                    }
                                } else {
                                    toast("请先选择领队");
                                }

                                break;
                            case R.id.tv_select_people_double_profressional://专业双打  ChooseAthleteMajorActivity
                                if (leaderPlayers != null && leaderPlayers.size() > 0) {
                                    if (coachPlayers != null && coachPlayers.size() > 0) {
                                        ownerType = 4;
                                        clickPosition = position;
                                        /*遍历一下 男团还是女团*/
                                        SelectPeopleProjectBean selectPeopleProjectBean = selectPeopleProjectAdapter.getData().get(position);
                                        String itemProjectId = selectPeopleProjectBean.getItemProjectId();

                                        String sexType = selectPeopleProjectBean.getSexType();
                                        String matchTitle = selectPeopleProjectBean.getItemTitle();
                                        String projectName = selectPeopleProjectBean.getProjectName();
                                        Intent intent1 = new Intent();
                                        intent1.setClass(ClubTeamRegisterChannelActivity.this, ChooseAthleteMajorActivity.class);
                                        //专业   双打 男子双打  男团选   女子双打 女团选   必须是同一个团体   （左右俩人）  页面重新搞  男团/女团/2
                                        //      混双  一男 一女  从同组别的男团和组团选   （男团 和女团 少的那个人数为准 男的10个人 女的9个人 只能有9组）
                                        //1 团体 2 单打  3 双打   4 混双（和业余双打一样）
                                        //0 领队  1 教练员   2团体  3混合单打  4双打专业  5 双打非专业
                                        //0是男  1是女  2 是混合   3 是混双  sexType
                                        int i = Integer.parseInt(sexType);
                                        List<SelectPeopleProjectItemBean> manList = new ArrayList<>();
                                        List<SelectPeopleProjectItemBean> womanList = new ArrayList<>();
                                        for (int m = 0; m < selectPeopleProjectBeans.size(); m++) {
                                            if (selectPeopleProjectBeans.get(m).getProjectName().equals(clickProjectName)) {
                                                /*团体*/
                                                if (selectPeopleProjectBeans.get(m).getItemProjectType().equals("2")) {
                                                    if (selectPeopleProjectBeans.get(m).getSexType().equals("1")) {
                                                        /*男子团体*/
                                                        List<SelectPeopleProjectItemBean> newmanList = selectPeopleProjectBeans.get(m).getNewList();
                                                        List<SelectPeopleProjectItemBean.Players> manPlayList = new ArrayList<>();
                                                        manList = selectPeopleProjectBeans.get(m).getNewList();
                                                        for (int ll = 0; ll < newmanList.size(); ll++) {
                                                            List<SelectPeopleProjectItemBean.Players> playersList = newmanList.get(ll).getPlayersList();
                                                            manPlayList.addAll(playersList);
                                                            SelectPeopleProjectItemBean selectPeopleProjectItemBean = manList.get(ll);
                                                            selectPeopleProjectItemBean.setPlayersList(newmanList.get(ll).getPlayersList());
                                                            manList.set(ll, selectPeopleProjectItemBean);
                                                        }

                                                    }
                                                    if (selectPeopleProjectBeans.get(m).getSexType().equals("2")) {
                                                        /*女子团体*/
                                                        /*有几个团体 创建几个双打*/
                                                        womanList = selectPeopleProjectBeans.get(m).getNewList();

                                                        if (selectPeopleProjectBeans.get(m).getItemType() == 2) {
                                                            List<SelectPeopleProjectItemBean> newwomanList = selectPeopleProjectBeans.get(m).getNewList();

                                                            List<SelectPeopleProjectItemBean.Players> womanPlayList = new ArrayList<>();

                                                            for (int u = 0; u < newwomanList.size(); u++) {
                                                                List<SelectPeopleProjectItemBean.Players> playersList = newwomanList.get(u).getPlayersList();
                                                                womanPlayList.addAll(playersList);
                                                                SelectPeopleProjectItemBean selectPeopleProjectItemBean = womanList.get(u);
                                                                selectPeopleProjectItemBean.setPlayersList(newwomanList.get(u).getPlayersList());
                                                                womanList.set(u, selectPeopleProjectItemBean);
                                                            }
                                                        }
                                                    }
                                                    if (selectPeopleProjectBeans.get(m).getSexType().equals("4")) {
                                                        /*男子团体*/
                                                        List<SelectPeopleProjectItemBean> newmanList = selectPeopleProjectBeans.get(m).getNewList();
                                                        List<SelectPeopleProjectItemBean.Players> manPlayList = new ArrayList<>();
                                                        manList = selectPeopleProjectBeans.get(m).getNewList();
                                                        for (int ll = 0; ll < newmanList.size(); ll++) {
                                                            List<SelectPeopleProjectItemBean.Players> playersList = newmanList.get(ll).getPlayersList();
                                                            manPlayList.addAll(playersList);
                                                            SelectPeopleProjectItemBean selectPeopleProjectItemBean = manList.get(ll);
                                                            selectPeopleProjectItemBean.setPlayersList(newmanList.get(ll).getPlayersList());
                                                            manList.set(ll, selectPeopleProjectItemBean);
                                                        }
                                                        womanList = selectPeopleProjectBeans.get(m).getNewList();
                                                        if (selectPeopleProjectBeans.get(m).getItemType() == 2) {
                                                            List<SelectPeopleProjectItemBean> newwomanList = selectPeopleProjectBeans.get(m).getNewList();
                                                            List<SelectPeopleProjectItemBean.Players> womanPlayList = new ArrayList<>();
                                                            for (int u = 0; u < newwomanList.size(); u++) {
                                                                List<SelectPeopleProjectItemBean.Players> playersList = newwomanList.get(u).getPlayersList();
                                                                womanPlayList.addAll(playersList);
                                                                SelectPeopleProjectItemBean selectPeopleProjectItemBean = womanList.get(u);
                                                                selectPeopleProjectItemBean.setPlayersList(newwomanList.get(u).getPlayersList());
                                                                womanList.set(u, selectPeopleProjectItemBean);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (i == 1) {
                                            /*男子双打*/
                                            if (manList.size() > 0 && manList.get(0).getPlayersList().size() > 0) {
                                                boolean isComplete = false;
                                                for (int mm = 0; mm < manList.size(); mm++) {
                                                    if (manList.get(mm).getPlayersList().size() == 0) {
                                                        isComplete = true;
                                                    }
                                                }
                                                if (isComplete) {
                                                    toast("请完善空团体或者删除多余的团体队伍");
                                                    return;
                                                } else {
                                                    intent1.putExtra("manList", (Serializable) manList);
                                                    intent1.putExtra(ChooseAthleteMajorActivity.CLICK_POSITION, clickPosition);
                                                    intent1.putExtra(ChooseAthleteMajorActivity.OWNER_TYPE, 1);
                                                    intent1.putExtra(ChooseAthleteMajorActivity.MATCH_TITLE, projectName + " " + matchTitle);
                                                    intent1.putExtra(ChooseAthleteMajorActivity.CLUB_ID, "" + teamTitleId);
                                                    intent1.putExtra(ChooseAthleteMajorActivity.SEX_TYPE, "" + 1);
                                                    intent1.putExtra(ChooseAthleteMajorActivity.MATCH_ID, "" + matchId);
                                                    intent1.putExtra(ChooseAthleteMajorActivity.PROJECT_ID, "" + selectPeopleProjectBeans.get(clickPosition).getItemProjectId());
                                                    List<String> list = new ArrayList<String>(projectIdSet);
                                                    intent1.putExtra("projectList", (Serializable) list);
                                                    intent1.putExtra("selectPeopleProjectBeans", (Serializable) selectPeopleProjectBeans);//10
                                                    intent1.putExtra(SelectAthletesSingleActivity.CLICK_POJECT_ID, itemProjectId);
                                                }
                                                startActivityForResult(intent1, 4);
                                            } else {
                                                toast("请先选择团体人数");
                                            }
                                        } else if (i == 2) {
                                            /*女子双打*/
                                            if (womanList.size() > 0 && womanList.get(0).getPlayersList().size() > 0) {
                                                boolean isComplete = false;
                                                for (int mm = 0; mm < womanList.size(); mm++) {
                                                    if (womanList.get(mm).getPlayersList().size() == 0) {
                                                        isComplete = true;
                                                    }
                                                }
                                                if (isComplete) {
                                                    toast("请完善空团体或者删除多余的团体队伍");
                                                    return;
                                                } else {
                                                    intent1.putExtra("womanList", (Serializable) womanList);
                                                    intent1.putExtra(ChooseAthleteMajorActivity.CLICK_POSITION, clickPosition);
                                                    intent1.putExtra(ChooseAthleteMajorActivity.OWNER_TYPE, 2);
                                                    intent1.putExtra(ChooseAthleteMajorActivity.MATCH_TITLE, projectName + " " + matchTitle);
                                                    intent1.putExtra(ChooseAthleteMajorActivity.CLUB_ID, "" + teamTitleId);
                                                    intent1.putExtra(ChooseAthleteMajorActivity.MATCH_ID, "" + matchId);
                                                    intent1.putExtra(ChooseAthleteMajorActivity.SEX_TYPE, "" + 2);

                                                    intent1.putExtra(ChooseAthleteMajorActivity.PROJECT_ID,
                                                            "" + selectPeopleProjectBeans.get(clickPosition).getItemProjectId());
                                                    List<String> list = new ArrayList<String>(projectIdSet);
                                                    intent1.putExtra("projectList", (Serializable) list);
                                                    intent1.putExtra("selectPeopleProjectBeans", (Serializable) selectPeopleProjectBeans);//10
                                                    intent1.putExtra(SelectAthletesSingleActivity.CLICK_POJECT_ID, itemProjectId);
                                                    startActivityForResult(intent1, 4);
                                                }
                                            } else {
                                                toast("请先选择团体人数");
                                            }
                                        } else if (i == 4) {
                                            /*混双*/
                                            if ((manList.size() > 0 && manList.get(0).getPlayersList().size() > 0) && (womanList.size() > 0
                                                    && womanList.get(0).getPlayersList().size() > 0)) {


                                                intent1.putExtra("manList", (Serializable) manList);
                                                intent1.putExtra("womanList", (Serializable) womanList);
                                                intent1.putExtra(ChooseAthleteMajorActivity.CLICK_POSITION, clickPosition);
                                                intent1.putExtra(ChooseAthleteMajorActivity.MATCH_TITLE, projectName + " " + matchTitle);
                                                intent1.putExtra(ChooseAthleteMajorActivity.OWNER_TYPE, 3);
                                                intent1.putExtra(ChooseAthleteMajorActivity.CLUB_ID, "" + teamTitleId);
                                                intent1.putExtra(ChooseAthleteMajorActivity.MATCH_ID, "" + matchId);
                                                intent1.putExtra(ChooseAthleteMajorActivity.SEX_TYPE, "" + 4);

                                                intent1.putExtra(ChooseAthleteMajorActivity.PROJECT_ID,
                                                        "" + selectPeopleProjectBeans.get(clickPosition).getItemProjectId());
                                                List<String> list = new ArrayList<String>(projectIdSet);
                                                intent1.putExtra("projectList", (Serializable) list);
                                                intent1.putExtra("selectPeopleProjectBeans", (Serializable) selectPeopleProjectBeans);//10
                                                intent1.putExtra(SelectAthletesSingleActivity.CLICK_POJECT_ID, itemProjectId);
                                                startActivityForResult(intent1, 5);
                                            } else {
                                                toast("请先选择团体人数");
                                            }
                                        }

                                    } else {
                                        toast("请先选择教练员");
                                    }
                                } else {
                                    toast("请先选择领队");
                                }
                                break;
                            case R.id.tv_feizhuanye_shuangda://业余双打  ChooseNoAthleteMajorActivity
                                if (leaderPlayers != null && leaderPlayers.size() > 0) {
                                    if (coachPlayers != null && coachPlayers.size() > 0) {
                                        ownerType = 5;
                                        clickPosition = position;
                                        //   业余  双打  男子双打 女子双打 不区分团体  符合年龄条件和性别条件  所有符合的人数总和/2
                                        //   混合  混合双打  所有符合年龄/2
                                        //   混双    一男一女  但是没有 从团体选  人数最少的性别

                                        clickPosition = position;
                                        long projectId = selectPeopleProjectAdapter.getData().get(position).getProjectId();
                                        showLoading();
                                        mViewModel.chooseShuangDaJoinMatchPlayer("" + matchId, "" + teamTitleId, "" + projectId);

                                    } else {
                                        toast("请先选择教练员");
                                    }
                                } else {
                                    toast("请先选择领队");
                                }
                                break;
                        }

                    }
                });

                //                initApplyForClub();
            }
        });

        mViewModel.chooseJoinMatchPlayerLiveData.observe(this, new Observer<ResponseBean<ChooseJoinMatchUserBean>>() {
            @Override
            public void onChanged(ResponseBean<ChooseJoinMatchUserBean> responseBean) {
                dismissLoading();
                if (responseBean.getData() != null && responseBean.getData().getPlayers().size() > 0) {
                    switch (ownerType) {
                        case 3://混合单打
                            // 请求接口
                            SelectPeopleProjectBean selectPeopleProjectBean = selectPeopleProjectAdapter.getData().get(clickPosition);
                            String itemProjectId = selectPeopleProjectBean.getItemProjectId();


                            List<SelectPeopleProjectItemBean.Players> playersList = selectPeopleProjectBean.getNewList().get(0).getPlayersList();
                            List<ChooseJoinMatchUserBean.PlayersBean> players = responseBean.getData().getPlayers();

                            if (playersList.size() > 0) {
                                for (int n = 0; n < players.size(); n++) {
                                    for (int m = 0; m < playersList.size(); m++) {
                                        long userId = playersList.get(m).getUserId();
                                        if (userId==players.get(n).getUserId()) {
                                            ChooseJoinMatchUserBean.PlayersBean playersBean = players.get(n);
                                            playersBean.setSelect(true);
                                            players.set(n, playersBean);
                                        }
                                    }
                                }
                            }
                            Intent intent2 = new Intent(ClubTeamRegisterChannelActivity.this, SelectAthletesSingleActivity.class);
                            intent2.putExtra(SelectAthletesSingleActivity.OWN_ER_TYPE, 1);
                            intent2.putExtra(SelectAthletesSingleActivity.OWN_IN_TYPE, 3);
                            intent2.putExtra(SelectAthletesSingleActivity.OWN_POSITION, clickPosition);
                            intent2.putExtra(SelectAthletesSingleActivity.CLUB_ID, "" + teamTitleId);//俱乐部id
                            if (TextUtils.isEmpty(selectPeopleProjectBeans.get(clickPosition).getProjectName())) {
                                intent2.putExtra(SelectAthletesSingleActivity.ITEM_TITLE, "" + selectPeopleProjectBeans.get(clickPosition).getItemTitle());//俱乐部id
                            } else {
                                intent2.putExtra(SelectAthletesSingleActivity.ITEM_TITLE, "" + selectPeopleProjectBeans.get(clickPosition).getProjectName()
                                        + " " + selectPeopleProjectBeans.get(clickPosition).getItemTitle());//俱乐部id
                            }
                            intent2.putExtra(SelectAthletesSingleActivity.MATCH_ID, "" + matchId);
                            List<String> list = new ArrayList<String>(projectIdSet);
                            intent2.putExtra("projectList", (Serializable) list);
                            intent2.putExtra(SelectAthletesSingleActivity.PROJECT_ID, "" + selectPeopleProjectBeans.get(clickPosition).getItemProjectId());
                            intent2.putExtra("data", (Serializable) responseBean.getData().getPlayers());
                            intent2.putExtra("selectPeopleProjectBeans", (Serializable) selectPeopleProjectBeans);//10
                            intent2.putExtra(SelectAthletesSingleActivity.CLICK_POJECT_ID, itemProjectId);


                            startActivityForResult(intent2, 3);
                            break;
                    }
                } else {
                    if (!responseBean.getErrorCode().equals("200")) {
                        toast("" + responseBean.getMessage());
                    } else {
                        if (choosePlayerPosition == 0) {
                            choosePlayerPosition = 1;
                        } else {
                            toast("暂无选择的人");
                        }
                    }
                }
            }
        });
        /*微信*/
        mViewModel.clubApplyWxMatchLiveData.observe(this, new Observer<ResponseBean<WXPayBean>>() {
            @Override
            public void onChanged(ResponseBean<WXPayBean> responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    PayFactory payFactory = new PayFactory();
                    payFactory.WxPay(ClubTeamRegisterChannelActivity.this, responseBean.getData());
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        /*支付宝*/
        mViewModel.clubApplyZfbMatchLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    PayFactory payFactory = new PayFactory();
                    payFactory.AliPay((String) responseBean.getData(), ClubTeamRegisterChannelActivity.this, new PayFactory.PayListener() {
                        public void onPayListener(String s, boolean isOk) {
                            if (isOk) {
                                new MessageDialogBuilder(ClubTeamRegisterChannelActivity.this)
                                        .setMessage("您已成功支付提交报名申请，预祝您在本次比赛" +
                                                "取得一个好成绩")
                                        .setTvTitle("支付成功")
                                        .setCancelAble(false)
                                        .setTvCancel("取消")
                                        .setBtnCancleHint(false)
                                        .setTvSure("确定")
                                        .setSureListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
//                                                TCAgent.onEvent(ClubTeamRegisterChannelActivity.this, "ClubTeamRegisterChannel_payWay_zfb");
                                                finish();
                                            }
                                        })
                                        .show();
                            } else {
                                toast("" + responseBean.getMessage());
                                finish();
                            }
                        }
                    });
                } else {
                    if (responseBean.getErrorCode().equals("200")) {
                        new MessageDialogBuilder(ClubTeamRegisterChannelActivity.this)
                                .setMessage("您已成功支付提交报名申请，预祝您在本次比赛" +
                                        "取得一个好成绩")
                                .setTvTitle("支付成功")
                                .setCancelAble(false)
                                .setTvCancel("取消")
                                .setBtnCancleHint(false)
                                .setTvSure("确定")
                                .setSureListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //                                        TCAgent.onEvent(ClubTeamRegisterChannelActivity.this, "ClubTeamRegisterChannel_payWay_zfb");
                                        finish();
                                    }
                                })
                                .show();
                    } else {
                        toast("" + responseBean.getMessage());
                    }
                }
            }
        });
        /*针对团体单独调用接口*/
        mViewModel.tuantiManLiveData.observe(this, new Observer<ResponseBean<ChooseJoinMatchUserBean>>() {
            @Override
            public void onChanged(ResponseBean<ChooseJoinMatchUserBean> responseBean) {
                tuantiCount = tuantiCount + 1;
                Log.e(TAG, "onChanged: 调用接口====" + tuantiCount);
                if (responseBean.getData() != null && responseBean.getData().getPlayers().size() > 0) {
                    for (int i = 0; i < selectPeopleProjectBeans.size(); i++) {
                        Log.e(TAG, "onChanged: projectId====" + selectPeopleProjectBeans.get(i).getProjectId());

                        if (responseBean.getData().getItemId() == selectPeopleProjectBeans.get(i).getProjectId()) {
                            Log.e(TAG, "onChanged: 相等的参数" + selectPeopleProjectBeans.get(i).getProjectId());
                            SelectPeopleProjectBean selectPeopleProjectBean = selectPeopleProjectBeans.get(i);
                            selectPeopleProjectBean.setTuantiPlayers(responseBean.getData().getPlayers());
                            selectPeopleProjectBean.setTuantiOtherPlayers(responseBean.getData().getPlayers());
                            selectPeopleProjectBeans.set(i, selectPeopleProjectBean);
                            Log.e(TAG, "onChanged: man  位置position=====" + i);
                            Log.e(TAG, "onChanged: manList===" + new Gson().toJson(selectPeopleProjectBeans));

                        }
                    }
                    selectPeopleProjectAdapter.setNewData(selectPeopleProjectBeans);
                    Log.e(TAG, "buka: onChanged: 数据大于0");
                } else {
                    if (!responseBean.getErrorCode().equals("200")) {
                        toast("" + responseBean.getMessage());
                        Log.e(TAG, "buka: onChanged: 返回200  无数据");
                    } else {
                        Log.e(TAG, "buka: onChanged: 暂无选择的人 ids===" + responseBean.getData().getItemId());
                    }
                }
                if (tuantiCount == competitionTotalCount) {
                    dismissLoading();
                    Log.e(TAG, "onChanged: competitionTotalCount===" + competitionTotalCount);
                } else {
                    Log.e(TAG, "onChanged: tuantiCount===" + tuantiCount + "competitionTotalCount===" + competitionTotalCount);
                }
            }
        });

        /*针对业余双打 掉接口*/
        mViewModel.shuangDaLiveData.observe(this, new Observer<ResponseBean<ChooseJoinMatchUserBean>>() {
            @Override
            public void onChanged(ResponseBean<ChooseJoinMatchUserBean> responseBean) {
                dismissLoading();
                if (responseBean.getData() != null && responseBean.getData().getPlayers().size() > 0) {
                    List<ChooseJoinMatchUserBean.PlayersBean> players = responseBean.getData().getPlayers();
                    if (players.size() > 0) {

                        Intent intent = new Intent();
                        SelectPeopleProjectBean selectPeopleProjectBean = selectPeopleProjectAdapter.getData().get(clickPosition);
                        String itemProjectId = selectPeopleProjectBean.getItemProjectId();


                        String sexType = selectPeopleProjectBean.getSexType();
                        intent.setClass(ClubTeamRegisterChannelActivity.this, ChooseNoAthleteMajorActivity.class);
                        intent.putExtra(ChooseNoAthleteMajorActivity.CLICK_POSITION, clickPosition);
                        intent.putExtra(ChooseNoAthleteMajorActivity.USER_SEX_TYPE, sexType);
                        if (TextUtils.isEmpty(selectPeopleProjectBean.getProjectName())) {
                            intent.putExtra(ChooseNoAthleteMajorActivity.ITEAM_TITLE, selectPeopleProjectBean.getItemTitle());
                        } else {
                            intent.putExtra(ChooseNoAthleteMajorActivity.ITEAM_TITLE, selectPeopleProjectBean.getProjectName() + " " + selectPeopleProjectBean.getItemTitle());
                        }
                        intent.putExtra("data", (Serializable) players);
                        Log.e(TAG, "onChanged: players size====="+players.size() );
                        intent.putExtra(ChooseNoAthleteMajorActivity.MATCH_ID, "" + matchId);
                        intent.putExtra(ChooseNoAthleteMajorActivity.CLUB_ID, "" + teamTitleId);
                        intent.putExtra(ChooseNoAthleteMajorActivity.PROJECT_ID, "" + selectPeopleProjectBean.getItemProjectId());
                        intent.putExtra(ChooseNoAthleteMajorActivity.GASONSTR, "" + new Gson().toJson(players));
                        List<String> list = new ArrayList<String>(projectIdSet);
                        intent.putExtra("projectList", (Serializable) list);
                        intent.putExtra("selectPeopleProjectBeans", (Serializable) selectPeopleProjectBeans);//10
                        intent.putExtra(SelectAthletesSingleActivity.CLICK_POJECT_ID, itemProjectId);


                        startActivityForResult(intent, 5);
                    } else {
                        toast("暂无报名人员");
                    }
                } else {
                    if (!responseBean.getErrorCode().equals("200")) {
                        toast("" + responseBean.getMessage());
                    } else {
                        if (choosePlayerPosition == 0) {
                            choosePlayerPosition = 1;
                        } else {
                            toast("暂无选择的人");
                        }
                    }
                }
            }
        });
    }

    //团体人数处理
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void selectTuantiData(int position, int waiPosition) {
        /*选择逻辑 判断*/
        clickPosition = waiPosition;

        duiwuPosition = position;
        SelectPeopleProjectBean selectPeopleProjectBeanmyBean = selectPeopleProjectBeans.get(clickPosition);
        String itemProjectId = selectPeopleProjectBeanmyBean.getItemProjectId();//项目的id


        clickProjectId = selectPeopleProjectBeanmyBean.getItemProjectId();
        List<SelectPeopleProjectItemBean> newList = selectPeopleProjectBeanmyBean.getNewList();
        SelectPeopleProjectItemBean selectPeopleProjectItemBean12 = selectPeopleProjectBeanmyBean.getNewList().get(duiwuPosition);
        List<ChooseJoinMatchUserBean.PlayersBean> tuantiOtherPlayers = selectPeopleProjectBeanmyBean.getTuantiOtherPlayers();
        List<ChooseJoinMatchUserBean.PlayersBean> selectTuanPlayers = new ArrayList<>();
        String sexType = selectPeopleProjectBeanmyBean.getSexType();
        Log.e(TAG, "selectTuantiData: " + new Gson().toJson(selectPeopleProjectBeanmyBean));
        if (duiwuPosition == 0) {
            for (int o = 0; o < tuantiOtherPlayers.size(); o++) {
                tuantiOtherPlayers.get(o).setSelect(false);
            }
            selectTuanPlayers.addAll(tuantiOtherPlayers);
            for (int mm = 0; mm < newList.size(); mm++) {
                SelectPeopleProjectItemBean selectPeopleProjectItemBean = newList.get(mm);
                if (mm == duiwuPosition) {
                    List<SelectPeopleProjectItemBean.Players> playersList = selectPeopleProjectItemBean.getPlayersList();
                    for (int n = 0; n < playersList.size(); n++) {
                        long userId = playersList.get(n).getUserId();
                        for (int k = 0; k < selectTuanPlayers.size(); k++) {
                            long userId1 = selectTuanPlayers.get(k).getUserId();
                            if (userId1==userId) {
                                ChooseJoinMatchUserBean.PlayersBean playersBean = selectTuanPlayers.get(k);
                                playersBean.setSelect(true);
                                selectTuanPlayers.set(k, playersBean);
                            }
                        }
                    }
                } else {
                    List<SelectPeopleProjectItemBean.Players> playersList = selectPeopleProjectItemBean.getPlayersList();
                    for (int mmm = 0; mmm < playersList.size(); mmm++) {
                        long userId = playersList.get(mmm).getUserId();
                        for (int nnn = 0; nnn < selectTuanPlayers.size(); nnn++) {
                            long userId1 = selectTuanPlayers.get(nnn).getUserId();
                            if (userId1==userId) {
                                selectTuanPlayers.remove(nnn);
                            }
                        }
                    }

                }
            }
            Log.e(TAG, "selectTuantiData: selectTuanPlayers===" + new Gson().toJson(selectTuanPlayers));
            Intent intent = new Intent(ClubTeamRegisterChannelActivity.this, SelectPlayersForTeamAndSinglesActivity.class);
            intent.putExtra(SelectPlayersForTeamAndSinglesActivity.TEAM_TITLE, "" + selectPeopleProjectItemBean12.getName());
            intent.putExtra(SelectPlayersForTeamAndSinglesActivity.DUIWU_NAME, "-队伍" + (duiwuPosition + 1));
            intent.putExtra(SelectPlayersForTeamAndSinglesActivity.DUIWU_POSITION, duiwuPosition);
            intent.putExtra(SelectPlayersForTeamAndSinglesActivity.CLICK_POSITION, clickPosition);
            intent.putExtra("sex_type", "" + sexType);

            intent.putExtra(SelectPlayersForTeamAndSinglesActivity.MATCH_ID, matchId);
            intent.putExtra(SelectPlayersForTeamAndSinglesActivity.CLUB_ID, teamTitleId);
            intent.putExtra(SelectPlayersForTeamAndSinglesActivity.PROJECT_ID, clickProjectId);

            List<String> list = new ArrayList<String>(projectIdSet);
            intent.putExtra("projectList", (Serializable) list);
            intent.putExtra("data", (Serializable) selectTuanPlayers);//10
            intent.putExtra("selectPeopleProjectBeans", (Serializable) selectPeopleProjectBeans);//10
            intent.putExtra(SelectPlayersForTeamAndSinglesActivity.CLICK_POJECT_ID, "" + clickProjectId);

            startActivityForResult(intent, 111);
        } else {
            /*先判断 上个是否有数据*/
            int size = selectPeopleProjectBeanmyBean.getNewList().size();
            SelectPeopleProjectItemBean selectPeopleProjectItemBean2 = selectPeopleProjectBeanmyBean.getNewList().get(duiwuPosition - 1);
            List<SelectPeopleProjectItemBean.Players> playersList = selectPeopleProjectItemBean2.getPlayersList();
            if (playersList.size() > 0) {
                List<ChooseJoinMatchUserBean.PlayersBean> alreadyPlayer = new ArrayList<>();
                if (size == 1) {
                    for (int i = 0; i < playersList.size(); i++) {
                        ChooseJoinMatchUserBean.PlayersBean choose = new ChooseJoinMatchUserBean.PlayersBean();
                        choose.setUserId(playersList.get(i).getUserId());
                        choose.setSex("" + playersList.get(i).getSex());
                        choose.setUserName("" + playersList.get(i).getUserName());
                        alreadyPlayer.add(choose);
                    }
                } else {
                    for (int i = 0; i < size; i++) {
                        SelectPeopleProjectItemBean selectPeopleProjectItemBean1 = selectPeopleProjectBeanmyBean.getNewList().get(i);
                        if (i == duiwuPosition) {
                            //                            List<SelectPeopleProjectItemBean.Players> playersList1 = selectPeopleProjectItemBean1.getPlayersList();
                            //                            for (int mmmm = 0; mmmm < playersList1.size(); mmmm++) {
                            //                                playersList1.get(mmmm).setSelect(true);
                            //                            }
                        } else {
                            List<SelectPeopleProjectItemBean.Players> playersList1 = selectPeopleProjectItemBean1.getPlayersList();
                            if (playersList1.size() > 0) {
                                for (int m = 0; m < playersList1.size(); m++) {

                                    ChooseJoinMatchUserBean.PlayersBean choose = new ChooseJoinMatchUserBean.PlayersBean();
                                    choose.setUserId(playersList1.get(m).getUserId());
                                    choose.setSex("" + playersList1.get(m).getSex());
                                    choose.setUserName("" + playersList1.get(m).getUserName());
                                    alreadyPlayer.add(choose);


                                }
                            }
                        }

                    }
                }
                List<ChooseJoinMatchUserBean.PlayersBean> tuantiAllPlayers = selectPeopleProjectBeanmyBean.getTuantiOtherPlayers();//全部的人数
                List<ChooseJoinMatchUserBean.PlayersBean> tuantiMyAllPlayers = new ArrayList<>();//全部的人数
                List<ChooseJoinMatchUserBean.PlayersBean> tuantiMyOwnAllPlayers = new ArrayList<>();//全部的人数
                for (int i = 0; i < tuantiAllPlayers.size(); i++) {
                    ChooseJoinMatchUserBean.PlayersBean choose = new ChooseJoinMatchUserBean.PlayersBean();
                    choose.setUserId(tuantiAllPlayers.get(i).getUserId());
                    choose.setSex("" + tuantiAllPlayers.get(i).getSex());
                    choose.setUserName("" + tuantiAllPlayers.get(i).getUserName());
                    tuantiMyAllPlayers.add(choose);//336 9400  880
                }
                //并集
                //list1.addAll(list2);
                //交集
                //list1.retainAll(list2);
                //差集
                //list1.removeAll(list2);
                tuantiMyOwnAllPlayers.addAll(tuantiMyAllPlayers);
                boolean b = tuantiMyAllPlayers.removeAll(alreadyPlayer);
                SelectPeopleProjectItemBean selectPeopleProjectItemBean1 = selectPeopleProjectBeanmyBean.getNewList().get(duiwuPosition);
                List<SelectPeopleProjectItemBean.Players> playersList1 = selectPeopleProjectItemBean1.getPlayersList();

                for (int k = 0; k < tuantiMyAllPlayers.size(); k++) {
                    for (int l = 0; l < playersList1.size(); l++) {
                        long userId1 = tuantiMyAllPlayers.get(k).getUserId();
                        long userId = playersList1.get(l).getUserId();
                        if (userId==userId1) {
                            ChooseJoinMatchUserBean.PlayersBean playersBean = tuantiMyAllPlayers.get(k);
                            playersBean.setSelect(true);
                            tuantiMyAllPlayers.set(k, playersBean);
                        }
                    }

                }
                Intent intent = new Intent(ClubTeamRegisterChannelActivity.this, SelectPlayersForTeamAndSinglesActivity.class);
                intent.putExtra(SelectPlayersForTeamAndSinglesActivity.TEAM_TITLE, "" + selectPeopleProjectBeanmyBean.getProjectName() + " " + selectPeopleProjectBeanmyBean.getItemTitle());
                intent.putExtra(SelectPlayersForTeamAndSinglesActivity.DUIWU_NAME, "-队伍" + (duiwuPosition + 1));
                intent.putExtra(SelectPlayersForTeamAndSinglesActivity.DUIWU_POSITION, duiwuPosition);
                intent.putExtra(SelectPlayersForTeamAndSinglesActivity.CLICK_POSITION, clickPosition);
                intent.putExtra("sex_type", sexType);
                intent.putExtra(SelectPlayersForTeamAndSinglesActivity.MATCH_ID, matchId);
                intent.putExtra(SelectPlayersForTeamAndSinglesActivity.CLUB_ID, teamTitleId);
                intent.putExtra(SelectPlayersForTeamAndSinglesActivity.PROJECT_ID, "" + selectPeopleProjectBeanmyBean.getItemProjectId());
                List<String> list = new ArrayList<String>(projectIdSet);
                intent.putExtra("projectList", (Serializable) list);
                intent.putExtra("data", (Serializable) tuantiMyAllPlayers);//5
                intent.putExtra("selectPeopleProjectBeans", (Serializable) selectPeopleProjectBeans);//10
                intent.putExtra(SelectPlayersForTeamAndSinglesActivity.CLICK_POJECT_ID, "" + clickProjectId);
                startActivityForResult(intent, 111);

            } else {
                toast("请先选择上层队伍数据");
            }
        }
    }

    List<MatchOrderInfoBean> teamRegisterSubmitBeanList = new ArrayList<>();
    TeamRegisterSubmitAdapter teamRegisterSubmitAdapter;

    /*申请*/
    private void initApplyForClub() {


        clickType = -1;
        teamRegisterSubmitBeanList.clear();

        mBinding.rvSubmitApply.removeItemDecoration(mSixDiD);

        mBinding.rvSubmitApply.addItemDecoration(mSixDiD);
        teamRegisterSubmitAdapter = new TeamRegisterSubmitAdapter(
                ClubTeamRegisterChannelActivity.this,
                width, mMySixDid);
        mBinding.rvSubmitApply.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvSubmitApply.setAdapter(teamRegisterSubmitAdapter);
        List<SelectPeopleProjectBean> data = selectPeopleProjectAdapter.getData();

        baomingNum = 0;
        shiSuMaxNum = 0;
        //1 团体 2 单打  3 双打   4 混双（和业余双打一样）
        List<MatchOrderInfoBean.PlayersBean> playersBeanList;

        for (int i = 0; i < data.size(); i++) {
            int itemProjectType = Integer.parseInt(data.get(i).getItemProjectType());
            MatchOrderInfoBean matchOrderInfoBean = new MatchOrderInfoBean();
            SelectPeopleProjectBean selectPeopleProjectBean = data.get(i);
            playersBeanList = new ArrayList<>();
            switch (itemProjectType) {
                case 0://领队
                    try {
                        matchOrderInfoBean.setCardType(0);
                        MatchOrderInfoBean.LeaderBean leaderBean = new MatchOrderInfoBean.LeaderBean();
                        SelectPeopleProjectItemBean selectPeopleProjectItemBean = selectPeopleProjectBean.getNewList().get(0);
                        SelectPeopleProjectItemBean.Players players = selectPeopleProjectItemBean.getPlayersList().get(0);
                        leaderBean.setSex(players.getSex());
                        leaderBean.setUserId(players.getUserId());
                        leaderBean.setUserName(players.getUserName());
                        matchOrderInfoBean.setLeader(leaderBean);
                        matchOrderInfoBean.setMatchTitle("领队");
                        shiSuMaxNum = shiSuMaxNum + 1;
                    } catch (Exception e) {
                        toast("领队人员异常");

                    }

                    break;
                case 1://1 教练员
                    try {
                        matchOrderInfoBean.setCardType(1);
                        List<MatchOrderInfoBean.RefereeBean> refereeBeanList = new ArrayList<>();
                        List<SelectPeopleProjectItemBean.Players> playersList = selectPeopleProjectBean.getNewList().get(0).getPlayersList();
                        int peopleNum = 0;
                        for (int o = 0; o < playersList.size(); o++) {
                            MatchOrderInfoBean.RefereeBean refereeBean = new MatchOrderInfoBean.RefereeBean();
                            refereeBean.setSex(playersList.get(o).getSex());
                            refereeBean.setUserId(playersList.get(o).getUserId());
                            refereeBean.setUserName(playersList.get(o).getUserName());
                            refereeBeanList.add(refereeBean);
                            peopleNum = peopleNum + 1;
                        }
                        shiSuMaxNum = shiSuMaxNum + peopleNum;
                        matchOrderInfoBean.setMatchTitle("带队教练员");
                        matchOrderInfoBean.setReferee(refereeBeanList);
                    } catch (Exception e) {
                        toast("教练员人员异常");

                    }

                    break;
                case 2://2 团体
                    try {
                        if (selectPeopleProjectBean.getNewList().size() > 0) {
                            matchOrderInfoBean.setCardType(2);
                            matchOrderInfoBean.setMatchTitle("" + selectPeopleProjectBean.getProjectName() + " " + selectPeopleProjectBean.getItemTitle());

                            List<SelectPeopleProjectItemBean> newList = selectPeopleProjectBean.getNewList();
                            MatchOrderInfoBean.PlayersBean playersBean = new MatchOrderInfoBean.PlayersBean();
                            playersBean.setType("1");
                            List<MatchOrderInfoBean.PlayersBean.TeamsBean> teamsBeanList = new ArrayList<>();
                            playersBean.setItemTitle("" + "" + selectPeopleProjectBean.getProjectName() + " " + selectPeopleProjectBean.getItemTitle());
                            int tuantiPeopleNum = 0;
                            for (int m = 0; m < newList.size(); m++) {

                                MatchOrderInfoBean.PlayersBean.TeamsBean teamsBean = new MatchOrderInfoBean.PlayersBean.TeamsBean();
                                SelectPeopleProjectItemBean selectPeopleProjectItemBean1 = newList.get(m);
                                List<SelectPeopleProjectItemBean.Players> playersList1 = selectPeopleProjectItemBean1.getPlayersList();
                                if (playersList1.size() > 0) {
                                    String round = CommonUtilis.numberToChinese(m + 1);
                                    teamsBean.setTeamTitle("队伍" + round);
                                    List<MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean> playerList = new ArrayList<>();
                                    for (int n = 0; n < playersList1.size(); n++) {
                                        MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean playerBean = new MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean();

                                        playerBean.setSex("" + playersList1.get(n).getSex());
                                        playerBean.setUserId(playersList1.get(n).getUserId());
                                        playerBean.setUserName("" + playersList1.get(n).getUserName());
                                        playerList.add(playerBean);
                                        tuantiPeopleNum = tuantiPeopleNum + 1;
                                    }
                                    teamsBean.setPlayer(playerList);
                                    teamsBeanList.add(teamsBean);
                                }
                            }
                            Log.e(TAG, "initApplyForClub: 团体num===" + tuantiPeopleNum);
                            shiSuMaxNum = shiSuMaxNum + tuantiPeopleNum;
                            baomingNum = baomingNum + tuantiPeopleNum;
                            if (teamsBeanList.size() > 0) {
                                playersBean.setTeams(teamsBeanList);
                                playersBean.setItemId(Long.parseLong(selectPeopleProjectBean.getItemId()));
                                playersBean.setProjectId(Long.parseLong(selectPeopleProjectBean.getItemProjectId()));
                                playersBeanList.add(playersBean);
                            }
                        }
                    } catch (Exception e) {
                        toast("团体人员异常");

                    }


                    break;
                case 3://3  混合单打
                    try {
                        matchOrderInfoBean.setCardType(3);
                        List<MatchOrderInfoBean.RefereeBean> refereeBeanList3 = new ArrayList<>();
                        List<SelectPeopleProjectItemBean.Players> playersList3 = selectPeopleProjectBean.getNewList().get(0).getPlayersList();
                        int peopleNum3 = 0;
                        for (int o = 0; o < playersList3.size(); o++) {
                            MatchOrderInfoBean.RefereeBean refereeBean = new MatchOrderInfoBean.RefereeBean();
                            refereeBean.setSex(playersList3.get(o).getSex());
                            refereeBean.setUserId(playersList3.get(o).getUserId());
                            refereeBean.setUserName(playersList3.get(o).getUserName());
                            refereeBeanList3.add(refereeBean);
                            peopleNum3 = peopleNum3 + 1;
                        }
                        shiSuMaxNum = shiSuMaxNum + peopleNum3;
                        baomingNum = baomingNum + peopleNum3;

                        if (TextUtils.isEmpty(selectPeopleProjectBean.getProjectName())) {
                            matchOrderInfoBean.setMatchTitle("" + selectPeopleProjectBean.getItemTitle());
                        } else {
                            matchOrderInfoBean.setMatchTitle("" + selectPeopleProjectBean.getProjectName() + " " + selectPeopleProjectBean.getItemTitle());
                        }
                        matchOrderInfoBean.setItemId(Long.valueOf(selectPeopleProjectBean.getProjectId()));
                        matchOrderInfoBean.setReferee(refereeBeanList3);
                    } catch (Exception e) {
                        toast("混合单打人员异常");

                    }

                    break;


                case 4:////双打专业
                    try {
                        matchOrderInfoBean.setCardType(4);
                        if (TextUtils.isEmpty(selectPeopleProjectBean.getProjectName())) {
                            matchOrderInfoBean.setMatchTitle("" + selectPeopleProjectBean.getItemTitle());
                        } else {
                            matchOrderInfoBean.setMatchTitle("" + selectPeopleProjectBean.getProjectName() + " " + selectPeopleProjectBean.getItemTitle());
                        }

                        if (selectPeopleProjectBean.getNewList().size() > 0) {
                            matchOrderInfoBean.setCardType(4);
                            int tuantiPeopleNum = 0;
                            List<SelectPeopleProjectItemBean> newList = selectPeopleProjectBean.getNewList();
                            MatchOrderInfoBean.PlayersBean playersBean = new MatchOrderInfoBean.PlayersBean();
                            playersBean.setType("3");
                            List<MatchOrderInfoBean.PlayersBean.TeamsBean> teamsBeanList = new ArrayList<>();
                            if (TextUtils.isEmpty(selectPeopleProjectBean.getProjectName())) {
                                playersBean.setItemTitle("" + selectPeopleProjectBean.getItemTitle());
                            } else {
                                playersBean.setItemTitle("" + selectPeopleProjectBean.getProjectName() + " " + selectPeopleProjectBean.getItemTitle());

                            }
                            for (int m = 0; m < newList.size(); m++) {
                                MatchOrderInfoBean.PlayersBean.TeamsBean teamsBean = new MatchOrderInfoBean.PlayersBean.TeamsBean();
                                SelectPeopleProjectItemBean selectPeopleProjectItemBean1 = newList.get(m);
                                List<SelectPeopleProjectItemBean.Players> playersList1 = selectPeopleProjectItemBean1.getPlayersList();
                                String round = CommonUtilis.numberToChinese(m + 1);
                                teamsBean.setTeamTitle("队伍" + round);
                                List<MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean> playerList = new ArrayList<>();
                                for (int n = 0; n < playersList1.size(); n++) {
                                    MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean playerBean = new MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean();
                                    String leftOneName = playersList1.get(n).getLeftOneName();
                                    if (!TextUtils.isEmpty(leftOneName)) {
                                        tuantiPeopleNum = tuantiPeopleNum + 1;
                                    }
                                    String rightOneName = playersList1.get(n).getRightOneName();
                                    if (!TextUtils.isEmpty(rightOneName)) {
                                        tuantiPeopleNum = tuantiPeopleNum + 1;
                                    }
                                    playerBean.setSex("" + playersList1.get(n).getLeftSex());
                                    playerBean.setUserId(playersList1.get(n).getLeftUserId());
                                    playerBean.setUserName("" + playersList1.get(n).getLeftOneName());
                                    playerList.add(playerBean);
                                    MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean playerBean2 = new MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean();

                                    playerBean2.setSex("" + playersList1.get(n).getRightSex());
                                    playerBean2.setUserId(playersList1.get(n).getRightUserId());
                                    playerBean2.setUserName("" + playersList1.get(n).getRightOneName());
                                    playerList.add(playerBean2);
                                }
                                teamsBean.setPlayer(playerList);
                                teamsBeanList.add(teamsBean);
                            }
                            playersBean.setTeams(teamsBeanList);
                            playersBean.setItemId(selectPeopleProjectBean.getProjectId());
                            playersBean.setProjectId(Long.parseLong(selectPeopleProjectBean.getItemProjectId()));
                            playersBeanList.add(playersBean);
                            shiSuMaxNum = shiSuMaxNum + tuantiPeopleNum;
                            baomingNum = baomingNum + tuantiPeopleNum;
                            Log.e(TAG, "initApplyForClub: 双打专业num=" + tuantiPeopleNum);
                        } else {

                            matchOrderInfoBean.setCardType(4);
                            MatchOrderInfoBean.PlayersBean playersBean = new MatchOrderInfoBean.PlayersBean();
                            playersBean.setType("3");
                            List<MatchOrderInfoBean.PlayersBean.TeamsBean> teamsBeanList = new ArrayList<>();
                            if (TextUtils.isEmpty(selectPeopleProjectBean.getProjectName())) {
                                playersBean.setItemTitle("" + selectPeopleProjectBean.getItemTitle());
                            } else {
                                playersBean.setItemTitle("" + selectPeopleProjectBean.getProjectName() + " " + selectPeopleProjectBean.getItemTitle());
                            }
                            playersBean.setTeams(teamsBeanList);
                            playersBean.setItemId(selectPeopleProjectBean.getProjectId());
                            playersBean.setProjectId(Long.parseLong(selectPeopleProjectBean.getItemProjectId()));
                            playersBeanList.add(playersBean);
                        }

                    } catch (Exception e) {
                        toast("双打专业人员异常");

                    }


                    break;
                case 5://双打非专业
                    try {

                        matchOrderInfoBean.setCardType(5);
                        if (TextUtils.isEmpty(selectPeopleProjectBean.getProjectName())) {
                            matchOrderInfoBean.setMatchTitle("" + selectPeopleProjectBean.getItemTitle());
                        } else {
                            matchOrderInfoBean.setMatchTitle("" + selectPeopleProjectBean.getProjectName() + " " + selectPeopleProjectBean.getItemTitle());
                        }
                        if (selectPeopleProjectBean.getNewList().size() > 0) {
                            matchOrderInfoBean.setCardType(5);
                            List<SelectPeopleProjectItemBean> newList = selectPeopleProjectBean.getNewList();
                            MatchOrderInfoBean.PlayersBean playersBean = new MatchOrderInfoBean.PlayersBean();
                            playersBean.setType("4");
                            int tuantiPeopleNum = 0;
                            List<MatchOrderInfoBean.PlayersBean.TeamsBean> teamsBeanList = new ArrayList<>();
                            if (TextUtils.isEmpty(selectPeopleProjectBean.getProjectName())) {
                                playersBean.setItemTitle("" + selectPeopleProjectBean.getItemTitle());
                            } else {
                                playersBean.setItemTitle("" + selectPeopleProjectBean.getProjectName() + " " + selectPeopleProjectBean.getItemTitle());

                            }
                            for (int m = 0; m < newList.size(); m++) {

                                MatchOrderInfoBean.PlayersBean.TeamsBean teamsBean = new MatchOrderInfoBean.PlayersBean.TeamsBean();
                                SelectPeopleProjectItemBean selectPeopleProjectItemBean1 = newList.get(m);
                                List<SelectPeopleProjectItemBean.Players> playersList1 = selectPeopleProjectItemBean1.getPlayersList();
                                String round = CommonUtilis.numberToChinese(m + 1);
                                teamsBean.setTeamTitle("队伍" + round);
                                List<MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean> playerList = new ArrayList<>();
                                for (int n = 0; n < playersList1.size(); n++) {
                                    MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean playerBean;
                                    String leftOneName = playersList1.get(n).getLeftOneName();
                                    if (!TextUtils.isEmpty(leftOneName)) {
                                        tuantiPeopleNum = tuantiPeopleNum + 1;
                                        playerBean = new MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean();
                                        playerBean.setSex("" + playersList1.get(n).getSex());
                                        playerBean.setUserId(playersList1.get(n).getLeftUserId());
                                        playerBean.setUserName("" + playersList1.get(n).getLeftOneName());
                                        playerList.add(playerBean);
                                    }
                                    String rightOneName = playersList1.get(n).getRightOneName();
                                    if (!TextUtils.isEmpty(rightOneName)) {
                                        tuantiPeopleNum = tuantiPeopleNum + 1;
                                        playerBean = new MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean();
                                        playerBean.setSex("" + playersList1.get(n).getSex());
                                        playerBean.setUserId(playersList1.get(n).getRightUserId());
                                        playerBean.setUserName("" + playersList1.get(n).getRightOneName());
                                        playerList.add(playerBean);
                                    }

                                }
                                teamsBean.setPlayer(playerList);
                                teamsBeanList.add(teamsBean);
                            }
                            playersBean.setTeams(teamsBeanList);
                            playersBean.setItemId(Long.parseLong(selectPeopleProjectBean.getItemId()));
                            playersBean.setProjectId(Long.parseLong(selectPeopleProjectBean.getItemProjectId()));
                            shiSuMaxNum = shiSuMaxNum + tuantiPeopleNum;
                            baomingNum = baomingNum + tuantiPeopleNum;

                            playersBeanList.add(playersBean);
                        } else {
                            matchOrderInfoBean.setCardType(5);
                            List<SelectPeopleProjectItemBean> newList = selectPeopleProjectBean.getNewList();
                            MatchOrderInfoBean.PlayersBean playersBean = new MatchOrderInfoBean.PlayersBean();
                            playersBean.setType("4");
                            List<MatchOrderInfoBean.PlayersBean.TeamsBean> teamsBeanList = new ArrayList<>();
                            if (TextUtils.isEmpty(selectPeopleProjectBean.getProjectName())) {
                                playersBean.setItemTitle("" + selectPeopleProjectBean.getItemTitle());
                            } else {
                                playersBean.setItemTitle("" + selectPeopleProjectBean.getProjectName() + " " + selectPeopleProjectBean.getItemTitle());

                            }
                            MatchOrderInfoBean.PlayersBean.TeamsBean teamsBean = new MatchOrderInfoBean.PlayersBean.TeamsBean();
                            teamsBean.setId(selectPeopleProjectBean.getProjectId());
                            if (TextUtils.isEmpty(selectPeopleProjectBean.getProjectName())) {
                                teamsBean.setTeamTitle("" + selectPeopleProjectBean.getItemTitle());
                            } else {
                                teamsBean.setTeamTitle("" + selectPeopleProjectBean.getProjectName() + " " + selectPeopleProjectBean.getItemTitle());
                            }
                            teamsBean.setPlayer(new ArrayList<>());
                            teamsBeanList.add(new MatchOrderInfoBean.PlayersBean.TeamsBean());
                            playersBean.setTeams(teamsBeanList);
                            playersBean.setItemId(Long.parseLong(selectPeopleProjectBean.getItemId()));
                            playersBean.setProjectId(Long.parseLong(selectPeopleProjectBean.getItemProjectId()));
                            playersBeanList.add(playersBean);
                        }
                    } catch (Exception e) {
                        toast("双打非专业人员异常");

                    }
                    break;
            }
            int cardType = matchOrderInfoBean.getCardType();
            if (cardType == 0 || cardType == 1 || cardType == 3) {
                if (cardType == 3) {
                    List<MatchOrderInfoBean.RefereeBean> referee = matchOrderInfoBean.getReferee();
                    if (referee.size() > 0) {
                        matchOrderInfoBean.setPlayers(playersBeanList);
                        teamRegisterSubmitBeanList.add(matchOrderInfoBean);
                    }
                } else {
                    matchOrderInfoBean.setPlayers(playersBeanList);
                    teamRegisterSubmitBeanList.add(matchOrderInfoBean);
                }
            } else {
                if (playersBeanList.size() > 0) {
                    matchOrderInfoBean.setPlayers(playersBeanList);
                    teamRegisterSubmitBeanList.add(matchOrderInfoBean);
                }
            }
        }
        if (teamRegisterSubmitBeanList.size() == 2) {
            ToastUtils.showToast(ClubTeamRegisterChannelActivity.this, "请选择参赛人员");
            return;
        } else {
            mBinding.llSelectClub.setVisibility(View.GONE);
            mBinding.llSelectCoachTeam.setVisibility(View.GONE);
            mBinding.llSubmitApply.setVisibility(View.VISIBLE);
            /*让选中人数可见*/
            mBinding.tvEventInformation.setTextColor(Color.parseColor("#333333"));
            mBinding.viewEventInformation.setBackgroundResource(R.drawable.shape_competition_big_gray_circle);
            mBinding.tvChargingSet.setTextColor(Color.parseColor("#ff4795ed"));
            mBinding.viewChargingSet.setBackgroundResource(R.drawable.shape_competition_blue_circle);
            mBinding.tvCompetitionItem.setTextColor(Color.parseColor("#333333"));
            mBinding.viewCompetitionItem.setBackgroundResource(R.drawable.shape_competition_big_gray_circle);

        }
        List<MatchOrderInfoBean> removeList = new ArrayList<>();
        for (int i = 0; i < teamRegisterSubmitBeanList.size(); i++) {
            MatchOrderInfoBean matchOrderInfoBean = teamRegisterSubmitBeanList.get(i);
            if (matchOrderInfoBean.getCardType() == 4) {
                Log.e(TAG, "initApplyForClub: " + teamRegisterSubmitBeanList.get(i).getMatchTitle());
                MatchOrderInfoBean.PlayersBean playersBean = matchOrderInfoBean.getPlayers().get(0);
                if (playersBean != null && playersBean.getTeams() != null && playersBean.getTeams().size() == 0) {
                    Log.e(TAG, "initApplyForClub: 需要 移除" + teamRegisterSubmitBeanList.get(i).getMatchTitle());
                    //                    teamRegisterSubmitBeanList.remove(i);
                    removeList.add(teamRegisterSubmitBeanList.get(i));
                }

            }
        }

        teamRegisterSubmitBeanList.removeAll(removeList);//取出来差集
        teamRegisterSubmitAdapter.setNewData(teamRegisterSubmitBeanList);
        teamRegisterSubmitAdapter.notifyDataSetChanged();
        Log.e(TAG, "initApplyForClub: " + new Gson().toJson(teamRegisterSubmitBeanList));
        /*判断是否收费*/
        if (queryMatchApplyInfoBean != null) {
            replaceChargeEat = queryMatchApplyInfoBean.getReplaceChargeEat();//是否需要食宿费
            replaceCharge = Integer.parseInt(queryMatchApplyInfoBean.getReplaceCharge());//是否代收报名费  1 是需要 0 是不需要
            teamFree = Integer.parseInt(queryMatchApplyInfoBean.getTeamFree());
            if (replaceCharge != 1 && replaceChargeEat != 1) {
                mBinding.rlHavePayBaoming.setVisibility(View.GONE);
                mBinding.rlNoHavePayBaoming.setVisibility(View.VISIBLE);
                mBinding.rlNeedEat.setVisibility(View.GONE);
                mBinding.rlNotNeedEat.setVisibility(View.VISIBLE);
                allMoney = 0;
                mBinding.tvMoney.setText("");
                mBinding.tvHeji.setVisibility(View.GONE);
                mBinding.llPayWay.setVisibility(View.GONE);
            } else {
                mBinding.llPayWay.setVisibility(View.VISIBLE);
                mBinding.rlHavePayBaoming.setVisibility(View.GONE);
                mBinding.rlNoHavePayBaoming.setVisibility(View.GONE);
                mBinding.rlNeedEat.setVisibility(View.GONE);
                mBinding.rlNotNeedEat.setVisibility(View.GONE);
                /*需要食宿费*/
                if (replaceChargeEat == 1) {
                    mBinding.rlNeedEat.setVisibility(View.VISIBLE);
                    mBinding.tvShisuMoney.setText("" + "¥" + queryMatchApplyInfoBean.getEatFree() + "/人/天;" + ""
                            + "共" + queryMatchApplyInfoBean.getDaysCount() + "天");
                    mBinding.rlSelectPeople.setVisibility(View.VISIBLE);
                    mBinding.llShisuShow.setVerticalGravity(View.GONE);
                }
                /*是否需要待收报名费*/
                if (replaceCharge == 1) {
                    //还得判断有没有俱乐部交钱（有没有选中俱乐部 新建赛事的时候）   在判断这次支付 俱乐部有没有交钱
                    mBinding.rlHavePayBaoming.setVisibility(View.VISIBLE);
                    if (teamFree == 0) {
                        /*没有交*/
                        mBinding.tvBaomingMoney.setText("¥" + queryMatchApplyInfoBean.getRegistrationCount() + "/俱乐部;"
                                + "¥" + queryMatchApplyInfoBean.getOwnFree() + "" + "/人");
                    } else {
                        mBinding.tvBaomingMoney.setText(
                                "¥" + queryMatchApplyInfoBean.getOwnFree() + "" + "/人");
                    }
                    mBinding.tvBaomingPeople.setText("共" + baomingNum + "人");
                    baomingMoney = queryMatchApplyInfoBean.getRegistrationCount() + queryMatchApplyInfoBean.getOwnFree() * baomingNum;
                    if (teamFree == 0) {
                        allMoney = baomingMoney + queryMatchApplyInfoBean.getRegistrationCount();
                    } else {
                        allMoney = baomingMoney;
                    }
                    mBinding.tvMoney.setText("¥" + allMoney);
                }

            }

        } else {
            toast("返回数据为null");
        }

    }

    /*提交订单*/
    private void commitOrder(long ids) {
        ClubApplyMatchBean clubApplyMatchBean = new ClubApplyMatchBean();
        clubApplyMatchBean.setId("" + ids);
        List<MatchOrderInfoBean> data = teamRegisterSubmitAdapter.getData();
        MatchOrderInfoBean matchOrderLeader = data.get(0);
        MatchOrderInfoBean matchOrderCoach = data.get(1);
        clubApplyMatchBean.setClickType("" + submitClickType);
        clubApplyMatchBean.setLeader("" + matchOrderLeader.getLeader().getUserId());
        List<MatchOrderInfoBean.RefereeBean> referee = matchOrderCoach.getReferee();
        List<String> refree = new ArrayList<>();
        for (int i = 0; i < referee.size(); i++) {
            refree.add("" + referee.get(i).getUserId());
        }
        clubApplyMatchBean.setMatchId(matchId);
        clubApplyMatchBean.setEnrollId("" + teamTitleId);
        clubApplyMatchBean.setPersonCount("" + baomingNum);
        if (eatCount == 0) {
            clubApplyMatchBean.setNeedEat("0");
        } else {
            clubApplyMatchBean.setNeedEat("1");
        }
        clubApplyMatchBean.setEatCount("" + eatCount);
        if (submitClickType == 1) {
            clubApplyMatchBean.setFreeType("");
        } else {
            clubApplyMatchBean.setFreeType("" + payWay);
        }
        clubApplyMatchBean.setReferee(refree);
        List<ClubApplyMatchBean.PlayersBean> commitPlayList = new ArrayList<>();
        List<MatchOrderInfoBean> myData = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getMatchTitle().equals("领队")) {

            } else if (data.get(i).getMatchTitle().equals("带队教练员")) {

            } else {
                myData.add(data.get(i));
            }

        }

        for (int i = 0; i < myData.size(); i++) {
            int cardType = myData.get(i).getCardType();

            List<MatchOrderInfoBean.PlayersBean> players = myData.get(i).getPlayers();
            ClubApplyMatchBean.PlayersBean playersBean = new ClubApplyMatchBean.PlayersBean();
            if (cardType == 1) {


            } else if (cardType == 3) {
                List<MatchOrderInfoBean.RefereeBean> referee1 = myData.get(i).getReferee();
                playersBean.setItemTitle("" + myData.get(i).getMatchTitle());
                playersBean.setItemId("" + myData.get(i).getItemId());
                playersBean.setType("" + 2);
                List<ClubApplyMatchBean.PlayersBean.TeamsBean> teamClubList = new ArrayList<>();
                ClubApplyMatchBean.PlayersBean.TeamsBean teamsBean = new ClubApplyMatchBean.PlayersBean.TeamsBean();
                List<ClubApplyMatchBean.PlayersBean.TeamsBean.PlayerBean> playerMatch = new ArrayList<>();
                for (int n = 0; n < referee1.size(); n++) {
                    ClubApplyMatchBean.PlayersBean.TeamsBean.PlayerBean playerBean = new ClubApplyMatchBean.PlayersBean.TeamsBean.PlayerBean();
                    playerBean.setId("" + referee1.get(n).getUserId());
                    playerMatch.add(playerBean);
                }
                teamsBean.setPlayer(playerMatch);
                teamsBean.setTeamTitle("" + myData.get(i).getItemTitle());
                teamClubList.add(teamsBean);
                playersBean.setTeams(teamClubList);
            } else {
                //1 团体 2  单打
                List<ClubApplyMatchBean.PlayersBean.TeamsBean> teamClubList = new ArrayList<>();
                if (players != null && players.size() > 0) {
                    //团体  双打 或者其他的
                    playersBean.setItemId(String.valueOf(players.get(0).getItemId()));
                    playersBean.setProjectId(String.valueOf(players.get(0).getProjectId()));
                    playersBean.setItemTitle(players.get(0).getItemTitle());
                    playersBean.setType(players.get(0).getType());
                    List<MatchOrderInfoBean.PlayersBean.TeamsBean> teams = players.get(0).getTeams();
                    for (int m = 0; m < teams.size(); m++) {
                        ClubApplyMatchBean.PlayersBean.TeamsBean teamsBean = new ClubApplyMatchBean.PlayersBean.TeamsBean();
                        teamsBean.setTeamTitle("" + teams.get(m).getTeamTitle());
                        List<MatchOrderInfoBean.PlayersBean.TeamsBean.PlayerBean> player = teams.get(m).getPlayer();
                        if (player != null && player.size() > 0) {
                            List<ClubApplyMatchBean.PlayersBean.TeamsBean.PlayerBean> playerMatch = new ArrayList<>();
                            for (int n = 0; n < player.size(); n++) {
                                ClubApplyMatchBean.PlayersBean.TeamsBean.PlayerBean playerBean = new ClubApplyMatchBean.PlayersBean.TeamsBean.PlayerBean();
                                playerBean.setId("" + player.get(n).getUserId());
                                playerMatch.add(playerBean);
                            }
                            teamsBean.setPlayer(playerMatch);
                        } else {
                            teamsBean.setPlayer(new ArrayList<>());
                        }
                        teamClubList.add(teamsBean);
                    }
                }
                playersBean.setTeams(teamClubList);
            }
            //   id是项目id  projectId 是组别id
            commitPlayList.add(playersBean);
        }
        clubApplyMatchBean.setPlayers(commitPlayList);
        if (submitClickType == 1) {
            commitSaveOrder = 1;
            showLoading();
            mViewModel.clubApplyMatch(clubApplyMatchBean);
        } else if (submitClickType == 2) {
            showLoading();
            commitSaveOrder = 2;
            if (payWay == 1) {
                /*微信*/
                mViewModel.clubApplyWxMatch(clubApplyMatchBean);
            } else {
                /*支付宝*/
                mViewModel.clubApplyZfbMatch(clubApplyMatchBean);
            }
        }
    }

    int replaceChargeEat;
    int replaceCharge;
    int teamFree;
    private int eatCount = 0;
    CustomDialog customDialogConfirm;

    public void showChangeEatCount() {
        customDialogConfirm = new CustomDialog(ClubTeamRegisterChannelActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_room_and_board);
        customDialogConfirm.show();
        View customView = customDialogConfirm.getCustomView();
        TextView tvSure = customView.findViewById(R.id.tv_sure);
        TextView tvCancel = customView.findViewById(R.id.tv_cancel);
        TextView tvError = customView.findViewById(R.id.tv_error);
        EditText etHintPeople = customView.findViewById(R.id.et_text_people);

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialogConfirm != null) {

                    String etPeopleNum = etHintPeople.getText().toString();
                    if (TextUtils.isEmpty(etPeopleNum)) {
                        toast("请输入人数");
                    } else {
                        eatCount = Integer.parseInt(etPeopleNum);
                        if (eatCount > shiSuMaxNum) {
                            tvError.setVisibility(View.VISIBLE);
                        } else {
                            tvError.setVisibility(View.GONE);
                            clickType = 1;
                            customDialogConfirm.dismiss();
                            mBinding.rlSelectPeople.setVisibility(View.GONE);
                            mBinding.llShisuShow.setVisibility(View.VISIBLE);
                            mBinding.tvChangePeople.setVisibility(View.VISIBLE);
                            mBinding.tvCount.setText("共" + eatCount + "人");
                            /*计算一下总金额*/
                            eatMoney = queryMatchApplyInfoBean.getEatFree() * queryMatchApplyInfoBean.getDaysCount() * eatCount;
                            allMoney = eatMoney + baomingMoney;
                            mBinding.tvMoney.setText("¥" + allMoney);
                        }
                    }
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialogConfirm != null) {
                    customDialogConfirm.dismiss();
                }

            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_select_people://选择人数
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showChangeEatCount();

                break;
            case R.id.tv_change_people://修改人数
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showChangeEatCount();

                break;
            case R.id.ll_select_club_and_team://选择俱乐部
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (checkUserClunRoleBean != null) {
                    List<CheckUserClunRoleBean.ClubInfoBean> clubInfo = checkUserClunRoleBean.getClubInfo();
                    for (int m = 0; m < clubInfo.size(); m++) {
                        if (clubInfo.get(m) == null) {
                            clubInfo.remove(m);
                        }
                    }
                    showPopMatchForm(clubInfo);
                } else {
                    toast("no Data");
                }
                break;
            case R.id.tv_next_step://选择俱乐部 点击下一步
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                /*调用接口 判断是否满足条件*/
                if (TextUtils.isEmpty(teamTitleId) || teamTitleId.equals("null")) {
                    toast("请选择俱乐部");
                    mBinding.tvEventInformation.setTextColor(Color.parseColor("#333333"));
                    mBinding.viewEventInformation.setBackgroundResource(R.drawable.shape_competition_big_gray_circle);
                    mBinding.tvChargingSet.setTextColor(Color.parseColor("#ff4795ed"));
                    mBinding.viewChargingSet.setBackgroundResource(R.drawable.shape_competition_blue_circle);
                    mBinding.tvCompetitionItem.setTextColor(Color.parseColor("#333333"));
                    mBinding.viewCompetitionItem.setBackgroundResource(R.drawable.shape_competition_big_gray_circle);
                } else {
                    mBinding.tvEventInformation.setTextColor(Color.parseColor("#333333"));
                    mBinding.viewEventInformation.setBackgroundResource(R.drawable.shape_competition_big_gray_circle);
                    mBinding.tvChargingSet.setTextColor(Color.parseColor("#ff4795ed"));
                    mBinding.viewChargingSet.setBackgroundResource(R.drawable.shape_competition_blue_circle);
                    mBinding.tvCompetitionItem.setTextColor(Color.parseColor("#333333"));
                    mBinding.viewCompetitionItem.setBackgroundResource(R.drawable.shape_competition_big_gray_circle);
                    /*先调用删除缓存的接口*/

                    showLoading();
                    mViewModel.judgeUserShiFouChongFuDeleteAll("" + matchId, "" + teamTitleId);
                }

                break;
            case R.id.tv_select_last://上一步  选择参赛人员
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.llSelectClub.setVisibility(View.VISIBLE);
                mBinding.llSelectCoachTeam.setVisibility(View.GONE);
                mBinding.llSubmitApply.setVisibility(View.GONE);
                mBinding.tvEventInformation.setTextColor(Color.parseColor("#ff4795ed"));
                mBinding.viewEventInformation.setBackgroundResource(R.drawable.shape_competition_blue_circle);
                mBinding.tvChargingSet.setTextColor(Color.parseColor("#333333"));
                mBinding.viewChargingSet.setBackgroundResource(R.drawable.shape_competition_big_gray_circle);
                mBinding.tvCompetitionItem.setTextColor(Color.parseColor("#333333"));
                mBinding.viewCompetitionItem.setBackgroundResource(R.drawable.shape_competition_big_gray_circle);
                teamRegisterSubmitBeanList.clear();
                break;


            case R.id.tv_select_next://下一步  选择参赛人员
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                List<SelectPeopleProjectBean> data2 = selectPeopleProjectAdapter.getData();
                if (data2.size() >= 2) {
                    SelectPeopleProjectBean selectPeopleProjectBean1 = data2.get(0);
                    SelectPeopleProjectBean selectPeopleProjectBean2 = data2.get(1);
                    try {
                        SelectPeopleProjectItemBean selectPeopleProjectItemBean1 = selectPeopleProjectBean1.getNewList().get(0);
                        SelectPeopleProjectItemBean.Players players = selectPeopleProjectItemBean1.getPlayersList().get(0);

                        SelectPeopleProjectItemBean selectPeopleProjectItemBean2 = selectPeopleProjectBean2.getNewList().get(0);
                        SelectPeopleProjectItemBean.Players players2 = selectPeopleProjectItemBean2.getPlayersList().get(0);
                        if (players.getUserId()==0) {
                            toast("请选择领队");
                            return;
                        }
                        if (players2.getUserId()==0) {
                            toast("请选择教练");
                            return;
                        }

                    } catch (Exception e) {
                        toast("请选择领队和教练");
                        return;
                    }

                    List<SelectPeopleProjectBean> data1 = new ArrayList<>();
                    boolean isHaveConainers = false;
                    for (int mm = 0; mm < data2.size(); mm++) {
                        if (data2.get(mm).getItemProjectType().equals("2")) {
                            List<SelectPeopleProjectItemBean> newList = data2.get(mm).getNewList();

                            if (newList != null && newList.size() > 0) {
                                for (int n = 0; n < newList.size(); n++) {
                                    List<SelectPeopleProjectItemBean.Players> playersList = newList.get(n).getPlayersList();
                                    if (playersList.size() > 0) {
                                        isHaveConainers = true;
                                        Log.e(TAG, "有值===position==" + n + "   hahahah====" + new Gson().toJson(newList.get(n).getPlayersList()));
                                    } else {
                                        Log.e(TAG, "妹值===position==" + n + "   hahahah====" + new Gson().toJson(newList.get(n).getPlayersList()));
                                    }
                                }
                            }
                            if (isHaveConainers) {
                                Log.e(TAG, "onClick: 有大于0的值");
                                data1.add(data2.get(mm));
                            } else {

                            }


                        } else {
                            data1.add(data2.get(mm));
                            Log.e(TAG, "onClick1: 添加====" + mm);
                        }
                    }
                    Log.e(TAG, "onClick: data1.size()===" + data1.size() + "data==" + new Gson().toJson(data1));
                    if (data1.size() == 2) {
                        ToastUtils.showToast(ClubTeamRegisterChannelActivity.this, "请选择参赛人员");
                        return;
                    } else {
                        /*遍历数组 判断是否 符合条件*/
                        String matchLevel = queryMatchApplyInfoBean.getMatchLevel();
                        if (matchLevel.equals("专业级")) {
                            boolean isCanContinue = false;
                            List<SelectPeopleProjectBean> data = data2;
                            /*双打 类别  cardType 4 itemprojectType wei 4      团体 类别 itemproject为2  cardType 2  sexType  projectName*/

                            for (int i = 0; i < data.size(); i++) {
                                /*先挑出来所有的双打  */
                                if (data.get(i).getCardType() == 4) {
                                    SelectPeopleProjectBean selectPeopleProjectBean = data.get(i);
                                    String projectName = selectPeopleProjectBean.getProjectName();
                                    String sexType = selectPeopleProjectBean.getSexType();//性别 1 和 2
                                    List<SelectPeopleProjectItemBean> newList = selectPeopleProjectBean.getNewList();
                                    for (int j = 0; j < data.size(); j++) {
                                        if (data.get(j).getProjectName().equals(projectName)) {
                                            if (data.get(j).getCardType() == 2) {
                                                /*团体的size*/
                                                if (sexType.equals(data.get(j).getSexType())) {
                                                    //满足这三个条件 获取playList集合数据
                                                    List<SelectPeopleProjectItemBean> newList1 = data.get(j).getNewList();
                                                    if (newList1 != null && newList1.size() > 0) {
                                                        if (newList != null && newList.size() > 0) {
                                                            if (newList.size() > newList1.size()) {
                                                                isCanContinue = true;
                                                            }
                                                        }
                                                    } else {
                                                        isCanContinue = true;
                                                    }

                                                }

                                            }
                                        }
                                    }
                                }
                            }

                            if (isCanContinue) {
                                toast("双打人数不符合团体要求");
                                return;
                            } else {
                                initApplyForClub();
                            }
                        } else {
                            initApplyForClub();
                        }
                    }

                } else {
                    toast("请选择参赛人员");
                }

                break;
            case R.id.tv_change_sign_up://修改报名
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.llSelectClub.setVisibility(View.GONE);
                mBinding.llSelectCoachTeam.setVisibility(View.VISIBLE);
                mBinding.llSubmitApply.setVisibility(View.GONE);
                mBinding.tvEventInformation.setTextColor(Color.parseColor("#333333"));
                mBinding.viewEventInformation.setBackgroundResource(R.drawable.shape_competition_big_gray_circle);
                mBinding.tvChargingSet.setTextColor(Color.parseColor("#ff4795ed"));
                mBinding.viewChargingSet.setBackgroundResource(R.drawable.shape_competition_blue_circle);
                mBinding.tvCompetitionItem.setTextColor(Color.parseColor("#333333"));
                mBinding.viewCompetitionItem.setBackgroundResource(R.drawable.shape_competition_big_gray_circle);
                break;
            case R.id.cb_pay_zfb://支付宝
            case R.id.ll_cb_pay_zfb:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.cbPayZfb.setChecked(true);
                mBinding.cbPayWx.setChecked(false);
                payWay = 2;
                break;
            case R.id.cb_pay_wx://微信
            case R.id.ll_cb_pay_wx:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.cbPayZfb.setChecked(false);
                mBinding.cbPayWx.setChecked(true);
                payWay = 1;
                break;
            case R.id.tv_save_sign_up://保存报名
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                submitClickType = 1;
                if (replaceChargeEat == 1) {
                    if (clickType == -1) {
                        toast("请选择人数");
                        return;
                    }
                }
                new MessageDialogBuilder(ClubTeamRegisterChannelActivity.this)
                        .setMessage("支付提交前可随时修改报名")
                        .setTvTitle("是否保存报名表？")
                        .setCancelAble(false)
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String, Object> gamegrade = new HashMap<String, Object>();
                                gamegrade.put("ClubTeamRegisterChannel_saveSignUp", "保存报名");//自定义参数：音乐类型，值：流行
                                MobclickAgent.onEventObject(ClubTeamRegisterChannelActivity.this, "保存报名", gamegrade);
//                                TCAgent.onEvent(ClubTeamRegisterChannelActivity.this, "ClubTeamRegisterChannel_saveSignUp");
                                showLoading();
                                mViewModel.getClubOrderId();
                            }
                        })
                        .show();
                break;
            case R.id.tv_pay_commit://支付提交
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                submitClickType = 2;
                if (replaceChargeEat == 1) {
                    if (clickType == -1) {
                        toast("请选择人数");
                        return;
                    }
                }
                if (replaceCharge == 0 && replaceChargeEat == 0) {
                    new MessageDialogBuilder(ClubTeamRegisterChannelActivity.this)
                            .setMessage("报名费用一旦提交，报名表无法修改，如有人员" +
                                    "调整，请在比赛开始当天之前在“我的-我的报名”" +
                                    "选择“我要退赛”并重新报名，报名费会在24小" +
                                    "时内退还给您")
                            .setTvTitle("是否确认支付提交报名？")
                            .setCancelAble(false)
                            .setTvCancel("取消")
                            .setBtnCancleHint(false)
                            .setTvSure("确定")
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showLoading();
                                    mViewModel.getClubOrderId();
                                }
                            })
                            .show();

                } else {
                    if (payWay == 0) {
                        toast("请选择支付方式");
                        return;
                    }
                    new MessageDialogBuilder(ClubTeamRegisterChannelActivity.this)
                            .setMessage("报名费用一旦提交，报名表无法修改，如有人员" +
                                    "调整，请在比赛开始当天之前在“我的-我的报名”" +
                                    "选择“我要退赛”并重新报名，报名费会在24小" +
                                    "时内退还给您")
                            .setTvTitle("是否确认支付提交报名？")
                            .setCancelAble(false)
                            .setTvCancel("取消")
                            .setBtnCancleHint(false)
                            .setTvSure("确定")
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showLoading();
                                    mViewModel.getClubOrderId();
                                }
                            })
                            .show();
                }
                break;
        }
    }


    //1 领队  来返回数据  2 教练员   3  混合单打  4 专业双打   5 业余双打  6 团体
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {


            List<SelectPeopleProjectBean> data1 = selectPeopleProjectAdapter.getData();
            if (data1 != null || selectPeopleProjectAdapter.getData() != null) {
                if (requestCode == 1) {//Leader
                    if (resultCode == RESULT_OK) {
                        List<ChooseJoinMatchUserBean.PlayersBean> myPlayList = (List<ChooseJoinMatchUserBean.PlayersBean>) data.getSerializableExtra("listbean");
                        leaderPlayers.add(myPlayList.get(0));

                        List<SelectPeopleProjectItemBean.Players> playersList = new ArrayList<>();
                        SelectPeopleProjectItemBean.Players players = new SelectPeopleProjectItemBean.Players();
                        players.setSelect(myPlayList.get(0).isSelect());
                        players.setSex(myPlayList.get(0).getSex());
                        players.setUserId( myPlayList.get(0).getUserId());
                        players.setUserName(myPlayList.get(0).getUserName());
                        playersList.add(players);
                        data1.get(0).getNewList().get(0).setPlayersList(playersList);
                        selectPeopleProjectAdapter.setNewData(data1);
                        selectPeopleProjectAdapter.notifyDataSetChanged();
                    }
                } else if (requestCode == 2) {
                    Log.e(TAG, "onActivityResult: add Data");
                    if (resultCode == RESULT_OK) {
                        List<ChooseJoinMatchUserBean.PlayersBean> myPlayList = (List<ChooseJoinMatchUserBean.PlayersBean>) data.getSerializableExtra("listbean");
                        coachPlayers.clear();
                        coachPlayers.addAll(myPlayList);

                        List<SelectPeopleProjectItemBean.Players> playersList = new ArrayList<>();
                        /*2 教练员*/
                        for (int i = 0; i < myPlayList.size(); i++) {
                            SelectPeopleProjectItemBean.Players players = new SelectPeopleProjectItemBean.Players();
                            players.setSelect(myPlayList.get(i).isSelect());
                            players.setSex(myPlayList.get(i).getSex());
                            players.setUserId( myPlayList.get(i).getUserId());
                            players.setUserName(myPlayList.get(i).getUserName());
                            playersList.add(players);
                        }
                        data1.get(1).getNewList().get(0).setPlayersList(playersList);
                        selectPeopleProjectAdapter.setNewData(data1);
                        selectPeopleProjectAdapter.notifyDataSetChanged();
                    }
                } else if (requestCode == 3)//混合单打
                {
                    if (resultCode == RESULT_OK) {
                        List<ChooseJoinMatchUserBean.PlayersBean> myPlayList = (List<ChooseJoinMatchUserBean.PlayersBean>) data.getSerializableExtra("listbean");
                        List<SelectPeopleProjectItemBean.Players> playersList = new ArrayList<>();
                        /*2 教练员*/
                        for (int i = 0; i < myPlayList.size(); i++) {
                            SelectPeopleProjectItemBean.Players players = new SelectPeopleProjectItemBean.Players();
                            players.setSelect(myPlayList.get(i).isSelect());
                            players.setSex(myPlayList.get(i).getSex());
                            players.setUserId(myPlayList.get(i).getUserId());
                            players.setUserName(myPlayList.get(i).getUserName());
                            playersList.add(players);
                        }
                        data1.get(clickPosition).getNewList().get(0).setPlayersList(playersList);
                        selectPeopleProjectAdapter.setNewData(data1);
                        selectPeopleProjectAdapter.notifyDataSetChanged();
                    }
                } else if (requestCode == 4) {// 4 专业双打   5 业余双打  6 团体
                    if (resultCode == RESULT_OK) {
                        List<SelectPeopleProjectItemBean> bean = (List<SelectPeopleProjectItemBean>) data.getSerializableExtra("bean");
                        int clickExtra = data.getIntExtra(ChooseAthleteMajorActivity.CLICK_POSITION, 0);

                        SelectPeopleProjectBean selectPeopleProjectBean1 = selectPeopleProjectBeans.get(clickExtra);
                        List<SelectPeopleProjectItemBean> newList = selectPeopleProjectBean1.getNewList();
                        newList.clear();
                        for (int i = 0; i < bean.size(); i++) {
                            SelectPeopleProjectItemBean selectPeopleProjectItemBean = new SelectPeopleProjectItemBean();
                            //                            SelectPeopleProjectItemBean.Players playersList = new SelectPeopleProjectItemBean.Players();
                            List<SelectPeopleProjectItemBean.Players> playersList1 = bean.get(i).getPlayersList();
                            selectPeopleProjectItemBean.setPlayersList(playersList1);
                            selectPeopleProjectItemBean.setName("" + selectPeopleProjectBean1.getItemTitle() + "-队伍" + (i + 1));
                            selectPeopleProjectItemBean.setGroupName("" + selectPeopleProjectBean1.getProjectName());
                            //                            selectPeopleProjectItemBean.setType();
                            //                            selectPeopleProjectItemBean.setSelect();
                            newList.add(selectPeopleProjectItemBean);
                        }
                        selectPeopleProjectBean1.setNewList(newList);
                        selectPeopleProjectBeans.set(clickExtra, selectPeopleProjectBean1);

                        selectPeopleProjectAdapter.setNewData(selectPeopleProjectBeans);
                        selectPeopleProjectAdapter.notifyDataSetChanged();


                        //
                    }

                } else if (requestCode == 5) {//业余双打
                    if (resultCode == RESULT_OK) {
                        List<ChooseJoinMatchUserBean.PlayersBean> myPlayList = (List<ChooseJoinMatchUserBean.PlayersBean>) data.getSerializableExtra("bean");
                        int clickExtra = data.getIntExtra(ChooseAthleteMajorActivity.CLICK_POSITION, 0);

                        List<SelectPeopleProjectItemBean.Players> playersList = new ArrayList<>();
                        for (int i = 0; i < myPlayList.size(); i++) {
                            SelectPeopleProjectItemBean.Players players = new SelectPeopleProjectItemBean.Players();
                            if (TextUtils.isEmpty(myPlayList.get(i).getUserLeftName()) || TextUtils.isEmpty(myPlayList.get(i).getUserRightName())) {

                            } else {
                                players.setSelect(myPlayList.get(i).isSelect());
                                players.setSex(myPlayList.get(i).getSex());
                                players.setUserId( myPlayList.get(i).getUserId());
                                players.setUserName(myPlayList.get(i).getUserName());
                                players.setLeftOneName("" + myPlayList.get(i).getUserLeftName());
                                players.setLeftUserId( myPlayList.get(i).getUserLeftId());
                                players.setRightUserId( myPlayList.get(i).getUserRightId());
                                players.setRightOneName("" + myPlayList.get(i).getUserRightName());
                                players.setLeftSex("" + myPlayList.get(i).getLeftSex());
                                players.setRightSex("" + myPlayList.get(i).getRightSex());
                                playersList.add(players);
                            }
                        }
                        if (data1.get(clickPosition).getNewList().size() > 0) {
                            data1.get(clickPosition).getNewList().get(0).setPlayersList(playersList);
                            selectPeopleProjectAdapter.setNewData(data1);
                            selectPeopleProjectAdapter.notifyDataSetChanged();
                        } else {
                            List<SelectPeopleProjectItemBean> newList = new ArrayList<>();
                            SelectPeopleProjectItemBean selectPeopleProjectItemBean = new SelectPeopleProjectItemBean();
                            selectPeopleProjectItemBean.setType(Integer.parseInt(data1.get(clickPosition).getItemProjectType()));
                            selectPeopleProjectItemBean.setName("" + data1.get(clickPosition).getItemTitle());
                            selectPeopleProjectItemBean.setGroupName("" + data1.get(clickPosition).getProjectName());
                            selectPeopleProjectItemBean.setPlayersList(playersList);
                            newList.add(selectPeopleProjectItemBean);
                            data1.get(clickPosition).setNewList(newList);
                            selectPeopleProjectAdapter.setNewData(data1);
                            selectPeopleProjectAdapter.notifyDataSetChanged();


                        }


                    }
                } else if (requestCode == 111) {

                    //                    if (resultCode == RESULT_OK) {
                    //                        List<SelectPlayerForTeamBean.SelectBean> myPlayList = (List<SelectPlayerForTeamBean.SelectBean>) data.getSerializableExtra("listbean");
                    //                        List<SelectPeopleProjectItemBean.Players> playersList = new ArrayList<>();
                    //                        if (myPlayList.size() > 5 || myPlayList.size() < 3) {
                    //                            toast("团队人员范围3-5");
                    //                        } else {
                    //                            /*2 教练员*/
                    //                            for (int i = 0; i < myPlayList.size(); i++) {
                    //                                SelectPeopleProjectItemBean.Players players = new SelectPeopleProjectItemBean.Players();
                    //                                players.setSelect(myPlayList.get(i).isSelect());
                    //                                players.setSex(myPlayList.get(i).getSex());
                    //                                players.setUserId("" + myPlayList.get(i).getUserId());
                    //                                players.setUserName(myPlayList.get(i).getUserName());
                    //                                playersList.add(players);
                    //                            }
                    //
                    //                            data1.get(clickPosition).getNewList().get(duiwuPosition).setPlayersList(playersList);
                    //                            selectPeopleProjectAdapter.setNewData(data1);
                    //                            selectPeopleProjectAdapter.notifyDataSetChanged();
                    //                        }
                    //                    }

                    /*团体*/
                    if (resultCode == RESULT_OK) {
                        try {
                            String sexType = data.getStringExtra("sex_type");
                            if (sexType == null) {
                                sexType = "0";
                            }
                            int mSexTyps = Integer.parseInt(sexType);
                            List<SelectPlayerForTeamBean.SelectBean> myPlayList = (List<SelectPlayerForTeamBean.SelectBean>) data.getSerializableExtra("listbean");
                            List<SelectPeopleProjectItemBean.Players> playersList = new ArrayList<>();
                            if (myPlayList.size() > 5 || myPlayList.size() < 2) {
                                toast("团队人员范围2-5");
                            } else {
                                /*2 教练员*/
                                for (int i = 0; i < myPlayList.size(); i++) {
                                    SelectPeopleProjectItemBean.Players players = new SelectPeopleProjectItemBean.Players();
                                    players.setSelect(myPlayList.get(i).isSelect());
                                    players.setSex(myPlayList.get(i).getSex());
                                    players.setUserId(myPlayList.get(i).getUserId());
                                    players.setUserName(myPlayList.get(i).getUserName());
                                    playersList.add(players);
                                }

                                data1.get(clickPosition).getNewList().get(duiwuPosition).setPlayersList(playersList);

                                String clickProjectName = data1.get(clickPosition).getProjectName();

                                for (int m = 0; m < data1.size(); m++) {
                                    if (data1.get(m).getProjectName().equals(clickProjectName)) {
                                        /*团体*/
                                        if (data1.get(m).getItemProjectType().equals("4")) {
                                            if (mSexTyps == 1) {
                                                SelectPeopleProjectBean selectPeopleProjectBean = data1.get(m);
                                                selectPeopleProjectBean.setNewList(new ArrayList<>());

                                            }
                                            if (mSexTyps == 2) {

                                                SelectPeopleProjectBean selectPeopleProjectBean = data1.get(m);
                                                selectPeopleProjectBean.setNewList(new ArrayList<>());
                                            }
                                            if (mSexTyps == 4) {
                                                SelectPeopleProjectBean selectPeopleProjectBean = data1.get(m);
                                                selectPeopleProjectBean.setNewList(new ArrayList<>());
                                            }
                                        }
                                    }
                                }

                                selectPeopleProjectAdapter.setNewData(data1);
                                selectPeopleProjectAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "onActivityResult: " + e.getMessage());
                        }
                    }
                }
            } else {
                toast("请按照规定选择人");
            }
        } catch (Exception e) {
            Log.e(TAG, "onActivityResult: " + e.getMessage());
        }
    }


    public void showPopMatchForm(List<CheckUserClunRoleBean.ClubInfoBean> clubInfo) {
        mMovieTicketWindow = new CommonPopupWindow.Builder(ClubTeamRegisterChannelActivity.this)
                .setView(R.layout.pop_select_and_team)
                .setBackGroundLevel(1f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        RecyclerView rvTeamAndDialog = view.findViewById(R.id.rv_club_and_team);
                        TeamAndClubChannelAdapter teamAndClubAdapter = new TeamAndClubChannelAdapter();
                        rvTeamAndDialog.setAdapter(teamAndClubAdapter);
                        rvTeamAndDialog.setLayoutManager(new LinearLayoutManager(ClubTeamRegisterChannelActivity.this, RecyclerView.VERTICAL, false));
                        teamAndClubAdapter.setNewData(clubInfo);
                        teamAndClubAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                boolean fastClick = isFastClick();
                                if (!fastClick) {
                                    mMovieTicketWindow.dismiss();
                                    mBinding.llTextSelect.setText("" + teamAndClubAdapter.getData().get(position).getTeamTitle());
                                    mBinding.llTextSelect.setTextColor(Color.BLACK);
                                    teamTitleId = "" + teamAndClubAdapter.getData().get(position).getId();
                                }
                            }
                        });
                    }
                })
                .setOutsideTouchable(true)//设置外层不可点击
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
        if (mMovieTicketWindow == null || !mMovieTicketWindow.isShowing()) {
            mMovieTicketWindow.setFocusable(true);// 这个很重要
            mMovieTicketWindow.showAsDropDown(mBinding.llTextContainer);
        }
    }


    /*6张图片的适配器*/
    public static class SixModelAdapter extends BaseQuickAdapter<SelectPeopleProjectItemBean.Players, BaseViewHolder> {
        private int width;
        private Context context;

        public SixModelAdapter(Context context, int width) {
            super(R.layout.item_six_module);
            this.context = context;
            this.width = width;
        }

        @Override
        protected void convert(BaseViewHolder helper, SelectPeopleProjectItemBean.Players item) {
            //设置图片圆角角度
            RoundedCorners roundedCorners = new RoundedCorners(6);
            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
            //            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
            //.apply(options)
            //            LinearLayout ll_content = helper.getView(R.id.ll_content);
            TextView tvText = helper.getView(R.id.tv_text_six_module);
            ImageView ivSex = helper.getView(R.id.iv_sex);
            tvText.setText("" + item.getUserName());
            String sex = item.getSex();
            if (Integer.parseInt(sex) == 1) {
                ivSex.setBackgroundResource(R.drawable.img_sex_little_blue);
            } else {
                ivSex.setBackgroundResource(R.drawable.img_blue_woman);
            }
            tvText.setLayoutParams(new RelativeLayout.LayoutParams((width - dip2px(context, 70)) / 5, ViewGroup.LayoutParams.WRAP_CONTENT));
            //            ImageLoader.loadRoundImage1(ivLogo, (String) item, R.drawable.ic_default_image);
        }
    }


    /*教练员的适配器*/

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public void wxCallBack(int code) {
        switch (code) {
            case 0:
                new MessageDialogBuilder(ClubTeamRegisterChannelActivity.this)
                        .setMessage("您已成功支付提交报名申请，预祝您在本次比赛" +
                                "取得一个好成绩")
                        .setTvTitle("支付成功")
                        .setCancelAble(false)
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("确定")
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String, Object> gamegrade = new HashMap<String, Object>();
                                gamegrade.put("ClubTeamRegisterChannel_payWay", "支付报名");//自定义参数：音乐类型，值：流行
                                MobclickAgent.onEventObject(ClubTeamRegisterChannelActivity.this, "支付报名", gamegrade);
//                                TCAgent.onEvent(ClubTeamRegisterChannelActivity.this, "ClubTeamRegisterChannel_payWay_wx");
                                finish();
                            }
                        })
                        .show();
                break;
            case -1:
                toast("支付异常");
                finish();
                break;
            case -2:
                toast("用户取消");
                finish();
                break;
        }
    }


}