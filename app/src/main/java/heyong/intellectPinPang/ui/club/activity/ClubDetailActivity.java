package heyong.intellectPinPang.ui.club.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.appbar.AppBarLayout;
//import com.tendcloud.tenddata.TCAgent;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.xq.fasterdialog.dialog.CustomDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.club.ClubMemberAdapter;
import heyong.intellectPinPang.bean.competition.JudgeClubContestWithStatusBean;
import heyong.intellectPinPang.bean.competition.QueryNowUserIsChargeBean;
import heyong.intellectPinPang.bean.competition.ClubTypeDataBean;
import heyong.intellectPinPang.bean.competition.XlClubInfoBean;
import heyong.intellectPinPang.databinding.ActivityClubDetailBinding;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.ui.competition.activity.PersonalInformationActivity;
import heyong.intellectPinPang.ui.homepage.activity.EventListActivity;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MapUtil;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.handong.framework.utils.ToastUtils;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;

/**
 * 是否加入    加入的身份
 * 俱乐部详情  陌生人进来 只有一个申请加入   还有一个教练员（查看成绩 创建对内比赛）
 * 负责人（创建对内比赛  编辑俱乐部 查看比赛成绩）     普通人（查看成绩）
 */
public class ClubDetailActivity extends BaseActivity<ActivityClubDetailBinding, ClubViewModel> implements View.OnClickListener {
    public static final String TAG = ClubDetailActivity.class.getSimpleName();

    public static final String CLUB_ID = "id";
    private String id = "";
    int isCharge = 0;
    List<XlClubInfoBean.RecordListBean> recordList;
    MyDividerItemDecoration mSixDiD;
    XlClubInfoBean data;
    private String clubId = "";
    List<ClubTypeDataBean> clubTypeDataBeanList;
    AppBarLayout.ScrollingViewBehavior scrollingViewBehavior;
    private String userIds = "";
    private String clubUserId = "";
    double laty;
    double latx;
    private String address = "";
    @Override
    public int getLayoutRes() {
        return R.layout.activity_club_detail;
    }

    @Override
    protected void onResume() {
        super.onResume();
        dismissLoading();
        mViewModel.getXlClubInfo(id);
        mViewModel.getClubTypeData();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        id = getIntent().getStringExtra(CLUB_ID);
        mBinding.bottomScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (mBinding.bottomScrollView.getScrollY() == 0) {
                    //顶部
                    mBinding.ivLogo.setVisibility(View.GONE);
                } else {
                    mBinding.ivLogo.setVisibility(View.VISIBLE);
                }

                View contentView = mBinding.bottomScrollView.getChildAt(0);
                if (contentView != null && contentView.getMeasuredHeight() == (mBinding.bottomScrollView.getScrollY() + mBinding.bottomScrollView.getHeight())) {
                    //底部
                }

            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ClubDetailActivity.this, 5);
        mBinding.rvClubNumber.removeItemDecoration(mSixDiD);
        mSixDiD = new MyDividerItemDecoration(ClubDetailActivity.this, 5,
                getResources().getColor(R.color.white));
        mBinding.rvClubNumber.addItemDecoration(mSixDiD);
        ClubMemberAdapter clubMemberAdapter = new ClubMemberAdapter(ClubDetailActivity.this, wdith);
        mBinding.rvClubNumber.setLayoutManager(gridLayoutManager);
        mBinding.rvClubNumber.setAdapter(clubMemberAdapter);

        clubMemberAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    long userId = clubMemberAdapter.getData().get(position).getId();
//                Intent intent = new Intent(ClubDetailActivity.this, MemberShipDetailsActivity.class);
//                intent.putExtra(MemberShipDetailsActivity.USER_ID, "" + userId);
//                startActivity(intent);
                    userIds = "" + clubMemberAdapter.getData().get(position).getUserId();
                    clubUserId = "" + userId;
                    mViewModel.queryNowUserIsCharge("" + id);
                }
            }
        });
        mViewModel.queryNowUserIsChargeLiveData.observe(this, new Observer<ResponseBean<QueryNowUserIsChargeBean>>() {
            @Override
            public void onChanged(ResponseBean<QueryNowUserIsChargeBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    QueryNowUserIsChargeBean data = responseBean.getData();
                    if (data.isInCharge()) {
                        MemberShipDetailsActivity.goActivity(ClubDetailActivity.this, "" + clubUserId, 2);
                    } else {
                        MemberShipDetailsActivity.goActivity(ClubDetailActivity.this, "" + userIds, 1);

                    }
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.quitClubLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    toast("退出成功");
                    finish();
                } else {
                    toast("" + responseBean.getMessage());
                }

            }
        });
        mViewModel.getClubTypeDataLiveData.observe(this, new Observer<ResponseBean<List<ClubTypeDataBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<ClubTypeDataBean>> loginBeanResponseBean) {
                if (loginBeanResponseBean.getErrorCode().equals("200")) {
                    clubTypeDataBeanList = loginBeanResponseBean.getData();
                } else {
                    toast("" + loginBeanResponseBean.getMessage());
                }
            }
        });
        mViewModel.judgeClubContestWithStatusLiveData.observe(this, new Observer<ResponseBean<JudgeClubContestWithStatusBean>>() {
            @Override
            public void onChanged(ResponseBean<JudgeClubContestWithStatusBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    Intent intent2 = new Intent(ClubDetailActivity.this, CreateTeamGamesActivity.class);
                    intent2.putExtra(CreateTeamGamesActivity.CLUB_ID, clubId);
                    startActivity(intent2);
                } else if (responseBean.getErrorCode().equals("100000-100022")) {
                    JudgeClubContestWithStatusBean data = responseBean.getData();
                    if (data != null) {
                        String matchType = data.getMatchType();
                        int i = Integer.parseInt(matchType);
                        if (i == 1) {
                            //单打
//                            GameUnderWayTest gameUnderWayTest=new GameUnderWayTest();
//                            gameUnderWayTest.goActivity(ClubDetailActivity.this,"" + data.getId(),"" + data.getContestTitle());

                            Intent intent = new Intent(ClubDetailActivity.this, GameUnderWayActivity.class);
                            intent.putExtra(GameUnderWayActivity.CONTEST_ID, "" + data.getId());
                            intent.putExtra(GameUnderWayActivity.MATCH_TYITLE, "" + data.getContestTitle());
                            intent.putExtra(GameUnderWayActivity.CLUB_ID, clubId);
                            startActivity(intent);
                        } else {
                            //团体
                            String contestStatus = data.getContestStatus();
//                            1进行中 2已结束 3 未开始
                            if (contestStatus.equals("1")) {
                                /*1进行中*/
                                Intent intent = new Intent(ClubDetailActivity.this, TeamResultsActivity.class);
                                intent.putExtra(TeamResultsActivity.CONTESTID, "" + data.getId());
                                intent.putExtra(TeamResultsActivity.CLUB_ID, clubId);
                                startActivity(intent);
                            } else {
                                /*3 未开始*/
                                Intent intent = new Intent(ClubDetailActivity.this, DrawLotsHostAndGuestActivity.class);
                                intent.putExtra(DrawLotsHostAndGuestActivity.IDS, data.getId());
                                intent.putExtra(TeamResultsActivity.CLUB_ID, clubId);
                                startActivity(intent);
                            }

                        }
                    } else {
                        toast("" + responseBean.getMessage());
                    }
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.deleteXlClubInfoLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    if (customDialogConfirm != null) {
                        customDialogConfirm.dismiss();
                    }
                    toast("解散成功");
                } else {
                    /*解散失败*/
                    showExitError("" + responseBean.getMessage());
                }

            }
        });
        mViewModel.askForJoinClubLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("100000-100018")) {
//                    new MessageDialogBuilderException(ClubDetailActivity.this)
//                            .setBtnCancleHint(true)
//                            .setTvSure("确定")
//                            .setBtnCancleHint(false)
//                            .setSureListener(v1 ->
//                                    finish()
//                            )
//                            .show();
                    new MessageDialogBuilder(ClubDetailActivity.this)
                            .setMessage("申请提交后需要审核，每天只可对同一\n" +
                                    "俱乐部发送5次加入申请\n" +
                                    "                            ")
                            .setTvTitle("确定要加入该俱乐部吗？")
                            .setTvSure("确定")
                            .setBtnCancleHint(false)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    toast("" + responseBean.getMessage());
                                }
                            })

                            .show();

                } else if (responseBean.getErrorCode().equals("200")) {
                    new MessageDialogBuilder(ClubDetailActivity.this)
                            .setMessage("申请提交后需要审核，每天只可对同一\n" +
                                    "俱乐部发送5次加入申请\n" +
                                    "                            ")
                            .setTvTitle("确定要加入该俱乐部吗？")
                            .setTvSure("确定")
                            .setBtnCancleHint(false)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ToastUtils.showToast(ClubDetailActivity.this, "您已成功申请，等待俱乐部审核");
                                }
                            })
                            .show();

                } else if (responseBean.getErrorCode().equals("100000-100012")) {
                    new MessageDialogBuilder(ClubDetailActivity.this)
                            .setMessage("")
                            .setTvTitle("请先完善个人信息\n")
                            .setTvCancel("取消")
                            .setTvSure("确定")
                            .setBtnCancleHint(false)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goActivity(PersonalInformationActivity.class);
                                }
                            })
                            .show();

                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.getXlClubInfoLiveData.observe(this, new Observer<ResponseBean<XlClubInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<XlClubInfoBean> xlClubInfoListBeanResponseBean) {
                if (xlClubInfoListBeanResponseBean.getErrorCode().equals("200")) {

                    data = xlClubInfoListBeanResponseBean.getData();
                    ImageLoader.loadImage(mBinding.ivClubLogo, data.getCoverLogo());
                    ImageLoader.loadImage(mBinding.ivBgLogo, data.getClubImgUrl());
                    clubId = "" + data.getId();
                    mBinding.tvIntroduce.setText("" + data.getTeamIntroduce());
                    mBinding.tvTeamTitle.setText("" + data.getTeamTitle());
                    mBinding.tvName.setText("负责人（领队）：" + data.getInCharge());
                    mBinding.tvTelNumber.setText("联系电话：" + data.getPhoneNum());
                    mBinding.tvAddress.setText("地址：" + data.getCity()+data.getDetailAddress());
                    Log.e(TAG, "onChanged:俱乐部信息=== "+new Gson().toJson(data));
                    /*截取逗号之前的*/
//                    latx = Double.parseDouble(lngLat.substring(0, lngLat.indexOf(",")));
//                    /*截取逗号之后的*/
//                    laty = Double.parseDouble(lngLat.substring(lngLat.indexOf(",")).replace(",", ""));
//                    address = gameMatchBean.getHoldCity() + " " + gameMatchBean.getVenue();

                    mBinding.tvTableNumber.setText("场馆球台数：" + data.getTablesNum());
                    recordList = data.getRecordList();
                    mBinding.tvAllNum.setText("共" + data.getMemberCount() + "人");
                    List<XlClubInfoBean.MemberListBean> memberList = data.getMemberList();
                    if (memberList != null && memberList.size() > 0) {
                        clubMemberAdapter.setNewData(memberList);
                    } else {
                        clubMemberAdapter.setNewData(new ArrayList<>());
                    }
                    try {
                        for (int i = 0; i < recordList.size(); i++) {
                            List<XlClubInfoBean.RecordListBean.RecordTypeCountBean> recordTypeCount = recordList.get(i).getRecordTypeCount();
                            if (i == 0) {
                                mBinding.tvTheChampionGroup.setText("" + recordTypeCount.get(0).getCounts());
                                mBinding.tvTheChampionDanda.setText("" + recordTypeCount.get(1).getCounts());
                                mBinding.tvTheChampionShuangda.setText("" + recordTypeCount.get(2).getCounts());
                                mBinding.tvTheChampionHunshuang.setText("" + recordTypeCount.get(3).getCounts());
                            } else if (i == 1) {
                                mBinding.tvYajunTuanti.setText("" + recordTypeCount.get(0).getCounts());
                                mBinding.tvYajunDanda.setText("" + recordTypeCount.get(1).getCounts());
                                mBinding.tvYajunShuangda.setText("" + recordTypeCount.get(2).getCounts());
                                mBinding.tvYajunHunshuang.setText("" + recordTypeCount.get(3).getCounts());
                            } else if (i == 2) {
                                mBinding.tvJijunTuanti.setText("" + recordTypeCount.get(0).getCounts());
                                mBinding.tvJijunDanda.setText("" + recordTypeCount.get(1).getCounts());
                                mBinding.tvJijunShuangda.setText("" + recordTypeCount.get(2).getCounts());
                                mBinding.tvJijunHunshuang.setText("" + recordTypeCount.get(3).getCounts());
                            } else if (i == 3) {
                                mBinding.tvDisiTuanti.setText("" + recordTypeCount.get(0).getCounts());
                                mBinding.tvDisiDanda.setText("" + recordTypeCount.get(1).getCounts());
                                mBinding.tvDisiShuangda.setText("" + recordTypeCount.get(2).getCounts());
                                mBinding.tvDisiHunshuang.setText("" + recordTypeCount.get(3).getCounts());
                            } else if (i == 4) {
                                mBinding.tvDiwuTuanti.setText("" + recordTypeCount.get(0).getCounts());
                                mBinding.tvDiwuDanda.setText("" + recordTypeCount.get(1).getCounts());
                                mBinding.tvDiwuShuangda.setText("" + recordTypeCount.get(2).getCounts());
                                mBinding.tvDiwuHunshuang.setText("" + recordTypeCount.get(3).getCounts());
                            } else if (i == 5) {
                                mBinding.tvDiliuTuanti.setText("" + recordTypeCount.get(0).getCounts());
                                mBinding.tvDiliuDanda.setText("" + recordTypeCount.get(1).getCounts());
                                mBinding.tvDiliuShuangda.setText("" + recordTypeCount.get(2).getCounts());
                                mBinding.tvDiliuHunshuang.setText("" + recordTypeCount.get(3).getCounts());
                            } else if (i == 6) {
                                mBinding.tvDiqiTuanti.setText("" + recordTypeCount.get(0).getCounts());
                                mBinding.tvDiqiDanda.setText("" + recordTypeCount.get(1).getCounts());
                                mBinding.tvDiqiShuangda.setText("" + recordTypeCount.get(2).getCounts());
                                mBinding.tvDiqiHunshuang.setText("" + recordTypeCount.get(3).getCounts());
                            } else if (i == 7) {
                                mBinding.tvDibaTuanti.setText("" + recordTypeCount.get(0).getCounts());
                                mBinding.tvDibaDanda.setText("" + recordTypeCount.get(1).getCounts());
                                mBinding.tvDibaShuangda.setText("" + recordTypeCount.get(2).getCounts());
                                mBinding.tvDibaHunshuang.setText("" + recordTypeCount.get(3).getCounts());
                            }
                        }
                    } catch (Exception e) {

                    }

                    /*判断是否先加入俱乐部*/
                    isCharge = Integer.parseInt(data.getIsCharge());
                    int joinStatus = Integer.parseInt(data.getJoinStatus());
                    if (joinStatus == 0) {
                        /*未加入*/
                        mBinding.llNotJoinIn.setVisibility(View.VISIBLE);
                        mBinding.llCoach.setVisibility(View.GONE);
                        mBinding.llRegularNumbers.setVisibility(View.GONE);
                        mBinding.llPrincipal.setVisibility(View.GONE);
                        mBinding.ivRight.setVisibility(View.GONE);
                    } else {
                        /*已加入*/
                        //牛逼的是 1  其次是2  普通人是3
                        mBinding.ivRight.setVisibility(View.VISIBLE);
                        if (isCharge == 1) {
                            mBinding.llPrincipal.setVisibility(View.VISIBLE);
                            mBinding.llNotJoinIn.setVisibility(View.GONE);
                            mBinding.llCoach.setVisibility(View.GONE);
                            mBinding.llRegularNumbers.setVisibility(View.GONE);
                            String teamType = data.getTeamType();
                            if (Integer.parseInt(teamType) == 1) {
                                mBinding.ivRight.setVisibility(View.VISIBLE);
                            } else {
                                mBinding.ivRight.setVisibility(View.GONE);

                            }
                        } else if (isCharge == 2) {
                            mBinding.llPrincipal.setVisibility(View.GONE);
                            mBinding.llNotJoinIn.setVisibility(View.GONE);
                            mBinding.llCoach.setVisibility(View.VISIBLE);
                            mBinding.llRegularNumbers.setVisibility(View.GONE);
                        } else if (isCharge == 3) {
                            mBinding.llPrincipal.setVisibility(View.GONE);
                            mBinding.llNotJoinIn.setVisibility(View.GONE);
                            mBinding.llCoach.setVisibility(View.GONE);
                            mBinding.llRegularNumbers.setVisibility(View.VISIBLE);
                        } else {
                            mBinding.ivRight.setVisibility(View.GONE);
                        }
                        /*先判断身份  然后在判断自由团体  如果是自由团体的话 才能解散  负责人 解散 功能  教练员和普通成员 是退群功能*/

                    }

                } else {
                    toast("" + xlClubInfoListBeanResponseBean.getMessage());
                }

            }
        });
    }


    /*退出俱乐部*/
    private void exitClub() {
        mViewModel.quitClub(id);
    }

    CustomDialog customDialogConfirm;

    private void exitClubDialog() {
        customDialogConfirm = new CustomDialog(ClubDetailActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_exit_club);
        customDialogConfirm.show();
        View customView = customDialogConfirm.getCustomView();
        showAskConfirmView(customView, customDialogConfirm);
    }

    private void showAskConfirmView(View customView, CustomDialog customDialog) {
        TextView tvExitClub = customView.findViewById(R.id.tv_exit_club);
        tvExitClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialog != null) {
                    customDialog.dismiss();
                }
                new MessageDialogBuilder(ClubDetailActivity.this)
                        .setMessage("")
                        .setTvTitle("是否解散俱乐部？")
                        .setTvCancel("取消")
                        .setTvSure("确定")
                        .setBtnCancleHint(false)
                        .setSureListener(v1 ->
                                getExitData()
                        )
                        .show();
            }
        });
        TextView tvCancel = customView.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialog != null) {
                    customDialog.dismiss();
                }

            }
        });

    }

    /*调用接口*/
    private void getExitData() {
        mViewModel.deleteXlClubInfo(id, data.getTeamType());
    }

    /*解散失败*/
    private void showExitError(String message) {

        new MessageDialogBuilder(ClubDetailActivity.this)
                .setMessage("" + message)
                .setTvTitle("解散失败")
                .setBtnCancleHint(true)
                .setTvSure("确定")
                .setBtnCancleHint(false)
                .setSureListener(v1 ->
                        toast("解散成功")
                )
                .show();
    }

    private void showCommit() {
//        new MessageDialogBuilderException(ClubDetailActivity.this)
//                .setSureListener(v1 ->
//                        toast("哈哈哈哈")
//                )
//                .show();


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_zhanji://战绩
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.tvZhanji.setTextColor(Color.parseColor("#ff4795ed"));
                mBinding.viewZhanji.setVisibility(View.VISIBLE);
                mBinding.tvInformation.setTextColor(Color.parseColor("#FF333333"));
                mBinding.viewInformation.setVisibility(View.GONE);
                mBinding.appbar.setExpanded(false);
                mBinding.bottomScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.bottomScrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });

                break;
            case R.id.rl_information://信息
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.tvZhanji.setTextColor(Color.parseColor("#FF333333"));
                mBinding.viewZhanji.setVisibility(View.GONE);
                mBinding.tvInformation.setTextColor(Color.parseColor("#ff4795ed"));
                mBinding.viewInformation.setVisibility(View.VISIBLE);
                mBinding.appbar.setExpanded(true);

                mBinding.bottomScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.bottomScrollView.scrollTo(0, 0);
                    }
                });
                break;
            case R.id.iv_finish:

                finish();
                break;

            case R.id.iv_logo:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.appbar.setExpanded(true);

                mBinding.bottomScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.bottomScrollView.scrollTo(0, 0);
                    }
                });
                break;
            case R.id.ll_not_join_in://申请加入
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mViewModel.askForJoinClub(clubId);


                break;
            case R.id.tv_coach_create://创建队内比赛
            case R.id.tv_create_team_games://创建队内比赛
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Map<String, Object> game = new HashMap<>();
                game.put("clubDetail_create", "创建队内比赛");//自定义参数：音乐类型，值：流行
                MobclickAgent.onEventObject(ClubDetailActivity.this, "创建队内比赛", game);
