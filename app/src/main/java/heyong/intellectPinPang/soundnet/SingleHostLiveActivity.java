package heyong.intellectPinPang.soundnet;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.elvishew.xlog.XLog;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ToastUtils;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.live.LiveRoomBean;
import heyong.intellectPinPang.bean.gsonbean.PostBean;
import heyong.intellectPinPang.bean.live.BeginLiveBroadCastBean;
import heyong.intellectPinPang.event.SwipMessageEvent;
import heyong.intellectPinPang.live.activity.LiveEndActivity;
import heyong.intellectPinPang.live.activity.PayLiveChargeActivity;
import heyong.intellectPinPang.soundnet.actionsheet.toolactionsheet.LiveRoomToolActionSheet;
import heyong.intellectPinPang.soundnet.components.CameraTextureView;
import heyong.intellectPinPang.soundnet.components.bottomLayout.LiveBottomButtonLayout;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.request.Request;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.EnterRoomResponse;
import heyong.intellectPinPang.soundnet.vlive.utils.Global;
import heyong.intellectPinPang.utils.Utils;
import io.agora.rtc.Constants;


public class SingleHostLiveActivity extends LiveRoomActivity implements View.OnClickListener {
    private static final String TAG = SingleHostLiveActivity.class.getSimpleName();

    private FrameLayout mVideoLayout;
    ImageView ivFinishLogo;
    private RelativeLayout rlTravierView;
    private LinearLayout llNoPursaceView;
    private TextView tvBuyOrder, tvCancelOrder, tvNoPursaceViewBuy;

    Handler handler = new Handler();
    private String matchId;
    private ImageView ivSwitchCamera;
    TextView tvRoomName;
    TextView tvSeeNum;
    TextView tvAlreadyEnd;
    TextView tvLeftName, tvRightName;
    CountDownTimer countDownTimerTips;
    LinearLayout llContentTips;

    public static void goActivity(Context context, boolean isCreateer, String matchId) {
        Intent intent = new Intent(context, SingleHostLiveActivity.class);
        intent.putExtra(Global.Constants.TAB_KEY, 1);
        intent.putExtra(Global.Constants.KEY_IS_ROOM_OWNER, isCreateer);
        intent.putExtra(Global.Constants.KEY_CREATE_ROOM, isCreateer);
        intent.putExtra(Global.Constants.KEY_MATCH_ID, matchId);
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar(false);
    }

    @Override
    protected void onPermissionGranted() {
        initUI();
        super.onPermissionGranted();
    }

    private void initUI() {

        setContentView(R.layout.activity_single_host);
        EventBus.getDefault().register(this);
        matchId = getIntent().getStringExtra(Global.Constants.KEY_MATCH_ID);

        tvSeeNum = findViewById(R.id.tv_see_num);
        ivFinishLogo = findViewById(R.id.iv_finish_logo);
        llContentTips = findViewById(R.id.ll_content_tips);
        tvRoomName = findViewById(R.id.tv_room_name);
        rlTravierView = findViewById(R.id.rl_travier_view);
        tvBuyOrder = findViewById(R.id.tv_buy_order);
        tvCancelOrder = findViewById(R.id.tv_cancel_order);
        llNoPursaceView = findViewById(R.id.ll_no_pursace_view);
        tvAlreadyEnd = findViewById(R.id.tv_already_end);
        tvNoPursaceViewBuy = findViewById(R.id.tv_no_pursace_view_buy);
        ivSwitchCamera = findViewById(R.id.iv_switch_camera);
        tvLeftName = findViewById(R.id.tv_left_name);
        tvRightName = findViewById(R.id.tv_right_name);

        ivSwitchCamera.setOnClickListener(this);
        ivFinishLogo.setOnClickListener(this);
        llNoPursaceView.setOnClickListener(this);
        tvNoPursaceViewBuy.setOnClickListener(this);
        tvAlreadyEnd.setVisibility(View.GONE);
        tvBuyOrder.setOnClickListener(this);
        tvCancelOrder.setOnClickListener(this);
        Log.e(TAG, "initUI:setRole=== " + (isOwner ? LiveBottomButtonLayout.ROLE_OWNER :
                isHost ? LiveBottomButtonLayout.ROLE_HOST :
                        LiveBottomButtonLayout.ROLE_AUDIENCE));
        mVideoLayout = findViewById(R.id.foreground_video);

        if (isOwner) {
            Log.e(TAG, "initUI: becomesOwner");
            becomesOwner(false, false);
        }
        onGlobalLayoutCompleted();
        isBrocastReceiver = getIntent().getBooleanExtra(Global.Constants.KEY_CREATE_ROOM, false);

        if (isBrocastReceiver) {
            ivSwitchCamera.setVisibility(View.VISIBLE);
            llNoPursaceView.setVisibility(View.GONE);
        } else {
            ivSwitchCamera.setVisibility(View.GONE);
            llNoPursaceView.setVisibility(View.VISIBLE);
        }
        countDownTimerTips = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                llContentTips.setVisibility(View.GONE);
            }
        }.start();
    }

    @Override
    protected void onGlobalLayoutCompleted() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_switch_camera:

                switchCamera();
                break;
            case R.id.iv_finish_logo:
                onBackPressed();
                break;
            case R.id.live_bottom_btn_more:

                LiveRoomToolActionSheet toolSheet = (LiveRoomToolActionSheet) showActionSheetDialog(
                        ACTION_SHEET_TOOL, tabIdToLiveType(), isOwner, true, this);
                toolSheet.setEnableInEarMonitoring(inEarMonitorEnabled);
                break;
            case R.id.live_bottom_btn_fun1:
                if (isHost || isOwner) {
                    showActionSheetDialog(ACTION_SHEET_BG_MUSIC, tabIdToLiveType(), true, true, this);
                } else {
                    showActionSheetDialog(ACTION_SHEET_GIFT, tabIdToLiveType(), false, true, this);
                }
                break;
            case R.id.live_bottom_btn_fun2:
                // this button is hidden when
                // current user is not host.
                if (isHost || isOwner) {
                    showActionSheetDialog(ACTION_SHEET_BEAUTY, tabIdToLiveType(), true, true, this);
                }
                break;
            case R.id.tv_buy_order:
                if (liveRoomData != null) {
                    long id = liveRoomData.getId();
                    PayLiveChargeActivity.Companion.startActivity(this, "" + id);
                }

                break;
            case R.id.tv_cancel_order:
                rlTravierView.setVisibility(View.GONE);
                break;
            case R.id.tv_no_pursace_view_buy:
                if (liveRoomData != null) {
                    long id = liveRoomData.getId();
                    PayLiveChargeActivity.Companion.startActivity(this, "" + id);
                }
                rlTravierView.setVisibility(View.VISIBLE);

