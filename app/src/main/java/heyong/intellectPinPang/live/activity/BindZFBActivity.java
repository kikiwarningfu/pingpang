package heyong.intellectPinPang.live.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xq.fasterdialog.dialog.CustomDialog;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityBindZFBBinding;
import heyong.intellectPinPang.live.model.LiveViewModel;
import heyong.intellectPinPang.ui.homepage.activity.GameDetailActivity;

/**
 * 绑定支付宝的界面UI
 */
public class BindZFBActivity extends BaseActivity<ActivityBindZFBBinding, LiveViewModel> implements View.OnClickListener {
    private String id = "";

    public static void goActivity(Context context, String id) {
        Intent intent = new Intent(context, BindZFBActivity.class);
        intent.putExtra("id", "" + id);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_bind_z_f_b;
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_name_one, R.id.et_account_one};
        return ids;
    }


    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        id = getIntent().getStringExtra("id");


        mViewModel.liveUserAccountLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    toast("绑定成功");
                    finish();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mViewModel.bindingWithdrawalAccountData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    showBindSuccess();
                } else {
                    showBindFail();
                }

            }
        });


    }

    //绑定失败
    private void showBindFail() {

        CustomDialog customDialogConfirm = new CustomDialog(BindZFBActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_bind_fail);
        customDialogConfirm.show();
        View customView = customDialogConfirm.getCustomView();

        TextView tvCancel = customView.findViewById(R.id.tv_cancel);
        TextView tvBindRetry = customView.findViewById(R.id.tv_bind_retry);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customDialogConfirm != null) {
                    customDialogConfirm.dismiss();
                }

            }
        });
        tvBindRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customDialogConfirm != null) {
                    customDialogConfirm.dismiss();
                }
                String trimName = mBinding.etNameOne.getText().toString().trim();
                if (TextUtils.isEmpty(trimName)) {
                    toast("请输入姓名");
                    return;
                }
                String zfbAccount = mBinding.etAccountOne.getText().toString().trim();
                if (TextUtils.isEmpty(zfbAccount)) {
                    toast("请输入支付宝的账号");
                    return;
                }
                showLoading();
                mViewModel.bindingWithdrawalAccount("" + id, "", "", "", "" + trimName, "" + zfbAccount, "1");

            }
        });
    }

    //绑定成功
    private void showBindSuccess() {
        CustomDialog customDialogConfirm = new CustomDialog(BindZFBActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_bind_success);
        customDialogConfirm.show();

        CountDownTimer countDownTimer = new CountDownTimer(1500, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (customDialogConfirm != null) {
                    customDialogConfirm.dismiss();
                }
                finish();

            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                String trimName = mBinding.etNameOne.getText().toString().trim();
                if (TextUtils.isEmpty(trimName)) {
                    toast("请输入姓名");
                    return;
                }
                String zfbAccount = mBinding.etAccountOne.getText().toString().trim();
                if (TextUtils.isEmpty(zfbAccount)) {
                    toast("请输入支付宝的账号");
                    return;
                }
                showLoading();
                mViewModel.bindingWithdrawalAccount("" + id, "", "", "", "" + trimName, "" + zfbAccount, "1");


                break;
        }
    }
}