package heyong.intellectPinPang.ui.coachdisplay;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.club.CoachFillMatchFormAdapter;
import heyong.intellectPinPang.adapter.club.CoachListAdapter;
import heyong.intellectPinPang.adapter.club.CoachListTwoAdapter;
import heyong.intellectPinPang.bean.competition.FillInMatchBaseInfoBean;
import heyong.intellectPinPang.bean.competition.MatchBaseInfoBean;
import heyong.intellectPinPang.bean.competition.SubmitTeamArrangeBean;
import heyong.intellectPinPang.bean.competition.TeamArrangePlayBean;
import heyong.intellectPinPang.databinding.ActivityCoachFillMatchFormBinding;
import heyong.intellectPinPang.event.DisplayCoachMatchFormEvent;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.widget.MyDividerItemDecoration;
import heyong.intellectPinPang.widget.popupwindow.CommonPopupWindow;

public class DisplayCoachFillMatchFormActivity extends BaseActivity<ActivityCoachFillMatchFormBinding, BaseViewModel> implements View.OnClickListener {
    public static final String INPUT_TYPE = "INPUT_TYPE";
    public static final String TAG = DisplayCoachFillMatchFormActivity.class.getSimpleName();
    private int mInputType = 0;//1 主队 2 客队
    CommonPopupWindow mMovieTicketWindow;
    CoachListAdapter coachListAdapter;
    CoachListTwoAdapter sportsListAdapter;
    MyDividerItemDecoration mSixDiD;
    MyDividerItemDecoration mSixDiD2;
    CommonPopupWindow mShowWindow;
    MatchBaseInfoBean matchBaseInfoBean;
    SubmitTeamArrangeBean submitTeamArrangeBean;
    FillInMatchBaseInfoBean data;
    private int ownerType = 0;
    private String arrangeId = "";
    List<FillInMatchBaseInfoBean.CoachBean> coachList = new ArrayList<>();
    List<FillInMatchBaseInfoBean.PlayersBean> playerList = new ArrayList<>();

