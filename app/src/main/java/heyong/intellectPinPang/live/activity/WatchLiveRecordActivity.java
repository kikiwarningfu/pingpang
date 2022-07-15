package heyong.intellectPinPang.live.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;

import com.elvishew.xlog.XLog;

import org.song.videoplayer.DemoQSVideoView;
import org.song.videoplayer.IVideoPlayer;
import org.song.videoplayer.PlayListener;
import org.song.videoplayer.media.IjkMedia;


import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.handong.framework.utils.SystemUtil;
import heyong.intellectPinPang.bean.live.LiveRoomBean;
import heyong.intellectPinPang.live.qsvideo.danmaku.DanmakuConfig;
import heyong.intellectPinPang.live.qsvideo.danmaku.DanmakuControl;
import heyong.intellectPinPang.live.qsvideo.danmaku.QSDanmakuParser;
import heyong.intellectPinPang.live.qsvideo.io.FileUtil;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityWatchLiveRecordBinding;
import heyong.intellectPinPang.live.model.LiveViewModel;
import heyong.intellectPinPang.utils.Utils;
import heyong.lzy.imagepicker.util.StatusBarUtil;

/**
 * 录播 播放视频  回放
 */
public class WatchLiveRecordActivity extends BaseActivity<ActivityWatchLiveRecordBinding, LiveViewModel> implements View.OnClickListener {
    String url = "http://images.xlttsports.com/44b39e91404a93c330314ba1893f3f10_627182533029024944__uid_s_570355328__uid_e_video.m3u8";
    //    private JzvdDanmu jzvdDanmu;
    private Button btnDanmu, btnDiyDanmu;
    private Handler handler = new Handler();
    private String id = "";
    DemoQSVideoView demoVideoView;
    DanmakuControl danmakuControl;
    private boolean isBuy = false;
    private int myPosition = 0;

