package heyong.intellectPinPang.ui.club.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.club.CoachFillMatchFormAdapter;
import heyong.intellectPinPang.adapter.club.CoachListAdapter;
import heyong.intellectPinPang.adapter.club.CoachListTwoAdapter;
import heyong.intellectPinPang.bean.competition.FillInMatchBaseInfoBean;
import heyong.intellectPinPang.bean.competition.MatchBaseInfoBean;
import heyong.intellectPinPang.bean.competition.SubmitTeamArrangeBean;
import heyong.intellectPinPang.bean.competition.TeamArrangePlayBean;
import heyong.intellectPinPang.databinding.ActivityCoachFillMatchFormBinding;
import heyong.intellectPinPang.event.SwipMessageEvent;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageCoachFormBuilder;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;
import heyong.intellectPinPang.widget.popupwindow.CommonPopupWindow;

/**
 * 教练员的填写对阵列表    教练员-填写对阵名单界面1   填写对阵表
 */
public class CoachFillMatchFormActivity extends BaseActivity<ActivityCoachFillMatchFormBinding, ClubViewModel> implements View.OnClickListener {
    private static final String TAG = CoachFillMatchFormActivity.class.getSimpleName();
    CoachListAdapter coachListAdapter;
    CoachListTwoAdapter coachListAdapter2;
    MyDividerItemDecoration mSixDiD;
    MyDividerItemDecoration mSixDiD2;
    CommonPopupWindow mMovieTicketWindow;
    CommonPopupWindow mShowWindow;
    MatchBaseInfoBean matchBaseInfoBean;
    SubmitTeamArrangeBean submitTeamArrangeBean;
    FillInMatchBaseInfoBean data;
    private int ownerType = 0;
    private String arrangeId = "";
    List<SubmitTeamArrangeBean.TeamArrangesBean> teamArrangesBeans;
    HashSet<String> repetitionSet = new HashSet();

