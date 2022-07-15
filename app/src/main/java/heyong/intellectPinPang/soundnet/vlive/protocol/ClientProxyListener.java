package heyong.intellectPinPang.soundnet.vlive.protocol;


import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.bean.live.LiveRoomBean;
import heyong.intellectPinPang.bean.live.BeginLiveBroadCastBean;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.AppVersionResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.AudienceListResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.CreateRoomResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.CreateUserResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.EditUserResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.EnterRoomResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.GiftListResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.GiftRankResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.LeaveRoomResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.LoginResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.ModifyUserStateResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.MusicListResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.OssPolicyResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.ProductListResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.RefreshTokenResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.RoomListResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.SeatStateResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.SendGiftResponse;

public interface ClientProxyListener {
    void onAppVersionResponse(AppVersionResponse response);

    void onRefreshTokenResponse(RefreshTokenResponse refreshTokenResponse);

    void onOssPolicyResponse(OssPolicyResponse response);

    void onMusicLisResponse(MusicListResponse response);

    void onGiftListResponse(GiftListResponse response);

    void onRoomListResponse(RoomListResponse response);

    void onCreateUserResponse(CreateUserResponse response);

    void onEditUserResponse(EditUserResponse response);

    void onLoginResponse(LoginResponse response);

    void onCreateRoomResponse(CreateRoomResponse response);

    void onEnterRoomResponse(EnterRoomResponse response);

    void onLeaveRoomResponse(LeaveRoomResponse response);

    void onAudienceListResponse(AudienceListResponse response);

    void onRequestSeatStateResponse(SeatStateResponse response);

    void onModifyUserStateResponse(ModifyUserStateResponse response);

    void onSendGiftResponse(SendGiftResponse response);

    void onGiftRankResponse(GiftRankResponse response);

    void onGetProductListResponse(ProductListResponse response);

    void onProductStateChangedResponse(String productId, int state, boolean success);

    void onProductPurchasedResponse(boolean success);

    void onSeatInteractionResponse(long processId, String userId, int seatNo, int type);

    void onResponseError(int requestType, String error, String message);

    //开始直播
    void beginLiveBroadcastResponse(ResponseBean<BeginLiveBroadCastBean> body1);

    //暂停直播
    void suspendLiveBroadcastResponse();

    //继续直播
    void continueLiveBroadcastResponse();

    //结束直播
    void endLiveBroadcastResponse(ResponseBean body1);

    //开启云端录制
    void openCloudRecording(ResponseBean body1);

    //关闭云端录制
    void stopCloudRecording(ResponseBean body1);


    //进入直播间
    void getIntoLiveBroadcastingRoomResponse(ResponseBean<LiveRoomBean> body1);

    //离开直播间
    void leaveLiveBroadcastingRoom(ResponseBean body);

    //离开直播间
    void buyCompleteVideoResponse();


}