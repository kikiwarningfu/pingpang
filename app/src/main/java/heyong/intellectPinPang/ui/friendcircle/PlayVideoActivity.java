package heyong.intellectPinPang.ui.friendcircle;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cn.jzvd.JzvdStd;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.intellectPinPang.R;
import me.jessyan.autosize.AutoSizeCompat;

public class PlayVideoActivity extends BaseActivity {
    private String url;
    boolean isPlayResume;
    JzvdStd jzVideoPlayerStandard;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_play_video;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        initData();
//        url="https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218114723HDu3hhxqIT.mp4";
        jzVideoPlayerStandard = (JzvdStd) findViewById(R.id.video_view);
        jzVideoPlayerStandard.setUp(""+url,
                "", JzvdStd.SCREEN_NORMAL);
//        jzVideoPlayerStandard.thumbImageView.setimage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
        jzVideoPlayerStandard.startVideo();
//        jzVideoPlayerStandard.mRetryLayout
        jzVideoPlayerStandard.startPreloading(); //开始预加载，加载完等待播放
        jzVideoPlayerStandard.startVideoAfterPreloading(); //如果预加载完会开始播放，如果未加载则开始加载
//
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPlayResume) {
            JzvdStd.goOnPlayOnResume();
            isPlayResume = false;
        }

    }

    private void initData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            //视频url
            url = getIntent().getExtras().getString("url");
            Log.e(TAG, "initData: url====" + url);
        }
    }


    @Override
    public void onBackPressed() {
        if (JzvdStd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JzvdStd.goOnPlayOnPause();

        isPlayResume = true;

        jzVideoPlayerStandard.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (jzVideoPlayerStandard != null) {
            jzVideoPlayerStandard.releaseAllVideos();
        }

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //横竖屏切换
        refresh();
    }

    /**
     * 横竖屏切换刷新
     */
    private void refresh() {
        boolean isBaseOnWidth = (getResources().getDisplayMetrics().widthPixels <= getResources().getDisplayMetrics().heightPixels);
        Window window = getWindow();
        if (window != null) {
            window.getDecorView().post(() -> {
                window.getDecorView().setVisibility(View.GONE);
                AutoSizeCompat.autoConvertDensity(getResources(), 420, isBaseOnWidth);
                window.getDecorView().setVisibility(View.VISIBLE);
            });
        }
    }


}
