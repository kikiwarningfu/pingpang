package heyong.intellectPinPang.ui.competition;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;

import heyong.handong.framework.api.Api;
import heyong.handong.framework.base.BaseViewModel;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.bean.competition.OrderBookBean;
import heyong.intellectPinPang.bean.competition.RegistrationBean;
import heyong.intellectPinPang.bean.competition.CheckUserClunRoleBean;
import heyong.intellectPinPang.bean.competition.ChooseJoinMatchUserBean;
import heyong.intellectPinPang.bean.competition.ClubApplyMatchBean;
import heyong.intellectPinPang.bean.competition.CompetitionItemBean;
import heyong.intellectPinPang.bean.competition.GameMatchBean;
import heyong.intellectPinPang.bean.competition.MatchPersonalOrderInfoBean;
import heyong.intellectPinPang.bean.competition.OrderIdBean;
import heyong.intellectPinPang.bean.competition.PersonalClubBean;
import heyong.intellectPinPang.bean.competition.QueryMatchApplyInfoBean;
import heyong.intellectPinPang.bean.competition.RefereeApplyMatchBean;
import heyong.intellectPinPang.bean.competition.RefereeChooseDataBean;
import heyong.intellectPinPang.bean.competition.RefereeConsoleBean;
import heyong.intellectPinPang.bean.competition.RefreerConsoleSortBean;
import heyong.intellectPinPang.bean.competition.SearchMathListBean;
import heyong.intellectPinPang.bean.gsonbean.PostBean;
import heyong.intellectPinPang.bean.pay.WXPayBean;
import heyong.intellectPinPang.model.RequestService;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CompetitionViewModel extends BaseViewModel {

    public MutableLiveData<ResponseBean<SearchMathListBean>> SearchMathListBeanLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<GameMatchBean>> gameMatchLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> applyMatchLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> joinClubLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> joinTwoClubLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> refereeMacthLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<PersonalClubBean>> personOnClubLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> registrantsMacthLiveData = new MutableLiveData<>();
    public MutableLiveData<OrderIdBean> getOrderIdLiveData = new MutableLiveData<>();
    public MutableLiveData<OrderIdBean> getOrderClubIdLiveData = new MutableLiveData<>();
    public MutableLiveData<OrderIdBean> getSaveOrderIdLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<WXPayBean>> wxPayMacthLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> zfbPayMacthLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<List<CompetitionItemBean>>> competitionItemLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> getClubLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<RefereeApplyMatchBean>> refereeApplyMatchLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<RefereeApplyMatchBean>> refereeApplyToMatchLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<CheckUserClunRoleBean>> checkUserClunRoleLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> checkNowApplyLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> judgeUserShiFouChongFuDeleteAllLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<ChooseJoinMatchUserBean>> chooseJoinMatchUserLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<ChooseJoinMatchUserBean>> chooseJoinMatchPlayerLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<ChooseJoinMatchUserBean>> tuantiManLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<ChooseJoinMatchUserBean>> shuangDaLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<QueryMatchApplyInfoBean>> queryMatchApplyInfoLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> clubApplyMatchLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<WXPayBean>> clubApplyWxMatchLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> clubApplyZfbMatchLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<MatchPersonalOrderInfoBean>> matchPersonalOrderLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> signOutMatchOrderLiveData = new MutableLiveData<>();//删除订单
    public MutableLiveData<ResponseBean> patEnrollMatchZFBLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<WXPayBean>> patEnrollMatchwxLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> deleteXlEnrollMatchLiveData = new MutableLiveData<>();//删除订单
    public MutableLiveData<ResponseBean<RefreerConsoleSortBean>> refereerConsoleSortLiveData = new MutableLiveData<>();//删除订单
    public MutableLiveData<ResponseBean<List<RefereeConsoleBean>>> refereerConsoleLiveData = new MutableLiveData<>();//删除订单
    public MutableLiveData<ResponseBean<List<RefereeChooseDataBean>>> refereeChooseDataLiveData = new MutableLiveData<>();//删除订单
    public MutableLiveData<ResponseBean> updateRefereeLiveData = new MutableLiveData<>();//删除订单
    public MutableLiveData<ResponseBean> deleteRefereeLiveData = new MutableLiveData<>();//删除订单
    public MutableLiveData<ResponseBean> startMatchLiveData = new MutableLiveData<>();//删除订单
    public MutableLiveData<ResponseBean> judgeUserShiFouChongFuLiveData = new MutableLiveData<>();//删除订单
    public MutableLiveData<ResponseBean> judgeUserShiFouChongFuDeleteLiveData = new MutableLiveData<>();//删除订单
    public MutableLiveData<ResponseBean> judgeUserJoinMatchLiveData = new MutableLiveData<>();//删除订单
    public MutableLiveData<ResponseBean<List<RegistrationBean>>> RegistrationDetailsLiveData = new MutableLiveData<>();//删除订单
    public MutableLiveData<ResponseBean<OrderBookBean>> getOrderBookLiveData = new MutableLiveData<>();//删除订单


    /**
     * 报名详情
     */
    public void RegistrationDetails(String ids) {
        PostBean postBean = new PostBean();
        postBean.setId("" + ids);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).RegistrationDetails(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<List<RegistrationBean>>>() {
                    @Override
                    public void onSuccess(ResponseBean<List<RegistrationBean>> responseBean) {
                        RegistrationDetailsLiveData.postValue(responseBean);
                    }
                });
    }
    /**
     * 获取秩序册
     */
    public void getOrderBook(String ids) {
        PostBean postBean = new PostBean();
        postBean.setId("" + ids);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getOrderBook(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<OrderBookBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<OrderBookBean> responseBean) {
                        getOrderBookLiveData.postValue(responseBean);
                    }
                });
    }



    /**
     * 查询是否重复报名比赛
     */
    public void judgeUserJoinMatch(String matchId) {
        PostBean postBean = new PostBean();
        postBean.setMatchId("" + matchId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).judgeUserJoinMatch(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        judgeUserJoinMatchLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * 查询是否有重复的人
     */
    public void judgeUserShiFouChongFu(String matchId, String clubId, String projectId, List<Long> userId, List<String> projectIds) {
        PostBean postBean = new PostBean();
        postBean.setMatchId("" + matchId);
        postBean.setClubId("" + clubId);
        postBean.setProjectId("" + projectId);
        postBean.setUserIds(userId);
        postBean.setProjectIds(projectIds);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).judgeUserShiFouChongFu(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {

                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        judgeUserShiFouChongFuLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * 查询是否有重复的人
     */
    public void judgeUserShiFouChongFuDelete(String matchId, String clubId, String projectId, List<Long> userId, List<String> projectIds) {
        PostBean postBean = new PostBean();
        postBean.setMatchId("" + matchId);
        postBean.setClubId("" + clubId);
        postBean.setProjectId("" + projectId);
        postBean.setUserIds(userId);
        postBean.setProjectIds(projectIds);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).judgeUserShiFouChongFuDelete(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {

                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        judgeUserShiFouChongFuDeleteLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * 开始比赛 startMatch
     */
    public void startMatch(String matchId, String status) {
        PostBean postBean = new PostBean();
        postBean.setMatchId("" + matchId);
        postBean.setStatus("" + status);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).startMatch(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {

                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        startMatchLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * 删除裁判 deleteReferee
     */
    public void deleteReferee(String matchId, String tableNum) {
        PostBean postBean = new PostBean();
        postBean.setMatchId("" + matchId);
        postBean.setTableNum("" + tableNum);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).deleteReferee(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {

                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        deleteRefereeLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * 可选择的裁判员数据
     */
    public void updateReferee(String matchId, String tableNum, String userId) {
        PostBean postBean = new PostBean();
        postBean.setMatchId("" + matchId);
        postBean.setTableNum("" + tableNum);
        postBean.setUserId(userId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).updateReferee(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {

                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        updateRefereeLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * 可选择的裁判员数据
     */
    public void refereeChooseData(String matchId, String tableNum) {
        PostBean postBean = new PostBean();
        postBean.setMatchId("" + matchId);
        postBean.setTableNum("" + tableNum);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).refereeChooseData(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<List<RefereeChooseDataBean>>>() {

                    @Override
                    public void onSuccess(ResponseBean<List<RefereeChooseDataBean>> responseBean) {
                        refereeChooseDataLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * 裁判长页面筛选条件
     */
    public void refereerConsole(String matchId, String beginTime, String timeCode) {
        PostBean postBean = new PostBean();
        postBean.setMatchId("" + matchId);
        long timeStamp = System.currentTimeMillis();
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String times = sdr.format(timeStamp);
        if (beginTime.equals("今日")) {
            postBean.setBeginTime("" + times);
        } else {
            postBean.setBeginTime("" + beginTime + " 00:00:00");
        }
        postBean.setTimeCode("" + timeCode);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).refereerConsole(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<List<RefereeConsoleBean>>>() {

                    @Override
                    public void onSuccess(ResponseBean<List<RefereeConsoleBean>> responseBean) {
                        refereerConsoleLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * 裁判长页面筛选条件
     */
    public void refereerConsoleSort(String matchId) {
        PostBean postBean = new PostBean();
        postBean.setMatchId("" + matchId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).refereerConsoleSort(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<RefreerConsoleSortBean>>() {

                    @Override
                    public void onSuccess(ResponseBean<RefreerConsoleSortBean> responseBean) {
                        refereerConsoleSortLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * 删除订单
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
     * （我的报名）未支付报名支付
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
     * 个人的订单详情
     */
    public void matchPersonalOrderInfo(String id) {
        PostBean postBean = new PostBean();
        postBean.setId("" + id);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).matchPersonalOrderInfo(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<MatchPersonalOrderInfoBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<MatchPersonalOrderInfoBean> responseBean) {
                        matchPersonalOrderLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * 俱乐部报名保存 支付宝提交
     */
    public void clubApplyZfbMatch(ClubApplyMatchBean clubApplyMatchBean) {

        String params = new Gson().toJson(clubApplyMatchBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).clubApplyMatch(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        clubApplyZfbMatchLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * 俱乐部报名保存 微信提交
     */
    public void clubApplyWxMatch(ClubApplyMatchBean clubApplyMatchBean) {

        String params = new Gson().toJson(clubApplyMatchBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).clubWxApplyMatch(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<WXPayBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<WXPayBean> responseBean) {
                        clubApplyWxMatchLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * 俱乐部报名保存（或支付提交）
     */
    public void clubApplyMatch(ClubApplyMatchBean clubApplyMatchBean) {

        String params = new Gson().toJson(clubApplyMatchBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).clubApplyMatch(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        clubApplyMatchLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * (第三通道)选择参赛人员（运动员）
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
     * (第三通道)选择参赛人员（运动员）
     */
    public void chooseShuangDaJoinMatchPlayer(String matchId, String clubId, String projectId) {
        PostBean postBean = new PostBean();
        postBean.setMatchId(matchId);
        postBean.setClubId(clubId);
        postBean.setProjectId(projectId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "okhttp chooseShuangDaJoinMatchPlayer: 请求业余双打matchId==" + matchId + "clubId==" + clubId + "projectId===" + projectId);

        Api.getApiService(RequestService.class).chooseJoinMatchPlayer(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<ChooseJoinMatchUserBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<ChooseJoinMatchUserBean> responseBean) {
                        shuangDaLiveData.postValue(responseBean);
                    }
                });
    }

    public static final String TAG = CompetitionViewModel.class.getSimpleName();

    /**
     * (第三通道)选择参赛人员（运动员男）
     */
    public void chooseMan(String matchId, String clubId, String projectId, String itemProjectId) {
        PostBean postBean = new PostBean();
        postBean.setMatchId(matchId);
        postBean.setClubId(clubId);
        postBean.setProjectId(projectId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "okhttp chooseMan: 请求男团matchId==" + matchId + "clubId==" + clubId + "projectId===" + projectId);
        Api.getApiService(RequestService.class).chooseJoinMatchPlayer(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<ChooseJoinMatchUserBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<ChooseJoinMatchUserBean> responseBean) {
                        ChooseJoinMatchUserBean data = responseBean.getData();
                        Log.e(TAG, "onChanged: 调用接口matchId==" + matchId + "clubId==" + clubId + "projectId===" + projectId );
                        if (data != null) {
//                            Log.e(TAG, "onSuccess: 请求id" + matchId + " " + " " + clubId + " " + projectId + " "
//                                    + "返回数据" + responseBean.getData().getItemId() + "size====" + responseBean.getData().getPlayers().size());

                            tuantiManLiveData.postValue(responseBean);
                        } else {
                            tuantiManLiveData.postValue(responseBean);
                        }
                    }
                });
    }

    /**
     * (第三通道)选择参赛人员（运动员）
     */
    public void chooseJoinMatchPlayer(String matchId, String clubId, String projectId) {
        PostBean postBean = new PostBean();
        postBean.setMatchId(matchId);
        postBean.setClubId(clubId);
        postBean.setProjectId(projectId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "okhttp 单打: 请求单打matchId==" + matchId + "clubId==" + clubId + "projectId===" + projectId);

        Api.getApiService(RequestService.class).chooseJoinMatchPlayer(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<ChooseJoinMatchUserBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<ChooseJoinMatchUserBean> responseBean) {
                        chooseJoinMatchPlayerLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * 判断此场比赛，此俱乐部是否有报过名或者是否有人正在操作
     */
    public void chooseJoinMatchUser(String matchId, String clubId) {
        PostBean postBean = new PostBean();
        postBean.setMatchId(matchId);
        postBean.setClubId(clubId);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).chooseJoinMatchUser(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<ChooseJoinMatchUserBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<ChooseJoinMatchUserBean> responseBean) {
                        chooseJoinMatchUserLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * 判断此场比赛，此俱乐部是否有报过名或者是否有人正在操作
     */
    public void checkNowApply(String matchId, String clubId) {
        PostBean postBean = new PostBean();
        postBean.setMatchId(matchId);
        postBean.setClubId(clubId);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).checkNowApply(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        checkNowApplyLiveData.postValue(responseBean);
                    }
                });
    }
    /**
     * 判断此场比赛，此俱乐部是否有报过名或者是否有人正在操作
     */
    public void judgeUserShiFouChongFuDeleteAll(String matchId, String clubId) {
        PostBean postBean = new PostBean();
        postBean.setMatchId(matchId);
        postBean.setClubId(clubId);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).judgeUserShiFouChongFuDeleteAll(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        judgeUserShiFouChongFuDeleteAllLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * 查询此人是否有非自由团体绝了不负责人，教练员身份，实名认证    goActivity(ClubTeamRegisterChannelActivity.class);
     */
    public void checkUserClunRole(String matchId) {
        PostBean postBean = new PostBean();
        postBean.setId(matchId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).checkUserClunRole(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<CheckUserClunRoleBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<CheckUserClunRoleBean> responseBean) {
                        checkUserClunRoleLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * 裁判员报名
     */
    public void refereeApplyToMatch(String matchId) {
        PostBean postBean = new PostBean();
        postBean.setMatchId(matchId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).refereeApplyToMatch(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        refereeApplyToMatchLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * 判断裁判员能否报名
     */
    public void refereeApplyMatch(String id) {
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
        Api.getApiService(RequestService.class).refereeApplyMatch(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<RefereeApplyMatchBean>>() {

                    @Override
                    public void onSuccess(ResponseBean<RefereeApplyMatchBean> responseBean) {
                        refereeApplyMatchLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * 获取订单的id
     */
    public void getSaveOrderId() {

        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Api.getApiService(RequestService.class).getOrderId(requestBody)
                .subscribe(new SimpleObserver<OrderIdBean>() {

                    @Override
                    public void onSuccess(OrderIdBean responseBean) {
                        getSaveOrderIdLiveData.postValue(responseBean);
                    }
                });


    }

    /**
     * 获取订单的id
     */
    public void getClubOrderId() {

        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Api.getApiService(RequestService.class).getOrderId(requestBody)
                .subscribe(new SimpleObserver<OrderIdBean>() {

                    @Override
                    public void onSuccess(OrderIdBean responseBean) {
                        getOrderClubIdLiveData.postValue(responseBean);
                    }
                });


    }

    /**
     * 获取订单的id
     */
    public void getOrderId() {

        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Api.getApiService(RequestService.class).getOrderId(requestBody)
                .subscribe(new SimpleObserver<OrderIdBean>() {

                    @Override
                    public void onSuccess(OrderIdBean responseBean) {
                        getOrderIdLiveData.postValue(responseBean);
                    }
                });


    }


    /**
     * 个人报名（保存报名）
     */
    public void registrantsMatch(String matchId, String projectItemId, String itemType, String projectId
            , String clickType, String freeType, String needNat, String id) {

        PostBean postBean = new PostBean();
        postBean.setMatchId(matchId);
        postBean.setId(id);
        postBean.setProjectItemId(projectItemId);
        postBean.setItemType(itemType);
        postBean.setProjectId(projectId);
        postBean.setClickType(clickType);
        postBean.setFreeType(freeType);
        if (needNat.equals("-1")) {
            postBean.setNeedEat("0");
        } else {
            postBean.setNeedEat(needNat);
        }

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (freeType.equals("1")) {
            /*微信支付*/
            Api.getApiService(RequestService.class).regWxistrantsMacth(requestBody)
                    .subscribe(new SimpleObserver<ResponseBean<WXPayBean>>() {

                        @Override
                        public void onSuccess(ResponseBean<WXPayBean> responseBean) {
                            wxPayMacthLiveData.postValue(responseBean);
                        }
                    });
        } else if (freeType.equals("2")) {
            /*支付宝*/
            Api.getApiService(RequestService.class).regZfbistrantsMacth(requestBody)
                    .subscribe(new SimpleObserver<ResponseBean>() {

                        @Override
                        public void onSuccess(ResponseBean responseBean) {
                            zfbPayMacthLiveData.postValue(responseBean);
                        }
                    });
        } else {
            Api.getApiService(RequestService.class).registrantsMacth(requestBody)
                    .subscribe(new SimpleObserver<ResponseBean>() {

                        @Override
                        public void onSuccess(ResponseBean responseBean) {
                            registrantsMacthLiveData.postValue(responseBean);
                        }
                    });
        }

    }


    /**
     * 个人报名（比赛项目）
     */
    public void competitionItem(String id, String itemType) {

        PostBean postBean = new PostBean();
        postBean.setId(id);
        postBean.setItemType("" + itemType);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).competitionItem(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<List<CompetitionItemBean>>>() {

                    @Override
                    public void onSuccess(ResponseBean<List<CompetitionItemBean>> responseBean) {
                        competitionItemLiveData.postValue(responseBean);
                    }
                });

    }

    /**
     * 赛事名称模糊搜索
     */
    public void searchMatchTitle(String matchStatus, String matchGrade, String matchLevel, String matchTitle, String pageNo, String pageSize) {
        PostBean postBean = new PostBean();
        postBean.setMatchStatus(matchStatus);
        postBean.setMatchGrade(matchGrade);
        postBean.setMatchLevel(matchLevel);
        postBean.setMatchTitle(matchTitle);
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
        Api.getApiService(RequestService.class).searchMatchTitle(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<SearchMathListBean>>() {

                    @Override
                    public void onSuccess(ResponseBean<SearchMathListBean> responseBean) {
                        SearchMathListBeanLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * 赛事详情
     */
    public void gameMatch(String id) {
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
        Api.getApiService(RequestService.class).gameMatch(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<GameMatchBean>>() {

                    @Override
                    public void onSuccess(ResponseBean<GameMatchBean> responseBean) {
                        gameMatchLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * 我要报名
     */
    public void applyMatch(String ids) {
        PostBean postBean = new PostBean();
        postBean.setId(ids);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).applyMatch(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {

                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        applyMatchLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * 个人报名 (判断是否有俱乐部)
     */
    public void joinClub() {
        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).joinClub(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {

                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        joinClubLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * 第二通道  个人报名 (判断是否有俱乐部)
     */
    public void joinTwoClub() {
        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).joinClub(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {

                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        joinTwoClubLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * 个人报名 判断      (裁判员)
     */
    public void refereeMacth() {
        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).refereeMacth(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {

                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        refereeMacthLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * 个人代表俱乐部报名   俱乐部列表
     */
    public void personOnClub(String postId) {
        PostBean postBean = new PostBean();
        postBean.setId(postId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).personOnClub(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<PersonalClubBean>>() {

                    @Override
                    public void onSuccess(ResponseBean<PersonalClubBean> responseBean) {
                        personOnClubLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * 个人代表俱乐部(提交报名)
     */
    public void getClub(String id, String clubId) {
        PostBean postBean = new PostBean();
        postBean.setMatchId(id);
        postBean.setClubId(clubId);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getClub(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {

                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        getClubLiveData.postValue(responseBean);
                    }
                });
    }
}