//                setupRemotePreview();

                break;
            case R.id.ll_no_pursace_view:
//                removeRemotePreview();
//                rlTravierView.setVisibility(View.VISIBLE);


                break;
        }
    }

    @Override
    public void onEnterRoomResponse(EnterRoomResponse response) {
        super.onEnterRoomResponse(response);

    }

    @Override
    public void onResponseError(int requestType, String error, String message) {


    }

    //????????????
    @Override
    public void beginLiveBroadcastResponse(ResponseBean<BeginLiveBroadCastBean> body1) {
        super.beginLiveBroadcastResponse(body1);

        runOnUiThread(() -> {
            XLog.e("?????????????????????");
            if (isOwner) {
                XLog.e(TAG, "onEnterRoomResponse: becomesOwner2 ");
                becomesOwner(true, true);

                BeginLiveBroadCastBean data = body1.getData();
                if (data != null) {
                    tvRoomName.setText("????????????" + data.getChannelName());
//                    tvSeeNum.setText("" + Utils.getShowVisitor(Double.parseDouble(String.valueOf(data.getOnLineCount()))));


                    tvLeftName.setText("" + data.getLeftName());
                    tvRightName.setText("" + data.getRightName());

                }
            } else {
                XLog.e(TAG, "onEnterRoomResponse: becomeAudience ");
                becomeAudience();
            }
        });

    }

    @Override
    public void suspendLiveBroadcastResponse() {

    }

    @Override
    public void onUserOffline(int uid, int reason) {
        super.onUserOffline(uid, reason);
        tvAlreadyEnd.setVisibility(View.VISIBLE);

    }

    @Override
    public void onConnectionBanned() {
        XLog.e("onConnectionBanned  22222");
    }

    @Override
    public void continueLiveBroadcastResponse() {

    }

    @Override
    public void onRtcJoinChannelSuccess(String channel, int uid, int elapsed) {
        super.onRtcJoinChannelSuccess(channel, uid, elapsed);
        XLog.e("okhttp success" + "uid===" + uid);
//         intent.putExtra(Global.Constants.KEY_IS_ROOM_OWNER, isCreateer);
        boolean isCreater = getIntent().getBooleanExtra(Global.Constants.KEY_IS_ROOM_OWNER, false);
        if (isCreater) {

            //??????????????????
            PostBean postBean = new PostBean();
            postBean.setId("" + matchId);
            String params = new Gson().toJson(postBean);
            sendMyRequest(Request.PRODUCT_OPEN_CLOUD_RECORD, params);
        } else {
            XLog.e("????????? ?????????????????????");
        }
    }

    //????????????
    @Override
    public void endLiveBroadcastResponse(ResponseBean body1) {
        if (body1.getErrorCode().equals("200")) {
            finish();
        } else {
            ToastUtils.showToast(this, "" + body1.getMessage());
        }
    }

    //??????????????????
    @Override
    public void openCloudRecording(ResponseBean body1) {


    }

    //??????????????????
    @Override
    public void stopCloudRecording(ResponseBean body1) {
        super.stopCloudRecording(body1);
        LiveEndActivity.Companion.start(this, matchId, 1);
        finish();
    }

    @Override
    public void onUserJoined(int uid, int elapsed) {
        super.onUserJoined(uid, elapsed);
        XLog.e("??????????????????  uid====" + uid);

    }

