package heyong.intellectPinPang.ui.login.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.blankj.utilcode.util.StringUtils;
//import com.umeng.message.PushAgent;
//import com.umeng.message.UTrack;

import heyong.handong.framework.account.AccountHelper;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.LoginBean;
import heyong.intellectPinPang.databinding.ActivityBindPhoneBinding;
import heyong.intellectPinPang.model.LoginViewModel;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.intellectPinPang.ui.MyHomePageActivity;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.CommonUtilis;

/**
 * 绑定手机号
 */
public class BindPhoneActivity extends BaseActivity<ActivityBindPhoneBinding, LoginViewModel> implements View.OnClickListener {
    public static final String USER_ID = "user_id";
    public static final String USER_TOKEN = "user_token";
    public static final String THIRD_LOGIN_TYPE = "third_login_type";
    private String userId = "";
    private String thirdLoginType = "";
    public static final String TAG = BindPhoneActivity.class.getSimpleName();
    private String codeId = "";
    private String strUserToken = "";
    private boolean isSuccessBind = false;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_bind_phone;
    }

    @Override
    public View[] filterViewByIds() {
        View[] views = {mBinding.ivTelCodeClose, mBinding.ivTelClose};
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
        userId = getIntent().getStringExtra(USER_ID);
        thirdLoginType = getIntent().getStringExtra(THIRD_LOGIN_TYPE);
        strUserToken = getIntent().getStringExtra(USER_TOKEN);
        Log.e(TAG, "initView: " + userId);
        initCodeVisible(mBinding.etTel, mBinding.ivTelClose);
        initCodeVisible(mBinding.etTelCode, mBinding.ivTelCodeClose);
        mBinding.ivTelClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.etTel.setText("");
                mBinding.ivTelClose.setVisibility(View.GONE);
            }
        });
        mBinding.ivTelCodeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.etTelCode.setText("");
                mBinding.ivTelCodeClose.setVisibility(View.GONE);
            }
        });

        mBinding.etTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etTelCheck(s);
                if (s.length() >= 1) {
                    mBinding.ivTelClose.setVisibility(View.VISIBLE);
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
                    mBinding.ivTelCodeClose.setVisibility(View.VISIBLE);
                }
            }

            private void codeTextCheck(Editable s) {
                String telNumber = getTelNumber();
                String telCode = getTelCode();
                if (StringUtils.isEmpty(telNumber) || StringUtils.isEmpty(telCode)) {
                    mBinding.tvLogin.setEnabled(false);
                    mBinding.tvLogin.setBackgroundResource(R.drawable.shape_no_commit);
                    mBinding.tvLogin.setTextColor(Color.parseColor("#B2B2B2"));
                } else {
                    mBinding.tvLogin.setEnabled(true);
                    mBinding.tvLogin.setBackgroundResource(R.drawable.shape_croci_corners_25);
                    mBinding.tvLogin.setTextColor(Color.parseColor("#ffffff"));
                }
            }
        });

        mViewModel.bindPhoneNumLiveData.observe(this, new Observer<ResponseBean<LoginBean>>() {
            @Override
            public void onChanged(ResponseBean<LoginBean> loginBeanResponseBean) {
                LoginBean data = loginBeanResponseBean.getData();
                if (loginBeanResponseBean.getErrorCode().equals("200")) {
                    isSuccessBind = true;
                    AccountHelper.login("" + data.getSign(), data.getUserId(), "", "",
                            "", "" + data.getUserName());
                    goActivity(MyHomePageActivity.class);
//                    PushAgent.getInstance(BindPhoneActivity.this).setAlias("" + data.getUserId(),
//                            "String", new UTrack.ICallBack() {
//                                @Override
//                                public void onMessage(boolean isSuccess, String message) {
////                                    Log.i("UmengAlias", "isSuccess=" + isSuccess + "/nmessage==" + message);
////                                    Log.i("UmengAlias", "alias=" + "chuangbanlv_" + data.getUserId());
//                                }
//                            });
                    finish();
                } else {
                    toast("" + loginBeanResponseBean.getMessage());
                }
            }
        });
        mViewModel.getCodeLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
//                if (responseBean.isSuccess()) {
                if (responseBean.getErrorCode().equals("200")) {

                    codeId = "哈哈哈";
                    mBinding.btnGetVerifyCode.startCountDown();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
    }

    private void etTelCheck(Editable s) {
        String telNumber = getTelNumber();
        String telCode = getTelCode();
        if (StringUtils.isEmpty(telNumber) || StringUtils.isEmpty(telCode)) {
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

    private String getTelNumber() {
        return mBinding.etTel.getText().toString().trim();
    }

    private String getTelCode() {
        return mBinding.etTelCode.getText().toString().trim();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                String phoneNumber = getTelNumber();
                String telCode = getTelCode();
                if (TextUtils.isEmpty(phoneNumber)) {
                    toast("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(telCode)) {
                    toast("请输入验证码");
                    return;
                }


                if (CommonUtilis.isMobileNO(phoneNumber.trim())) {
                    /*调用短信验证码登录*/
                    mViewModel.bindPhoneNum("" + thirdLoginType,
                            "" + telCode, "" + userId, "" + phoneNumber);
                } else {
                    toast("请输入正确的手机号");
                }

                break;
            case R.id.btn_get_verify_code:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                String phoneNumber1 = getTelNumber();

                if (!TextUtils.isEmpty(phoneNumber1) && phoneNumber1.length() == 11) {

                    if (CommonUtilis.isMobileNO(phoneNumber1.trim())) {

                        codeId = "";
                        mViewModel.getCode("" + phoneNumber1, "3");
//                        codeId = "哈哈哈哈";
//                        mBinding.btnGetVerifyCode.startCountDown();
                    } else {
                        toast("请输入正确的手机号");
                    }
                } else {
                    toast("请输入正确的手机号");
                }

                break;
            case R.id.iv_close:
                try {
//                    LoginActivity.instance.finish();
                    AccountHelper.setToken("");
                    finish();
                } catch (Exception e) {
                    AccountHelper.setToken("");
                    finish();
                }

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isSuccessBind) {
            AccountHelper.setToken("");
        }
    }
}
