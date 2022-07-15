package heyong.intellectPinPang.ui.login.activity;

import android.content.Intent;
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

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityFindPasswordBinding;
import heyong.intellectPinPang.ui.mine.MineViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.CommonUtilis;

/**
 * 找回密码
 */
public class FindPasswordActivity extends BaseActivity<ActivityFindPasswordBinding, MineViewModel> implements View.OnClickListener {

    private String codeId = "";
    public static final String TYPE = "type";
    private String telNumber = "";

    @Override
    public View[] filterViewByIds() {
        View[] views = {mBinding.ivTelPhoneClear, mBinding.ivTelCodeClear};
        return views;
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_tel_number, R.id.et_tel_code};
        return ids;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_find_password;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);

        initCodeVisible(mBinding.etTelNumber, mBinding.ivTelPhoneClear);
        initCodeVisible(mBinding.etTelCode, mBinding.ivTelCodeClear);
        mBinding.ivTelPhoneClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.etTelNumber.setText("");
                mBinding.ivTelPhoneClear.setVisibility(View.GONE);
            }
        });
        mBinding.ivTelCodeClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.etTelCode.setText("");
                mBinding.ivTelCodeClear.setVisibility(View.GONE);
            }
        });
        mViewModel.getCodeLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                //                if (responseBean.isSuccess()) {
                if (responseBean.getErrorCode().equals("200")) {

                    codeId = "哈哈哈";
                    mBinding.tvTelNumberError.setVisibility(View.GONE);
                    mBinding.btnGetVerifyCode.startCountDown();
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
                codeTextCheck(s);
                if (s.length() >= 1) {
                    mBinding.ivTelCodeClear.setVisibility(View.VISIBLE);
                }
            }
        });
        mBinding.etTelNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String telCodeLogin = getTelNumber();
                String telCode = getTelCode();
                if (StringUtils.isEmpty(telCodeLogin) || StringUtils.isEmpty(telCode)) {
                    mBinding.tvLogin.setEnabled(false);
                    mBinding.tvLogin.setBackgroundResource(R.drawable.shape_no_commit);
                    mBinding.tvLogin.setTextColor(Color.parseColor("#B2B2B2"));
                } else {
                    mBinding.tvLogin.setEnabled(true);
                    mBinding.tvLogin.setBackgroundResource(R.drawable.shape_croci_corners_25);
                    mBinding.tvLogin.setTextColor(Color.parseColor("#ffffff"));
                }
                if (s.length() >= 1) {
                    mBinding.btnGetVerifyCode.setBackgroundResource(R.drawable.shape_solid_blue);
                    mBinding.btnGetVerifyCode.setTextColor(Color.parseColor("#4795ED"));
                } else {
                    mBinding.tvTelNumberError.setVisibility(View.GONE);
                    mBinding.btnGetVerifyCode.setBackgroundResource(R.drawable.shape_solid_gray);
                    mBinding.btnGetVerifyCode.setTextColor(Color.parseColor("#cccccc"));
                }
            }
        });

        mViewModel.updatePasswordFindLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    Intent intent = new Intent(FindPasswordActivity.this, SetPasswordActivity.class);
                    intent.putExtra(SetPasswordActivity.ACCOUNT, "" + telNumber);
                    startActivity(intent);
                    finish();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

    }

    private void codeTextCheck(Editable s) {
        String telCodeLogin = getTelNumber();
        String telCode = getTelCode();
        if (StringUtils.isEmpty(telCodeLogin) || StringUtils.isEmpty(telCode)) {
            mBinding.tvLogin.setEnabled(false);
            mBinding.tvLogin.setBackgroundResource(R.drawable.shape_no_commit);
            mBinding.tvLogin.setTextColor(Color.parseColor("#B2B2B2"));
        } else {
            mBinding.tvLogin.setEnabled(true);
            mBinding.tvLogin.setBackgroundResource(R.drawable.shape_croci_corners_25);
            mBinding.tvLogin.setTextColor(Color.parseColor("#ffffff"));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_verify_code://获取验证码
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                telNumber = getTelNumber();
                if (CommonUtilis.isMobileNO(telNumber)) {
                    codeId = "";
                    mViewModel.getCode("" + telNumber, "2");
                } else {
                    toast("请输入正确的手机号");
                    mBinding.tvTelNumberError.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_login:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                String code = mBinding.etTelCode.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    toast("验证码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(codeId)) {
                    toast("请点击获取验证码");
                    return;
                }
                mViewModel.updatePasswordFind("" + code, "" + telNumber);
                break;
            case R.id.iv_back:
                finish();
                break;
        }


    }

    private void initCodeVisible(EditText etTelCodeLogin, ImageView ivTelCodeLogisn) {
        etTelCodeLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ivTelCodeLogisn.setVisibility(View.VISIBLE);
                } else {
                    ivTelCodeLogisn.setVisibility(View.GONE);
                }
            }
        });
    }


    private String getTelNumber() {
        return mBinding.etTelNumber.getText().toString().trim();
    }

    private String getTelCode() {
        return mBinding.etTelCode.getText().toString().trim();
    }

}