//                TCAgent.onEvent(ClubDetailActivity.this, "clubDetail_create");
                showLoading();
                mViewModel.judgeClubContestWithStatus(id);

                break;
            case R.id.tv_club_editor://编辑俱乐部
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Intent intent = new Intent(ClubDetailActivity.this, CreateClubActivity.class);
                if (data != null) {
                    intent.putExtra("data", (Serializable) data);
                }
                if (clubTypeDataBeanList != null) {
                    intent.putExtra("mydata", (Serializable) clubTypeDataBeanList);
                }
                intent.putExtra(CreateClubActivity.CLUB_TYPE, 1);
                intent.putExtra(CreateClubActivity.IDS, clubId);
                startActivity(intent);

                break;
            case R.id.tv_team_score://队内比赛成绩
            case R.id.tv_regular_numbers_score://队内比赛成绩
            case R.id.tv_team_results://队内比赛成绩
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Intent intentTeamList = new Intent(ClubDetailActivity.this, InternalGameListActivity.class);
                intentTeamList.putExtra(InternalGameListActivity.CLUB_ID, clubId);
                startActivity(intentTeamList);
                break;

            case R.id.ll_club_member://俱乐部会员列表
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Map<String, Object> gamemember = new HashMap<String, Object>();
                gamemember.put("clubDetail_member", "查看俱乐部会员");//自定义参数：音乐类型，值：流行
                MobclickAgent.onEventObject(ClubDetailActivity.this, "查看俱乐部会员", gamemember);