    public static void goActivity(Context context, MatchBaseInfoBean matchBaseInfoBean) {
        Intent intent = new Intent();
        intent.setClass(context, CoachFillMatchFormActivity.class);
        intent.putExtra("data", (Serializable) matchBaseInfoBean);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_coach_fill_match_form;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        matchBaseInfoBean = (MatchBaseInfoBean) getIntent().getSerializableExtra("data");
        if (matchBaseInfoBean.getLeftOrRight().equals("1")) {
            if (matchBaseInfoBean.getLeftClubType().equals("1")) {
                mBinding.tvZhuke.setText("主队:");
            } else {
                mBinding.tvZhuke.setText("客队:");
            }
        } else {
            if (matchBaseInfoBean.getRightClubType().equals("1")) {
                mBinding.tvZhuke.setText("主队:");
            } else {
                mBinding.tvZhuke.setText("客队:");
            }
        }
        if (matchBaseInfoBean != null) {
            mViewModel.fillInMatchBaseInfo(matchBaseInfoBean);
        }
        arrangeId = matchBaseInfoBean.getArrangeId();
        mViewModel.getTeamArangePlayLiveData.observe(this, new Observer<ResponseBean<List<TeamArrangePlayBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<TeamArrangePlayBean>> listResponseBean) {
                /*获取人数*/
                if (listResponseBean.getData() != null && listResponseBean.getData().size() > 0) {
                    List<TeamArrangePlayBean> data = listResponseBean.getData();
                    if (data != null && data.size() > 0) {
                        TeamArrangePlayBean teamArrangePlayBean = new TeamArrangePlayBean();
                        teamArrangePlayBean.setName("取消选择");
                        data.add(teamArrangePlayBean);
                        List<TeamArrangePlayBean> dataOtherList = new ArrayList<>();
                        if (repetitionSet != null && repetitionSet.size() > 0) {
                            for (String str : repetitionSet) {
                                for (int mm = 0; mm < data.size(); mm++) {
                                    if (data.get(mm).getName().equals(str)) {
                                        Log.e(TAG, "onChanged: 等于==="+str );
                                        dataOtherList.add(data.get(mm));
                                    }
                                }
                            }
                            Log.e(TAG, "onChanged: dataOtherList="+new Gson().toJson(dataOtherList) );
                            data.removeAll(dataOtherList);//取出来差集
                        }
                    }
                    Log.e(TAG, "onChanged: data="+new Gson().toJson(data) );

                    switch (ownerType) {
                        case 1:
                            showPeoplePop(mBinding.llCoachSelectSingleOne, data, 0);
                            break;
                        case 2:
                            showPeoplePop(mBinding.llCoachDoubleOneLeft, data, 0);
                            break;
                        case 3:
                            showPeoplePop(mBinding.llCoachDoubleOneRight, data, 0);

                            break;
                        case 4:
                            showPeoplePop(mBinding.llCoachSelectSingleTwo, data, 1);

                            break;
                        case 5:
                            showPeoplePop(mBinding.rlDoubleTwoLeft, data, 1);

                            break;
                        case 6:
                            showPeoplePop(mBinding.rlDoubleTwoRight, data, 1);

                            break;
                        case 7:
                            showPeoplePop(mBinding.llCoachSelectSingleThree, data, 2);
                            break;
                        case 8:
                            showPeoplePop(mBinding.rlDoubleThreeLeft, data, 2);

                            break;
                        case 9:
                            showPeoplePop(mBinding.rlDoubleThreeRight, data, 2);

                            break;
                        case 10:
                            showPeoplePop(mBinding.llCoachSingleFour, data, 3);
                            break;
                        case 11:
                            showPeoplePop(mBinding.rlDoubleFourLeft, data, 3);
                            break;
                        case 12:
                            showPeoplePop(mBinding.rlDoubleFourRight, data, 3);
                            break;
                        case 13:
                            showPeoplePop(mBinding.llCoachSingleFive, data, 4);

                            break;
                        case 14:
                            showPeoplePop(mBinding.rlDoubleFiveLeft, data, 4);

                            break;
                        case 15:
                            showPeoplePop(mBinding.rlDoubleFiveRight, data, 4);
                            break;
                    }


                } else {
                    if (listResponseBean.getErrorCode().equals("200")) {
                        toast("暂无选择的人");
                    } else {
                        toast("" + listResponseBean.getMessage());

                    }
                }
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CoachFillMatchFormActivity.this, 5);
        mBinding.rvCoachList.removeItemDecoration(mSixDiD);
        mSixDiD = new

                MyDividerItemDecoration(CoachFillMatchFormActivity.this, 5,
                getResources().

                        getColor(R.color.white));
        mBinding.rvCoachList.addItemDecoration(mSixDiD);
        //绑定manager
        mBinding.rvCoachList.setLayoutManager(gridLayoutManager);
        coachListAdapter = new

                CoachListAdapter(CoachFillMatchFormActivity.this, wdith);
        mBinding.rvCoachList.setAdapter(coachListAdapter);


        mBinding.rvSportsList.removeItemDecoration(mSixDiD2);
        mSixDiD2 = new

                MyDividerItemDecoration(CoachFillMatchFormActivity.this, 5,
                getResources().

                        getColor(R.color.white));
        mBinding.rvSportsList.addItemDecoration(mSixDiD2);
        //绑定manager
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(CoachFillMatchFormActivity.this, 5);

        mBinding.rvSportsList.setLayoutManager(gridLayoutManager2);
        coachListAdapter2 = new

                CoachListTwoAdapter(CoachFillMatchFormActivity.this, wdith);
        mBinding.rvSportsList.setAdapter(coachListAdapter2);
        coachListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    FillInMatchBaseInfoBean.CoachBean coachBean = coachListAdapter.getData().get(position);
                    showPopWindow(coachBean);
                }
            }
        });
        coachListAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                FillInMatchBaseInfoBean.PlayersBean playersBean = coachListAdapter2.getData().get(position);
