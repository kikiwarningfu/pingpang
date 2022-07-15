package heyong.intellectPinPang.soundnet.vlive.agora.rtc;

import com.elvishew.xlog.XLog;

import java.util.ArrayList;
import java.util.List;

import io.agora.rtc.IRtcEngineEventHandler;

public class AgoraRtcHandler extends IRtcEngineEventHandler {
    private List<RtcEventHandler> mHandlers;

    public AgoraRtcHandler() {
        mHandlers = new ArrayList<>();
    }

    public void registerEventHandler(RtcEventHandler handler) {

        if (!mHandlers.contains(handler)) {
            mHandlers.add(handler);
        }
    }

    public void removeEventHandler(RtcEventHandler handler) {

        mHandlers.remove(handler);
    }

    @Override
    public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
        XLog.e("mHandlers   onJoinChannelSuccess");

        for (RtcEventHandler handler : mHandlers) {
            handler.onRtcJoinChannelSuccess(channel, uid, elapsed);
        }
    }

    @Override
    public void onRemoteVideoStateChanged(int uid, int state, int reason, int elapsed) {
        XLog.e("mHandlers   onRemoteVideoStateChanged");

        for (RtcEventHandler handler : mHandlers) {
            handler.onRtcRemoteVideoStateChanged(uid, state, reason, elapsed);
        }
    }

    @Override
    public void onRtcStats(RtcStats stats) {
//        XLog.e("mHandlers   onRtcStats");

        for (RtcEventHandler handler : mHandlers) {
            handler.onRtcStats(stats);
        }
    }

    @Override
    public void onChannelMediaRelayStateChanged(int state, int code) {
        XLog.e("mHandlers   onChannelMediaRelayStateChanged");

        for (RtcEventHandler handler : mHandlers) {
            handler.onRtcChannelMediaRelayStateChanged(state, code);
        }
    }

    @Override
    public void onChannelMediaRelayEvent(int code) {
        XLog.e("mHandlers   onChannelMediaRelayEvent");

        for (RtcEventHandler handler : mHandlers) {
            handler.onRtcChannelMediaRelayEvent(code);
        }
    }

    @Override
    public void onAudioVolumeIndication(AudioVolumeInfo[] speakers, int totalVolume) {
        XLog.e("mHandlers   onAudioVolumeIndication");

        for (RtcEventHandler handler : mHandlers) {
            handler.onRtcAudioVolumeIndication(speakers, totalVolume);
        }
    }

    @Override
    public void onAudioRouteChanged(int routing) {
        XLog.e("mHandlers   onAudioRouteChanged");

        for (RtcEventHandler handler : mHandlers) {
            handler.onRtcAudioRouteChanged(routing);
        }
    }

    @Override
    public void onUserJoined(int uid, int elapsed) {

        for (RtcEventHandler handler : mHandlers) {
            handler.onUserJoined(uid, elapsed);
        }

    }

    @Override
    public void onUserOffline(int uid, int reason) {
        XLog.e("mHandlers   onUserOffline");
        for (RtcEventHandler handler : mHandlers) {
            handler.onUserOffline(uid, reason);
        }

    }

    @Override
    public void onConnectionBanned() {
        XLog.e("mHandlers   onConnectionBanned");
        super.onConnectionBanned();
        for (RtcEventHandler handler : mHandlers) {
            handler.onConnectionBanned();
        }
    }
}
