package heyong.intellectPinPang.soundnet;


import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;

import com.elvishew.xlog.XLog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ToastUtils;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.live.LiveRoomBean;
import heyong.intellectPinPang.bean.gsonbean.PostBean;
import heyong.intellectPinPang.bean.live.BeginLiveBroadCastBean;
import heyong.intellectPinPang.soundnet.actionsheet.BackgroundMusicActionSheet;
import heyong.intellectPinPang.soundnet.actionsheet.BeautySettingActionSheet;
import heyong.intellectPinPang.soundnet.actionsheet.GiftActionSheet;
import heyong.intellectPinPang.soundnet.actionsheet.LiveRoomSettingActionSheet;
import heyong.intellectPinPang.soundnet.actionsheet.LiveRoomUserListActionSheet;
import heyong.intellectPinPang.soundnet.actionsheet.VoiceActionSheet;
import heyong.intellectPinPang.soundnet.actionsheet.toolactionsheet.LiveRoomToolActionSheet;
import heyong.intellectPinPang.soundnet.components.GiftAnimWindow;
import heyong.intellectPinPang.soundnet.components.LiveMessageEditLayout;
import heyong.intellectPinPang.soundnet.components.LiveRoomMessageList;
import heyong.intellectPinPang.soundnet.components.LiveRoomUserLayout;
import heyong.intellectPinPang.soundnet.components.RtcStatsView;
import heyong.intellectPinPang.soundnet.components.bottomLayout.LiveBottomButtonLayout;
import heyong.intellectPinPang.soundnet.framework.RtcVideoConsumer;
import heyong.intellectPinPang.soundnet.vlive.Config;
import heyong.intellectPinPang.soundnet.vlive.agora.rtm.model.GiftRankMessage;
import heyong.intellectPinPang.soundnet.vlive.agora.rtm.model.NotificationMessage;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.model.UserProfile;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.request.Request;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.AudienceListResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.CreateRoomResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.EnterRoomResponse;
import heyong.intellectPinPang.soundnet.vlive.utils.GiftUtil;
import heyong.intellectPinPang.soundnet.vlive.utils.Global;
import io.agora.capture.video.camera.VideoModule;
import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.models.ChannelMediaOptions;
import io.agora.rtc.models.ClientRoleOptions;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;

/**
 * 观看直播间
 */
