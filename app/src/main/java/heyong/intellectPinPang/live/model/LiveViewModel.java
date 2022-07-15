package heyong.intellectPinPang.live.model;

import android.text.TextUtils;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import heyong.handong.framework.api.Api;
import heyong.handong.framework.api.LiveApi;
import heyong.handong.framework.api.Params;
import heyong.handong.framework.base.BaseViewModel;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.bean.live.GetOrderMoneyBean;
import heyong.intellectPinPang.bean.live.LiveRoomBean;
import heyong.intellectPinPang.bean.live.MoneyDetailListBean;
import heyong.intellectPinPang.bean.live.SuspendLiveBroadCastBean;
import heyong.intellectPinPang.bean.live.LiveBroadCastMatchListBean;
import heyong.intellectPinPang.bean.live.WalletInfoBean;
import heyong.intellectPinPang.bean.live.WatchLiveBean;
import heyong.intellectPinPang.bean.live.WithDrawMyInfoBean;
import heyong.intellectPinPang.bean.live.LiveHallBean;
import heyong.intellectPinPang.bean.live.OfferRewardOrderListBean;
import heyong.intellectPinPang.bean.live.OfferRewardOrderListConditionBean;
import heyong.intellectPinPang.bean.live.XlZBjieDanOrdeListBean;
import heyong.intellectPinPang.bean.live.XlZhiboJiedanOrderBean;
import heyong.intellectPinPang.bean.competition.MatchScreenFormatBean;
import heyong.intellectPinPang.bean.live.BeginLiveBroadCastBean;
import heyong.intellectPinPang.bean.live.CanonDemandBean;
import heyong.intellectPinPang.bean.live.CreateXlZhiboSetOrderBean;
import heyong.intellectPinPang.bean.live.LiveUserWithDrawalDetailBean;
import heyong.intellectPinPang.bean.competition.MyMatchBean;
import heyong.intellectPinPang.bean.live.LiveListBean;
import heyong.intellectPinPang.bean.live.LiveMatchByBean;
import heyong.intellectPinPang.bean.live.LiveMatchUrlBean;
import heyong.intellectPinPang.bean.live.LiveUserAppraiseBean;
import heyong.intellectPinPang.bean.live.LiveUserInfoBean;
import heyong.intellectPinPang.bean.competition.LiveMatchPublishListBean;
import heyong.intellectPinPang.bean.gsonbean.PostBean;
import heyong.intellectPinPang.bean.live.LiveMatchDetailBean;
import heyong.intellectPinPang.bean.live.LiveUserOrderListBean;
import heyong.intellectPinPang.bean.live.LiveUserRefundDetailBean;
import heyong.intellectPinPang.bean.live.OrderCenterBean;
import heyong.intellectPinPang.bean.live.XLzhiboSetOrderInfoBean;
import heyong.intellectPinPang.bean.live.XlZhiboGetMatchScreenDataBean;
import heyong.intellectPinPang.bean.live.XlZhiboOrderPayMentBean;
import heyong.intellectPinPang.bean.live.XlZhiboSetOrder;
import heyong.intellectPinPang.bean.live.XlZhiboSetOrderListBean;
import heyong.intellectPinPang.bean.live.ZhiboJiedanUserBaseInfoBean;
import heyong.intellectPinPang.bean.pay.WXPayBean;
import heyong.intellectPinPang.model.RequestService;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LiveViewModel extends BaseViewModel {
    public MutableLiveData<ResponseBean<LiveMatchPublishListBean>> liveMatchPublishListLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveUserInfoBean>> liveUserInfoLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveMatchDetailBean>> liveMatchPublishDetailLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> liveMatchAcceptLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveListBean>> liveZhiBoMatchListLiveDataLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveListBean>> liveLuBOMatchListLiveDataLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveUserOrderListBean>> liveUserOrderPublishLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveUserOrderListBean>> liveUserOrderReceiveLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> liveUserApplyLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> liveUserAppraiseLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveUserAppraiseBean>> liveUserAppraiseMyLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> getUploadTokenLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> liveUserAccountLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> liveRefundApplyLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveMatchByBean>> liveMatchRepayLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> liveUserOrderReceiveDeleteLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> liveUserOrderPublishDeleteLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveUserRefundDetailBean>> liveUserRefundDetailLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> liveMatchCheckOrderLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> liveUserOrderPublishData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> liveUserOrderCancelData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveMatchUrlBean>> liveMatchUrlData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> liveUserWithDrawlData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<OrderCenterBean>> orderReceivingCenterData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<ZhiboJiedanUserBaseInfoBean>> zhiboJiedanUserBaseInfoData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> relieveJiedanRoleData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<List<OfferRewardOrderListConditionBean>>> offerRewardOrderListConditionData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<OfferRewardOrderListBean>> OfferRewardOrderListBeanData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> xlZhiboJiedanUserData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> createXlZhiboJiedanUserData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveUserWithDrawalDetailBean>> liveUserWithDrawalDetailData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<WXPayBean>> payYaJinWxData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<WXPayBean>> buyCompleteVideoWxData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> payYaJinZfbData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> buyCompleteVideoZFBData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<XlZhiboGetMatchScreenDataBean>> xlZhibogetMatchScreenData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<MatchScreenFormatBean>> xlZhibogetMatchScreenFormatData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<CanonDemandBean>> getCanOnDemandData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<XlZhiboSetOrder>> getXlZhiboSetOrderData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<CreateXlZhiboSetOrderBean>> createXlZhiboSetOrderData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> zhifuOrderZFBData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<WXPayBean>> zhifuOrderWXData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<XlZhiboOrderPayMentBean>> xlZhiboSetOrderPaymentData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<XlZhiboSetOrderListBean>> getXlZhiboSetOrderListData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<XLzhiboSetOrderInfoBean>> getXlZhiboSetOrderInfoData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> cancelOrderData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> receiveRewardOrderData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> xlZhiboSetOrderconfirmOrderData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> complaintOrderData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<XlZBjieDanOrdeListBean>> getXlZhiboJiedanOrderListData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<XlZhiboJiedanOrderBean>> getXlZhiboJiedanOrderData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> cancleJieDanOrderData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<BeginLiveBroadCastBean>> beginLiveBroadcastData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> endLiveBroadcastData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> endLiveBroadcastForceData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveHallBean>> liveBroadcastMatchAgainstListData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveHallBean>> liveBroadcastMatchAgainstInfoListtData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveBroadCastMatchListBean>> liveBroadcastMatchListData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<SuspendLiveBroadCastBean>> suspendLiveBroadcastData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<WalletInfoBean>> walletInfoData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<MoneyDetailListBean>> getXlZhiboMoneyDetailsListData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<WithDrawMyInfoBean>> withdrawalInfoData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<GetOrderMoneyBean>> getOrderMoneyData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> withdrawalData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveRoomBean>> watchPlaybackData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> bindingWithdrawalAccountData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> unbindAccountData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> leaveLiveBroadcastingRoomData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<WatchLiveBean>> getIntoLiveBroadcastingRoomData = new MutableLiveData<>();
    public ObservableField<String> matchTime = new ObservableField<>();
    public ObservableField<String> matchTimeCode = new ObservableField<>();

    public String codeTime = "";

    public void leaveLiveBroadcastingRoom(String id) {
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
        LiveApi.getApiService(RequestService.class).leaveLiveBroadcastingRoom(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean responseBean) {
                        leaveLiveBroadcastingRoomData.postValue(responseBean);
                    }
                });
    }

    public void unbindAccount(String id, String wxStatus, String apayStatus) {
        PostBean postBean = new PostBean();
        postBean.setId(id);

        if (!TextUtils.isEmpty(wxStatus)) {
            postBean.setWxStatus("" + wxStatus);
        }
        if (!TextUtils.isEmpty(apayStatus)) {
            postBean.setApayStatus("" + apayStatus);
        }

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).unbindAccount(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean responseBean) {
                        unbindAccountData.postValue(responseBean);
                    }
                });
    }


    public void bindingWithdrawalAccount(String id, String wxName, String wx_open_id,
                                         String wx_status, String apay_name, String apay_account, String apay_status) {
        PostBean postBean = new PostBean();
        postBean.setId(id);

        if (!TextUtils.isEmpty(wxName)) {
            postBean.setWxName("" + wxName);
        }
        if (!TextUtils.isEmpty(wx_open_id)) {
            postBean.setWxOpenId("" + wx_open_id);
        }
        if (!TextUtils.isEmpty(wx_status)) {
            postBean.setWxStatus("" + wx_status);

        }
        if (!TextUtils.isEmpty(apay_name)) {
            postBean.setApayName("" + apay_name);
        }
        if (!TextUtils.isEmpty(apay_account)) {
            postBean.setApayAccount("" + apay_account);
        }
        if (!TextUtils.isEmpty(apay_status)) {
            postBean.setApayStatus("" + apay_status);
        }
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).bindingWithdrawalAccount(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean responseBean) {
                        bindingWithdrawalAccountData.postValue(responseBean);
                    }
                });
    }


    public void watchPlayback(String id) {
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
        LiveApi.getApiService(RequestService.class).watchPlayback(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<LiveRoomBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<LiveRoomBean> responseBean) {
                        watchPlaybackData.postValue(responseBean);
                    }
                });
    }

    public void withdrawal(String detailType, String money) {
        PostBean postBean = new PostBean();
        postBean.setDetailType(detailType);
        postBean.setMoney(money);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).withdrawal(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean responseBean) {
                        withdrawalData.postValue(responseBean);
                    }
                });
    }


    public void getOrderMoney(String itemType, String orderType, String arrangeId) {
        PostBean postBean = new PostBean();
        postBean.setItemType(itemType);
        postBean.setOrderType(orderType);
        postBean.setArrangeId(arrangeId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).getOrderMoney(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<GetOrderMoneyBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<GetOrderMoneyBean> responseBean) {
                        getOrderMoneyData.postValue(responseBean);
                    }
                });
    }

    public void withdrawalInfo(String id) {
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
        Observable<ResponseBean<WithDrawMyInfoBean>> responseBeanObservable = LiveApi.getApiService(RequestService.class).withdrawalInfo(requestBody);
        responseBeanObservable
                .subscribe(new SimpleObserver<ResponseBean<WithDrawMyInfoBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<WithDrawMyInfoBean> responseBean) {
                        withdrawalInfoData.postValue(responseBean);
                    }


                });
    }


    public void getXlZhiboMoneyDetailsList(String walletId, String moneyType, String page, String pageSize) {
        PostBean postBean = new PostBean();
        if (moneyType.equals("0")) {
            postBean.setMoneyType("");
        } else {
            postBean.setMoneyType("" + moneyType);
        }
        postBean.setWalletId("" + walletId);
        postBean.setPageNo("" + page);
        postBean.setPageSize("" + pageSize);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).getXlZhiboMoneyDetailsList(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<MoneyDetailListBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<MoneyDetailListBean> responseBean) {
                        getXlZhiboMoneyDetailsListData.postValue(responseBean);
                    }
                });
    }


    public void walletInfo() {
        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).walletInfo(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<WalletInfoBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<WalletInfoBean> responseBean) {
                        walletInfoData.postValue(responseBean);
                    }
                });
    }


    public void suspendLiveBroadcast(String id) {
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
        LiveApi.getApiService(RequestService.class).suspendLiveBroadcast(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<SuspendLiveBroadCastBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<SuspendLiveBroadCastBean> responseBean) {
                        suspendLiveBroadcastData.postValue(responseBean);
                    }
                });
    }

    public void liveBroadcastMatchList(String levelCode, String pageNo, String pageSize, String matchTitle) {
        PostBean postBean = new PostBean();
        postBean.setLevelCode("" + levelCode);
        postBean.setPageNo("" + pageNo);
        postBean.setPageSize("" + pageSize);
        postBean.setMatchTitle("" + matchTitle);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).liveBroadcastMatchList(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<LiveBroadCastMatchListBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<LiveBroadCastMatchListBean> responseBean) {
                        liveBroadcastMatchListData.postValue(responseBean);
                    }
                });
    }

    public void liveBroadcastMatchAgainstInfoList(String matchId, String itemType, String pageNo, String pageSize, String name, String code) {
        PostBean postBean = new PostBean();
        postBean.setMatchId("" + matchId);
        postBean.setItemType("" + itemType);
        postBean.setPageNo("" + pageNo);
        postBean.setPageSize("" + pageSize);
        postBean.setName("" + name);
        postBean.setCode("" + code);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).liveBroadcastMatchAgainstInfoList(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<LiveHallBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<LiveHallBean> responseBean) {
                        liveBroadcastMatchAgainstInfoListtData.postValue(responseBean);
                    }
                });
    }

    public void liveBroadcastMatchAgainstList(String matchId, String itemType, String pageNo, String pageSize, String name, String code) {
        PostBean postBean = new PostBean();
        postBean.setMatchId("" + matchId);
        postBean.setItemType("" + itemType);
        postBean.setPageNo("" + pageNo);
        postBean.setPageSize("" + pageSize);
        postBean.setName("" + name);
        postBean.setCode("" + code);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).liveBroadcastMatchAgainstList(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<LiveHallBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<LiveHallBean> responseBean) {
                        liveBroadcastMatchAgainstListData.postValue(responseBean);
                    }
                });
    }

    public void endLiveBroadcastForce(String id, String force) {
        PostBean postBean = new PostBean();
        postBean.setId("" + id);
        postBean.setForce("" + force);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).endLiveBroadcastForce(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean responseBean) {
                        endLiveBroadcastForceData.postValue(responseBean);
                    }
                });
    }


    public void endLiveBroadcast(String id) {
        PostBean postBean = new PostBean();
        postBean.setForce("false");
        postBean.setId("" + id);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).endLiveBroadcast(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean responseBean) {
                        endLiveBroadcastData.postValue(responseBean);
                    }
                });
    }


    public void cancleJieDanOrder(String id) {
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
        LiveApi.getApiService(RequestService.class).cancleJieDanOrder(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean responseBean) {
                        cancleJieDanOrderData.postValue(responseBean);
                    }
                });
    }


    public void getXlZhiboJiedanOrder(String id) {
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
        LiveApi.getApiService(RequestService.class).getXlZhiboJiedanOrder(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<XlZhiboJiedanOrderBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<XlZhiboJiedanOrderBean> responseBean) {
                        getXlZhiboJiedanOrderData.postValue(responseBean);
                    }
                });
    }

    public void getXlZhiboJiedanOrderList(String code, String pageNo, String pageSize, String orderTime) {
        PostBean postBean = new PostBean();
        postBean.setCode("" + code);
        postBean.setPageNo("" + pageNo);
        postBean.setPageSize("" + pageSize);

        postBean.setCreateTime("" + orderTime);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).getXlZhiboJiedanOrderList(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<XlZBjieDanOrdeListBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<XlZBjieDanOrdeListBean> responseBean) {
                        getXlZhiboJiedanOrderListData.postValue(responseBean);
                    }
                });
    }


    public void complaintOrder(String id, String reason) {
        PostBean postBean = new PostBean();
        postBean.setId("" + id);
        postBean.setReason("" + reason);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).complaintOrder(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean responseBean) {
                        complaintOrderData.postValue(responseBean);
                    }
                });
    }


    public void xlZhiboSetOrderconfirmOrder(String id) {
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
        LiveApi.getApiService(RequestService.class).xlZhiboSetOrderconfirmOrder(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean responseBean) {
                        xlZhiboSetOrderconfirmOrderData.postValue(responseBean);
                    }
                });
    }


    public void receiveRewardOrder(String id) {
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
        LiveApi.getApiService(RequestService.class).receiveRewardOrder(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean responseBean) {
                        receiveRewardOrderData.postValue(responseBean);
                    }
                });
    }

    public void cancelOrder(String id) {
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
        LiveApi.getApiService(RequestService.class).cancelOrder(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean responseBean) {
                        cancelOrderData.postValue(responseBean);
                    }
                });
    }

    public void getXlZhiboSetOrderInfo(String id) {
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
        LiveApi.getApiService(RequestService.class).getXlZhiboSetOrderInfo(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<XLzhiboSetOrderInfoBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<XLzhiboSetOrderInfoBean> responseBean) {
                        getXlZhiboSetOrderInfoData.postValue(responseBean);
                    }
                });
    }

    public void getXlZhiboSetOrderList(String code, String pageNo, String pageSize, String orderTime) {
        PostBean postBean = new PostBean();
        postBean.setCode("" + code);
        postBean.setPageNo("" + pageNo);
        postBean.setPageSize("" + pageSize);
        postBean.setOrderTime("" + orderTime);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).getXlZhiboSetOrderList(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<XlZhiboSetOrderListBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<XlZhiboSetOrderListBean> responseBean) {
                        getXlZhiboSetOrderListData.postValue(responseBean);
                    }
                });
    }


    public void xlZhiboSetOrderPayment(String id) {
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
        LiveApi.getApiService(RequestService.class).xlZhiboSetOrderPayment(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<XlZhiboOrderPayMentBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<XlZhiboOrderPayMentBean> responseBean) {
                        xlZhiboSetOrderPaymentData.postValue(responseBean);
                    }
                });
    }


    public void zhifuOrderWX(String id, String freeType) {
        PostBean postBean = new PostBean();
        postBean.setId("" + id);
        postBean.setFreeType("" + freeType);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).zhifuOrderWX(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<WXPayBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<WXPayBean> responseBean) {
                        zhifuOrderWXData.postValue(responseBean);
                    }
                });
    }

    public void zhifuOrderZFB(String id, String freeType) {
        PostBean postBean = new PostBean();
        postBean.setId("" + id);
        postBean.setFreeType("" + freeType);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).zhifuOrderZFB(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean responseBean) {
                        zhifuOrderZFBData.postValue(responseBean);
                    }
                });
    }


    public void createXlZhiboSetOrder(XlZhiboSetOrder xlZhiboSetOrder) {

        String params = new Gson().toJson(xlZhiboSetOrder);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).createXlZhiboSetOrder(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<CreateXlZhiboSetOrderBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<CreateXlZhiboSetOrderBean> responseBean) {
                        createXlZhiboSetOrderData.postValue(responseBean);
                    }
                });
    }


    public void getXlZhiboSetOrder(String arrangeId, String itemTitle) {
        PostBean postBean = new PostBean();
        postBean.setArrangeId("" + arrangeId);
        postBean.setItemTitle("" + itemTitle);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).getXlZhiboSetOrder(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<XlZhiboSetOrder>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<XlZhiboSetOrder> responseBean) {
                        getXlZhiboSetOrderData.postValue(responseBean);
                    }
                });
    }


    public void getCanOnDemand(String matchId, String projectItemId, String groupNum, String pageNo, String pageSize) {
        PostBean postBean = new PostBean();
        postBean.setMatchId("" + matchId);
        postBean.setProjectItemId("" + projectItemId);
        postBean.setGroupNum("" + groupNum);
        postBean.setPageNo("" + pageNo);
        postBean.setPageSize("" + pageSize);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).getCanOnDemand(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<CanonDemandBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<CanonDemandBean> responseBean) {
                        getCanOnDemandData.postValue(responseBean);
                    }
                });
    }


    public void xlZhibogetMatchScreenFormat(String matchId, String projectItemId) {
        PostBean postBean = new PostBean();
        postBean.setMatchId("" + matchId);
        postBean.setProjectItemId("" + projectItemId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).xlZhibogetMatchScreenFormat(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<MatchScreenFormatBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<MatchScreenFormatBean> responseBean) {
                        xlZhibogetMatchScreenFormatData.postValue(responseBean);
                    }
                });
    }

    public void xlZhibogetMatchScreen(String id) {
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
        LiveApi.getApiService(RequestService.class).xlZhibogetMatchScreen(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<XlZhiboGetMatchScreenDataBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<XlZhiboGetMatchScreenDataBean> responseBean) {
                        xlZhibogetMatchScreenData.postValue(responseBean);
                    }
                });
    }


    public void offerRewardOrderListCondition(String matchId) {
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
        LiveApi.getApiService(RequestService.class).offerRewardOrderListCondition(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<List<OfferRewardOrderListConditionBean>>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<List<OfferRewardOrderListConditionBean>> responseBean) {
                        offerRewardOrderListConditionData.postValue(responseBean);
                    }
                });
    }

    public void offerRewardOrderList(String matchTime, String matchId, String pageNo, String pageSize) {
        PostBean postBean = new PostBean();
        postBean.setMatchId("" + matchId);
        postBean.setMatchTime("" + matchTime);
        postBean.setPageNo("" + pageNo);
        postBean.setPageSize("" + pageSize);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).offerRewardOrderList(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<OfferRewardOrderListBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<OfferRewardOrderListBean> responseBean) {
                        OfferRewardOrderListBeanData.postValue(responseBean);
                    }
                });
    }


    public int orderStatus = 0;

    //payYaJin
    public void buyCompleteVideo(String id, String depositFreeType) {
        PostBean postBean = new PostBean();
        postBean.setZhiboArrangeId("" + id);
        postBean.setFreeType("" + depositFreeType);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (depositFreeType.equals("1")) {
            LiveApi.getApiService(RequestService.class).buyCompleteVideoWx(requestBody)
                    .subscribe(new SimpleObserver<ResponseBean<WXPayBean>>() {
                        @Override
                        public void onSuccess(@NotNull ResponseBean<WXPayBean> responseBean) {
                            buyCompleteVideoWxData.postValue(responseBean);
                        }
                    });
        } else {
            LiveApi.getApiService(RequestService.class).buyCompleteVideoZFB(requestBody)
                    .subscribe(new SimpleObserver<ResponseBean>() {
                        @Override
                        public void onSuccess(@NotNull ResponseBean responseBean) {
                            buyCompleteVideoZFBData.postValue(responseBean);
                        }
                    });
        }

    }

    //payYaJin
    public void payYaJin(String id, String depositFreeType) {
        PostBean postBean = new PostBean();
        postBean.setId("" + id);
        postBean.setDepositFreeType("" + depositFreeType);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (depositFreeType.equals("1")) {
            LiveApi.getApiService(RequestService.class).payYaJinWx(requestBody)
                    .subscribe(new SimpleObserver<ResponseBean<WXPayBean>>() {
                        @Override
                        public void onSuccess(@NotNull ResponseBean<WXPayBean> responseBean) {
                            payYaJinWxData.postValue(responseBean);
                        }
                    });
        } else {
            LiveApi.getApiService(RequestService.class).payYaJinZFB(requestBody)
                    .subscribe(new SimpleObserver<ResponseBean>() {
                        @Override
                        public void onSuccess(@NotNull ResponseBean responseBean) {
                            payYaJinZfbData.postValue(responseBean);
                        }
                    });
        }

    }


    //recovery
    public void xlZhiboJiedanUser(String id) {
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
        LiveApi.getApiService(RequestService.class).xlZhiboJiedanUser(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean responseBean) {
                        xlZhiboJiedanUserData.postValue(responseBean);
                    }
                });
    }


    public void relieveJiedanRole(String id, String unbound) {
        PostBean postBean = new PostBean();
        postBean.setId("" + id);
        postBean.setUnbound("" + unbound);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).relieveJiedanRole(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean responseBean) {
                        relieveJiedanRoleData.postValue(responseBean);
                    }
                });
    }


    public void createXlZhiboJiedanUser(String id, String name, String sex, String idCardNum,
                                        String deposit, String phoneXinghao) {
        PostBean postBean = new PostBean();
        postBean.setId("" + id);
        postBean.setName("" + name);
        postBean.setSex("" + sex);
        postBean.setIdCardNum("" + idCardNum);
        postBean.setDeposit("" + deposit);
        postBean.setPhoneXinghao("" + phoneXinghao);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).createXlZhiboJiedanUser(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean responseBean) {
                        createXlZhiboJiedanUserData.postValue(responseBean);
                    }
                });
    }

    public void zhiboJiedanUserBaseInfo() {
        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).zhiboJiedanUserBaseInfo(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<ZhiboJiedanUserBaseInfoBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<ZhiboJiedanUserBaseInfoBean> responseBean) {
                        zhiboJiedanUserBaseInfoData.postValue(responseBean);
                    }
                });
    }

    public void orderReceivingCenter() {
        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).orderReceivingCenter(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<OrderCenterBean>>() {
                    @Override
                    public void onSuccess(@NotNull ResponseBean<OrderCenterBean> responseBean) {
                        orderReceivingCenterData.postValue(responseBean);
                    }
                });
    }


    //liveUserWithDrawalDetail  用户提现详情
    public void liveUserWithDrawalDetail(String requestId) {
        LiveApi.getApiService(RequestService.class).liveUserWithDrawalDetail(requestId)
                .subscribe(new SimpleObserver<ResponseBean<LiveUserWithDrawalDetailBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<LiveUserWithDrawalDetailBean> responseBean) {
                        liveUserWithDrawalDetailData.postValue(responseBean);
                    }
                });
    }


    //根据ID获取直播或录播地址
    public void liveMatchUrl(String requestId) {
        LiveApi.getApiService(RequestService.class).liveMatchUrl(requestId)
                .subscribe(new SimpleObserver<ResponseBean<LiveMatchUrlBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<LiveMatchUrlBean> responseBean) {
                        liveMatchUrlData.postValue(responseBean);
                    }
                });
    }

    //liveUserWithDrawl  用户申请提现
    public void liveUserWithDrawl(String requestId) {

        LiveApi.getApiService(RequestService.class).liveUserWithDrawl(requestId)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        liveUserWithDrawlData.postValue(responseBean);
                    }
                });
    }


    //发布取消悬赏
    public void liveUserOrderPublish(String requestId) {
        LiveApi.getApiService(RequestService.class).liveUserOrderPublish(requestId)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        liveUserOrderPublishData.postValue(responseBean);
                    }
                });
    }

    //接单取消悬赏
    public void liveUserOrderCancel(String requestId) {
        LiveApi.getApiService(RequestService.class).liveUserOrderCancel(requestId)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        liveUserOrderCancelData.postValue(responseBean);
                    }
                });
    }


    //删除接单人订单
    public void liveUserOrderReceiveDelete(String requestId) {
        LiveApi.getApiService(RequestService.class).liveUserOrderReceiveDelete(requestId)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        liveUserOrderReceiveDeleteLiveData.postValue(responseBean);
                    }
                });
    }

    //删除发布人的订单
    public void liveUserOrderPublishDelete(String requestId) {
        LiveApi.getApiService(RequestService.class).liveUserOrderPublishDelete(requestId)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        liveUserOrderPublishDeleteLiveData.postValue(responseBean);
                    }
                });
    }

    //在我的里面的订单列表中支付
    public void liveMatchRepay(String orderId) {
        LiveApi.getApiService(RequestService.class).liveMatchRepay(orderId)
                .subscribe(new SimpleObserver<ResponseBean<LiveMatchByBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<LiveMatchByBean> responseBean) {
                        liveMatchRepayLiveData.postValue(responseBean);
                    }
                });
    }


    //查询订单是否支付成功
    public void liveMatchCheckOrder(String orderId) {
        LiveApi.getApiService(RequestService.class).liveMatchCheckOrder(orderId)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        liveMatchCheckOrderLiveData.postValue(responseBean);
                    }
                });
    }

    //订单列表
    public void LiveOrderList(String pageNo, String pageSize
            , int status, int ownersType) {
        PostBean postBean = new PostBean();
        postBean.setPageNo("" + pageNo);
        postBean.setPageSize("" + pageSize);


        //1  我发布的  2 我接取的
        String commitStatus = "";
        if (ownersType == 1) {
//            订单状态 100=全部 -1=未支付 0=已支付 4=已经完成 3=退款\r
//            在我接的订单时，订单状态 100=全部 1=待直播 4=已经完成 2=未完成 5=已提现【必须】"
            switch (status) {
                case 1:
                    commitStatus = "100";
                    break;
                case 2:
                    commitStatus = "-1";
                    break;
                case 3:
                    commitStatus = "0";
                    break;
                case 4:
                    commitStatus = "4";
                    break;
                case 5:
                    commitStatus = "3";
                    break;
            }
            postBean.setStatus("" + commitStatus);
            String params = new Gson().toJson(postBean);
            RequestBody requestBody = null;
            try {
                requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LiveApi.getApiService(RequestService.class).liveUserOrderPublish(requestBody)
                    .subscribe(new SimpleObserver<ResponseBean<LiveUserOrderListBean>>() {
                        @Override
                        public void onSuccess(ResponseBean<LiveUserOrderListBean> responseBean) {
                            liveUserOrderPublishLiveData.postValue(responseBean);
                        }
                    });
        } else {
            switch (status) {
                case 1:
                    commitStatus = "100";
                    break;
                case 2:
                    commitStatus = "1";
                    break;
                case 3:
                    commitStatus = "2";
                    break;
                case 4:
                    commitStatus = "4";
                    break;
                case 5:
                    commitStatus = "5";
                    break;
            }
            postBean.setStatus("" + commitStatus);
            String params = new Gson().toJson(postBean);
            RequestBody requestBody = null;
            try {
                requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LiveApi.getApiService(RequestService.class).liveUserOrderReceive(requestBody)
                    .subscribe(new SimpleObserver<ResponseBean<LiveUserOrderListBean>>() {
                        @Override
                        public void onSuccess(ResponseBean<LiveUserOrderListBean> responseBean) {
                            liveUserOrderReceiveLiveData.postValue(responseBean);
                        }
                    });
        }


    }


    //评价详情
    public void liveUserAppraise(String requestId) {
        LiveApi.getApiService(RequestService.class).liveUserAppraise(requestId)
                .subscribe(new SimpleObserver<ResponseBean<LiveUserAppraiseBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<LiveUserAppraiseBean> responseBean) {
                        liveUserAppraiseMyLiveData.postValue(responseBean);
                    }
                });
    }

    //退款详情
    public void liveUserRefundDetail(String requestId) {
        LiveApi.getApiService(RequestService.class).liveUserRefundDetail(requestId)
                .subscribe(new SimpleObserver<ResponseBean<LiveUserRefundDetailBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<LiveUserRefundDetailBean> responseBean) {
                        liveUserRefundDetailLiveData.postValue(responseBean);
                    }
                });
    }


    //悬赏人对直播申请退款
    public void liveUserRefund(String requestId, String complainTitle
            , String complain) {
        PostBean postBean = new PostBean();
        postBean.setRequestId("" + requestId);
        postBean.setComplainTitle("" + complainTitle);
        postBean.setComplain("" + complain);
//        postBean.setComplainPhone("" + complainPhone);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).liveUserRefund(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        liveRefundApplyLiveData.postValue(responseBean);
                    }
                });
    }


    //用户填写提现的账户信息
    public void liveUserAccount(String accountType, String realName
            , String accountCode, String bankCode) {
        PostBean postBean = new PostBean();
        postBean.setAccountType("" + accountType);
        postBean.setRealName("" + realName);
        postBean.setAccountCode("" + accountCode);
        postBean.setBankCode("" + bankCode);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).liveUserAccount(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        liveUserAccountLiveData.postValue(responseBean);
                    }
                });
    }

    //获取录播的列表
    public void liveLuBoSearchMatchList(String pageNo, String pageSize
            , String searchName) {
        PostBean postBean = new PostBean();
        postBean.setPageNo("" + pageNo);
        postBean.setPageSize("" + pageSize);
        postBean.setType("" + 2);
        postBean.setStatus("" + "0");
        postBean.setSearchName("" + searchName);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).liveMatchList(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<LiveListBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<LiveListBean> responseBean) {
                        liveLuBOMatchListLiveDataLiveData.postValue(responseBean);
                    }
                });
    }


    //获取直播的列表  status":"int //类型 0=全部，1=推荐 2=专业，3=业务，4=单打  5=双打  6=混双　７=团体【必须】",
    public void liveZhiBoSearchMatchList(String searchName) {
        PostBean postBean = new PostBean();
        postBean.setPageNo("" + 1);
        postBean.setPageSize("" + 50);
        postBean.setType("" + 1);
        postBean.setStatus("" + "0");
        postBean.setSearchName("" + searchName);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).liveMatchList(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<LiveListBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<LiveListBean> responseBean) {
                        liveZhiBoMatchListLiveDataLiveData.postValue(responseBean);
                    }
                });
    }

    //获取录播的列表
    public void liveLuBoMatchList(String pageNo, String pageSize
            , String status, String searchName) {
        PostBean postBean = new PostBean();
        postBean.setPageNo("" + pageNo);
        postBean.setPageSize("" + pageSize);
        postBean.setType("" + 2);
        postBean.setStatus("" + status);
        postBean.setSearchName("" + searchName);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).liveMatchList(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<LiveListBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<LiveListBean> responseBean) {
                        liveLuBOMatchListLiveDataLiveData.postValue(responseBean);
                    }
                });
    }


    //获取直播的列表  status":"int //类型 0=全部，1=推荐 2=专业，3=业务，4=单打  5=双打  6=混双　７=团体【必须】",
    public void liveZhiBoMatchList(String status, String searchName) {
        PostBean postBean = new PostBean();
        postBean.setPageNo("" + 1);
        postBean.setPageSize("" + 50);
        postBean.setType("" + 1);
        postBean.setStatus("" + status);
        postBean.setSearchName("" + searchName);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).liveMatchList(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<LiveListBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<LiveListBean> responseBean) {
                        liveZhiBoMatchListLiveDataLiveData.postValue(responseBean);
                    }
                });
    }


    //每个赛事的悬赏榜单列表
    public void liveMatchPublishList(String matchId) {
        LiveApi.getApiService(RequestService.class).liveMatchPublishList(matchId)
                .subscribe(new SimpleObserver<ResponseBean<LiveMatchPublishListBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<LiveMatchPublishListBean> responseBean) {
                        liveMatchPublishListLiveData.postValue(responseBean);
                    }
                });
    }

    //获取用户接单的相关信息
    public void liveUserInfo() {
        LiveApi.getApiService(RequestService.class).liveUserInfo()
                .subscribe(new SimpleObserver<ResponseBean<LiveUserInfoBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<LiveUserInfoBean> responseBean) {
                        liveUserInfoLiveData.postValue(responseBean);
                    }
                });
    }

    //悬赏人对直播做评价
    public void liveUserAppraise(String requestId, String start, String content) {
        PostBean postBean = new PostBean();
        postBean.setRequestId("" + requestId);
        postBean.setStart("" + start);
        postBean.setContent("" + content);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).liveUserAppraise(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        liveUserAppraiseLiveData.postValue(responseBean);
                    }
                });
    }


    //用户申请成为接单人
    public void liveUserApply(String deviceInfo, String videoUrl) {
        PostBean postBean = new PostBean();
        postBean.setDeviceInfo("" + deviceInfo);
        postBean.setVideoUrl("" + videoUrl);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).liveUserApply(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        liveUserApplyLiveData.postValue(responseBean);
                    }
                });
    }


    //已经发布悬赏的订单详情
    public void liveMatchPublishDetail(String requestId) {
        LiveApi.getApiService(RequestService.class).liveMatchPublishDetail(requestId)
                .subscribe(new SimpleObserver<ResponseBean<LiveMatchDetailBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<LiveMatchDetailBean> responseBean) {
                        liveMatchPublishDetailLiveData.postValue(responseBean);
                    }
                });
    }

    //接受悬赏
    public void liveMatchAccept(String requestId) {
        LiveApi.getApiService(RequestService.class).liveMatchAccept(requestId)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        liveMatchAcceptLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * 七牛云上传图片获取凭证   上传视频  获取token
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

    public MutableLiveData<ResponseBean<MyMatchBean>> getMyMatchLiveData = new MutableLiveData<>();

    /**
     * 我的赛事
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
}
