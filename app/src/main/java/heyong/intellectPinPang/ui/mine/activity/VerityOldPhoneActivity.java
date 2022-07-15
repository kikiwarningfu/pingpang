package heyong.intellectPinPang.ui.mine.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.blankj.utilcode.util.StringUtils;

import heyong.handong.framework.account.AccountHelper;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityVerityOldPhoneBinding;
import heyong.intellectPinPang.ui.mine.MineViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.CommonUtilis;

/**
 * 验证旧手机号
 */
public class VerityOldPhoneActivity extends BaseActivity<ActivityVerityOldPhoneBinding, MineViewModel> implements View.OnClickListener {

    String telephone;
    private String codeId = "";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_verity_old_phone;
    }

    @Override
    public View[] filterViewByIds() {
        View[] views = {mBinding.ivTelCodeClear};
        return views;
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_tel, R.id.et_tel_code};
        return ids;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        telephone = AccountHelper.getTelephone();
        mBinding.etTel.setText("" + telephone);
        initCodeVisible(mBinding.etTelCode, mBinding.ivTelCodeClear);
        mViewModel.updateAccountLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                //                if (responseBean.isSuccess()) {
                if (responseBean.getErrorCode().equals("200")) {

                    goActivity(ChangeBindPhoneActivity.class);
                    finish();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        mBinding.etTelCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                codeCheck(s);
                if (s.length() >= 1) {
                    mBinding.ivTelCodeClear.setVisibility(View.VISIBLE);
                } else if (s.length() == 0) {
                    mBinding.ivTelCodeClear.setVisibility(View.GONE);
                }
            }
        });
        mViewModel.getCodeLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                //                if (responseBean.isSuccess()) {
                if (responseBean.getErrorCode().equals("200")) {

                    mBinding.btnGetVerifyCode.startCountDown();
                    codeId = "哈哈哈哈";
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
    }

    private void codeCheck(Editable s) {
        String phonePassword = getTelCode();
        if (StringUtils.isEmpty(phonePassword)) {
            mBinding.tvLogin.setEnabled(false);
            mBinding.tvLogin.setBackgroundResource(R.drawable.shape_no_commit);
            mBinding.tvLogin.setTextColor(Color.parseColor("#B2B2B2"));
        } else {
            mBinding.tvLogin.setEnabled(true);
            mBinding.tvLogin.setBackgroundResource(R.drawable.shape_croci_corners_25);
            mBinding.tvLogin.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    private String getTelCode() {
        return mBinding.etTelCode.getText().toString().trim();
    }


    private void initCodeVisible(EditText etTelCodeLogin, ImageView ivTelCodeLogisn) {
        etTelCodeLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String s = mBinding.etTelCode.getText().toString();
                    if (s.length() >= 1) {
                        ivTelCodeLogisn.setVisibility(View.VISIBLE);
                    } else {
                        ivTelCodeLogisn.setVisibility(View.GONE);
                    }
                } else {
                    ivTelCodeLogisn.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_login://判断 是否确认更换
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                String etCode = getTelCode();
                if (TextUtils.isEmpty(etCode)) {
                    toast("请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(codeId)) {
                    toast("请点击获取验证码");
                    return;
                }
                mViewModel.updateAccount(AccountHelper.getTelephone(), "" + etCode);


                break;
            case R.id.btn_get_verify_code:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                String telCodeLogin = mBinding.etTel.getText().toString();

                if (CommonUtilis.isMobileNO(telCodeLogin)) {

                    codeId = "";
                    mViewModel.getCode("" + telephone, "3");
                } else {
                    toast("请输入正确的手机号");
                }

                break;
        }
    }
}