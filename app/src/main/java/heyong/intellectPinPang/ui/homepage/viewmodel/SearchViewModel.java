package heyong.intellectPinPang.ui.homepage.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import heyong.handong.framework.api.Api;
import heyong.handong.framework.base.BaseViewModel;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.bean.competition.XlClubInfoListBean;
import heyong.intellectPinPang.bean.competition.XlUserInfoListBean;
import heyong.intellectPinPang.bean.gsonbean.PostBean;
import heyong.intellectPinPang.model.RequestService;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SearchViewModel extends BaseViewModel {
    public MutableLiveData<ResponseBean<XlUserInfoListBean>> getXlUserInfoListLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<XlClubInfoListBean>> getXlClubInfoListLiveData = new MutableLiveData<>();

    /**
     * 俱乐部列表
     */
    public void getXlClubInfoList(String pageNo, String pageSize, String teamTitle, String city,String textCode) {
        PostBean loginPostBean = new PostBean();
        if (!TextUtils.isEmpty(textCode)) {
            loginPostBean.setTeamType(textCode);
        }
        if (!TextUtils.isEmpty(pageNo)) {
            loginPostBean.setPageNo(pageNo);
        }
        if (!TextUtils.isEmpty(pageSize)) {
            loginPostBean.setPageSize(pageSize);
        }
        if (!TextUtils.isEmpty(teamTitle)) {
            loginPostBean.setTeamTitle(teamTitle);
        }
        if (!TextUtils.isEmpty(city)) {
            loginPostBean.setCity(city);
        }
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getXlClubInfoList(requestBody).subscribe(new SimpleObserver<ResponseBean<XlClubInfoListBean>>() {
            @Override
            public void onSuccess(ResponseBean<XlClubInfoListBean> responseBean) {
                getXlClubInfoListLiveData.postValue(responseBean);
            }
        });

    }
    /**
     * 运动员列表
     */
    public void getXlUserInfoList(String pageNo, String pageSize, String name) {

        PostBean postBean = new PostBean();
        if (!TextUtils.isEmpty(pageNo)) {
            postBean.setPageNo(pageNo);
        }
        if (!TextUtils.isEmpty(pageSize)) {
            postBean.setPageSize(pageSize);
        }
        if (!TextUtils.isEmpty(name)) {
            postBean.setName(name);
        }
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getXlUserInfoList(requestBody).subscribe(new SimpleObserver<ResponseBean<XlUserInfoListBean>>() {
            @Override
            public void onSuccess(ResponseBean<XlUserInfoListBean> responseBean) {
                getXlUserInfoListLiveData.postValue(responseBean);
            }
        });
    }




}
