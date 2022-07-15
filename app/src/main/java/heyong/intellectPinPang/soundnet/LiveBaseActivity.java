package heyong.intellectPinPang.soundnet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceView;
import android.view.TextureView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.elvishew.xlog.XLog;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.soundnet.framework.PreprocessorFaceUnity;
import heyong.intellectPinPang.soundnet.framework.RtcVideoConsumer;
import heyong.intellectPinPang.soundnet.vlive.Config;
import heyong.intellectPinPang.soundnet.vlive.agora.rtc.RtcEventHandler;
import heyong.intellectPinPang.soundnet.vlive.agora.rtm.RtmMessageListener;
import heyong.intellectPinPang.soundnet.vlive.agora.rtm.RtmMessageManager;
import heyong.intellectPinPang.soundnet.vlive.agora.rtm.model.GiftRankMessage;
import heyong.intellectPinPang.soundnet.vlive.agora.rtm.model.NotificationMessage;
import heyong.intellectPinPang.soundnet.vlive.agora.rtm.model.PKStateMessage;
import heyong.intellectPinPang.soundnet.vlive.agora.rtm.model.SeatStateMessage;
import heyong.intellectPinPang.soundnet.vlive.utils.Global;
import io.agora.capture.video.camera.CameraManager;
import io.agora.capture.video.camera.VideoModule;
import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.models.ChannelMediaOptions;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmChannelAttribute;
import io.agora.rtm.RtmChannelMember;

