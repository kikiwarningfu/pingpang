package heyong.intellectPinPang.ui.club.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.FillMatchFormAdapter;
import heyong.intellectPinPang.bean.competition.ArrangeTeamContestBean;
import heyong.intellectPinPang.bean.competition.UpdatexlClubContestArrangeBean;
import heyong.intellectPinPang.bean.competition.getClubContestTeamNumberBean;
import heyong.intellectPinPang.databinding.ActivityFillMatchFormBinding;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.widget.popupwindow.CommonPopupWindow;

/**
 * 填写对阵表  对内比赛  人数可以重复  但是正式比赛 ABCAB
 */
public class FillMatchFormActivity extends BaseActivity<ActivityFillMatchFormBinding, ClubViewModel> implements View.OnClickListener {
    CommonPopupWindow mMovieTicketWindow;
    public static final String CONTESTID = "contestId";
    private String strContestId = "";
    public static final String TEAM_TITLE = "team_title";
    private String strTeamTitle = "";
    public static final String HOST_NAME = "host_name";
    private String strHostName = "";
    public static final String ORITION_TYPE = "orition_type";
    public static final String TEAM_ID = "team_id";
    public static final String TEAM_NAME = "team_name";
    public static final String TEAM_TYPE = "team_type";
    private String strTeamType = "";
    private String strTeamName = "";
    private String teamId = "";
    private int showPopWindowType = -1;
    private int firstCardType = -1;
    private int secondCardType = -1;
    private int thirdCardType = -1;
    private int fourthCardType = -1;
    private int fifthCardType = -1;
    List<getClubContestTeamNumberBean> data;
    ArrangeTeamContestBean arrangeTeamContestBean = new ArrangeTeamContestBean();
    ArrangeTeamContestBean arrangeTeamContestBean1 = new ArrangeTeamContestBean();
    ArrangeTeamContestBean arrangeTeamContestBean2 = new ArrangeTeamContestBean();
    ArrangeTeamContestBean arrangeTeamContestBean3 = new ArrangeTeamContestBean();
    ArrangeTeamContestBean arrangeTeamContestBean4 = new ArrangeTeamContestBean();


    private int oritionType = -1;//oritionType 0 是主  1是客
    String teamMyId = "";

    /*双打运动员不能是同一个人*/
    @Override
    public int getLayoutRes() {
        return R.layout.activity_fill_match_form;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        mBinding.topbar.getIvFinish().setVisibility(View.GONE);
        strContestId = getIntent().getStringExtra(CONTESTID);
        strTeamTitle = getIntent().getStringExtra(TEAM_TITLE);
        strHostName = getIntent().getStringExtra(HOST_NAME);
        teamId = getIntent().getStringExtra(TEAM_ID);
        strTeamName = getIntent().getStringExtra(TEAM_NAME);
        strTeamType = getIntent().getStringExtra(TEAM_TYPE);

        oritionType = getIntent().getIntExtra(ORITION_TYPE, 0);
        mBinding.tvZhudui.setText("" + strTeamType + ":");
        mBinding.tvTeamName.setText("" + strTeamName);
        mBinding.llSelectOne.setVisibility(View.GONE);
        mBinding.llSelectOneShuangda.setVisibility(View.GONE);
        mBinding.rlSelectTwo.setVisibility(View.GONE);
        mBinding.llSelectTwoShuangda.setVisibility(View.GONE);
        mBinding.rlSelectThree.setVisibility(View.GONE);
        mBinding.llSelectThreeShuangda.setVisibility(View.GONE);
        mBinding.rlSelectFour.setVisibility(View.GONE);
        mBinding.llSelectFourShuangda.setVisibility(View.GONE);
        mBinding.llSelectFifth.setVisibility(View.GONE);
        mBinding.llSelectFifthShuangda.setVisibility(View.GONE);
        mViewModel.getClubContestTeamMember(teamId);
        mViewModel.arrangeTeamContest(strContestId);


        mViewModel.getClubContestTeamMemberLiveData.observe(this, new Observer<ResponseBean<List<getClubContestTeamNumberBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<getClubContestTeamNumberBean>> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    data = responseBean.getData();
                } else {
                    toast("" + responseBean.getMessage());
                }

            }
        });

        mViewModel.updateXlClubContestArrangeLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    toast("" + responseBean.getMessage());
                }

            }
        });
