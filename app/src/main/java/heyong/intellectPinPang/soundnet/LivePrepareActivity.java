package heyong.intellectPinPang.soundnet;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elvishew.xlog.XLog;

import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.CreateXlClubContestInfoBean;
import heyong.intellectPinPang.bean.live.LiveRoomBean;
import heyong.intellectPinPang.bean.live.BeginLiveBroadCastBean;
import heyong.intellectPinPang.bean.live.XlZhiboJiedanOrderBean;
import heyong.intellectPinPang.soundnet.actionsheet.BeautySettingActionSheet;
import heyong.intellectPinPang.soundnet.actionsheet.LiveRoomSettingActionSheet;
import heyong.intellectPinPang.soundnet.components.CameraTextureView;
import heyong.intellectPinPang.soundnet.framework.PreprocessorFaceUnity;
import heyong.intellectPinPang.soundnet.vlive.utils.Global;
import io.agora.capture.video.camera.VideoModule;
import io.agora.capture.video.camera.CameraVideoChannel;
import io.agora.framework.modules.channels.ChannelManager;


/**
 * 直播 待直播页面
 */
public class LivePrepareActivity extends LiveBaseActivity implements View.OnClickListener,
        BeautySettingActionSheet.BeautyActionSheetListener,
        LiveRoomSettingActionSheet.LiveRoomSettingActionSheetListener {

    public static final int RESULT_GO_LIVE = 2;

    private static final String TAG = LivePrepareActivity.class.getSimpleName();
    private static final int MAX_NAME_LENGTH = 25;

    private TextView mStartBroadBtn;

    private Dialog mExitDialog;


    private FrameLayout mLocalPreviewLayout;
    private CameraVideoChannel mCameraChannel;
    private PreprocessorFaceUnity mPreprocessor;

    private boolean mCameraPersist;

    private boolean mActivityFinished;
    private boolean mPermissionGranted;

    private int mVideoInitCount;
    private ImageView ivFinishLogo;
    private int playType = 0;
    private LinearLayout llAgreeText;
    private ImageView ivAgreeWith, ivLeftLogo, ivRightLogo;
    private ImageView ivSwitchCamera;
    XlZhiboJiedanOrderBean xlBean;
    TextView tvRoomName;
    TextView tvSeeNum, tvLeftName, tvRightName;
    LinearLayout llRightContainer;

    @Override
    protected void onGlobalLayoutCompleted() {

    }

    public static void goActivity(Context context, boolean isCreater, String matchId, XlZhiboJiedanOrderBean xlBean) {
        Intent intent = new Intent(context, LivePrepareActivity.class);
        intent.putExtra(Global.Constants.TAB_KEY, 1);
        intent.putExtra(Global.Constants.KEY_IS_ROOM_OWNER, isCreater);
        intent.putExtra(Global.Constants.KEY_CREATE_ROOM, isCreater);
        intent.putExtra(Global.Constants.KEY_MATCH_ID, matchId);
        intent.putExtra("data", xlBean);
        context.startActivity(intent);
    }

    //    Intent intent = new Intent(getActivity(), LivePrepareActivity.class);
////                Intent intent = new Intent(getActivity(), BeginLiveActivity.class);
//                intent.putExtra(Global.Constants.TAB_KEY, 1);
//                intent.putExtra(Global.Constants.KEY_IS_ROOM_OWNER, true);
//                intent.putExtra(Global.Constants.KEY_CREATE_ROOM, true);
//                intent.putExtra(Global.Constants.KEY_MATCH_ID, "619492060864861360");
//    //                intent.putExtra(Global.Constants.KEY_ROOM_OWNER_ID,
////                       "347658454774591488");
//    startActivity(intent);
    private void initUI() {
        hideStatusBar(false);
        setContentView(R.layout.activity_live_prepare);
        mStartBroadBtn = findViewById(R.id.live_prepare_go_live_btn);
        xlBean = (XlZhiboJiedanOrderBean) getIntent().getSerializableExtra("data");

        ivFinishLogo = findViewById(R.id.iv_finish_logo);
        tvRoomName = findViewById(R.id.tv_room_name);
        tvSeeNum = findViewById(R.id.tv_see_num);
        llRightContainer = findViewById(R.id.ll_right_container);
        ivLeftLogo = findViewById(R.id.iv_left_logo);
        ivRightLogo = findViewById(R.id.img_right_logo);
        tvLeftName = findViewById(R.id.tv_left_name);
        tvRightName = findViewById(R.id.tv_right_name);


        llAgreeText = findViewById(R.id.ll_xieyi);
        ivAgreeWith = findViewById(R.id.iv_xieyi);
        ivSwitchCamera = findViewById(R.id.iv_switch_camera);

        mStartBroadBtn.setOnClickListener(this);
        ivSwitchCamera.setOnClickListener(this);
        ivFinishLogo.setOnClickListener(this);
        llAgreeText.setOnClickListener(this);
        mCameraChannel = (CameraVideoChannel) VideoModule.instance().
                getVideoChannel(ChannelManager.ChannelID.CAMERA);
        mPreprocessor = (PreprocessorFaceUnity) VideoModule.instance().
                getPreprocessor(ChannelManager.ChannelID.CAMERA);

        mLocalPreviewLayout = findViewById(R.id.local_preview_layout);
        if (xlBean != null) {
            if (xlBean.getXlZhiboSetOrder() != null) {
                XlZhiboJiedanOrderBean.XlZhiboSetOrderBean xlZhiboSetOrder = xlBean.getXlZhiboSetOrder();
//                tvRoomName.setText("房间号：" + xlZhiboSetOrder.getOrderNum());
                tvRightName.setVisibility(View.GONE);
                if (xlZhiboSetOrder.getItemType().equals("1") || xlZhiboSetOrder.getItemType().equals("2")) {
                    //团体单打
                    ivLeftLogo.setVisibility(View.GONE);
                    ivRightLogo.setVisibility(View.GONE);
                    tvLeftName.setText(""+xlZhiboSetOrder.getPlayer1());
                    tvRightName.setText(""+xlZhiboSetOrder.getPlayer3());
                    tvLeftName.setVisibility(View.VISIBLE);
                    tvRightName.setVisibility(View.VISIBLE);
                } else {
                    ivLeftLogo.setVisibility(View.GONE);
                    ivRightLogo.setVisibility(View.GONE);
                    tvLeftName.setText(""+xlZhiboSetOrder.getPlayer1()+" "+xlZhiboSetOrder.getPlayer2());
                    tvRightName.setText(""+xlZhiboSetOrder.getPlayer3()+" "+xlZhiboSetOrder.getPlayer4());
                    tvLeftName.setVisibility(View.VISIBLE);
                    tvRightName.setVisibility(View.VISIBLE);

                }
            }
        }

        changeUIStyles();
        checkFUAuth();
    }

    private void changeUIStyles() {
        if (tabId == Config.LIVE_TYPE_VIRTUAL_HOST) {
            hideStatusBar(true);
            // It only accepts front camera frames for virtual images.

            RelativeLayout layout = findViewById(R.id.activity_layout);
            layout.setBackgroundColor(Color.WHITE);

            int virtualImage = getIntent().getIntExtra(
                    Global.Constants.KEY_VIRTUAL_IMAGE, -1);

            // The listener needs to be reset before the
            // virtual image is selected
            mPreprocessor.setOnBundleLoadedListener(this::tryInitializeVideo);

            mPreprocessor.setOnFirstFrameListener(this::tryInitializeVideo);
            config().setBeautyEnabled(true);
            startCameraCapture();
            mPreprocessor.onAnimojiSelected(virtualImage);
        } else {
            hideStatusBar(false);
            startCameraCapture();
            mPreprocessor.onAnimojiSelected(-1);
            mLocalPreviewLayout.addView(new CameraTextureView(this));
        }
    }

    private void checkFUAuth() {
        if (mPreprocessor != null &&
                !mPreprocessor.FUAuthenticated()) {
//            showLongToast(getString(R.string.no_fu_auth_notice));
            if (tabId == Config.LIVE_TYPE_VIRTUAL_HOST) {
                mStartBroadBtn.setEnabled(false);
            }
        }
    }

    private void tryInitializeVideo() {
        if (mVideoInitCount >= 2) {
            return;
        }

        mVideoInitCount++;
        if (mVideoInitCount == 2) {
            runOnUiThread(() -> mLocalPreviewLayout.
                    addView(new CameraTextureView(this)));
        }
    }

    @Override
    protected void onPermissionGranted() {
        mPermissionGranted = true;
        initUI();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.live_prepare_close:
//                onBackPressed();
//                break;
//            case R.id.live_prepare_switch_camera:
//                switchCamera();
//                break;
//            case R.id.random_btn:
//                setRandomRoomName();
//                break;
            case R.id.iv_switch_camera:

                switchCamera();

                break;
            case R.id.iv_finish_logo:
                onBackPressed();

                break;
            case R.id.ll_xieyi:
                if (playType == 0) {
                    playType = 1;
                    ImageLoader.loadImage(ivAgreeWith, R.drawable.img_live_select);

                } else {
                    playType = 0;
                    ImageLoader.loadImage(ivAgreeWith, R.drawable.img_live_no_select);


                }

                break;
            case R.id.live_prepare_go_live_btn:
                //todo 掉接口 判断用户是否封禁
                if (playType == 0) {
                    showShortToast("请同意协议");
                    return;
                }
                gotoBroadcastActivity();
                break;
//            case R.id.live_prepare_beauty_btn:
//                showActionSheetDialog(ACTION_SHEET_BEAUTY, tabIdToLiveType(), true, true, this);
//                break;
//            case R.id.live_prepare_setting_btn:
//                showActionSheetDialog(ACTION_SHEET_VIDEO, tabIdToLiveType(), true, true, this);
//                break;
            default:

                break;
        }
    }