public abstract class LiveBaseActivity extends SoundBaseActivity
        implements RtcEventHandler, RtmMessageListener {


    protected static final String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int PERMISSION_REQ = 1;

    // values of a live room
    protected String roomName;
    protected String roomId;
    protected boolean isOwner;
    protected String ownerId;
    protected boolean isHost;
    protected int myRtcRole;
    protected int ownerRtcUid;
    protected int tabId;

    // Current rtc channel generated by server
    // and obtained when entering the room.
    protected String rtcChannelName;

    private RtmMessageManager mMessageManager;
    private CameraManager mCameraVideoManager;
    private PreprocessorFaceUnity mFUPreprocessor;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keepScreenOn(getWindow());
        checkPermissions();
    }

    protected void checkPermissions() {
        if (!permissionArrayGranted()) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQ);
        } else {
            performInit();
        }
    }

    private boolean permissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(
                this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean permissionArrayGranted() {
        boolean granted = true;
        for (String per : PERMISSIONS) {
            if (!permissionGranted(per)) {
                granted = false;
                break;
            }
        }
        return granted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQ) {
            if (permissionArrayGranted()) {
                performInit();
            } else {
                Toast.makeText(this, R.string.permission_not_granted, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void performInit() {
        initRoom();
        onPermissionGranted();
    }

    public static final String TAG = LiveBaseActivity.class.getSimpleName();

    private void initRoom() {
        Intent intent = getIntent();
        roomName = intent.getStringExtra(Global.Constants.KEY_ROOM_NAME);
        roomId = intent.getStringExtra(Global.Constants.KEY_ROOM_ID);
        isOwner = intent.getBooleanExtra(Global.Constants.KEY_IS_ROOM_OWNER, false);
        ownerId = intent.getStringExtra(Global.Constants.KEY_ROOM_OWNER_ID);//USER_ID
        Log.e(TAG, "initRoom: "+"roomName==="+roomName+" roomId==="+roomId+" isOwner==="+isOwner+" ownerId==="+ownerId);
        isHost = isOwner;
        myRtcRole = isOwner ? Constants.CLIENT_ROLE_BROADCASTER : Constants.CLIENT_ROLE_AUDIENCE;
        tabId = intent.getIntExtra(Global.Constants.TAB_KEY, 0);
        tabId=2;
        mMessageManager = RtmMessageManager.instance();
        mMessageManager.init(rtmClient());
        mMessageManager.registerMessageHandler(this);
        mMessageManager.setCallbackThread(new Handler(getMainLooper()));

        initCameraIfNeeded();
    }

    private void initCameraIfNeeded() {
        if (mCameraVideoManager == null) {
            mCameraVideoManager = cameraVideoManager();
        }

//        if (mCameraVideoManager != null) {
//            mCameraVideoManager.enablePreprocessor(
//                    config().isBeautyEnabled());
//        }
//
//        if (mFUPreprocessor == null &&
//                mCameraVideoManager != null) {
//            mFUPreprocessor = (PreprocessorFaceUnity)
//                    mCameraVideoManager.getPreprocessor();
//        }
    }

    protected abstract void onPermissionGranted();

    @Override
    public void onStart() {
        super.onStart();
        registerRtcHandler(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        removeRtcHandler(this);
    }

    protected RtmMessageManager getMessageManager() {
        return mMessageManager;
    }

//    protected void joinRtcChannel(String token, int uid) {
//        rtcEngine().setClientRole(myRtcRole);
//        rtcEngine().setVideoSource(new RtcVideoConsumer(VideoModule.instance()));
//        setVideoConfiguration();
//        XLog.e(" 开始加入频道 身份是"+myRtcRole+"==========joinRtcChannel==="+"token"+token
//                +"rtcChannelName"+rtcChannelName+" uid"+uid);
////        rtcEngine().joinChannel(token,
////                rtcChannelName, null, uid);
//        String tokens="00631b6eef5fb5e41eea48544d9bc1d53a1IADyfEqc1I3Ok1T3FnE0mdkYwh9ESZHp9d3tqi549wp0xblwi4sAAAAAEAD+bihbWaNBYQEAAQBZo0Fh";
//        String channelId="11111111";
//        ChannelMediaOptions option = new ChannelMediaOptions();
//        option.autoSubscribeAudio = true;
//        option.autoSubscribeVideo = true;
//
//        rtcEngine().joinChannel(tokens,
//                channelId, "Extra Optional Data", 0,option);
//    }

    protected SurfaceView setupRemoteVideo(int uid) {
        SurfaceView surfaceView = RtcEngine.CreateRendererView(this);
        rtcEngine().setupRemoteVideo(new VideoCanvas(
                surfaceView, VideoCanvas.RENDER_MODE_HIDDEN, uid));
        return surfaceView;
    }

    protected void removeRemoteVideo(int uid) {
        rtcEngine().setupRemoteVideo(new VideoCanvas(null, VideoCanvas.RENDER_MODE_HIDDEN, uid));
    }

    protected void setVideoConfiguration() {
        Log.e(TAG, "setVideoConfiguration: "+config().createVideoEncoderConfig(tabIdToLiveType()) );
//        VideoEncoderConfiguration config=new VideoEncoderConfiguration();
//        config.orientationMode=
//        VideoEncoderConfiguration videoEncoderConfig = config().createVideoEncoderConfig(tabIdToLiveType());
//        videoEncoderConfig.orientationMode=VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_ADAPTIVE;


        VideoEncoderConfiguration.ORIENTATION_MODE orientationMode =
                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_ADAPTIVE;

        VideoEncoderConfiguration.VideoDimensions dimensions =
                new VideoEncoderConfiguration.VideoDimensions(1920, 1080);

        VideoEncoderConfiguration.FRAME_RATE frameRate =
                VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15;

        VideoEncoderConfiguration videoEncoderConfiguration =
                new VideoEncoderConfiguration(dimensions, frameRate, VideoEncoderConfiguration.STANDARD_BITRATE, orientationMode);
        rtcEngine().setVideoEncoderConfiguration(videoEncoderConfiguration);


    }

    protected int tabIdToLiveType() {
        //LIVE_TYPE_MULTI_HOST 多人连麦直播
        //LIVE_TYPE_SINGLE_HOST 单主播直播
        //LIVE_TYPE_PK_HOST   视频PK连麦
        //LIVE_TYPE_VIRTUAL_HOST  虚拟主播
        //LIVE_TYPE_ECOMMERCE   电商直播
        return Config.LIVE_TYPE_SINGLE_HOST;
    }

    protected void startCameraCapture() {
        initCameraIfNeeded();
        if (mCameraVideoManager != null) {
            enablePreProcess(config().isBeautyEnabled());
            mCameraVideoManager.startCapture();
        }
    }

    protected void setLocalPreview(SurfaceView surfaceView) {
        initCameraIfNeeded();
        if (mCameraVideoManager != null) {
            mCameraVideoManager.setLocalPreview(surfaceView);
        }
    }

    protected void setLocalPreview(TextureView textureView) {
        initCameraIfNeeded();
        if (mCameraVideoManager != null) {
            mCameraVideoManager.setLocalPreview(textureView);
        }
    }

    protected void switchCamera() {
        initCameraIfNeeded();
        if (mCameraVideoManager != null) {
            mCameraVideoManager.switchCamera();
        }
    }

    protected void stopCameraCapture() {
        initCameraIfNeeded();
        if (mCameraVideoManager != null) {
            mCameraVideoManager.stopCapture();
        }
    }

    protected void enablePreProcess(boolean enabled) {
        initCameraIfNeeded();
        if (mCameraVideoManager != null) {
            mCameraVideoManager.enablePreprocessor(enabled);
        }
    }

    protected void setBlurValue(float blur) {
        initCameraIfNeeded();
        if (mFUPreprocessor != null) {
            mFUPreprocessor.setBlurValue(blur);
        }
    }

    protected void setWhitenValue(float whiten) {
        initCameraIfNeeded();
        if (mFUPreprocessor != null) {
            mFUPreprocessor.setWhitenValue(whiten);
        }
    }

    protected void setCheekValue(float cheek) {
        initCameraIfNeeded();
        if (mFUPreprocessor != null) {
            mFUPreprocessor.setCheekValue(cheek);
        }
    }

    protected void setEyeValue(float eye) {
        initCameraIfNeeded();
        if (mFUPreprocessor != null) {
            mFUPreprocessor.setEyeValue(eye);
        }
    }

    protected void joinRtmChannel(String token, int uid) {
        Log.e(TAG, "joinRtmChannel: rtcChannelName===" + rtcChannelName);
        mMessageManager.joinChannel(rtcChannelName, new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e(TAG, "onSuccess: rtcChannelNameSuccess" + rtcChannelName);
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                XLog.e("rtm join channel failed " + rtcChannelName + " msg:" + errorInfo.toString());
            }
        });
    }

    protected void leaveRtmChannel(ResultCallback<Void> callback) {
        mMessageManager.leaveChannel(callback);
        Log.e(TAG, "leaveRtmChannel: ");
    }

    @Override
    public void onRtmConnectionStateChanged(int state, int reason) {
        XLog.e("statePrepare=====" + detalWithState(state) +
                "   reasonPrepare====" + dealWithReason(reason));
    }

    public String dealWithReason(int reason) {
        String str = "";
        switch (reason) {
            case 0:
                str = "建立网络连接中";
                break;
            case 1:
                str = "成功加入频道";
                break;
            case 2:
                str = "网络连接中断";
                break;
            case 3:
                str = "网络连接被服务器禁止。可能服务端踢人场景时会报这个错。";
                break;
            case 4:
                str = "加入频道失败";
                break;
            case 5:
                str = "离开频道";
                break;
            case 6:
                str="不是有效的 APP ID。请更换有效的 APP ID 重新加入频道";
                break;
            case 7:
                str="不是有效的 APP ID。请更换有效的 APP ID 重新加入频道";
                break;
            case 8:
                str="生成的 Token 无效";
                break;
            case 9:
                str="当前使用的 Token 过期，不再有效，需要重新在你的服务端申请生成 Token";
                break;
            case 10:
                str="此用户被服务器禁止。";
                break;
            case 11:
                str="由于设置了代理服务器，SDK 尝试重连";
                break;
            case 12:
                str="更新 Token 引起网络连接状态改变";
                break;
            case 13:
                str="客户端 IP 地址变更，可能是由于网络类型，或网络运营商的 IP 或端口发生改变引起";
                break;
            case 14:
                str="SDK 和服务器连接保活超时，进入自动重连状态";
                break;
        }
        return str;
    }

    public String detalWithState(int state) {
        String str = "";
        switch (state) {
            case 1:
                str = "网络连接断开";
                break;
            case 2:
                str = "建立网络连接中";
                break;
            case 3:
                str = "网络已连接";
                break;
            case 4:
                str = "重新建立网络连接中";
                break;
            case 5:
                str = "网络连接失败";
                break;
        }
        return str;
    }
    @Override
    public void onRtmTokenExpired() {
        XLog.e("onRtmTokenExpired");

    }

    @Override
    public void onRtmPeersOnlineStatusChanged(Map<String, Integer> map) {
        XLog.e("onRtmPeersOnlineStatusChanged");

    }

    @Override
    public void onRtmMemberCountUpdated(int memberCount) {
        XLog.e("onRtmMemberCountUpdated");

    }

    @Override
    public void onRtmAttributesUpdated(List<RtmChannelAttribute> attributeList) {
        XLog.e("onRtmAttributesUpdated");

    }

    @Override
    public void onRtmMemberJoined(RtmChannelMember rtmChannelMember) {
        XLog.e("onRtmMemberJoined");

    }

    @Override
    public void onRtmMemberLeft(RtmChannelMember rtmChannelMember) {
        XLog.e("onRtmMemberLeft");

    }

    @Override
    public void onRtmSeatInvited(String userId, String userName, int index) {
        XLog.e("onRtmSeatInvited");

    }

    @Override
    public void onRtmSeatApplied(String userId, String userName, int index) {
        XLog.e("onRtmSeatApplied");

    }

    @Override
    public void onRtmInvitationAccepted(long processId, String userId, String userName, int index) {
        XLog.e("onRtmInvitationAccepted");

    }

    @Override
    public void onRtmApplicationAccepted(long processId, String userId, String userName, int index) {
        XLog.e("onRtmApplicationAccepted");

    }

    @Override
    public void onRtmInvitationRejected(long processId, String userId, String userName, int index) {
        XLog.e("onRtmInvitationRejected");

    }

    @Override
    public void onRtmApplicationRejected(long processId, String userId, String userName, int index) {
        XLog.e("onRtmApplicationRejected");

    }

    @Override
    public void onRtmOwnerForceLeaveSeat(String userId, String userName, int index) {
        XLog.e("onRtmOwnerForceLeaveSeat");

    }

    @Override
    public void onRtmHostLeaveSeat(String userId, String userName, int index) {
        XLog.e("onRtmHostLeaveSeat");

    }

    @Override
    public void onRtmPkReceivedFromAnotherHost(String userId, String userName, String pkRoomId) {
        XLog.e("onRtmPkReceivedFromAnotherHost");

    }

    @Override
    public void onRtmPkAcceptedByTargetHost(String userId, String userName, String pkRoomId) {
        XLog.e("onRtmPkAcceptedByTargetHost");

    }

    @Override
    public void onRtmPkRejectedByTargetHost(String userId, String userName, String pkRoomId) {
        XLog.e("onRtmPkRejectedByTargetHost");

    }

    @Override
    public void onRtmChannelMessageReceived(String peerId, String nickname, String content) {
        XLog.e("onRtmChannelMessageReceived");

    }

    @Override
    public void onRtmRoomGiftRankChanged(int total, List<GiftRankMessage.GiftRankItem> list) {
        XLog.e("onRtmRoomGiftRankChanged");

    }

    @Override
    public void onRtmOwnerStateChanged(String userId, String userName, int uid, int enableAudio, int enableVideo) {
        XLog.e("onRtmOwnerStateChanged");

    }

    @Override
    public void onRtmSeatStateChanged(List<SeatStateMessage.SeatStateMessageDataItem> data) {
        XLog.e("onRtmSeatStateChanged");

    }

    @Override
    public void onRtmReceivePKEvent(PKStateMessage.PKStateMessageBody messageData) {
        XLog.e("onRtmReceivePKEvent");

    }

    @Override
    public void onRtmGiftMessage(String fromUserId, String fromUserName, String toUserId, String toUserName, int giftId) {
        XLog.e("onRtmGiftMessage");

    }

    @Override
    public void onRtmChannelNotification(int total, List<NotificationMessage.NotificationItem> list) {
        XLog.e("onRtmChannelNotification");

    }

    @Override
    public void onRtmProductPurchased(String productId, int count) {
        XLog.e("onRtmProductPurchased");

    }

    @Override
    public void onRtmProductStateChanged(String productId, int state) {
        XLog.e("onRtmProductStateChanged");

    }

    @Override
    public void onRtmLeaveMessage() {
        XLog.e("onRtmLeaveMessage");

    }

    @Override
    public void onRtcJoinChannelSuccess(String channel, int uid, int elapsed) {
        XLog.e("onRtcJoinChannelSuccess");
    }

    @Override
    public void onRtcRemoteVideoStateChanged(int uid, int state, int reason, int elapsed) {
        XLog.e("onRtcRemoteVideoStateChanged" + uid);

    }

    @Override
    public void onRtcStats(IRtcEngineEventHandler.RtcStats stats) {
//        XLog.e("onRtcStats" + stats.cpuAppUsage);
//        XLog.e("onRtcStats  stats====" + new Gson().toJson(stats));

    }

    @Override
    public void onRtcChannelMediaRelayStateChanged(int state, int code) {
        XLog.e("onRtcChannelMediaRelayStateChanged" + code);

    }

    @Override
    public void onRtcChannelMediaRelayEvent(int code) {
        XLog.e("onRtcChannelMediaRelayEvent" + code);

    }

    @Override
    public void onRtcAudioVolumeIndication(IRtcEngineEventHandler.AudioVolumeInfo[] speakers, int totalVolume) {
        XLog.e("onRtcAudioVolumeIndication" + totalVolume);

    }

    @Override
    public void onRtcAudioRouteChanged(int routing) {
        XLog.e("onRtcAudioRouteChanged" + routing);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void finish() {
        super.finish();
        rtcEngine().leaveChannel();

        if (mMessageManager != null) {
            mMessageManager.removeMessageHandler(this);
            mMessageManager.leaveChannel(new ResultCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }

                @Override
                public void onFailure(ErrorInfo errorInfo) {

                }
            });
        }
    }
}