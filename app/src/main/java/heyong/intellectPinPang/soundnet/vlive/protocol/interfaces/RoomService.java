package heyong.intellectPinPang.soundnet.vlive.protocol.interfaces;


import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.bean.live.LiveRoomBean;
import heyong.intellectPinPang.bean.live.BeginLiveBroadCastBean;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.body.CreateRoomRequestBody;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.body.ModifyUserStateRequestBody;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.body.SendGiftBody;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.AudienceListResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.CreateRoomResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.EnterRoomResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.GiftRankResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.LeaveRoomResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.ModifyUserStateResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.RoomListResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.SeatStateResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.SendGiftResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RoomService {
    @POST("ent/v1/room")
    Call<CreateRoomResponse> requestCreateLiveRoom(@Header("token") String token, @Header("reqId") long reqId,
                                                   @Header("reqType") int reqType, @Body CreateRoomRequestBody body);

    @POST("ent/v1/room/{roomId}/entry")
    Call<EnterRoomResponse> requestEnterLiveRoom(@Header("token") String token, @Header("reqId") long reqId,
                                                 @Header("reqType") int reqType, @Path("roomId") String roomId);

    @POST("ent/v1/room/{roomId}/exit")
    Call<LeaveRoomResponse> requestLeaveLiveRoom(@Header("token") String token, @Header("reqId") long reqId,
                                                 @Header("reqType") int reqType, @Path("roomId") String roomId);

    @GET("ent/v1/room/{roomId}/user/page")
    Call<AudienceListResponse> requestAudienceList(@Header("token") String token, @Header("reqId") long reqId,
                                                   @Header("reqType") int reqType, @Path("roomId") String roomId,
                                                   @Query("nextId") String nextId, @Query("count") int count,
                                                   @Query("type") int type);

    @GET("ent/v1/room/{roomId}/seats")
    Call<SeatStateResponse> requestSeatState(@Header("token") String token, @Header("reqId") long reqId,
                                             @Header("reqType") int reqType, @Path("roomId") String roomId);

    @POST("ent/v1/room/{roomId}/user/{userId}")
    Call<ModifyUserStateResponse> requestModifyUserState(@Header("token") String token, @Path("roomId") String roomId,
                                                         @Path("userId") String userId, @Body ModifyUserStateRequestBody body);

    @POST("ent/v1/room/{roomId}/gift")
    Call<SendGiftResponse> requestSendGift(@Header("token") String token, @Header("reqId") long reqId,
                                           @Header("reqType") int reqType, @Path("roomId") String roomId,
                                           @Body SendGiftBody body);

    @GET("ent/v1/room/{roomId}/ranks")
    Call<GiftRankResponse> requestGiftRank(@Header("reqId") long reqId, @Header("reqType") int reqType,
                                           @Path("roomId") String roomId);

    @GET("ent/v1/room/page")
    Call<RoomListResponse> requestRoomList(@Header("reqId") long reqId, @Header("token") String token,
                                           @Header("reqType") int reqType, @Query("nextId") String nextId,
                                           @Query("count") int count, @Query("type") int type,
                                           @Query("pkState") Integer pkState);


    //开始直播
    @POST("xlZhiboJiedanOrder/beginLiveBroadcast")
    Call<ResponseBean<BeginLiveBroadCastBean>> beginLiveBroadcast(@Body RequestBody body);

    //暂停直播
    @POST("xlZhiboJiedanOrder/suspendLiveBroadcast")
    Call<RoomListResponse> suspendLiveBroadcast(@Body RequestBody body);

    //继续直播
    @POST("xlZhiboJiedanOrder/continueLiveBroadcast")
    Call<RoomListResponse> continueLiveBroadcast(@Body RequestBody body);

    //结束直播
    @POST("xlZhiboJiedanOrder/endLiveBroadcast")
    Call<ResponseBean> endLiveBroadcast(@Body RequestBody body);

    //开启云端录制
    @POST("xlZhiboArrange/openCloudRecording")
    Call<ResponseBean> openCloudRecording(@Body RequestBody body);

    //关闭云端录制
    @POST("xlZhiboArrange/stopCloudRecording")
    Call<ResponseBean> stopCloudRecording(@Body RequestBody body);


    //进入直播间
    @POST("xlZhiboArrange/getIntoLiveBroadcastingRoom")
    Call<ResponseBean<LiveRoomBean>> getIntoLiveBroadcastingRoom(@Body RequestBody body);

    //离开直播间
    @POST("xlZhiboArrange/leaveLiveBroadcastingRoom")
    Call<ResponseBean> leaveLiveBroadcastingRoom(@Body RequestBody body);




}
