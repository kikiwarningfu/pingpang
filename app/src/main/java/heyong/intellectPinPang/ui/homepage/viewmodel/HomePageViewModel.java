package heyong.intellectPinPang.ui.homepage.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import heyong.handong.framework.api.AdApi;
import heyong.handong.framework.api.Api;
import heyong.handong.framework.api.LiveApi;
import heyong.handong.framework.api.OtherHoutaiApi;
import heyong.handong.framework.base.BaseViewModel;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.bean.SearchRefreeListBean;
import heyong.intellectPinPang.bean.homepage.AppAdvertisementBean;
import heyong.intellectPinPang.bean.competition.MatchAgainstDataBean;
import heyong.intellectPinPang.bean.competition.MyGameListBean;
import heyong.intellectPinPang.bean.live.AliPlayVideoBean;
import heyong.intellectPinPang.bean.competition.QueryNowUserIsChargeBean;
import heyong.intellectPinPang.bean.competition.SecondNavigationFourBean;
import heyong.intellectPinPang.bean.competition.ArrangeDrawDataBean;
import heyong.intellectPinPang.bean.competition.BeginArrangeBean;
import heyong.intellectPinPang.bean.competition.ChooseTeamFirstBean;
import heyong.intellectPinPang.bean.competition.DateLimitBean;
import heyong.intellectPinPang.bean.competition.JudgeJoinMatchStatusBean;
import heyong.intellectPinPang.bean.competition.JudgeUnCompletePageBean;
import heyong.intellectPinPang.bean.competition.MatchArrangeGroupListBean;
import heyong.intellectPinPang.bean.competition.MatchArrangeKnockOutMatchBean;
import heyong.intellectPinPang.bean.competition.MatchArrangeMatchScoreBean;
import heyong.intellectPinPang.bean.competition.MatchArrangeRoundBean;
import heyong.intellectPinPang.bean.competition.MatchBaseInfoBean;
import heyong.intellectPinPang.bean.competition.MatchScreenBean;
import heyong.intellectPinPang.bean.competition.MatchScreenFormatBean;
import heyong.intellectPinPang.bean.competition.NearFetureBean;
import heyong.intellectPinPang.bean.competition.QueryComputeInfoBean;
import heyong.intellectPinPang.bean.competition.QueryPointDetailBean;
import heyong.intellectPinPang.bean.competition.QuerySubmitBean;
import heyong.intellectPinPang.bean.competition.TableNumArrangeBean;
import heyong.intellectPinPang.bean.competition.TeamMemberByMatchBean;
import heyong.intellectPinPang.bean.competition.XlMatchInfoArrangeChessBean;
import heyong.intellectPinPang.bean.competition.XlMatchInfoBlueDataBean;
import heyong.intellectPinPang.bean.gsonbean.PostBean;
import heyong.intellectPinPang.bean.homepage.UpdateBean;
import heyong.intellectPinPang.bean.live.LiveMatchByBean;
import heyong.intellectPinPang.bean.live.LiveMatchDetailBean;
import heyong.intellectPinPang.bean.live.LiveMatchInitBean;
import heyong.intellectPinPang.bean.live.LiveMatchStatusBean;
import heyong.intellectPinPang.bean.live.LiveMatchUrlBean;
import heyong.intellectPinPang.model.RequestService;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HomePageViewModel extends BaseViewModel {
    public MutableLiveData<ResponseBean> checkUserPowerLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<QueryPointDetailBean>> queryPointsDetailsLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<JudgeJoinMatchStatusBean>> judgeMatchStatusLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<TeamMemberByMatchBean>> teamByMatchLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<MatchArrangeRoundBean>> getMatchArrangeRoundLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<List<MatchAgainstDataBean>>> getMatchAgainstDataLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<MatchArrangeKnockOutMatchBean>> getMatchKnockOutMatchLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<MatchArrangeGroupListBean>> getMatchGroupListLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<MatchArrangeMatchScoreBean>> getMatchScoreLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<MatchScreenBean>> getMatchScreenLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<MatchScreenFormatBean>> getMatchScreenFormatLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> judgeMyInfoLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<QueryComputeInfoBean>> queryComputeInfoLiveData = new MutableLiveData<>();

    public MutableLiveData<ResponseBean<List<MyGameListBean>>> wodeMatchsLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<TableNumArrangeBean>> tableNumArrangeLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<TableNumArrangeBean>> tableNumArrangeChiefLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<XlMatchInfoArrangeChessBean>> arrangeChessLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<List<String>>> queryTableLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> tuningStationLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<DateLimitBean>> dateLimitLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<JudgeUnCompletePageBean>> judgeUnCompletePageLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<List<String>>> refereeInTableNumLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> sortTableNumArrangeLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<ArrangeDrawDataBean>> arrangeDrawLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> sendMessageLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<BeginArrangeBean>> beginArrangeLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> judgeRefereeUpdateScoreLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<ChooseTeamFirstBean>> chooseTeamFirstLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<QuerySubmitBean>> querySubmitLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<List<NearFetureBean>>> theNearFutureLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<NearFetureBean>> myJoinMatchLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<UpdateBean>> updateLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<List<XlMatchInfoBlueDataBean>>> blueDataLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveMatchInitBean>> liveMatchInitLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveMatchStatusBean>> liveMatchStatusLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveMatchDetailBean>> liveMatchDetailLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<AliPlayVideoBean>> aliplayVideoLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> liveMatchCheckOrderLiveData = new MutableLiveData<>();


    public MutableLiveData<ResponseBean<LiveMatchByBean>> liveMatchBuyLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveMatchByBean>> liveMatchPublishLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<LiveMatchUrlBean>> liveMatchUrlData = new MutableLiveData<>();

    public MutableLiveData<ResponseBean<QueryNowUserIsChargeBean>> queryNowUserIsChargeLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> endTeamArrangeLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> appScanCodeLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<List<AppAdvertisementBean>>> appAdvertisementData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> phpAesLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<SearchRefreeListBean>> searchTableNumArrangeLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> waiverOperationLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<SecondNavigationFourBean>> getSecondNavigation4LiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<SecondNavigationFourBean>> getSecondTaoTaiLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> endArrangeLiveData = new MutableLiveData<>();


    public void searchTableNumArrange(String name,String matchId,String itemType,String dateTime,String pageNo,String pageSize) {
        PostBean postBean = new PostBean();
        postBean.setName(""+name);
        postBean.setMatchId(""+matchId);
        postBean.setItemType(""+itemType);
        postBean.setDateTime(""+dateTime);
        postBean.setPageNo("" + pageNo);
        postBean.setPageSize("" + pageSize);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).searchTableNumArrange(requestBody).subscribe
                (new SimpleObserver<ResponseBean<SearchRefreeListBean>>() {
            @Override
            public void onSuccess(ResponseBean<SearchRefreeListBean> responseBean) {
                searchTableNumArrangeLiveData.postValue(responseBean);
            }
        });
    }


    //php加密商城  用来获取商城的加密串  来获取商城的加密地址
    public void phpAes() {
        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).phpAes(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                phpAesLiveData.postValue(responseBean);

            }
        });
    }

    public void appAdvertisement() {
        PostBean postBean = new PostBean();
        postBean.setDisplayPositionId("" + 1);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AdApi.getApiService(RequestService.class).appAdvertisement(requestBody).subscribe(new SimpleObserver<ResponseBean<List<AppAdvertisementBean>>>() {
            @Override
            public void onSuccess(ResponseBean<List<AppAdvertisementBean>> responseBean) {
                appAdvertisementData.postValue(responseBean);

            }
        });
    }


