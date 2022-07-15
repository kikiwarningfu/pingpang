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

    //php加密
    @POST("matchOperation/phpAes")
    Observable<ResponseBean> phpAes(@Body RequestBody body);


    //用户后台登录
    @POST("app/appScanCode")
    Observable<ResponseBean> appScanCode(@Body RequestBody body);

    //对阵列表
    @POST("xlMatchInfo/getMatchAgainstData")
    Observable<ResponseBean<List<MatchAgainstDataBean>>> getMatchAgainstData(@Body RequestBody body);

    //弃权
    @POST("waiver/waiverOperation")
    Observable<ResponseBean> waiverOperation(@Body RequestBody body);

    //直播   直播初始化，设置付款方式及价位
    @GET("live/match/init")
    Observable<ResponseBean<LiveMatchInitBean>> liveMatchInit();

    //根据比赛ID确认是否可以发布悬赏
    @GET("live/match/status/{matchArrangeId}")
//get请求直接拼"/"加参数
    Observable<ResponseBean<LiveMatchStatusBean>> liveMatchStatus(@Path("matchArrangeId") String userId);


    //比赛详情，发布悬赏时展示用
    @GET("live/match/detail/{matchArrangeId}")
//get请求直接拼"/"加参数
    Observable<ResponseBean<LiveMatchDetailBean>> liveMatchDetail(@Path("matchArrangeId") String userId);

    //比赛详情，发布悬赏时展示用
    @GET("live/match/detail/{matchArrangeId}")
//get请求直接拼"/"加参数
    Observable<ResponseBean<AliPlayVideoBean>> liveAliPlayVideo(@Path("matchArrangeId") String userId);

    //每个赛事的悬赏榜单列表
    @GET("live/match/publish/list/{matchId}")
//get请求直接拼"/"加参数
    Observable<ResponseBean<LiveMatchPublishListBean>> liveMatchPublishList(@Path("matchId") String matchId);

    //live/match/publish/detail/{requestId}  已经发布悬赏的订单详情
    @GET("live/match/publish/detail/{requestId}")
    Observable<ResponseBean<LiveMatchDetailBean>> liveMatchPublishDetail(@Path("requestId") String requestId);

    //live/match/accept/{requestId} 接受悬赏
    @GET("live/match/accept/{requestId}")
    Observable<ResponseBean> liveMatchAccept(@Path("requestId") String requestId);

    //查询订单是否支付成功
    @GET("live/match/checkOrder/{orderId}")
