package heyong.intellectPinPang.ui.club.viewmodel;

import android.text.TextUtils;

import androidx.databinding.ObservableField;
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
import heyong.intellectPinPang.bean.competition.ClubContestStatisticsBean;
import heyong.intellectPinPang.bean.competition.LoginIdentifyBean;
import heyong.intellectPinPang.bean.competition.JudgeClubContestWithStatusBean;
import heyong.intellectPinPang.bean.competition.QueryNowUserIsChargeBean;
import heyong.intellectPinPang.bean.competition.BeginArrangeBean;
import heyong.intellectPinPang.bean.competition.FillInMatchBaseInfoBean;
import heyong.intellectPinPang.bean.competition.FillInMatchListBean;
import heyong.intellectPinPang.bean.competition.MatchBaseInfoBean;
import heyong.intellectPinPang.bean.competition.SubmitTeamArrangeBean;
import heyong.intellectPinPang.bean.competition.TeamArrangePlayBean;
import heyong.intellectPinPang.bean.competition.XlUserInfoBean;
import heyong.intellectPinPang.bean.competition.ArrangeTeamContestBean;
import heyong.intellectPinPang.bean.competition.CalculationInfoBean;
import heyong.intellectPinPang.bean.competition.ChooseMemberDataBean;
import heyong.intellectPinPang.bean.competition.ClubContestTeamArrangeBean;
import heyong.intellectPinPang.bean.competition.ClubContestTeamBean;
import heyong.intellectPinPang.bean.competition.ClubTypeDataBean;
import heyong.intellectPinPang.bean.competition.CreateXlClubContestInfoBean;
import heyong.intellectPinPang.bean.competition.LoginBean;
import heyong.intellectPinPang.bean.competition.MatchExperienceBean;
import heyong.intellectPinPang.bean.competition.QueryChessInfoBean;
import heyong.intellectPinPang.bean.competition.ReckonInfoDataBean;
import heyong.intellectPinPang.bean.competition.SingleHitDataBean;
import heyong.intellectPinPang.bean.competition.UpdateXlClubContestTeamBean;
import heyong.intellectPinPang.bean.competition.UpdatexlClubContestArrangeBean;
import heyong.intellectPinPang.bean.competition.XlClubContestInfoBean;
import heyong.intellectPinPang.bean.competition.XlClubContestInfoListBean;
import heyong.intellectPinPang.bean.competition.XlClubContestTeamBean;
import heyong.intellectPinPang.bean.competition.XlClubInfoBean;
import heyong.intellectPinPang.bean.competition.XlClubInfoListBean;
import heyong.intellectPinPang.bean.competition.XlClubMemberBean;
import heyong.intellectPinPang.bean.competition.XlClubMemberListBean;
import heyong.intellectPinPang.bean.competition.getClubContestTeamNumberBean;
import heyong.intellectPinPang.bean.gsonbean.PostBean;
import heyong.intellectPinPang.model.RequestService;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ClubViewModel extends BaseViewModel {

    public MutableLiveData<ResponseBean<LoginBean>> createXlClubInfoLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<XlClubMemberBean>> getXlClubMemberLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> deleteXlClubInfoLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> editXlClubInfoLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<XlClubInfoListBean>> getXlClubInfoListLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<List<ClubTypeDataBean>>> getClubTypeDataLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<XlClubInfoBean>> getXlClubInfoLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<XlClubMemberListBean>> getXlClubMemberListLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> deleteXlClubMemberListLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> quitClubLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> updateMemberInfoLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> getUploadTokenLiveData = new MutableLiveData<>();

    public MutableLiveData<ResponseBean<XlUserInfoBean>> getXlUserInfoLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<List<ChooseMemberDataBean>>> chooseMemberDataLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> createXlClubContestInfoLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<List<XlClubContestTeamBean>>> getXlClubContestTeamLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<UpdateXlClubContestTeamBean>> updateXlClubContestTeamLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<List<ArrangeTeamContestBean>>> arrangeTeamContestLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> updateXlClubContestArrangeLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<List<getClubContestTeamNumberBean>>> getClubContestTeamMemberLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<SingleHitDataBean>> getClubContestTeamDataLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<List<ClubContestTeamBean>>> getClubContestTeamLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<ClubContestTeamArrangeBean>> getClubContestTeamArrangeLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<QueryChessInfoBean>> queryChessInfoBeanLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> waiverContestLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> uploadContestImgLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> updateXlClubContestArrangeChessLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> suspendWaiverLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> updateXlClubContestInfoLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<XlClubContestInfoBean>> getXlClubContestInfoLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<XlClubContestInfoListBean>> getXlClubContestInfoListLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<JudgeClubContestWithStatusBean>> judgeClubContestWithStatusLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> askForJoinClubLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<MatchExperienceBean>> matchExperienceLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<CalculationInfoBean>> calculationInfoLiveData = new MutableLiveData<>();

    public ObservableField<String> localCity = new ObservableField<>();
    public ObservableField<String> localTeamTitle = new ObservableField<>();
    public MutableLiveData<ResponseBean<List<ReckonInfoDataBean>>> reckonInfoDataLiveData = new MutableLiveData<>();

    public MutableLiveData<ResponseBean<FillInMatchListBean>> fillInMatchListLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<FillInMatchBaseInfoBean>> fillInMatchBaseInfoLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<List<TeamArrangePlayBean>>> getTeamArangePlayLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> submitTeamArrangeLiveData = new MutableLiveData<>();

    public MutableLiveData<ResponseBean<BeginArrangeBean>> beginArrangeLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> coachSuspendLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> waiverChessLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> endArrangeLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> updateChessScoreLiveData = new MutableLiveData<>();
    public MutableLiveData<LoginIdentifyBean> getLoginInentifyLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<ClubContestStatisticsBean>> ClubContestStatisticsLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<QueryNowUserIsChargeBean>> queryNowUserIsChargeLiveData = new MutableLiveData<>();

    //??????????????????????????????
    public void getLoginInentify(String id) {
        PostBean postBean = new PostBean();
        postBean.setId("" + id);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getLoginInentify(requestBody).subscribe(new SimpleObserver<LoginIdentifyBean>() {
            @Override
            public void onSuccess(LoginIdentifyBean responseBean) {
                getLoginInentifyLiveData.postValue(responseBean);
            }
        });
    }

    //?????????????????????
    public void updateChessScore(String id, String leftIntegral, String rightIntegral) {
        PostBean postBean = new PostBean();
        postBean.setId("" + id);
        postBean.setLeftIntegral("" + leftIntegral);
        postBean.setRightIntegral("" + rightIntegral);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).updateChessScore(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                updateChessScoreLiveData.postValue(responseBean);

            }
        });
    }

    //???????????????????????????????????????????????????
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


    //matchOperation/endArrange  ????????????????????????
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

    //?????????????????????
    public void waiverChess(String id, int type) {
        PostBean postBean = new PostBean();
        postBean.setId("" + id);
        if (type == 1) {
            //??????
            postBean.setLeftWaiver("" + 1);
        } else {
            postBean.setRightWaiver("" + 1);
        }
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).waiverChess(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                waiverChessLiveData.postValue(responseBean);

            }
        });
    }


    //???????????????
    public void coachSuspend(String id, String direction) {
        PostBean postBean = new PostBean();
        postBean.setId("" + id);
        postBean.setDirection("" + direction);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).coachSuspend(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                coachSuspendLiveData.postValue(responseBean);

            }
        });
    }

    //?????????????????????
    public void beginArrange(String ids, String codeType) {
        PostBean postBean = new PostBean();
        postBean.setId("" + ids);
        if (!TextUtils.isEmpty(codeType) && codeType != null) {
            postBean.setCodeType("" + codeType);
        } else {
            postBean.setCodeType("2");
        }
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

    /**
     * ??????????????????
     */
    public void submitTeamArrange(SubmitTeamArrangeBean submitTeamArrangeBean) {
        String params = new Gson().toJson(submitTeamArrangeBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).submitTeamArrange(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        submitTeamArrangeLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ??????????????????????????????
     */
    public void getTeamArangePlay(String ownTeamId, String player1Set, String arrangeId) {
        PostBean postBean = new PostBean();
        postBean.setOwnTeamId(ownTeamId);
        postBean.setPlayer1Set(player1Set);
        postBean.setArrangeId("" + arrangeId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getTeamArangePlay(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<List<TeamArrangePlayBean>>>() {
                    @Override
                    public void onSuccess(ResponseBean<List<TeamArrangePlayBean>> responseBean) {
                        getTeamArangePlayLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ??????????????????????????????
     */
    public void fillInMatchBaseInfo(MatchBaseInfoBean matchBaseInfoBean) {
        String params = new Gson().toJson(matchBaseInfoBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).fillInMatchBaseInfo(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<FillInMatchBaseInfoBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<FillInMatchBaseInfoBean> responseBean) {
                        fillInMatchBaseInfoLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ??????????????????????????????
     */
    public void fillInMatchList(String id) {
        PostBean postBean = new PostBean();
        postBean.setId(id);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).fillInMatchList(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<FillInMatchListBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<FillInMatchListBean> responseBean) {
                        fillInMatchListLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ???????????????????????????
     */
    public void reckonInfoData(String joinId, String matchId) {
        PostBean postBean = new PostBean();
        postBean.setJoinId(joinId);
        postBean.setMatchId(matchId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).reckonInfoData(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<List<ReckonInfoDataBean>>>() {
                    @Override
                    public void onSuccess(ResponseBean<List<ReckonInfoDataBean>> responseBean) {
                        reckonInfoDataLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ???????????????????????????
     */
    public void calculationInfo(String teamId, String intergal) {
        PostBean postBean = new PostBean();
        postBean.setTeamId(teamId);
        postBean.setIntegral("" + intergal);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).calculationInfo(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<CalculationInfoBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<CalculationInfoBean> responseBean) {
                        calculationInfoLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ?????????????????????
     */
    public void matchExperience(String sportsId, String pageNo, String pageSize, int dayType) {
        PostBean postBean = new PostBean();
        postBean.setId(sportsId);
        postBean.setPageNo(pageNo);
        postBean.setPageSize(pageSize);
        if (dayType == -1) {
            postBean.setCondition("");
        } else {
            postBean.setCondition("" + dayType);
        }
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).matchExperience(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<MatchExperienceBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<MatchExperienceBean> responseBean) {
                        matchExperienceLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ?????????????????????
     */
    public void askForJoinClub(String clubId) {
        PostBean postBean = new PostBean();
        postBean.setId(clubId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).askForJoinClub(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        askForJoinClubLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ??????????????????
     */
    public void judgeClubContestWithStatus(String id) {
        PostBean postBean = new PostBean();
        postBean.setId(id);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).judgeClubContestWithStatus(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<JudgeClubContestWithStatusBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<JudgeClubContestWithStatusBean> responseBean) {
                        judgeClubContestWithStatusLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ??????????????????
     */
    public void getXlClubContestInfoList(String clubId, String pageNo, String pageSize) {
        PostBean postBean = new PostBean();
        postBean.setClubId(clubId);
        postBean.setPageNo(pageNo);
        postBean.setPageSize(pageSize);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getXlClubContestInfoList(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<XlClubContestInfoListBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<XlClubContestInfoListBean> responseBean) {
                        getXlClubContestInfoListLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ??????????????????
     */
    public void getXlClubContestInfo(String contestId, String teamLeftId) {
        PostBean postBean = new PostBean();
        postBean.setContestId(contestId);
        postBean.setTeamLeftId(teamLeftId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getXlClubContestInfo(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<XlClubContestInfoBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<XlClubContestInfoBean> responseBean) {
                        getXlClubContestInfoLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ??????????????????
     */
    public void updateXlClubContestInfo(String id, boolean force) {
        PostBean postBean = new PostBean();
        postBean.setId(id);
        postBean.setForce(force?"true":"false");
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).updateXlClubContestInfo(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        updateXlClubContestInfoLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ?????????????????????  xlClubContestArrangeChess/updateXlClubContestArrangeChess
     */
    public void suspendWaiver(String id, String leftWavier,
                              String rightWavier, String rightSuspend
            , String leftSuspend) {
        PostBean postBean = new PostBean();
        postBean.setId(id);
        postBean.setLeftWavier(leftWavier);
        postBean.setRightWavier(rightWavier);
        postBean.setRightSuspend(rightSuspend);
        postBean.setLeftSuspend(leftSuspend);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).suspendWaiver(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        suspendWaiverLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ????????????  xlClubContestArrangeChess/updateXlClubContestArrangeChess
     */
    public void updateXlClubContestArrangeChess(String id, String leftIntegral,
                                                String rightIntegral) {
        PostBean postBean = new PostBean();
        postBean.setId(id);
        postBean.setLeftIntegral(leftIntegral);
        postBean.setRightIntegral(rightIntegral);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).updateXlClubContestArrangeChess(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        updateXlClubContestArrangeChessLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ??????????????????
     */
    public void waiverContest(String id, String winId1, String winId2, String winTeamId,
                              String leftWinCount, String rightWinCount, String leftWavier, String rightWavier) {
        PostBean postBean = new PostBean();
        postBean.setId(id);
        postBean.setStatus("1");
        postBean.setWinId1(winId1);
        postBean.setWinId2(winId2);
        postBean.setLeftWavier("" + leftWavier);
        postBean.setRightWavier("" + rightWavier);
        postBean.setWinTeamId(winTeamId);
        postBean.setLeftWinCount(leftWinCount);
        postBean.setRightWinCount(rightWinCount);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).waiverContest(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        waiverContestLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ?????????????????????????????????
     */
    public void uploadContestImg(String contestArrangeId, String imgUrl) {
        PostBean postBean = new PostBean();
        postBean.setContestArrangeId(contestArrangeId);
        postBean.setImgUrl(imgUrl);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).uploadContestImg(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        uploadContestImgLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ??????????????????,??????????????????????????????????????????   clickType;//???????????? 1?????????????????????????????????2????????????????????????
     */
    public void queryChessInfo(String contestArrangeId, String clickType) {
        PostBean postBean = new PostBean();
        postBean.setContestArrangeId(contestArrangeId);
        postBean.setClickType(clickType);
//
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).queryChessInfo(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<QueryChessInfoBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<QueryChessInfoBean> responseBean) {
                        queryChessInfoBeanLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ??????????????????  ??????  ???5???
     */
    public void getClubContestTeamArrange(String contestId) {
        PostBean postBean = new PostBean();
        postBean.setContestId(contestId);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getClubContestTeamArrange(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<ClubContestTeamArrangeBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<ClubContestTeamArrangeBean> responseBean) {
                        getClubContestTeamArrangeLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ????????????????????????   danda   ???????????????
     */
    public void getClubContestTeam(String contestId) {
        PostBean postBean = new PostBean();
        postBean.setContestId(contestId);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getClubContestTeamData(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<List<ClubContestTeamBean>>>() {
                    @Override
                    public void onSuccess(ResponseBean<List<ClubContestTeamBean>> responseBean) {
                        getClubContestTeamLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ????????????id??????????????????????????????
     * @param contestId
     */
    public void getClubContestStatistics(String contestId, String clubId) {
        PostBean postBean = new PostBean();
        postBean.setId(contestId);
        postBean.setClubId(clubId);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getClubContestStatistics(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<ClubContestStatisticsBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<ClubContestStatisticsBean> responseBean) {
                        ClubContestStatisticsLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ????????????????????????   danda  ?????????????????????
     */
    public void getSingleHitData(String contestId, String teamleftId) {
        PostBean postBean = new PostBean();
        postBean.setContestId(contestId);
        postBean.setTeamLeftId(teamleftId);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getSingleHitData(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<SingleHitDataBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<SingleHitDataBean> responseBean) {
                        getClubContestTeamDataLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ???????????????????????????
     */
    public void getClubContestTeamMember(String contestId) {
        PostBean postBean = new PostBean();
        postBean.setContestTeamId(contestId);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getClubContestTeamMember(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<List<getClubContestTeamNumberBean>>>() {
                    @Override
                    public void onSuccess(ResponseBean<List<getClubContestTeamNumberBean>> responseBean) {
                        getClubContestTeamMemberLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ????????????????????????????????????
     */
    public void updateXlClubContestArrange(UpdatexlClubContestArrangeBean postBean) {


        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).updateXlClubContestArrange(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        updateXlClubContestArrangeLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ??????????????????????????????
     */
    public void arrangeTeamContest(String contestId) {
        PostBean postBean = new PostBean();
        postBean.setContestId(contestId);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).arrangeTeamContest(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<List<ArrangeTeamContestBean>>>() {
                    @Override
                    public void onSuccess(ResponseBean<List<ArrangeTeamContestBean>> responseBean) {
                        arrangeTeamContestLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ??????????????????????????????
     */
    public void updateXlClubContestTeam(String clubContestInfoId, String leftTeamId, String rightTeamId) {
        PostBean postBean = new PostBean();
        postBean.setClubContestInfoId(clubContestInfoId);
        postBean.setLeftTeamId(leftTeamId);
        postBean.setRightTeamId(rightTeamId);

        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).updateXlClubContestTeam(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<UpdateXlClubContestTeamBean>>() {
                    @Override
                    public void onSuccess(ResponseBean<UpdateXlClubContestTeamBean> responseBean) {
                        updateXlClubContestTeamLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ?????????????????????????????????
     */
    public void getXlClubContestTeam(String clubContestInfoId) {
        PostBean postBean = new PostBean();
        postBean.setClubContestInfoId(clubContestInfoId);
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getXlClubContestTeam(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<List<XlClubContestTeamBean>>>() {
                    @Override
                    public void onSuccess(ResponseBean<List<XlClubContestTeamBean>> responseBean) {
                        getXlClubContestTeamLiveData.postValue(responseBean);
                    }
                });
    }

    /**
     * ??????????????????
     */
    public void createXlClubContestInfo(CreateXlClubContestInfoBean bean) {
        String params = new Gson().toJson(bean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).createXlClubContestInfo(requestBody)
                .subscribe(new SimpleObserver<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        createXlClubContestInfoLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ?????????????????????????????????
     */
    public void chooseMemberData(String id) {

        PostBean postBean = new PostBean();
        if (!TextUtils.isEmpty(id)) {
            postBean.setId(id);
        }
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).chooseMemberData(requestBody)
                .subscribe(new SimpleObserver<ResponseBean<List<ChooseMemberDataBean>>>() {
                    @Override
                    public void onSuccess(ResponseBean<List<ChooseMemberDataBean>> responseBean) {
                        chooseMemberDataLiveData.postValue(responseBean);
                    }
                });
    }


    /**
     * ???????????????
     */
    public void getXlUserInfo(String id) {

        PostBean postBean = new PostBean();
        if (!TextUtils.isEmpty(id)) {
            postBean.setId(id);
        }
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getXlUserInfo(requestBody).subscribe(new SimpleObserver<ResponseBean<XlUserInfoBean>>() {
            @Override
            public void onSuccess(ResponseBean<XlUserInfoBean> responseBean) {
                getXlUserInfoLiveData.postValue(responseBean);
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
     * ???????????????
     */
    public void deleteXlClubMember(String id) {
        PostBean loginPostBean = new PostBean();
        if (!TextUtils.isEmpty(id)) {
            loginPostBean.setId(id);
        }

        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).deleteXlClubMember(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                deleteXlClubMemberListLiveData.postValue(responseBean);
            }
        });

    }

    /**
     * ???????????????
     */
    public void quitClub(String id) {
        PostBean postBean = new PostBean();
        if (!TextUtils.isEmpty(id)) {
            postBean.setId(id);
        }
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).quitClub(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                quitClubLiveData.postValue(responseBean);
            }
        });

    }

    /**
     * ???????????????????????????
     */
    public void updateMemberInfo(String id, String type) {
        PostBean postBean = new PostBean();
        if (!TextUtils.isEmpty(id)) {
            postBean.setId(id);
        }
        if (!TextUtils.isEmpty(type)) {
            postBean.setMemberType(type);
        }
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).updateMemberInfo(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                updateMemberInfoLiveData.postValue(responseBean);
            }
        });

    }

    /**
     * ?????????????????????
     */
    public void getXlClubMemberList(String clubId) {
        PostBean loginPostBean = new PostBean();
        if (!TextUtils.isEmpty(clubId)) {
            loginPostBean.setClubId(clubId);
        }

        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getXlClubMemberList(requestBody).subscribe(new SimpleObserver<ResponseBean<XlClubMemberListBean>>() {
            @Override
            public void onSuccess(ResponseBean<XlClubMemberListBean> responseBean) {
                getXlClubMemberListLiveData.postValue(responseBean);
            }
        });

    }

    /**
     * ???????????????
     */
    public void getXlClubInfo(String id) {
        PostBean loginPostBean = new PostBean();
        if (!TextUtils.isEmpty(id)) {
            loginPostBean.setId(id);
        }

        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getXlClubInfo(requestBody).subscribe(new SimpleObserver<ResponseBean<XlClubInfoBean>>() {
            @Override
            public void onSuccess(ResponseBean<XlClubInfoBean> responseBean) {
                getXlClubInfoLiveData.postValue(responseBean);
            }
        });

    }


    /**
     * ???????????????
     */
    public void getXlClubInfoList(String pageNo, String pageSize, String teamTitle, String city, String textCode) {
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
            if (teamTitle == null || teamTitle.equals("null")) {
                loginPostBean.setTeamTitle("");
            } else {
                loginPostBean.setTeamTitle(teamTitle);
            }
        }
        if (!TextUtils.isEmpty(city)) {
            if (city == null || city.equals("null")) {
                loginPostBean.setCity("");
            } else {
                loginPostBean.setCity(city);
            }
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
     * ???????????????
     */
    public void createXlClubInfo(String teamTitle, String coverLogo, String teamType, String abbreviation, String inCharge,
                                 String phoneNum, String city, String detailAddress, String clubImgUrl,
                                 String qualificationImgUrl, String tablesNum, String teamIntroduce) {

        PostBean loginPostBean = new PostBean();
        if (!TextUtils.isEmpty(teamTitle)) {
            loginPostBean.setTeamTitle(teamTitle);
        }
        if (!TextUtils.isEmpty(coverLogo)) {
            loginPostBean.setCoverLogo(coverLogo);
        }
        if (!TextUtils.isEmpty(teamType)) {
            loginPostBean.setTeamType(teamType);
        }
        if (!TextUtils.isEmpty(abbreviation)) {
            loginPostBean.setAbbreviation(abbreviation);
        }
        if (!TextUtils.isEmpty(inCharge)) {
            loginPostBean.setInCharge(inCharge);
        }
        if (!TextUtils.isEmpty(phoneNum)) {
            loginPostBean.setPhoneNum(phoneNum);
        }
        if (!TextUtils.isEmpty(city)) {
            loginPostBean.setCity(city);
        }

        if (!TextUtils.isEmpty(detailAddress)) {
            loginPostBean.setDetailAddress(detailAddress);
        }

        if (!TextUtils.isEmpty(clubImgUrl)) {
            loginPostBean.setClubImgUrl(clubImgUrl);
        }

        if (!TextUtils.isEmpty(qualificationImgUrl)) {
            loginPostBean.setQualificationImgUrl(qualificationImgUrl);
        }

        if (!TextUtils.isEmpty(tablesNum)) {
            loginPostBean.setTablesNum(tablesNum);
        }

        if (!TextUtils.isEmpty(teamIntroduce)) {
            loginPostBean.setTeamIntroduce(teamIntroduce);
        }

        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).createXlClubInfo(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                createXlClubInfoLiveData.postValue(responseBean);
            }
        });

    }

    /**
     * ???????????????
     */
    public void deleteXlClubInfo(String id, String teamType) {
        PostBean loginPostBean = new PostBean();
        if (!TextUtils.isEmpty(id)) {
            loginPostBean.setId(id);
        }
        if (!TextUtils.isEmpty(teamType)) {
            loginPostBean.setTeamType(teamType);
        }
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).deleteXlClubInfo(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                deleteXlClubInfoLiveData.postValue(responseBean);
            }
        });

    }


    /**
     * ?????????????????????
     */
    public void getXlClubMember(String id) {
        PostBean loginPostBean = new PostBean();
        if (!TextUtils.isEmpty(id)) {
            loginPostBean.setId(id);
        }

        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getXlClubMember(requestBody).subscribe(new SimpleObserver<ResponseBean<XlClubMemberBean>>() {
            @Override
            public void onSuccess(ResponseBean<XlClubMemberBean> responseBean) {
                getXlClubMemberLiveData.postValue(responseBean);
            }
        });

    }

    /**
     * ???????????????
     */
    public void editXlClubInfo(String id, String teamTitle, String coverLogo, String teamType, String abbreviation, String inCharge,
                               String phoneNum, String city, String detailAddress, String clubImgUrl,
                               String qualificationImgUrl, String tablesNum, String teamIntroduce) {

        PostBean loginPostBean2 = new PostBean();
        if (!TextUtils.isEmpty(id)) {
            loginPostBean2.setId(id);
        }
        if (!TextUtils.isEmpty(teamTitle)) {
            loginPostBean2.setTeamTitle(teamTitle);
        }
        if (!TextUtils.isEmpty(coverLogo)) {
            loginPostBean2.setCoverLogo(coverLogo);
        }
        if (!TextUtils.isEmpty(teamType)) {
            loginPostBean2.setTeamType(teamType);
        }
        if (!TextUtils.isEmpty(abbreviation)) {
            loginPostBean2.setAbbreviation(abbreviation);
        }
        if (!TextUtils.isEmpty(inCharge)) {
            loginPostBean2.setInCharge(inCharge);
        }
        if (!TextUtils.isEmpty(phoneNum)) {
            loginPostBean2.setPhoneNum(phoneNum);
        }
        if (!TextUtils.isEmpty(city)) {
            loginPostBean2.setCity(city);
        }

        if (!TextUtils.isEmpty(detailAddress)) {
            loginPostBean2.setDetailAddress(detailAddress);
        }

        if (!TextUtils.isEmpty(clubImgUrl)) {
            loginPostBean2.setClubImgUrl(clubImgUrl);
        }

        if (!TextUtils.isEmpty(qualificationImgUrl)) {
            loginPostBean2.setQualificationImgUrl(qualificationImgUrl);
        }

        if (!TextUtils.isEmpty(tablesNum)) {
            loginPostBean2.setTablesNum(tablesNum);
        }

        if (!TextUtils.isEmpty(teamIntroduce)) {
            loginPostBean2.setTeamIntroduce(teamIntroduce);
        }

        String params2 = new Gson().toJson(loginPostBean2);
        RequestBody requestBody2 = null;
        try {
            requestBody2 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params2).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).updateXlClubInfo(requestBody2).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                editXlClubInfoLiveData.postValue(responseBean);
            }
        });
    }

    /**
     * ???????????????
     */
    public void getClubTypeData() {
        PostBean loginPostBean = new PostBean();
        String params = new Gson().toJson(loginPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getApiService(RequestService.class).getClubTypeData(requestBody).subscribe(new SimpleObserver<ResponseBean<List<ClubTypeDataBean>>>() {
            @Override
            public void onSuccess(ResponseBean<List<ClubTypeDataBean>> responseBean) {
                getClubTypeDataLiveData.postValue(responseBean);
            }
        });

    }
}
