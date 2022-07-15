package heyong.intellectPinPang.ui.club.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.CreateXlClubContestInfoBean;
import heyong.intellectPinPang.databinding.ActivityChooseFormatBinding;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.widget.popupwindow.CommonPopupWindow;

/**
 * 选择赛制
 */
public class ChooseFormatActivity extends BaseActivity<ActivityChooseFormatBinding, ClubViewModel> implements View.OnClickListener {
    CommonPopupWindow mMovieTicketWindow;
    private int type = -1;
    private int showTxTType = 0;
    CreateXlClubContestInfoBean data;
    private int firstType = 1;//单打1  双打2
    private int secondType = 1;
    private int thirdType = 1;
    private int fourType = 1;
    private int fifthType = 1;
    public static final String TAG = ChooseFormatActivity.class.getSimpleName();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_choose_format;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        mBinding.llCustomMatchSystem.setVisibility(View.VISIBLE);
        mBinding.llTheWordSystem.setVisibility(View.GONE);//单打
        data = (CreateXlClubContestInfoBean) getIntent().getSerializableExtra("data");
        mViewModel.createXlClubContestInfoLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if(responseBean.getErrorCode().equals("200"))
                {
                    BigDecimal bigDecimal = new BigDecimal((String) responseBean.getData());
                    long ids = bigDecimal.longValue();
                    Intent intent = new Intent(ChooseFormatActivity.this, DrawLotsHostAndGuestActivity.class);
                    intent.putExtra(DrawLotsHostAndGuestActivity.IDS, ids);
                    startActivity(intent);
                    CreateTeamGamesActivity.instance.finish();//调用
                    finish();
                }else
                {
                    toast(""+responseBean.getMessage());
                }

            }
        });
    }

    public void showPopCity(View view) {
        mMovieTicketWindow = new CommonPopupWindow.Builder(ChooseFormatActivity.this)
                .setView(R.layout.pop_single_and_double)
                .setBackGroundLevel(1f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        LinearLayout llFirstGame = view.findViewById(R.id.ll_the_first_game);
                        LinearLayout llSecondGame = view.findViewById(R.id.ll_second_game);
                        llFirstGame.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (AntiShakeUtils.isInvalidClick(v))
                                    return;
                                showTeamSelect(View.VISIBLE, View.GONE, false, true);
                                showTxtString("单打");
                                switch (showTxTType) {
                                    case 1:
                                        firstType = 1;
                                        break;
                                    case 2:
                                        secondType = 1;
                                        break;
                                    case 3:
                                        thirdType = 1;
                                        break;
                                    case 4:
                                        fourType = 1;
                                        break;
                                    case 5:
                                        fifthType = 1;
                                        break;
                                }
                                mMovieTicketWindow.dismiss();
                            }
                        });
                        llSecondGame.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (AntiShakeUtils.isInvalidClick(v))
                                    return;
                                showTeamSelect(View.VISIBLE, View.GONE, false, true);
                                showTxtString("双打");
                                switch (showTxTType) {
                                    case 1:
                                        firstType = 2;
                                        break;
                                    case 2:
                                        secondType = 2;
                                        break;
                                    case 3:
                                        thirdType = 2;
                                        break;
                                    case 4:
                                        fourType = 2;
                                        break;
                                    case 5:
                                        fifthType = 2;
                                        break;
                                }
                                mMovieTicketWindow.dismiss();
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

    private void shoEmpty(String message) {

        mBinding.tvFirstGame.setText(message);

        mBinding.tvSecondGame.setText(message);

        mBinding.tvThirdGame.setText(message);

        mBinding.tvFourGame.setText(message);

        mBinding.tvFiveGame.setText(message);

    }

    private void showTxtString(String message) {
        switch (showTxTType) {
            case 1:
                mBinding.tvFirstGame.setText(message);
                break;
            case 2:
                mBinding.tvSecondGame.setText(message);
                break;
            case 3:
                mBinding.tvThirdGame.setText(message);
                break;
            case 4:
                mBinding.tvFourGame.setText(message);
                break;
            case 5:
                mBinding.tvFiveGame.setText(message);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_the_first_game:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showTxTType = 1;
                showPopCity(mBinding.llTheFirstGame);
                break;
            case R.id.ll_the_second_name:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showTxTType = 2;
                showPopCity(mBinding.llTheSecondName);
                break;
            case R.id.ll_the_third_name:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showTxTType = 3;
                showPopCity(mBinding.llTheThirdName);
                break;
            case R.id.ll_four_name:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showTxTType = 4;
                showPopCity(mBinding.llFourName);
                break;
            case R.id.ll_five_name:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showTxTType = 5;
                showPopCity(mBinding.llFiveName);
                break;

            case R.id.ll_the_ping:
            case R.id.cb_the_ping://世界乒乓球   单打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                type = 1;

                notifyChange();
                shoEmpty("选择");
                break;

            case R.id.ll_container_custom_match_system:
            case R.id.cb_custom_match_system://自定义赛制
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                type = 2;
                notifyChange();
                shoEmpty("选择");
                break;

            case R.id.tv_start_competition://开始比赛
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (type == -1) {
                    toast("请选择比赛赛制");
                    return;
                }
                data.setContest("" + type);
                List<CreateXlClubContestInfoBean.XlClubContestArrangesBean> xlClubContestArranges = new
                        ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    CreateXlClubContestInfoBean.XlClubContestArrangesBean xlClubContestArrangesBean =
                            new CreateXlClubContestInfoBean.XlClubContestArrangesBean();
                    if (i == 0) {
                        xlClubContestArrangesBean.setContestType("" + firstType);
                    } else if (i == 1) {
                        xlClubContestArrangesBean.setContestType("" + secondType);
                    } else if (i == 2) {
                        xlClubContestArrangesBean.setContestType("" + thirdType);
                    } else if (i == 3) {
                        xlClubContestArrangesBean.setContestType("" + fourType);
                    } else {
                        xlClubContestArrangesBean.setContestType("" + fifthType);
                    }
                    xlClubContestArranges.add(xlClubContestArrangesBean);
                }
                data.setXlClubContestArranges(xlClubContestArranges);
                mViewModel.createXlClubContestInfo(data);

                break;
        }

    }


    private void notifyChange() {
        if (type == 1) {
            showTeamSelect(View.GONE, View.VISIBLE, true, false);

        } else if (type == 2) {
            showTeamSelect(View.VISIBLE, View.GONE, false, true);
        }
    }

    private void showTeamSelect(int visible, int gone, boolean b, boolean b2) {
        mBinding.llCustomMatchSystem.setVisibility(visible);
        mBinding.llTheWordSystem.setVisibility(gone);
        mBinding.cbThePing.setChecked(b);
        mBinding.cbCustomMatchSystem.setChecked(b2);
    }
}