    List<TeamArrangePlayBean> teamArrangePlayBeans = new ArrayList<>();
    List<TeamArrangePlayBean> teamArrangePlayBeansAll = new ArrayList<>();
    List<SubmitTeamArrangeBean.TeamArrangesBean> teamArrangesBeans = new ArrayList<>();
    HashSet<String> repetitionSet = new HashSet();
    public static void goCoachFormActivity(Context context, int mInputType) {
        Intent intent = new Intent(context, DisplayCoachFillMatchFormActivity.class);
        intent.putExtra(INPUT_TYPE, mInputType);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_coach_fill_match_form;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.tvTilte.setText("成人组男子团体【第一阶段A组】");

        mInputType = getIntent().getIntExtra(INPUT_TYPE, 0);
        submitTeamArrangeBean = new SubmitTeamArrangeBean();
        mBinding.setListener(this);
        mBinding.llCoachSelectSingleOne.setVisibility(View.VISIBLE);
        mBinding.llCoachSingleOne.setVisibility(View.VISIBLE);
        mBinding.llCoachDoubleOne.setVisibility(View.GONE);

        mBinding.llCoachSingleTwo.setVisibility(View.VISIBLE);
        mBinding.llCoachDoubleTwo.setVisibility(View.GONE);

        mBinding.llCoachSingleThree.setVisibility(View.VISIBLE);
        mBinding.llCoachDoubleThree.setVisibility(View.GONE);

        mBinding.llCoachSingleFour.setVisibility(View.VISIBLE);
        mBinding.llCoachDoubleFour.setVisibility(View.GONE);

        mBinding.llCoachSingleFive.setVisibility(View.VISIBLE);
        mBinding.llCoachDoubleFive.setVisibility(View.GONE);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(DisplayCoachFillMatchFormActivity.this, 5);
        mBinding.rvCoachList.removeItemDecoration(mSixDiD);
        mSixDiD = new MyDividerItemDecoration(DisplayCoachFillMatchFormActivity.this, 5,
                getResources().getColor(R.color.white));
        mBinding.rvCoachList.addItemDecoration(mSixDiD);
        //绑定manager
        mBinding.rvCoachList.setLayoutManager(gridLayoutManager);
        coachListAdapter = new CoachListAdapter(DisplayCoachFillMatchFormActivity.this, wdith);
        mBinding.rvCoachList.setAdapter(coachListAdapter);


        mBinding.rvSportsList.removeItemDecoration(mSixDiD2);
        mSixDiD2 = new MyDividerItemDecoration(DisplayCoachFillMatchFormActivity.this, 5,
                getResources().getColor(R.color.white));
        mBinding.rvSportsList.addItemDecoration(mSixDiD2);
        //绑定manager
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(DisplayCoachFillMatchFormActivity.this, 5);

        mBinding.rvSportsList.setLayoutManager(gridLayoutManager2);
        sportsListAdapter = new CoachListTwoAdapter(DisplayCoachFillMatchFormActivity.this, wdith);
        mBinding.rvSportsList.setAdapter(sportsListAdapter);
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
        sportsListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //                FillInMatchBaseInfoBean.PlayersBean playersBean = sportsListAdapter.getData().get(position);
                //                showPlayersWindow(playersBean);
            }
        });

        if (mInputType == 1) {//主队
            coachList.clear();
            playerList.clear();
            teamArrangesBeans.clear();
            teamArrangePlayBeans.clear();
            teamArrangePlayBeansAll.clear();
            mBinding.tvZhuke.setText("主队:");
            mBinding.tvFormationName.setText("超级战神队");
            mBinding.tvOwnClubName.setText("小亮俱乐部");
            FillInMatchBaseInfoBean.CoachBean coachBean = new FillInMatchBaseInfoBean.CoachBean();
            coachBean.setName("刘亦菲");
            coachList.add(coachBean);
            FillInMatchBaseInfoBean.PlayersBean playersBean1 = new FillInMatchBaseInfoBean.PlayersBean();
            playersBean1.setName("邓亚萍");
            FillInMatchBaseInfoBean.PlayersBean playersBean2 = new FillInMatchBaseInfoBean.PlayersBean();
            playersBean2.setName("周笔畅");
            FillInMatchBaseInfoBean.PlayersBean playersBean3 = new FillInMatchBaseInfoBean.PlayersBean();
            playersBean3.setName("李宇春");
            playerList.add(playersBean1);
            playerList.add(playersBean2);
            playerList.add(playersBean3);
            mBinding.tvOneSingleLeftName.setText("A");
            mBinding.tvTwoSingleLeftName.setText("B");
            mBinding.tvThreeSingleLeftName.setText("C");
            mBinding.tvFourSingleLeftName.setText("A");
            mBinding.tvFiveSingleLeftName.setText("B");
            TeamArrangePlayBean teamArrangePlayBean1 = new TeamArrangePlayBean();
            teamArrangePlayBean1.setName("吴彦祖");
            TeamArrangePlayBean teamArrangePlayBean2 = new TeamArrangePlayBean();
            teamArrangePlayBean2.setName("彭于晏");
            TeamArrangePlayBean teamArrangePlayBean3 = new TeamArrangePlayBean();
            teamArrangePlayBean3.setName("乔杉");
            TeamArrangePlayBean teamArrangePlayBean = new TeamArrangePlayBean();
            teamArrangePlayBean.setName("取消选择");
            teamArrangePlayBeans.add(teamArrangePlayBean1);
            teamArrangePlayBeans.add(teamArrangePlayBean2);
            teamArrangePlayBeans.add(teamArrangePlayBean3);
            teamArrangePlayBeans.add(teamArrangePlayBean);

            teamArrangePlayBeansAll.add(teamArrangePlayBean1);
            teamArrangePlayBeansAll.add(teamArrangePlayBean2);
            teamArrangePlayBeansAll.add(teamArrangePlayBean3);
            teamArrangePlayBeansAll.add(teamArrangePlayBean);
            SubmitTeamArrangeBean.TeamArrangesBean teamArrangesBean1 = new SubmitTeamArrangeBean.TeamArrangesBean();
            teamArrangesBean1.setPlayer1Set("A");
            SubmitTeamArrangeBean.TeamArrangesBean teamArrangesBean2 = new SubmitTeamArrangeBean.TeamArrangesBean();
            teamArrangesBean2.setPlayer1Set("B");
            SubmitTeamArrangeBean.TeamArrangesBean teamArrangesBean3 = new SubmitTeamArrangeBean.TeamArrangesBean();
            teamArrangesBean3.setPlayer1Set("C");
            SubmitTeamArrangeBean.TeamArrangesBean teamArrangesBean4 = new SubmitTeamArrangeBean.TeamArrangesBean();
            teamArrangesBean4.setPlayer1Set("A");
            SubmitTeamArrangeBean.TeamArrangesBean teamArrangesBean5 = new SubmitTeamArrangeBean.TeamArrangesBean();
            teamArrangesBean5.setPlayer1Set("B");
            teamArrangesBeans.add(teamArrangesBean1);
            teamArrangesBeans.add(teamArrangesBean2);
            teamArrangesBeans.add(teamArrangesBean3);
            teamArrangesBeans.add(teamArrangesBean4);
            teamArrangesBeans.add(teamArrangesBean5);
            Log.e(TAG, "initView: setBeans1111==="+new Gson().toJson(teamArrangesBeans) );
            submitTeamArrangeBean.setTeamArranges(teamArrangesBeans);

        } else {//客队
            coachList.clear();
            playerList.clear();
            teamArrangePlayBeans.clear();
            teamArrangePlayBeansAll.clear();
            teamArrangesBeans.clear();
            mBinding.tvZhuke.setText("客队:");
            mBinding.tvOwnClubName.setText("超级战神队");
            mBinding.tvFormationName.setText("小亮俱乐部");
            FillInMatchBaseInfoBean.CoachBean coachBean = new FillInMatchBaseInfoBean.CoachBean();
            coachBean.setName("郭德纲");
            coachList.add(coachBean);
            FillInMatchBaseInfoBean.PlayersBean playersBean1 = new FillInMatchBaseInfoBean.PlayersBean();
            playersBean1.setName("吴彦祖");
            FillInMatchBaseInfoBean.PlayersBean playersBean2 = new FillInMatchBaseInfoBean.PlayersBean();
            playersBean2.setName("彭于晏");
            FillInMatchBaseInfoBean.PlayersBean playersBean3 = new FillInMatchBaseInfoBean.PlayersBean();
            playersBean3.setName("乔杉");
            playerList.add(playersBean1);
            playerList.add(playersBean2);
            playerList.add(playersBean3);
            mBinding.tvOneSingleLeftName.setText("X");
            mBinding.tvTwoSingleLeftName.setText("Y");
            mBinding.tvThreeSingleLeftName.setText("Z");
            mBinding.tvFourSingleLeftName.setText("Y");
            mBinding.tvFiveSingleLeftName.setText("X");
            TeamArrangePlayBean teamArrangePlayBean1 = new TeamArrangePlayBean();
            teamArrangePlayBean1.setName("邓亚萍");
            TeamArrangePlayBean teamArrangePlayBean2 = new TeamArrangePlayBean();
            teamArrangePlayBean2.setName("周笔畅");
            TeamArrangePlayBean teamArrangePlayBean3 = new TeamArrangePlayBean();
            teamArrangePlayBean3.setName("李宇春");
            TeamArrangePlayBean teamArrangePlayBean = new TeamArrangePlayBean();
            teamArrangePlayBean.setName("取消选择");
            teamArrangePlayBeans.add(teamArrangePlayBean1);
            teamArrangePlayBeans.add(teamArrangePlayBean2);
            teamArrangePlayBeans.add(teamArrangePlayBean3);
            teamArrangePlayBeans.add(teamArrangePlayBean);
            teamArrangePlayBeansAll.add(teamArrangePlayBean1);
            teamArrangePlayBeansAll.add(teamArrangePlayBean2);
            teamArrangePlayBeansAll.add(teamArrangePlayBean3);
            teamArrangePlayBeansAll.add(teamArrangePlayBean);
            SubmitTeamArrangeBean.TeamArrangesBean teamArrangesBean1 = new SubmitTeamArrangeBean.TeamArrangesBean();
            teamArrangesBean1.setPlayer1Set("X");
            SubmitTeamArrangeBean.TeamArrangesBean teamArrangesBean2 = new SubmitTeamArrangeBean.TeamArrangesBean();
            teamArrangesBean2.setPlayer1Set("Y");
            SubmitTeamArrangeBean.TeamArrangesBean teamArrangesBean3 = new SubmitTeamArrangeBean.TeamArrangesBean();
            teamArrangesBean3.setPlayer1Set("Z");
            SubmitTeamArrangeBean.TeamArrangesBean teamArrangesBean4 = new SubmitTeamArrangeBean.TeamArrangesBean();
            teamArrangesBean4.setPlayer1Set("Y");
            SubmitTeamArrangeBean.TeamArrangesBean teamArrangesBean5 = new SubmitTeamArrangeBean.TeamArrangesBean();
            teamArrangesBean5.setPlayer1Set("X");
            teamArrangesBeans.add(teamArrangesBean1);
            teamArrangesBeans.add(teamArrangesBean2);
            teamArrangesBeans.add(teamArrangesBean3);
            teamArrangesBeans.add(teamArrangesBean4);
            teamArrangesBeans.add(teamArrangesBean5);
            Log.e(TAG, "initView: setBeans2222==="+new Gson().toJson(teamArrangesBeans) );

            submitTeamArrangeBean.setTeamArranges(teamArrangesBeans);
        }
        coachListAdapter.setNewData(coachList);

        sportsListAdapter.setNewData(playerList);

    }
    //
    //    private void showPlayersWindow(FillInMatchBaseInfoBean.PlayersBean playersBean) {
    //        mShowWindow = new CommonPopupWindow.Builder(DisplayCoachFillMatchFormActivity.this)
    //                .setView(R.layout.pop_fill_match_message)
    //                .setBackGroundLevel(1f)
    //                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
    //                    @Override
    //                    public void getChildView(View view, int layoutResId) {
    //                        if (AntiShakeUtils.isInvalidClick(view))
    //                            return;
    //                        TextView tvUserName = view.findViewById(R.id.tv_user_name);
    //                        TextView tvChipaiShou = view.findViewById(R.id.tv_chipaishou);
    //
    //                        TextView tvDiBan = view.findViewById(R.id.tv_diban_name);
    //                        TextView tvZhengShouName = view.findViewById(R.id.tv_zhengshou_name);
    //                        TextView tvFanshouName = view.findViewById(R.id.tv_fanshou_name);
    //
    //                        tvDiBan.setText("" + playersBean.getFloorBrandName());
    //                        tvZhengShouName.setText("" + playersBean.getRightBrandName());
    //                        tvFanshouName.setText("" + playersBean.getLeftBrandName());
    //
    //                        tvUserName.setText("" + playersBean.getName());
    //                        tvChipaiShou.setText("" + playersBean.getClapHandName() + "持拍");
    //                    }
    //                })
    //                .setOutsideTouchable(true)//设置外层不可点击
    //                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    //                .create();
    //        //获取需要在其上方显示的控件的位置信息
    //        int[] location = new int[2];
    //        mBinding.tvSports.getLocationOnScreen(location);
    //        Log.e(TAG, "showPopWindow: " + location[0]);
    //        Log.e(TAG, "showPopWindow: " + location[1]);
    ////        View view1 = View.inflate(MovieTicketActivity.this, R.layout.activity_movie_ticket, null);
    //        if (mShowWindow == null || !mShowWindow.isShowing()) {
    //            mShowWindow.setFocusable(true);// 这个很重要
    //            mShowWindow.showAtLocation(mBinding.tvSports, Gravity.NO_GRAVITY, (location[0] + mBinding.tvSports.getWidth() / 2) - 60 / 2, location[1] - location[1] / 4 - 120);
    //        }
    //
    //
    //    }

    private void showPopWindow(FillInMatchBaseInfoBean.CoachBean coachBean) {
        mShowWindow = new CommonPopupWindow.Builder(DisplayCoachFillMatchFormActivity.this)
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
    public void clearSetValue()
    {
        if (mInputType == 1) {//主队
            teamArrangePlayBeansAll.clear();
            FillInMatchBaseInfoBean.CoachBean coachBean = new FillInMatchBaseInfoBean.CoachBean();
            coachBean.setName("刘亦菲");
            coachList.add(coachBean);
            FillInMatchBaseInfoBean.PlayersBean playersBean1 = new FillInMatchBaseInfoBean.PlayersBean();
            playersBean1.setName("邓亚萍");
            FillInMatchBaseInfoBean.PlayersBean playersBean2 = new FillInMatchBaseInfoBean.PlayersBean();
            playersBean2.setName("周笔畅");
            FillInMatchBaseInfoBean.PlayersBean playersBean3 = new FillInMatchBaseInfoBean.PlayersBean();
            playersBean3.setName("李宇春");
            playerList.add(playersBean1);
            playerList.add(playersBean2);
            playerList.add(playersBean3);

            TeamArrangePlayBean teamArrangePlayBean1 = new TeamArrangePlayBean();
            teamArrangePlayBean1.setName("吴彦祖");
            TeamArrangePlayBean teamArrangePlayBean2 = new TeamArrangePlayBean();
            teamArrangePlayBean2.setName("彭于晏");
            TeamArrangePlayBean teamArrangePlayBean3 = new TeamArrangePlayBean();
            teamArrangePlayBean3.setName("乔杉");
            TeamArrangePlayBean teamArrangePlayBean = new TeamArrangePlayBean();
            teamArrangePlayBean.setName("取消选择");
            teamArrangePlayBeansAll.add(teamArrangePlayBean1);
            teamArrangePlayBeansAll.add(teamArrangePlayBean2);
            teamArrangePlayBeansAll.add(teamArrangePlayBean3);
            teamArrangePlayBeansAll.add(teamArrangePlayBean);




        } else {//客队
            teamArrangePlayBeansAll.clear();
            FillInMatchBaseInfoBean.CoachBean coachBean = new FillInMatchBaseInfoBean.CoachBean();
            coachBean.setName("郭德纲");
            coachList.add(coachBean);
            FillInMatchBaseInfoBean.PlayersBean playersBean1 = new FillInMatchBaseInfoBean.PlayersBean();
            playersBean1.setName("吴彦祖");
            FillInMatchBaseInfoBean.PlayersBean playersBean2 = new FillInMatchBaseInfoBean.PlayersBean();
            playersBean2.setName("彭于晏");
            FillInMatchBaseInfoBean.PlayersBean playersBean3 = new FillInMatchBaseInfoBean.PlayersBean();
            playersBean3.setName("乔杉");
            playerList.add(playersBean1);
            playerList.add(playersBean2);
            playerList.add(playersBean3);

            TeamArrangePlayBean teamArrangePlayBean1 = new TeamArrangePlayBean();
            teamArrangePlayBean1.setName("邓亚萍");
            TeamArrangePlayBean teamArrangePlayBean2 = new TeamArrangePlayBean();
            teamArrangePlayBean2.setName("周笔畅");
            TeamArrangePlayBean teamArrangePlayBean3 = new TeamArrangePlayBean();
            teamArrangePlayBean3.setName("李宇春");
            TeamArrangePlayBean teamArrangePlayBean = new TeamArrangePlayBean();
            teamArrangePlayBean.setName("取消选择");

            teamArrangePlayBeansAll.add(teamArrangePlayBean1);
            teamArrangePlayBeansAll.add(teamArrangePlayBean2);
            teamArrangePlayBeansAll.add(teamArrangePlayBean3);
            teamArrangePlayBeansAll.add(teamArrangePlayBean);
        }
        List<TeamArrangePlayBean> dataOtherList = new ArrayList<>();
        Log.e(TAG, "clearSetValue: repetitionSet==="+new Gson().toJson(repetitionSet) );
        if (repetitionSet != null && repetitionSet.size() > 0) {
            for (String str : repetitionSet) {
                for (int mm = 0; mm < teamArrangePlayBeansAll.size(); mm++) {
                    if (teamArrangePlayBeansAll.get(mm).getName().equals(str)) {
                        Log.e(TAG, "onChanged: 等于===" + str);
                        dataOtherList.add(teamArrangePlayBeansAll.get(mm));
                    }
                }
            }
            Log.e(TAG, "onChanged: dataOtherList=" + new Gson().toJson(dataOtherList));
            teamArrangePlayBeansAll.removeAll(dataOtherList);//取出来差集
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_coach_select_single_one://第一个
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                clearSetValue();

                showPeoplePop(mBinding.llCoachSelectSingleOne, teamArrangePlayBeansAll, 0);
                break;
            case R.id.ll_coach_select_single_two://第二个
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                clearSetValue();
                showPeoplePop(mBinding.llCoachSelectSingleTwo, teamArrangePlayBeansAll, 1);
                break;
            case R.id.ll_coach_select_single_three://第三个
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                clearSetValue();
                showPeoplePop(mBinding.llCoachSelectSingleThree, teamArrangePlayBeansAll, 2);

                break;
            case R.id.ll_coach_select_single_four://第四个
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                clearSetValue();
                showPeoplePop(mBinding.llCoachSelectSingleFour, teamArrangePlayBeansAll, 3);

                break;
            case R.id.ll_coach_select_single_five://第五个
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                clearSetValue();
                showPeoplePop(mBinding.llCoachSelectSingleFive, teamArrangePlayBeansAll, 4);


                break;
            case R.id.tv_submit:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                List<SubmitTeamArrangeBean.TeamArrangesBean> teamArrangesBeanList = submitTeamArrangeBean.getTeamArranges();
                boolean isCanSubmit = true;
                for (int i = 0; i < teamArrangesBeanList.size(); i++) {
                    int hitType = 1;
                    String player1Id = teamArrangesBeanList.get(i).getPlayer1Name();
                    String player2Id = teamArrangesBeanList.get(i).getPlayer2Name();
                    if (hitType == 1) {
                        /*单打 */
                        if (TextUtils.isEmpty(player1Id) || player1Id.equals("请选择")) {
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
                    new MessageDialogBuilder(DisplayCoachFillMatchFormActivity.this)
                            .setMessage("")
                            .setTvTitle("是否提交对阵名单？")
                            .setTvCancel("取消")
                            .setBtnCancleHint(false)
                            .setTvSure("确定")
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    toast("提交");
                                    Log.e(TAG, "onClick: 提交：===" + new Gson().toJson(submitTeamArrangeBean));
                                    EventBus.getDefault().post(new DisplayCoachMatchFormEvent(mInputType, new Gson().toJson(submitTeamArrangeBean)));
                                    finish();
                                }
                            })
                            .show();
                } else {
                    toast("请选择运动员");
                }
                break;

        }
    }

    private void showPeoplePop(ViewGroup llCoachSelectSingleOne, List<TeamArrangePlayBean> data, int clickPosition) {
        mMovieTicketWindow = new CommonPopupWindow.Builder(DisplayCoachFillMatchFormActivity.this)
                .setView(R.layout.pop_fill_match_form)
                .setBackGroundLevel(1f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        if (AntiShakeUtils.isInvalidClick(view))
                            return;

                        RecyclerView rvFillMatchForm = view.findViewById(R.id.rv_fill_match_form);
                        rvFillMatchForm.setLayoutManager(new LinearLayoutManager(DisplayCoachFillMatchFormActivity.this, RecyclerView.VERTICAL, false));
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
                                    Log.e(TAG, "onItemClick: name===" + name + "clickposition===" + clickPosition);
                                    String playerSet = "";
                                    String ids = "";
                                    if (name.equals("取消选择")) {
                                        ids = "";
                                        if (ownerType % 3 == 0) {
                                            repetitionSet.remove("" + player1Name);
                                            teamArrangesBean.setPlayer1Name("");
                                            teamArrangesBean.setPlayer1Id("");
                                            playerSet = teamArrangesBean.getPlayer1Set();
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
                                            if (!TextUtils.isEmpty(player1Name) || player1Name.equals("请选择")) {
                                                repetitionSet.remove("" + player1Name);
                                            }

                                            teamArrangesBean.setPlayer1Name("" + name);
                                            teamArrangesBean.setPlayer1Id("" + ids);
                                            playerSet = teamArrangesBean.getPlayer1Set();
                                            /*第三个*/
                                        } else if (ownerType % 3 == 1) {
                                            if (!TextUtils.isEmpty(player1Name) || player1Name.equals("请选择")) {
                                                repetitionSet.remove("" + player1Name);
                                            }

                                            teamArrangesBean.setPlayer1Name("" + name);
                                            teamArrangesBean.setPlayer1Id("" + ids);
                                            playerSet = teamArrangesBean.getPlayer1Set();

                                            /*第一个*/
                                        } else if (ownerType % 3 == 2) {
                                            if (!TextUtils.isEmpty(player1Name) || player1Name.equals("请选择")) {
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
                                    Log.e(TAG, "initView: setBeans3333==="+new Gson().toJson(teamArrangesBeans) );

                                    if (name.equals("取消选择")) {
                                        dealData( clickPosition, playerSet, "请选择", ids);

                                    } else {
                                        dealData( clickPosition, playerSet, name, ids);
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
    private void dealData( int clickPosition, String playerSet, String name, String playerId) {
        List<SubmitTeamArrangeBean.TeamArrangesBean> teamArrangesBeanList = submitTeamArrangeBean.getTeamArranges();
        SubmitTeamArrangeBean.TeamArrangesBean teamArrangesBean = teamArrangesBeanList.get(clickPosition);

        String player1Name = teamArrangesBean.getPlayer1Name();
        String player2Name = teamArrangesBean.getPlayer2Name();


//        Log.e(TAG, "dealData: " + new Gson().toJson(teamArrangesBeanList));
        for (int i = 0; i < teamArrangesBeanList.size(); i++) {
            if (teamArrangesBeanList.get(i).getPlayer1Set().equals(playerSet)) {
                teamArrangesBeanList.get(i).setPlayer1Name(name);
                teamArrangesBeanList.get(i).setPlayer1Id("" + playerId);
            }


        }
//        Log.e(TAG, "dealData: teamArrangesBeanList===" + new Gson().toJson(teamArrangesBeanList));

        for (int i = 0; i < teamArrangesBeanList.size(); i++) {
            String Myplayer1Name1 = teamArrangesBeanList.get(i).getPlayer1Name();
            String Myplayer1Name2 = teamArrangesBeanList.get(i).getPlayer2Name();
            if (i == 0) {

                    mBinding.tvOneSingleLeftSelectName.setText("" + Myplayer1Name1);

            } else if (i == 1) {

                    mBinding.tvTwoSingleLeftSelectName.setText("" + Myplayer1Name1);


            } else if (i == 2) {

                    mBinding.tvThreeSingleLeftSelectName.setText("" + Myplayer1Name1);

            } else if (i == 3) {

                    mBinding.tvFourSingleLeftSelectName.setText("" + Myplayer1Name1);

            } else if (i == 4) {

                    mBinding.tvFiveSingleLeftSelectName.setText("" + Myplayer1Name1);

            }
        }

        submitTeamArrangeBean.setTeamArranges(teamArrangesBeanList);
        Log.e(TAG, "initView: setBeans4444==="+new Gson().toJson(teamArrangesBeans) );

        //        Log.e(TAG, "dealData: "+new Gson().toJson(submitTeamArrangeBean) );
        for (int mm = 0; mm < teamArrangesBeanList.size(); mm++) {
            SubmitTeamArrangeBean.TeamArrangesBean teamArrangesBean1 = teamArrangesBeanList.get(mm);
            String player1Name1 = teamArrangesBean1.getPlayer1Name();
            String player2Name1 = teamArrangesBean1.getPlayer2Name();

            repetitionSet.add("" + player1Name1);

        }
    }
}
