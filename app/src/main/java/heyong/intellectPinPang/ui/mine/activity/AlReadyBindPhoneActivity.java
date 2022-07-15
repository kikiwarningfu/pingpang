package heyong.intellectPinPang.ui.mine.activity;

import androidx.annotation.Nullable;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import heyong.handong.framework.account.AccountHelper;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityAlReadyBindPhoneBinding;
import heyong.intellectPinPang.utils.AntiShakeUtils;

/**
 * 已绑定手机号
 */
public class AlReadyBindPhoneActivity extends BaseActivity<ActivityAlReadyBindPhoneBinding, BaseViewModel> implements View.OnClickListener {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_al_ready_bind_phone;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        mBinding.tvTel.setText("" + AccountHelper.getTelephone());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_change_tel_phone://更改手机号
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                String telephone = AccountHelper.getTelephone();
                if (!TextUtils.isEmpty(telephone)) {
                    goActivity(VerityOldPhoneActivity.class);
                    finish();
                }
                break;
        }
    }
}