public abstract class LiveRoomActivity extends LiveBaseActivity implements
        BeautySettingActionSheet.BeautyActionSheetListener,
        LiveRoomSettingActionSheet.LiveRoomSettingActionSheetListener,
        BackgroundMusicActionSheet.BackgroundMusicActionSheetListener,
        GiftActionSheet.GiftActionSheetListener,
        LiveRoomToolActionSheet.LiveRoomToolActionSheetListener,
        VoiceActionSheet.VoiceActionSheetListener,
        LiveBottomButtonLayout.LiveBottomButtonListener,
        TextView.OnEditorActionListener,
        LiveRoomUserLayout.UserLayoutListener,
        LiveRoomUserListActionSheet.OnUserSelectedListener {

    private static final String TAG = LiveRoomActivity.class.getSimpleName();
    private static final int IDEAL_MIN_KEYBOARD_HEIGHT = 200;
    private static final int MIN_ONLINE_MUSIC_INTERVAL = 100;

    private Rect mDecorViewRect;
    private int mInputMethodHeight;

    // UI components of a live room
    protected LiveRoomUserLayout participants;
    protected LiveRoomMessageList messageList;
    protected LiveBottomButtonLayout bottomButtons;
    protected LiveMessageEditLayout messageEditLayout;
    protected AppCompatEditText messageEditText;
    protected RtcStatsView rtcStatsView;
    protected Dialog curDialog;

    protected InputMethodManager inputMethodManager;

    private LiveRoomUserListActionSheet mRoomUserActionSheet;

    // Rtc Engine requires that the calls of startAudioMixing
    // should not be too frequent if online musics are played.
    // The interval is better not to be fewer than 100 ms.
    private volatile long mLastMusicPlayedTimeStamp;

    private boolean mActivityFinished;
    protected boolean inEarMonitorEnabled;
    private boolean mHeadsetWithMicrophonePlugged;
    private String matchId = "";

    private BroadcastReceiver mHeadPhoneReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (AudioManager.ACTION_HEADSET_PLUG.equals(action)) {
                boolean plugged = intent.getIntExtra("state", -1) == 1;
                boolean hasMic = intent.getIntExtra("microphone", -1) == 1;
                mHeadsetWithMicrophonePlugged = plugged && hasMic;
                Log.e(TAG, "Wired headset is plugged：" + mHeadsetWithMicrophonePlugged);
            }
        }
    };

    private NetworkReceiver mNetworkReceiver = new NetworkReceiver();
    boolean isBrocastReceiver;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().getViewTreeObserver()
                .addOnGlobalLayoutListener(this::detectKeyboardLayout);

        inputMethodManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        IntentFilter headPhoneFilter = new IntentFilter();
        headPhoneFilter.addAction(AudioManager.ACTION_HEADSET_PLUG);
        registerReceiver(mHeadPhoneReceiver, headPhoneFilter);

    }

    @Override
    protected void onPermissionGranted() {
        matchId = getIntent().getStringExtra(Global.Constants.KEY_MATCH_ID);
        isBrocastReceiver = getIntent().getBooleanExtra(Global.Constants.KEY_CREATE_ROOM, false);
        if (isBrocastReceiver) {
            createRoom();
        } else {
            enterRoom();
        }
    }

    private void detectKeyboardLayout() {
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        if (mDecorViewRect == null) {
            mDecorViewRect = rect;
        }

        int diff = mDecorViewRect.height() - rect.height();

        // The global layout listener may be invoked several
        // times when the activity is launched, we need to care
        // about the value of detected input method height to
        // filter out the cases that are not desirable.
        if (diff == mInputMethodHeight) {
            // The input method is still shown
            return;
        }

        if (diff > IDEAL_MIN_KEYBOARD_HEIGHT && mInputMethodHeight == 0) {
            mInputMethodHeight = diff;
            onInputMethodToggle(true, diff);
        } else if (mInputMethodHeight > 0) {
            onInputMethodToggle(false, mInputMethodHeight);
            mInputMethodHeight = 0;
        }
    }

    protected void onInputMethodToggle(boolean shown, int height) {
        RelativeLayout.LayoutParams params =
                (RelativeLayout.LayoutParams) messageEditLayout.getLayoutParams();
        int change = shown ? height : -height;
        params.bottomMargin += change;
        messageEditLayout.setLayoutParams(params);

        if (shown) {
            messageEditText.requestFocus();
            messageEditText.setOnEditorActionListener(this);
        } else {
            messageEditLayout.setVisibility(View.GONE);
        }
    }

    //开播
    private void createRoom() {
        PostBean postBean = new PostBean();
        postBean.setId("" + matchId);
        String params = new Gson().toJson(postBean);
        sendMyRequest(Request.PRODUCT_BEGIN_LIVE, params);
    }


    @Override
    public void onCreateRoomResponse(CreateRoomResponse response) {
    }

    //进入直播间
    protected void enterRoom() {

        PostBean postBean = new PostBean();
        postBean.setId("" + matchId);
        String params = new Gson().toJson(postBean);
        sendMyRequest(Request.PRODUCT_INTO_LIVE, params);

    }

    @Override
    public void stopCloudRecording(ResponseBean body1) {

    }

    //进入直播间
    @Override
    public void getIntoLiveBroadcastingRoomResponse(ResponseBean<LiveRoomBean> response) {
        if (response.getErrorCode().equals("200")) {
            XLog.e("role===" + myRtcRole + " " + "进入直播间 gson===" + new Gson().toJson(response.getData()));
            LiveRoomBean data = response.getData();
            if (data != null) {
                rtcChannelName = data.getChannelName();
                roomName = data.getChannelName();
                ClientRoleOptions clientRoleOptions = new ClientRoleOptions();
                clientRoleOptions.audienceLatencyLevel = Constants.AUDIENCE_LATENCY_LEVEL_LOW_LATENCY;
                rtcEngine().setClientRole(myRtcRole, clientRoleOptions);
                rtcEngine().setVideoSource(new RtcVideoConsumer(VideoModule.instance()));
                setVideoConfiguration();
                String tokens = "" + data.getToken();
                String channelId = "" + data.getChannelName();

                ChannelMediaOptions option = new ChannelMediaOptions();
                option.autoSubscribeAudio = true;
                option.autoSubscribeVideo = true;
                rtcEngine().joinChannel(tokens,
                        channelId, "Extra Optional Data", data.getUserId(), option);


            }
        } else {
            ToastUtils.showToast(this, "" + response.getMessage());
        }
    }

    //开始直播
    @Override
    public void beginLiveBroadcastResponse(ResponseBean<BeginLiveBroadCastBean> response) {
        XLog.e("role===" + myRtcRole + " " + "开始直播 gson===" + new Gson().toJson(response.getData()));

        if (response.getErrorCode().equals("200")) {
            BeginLiveBroadCastBean data = response.getData();
            if (data != null) {
                rtcChannelName = data.getChannelName();
                roomName = data.getChannelName();

                rtcEngine().setClientRole(myRtcRole);
                rtcEngine().setVideoSource(new RtcVideoConsumer(VideoModule.instance()));
                setVideoConfiguration();
                String tokens = "" + data.getToken();
                String channelId = "" + data.getChannelName();

//                String tokens = ""+data.getToken();
//                String channelId = "" + data.getChannelName();
                ChannelMediaOptions option = new ChannelMediaOptions();
                option.autoSubscribeAudio = true;
                option.autoSubscribeVideo = true;

                rtcEngine().joinChannel(tokens,
                        channelId, "Extra Optional Data", data.getOptionalUid(), option);

            }
        } else {
            ToastUtils.showToast(this, "" + response.getMessage());

        }
    }

    //结束直播
    @Override
    public void endLiveBroadcastResponse(ResponseBean body1) {
//        finish();
//        closeDialog();
//        dismissActionSheetDialog();

    }

    @Override
    public void leaveLiveBroadcastingRoom(ResponseBean body) {
        finish();
        closeDialog();
        dismissActionSheetDialog();
    }

    //enterRoom
    @Override
    public void onEnterRoomResponse(EnterRoomResponse response) {

    }

    private void initUserCount(final int total, final List<EnterRoomResponse.RankInfo> rankUsers) {
        runOnUiThread(() -> participants.reset(total, rankUsers));
    }

    @Override
    public void onActionSheetBeautyEnabled(boolean enabled) {
        if (bottomButtons != null) bottomButtons.setBeautyEnabled(enabled);
        enablePreProcess(enabled);
    }

    @Override
    public void onActionSheetBlurSelected(float blur) {
        setBlurValue(blur);
    }

    @Override
    public void onActionSheetWhitenSelected(float whiten) {
        setWhitenValue(whiten);
    }

    @Override
    public void onActionSheetCheekSelected(float cheek) {
        setCheekValue(cheek);
    }

    @Override
    public void onActionSheetEyeEnlargeSelected(float eye) {
        setEyeValue(eye);
    }

    @Override
    public void onActionSheetResolutionSelected(int index) {
        config().setResolutionIndex(index);
        setVideoConfiguration();
    }

    @Override
    public void onActionSheetFrameRateSelected(int index) {
        config().setFrameRateIndex(index);
        setVideoConfiguration();
    }

    @Override
    public void onActionSheetBitrateSelected(int bitrate) {
        config().setVideoBitrate(bitrate);
        setVideoConfiguration();
    }

    @Override
    public void onActionSheetSettingBackPressed() {
        dismissActionSheetDialog();
    }

    @Override
    public void onActionSheetMusicSelected(int index, String name, String url) {
        long now = System.currentTimeMillis();
        if (now - mLastMusicPlayedTimeStamp > MIN_ONLINE_MUSIC_INTERVAL) {
            rtcEngine().startAudioMixing(url, false, false, -1);
            if (bottomButtons != null) bottomButtons.setMusicPlaying(true);
            mLastMusicPlayedTimeStamp = now;
        }
    }

    @Override
    public void onActionSheetMusicStopped() {
        rtcEngine().stopAudioMixing();
        if (bottomButtons != null) bottomButtons.setMusicPlaying(false);
    }

    @Override
    public void onActionSheetGiftSend(String name, int index, int value) {
        dismissActionSheetDialog();
//        SendGiftRequest request = new SendGiftRequest(config().
//                getUserProfile().getToken(), roomId, index);
//        sendRequest(Request.SEND_GIFT, request);
    }

    /**
     * @param monitor the ideal monitoring state to be checked
     * @return true if the current audio route is wired or wire-less
     * headset with microphone, the audio route can be enabled.
     * Returns true if the state is allowed to be changed.
     */
    @Override
    public boolean onActionSheetEarMonitoringClicked(boolean monitor) {
        if (monitor) {
            if (mHeadsetWithMicrophonePlugged) {
                rtcEngine().enableInEarMonitoring(true);
                inEarMonitorEnabled = true;
                return true;
            } else {
                showShortToast(getResources().getString(R.string.in_ear_monitoring_failed));
                // In ear monitor state does not change here.
                return false;
            }
        } else {
            rtcEngine().enableInEarMonitoring(false);
            // It is always allowed to disable the in-ear monitoring.
            inEarMonitorEnabled = false;
            return true;
        }
    }

    @Override
    public void onActionSheetRealDataClicked() {
        if (rtcStatsView != null) {
            runOnUiThread(() -> {
                int visibility = rtcStatsView.getVisibility();
                if (visibility == View.VISIBLE) {
                    rtcStatsView.setVisibility(View.GONE);
                } else if (visibility == View.GONE) {
                    rtcStatsView.setVisibility(View.VISIBLE);
                    rtcStatsView.setLocalStats(0, 0, 0, 0);
                }

                // Only clicking data button will dismiss
                // the action sheet dialog.
                dismissActionSheetDialog();
            });
        }
    }

    @Override
    public void onActionSheetSettingClicked() {
        showActionSheetDialog(ACTION_SHEET_VIDEO, tabIdToLiveType(), isHost, false, this);
    }

    @Override
    public void onActionSheetRotateClicked() {
        switchCamera();
    }

    @Override
    public void onActionSheetVideoClicked(boolean muted) {
        if (isHost || isOwner) {
            rtcEngine().muteLocalVideoStream(muted);
            config().setVideoMuted(muted);
        }
    }

    @Override
    public void onActionSheetSpeakerClicked(boolean muted) {
        if (isHost || isOwner) {
            rtcEngine().muteLocalAudioStream(muted);
            config().setAudioMuted(muted);
        }
    }

    @Override
    public void onActionSheetAudioRouteSelected(int type) {

    }

    @Override
    public void onActionSheetAudioRouteEnabled(boolean enabled) {

    }

    @Override
    public void onActionSheetAudioBackPressed() {

        dismissActionSheetDialog();
    }

    @Override
    public void onLiveBottomLayoutShowMessageEditor() {
        if (messageEditLayout != null) {
            messageEditLayout.setVisibility(View.VISIBLE);
            messageEditText.requestFocus();
            inputMethodManager.showSoftInput(messageEditText, 0);
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            Editable editable = messageEditText.getText();
            if (TextUtils.isEmpty(editable)) {
                showShortToast(getResources().getString(R.string.live_send_empty_message));
            } else {
                sendChatMessage(editable.toString());
                messageEditText.setText("");
            }

            inputMethodManager.hideSoftInputFromWindow(messageEditText.getWindowToken(), 0);
            return true;
        }
        return false;
    }

    private void sendChatMessage(String content) {
        Log.e(TAG, "sendChatMessage: sendChatMessage=-===" + content);
        Config.UserProfile profile = config().getUserProfile();
        getMessageManager().sendChatMessage(profile.getUserId(),
                profile.getUserName(), content, new ResultCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }

                    @Override
                    public void onFailure(ErrorInfo errorInfo) {

                    }
                });
        messageList.addMessage(LiveRoomMessageList.MSG_TYPE_CHAT, profile.getUserName(), content);
    }

    protected boolean isCurDialogShowing() {
        return curDialog != null && curDialog.isShowing();
    }

    protected void closeDialog() {
        if (isCurDialogShowing()) {
            curDialog.dismiss();
        }
    }

    @Override
    public void onUserLayoutShowUserList(View view) {
        // Show all user info list
//        mRoomUserActionSheet = (LiveRoomUserListActionSheet)
//                showActionSheetDialog(ACTION_SHEET_ROOM_USER, tabIdToLiveType(tabId), isHost, true, this);
//        mRoomUserActionSheet.setup(proxy(), this, roomId, config().getUserProfile().getToken());
//        mRoomUserActionSheet.requestMoreAudience();
    }

    @Override
    public void onAudienceListResponse(AudienceListResponse response) {
        List<UserProfile> userList = new ArrayList<>();
        for (AudienceListResponse.AudienceInfo info : response.data.list) {
            UserProfile profile = new UserProfile();
            profile.setUserId(info.userId);
            profile.setUserName(info.userName);
            profile.setAvatar(info.avatar);
            userList.add(profile);
        }

        if (mRoomUserActionSheet != null && mRoomUserActionSheet.getVisibility() == View.VISIBLE) {
            runOnUiThread(() -> mRoomUserActionSheet.appendUsers(userList));
        }
    }

    @Override
    public void onActionSheetUserListItemSelected(String userId, String userName) {
        // Called when clicking an online user's name, and want to see the detail
    }

    @Override
    public void onRtmChannelMessageReceived(String peerId, String nickname, String content) {
        runOnUiThread(() -> messageList.addMessage(LiveRoomMessageList.MSG_TYPE_CHAT, nickname, content));
    }

    @Override
    public void onRtmRoomGiftRankChanged(int total, List<GiftRankMessage.GiftRankItem> list) {
        // The rank of user sending gifts has changed. The client
        // needs to update UI in this callback.
        if (list == null) return;

        List<EnterRoomResponse.RankInfo> rankList = new ArrayList<>();
        for (GiftRankMessage.GiftRankItem item : list) {
            EnterRoomResponse.RankInfo info = new EnterRoomResponse.RankInfo();
            info.userId = item.userId;
            info.userName = item.userName;
            info.avatar = item.avatar;
            rankList.add(info);
        }

        runOnUiThread(() -> participants.reset(rankList));
    }

    @Override
    public void onRtmGiftMessage(String fromUserId, String fromUserName, String toUserId, String toUserName, int giftId) {
        runOnUiThread(() -> {
            String from = TextUtils.isEmpty(fromUserName) ? fromUserId : fromUserName;
            String to = TextUtils.isEmpty(toUserName) ? toUserId : toUserName;
            messageList.addMessage(LiveRoomMessageList.MSG_TYPE_GIFT, from, to, giftId);

            GiftAnimWindow window = new GiftAnimWindow(LiveRoomActivity.this, R.style.gift_anim_window);
            window.setAnimResource(GiftUtil.getGiftAnimRes(giftId));
            window.show();
        });
    }

    @Override
    public void onRtmChannelNotification(int total, List<NotificationMessage.NotificationItem> list) {
        // User enter & leave notifications.
        runOnUiThread(() -> {
            // update room user count
            participants.reset(total);
            for (NotificationMessage.NotificationItem item : list) {
                messageList.addMessage(LiveRoomMessageList.MSG_TYPE_SYSTEM, item.userName, "", item.state);
            }
        });
    }

    @Override
    public void onRtmLeaveMessage() {
        runOnUiThread(this::leaveRoom);
    }

    @Override
    public void onStart() {
        super.onStart();
        if ((isOwner || isHost) && !config().isVideoMuted()) {
            startCameraCapture();
        }
    }

    @Override
    public void onRtcJoinChannelSuccess(String channel, int uid, int elapsed) {
        XLog.e("onRtcJoinChannelSuccess:" + channel + " uid:" + (uid & 0xFFFFFFFFL));
    }

    @Override
    public void onRtcRemoteVideoStateChanged(int uid, int state, int reason, int elapsed) {
        XLog.e("onRtcRemoteVideoStateChanged: " + (uid & 0xFFFFFFFFL) +
                " state:" + state + " reason:" + reason);
    }

    @Override
    public void onUserOffline(int uid, int reason) {
        XLog.e("onUserOffline: uid===" + (uid & 0xFFFFFFFFL) +
                " reason:" + reason);
//        ToastUtils.showToast(LiveRoomActivity.this, "即将退出直播间");
//        CountDownTimer countDownTimer = new CountDownTimer(2000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//            }
//
//            @Override
//            public void onFinish() {
                finish();
//            }
//        }.start();
    }

    @Override
    public void onUserJoined(int uid, int elapsed) {
        XLog.e("onUserJoined: uid===" + (uid & 0xFFFFFFFFL) +
                " elapsed:" + elapsed);
    }

    @Override
    public void onRtcStats(IRtcEngineEventHandler.RtcStats stats) {
        runOnUiThread(() -> {
            if (rtcStatsView != null && rtcStatsView.getVisibility() == View.VISIBLE) {
                rtcStatsView.setLocalStats(stats.rxKBitRate,
                        stats.rxPacketLossRate, stats.txKBitRate,
                        stats.txPacketLossRate);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if ((isHost || isOwner) && !config().isVideoMuted()
                && !mActivityFinished) {
            // If now the app goes to background, stop the camera
            // capture if the host is displaying his video.
            stopCameraCapture();
        }
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    private void showExitDialog() {
        int titleRes;
        int messageRes;
        if (isBrocastReceiver) {
            titleRes = R.string.end_live_streaming_title_owner;
            messageRes = R.string.end_live_streaming_message_owner;
        } else {
            titleRes = R.string.finish_broadcast_title_audience;
            messageRes = R.string.finish_broadcast_message_audience;
        }
        curDialog = showDialog(titleRes, messageRes, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XLog.e("离开房间");
                curDialog.dismiss();
                leaveRoom();
            }
        });
    }

    protected void leaveRoom() {
        if (isBrocastReceiver) {
            leaveEndRoom();
            XLog.e("leaveEndRoom");

        } else {
            leaveClass();
            XLog.e("leaveClass");

        }

    }

    //结束直播   离开直播
    protected void leaveEndRoom() {
        //调用结束云端录制的接口
        PostBean postBean = new PostBean();
        postBean.setId("" + matchId);
        String params = new Gson().toJson(postBean);
        sendMyRequest(Request.PRODUCT_CLOSE_CLOUD_RECORD, params);


    }

    //离开直播间
    protected void leaveClass() {
        PostBean postBean = new PostBean();
        postBean.setId("" + matchId);
        String params = new Gson().toJson(postBean);
        sendMyRequest(Request.PRODUCT_LEAVE_LIVE, params);

    }

    @Override
    public void finish() {
        super.finish();
        mActivityFinished = true;
        stopCameraCapture();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mHeadPhoneReceiver);
    }


    @Override
    public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mNetworkReceiver);
    }

    protected static class NetworkReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context.
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) return;

            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null || !info.isAvailable() || !info.isConnected()) {
                Toast.makeText(context, R.string.network_unavailable, Toast.LENGTH_SHORT).show();
            } else {
                int type = info.getType();
                if (ConnectivityManager.TYPE_WIFI == type) {
                    Toast.makeText(context, R.string.network_switch_to_wifi, Toast.LENGTH_SHORT).show();
                } else if (ConnectivityManager.TYPE_MOBILE == type) {
                    Toast.makeText(context, R.string.network_switch_to_mobile, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