//                TCAgent.onEvent(ClubDetailActivity.this, "clubDetail_member");
                Intent intent1 = new Intent(ClubDetailActivity.this, ClubMemberActivity.class);
                intent1.putExtra(ClubMemberActivity.CLUB_MEMBER_ID, "" + id);
                intent1.putExtra(ClubMemberActivity.CLUB_USER_ID, "" + id);
                startActivity(intent1);

                break;
            case R.id.iv_right://解散俱乐部
                //牛逼的是 1  其次是2  普通人是3
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (isCharge == 1) {
                    exitClubDialog();
                } else if (isCharge == 2) {
                    new MessageDialogBuilder(ClubDetailActivity.this)
                            .setMessage("")
                            .setTvTitle("是否退出俱乐部？")
                            .setTvCancel("取消")
                            .setTvSure("确定")
                            .setBtnCancleHint(false)
                            .setSureListener(v1 ->
                                    exitClub()
                            )
                            .show();
                } else if (isCharge == 3) {
                    new MessageDialogBuilder(ClubDetailActivity.this)
                            .setMessage("")
                            .setTvTitle("是否退出俱乐部？")
                            .setTvCancel("取消")
                            .setTvSure("确定")
                            .setBtnCancleHint(false)
                            .setSureListener(v1 ->
                                    exitClub()
                            )
                            .show();
                }
