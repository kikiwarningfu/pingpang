package heyong.intellectPinPang.model;

import java.util.HashMap;
import java.util.List;

import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.bean.SearchRefreeListBean;
import heyong.intellectPinPang.bean.competition.ClubContestStatisticsBean;
import heyong.intellectPinPang.bean.homepage.AppAdvertisementBean;
import heyong.intellectPinPang.bean.live.GetOrderMoneyBean;
import heyong.intellectPinPang.bean.live.LiveRoomBean;
import heyong.intellectPinPang.bean.live.MoneyDetailListBean;
import heyong.intellectPinPang.bean.live.SuspendLiveBroadCastBean;
import heyong.intellectPinPang.bean.live.LiveBroadCastMatchListBean;
import heyong.intellectPinPang.bean.live.WalletInfoBean;
import heyong.intellectPinPang.bean.live.WithDrawMyInfoBean;
import heyong.intellectPinPang.bean.live.LiveHallBean;
import heyong.intellectPinPang.bean.live.OfferRewardOrderListBean;
import heyong.intellectPinPang.bean.live.OfferRewardOrderListConditionBean;
import heyong.intellectPinPang.bean.live.XlZBjieDanOrdeListBean;
import heyong.intellectPinPang.bean.live.XlZhiboJiedanOrderBean;
import heyong.intellectPinPang.bean.competition.LoginIdentifyBean;
import heyong.intellectPinPang.bean.competition.MatchAgainstDataBean;
import heyong.intellectPinPang.bean.competition.MyGameListBean;
import heyong.intellectPinPang.bean.competition.OrderBookBean;
import heyong.intellectPinPang.bean.competition.RegistrationBean;
import heyong.intellectPinPang.bean.competition.AllAchievementBean;
import heyong.intellectPinPang.bean.competition.AllMyInfoBean;
import heyong.intellectPinPang.bean.competition.ArrangeDrawDataBean;
import heyong.intellectPinPang.bean.competition.ArrangeTeamContestBean;
import heyong.intellectPinPang.bean.competition.AwardsMathListBean;
import heyong.intellectPinPang.bean.competition.BeginArrangeBean;
import heyong.intellectPinPang.bean.competition.CalculationInfoBean;
import heyong.intellectPinPang.bean.competition.CheckUserClunRoleBean;
import heyong.intellectPinPang.bean.competition.ChooseJoinMatchUserBean;
import heyong.intellectPinPang.bean.competition.ChooseMemberDataBean;
import heyong.intellectPinPang.bean.competition.ChooseTeamFirstBean;
import heyong.intellectPinPang.bean.competition.ClubContestTeamArrangeBean;
import heyong.intellectPinPang.bean.competition.ClubContestTeamBean;
import heyong.intellectPinPang.bean.competition.ClubTypeDataBean;
import heyong.intellectPinPang.bean.competition.CoachMatchDtaBean;
import heyong.intellectPinPang.bean.competition.CoashRoleListBean;
import heyong.intellectPinPang.bean.competition.CompetitionItemBean;
import heyong.intellectPinPang.bean.competition.DateLimitBean;
import heyong.intellectPinPang.bean.competition.EnListBean;
import heyong.intellectPinPang.bean.competition.FillInMatchBaseInfoBean;
import heyong.intellectPinPang.bean.competition.FillInMatchListBean;
import heyong.intellectPinPang.bean.competition.GameMatchBean;
import heyong.intellectPinPang.bean.competition.IdentifyBean;
import heyong.intellectPinPang.bean.competition.JudgeClubContestWithStatusBean;
import heyong.intellectPinPang.bean.competition.JudgeJoinMatchStatusBean;
import heyong.intellectPinPang.bean.competition.JudgeUnCompletePageBean;
import heyong.intellectPinPang.bean.competition.LiveMatchPublishListBean;
import heyong.intellectPinPang.bean.competition.LoginBean;
import heyong.intellectPinPang.bean.competition.MatchArrangeGroupListBean;
import heyong.intellectPinPang.bean.competition.MatchArrangeKnockOutMatchBean;
import heyong.intellectPinPang.bean.competition.MatchArrangeMatchScoreBean;
import heyong.intellectPinPang.bean.competition.MatchArrangeRoundBean;
import heyong.intellectPinPang.bean.competition.MatchBaseInfoBean;
import heyong.intellectPinPang.bean.competition.MatchExperienceBean;
import heyong.intellectPinPang.bean.competition.MatchOrderInfoBean;
import heyong.intellectPinPang.bean.competition.MatchPersonalOrderInfoBean;
import heyong.intellectPinPang.bean.competition.MatchScreenBean;
import heyong.intellectPinPang.bean.competition.MatchScreenFormatBean;
import heyong.intellectPinPang.bean.competition.MessageInfoListBean;
import heyong.intellectPinPang.bean.competition.ModelDataBrandIdBean;
import heyong.intellectPinPang.bean.competition.MyAchievementBean;
import heyong.intellectPinPang.bean.competition.MyBaseInfoBean;
import heyong.intellectPinPang.bean.competition.MyClubListBean;
import heyong.intellectPinPang.bean.competition.MyMatchBean;
import heyong.intellectPinPang.bean.competition.NearFetureBean;
import heyong.intellectPinPang.bean.competition.OrderIdBean;
import heyong.intellectPinPang.bean.competition.PersonalClubBean;
import heyong.intellectPinPang.bean.competition.QueryChessInfoBean;
import heyong.intellectPinPang.bean.competition.QueryComputeInfoBean;
import heyong.intellectPinPang.bean.competition.QueryMatchApplyInfoBean;
import heyong.intellectPinPang.bean.competition.QueryNowUserIsChargeBean;
import heyong.intellectPinPang.bean.competition.QueryPointDetailBean;
import heyong.intellectPinPang.bean.competition.QuerySubmitBean;
import heyong.intellectPinPang.bean.competition.ReckonInfoDataBean;
import heyong.intellectPinPang.bean.competition.RefereeApplyMatchBean;
import heyong.intellectPinPang.bean.competition.RefereeChooseDataBean;
import heyong.intellectPinPang.bean.competition.RefereeConsoleBean;
import heyong.intellectPinPang.bean.competition.RefereeInfoBean;
import heyong.intellectPinPang.bean.competition.RefreerConsoleSortBean;
import heyong.intellectPinPang.bean.competition.SearchMathListBean;
import heyong.intellectPinPang.bean.competition.SecondNavigationFourBean;
import heyong.intellectPinPang.bean.competition.SelectClubListBean;
import heyong.intellectPinPang.bean.competition.SingleHitDataBean;
import heyong.intellectPinPang.bean.competition.TableNumArrangeBean;
import heyong.intellectPinPang.bean.competition.TeamArrangePlayBean;
import heyong.intellectPinPang.bean.competition.TeamMemberByMatchBean;
import heyong.intellectPinPang.bean.competition.ThroughListBean;
import heyong.intellectPinPang.bean.competition.TitleListBean;
import heyong.intellectPinPang.bean.competition.UpdateXlClubContestTeamBean;
import heyong.intellectPinPang.bean.competition.XlClubContestInfoBean;
import heyong.intellectPinPang.bean.competition.XlClubContestInfoListBean;
import heyong.intellectPinPang.bean.competition.XlClubContestTeamBean;
import heyong.intellectPinPang.bean.competition.XlClubInfoBean;
import heyong.intellectPinPang.bean.competition.XlClubInfoListBean;
import heyong.intellectPinPang.bean.competition.XlClubMemberBean;
import heyong.intellectPinPang.bean.competition.XlClubMemberListBean;
import heyong.intellectPinPang.bean.competition.XlEquipmentBrandListBean;
import heyong.intellectPinPang.bean.competition.XlMatchInfoArrangeChessBean;
import heyong.intellectPinPang.bean.competition.XlMatchInfoBlueDataBean;
import heyong.intellectPinPang.bean.competition.XlUserInfoBean;
import heyong.intellectPinPang.bean.competition.XlUserInfoListBean;
import heyong.intellectPinPang.bean.competition.getClubContestTeamNumberBean;
import heyong.intellectPinPang.bean.homepage.UpdateBean;
import heyong.intellectPinPang.bean.live.AliPlayVideoBean;
import heyong.intellectPinPang.bean.live.BeginLiveBroadCastBean;
import heyong.intellectPinPang.bean.live.CanonDemandBean;
import heyong.intellectPinPang.bean.live.CreateXlZhiboSetOrderBean;
import heyong.intellectPinPang.bean.live.LiveListBean;
import heyong.intellectPinPang.bean.live.LiveMatchByBean;
import heyong.intellectPinPang.bean.live.LiveMatchDetailBean;
import heyong.intellectPinPang.bean.live.LiveMatchInitBean;
import heyong.intellectPinPang.bean.live.LiveMatchStatusBean;
import heyong.intellectPinPang.bean.live.LiveMatchUrlBean;
import heyong.intellectPinPang.bean.live.LiveUserAppraiseBean;
import heyong.intellectPinPang.bean.live.LiveUserInfoBean;
import heyong.intellectPinPang.bean.live.LiveUserOrderListBean;
import heyong.intellectPinPang.bean.live.LiveUserRefundDetailBean;
import heyong.intellectPinPang.bean.live.LiveUserWithDrawalDetailBean;
import heyong.intellectPinPang.bean.live.OrderCenterBean;
import heyong.intellectPinPang.bean.live.XLzhiboSetOrderInfoBean;
import heyong.intellectPinPang.bean.live.XlZhiboGetMatchScreenDataBean;
import heyong.intellectPinPang.bean.live.XlZhiboOrderPayMentBean;
import heyong.intellectPinPang.bean.live.XlZhiboSetOrder;
import heyong.intellectPinPang.bean.live.XlZhiboSetOrderListBean;
import heyong.intellectPinPang.bean.live.ZhiboJiedanUserBaseInfoBean;
import heyong.intellectPinPang.bean.pay.WXPayBean;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RequestService {

    @POST("xlMatchInfo/searchTableNumArrange")
    Observable<ResponseBean<SearchRefreeListBean>> searchTableNumArrange(@Body RequestBody body);

    @POST("xlZhiboArrange/leaveLiveBroadcastingRoom")
    Observable<ResponseBean> leaveLiveBroadcastingRoom(@Body RequestBody body);


    @POST("xlZhiboBannerManage/appAdvertisement")
    Observable<ResponseBean<List<AppAdvertisementBean>>> appAdvertisement(@Body RequestBody body);

    //xlZhiboMoneyDetails/unbindAccount
    @POST("xlZhiboMoneyDetails/unbindAccount")
    Observable<ResponseBean> unbindAccount(@Body RequestBody body);


    //xlZhiboJiedanUser/bindingWithdrawalAccount
    @POST("xlZhiboJiedanUser/bindingWithdrawalAccount")
    Observable<ResponseBean> bindingWithdrawalAccount(@Body RequestBody body);

    @POST("xlZhiboArrange/watchPlayback")
    Observable<ResponseBean<LiveRoomBean>> watchPlayback(@Body RequestBody body);


    @POST("xlZhiboMoneyDetails/withdrawal")
    Observable<ResponseBean> withdrawal(@Body RequestBody body);


    @POST("xlZhiboSetOrder/getOrderMoney")
    Observable<ResponseBean<GetOrderMoneyBean>> getOrderMoney(@Body RequestBody body);


    @POST("xlZhiboMoneyDetails/withdrawalInfo")
    Observable<ResponseBean<WithDrawMyInfoBean>> withdrawalInfo(@Body RequestBody body);

    //xlZhiboMoneyDetails/getXlZhiboMoneyDetailsList
    @POST("xlZhiboMoneyDetails/getXlZhiboMoneyDetailsList")
    Observable<ResponseBean<MoneyDetailListBean>> getXlZhiboMoneyDetailsList(@Body RequestBody body);

    @POST("xlZhiboJiedanUser/walletInfo")
    Observable<ResponseBean<WalletInfoBean>> walletInfo(@Body RequestBody body);

    @POST("xlZhiboJiedanOrder/suspendLiveBroadcast")
    Observable<ResponseBean<SuspendLiveBroadCastBean>> suspendLiveBroadcast(@Body RequestBody body);

    @POST("xlZhiboArrange/liveBroadcastMatchList")
    Observable<ResponseBean<LiveBroadCastMatchListBean>> liveBroadcastMatchList(@Body RequestBody body);


    @POST("xlZhiboArrange/liveBroadcastMatchAgainstList")
    Observable<ResponseBean<LiveHallBean>> liveBroadcastMatchAgainstList(@Body RequestBody body);

    @POST("xlZhiboArrange/liveBroadcastMatchAgainstInfoList")
    Observable<ResponseBean<LiveHallBean>> liveBroadcastMatchAgainstInfoList(@Body RequestBody body);

    @POST("xlZhiboJiedanOrder/endLiveBroadcast")
    Observable<ResponseBean> endLiveBroadcast(@Body RequestBody body);

    @POST("xlZhiboJiedanOrder/endLiveBroadcast")
    Observable<ResponseBean> endLiveBroadcastForce(@Body RequestBody body);


    @POST("xlZhiboJiedanOrder/beginLiveBroadcast")
    Observable<ResponseBean<BeginLiveBroadCastBean>> beginLiveBroadcast(@Body RequestBody body);


    @POST("xlZhiboJiedanOrder/cancleJieDanOrder")
    Observable<ResponseBean> cancleJieDanOrder(@Body RequestBody body);


    @POST("xlZhiboJiedanOrder/getXlZhiboJiedanOrder")
    Observable<ResponseBean<XlZhiboJiedanOrderBean>> getXlZhiboJiedanOrder(@Body RequestBody body);


    @POST("xlZhiboJiedanOrder/getXlZhiboJiedanOrderList")
    Observable<ResponseBean<XlZBjieDanOrdeListBean>> getXlZhiboJiedanOrderList(@Body RequestBody body);


    @POST("xlZhiboSetOrder/complaintOrder")
    Observable<ResponseBean> complaintOrder(@Body RequestBody body);


    @POST("xlZhiboSetOrder/confirmOrder")
    Observable<ResponseBean> xlZhiboSetOrderconfirmOrder(@Body RequestBody body);

    @POST("xlZhiboSetOrder/receiveRewardOrder")
    Observable<ResponseBean> receiveRewardOrder(@Body RequestBody body);


    @POST("xlZhiboSetOrder/cancelOrder")
    Observable<ResponseBean> cancelOrder(@Body RequestBody body);

    @POST("xlZhiboSetOrder/getXlZhiboSetOrderInfo")
    Observable<ResponseBean<XLzhiboSetOrderInfoBean>> getXlZhiboSetOrderInfo(@Body RequestBody body);

    @POST("xlZhiboSetOrder/getXlZhiboSetOrderList")
    Observable<ResponseBean<XlZhiboSetOrderListBean>> getXlZhiboSetOrderList(@Body RequestBody body);

    @POST("xlZhiboSetOrder/payment")
    Observable<ResponseBean<XlZhiboOrderPayMentBean>> xlZhiboSetOrderPayment(@Body RequestBody body);


    @POST("xlZhiboSetOrder/zhifuOrder")
    Observable<ResponseBean> zhifuOrderZFB(@Body RequestBody body);

    @POST("xlZhiboSetOrder/zhifuOrder")
    Observable<ResponseBean<WXPayBean>> zhifuOrderWX(@Body RequestBody body);


    @POST("xlZhiboSetOrder/createXlZhiboSetOrder")
    Observable<ResponseBean<CreateXlZhiboSetOrderBean>> createXlZhiboSetOrder(@Body RequestBody body);

    @POST("xlZhiboArrange/getCanOnDemand")
    Observable<ResponseBean<CanonDemandBean>> getCanOnDemand(@Body RequestBody body);

    //xlZhiboSetOrder/getXlZhiboSetOrder
    @POST("xlZhiboSetOrder/getXlZhiboSetOrder")
    Observable<ResponseBean<XlZhiboSetOrder>> getXlZhiboSetOrder(@Body RequestBody body);

    @POST("xlZhiboArrange/getMatchScreen")
    Observable<ResponseBean<XlZhiboGetMatchScreenDataBean>> xlZhibogetMatchScreen(@Body RequestBody body);

    @POST("xlZhiboArrange/getMatchScreenFormat")
    Observable<ResponseBean<MatchScreenFormatBean>> xlZhibogetMatchScreenFormat(@Body RequestBody body);


    @POST("xlZhiboSetOrder/offerRewardOrderListCondition")
    Observable<ResponseBean<List<OfferRewardOrderListConditionBean>>> offerRewardOrderListCondition(@Body RequestBody body);

    @POST("xlZhiboSetOrder/offerRewardOrderList")
    Observable<ResponseBean<OfferRewardOrderListBean>> offerRewardOrderList(@Body RequestBody body);


    @POST("xlZhiboJiedanUser/orderReceivingCenter")
    Observable<ResponseBean<OrderCenterBean>> orderReceivingCenter(@Body RequestBody body);


    @POST("xlZhiboJiedanUser/zhiboJiedanUserBaseInfo")
    Observable<ResponseBean<ZhiboJiedanUserBaseInfoBean>> zhiboJiedanUserBaseInfo(@Body RequestBody body);


    @POST("xlZhiboJiedanUser/createXlZhiboJiedanUser")
    Observable<ResponseBean> createXlZhiboJiedanUser(@Body RequestBody body);


    @POST("xlZhiboJiedanUser/relieveJiedanRole")
    Observable<ResponseBean> relieveJiedanRole(@Body RequestBody body);


    @POST("xlZhiboJiedanUser/updateXlZhiboJiedanUser")
    Observable<ResponseBean> xlZhiboJiedanUser(@Body RequestBody body);

    @POST("xlZhiboJiedanUser/payYaJin")
    Observable<ResponseBean> payYaJinZFB(@Body RequestBody body);

    @POST("xlZhiboJiedanUser/payYaJin")
    Observable<ResponseBean<WXPayBean>> payYaJinWx(@Body RequestBody body);

    @POST("xlZhiboArrange/buyCompleteVideo")
    Observable<ResponseBean> buyCompleteVideoZFB(@Body RequestBody body);

    @POST("xlZhiboArrange/buyCompleteVideo")
    Observable<ResponseBean<WXPayBean>> buyCompleteVideoWx(@Body RequestBody body);

    //php??????
    @POST("matchOperation/phpAes")
    Observable<ResponseBean> phpAes(@Body RequestBody body);


    //??????????????????
    @POST("app/appScanCode")
    Observable<ResponseBean> appScanCode(@Body RequestBody body);

    //????????????
    @POST("xlMatchInfo/getMatchAgainstData")
    Observable<ResponseBean<List<MatchAgainstDataBean>>> getMatchAgainstData(@Body RequestBody body);

    //??????
    @POST("waiver/waiverOperation")
    Observable<ResponseBean> waiverOperation(@Body RequestBody body);

    //??????   ?????????????????????????????????????????????
    @GET("live/match/init")
    Observable<ResponseBean<LiveMatchInitBean>> liveMatchInit();

    //????????????ID??????????????????????????????
    @GET("live/match/status/{matchArrangeId}")
//get???????????????"/"?????????
    Observable<ResponseBean<LiveMatchStatusBean>> liveMatchStatus(@Path("matchArrangeId") String userId);


    //???????????????????????????????????????
    @GET("live/match/detail/{matchArrangeId}")
//get???????????????"/"?????????
    Observable<ResponseBean<LiveMatchDetailBean>> liveMatchDetail(@Path("matchArrangeId") String userId);

    //???????????????????????????????????????
    @GET("live/match/detail/{matchArrangeId}")
//get???????????????"/"?????????
    Observable<ResponseBean<AliPlayVideoBean>> liveAliPlayVideo(@Path("matchArrangeId") String userId);

    //?????????????????????????????????
    @GET("live/match/publish/list/{matchId}")
//get???????????????"/"?????????
    Observable<ResponseBean<LiveMatchPublishListBean>> liveMatchPublishList(@Path("matchId") String matchId);

    //live/match/publish/detail/{requestId}  ?????????????????????????????????
    @GET("live/match/publish/detail/{requestId}")
    Observable<ResponseBean<LiveMatchDetailBean>> liveMatchPublishDetail(@Path("requestId") String requestId);

    //live/match/accept/{requestId} ????????????
    @GET("live/match/accept/{requestId}")
    Observable<ResponseBean> liveMatchAccept(@Path("requestId") String requestId);

    //??????????????????????????????
    @GET("live/match/checkOrder/{orderId}")
//get???????????????"/"?????????
    Observable<ResponseBean> liveMatchCheckOrder(@Path("orderId") String orderId);


    //???????????????
    @POST("live/match/publish")
    Observable<ResponseBean<LiveMatchByBean>> liveMatchPublish(@Body RequestBody body);

    //????????????
    @GET("live/user/appraise/{requestId}")
    Observable<ResponseBean<LiveUserAppraiseBean>> liveUserAppraise(@Path("requestId") String userId);


    //    live/match/list
    //??????????????????????????????
    @POST("live/match/list")
    Observable<ResponseBean<LiveListBean>> liveMatchList(@Body RequestBody body);


    //?????????????????????????????????
    @POST("live/user/account")
    Observable<ResponseBean> liveUserAccount(@Body RequestBody body);

    //live/user/appraise   ???????????????????????????
    @POST("live/user/appraise")
    Observable<ResponseBean> liveUserAppraise(@Body RequestBody body);


    //????????????????????????????????????
    @GET("live/user/info")
    Observable<ResponseBean<LiveUserInfoBean>> liveUserInfo();


    //???????????????????????????
    @POST("live/user/apply")
    Observable<ResponseBean> liveUserApply(@Body RequestBody body);


    //??????????????????????????????
    @POST("live/user/reFund")
    Observable<ResponseBean> liveUserRefund(@Body RequestBody body);

    //????????????
    @GET("live/user/reFundDetail/{requestId}")
    Observable<ResponseBean<LiveUserRefundDetailBean>> liveUserRefundDetail(@Path("requestId") String userId);


    //??????????????????  live/user/order/receive
    @POST("live/user/order/receive")
    Observable<ResponseBean<LiveUserOrderListBean>> liveUserOrderReceive(@Body RequestBody body);

    //??????????????????  live/user/order/publish
    @POST("live/user/order/publish")
    Observable<ResponseBean<LiveUserOrderListBean>> liveUserOrderPublish(@Body RequestBody body);


    //???????????????????????????????????????
    @GET("live/match/rePay/{orderId}")
    Observable<ResponseBean<LiveMatchByBean>> liveMatchRepay(@Path("orderId") String orderId);

    //???????????????????????????  ????????????
    @POST("live/match/buy")
    Observable<ResponseBean<LiveMatchByBean>> liveMatchBuy(@Body RequestBody body);


    //??????ID???????????????????????????
    @GET("live/match/url/{requestId}")
    Observable<ResponseBean<LiveMatchUrlBean>> liveMatchUrl(@Path("requestId") String requestId);

    //????????????
    @GET("live/user/withdrawal/detail/{requestId}")
    Observable<ResponseBean<LiveUserWithDrawalDetailBean>> liveUserWithDrawalDetail(@Path("requestId") String requestId);


    //??????????????????
    @GET("live/user/withdrawal/{requestId}")
    Observable<ResponseBean> liveUserWithDrawl(@Path("requestId") String requestId);


    //????????????????????????
    @GET("live/user/order/receive/cancle/{requestId}")
    Observable<ResponseBean> liveUserOrderCancel(@Path("requestId") String userId);

    //?????????????????????????????????
    @GET("live/user/order/publish/cancle/{requestId}")
    Observable<ResponseBean> liveUserOrderPublish(@Path("requestId") String requestId);

    //?????????????????????????????????????????????
    @GET("live/user/order/publish/delete/{requestId}")
    Observable<ResponseBean> liveUserOrderPublishDelete(@Path("requestId") String requestId);

    //???????????????????????????????????????
    @GET("live/user/order/receive/delete/{requestId}")
    Observable<ResponseBean> liveUserOrderReceiveDelete(@Path("requestId") String requestId);


    //-----------------------------------------------App???

    //??????????????????
    @POST("xlMatchInfo/theNearFuture")
    Observable<ResponseBean<List<NearFetureBean>>> theNearFuture(@Body RequestBody body);

    //??????????????????
    @POST("xlMatchInfo/myJoinMatch")
    Observable<ResponseBean<NearFetureBean>> myJoinMatch(@Body RequestBody body);

    //xlMatchInfo/version ???????????????
    @POST("xlMatchInfo/version")
    Observable<ResponseBean<UpdateBean>> version(@Body RequestBody body);


    //????????????
    @POST("xlMatchInfo/blueData")
    Observable<ResponseBean<List<XlMatchInfoBlueDataBean>>> blueData(@Body RequestBody body);

    //??????
    @POST("xlUserInfo/loginAuthentication")
    Observable<ResponseBean<LoginBean>> loginAuthentication(@Body RequestBody body);

    //????????????????????????????????????
    @POST("xlUserInfo/bindPhoneNum")
    Observable<ResponseBean<LoginBean>> bindPhoneNum(@Body RequestBody body);

    //???????????????
    @POST("xlClubInfo/getXlClubInfoList")
    Observable<ResponseBean<XlClubInfoListBean>> getXlClubInfoList(@Body RequestBody body);

    //???????????????
    @POST("xlClubInfo/createXlClubInfo")
    Observable<ResponseBean> createXlClubInfo(@Body RequestBody body);

    //???????????????
    @POST("xlClubInfo/updateXlClubInfo")
    Observable<ResponseBean> updateXlClubInfo(@Body RequestBody body);

    //???????????????
    @POST("xlClubInfo/getXlClubInfo")
    Observable<ResponseBean<XlClubInfoBean>> getXlClubInfo(@Body RequestBody body);

    //?????????????????????
    @POST("xlClubMember/getXlClubMemberList")
    Observable<ResponseBean<XlClubMemberListBean>> getXlClubMemberList(@Body RequestBody body);

    //?????????????????????
    @POST("xlClubMember/getXlClubMember")
    Observable<ResponseBean<XlClubMemberBean>> getXlClubMember(@Body RequestBody body);

    //???????????????   ????????????????????????
    @POST("xlClubInfo/deleteXlClubInfo")
    Observable<ResponseBean<LoginBean>> deleteXlClubInfo(@Body RequestBody body);

    //???????????????
    @POST("xlClubInfo/getClubTypeData")
    Observable<ResponseBean<List<ClubTypeDataBean>>> getClubTypeData(@Body RequestBody body);

    //???????????????
    @POST("xlClubMember/deleteXlClubMember")
    Observable<ResponseBean> deleteXlClubMember(@Body RequestBody body);

    //???????????????
    @POST("xlClubMember/quitClub")
    Observable<ResponseBean> quitClub(@Body RequestBody body);

    //???????????????????????????
    @POST("xlClubMember/approvalClubApply")
    Observable<ResponseBean> approvalClubApply(@Body RequestBody body);

    //???????????????????????????
    @POST("xlClubMember/updateMemberInfo")
    Observable<ResponseBean> updateMemberInfo(@Body RequestBody body);

    //?????????????????????
    @POST("xlClubMember/askForJoinClub")
    Observable<ResponseBean> askForJoinClub(@Body RequestBody body);

    //xlUserInfo/matchExperience ?????????????????????
    @POST("xlUserInfo/matchExperience")
    Observable<ResponseBean<MatchExperienceBean>> matchExperience(@Body RequestBody body);

    //xlClubContestResult/calculationInfo   ???????????????????????????
    @POST("xlClubContestResult/calculationInfo")
    Observable<ResponseBean<CalculationInfoBean>> calculationInfo(@Body RequestBody body);

    //xlClubContestResult/reckonInfoData  ?????????????????? ??????????????????
    @POST("xlClubContestResult/reckonInfoData")
    Observable<ResponseBean<List<ReckonInfoDataBean>>> reckonInfoData(@Body RequestBody body);

    //matchOperation/fillInMatchList ??????????????????????????????
    @POST("matchOperation/fillInMatchList")
    Observable<ResponseBean<FillInMatchListBean>> fillInMatchList(@Body RequestBody body);

    //matchOperation/fillInMatchBaseInfo  ??????????????????????????????
    @POST("matchOperation/fillInMatchBaseInfo")
    Observable<ResponseBean<FillInMatchBaseInfoBean>> fillInMatchBaseInfo(@Body RequestBody body);

    //matchOperation/getTeamArangePlay  ???????????????
    @POST("matchOperation/getTeamArangePlay")
    Observable<ResponseBean<List<TeamArrangePlayBean>>> getTeamArangePlay(@Body RequestBody body);

    //matchOperation/submitTeamArrange  ??????????????????
    @POST("matchOperation/submitTeamArrange")
    Observable<ResponseBean> submitTeamArrange(@Body RequestBody body);

    //???????????????????????????????????????  matchOperation/judgeUnCompletePage
    @POST("matchOperation/judgeUnCompletePage")
    Observable<ResponseBean<JudgeUnCompletePageBean>> judgeUnCompletePage(@Body RequestBody body);

    //?????????????????????  matchOperation/refereeInTableNum
    @POST("matchOperation/refereeInTableNum")
    Observable<ResponseBean<List<String>>> refereeInTableNum(@Body RequestBody body);

    //?????????????????????????????????  matchOperation/sortTableNumArrange
    @POST("matchOperation/sortTableNumArrange")
    Observable<ResponseBean> sortTableNumArrange(@Body RequestBody body);

    //??????????????????  matchOperation/arrangeDraw
    @POST("matchOperation/arrangeDraw")
    Observable<ResponseBean<ArrangeDrawDataBean>> arrangeDraw(@Body RequestBody body);

    //????????????
    @POST("matchOperation/pushMessageToCoachAndroid")
    Observable<ResponseBean> pushMessageToCoachAndroid(@Body RequestBody body);

    //????????????????????????????????????
    @POST("matchOperation/chooseTeamFirst")
    Observable<ResponseBean<ChooseTeamFirstBean>> chooseTeamFirst(@Body RequestBody body);


    //?????????????????????
    @POST("matchOperation/beginArrange")
    Observable<ResponseBean<BeginArrangeBean>> beginArrange(@Body RequestBody body);

    //matchOperation/judgeRefereeUpdateScore  ???????????????????????????????????????
    @POST("matchOperation/judgeRefereeUpdateScore")
    Observable<ResponseBean> judgeRefereeUpdateScore(@Body RequestBody body);

    //matchOperation/coachSuspend   ???????????????
    @POST("matchOperation/coachSuspend")
    Observable<ResponseBean> coachSuspend(@Body RequestBody body);

    //matchOperation/waiverChess  ??????
    @POST("matchOperation/waiverChess")
    Observable<ResponseBean> waiverChess(@Body RequestBody body);

    //matchOperation/endArrange  ????????????????????????
    @POST("matchOperation/endArrange")
    Observable<ResponseBean> endArrange(@Body RequestBody body);

    //matchOperation/endArrange  ?????????????????????
    @POST("matchOperation/updateChessScore")
    Observable<ResponseBean> updateChessScore(@Body RequestBody body);


    //matchOperation/getLoginInentify  ??????????????????????????????
    @POST("matchOperation/getLoginInentify")
    Observable<LoginIdentifyBean> getLoginInentify(@Body RequestBody body);


    //???????????????????????????????????????????????????
    @POST("xlClubMember/queryNowUserIsCharge")
    Observable<ResponseBean<QueryNowUserIsChargeBean>> queryNowUserIsCharge(@Body RequestBody body);

    //?????????????????????????????????
    @POST("matchOperation/endTeamArrange")
    Observable<ResponseBean<QueryNowUserIsChargeBean>> endTeamArrange(@Body RequestBody body);


    //????????????
    @POST("TaoTaiSaiDaohang/getSecondNavigation")
    Observable<ResponseBean<SecondNavigationFourBean>> getSecondNavigation(@Body RequestBody body);

    /**
     * ?????????????????????????????????
     */
    @POST("upload/getUploadToken")
    Observable<ResponseBean> getUploadToken(@QueryMap HashMap<String, String> params);

    /**
     * ???????????????
     */
    @POST("xlUserInfo/getXlUserInfoList")
    Observable<ResponseBean<XlUserInfoListBean>> getXlUserInfoList(@Body RequestBody body);


    /**
     * ???????????????
     */
    @POST("xlUserInfo/getXlUserInfo")
    Observable<ResponseBean<XlUserInfoBean>> getXlUserInfo(@Body RequestBody body);


    //???????????????????????????????????????  xlMatchInfo/checkUserPower
    @POST("xlMatchInfo/checkUserPower")
    Observable<ResponseBean> checkUserPower(@Body RequestBody body);

    //????????????????????????????????? xlMatchInfo/getMatchArrangeData
    @POST("xlMatchInfo/getMatchArrangeData")
    Observable<ResponseBean<MatchArrangeRoundBean>> getMatchArrangeData(@Body RequestBody body);

    //xlMatchInfo/queryPointsDetails   ??????????????????
    @POST("xlMatchInfo/queryPointsDetails")
    Observable<ResponseBean<QueryPointDetailBean>> queryPointsDetails(@Body RequestBody body);

    //xlMatchInfo/teamMemberByMatch   ??????????????????
    @POST("xlMatchInfo/teamMemberByMatch")
    Observable<ResponseBean<TeamMemberByMatchBean>> teamMemberByMatch(@Body RequestBody body);


    //xlMatchInfo/tableNumArrange  ????????????????????????????????????
    @POST("xlMatchInfo/tableNumArrange")
    Observable<ResponseBean<TableNumArrangeBean>> tableNumArrange(@Body RequestBody body);

    //xlMatchInfo/wodeMatchs ????????????????????????????????????
    @POST("xlMatchInfo/wodeMatchs")
    Observable<ResponseBean<List<MyGameListBean>>> wodeMatchs(@Body RequestBody body);

    //xlMatchReferee/queryTable  ??????
    @POST("xlMatchReferee/queryTable")
    Observable<ResponseBean<List<String>>> queryTable(@Body RequestBody body);

    //xlMatchReferee/tuningStation  ??????
    @POST("xlMatchReferee/tuningStation")
    Observable<ResponseBean> tuningStation(@Body RequestBody body);

    //xlMatchInfo/dateLimit  ??????
    @POST("xlMatchInfo/dateLimit")
    Observable<ResponseBean<DateLimitBean>> dateLimit(@Body RequestBody body);


    //xlMatchInfo/arrangeChess  ???????????????????????????????????????????????????
    @POST("xlMatchInfo/arrangeChess")
    Observable<ResponseBean<XlMatchInfoArrangeChessBean>> arrangeChess(@Body RequestBody body);


    //??????????????????????????????  xlMatchInfo/judgeJoinMatchStatus
    @POST("xlMatchInfo/judgeJoinMatchStatus")
    Observable<ResponseBean<JudgeJoinMatchStatusBean>> judgeJoinMatchStatus(@Body RequestBody body);


    //????????????????????????????????? xlMatchInfo/getMatchArrangeData
    @POST("xlMatchInfo/getMatchArrangeData")
    Observable<ResponseBean<MatchArrangeKnockOutMatchBean>> getMatchArrangeKnockOutData(@Body RequestBody body);

    //???????????????????????????????????? xlMatchInfo/getMatchArrangeData
    @POST("xlMatchInfo/getMatchArrangeData")
    Observable<ResponseBean<MatchArrangeGroupListBean>> getMatchGroupList(@Body RequestBody body);

    //????????????????????????????????? xlMatchInfo/getMatchArrangeData
    @POST("xlMatchInfo/getMatchArrangeData")
    Observable<ResponseBean<MatchArrangeMatchScoreBean>> getMatchScoreList(@Body RequestBody body);


    //???????????????????????????????????????  xlMatchInfo/getMatchScreen
    @POST("xlMatchInfo/getMatchScreen")
    Observable<ResponseBean<MatchScreenBean>> getMatchScreen(@Body RequestBody body);

    //xlMatchInfo/getMatchScreenFormat  ???????????????????????????????????????
    @POST("xlMatchInfo/getMatchScreenFormat")
    Observable<ResponseBean<MatchScreenFormatBean>> getMatchScreenFormat(@Body RequestBody body);


    //xlUserInfo/judgeMyInfo  ????????????????????????????????????
    @POST("xlUserInfo/judgeMyInfo")
    Observable<ResponseBean> judgeMyInfo(@Body RequestBody body);


    //xlMatchInfo/queryComputeInfo  ???????????? ????????????
    @POST("xlMatchInfo/queryComputeInfo")
    Observable<ResponseBean<QueryComputeInfoBean>> queryComputeInfo(@Body RequestBody body);


    //????????????  createXlMatchInfo
    @POST("xlMatchInfo/createXlMatchInfo")
    Observable<ResponseBean> createXlMatchInfo(@Body RequestBody body);


    //?????????????????????????????????  xlClubContestInfo/chooseMemberData
    @POST("xlClubContestInfo/chooseMemberData")
    Observable<ResponseBean<List<ChooseMemberDataBean>>> chooseMemberData(@Body RequestBody body);

    //xlClubContestInfo/createXlClubContestInfo  ??????????????????
    @POST("xlClubContestInfo/createXlClubContestInfo")
    Observable<ResponseBean> createXlClubContestInfo(@Body RequestBody body);

    //xlClubContestTeam/getXlClubContestTeam   ?????????????????????????????????
    @POST("xlClubContestTeam/getXlClubContestTeam")
    Observable<ResponseBean<List<XlClubContestTeamBean>>> getXlClubContestTeam(@Body RequestBody body);

    //??????????????????????????????  xlClubContestTeam/updateXlClubContestTeam
    @POST("xlClubContestTeam/updateXlClubContestTeam")
    Observable<ResponseBean<UpdateXlClubContestTeamBean>> updateXlClubContestTeam(@Body RequestBody body);

    //??????????????????????????????  xlClubContestArrange/arrangeTeamContest
    @POST("xlClubContestArrange/arrangeTeamContest")
    Observable<ResponseBean<List<ArrangeTeamContestBean>>> arrangeTeamContest(@Body RequestBody body);

    //????????????????????????????????????  xlClubContestArrange/updateXlClubContestArrange
    @POST("xlClubContestArrange/updateXlClubContestArrange")
    Observable<ResponseBean> updateXlClubContestArrange(@Body RequestBody body);

    //???????????????????????????  xlContestTeamMember/getClubContestTeamMember
    @POST("xlContestTeamMember/getClubContestTeamMember")
    Observable<ResponseBean<List<getClubContestTeamNumberBean>>> getClubContestTeamMember(@Body RequestBody body);

    //????????????????????????   xlClubContestArrange/getClubContestTeamData
    @POST("xlClubContestArrange/getClubContestTeamData")
    Observable<ResponseBean<List<ClubContestTeamBean>>> getClubContestTeamData(@Body RequestBody body);

    //???????????????????????????????????????
    @POST("xlClubContestInfo/clubContestStatistics")
    Observable<ResponseBean<ClubContestStatisticsBean>> getClubContestStatistics(@Body RequestBody body);

    //??????????????????   xlClubContestArrange/getClubContestTeamArrange

    @POST("xlClubContestArrange/getClubContestTeamArrange")
    Observable<ResponseBean<ClubContestTeamArrangeBean>> getClubContestTeamArrange(@Body RequestBody body);


    //??????????????????  xlClubContestArrange/getSingleHitData
    @POST("xlClubContestArrange/getSingleHitData")
    Observable<ResponseBean<SingleHitDataBean>> getSingleHitData(@Body RequestBody body);

    //xlClubContestArrangeChess/queryChessInfo  ??????????????????,??????????????????????????????????????????
    @POST("xlClubContestArrangeChess/queryChessInfo")
    Observable<ResponseBean<QueryChessInfoBean>> queryChessInfo(@Body RequestBody body);

    //xlClubContestArrangeChess/waiverContest   ??????????????????
    @POST("xlClubContestArrangeChess/waiverContest")
    Observable<ResponseBean> waiverContest(@Body RequestBody body);

    //xlClubContestArrangeImg/createXlClubContestArrangeImg  ?????????????????????????????????
    @POST("xlClubContestArrangeImg/createXlClubContestArrangeImg")
    Observable<ResponseBean> uploadContestImg(@Body RequestBody body);

    //xlClubContestArrangeChess/updateXlClubContestArrangeChess   ????????????
    @POST("xlClubContestArrangeChess/updateXlClubContestArrangeChess")
    Observable<ResponseBean> updateXlClubContestArrangeChess(@Body RequestBody body);

    //xlClubContestArrangeChess/suspendWaiver  ?????????????????????
    @POST("xlClubContestArrangeChess/suspendWaiver")
    Observable<ResponseBean> suspendWaiver(@Body RequestBody body);

    //xlClubContestInfo/updateXlClubContestInfo   ??????????????????
    @POST("xlClubContestInfo/updateXlClubContestInfo")
    Observable<ResponseBean> updateXlClubContestInfo(@Body RequestBody body);

    //xlClubContestInfo/updateXlClubContestInfo   ??????????????????
    @POST("xlClubContestInfo/getXlClubContestInfo")
    Observable<ResponseBean<XlClubContestInfoBean>> getXlClubContestInfo(@Body RequestBody body);

    //xlClubContestInfo/getXlClubContestInfoList   ??????????????????
    @POST("xlClubContestInfo/getXlClubContestInfoList")
    Observable<ResponseBean<XlClubContestInfoListBean>> getXlClubContestInfoList(@Body RequestBody body);

    //???????????????(?????????????????????????????????)   xlClubInfo/myClubList
    @POST("xlClubInfo/myClubList")
    Observable<ResponseBean<List<SelectClubListBean>>> myClubList(@Body RequestBody body);


    //????????????????????????????????????  xlClubContestInfo/judgeClubContestWithStatus
    @POST("xlClubContestInfo/judgeClubContestWithStatus")
    Observable<ResponseBean<JudgeClubContestWithStatusBean>> judgeClubContestWithStatus(@Body RequestBody body);


    //xlUserInfo/updateAccount   ????????????
    @POST("xlUserInfo/updateAccount")
    Observable<ResponseBean> updateAccount(@Body RequestBody body);


    //xlUserInfo/getCode    ?????????????????????
    @POST("xlUserInfo/getCode")
    Observable<ResponseBean> getCode(@Body RequestBody body);


    //xlUserInfo/goThrough  ????????????
    @POST("xlUserInfo/goThrough")
    Observable<ResponseBean<List<ThroughListBean>>> goThrough(@Body RequestBody body);

    //xlUserInfo/getAllMyInfo  ????????????????????????
    @POST("xlUserInfo/getAllMyInfo")
    Observable<ResponseBean<AllMyInfoBean>> getAllMyInfo(@Body RequestBody body);


    //-----------------------------------------------------------------------------------------?????????????????????
    //xlUserInfo/updateXlUserInfo  ??????????????????
    @POST("xlUserInfo/updateXlUserInfo")
    Observable<ResponseBean> updateXlUserInfo(@Body RequestBody body);

    //localhost:9580/xlEquipmentBrand/getXlEquipmentBrandList   ??????????????????
    @POST("backstage/getXlEquipmentBrandList")
    Observable<ResponseBean<XlEquipmentBrandListBean>> getXlEquipmentBrandList(@Body RequestBody body);

    //localhost:9580/xlEquipmentModel/getModelDataByBrandId  ??????????????????
    @POST("backstage/getModelDataByBrandId")
    Observable<ResponseBean<List<ModelDataBrandIdBean>>> getModelDataByBrandId(@Body RequestBody body);


    //xlUserInfo/getMyClub   ???????????????
    @POST("xlUserInfo/getMyClub")
    Observable<ResponseBean<MyClubListBean>> getMyClub(@Body RequestBody body);


    //xlUserInfo/verifyMaterial   ????????????
    @POST("xlUserInfo/verifyMaterial")
    Observable<ResponseBean> verifyMaterial(@Body RequestBody body);


    //xlUserInfo/getMyMatch  ????????????
    @POST("xlUserInfo/getMyMatch")
    Observable<ResponseBean<MyMatchBean>> getMyMatch(@Body RequestBody body);


    //xlUserInfo/getIdentityInfo   ??????????????????
    @POST("xlUserInfo/getIdentityInfo")
    Observable<ResponseBean<IdentifyBean>> getIdentityInfo(@Body RequestBody body);

    //xlMatchInfo/getCoachMatchDta  ???????????????????????????
    @POST("xlMatchInfo/getCoachMatchDta")
    Observable<ResponseBean<List<CoachMatchDtaBean>>> getCoachMatchDta(@Body RequestBody body);

    //xlMessageInfo/getXlMessageInfoList  ????????????
    @POST("xlMessageInfo/getXlMessageInfoList")
    Observable<ResponseBean<MessageInfoListBean>> getXlMessageInfoList(@Body RequestBody body);

    //xlMessageInfo/updateXlMessageInfo   ???????????????????????????
    @POST("xlMessageInfo/updateXlMessageInfo")
    Observable<ResponseBean> updateXlMessageInfo(@Body RequestBody body);

    //xlMessageInfo/getTitleList    ?????????????????????
    @POST("xlMessageInfo/getTitleList")
    Observable<ResponseBean<TitleListBean>> getTitleList(@Body RequestBody body);

    //xlUserInfo/myBaseInfo   ????????????????????????
    @POST("xlUserInfo/myBaseInfo")
    Observable<ResponseBean<MyBaseInfoBean>> myBaseInfo(@Body RequestBody body);

    //xlUserInfo/myEnlist   ????????????
    @POST("xlUserInfo/myEnlist")
    Observable<ResponseBean<EnListBean>> myEnlist(@Body RequestBody body);

    //xlEnrollMatch/deleteXlEnrollMatch  ????????????
    @POST("xlEnrollMatch/deleteXlEnrollMatch")
    Observable<ResponseBean> deleteXlEnrollMatch(@Body RequestBody body);

    //xlMatchReferee/refereerConsoleSort  ???????????????????????????
    @POST("xlMatchReferee/refereerConsoleSort")
    Observable<ResponseBean<RefreerConsoleSortBean>> refereerConsoleSort(@Body RequestBody body);

    //xlMatchReferee/refereerConsole  ??????????????????????????????
    @POST("xlMatchReferee/refereerConsole")
    Observable<ResponseBean<List<RefereeConsoleBean>>> refereerConsole(@Body RequestBody body);

    //xlMatchReferee/refereeChooseData  ???????????????????????????
    @POST("xlMatchReferee/refereeChooseData")
    Observable<ResponseBean<List<RefereeChooseDataBean>>> refereeChooseData(@Body RequestBody body);

    //xlMatchReferee/updateReferee  ?????????????????????
    @POST("xlMatchReferee/updateReferee")
    Observable<ResponseBean> updateReferee(@Body RequestBody body);

    //xlMatchReferee/deleteReferee  ????????????
    @POST("xlMatchReferee/deleteReferee")
    Observable<ResponseBean> deleteReferee(@Body RequestBody body);

    //xlMatchReferee/startMatch  ????????????
    @POST("xlMatchReferee/startMatch")
    Observable<ResponseBean> startMatch(@Body RequestBody body);

    //xlMatchInfo/judgeUserShiFouChongFu  ???????????????????????????
    @POST("xlMatchInfo/judgeUserShiFouChongFu")
    Observable<ResponseBean> judgeUserShiFouChongFu(@Body RequestBody body);

    //xlMatchInfo/judgeUserShiFouChongFuDelete  ???????????????????????????
    @POST("xlMatchInfo/judgeUserShiFouChongFuDelete")
    Observable<ResponseBean> judgeUserShiFouChongFuDelete(@Body RequestBody body);

    //xlMatchInfo/judgeUserJoinMatch  ??????????????????????????????
    @POST("xlMatchInfo/judgeUserJoinMatch")
    Observable<ResponseBean> judgeUserJoinMatch(@Body RequestBody body);

    //waiver/RegistrationDetails  ????????????
    @POST("waiver/RegistrationDetails")
    Observable<ResponseBean<List<RegistrationBean>>> RegistrationDetails(@Body RequestBody body);

    //appOperationLog/getOrderBook  ???????????????
    @POST("appOperationLog/getOrderBook")
    Observable<ResponseBean<OrderBookBean>> getOrderBook(@Body RequestBody body);


    //xlMatchReferee/getData  ???????????????????????????????????????
    @POST("matchOperation/querySubmit")
    Observable<ResponseBean<QuerySubmitBean>> querySubmit(@Body RequestBody body);

    //matchOperation/confirmScore  ??????????????????
    @POST("matchOperation/confirmScore")
    Observable<ResponseBean> confirmScore(@Body RequestBody body);


    //matchOperation/scoringReferee  ??????????????????
    @POST("matchOperation/scoringReferee")
    Observable<ResponseBean> scoringReferee(@Body RequestBody body);

    //matchOperation/joinBymessage ???????????????????????????
    @POST("matchOperation/joinBymessage")
    Observable<ResponseBean<MatchBaseInfoBean>> joinBymessage(@Body RequestBody body);

    //xlEnrollMatch/signOutMatchOrder  ????????????
    @POST("xlEnrollMatch/signOutMatchOrder")
    Observable<ResponseBean> signOutMatchOrder(@Body RequestBody body);

    //xlEnrollMatch/matchOrderInfo  ??????????????????
    @POST("xlEnrollMatch/matchOrderInfo")
    Observable<ResponseBean<MatchOrderInfoBean>> matchOrderInfo(@Body RequestBody body);

    //xlEnrollMatch/matchOrderInfo  ??????????????????  ??????
    @POST("xlEnrollMatch/matchOrderInfo")
    Observable<ResponseBean<MatchPersonalOrderInfoBean>> matchPersonalOrderInfo(@Body RequestBody body);

    //xlUserInfo/refereeInfo  ???????????????
    @POST("xlUserInfo/refereeInfo")
    Observable<ResponseBean<RefereeInfoBean>> refereeInfo(@Body RequestBody body);

    //xlRoleInfo/getCoachRoleList   ?????????????????????  ????????? 1
    @POST("xlRoleInfo/getCoachRoleList")
    Observable<ResponseBean<List<CoashRoleListBean>>> getCoachRoleList(@Body RequestBody body);


    //??????????????????????????????   xlUserInfo/updateRefereeInfo
    @POST("xlUserInfo/updateRefereeInfo")
    Observable<ResponseBean> updateRefereeInfo(@Body RequestBody body);

    //xlUserInfo/myAchievement   ????????????  //????????????
    @POST("xlUserInfo/myAchievement")
    Observable<ResponseBean<List<MyAchievementBean>>> myAchievement(@Body RequestBody body);


    //xlUserInfo/myAchievement   ????????????  ????????????
    @POST("xlUserInfo/myAchievement")
    Observable<ResponseBean<AllAchievementBean>> myAllAchievement(@Body RequestBody body);


    //xlUserInfo/awardsMatchList  ????????????,???????????????????????????
    @POST("xlUserInfo/awardsMatchList")
    Observable<ResponseBean<List<AwardsMathListBean>>> awardsMatchList(@Body RequestBody body);

    //xlMatchInfo/registrantsMacth  ??????????????????????????????
    @POST("xlMatchInfo/registrantsMacth")
    Observable<ResponseBean> registrantsMacth(@Body RequestBody body);

    //xlMatchInfo/registrantsMacth  ??????????????????????????????
    @POST("xlMatchInfo/registrantsMacth")
    Observable<ResponseBean<WXPayBean>> regWxistrantsMacth(@Body RequestBody body);

    //xlMatchInfo/registrantsMacth  ??????????????????????????????
    @POST("xlMatchInfo/registrantsMacth")
    Observable<ResponseBean> regZfbistrantsMacth(@Body RequestBody body);

    //matchOperation/getOrderId  ???????????????id
    @POST("matchOperation/getOrderId")
    Observable<OrderIdBean> getOrderId(@Body RequestBody body);

    //xlMatchInfo/competitionItem  ??????????????????????????????
    @POST("xlMatchInfo/competitionItem")
    Observable<ResponseBean<List<CompetitionItemBean>>> competitionItem(@Body RequestBody body);

    //xlMatchInfo/refereeApplyMatch  ???????????????????????????
    @POST("xlMatchInfo/refereeApplyMatch")
    Observable<ResponseBean<RefereeApplyMatchBean>> refereeApplyMatch(@Body RequestBody body);

    //xlMatchInfo/refereeApplyToMatch ???????????????
    @POST("xlMatchInfo/refereeApplyToMatch")
    Observable<ResponseBean> refereeApplyToMatch(@Body RequestBody body);

    //xlMatchInfo/checkUserClunRole   ???????????????????????????????????????????????????????????????????????????????????????
    @POST("xlMatchInfo/checkUserClunRole")
    Observable<ResponseBean<CheckUserClunRoleBean>> checkUserClunRole(@Body RequestBody body);


    //xlMatchInfo/checkNowApply   ?????????????????????????????????????????????????????????????????????????????????
    @POST("xlMatchInfo/checkNowApply")
    Observable<ResponseBean> checkNowApply(@Body RequestBody body);

    //xlMatchInfo/judgeUserShiFouChongFuDeleteAll   ??????????????????????????????
    @POST("xlMatchInfo/judgeUserShiFouChongFuDeleteAll")
    Observable<ResponseBean> judgeUserShiFouChongFuDeleteAll(@Body RequestBody body);


    // xlMatchInfo/chooseJoinMatchUser  (????????????)??????????????????(??????)
    @POST("xlMatchInfo/chooseJoinMatchUser")
    Observable<ResponseBean<ChooseJoinMatchUserBean>> chooseJoinMatchUser(@Body RequestBody body);

    //xlMatchInfo/chooseJoinMatchPlayer  (????????????)?????????????????????????????????
    @POST("xlMatchInfo/chooseJoinMatchPlayer")
    Observable<ResponseBean<ChooseJoinMatchUserBean>> chooseJoinMatchPlayer(@Body RequestBody body);


    //xlMatchInfo/queryMatchApplyInfo  ??????????????????????????????????????????(???????????????????????????????????????)
    @POST("xlMatchInfo/queryMatchApplyInfo")
    Observable<ResponseBean<QueryMatchApplyInfoBean>> queryMatchApplyInfo(@Body RequestBody body);


    //xlMatchInfo/clubApplyMatch  ??????????????????????????????????????????
    @POST("xlMatchInfo/clubApplyMatch")
    Observable<ResponseBean> clubApplyMatch(@Body RequestBody body);


    //???????????????????????????????????????  xlEnrollMatch/patEnrollMatch
    @POST("xlEnrollMatch/patEnrollMatch")
    Observable<ResponseBean> patEnrollMatch(@Body RequestBody body);

    //????????????  xlUserInfo/updatePassword
    @POST("xlUserInfo/updatePassword")
    Observable<ResponseBean> updatePasswordFind(@Body RequestBody body);

    //????????????
    @POST("xlUserInfo/updatePassword")
    Observable<ResponseBean> updatePasswordSetting(@Body RequestBody body);


    //???????????????????????????????????????  xlEnrollMatch/patEnrollMatch
    @POST("xlEnrollMatch/patEnrollMatch")
    Observable<ResponseBean<WXPayBean>> patWxEnrollMatch(@Body RequestBody body);


    @POST("xlMatchInfo/clubApplyMatch")
    Observable<ResponseBean<WXPayBean>> clubWxApplyMatch(@Body RequestBody body);

    //-------------------------------------------------------------??????
    //xlMatchInfo/searchMatchTitle  ????????????????????????
    @POST("xlMatchInfo/getXlMatchInfoList")
    Observable<ResponseBean<SearchMathListBean>> searchMatchTitle(@Body RequestBody body);


    //xlMatchInfo/gameMatch ????????????
    @POST("xlMatchInfo/gameMatch")
    Observable<ResponseBean<GameMatchBean>> gameMatch(@Body RequestBody body);

    //xlMatchInfo/gameMatch ??????????????????
    @POST("xlMatchInfo/applyMatch")
    Observable<ResponseBean> applyMatch(@Body RequestBody body);

    //xlMatchInfo/joinClub   ???????????? (????????????????????????)
    @POST("xlMatchInfo/joinClub")
    Observable<ResponseBean> joinClub(@Body RequestBody body);

    //xlMatchInfo/refereeMacth   ???????????? (?????????)
    @POST("xlMatchInfo/refereeMacth")
    Observable<ResponseBean> refereeMacth(@Body RequestBody body);

    //xlMatchInfo/personOnClub   ???????????????????????????
    @POST("xlMatchInfo/personOnClub")
    Observable<ResponseBean<PersonalClubBean>> personOnClub(@Body RequestBody body);

    //xlMatchInfo/personOnClub  ?????????????????????(????????????)
    @POST("xlMatchInfo/getClub")
    Observable<ResponseBean> getClub(@Body RequestBody body);

}
