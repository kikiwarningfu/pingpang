package heyong.intellectPinPang.ui.friendcircle;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import heyong.handong.framework.account.AccountHelper;
import heyong.handong.framework.api.Api;
import heyong.handong.framework.api.FriendApi;
import heyong.handong.framework.api.Params;
import heyong.handong.framework.base.BaseViewModel;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.bean.friend.DynamicDetailBean;
import heyong.intellectPinPang.bean.friend.MyReplyListBean;
import heyong.intellectPinPang.bean.friend.ReplyCommentPageBean;
import heyong.intellectPinPang.bean.friend.ReplyPageBean;
import heyong.intellectPinPang.bean.friend.DynamicListBean;
import heyong.intellectPinPang.bean.friend.FriendListPostBean;
import heyong.intellectPinPang.bean.friend.PushMomentMessagePostBean;
import heyong.intellectPinPang.bean.gsonbean.FriendPostBean;
import heyong.intellectPinPang.model.FriendService;
import heyong.intellectPinPang.model.RequestService;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PublishEditCircleViewModel extends BaseViewModel {
    public List<String> imgItems = new ArrayList<>();
    public MutableLiveData<ResponseBean> getUploadTokenLiveDataVideo = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> getUploadTokenLiveDataImage = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> pushMomentMessageLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> deleteMessageLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> collectDynamicLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> cancelCollectDynamicLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> commentMomentMessageLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> commentMomentMessageReplyLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> thumbsUpMomentMessageLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> cancelThumbsUpMomentMessageLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<DynamicDetailBean>> getMomentDetailByIdLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<ReplyCommentPageBean>> replyCommentPageBeanLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean> DynamicDetailBeanLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<MyReplyListBean>> getReplyListLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<ReplyPageBean>> getReplyPageLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseBean<DynamicListBean>> getMomentListLiveData = new MutableLiveData<>();

    public List<String> videoItems = new ArrayList<>();
    public List<String> videolocalPath = new ArrayList<>();
    public MutableLiveData<ResponseBean> getUploadTokenLiveData = new MutableLiveData<>();

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
     * 动态发表  用户发表动态，动态中的内容为文字，图片，视频，动态中的图片个数限制为9个。
     */
    public void pushMomentMessage(PushMomentMessagePostBean postBean) {

//        PushMomentMessagePostBean postBean = new PushMomentMessagePostBean();
        String params = new Gson().toJson(postBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FriendApi.getApiService(FriendService.class).pushDynamic(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                pushMomentMessageLiveData.postValue(responseBean);
            }
        });


    }

    /**
     * 删除动态
     */
    public void deleteDynamic(String id, String userId) {
        FriendPostBean friendPostBean = new FriendPostBean();
        friendPostBean.setId("" + id);
        friendPostBean.setUserId("" + userId);
        String params = new Gson().toJson(friendPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FriendApi.getApiService(FriendService.class).deleteMomentMessage(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                deleteMessageLiveData.postValue(responseBean);
            }
        });
    }
    /**
     * 收藏动态
     */
    public void collectDynamic(String dynaId, String currentUserId) {
        FriendPostBean friendPostBean = new FriendPostBean();
        friendPostBean.setDynaId("" + dynaId);
        friendPostBean.setCurrentUserId("" + currentUserId);
        String params = new Gson().toJson(friendPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FriendApi.getApiService(FriendService.class).collectDynamic(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                collectDynamicLiveData.postValue(responseBean);
            }
        });
    }
    /**
     * 取消收藏
     */
    public void cancelCollectDynamic(String dynaId, String currentUserId) {
        FriendPostBean friendPostBean = new FriendPostBean();
        friendPostBean.setDynaId("" + dynaId);
        friendPostBean.setCurrentUserId("" + currentUserId);
        String params = new Gson().toJson(friendPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FriendApi.getApiService(FriendService.class).cancelCollectDynamic(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                cancelCollectDynamicLiveData.postValue(responseBean);
            }
        });
    }



    /**
     * 评论动态
     */
    public void commentDynamic(String dynaId, String userId, String content, String comId,String picture) {
        FriendPostBean friendPostBean = new FriendPostBean();
        friendPostBean.setDynaId("" + dynaId);
        friendPostBean.setUserId("" + userId);
        friendPostBean.setContent("" + content);
        if (!TextUtils.isEmpty(comId)) {
            friendPostBean.setComId("" + comId);
        }
        friendPostBean.setPicture(""+picture);
        String params = new Gson().toJson(friendPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FriendApi.getApiService(FriendService.class).commentDynamic(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                commentMomentMessageLiveData.postValue(responseBean);
            }
        });
    }


    /**
     * 回复评论
     */
    public void replyDynamic(String dynaId, String userId, String content, String commentId,String replyPid
    ,String picture) {
        FriendPostBean friendPostBean = new FriendPostBean();
        friendPostBean.setDynaId("" + dynaId);
        friendPostBean.setUserId("" + userId);
        friendPostBean.setContent(content);
        friendPostBean.setComId("" + commentId);
        friendPostBean.setReplyPid(""+replyPid);
        friendPostBean.setPicture(""+picture);
        String params = new Gson().toJson(friendPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FriendApi.getApiService(FriendService.class).replyDynamic(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                commentMomentMessageReplyLiveData.postValue(responseBean);
            }
        });
    }


    /**
     * 点赞
     */
    public void thumbsUpMomentMessage(String dynaId, String userId, String commentId, String replyId) {
        FriendPostBean friendPostBean = new FriendPostBean();
        friendPostBean.setDynaId("" + dynaId);
        friendPostBean.setUserId("" + userId);
        if (!TextUtils.isEmpty(commentId)) {
            friendPostBean.setCommentId("" + commentId);
        }
        if (!TextUtils.isEmpty(replyId)) {
            friendPostBean.setReplyId("" + replyId);
        }
        String params = new Gson().toJson(friendPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FriendApi.getApiService(FriendService.class).fabulousDynamic(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                thumbsUpMomentMessageLiveData.postValue(responseBean);
            }
        });
    }


    /**
     * 取消点赞
     */
    public void cancelThumbsUpMomentMessage(String id, String dynaId, String userId, String commentId, String replyId) {
        FriendPostBean friendPostBean = new FriendPostBean();
        friendPostBean.setId("" + id);
        friendPostBean.setDynaId("" + dynaId);
        friendPostBean.setUserId("" + userId);
        String params = new Gson().toJson(friendPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FriendApi.getApiService(FriendService.class).cancelFabulousDynamic(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                cancelThumbsUpMomentMessageLiveData.postValue(responseBean);
            }
        });
    }


    /**
     * 动态详情
     */
    public void getMomentDetailById(String id, String userId) {
        FriendPostBean friendPostBean = new FriendPostBean();
        friendPostBean.setId(id);
        friendPostBean.setCurrentUserId("" + userId);

        String params = new Gson().toJson(friendPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FriendApi.getApiService(FriendService.class).getDynamicDetail(requestBody).subscribe(new SimpleObserver<ResponseBean<DynamicDetailBean>>() {
            @Override
            public void onSuccess(ResponseBean<DynamicDetailBean> responseBean) {
                getMomentDetailByIdLiveData.postValue(responseBean);
            }
        });
    }

    /**
     * 动态详情的评论
     */
    public void replyCommentPage(String page, String pageNumber,String dynaId,String currentUserId) {
        FriendPostBean friendPostBean = new FriendPostBean();
        friendPostBean.setDynaId(dynaId);
        friendPostBean.setCurrentUserId("" + currentUserId);
        friendPostBean.setPage(""+page);
        friendPostBean.setPageNumber(""+pageNumber);
        String params = new Gson().toJson(friendPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FriendApi.getApiService(FriendService.class).replyCommentPage(requestBody).subscribe(new SimpleObserver<ResponseBean<ReplyCommentPageBean>>() {
            @Override
            public void onSuccess(ResponseBean<ReplyCommentPageBean> responseBean) {
                replyCommentPageBeanLiveData.postValue(responseBean);
            }
        });
    }


    /**
     * 查看所有用户动态
     */
    public void getMomentList(String page, String pagesize, String type, String userIds, String isHot, String province, String city, String area) {
        FriendListPostBean friendPostBean = new FriendListPostBean();
        friendPostBean.setPage("" + page);
        friendPostBean.setPageNumber("" + pagesize);
        if (!TextUtils.isEmpty(type)) {
            friendPostBean.setType("" + type);
        }
        if (!TextUtils.isEmpty(userIds)) {
            friendPostBean.setUserIds("" + userIds);
        }
        if (!TextUtils.isEmpty(isHot)) {
            friendPostBean.setIsHot("" + isHot);
        }
        if (!TextUtils.isEmpty(province)) {
            friendPostBean.setProvince("" + province);
        }
        if (!TextUtils.isEmpty(city)) {
            friendPostBean.setCity("" + city);
        }
        if (!TextUtils.isEmpty(area)) {
            friendPostBean.setArea("" + area);
        }
        if (AccountHelper.getUserId() != 0) {
            friendPostBean.setCurrentUserId("" + AccountHelper.getUserId());
        }
        String params = new Gson().toJson(friendPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FriendApi.getApiService(FriendService.class).getDynamicList(requestBody).subscribe(new SimpleObserver<ResponseBean<DynamicListBean>>() {
            @Override
            public void onSuccess(ResponseBean<DynamicListBean> responseBean) {
                getMomentListLiveData.postValue(responseBean);
            }
        });
    }


    /**
     * 分享动态
     */
    public void shareDynamic(String dynaId) {
        FriendPostBean friendPostBean = new FriendPostBean();
        friendPostBean.setDynaId("" + dynaId);
        String params = new Gson().toJson(friendPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FriendApi.getApiService(FriendService.class).shareDynamic(requestBody).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                DynamicDetailBeanLiveData.postValue(responseBean);
            }
        });
    }
    //getReplyList   评论详情
    public void getReplyList(String dynaId,String currentUserId,String commentId) {
        FriendPostBean friendPostBean = new FriendPostBean();
        friendPostBean.setDynaId("" + dynaId);
        friendPostBean.setCurrentUserId(""+currentUserId);
        friendPostBean.setCommentId(""+commentId);
        String params = new Gson().toJson(friendPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FriendApi.getApiService(FriendService.class).getReplyList(requestBody).subscribe(new SimpleObserver<ResponseBean<MyReplyListBean>>() {
            @Override
            public void onSuccess(ResponseBean<MyReplyListBean> responseBean) {
                getReplyListLiveData.postValue(responseBean);
            }
        });
    }
    //getReplyPage   评论详情分页
    public void getReplyPage(String dynaId,String currentUserId,String commentId,String page,String pageNumber) {
        FriendPostBean friendPostBean = new FriendPostBean();
        friendPostBean.setDynaId("" + dynaId);
        friendPostBean.setCurrentUserId(""+currentUserId);
        friendPostBean.setCommentId(""+commentId);
        friendPostBean.setPageNumber(""+pageNumber);
        friendPostBean.setPage(""+page);
        String params = new Gson().toJson(friendPostBean);
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FriendApi.getApiService(FriendService.class).getReplyPage(requestBody).subscribe(new SimpleObserver<ResponseBean<ReplyPageBean>>() {
            @Override
            public void onSuccess(ResponseBean<ReplyPageBean> responseBean) {
                getReplyPageLiveData.postValue(responseBean);
            }
        });
    }


    /**
     * 七牛云上传图片获取凭证
     */
    public void getUploadTokenVideo(String fileName) {

        HashMap<String, String> params = Params.newParams()
                .put("fileName", fileName)
                .params();
        Api.getApiService(RequestService.class).getUploadToken(params).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                getUploadTokenLiveDataVideo.postValue(responseBean);
            }
        });

    }

    /**
     * 七牛云上传图片获取凭证
     */
    public void getUploadTokenImage(String fileName) {

        HashMap<String, String> params = Params.newParams()
                .put("fileName", fileName)
                .params();
        Api.getApiService(RequestService.class).getUploadToken(params).subscribe(new SimpleObserver<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                getUploadTokenLiveDataImage.postValue(responseBean);
            }
        });

    }


}
