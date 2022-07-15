package heyong.intellectPinPang.model;

import android.app.Service;

import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.bean.friend.DynamicDetailBean;
import heyong.intellectPinPang.bean.friend.MyReplyListBean;
import heyong.intellectPinPang.bean.friend.ReplyCommentPageBean;
import heyong.intellectPinPang.bean.friend.ReplyPageBean;
import heyong.intellectPinPang.bean.friend.DynamicListBean;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

//朋友圈
public interface FriendService {

    //动态发表  用户发表动态，动态中的内容为文字，图片，视频，动态中的图片个数限制为9个。
    @POST("friends/dynamic/pushDynamic")
    Observable<ResponseBean> pushDynamic(@Body RequestBody body);


    //删除动态  /moment/deleteMomentMessage  当用户发表动态后，可自行删除动态，且只可删除本人发布动态。
    @POST("friends/dynamic/deleteDynamic")
    Observable<ResponseBean> deleteMomentMessage(@Body RequestBody body);


    //评论动态
    @POST("friends/dynamic/commentDynamic")
    Observable<ResponseBean> commentDynamic(@Body RequestBody body);


    //回复评论
    @POST("friends/dynamic/replyDynamic")
    Observable<ResponseBean> replyDynamic(@Body RequestBody body);

    //点赞动态
    @POST("friends/dynamic/fabulousDynamic")
    Observable<ResponseBean> fabulousDynamic(@Body RequestBody body);

    //取消点赞动态
    @POST("friends/dynamic/cancelFabulousDynamic")
    Observable<ResponseBean> cancelFabulousDynamic(@Body RequestBody body);

    //2.6动态详情
    @POST("friends/dynamic/getDynamicDetail")
    Observable<ResponseBean<DynamicDetailBean>> getDynamicDetail(@Body RequestBody body);

    //动态详情的评论
    @POST("friends/reply/getCommentPage")
    Observable<ResponseBean<ReplyCommentPageBean>> replyCommentPage(@Body RequestBody body);


    //查看所有用户动态
    @POST("friends/dynamic/getDynamicList")
    Observable<ResponseBean<DynamicListBean>> getDynamicList(@Body RequestBody body);


    //分享动态
    @POST("friends/dynamic/shareDynamic")
    Observable<ResponseBean> shareDynamic(@Body RequestBody body);

    //收藏动态
    @POST("friends/reply/collectDynamic")
    Observable<ResponseBean> collectDynamic(@Body RequestBody body);

    //取消收藏
    @POST("friends/reply/cancelCollectDynamic")
    Observable<ResponseBean> cancelCollectDynamic(@Body RequestBody body);

    //评论详情
    @POST("friends/reply/getReplyList")
    Observable<ResponseBean<MyReplyListBean>> getReplyList(@Body RequestBody body);

    //评论详情分页
    @POST("friends/reply/getReplyPage")
    Observable<ResponseBean<ReplyPageBean>> getReplyPage(@Body RequestBody body);
    //收藏列表


}
