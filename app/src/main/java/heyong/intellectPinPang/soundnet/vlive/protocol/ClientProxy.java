package heyong.intellectPinPang.soundnet.vlive.protocol;


import com.elvishew.xlog.XLog;

import org.json.JSONException;
import org.json.JSONObject;

import heyong.intellectPinPang.soundnet.vlive.protocol.model.request.AudienceListRequest;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.request.CreateRoomRequest;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.request.ModifySeatStateRequest;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.request.ModifyUserStateRequest;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.request.OssPolicyRequest;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.request.PKRequest;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.request.ProductRequest;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.request.RefreshTokenRequest;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.request.Request;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.request.RoomListRequest;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.request.RoomRequest;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.request.SeatInteractionRequest;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.request.SendGiftRequest;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.request.UserRequest;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ClientProxy {
    public static final int ROOM_TYPE_SINGLE = 1;
    public static final int ROOM_TYPE_HOST_IN = 2;
    public static final int ROOM_TYPE_PK = 3;
    public static final int ROOM_TYPE_VIRTUAL_HOST = 4;
    public static final int ROOM_TYPE_ECOMMERCE = 5;

    public static final int PK_WAIT = 0;
    public static final int PK_IN = 1;
    public static final int PK_UNAWARE = 2;

    private static final String APP_CODE = "ent-super";
    private static final int OS_TYPE = 2;

    // 1 means android phone app (rather than a pad app)
    private static final int TERMINAL_TYPE = 1;

    private Client mClient;
    private long mReqId = 1;

    private static volatile ClientProxy sInstance;

    private ClientProxy() {
        mClient = new Client();
    }

    public static ClientProxy instance() {
        if (sInstance == null) {
            synchronized (ClientProxy.class) {
                if (sInstance == null) {
                    sInstance = new ClientProxy();
                }
            }
        }

        return sInstance;
    }

    public long sendMyRequest(int request, String params) {
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    new JSONObject(params).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch (request) {
            case Request.PRODUCT_BEGIN_LIVE://开始直播

                XLog.e("开始直播  请求数据");
                mClient.productBeginLive(requestBody);
                break;
            case Request.PRODUCT_SUSPPEND_LIVE://暂停直播


                break;
            case Request.PRODUCT_CONTINUE_LIVE://继续直播


                break;
            case Request.PRODUCT_END_LIVE://结束直播

                mClient.endLiveBroadcast(requestBody);


                break;
            case Request.PRODUCT_INTO_LIVE://进入直播间

                mClient.getIntoLiveBroadcastingRoom(requestBody);


                break;
            case Request.PRODUCT_LEAVE_LIVE://离开直播间

                mClient.leaveLiveBroadcastingRoom(requestBody);


                break;
            case Request.PRODUCT_BUY_VIDEO_LIVE://购买完整视频


                break;
            case Request.PRODUCT_OPEN_CLOUD_RECORD://打开云端录制

                mClient.openCloudRecording(requestBody);
                break;
            case Request.PRODUCT_CLOSE_CLOUD_RECORD://关闭云端录制

                mClient.stopCloudRecording(requestBody);
                break;

        }

        return mReqId++;
    }

    public long sendRequest(int request, Object params) {
        switch (request) {
            case Request.APP_VERSION:
                String ver = (String) params;
                mClient.requestVersion(mReqId, APP_CODE, OS_TYPE, TERMINAL_TYPE, ver);
                break;
            case Request.GIFT_LIST:
                mClient.requestGiftList(mReqId);
                break;
            case Request.MUSIC_LIST:
                mClient.requestMusicList(mReqId);
                break;
            case Request.OSS:
                OssPolicyRequest ossRequest = (OssPolicyRequest) params;
                mClient.requestOssPolicy(mReqId, ossRequest.token, ossRequest.type);
                break;
            case Request.CREATE_USER:
                UserRequest userRequest = (UserRequest) params;
                mClient.createUser(mReqId, userRequest.userName);
                break;
            case Request.EDIT_USER:
                userRequest = (UserRequest) params;
                mClient.editUser(mReqId, userRequest.token, userRequest.userId,
                        userRequest.userName, userRequest.avatar);
                break;
            case Request.USER_LOGIN:
                String userId = (String) params;
                mClient.login(mReqId, userId);
                break;
            case Request.ROOM_LIST:
                RoomListRequest roomListRequest = (RoomListRequest) params;
                mClient.requestRoomList(mReqId, roomListRequest.token, roomListRequest.nextId,
                        roomListRequest.count, roomListRequest.type, roomListRequest.pkState);
                break;
            case Request.CREATE_ROOM:
                CreateRoomRequest createRoomRequest = (CreateRoomRequest) params;
                mClient.createRoom(mReqId, createRoomRequest.token,
                        createRoomRequest.roomName, createRoomRequest.type,
                        createRoomRequest.avatar);
                break;
            case Request.ENTER_ROOM:
                RoomRequest roomRequest = (RoomRequest) params;
                mClient.enterRoom(mReqId, roomRequest.token, roomRequest.roomId);
                break;
            case Request.LEAVE_ROOM:
                roomRequest = (RoomRequest) params;
                mClient.leaveRoom(mReqId, roomRequest.token, roomRequest.roomId);
                break;
            case Request.AUDIENCE_LIST:
                AudienceListRequest audienceRequest = (AudienceListRequest) params;
                mClient.requestAudienceList(mReqId, audienceRequest.token, audienceRequest.roomId,
                        audienceRequest.nextId, audienceRequest.count, audienceRequest.type);
                break;
            case Request.SEND_GIFT:
                SendGiftRequest sendGiftRequest = (SendGiftRequest) params;
                mClient.sendGift(mReqId, sendGiftRequest.token, sendGiftRequest.roomId,
                        sendGiftRequest.giftId, sendGiftRequest.count);
                break;
            case Request.GIFT_RANK:
                String roomId = (String) params;
                mClient.giftRank(mReqId, roomId);
                break;
            case Request.SEAT_STATE:
                roomRequest = (RoomRequest) params;
                mClient.requestSeatState(mReqId, roomRequest.token, roomRequest.roomId);
                break;
            case Request.MODIFY_USER_STATE:
                ModifyUserStateRequest userStateRequest = (ModifyUserStateRequest) params;
                mClient.modifyUserState(userStateRequest.token, userStateRequest.roomId,
                        userStateRequest.userId, userStateRequest.enableAudio,
                        userStateRequest.enableVideo, userStateRequest.enableChat);
                break;
            case Request.MODIFY_SEAT_STATE:
                ModifySeatStateRequest modifySeatRequest = (ModifySeatStateRequest) params;
                mClient.modifySeatState(modifySeatRequest.token,
                        modifySeatRequest.roomId,
                        modifySeatRequest.no,
                        modifySeatRequest.state);
                break;
            case Request.REFRESH_TOKEN:
                RefreshTokenRequest refreshTokenRequest = (RefreshTokenRequest) params;
                mClient.refreshToken(mReqId, refreshTokenRequest.token, refreshTokenRequest.roomId);
                break;
            case Request.PK_BEHAVIOR:
                PKRequest pkRequest = (PKRequest) params;
                mClient.requestPKBehavior(pkRequest.token, pkRequest.myRoomId,
                        pkRequest.targetRoomId, pkRequest.type);
                break;
            case Request.PK_END:
                pkRequest = (PKRequest) params;
                mClient.requestPKEnd(pkRequest.token, pkRequest.myRoomId);
                break;
            case Request.SEAT_INTERACTION:
                SeatInteractionRequest seatInteractionRequest = (SeatInteractionRequest) params;
                mClient.requestSeatInteraction(seatInteractionRequest.token,
                        seatInteractionRequest.roomId, seatInteractionRequest.userId,
                        seatInteractionRequest.no, seatInteractionRequest.type);
                break;
            case Request.PRODUCT_LIST:
                ProductRequest productRequest = (ProductRequest) params;
                mClient.requestProductList(productRequest.token, productRequest.roomId);
                break;
            case Request.PRODUCT_MANAGE:
                productRequest = (ProductRequest) params;
                mClient.requestManageProductState(productRequest.token,
                        productRequest.roomId, productRequest.productId,
                        productRequest.state);
                break;
            case Request.PRODUCT_PURCHASE:
                productRequest = (ProductRequest) params;
                mClient.requestPurchaseProduct(productRequest.token,
                        productRequest.roomId, productRequest.productId,
                        productRequest.count);
                break;
        }

        return mReqId++;
    }

    public void registerProxyListener(ClientProxyListener listener) {
        mClient.registerProxyListener(listener);
    }

    public void removeProxyListener(ClientProxyListener listener) {
        mClient.removeProxyListener(listener);
    }
}
