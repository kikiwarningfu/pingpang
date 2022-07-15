package heyong.intellectPinPang.soundnet.vlive.agora;

import android.content.Context;

import androidx.annotation.NonNull;

import com.elvishew.xlog.XLog;

import heyong.intellectPinPang.SampleApplicationLike;
import heyong.intellectPinPang.soundnet.vlive.agora.rtc.AgoraRtcHandler;
import heyong.intellectPinPang.soundnet.vlive.agora.rtc.RtcEventHandler;
import heyong.intellectPinPang.soundnet.vlive.agora.rtm.RtmMessageManager;
import heyong.intellectPinPang.soundnet.vlive.utils.UserUtil;
import io.agora.rtc.Constants;
import io.agora.rtc.RtcEngine;
import io.agora.rtm.RtmClient;


public class AgoraEngine {

    private RtcEngine mRtcEngine;
    private AgoraRtcHandler mRtcEventHandler = new AgoraRtcHandler();

    private RtmClient mRtmClient;

    public AgoraEngine(@NonNull Context application, String appId) {
        try {
            mRtcEngine = RtcEngine.create(application, appId, mRtcEventHandler);
            mRtcEngine.enableVideo();
            mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING);
            mRtcEngine.enableDualStreamMode(false);
            mRtcEngine.setLogFile(UserUtil.rtcLogFilePath(application));

            mRtmClient = RtmClient.createInstance(application, appId, RtmMessageManager.instance());
            mRtmClient.setLogFile(UserUtil.rtmLogFilePath(application));
            XLog.e("引擎初始化成功");
        } catch (Exception e) {
            XLog.e("引擎初始化失败"+e.getMessage());
            e.printStackTrace();
        }
    }

    public RtcEngine rtcEngine() {
        return mRtcEngine;
    }

    public RtmClient rtmClient() {
        return mRtmClient;
    }

    public void registerRtcHandler(RtcEventHandler handler) {
        if (mRtcEventHandler != null) mRtcEventHandler.registerEventHandler(handler);
    }

    public void removeRtcHandler(RtcEventHandler handler) {
        if (mRtcEventHandler != null) mRtcEventHandler.removeEventHandler(handler);
    }

    public void release() {
        if (mRtcEngine != null) RtcEngine.destroy();
        if (mRtmClient != null) {
            mRtmClient.logout(null);
            mRtmClient.release();
        }
    }
}