//                exitClubDialog();
                break;
            case R.id.tv_the_champion_group://冠军团体
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {

                } catch (Exception e) {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(0).getRecordTypeCount().get(0).getProjectType(),
                            "" + recordList.get(0).getRanking());
                }

                break;
            case R.id.tv_the_champion_danda://冠军单打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(0).getRecordTypeCount().get(1).getProjectType(),
                            "" + recordList.get(0).getRanking());
                } catch (Exception e) {

                }
            case R.id.tv_the_champion_shuangda://冠军双打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(0).getRecordTypeCount().get(2).getProjectType(),
                            "" + recordList.get(0).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_the_champion_hunshuang://冠军混双
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(0).getRecordTypeCount().get(3).getProjectType(),
                            "" + recordList.get(0).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_yajun_tuanti://亚军团体
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(1).getRecordTypeCount().get(0).getProjectType(),
                            "" + recordList.get(1).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_yajun_danda://亚军单打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(1).getRecordTypeCount().get(1).getProjectType(),
                            "" + recordList.get(1).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_yajun_shuangda://亚军双打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(1).getRecordTypeCount().get(2).getProjectType(),
                            "" + recordList.get(1).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_yajun_hunshuang://亚军混双
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(1).getRecordTypeCount().get(3).getProjectType(),
                            "" + recordList.get(1).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_jijun_tuanti://季军团体
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(2).getRecordTypeCount().get(0).getProjectType(),
                            "" + recordList.get(2).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_jijun_danda://季军单打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(2).getRecordTypeCount().get(1).getProjectType(),
                            "" + recordList.get(2).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_jijun_shuangda://季军双打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(2).getRecordTypeCount().get(2).getProjectType(),
                            "" + recordList.get(2).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_jijun_hunshuang://季军混双
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(2).getRecordTypeCount().get(3).getProjectType(),
                            "" + recordList.get(2).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_disi_tuanti://第四团体
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(3).getRecordTypeCount().get(0).getProjectType(),
                            "" + recordList.get(3).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_disi_danda:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(3).getRecordTypeCount().get(1).getProjectType(),
                            "" + recordList.get(3).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_disi_shuangda:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(3).getRecordTypeCount().get(2).getProjectType(),
                            "" + recordList.get(3).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_disi_hunshuang:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(3).getRecordTypeCount().get(3).getProjectType(),
                            "" + recordList.get(3).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_diwu_tuanti:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(4).getRecordTypeCount().get(0).getProjectType(),
                            "" + recordList.get(4).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_diwu_danda:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(4).getRecordTypeCount().get(1).getProjectType(),
                            "" + recordList.get(4).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_diwu_shuangda:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(4).getRecordTypeCount().get(2).getProjectType(),
                            "" + recordList.get(4).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_diwu_hunshuang:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(4).getRecordTypeCount().get(3).getProjectType(),
                            "" + recordList.get(4).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_diliu_tuanti:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(5).getRecordTypeCount().get(0).getProjectType(),
                            "" + recordList.get(5).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_diliu_danda:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(5).getRecordTypeCount().get(1).getProjectType(),
                            "" + recordList.get(5).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_diliu_shuangda:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(5).getRecordTypeCount().get(2).getProjectType(),
                            "" + recordList.get(5).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_diliu_hunshuang:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(5).getRecordTypeCount().get(3).getProjectType(),
                            "" + recordList.get(5).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_diqi_tuanti:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(6).getRecordTypeCount().get(0).getProjectType(),
                            "" + recordList.get(6).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_diqi_danda:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(6).getRecordTypeCount().get(1).getProjectType(),
                            "" + recordList.get(6).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_diqi_shuangda:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(6).getRecordTypeCount().get(2).getProjectType(),
                            "" + recordList.get(6).getRanking());

                } catch (Exception e) {

                }
                break;
            case R.id.tv_diqi_hunshuang:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(6).getRecordTypeCount().get(3).getProjectType(),
                            "" + recordList.get(6).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_diba_tuanti:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(7).getRecordTypeCount().get(0).getProjectType(),
                            "" + recordList.get(7).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_diba_danda:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(7).getRecordTypeCount().get(1).getProjectType(),
                            "" + recordList.get(7).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_diba_shuangda:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(7).getRecordTypeCount().get(2).getProjectType(),
                            "" + recordList.get(7).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.tv_diba_hunshuang:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    EventListActivity.goActivity(ClubDetailActivity.this, id,
                            recordList.get(7).getRecordTypeCount().get(3).getProjectType(),
                            "" + recordList.get(7).getRanking());
                } catch (Exception e) {

                }
                break;
            case R.id.ll_address:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Map<String, Object> gamegrade = new HashMap<String, Object>();
                gamegrade.put("eventSignUpPage_location", "地图定位");//自定义参数：音乐类型，值：流行
                MobclickAgent.onEventObject(ClubDetailActivity.this, "地图定位", gamegrade);