//    state	??????????????????????????????
//    CONNECTION_STATE_DISCONNECTED(1)?????????????????????
//    CONNECTION_STATE_CONNECTING(2)????????????????????????
//    CONNECTION_STATE_CONNECTED(3)??????????????????
//    CONNECTION_STATE_RECONNECTING(4)??????????????????????????????
//    CONNECTION_STATE_FAILED(5)?????????????????????


//    reason	??????????????????????????????????????????????????????
//    CONNECTION_CHANGED_CONNECTING(0)????????????????????????
//    CONNECTION_CHANGED_JOIN_SUCCESS(1)?????????????????????
//    CONNECTION_CHANGED_INTERRUPTED(2)?????????????????????
//    CONNECTION_CHANGED_BANNED_BY_SERVER(3)????????????????????????????????????????????????????????????????????????????????????
//    CONNECTION_CHANGED_JOIN_FAILED(4)?????????????????????
//    CONNECTION_CHANGED_LEAVE_CHANNEL(5)???????????????
//    CONNECTION_CHANGED_INVALID_APP_ID(6)?????????????????? APP ID????????????????????? APP ID ??????????????????
//    CONNECTION_CHANGED_INVALID_CHANNEL_NAME(7)???????????????????????????????????????????????????????????????????????????
//    CONNECTION_CHANGED_INVALID_TOKEN(8)???????????? Token ?????????????????????????????????
//    ???????????????????????? App Certificate??????????????????????????? Token??????????????? App Certificate??????????????? Token
//    ????????? joinChannel ???????????????????????? uid ????????? Token ???????????? uid ?????????
//    CONNECTION_CHANGED_TOKEN_EXPIRED(9)?????????????????? Token ?????????????????????????????????????????????????????????????????? Token
//    CONNECTION_CHANGED_REJECTED_BY_SERVER(10)?????????????????????????????????????????????????????????
//    ??????????????????????????????????????????????????? API????????? joinChannel???
//    ??????????????? startEchoTest ?????????????????????????????????????????????????????????????????????????????????????????????
//    CONNECTION_CHANGED_SETTING_PROXY_SERVER(11)????????????????????????????????????SDK ????????????
//    CONNECTION_CHANGED_RENEW_TOKEN(12)????????? Token ??????????????????????????????
//    CONNECTION_CHANGED_CLIENT_IP_ADDRESS_CHANGED(13)???????????? IP ?????????????????????????????????????????????????????????????????? IP ???????????????????????????
//    CONNECTION_CHANGED_KEEP_ALIVE_TIMEOUT(14)???SDK ?????????????????????????????????????????????????????????


    @Override
    public void onRtmConnectionStateChanged(int state, int reason) {
        super.onRtmConnectionStateChanged(state, reason);
        XLog.e("state=====" + detalWithState(state) + "   reason====" + dealWithReason(reason));

    }

    public String dealWithReason(int reason) {
        String str = "";
        switch (reason) {
            case 0:
                str = "?????????????????????";
                break;
            case 1:
                str = "??????????????????";
                break;
            case 2:
                str = "??????????????????";
                break;
            case 3:
                str = "?????????????????????????????????????????????????????????????????????????????????";
                break;
            case 4:
                str = "??????????????????";
                break;
            case 5:
                str = "????????????";
                break;
            case 6:
                str = "??????????????? APP ID????????????????????? APP ID ??????????????????";
                break;
            case 7:
                str = "????????????????????????????????????????????????????????????????????????";
                break;
            case 8:
                str = "????????? Token ??????";
                break;
            case 9:
                str = "??????????????? Token ?????????????????????????????????????????????????????????????????? Token";
                break;
            case 10:
                str = "??????????????????????????????";
                break;
            case 11:
                str = "?????????????????????????????????SDK ????????????";
                break;
            case 12:
                str = "?????? Token ??????????????????????????????";
                break;
            case 13:
                str = "????????? IP ?????????????????????????????????????????????????????????????????? IP ???????????????????????????";
                break;
            case 14:
                str = "SDK ?????????????????????????????????????????????????????????";
                break;
        }
        return str;
    }

    public String detalWithState(int state) {
        String str = "";
        switch (state) {
            case 1:
                str = "??????????????????";
                break;
            case 2:
                str = "?????????????????????";
                break;
            case 3:
                str = "???????????????";
                break;
            case 4:
                str = "???????????????????????????";
                break;
            case 5:
                str = "??????????????????";
                break;
        }
        return str;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEvent(SwipMessageEvent event) {
        Log.e(TAG, "SwipMessageEvent:  ????????????");
        XLog.e("????????????");
        rlTravierView.setVisibility(View.GONE);
        llNoPursaceView.setVisibility(View.GONE);
        setupRemotePreview();
    }

    CountDownTimer timer;
    LiveRoomBean liveRoomData;

    //???????????????
    @Override
    public void getIntoLiveBroadcastingRoomResponse(ResponseBean<LiveRoomBean> body1) {
        super.getIntoLiveBroadcastingRoomResponse(body1);
        XLog.e("getIntoLiveBroadcastingRoomResponse");
        liveRoomData = body1.getData();
//        private String free;//??????????????????
//        private boolean purchased;//???????????????
//        private boolean canTry;//???????????????
//        private int surplusSecond;//??????????????????
        tvRoomName.setText("????????????" + liveRoomData.getChannelName());
        tvSeeNum.setText("" + Utils.getShowVisitor(Double.parseDouble(String.valueOf(liveRoomData.getOnLineCount()))));

        String itemType = liveRoomData.getItemType();
        if (itemType.equals("1") || itemType.equals("2")) {
            tvLeftName.setText("" + liveRoomData.getPlayer1());
            tvRightName.setText("" + liveRoomData.getPlayer3());
        } else {
            tvLeftName.setText("" + liveRoomData.getPlayer1() + " " + liveRoomData.getPlayer2());
            tvRightName.setText("" + liveRoomData.getPlayer3() + " " + liveRoomData.getPlayer4());
        }


        handler.post(new Runnable() {
            @Override
            public void run() {
                if (liveRoomData.isPurchased()) {
                    //??????????????? ????????????????????? ???????????????
                    rlTravierView.setVisibility(View.GONE);
                    llNoPursaceView.setVisibility(View.GONE);
                    if (timer != null) {
                        timer.cancel();
                    }
                    ownerRtcUid = liveRoomData.getOptionalUid();
                    becomeAudience();
                } else {
                    XLog.e("???????????? ????????????");
                    //????????????
                    llNoPursaceView.setVisibility(View.VISIBLE);
                    if (liveRoomData.isCanTry()) {
                        XLog.e("?????????");
                        //?????????
                        ownerRtcUid = liveRoomData.getOptionalUid();
                        becomeAudience();
                        int surplusSecond = liveRoomData.getSurplusSecond();
                        timer = new CountDownTimer(surplusSecond * 1000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                XLog.e("?????????" + millisUntilFinished);

                            }

                            @Override
                            public void onFinish() {
                                XLog.e("????????????");
                                rlTravierView.setVisibility(View.VISIBLE);
                                removeRemotePreview();
                            }
                        }.start();
                    } else {
                        XLog.e("????????????");

                        //????????????
                        rlTravierView.setVisibility(View.VISIBLE);
                        removeRemotePreview();
                    }
                }
            }
        });
    }

    @Override
    public void leaveLiveBroadcastingRoom(ResponseBean body) {
        super.leaveLiveBroadcastingRoom(body);
        finish();
    }


    @Override
    public void buyCompleteVideoResponse() {

    }

    //??????
    private void becomesOwner(boolean audioMuted, boolean videoMuted) {

        if (!videoMuted) {
            XLog.e("???????????????");
            startCameraCapture();
        } else {
            XLog.e("???????????????");
        }

        rtcEngine().setClientRole(Constants.CLIENT_ROLE_BROADCASTER);
        XLog.e("audioMuted===" + audioMuted + " videoMuted====" + videoMuted);
        config().setAudioMuted(audioMuted);
        config().setVideoMuted(videoMuted);
        initLocalPreview();
    }

    private void initLocalPreview() {
        CameraTextureView textureView = new CameraTextureView(this);
        mVideoLayout.addView(textureView);
    }

    //??????
    private void becomeAudience() {
        isHost = false;
        stopCameraCapture();
//        bottomButtons.setRole(LiveBottomButtonLayout.ROLE_AUDIENCE);
        rtcEngine().setClientRole(Constants.CLIENT_ROLE_AUDIENCE);
        config().setAudioMuted(true);
        config().setVideoMuted(true);
        setupRemotePreview();
    }

    //570355328   583128336
    //setupRemoteVideo  ??????
    private void setupRemotePreview() {
        XLog.e("????????????" + ownerRtcUid);
        SurfaceView surfaceView = setupRemoteVideo(ownerRtcUid);
        mVideoLayout.addView(surfaceView);
    }

    private void removeRemotePreview() {
        removeRemoteVideo(ownerRtcUid);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void finish() {
        super.finish();
//        bottomButtons.clearStates(application());
    }


}
