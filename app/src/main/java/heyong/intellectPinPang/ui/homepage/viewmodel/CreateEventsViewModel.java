package heyong.intellectPinPang.ui.homepage.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import heyong.handong.framework.api.Api;
import heyong.handong.framework.api.Params;
import heyong.handong.framework.base.BaseViewModel;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.bean.competition.SelectClubListBean;
import heyong.intellectPinPang.bean.gsonbean.CreateEventsBean;
import heyong.intellectPinPang.bean.gsonbean.PostBean;
import heyong.intellectPinPang.model.RequestService;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CreateEventsViewModel extends BaseViewModel {
    public MutableLiveData<ResponseBean> getUploadTokenLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> createXlMatchInfoLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<List<SelectClubListBean>>> myClubListLiveData = new MutableLiveData<>();

    /**
     * 队内赛事列表
     */
    public void myClubList() {
        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).myClubList(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<List<SelectClubListBean>>>() {
                    @Override
                    public void onSuccess(ResponseBean<List<SelectClubListBean>> responseBean) {
                        myClubListLiveData.postValue(responseBean);
                    }
                });
    }



    /**
     * 七牛云上传图片获取凭证
     */
    public void getUploadToken(String fileName) {

        HashMap<String, String> params = Params.newParams()
                .put("fileName", fileName)
                .params();
        Api.getApiService(RequestService.class).getUploadToken(params).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                getUploadTokenLiveData.postValue(responseBean);
            }
        });

    }

    /**
     * 创建比赛
     */
    public void createXlMatchInfo(CreateEventsBean postBean) {

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).createXlMatchInfo(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                createXlMatchInfoLiveData.postValue(responseBean);
            }
        });
    }
}