//    private boolean isRoomNameValid() {
//        return mEditText.getText() != null && !TextUtils.isEmpty(mEditText.getText());
//    }

    private void gotoBroadcastActivity() {


        mStartBroadBtn.setEnabled(false);
        Intent intent;
        intent = new Intent(this, SingleHostLiveActivity.class);
        if (getIntent().getExtras() != null) {
            intent.putExtras(getIntent().getExtras());
        }
        startActivity(intent);
        // If we go live, we send a message to image select
        // activity that it does need to keep track in stack
        setResult(RESULT_GO_LIVE);
        mCameraPersist = true;
        finish();
    }

    @Override
    public void onActionSheetBeautyEnabled(boolean enabled) {
//        findViewById(R.id.live_prepare_beauty_btn).setActivated(enabled);
//        enablePreProcess(enabled);
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
    }

    @Override
    public void onActionSheetFrameRateSelected(int index) {
        config().setFrameRateIndex(index);
    }

    @Override
    public void onActionSheetBitrateSelected(int bitrate) {
        config().setVideoBitrate(bitrate);
    }

    @Override
    public void onActionSheetSettingBackPressed() {

    }

    @Override
    public void onBackPressed() {
//        boolean fromVirtualImage = getIntent().getBooleanExtra(
//                VirtualImageSelectActivity.
//                        KEY_FROM_VIRTUAL_IMAGE, false);
//
//        if (fromVirtualImage) {
//            finish();
//            return;
//        }

        mExitDialog = showDialog(R.string.end_live_streaming_title_owner,
                R.string.end_live_streaming_message_owner, view -> {
                    dismissDialog();
                    finish();
                });
    }

    private void dismissDialog() {
        if (mExitDialog != null && mExitDialog.isShowing()) {
            mExitDialog.dismiss();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPermissionGranted) {
            startCameraCapture();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!mCameraPersist && mCameraChannel != null && !mActivityFinished
                && mCameraChannel.hasCaptureStarted()) {
            // Stop camera capture when this activity
            // goes to background, but not because
            // of being finished.
            mCameraChannel.stopCapture();
        }
    }

    @Override
    public void finish() {
        super.finish();
        dismissDialog();
        mActivityFinished = true;
        if (!mCameraPersist && mCameraChannel != null
                && mCameraChannel.hasCaptureStarted()) {
            mPreprocessor.onAnimojiSelected(-1);
            mCameraChannel.stopCapture();
        }
    }

    public void onPolicyClosed(View view) {
//        if (view.getId() == R.id.live_prepare_policy_close) {
//            findViewById(R.id.live_prepare_policy_caution_layout).setVisibility(View.GONE);
//        }
    }


    @Override
    public void onResponseError(int requestType, String error, String message) {

    }

    @Override
    public void beginLiveBroadcastResponse(ResponseBean<BeginLiveBroadCastBean> body1) {


    }

    @Override
    public void suspendLiveBroadcastResponse() {

    }

    @Override
    public void continueLiveBroadcastResponse() {

    }

    @Override
    public void endLiveBroadcastResponse(ResponseBean body1) {

    }

    @Override
    public void openCloudRecording(ResponseBean body1) {

    }

    @Override
    public void stopCloudRecording(ResponseBean body1) {

    }

    @Override
    public void getIntoLiveBroadcastingRoomResponse(ResponseBean<LiveRoomBean> body1) {

    }

    @Override
    public void leaveLiveBroadcastingRoom(ResponseBean body) {

    }


    @Override
    public void buyCompleteVideoResponse() {

    }

    @Override
    public void onUserJoined(int uid, int elapsed) {

    }

    @Override
    public void onUserOffline(int uid, int reason) {

    }

    @Override
    public void onConnectionBanned() {
        XLog.e("onConnectionBanned 1111111111111111111");
    }

    @Override
    public void onRtmConnectionStateChanged(int state, int reason) {
        super.onRtmConnectionStateChanged(state, reason);
        XLog.e("statePrepare=====" + detalWithState(state) + "   reasonPrepare====" + dealWithReason(reason));

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

}
