package heyong.intellectPinPang.ui.login.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.blankj.utilcode.util.StringUtils;
import com.elvishew.xlog.XLog;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
//import com.umeng.message.PushAgent;
//import com.umeng.message.UTrack;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import heyong.handong.framework.account.AccountHelper;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.LoginBean;
import heyong.intellectPinPang.databinding.ActivityLoginBinding;
import heyong.intellectPinPang.model.LoginViewModel;
import heyong.intellectPinPang.ui.MyHomePageActivity;
import heyong.intellectPinPang.ui.mine.activity.WebViewActivity;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.AppManager;
import heyong.intellectPinPang.utils.CommonUtilis;


/**
 * 登录界面 aaaaa aa
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements View.OnClickListener {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private int loginType = 0;//0 是验证码登录  1 密码登录
    private UMShareAPI mShareAPI;

    private int thirdLoginType = -1;// 1微信   2 qq
    public static LoginActivity instance = null;
    private String codeId = "";

    @Override
    public View[] filterViewByIds() {
        View[] views = {mBinding.ivPasswordClear, mBinding.ivTelCodeClear,
                mBinding.ivTelCodeLoginClear, mBinding.ivTelPhoneClear};
        return views;

    }

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_tel_code_login, R.id.et_tel_code, R.id.et_password, R.id.et_tel};
        return ids;
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mShareAPI = UMShareAPI.get(this);
        String token = AccountHelper.getToken();
        if (!TextUtils.isEmpty(token)) {
            goActivity(MyHomePageActivity.class);
            finish();
        }
        mBinding.setListener(this);
        mBinding.llCodeLogin.setVisibility(View.VISIBLE);
        mBinding.llLoginPassword.setVisibility(View.GONE);

        initCodeVisible(mBinding.etTelCodeLogin, mBinding.ivTelCodeLoginClear);
        initCodeVisible(mBinding.etTelCode, mBinding.ivTelCodeClear);
        initCodeVisible(mBinding.etTel, mBinding.ivTelPhoneClear);
        initCodeVisible(mBinding.etPassword, mBinding.ivPasswordClear);
        mBinding.ivPasswordClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.etPassword.setText("");
                mBinding.ivPasswordClear.setVisibility(View.GONE);
            }
        });
        mBinding.ivTelPhoneClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.etTel.setText("");
                mBinding.ivTelPhoneClear.setVisibility(View.GONE);
            }
        });
        mBinding.ivTelCodeLoginClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.etTelCodeLogin.setText("");
                mBinding.ivTelCodeLoginClear.setVisibility(View.GONE);
            }
        });
        mBinding.ivTelCodeClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.etTelCode.setText("");
                mBinding.ivTelCodeClear.setVisibility(View.GONE);
            }
        });


        mBinding.etTelCodeLogin.addTextChangedListener(new TextWatcher() {
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
                }
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
                passwordCheck(s);
                if (s.length() >= 1) {
                    mBinding.ivTelPhoneClear.setVisibility(View.VISIBLE);
                }

            }
        });
        mBinding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                passwordCheck(s);
                if (s.length() >= 1) {
                    mBinding.ivPasswordClear.setVisibility(View.VISIBLE);
                }
            }
        });

        /*密码登录*/
        mViewModel.passwordLiveData.observe(this, new Observer<ResponseBean<LoginBean>>() {
            @Override
            public void onChanged(ResponseBean<LoginBean> loginBeanResponseBean) {
                if (loginBeanResponseBean.getErrorCode().equals("200")) {
                    LoginBean data = loginBeanResponseBean.getData();
                    if (data != null) {
                        AccountHelper.login("" + data.getSign(),  data.getUserId(), "", "",
                                "", "" + data.getUserName());
                        Log.e(TAG, "onChanged:333 ====" + "zhinengpingcai_" + data.getUserId());
//zhinengpingcai_511486160083128336
                        Log.e(TAG, "onChanged: 333"+ JPushInterface.getRegistrationID(LoginActivity.this));
//                        PushAgent.getInstance(LoginActivity.this).setAlias("zhinengpingcai_" + data.getUserId(),
//                                "zhinengpingcai_type", new UTrack.ICallBack() {
//                                    @Override
//                                    public void onMessage(boolean isSuccess, String message) {
////                                        Log.e("UmengAlias", "i111sSuccess=" + isSuccess + "/nmessage==" + message);
////                                        Log.e("UmengAlias", "al111ias=" + "chuangbanlv_" + data.getUserId());
//                                    }
//                                });
                        goActivity(MyHomePageActivity.class);
                        dealWithAlias(data.getUserId());
                        finish();
                    } else {
                        toast("数据异常");
                    }
                } else if (loginBeanResponseBean.getErrorCode().equals("100000-100001")) {
                    toast("您还未设置密码请先用验证码登录");
                } else if (loginBeanResponseBean.getErrorCode().equals("100000-100002")) {
                    toast("未绑定手机号");
                } else if (loginBeanResponseBean.getErrorCode().equals("100000-100003")) {
                    toast("验证码失效");
                } else if (loginBeanResponseBean.getErrorCode().equals("100000-100004")) {
                    toast("验证码错误");
                } else if (loginBeanResponseBean.getErrorCode().equals("100000-100006")) {
                    toast("密码错误");
                } else {
                    toast("" + loginBeanResponseBean.getMessage());
                }
            }
        });
        /*密码登录111*/
        mViewModel.loginLiveData.observe(this, new Observer<ResponseBean<LoginBean>>() {
            @Override
            public void onChanged(ResponseBean<LoginBean> loginBeanResponseBean) {
                dismissLoading();
                if (loginBeanResponseBean.getErrorCode().equals("200") || loginBeanResponseBean.getErrorCode().equals("100000-100005")) {
                    LoginBean data = loginBeanResponseBean.getData();
                    if (data != null) {
                        AccountHelper.login("" + data.getSign(),  data.getUserId(), "", "",
                                "", "" + data.getUserName());
                        Log.e(TAG, "onChanged: 111====" + "zhinengpingcai_" + data.getUserId());
                        Log.e(TAG, "onChanged: 111"+ JPushInterface.getRegistrationID(LoginActivity.this));

//                        PushAgent.getInstance(LoginActivity.this).setAlias("zhinengpingcai_" + data.getUserId(),
//                                "zhinengpingcai_type", new UTrack.ICallBack() {
//                                    @Override
//                                    public void onMessage(boolean isSuccess, String message) {
////                                        Log.e("UmengAlias", "i111sSuccess=" + isSuccess + "/nmessage==" + message);
////                                        Log.e("UmengAlias", "al111ias=" + "chuangbanlv_" + data.getUserId());
//                                    }
//                                });
                        goActivity(MyHomePageActivity.class);
                        dealWithAlias(data.getUserId());
                        finish();
                    } else {
                        toast("数据异常");
                    }
                } else if (loginBeanResponseBean.getErrorCode().equals("100000-100001")) {
                    toast("您还未设置密码请先用验证码登录");
                } else if (loginBeanResponseBean.getErrorCode().equals("100000-100002")) {
                    toast("未绑定手机号");
                } else if (loginBeanResponseBean.getErrorCode().equals("100000-100003")) {
                    toast("验证码失效");
                } else if (loginBeanResponseBean.getErrorCode().equals("100000-100004")) {
                    toast("验证码错误");
                } else if (loginBeanResponseBean.getErrorCode().equals("100000-100006")) {
                    toast("密码错误");
                } else {
                    toast("" + loginBeanResponseBean.getMessage());
                }

            }
        });

        mViewModel.thirdLoginData.observe(this, new Observer<ResponseBean<LoginBean>>() {
            @Override
            public void onChanged(ResponseBean<LoginBean> responseBean) {
                LoginBean data = responseBean.getData();
                if (responseBean.getErrorCode().equals("100000-100002"))//未绑定手机号
                {
//                    AccountHelper.login("" + data.getSign(), 0, "", "",
//                            "", "");
                    long userId = responseBean.getData().getUserId();
                    Intent intent = new Intent(LoginActivity.this, BindPhoneActivity.class);
                    intent.putExtra(BindPhoneActivity.USER_ID, "" + userId);
                    intent.putExtra(BindPhoneActivity.THIRD_LOGIN_TYPE, "" + thirdLoginType);
                    intent.putExtra(BindPhoneActivity.USER_TOKEN, "" + data.getSign());
                    startActivity(intent);
                } else if (responseBean.getErrorCode().equals("100000-100005") ||
                        (responseBean.getErrorCode().equals("200")))//请设置密码  密码错误和200 都跳转首页
                {
                    AccountHelper.login("" + data.getSign(), data.getUserId(), "", "",
                            "", "" + data.getUserName());
                    Log.e(TAG, "onChanged:222 ====" + "zhinengpingcai_" + data.getUserId());
                    Log.e(TAG, "onChanged: 222"+ JPushInterface.getRegistrationID(LoginActivity.this));

//                    PushAgent.getInstance(LoginActivity.this).setAlias("zhinengpingcai_" + data.getUserId(),
//                            "zhinengpingcai_type", new UTrack.ICallBack() {
//                                @Override
//                                public void onMessage(boolean isSuccess, String message) {
////                                    Log.e("UmengAlias", "i111sSuccess=" + isSuccess + "/nmessage==" + message);
////                                    Log.e("UmengAlias", "al111ias=" + "chuangbanlv_" + data.getUserId());
//                                }
//                            });
                    goActivity(MyHomePageActivity.class);
                    dealWithAlias(data.getUserId());
                    finish();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });


        mViewModel.getCodeLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
//                if (responseBean.isSuccess()) {
                if (responseBean.getErrorCode().equals("200")) {

                    codeId = "哈哈哈";
                    mBinding.tvCodeLoginNumberError.setVisibility(View.GONE);
                    mBinding.btnGetVerifyCode.startCountDown();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });

    }

    private void dealWithAlias(long userId) {
        XLog.e( "dealWithAlias: userId===="+ "zhinengpingcai_"+userId);
        JPushInterface.setAlias(LoginActivity.this,1,"zhinengpingcai_"+userId);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    private void codeTextCheck(Editable s) {
        String telCodeLogin = getTelCodeLogin();
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
            mBinding.tvCodeLoginNumberError.setVisibility(View.GONE);
            mBinding.btnGetVerifyCode.setBackgroundResource(R.drawable.shape_solid_gray);
            mBinding.btnGetVerifyCode.setTextColor(Color.parseColor("#cccccc"));
        }
    }

    private void codeCheck(Editable s) {
        String telCodeLogin = getTelCodeLogin();
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

    private void passwordCheck(Editable s) {
        String phoneTel = getPhoneTel();
        String phonePassword = getPhonePassword();
        if (StringUtils.isEmpty(phoneTel) || StringUtils.isEmpty(phonePassword)) {
            mBinding.tvLogin.setEnabled(false);
            mBinding.tvLogin.setBackgroundResource(R.drawable.shape_no_commit);
            mBinding.tvLogin.setTextColor(Color.parseColor("#B2B2B2"));
        } else {
            mBinding.tvLogin.setEnabled(true);
            mBinding.tvLogin.setBackgroundResource(R.drawable.shape_croci_corners_25);
            mBinding.tvLogin.setTextColor(Color.parseColor("#ffffff"));
        }
        if (s.length() >= 1) {

        } else {
            mBinding.tvCodeLoginNumberError.setVisibility(View.GONE);
            mBinding.tvTelPasswordError.setVisibility(View.GONE);
        }
    }

    private String getTelCodeLogin() {
        return mBinding.etTelCodeLogin.getText().toString().trim();
    }

    private String getTelCode() {
        return mBinding.etTelCode.getText().toString().trim();
    }

    private String getPhoneTel() {
        return mBinding.etTel.getText().toString();
    }

    private String getPhonePassword() {
        return mBinding.etPassword.getText().toString();
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
            case R.id.tv_back_finish:
                AppManager.getAppManager().finishAllActivity();
                AppManager.getAppManager().AppExit();
//                System.exit(0);
                finish();
                break;
            case R.id.tv_login:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (loginType == 0) {
                    String phoneNumber = getTelCodeLogin();
                    String telCode = getTelCode();
                    if (TextUtils.isEmpty(phoneNumber)) {
                        toast("请输入手机号");
                        return;
                    }
//                    if (TextUtils.isEmpty(codeId)) {
//                        toast("请点击获取验证码");
//                        return;
//                    }
                    if (TextUtils.isEmpty(telCode)) {
                        toast("请输入验证码");
                        return;
                    }

                    boolean checked = mBinding.cbCheck.isChecked();
                    if (checked) {
                        /*验证码登录 xlkj2021*/

                        if (CommonUtilis.isMobileNO(phoneNumber.trim())) {
                            showLoading();
                            /*调用短信验证码登录  telCode*/
                            mViewModel.login("" + phoneNumber, ""+telCode, "", "", "", "1");
                        } else {
                            toast("请输入正确的手机号");
                        }
                    } else {
                        toast("请先同意用户协议");
                    }

                } else {
                    String phoneNumber = getPhoneTel();
                    String phonePassword = getPhonePassword();
                    if (TextUtils.isEmpty(phoneNumber)) {
                        toast("请输入手机号");
                        return;
                    }
                    if (TextUtils.isEmpty(phonePassword)) {
                        toast("请输入密码");
                        return;
                    }


                    if (CommonUtilis.isMobileNO(phoneNumber.trim())) {
                        boolean checked = mBinding.cbCheck.isChecked();
                        if (checked) {
                            boolean b = CommonUtilis.validatePhonePass(phonePassword);
                            if (b) {
                                if (phonePassword.length() < 8 || phonePassword.length() > 20) {
                                    toast("请输入8-20位数字和字母组合密码");
                                } else {
                                    boolean numeric = CommonUtilis.isNumeric(phonePassword);
                                    boolean aChar = CommonUtilis.isChar(phonePassword);
                                    if (!numeric && !aChar) {
                                        mViewModel.passwordLogin("" + phoneNumber, "" + phonePassword, "", "", "", "2");
                                    } else {
                                        toast("请输入8-20位数字和字母组合密码");
                                    }
                                }
                            } else {
                                toast("请输入8-20位数字和字母组合密码");
                            }
                        } else {
                            toast("请先同意用户协议");
                        }

                    } else {
                        toast("请输入正确的手机号");
                    }
                }
                break;
            case R.id.tv_login_type://登录方式
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (loginType == 0) {
                    /*验证码登录*/
                    mBinding.tvLoginType.setText("验证码登录");
                    mBinding.llCodeLogin.setVisibility(View.GONE);
                    mBinding.llLoginPassword.setVisibility(View.VISIBLE);
                    mBinding.tvTitleLoginType.setText("密码登录");
                    loginType = 1;
                } else if (loginType == 1) {
                    /*短信登录*/
                    mBinding.tvLoginType.setText("密码登录");
                    mBinding.tvTitleLoginType.setText("验证码登录");
                    mBinding.llCodeLogin.setVisibility(View.VISIBLE);
                    mBinding.llLoginPassword.setVisibility(View.GONE);
                    loginType = 0;

                }

                break;
            case R.id.btn_get_verify_code://获取验证码
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                String telCodeLogin = getTelCodeLogin();
                if (CommonUtilis.isMobileNO(telCodeLogin)) {
                    mBinding.tvCodeLoginNumberError.setVisibility(View.GONE);

                    codeId = "";
                    mViewModel.getCode(""+telCodeLogin,"1");

//                    codeId = "哈哈哈哈";
//                    mBinding.btnGetVerifyCode.startCountDown();
//13103590031
                } else {
                    toast("请输入正确的手机号");
                    mBinding.tvCodeLoginNumberError.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.tv_forget_password:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                goActivity(FindPasswordActivity.class);

                break;
            case R.id.iv_we_chat:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                boolean checked = mBinding.cbCheck.isChecked();
                if (checked) {
                    thirdLoginType = 3;
                    UMShareConfig config = new UMShareConfig();
                    config.isNeedAuthOnGetUserInfo(true);
                    UMShareAPI.get(LoginActivity.this).setShareConfig(config);
                    mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, authListener);
                } else {
                    toast("请先同意用户协议");
                }

                break;
            case R.id.iv_qq:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                boolean checked2 = mBinding.cbCheck.isChecked();
                if (checked2) {
                    thirdLoginType = 4;
                    UMShareConfig config2 = new UMShareConfig();

                    config2.isNeedAuthOnGetUserInfo(true);
                    UMShareAPI.get(LoginActivity.this).setShareConfig(config2);
                    mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, authListener);
                } else {
                    toast("请先同意用户协议");
                }

                break;
            case R.id.tv_policy://隐私政策
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Intent intentWeb = new Intent(this, WebViewActivity.class);
                intentWeb.putExtra(WebViewActivity.TITLE, "隐私政策");
                intentWeb.putExtra(WebViewActivity.URLS, "http://images.xlttsports.com/%E9%9A%90%E7%A7%81%E6%94%BF%E7%AD%96.html");
                startActivity(intentWeb);

                break;
            case R.id.tv_user_agreement://用户协议
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Intent intentWeb2 = new Intent(this, WebViewActivity.class);
                intentWeb2.putExtra(WebViewActivity.TITLE, "用户协议");
                intentWeb2.putExtra(WebViewActivity.URLS, "http://images.xlttsports.com/%E7%94%A8%E6%88%B7%E5%8D%8F%E8%AE%AE.html");
                startActivity(intentWeb2);


                break;

        }
    }


    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Logger.d(data);
            Log.e(TAG, "onComplete: 授权返回数据" + new Gson().toJson(data));
            if (platform == SHARE_MEDIA.WEIXIN) {
                String sex;
                try {
                    if (data.get("gender") != null) {
                        sex = "男".equals(data.get("gender")) ? "1" : "0";
                    } else {
                        sex = "1";
                    }
                } catch (Exception e) {
                    sex = "1";
                }
                String iconurl = data.get("iconurl");
                String openid = data.get("openid");
                String screen_name = data.get("screen_name");
                //   country -> 中国 unionid -> oOKvQt-cFYNfy3Mn6n7jB4CKw1fo gender -> 男 city -> 衡水
                //openid -> oQsxN1qwjhwcLnfjcS1JO0O6Rwb4 language -> zh_CN profile_image_url -> https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJgb7ZmNqrdUOSeyz46NddNyRIpOBiaImoVLNacxZ9XFeW3EoRGH5Wod0ibjB5Y4iat0wfpZYDicUicuuw/132
                //accessToken -> 40_QuyCKygovFZ-e9V7L2wAG5lY2crzF90LPN5RYGvAPmJpK9e9O5BMYHlrq7MJqVj5Bzrrw3IvRr8LAQ2e3KtpSl7QqlVF9xYo-MdAZ8nm2dg
                //access_token -> 40_QuyCKygovFZ-e9V7L2wAG5lY2crzF90LPN5RYGvAPmJpK9e9O5BMYHlrq7MJqVj5Bzrrw3IvRr8LAQ2e3KtpSl7QqlVF9xYo-MdAZ8nm2dg
                //uid -> oOKvQt-cFYNfy3Mn6n7jB4CKw1fo   province -> 河北 screen_name -> 命运の我手中 name -> 命运の我手中
                //iconurl -> https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJgb7ZmNqrdUOSeyz46NddNyRIpOBiaImoVLNacxZ9XFeW3EoRGH5Wod0ibjB5Y4iat0wfpZYDicUicuuw/132
                //expiration -> 1608628390910  expires_in -> 1608628390910  refreshToken -> 40_FmrtsUjaMJHDLQuEyfHRzRRycnDii1NquR3A6j_jVpYUptNGJJqQm5rZF2Kg_MWzhBGm8X8NtmrXPapmUeP67Datzw6jnxgztBlCGQuGYuU
                mViewModel.thirdLogin("" + openid, "", "" + iconurl, "" + screen_name, "" + thirdLoginType);
            } else if (platform == SHARE_MEDIA.QQ) {
                String iconurl = data.get("iconurl");
                String screen_name = data.get("screen_name");
                String openid = data.get("openid");
                mViewModel.thirdLogin("", "" + openid, "" + iconurl, "" + screen_name, "" + thirdLoginType);
            }
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            toast("失败：" + t.getMessage());
            thirdLoginType = -1;
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            toast("取消了");
            thirdLoginType = -1;
            //dismissNewLoading();
            //Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return false;//拦截事件
            case KeyEvent.KEYCODE_MENU:
                break;
            case KeyEvent.KEYCODE_HOME:
                // 收不到
                break;
            case KeyEvent.KEYCODE_APP_SWITCH:
                // 收不到
                break;
            default:
                break;
        }

        return super.onKeyDown(keyCode, event);

    }

}
