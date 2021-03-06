//package heyong.intellectPinPang.live.activity;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.lifecycle.Observer;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.content.res.Configuration;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.os.Handler;
//import android.os.Message;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.GestureDetector;
//import android.view.Gravity;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.alivc.live.AliLiveBeautyManager;
//import com.alivc.live.AliLiveCallback;
//import com.alivc.live.AliLiveConfig;
//import com.alivc.live.AliLiveConstants;
//import com.alivc.live.AliLiveEngine;
//import com.alivc.live.AliLiveError;
//import com.alivc.live.AliLiveRTMPConfig;
//import com.alivc.live.AliLiveRenderView;
//import com.alivc.live.bean.AliLiveLocalVideoStats;
//import com.alivc.live.bean.AliLiveRemoteAudioStats;
//import com.alivc.live.bean.AliLiveRemoteVideoStats;
//import com.alivc.live.bean.AliLiveResult;
//import com.alivc.live.bean.AliLiveStats;
//
//import java.lang.ref.WeakReference;
//import java.util.ArrayList;
//import java.util.List;
//
//import heyong.intellectPinPang.ui.BaseActivity;
//import heyong.handong.framework.base.ResponseBean;
//import heyong.intellectPinPang.bean.Constants;
//import heyong.intellectPinPang.R;
//import heyong.intellectPinPang.bean.live.LiveMatchUrlBean;
//import heyong.intellectPinPang.databinding.ActivityLiveBinding;
//import heyong.intellectPinPang.live.aliview.AdvancedSoundEffectView;
//import heyong.intellectPinPang.live.aliview.AliLiveConfigView;
//import heyong.intellectPinPang.live.aliview.AliPushConfigView;
//import heyong.intellectPinPang.live.aliview.BGMView;
//import heyong.intellectPinPang.live.aliview.BeautyView;
//import heyong.intellectPinPang.live.aliview.DataView;
//import heyong.intellectPinPang.live.aliview.FocusView;
//import heyong.intellectPinPang.live.aliview.VoiceChangerView;
//import heyong.intellectPinPang.live.listener.AliLiveConfigListener;
//import heyong.intellectPinPang.live.listener.AliLiveVoiceChangeListener;
//import heyong.intellectPinPang.live.listener.BGMClickListener;
//import heyong.intellectPinPang.live.listener.BeautyClickAndSlideListener;
//import heyong.intellectPinPang.live.listener.ButtonClickListener;
//import heyong.intellectPinPang.live.model.LiveViewModel;
//import heyong.intellectPinPang.live.wheel.widget.CommonDialog;
//import heyong.intellectPinPang.live.wheel.widget.TextFormatUtil;
//import heyong.intellectPinPang.utils.BeautyManager;
//import heyong.intellectPinPang.utils.MessageDialogBuilder;
//import heyong.intellectPinPang.utils.OrientationDetector;
//import heyong.intellectPinPang.utils.PhoneStateManger;
//import heyong.intellectPinPang.utils.ThreadUtils;
//import heyong.handong.framework.utils.ToastUtils;
//import heyong.intellectPinPang.utils.URLDialog;
//import heyong.lzy.imagepicker.util.StatusBarUtil;
//
//
///**
// * ????????????Live=rtmp://live.xlttsports.com/pingpong/1234?auth_key=1617262456-0-0-c952e2c3c0cb7d3cf7178b36804f92ef
// * ?????????????????????Play=http://play.xlttsports.com/pingpong/1234.m3u8?auth_key=1617262456-6857029-0-7d0206b0429c7a252c33f6b2629d0443
// * <p>
// * ???????????????  ???????????????
// * ????????????Live=rtmp://live.xlttsports.com/pingpong/1234?auth_key=1617262456-0-0-c952e2c3c0cb7d3cf7178b36804f92ef
// * ?????????????????????Play=http://play.xlttsports.com/pingpong/1234.m3u8?auth_key=1617262456-6857029-0-7d0206b0429c7a252c33f6b2629d0443
// */
//public class LiveActivity extends BaseActivity<ActivityLiveBinding, LiveViewModel> implements ButtonClickListener,
//        BeautyClickAndSlideListener, AliPushConfigView.OnSaveConfigListener, View.OnClickListener {
//    private static final String TAG = LiveActivity.class.getSimpleName();
//
//    //    private String rtmpUrl = "rtmp://ossrs.net/" + StatusBarUtils.getRandomAlphaString(3) + '/' + StatusBarUtils.getRandomAlphaDigitString(5);
//    private String rtmpUrl = "rtmp://live.xlttsports.com/pingpong/1234?auth_key=1617262456-0-0-c952e2c3c0cb7d3cf7178b36804f92ef";
//    private static final int BGM_CURRENT_POSITION_WHAT = 0x0001;
//    private String mCurrentPushUrl = "rtmp://live.xlttsports.com/test/hello?auth_key=1618880346-0-0-0ac021cc278e4ac0c71987b4ab7d8e96";
////    private String mCurrentPushUrl = "rtmp://live.xlttsports.com/pingpong/1234?auth_key=1617262456-0-0-c952e2c3c0cb7d3cf7178b36804f92ef";
//
//
//    //    private AnchorButtonListView mButtonListView;
//    private BeautyView mBeautyView;
//    private BGMView mBGMView;
//    private AdvancedSoundEffectView mAdvanceSoundEffectView;
//    private AliLiveConfigView mAliLiveConfigView;
//    private FrameLayout mContainer;
//    private TextView mShowMessage;
//    //    ?????????
//    private AliLiveEngine mAliLiveEngine;
//    //    ?????????surfaceView
//    private AliLiveRenderView mAliLiveRenderView;
//
//    //    ??????????????????
//    private AliPushConfigView mAliPushConfigView;
//
//    //    ????????????
//    private boolean isMute;
//
//    private FocusView mFocusView;
//
//    private AliLiveBeautyManager mAliLiveBeautyManager;
//
//    private AliLiveConfig mAliLiveConfig;
//
//    //    ????????????????????????
//    private boolean isBeautyOpen = true;
//
//    //    ???????????????
//    private OrientationDetector mOrientationDetector;
//    //    ????????????
//    private int mCUrrentPosition = 0;
//    /**
//     * ??????????????????
//     */
//    private boolean isLandscape = false;
//
//
//    /**
//     * ????????????
//     */
//    private PhoneStateManger mPhoneStateManger;
//
//    /**
//     * ??????????????????
//     */
//    private boolean isPreviewing = false;
//
//    /**
//     * ????????????BGM??????????????????
//     */
//    private boolean mEnableUpdateBGMCurrentPosition = true;
//    /**
//     * ??????????????????????????????
//     */
//    boolean mIsBGMLoop = false;
//    /**
//     * BGM???????????????
//     */
//    private boolean mBGMhasPlay = false;
//
//    private BeautyManager mBeautyManager;
//    private MyHandler mHandler;
//    private VoiceChangerView mVoiceChanger;
//    private Button mScanBtn;//??????
//    private AliLiveLocalVideoStats maliLiveLocalVideoStats;
//    private DataView mDataView;
//    private boolean isMuteFlag = false;//???????????????????????????
//
//    public static final String REQUEST_ID = "request_id";
//    private String strRequestId = "";
//
//
//    @Override
//    public int getLayoutRes() {
//        return R.layout.activity_live;
//    }
//
//    @Override
//    public void initView(@Nullable Bundle savedInstanceState) {
//        StatusBarUtil.transparencyBar(LiveActivity.this); //????????????????????????
////        StatusBarUtil.statusBarLightMode(LiveActivity.this);
//        strRequestId = getIntent().getStringExtra(REQUEST_ID);
//
//
//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        initOrientationDetector();
//        mHandler = new MyHandler(this);
//        mBeautyManager = new BeautyManager();
//        initViews();
//        initLiveSDK();
//        mBinding.setListener(this);
//        startPreview();//??????????????????
//
//        CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//            }
//
//            @Override
//            public void onFinish() {
//                if (mAliLiveEngine != null) {
//                    mAliLiveEngine.switchCamera();
//                }else
//                {
//                    toast("????????????");
//                }
//                mBinding.ivLivePlay.setVisibility(View.VISIBLE);
//            }
//        };
//        countDownTimer.start();
//
//        mViewModel.liveMatchUrlData.observe(this, new Observer<ResponseBean<LiveMatchUrlBean>>() {
//            @Override
//            public void onChanged(ResponseBean<LiveMatchUrlBean> liveMatchUrlBeanResponseBean) {
//                if (liveMatchUrlBeanResponseBean.getErrorCode().equals("200")) {
//                    LiveMatchUrlBean data = liveMatchUrlBeanResponseBean.getData();
//                    if (data != null) {
//                        mCurrentPushUrl = data.getLiveUrl();
//                        if (TextUtils.isEmpty(mCurrentPushUrl)) {
//                            showTipDialog("??????", "?????????????????????...");
//                        } else {
//                            startPublish(mCurrentPushUrl);
//                        }
//                    } else {
//                        toast("" + liveMatchUrlBeanResponseBean.getMessage());
//                    }
//                } else {
//                    toast("" + liveMatchUrlBeanResponseBean.getMessage());
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_live_play:
////                if (!TextUtils.isEmpty(strRequestId)) {
////                    mViewModel.liveMatchUrl("" + strRequestId);
////                } else {
////                    showTipDialog("??????", "?????????????????????...");
////                }
//                startPublish(mCurrentPushUrl);
////                if (TextUtils.isEmpty(mCurrentPushUrl)) {
////                    showTipDialog("??????", "?????????????????????...");
////                } else {
////                    startPublish(mCurrentPushUrl);
////                }
//                break;
//        }
//    }
//
//
//    private static class MyHandler extends Handler {
//
//        private WeakReference<LiveActivity> weakReference;
//
//        public MyHandler(LiveActivity LiveActivity) {
//            weakReference = new WeakReference<>(LiveActivity);
//        }
//
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            LiveActivity LiveActivity = weakReference.get();
//            if (LiveActivity == null) {
//                return;
//            }
//            switch (msg.what) {
//                case BGM_CURRENT_POSITION_WHAT:
//                    LiveActivity.startGetBGMPosition();
//                    break;
//            }
//        }
//    }
//
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE && mAliLiveEngine != null) {
//            isLandscape = true;
////            ?????????????????????
//            if (mCUrrentPosition == 90) {
//                mAliLiveEngine.setDeviceOrientationMode(AliLiveConstants.AliLiveOrientationMode.AliLiveOrientationModeLandscapeRight);
//            } else {
//                mAliLiveEngine.setDeviceOrientationMode(AliLiveConstants.AliLiveOrientationMode.AliLiveOrientationModeLandscapeLeft);
//            }
//        } else if (mAliLiveEngine != null) {
////            ?????????????????????
//            isLandscape = false;
//            mAliLiveEngine.setDeviceOrientationMode(AliLiveConstants.AliLiveOrientationMode.AliLiveOrientationModePortrait);
//        }
//        Log.e("onConfigurationChanged", "mCUrrentPosition:" + mCUrrentPosition + "\norientation:" + newConfig.orientation);
//    }
//
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        //pausePush?????????????????????
//        if (mAliLiveEngine != null) {
//            mAliLiveEngine.pausePush();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        //resumePush???????????????????????????
//        if (mAliLiveEngine != null) {
//            mAliLiveEngine.resumePush();
//        }
//        //?????????onResume????????????onPause????????????????????????????????????????????????????????????????????????
//        onBeautyOpen(isBeautyOpen);
//    }
//
//    private void initOrientationDetector() {
//        mOrientationDetector = new OrientationDetector(LiveActivity.this);
//        mOrientationDetector.setOrientationChangedListener(new OrientationDetector.OrientationChangedListener() {
//            @Override
//            public void onOrientationChanged() {
//                int orientation = mOrientationDetector.getOrientation();
//                if ((orientation >= 35) && (orientation < 135)) {
//                    if (isLandscape && mCUrrentPosition != 90 && mAliLiveEngine != null) {
//                        mAliLiveEngine.setDeviceOrientationMode(AliLiveConstants.AliLiveOrientationMode.AliLiveOrientationModeLandscapeRight);
//                    }
//                    mCUrrentPosition = 90;
//                } else if ((orientation >= 200) && (orientation < 335)) {
//                    if (isLandscape && mCUrrentPosition != 270 && mAliLiveEngine != null) {
//                        mAliLiveEngine.setDeviceOrientationMode(AliLiveConstants.AliLiveOrientationMode.AliLiveOrientationModeLandscapeLeft);
//                    }
//                    mCUrrentPosition = 270;
//                } else {
//                    mCUrrentPosition = 0;
//                }
//
//            }
//        });
//    }
//
//    private void initLiveSDK() {
//        if (mAliLiveConfig == null) {
//            AliLiveRTMPConfig rtmpConfig = new AliLiveRTMPConfig();
//            rtmpConfig.videoInitBitrate = 1000;
//            rtmpConfig.videoTargetBitrate = 1500;
//            rtmpConfig.videoMinBitrate = 600;
//            mAliLiveConfig = new AliLiveConfig(rtmpConfig);
//            mAliLiveConfig.videoFPS = 20;
//            mAliLiveConfig.videoPushProfile = AliLiveConstants.AliLiveVideoPushProfile.AliLiveVideoProfile_540P;
//            mAliLiveConfig.enableHighDefPreview = false;
//        }
//        mAliLiveConfig.accountId = Constants.HTTP_DNS_ACCOUNT_ID;
//
//        mAliLiveConfig.extra = Constants.LIVE_EXTRA_INFO;
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inScaled = false;
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background_push, options);
//        if (bitmap != null) {
//            mAliLiveConfig.pauseImage = bitmap;
//        }
//        Log.d("LiveActivity", "sdk version " + AliLiveEngine.getSdkVersion());
//        mAliLiveEngine = AliLiveEngine.create(LiveActivity.this, mAliLiveConfig);
//        mAliLiveBeautyManager = mAliLiveEngine.getBeautyManager();
//        mBeautyManager.setmAliLiveBeautyManager(mAliLiveBeautyManager);
//        mAliPushConfigView.setmAliLiveConfig(mAliLiveConfig);
//        mAliLiveEngine.setStatsCallback(statsCallback);
//        mAliLiveEngine.setRtsCallback(rtsCallback);
//        mAliLiveEngine.setStatusCallback(statusCallback);
//        mAliLiveEngine.setNetworkCallback(networkCallback);
//        mAliLiveEngine.setVidePreProcessDelegate(new AliLiveCallback.AliLiveVideoPreProcessCallback() {
//            @Override
//            public int onTexture(int textureId, int width, int height, int rotate, int i4) {
//                Log.e("LiveActivity", "onTexture: " + textureId + " --- " + width + " --- " + height + " --- " + i4);
//                return textureId;
//            }
//
//            @Override
//            public void onTextureDestroy() {
//                Log.e("LiveActivity", "onTexture: ");
//            }
//
//            @Override
//            public void onVideoData(long l, long l1, long l2, AliLiveConstants.AliLiveImageFormat aliLiveImageFormat, int i, int i1, int i2, int i3, int i4, int i5) {
//                Log.e("LiveActivity", "onVideoData: " + l + " --- " + l1 + " --- " + l2 + " --- " + i + " --- " + i1 + " --- "
//                        + i2 + " --- " + i3 + " --- " + i4 + " --- " + i5);
//            }
//        });
//    }
//
//    private AliLiveCallback.StatusCallback statusCallback = new AliLiveCallback.StatusCallback() {
//
//        @Override
//        public void onLiveSdkError(AliLiveEngine aliLiveEngine, final AliLiveError aliLiveError) {
//            if (aliLiveError.errorCode == AliLiveError.AliLiveSdkErrorCodePushError) {
//                ThreadUtils.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ToastUtils.showToast(LiveActivity.this, "???????????????" + aliLiveError.errorDescription);
//                    }
//                });
//            }
//        }
//
//        @Override
//        public void onLiveSdkWarning(AliLiveEngine aliLiveEngine, int i) {
//
//        }
//
//        @Override
//        public void onPreviewStarted(AliLiveEngine aliLiveEngine) {
//            Log.d("statusCallback====", "onPreviewStarted");
//            isPreviewing = true;
//        }
//
//        @Override
//        public void onPreviewStopped(AliLiveEngine aliLiveEngine) {
//            Log.d("statusCallback====", "onPreviewStopped");
//            isPreviewing = false;
//        }
//
//        @Override
//        public void onFirstVideoFramePreviewed(AliLiveEngine aliLiveEngine) {
//        }
//
//        @Override
//        public void onLivePushStarted(AliLiveEngine aliLiveEngine) {
//            ThreadUtils.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    ToastUtils.showToast(LiveActivity.this, "????????????");
//                }
//            });
//        }
//
//        @Override
//        public void onLivePushStopped(AliLiveEngine aliLiveEngine) {
//
//        }
//
//        @Override
//        public void onAudioFocusChanged(int focusChange) {
//            if (focusChange < 0) {
//                //loss,??????????????????
//                if (mAliLiveEngine != null && mAliLiveEngine.isPublishing()) {
//                    mAliLiveEngine.setMute(true);
//                }
//            } else if (focusChange > 0) {
//                //gain????????????????????????
//                if (mAliLiveEngine != null && mAliLiveEngine.isPublishing()) {
//                    mAliLiveEngine.setMute(false);
//                }
//            } else {
//
//            }
//        }
//
//        @Override
//        public void onBGMStateChanged(AliLiveEngine publisher, final AliLiveConstants.AliLiveAudioPlayingStateCode playState,
//                                      final AliLiveConstants.AliLiveAudioPlayingErrorCode errorCode) {
//            ThreadUtils.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    int bgmCurrentPosition = mAliLiveEngine.getBGMCurrentPosition();
//                    if (playState == AliLiveConstants.AliLiveAudioPlayingStateCode.AliLiveAudioPlayingStarted) {
//                        mBGMView.setBGMDuration(mAliLiveEngine.getBGMDuration());
//                        mEnableUpdateBGMCurrentPosition = true;
//                        mBGMView.setBGMCurrentPosition(bgmCurrentPosition);
//                        startGetBGMPosition();
//                    } else if (playState == AliLiveConstants.AliLiveAudioPlayingStateCode.AliLiveAudioPlayingBuffering
//                            || playState == AliLiveConstants.AliLiveAudioPlayingStateCode.AliLiveAudioPlayingPaused) {
//                        mEnableUpdateBGMCurrentPosition = false;
//                    } else if (playState == AliLiveConstants.AliLiveAudioPlayingStateCode.AliLiveAudioPlayingBufferingEnd
//                            || playState == AliLiveConstants.AliLiveAudioPlayingStateCode.AliLiveAudioPlayingResumed) {
//                        mEnableUpdateBGMCurrentPosition = true;
//                        mBGMView.setBGMCurrentPosition(bgmCurrentPosition);
//                    } else if (playState == AliLiveConstants.AliLiveAudioPlayingStateCode.AliLiveAudioPlayingEnded && mIsBGMLoop) {
//                        mEnableUpdateBGMCurrentPosition = true;
//                        mBGMView.setBGMCurrentPosition(bgmCurrentPosition);
//                    } else {
//                        mBGMView.setBGMCurrentPosition(bgmCurrentPosition);
//                        mEnableUpdateBGMCurrentPosition = false;
//                        stopGetBGMPosition();
//                    }
//                    if (errorCode != AliLiveConstants.AliLiveAudioPlayingErrorCode.AliLiveAudioPlayingNoError) {
//                        ToastUtils.showToast(LiveActivity.this, "onBGMStateChanged:" + playState + " errorcode " + errorCode);
//                    }
//                }
//            });
//        }
//    };
//
//    private AliLiveCallback.StatsCallback statsCallback = new AliLiveCallback.StatsCallback() {
//        @Override
//        public void onLiveTotalStats(AliLiveStats aliLiveStats) {
//        }
//
//        @Override
//        public void onLiveLocalVideoStats(AliLiveLocalVideoStats aliLiveLocalVideoStats) {
//            maliLiveLocalVideoStats = aliLiveLocalVideoStats;
//        }
//
//        @Override
//        public void onLiveRemoteVideoStats(AliLiveRemoteVideoStats aliLiveRemoteVideoStats) {
//        }
//
//        @Override
//        public void onLiveRemoteAudioStats(AliLiveRemoteAudioStats aliLiveRemoteAudioStats) {
//        }
//    };
//
//    private AliLiveCallback.RtsCallback rtsCallback = new AliLiveCallback.RtsCallback() {
//        @Override
//        public void onSubscribeResult(AliLiveResult aliLiveResult, String s) {
//
//        }
//
//        @Override
//        public void onUnSubscribeResult(AliLiveResult aliLiveResult, String s) {
//
//        }
//
//        @Override
//        public void onFirstPacketReceivedWithUid(String s) {
//
//        }
//
//        @Override
//        public void onFirstRemoteVideoFrameDrawn(String s, AliLiveConstants.AliLiveVideoTrack aliLiveVideoTrack) {
//
//        }
//    };
//
//    private AliLiveCallback.NetworkCallback networkCallback = new AliLiveCallback.NetworkCallback() {
//        @Override
//        public void onNetworkStatusChange(final AliLiveConstants.AliLiveNetworkStatus status) {
//            ThreadUtils.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
////                    ToastUtils.showToast(LiveActivity.this, "?????????????????? = " + status.name());
//                }
//            });
//        }
//
//        @Override
//        public void onNetworkPoor() {
//            ThreadUtils.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    ToastUtils.showToast(LiveActivity.this, "??????");
//                }
//            });
//        }
//
//        @Override
//        public void onConnectRecovery() {
//            ThreadUtils.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    ToastUtils.showToast(LiveActivity.this, "????????????");
//                }
//            });
//        }
//
//        @Override
//        public void onReconnectStart() {
//            ThreadUtils.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    ToastUtils.showToast(LiveActivity.this, "????????????");
//                }
//            });
//        }
//
//        @Override
//        public void onConnectionLost() {
//            ThreadUtils.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    ToastUtils.showToast(LiveActivity.this, "????????????");
//                }
//            });
//        }
//
//        @Override
//        public void onReconnectStatus(final boolean success) {
//            ThreadUtils.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (success) {
//                        ToastUtils.showToast(LiveActivity.this, "????????????");
//                    } else {
//                        ToastUtils.showToast(LiveActivity.this, "????????????");
//                    }
//
//                }
//            });
//        }
//    };
//
//
//    private void initViews() {
////        mButtonListView = findViewById(R.id.live_buttonlistview);
//        mContainer = findViewById(R.id.push_container);
//        mBeautyView = findViewById(R.id.live_beauty_view);
//        mAdvanceSoundEffectView = findViewById(R.id.live_advance_sound_effect);
//        mBGMView = mAdvanceSoundEffectView.getBGMView();
//        mAliLiveConfigView = mAdvanceSoundEffectView.getLiveConfigView();
//        mVoiceChanger = findViewById(R.id.live_voice_changer);
//        mAliPushConfigView = findViewById(R.id.live_configview);
//        mShowMessage = findViewById(R.id.live_url_message);
//        mAliPushConfigView.setmOnSaveConfigListener(this);
//        mDataView = findViewById(R.id.ll_data);
//        mBeautyView.setClickListener(this);
//        List<String> data = new ArrayList<>();
//        data.addAll(Constants.getPushActivityButtonList());
//
////        mButtonListView.setData(data);
////        mButtonListView.setClickListener(this);
//
//
//        //BGM
//        mBGMView.setBGMClickListener(new BGMClickListener() {
//
//            @Override
//            public void onPlayClick(String path, boolean push, boolean loop) {
//                mBGMhasPlay = true;
//                mIsBGMLoop = loop;
//                mAliLiveEngine.playBGM(path, push, loop);
//            }
//
//            @Override
//            public void onResumeClick() {
//                mAliLiveEngine.resumeBGM();
//            }
//
//            @Override
//            public void onPauseClick() {
//                mAliLiveEngine.pauseBGM();
//            }
//
//            @Override
//            public void onStopClick() {
//                mBGMhasPlay = false;
//                mAliLiveEngine.stopBGM();
//            }
//
//            @Override
//            public void onSeek(int position) {
//                mAliLiveEngine.setBGMPosition(position);
//            }
//
//            @Override
//            public void onVolume(int volume) {
//                mAliLiveEngine.setBGMVolume(volume);
//            }
//
//            @Override
//            public void onPushSwitchChanged(String path, boolean push, boolean loop) {
//                if (mBGMhasPlay) {
//                    mAliLiveEngine.playBGM(path, push, loop);
//                }
//            }
//
//            @Override
//            public void onLoopSwitchChanged(String path, boolean push, boolean loop) {
//                if (mBGMhasPlay) {
//                    mIsBGMLoop = loop;
//                    mAliLiveEngine.playBGM(path, push, loop);
//                }
//
//            }
//        });
//
//        //????????????
//        mVoiceChanger.setAliLiveVoiceChangeListener(new AliLiveVoiceChangeListener() {
//            @Override
//            public void onAliLiveVoiceChangerMode(AliLiveConstants.AliLiveVoiceChangerMode voiceChangerMode) {
//                mAliLiveEngine.setVoiceChangerMode(voiceChangerMode);
//            }
//        });
//
//        mAdvanceSoundEffectView.setAliLiveConfigListener(new AliLiveConfigListener() {
//            @Override
//            public void onEarbackChanged(boolean enableEarback) {
//                mAliLiveEngine.enableEarBack(enableEarback);
//            }
//
//            @Override
//            public void onEarbackVolume(int volume) {
//                mAliLiveEngine.setEarBackVolume(volume);
//            }
//
//            @Override
//            public void onPicthValue(float value) {
//                mAliLiveEngine.setPicthValue(value);
//            }
//
//            @Override
//            public void onAliLiveReverbMode(AliLiveConstants.AliLiveReverbMode reverbMode) {
//                mAliLiveEngine.setReverbMode(reverbMode);
//            }
//
//            @Override
//            public void onAliLiveVoiceChangerMode(AliLiveConstants.AliLiveVoiceChangerMode voiceChangerMode) {
//                mAliLiveEngine.setVoiceChangerMode(voiceChangerMode);
//            }
//        });
//
//        //????????????
//        mAliLiveConfigView.setAliLiveConfigListener(new AliLiveConfigListener() {
//            @Override
//            public void onEarbackChanged(boolean enableEarback) {
//                mAliLiveEngine.enableEarBack(enableEarback);
//            }
//
//            @Override
//            public void onEarbackVolume(int volume) {
//                mAliLiveEngine.setEarBackVolume(volume);
//            }
//
//            @Override
//            public void onPicthValue(float value) {
//                mAliLiveEngine.setPicthValue(value);
//            }
//
//            @Override
//            public void onAliLiveReverbMode(AliLiveConstants.AliLiveReverbMode reverbMode) {
//                mAliLiveEngine.setReverbMode(reverbMode);
//            }
//
//            @Override
//            public void onAliLiveVoiceChangerMode(AliLiveConstants.AliLiveVoiceChangerMode voiceChangerMode) {
//                mAliLiveEngine.setVoiceChangerMode(voiceChangerMode);
//            }
//        });
//        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new MessageDialogBuilder(LiveActivity.this)
//                        .setMessage("")
//                        .setTvTitle("???????????????????")
//                        .setTvCancel("??????")
//                        .setBtnCancleHint(false)
//                        .setTvSure("??????")
//                        .setSureListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                finish();
//                            }
//                        })
//                        .show();
//            }
//        });
//    }
//
//
//    /**
//     * ????????????URL???dialog
//     */
//    private void createDialog() {
//        new URLDialog().createAndShowDialog("??????URL", LiveActivity.this, new URLDialog.OnUrlInputListener() {
//            @Override
//            public void getUrl(String url) {
//                startPublish(url);
//            }
//        });
//
//    }
//
//    /**
//     * ???????????????????????????
//     */
//    private void initFocus() {
//        /**
//         * ??????focus
//         */
//        if (mAliLiveRenderView == null) {
//            return;
//        }
//        if (mFocusView == null) {
//            mFocusView = new FocusView(LiveActivity.this);
//            mFocusView.setPadding(10, 10, 10, 10);
//            addSubView(mFocusView);
//        }
//
//        final GestureDetector gestureDetector = new GestureDetector(LiveActivity.this,
//                new GestureDetector.SimpleOnGestureListener() {
//                    @Override
//                    public boolean onSingleTapUp(MotionEvent e) {
//                        if (mAliLiveEngine == null) {
//                            return true;
//                        }
//                        AliLiveEngine.AliLivePoint aliLivePoint = new AliLiveEngine.AliLivePoint();
//                        aliLivePoint.x = (int) e.getX();
//                        aliLivePoint.y = (int) e.getY();
//                        Log.d("mAliLiveEngine----", mAliLiveEngine.isCameraFocusPointSupported() + "----" + mAliLiveEngine.isCameraExposurePointSupported());
//                        Log.d("mAliLiveEngine----", e.getX() + "----" + e.getY());
//                        mAliLiveEngine.setCameraFocusPoint(aliLivePoint);
////                        mAliLiveEngine.setCameraExposurePoint(aliLivePoint);
//                        mFocusView.showView();
//                        mFocusView.setLocation(e.getRawX(), e.getRawY());
//                        return true;
//                    }
//                });
//        mAliLiveRenderView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getPointerCount() == 1) {
//                    gestureDetector.onTouchEvent(event);
//                }
//                return true;
//            }
//        });
//    }
//
//    /**
//     * ??????BGM??????????????????
//     */
//    private void startGetBGMPosition() {
//        if (mAliLiveEngine != null && mBGMView != null) {
//            if (mEnableUpdateBGMCurrentPosition) {
//                int bgmCurrentPosition = mAliLiveEngine.getBGMCurrentPosition();
//                mBGMView.setBGMCurrentPosition(bgmCurrentPosition);
//            }
//        }
//        if (mHandler != null) {
//            mHandler.sendEmptyMessageDelayed(BGM_CURRENT_POSITION_WHAT, 500);
//        }
//    }
//
//    private void stopGetBGMPosition() {
//        if (mHandler != null) {
//            mHandler.removeCallbacksAndMessages(null);
//        }
//    }
//
//    /**
//     * addSubView ?????????view????????????
//     *
//     * @param view ???view
//     */
//    private void addSubView(View view) {
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//        mContainer.addView(view, params);//??????????????????
//    }
//
//
//    /**
//     * ??????????????????
//     */
//    private void initPhoneStateManger() {
//        if (mPhoneStateManger == null) {
//            mPhoneStateManger = new PhoneStateManger(this);
//            mPhoneStateManger.registPhoneStateListener();
//            mPhoneStateManger.setOnPhoneStateChangeListener(new PhoneStateManger.OnPhoneStateChangeListener() {
//
//                @Override
//                public void stateIdel() {
//                    // ??????
//                    if (mAliLiveEngine != null && mAliLiveEngine.isPublishing()) {
//                        mAliLiveEngine.setMute(false);
//                    }
//                }
//
//                @Override
//                public void stateOff() {
//                    // ??????
//                    if (mAliLiveEngine != null && mAliLiveEngine.isPublishing()) {
//                        mAliLiveEngine.setMute(true);
//                    }
//                }
//
//                @Override
//                public void stateRinging() {
//                    // ??????
//                    if (mAliLiveEngine != null && mAliLiveEngine.isPublishing()) {
//                        mAliLiveEngine.setMute(true);
//                    }
//                }
//            });
//        }
//
//    }
//
//    /**
//     * ????????????
//     */
//    private void startPreview() {
//        if (isPreviewing) {
//            return;
//        }
//        if (mAliLiveEngine == null) {
//            initLiveSDK();
//        }
////        ??????????????????????????????
//        if (mOrientationDetector != null && mOrientationDetector.canDetectOrientation()) {
//            mOrientationDetector.enable();
//        }
//
//        if (mAliLiveRenderView == null) {
//            mAliLiveRenderView = mAliLiveEngine.createRenderView(false);
//            addSubView(mAliLiveRenderView);
//            initFocus();
//            mAliLiveEngine.setPreviewMode(AliLiveConstants.AliLiveRenderMode.AliLiveRenderModeAuto,
//                    AliLiveConstants.AliLiveRenderMirrorMode.AliLiveRenderMirrorModeOnlyFront);
//        }
//        mAliLiveEngine.startPreview(mAliLiveRenderView);
//        if (isBeautyOpen) {
//            onBeautyOpen(true);
//            mAliLiveBeautyManager.enable(AliLiveBeautyManager.EnableType.Basic);
//            mAliLiveBeautyManager.setBeautyParam(AliLiveBeautyManager.BeautyParam.SkinBuffing_SkinBuffing, 0.5f);
//            mAliLiveBeautyManager.setBeautyParam(AliLiveBeautyManager.BeautyParam.SkinWhiting_SkinWhiting, 0.4f);
//            if (mBeautyManager != null) {
//                mBeautyManager.resumeParams();
//            }
//        }
//    }
//
//    private void startPublish(String url) {
//
////        startPreview();
//
////        mButtonListView.setButtonEnable("????????????", false);
////        mButtonListView.setButtonEnable("????????????", true);
//        if (TextUtils.isEmpty(url)) {
//            return;
//        }
//        if (mAliLiveEngine.isPublishing() && url.equals(mCurrentPushUrl)) {
//            return;
//        }
//        mCurrentPushUrl = url;
////        mShowMessage.setVisibility(View.VISIBLE);
//        Log.e(TAG, "??????push url :  " + mCurrentPushUrl);
////        mShowMessage.setText("??????push url : " + mCurrentPushUrl);
//        if (mAliLiveEngine.isPublishing()) {
//            mAliLiveEngine.stopPush();
//            mAliLiveEngine.startPush(mCurrentPushUrl);
//        } else {
//            mAliLiveEngine.startPush(mCurrentPushUrl);
//
//        }
//        mBinding.ivLivePlay.setVisibility(View.GONE);
//    }
//
//    private void stopPreview() {
////        mButtonListView.setButtonEnable("????????????", true);
////        mButtonListView.setButtonEnable("????????????", false);
//        if (mAliLiveEngine != null) {
//            mAliLiveEngine.stopPreview();
//        }
//        if (mOrientationDetector != null) {
//            mOrientationDetector.disable();
//        }
//
//        if (mAliLiveRenderView != null) {
//            mAliLiveRenderView.setVisibility(View.GONE);
//            mAliLiveRenderView.setVisibility(View.VISIBLE);
//        }
//    }
//
//    private void stopPublish() {
////        mButtonListView.setButtonEnable("????????????", true);
////        mButtonListView.setButtonEnable("????????????", false);
//        if (mAliLiveEngine != null && mAliLiveEngine.isPublishing()) {
//            mAliLiveEngine.stopPush();
//        }
//    }
//
//    private void destroyEngine() {
//        stopPreview();
//        stopPublish();
//        if (mAliLiveEngine != null) {
//            mAliLiveEngine.destroy();
//            mAliLiveEngine = null;
//        }
//    }
//
//    /**
//     * ??????????????????
//     */
//    private void startCaptureActivityForResult() {
////        stopPreview();
////        Intent intent = new Intent(LiveActivity.this, CaptureActivity.class);
////        startActivityForResult(intent, CaptureActivity.REQ_CODE);
//    }
//
//    private void showTipDialog(String tittle, String msg) {
//        CommonDialog dialog = new CommonDialog(this);
//        dialog.setDialogTitle(tittle);
//        dialog.setDialogContent(msg);
//        dialog.setConfirmButton(TextFormatUtil.getTextFormat(this, R.string.liveroom_btn_confirm), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }
//
//    /**
//     * ??????url???????????????
//     */
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
////        if (requestCode == CaptureActivity.REQ_CODE) {
////            switch (resultCode) {
////                case RESULT_OK:
////                    startPublish(data.getStringExtra(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN));
////                    break;
////                case RESULT_CANCELED:
////                    startPublish(mCurrentPushUrl);
////                    break;
////                default:
////                    break;
////            }
////        }
//
//    }
//
//
//    /**
//     * ???????????????
//     */
//    @Override
//    public void onBackPressed() {
//        if (mAliPushConfigView.getVisibility() == View.VISIBLE) {
//            mAliPushConfigView.setVisibility(View.GONE);
//        } else if (mBeautyView.getVisibility() == View.VISIBLE) {
//            mBeautyView.setVisibility(View.GONE);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        destroyEngine();
//        mAliLiveBeautyManager.destroy();
//        if (mOrientationDetector != null) {
//            mOrientationDetector.setOrientationChangedListener(null);
//        }
//        if (mPhoneStateManger != null) {
//            mPhoneStateManger.setOnPhoneStateChangeListener(null);
//            mPhoneStateManger.unRegistPhoneStateListener();
//            mPhoneStateManger = null;
//        }
//        stopGetBGMPosition();
//    }
//
//
//    /**
//     * ???????????????????????????
//     */
//    @Override
//    public void onButtonClick(String pageName, int pageIndex, String message, int position) {
//        onBeautyOpen(isBeautyOpen);
//        if (message.equals("????????????")) {
//            mBeautyView.setVisibility(View.GONE);
//        }
//        if (!isBeautyOpen) {
//            return;
//        }
//        if (mBeautyManager != null) {
//            mBeautyManager.onButtonClick(pageName, pageIndex, message, position);
//        }
//    }
//
//    @Override
//    public void onProgressChanged(String pageName, int pageIndex, String message, float position) {
//        onBeautyOpen(isBeautyOpen);
//        if (!isBeautyOpen) {
//            return;
//        }
//        if (mBeautyManager != null) {
//            mBeautyManager.onProgressChanged(pageName, pageIndex, message, position);
//        }
//    }
//
//    @Override
//    public void onSwitchChanged(String pageName, int pageIndex, String message, boolean isCheck) {
//        onBeautyOpen(isBeautyOpen);
//        if (!isBeautyOpen) {
//            return;
//        }
//        if (mBeautyManager != null) {
//            mBeautyManager.onSwitchChanged(pageName, pageIndex, message, isCheck);
//        }
//    }
//
//
//    @Override
//    public void onPageSwitch(String pageName, int pageIndex, boolean isCheck) {
//        onBeautyOpen(isBeautyOpen);
//        if (!isBeautyOpen) {
//            return;
//        }
//        if (mBeautyManager != null) {
//            mBeautyManager.onPageSwitch(pageName, pageIndex, isCheck);
//        }
//    }
//
//
//    //    ?????????????????????
//    @Override
//    public void onSaveClick() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(LiveActivity.this);
//        builder.setTitle("????????????????????????????????????")
//                .setNegativeButton("??????", null);
//        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
//
//            public void onClick(DialogInterface dialog, int which) {
//                //???????????????????????????????????????????????????????????????
//                destroyEngine();
//
//                initLiveSDK();
//
//                startPreview();
//                ThreadUtils.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        startPublish(mCurrentPushUrl);
//                    }
//                }, 1000);
//            }
//        });
//        builder.show();
//
//
//    }
//
//
//    //    ???????????????????????????????????????
//    @Override
//    public void onBeautyOpen(boolean isOpen) {
//        isBeautyOpen = isOpen;
//        mAliLiveBeautyManager.enable(isOpen ? AliLiveBeautyManager.EnableType.Basic : AliLiveBeautyManager.EnableType.Off);
//    }
//
//    @Override
//    public void onButtonClick(String message, int position) {
//        switch (message) {
//            case "????????????":
////                mCurrentPushUrl = mPushUrlEt.getText().toString();
//                if (TextUtils.isEmpty(mCurrentPushUrl)) {
//                    showTipDialog("??????", "?????????????????????...");
//                } else {
//                    startPublish(mCurrentPushUrl);
//                }
//                break;
//            case "????????????":
//                stopPublish();
//                break;
//            case "??????":
//                if (mBeautyView.getVisibility() == View.VISIBLE) {
//                    mBeautyView.setVisibility(View.GONE);
//                } else {
//                    mBeautyView.setVisibility(View.VISIBLE);
////                    mButtonListView.hideItems(true);
//                }
//                break;
//            case "????????????":
//                mAliPushConfigView.setVisibility(View.VISIBLE);
//                break;
//            case "??????":
//                startCaptureActivityForResult();
//                break;
//            case "??????":
//                if (!isMuteFlag) {
//                    if (mAliLiveEngine != null) {
//                        mAliLiveEngine.setMute(true);
//                    }
//                    Toast toast = Toast.makeText(this, "?????????", Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                    isMuteFlag = true;
//                } else {
//                    if (mAliLiveEngine != null) {
//                        mAliLiveEngine.setMute(false);
//                    }
//                    Toast toast = Toast.makeText(this, "????????????", Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                    isMuteFlag = false;
//                }
//                break;
//            case "?????????":
//                if (mAliLiveEngine != null) {
//                    mAliLiveEngine.switchCamera();
//                }
//                break;
//            case "??????":
//                if (mAdvanceSoundEffectView.getVisibility() == View.VISIBLE) {
//                    mAdvanceSoundEffectView.setVisibility(View.GONE);
//                } else {
//                    mAdvanceSoundEffectView.setVisibility(View.VISIBLE);
//                }
//                break;
//            case "????????????":
//                if (mDataView.getVisibility() == View.VISIBLE) {
//                    mDataView.setVisibility(View.GONE);
////                    mButtonListView.setButtonEnable("????????????", true);
//                } else {
//                    mDataView.setVisibility(View.VISIBLE);
////                    mButtonListView.setButtonEnable("????????????", false);
//                    if (maliLiveLocalVideoStats != null) {
//                        ((TextView) mDataView.findViewById(R.id.tv_data1)).setText("????????????:" + (int) (maliLiveLocalVideoStats.sentBitrate / 1024) + "kbps");
//                        ((TextView) mDataView.findViewById(R.id.tv_data2)).setText("????????????:" + maliLiveLocalVideoStats.sentFps + "fps");
//                        ((TextView) mDataView.findViewById(R.id.tv_data3)).setText("????????????:" + maliLiveLocalVideoStats.encodeFps);
//                    } else {
//                        mDataView.setVisibility(View.GONE);
//                        Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                break;
//
//            default:
//                break;
//        }
//    }
//}