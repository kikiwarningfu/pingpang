//package heyong.intellectPinPang.live.activity;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.lifecycle.Observer;
//import androidx.recyclerview.widget.GridLayoutManager;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.SurfaceTexture;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.Surface;
//import android.view.TextureView;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//import com.blankj.utilcode.util.SizeUtils;
//import com.bumptech.glide.Glide;
//import com.google.gson.Gson;
//import com.qiniu.android.http.ResponseInfo;
//import com.qiniu.android.storage.UpCompletionHandler;
//import com.qiniu.android.storage.UploadManager;
//
//import org.json.JSONObject;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Random;
//import java.util.Set;
//
//import heyong.intellectPinPang.ui.BaseActivity;
//import heyong.handong.framework.base.ResponseBean;
//import heyong.intellectPinPang.R;
//import heyong.intellectPinPang.databinding.ActivityVideoSelectBinding;
//import heyong.intellectPinPang.live.adapter.VideoListAdapter;
//import heyong.intellectPinPang.live.adapter.decoration.ThreeGridDecoration;
//import heyong.intellectPinPang.bean.live.Topic;
//import heyong.intellectPinPang.live.model.LiveViewModel;
//import heyong.intellectPinPang.live.permission.KbPermission;
//import heyong.intellectPinPang.utils.VideoFileUtils;
//
///**
// * 视频上传
// */
//public class VideoSelectActivity extends BaseActivity<ActivityVideoSelectBinding, LiveViewModel> implements TextureView.SurfaceTextureListener {
//
//    private static final int MSG_SHOW_VIDEO_LIST = 1000;
//    private static final int MSG_HIDE_PLAY_PAUS_VIEW = 2000;
//    private static final int MSG_DELAY_FIRST_FRAME = 3000;
//
//    public static final int STATE_IDLE = 0; //通常状态
//    public static final int STATE_PLAYING = 1; //视频正在播放
//    public static final int STATE_PAUSED = 2; //视频暂停
//    public static final int DEFAULT_SHOW_TIME = 3000; // 控制器的默认显示时间3秒
//
////    @BindView(R.id.tv_next)
////    TextView mTitleNext; // 下一步
////
////    @BindView(R.id.tv_cancel)
////    TextView mTvCancel; // 取消
////
////    @BindView(R.id.iv_empty)
////    ImageView mIvEmpty; //占位图
////
////    @BindView(R.id.tv_empty)
////    TextView mTvEmpty; //空白时文案
////
////    @BindView(R.id.rl_video_play)
////    SquareRelativeLayout mVideoPlay; //承载播放器的布局
////
////    @BindView(R.id.rv_video_list)
////    RecyclerView mVideoList; //视频列表
////
////    @BindView(R.id.fl_loading)
////    FrameLayout mLoading; //加载中
////
////    //进度条相关
////    @BindView(R.id.fl_circle_progress)
////    ViewGroup mFlCircleProgress;
////
////    @BindView(R.id.circle_progress)
////    KbWithWordsCircleProgressBar mCircleProgress;
//
//    private TextureView mTextureview; //更换为TextureView
//    private Surface mSurface; //surface
//    private ImageView mPlayPause; // 播放暂停按钮
//    private ImageView mVideoBg; // 视频缩略图
//
//    public List<Topic> mAllVideoList = new ArrayList<>(); // 视频信息集合
//    private VideoListAdapter mVideoAdapter; // 视频列表适配器
//    private MediaPlayer mMediaPlayer = new MediaPlayer(); // 播放器
//
//    private Context mContext;
////    private ApiInterface mApi;
//
//    private int mCurState = STATE_IDLE; // 当前状态
//    private boolean mPlayPuseIsShow = false; // 是否显示了播放暂停
//
//
//    String key = "";
//    String picPath = "";
//
//
//    @SuppressLint("HandlerLeak")
//    public Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == MSG_SHOW_VIDEO_LIST) {
//                if (VideoSelectActivity.this.isFinishing()) {
//                    return;
//                }
//                mBinding.flLoading.setVisibility(View.GONE);
//                if (mAllVideoList.size() <= 0) {
//                    return;
//                }
//
//                mBinding.ivEmpty.setVisibility(View.GONE);
//                mBinding.ivEmpty.setVisibility(View.GONE);
//
//                addSurfaceView(mBinding.rlVideoPlay, mAllVideoList.get(0));
//                Glide.with(mContext).load(new File(mAllVideoList.get(0).getLocalVideoPath())).into(mVideoBg);
//                mVideoAdapter.addData(mAllVideoList);
//            } else if (msg.what == MSG_HIDE_PLAY_PAUS_VIEW) {
//                if (mMediaPlayer == null) return;
//                if (mMediaPlayer.isPlaying()) {
//                    mPlayPause.setVisibility(View.GONE);
//                    mPlayPuseIsShow = false;
//                } else {
//                    mPlayPause.setVisibility(View.VISIBLE);
//                    mPlayPuseIsShow = true;
//                }
//            } else if (msg.what == MSG_DELAY_FIRST_FRAME) {
//                mVideoBg.setVisibility(View.GONE);
//            }
//        }
//    };
//
//    @Override
//    public int getLayoutRes() {
//        return R.layout.activity_video_select;
//    }
//
//    @Override
//    public void initView(@Nullable Bundle savedInstanceState) {
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////            SystemUtil.setLightStatusBar(this, Color.WHITE);
////        }
//        Log.e(TAG, "onCreate: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath());
//        mContext = VideoSelectActivity.this;
////        mApi = ApiHelper.getInstance().buildRetrofit(ApiHelper.BASE_URL)
////                .createService(ApiInterface.class);
//
//        mBinding.rvVideoList.setLayoutManager(new GridLayoutManager(this, 3));
//        mVideoAdapter = new VideoListAdapter(mContext);
//        mBinding.rvVideoList.setAdapter(mVideoAdapter);
//        mBinding.rvVideoList.addItemDecoration(new ThreeGridDecoration(SizeUtils.dp2px(2),
//                SizeUtils.dp2px(2)));
//
//        getData();
//
//        initVideo();
//
//        setListener();
//
//
//        mViewModel.getUploadTokenLiveData.observe(this, new Observer<ResponseBean>() {
//            @Override
//            public void onChanged(ResponseBean responseBean) {
//                if (responseBean.getErrorCode().equals("200")) {
//                    UploadManager uploadManager = new UploadManager();
//                    // 设置图片名字
//                    uploadImage(picPath, responseBean.getData().toString(), uploadManager, key);
//                } else {
//                    toast("暂无权限");
//                }
//
//            }
//        });
//
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (mMediaPlayer.isPlaying()) {
//            mMediaPlayer.stop();
//            mCurState = STATE_IDLE;
//            mHandler.removeMessages(MSG_HIDE_PLAY_PAUS_VIEW);
//            if (mPlayPause != null) {
//                mPlayPause.setImageResource(R.drawable.img_play);
//                mPlayPause.setVisibility(View.VISIBLE);
//            }
//            if (mVideoBg != null)
//                mVideoBg.setVisibility(View.VISIBLE);
//        }
//    }
//
//    /**
//     * 配置视频播放相关
//     */
//    private void initVideo() {
//
//        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                // 默认背景隐藏，播放按钮隐藏
//                //mVideoBg.setVisibility(View.GONE);
//                mPlayPause.setImageResource(R.drawable.img_pause);
//                mPlayPause.setVisibility(View.VISIBLE);
//                mPlayPuseIsShow = true;
//                Log.e(TAG, "onPrepared: 播放");
//                mMediaPlayer.start();
//                mCurState = STATE_PLAYING;
//                //延迟隐藏第一帧图片
//                mHandler.removeMessages(MSG_DELAY_FIRST_FRAME);
//                mHandler.sendEmptyMessageDelayed(MSG_DELAY_FIRST_FRAME, 300);
//                //三秒消失播放按钮
//                mHandler.removeMessages(MSG_HIDE_PLAY_PAUS_VIEW);
//                mHandler.sendEmptyMessageDelayed(MSG_HIDE_PLAY_PAUS_VIEW, DEFAULT_SHOW_TIME);
//            }
//        });
//
//        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//
//                if (mVideoAdapter.getTopList().size() > 0) {
//                    deleteSurfaceView(mBinding.rlVideoPlay);
//                    addSurfaceView(mBinding.rlVideoPlay, mVideoAdapter.getCheckPosition());
//                    initVideo();
////                mVideoBg.setImageBitmap(mVideoAdapter.getCheckPosition().getThumb());
//                    if (mVideoAdapter.getCheckPosition() != null) {
//                        Glide.with(mContext).load(new File(mVideoAdapter.getCheckPosition().getLocalVideoPath())).into(mVideoBg);
//                    }
//                    // 移除已发送的消息
//                    mHandler.removeMessages(MSG_HIDE_PLAY_PAUS_VIEW);
//                    mVideoBg.setVisibility(View.VISIBLE);
//                    mPlayPause.setImageResource(R.drawable.img_play);
//                    mPlayPause.setVisibility(View.VISIBLE);
//                    mPlayPuseIsShow = true;
//                    mCurState = STATE_IDLE;
//                }
//
//            }
//        });
//    }
//
//    /**
//     * 设置监听
//     */
//    private void setListener() {
//        mVideoAdapter.setPhotoAlbumListener(new PhotoAlbumListener<Topic>() {
//            @Override
//            public void onSelected(Topic topic) {
//                if (topic == mVideoAdapter.getCheckPosition())
//                    return;
//                if (mMediaPlayer != null) {
//                    mMediaPlayer.stop();
//                    mMediaPlayer.reset();
//
//                    mCurState = STATE_IDLE;
//
//                    deleteSurfaceView(mBinding.rlVideoPlay);
//                    addSurfaceView(mBinding.rlVideoPlay, topic);
//                    initVideo();
//                }
//                Glide.with(mContext).load(topic.getLocalVideoPath()).into(mVideoBg);
//                // 默认背景显示，播放按钮显示
//                mVideoBg.setVisibility(View.VISIBLE);
//                mPlayPause.setImageResource(R.drawable.img_play);
//                mPlayPause.setVisibility(View.VISIBLE);
//                mPlayPuseIsShow = true;
//            }
//
//            @Override
//            public void onClickCamera() {
//
//            }
//        });
//
//        mBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        mBinding.rlVideoPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mMediaPlayer.isPlaying() && mPlayPuseIsShow == true) {
//                    mPlayPause.setVisibility(View.GONE);
//                    mPlayPuseIsShow = false;
//                } else if (mMediaPlayer.isPlaying() && mPlayPuseIsShow == false) {
//                    mPlayPause.setVisibility(View.VISIBLE);
//                    mPlayPuseIsShow = true;
//                    mHandler.removeMessages(MSG_HIDE_PLAY_PAUS_VIEW);
//                    mHandler.sendEmptyMessageDelayed(MSG_HIDE_PLAY_PAUS_VIEW, DEFAULT_SHOW_TIME);
//                }
//            }
//        });
//
//        mBinding.tvNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mVideoAdapter.getTopList().size() > 0) {
//                    if (mVideoAdapter.getCheckPosition() != null) {
//
//                        if (mMediaPlayer.isPlaying()) {
//                            mMediaPlayer.pause();
//                            mCurState = STATE_PAUSED;
//                            mPlayPause.setImageResource(R.drawable.img_play);
//                            mPlayPause.setVisibility(View.VISIBLE);
//                            mPlayPuseIsShow = true;
//                        }
//
//                        uploadVideo();
//                    }
//                } else {
//                    toast("请先录视频 然后上传");
//                }
//            }
//        });
//    }
//
//
//    /**
//     * 遍历  获取视频资源
//     */
//    private void getData() {
//
//        mBinding.flLoading.setVisibility(View.VISIBLE);
//        // 获取本地相册内视频文件
//        new Thread() {
//            @Override
//            public void run() {
//                VideoFileUtils.getVideoFile(mAllVideoList, new File(Environment.getExternalStorageDirectory() /*+ "/DCIM"*/ + "/DCIM/Camera"));
////                VideoFileUtils.getVideoFile(mAllVideoList, new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()));
//                if (mContext != null) {
//                    mHandler.sendEmptyMessage(MSG_SHOW_VIDEO_LIST);
//                }
//            }
//        }.start();
//    }
//
//    /**
//     * 添加SurfaceView
//     */
//    private void addSurfaceView(RelativeLayout relativeLayout, Topic videoItem) {
//
//        //mSurface = new SurfaceView(this);
//        mTextureview = new TextureView(this);
//        mTextureview.setSurfaceTextureListener(this);//设置监听函数  重写4个方法
//
//        RelativeLayout.LayoutParams lp1 = null;
//        // 特殊处理阿里云拍摄的视频
////        if (videoItem.getLocalVideoPath().contains("DaishuAli")) {
////            videoItem.setRotation("90");
////        }
//        try {
//            //先判断旋转方向再判断视频宽高度，确保适用于大多数视频
//            if (videoItem == null) {
//                lp1 = new RelativeLayout.LayoutParams(
//                        SizeUtils.dp2px(211), ViewGroup.LayoutParams.MATCH_PARENT
//                );
//            } else if (Integer.parseInt(videoItem.getRotation()) == 0 || Integer.parseInt(videoItem.getRotation()) == 180) {
//                if (Integer.parseInt(videoItem.getHeight()) > Integer.parseInt(videoItem.getWidth())) {
//                    lp1 = new RelativeLayout.LayoutParams(
//                            SizeUtils.dp2px(211), ViewGroup.LayoutParams.MATCH_PARENT
//                    );
//                } else if (Integer.parseInt(videoItem.getHeight()) == Integer.parseInt(videoItem.getWidth())) {
//                    lp1 = new RelativeLayout.LayoutParams(
//                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
//                    );
//                } else {
//                    lp1 = new RelativeLayout.LayoutParams(
//                            ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(202)
//                    );
//                }
//            } else if (Integer.parseInt(videoItem.getRotation()) == 90) {
//                lp1 = new RelativeLayout.LayoutParams(
//                        SizeUtils.dp2px(211), ViewGroup.LayoutParams.MATCH_PARENT
//                );
//            } else if (Integer.parseInt(videoItem.getRotation()) == 270) {
//                lp1 = new RelativeLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(202)
//                );
//            } else {
//                lp1 = new RelativeLayout.LayoutParams(
//                        SizeUtils.dp2px(211), ViewGroup.LayoutParams.MATCH_PARENT
//                );
//            }
//            lp1.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//
//            relativeLayout.addView(mTextureview, lp1);
//
//            mVideoBg = new ImageView(this);
//            mVideoBg.setScaleType(ImageView.ScaleType.FIT_XY);
//            relativeLayout.addView(mVideoBg, lp1);
//
//            mPlayPause = new ImageView(this);
//            mPlayPause.setImageResource(R.drawable.img_play);
//            RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
//                    SizeUtils.dp2px(30), SizeUtils.dp2px(36)
//            );
//
//            mPlayPause.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mVideoAdapter.getCheckPosition() == null) {
//                        Log.e(TAG, "onClick: " + mVideoAdapter.getCheckPosition().getLocalVideoPath());
//                        return;
//                    }
//                    Log.e(TAG, "onClick: " + mCurState);
//                    switch (mCurState) {
//
//                        case STATE_PLAYING:
//                            mMediaPlayer.pause();
//                            mCurState = STATE_PAUSED;
//                            mPlayPause.setImageResource(R.drawable.img_play);
//                            break;
//                        case STATE_PAUSED:
//                            mMediaPlayer.start();
//                            mCurState = STATE_PLAYING;
//                            mPlayPause.setImageResource(R.drawable.img_pause);
//                            mHandler.removeMessages(MSG_HIDE_PLAY_PAUS_VIEW);
//                            mHandler.sendEmptyMessageDelayed(MSG_HIDE_PLAY_PAUS_VIEW, DEFAULT_SHOW_TIME);
//                            break;
//                        case STATE_IDLE:
//                            try {
//                                mMediaPlayer.reset();
//                                Log.e(TAG, "onClick: STATE_IDLE");
//                                mMediaPlayer.setSurface(mSurface);
//                                mMediaPlayer.setDataSource(mVideoAdapter.getCheckPosition().getLocalVideoPath());
//                                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                                mMediaPlayer.setScreenOnWhilePlaying(true);
//                                mMediaPlayer.prepareAsync();
//
//                            } catch (Exception e) {
//                                Toast.makeText(VideoSelectActivity.this, "该视频无法播放，换一个吧~", Toast.LENGTH_SHORT).show();
//                            }
//                            break;
//                    }
//                }
//            });
//
//            lp2.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//            relativeLayout.addView(mPlayPause, lp2);
//        } catch (Exception e) {
//            lp1 = new RelativeLayout.LayoutParams(
//                    SizeUtils.dp2px(211), ViewGroup.LayoutParams.MATCH_PARENT
//            );
//            lp1.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//
//            mVideoBg = new ImageView(this);
//            mVideoBg.setBackgroundColor(getResources().getColor(R.color.black));
//            relativeLayout.addView(mVideoBg, lp1);
//
//            mPlayPause = new ImageView(this);
//            mPlayPause.setImageResource(R.drawable.img_play);
//            RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
//                    SizeUtils.dp2px(30), SizeUtils.dp2px(36)
//            );
//
//            mPlayPause.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(mContext, "该视频无法播放，换一个吧~", Toast.LENGTH_SHORT).show();
//                }
//            });
//            mVideoBg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(mContext, "该视频无法播放，换一个吧~", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            lp2.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//            relativeLayout.addView(mPlayPause, lp2);
//        }
//    }
//
//
//    /**
//     * 删除SurfaceView
//     *
//     * @param relativeLayout
//     */
//    private void deleteSurfaceView(RelativeLayout relativeLayout) {
//        relativeLayout.removeAllViews();
//    }
//
//    private void uploadVideo() {
//        showLoading();
//
//        File file = new File(mVideoAdapter.getCheckPosition().getLocalVideoPath());
//
//        Random random = new Random();
//        Set<Integer> set = new HashSet<Integer>();
//        while (set.size() < 3) {
//            int randomInt = random.nextInt(10);
//            set.add(randomInt);
//        }
//        StringBuffer sb = new StringBuffer();
//        for (Integer i : set) {
//            sb.append("" + i);
//        }
//        key = "android_video_" + sb.toString() + file.getPath();
//        Log.e(TAG, "uploadVideo: name===" + key);
//        picPath = file.getPath();
//        Log.e(TAG, "uploadImg2QiNiu: localPath===" + file.getPath());
//        mViewModel.getUploadToken(key);
//        //路径
//        //是否需要压缩
//        //实现上传进度监听
////        ProgressRequestBody requestFile = new ProgressRequestBody(file, "image/*", new ProgressRequestBody.UploadCallbacks() {
////            @Override
////            public void onProgressUpdate(int percentage) {
////                Log.e(TAG, "onProgressUpdate: " + percentage);
////                mCircleProgress.setProgress(percentage);
////            }
////
////            @Override
////            public void onError() {
////
////            }
////
////            @Override
////            public void onFinish() {
////            }
////        });
////
////        MultipartBody.Part body =
////                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
////
////        mApi.uploadFile(body).enqueue(new Callback<UploadVideoResp>() {
////            @Override
////            public void onResponse(Call<UploadVideoResp> call, Response<UploadVideoResp> response) {
////                mBinding.flCircleProgress.setVisibility(View.GONE);
////                UploadVideoResp resp = response.body();
////                if (resp != null) {
////                    Toast.makeText(mContext, "视频上传成功！", Toast.LENGTH_SHORT).show();
////                }
////            }
////
////            @Override
////            public void onFailure(Call<UploadVideoResp> call, Throwable t) {
////                mBinding.flCircleProgress.setVisibility(View.GONE);
////                Toast.makeText(mContext, "视频上传失败，稍后重试", Toast.LENGTH_SHORT).show();
////            }
////        });
//    }
//
//    private void uploadImage(String picPath, String token, UploadManager uploadManager, String key) {
//        Log.e(TAG, "uploadImage: picPath===" + picPath);
//        Log.e(TAG, "uploadImage: token===" + token);
//        Log.e(TAG, "uploadImage:key ===" + key);
////        uploadManager.put(picPath, key, Auth.create(Constants.AccessKey, Constants.SecretKey).uploadToken("xlttsports"), new UpCompletionHandler() {
//
//                    uploadManager.put(picPath, key, token, new UpCompletionHandler() {
//            @Override
//            public void complete(String key, ResponseInfo info, JSONObject res) {
//                // info.error中包含了错误信息，可打印调试
//                // 上传成功后将key值上传到自己的服务器
//                if (info.isOK()) {
//                    Log.e(TAG, "token===" + token);
//                    String headpicPath = "http://images.xlttsports.com/" + key;
//                    Log.e(TAG, "complete: " + headpicPath);
//
//                    dismissLoading();
////                UploadVideoResp resp = response.body();
////                if (resp != null) {
//                    Toast.makeText(mContext, "视频上传成功！", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent();
//                    intent.putExtra("url", headpicPath);
//                    setResult(RESULT_OK, intent);
//                    finish();
////                }
//                } else {
//                    dismissLoading();
//                    Toast.makeText(mContext, "视频上传失败，稍后重试", Toast.LENGTH_SHORT).show();
//                }
//                Log.e(TAG, "complete: error===" + new Gson().toJson(info));
//
//            }
//        }, null);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        KbPermission.onRequestPermissionResult(requestCode, permissions, grantResults);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mMediaPlayer != null) {
//            mMediaPlayer.release();
//            mMediaPlayer = null;
//        }
//    }
//
//    @Override
//    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//        mSurface = new Surface(surface);
//    }
//
//    @Override
//    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
//    }
//
//    @Override
//    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
//        return true;
//    }
//
//    @Override
//    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//
//    }
//
//    @Override
//    public void finish() {
//        super.finish();
//        //Activity退出时动画
//        overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_out_top);
//    }
//}