//                showPlayersWindow(playersBean);
            }
        });
        mViewModel.submitTeamArrangeLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    EventBus.getDefault().post(new SwipMessageEvent());
                    new MessageDialogBuilder(CoachFillMatchFormActivity.this)
                            .setMessage("")
                            .setTvTitle("提交成功")
                            .setBtnCancleHint(true)
                            .setTvSure("返回")
                            .setSureListener(v1 ->
                                    finish()
                            )
                            .show();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.fillInMatchBaseInfoLiveData.observe(this, new Observer<ResponseBean<FillInMatchBaseInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<FillInMatchBaseInfoBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    data = responseBean.getData();
                    if (data != null) {
                        submitTeamArrangeBean = new SubmitTeamArrangeBean();
                        submitTeamArrangeBean.setAppOperationLogId(matchBaseInfoBean.getAppOperationLogId());
                        submitTeamArrangeBean.setArrangeId("" + matchBaseInfoBean.getArrangeId());
                        submitTeamArrangeBean.setLeftOrRight("" + matchBaseInfoBean.getLeftOrRight());
                        submitTeamArrangeBean.setLeftTeamId(""+matchBaseInfoBean.getLeftTeamId());
                        submitTeamArrangeBean.setRightTeamId(""+matchBaseInfoBean.getRightTeamId());
                        teamArrangesBeans = new ArrayList<>();
                        List<FillInMatchBaseInfoBean.OwnListBean> ownList = data.getOwnList();
                        for (int i = 0; i < ownList.size(); i++) {
                            int hitType = Integer.parseInt(ownList.get(i).getHitType());
                            SubmitTeamArrangeBean.TeamArrangesBean teamArrangesBean = new SubmitTeamArrangeBean.TeamArrangesBean();
                            teamArrangesBean.setHitType("" + hitType);
                            teamArrangesBean.setOwnTeamId("" + ownList.get(i).getOwnTeamId());
                            teamArrangesBean.setTeamArrangeId("" + ownList.get(i).getTeamArrangeId());
                            if (hitType == 1) {
                                teamArrangesBean.setPlayer1Set("" + ownList.get(i).getPlayer1Set());
                            } else {
                                teamArrangesBean.setPlayer1Set("" + ownList.get(i).getPlayer1Set());
                                teamArrangesBean.setPlayer2Set("" + ownList.get(i).getPlayer2Set());
                            }
                            if (i == 0) {
                                /*第一个*/
                                if (hitType == 1) {
                                    /*单打*/
                                    mBinding.llCoachSingleOne.setVisibility(View.VISIBLE);
                                    mBinding.tvOneSingleLeftName.setText("" + ownList.get(i).getPlayer1Set());
                                } else {
//                                    tv_double_two_left_name
                                    mBinding.llCoachDoubleOne.setVisibility(View.VISIBLE);
                                    mBinding.tvDoubleOneLeftName.setText("" + ownList.get(i).getPlayer1Set());
                                    mBinding.tvDoubleOneRightName.setText("" + ownList.get(i).getPlayer2Set());
                                }

                            } else if (i == 1) {
                                if (hitType == 1) {
                                    mBinding.llCoachSingleTwo.setVisibility(View.VISIBLE);
                                    mBinding.tvTwoSingleLeftName.setText("" + ownList.get(i).getPlayer1Set());
                                } else {
                                    mBinding.llCoachDoubleTwo.setVisibility(View.VISIBLE);
                                    mBinding.tvDoubleTwoLeftName.setText("" + ownList.get(i).getPlayer1Set());
                                    mBinding.tvDoubleTwoRightName.setText("" + ownList.get(i).getPlayer2Set());
                                }

                            } else if (i == 2) {
                                if (hitType == 1) {
                                    mBinding.llCoachSingleThree.setVisibility(View.VISIBLE);
                                    mBinding.tvThreeSingleLeftName.setText("" + ownList.get(i).getPlayer1Set());
                                } else {
                                    mBinding.llCoachDoubleThree.setVisibility(View.VISIBLE);
                                    mBinding.tvDoubleThreeLeftName.setText("" + ownList.get(i).getPlayer1Set());
                                    mBinding.tvDoubleThreeRightName.setText("" + ownList.get(i).getPlayer2Set());
                                }
                            } else if (i == 3) {
                                if (hitType == 1) {
                                    mBinding.llCoachSingleFour.setVisibility(View.VISIBLE);
                                    mBinding.tvFourSingleLeftName.setText("" + ownList.get(i).getPlayer1Set());

                                } else {
                                    mBinding.llCoachDoubleFour.setVisibility(View.VISIBLE);
                                    mBinding.tvDoubleFourLeftName.setText("" + ownList.get(i).getPlayer1Set());
                                    mBinding.tvDoubleFourRightName.setText("" + ownList.get(i).getPlayer2Set());
                                }
                            } else if (i == 4) {
                                if (hitType == 1) {
                                    mBinding.llCoachSingleFive.setVisibility(View.VISIBLE);
                                    mBinding.tvFiveSingleLeftName.setText("" + ownList.get(i).getPlayer1Set());
                                } else {
                                    mBinding.llCoachDoubleFive.setVisibility(View.VISIBLE);
                                    mBinding.tvDoubleFiveLeftName.setText("" + ownList.get(i).getPlayer1Set());
                                    mBinding.tvDoubleFiveRightName.setText("" + ownList.get(i).getPlayer2Set());
                                }
                            }
                            teamArrangesBeans.add(teamArrangesBean);
                        }
                        submitTeamArrangeBean.setTeamArranges(teamArrangesBeans);
                        mBinding.tvFormationName.setText("" + data.getOpponentClubName());
                        mBinding.tvOwnClubName.setText("" + data.getOwnClubName());
                        mBinding.tvTilte.setText("" + data.getTitle());

                        List<FillInMatchBaseInfoBean.CoachBean> coach = data.getCoach();
                        List<FillInMatchBaseInfoBean.PlayersBean> players = data.getPlayers();

                        coachListAdapter.setNewData(coach);

                        coachListAdapter2.setNewData(players);

                    } else {
                        toast("" + responseBean.getMessage());
                    }
                } else {
                    toast("" + responseBean.getMessage());
                }


            }
        });


    }


    private void showPopWindow(FillInMatchBaseInfoBean.CoachBean coachBean) {
        mShowWindow = new CommonPopupWindow.Builder(CoachFillMatchFormActivity.this)
                .setView(R.layout.pop_fill_match_message)
                .setBackGroundLevel(1f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        if (AntiShakeUtils.isInvalidClick(view))
                            return;
                        TextView tvUserName = view.findViewById(R.id.tv_user_name);
                        TextView tvChipaiShou = view.findViewById(R.id.tv_chipaishou);

                        TextView tvDiBan = view.findViewById(R.id.tv_diban_name);
                        TextView tvZhengShouName = view.findViewById(R.id.tv_zhengshou_name);
                        TextView tvFanshouName = view.findViewById(R.id.tv_fanshou_name);

                        tvDiBan.setText("" + coachBean.getFloorBrandName());
                        tvZhengShouName.setText("" + coachBean.getRightBrandName());
                        tvFanshouName.setText("" + coachBean.getLeftBrandName());

                        tvUserName.setText("" + coachBean.getName());
                        tvChipaiShou.setText("" + coachBean.getClapHandName() + "持拍");


                    }
                })
                .setOutsideTouchable(true)//设置外层不可点击
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        mBinding.tvCoachs.getLocationOnScreen(location);
        Log.e(TAG, "showPopWindow: " + location[0]);
        Log.e(TAG, "showPopWindow: " + location[1]);
