package heyong.intellectPinPang.ui.mine.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.AllAchievementBean;
import heyong.intellectPinPang.bean.competition.MyAchievementBean;
import heyong.intellectPinPang.databinding.ActivityMyGradeBinding;
import heyong.intellectPinPang.ui.mine.MineViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.DeviceMessageUtil;

/**
 * 我的成绩    教练员   不显示成绩
 */
public class MyGradeActivity extends BaseActivity<ActivityMyGradeBinding, MineViewModel> implements View.OnClickListener {
    private int typeRole = 1;//1 是运动员  2 是教练员
    private int gradeType = 1;//1 是所有成绩 2 获奖成绩
    public static final String TAG = MyGradeActivity.class.getSimpleName();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_my_grade;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);

        Log.e(TAG, "initView: brand==" + DeviceMessageUtil.getBrand()
                + " deviceName===" + DeviceMessageUtil.getDeviceName()+
//                + " imeiImsi===" + DeviceMessageUtil.getEI_SI("", this)
//                + "  cardNumber==" + DeviceMessageUtil.getCard1Number(this) + " " +
                "androidId===" + DeviceMessageUtil.getAndroidId(this)
                + " resolution===" + DeviceMessageUtil.getResolution(this));

        mBinding.rlSportsContainer.setBackgroundResource(R.drawable.shape_left_corners_blue);
        mBinding.rlCoachContainer.setBackgroundResource(R.drawable.shape_right_corners_gray);
        mBinding.tvSportsContainer.setTextColor(Color.parseColor("#FFFFFF"));
        mBinding.tvCoach.setTextColor(Color.parseColor("#666666"));
        gradeType = 1;
        typeRole = 1;
        mBinding.llGradeVisvible.setVisibility(View.VISIBLE);
        mBinding.viewScore.setVisibility(View.VISIBLE);
        mBinding.viewGetAwardScore.setVisibility(View.INVISIBLE);
        mBinding.llAllGrade.setVisibility(View.VISIBLE);
        mBinding.llHuojiangGrade.setVisibility(View.GONE);
        mBinding.tvAllScore.setTextColor(Color.parseColor("#FF4795ED"));
        mBinding.tvGetAwardScore.setTextColor(Color.parseColor("#FF666666"));
        mViewModel.myAchievementLiveData.observe(this, new Observer<ResponseBean<List<MyAchievementBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<MyAchievementBean>> responseBean) {
                dealData(responseBean);
            }
        });
        mViewModel.myAllAchievementLiveData.observe(this, new Observer<ResponseBean<AllAchievementBean>>() {
            @Override
            public void onChanged(ResponseBean<AllAchievementBean> allAchievementBeanResponseBean) {
//                if (allAchievementBeanResponseBean.isSuccess()) {
                    if(allAchievementBeanResponseBean.getErrorCode().equals("200")){

                        AllAchievementBean data = allAchievementBeanResponseBean.getData();
                    if (data != null) {
                        mBinding.tvTuantiHejiOne.setText("" + data.getTeamCount());
                        mBinding.tvDandaHejiOne.setText("" + data.getSingleCount());
                        mBinding.tvShuangdaHejiOne.setText("" + data.getDoubleCount());
                        mBinding.tvHunshuangHejiOne.setText("" + data.getHunHeCount());
                        mBinding.tvBisaiChangci.setText("" + data.getAllCount());
                        mBinding.tvWinCount.setText("" + data.getWinCount());
                        mBinding.tvLoseCount.setText("" + data.getLoseCount());
                        mBinding.tvWinRate.setText("" + data.getWin());
                    }
                } else {
                    toast("" + allAchievementBeanResponseBean.getMessage());
                }
            }
        });
        getData();
    }

    private void dealData(ResponseBean<List<MyAchievementBean>> responseBean) {
//        if (responseBean.isSuccess()) {
            if(responseBean.getErrorCode().equals("200")){

                List<MyAchievementBean> data = responseBean.getData();
            if (data != null && data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
//                            1团体2单打3双打,4混双
                    String projectItem = data.get(i).getProjectItem();
                    int counts = data.get(i).getCounts();
                    List<MyAchievementBean.RanksBean> ranks = data.get(i).getRanks();
                    switch (projectItem) {
                        case "1":
                            mBinding.tvTuantiHeji.setText("" + counts);
                            mBinding.tvTuantiHeji.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goAnotherActivity(-1, 1);
                                }
                            });
                            for (int j = 0; j < ranks.size(); j++) {
                                switch (ranks.get(j).getRanking()) {
                                    case 1:
                                        mBinding.tvGuanjunTuanti.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvGuanjunTuanti.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(1, 1);
                                            }
                                        });
                                        break;
                                    case 2:
                                        mBinding.tvYajunTuanti.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvYajunTuanti.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(2, 1);
                                            }
                                        });
                                        break;
                                    case 3:
                                        mBinding.tvJijunTuanti.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvJijunTuanti.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(3, 1);

                                            }
                                        });
                                        break;
                                    case 4:
                                        mBinding.tvDisiTuanti.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDisiTuanti.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(4, 1);

                                            }
                                        });
                                        break;
                                    case 5:
                                        mBinding.tvDiwuTuanti.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDiwuTuanti.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(5, 1);

                                            }
                                        });
                                        break;
                                    case 6:
                                        mBinding.tvDiliuTuanti.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDiliuTuanti.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(6, 1);

                                            }
                                        });
                                        break;
                                    case 7:
                                        mBinding.tvDiqiTuanti.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDiqiTuanti.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(7, 1);

                                            }
                                        });
                                        break;
                                    case 8:
                                        mBinding.tvDibaTuanti.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDibaTuanti.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(8, 1);

                                            }
                                        });
                                        break;
                                }
                            }
                            break;
                        case "2":
                            mBinding.tvDandaHeji.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goAnotherActivity(-1, 2);

                                }
                            });
                            mBinding.tvDandaHeji.setText("" + counts);
                            for (int j = 0; j < ranks.size(); j++) {
                                switch (ranks.get(j).getRanking()) {
                                    case 1:
                                        mBinding.tvGuanjunDanda.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvGuanjunDanda.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(1, 2);

                                            }
                                        });
                                        break;
                                    case 2:
                                        mBinding.tvYajunDanda.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvYajunDanda.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(2, 2);

                                            }
                                        });
                                        break;
                                    case 3:
                                        mBinding.tvJijunDanda.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvJijunDanda.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(3, 2);

                                            }
                                        });
                                        break;
                                    case 4:
                                        mBinding.tvDisiDanda.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDisiDanda.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(4, 2);

                                            }
                                        });
                                        break;
                                    case 5:
                                        mBinding.tvDiwuDanda.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDiwuDanda.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(5, 2);

                                            }
                                        });
                                        break;
                                    case 6:
                                        mBinding.tvDiliuDanda.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDiliuDanda.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(6, 2);

                                            }
                                        });
                                        break;
                                    case 7:
                                        mBinding.tvDiqiDanda.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDiqiDanda.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(7, 2);

                                            }
                                        });
                                        break;
                                    case 8:
                                        mBinding.tvDibaDanda.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDibaDanda.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(8, 2);

                                            }
                                        });
                                        break;
                                }
                            }
                            break;
                        case "3":
                            mBinding.tvShuangdaHeji.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goAnotherActivity(-1, 3);

                                }
                            });
                            mBinding.tvShuangdaHeji.setText("" + counts);
                            for (int j = 0; j < ranks.size(); j++) {
                                switch (ranks.get(j).getRanking()) {
                                    case 1:
                                        mBinding.tvGuanjunShuangda.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvGuanjunShuangda.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(1, 3);
                                            }
                                        });
                                        break;
                                    case 2:
                                        mBinding.tvYajunShuangda.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvYajunShuangda.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(2, 3);

                                            }
                                        });
                                        break;
                                    case 3:
                                        mBinding.tvJijunShuangda.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvJijunShuangda.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(3, 3);

                                            }
                                        });
                                        break;
                                    case 4:
                                        mBinding.tvDisiShuangda.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDisiShuangda.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(4, 3);

                                            }
                                        });
                                        break;
                                    case 5:
                                        mBinding.tvDiwuShuangda.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDiwuShuangda.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(5, 3);

                                            }
                                        });
                                        break;
                                    case 6:
                                        mBinding.tvDiliuShuangda.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDiliuShuangda.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(6, 3);

                                            }
                                        });
                                        break;
                                    case 7:
                                        mBinding.tvDiqiShuangda.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDiqiShuangda.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(7, 3);

                                            }
                                        });
                                        break;
                                    case 8:
                                        mBinding.tvDibaShuangda.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDibaShuangda.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(8, 3);

                                            }
                                        });
                                        break;
                                }
                            }
                            break;
                        case "4":
                            mBinding.tvHunshuangHeji.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goAnotherActivity(-1, 4);

                                }
                            });
                            mBinding.tvHunshuangHeji.setText("" + counts);
                            for (int j = 0; j < ranks.size(); j++) {
                                switch (ranks.get(j).getRanking()) {
                                    case 1:
                                        mBinding.tvGuanjunHunshhuang.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvGuanjunHunshhuang.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(1, 4);
                                            }
                                        });
                                        break;
                                    case 2:
                                        mBinding.tvYajunHunshuang.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvYajunHunshuang.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(2, 4);
                                            }
                                        });
                                        break;
                                    case 3:
                                        mBinding.tvJijunHunshuang.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvJijunHunshuang.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(3, 4);
                                            }
                                        });
                                        break;
                                    case 4:
                                        mBinding.tvDisiHunshuang.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDisiHunshuang.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(4, 4);
                                            }
                                        });
                                        break;
                                    case 5:
                                        mBinding.tvDiwuHunshuang.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDiwuHunshuang.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(5, 4);
                                            }
                                        });
                                        break;
                                    case 6:
                                        mBinding.tvDiliuHunshuang.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDiliuHunshuang.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(6, 4);
                                            }
                                        });
                                        break;
                                    case 7:
                                        mBinding.tvDiqiHunshuang.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDiqiHunshuang.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(7, 4);
                                            }
                                        });
                                        break;
                                    case 8:
                                        mBinding.tvDibaHunshuang.setText("" + ranks.get(j).getCounts());
                                        mBinding.tvDibaHunshuang.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                goAnotherActivity(8, 4);
                                            }
                                        });
                                        break;
                                }
                            }
                            break;
                    }
                }
            }
        } else {
            toast("" + responseBean.getMessage());
        }
    }

    private void goAnotherActivity(int ranking, int itemProjectType) {
//typeRole
// gradeType
        Intent intent = new Intent();
        if (gradeType == 2) {
            //获奖成绩
            intent.setClass(MyGradeActivity.this, AwardWinningScoreActivity.class);
            intent.putExtra(AwardWinningScoreActivity.ITEM, "" + itemProjectType);
            if (ranking == -1) {
                intent.putExtra(AwardWinningScoreActivity.RANKING, "");
            } else {
                intent.putExtra(AwardWinningScoreActivity.RANKING, "" + ranking);
            }
            intent.putExtra(AwardWinningScoreActivity.ROLE, "" + typeRole);
        } else {
            //所有成绩
            intent.setClass(MyGradeActivity.this, AllGradeActivity.class);
            intent.putExtra(AwardWinningScoreActivity.ITEM, "" + itemProjectType);
            if (ranking == -1) {
                intent.putExtra(AwardWinningScoreActivity.RANKING, "");
            } else {
                intent.putExtra(AwardWinningScoreActivity.RANKING, "" + ranking);
            }
            intent.putExtra(AwardWinningScoreActivity.ROLE, "" + typeRole);
        }
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_sports_container://运动员
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.rlSportsContainer.setBackgroundResource(R.drawable.shape_left_corners_blue);
                mBinding.rlCoachContainer.setBackgroundResource(R.drawable.shape_right_corners_gray);
                mBinding.tvSportsContainer.setTextColor(Color.parseColor("#FFFFFF"));
                mBinding.tvCoach.setTextColor(Color.parseColor("#666666"));
                typeRole = 1;
                mBinding.llGradeVisvible.setVisibility(View.VISIBLE);
                getData();
                break;
            case R.id.rl_coach_container://教练员
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.rlSportsContainer.setBackgroundResource(R.drawable.shape_left_corners_gray);
                mBinding.rlCoachContainer.setBackgroundResource(R.drawable.shape_right_corners_blue);
                mBinding.tvSportsContainer.setTextColor(Color.parseColor("#666666"));
                mBinding.tvCoach.setTextColor(Color.parseColor("#FFFFFF"));
                typeRole = 2;
                mBinding.llGradeVisvible.setVisibility(View.GONE);
                getData();

                break;
            case R.id.ll_all_scored://所有成绩
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                gradeType = 1;
                mBinding.viewScore.setVisibility(View.VISIBLE);
                mBinding.viewGetAwardScore.setVisibility(View.INVISIBLE);
                mBinding.tvAllScore.setTextColor(Color.parseColor("#FF4795ED"));
                mBinding.tvGetAwardScore.setTextColor(Color.parseColor("#FF666666"));
                mBinding.llAllGrade.setVisibility(View.VISIBLE);
                mBinding.llHuojiangGrade.setVisibility(View.GONE);
                getData();

                break;
            case R.id.ll_get_award_score://获奖成绩
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                gradeType = 2;
                mBinding.viewGetAwardScore.setVisibility(View.VISIBLE);
                mBinding.viewScore.setVisibility(View.INVISIBLE);
                mBinding.tvGetAwardScore.setTextColor(Color.parseColor("#FF4795ED"));
                mBinding.tvAllScore.setTextColor(Color.parseColor("#FF666666"));
                mBinding.llAllGrade.setVisibility(View.GONE);
                mBinding.llHuojiangGrade.setVisibility(View.VISIBLE);
                getData();
                break;

            case R.id.iv_finish:

                finish();
                break;
            // 1团体2单打3双打,4混双
            case R.id.tv_tuanti_heji_one://团体合计
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                goAnotherActivity(-1, 1);

                break;
            case R.id.tv_danda_heji_one://单打合计
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                goAnotherActivity(-1, 2);

                break;
            case R.id.tv_shuangda_heji_one://双打合计
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                goAnotherActivity(-1, 3);
                break;
            case R.id.tv_hunshuang_heji_one://混双合计
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                goAnotherActivity(-1, 4);
                break;

        }
    }

    public void getData() {
        if (gradeType == 1) {//所有成绩 不返回list集合
            mViewModel.myAllAchievement("" + typeRole, "" + gradeType);
        } else {
            //成绩单  返回list
            mViewModel.myAchievement("" + typeRole, "" + gradeType);
        }
    }
}