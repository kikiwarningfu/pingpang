package heyong.intellectPinPang.ui.coachdisplay;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityDisplayWavierUpBinding;
import heyong.intellectPinPang.utils.MessageDialogBuilder;

public class DisplayWavierUpActivity extends BaseActivity<ActivityDisplayWavierUpBinding, BaseViewModel> implements View.OnClickListener {
    RefereeDisPlayBean refereeDisPlayBean;
    private String leftGiveUpName;
    private String rightGiveUpName;
    private String giveUpName;
    private int inputType = 0;
    private String position = "";
    private String dataList = "";
    private int mPosition = 0;
    List<RefereeDisPlayBean> totalList;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_display_wavier_up;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        refereeDisPlayBean = (RefereeDisPlayBean) getIntent().getSerializableExtra("data");
        position = getIntent().getStringExtra("position");
        dataList = getIntent().getStringExtra("dataList");
        try {
            mPosition = Integer.parseInt(position);
        } catch (Exception e) {

        }
        totalList = new Gson().fromJson(dataList, new TypeToken<List<RefereeDisPlayBean>>() {
        }.getType());

        leftGiveUpName = "" + refereeDisPlayBean.getLeftName();
        rightGiveUpName = "" + refereeDisPlayBean.getRightName();

        mBinding.tvLeftGiveUp.setText("" + leftGiveUpName);
        mBinding.tvRightGiveUp.setText("" + rightGiveUpName);
        mBinding.ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_finish:

                finish();

                break;
            case R.id.rl_double_give_up://双弃权
                clearView();
                mBinding.rlDoubleGiveUp.setBackgroundResource(R.drawable.shape_solid_blue_0_5);
                mBinding.tvDoubleGiveUp.setTextColor(Color.parseColor("#ff4795ed"));
                mBinding.ivDoubleGiveUp.setVisibility(View.VISIBLE);
                inputType = 1;
                giveUpName = "双弃权";
                break;
            case R.id.rl_left_give_up:
                clearView();
                mBinding.rlLeftGiveUp.setBackgroundResource(R.drawable.shape_solid_blue_0_5);
                mBinding.tvLeftGiveUp.setTextColor(Color.parseColor("#ff4795ed"));
                mBinding.ivLeftGiveUp.setVisibility(View.VISIBLE);
                inputType = 2;
                giveUpName = "" + leftGiveUpName;
                break;
            case R.id.rl_right_give_up:
                clearView();
                mBinding.rlRightGiveUp.setBackgroundResource(R.drawable.shape_solid_blue_0_5);
                mBinding.tvRightGiveUp.setTextColor(Color.parseColor("#ff4795ed"));
                mBinding.ivRightGiveUp.setVisibility(View.VISIBLE);
                inputType = 3;
                giveUpName = "" + rightGiveUpName;
                break;

            case R.id.tv_commit:
                if (inputType == 0) {
                    toast("请选择弃权方式");
                } else {

                    new MessageDialogBuilder(DisplayWavierUpActivity.this)
                            .setMessage("")
                            .setTvTitle("是否确定" + giveUpName + "弃权!")
                            .setTvCancel("取消")
                            .setTvSure("确定")
                            .setBtnCancleHint(true)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int itemType = refereeDisPlayBean.getItemType();
                                    if (itemType == 1) {
                                        //团体

                                        dealTeamResult();


                                    } else if (itemType == 2) {

                                        dealDanDaResult();
                                    }



                                }
                            })
                            .show();


                }

                break;
        }
    }

    //处理单打
    private void dealDanDaResult() {
        RefereeDisPlayBean refereeDisPlayBean = totalList.get(mPosition);
        refereeDisPlayBean.setShowGiveUp(false);
        if (inputType == 1) {
            //双弃权
            refereeDisPlayBean.setLeftWavier(1);
            refereeDisPlayBean.setLeftWinCount(0);
            refereeDisPlayBean.setRightWavier(1);
            refereeDisPlayBean.setRightWinCount(0);
        } else if (inputType == 2) {
            //左边第一个弃权
            refereeDisPlayBean.setLeftWavier(1);
            refereeDisPlayBean.setLeftWinCount(0);
            refereeDisPlayBean.setRightWavier(0);
            refereeDisPlayBean.setRightWinCount(3);

        } else if (inputType == 3) {
            //右边第二个弃权
            refereeDisPlayBean.setLeftWavier(0);
            refereeDisPlayBean.setLeftWinCount(3);
            refereeDisPlayBean.setRightWavier(1);
            refereeDisPlayBean.setRightWinCount(0);

        }
        refereeDisPlayBean.setShowStatus(2);

        totalList.set(mPosition, refereeDisPlayBean);

        Intent intent = new Intent();
        intent.putExtra("totalList", new Gson().toJson(totalList));
        setResult(RESULT_OK, intent);
        finish();
    }

    //处理双打
    private void dealTeamResult() {
        RefereeDisPlayBean refereeDisPlayBean = totalList.get(mPosition);
        refereeDisPlayBean.setShowGiveUp(false);
        if (inputType == 1) {
            //双弃权
            refereeDisPlayBean.setLeftWavier(1);
            refereeDisPlayBean.setLeftWinCount(0);
            refereeDisPlayBean.setRightWavier(1);
            refereeDisPlayBean.setRightWinCount(0);
        } else if (inputType == 2) {
            //左边第一个弃权
            refereeDisPlayBean.setLeftWavier(1);
            refereeDisPlayBean.setLeftWinCount(0);
            refereeDisPlayBean.setRightWavier(0);
            refereeDisPlayBean.setRightWinCount(3);

        } else if (inputType == 3) {
            //右边第二个弃权
            refereeDisPlayBean.setLeftWavier(0);
            refereeDisPlayBean.setLeftWinCount(3);
            refereeDisPlayBean.setRightWavier(1);
            refereeDisPlayBean.setRightWinCount(0);

        }
        refereeDisPlayBean.setShowStatus(2);
        totalList.set(mPosition, refereeDisPlayBean);

        Intent intent = new Intent();
        intent.putExtra("totalList", new Gson().toJson(totalList));
        setResult(RESULT_OK, intent);
        finish();
    }


    private void clearView() {

        mBinding.rlDoubleGiveUp.setBackgroundResource(R.drawable.shape_solid_gray_0_5);
        mBinding.rlLeftGiveUp.setBackgroundResource(R.drawable.shape_solid_gray_0_5);
        mBinding.rlRightGiveUp.setBackgroundResource(R.drawable.shape_solid_gray_0_5);

        mBinding.ivLeftGiveUp.setVisibility(View.INVISIBLE);
        mBinding.ivDoubleGiveUp.setVisibility(View.INVISIBLE);
        mBinding.ivRightGiveUp.setVisibility(View.INVISIBLE);

        mBinding.tvLeftGiveUp.setTextColor(Color.parseColor("#FF666666"));
        mBinding.tvRightGiveUp.setTextColor(Color.parseColor("#FF666666"));
        mBinding.tvDoubleGiveUp.setTextColor(Color.parseColor("#FF666666"));

    }
}
