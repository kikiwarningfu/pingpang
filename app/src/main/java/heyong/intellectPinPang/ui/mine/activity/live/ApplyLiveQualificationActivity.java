package heyong.intellectPinPang.ui.mine.activity.live;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import cn.jzvd.JzvdStd;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.SystemUtil;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.live.LiveUserInfoBean;
import heyong.intellectPinPang.databinding.ActivityApplyLiveQualificationBinding;
import heyong.intellectPinPang.live.model.LiveViewModel;

/**
 * 申请直播资格界面
 */
public class ApplyLiveQualificationActivity extends BaseActivity<ActivityApplyLiveQualificationBinding, LiveViewModel> implements View.OnClickListener {
    private String deviceInfo = "";
    private String videoUrl = "";

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.liveUserInfo();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_apply_live_qualification;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        mBinding.rlVideoView.setVisibility(View.GONE);
        mBinding.rlNoUploadView.setVisibility(View.VISIBLE);

        mBinding.llRetry.setVisibility(View.GONE);
        mBinding.tvBrand.setText("" + SystemUtil.getDeviceBrand() + "-" + SystemUtil.getSystemModel());
        deviceInfo = "" + SystemUtil.getDeviceBrand() + "-" + SystemUtil.getSystemModel();
        mViewModel.liveUserInfoLiveData.observe(this, new Observer<ResponseBean<LiveUserInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<LiveUserInfoBean> liveUserInfoBeanResponseBean) {
                 if (liveUserInfoBeanResponseBean.getErrorCode().equals("200")) {
                    LiveUserInfoBean data = liveUserInfoBeanResponseBean.getData();
                    if (data != null) {
                        int status = data.getStatus();
                        //	"status":"int //状态信息\r  0=申请中，1=审核通过，2=审核拒绝，3=禁用",
                        if (!TextUtils.isEmpty(data.getTestVideo())) {
                            mBinding.rlVideoView.setVisibility(View.VISIBLE);
                            mBinding.rlNoUploadView.setVisibility(View.GONE);
                            mBinding.llRetry.setVisibility(View.VISIBLE);
                            //将路径转换成uri
//                            mBinding.videoView.setUp("" + data.getTestVideo(),
//                                    JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
//                                    "");
                            mBinding.videoView.setUp(""+data.getTestVideo(),
                                    "", JzvdStd.SCREEN_NORMAL);
                        } else {
                            mBinding.llRetry.setVisibility(View.GONE);
                        }
                        videoUrl = data.getTestVideo();
                        deviceInfo = data.getDeviceInfo();
                        mBinding.tvBrand.setText("" + deviceInfo);

                        if (status == 0)//申请中
                        {
                            mBinding.tvApplyStatus.setText("审核中");
                            mBinding.tvUploadAgain.setVisibility(View.GONE);
                            mBinding.tvApplyStatus.setTextColor(Color.parseColor("#4795ED"));
                            mBinding.tvCommit.setEnabled(false);
                            mBinding.tvCommit.setBackgroundResource(R.drawable.shape_no_commit);
                            mBinding.tvCommit.setTextColor(Color.parseColor("#B2B2B2"));
                        } else if (status == 1)//审核通过  申请成功
                        {
                            mBinding.tvApplyStatus.setText("申请成功");
                            mBinding.tvApplyStatus.setTextColor(Color.parseColor("#4795ED"));
                            mBinding.tvCommit.setEnabled(false);
                            mBinding.tvCommit.setBackgroundResource(R.drawable.shape_no_commit);
                            mBinding.tvCommit.setTextColor(Color.parseColor("#B2B2B2"));
                            mBinding.tvCommit.setVisibility(View.GONE);
                            mBinding.tvUploadAgain.setVisibility(View.GONE);

                        } else if (status == 2)//审核拒绝
                        {
                            mBinding.tvApplyStatus.setText("申请失败");
                            mBinding.tvApplyStatus.setTextColor(Color.parseColor("#CE2626"));
                            mBinding.tvCommit.setEnabled(true);
                            mBinding.tvCommit.setText("重新提交");
                            mBinding.tvCommit.setBackgroundResource(R.drawable.shape_croci_corners_25);
                            mBinding.tvCommit.setTextColor(Color.parseColor("#ffffff"));
                            mBinding.tvApplyResult.setVisibility(View.VISIBLE);
                            mBinding.tvApplyResult.setText("" + data.getRemark());

                        } else {
                            //禁用
                            mBinding.tvApplyStatus.setText("禁用");
                            mBinding.tvApplyStatus.setTextColor(Color.parseColor("#CE2626"));
                            mBinding.tvCommit.setEnabled(false);
                            mBinding.tvCommit.setText("无法申请");
                            mBinding.tvCommit.setBackgroundResource(R.drawable.shape_no_commit);
                            mBinding.tvCommit.setTextColor(Color.parseColor("#B2B2B2"));
                            mBinding.tvApplyResult.setVisibility(View.GONE);
                            mBinding.tvUploadAgain.setVisibility(View.GONE);

                        }
                    }
                } else {
                    toast("" + liveUserInfoBeanResponseBean.getMessage());
                }
            }
        });

        mViewModel.liveUserApplyLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    toast("已提交申请");
                    finish();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                if (TextUtils.isEmpty(videoUrl)) {
                    toast("请上传视频地址");
                    return;
                }
                //调用接口
                mViewModel.liveUserApply("" + deviceInfo, videoUrl);

                break;
            case R.id.rl_no_upload_view:
                //申请成为直播的视频
//                Intent intent = new Intent(ApplyLiveQualificationActivity.this, VideoSelectActivity.class);
//                startActivityForResult(intent, 2);

                break;
            case R.id.ll_retry://重新上传

//                Intent intent2 = new Intent(ApplyLiveQualificationActivity.this, VideoSelectActivity.class);
//                startActivityForResult(intent2, 2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                videoUrl = data.getStringExtra("url");
                Log.e(TAG, "onActivityResult: 上传视频地址" + videoUrl);
                mBinding.rlVideoView.setVisibility(View.VISIBLE);
                mBinding.rlNoUploadView.setVisibility(View.GONE);
                mBinding.llRetry.setVisibility(View.VISIBLE);
                //将路径转换成uri

                mBinding.videoView.setUp(""+videoUrl,
                        "", JzvdStd.SCREEN_NORMAL);
            }
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
        mBinding.videoView.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBinding.videoView != null) {
            mBinding.videoView.releaseAllVideos();
        }
    }

}