//    getMatchAgainstData

    //赛事正式结束
    public void appScanCode(String code) {
        PostBean postBean = new PostBean();
        postBean.setCode("" + code);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OtherHoutaiApi.getApiService(RequestService.class).appScanCode(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                appScanCodeLiveData.postValue(responseBean);

            }
        });
    }


    //matchOperation/endArrange  裁判员点结束比赛
    public void endArrange(String id, int leftWinCount, int rightWinCount, int leftOrRight) {
        PostBean postBean = new PostBean();
        postBean.setId("" + id);
        postBean.setLeftWinCount("" + leftWinCount);
        postBean.setRightWinCount("" + rightWinCount);
        postBean.setLeftOrRight("" + leftOrRight);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).endArrange(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                endArrangeLiveData.postValue(responseBean);

            }
        });
    }

    //弃权新接口
    public void waiverOperation(String arrangeId, String itemType, String waiverStatus) {
        PostBean postBean = new PostBean();
        postBean.setArrangeId("" + arrangeId);
        postBean.setItemType("" + itemType);
        postBean.setWaiverStatus("" + waiverStatus);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).waiverOperation(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                waiverOperationLiveData.postValue(responseBean);
            }
        });
    }

    //赛事正式结束
    public void endTeamArrange(String arrangeId, int leftWinCount, int rightWinCount, int leftOrRight) {
        PostBean postBean = new PostBean();
        postBean.setId("" + arrangeId);
        postBean.setLeftWinCount("" + leftWinCount);
        postBean.setRightWinCount("" + rightWinCount);
        postBean.setLeftOrRight("" + leftOrRight);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).endTeamArrange(requestBody).subscribe(new SimpleObserver<ResponseBean<QueryNowUserIsChargeBean>>() {
            @Override
            public void onSuccess(ResponseBean<QueryNowUserIsChargeBean> responseBean) {
                endTeamArrangeLiveData.postValue(responseBean);

            }
        });
    }


    //判断当前登陆人是否为此俱乐部负责人
    public void queryNowUserIsCharge(String clubId) {
        PostBean postBean = new PostBean();
        postBean.setClubId("" + clubId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).queryNowUserIsCharge(requestBody).subscribe(new SimpleObserver<ResponseBean<QueryNowUserIsChargeBean>>() {
            @Override
            public void onSuccess(ResponseBean<QueryNowUserIsChargeBean> responseBean) {
                queryNowUserIsChargeLiveData.postValue(responseBean);

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


    //观看直播或录像付费
    public void liveMatchPublish(String money, String matchArrangeId, String payType, String type) {
        PostBean postBean = new PostBean();
        postBean.setMoney("" + money);
        postBean.setMatchArrangeId("" + matchArrangeId);
        postBean.setPayType("" + payType);
        postBean.setType("" + type);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).liveMatchPublish(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<LiveMatchByBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<LiveMatchByBean> responseBean) {
                        liveMatchPublishLiveData.postValue(responseBean);
                    }
                });
    }


    //观看直播或录像付费
    public void liveMatchBuy(String money, String matchArrangeId, String payType) {
        PostBean postBean = new PostBean();
        postBean.setMoney("" + money);
        postBean.setMatchArrangeId("" + matchArrangeId);
        postBean.setPayType("" + payType);
        //        postBean.setType("" + type);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).liveMatchBuy(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<LiveMatchByBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<LiveMatchByBean> responseBean) {
                        liveMatchBuyLiveData.postValue(responseBean);
                    }
                });
    }

    //比赛详情，发布悬赏时展示用
    public void listAliPlayVideo(String matchArrangeId) {
        LiveApi.getApiService(RequestService.class).liveAliPlayVideo(matchArrangeId)
                .subscribe(new SimpleObserver<ResponseBean<AliPlayVideoBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<AliPlayVideoBean> responseBean) {
                        aliplayVideoLiveData.postValue(responseBean);
                    }
                });
    }

    //比赛详情，发布悬赏时展示用
    public void liveMatchDetail(String matchArrangeId) {
        LiveApi.getApiService(RequestService.class).liveMatchDetail(matchArrangeId)
                .subscribe(new SimpleObserver<ResponseBean<LiveMatchDetailBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<LiveMatchDetailBean> responseBean) {
                        liveMatchDetailLiveData.postValue(responseBean);
                    }
                });
    }

    //根据比赛id 确认是否可以发布悬赏
    public void liveMatchStatus(String matchArrangeId) {
        LiveApi.getApiService(RequestService.class).liveMatchStatus(matchArrangeId)
                .subscribe(new SimpleObserver<ResponseBean<LiveMatchStatusBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<LiveMatchStatusBean> responseBean) {
                        liveMatchStatusLiveData.postValue(responseBean);
                    }
                });
    }


    //直播初始化
    public void liveMatchInit() {
        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LiveApi.getApiService(RequestService.class).liveMatchInit()
                .subscribe(new SimpleObserver<ResponseBean<LiveMatchInitBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<LiveMatchInitBean> responseBean) {
                        liveMatchInitLiveData.postValue(responseBean);
                    }
                });
    }

    //首页近期比赛
    public void theNearFuture() {
        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).theNearFuture(requestBody).subscribe(new SimpleObserver<ResponseBean<List<NearFetureBean>>>() {
            @Override
            public void onSuccess(ResponseBean<List<NearFetureBean>> responseBean) {
                theNearFutureLiveData.postValue(responseBean);
            }
        });
    }

    //首页我的比赛
    public void myJoinMatch() {
        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).myJoinMatch(requestBody).subscribe(new SimpleObserver<ResponseBean<NearFetureBean>>() {
            @Override
            public void onSuccess(ResponseBean<NearFetureBean> responseBean) {
                myJoinMatchLiveData.postValue(responseBean);

            }
        });
    }

    //版本更新
    public void version() {
        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).version(requestBody).subscribe(new SimpleObserver<ResponseBean<UpdateBean>>() {
            @Override
            public void onSuccess(ResponseBean<UpdateBean> responseBean) {
                updateLiveData.postValue(responseBean);

            }
        });
    }


    //首页我的比赛
    public void blueData() {
        PostBean postBean = new PostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).blueData(requestBody).subscribe(new SimpleObserver<ResponseBean<List<XlMatchInfoBlueDataBean>>>() {
            @Override
            public void onSuccess(ResponseBean<List<XlMatchInfoBlueDataBean>> responseBean) {
                blueDataLiveData.postValue(responseBean);

            }
        });
    }

    public MutableLiveData<ResponseBean<MatchBaseInfoBean>> joinBymessageLiveData = new MutableLiveData<>();

    //查询教练员是否已提交对阵单
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

    //matchOperation/confirmScore   确认比赛成绩
    public MutableLiveData<ResponseBean> confirmScoreLiveData = new MutableLiveData<>();

    //确认比赛成绩
    public void confirmScore(String id, String relationId) {
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
        Api.getApiService(RequestService.class).confirmScore(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                confirmScoreLiveData.postValue(responseBean);
            }
        });
    }

    //matchOperation/confirmScore   评分
    public MutableLiveData<ResponseBean> scoringRefereeLiveData = new MutableLiveData<>();

    //查询教练员是否已提交对阵单
    public void scoringReferee(String id, String relationId, String marks) {
        PostBean postBean = new PostBean();
        postBean.setId("" + id);
        postBean.setRelationId("" + relationId);
        postBean.setMarks("" + marks);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).scoringReferee(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                scoringRefereeLiveData.postValue(responseBean);
            }
        });
    }


    //查询教练员是否已提交对阵单
    public void querySubmit(String arrangeId, String left1Name, String right1Name) {
        PostBean postBean = new PostBean();
        postBean.setArrangeId("" + arrangeId);
        postBean.setLeft1Name("" + left1Name);
        postBean.setRight1Name("" + right1Name);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).querySubmit(requestBody).subscribe(new SimpleObserver<ResponseBean<QuerySubmitBean>>() {
            @Override
            public void onSuccess(ResponseBean<QuerySubmitBean> responseBean) {
                querySubmitLiveData.postValue(responseBean);
            }
        });
    }


    //队内对决抽签（先后顺序）
    public void chooseTeamFirst(String id, String player1, String player2, String player3, String player4) {
        PostBean postBean = new PostBean();
        postBean.setId("" + id);
        postBean.setPlayer1("" + player1);
        postBean.setPlayer2("" + player2);
        postBean.setPlayer3("" + player3);
        postBean.setPlayer4("" + player4);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).chooseTeamFirst(requestBody).subscribe(new SimpleObserver<ResponseBean<ChooseTeamFirstBean>>() {
            @Override
            public void onSuccess(ResponseBean<ChooseTeamFirstBean> responseBean) {
                chooseTeamFirstLiveData.postValue(responseBean);
            }
        });
    }


    //发送消息
    public void pushMessageToCoachAndroid(String id) {
        PostBean postBean = new PostBean();
        postBean.setArrangeId("" + id);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).pushMessageToCoachAndroid(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                sendMessageLiveData.postValue(responseBean);
            }
        });
    }

    //裁判员开始比赛
    public void beginArrange(String ids) {
        PostBean postBean = new PostBean();
        postBean.setId("" + ids);
        postBean.setCodeType("2");
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).beginArrange(requestBody).subscribe(new SimpleObserver<ResponseBean<BeginArrangeBean>>() {
            @Override
            public void onSuccess(ResponseBean<BeginArrangeBean> responseBean) {
                beginArrangeLiveData.postValue(responseBean);

            }
        });
    }
    //judgeRefereeUpdateScore  判断裁判员是否可以修改比分

    public void judgeRefereeUpdateScore(String arrangeId) {
        PostBean postBean = new PostBean();
        postBean.setArrangeId("" + arrangeId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).judgeRefereeUpdateScore(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                judgeRefereeUpdateScoreLiveData.postValue(responseBean);

            }
        });
    }


    //正式赛事抽签
    public void arrangeDraw(ArrangeDrawDataBean arrangeDrawBean) {

        String params = new Gson().toJson(arrangeDrawBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).arrangeDraw(requestBody).subscribe(new SimpleObserver<ResponseBean<ArrangeDrawDataBean>>() {
            @Override
            public void onSuccess(ResponseBean<ArrangeDrawDataBean> responseBean) {
                arrangeDrawLiveData.postValue(responseBean);
            }
        });
    }

    //裁判员球台上的编排排序
    public void sortTableNumArrange(String id, String site) {
        PostBean loginPostBean = new PostBean();
        loginPostBean.setId("" + id);
        loginPostBean.setSite(site);

        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).sortTableNumArrange(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                sortTableNumArrangeLiveData.postValue(responseBean);
            }
        });
    }


    //裁判员所在球台
    public void refereeInTableNum(String matchId) {
        PostBean loginPostBean = new PostBean();
        loginPostBean.setMatchId("" + matchId);
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).refereeInTableNum(requestBody).subscribe(new SimpleObserver<ResponseBean<List<String>>>() {
            @Override
            public void onSuccess(ResponseBean<List<String>> responseBean) {
                refereeInTableNumLiveData.postValue(responseBean);
            }
        });

    }


    /**
     * 判断是否有未完成操作的页面   "errorCode": "100000-100044",犹未完成页面
     */
    public void judgeUnCompletePage() {
        PostBean loginPostBean = new PostBean();
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).judgeUnCompletePage(requestBody).subscribe(new SimpleObserver<ResponseBean<JudgeUnCompletePageBean>>() {
            @Override
            public void onSuccess(ResponseBean<JudgeUnCompletePageBean> responseBean) {
                judgeUnCompletePageLiveData.postValue(responseBean);
            }
        });

    }


    /**
     * xlMatchInfo/dateLimit   限制时间
     */
    public void dateLimit(String ids) {
        PostBean loginPostBean = new PostBean();
        loginPostBean.setId("" + ids);
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).dateLimit(requestBody).subscribe(new SimpleObserver<ResponseBean<DateLimitBean>>() {
            @Override
            public void onSuccess(ResponseBean<DateLimitBean> responseBean) {
                dateLimitLiveData.postValue(responseBean);
            }
        });

    }


    /**
     * xlMatchReferee/tuningStation  调台
     */
    public void tuningStation(String matchId, String beginTime, String id, String tableNum) {
        PostBean loginPostBean = new PostBean();
        loginPostBean.setMatchId("" + matchId);
        loginPostBean.setBeginTime(beginTime);
        loginPostBean.setId("" + id);
        loginPostBean.setTableNum("" + tableNum);


        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).tuningStation(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                tuningStationLiveData.postValue(responseBean);
            }
        });
    }

    /**
     * xlMatchReferee/queryTable  球台
     */
    public void queryTable(String matchId) {
        PostBean loginPostBean = new PostBean();
        loginPostBean.setMatchId("" + matchId);
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).queryTable(requestBody).subscribe(new SimpleObserver<ResponseBean<List<String>>>() {
            @Override
            public void onSuccess(ResponseBean<List<String>> responseBean) {
                queryTableLiveData.postValue(responseBean);
            }
        });
    }

    /**
     * 该赛事下某球台下所有编排  xlMatchInfo/tableNumArrange
     */
    public void wodeMatchs(String id, String beginTime, String pageNo, String pageSize) {

        PostBean loginPostBean = new PostBean();

        if (TextUtils.isEmpty(beginTime)) {
            loginPostBean.setBeginTime(beginTime);
        } else {
            loginPostBean.setBeginTime(beginTime + " 00:00:00");
        }

        loginPostBean.setId("" + id);

        loginPostBean.setPageNo("" + pageNo);
        loginPostBean.setPageSize("" + pageSize);


        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).

                wodeMatchs(requestBody).

                subscribe(new SimpleObserver<ResponseBean<List<MyGameListBean>>>() {
                    @Override
                    public void onSuccess(ResponseBean<List<MyGameListBean>> responseBean) {
                        wodeMatchsLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * 该赛事下某球台下所有编排  xlMatchInfo/tableNumArrange
     */
    public void tableMyNumArrange(String tableNum, String matchId, String id, String beginTime, String status, String code, String pageNo, String pageSize) {

        PostBean loginPostBean = new PostBean();
        loginPostBean.setTableNum(tableNum);
        loginPostBean.setMatchId(matchId);
        if (code.equals("1")) {
            loginPostBean.setId(id);
            loginPostBean.setCode("" + code);
        } else {
            loginPostBean.setCode("" + 2);
        }
        if (TextUtils.isEmpty(beginTime)) {
            loginPostBean.setBeginTime(beginTime);
        } else {
            loginPostBean.setBeginTime(beginTime + " 00:00:00");
        }
        loginPostBean.setStatus(status);
        loginPostBean.setPageNo("" + pageNo);
        loginPostBean.setPageSize("" + pageSize);

        //        loginPostBean.setArrangeNum("" + 1);
        //        loginPostBean.setMatchId("" + "512317538144129040");
        //        loginPostBean.setId("1");
        //        loginPostBean.setBeginTime("");

        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).tableNumArrange(requestBody).subscribe(new SimpleObserver<ResponseBean<TableNumArrangeBean>>() {
            @Override
            public void onSuccess(ResponseBean<TableNumArrangeBean> responseBean) {
                tableNumArrangeLiveData.postValue(responseBean);
            }
        });
    }


    /**
     * 该赛事下某球台下所有编排  xlMatchInfo/tableNumArrange
     * code:1 编排表格跳转  2 其他页面调用
     * id  编排id code为1时需要穿  code2 不用传
     */
    public void tableNumArrangeChief(String tableNum, String matchId, String id, String beginTime, String status, String code, String pageNo, String pageSize) {

        PostBean loginPostBean = new PostBean();

        loginPostBean.setTableNum(tableNum);
        loginPostBean.setMatchId(matchId);
        //        loginPostBean.setId(id);
        if (code.equals("1")) {
            loginPostBean.setId(id);
            loginPostBean.setCode("" + code);
        } else {
            loginPostBean.setCode("" + 2);

        }
        if (TextUtils.isEmpty(beginTime)) {
            loginPostBean.setBeginTime(beginTime);

        } else {
            if (beginTime.contains("00:00:00")) {
                loginPostBean.setBeginTime(beginTime);
            } else {
                loginPostBean.setBeginTime(beginTime + " 00:00:00");

            }

        }
        loginPostBean.setStatus(status);

        loginPostBean.setPageNo("" + pageNo);
        loginPostBean.setPageSize("" + pageSize);
        //        loginPostBean.setArrangeNum("" + 1);
        //        loginPostBean.setMatchId("" + "512317538144129040");
        //        loginPostBean.setId("1");

        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).tableNumArrange(requestBody).subscribe(new SimpleObserver<ResponseBean<TableNumArrangeBean>>() {
            @Override
            public void onSuccess(ResponseBean<TableNumArrangeBean> responseBean) {
                tableNumArrangeChiefLiveData.postValue(responseBean);
            }
        });
    }

    /**
     * 编排下对局或者团队编排下的具体编排  xlMatchInfo/arrangeChess
     */
    public void arrangeChess(String id) {
        PostBean loginPostBean = new PostBean();
        //        loginPostBean.setId("" + 1);
        loginPostBean.setId("" + id);

        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).arrangeChess(requestBody).subscribe(new SimpleObserver<ResponseBean<XlMatchInfoArrangeChessBean>>() {
            @Override
            public void onSuccess(ResponseBean<XlMatchInfoArrangeChessBean> responseBean) {
                arrangeChessLiveData.postValue(responseBean);
            }
        });
    }


    /**
     * 积分详情列表
     */
    public void teamMemberByMatch(String id, String clubId, String matchId) {

        PostBean loginPostBean = new PostBean();
        loginPostBean.setId("" + id);
        loginPostBean.setClubId("" + clubId);
        loginPostBean.setMatchId(matchId);
        //        loginPostBean.setId("" + "526801420729864624");
        //        loginPostBean.setClubId("" + "512196047566049296");
        //        loginPostBean.setMatchId("" + "512317538144129040");
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).teamMemberByMatch(requestBody).subscribe(new SimpleObserver<ResponseBean<TeamMemberByMatchBean>>() {
            @Override
            public void onSuccess(ResponseBean<TeamMemberByMatchBean> responseBean) {
                teamByMatchLiveData.postValue(responseBean);
            }
        });
    }

    //获取成绩单名次 http://localhost:9590/TaoTaiSaiDaohang/getSecondNavigation
    public void getSecondGradeNavigation(String itemId) {
        PostBean postBean = new PostBean();
        //        postBean.setId("" + id);//成绩单不传id  其他 传 groupNum
        postBean.setItemId("" + itemId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getSecondNavigation(requestBody).subscribe(new SimpleObserver<ResponseBean<SecondNavigationFourBean>>() {
            @Override
            public void onSuccess(ResponseBean<SecondNavigationFourBean> responseBean) {
                getSecondNavigation4LiveData.postValue(responseBean);
            }
        });
    }

    //获取名次 http://localhost:9590/TaoTaiSaiDaohang/getSecondNavigation
    public void getSecondTaoTaiNavigation(String itemId, String id) {
        PostBean postBean = new PostBean();
        postBean.setId("" + id);//成绩单不传id  其他 传 groupNum
        postBean.setItemId("" + itemId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getSecondNavigation(requestBody).subscribe(new SimpleObserver<ResponseBean<SecondNavigationFourBean>>() {
            @Override
            public void onSuccess(ResponseBean<SecondNavigationFourBean> responseBean) {
                getSecondTaoTaiLiveData.postValue(responseBean);
            }
        });
    }

    /**
     * 赛事详情对阵列表
     */
    public void getMatchAgainstData(String matchId, String projectItemId, String groupNum, String matchFormat, String matchFormatName
            , String groupName) {

        PostBean loginPostBean = new PostBean();
        loginPostBean.setMatchId(matchId);
        loginPostBean.setProjectItemId(projectItemId);
        loginPostBean.setGroupNum(groupNum);
        //        loginPostBean.setGroupNum("");
        loginPostBean.setMatchFormat(matchFormat);
        loginPostBean.setGroupName(groupName);
        loginPostBean.setMatchFormatName("" + matchFormatName);


        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*"format": "1",1:循环赛（第一阶段），2:淘汰赛（第二阶段）,3出组名单4成绩单*/
        /*MatchArrangeKnockOutMatchBean  淘汰赛*/
        /*MatchArrangeRoundBean  单循环*/
        /*MatchArrangeGroupListBean 出组名单*/
        /* 成绩单 MatchArrangeMatchScoreBean*/

        Api.getApiService(RequestService.class).getMatchAgainstData(requestBody).subscribe(new SimpleObserver<ResponseBean<List<MatchAgainstDataBean>>>() {
            @Override
            public void onSuccess(ResponseBean<List<MatchAgainstDataBean>> responseBean) {
                getMatchAgainstDataLiveData.postValue(responseBean);
            }
        });

    }

    /**
     * 赛事详情表格（单循环）
     */
    public void getMatchTaoTaiArrangeData(String matchId, String projectItemId, String groupNum, String matchFormat, String matchFormatName
            , String groupName, String navigation) {

        PostBean loginPostBean = new PostBean();
        loginPostBean.setMatchId(matchId);
        loginPostBean.setProjectItemId(projectItemId);
        loginPostBean.setGroupNum(groupNum);
        //        loginPostBean.setGroupNum("");
        loginPostBean.setMatchFormat(matchFormat);
        loginPostBean.setGroupName(groupName);
        loginPostBean.setMatchFormatName("" + matchFormatName);
        if (TextUtils.isEmpty(navigation)) {
        } else {
            loginPostBean.setNavigation("" + navigation);
        }

        //        {  淘汰赛
        //    "matchId":"512317538144129040",
        //    "projectItemId":"512317538177683472",
        //    "groupNum":"1",
        //    "groupName":"",
        //    "matchFormatName":"",
        //    "matchFormat":"2"
        //        }
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*"format": "1",1:循环赛（第一阶段），2:淘汰赛（第二阶段）,3出组名单4成绩单*/
        /*MatchArrangeKnockOutMatchBean  淘汰赛*/
        /*MatchArrangeRoundBean  单循环*/
        /*MatchArrangeGroupListBean 出组名单*/
        /* 成绩单 MatchArrangeMatchScoreBean*/

        if (matchFormat.equals("1")) {
            Api.getApiService(RequestService.class).getMatchArrangeData(requestBody).subscribe(new SimpleObserver<ResponseBean<MatchArrangeRoundBean>>() {
                @Override
                public void onSuccess(ResponseBean<MatchArrangeRoundBean> responseBean) {
                    getMatchArrangeRoundLiveData.postValue(responseBean);
                }
            });
        } else if (matchFormat.equals("2")) {
            Api.getApiService(RequestService.class).getMatchArrangeKnockOutData(requestBody).subscribe(new SimpleObserver<ResponseBean<MatchArrangeKnockOutMatchBean>>() {
                @Override
                public void onSuccess(ResponseBean<MatchArrangeKnockOutMatchBean> responseBean) {
                    getMatchKnockOutMatchLiveData.postValue(responseBean);
                }
            });
        } else if (matchFormat.equals("3")) {
            Api.getApiService(RequestService.class).getMatchGroupList(requestBody).subscribe(new SimpleObserver<ResponseBean<MatchArrangeGroupListBean>>() {
                @Override
                public void onSuccess(ResponseBean<MatchArrangeGroupListBean> responseBean) {
                    getMatchGroupListLiveData.postValue(responseBean);
                }
            });
        } else if (matchFormat.equals("4")) {
            Api.getApiService(RequestService.class).getMatchScoreList(requestBody).subscribe(new SimpleObserver<ResponseBean<MatchArrangeMatchScoreBean>>() {
                @Override
                public void onSuccess(ResponseBean<MatchArrangeMatchScoreBean> responseBean) {
                    getMatchScoreLiveData.postValue(responseBean);
                }
            });
        }
    }

    /**
     * 赛事详情表格（单循环）
     */
    public void getMatchArrangeData(String matchId, String projectItemId, String groupNum, String matchFormat, String matchFormatName
            , String groupName) {

        PostBean loginPostBean = new PostBean();
        loginPostBean.setMatchId(matchId);
        loginPostBean.setProjectItemId(projectItemId);
        loginPostBean.setGroupNum(groupNum);
        //        loginPostBean.setGroupNum("");
        loginPostBean.setMatchFormat(matchFormat);
        loginPostBean.setGroupName(groupName);
        loginPostBean.setMatchFormatName("" + matchFormatName);

        //        {  淘汰赛
        //    "matchId":"512317538144129040",
        //    "projectItemId":"512317538177683472",
        //    "groupNum":"1",
        //    "groupName":"",
        //    "matchFormatName":"",
        //    "matchFormat":"2"
        //        }
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*"format": "1",1:循环赛（第一阶段），2:淘汰赛（第二阶段）,3出组名单4成绩单*/
        /*MatchArrangeKnockOutMatchBean  淘汰赛*/
        /*MatchArrangeRoundBean  单循环*/
        /*MatchArrangeGroupListBean 出组名单*/
        /* 成绩单 MatchArrangeMatchScoreBean*/

        if (matchFormat.equals("1")) {
            Api.getApiService(RequestService.class).getMatchArrangeData(requestBody).subscribe(new SimpleObserver<ResponseBean<MatchArrangeRoundBean>>() {
                @Override
                public void onSuccess(ResponseBean<MatchArrangeRoundBean> responseBean) {
                    getMatchArrangeRoundLiveData.postValue(responseBean);
                }
            });
        } else if (matchFormat.equals("2")) {
            Api.getApiService(RequestService.class).getMatchArrangeKnockOutData(requestBody).subscribe(new SimpleObserver<ResponseBean<MatchArrangeKnockOutMatchBean>>() {
                @Override
                public void onSuccess(ResponseBean<MatchArrangeKnockOutMatchBean> responseBean) {
                    getMatchKnockOutMatchLiveData.postValue(responseBean);
                }
            });
        } else if (matchFormat.equals("3")) {
            Api.getApiService(RequestService.class).getMatchGroupList(requestBody).subscribe(new SimpleObserver<ResponseBean<MatchArrangeGroupListBean>>() {
                @Override
                public void onSuccess(ResponseBean<MatchArrangeGroupListBean> responseBean) {
                    getMatchGroupListLiveData.postValue(responseBean);
                }
            });
        } else if (matchFormat.equals("4")) {
            Api.getApiService(RequestService.class).getMatchScoreList(requestBody).subscribe(new SimpleObserver<ResponseBean<MatchArrangeMatchScoreBean>>() {
                @Override
                public void onSuccess(ResponseBean<MatchArrangeMatchScoreBean> responseBean) {
                    getMatchScoreLiveData.postValue(responseBean);
                }
            });
        }

    }


    /**
     * 判断此人是否参加比赛
     */
    public void judgeJoinMatchStatus(String id) {

        PostBean loginPostBean = new PostBean();
        loginPostBean.setId("" + id);
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).judgeJoinMatchStatus(requestBody).subscribe(new SimpleObserver<ResponseBean<JudgeJoinMatchStatusBean>>() {
            @Override
            public void onSuccess(ResponseBean<JudgeJoinMatchStatusBean> responseBean) {
                judgeMatchStatusLiveData.postValue(responseBean);
            }
        });
    }


    /**
     * 积分详情列表
     */
    public void queryPointsDetails(String userId, String projectItemId, String groupNum, String integral) {

        PostBean loginPostBean = new PostBean();
        loginPostBean.setUserId("" + userId);
        loginPostBean.setProjectItemId(projectItemId);
        loginPostBean.setGroupNum(groupNum);
        loginPostBean.setIntegral(integral);


        //        loginPostBean.setUserId("" + "511486160083128336");
        //        loginPostBean.setProjectItemId("514038703090864144");
        //        loginPostBean.setGroupNum("");
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).queryPointsDetails(requestBody).subscribe(new SimpleObserver<ResponseBean<QueryPointDetailBean>>() {
            @Override
            public void onSuccess(ResponseBean<QueryPointDetailBean> responseBean) {
                queryPointsDetailsLiveData.postValue(responseBean);
            }
        });
    }


    /**
     * 校验用户是否有权限创建赛事
     */
    public void checkUserPower() {

        PostBean loginPostBean = new PostBean();
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).checkUserPower(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                checkUserPowerLiveData.postValue(responseBean);
            }
        });
    }

    /**
     * 比赛详情筛选条件（组别）
     */
    public void getMatchScreen(String id) {

        PostBean loginPostBean = new PostBean();
        loginPostBean.setId(id);
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getMatchScreen(requestBody).subscribe(new SimpleObserver<ResponseBean<MatchScreenBean>>() {
            @Override
            public void onSuccess(ResponseBean<MatchScreenBean> responseBean) {
                getMatchScreenLiveData.postValue(responseBean);
            }
        });
    }

    /**
     * 赛事详情（筛选条件：赛制
     */
    public void getMatchScreenFormat(String matchId, String projectItemId) {

        PostBean loginPostBean = new PostBean();
        loginPostBean.setMatchId(matchId);
        loginPostBean.setProjectItemId("" + projectItemId);
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getMatchScreenFormat(requestBody).subscribe(new SimpleObserver<ResponseBean<MatchScreenFormatBean>>() {
            @Override
            public void onSuccess(ResponseBean<MatchScreenFormatBean> responseBean) {
                getMatchScreenFormatLiveData.postValue(responseBean);
            }
        });
    }

    /**
     * 校验用户是否有权限创建赛事
     */
    public void judgeMyInfo() {

        PostBean loginPostBean = new PostBean();
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).judgeMyInfo(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                judgeMyInfoLiveData.postValue(responseBean);
            }
        });
    }


    /**
     * xlMatchInfo/queryComputeInfo
     */
    public void queryComputeInfo(String matchId, String projectItemId, String groupNum, String integral) {

        PostBean loginPostBean = new PostBean();
        //        loginPostBean.setMatchId("" + "512317538144129040");
        //        loginPostBean.setProjectItemId("" + "514038703090864144");
        //        loginPostBean.setGroupNum("" + groupNum);
        loginPostBean.setMatchId("" + matchId);
        loginPostBean.setProjectItemId("" + projectItemId);
        loginPostBean.setGroupNum("" + groupNum);
        loginPostBean.setIntegral("" + integral);

        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).queryComputeInfo(requestBody).subscribe(new SimpleObserver<ResponseBean<QueryComputeInfoBean>>() {
            @Override
            public void onSuccess(ResponseBean<QueryComputeInfoBean> responseBean) {
                queryComputeInfoLiveData.postValue(responseBean);
            }
        });
    }
}