//        View view1 = View.inflate(MovieTicketActivity.this, R.layout.activity_movie_ticket, null);
        if (mShowWindow == null || !mShowWindow.isShowing()) {
            mShowWindow.setFocusable(true);// 这个很重要
            mShowWindow.showAtLocation(mBinding.tvCoachs, Gravity.NO_GRAVITY, (location[0] + mBinding.tvCoachs.getWidth() / 2) - 60 / 2, location[1] - location[1] / 4 - 120);
        }


    }

    private void showPlayersWindow(FillInMatchBaseInfoBean.PlayersBean playersBean) {
        mShowWindow = new CommonPopupWindow.Builder(CoachFillMatchFormActivity.this)
                .setView(R.layout.pop_fill_match_message)
                .setBackGroundLevel(1f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        if (AntiShakeUtils.isInvalidClick(view))
                            return;
                        TextView tvUserName = view.findViewById(R.id.tv_user_name);
                        TextView tvChipaiShou = view.findViewById(R.id.tv_chipaishou);

                        TextView tvDiBan = view.findViewById(R.id.tv_diban_name);
                        TextView tvZhengShouName = view.findViewById(R.id.tv_zhengshou_name);
                        TextView tvFanshouName = view.findViewById(R.id.tv_fanshou_name);

                        tvDiBan.setText("" + playersBean.getFloorBrandName());
                        tvZhengShouName.setText("" + playersBean.getRightBrandName());
                        tvFanshouName.setText("" + playersBean.getLeftBrandName());

                        tvUserName.setText("" + playersBean.getName());
                        tvChipaiShou.setText("" + playersBean.getClapHandName() + "持拍");
                    }
                })
                .setOutsideTouchable(true)//设置外层不可点击
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        mBinding.tvSports.getLocationOnScreen(location);
        Log.e(TAG, "showPopWindow: " + location[0]);
        Log.e(TAG, "showPopWindow: " + location[1]);
