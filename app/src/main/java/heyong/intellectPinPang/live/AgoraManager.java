package heyong.intellectPinPang.live;

import android.content.Context;
import android.util.SparseArray;
import android.view.SurfaceView;

import com.elvishew.xlog.XLog;

import heyong.intellectPinPang.R;
import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;

public class AgoraManager {
    private static AgoraManager instance;

    private RtcEngine mRtcEngine;

    private SparseArray<SurfaceView> mSurfaceViews;

    private int localUid;

    private int remoteUid;

    private OnMediaListener listener;

    private AgoraManager() {

    }

    public static synchronized AgoraManager getInstance() {
        if (null == instance) {
            synchronized (AgoraManager.class) {
                if (null == instance) {
                    instance = new AgoraManager();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        try {
            mRtcEngine = RtcEngine.create(context, context.getResources().getString(R.string.agora_app_id), mHandler);
            mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION);
            mSurfaceViews = new SparseArray<>();
            XLog.e("初始化成功");
        } catch (Exception e) {
            XLog.e("初始化失败"+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 加入房间
     *
     * @param channelName
     * @param uid
     */
    public void joinChannel(String channelName, int uid) {
        mRtcEngine.joinChannel(null, channelName, null, uid);
    }

    /**
     * 设置本地视频
     *
     * @param context
     * @param uid
     */
    public void setupLocalVideo(Context context, int uid) {
        localUid = uid;
        SurfaceView surfaceView = RtcEngine.CreateRendererView(context);
        surfaceView.setZOrderMediaOverlay(true);
        mRtcEngine.setupLocalVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid));
        mSurfaceViews.put(uid, surfaceView);
    }

    /**
     * 设置远端视频
     *
     * @param context
     * @param uid
     */
    public void setupRemoteVideo(Context context, int uid) {
        remoteUid = uid;
        SurfaceView surfaceView = RtcEngine.CreateRendererView(context);
        mRtcEngine.setupRemoteVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid));
        mSurfaceViews.put(uid, surfaceView);
    }

    /**
     * 离开房间
     */
    public void leaveChannel() {
        mRtcEngine.leaveChannel();
    }

    /**
     * 配置适配
     */
    public void setVideoConfig() {
        mRtcEngine.enableVideo();
        VideoEncoderConfiguration.ORIENTATION_MODE orientationMode =
                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_ADAPTIVE;

        VideoEncoderConfiguration.VideoDimensions dimensions =
                new VideoEncoderConfiguration.VideoDimensions(360, 640);

        VideoEncoderConfiguration.FRAME_RATE frameRate =
                VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_30;

        VideoEncoderConfiguration videoEncoderConfiguration =
                new VideoEncoderConfiguration(dimensions, frameRate, VideoEncoderConfiguration.STANDARD_BITRATE, orientationMode);
        mRtcEngine.setVideoEncoderConfiguration(videoEncoderConfiguration);
    }

    /**
     * 获取本地视频
     *
     * @return
     */
    public SurfaceView getLocalSurfaceView() {
        return mSurfaceViews.get(localUid);
    }

    /**
     * 获取远端视频
     *
     * @return
     */
    public SurfaceView getRemoteSurfaceView() {
        return mSurfaceViews.get(remoteUid);
    }

    /**
     * 回调接口
     */
    private IRtcEngineEventHandler mHandler = new IRtcEngineEventHandler() {
        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            if (null != listener) {
                listener.onJoinSuccess(channel, uid, elapsed);
            }
        }

        @Override
        public void onRejoinChannelSuccess(String channel, int uid, int elapsed) {
            if (null != listener) {
                listener.onRejoinSuccess(channel, uid, elapsed);
            }
        }

        @Override
        public void onLeaveChannel(RtcStats stats) {
            if (null != listener) {
                listener.onLeaveChannel(stats);
            }
        }

        @Override
        public void onUserJoined(int uid, int elapsed) {
            if (null != listener) {
                listener.onUserJoined(uid, elapsed);
            }
        }

        @Override
        public void onUserOffline(int uid, int reason) {
            if (null != listener) {
                listener.onUserOffline(uid, reason);
            }
        }
    };

    /**
     * 通话时的接口
     */
    public interface OnMediaListener {

        void onJoinSuccess(String channel, int uid, int elapsed);

        void onRejoinSuccess(String channel, int uid, int elapsed);

        void onLeaveChannel(IRtcEngineEventHandler.RtcStats stats);

        void onUserJoined(int uid, int elapsed);

        void onUserOffline(int uid, int reason);
    }

    /**
     * 设置通话时的接口回调
     *
     * @param listener
     */
    public void setOnMedialistener(OnMediaListener listener) {
        this.listener = listener;
    }
}