//        contestType    1是单打  2是双打
        mViewModel.arrangeTeamContestLiveData.observe(this, new Observer<ResponseBean<List<ArrangeTeamContestBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<ArrangeTeamContestBean>> listResponseBean) {
                if (listResponseBean.getErrorCode().equals("200")) {

                    List<ArrangeTeamContestBean> data = listResponseBean.getData();
                    if (data.size() == 5) {
                        arrangeTeamContestBean = data.get(0);
                        arrangeTeamContestBean1 = data.get(1);
                        arrangeTeamContestBean2 = data.get(2);
                        arrangeTeamContestBean3 = data.get(3);
                        arrangeTeamContestBean4 = data.get(4);
                    }
                    if (data != null && data.size() > 0) {
                        for (int i = 0; i < data.size(); i++) {
                            String contestType = data.get(i).getContestType();
                            if (i == 0) {
                                switch (Integer.parseInt(contestType)) {
                                    case 1:
                                        firstCardType = 1;
                                        mBinding.llSelectOne.setVisibility(View.VISIBLE);
                                        mBinding.llSelectOneShuangda.setVisibility(View.GONE);
                                        break;
                                    case 2:
                                        firstCardType = 2;
                                        mBinding.llSelectOne.setVisibility(View.GONE);
                                        mBinding.llSelectOneShuangda.setVisibility(View.VISIBLE);
                                        break;
                                }
                            } else if (i == 1) {
                                switch (Integer.parseInt(contestType)) {
                                    case 1:
                                        secondCardType = 1;
                                        mBinding.rlSelectTwo.setVisibility(View.VISIBLE);
                                        mBinding.llSelectTwoShuangda.setVisibility(View.GONE);
                                        break;
                                    case 2:
                                        secondCardType = 2;
                                        mBinding.rlSelectTwo.setVisibility(View.GONE);
                                        mBinding.llSelectTwoShuangda.setVisibility(View.VISIBLE);
                                        break;
                                }
                            } else if (i == 2) {
                                switch (Integer.parseInt(contestType)) {
                                    case 1:
                                        thirdCardType = 1;
                                        mBinding.rlSelectThree.setVisibility(View.VISIBLE);
                                        mBinding.llSelectThreeShuangda.setVisibility(View.GONE);
                                        break;
                                    case 2:
                                        thirdCardType = 2;
                                        mBinding.rlSelectThree.setVisibility(View.GONE);
                                        mBinding.llSelectThreeShuangda.setVisibility(View.VISIBLE);
                                        break;
                                }
                            } else if (i == 3) {
                                switch (Integer.parseInt(contestType)) {
                                    case 1:
                                        fourthCardType = 1;
                                        mBinding.rlSelectFour.setVisibility(View.VISIBLE);
                                        mBinding.llSelectFourShuangda.setVisibility(View.GONE);
                                        break;
                                    case 2:
                                        fourthCardType = 2;
                                        mBinding.rlSelectFour.setVisibility(View.GONE);
                                        mBinding.llSelectFourShuangda.setVisibility(View.VISIBLE);
                                        break;
                                }
                            } else if (i == 4) {
                                switch (Integer.parseInt(contestType)) {
                                    case 1:
                                        fifthCardType = 1;
                                        mBinding.llSelectFifth.setVisibility(View.VISIBLE);
                                        mBinding.llSelectFifthShuangda.setVisibility(View.GONE);
                                        break;
                                    case 2:
                                        fifthCardType = 2;
                                        mBinding.llSelectFifth.setVisibility(View.GONE);
                                        mBinding.llSelectFifthShuangda.setVisibility(View.VISIBLE);
                                        break;
                                }
                            }
                        }
                    }

                } else {
                    toast("" + listResponseBean.getMessage());
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                String strDanDaText = mBinding.tvDandaOneText.getText().toString();
                String strShuangDaOneOneText = mBinding.tvShuangdaOneOneText.getText().toString();
                String strShuangDaOneTwoText = mBinding.tvShuangdaOneTwoText.getText().toString();

                String strDanDaTwoText = mBinding.tvDandaTwoText.getText().toString();
                String strShuangDaTwoOneText = mBinding.tvShuangdaTwoOnetext.getText().toString();
                String strShhuangDaTwoTwoText = mBinding.tvShuangdaTwoTwotext.getText().toString();

                String syrDanDaThreeText = mBinding.tvDandaThreeText.getText().toString();
                String strShuangDaThreeOneText = mBinding.tvShuangdaThreeOnetext.getText().toString();
                String strShuangDaThreeTwoText = mBinding.tvShuangdaThreeTwotext.getText().toString();

                String strDanDaFourText = mBinding.tvDandaFourText.getText().toString();
                String strShuangdaFourOneText = mBinding.tvShuangdaFourOnetext.getText().toString();
                String strShuangdaFourTwoText = mBinding.tvShuangdaFourTwotext.getText().toString();

                String strDandaFifthText = mBinding.tvDandaFifthText.getText().toString();
                String strShuangdaFifthOneText = mBinding.tvShuangdaFifthOnetext.getText().toString();
                String strShuangDaFifthTwoText = mBinding.tvShuangdaFifthTwotext.getText().toString();

                if (firstCardType == 1) {
                    if (TextUtils.isEmpty(strDanDaText) || strDanDaText.equals("请选择")) {
                        toast("请完整填写对阵列表");
                        return;
                    }
                } else {
                    if (TextUtils.isEmpty(strShuangDaOneOneText) || TextUtils.isEmpty(strShuangDaOneTwoText)
                            || strShuangDaOneOneText.equals("请选择") || strShuangDaOneTwoText.equals("请选择")) {
                        toast("请完整填写对阵列表");
                        return;
                    }
                }
                if (secondCardType == 1) {
                    if (TextUtils.isEmpty(strDanDaTwoText) || strDanDaTwoText.equals("请选择")) {
                        toast("请完整填写对阵列表");
                        return;
                    }
                } else {
                    if (TextUtils.isEmpty(strShuangDaTwoOneText) || TextUtils.isEmpty(strShhuangDaTwoTwoText)
                            || strShuangDaTwoOneText.equals("请选择") || strShhuangDaTwoTwoText.equals("请选择")) {
                        toast("请完整填写对阵列表");
                        return;
                    }
                }

                if (thirdCardType == 1) {
                    if (TextUtils.isEmpty(syrDanDaThreeText) || syrDanDaThreeText.equals("请选择")) {
                        toast("请完整填写对阵列表");
                        return;
                    }
                } else {
                    if (TextUtils.isEmpty(strShuangDaThreeOneText) || TextUtils.isEmpty(strShuangDaThreeTwoText)
                            || strShuangDaThreeOneText.equals("请选择") || strShuangDaThreeTwoText.equals("请选择")) {
                        toast("请完整填写对阵列表");
                        return;
                    }
                }
                if (fourthCardType == 1) {
                    if (TextUtils.isEmpty(strDanDaFourText) || strDanDaFourText.equals("请选择")) {
                        toast("请完整填写对阵列表");
                        return;
                    }
                } else {
                    if (TextUtils.isEmpty(strShuangdaFourOneText) || TextUtils.isEmpty(strShuangdaFourTwoText)
                            || strShuangdaFourOneText.equals("请选择") || strShuangdaFourTwoText.equals("请选择")) {
                        toast("请完整填写对阵列表");
                        return;
                    }
                }

                if (fifthCardType == 1) {
                    if (TextUtils.isEmpty(strDandaFifthText) || strDandaFifthText.equals("请选择")) {
                        toast("请完整填写对阵列表");
                        return;
                    }
                } else {
                    if ((TextUtils.isEmpty(strShuangdaFifthOneText) || strShuangdaFifthOneText.equals("请选择")) ||
                            (TextUtils.isEmpty(strShuangDaFifthTwoText)) || strShuangDaFifthTwoText.equals("请选择")) {
                        toast("请完整填写对阵列表");
                        return;
                    }
                }
                UpdatexlClubContestArrangeBean updatexlClubContestArrangeBean =
                        new UpdatexlClubContestArrangeBean();
                List<UpdatexlClubContestArrangeBean.XlClubContestArrangeVOListBean> xlClubContestArrangeVOList =
                        new ArrayList<>();
                UpdatexlClubContestArrangeBean.XlClubContestArrangeVOListBean xlClubContestArrangeVOListBean = getXlClubContestArrangeVOListBean(arrangeTeamContestBean);
                UpdatexlClubContestArrangeBean.XlClubContestArrangeVOListBean xlClubContestArrangeVOListBean1 = getXlClubContestArrangeVOListBean(arrangeTeamContestBean1);
                UpdatexlClubContestArrangeBean.XlClubContestArrangeVOListBean xlClubContestArrangeVOListBean2 = getXlClubContestArrangeVOListBean(arrangeTeamContestBean2);
                UpdatexlClubContestArrangeBean.XlClubContestArrangeVOListBean xlClubContestArrangeVOListBean3 = getXlClubContestArrangeVOListBean(arrangeTeamContestBean3);
                UpdatexlClubContestArrangeBean.XlClubContestArrangeVOListBean xlClubContestArrangeVOListBean4 = getXlClubContestArrangeVOListBean(arrangeTeamContestBean4);

                xlClubContestArrangeVOList.add(xlClubContestArrangeVOListBean);
                xlClubContestArrangeVOList.add(xlClubContestArrangeVOListBean1);
                xlClubContestArrangeVOList.add(xlClubContestArrangeVOListBean2);
                xlClubContestArrangeVOList.add(xlClubContestArrangeVOListBean3);
                xlClubContestArrangeVOList.add(xlClubContestArrangeVOListBean4);
                updatexlClubContestArrangeBean.setXlClubContestArrangeVOList(xlClubContestArrangeVOList);
                mViewModel.updateXlClubContestArrange(updatexlClubContestArrangeBean);

                break;
            case R.id.ll_select_one://第一个单打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showPopWindowType = 1;
                showPopMatchForm(mBinding.llSelectOne);

                break;
            case R.id.ll_select_one_shuangda_one://第一个双打的第一个
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showPopWindowType = 2;
                showPopMatchForm(mBinding.llSelectOneShuangda);
                break;
            case R.id.ll_select_one_shuangda_two://第一个双打的第二个
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showPopWindowType = 3;
                showPopMatchForm(mBinding.llSelectOneShuangda);
                break;
            case R.id.rl_select_two://第二个单打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showPopWindowType = 4;
                showPopMatchForm(mBinding.rlSelectTwo);
                break;
            case R.id.rl_shuangda_two_one://第二个双打的第一个
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showPopWindowType = 5;
                showPopMatchForm(mBinding.llSelectTwoShuangda);
                break;
            case R.id.rl_shuangda_two_two://第二个双打的第二个
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showPopWindowType = 6;
                showPopMatchForm(mBinding.llSelectTwoShuangda);
                break;
            case R.id.rl_select_three://第三个单打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showPopWindowType = 7;
                showPopMatchForm(mBinding.rlSelectThree);
                break;
            /*ll_select_three_shuangda*/
            case R.id.rl_shuangda_three_one://第三个双打的第一个
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showPopWindowType = 8;
                showPopMatchForm(mBinding.llSelectThreeShuangda);
                break;
            case R.id.rl_shuangda_three_two://第三个双打的第二个
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showPopWindowType = 9;
                showPopMatchForm(mBinding.llSelectThreeShuangda);
                break;
            case R.id.rl_select_four://第四个单打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showPopWindowType = 10;
                showPopMatchForm(mBinding.rlSelectFour);
                break;
            /*ll_select_four_shuangda*/
            case R.id.rl_shuangda_four_one://第四个双打的第一个
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showPopWindowType = 11;
                showPopMatchForm(mBinding.llSelectFourShuangda);
                break;
            case R.id.rl_shuangda_four_two://第四个双打的第二个
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showPopWindowType = 12;
                showPopMatchForm(mBinding.llSelectFourShuangda);
                break;
            case R.id.ll_select_fifth://第五个单打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showPopWindowType = 13;
                showPopMatchForm(mBinding.llSelectFifth);
                break;
            /*ll_select_fifth_shuangda*/
            case R.id.rl_shuangda_fifth_one://第五个双打的第一个
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showPopWindowType = 14;
                showPopMatchForm(mBinding.llSelectFifthShuangda);
                break;
            case R.id.rl_shuangda_fifth_two://第五个双打的第二个
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showPopWindowType = 15;
                showPopMatchForm(mBinding.llSelectFifthShuangda);
                break;


        }
    }

    @NotNull
    private UpdatexlClubContestArrangeBean.XlClubContestArrangeVOListBean getXlClubContestArrangeVOListBean(ArrangeTeamContestBean arrangeTeamContestBean) {
        UpdatexlClubContestArrangeBean.XlClubContestArrangeVOListBean xlClubContestArrangeVOListBean =
                new UpdatexlClubContestArrangeBean.XlClubContestArrangeVOListBean();
        xlClubContestArrangeVOListBean.setId(arrangeTeamContestBean.getId());
        xlClubContestArrangeVOListBean.setContestId(arrangeTeamContestBean.getContestId());
        xlClubContestArrangeVOListBean.setContestType(arrangeTeamContestBean.getContestType());
        xlClubContestArrangeVOListBean.setLeftIntegral(arrangeTeamContestBean.getLeftIntegral());
        xlClubContestArrangeVOListBean.setRughtIntegral(arrangeTeamContestBean.getRughtIntegral());
        xlClubContestArrangeVOListBean.setWinId1(arrangeTeamContestBean.getWinId1());
        xlClubContestArrangeVOListBean.setWinId2(arrangeTeamContestBean.getWinId2());
        xlClubContestArrangeVOListBean.setLeft1Id(arrangeTeamContestBean.getLeft1Id());
        xlClubContestArrangeVOListBean.setLeft2Id(arrangeTeamContestBean.getLeft2Id());
        xlClubContestArrangeVOListBean.setRight1Id(arrangeTeamContestBean.getRight1Id());
        xlClubContestArrangeVOListBean.setRigth2Id(arrangeTeamContestBean.getRigth2Id());
        if (oritionType == 0) {
            xlClubContestArrangeVOListBean.setTeamLeftId("" + teamMyId);
        } else {
            xlClubContestArrangeVOListBean.setTeamRightId("" + teamMyId);
        }
        xlClubContestArrangeVOListBean.setCreateUser(arrangeTeamContestBean.getCreateUser());
        xlClubContestArrangeVOListBean.setCreateTime(arrangeTeamContestBean.getCreateTime());
        xlClubContestArrangeVOListBean.setUpdateUser(arrangeTeamContestBean.getUpdateUser());
        xlClubContestArrangeVOListBean.setUpdateTime(arrangeTeamContestBean.getUpdateTime());
        xlClubContestArrangeVOListBean.setIsDelete(arrangeTeamContestBean.getIsDelete());
        xlClubContestArrangeVOListBean.setTableNum(arrangeTeamContestBean.getTableNum());
        return xlClubContestArrangeVOListBean;
    }

    public void showPopMatchForm(View view) {
        mMovieTicketWindow = new CommonPopupWindow.Builder(FillMatchFormActivity.this)
                .setView(R.layout.pop_fill_match_form)
                .setBackGroundLevel(1f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        if (AntiShakeUtils.isInvalidClick(view))
                            return;
                        RecyclerView rvFillMatchForm = view.findViewById(R.id.rv_fill_match_form);
                        rvFillMatchForm.setLayoutManager(new LinearLayoutManager(FillMatchFormActivity.this, RecyclerView.VERTICAL, false));
                        FillMatchFormAdapter fillMatchFormAdapter = new FillMatchFormAdapter();
                        rvFillMatchForm.setAdapter(fillMatchFormAdapter);
                        fillMatchFormAdapter.setNewData(data);
                        fillMatchFormAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapters, View view, int position) {
                                boolean fastClick = isFastClick();
                                if (!fastClick) {
                                    String name = fillMatchFormAdapter.getData().get(position).getName();
                                    String strOne = mBinding.tvShuangdaOneOneText.getText().toString();
                                    String strTwo = mBinding.tvShuangdaOneTwoText.getText().toString();
                                    /*tv_shuangda_two_onetext   tv_shuangda_two_twotext*/
                                    String strTwoOneText = mBinding.tvShuangdaTwoOnetext.getText().toString();
                                    String strTwoTwoText = mBinding.tvShuangdaTwoTwotext.getText().toString();
                                    /*tv_shuangda_three_onetext  rl_shuangda_three_two*/
                                    String strThreeOneText = mBinding.tvShuangdaThreeOnetext.getText().toString();
                                    String strThreeTwoText = mBinding.tvShuangdaThreeTwotext.getText().toString();

                                    String strFourOneText = mBinding.tvShuangdaFourOnetext.getText().toString();
                                    String strFourTwoText = mBinding.tvShuangdaFourTwotext.getText().toString();
                                    String strFiveOneText = mBinding.tvShuangdaFifthOnetext.getText().toString();
                                    String strFiveTwoText = mBinding.tvShuangdaFifthTwotext.getText().toString();

                                    switch (showPopWindowType) {
                                        case 2://第一个双打的第一个
                                            if (!strTwo.equals("请选择")) {
                                                if (name.equals(strTwo)) {
                                                    toast("双打运动员不能是同一个人");
                                                    mMovieTicketWindow.dismiss();

                                                } else {
                                                    showText(position, name, fillMatchFormAdapter);
                                                }
                                            } else {
                                                showText(position, name, fillMatchFormAdapter);
                                            }
                                            break;
                                        case 3://第一个双打的第二个
                                            if (!strOne.equals("请选择")) {
                                                if (name.equals(strOne)) {
                                                    toast("双打运动员不能是同一个人");
                                                    mMovieTicketWindow.dismiss();

                                                } else {
                                                    showText(position, name, fillMatchFormAdapter);
                                                }
                                            } else {
                                                showText(position, name, fillMatchFormAdapter);
                                            }

                                            break;
                                        case 5://第二个双打的第一个
                                            if (!strTwoTwoText.equals("请选择")) {
                                                if (name.equals(strTwoTwoText)) {
                                                    toast("双打运动员不能是同一个人");
                                                    mMovieTicketWindow.dismiss();

                                                } else {
                                                    showText(position, name, fillMatchFormAdapter);
                                                }
                                            } else {
                                                showText(position, name, fillMatchFormAdapter);
                                            }
                                            break;
                                        case 6://第二个双打的第二个
                                            if (!strTwoOneText.equals("请选择")) {
                                                if (name.equals(strTwoOneText)) {
                                                    toast("双打运动员不能是同一个人");
                                                    mMovieTicketWindow.dismiss();

                                                } else {
                                                    showText(position, name, fillMatchFormAdapter);
                                                }
                                            } else {
                                                showText(position, name, fillMatchFormAdapter);
                                            }

                                            break;
                                        case 8:////第三个双打的第一个
                                            if (!strThreeTwoText.equals("请选择")) {
                                                if (name.equals(strThreeTwoText)) {
                                                    toast("双打运动员不能是同一个人");
                                                    mMovieTicketWindow.dismiss();

                                                } else {
                                                    showText(position, name, fillMatchFormAdapter);
                                                }
                                            } else {
                                                showText(position, name, fillMatchFormAdapter);
                                            }
                                            break;
                                        case 9://第三个双打的第二个
                                            if (!strThreeOneText.equals("请选择")) {
                                                if (name.equals(strThreeOneText)) {
                                                    toast("双打运动员不能是同一个人");
                                                    mMovieTicketWindow.dismiss();

                                                } else {
                                                    showText(position, name, fillMatchFormAdapter);
                                                }
                                            } else {
                                                showText(position, name, fillMatchFormAdapter);
                                            }
                                            break;
                                        case 11://第四个双打的第一个
                                            if (!strFourTwoText.equals("请选择")) {
                                                if (name.equals(strFourTwoText)) {
                                                    toast("双打运动员不能是同一个人");
                                                    mMovieTicketWindow.dismiss();

                                                } else {
                                                    showText(position, name, fillMatchFormAdapter);
                                                }
                                            } else {
                                                showText(position, name, fillMatchFormAdapter);
                                            }

                                            break;
                                        case 12://第四个双打的第二个
                                            if (!strFourOneText.equals("请选择")) {
                                                if (name.equals(strFourOneText)) {
                                                    toast("双打运动员不能是同一个人");
                                                    mMovieTicketWindow.dismiss();

                                                } else {
                                                    showText(position, name, fillMatchFormAdapter);
                                                }
                                            } else {
                                                showText(position, name, fillMatchFormAdapter);
                                            }

                                            break;
                                        case 14://第五个双打的第一个
                                            if (!strFiveTwoText.equals("请选择")) {
                                                if (name.equals(strFiveTwoText)) {
                                                    toast("双打运动员不能是同一个人");
                                                    mMovieTicketWindow.dismiss();

                                                } else {
                                                    showText(position, name, fillMatchFormAdapter);
                                                }
                                            } else {
                                                showText(position, name, fillMatchFormAdapter);
                                            }
                                            break;

                                        case 15://第五个双打的第二个

                                            if (!strFiveOneText.equals("请选择")) {
                                                if (name.equals(strFiveOneText)) {
                                                    toast("双打运动员不能是同一个人");
                                                    mMovieTicketWindow.dismiss();
                                                } else {
                                                    showText(position, name, fillMatchFormAdapter);
                                                }
                                            } else {
                                                showText(position, name, fillMatchFormAdapter);
                                            }
                                            break;
                                        default:
                                            showText(position, name, fillMatchFormAdapter);
                                            break;

                                    }

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
            mMovieTicketWindow.showAsDropDown(view);
        }
    }

    private void showText(int position, String name, FillMatchFormAdapter fillMatchFormAdapter) {
        long id = fillMatchFormAdapter.getData().get(position).getUserId();

        showTxt(name, id, fillMatchFormAdapter.getData().get(position));
        mMovieTicketWindow.dismiss();
    }

    private void showTxt(String name, long id, getClubContestTeamNumberBean getClubContestTeamNumberBean) {
        switch (showPopWindowType) {

            case 1://单打 左边   第一个单打
                if (oritionType == 0) {
                    /*主*/
                    arrangeTeamContestBean.setLeft1Id("" + id);
                } else if (oritionType == 1) {
                    arrangeTeamContestBean.setRight1Id("" + id);
                }
                mBinding.tvDandaOneText.setText("" + name);
                break;
            case 2://单打右边    第一个双打  左边
                if (oritionType == 0) {
                    /*主*/
                    arrangeTeamContestBean.setLeft1Id("" + id);
                } else if (oritionType == 1) {
                    arrangeTeamContestBean.setRight1Id("" + id);
                }
                mBinding.tvShuangdaOneOneText.setText("" + name);
                break;
            case 3:  //   第一个双打   右边
                String strFirsname = mBinding.tvShuangdaOneOneText.getText().toString();
                if (strFirsname.equals(name)) {
                    toast("双打运动员不能是同一个人");
                    return;
                } else {
                    if (oritionType == 0) {
                        /*主*/
                        arrangeTeamContestBean.setLeft2Id("" + id);
                    } else if (oritionType == 1) {
                        arrangeTeamContestBean.setRigth2Id("" + id);
                    }
                    mBinding.tvShuangdaOneTwoText.setText("" + name);
                }
                break;
            case 4://    第二个单打
                if (oritionType == 0) {
                    /*主*/
                    arrangeTeamContestBean1.setLeft1Id("" + id);
                } else if (oritionType == 1) {
                    arrangeTeamContestBean1.setRight1Id("" + id);
                }
                mBinding.tvDandaTwoText.setText("" + name);
                break;
            case 5://    第二个双打  左边
                if (oritionType == 0) {
                    /*主*/
                    arrangeTeamContestBean1.setLeft1Id("" + id);
                } else if (oritionType == 1) {
                    arrangeTeamContestBean1.setRight1Id("" + id);
                }
                mBinding.tvShuangdaTwoOnetext.setText("" + name);
                break;
            case 6://  第二个双打     右边
                String strSHuangDaTwoOne = mBinding.tvShuangdaTwoOnetext.getText().toString();
                if (strSHuangDaTwoOne.equals(name)) {
                    toast("双打运动员不能是同一个人");
                    return;
                } else {
                    if (oritionType == 0) {
                        /*主*/
                        arrangeTeamContestBean1.setLeft2Id("" + id);
                    } else if (oritionType == 1) {
                        arrangeTeamContestBean1.setRigth2Id("" + id);
                    }
                    mBinding.tvShuangdaTwoTwotext.setText("" + name);
                }
                break;
            case 7://第三个 单打
                if (oritionType == 0) {
                    /*主*/
                    arrangeTeamContestBean2.setLeft1Id("" + id);
                } else if (oritionType == 1) {
                    arrangeTeamContestBean2.setRight1Id("" + id);
                }
                mBinding.tvDandaThreeText.setText("" + name);
                break;
            case 8://第三个双打  左边
                if (oritionType == 0) {
                    /*主*/
                    arrangeTeamContestBean2.setLeft1Id("" + id);
                } else if (oritionType == 1) {
                    arrangeTeamContestBean2.setRight1Id("" + id);
                }
                mBinding.tvShuangdaThreeOnetext.setText("" + name);
                break;
            case 9://第三个双打   右边
                String strShuangDaThreeOneText = mBinding.tvShuangdaThreeOnetext.getText().toString();
                if (strShuangDaThreeOneText.equals(name)) {
                    toast("双打运动员不能是同一个人");
                    return;
                } else {
                    if (oritionType == 0) {
                        /*主*/
                        arrangeTeamContestBean2.setLeft2Id("" + id);
                    } else if (oritionType == 1) {
                        arrangeTeamContestBean2.setRigth2Id("" + id);
                    }
                    mBinding.tvShuangdaThreeTwotext.setText("" + name);
                }
                break;
            case 10://第四个单打
                if (oritionType == 0) {
                    /*主*/
                    arrangeTeamContestBean3.setLeft1Id("" + id);
                } else if (oritionType == 1) {
                    arrangeTeamContestBean3.setRight1Id("" + id);
                }
                mBinding.tvDandaFourText.setText("" + name);
                break;
            case 11://第四个双打   左边
                if (oritionType == 0) {
                    /*主*/
                    arrangeTeamContestBean3.setLeft1Id("" + id);
                } else if (oritionType == 1) {
                    arrangeTeamContestBean3.setRight1Id("" + id);
                }
                mBinding.tvShuangdaFourOnetext.setText("" + name);
                break;
            case 12://第四个双打   右边
                String strSHuangDaFourOneText = mBinding.tvShuangdaFourOnetext.getText().toString();
                if (strSHuangDaFourOneText.equals(name)) {
                    toast("双打运动员不能是同一个人");
                    return;
                } else {
                    if (oritionType == 0) {
                        /*主*/
                        arrangeTeamContestBean3.setLeft2Id("" + id);
                    } else if (oritionType == 1) {
                        arrangeTeamContestBean3.setRigth2Id("" + id);
                    }
                    mBinding.tvShuangdaFourTwotext.setText("" + name);
                }
                break;
            case 13://第五个单打
                if (oritionType == 0) {
                    /*主*/
                    arrangeTeamContestBean4.setLeft1Id("" + id);
                } else if (oritionType == 1) {
                    arrangeTeamContestBean4.setRight1Id("" + id);
                }
                mBinding.tvDandaFifthText.setText("" + name);
                break;
            case 14://第五个双打  左边
                if (oritionType == 0) {
                    /*主*/
                    arrangeTeamContestBean4.setLeft1Id("" + id);
                } else if (oritionType == 1) {
                    arrangeTeamContestBean4.setRight1Id("" + id);
                }
                mBinding.tvShuangdaFifthOnetext.setText("" + name);
                break;
            case 15://第五个双打   右边
                String strSHuangDaFifthOneText = mBinding.tvShuangdaFifthOnetext.getText().toString();
                if (strSHuangDaFifthOneText.equals(name)) {
                    toast("双打运动员不能是同一个人");
                    return;
                } else {
                    if (oritionType == 0) {
                        /*主*/
                        arrangeTeamContestBean4.setLeft2Id("" + id);
                    } else if (oritionType == 1) {
                        arrangeTeamContestBean4.setRigth2Id("" + id);
                    }
                    mBinding.tvShuangdaFifthTwotext.setText("" + name);
                }

                break;
        }
        teamMyId = "" + getClubContestTeamNumberBean.getContestTeamId();

    }
}