//        View view1 = View.inflate(MovieTicketActivity.this, R.layout.activity_movie_ticket, null);
        if (mShowWindow == null || !mShowWindow.isShowing()) {
            mShowWindow.setFocusable(true);// 这个很重要
            mShowWindow.showAtLocation(mBinding.tvSports, Gravity.NO_GRAVITY, (location[0] + mBinding.tvSports.getWidth() / 2) - 60 / 2, location[1] - location[1] / 4 - 120);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                List<SubmitTeamArrangeBean.TeamArrangesBean> teamArrangesBeanList = submitTeamArrangeBean.getTeamArranges();
                boolean isCanSubmit = true;
                for (int i = 0; i < teamArrangesBeanList.size(); i++) {
                    int hitType = Integer.parseInt(teamArrangesBeanList.get(i).getHitType());
                    String player1Id = teamArrangesBeanList.get(i).getPlayer1Id();
                    String player2Id = teamArrangesBeanList.get(i).getPlayer2Id();
                    if (hitType == 1) {
                        /*单打 */
                        if (TextUtils.isEmpty(player1Id)) {
                            isCanSubmit = false;
                        }
                    } else {
                        /*双打*/
                        if (TextUtils.isEmpty(player1Id) && TextUtils.isEmpty(player2Id)) {
                            isCanSubmit = false;
                        }
                    }

                }
                if (isCanSubmit) {
                    new MessageCoachFormBuilder(CoachFillMatchFormActivity.this, submitTeamArrangeBean)
                            .setTvTitle("是否提交对阵名单？")
                            .setTvCancel("取消")
                            .setBtnCancleHint(false)
                            .setTvSure("确定")
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //表单
                                    mViewModel.submitTeamArrange(submitTeamArrangeBean);
                                }
                            })
                            .show();
                } else {
                    toast("请选择运动员");
                }
                break;
            case R.id.ll_coach_select_single_one://第一个 单打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ownerType = 1;
                if (teamArrangesBeans != null && teamArrangesBeans.size() > 0) {
                    getData("" + getOwnTeamId(0), getPlayerSetId(0), "" + arrangeId);
                } else {
                    toast("暂无数据");
                }
                break;
            case R.id.ll_coach_double_one_left://第一个 双打 左边的
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ownerType = 2;
                if (teamArrangesBeans != null && teamArrangesBeans.size() > 0) {
                    getData("" + getOwnTeamId(0), getPlayerSetId(0), "" + arrangeId);
                } else {
                    toast("暂无数据");
                }

                break;
            case R.id.ll_coach_double_one_right://第一个双打  右边的
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ownerType = 3;
                if (teamArrangesBeans != null && teamArrangesBeans.size() > 0) {
                    getData("" + getOwnTeamId(0), getPlayerSetId(0), "" + arrangeId);
                } else {
                    toast("暂无数据");
                }

                break;

            case R.id.ll_coach_select_single_two://第二个单打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ownerType = 4;
                if (teamArrangesBeans != null && teamArrangesBeans.size() > 0) {
                    getData("" + getOwnTeamId(1), getPlayerSetId(1), "" + arrangeId);
                } else {
                    toast("暂无数据");
                }

                break;
            case R.id.rl_double_two_left://第二个  双打  左边的
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ownerType = 5;
                if (teamArrangesBeans != null && teamArrangesBeans.size() > 0) {
                    getData("" + getOwnTeamId(1), getPlayerSetId(1), "" + arrangeId);
                } else {
                    toast("暂无数据");
                }

                break;
            case R.id.rl_double_two_right://第二个  双打  右边的
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ownerType = 6;
                if (teamArrangesBeans != null && teamArrangesBeans.size() > 0) {
                    getData("" + getOwnTeamId(1), getPlayerSetId(1), "" + arrangeId);
                } else {
                    toast("暂无数据");
                }
                break;
            case R.id.ll_coach_select_single_three://第三个单打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ownerType = 7;
                if (teamArrangesBeans != null && teamArrangesBeans.size() > 0) {
                    getData("" + getOwnTeamId(2), getPlayerSetId(2), "" + arrangeId);
                } else {
                    toast("暂无数据");
                }
                break;
            case R.id.rl_double_three_left://第三个  双打  左边的
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ownerType = 8;
                if (teamArrangesBeans != null && teamArrangesBeans.size() > 0) {
                    getData("" + getOwnTeamId(2), getPlayerSetId(2), "" + arrangeId);
                } else {
                    toast("暂无数据");
                }
                break;
            case R.id.rl_double_three_right://第三个  双打  右边的
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ownerType = 9;
                if (teamArrangesBeans != null && teamArrangesBeans.size() > 0) {
                    getData("" + getOwnTeamId(2), getPlayerSetId(2), "" + arrangeId);
                } else {
                    toast("暂无数据");
                }
                break;
            case R.id.ll_coach_select_single_four://第四个单打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ownerType = 10;
                if (teamArrangesBeans != null && teamArrangesBeans.size() > 0) {
                    getData("" + getOwnTeamId(3), getPlayerSetId(3), "" + arrangeId);
                } else {
                    toast("暂无数据");
                }
                break;
            case R.id.rl_double_four_left://第四个   双打 左边的
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ownerType = 11;
                if (teamArrangesBeans != null && teamArrangesBeans.size() > 0) {
                    getData("" + getOwnTeamId(3), getPlayerSetId(3), "" + arrangeId);
                } else {
                    toast("暂无数据");
                }
                break;
            case R.id.rl_double_four_right://第四个双打  右边的
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ownerType = 12;
                if (teamArrangesBeans != null && teamArrangesBeans.size() > 0) {
                    getData("" + getOwnTeamId(3), getPlayerSetId(3), "" + arrangeId);
                } else {
                    toast("暂无数据");
                }
                break;
            case R.id.ll_coach_select_single_five://第五个单打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ownerType = 13;
                if (teamArrangesBeans != null && teamArrangesBeans.size() > 0) {
                    getData("" + getOwnTeamId(4), getPlayerSetId(4), "" + arrangeId);
                } else {
                    toast("暂无数据");
                }
                break;
            case R.id.rl_double_five_left://第五个双打  左边的
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ownerType = 14;
                if (teamArrangesBeans != null && teamArrangesBeans.size() > 0) {
                    getData("" + getOwnTeamId(4), getPlayerSetId(4), "" + arrangeId);
                } else {
                    toast("暂无数据");
                }
                break;
            case R.id.rl_double_five_right://第五个双打  右边的
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ownerType = 15;
                if (teamArrangesBeans != null && teamArrangesBeans.size() > 0) {
                    getData("" + getOwnTeamId(4), getPlayerSetId(4), "" + arrangeId);
                } else {
                    toast("暂无数据");
                }
                break;


        }
    }

    private void showPeoplePop(ViewGroup llCoachSelectSingleOne, List<TeamArrangePlayBean> data, int clickPosition) {
        mMovieTicketWindow = new CommonPopupWindow.Builder(CoachFillMatchFormActivity.this)
                .setView(R.layout.pop_fill_match_form)
                .setBackGroundLevel(1f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        if (AntiShakeUtils.isInvalidClick(view))
                            return;
                        RecyclerView rvFillMatchForm = view.findViewById(R.id.rv_fill_match_form);
                        rvFillMatchForm.setLayoutManager(new LinearLayoutManager(CoachFillMatchFormActivity.this, RecyclerView.VERTICAL, false));
                        CoachFillMatchFormAdapter fillMatchFormAdapter = new CoachFillMatchFormAdapter();
                        rvFillMatchForm.setAdapter(fillMatchFormAdapter);
                        fillMatchFormAdapter.setNewData(data);
                        fillMatchFormAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                boolean fastClick = isFastClick();
                                if (!fastClick) {
                                    /*点击以后 设置 Bean 类  然后遍历 Bean类  然后统一设置值*/
                                    List<SubmitTeamArrangeBean.TeamArrangesBean> teamArrangesBeanList = submitTeamArrangeBean.getTeamArranges();
                                    SubmitTeamArrangeBean.TeamArrangesBean teamArrangesBean = teamArrangesBeanList.get(clickPosition);
                                    String name = fillMatchFormAdapter.getData().get(position).getName();
                                    String player1Name = teamArrangesBean.getPlayer1Name();
                                    String player2Name = teamArrangesBean.getPlayer2Name();
                                    Log.e(TAG, "onItemClick: name===" + name + "clickposition===" + clickPosition);
                                    String playerSet = "";
                                    String ids = "";
                                    if (name.equals("取消选择")) {
                                        ids = "";
                                        if (ownerType % 3 == 0) {
                                            repetitionSet.remove("" + player2Name);
                                            teamArrangesBean.setPlayer2Name("");
                                            teamArrangesBean.setPlayer2Id("");
                                            playerSet = teamArrangesBean.getPlayer2Set();
                                            /*第三个*/
                                        } else if (ownerType % 3 == 1) {
                                            repetitionSet.remove("" + player1Name);
                                            teamArrangesBean.setPlayer1Name("");
                                            teamArrangesBean.setPlayer1Id("");
                                            playerSet = teamArrangesBean.getPlayer1Set();
                                            /*第一个*/
                                        } else if (ownerType % 3 == 2) {
                                            /*第二个*/
                                            repetitionSet.remove("" + player1Name);
                                            teamArrangesBean.setPlayer1Name("");
                                            teamArrangesBean.setPlayer1Id("");
                                            playerSet = teamArrangesBean.getPlayer1Set();

                                        }
                                    } else {
                                        ids = "" + fillMatchFormAdapter.getData().get(position).getId();
                                        if (ownerType % 3 == 0) {
                                            if(!TextUtils.isEmpty(player2Name)||player2Name.equals("请选择")) {
                                                repetitionSet.remove("" + player2Name);
                                            }

                                            teamArrangesBean.setPlayer2Name("" + name);
                                            teamArrangesBean.setPlayer2Id("" + ids);
                                            playerSet = teamArrangesBean.getPlayer2Set();
                                            /*第三个*/
                                        } else if (ownerType % 3 == 1) {
                                            if(!TextUtils.isEmpty(player1Name)||player1Name.equals("请选择")) {
                                                repetitionSet.remove("" + player1Name);
                                            }

                                            teamArrangesBean.setPlayer1Name("" + name);
                                            teamArrangesBean.setPlayer1Id("" + ids);
                                            playerSet = teamArrangesBean.getPlayer1Set();

                                            /*第一个*/
                                        } else if (ownerType % 3 == 2) {
                                            if(!TextUtils.isEmpty(player1Name)||player1Name.equals("请选择")) {
                                                repetitionSet.remove("" + player1Name);
                                            }

                                            /*第二个*/
                                            teamArrangesBean.setPlayer1Name("" + name);
                                            teamArrangesBean.setPlayer1Id("" + ids);
                                            playerSet = teamArrangesBean.getPlayer1Set();

                                        }
                                    }

                                    teamArrangesBeanList.set(clickPosition, teamArrangesBean);
                                    submitTeamArrangeBean.setTeamArranges(teamArrangesBeanList);
                                    if (name.equals("取消选择")) {
                                        dealData(submitTeamArrangeBean, clickPosition, playerSet, "请选择", ids);

                                    } else {
                                        dealData(submitTeamArrangeBean, clickPosition, playerSet, name, ids);
                                    }
                                    mMovieTicketWindow.dismiss();
                                }
                            }
                        });

                    }
                })
                .setOutsideTouchable(true)//设置外层不可点击
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
//        View view1 = View.inflate(MovieTicketActivity.this, R.layout.activity_movie_ticket, null);
        if (mMovieTicketWindow == null || !mMovieTicketWindow.isShowing()) {
            mMovieTicketWindow.setFocusable(true);// 这个很重要
            mMovieTicketWindow.showAsDropDown(llCoachSelectSingleOne);
        }
    }

    /*显示text  */
    private void dealData(SubmitTeamArrangeBean submitTeamArrangeBean, int clickPosition, String
            playerSet, String name, String playerId) {
        Log.e(TAG, "dealData: name===" + name);
        List<SubmitTeamArrangeBean.TeamArrangesBean> teamArrangesBeanList = submitTeamArrangeBean.getTeamArranges();
        SubmitTeamArrangeBean.TeamArrangesBean teamArrangesBean = teamArrangesBeanList.get(clickPosition);

        String player1Name = teamArrangesBean.getPlayer1Name();
        String player2Name = teamArrangesBean.getPlayer2Name();

        if (clickPosition == 0) {
            if (ownerType % 3 == 0) {
                mBinding.tvDoubleOneRightSelectName.setText("" + player2Name);
                /*第三个*/
            } else if (ownerType % 3 == 1) {
                /*第一个*/
                mBinding.tvOneSingleLeftSelectName.setText("" + player1Name);
            } else if (ownerType % 3 == 2) {
                /*第二个*/
                mBinding.tvDoubleOneLeftSelectName.setText("" + player1Name);
            }
        } else if (clickPosition == 1) {
            if (ownerType % 3 == 0) {//tv_double_two_right_select_name
                mBinding.tvDoubleTwoRightSelectName.setText("" + player2Name);
                /*第三个*/
            } else if (ownerType % 3 == 1) {//tv_double_two_left_select_name
                /*第一个*/
                mBinding.tvTwoSingleLeftSelectName.setText("" + player1Name);

            } else if (ownerType % 3 == 2) {//tv_two_single_left_select_name
                /*第二个*/
                mBinding.tvDoubleTwoLeftSelectName.setText("" + player1Name);

            }
        } else if (clickPosition == 2) {

            if (ownerType % 3 == 0) {//tv_double_two_right_select_name
                mBinding.tvDoubleThreeRightSelectName.setText("" + player2Name);
                /*第三个*/
            } else if (ownerType % 3 == 1) {//tv_double_three_left_select_name
                /*第一个*/
                mBinding.tvThreeSingleLeftSelectName.setText("" + player1Name);
            } else if (ownerType % 3 == 2) {//tv_three_single_left_select_name
                /*第二个*/
                mBinding.tvDoubleThreeLeftSelectName.setText("" + player1Name);
            }

        } else if (clickPosition == 3) {
            if (ownerType % 3 == 0) {//tv_double_two_right_select_name
                mBinding.tvDoubleFourRightSelectName.setText("" + player2Name);
                /*第三个*/
            } else if (ownerType % 3 == 1) {//tv_double_three_left_select_name
                /*第一个*/
                mBinding.tvFourSingleLeftSelectName.setText("" + player1Name);

            } else if (ownerType % 3 == 2) {//tv_four_single_left_select_name
                /*第二个*/
                mBinding.tvDoubleFourLeftSelectName.setText("" + player1Name);

            }
        } else {
            if (ownerType % 3 == 0) {//tv_double_two_right_select_name
                mBinding.tvDoubleFiveRightSelectName.setText("" + player2Name);
                /*第三个*/
            } else if (ownerType % 3 == 1) {//tv_double_three_left_select_name
                mBinding.tvFiveSingleLeftSelectName.setText("" + player1Name);

                /*第一个*/
            } else if (ownerType % 3 == 2) {//tv_four_single_left_select_name
                /*第二个*/
                mBinding.tvDoubleFiveLeftSelectName.setText("" + player1Name);

            }
        }
        Log.e(TAG, "dealData: " + new Gson().toJson(teamArrangesBeanList));
        for (int i = 0; i < teamArrangesBeanList.size(); i++) {
//            Log.e(TAG, "dealData: PlayerSet===" + playerSet + " upsetleft====" + teamArrangesBeanList.get(i).getPlayer1Set());
            if (teamArrangesBeanList.get(i).getPlayer1Set().equals(playerSet)) {
                teamArrangesBeanList.get(i).setPlayer1Name(name);
                teamArrangesBeanList.get(i).setPlayer1Id("" + playerId);
            }
            Log.e(TAG, "dealData: PlayerSet===" + playerSet + " upsetright====" + teamArrangesBeanList.get(i).getPlayer2Set());

            if (teamArrangesBeanList.get(i).getPlayer2Set().equals(playerSet)) {
                teamArrangesBeanList.get(i).setPlayer2Name(name);
                teamArrangesBeanList.get(i).setPlayer2Id("" + playerId);

            }
        }
        Log.e(TAG, "dealData: teamArrangesBeanList===" + new Gson().toJson(teamArrangesBeanList));

        for (int i = 0; i < teamArrangesBeanList.size(); i++) {
            String Myplayer1Name1 = teamArrangesBeanList.get(i).getPlayer1Name();
            String Myplayer1Name2 = teamArrangesBeanList.get(i).getPlayer2Name();
            int hitType = Integer.parseInt(teamArrangesBeanList.get(i).getHitType());
            if (i == 0) {
                if (hitType == 1) {
                    mBinding.tvOneSingleLeftSelectName.setText("" + Myplayer1Name1);
                } else {
                    mBinding.tvDoubleOneLeftSelectName.setText("" + Myplayer1Name1);
                    mBinding.tvDoubleOneRightSelectName.setText("" + Myplayer1Name2);
                }
            } else if (i == 1) {
                if (hitType == 1) {
                    mBinding.tvTwoSingleLeftSelectName.setText("" + Myplayer1Name1);
                } else {
                    mBinding.tvDoubleTwoLeftSelectName.setText("" + Myplayer1Name1);
                    mBinding.tvDoubleTwoRightSelectName.setText("" + Myplayer1Name2);
                }

            } else if (i == 2) {
                if (hitType == 1) {
                    mBinding.tvThreeSingleLeftSelectName.setText("" + Myplayer1Name1);
                } else {
                    mBinding.tvDoubleThreeLeftSelectName.setText("" + Myplayer1Name1);
                    mBinding.tvDoubleThreeRightSelectName.setText("" + Myplayer1Name2);
                }
            } else if (i == 3) {
                if (hitType == 1) {
                    mBinding.tvFourSingleLeftSelectName.setText("" + Myplayer1Name1);
                } else {
                    mBinding.tvDoubleFourLeftSelectName.setText("" + Myplayer1Name1);
                    mBinding.tvDoubleFourRightSelectName.setText("" + Myplayer1Name2);
                }
            } else if (i == 4) {
                if (hitType == 1) {
                    mBinding.tvFiveSingleLeftSelectName.setText("" + Myplayer1Name1);
                } else {
                    mBinding.tvDoubleFiveLeftSelectName.setText("" + Myplayer1Name1);
                    mBinding.tvDoubleFiveRightSelectName.setText("" + Myplayer1Name2);
                }
            }
        }
        submitTeamArrangeBean.setTeamArranges(teamArrangesBeanList);
        for (int mm = 0; mm < teamArrangesBeanList.size(); mm++) {
            SubmitTeamArrangeBean.TeamArrangesBean teamArrangesBean1 = teamArrangesBeanList.get(mm);
            String player1Name1 = teamArrangesBean1.getPlayer1Name();
            String player2Name1 = teamArrangesBean1.getPlayer2Name();
            int hitType = Integer.parseInt(teamArrangesBean1.getHitType());
            if (hitType == 1) {
                repetitionSet.add("" + player1Name1);
            } else {
                repetitionSet.add("" + player1Name1);
                repetitionSet.add("" + player2Name1);
            }
        }
    }


    public void getData(String ownTeamId, String player1Set, String arrangeId) {
        mViewModel.getTeamArangePlay(ownTeamId, player1Set, arrangeId);


    }


    public String getOwnTeamId(int clickPosition) {
        if (teamArrangesBeans != null && teamArrangesBeans.size() > 0) {
            return teamArrangesBeans.get(clickPosition).getOwnTeamId();
        } else {
            return "";
        }
    }

    public String getPlayerSetId(int clickPosition) {
        String player1Set = teamArrangesBeans.get(clickPosition).getPlayer1Set();
        String plater2Set = teamArrangesBeans.get(clickPosition).getPlayer2Set();
        if (ownerType % 3 == 0) {
            return plater2Set;
        } else if (ownerType % 3 == 1) {
            return player1Set;
        } else if (ownerType % 3 == 2) {
            return player1Set;
        } else {
            return player1Set;
        }
    }
}