//                TCAgent.onEvent(EventSignUpActivity.this, "eventSignUpPage_location");
                showMapRoute();

                break;

        }
    }
    /*点击确定和修改比分弹窗*/
    private void showMapRoute() {
        CustomDialog customDialogConfirm = new CustomDialog(ClubDetailActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_map_route);
        customDialogConfirm.show();
        View customView = customDialogConfirm.getCustomView();
        TextView tvMapGaode = customView.findViewById(R.id.tv_map_gaode);
        TextView tvMapTent = customView.findViewById(R.id.tv_map_tent);
        TextView tvMapBaidu = customView.findViewById(R.id.tv_map_baidu);

        tvMapGaode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialogConfirm != null) {
                    if (MapUtil.isGdMapInstalled()) {
                        MapUtil.openGaoDeNavi(ClubDetailActivity.this, 0, 0, "", laty, latx, address);
                    } else {
                        Toast.makeText(ClubDetailActivity.this, "尚未安装高德地图", Toast.LENGTH_SHORT).show();
                    }
                    customDialogConfirm.dismiss();
                }
            }
        });
        tvMapTent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialogConfirm != null) {
                    if (MapUtil.isTencentMapInstalled()) {
                        MapUtil.openTencentMap(ClubDetailActivity.this, 0, 0, "", laty, latx, address);
                    } else {
                        Toast.makeText(ClubDetailActivity.this, "尚未安装腾讯地图", Toast.LENGTH_SHORT).show();
                    }
                    customDialogConfirm.dismiss();
                }
            }
        });

        tvMapBaidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialogConfirm != null) {
                    if (MapUtil.isBaiduMapInstalled()) {
                        MapUtil.openBaiDuNavi(ClubDetailActivity.this, 0, 0, "", laty, latx, address);
                    } else {
                        Toast.makeText(ClubDetailActivity.this, "尚未安装百度地图", Toast.LENGTH_SHORT).show();
                    }
                    customDialogConfirm.dismiss();
                }

            }
        });

        TextView tvCancel = customView.findViewById(R.id.tv_cancel);
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

}