package heyong.intellectPinPang.ui.friendcircle;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityFriendCircleVideoPlayBinding;

public class FriendCircleVideoPlayActivity extends BaseActivity<ActivityFriendCircleVideoPlayBinding, BaseViewModel> {
    public static final String PATH = "path";
    private String strLocalPath = "";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_friend_circle_video_play;
    }

    @Override
    public void initView(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        strLocalPath=getIntent().getStringExtra(PATH);
        Gson gson = new Gson();
        String s = gson.toJson(strLocalPath);
        Log.e(TAG, "onPublishComplete: " + s);
        mBinding.myjsvdstd.setUp(strLocalPath, "");
//        mBinding.myjsvdstd.startPreloading(); //开始预加载，加载完等待播放
//        mBinding.myjsvdstd.startVideoAfterPreloading(); //如果预加载完会开始播放，如果未加载则开始加载
        mBinding.myjsvdstd.startVideo();
        mBinding.ivDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
//                                MediaMetadataRetriever media = new MediaMetadataRetriever();
//                                viewModel.fileid.set(result.videoId);
//                                media.setDataSource(videoPaths);
//                                Bitmap frameAtTime = media.getFrameAtTime();
//                                String width = media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
//                                String height = media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
//        ToastUtils.showShort("width=====" + width + ":========height==" + height);
//                                mBinding.myjsvdstd.thumbImageView.setImageBitmap(frameAtTime);
//                                viewModel.pubItemEntityList.add(new PubItemEntity("1", result.videoURL));
//                                mBinding.myjsvdstd.posterImageView.setImageBitmap(frameAtTime);
    }
    /**
     * 竖屏并退出全屏
     */
    private void changeScrenNormal() {
        if (mBinding.myjsvdstd != null && mBinding.myjsvdstd.screen == mBinding.myjsvdstd.SCREEN_FULLSCREEN) {
            mBinding.myjsvdstd.autoQuitFullscreen();
        }
    }

    /**
     * 横屏
     */
    private void changeScreenFullLandscape(float x) {
        //从竖屏状态进入横屏
        if (mBinding.myjsvdstd != null && mBinding.myjsvdstd.screen != mBinding.myjsvdstd.SCREEN_FULLSCREEN) {
            if ((System.currentTimeMillis() - mBinding.myjsvdstd.lastAutoFullscreenTime) > 2000) {
                mBinding.myjsvdstd.autoFullscreen(x);
                mBinding.myjsvdstd.lastAutoFullscreenTime = System.currentTimeMillis();
            }
        }
    }
    @Override
    public void onBackPressed() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//        ActivityCompat.finishAfterTransition(this);
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (mBinding.myjsvdstd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBinding.myjsvdstd.releaseAllVideos();
    }
}