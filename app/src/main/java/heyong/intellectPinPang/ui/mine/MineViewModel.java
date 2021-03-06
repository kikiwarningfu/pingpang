package heyong.intellectPinPang.ui.mine;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import heyong.handong.framework.api.Api;
import heyong.handong.framework.api.OtherApi;
import heyong.handong.framework.api.Params;
import heyong.handong.framework.base.BaseViewModel;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.bean.competition.AllAchievementBean;
import heyong.intellectPinPang.bean.competition.AllMyInfoBean;
import heyong.intellectPinPang.bean.competition.AwardsMathListBean;
import heyong.intellectPinPang.bean.competition.CoachMatchDtaBean;
import heyong.intellectPinPang.bean.competition.CoashRoleListBean;
import heyong.intellectPinPang.bean.competition.EnListBean;
import heyong.intellectPinPang.bean.competition.IdentifyBean;
import heyong.intellectPinPang.bean.competition.MatchBaseInfoBean;
import heyong.intellectPinPang.bean.competition.MatchOrderInfoBean;
import heyong.intellectPinPang.bean.competition.MessageInfoListBean;
import heyong.intellectPinPang.bean.competition.ModelDataBrandIdBean;
import heyong.intellectPinPang.bean.competition.MyAchievementBean;
import heyong.intellectPinPang.bean.competition.MyBaseInfoBean;
import heyong.intellectPinPang.bean.competition.MyClubListBean;
import heyong.intellectPinPang.bean.competition.MyMatchBean;
import heyong.intellectPinPang.bean.competition.QueryMatchApplyInfoBean;
import heyong.intellectPinPang.bean.competition.RefereeInfoBean;
import heyong.intellectPinPang.bean.competition.ThroughListBean;
import heyong.intellectPinPang.bean.competition.TitleListBean;
import heyong.intellectPinPang.bean.competition.UpdateXlUserInfoBean;
import heyong.intellectPinPang.bean.competition.XlEquipmentBrandListBean;
import heyong.intellectPinPang.bean.competition.XlMessageInfoPostBean;
import heyong.intellectPinPang.bean.gsonbean.PostBean;
import heyong.intellectPinPang.bean.pay.WXPayBean;
import heyong.intellectPinPang.model.RequestService;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MineViewModel extends BaseViewModel {
    public MutableLiveData<ResponseBean> getUploadTokenLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> updateXlUserInfoLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<XlEquipmentBrandListBean>> getXlEquipmentBrandListLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<List<ModelDataBrandIdBean>>> getModelDataByBrandIdLiveData = new MutableLiveData<>();

    public MutableLiveData<ResponseBean<MyClubListBean>> getMyClubLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> verifyMaterialLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<MyMatchBean>> getMyMatchLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<IdentifyBean>> getIdentityInfoLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<List<CoachMatchDtaBean>>> getCoachMatchDtaLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<MessageInfoListBean>> getXlMessageInfoListLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> updateXlMessageInfoLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<TitleListBean>> getTitleListLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<MyBaseInfoBean>> myBaseInfoLiveData = new MutableLiveData<>();//???????????????????????????
    public MutableLiveData<ResponseBean<EnListBean>> myEnlistLiveData = new MutableLiveData<>();//????????????
    public MutableLiveData<ResponseBean> deleteXlEnrollMatchLiveData = new MutableLiveData<>();//????????????
    public MutableLiveData<ResponseBean> signOutMatchOrderLiveData = new MutableLiveData<>();//????????????
    public MutableLiveData<ResponseBean<RefereeInfoBean>> refereeInfoLiveData = new MutableLiveData<>();//???????????????
    public MutableLiveData<ResponseBean<List<CoashRoleListBean>>> coashRoleListBeanLiveData = new MutableLiveData<>();//?????????????????????
    public MutableLiveData<ResponseBean> updateRefereeInfoLiveData = new MutableLiveData<>();//?????? ?????????????????????
    public MutableLiveData<ResponseBean<List<MyAchievementBean>>> myAchievementLiveData = new MutableLiveData<>();//????????????
    public MutableLiveData<ResponseBean<AllAchievementBean>> myAllAchievementLiveData = new MutableLiveData<>();//????????????
    public MutableLiveData<ResponseBean<List<AwardsMathListBean>>> awardsMatchListLiveData = new MutableLiveData<>();//????????????
    public MutableLiveData<ResponseBean> updateAccountLiveData = new MutableLiveData<>();//????????????
    public MutableLiveData<ResponseBean> getCodeLiveData = new MutableLiveData<>();//???????????????
    public MutableLiveData<ResponseBean<List<ThroughListBean>>> goThroughLiveData = new MutableLiveData<>();//????????????
    public MutableLiveData<ResponseBean<AllMyInfoBean>> getAllMyInfoLiveData = new MutableLiveData<>();//????????????????????????
    public MutableLiveData<ResponseBean> approvalClubApplyLiveData = new MutableLiveData<>();//????????????????????????
    public MutableLiveData<ResponseBean<MatchOrderInfoBean>> matchOrderInfoLiveData = new MutableLiveData<>();//????????????????????????
    public MutableLiveData<ResponseBean<QueryMatchApplyInfoBean>> queryMatchApplyInfoLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> patEnrollMatchZFBLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<WXPayBean>> patEnrollMatchwxLiveData = new MutableLiveData<>();

    public MutableLiveData<ResponseBean> updatePasswordFindLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> updatePasswordSettingLiveData = new MutableLiveData<>();

    public MutableLiveData<ResponseBean<MatchBaseInfoBean>> joinBymessageLiveData = new MutableLiveData<>();
    public int orderStatus=0;
    //???????????????????????????????????????
    public void joinBymessage(String id, String relationId) {
        PostBean postBean = new PostBean();
        postBean.setId("" + id);
        postBean.setRelationId("" + relationId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).joinBymessage(requestBody).subscribe(new SimpleObserver<ResponseBean<MatchBaseInfoBean>>() {
            @Override
            public void onSuccess(ResponseBean<MatchBaseInfoBean> responseBean) {
                joinBymessageLiveData.postValue(responseBean);
            }
        });
    }

    /**
     * ????????????  2????????????
     */
    public void updatePasswordFind(String code, String account) {
        PostBean postBean = new PostBean();
        postBean.setPasswordType("2");
        postBean.setCode(code);
        postBean.setAccount(account);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Api.getApiService(RequestService.class).updatePasswordFind(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        updatePasswordFindLiveData.postValue(responseBean);
                    }
                });

    }

    /**
     * ????????????
     */
    public void updatePasswordSetting(String password, String account) {
        PostBean postBean = new PostBean();
        postBean.setPasswordType("1");
        postBean.setPassword(password);
        postBean.setAccount(account);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Api.getApiService(RequestService.class).updatePasswordFind(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        updatePasswordSettingLiveData.postValue(responseBean);
                    }
                });

    }

    /**
     * ???????????????????????????????????????
     */
    public void patEnrollMatch(String id, String clickType) {
        PostBean postBean = new PostBean();
        postBean.setId(id);
        postBean.setClickType(clickType);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (clickType.equals("1")) {
            Api.getApiService(RequestService.class).patWxEnrollMatch(requestBody)
                    .subscribe(new SimpleObserver<ResponseBean<WXPayBean>>() {
                        @Override
                        public void onSuccess(ResponseBean<WXPayBean> responseBean) {
                            patEnrollMatchwxLiveData.postValue(responseBean);
                        }
                    });
        } else {
            Api.getApiService(RequestService.class).patEnrollMatch(requestBody)
                    .subscribe(new SimpleObserver<ResponseBean>() {
                        @Override
                        public void onSuccess(ResponseBean responseBean) {
                            patEnrollMatchZFBLiveData.postValue(responseBean);
                        }
                    });
        }
    }


    /**
     * ??????????????????????????????????????????(???????????????????????????????????????)
     */
    public void queryMatchApplyInfo(String id) {
        PostBean postBean = new PostBean();
        postBean.setId(id);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).queryMatchApplyInfo(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<QueryMatchApplyInfoBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<QueryMatchApplyInfoBean> responseBean) {
                        queryMatchApplyInfoLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ???????????????????????????
     */
    public void matchOrderInfo(String id) {
        PostBean postBean = new PostBean();
        postBean.setId(id);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Api.getApiService(RequestService.class).matchOrderInfo(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<MatchOrderInfoBean>>() {

                    @Override
                    public void onSuccess(ResponseBean<MatchOrderInfoBean> responseBean) {
                        matchOrderInfoLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ???????????????????????????
     */
    public void approvalClubApply(String id, String status) {
        PostBean postBean = new PostBean();
        postBean.setId(id);
        postBean.setStatus(status);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).approvalClubApply(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {

                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        approvalClubApplyLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ????????????????????????
     */
    public void getAllMyInfo() {
        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getAllMyInfo(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<AllMyInfoBean>>() {

                    @Override
                    public void onSuccess(ResponseBean<AllMyInfoBean> responseBean) {
                        getAllMyInfoLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ????????????,???????????????????????????
     */
    public void goThrough(String identity) {
        PostBean postBean = new PostBean();
        postBean.setIdentity(identity);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).goThrough(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<List<ThroughListBean>>>() {

                    @Override
                    public void onSuccess(ResponseBean<List<ThroughListBean>> responseBean) {
                        goThroughLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ?????????????????????
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


    /**
     * ????????????
     */
    public void updateAccount(String account, String code) {
        PostBean postBean = new PostBean();
        postBean.setAccount(account);
        postBean.setCode(code);
//        postBean.setAccount2(account2);
//        postBean.setCode2(code2);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).updateAccount(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {

                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        updateAccountLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ????????????,???????????????????????????
     */
    public void awardsMatchList(String role, String socreType, String rank, String item) {
        PostBean postBean = new PostBean();
        postBean.setRole(role);
        postBean.setSocreType(socreType);
        postBean.setRank(rank);
        postBean.setItem(item);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).awardsMatchList(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<List<AwardsMathListBean>>>() {

                    @Override
                    public void onSuccess(ResponseBean<List<AwardsMathListBean>> responseBean) {
                        awardsMatchListLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ????????????
     */
    public void myAllAchievement(String role, String socreType) {
        PostBean postBean = new PostBean();
        postBean.setRole(role);
        postBean.setSocreType(socreType);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).myAllAchievement(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<AllAchievementBean>>() {

                    @Override
                    public void onSuccess(ResponseBean<AllAchievementBean> responseBean) {
                        myAllAchievementLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ????????????
     */
    public void myAchievement(String role, String socreType) {
        PostBean postBean = new PostBean();
        postBean.setRole(role);
        postBean.setSocreType(socreType);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).myAchievement(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<List<MyAchievementBean>>>() {

                    @Override
                    public void onSuccess(ResponseBean<List<MyAchievementBean>> responseBean) {
                        myAchievementLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ??????????????????????????????
     */
    public void updateRefereeInfo(String id, String roleId, String introduce) {
        PostBean postBean = new PostBean();
        postBean.setId(id);
        postBean.setRoleId(roleId);
        postBean.setIntroduce(introduce);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).updateRefereeInfo(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {

                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        updateRefereeInfoLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ?????????????????????
     */
    public void getCoachRoleList(String roleType) {
        PostBean postBean = new PostBean();
        postBean.setRoleType(roleType);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getCoachRoleList(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<List<CoashRoleListBean>>>() {

                    @Override
                    public void onSuccess(ResponseBean<List<CoashRoleListBean>> responseBean) {
                        coashRoleListBeanLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ???????????????
     */
    public void refereeInfo() {
        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).refereeInfo(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<RefereeInfoBean>>() {

                    @Override
                    public void onSuccess(ResponseBean<RefereeInfoBean> responseBean) {
                        refereeInfoLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ????????????
     */
    public void deleteXlEnrollMatch(String id) {
        PostBean postBean = new PostBean();
        postBean.setId(id);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).deleteXlEnrollMatch(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {

                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        deleteXlEnrollMatchLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ????????????
     */
    public void signOutMatchOrder(String id) {
        PostBean postBean = new PostBean();
        postBean.setId(id);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).signOutMatchOrder(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {

                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        signOutMatchOrderLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ????????????
     */
    public void myEnlist(String pageNo, String pageSize) {
        PostBean postBean = new PostBean();
        postBean.setPageNo(pageNo);
        postBean.setPageSize(pageSize);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).myEnlist(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<EnListBean>>() {

                    @Override
                    public void onSuccess(ResponseBean<EnListBean> responseBean) {
                        myEnlistLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ????????????????????????
     */
    public void myBaseInfo() {
        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).myBaseInfo(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<MyBaseInfoBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<MyBaseInfoBean> responseBean) {
                        myBaseInfoLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ?????????????????????
     */
    public void getTitleList(String title, String msgType, String pageNo, String pageSize,String status) {
        PostBean postBean = new PostBean();
        postBean.setTitle(title);
        postBean.setMsgType(msgType);
        postBean.setPageNo(pageNo);
        postBean.setPageSize(pageSize);
        postBean.setStatus(status);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getTitleList(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<TitleListBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<TitleListBean> responseBean) {
                        getTitleListLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ????????????  ????????????
     */
    public void updateXlMessageInfo(String title, String msgType, String isTop, String isDelete) {
        PostBean postBean = new PostBean();
        postBean.setTitle(title);
        postBean.setMsgType(msgType);
        postBean.setIsTop(isTop);
        postBean.setIsDelete(isDelete);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).updateXlMessageInfo(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        updateXlMessageInfoLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ????????????
     */
    public void getXlMessageInfoList(String pageNo, String pageSize, String msgType) {
        XlMessageInfoPostBean postBean1 = new XlMessageInfoPostBean();
        postBean1.setMsgType(msgType);
        postBean1.setPageNo(pageNo);
        postBean1.setPageSize(pageSize);

        String params = new Gson().toJson(postBean1);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getXlMessageInfoList(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<MessageInfoListBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<MessageInfoListBean> responseBean) {
                        getXlMessageInfoListLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ???????????????????????????
     */
    public void getCoachMatchDta(String matchId, String projectType, String sexType, String status) {
        PostBean postBean = new PostBean();
        postBean.setMatchId(matchId);
        if (projectType.equals("566")) {
            postBean.setProjectType("");
        } else {
            postBean.setProjectType(projectType);
        }
        postBean.setSexType(sexType);
        postBean.setStatus(status);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getCoachMatchDta(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<List<CoachMatchDtaBean>>>() {
                    @Override
                    public void onSuccess(ResponseBean<List<CoachMatchDtaBean>> responseBean) {
                        getCoachMatchDtaLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ??????????????????
     */
    public void getIdentityInfo() {

        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getIdentityInfo(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<IdentifyBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<IdentifyBean> responseBean) {
                        getIdentityInfoLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ????????????
     */
    public void getMyMatch(String pageNo, String pageSize, String role, String status) {

        PostBean postBean = new PostBean();
        postBean.setPageNo(pageNo);
        postBean.setPageSize(pageSize);
        postBean.setRole(role);
        postBean.setStatus(status);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getMyMatch(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<MyMatchBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<MyMatchBean> responseBean) {
                        getMyMatchLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ????????????
     */
    public void verifyMaterial(String ownImg, String name, String identificationNum) {

        PostBean postBean = new PostBean();
        postBean.setOwnImg(ownImg);
        postBean.setName(name);
        postBean.setIdentificationNum(identificationNum);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).verifyMaterial(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        verifyMaterialLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ???????????????
     */
    public void getMyClub(String pageNo, String pageSize) {
        PostBean postBean = new PostBean();
        postBean.setPageNo(pageNo);
        postBean.setPageSize(pageSize);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getMyClub(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<MyClubListBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<MyClubListBean> responseBean) {
                        getMyClubLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ?????????????????????????????????
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
     * ??????????????????
     */
    public void updateXlUserInfo(UpdateXlUserInfoBean updateXlUserInfoBean) {

        String params = new Gson().toJson(updateXlUserInfoBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).updateXlUserInfo(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                updateXlUserInfoLiveData.postValue(responseBean);
            }
        });

    }


    /**
     * ??????????????????  1????????????2????????????
     */
    public void getXlEquipmentBrandList(String bardType) {
        PostBean postBean = new PostBean();

        postBean.setBardType(bardType);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OtherApi.getApiService(RequestService.class).getXlEquipmentBrandList(requestBody).subscribe(new SimpleObserver<ResponseBean<XlEquipmentBrandListBean>>() {
            @Override
            public void onSuccess(ResponseBean<XlEquipmentBrandListBean> responseBean) {
                getXlEquipmentBrandListLiveData.postValue(responseBean);
            }
        });

    }

    /**
     * ??????????????????
     */
    public void getModelDataByBrandId(String brandId) {
        PostBean postBean = new PostBean();
        postBean.setBrandId(brandId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OtherApi.getApiService(RequestService.class).getModelDataByBrandId(requestBody).subscribe(new SimpleObserver<ResponseBean<List<ModelDataBrandIdBean>>>() {
            @Override
            public void onSuccess(ResponseBean<List<ModelDataBrandIdBean>> responseBean) {
                getModelDataByBrandIdLiveData.postValue(responseBean);
            }
        });
    }


}
