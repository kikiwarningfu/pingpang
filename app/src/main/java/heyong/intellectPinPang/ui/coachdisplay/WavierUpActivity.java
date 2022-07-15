package heyong.intellectPinPang.ui.coachdisplay;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.TableNumArrangeBean;
import heyong.intellectPinPang.databinding.ActivityWavierUpBinding;
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel;
import heyong.intellectPinPang.utils.MessageDialogBuilder;

public class WavierUpActivity extends BaseActivity<ActivityWavierUpBinding, HomePageViewModel> implements View.OnClickListener {

    private int inputType = 0;
    private String giveUpName = "";
    private String leftGiveName = "";
    private String rightGiveName = "";
    TableNumArrangeBean.ArrangesBean arrangesBean;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_wavier_up;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        arrangesBean = (TableNumArrangeBean.ArrangesBean) getIntent().getSerializableExtra("data");
        if (arrangesBean != null) {
            String itemType = arrangesBean.getItemType();
            switch (itemType) {
                case "1":
                case "2":
                    leftGiveName = "" + arrangesBean.getPlayer1Name();
                    rightGiveName = "" + arrangesBean.getPlayer3Name();

                    break;
                default:

                    leftGiveName = "" + arrangesBean.getPlayer1Name() + "/" + arrangesBean.getPlayer2Name();
                    rightGiveName = "" + arrangesBean.getPlayer3Name() + "/" + arrangesBean.getPlayer4Name();
                    break;
            }
            mBinding.tvLeftGiveUp.setText("" + leftGiveName);
            mBinding.tvRightGiveUp.setText("" + rightGiveName);
        }

        mViewModel.waiverOperationLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    toast("弃权成功");
                    finish();
                } else {
                    toast("" + responseBean.getMessage());
                }
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
                inputType =3;
                giveUpName = "双弃权";
                break;
            case R.id.rl_left_give_up:
                clearView();
                mBinding.rlLeftGiveUp.setBackgroundResource(R.drawable.shape_solid_blue_0_5);
                mBinding.tvLeftGiveUp.setTextColor(Color.parseColor("#ff4795ed"));
                mBinding.ivLeftGiveUp.setVisibility(View.VISIBLE);
                inputType = 1;
                giveUpName = "" + leftGiveName;
                break;
            case R.id.rl_right_give_up:
                clearView();
                mBinding.rlRightGiveUp.setBackgroundResource(R.drawable.shape_solid_blue_0_5);
                mBinding.tvRightGiveUp.setTextColor(Color.parseColor("#ff4795ed"));
                mBinding.ivRightGiveUp.setVisibility(View.VISIBLE);
                inputType = 2;
                giveUpName = "" + rightGiveName;
                break;

            case R.id.tv_commit:
                if (inputType == 0) {
                    toast("请选择弃权方式");
                } else {
                    new MessageDialogBuilder(WavierUpActivity.this)
                            .setMessage("")
                            .setTvTitle("是否确定" + giveUpName + "弃权!")
                            .setTvCancel("取消")
                            .setTvSure("确定")
                            .setBtnCancleHint(true)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (arrangesBean != null) {
                                        showLoading();
                                        mViewModel.waiverOperation("" + arrangesBean.getId(),
                                                "" + arrangesBean.getItemType(), "" + inputType);
                                    }
//                                    Log.e(TAG, "onClick: "+inputType );

                                }
                            })
                            .show();


                }

                break;
        }
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
