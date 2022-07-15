package heyong.intellectPinPang.model;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import heyong.handong.framework.api.Api;
import heyong.handong.framework.base.BaseViewModel;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.bean.competition.LoginBean;
import heyong.intellectPinPang.bean.gsonbean.PostBean;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginViewModel extends BaseViewModel {
    public MutableLiveData<ResponseBean<LoginBean>> loginLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LoginBean>> passwordLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LoginBean>> thirdLoginData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LoginBean>> bindPhoneNumLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> getCodeLiveData = new MutableLiveData<>();//发送验证码

    /**
     * 登录
     */
    public void login(String account, String code, String oppenId, String qqId, String iosId, String loginType) {

        PostBean loginPostBean = new PostBean();
        if (!TextUtils.isEmpty(account)) {
            loginPostBean.setAccount(account);
        }
        if (!TextUtils.isEmpty(code)) {
            loginPostBean.setCode(code);
        }
        if (!TextUtils.isEmpty(oppenId)) {
            loginPostBean.setOppenId(oppenId);
        }
        if (!TextUtils.isEmpty(qqId)) {
            loginPostBean.setQqId(qqId);
        }
        if (!TextUtils.isEmpty(iosId)) {
            loginPostBean.setIosId(iosId);
        }
        if (!TextUtils.isEmpty(loginType)) {
            loginPostBean.setLoginType(loginType);
        }
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).loginAuthentication(requestBody).subscribe(new SimpleObserver<ResponseBean<LoginBean>>() {
            @Override
            public void onSuccess(ResponseBean<LoginBean> responseBean) {
                loginLiveData.postValue(responseBean);
            }
        });
    }
    /**
     * 密码登录
     */
    public void passwordLogin(String account, String code, String oppenId, String qqId, String iosId, String loginType) {

        PostBean loginPostBean = new PostBean();
        if (!TextUtils.isEmpty(account)) {
            loginPostBean.setAccount(account);
        }
        if (!TextUtils.isEmpty(code)) {
            loginPostBean.setPassword(code);
        }
        if (!TextUtils.isEmpty(oppenId)) {
            loginPostBean.setOppenId(oppenId);
        }
        if (!TextUtils.isEmpty(qqId)) {
            loginPostBean.setQqId(qqId);
        }
        if (!TextUtils.isEmpty(iosId)) {
            loginPostBean.setIosId(iosId);
        }
        if (!TextUtils.isEmpty(loginType)) {
            loginPostBean.setLoginType(loginType);
        }
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).loginAuthentication(requestBody).subscribe(new SimpleObserver<ResponseBean<LoginBean>>() {
            @Override
            public void onSuccess(ResponseBean<LoginBean> responseBean) {
                passwordLiveData.postValue(responseBean);
            }
        });
    }
    /*三方登录*/
    public void thirdLogin(String oppenId, String qqId, String headImg, String nickName, String loginType) {

        PostBean loginPostBean = new PostBean();

        if (!TextUtils.isEmpty(oppenId)) {
            loginPostBean.setOppenId(oppenId);
        }
        if (!TextUtils.isEmpty(qqId)) {
            loginPostBean.setQqId(qqId);
        }

        if (!TextUtils.isEmpty(headImg)) {
            loginPostBean.setHeadImg(headImg);
        }
        if (!TextUtils.isEmpty(nickName)) {
            loginPostBean.setNickName(nickName);
        }

        if (!TextUtils.isEmpty(loginType)) {
            loginPostBean.setLoginType(loginType);
        }
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).loginAuthentication(requestBody).subscribe(new SimpleObserver<ResponseBean<LoginBean>>() {
            @Override
            public void onSuccess(ResponseBean<LoginBean> responseBean) {
                thirdLoginData.postValue(responseBean);
            }
        });


    }

    /**
     * 绑定手机号
     */
    public void bindPhoneNum(String loginType, String code,String id,String account) {

        PostBean loginPostBean = new PostBean();
        if (!TextUtils.isEmpty(loginType)) {
            loginPostBean.setLoginType(loginType);
        }
        if (!TextUtils.isEmpty(code)) {
            loginPostBean.setCode(code);
        }
        loginPostBean.setId(""+id);
        loginPostBean.setAccount(""+account);
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).bindPhoneNum(requestBody).subscribe(new SimpleObserver<ResponseBean<LoginBean>>() {
            @Override
            public void onSuccess(ResponseBean<LoginBean> responseBean) {
                bindPhoneNumLiveData.postValue(responseBean);
            }
        });
    }
    /**
     * 发送验证码接口
     */
    public void getCode(String account, String codeType) {
        PostBean postBean = new PostBean();
        postBean.setAccount(account);
        postBean.setCodeType(codeType);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getCode(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {

                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        getCodeLiveData.postValue(responseBean);
                    }
                });
    }

}