    public static void goActivity(Context context, String id) {
        Intent intent = new Intent(context, WatchLiveRecordActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_watch_live_record;
    }

    @Override
    public void initView(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        XLog.e("xxxxxxd   initView");
        myPosition = myPosition + 1;
        StatusBarUtil.statusBarLightMode(this);

        mBinding.getRoot().setFitsSystemWindows(true);
//        jzvdDanmu = findViewById(R.id.myjsvdstd);
//        url = "http://images.xlttsports.com/44b39e91404a93c330314ba1893f3f10_627182533029024944__uid_s_570355328__uid_e_video.m3u8";
//        url = responseBean.getData().getVideoAddress();
//        jzvdDanmu.setUp(url, "", JzvdStd.SCREEN_NORMAL, JZMediaIjk.class);

        findView();
        demoVideoView.getCoverImageView().setImageResource(R.drawable.img_live_dafault);
//        demoVideoView.setLayoutParams(new LinearLayout.LayoutParams(-1,
//                getResources().getDisplayMetrics().widthPixels * 9 / 16));
        //进入全屏的模式 0横屏 1竖屏 2传感器自动横竖屏 3根据视频比例自动确定横竖屏      -1什么都不做
        demoVideoView.enterFullMode = 3;
        //是否非全屏下也可以手势调节进度
        demoVideoView.isWindowGesture = true;

        demoVideoView.setOnQsListener(new DemoQSVideoView.OnQsListener() {
            @Override
            public void click(int type) {
                //跳转到支付页面
                PayLiveChargeActivity.Companion.startActivity(WatchLiveRecordActivity.this, "" + id);


            }

            @Override
            public void onClickMei() {
                StatusBarUtil.statusBarLightMode(WatchLiveRecordActivity.this);
            }
        });
        demoVideoView.setPlayListener(new PlayListener() {

            int mode;

            @Override
            public void onStatus(int status) {//播放状态
                if (status == IVideoPlayer.STATE_AUTO_COMPLETE)
                    demoVideoView.quitWindowFullscreen();//播放完成退出全屏
            }

            @Override//全屏/普通/浮窗
            public void onMode(int mode) {
                this.mode = mode;
            }

            @Override
            public void onEvent(int what, Integer... extra) {
                //系统浮窗点击退出退出activity
                if (what == DemoQSVideoView.EVENT_CLICK_VIEW && extra[0] == R.id.help_float_close) {
                    if (demoVideoView.isSystemFloatMode()) finish();
                }
            }

        });
        //集成弹幕 播放前调用
        danmakuControl = DanmakuControl.bind(
                demoVideoView,
                new QSDanmakuParser(FileUtil.readAssets("danmu.json", this)),
                DanmakuConfig.getDefaultContext()
        );
        danmakuControl.hide();
        demoVideoView.release();
        demoVideoView.setDecodeMedia(IjkMedia.class);

//        demoVideoView.setUp(
//                QSVideo.Build(url).title("这是标清标题").definition("标清").resolution("标清 720P").build(),
//                QSVideo.Build(url).title("这是高清标题").definition("高清").resolution("高清 1080P").build(),
//                QSVideo.Build(url).title("这是2K标题").definition("2K").resolution("超高清 2K").build(),
//                QSVideo.Build(url).title("这是4K标题").definition("4K").resolution("极致 4K").build()
//
//        );
//        mViewModel.leaveLiveBroadcastingRoomData.observe(this, new Observer<ResponseBean>() {
//            @Override
//            public void onChanged(ResponseBean responseBean) {
//                if (responseBean.getErrorCode().equals("200")) {
//                    if (mExitDialog != null && mExitDialog.isShowing()) {
//                        mExitDialog.dismiss();
//                    }
//                    finish();
//                } else {
//                    toast("" + responseBean.getMessage());
//                }
//            }
//        });
        mViewModel.watchPlaybackData.observe(this, new Observer<ResponseBean<LiveRoomBean>>() {
            @Override
            public void onChanged(ResponseBean<LiveRoomBean> responseBean) {

                if (responseBean.getErrorCode().equals("200")) {

                    LiveRoomBean data = responseBean.getData();
                    if (data != null) {
                        url = responseBean.getData().getVideoAddress();
//                        url = "http://images.xlttsports.com/839fddf5074c90b6dc7bc6a057cbe72a_627182533029024944.m3u8";
                        //demoVideoView.seekTo(12000);
//                      demoVideoView.openCache();//缓存配置见最后,缓存框架太久没更新,不是很稳定
//                        demoVideoView.seekTo(0);

                        mBinding.tvTitle.setText("" + data.getItemTitle());
                        mBinding.onlineCount.setText("" + Utils.getShowVisitorWan(Double.parseDouble(String.valueOf(data.getOnLineCount()))));
                        String itemType = data.getItemType();
                        if (itemType.equals("1") || itemType.equals("2")) {
                            //团体或单打
                            mBinding.llLeftTwoContainer.setVisibility(View.GONE);
                            mBinding.llRightTwoContainer.setVisibility(View.GONE);
                            ImageLoader.loadImage(mBinding.ivLeftOne, data.getPlayer1Img(), R.drawable.morentouxiang);
                            ImageLoader.loadImage(mBinding.ivRightOne, data.getPlayer3Img(), R.drawable.morentouxiang);
                            mBinding.tvLeftOne.setText("" + data.getPlayer1());
                            mBinding.tvRightOne.setText("" + data.getPlayer3());
                        } else {
                            mBinding.llLeftTwoContainer.setVisibility(View.VISIBLE);
                            mBinding.llRightTwoContainer.setVisibility(View.VISIBLE);

                            ImageLoader.loadImage(mBinding.ivLeftOne, data.getPlayer2Img(), R.drawable.morentouxiang);
                            ImageLoader.loadImage(mBinding.ivLeftTwo, data.getPlayer1Img(), R.drawable.morentouxiang);
                            mBinding.tvLeftTwo.setText("" + data.getPlayer1());
                            mBinding.tvLeftOne.setText("" + data.getPlayer2());

                            ImageLoader.loadImage(mBinding.ivRightOne, data.getPlayer3Img(), R.drawable.morentouxiang);
                            ImageLoader.loadImage(mBinding.ivRightTwo, data.getPlayer4Img(), R.drawable.morentouxiang);
                            mBinding.tvRightOne.setText("" + data.getPlayer3());
                            mBinding.tvRightTwo.setText("" + data.getPlayer4());
                        }

                        isBuy = responseBean.getData().isPurchased();
                        if (isBuy) {
                            if (myPosition < 3) {
                                demoVideoView.setUp(url, "" + responseBean.getData().getItemTitle());
                                demoVideoView.play();
                            } else {
                                demoVideoView.setUp(url, "" + responseBean.getData().getItemTitle());
                                demoVideoView.play();
                                handler.removeCallbacks(runnable);
                                if (position > 0) {
                                    demoVideoView.seekTo(position);
                                    position = 0;
                                }
                            }

                            demoVideoView.setPurchaseView(false, 0);
                        } else {
                            XLog.e("true   time=10");
                            int surplusSecond = responseBean.getData().getSurplusSecond();
//                            if (responseBean.getData().isCanTry()) {
                            XLog.e("开始60秒");
                            XLog.e("position==="+myPosition);

//                            } else {
//                                demoVideoView.setPurchaseView(false, 0);
//                            }
                            if (myPosition <=2) {
                                demoVideoView.setUp(url, "" + responseBean.getData().getItemTitle());

                                demoVideoView.setPurchaseView(true, 61);
                                demoVideoView.play();
                            } else {
//                                if (demoVideoView.isSystemFloatMode())
//                                    return;
                                //暂停
//                                playFlag = demoVideoView.isPlaying();
//                                demoVideoView.stop();
                            }
                        }
                    }


                } else {
                    toast("" + responseBean.getMessage());
                }


            }
        });

    }

    private void findView() {
        demoVideoView = findViewById(R.id.myjsvdstd);

    }

    private Dialog mExitDialog;

    @Override
    public void onBackPressed() {

        if (demoVideoView.onBackPressed()) {
            if (demoVideoView.isSystemFloatMode())
                //系统浮窗返回上一界面 android:launchMode="singleTask"
                moveTaskToBack(true);
            return;
        }
        super.onBackPressed();
//        shoeExitDialog();
    }

    private void shoeExitDialog() {
        mExitDialog = showDialog(R.string.end_live_streaming_title_owner,
                R.string.end_live_streaming_message_owner, view -> {
                    mViewModel.leaveLiveBroadcastingRoom(id);

                });
    }

    protected Dialog showDialog(int title, int message,
                                final View.OnClickListener positiveClickListener) {
        final Dialog dialog = new Dialog(this,
                R.style.live_room_dialog_center_in_window);
        dialog.setContentView(R.layout.live_room_dialog);
        AppCompatTextView titleTextView = dialog.findViewById(R.id.dialog_title);
        titleTextView.setText(title);
        AppCompatTextView msgTextView = dialog.findViewById(R.id.dialog_message);
        msgTextView.setText(message);
        dialog.findViewById(R.id.dialog_negative_button)
                .setOnClickListener(view -> dialog.dismiss());
        dialog.findViewById(R.id.dialog_positive_button)
                .setOnClickListener(positiveClickListener);
//        hideStatusBar(dialog.getWindow(), false);
        dialog.show();
        return dialog;
    }

    private void dismissDialog() {
        if (mExitDialog != null && mExitDialog.isShowing()) {
            mExitDialog.dismiss();
        }
    }

    //    /**
//     * 模拟弹幕
//     */
//    private void testDanmu() {
//        handler.removeCallbacksAndMessages(null);
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                jzvdDanmu.addDanmaku("嫂子666", false);
//                handler.postDelayed(this, 100);
//            }
//        });
//    }
    private boolean playFlag;//记录退出时播放状态 回来的时候继续播放
    private int position;//记录销毁时的进度 回来继续该进度播放

    @Override
    public void onResume() {
        super.onResume();
        XLog.e("xxxxxxd   onResume");

        myPosition = myPosition + 1;
//        if (playFlag)
//            demoVideoView.play();
//        handler.removeCallbacks(runnable);
//        if (position > 0) {
//            demoVideoView.seekTo(position);
//            position = 0;
//        }
        id = getIntent().getStringExtra("id");
        mViewModel.watchPlayback(id);
        StatusBarUtil.statusBarLightMode(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (demoVideoView.isSystemFloatMode())
            return;
        //暂停
        playFlag = demoVideoView.isPlaying();
        demoVideoView.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (demoVideoView.isSystemFloatMode())
            return;
        //进入后台不马上销毁,延时15秒
        handler.postDelayed(runnable, 1000 * 15);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();//销毁
        if (demoVideoView.isSystemFloatMode())
            demoVideoView.quitWindowFloat();
        demoVideoView.release();
        handler.removeCallbacks(runnable);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (demoVideoView.getCurrentState() != IVideoPlayer.STATE_AUTO_COMPLETE)
                position = demoVideoView.getPosition();
            demoVideoView.release();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
