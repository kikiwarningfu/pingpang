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

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityChangeBindPhoneBinding;
import heyong.intellectPinPang.ui.mine.MineViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.CommonUtilis;
import heyong.intellectPinPang.utils.MessageDialogBuilder;

/**
 * 更换绑定手机号
 */
public class ChangeBindPhoneActivity extends BaseActivity<ActivityChangeBindPhoneBinding, MineViewModel> implements View.OnClickListener {

    public static final String TAG = ChangeBindPhoneActivity.class.getSimpleName();
    private String codeId = "";

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_tel_number, R.id.et_tel_code};
        return ids;
    }

    @Override
    public View[] filterViewByIds() {
        View[] views = {mBinding.ivTelCodeClear,mBinding.ivTelCodeLoginClear};
        return views;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_change_bind_phone;
    }


    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        initCodeVisibleOne(mBinding.etTelCode, mBinding.ivTelCodeClear);
        initCodeVisibleTwo(mBinding.etTelNumber, mBinding.ivTelCodeLoginClear);

        mBinding.ivTelCodeClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.etTelCode.setText("");
                mBinding.ivTelCodeClear.setVisibility(View.GONE);
            }
        });
        mBinding.ivTelCodeLoginClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.etTelNumber.setText("");
                mBinding.ivTelCodeLoginClear.setVisibility(View.GONE);


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
                codeTextCheck(s);
                if (s.length() >= 1) {
                    mBinding.ivTelCodeLoginClear.setVisibility(View.VISIBLE);
                } else if (s.length() == 0) {
                    mBinding.ivTelCodeLoginClear.setVisibility(View.GONE);
                }

            }
        });
        mViewModel.getCodeLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
//                if (responseBean.isSuccess()) {
                    if(responseBean.getErrorCode().equals("200")){

                        codeId = "哈哈哈哈";
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
                codeCheck(s);
                if (s.length() >= 1) {
                    mBinding.ivTelCodeClear.setVisibility(View.VISIBLE);
                } else if (s.length() == 0) {
                    mBinding.ivTelCodeClear.setVisibility(View.GONE);
                }

            }
        });
        mViewModel.updateAccountLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
//                if (responseBean.isSuccess()) {
                    if(responseBean.getErrorCode().equals("200")){

                        new MessageDialogBuilder(ChangeBindPhoneActivity.this)
                            .setMessage("")
                            .setTvTitle("更换成功")
                            .setTvSure("确定")
                            .setBtnCancleHint(true)
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            })
                            .show();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });


    }

    private void codeTextCheck(Editable s) {
        String telCodeLogin = getPhonNumber();
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
            mBinding.btnGetVerifyCode.setBackgroundResource(R.drawable.shape_solid_gray);
            mBinding.btnGetVerifyCode.setTextColor(Color.parseColor("#cccccc"));
        }
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


    private void initCodeVisibleOne(EditText etTelCodeLogin, ImageView ivTelCodeLogisn) {
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

    private void initCodeVisibleTwo(EditText etTelCodeLogin, ImageView ivTelCodeLogisn) {
        etTelCodeLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String s = mBinding.etTelNumber.getText().toString();
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

    private String getPhonNumber() {
        return mBinding.etTelNumber.getText().toString().trim();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_login://判断 是否确认更换
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                String telNumber = getPhonNumber();
                String etCode = getTelCode();
                if (TextUtils.isEmpty(etCode)) {
                    toast("请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(codeId)) {
                    toast("请点击获取验证码");
                    return;
                }
                new MessageDialogBuilder(ChangeBindPhoneActivity.this)
                        .setMessage("新手机号:" + getPhonNumber())
                        .setTvTitle("是否确认更换？")
                        .setTvCancel("取消")
                        .setTvSure("确定")
                        .setBtnCancleHint(false)
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mViewModel.updateAccount(telNumber, "" + etCode);
                            }
                        })
                        .show();
                break;
            case R.id.btn_get_verify_code:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                String telCodeLogin = getPhonNumber();

                if (CommonUtilis.isMobileNO(telCodeLogin)) {
                    codeId = "";
                    mViewModel.getCode("" + telCodeLogin, "" + 3);

                } else {
                    toast("请输入正确的手机号");
                }
        }
    }
}