//get请求直接拼"/"加参数
    Observable<ResponseBean> liveMatchCheckOrder(@Path("orderId") String orderId);


    //发布悬赏单
    @POST("live/match/publish")
    Observable<ResponseBean<LiveMatchByBean>> liveMatchPublish(@Body RequestBody body);

    //评价详情
    @GET("live/user/appraise/{requestId}")
    Observable<ResponseBean<LiveUserAppraiseBean>> liveUserAppraise(@Path("requestId") String userId);


    //    live/match/list
    //获取直播或录播的列表
    @POST("live/match/list")
    Observable<ResponseBean<LiveListBean>> liveMatchList(@Body RequestBody body);


    //用户填写提现的账户信息
    @POST("live/user/account")
    Observable<ResponseBean> liveUserAccount(@Body RequestBody body);

    //live/user/appraise   悬赏人对直播做评价
    @POST("live/user/appraise")
    Observable<ResponseBean> liveUserAppraise(@Body RequestBody body);


    //获取用户接单人的相关信息
    @GET("live/user/info")
    Observable<ResponseBean<LiveUserInfoBean>> liveUserInfo();


    //用户申请成为接单人
    @POST("live/user/apply")
    Observable<ResponseBean> liveUserApply(@Body RequestBody body);


    //悬赏人对直播申请退款
    @POST("live/user/reFund")
    Observable<ResponseBean> liveUserRefund(@Body RequestBody body);

    //退款详情
    @GET("live/user/reFundDetail/{requestId}")
    Observable<ResponseBean<LiveUserRefundDetailBean>> liveUserRefundDetail(@Path("requestId") String userId);


    //查看我的接单  live/user/order/receive
    @POST("live/user/order/receive")
    Observable<ResponseBean<LiveUserOrderListBean>> liveUserOrderReceive(@Body RequestBody body);

    //查看我的发布  live/user/order/publish
    @POST("live/user/order/publish")
    Observable<ResponseBean<LiveUserOrderListBean>> liveUserOrderPublish(@Body RequestBody body);


    //在我的里面的订单列表中支付
    @GET("live/match/rePay/{orderId}")
    Observable<ResponseBean<LiveMatchByBean>> liveMatchRepay(@Path("orderId") String orderId);

    //观看直播或录像付费  还么调用
    @POST("live/match/buy")
    Observable<ResponseBean<LiveMatchByBean>> liveMatchBuy(@Body RequestBody body);


    //根据ID获取直播或录播地址
    @GET("live/match/url/{requestId}")
    Observable<ResponseBean<LiveMatchUrlBean>> liveMatchUrl(@Path("requestId") String requestId);

    //提现详情
    @GET("live/user/withdrawal/detail/{requestId}")
    Observable<ResponseBean<LiveUserWithDrawalDetailBean>> liveUserWithDrawalDetail(@Path("requestId") String requestId);


    //用户申请提现
    @GET("live/user/withdrawal/{requestId}")
    Observable<ResponseBean> liveUserWithDrawl(@Path("requestId") String requestId);


    //接单用户放弃悬赏
    @GET("live/user/order/receive/cancle/{requestId}")
    Observable<ResponseBean> liveUserOrderCancel(@Path("requestId") String userId);

    //发布悬赏的用户取消悬赏
    @GET("live/user/order/publish/cancle/{requestId}")
    Observable<ResponseBean> liveUserOrderPublish(@Path("requestId") String requestId);

    //删除我发布的悬赏（其实是隐藏）
    @GET("live/user/order/publish/delete/{requestId}")
    Observable<ResponseBean> liveUserOrderPublishDelete(@Path("requestId") String requestId);

    //删除我的接单（其实是隐藏）
    @GET("live/user/order/receive/delete/{requestId}")
    Observable<ResponseBean> liveUserOrderReceiveDelete(@Path("requestId") String requestId);


    //-----------------------------------------------App内

    //首页近期比赛
    @POST("xlMatchInfo/theNearFuture")
    Observable<ResponseBean<List<NearFetureBean>>> theNearFuture(@Body RequestBody body);

    //首页我的比赛
    @POST("xlMatchInfo/myJoinMatch")
    Observable<ResponseBean<NearFetureBean>> myJoinMatch(@Body RequestBody body);

    //xlMatchInfo/version 查询版本号
    @POST("xlMatchInfo/version")
    Observable<ResponseBean<UpdateBean>> version(@Body RequestBody body);


    //蓝条数据
    @POST("xlMatchInfo/blueData")
    Observable<ResponseBean<List<XlMatchInfoBlueDataBean>>> blueData(@Body RequestBody body);

    //登录
    @POST("xlUserInfo/loginAuthentication")
    Observable<ResponseBean<LoginBean>> loginAuthentication(@Body RequestBody body);

    //第三方登录绑定手机号接口
    @POST("xlUserInfo/bindPhoneNum")
    Observable<ResponseBean<LoginBean>> bindPhoneNum(@Body RequestBody body);

    //俱乐部列表
    @POST("xlClubInfo/getXlClubInfoList")
    Observable<ResponseBean<XlClubInfoListBean>> getXlClubInfoList(@Body RequestBody body);

    //俱乐部新增
    @POST("xlClubInfo/createXlClubInfo")
    Observable<ResponseBean> createXlClubInfo(@Body RequestBody body);

    //俱乐部编辑
    @POST("xlClubInfo/updateXlClubInfo")
    Observable<ResponseBean> updateXlClubInfo(@Body RequestBody body);

    //俱乐部详情
    @POST("xlClubInfo/getXlClubInfo")
    Observable<ResponseBean<XlClubInfoBean>> getXlClubInfo(@Body RequestBody body);

    //俱乐部人员列表
    @POST("xlClubMember/getXlClubMemberList")
    Observable<ResponseBean<XlClubMemberListBean>> getXlClubMemberList(@Body RequestBody body);

    //俱乐部人员详情
    @POST("xlClubMember/getXlClubMember")
    Observable<ResponseBean<XlClubMemberBean>> getXlClubMember(@Body RequestBody body);

    //解散俱乐部   自由团体可以解散
    @POST("xlClubInfo/deleteXlClubInfo")
    Observable<ResponseBean<LoginBean>> deleteXlClubInfo(@Body RequestBody body);

    //俱乐部类型
    @POST("xlClubInfo/getClubTypeData")
    Observable<ResponseBean<List<ClubTypeDataBean>>> getClubTypeData(@Body RequestBody body);

    //俱乐部踢人
    @POST("xlClubMember/deleteXlClubMember")
    Observable<ResponseBean> deleteXlClubMember(@Body RequestBody body);

    //退出俱乐部
    @POST("xlClubMember/quitClub")
    Observable<ResponseBean> quitClub(@Body RequestBody body);

    //审核加入俱乐部申请
    @POST("xlClubMember/approvalClubApply")
    Observable<ResponseBean> approvalClubApply(@Body RequestBody body);

    //变更俱乐部人员身份
    @POST("xlClubMember/updateMemberInfo")
    Observable<ResponseBean> updateMemberInfo(@Body RequestBody body);

    //申请加入俱乐部
    @POST("xlClubMember/askForJoinClub")
    Observable<ResponseBean> askForJoinClub(@Body RequestBody body);

    //xlUserInfo/matchExperience 运动员比赛经历
    @POST("xlUserInfo/matchExperience")
    Observable<ResponseBean<MatchExperienceBean>> matchExperience(@Body RequestBody body);

    //xlClubContestResult/calculationInfo   计算详情页计算接口
    @POST("xlClubContestResult/calculationInfo")
    Observable<ResponseBean<CalculationInfoBean>> calculationInfo(@Body RequestBody body);

    //xlClubContestResult/reckonInfoData  计算详情页面 积分详情接口
    @POST("xlClubContestResult/reckonInfoData")
    Observable<ResponseBean<List<ReckonInfoDataBean>>> reckonInfoData(@Body RequestBody body);

    //matchOperation/fillInMatchList 教练员主客队抽签页面
    @POST("matchOperation/fillInMatchList")
    Observable<ResponseBean<FillInMatchListBean>> fillInMatchList(@Body RequestBody body);

    //matchOperation/fillInMatchBaseInfo  填写对阵名单基础数据
    @POST("matchOperation/fillInMatchBaseInfo")
    Observable<ResponseBean<FillInMatchBaseInfoBean>> fillInMatchBaseInfo(@Body RequestBody body);

    //matchOperation/getTeamArangePlay  获取运动员
    @POST("matchOperation/getTeamArangePlay")
    Observable<ResponseBean<List<TeamArrangePlayBean>>> getTeamArangePlay(@Body RequestBody body);

    //matchOperation/submitTeamArrange  提交对阵名单
    @POST("matchOperation/submitTeamArrange")
    Observable<ResponseBean> submitTeamArrange(@Body RequestBody body);

    //判断是否有未完成操作的页面  matchOperation/judgeUnCompletePage
    @POST("matchOperation/judgeUnCompletePage")
    Observable<ResponseBean<JudgeUnCompletePageBean>> judgeUnCompletePage(@Body RequestBody body);

    //裁判员所在球台  matchOperation/refereeInTableNum
    @POST("matchOperation/refereeInTableNum")
    Observable<ResponseBean<List<String>>> refereeInTableNum(@Body RequestBody body);

    //裁判员球台上的编排排序  matchOperation/sortTableNumArrange
    @POST("matchOperation/sortTableNumArrange")
    Observable<ResponseBean> sortTableNumArrange(@Body RequestBody body);

    //正式赛事抽签  matchOperation/arrangeDraw
    @POST("matchOperation/arrangeDraw")
    Observable<ResponseBean<ArrangeDrawDataBean>> arrangeDraw(@Body RequestBody body);

    //发送消息
    @POST("matchOperation/pushMessageToCoachAndroid")
    Observable<ResponseBean> pushMessageToCoachAndroid(@Body RequestBody body);

    //队内对决抽签（先后顺序）
    @POST("matchOperation/chooseTeamFirst")
    Observable<ResponseBean<ChooseTeamFirstBean>> chooseTeamFirst(@Body RequestBody body);


    //裁判员开始比赛
    @POST("matchOperation/beginArrange")
    Observable<ResponseBean<BeginArrangeBean>> beginArrange(@Body RequestBody body);

    //matchOperation/judgeRefereeUpdateScore  判断裁判员是否可以修改比分
    @POST("matchOperation/judgeRefereeUpdateScore")
    Observable<ResponseBean> judgeRefereeUpdateScore(@Body RequestBody body);

    //matchOperation/coachSuspend   裁判员暂停
    @POST("matchOperation/coachSuspend")
    Observable<ResponseBean> coachSuspend(@Body RequestBody body);

    //matchOperation/waiverChess  弃权
    @POST("matchOperation/waiverChess")
    Observable<ResponseBean> waiverChess(@Body RequestBody body);

    //matchOperation/endArrange  裁判员点结束比赛
    @POST("matchOperation/endArrange")
    Observable<ResponseBean> endArrange(@Body RequestBody body);

    //matchOperation/endArrange  裁判员修改比分
    @POST("matchOperation/updateChessScore")
    Observable<ResponseBean> updateChessScore(@Body RequestBody body);


    //matchOperation/getLoginInentify  判断是否为裁判长身份
    @POST("matchOperation/getLoginInentify")
    Observable<LoginIdentifyBean> getLoginInentify(@Body RequestBody body);


    //判断当前登陆人是否为此俱乐部负责人
    @POST("xlClubMember/queryNowUserIsCharge")
    Observable<ResponseBean<QueryNowUserIsChargeBean>> queryNowUserIsCharge(@Body RequestBody body);

    //结束正式赛事团体赛成绩
    @POST("matchOperation/endTeamArrange")
    Observable<ResponseBean<QueryNowUserIsChargeBean>> endTeamArrange(@Body RequestBody body);


    //获取名次
    @POST("TaoTaiSaiDaohang/getSecondNavigation")
    Observable<ResponseBean<SecondNavigationFourBean>> getSecondNavigation(@Body RequestBody body);

    /**
     * 七牛云上传图片获取凭证
     */
    @POST("upload/getUploadToken")
    Observable<ResponseBean> getUploadToken(@QueryMap HashMap<String, String> params);

    /**
     * 运动员列表
     */
    @POST("xlUserInfo/getXlUserInfoList")
    Observable<ResponseBean<XlUserInfoListBean>> getXlUserInfoList(@Body RequestBody body);


    /**
     * 运动员详情
     */
    @POST("xlUserInfo/getXlUserInfo")
    Observable<ResponseBean<XlUserInfoBean>> getXlUserInfo(@Body RequestBody body);


    //校验用户是否有权限创建赛事  xlMatchInfo/checkUserPower
    @POST("xlMatchInfo/checkUserPower")
    Observable<ResponseBean> checkUserPower(@Body RequestBody body);

    //赛事详情表格（单循环） xlMatchInfo/getMatchArrangeData
    @POST("xlMatchInfo/getMatchArrangeData")
    Observable<ResponseBean<MatchArrangeRoundBean>> getMatchArrangeData(@Body RequestBody body);

    //xlMatchInfo/queryPointsDetails   积分详情列表
    @POST("xlMatchInfo/queryPointsDetails")
    Observable<ResponseBean<QueryPointDetailBean>> queryPointsDetails(@Body RequestBody body);

    //xlMatchInfo/teamMemberByMatch   队伍成员页面
    @POST("xlMatchInfo/teamMemberByMatch")
    Observable<ResponseBean<TeamMemberByMatchBean>> teamMemberByMatch(@Body RequestBody body);


    //xlMatchInfo/tableNumArrange  该赛事下某球台下所有编排
    @POST("xlMatchInfo/tableNumArrange")
    Observable<ResponseBean<TableNumArrangeBean>> tableNumArrange(@Body RequestBody body);

    //xlMatchInfo/wodeMatchs 该赛事下某球台下所有编排
    @POST("xlMatchInfo/wodeMatchs")
    Observable<ResponseBean<List<MyGameListBean>>> wodeMatchs(@Body RequestBody body);

    //xlMatchReferee/queryTable  球台
    @POST("xlMatchReferee/queryTable")
    Observable<ResponseBean<List<String>>> queryTable(@Body RequestBody body);

    //xlMatchReferee/tuningStation  调台
    @POST("xlMatchReferee/tuningStation")
    Observable<ResponseBean> tuningStation(@Body RequestBody body);

    //xlMatchInfo/dateLimit  调台
    @POST("xlMatchInfo/dateLimit")
    Observable<ResponseBean<DateLimitBean>> dateLimit(@Body RequestBody body);


    //xlMatchInfo/arrangeChess  编排下对局或者团队编排下的具体编排
    @POST("xlMatchInfo/arrangeChess")
    Observable<ResponseBean<XlMatchInfoArrangeChessBean>> arrangeChess(@Body RequestBody body);


    //判断此人是否参加比赛  xlMatchInfo/judgeJoinMatchStatus
    @POST("xlMatchInfo/judgeJoinMatchStatus")
    Observable<ResponseBean<JudgeJoinMatchStatusBean>> judgeJoinMatchStatus(@Body RequestBody body);


    //赛事详情表格（淘汰赛） xlMatchInfo/getMatchArrangeData
    @POST("xlMatchInfo/getMatchArrangeData")
    Observable<ResponseBean<MatchArrangeKnockOutMatchBean>> getMatchArrangeKnockOutData(@Body RequestBody body);

    //赛事详情表格（出组名单） xlMatchInfo/getMatchArrangeData
    @POST("xlMatchInfo/getMatchArrangeData")
    Observable<ResponseBean<MatchArrangeGroupListBean>> getMatchGroupList(@Body RequestBody body);

    //赛事详情表格（成绩单） xlMatchInfo/getMatchArrangeData
    @POST("xlMatchInfo/getMatchArrangeData")
    Observable<ResponseBean<MatchArrangeMatchScoreBean>> getMatchScoreList(@Body RequestBody body);


    //校验用户是否有权限创建赛事  xlMatchInfo/getMatchScreen
    @POST("xlMatchInfo/getMatchScreen")
    Observable<ResponseBean<MatchScreenBean>> getMatchScreen(@Body RequestBody body);

    //xlMatchInfo/getMatchScreenFormat  赛事详情（筛选条件：赛制）
    @POST("xlMatchInfo/getMatchScreenFormat")
    Observable<ResponseBean<MatchScreenFormatBean>> getMatchScreenFormat(@Body RequestBody body);


    //xlUserInfo/judgeMyInfo  判断我的个人信息是否完善
    @POST("xlUserInfo/judgeMyInfo")
    Observable<ResponseBean> judgeMyInfo(@Body RequestBody body);


    //xlMatchInfo/queryComputeInfo  积分详情 计算详情
    @POST("xlMatchInfo/queryComputeInfo")
    Observable<ResponseBean<QueryComputeInfoBean>> queryComputeInfo(@Body RequestBody body);


    //创建比赛  createXlMatchInfo
    @POST("xlMatchInfo/createXlMatchInfo")
    Observable<ResponseBean> createXlMatchInfo(@Body RequestBody body);


    //对内俱乐部表赛选人列表  xlClubContestInfo/chooseMemberData
    @POST("xlClubContestInfo/chooseMemberData")
    Observable<ResponseBean<List<ChooseMemberDataBean>>> chooseMemberData(@Body RequestBody body);

    //xlClubContestInfo/createXlClubContestInfo  创建队内比赛
    @POST("xlClubContestInfo/createXlClubContestInfo")
    Observable<ResponseBean> createXlClubContestInfo(@Body RequestBody body);

    //xlClubContestTeam/getXlClubContestTeam   获取主客队页面基础数据
    @POST("xlClubContestTeam/getXlClubContestTeam")
    Observable<ResponseBean<List<XlClubContestTeamBean>>> getXlClubContestTeam(@Body RequestBody body);

    //队内团队比赛抽主客队  xlClubContestTeam/updateXlClubContestTeam
    @POST("xlClubContestTeam/updateXlClubContestTeam")
    Observable<ResponseBean<UpdateXlClubContestTeamBean>> updateXlClubContestTeam(@Body RequestBody body);

    //队内团队比赛安排列表  xlClubContestArrange/arrangeTeamContest
    @POST("xlClubContestArrange/arrangeTeamContest")
    Observable<ResponseBean<List<ArrangeTeamContestBean>>> arrangeTeamContest(@Body RequestBody body);

    //编辑队内团队比赛安排列表  xlClubContestArrange/updateXlClubContestArrange
    @POST("xlClubContestArrange/updateXlClubContestArrange")
    Observable<ResponseBean> updateXlClubContestArrange(@Body RequestBody body);

    //队内比赛下队伍人员  xlContestTeamMember/getClubContestTeamMember
    @POST("xlContestTeamMember/getClubContestTeamMember")
    Observable<ResponseBean<List<getClubContestTeamNumberBean>>> getClubContestTeamMember(@Body RequestBody body);

    //获取队内比赛队伍   xlClubContestArrange/getClubContestTeamData
    @POST("xlClubContestArrange/getClubContestTeamData")
    Observable<ResponseBean<List<ClubContestTeamBean>>> getClubContestTeamData(@Body RequestBody body);

    //获取赛事的场次统计相关信息
    @POST("xlClubContestInfo/clubContestStatistics")
    Observable<ResponseBean<ClubContestStatisticsBean>> getClubContestStatistics(@Body RequestBody body);

    //团队比赛安排   xlClubContestArrange/getClubContestTeamArrange

    @POST("xlClubContestArrange/getClubContestTeamArrange")
    Observable<ResponseBean<ClubContestTeamArrangeBean>> getClubContestTeamArrange(@Body RequestBody body);


    //单打编排数据  xlClubContestArrange/getSingleHitData
    @POST("xlClubContestArrange/getSingleHitData")
    Observable<ResponseBean<SingleHitDataBean>> getSingleHitData(@Body RequestBody body);

    //xlClubContestArrangeChess/queryChessInfo  查询对局详情,先创建对局根据赛事的赛事方法
    @POST("xlClubContestArrangeChess/queryChessInfo")
    Observable<ResponseBean<QueryChessInfoBean>> queryChessInfo(@Body RequestBody body);

    //xlClubContestArrangeChess/waiverContest   结束对局比赛
    @POST("xlClubContestArrangeChess/waiverContest")
    Observable<ResponseBean> waiverContest(@Body RequestBody body);

    //xlClubContestArrangeImg/createXlClubContestArrangeImg  结束对局比赛时上传图片
    @POST("xlClubContestArrangeImg/createXlClubContestArrangeImg")
    Observable<ResponseBean> uploadContestImg(@Body RequestBody body);

    //xlClubContestArrangeChess/updateXlClubContestArrangeChess   修改比分
    @POST("xlClubContestArrangeChess/updateXlClubContestArrangeChess")
    Observable<ResponseBean> updateXlClubContestArrangeChess(@Body RequestBody body);

    //xlClubContestArrangeChess/suspendWaiver  弃权，暂停接口
    @POST("xlClubContestArrangeChess/suspendWaiver")
    Observable<ResponseBean> suspendWaiver(@Body RequestBody body);

    //xlClubContestInfo/updateXlClubContestInfo   结束队内比赛
    @POST("xlClubContestInfo/updateXlClubContestInfo")
    Observable<ResponseBean> updateXlClubContestInfo(@Body RequestBody body);

    //xlClubContestInfo/updateXlClubContestInfo   单打比赛详情
    @POST("xlClubContestInfo/getXlClubContestInfo")
    Observable<ResponseBean<XlClubContestInfoBean>> getXlClubContestInfo(@Body RequestBody body);

    //xlClubContestInfo/getXlClubContestInfoList   队内赛事列表
    @POST("xlClubContestInfo/getXlClubContestInfoList")
    Observable<ResponseBean<XlClubContestInfoListBean>> getXlClubContestInfoList(@Body RequestBody body);

    //我的俱乐部(赛事选择承办俱乐部调用)   xlClubInfo/myClubList
    @POST("xlClubInfo/myClubList")
    Observable<ResponseBean<List<SelectClubListBean>>> myClubList(@Body RequestBody body);


    //判断是否有进行的队内比赛  xlClubContestInfo/judgeClubContestWithStatus
    @POST("xlClubContestInfo/judgeClubContestWithStatus")
    Observable<ResponseBean<JudgeClubContestWithStatusBean>> judgeClubContestWithStatus(@Body RequestBody body);


    //xlUserInfo/updateAccount   换绑接口
    @POST("xlUserInfo/updateAccount")
    Observable<ResponseBean> updateAccount(@Body RequestBody body);


    //xlUserInfo/getCode    发送验证码接口
    @POST("xlUserInfo/getCode")
    Observable<ResponseBean> getCode(@Body RequestBody body);


    //xlUserInfo/goThrough  制裁经历
    @POST("xlUserInfo/goThrough")
    Observable<ResponseBean<List<ThroughListBean>>> goThrough(@Body RequestBody body);

    //xlUserInfo/getAllMyInfo  我的个人信息详情
    @POST("xlUserInfo/getAllMyInfo")
    Observable<ResponseBean<AllMyInfoBean>> getAllMyInfo(@Body RequestBody body);


    //-----------------------------------------------------------------------------------------我的界面的接口
    //xlUserInfo/updateXlUserInfo  完善个人信息
    @POST("xlUserInfo/updateXlUserInfo")
    Observable<ResponseBean> updateXlUserInfo(@Body RequestBody body);

    //localhost:9580/xlEquipmentBrand/getXlEquipmentBrandList   品牌信息获取
    @POST("backstage/getXlEquipmentBrandList")
    Observable<ResponseBean<XlEquipmentBrandListBean>> getXlEquipmentBrandList(@Body RequestBody body);

    //localhost:9580/xlEquipmentModel/getModelDataByBrandId  某品牌下型号
    @POST("backstage/getModelDataByBrandId")
    Observable<ResponseBean<List<ModelDataBrandIdBean>>> getModelDataByBrandId(@Body RequestBody body);


    //xlUserInfo/getMyClub   我的俱乐部
    @POST("xlUserInfo/getMyClub")
    Observable<ResponseBean<MyClubListBean>> getMyClub(@Body RequestBody body);


    //xlUserInfo/verifyMaterial   实名认证
    @POST("xlUserInfo/verifyMaterial")
    Observable<ResponseBean> verifyMaterial(@Body RequestBody body);


    //xlUserInfo/getMyMatch  我的赛事
    @POST("xlUserInfo/getMyMatch")
    Observable<ResponseBean<MyMatchBean>> getMyMatch(@Body RequestBody body);


    //xlUserInfo/getIdentityInfo   实名认证详情
    @POST("xlUserInfo/getIdentityInfo")
    Observable<ResponseBean<IdentifyBean>> getIdentityInfo(@Body RequestBody body);

    //xlMatchInfo/getCoachMatchDta  教练员查看我的比赛
    @POST("xlMatchInfo/getCoachMatchDta")
    Observable<ResponseBean<List<CoachMatchDtaBean>>> getCoachMatchDta(@Body RequestBody body);

    //xlMessageInfo/getXlMessageInfoList  消息列表
    @POST("xlMessageInfo/getXlMessageInfoList")
    Observable<ResponseBean<MessageInfoListBean>> getXlMessageInfoList(@Body RequestBody body);

    //xlMessageInfo/updateXlMessageInfo   置顶消息，删除消息
    @POST("xlMessageInfo/updateXlMessageInfo")
    Observable<ResponseBean> updateXlMessageInfo(@Body RequestBody body);

    //xlMessageInfo/getTitleList    类别下消息列表
    @POST("xlMessageInfo/getTitleList")
    Observable<ResponseBean<TitleListBean>> getTitleList(@Body RequestBody body);

    //xlUserInfo/myBaseInfo   我的页面基础数据
    @POST("xlUserInfo/myBaseInfo")
    Observable<ResponseBean<MyBaseInfoBean>> myBaseInfo(@Body RequestBody body);

    //xlUserInfo/myEnlist   我的报名
    @POST("xlUserInfo/myEnlist")
    Observable<ResponseBean<EnListBean>> myEnlist(@Body RequestBody body);

    //xlEnrollMatch/deleteXlEnrollMatch  删除订单
    @POST("xlEnrollMatch/deleteXlEnrollMatch")
    Observable<ResponseBean> deleteXlEnrollMatch(@Body RequestBody body);

    //xlMatchReferee/refereerConsoleSort  裁判长页面筛选条件
    @POST("xlMatchReferee/refereerConsoleSort")
    Observable<ResponseBean<RefreerConsoleSortBean>> refereerConsoleSort(@Body RequestBody body);

    //xlMatchReferee/refereerConsole  裁判长控制台页面表格
    @POST("xlMatchReferee/refereerConsole")
    Observable<ResponseBean<List<RefereeConsoleBean>>> refereerConsole(@Body RequestBody body);

    //xlMatchReferee/refereeChooseData  可选择的裁判员数据
    @POST("xlMatchReferee/refereeChooseData")
    Observable<ResponseBean<List<RefereeChooseDataBean>>> refereeChooseData(@Body RequestBody body);

    //xlMatchReferee/updateReferee  更新裁判员数据
    @POST("xlMatchReferee/updateReferee")
    Observable<ResponseBean> updateReferee(@Body RequestBody body);

    //xlMatchReferee/deleteReferee  删除裁判
    @POST("xlMatchReferee/deleteReferee")
    Observable<ResponseBean> deleteReferee(@Body RequestBody body);

    //xlMatchReferee/startMatch  开始比赛
    @POST("xlMatchReferee/startMatch")
    Observable<ResponseBean> startMatch(@Body RequestBody body);

    //xlMatchInfo/judgeUserShiFouChongFu  查询是否有重复的人
    @POST("xlMatchInfo/judgeUserShiFouChongFu")
    Observable<ResponseBean> judgeUserShiFouChongFu(@Body RequestBody body);

    //xlMatchInfo/judgeUserShiFouChongFuDelete  查询是否有重复的人
    @POST("xlMatchInfo/judgeUserShiFouChongFuDelete")
    Observable<ResponseBean> judgeUserShiFouChongFuDelete(@Body RequestBody body);

    //xlMatchInfo/judgeUserJoinMatch  查询是否重复报名比赛
    @POST("xlMatchInfo/judgeUserJoinMatch")
    Observable<ResponseBean> judgeUserJoinMatch(@Body RequestBody body);

    //waiver/RegistrationDetails  报名详情
    @POST("waiver/RegistrationDetails")
    Observable<ResponseBean<List<RegistrationBean>>> RegistrationDetails(@Body RequestBody body);

    //appOperationLog/getOrderBook  获取秩序册
    @POST("appOperationLog/getOrderBook")
    Observable<ResponseBean<OrderBookBean>> getOrderBook(@Body RequestBody body);


    //xlMatchReferee/getData  查询教练员是否已提交对阵单
    @POST("matchOperation/querySubmit")
    Observable<ResponseBean<QuerySubmitBean>> querySubmit(@Body RequestBody body);

    //matchOperation/confirmScore  确认比赛成绩
    @POST("matchOperation/confirmScore")
    Observable<ResponseBean> confirmScore(@Body RequestBody body);


    //matchOperation/scoringReferee  给裁判员打分
    @POST("matchOperation/scoringReferee")
    Observable<ResponseBean> scoringReferee(@Body RequestBody body);

    //matchOperation/joinBymessage 消息进来填写对阵表
    @POST("matchOperation/joinBymessage")
    Observable<ResponseBean<MatchBaseInfoBean>> joinBymessage(@Body RequestBody body);

    //xlEnrollMatch/signOutMatchOrder  我要退赛
    @POST("xlEnrollMatch/signOutMatchOrder")
    Observable<ResponseBean> signOutMatchOrder(@Body RequestBody body);

    //xlEnrollMatch/matchOrderInfo  报名订单详情
    @POST("xlEnrollMatch/matchOrderInfo")
    Observable<ResponseBean<MatchOrderInfoBean>> matchOrderInfo(@Body RequestBody body);

    //xlEnrollMatch/matchOrderInfo  报名订单详情  个人
    @POST("xlEnrollMatch/matchOrderInfo")
    Observable<ResponseBean<MatchPersonalOrderInfoBean>> matchPersonalOrderInfo(@Body RequestBody body);

    //xlUserInfo/refereeInfo  裁判员详情
    @POST("xlUserInfo/refereeInfo")
    Observable<ResponseBean<RefereeInfoBean>> refereeInfo(@Body RequestBody body);

    //xlRoleInfo/getCoachRoleList   获取裁判员角色  固定传 1
    @POST("xlRoleInfo/getCoachRoleList")
    Observable<ResponseBean<List<CoashRoleListBean>>> getCoachRoleList(@Body RequestBody body);


    //申请，更新裁判员信息   xlUserInfo/updateRefereeInfo
    @POST("xlUserInfo/updateRefereeInfo")
    Observable<ResponseBean> updateRefereeInfo(@Body RequestBody body);

    //xlUserInfo/myAchievement   我的比赛  //获奖成绩
    @POST("xlUserInfo/myAchievement")
    Observable<ResponseBean<List<MyAchievementBean>>> myAchievement(@Body RequestBody body);


    //xlUserInfo/myAchievement   我的比赛  所有成绩
    @POST("xlUserInfo/myAchievement")
    Observable<ResponseBean<AllAchievementBean>> myAllAchievement(@Body RequestBody body);


    //xlUserInfo/awardsMatchList  获奖成绩,所有成绩的比赛列表
    @POST("xlUserInfo/awardsMatchList")
    Observable<ResponseBean<List<AwardsMathListBean>>> awardsMatchList(@Body RequestBody body);

    //xlMatchInfo/registrantsMacth  个人报名（保存报名）
    @POST("xlMatchInfo/registrantsMacth")
    Observable<ResponseBean> registrantsMacth(@Body RequestBody body);

    //xlMatchInfo/registrantsMacth  个人报名（保存报名）
    @POST("xlMatchInfo/registrantsMacth")
    Observable<ResponseBean<WXPayBean>> regWxistrantsMacth(@Body RequestBody body);

    //xlMatchInfo/registrantsMacth  个人报名（保存报名）
    @POST("xlMatchInfo/registrantsMacth")
    Observable<ResponseBean> regZfbistrantsMacth(@Body RequestBody body);

    //matchOperation/getOrderId  获取订单的id
    @POST("matchOperation/getOrderId")
    Observable<OrderIdBean> getOrderId(@Body RequestBody body);

    //xlMatchInfo/competitionItem  个人报名（比赛项目）
    @POST("xlMatchInfo/competitionItem")
    Observable<ResponseBean<List<CompetitionItemBean>>> competitionItem(@Body RequestBody body);

    //xlMatchInfo/refereeApplyMatch  判断裁判员能否报名
    @POST("xlMatchInfo/refereeApplyMatch")
    Observable<ResponseBean<RefereeApplyMatchBean>> refereeApplyMatch(@Body RequestBody body);

    //xlMatchInfo/refereeApplyToMatch 裁判员报名
    @POST("xlMatchInfo/refereeApplyToMatch")
    Observable<ResponseBean> refereeApplyToMatch(@Body RequestBody body);

    //xlMatchInfo/checkUserClunRole   查询此人是否有非自由团体绝了不负责人，教练员身份，实名认证
    @POST("xlMatchInfo/checkUserClunRole")
    Observable<ResponseBean<CheckUserClunRoleBean>> checkUserClunRole(@Body RequestBody body);


    //xlMatchInfo/checkNowApply   判断此场比赛，此俱乐部是否有报过名或者是否有人正在操作
    @POST("xlMatchInfo/checkNowApply")
    Observable<ResponseBean> checkNowApply(@Body RequestBody body);

    //xlMatchInfo/judgeUserShiFouChongFuDeleteAll   删除重复报名人数限制
    @POST("xlMatchInfo/judgeUserShiFouChongFuDeleteAll")
    Observable<ResponseBean> judgeUserShiFouChongFuDeleteAll(@Body RequestBody body);


    // xlMatchInfo/chooseJoinMatchUser  (第三通道)选择参赛人员(教练)
    @POST("xlMatchInfo/chooseJoinMatchUser")
    Observable<ResponseBean<ChooseJoinMatchUserBean>> chooseJoinMatchUser(@Body RequestBody body);

    //xlMatchInfo/chooseJoinMatchPlayer  (第三通道)选择参赛人员（运动员）
    @POST("xlMatchInfo/chooseJoinMatchPlayer")
    Observable<ResponseBean<ChooseJoinMatchUserBean>> chooseJoinMatchPlayer(@Body RequestBody body);


    //xlMatchInfo/queryMatchApplyInfo  查询比赛名称，报名收费属性等(赛事基础信息，提交报名页面)
    @POST("xlMatchInfo/queryMatchApplyInfo")
    Observable<ResponseBean<QueryMatchApplyInfoBean>> queryMatchApplyInfo(@Body RequestBody body);


    //xlMatchInfo/clubApplyMatch  俱乐部报名保存（或支付提交）
    @POST("xlMatchInfo/clubApplyMatch")
    Observable<ResponseBean> clubApplyMatch(@Body RequestBody body);


    //（我的报名）未支付报名支付  xlEnrollMatch/patEnrollMatch
    @POST("xlEnrollMatch/patEnrollMatch")
    Observable<ResponseBean> patEnrollMatch(@Body RequestBody body);

    //找回密码  xlUserInfo/updatePassword
    @POST("xlUserInfo/updatePassword")
    Observable<ResponseBean> updatePasswordFind(@Body RequestBody body);

    //设置密码
    @POST("xlUserInfo/updatePassword")
    Observable<ResponseBean> updatePasswordSetting(@Body RequestBody body);


    //（我的报名）未支付报名支付  xlEnrollMatch/patEnrollMatch
    @POST("xlEnrollMatch/patEnrollMatch")
    Observable<ResponseBean<WXPayBean>> patWxEnrollMatch(@Body RequestBody body);


    @POST("xlMatchInfo/clubApplyMatch")
    Observable<ResponseBean<WXPayBean>> clubWxApplyMatch(@Body RequestBody body);

    //-------------------------------------------------------------赛事
    //xlMatchInfo/searchMatchTitle  赛事名称模糊搜索
    @POST("xlMatchInfo/getXlMatchInfoList")
    Observable<ResponseBean<SearchMathListBean>> searchMatchTitle(@Body RequestBody body);


    //xlMatchInfo/gameMatch 赛事详情
    @POST("xlMatchInfo/gameMatch")
    Observable<ResponseBean<GameMatchBean>> gameMatch(@Body RequestBody body);

    //xlMatchInfo/gameMatch 查询我要报名
    @POST("xlMatchInfo/applyMatch")
    Observable<ResponseBean> applyMatch(@Body RequestBody body);

    //xlMatchInfo/joinClub   个人报名 (判断是否有俱乐部)
    @POST("xlMatchInfo/joinClub")
    Observable<ResponseBean> joinClub(@Body RequestBody body);

    //xlMatchInfo/refereeMacth   个人报名 (裁判员)
    @POST("xlMatchInfo/refereeMacth")
    Observable<ResponseBean> refereeMacth(@Body RequestBody body);

    //xlMatchInfo/personOnClub   个人代表俱乐部报名
    @POST("xlMatchInfo/personOnClub")
    Observable<ResponseBean<PersonalClubBean>> personOnClub(@Body RequestBody body);

    //xlMatchInfo/personOnClub  个人代表俱乐部(提交报名)
    @POST("xlMatchInfo/getClub")
    Observable<ResponseBean> getClub(@Body RequestBody body);

}
