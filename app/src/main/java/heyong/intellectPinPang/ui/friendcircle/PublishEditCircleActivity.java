package heyong.intellectPinPang.ui.friendcircle;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.elvishew.xlog.XLog;
import com.google.gson.Gson;
import com.nanchen.compresshelper.CompressHelper;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.filter.Filter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import heyong.handong.framework.account.AccountHelper;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.friend.MyPoiItems;
import heyong.intellectPinPang.bean.friend.PushMomentMessagePostBean;
import heyong.intellectPinPang.bus.RxBus;
import heyong.intellectPinPang.databinding.ActivityPublishEditCircleBinding;
import heyong.intellectPinPang.event.FriendCircleEvent;
import heyong.intellectPinPang.event.SwipQuanziEvent;
import heyong.intellectPinPang.slidecomprosser.SiliCompressor;
import heyong.intellectPinPang.utils.ImagePickerUtil;
import heyong.intellectPinPang.utils.RxBusSubscriber;
import heyong.intellectPinPang.widget.GifSizeFilter;
import heyong.intellectPinPang.widget.GridImageView;
import heyong.intellectPinPang.widget.PhotoPubSelDialog;
import heyong.lzy.imagepicker.ImagePicker;
import heyong.lzy.imagepicker.bean.ImageItem;
import heyong.lzy.imagepicker.ui.ImageBroseNewActivity;
import heyong.lzy.imagepicker.ui.ImageGridActivity;

/**
 * ????????????  192.168.101.34
 */
public class PublishEditCircleActivity extends BaseActivity<ActivityPublishEditCircleBinding, PublishEditCircleViewModel> implements View.OnClickListener, ImagePickerUtil.OnImageCallback {
    private static final int CONSTANTS_FRIEND_LOCATION = 87;
    private int pushType = 0;
    private ImagePickerUtil imagePickerUtil;
    public static final String TAG = PublishEditCircleActivity.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE = 23;
    private File takeImageFile;
    private String photoSavePath;
    private String photoSaveName;
    private int REQUEST_CODE_CAMERA = 1002; //??????
    private static final int REQUEST_CODE_SELECT = 10002;

    Uri photoUri;
    private String videoPath = "";
    private String key = "";
    private ArrayList<String> dataList = new ArrayList<String>();//???????????????list ??????

    private String imagePath = "";
    private String imageKey = "";
    private int picSize = 0;
    PushMomentMessagePostBean pushMomentMessagePostBean;
    private String province;
    private String city;
    private String area;
    private String detailAddress;
    private String cityCode = "0";
    private String title = "";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_publish_edit_circle;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        imagePickerUtil = new ImagePickerUtil();
        pushMomentMessagePostBean = new PushMomentMessagePostBean();
        pushMomentMessagePostBean.setUserId(AccountHelper.getUserId());


        mBinding.gridImageView.setImageClickListener(new GridImageView.ImageClickListener() {
            @Override
            public void onImageClick(String item, int position) {
                Intent intent = new Intent(PublishEditCircleActivity.this, ImageBroseNewActivity.class);
                intent.putExtra("path", item);
                intent.putExtra("current_position", position);
                intent.putExtra("is_delete", "1");
                startActivityForResult(intent, 9999);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                Log.e(TAG, "onImageClick: clickListPosition===" + mBinding.gridImageView.getImages().get(position));
                Log.e(TAG, "onImageClick: item====" + item + "    position====" + position);
            }
        });
        mBinding.gridImageView.setImageAddClickListener(() -> {

            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions.request(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.CAMERA)
                    .subscribe(new RxBusSubscriber<Boolean>() {
                        @Override
                        protected void onEvent(Boolean aBoolean) {
                            if (aBoolean) {
                                PhotoPubSelDialog photoPubSelDialog = new PhotoPubSelDialog(PublishEditCircleActivity.this);
                                photoPubSelDialog.setClickListener(new PhotoPubSelDialog.OnSelectListener() {
                                    //??????
                                    @Override
                                    public void takePhoto() {
                                        int size = mViewModel.imgItems.size();
                                        Log.e(TAG, "takePhoto: size====" + mViewModel.imgItems.size());

                                        if (size >= 9) {
                                            toast("????????????9???");
                                        } else {
                                            Intent intent = new Intent(PublishEditCircleActivity.this, ImageGridActivity.class);
                                            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // ???????????????????????????
                                            startActivityForResult(intent, REQUEST_CODE_SELECT);
//                                            ImagePicker imagePicker = ImagePicker.getInstance();
//                                            imagePicker.setMultiMode(false);
//                                            imagePicker.setShowCamera(false);
//                                            imagePicker.setCrop(false);
//                                            imagePicker.setFocusHeight(SystemHelper.getScreenInfo(PublishEditCircleActivity.this).heightPixels);
//                                            imagePicker.setFocusWidth(SystemHelper.getScreenInfo(PublishEditCircleActivity.this).widthPixels);
//                                            imagePickerUtil.takePhoto(PublishEditCircleActivity.this, 5, PublishEditCircleActivity.this);
                                        }
                                    }

                                    //???????????????
                                    @Override
                                    public void selectPhoto() {
                                        /*????????????9  ?????????????????????*/
                                        int size = mBinding.gridImageView.getImages().size();
                                        Log.e(TAG, "selectPhoto: size====" + mViewModel.imgItems.size());
                                        if (size >= 9) {
                                            toast("????????????9???");
                                        } else {
                                            int size1 = mBinding.gridImageView.getImages().size();
                                            if (size1 >= 1) {
                                                Matisse.from(PublishEditCircleActivity.this)
                                                        .choose(MimeType.ofImage())
                                                        .theme(R.style.Matisse_Dracula)
                                                        .countable(false)
                                                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                                        .maxSelectable(9 - size1)
                                                        .originalEnable(true)
                                                        .maxOriginalSize(10)
                                                        .imageEngine(new PicassoEngine())
                                                        .forResult(REQUEST_CODE_CHOOSE);
                                            } else {
                                                Matisse.from(PublishEditCircleActivity.this)
                                                        .choose(MimeType.ofAll())
                                                        .theme(R.style.Matisse_Dracula)
                                                        .countable(false)
                                                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                                        .maxSelectable(9 - size1)
                                                        .originalEnable(true)
                                                        .maxOriginalSize(10)
                                                        .imageEngine(new PicassoEngine())
                                                        .forResult(REQUEST_CODE_CHOOSE);
                                            }
                                        }
                                    }

                                    @Override
                                    public void openVideo() {
                                        //????????????
                                        int size = mViewModel.imgItems.size();
                                        Log.e(TAG, "openVideo: size====" + mViewModel.imgItems.size());

                                        if (size > 0) {
                                            toast("????????????????????????????????????");
                                        } else {
                                            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                                            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);//??????????????????10???
                                            startActivityForResult(intent, 4);
                                        }

                                    }
                                });
                                photoPubSelDialog.show();
                            } else {
                                //?????????????????????????????????????????????
                                toast("??????????????????????????????????????????");
                            }
                        }
                    });


        });
        mViewModel.getUploadTokenLiveDataVideo.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
//                if (responseBean.isSuccess()) {
                Log.e(TAG, "onChanged: uploadToken===" + responseBean.getData().toString());
                if (responseBean.getErrorCode().equals("200")) {

                    Configuration configuration = new Configuration.Builder()
                            .connectTimeout(10)
                            //.zone(zone)
                            //.dns(buildDefaultDns())//??????dns?????????
                            .responseTimeout(60).build();

                    UploadManager uploadManager = new UploadManager(configuration);
                    // ??????????????????
                    uploadVideo(responseBean.getData().toString(), uploadManager, videoPath);
                } else {
                    toast("????????????");
                }
            }
        });
        mViewModel.getUploadTokenLiveDataImage.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                UploadManager uploadManager = new UploadManager();
                uploadManager.put(imagePath, imageKey, responseBean.getData().toString(), new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        // info.error??????????????????????????????????????????
                        if (info.isOK()) {
                            String headpicPath = "http://images.xlttsports.com/" + key;
                            Log.e(TAG, "complete: " + headpicPath);
                            mViewModel.imgItems.add("" + "http://images.xlttsports.com/" + key);
                            Log.e(TAG, "getUploadTokenLiveDataImage: size====" + mViewModel.imgItems.size());

                            picNum++;
                            if (picNum < picSize) {
                                Log.e(TAG, "complete: picNum====" + picNum);
                                uoloadMatchImage(dataList.get(picNum));
                            } else {
                                dismissLoading();
                                picNum = 0;
                                flag = true;
                                toast("????????????");
                                return;
                            }
                        } else {
                            dismissLoading();
                            toast("????????????" + new Gson().toJson(info));
                        }
                    }
                }, null);

            }
        });
        mViewModel.pushMomentMessageLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("0")) {
                    toast("" + responseBean.getMessage());
                    EventBus.getDefault().post(new SwipQuanziEvent() );
                    RxBus.getDefault().post(new SwipQuanziEvent());
                    finish();
                } else {
                    toast("????????????" );
                }

            }
        });
    }

    private void uploadVideo(String uploadToken, UploadManager uploadManager, String videoPaths) {
        UploadOptions opt = new UploadOptions(null, null, true, new UpProgressHandler() {
            @Override
            public void progress(String key, double percent) {
                Log.e("qiniutest", "percent:" + percent);
            }
        }, null);
        File uploadFile = new File(videoPaths);

        uploadManager.put(uploadFile, key, uploadToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo respInfo,
                                         JSONObject jsonData) {
                        dismissLoading();
                        long endTime = System.currentTimeMillis();
                        if (respInfo.isOK()) {
//                            http://images.xlttsports.com/android_video_289/storage/emulated/0/Movies/Silicompressor/videos/VIDEO_20210714_143224.mp4
                            String videoPath = "http://images.xlttsports.com/" + key;
                            XLog.e(TAG, "complete: videoPath===" + videoPath);
                            Log.e(TAG, "uploadVideo: videoPath===" + "http://images.xlttsports.com/" + key);
                            mViewModel.videoItems.add("" + "http://images.xlttsports.com/" + key);
                            mViewModel.videolocalPath.add(videoPaths);
                            try {
                                String fileKey = jsonData.getString("key");
                                String fileHash = jsonData.getString("hash");
//                                Log.e("zw", jsonData.toString() + respInfo.toString());
//                                Logger.e("--------------------------------UPTime/ms: " + (endTime - startTime));
////                                Logger.e("File Size: " + Tools.formatSize(uploadFileLength));
//                                Logger.e("File Key: " + fileKey);
//                                Logger.e("File Hash: " + fileHash);
//                                Logger.e("X-Reqid: " + respInfo.reqId);
//                                Logger.e("X-Via: " + respInfo.xvia);
//                                Logger.e("--------------------------------" + "\n????????????");
//                                Gson gson = new Gson();
//                                String s = gson.toJson(videoPaths);
//                                Log.e(TAG, "onPublishComplete: " + s);
//                                mBinding.myjsvdstd.setUp(videoPaths, "");
                                mBinding.gridImageView.setVisibility(View.GONE);
                                mBinding.flVideo.setVisibility(View.VISIBLE);
//                                mBinding.myjsvdstd.setVisibility(View.GONE);
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

                                Glide.with(PublishEditCircleActivity.this)
                                        .setDefaultRequestOptions(
                                                new RequestOptions()
                                                        .frame(0)
                                                        .centerCrop()
                                        )
                                        .load(videoPaths)
                                        .into(mBinding.ivLogo);


                            } catch (JSONException e) {
                                XLog.e("--------------------------------" + "\n????????????");
                            }
                        } else {
                            Log.e(TAG, "complete: onError ???????????? ");
//                            Logger.e("--------------------------------" + "\n????????????" + respInfo.toString());
//                            Logger.e("--------------------------------" + "\n????????????" + jsonData.toString());

                        }
                    }

                }, opt);
    }

    /*************************************** ????????????******************************************************/
//????????????????????????????????????????????????true???????????????????????????????????????????????????????????????
    private boolean needCheckBackLocation = false;
    //???????????????target > 28????????????????????????????????????????????????"????????????"???????????????
    private static String BACK_LOCATION_PERMISSION = "android.permission.ACCESS_BACKGROUND_LOCATION";
    /**
     * ????????????????????????????????????????????????
     */
    private boolean isNeedCheck = true;

    /**
     * ?????????????????????????????????
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            BACK_LOCATION_PERMISSION
    };
    private static final int PERMISSON_REQUESTCODE = 0;

    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                if (requestCode == PERMISSON_REQUESTCODE) {
                    if (!verifyPermissions(paramArrayOfInt)) {
                        showMissingPermissionDialog();
                        isNeedCheck = false;
                    } else {
                        startActivityForResult(new Intent(PublishEditCircleActivity.this, FriendLocationActivity.class), CONSTANTS_FRIEND_LOCATION);
                        Log.e(TAG, "onRequestPermissionsResult: ??????????????????");
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        try {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    @TargetApi(23)
    private List<String> findDeniedPermissions(String[] permissions) {
        try {
            List<String> needRequestPermissonList = new ArrayList<String>();
            if (Build.VERSION.SDK_INT >= 23 && getApplicationInfo().targetSdkVersion >= 23) {
                for (String perm : permissions) {
                    if (checkMySelfPermission(perm) != PackageManager.PERMISSION_GRANTED
                            || shouldShowMyRequestPermissionRationale(perm)) {
                        if (!needCheckBackLocation
                                && BACK_LOCATION_PERMISSION.equals(perm)) {
                            continue;
                        }
                        needRequestPermissonList.add(perm);
                    }
                }
            }
            return needRequestPermissonList;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    private int checkMySelfPermission(String perm) {
        try {
            Method method = getClass().getMethod("checkSelfPermission", new Class[]{String.class});
            Integer permissionInt = (Integer) method.invoke(this, perm);
            return permissionInt;
        } catch (Throwable e) {
        }
        return -1;
    }

    private boolean shouldShowMyRequestPermissionRationale(String perm) {
        try {
            Method method = getClass().getMethod("shouldShowRequestPermissionRationale", new Class[]{String.class});
            Boolean permissionInt = (Boolean) method.invoke(this, perm);
            return permissionInt;
        } catch (Throwable e) {
        }
        return false;
    }

    /**
     * @param
     * @since 2.5.0
     */
    @TargetApi(23)
    private void checkPermissions(String... permissions) {
        try {
            if (Build.VERSION.SDK_INT >= 23 && getApplicationInfo().targetSdkVersion >= 23) {
                List<String> needRequestPermissonList = findDeniedPermissions(permissions);
                if (null != needRequestPermissonList
                        && needRequestPermissonList.size() > 0) {
                    try {
                        String[] array = needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]);
                        Method method = getClass().getMethod("requestPermissions", new Class[]{String[].class, int.class});
                        method.invoke(this, array, 0);
                    } catch (Throwable e) {

                    }
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:


                finish();

                break;
            case R.id.rl_address:
                if (Build.VERSION.SDK_INT > 28
                        && getApplicationContext().getApplicationInfo().targetSdkVersion > 28) {
                    needPermissions = new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            BACK_LOCATION_PERMISSION
                    };
                    needCheckBackLocation = true;
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(PublishEditCircleActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        //????????????
//            Log.e(TAG, "initLocation: ?????????????????????");
                        Log.e(TAG, "initGetMei: ????????????fragment ????????????");
                        if (isNeedCheck) {
                            checkPermissions(needPermissions);
                        }
                    } else {
                        Log.e(TAG, "initGetMei: ??????????????????fragment ????????????");
                        startActivityForResult(new Intent(PublishEditCircleActivity.this, FriendLocationActivity.class), CONSTANTS_FRIEND_LOCATION);
//            Log.e(TAG, "initLocation: ??????????????????????????? ??????????????????");

                    }

                }

                break;
            case R.id.iv_play://???????????????????????????
                int size2 = mViewModel.videoItems.size();
                String localPath = mViewModel.videolocalPath.get(0);
                Intent intent = new Intent(PublishEditCircleActivity.this, FriendCircleVideoPlayActivity.class);
                intent.putExtra("path", localPath);
                startActivityForResult(intent, 8888);
                break;

            case R.id.tv_publish:
                String strInputText = mBinding.etContent.getText().toString();
                int size = mViewModel.imgItems.size();
                int size1 = mViewModel.videoItems.size();
                if ((!TextUtils.isEmpty(strInputText)) || size != 0 || size1 != 0) {
                    if (!TextUtils.isEmpty(strInputText) && size == 0 && size1 == 0) {
                        pushType = 1;
                    }
                    if (!TextUtils.isEmpty(strInputText) && size != 0) {
                        pushType = 2;
                    }
                    if (!TextUtils.isEmpty(strInputText) && size1 != 0) {
                        pushType = 3;
                    }
                    pushMomentMessagePostBean.setContent(strInputText);
                    if (size > 0) {
                        List<PushMomentMessagePostBean.PictureListBean> pictureListBeans = new ArrayList<>();
                        for (int m = 0; m < size; m++) {
                            PushMomentMessagePostBean.PictureListBean filePixelsBean = new PushMomentMessagePostBean.PictureListBean();
                            PushMomentMessagePostBean.PictureListBean.FilePixelsBean filePixelsBean1 = new PushMomentMessagePostBean.PictureListBean.FilePixelsBean();
                            filePixelsBean1.setFileUrl("" + mViewModel.imgItems.get(m));
                            filePixelsBean.setFilePixels(filePixelsBean1);
                            pictureListBeans.add(filePixelsBean);
                        }
                        pushMomentMessagePostBean.setPictureList(new Gson().toJson(pictureListBeans));
                    }
                    if (size1 > 0) {

                        List<PushMomentMessagePostBean.PictureListBean> videoListBeans = new ArrayList<>();
                        for (int k = 0; k < size1; k++) {
                            PushMomentMessagePostBean.PictureListBean filePixelsBean = new PushMomentMessagePostBean.PictureListBean();
                            PushMomentMessagePostBean.PictureListBean.FilePixelsBean filePixelsBean1 = new PushMomentMessagePostBean.PictureListBean.FilePixelsBean();
                            filePixelsBean1.setFileUrl("" + mViewModel.videoItems.get(k));
                            filePixelsBean.setFilePixels(filePixelsBean1);
                            videoListBeans.add(filePixelsBean);
                        }
                        pushMomentMessagePostBean.setVideoList(new Gson().toJson(videoListBeans));
                    }
                    pushMomentMessagePostBean.setType(pushType);
                    pushMomentMessagePostBean.setAreaCode(Long.valueOf(cityCode));
                    pushMomentMessagePostBean.setAddress("" + province + city + area + title);
                    pushMomentMessagePostBean.setProvince("" + province);
                    pushMomentMessagePostBean.setCity("" + city);
                    pushMomentMessagePostBean.setArea("" + area);
                    pushMomentMessagePostBean.setDetailAddr("" + title);

                    Log.e(TAG, "" + new Gson().toJson(pushMomentMessagePostBean));
                    mViewModel.pushMomentMessage(pushMomentMessagePostBean);

                } else {
                    if (TextUtils.isEmpty(strInputText)) {
                        toast("???????????????");
                        return;
                    }
                    if (size == 0) {
                        toast("???????????????");
                        return;
                    }
                    if (size1 == 0) {
                        toast("???????????????");
                        return;
                    }
                }


                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        imagePickerUtil.handResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CODE_CHOOSE) {//???????????????
                //
                List<Uri> uris = Matisse.obtainResult(data);
                List<String> pathList = Matisse.obtainPathResult(data);
                Log.e(TAG, "onActivityResult: " + new Gson().toJson(uris));
                Log.e(TAG, "onActivityResult: " + new Gson().toJson(pathList));

                Log.e("OnActivityResult ", "-=====" + String.valueOf(Matisse.obtainOriginalState(data))
                        + "   ====" + Matisse.obtainResult(data));
                if (pathList.size() == 1) {
                    if (pathList.get(0).contains("mp4") || pathList.get(0).contains("3Gp") || pathList.get(0).contains("avi") || pathList.get(0).contains("rmvb") ||
                            pathList.get(0).contains("Flv")) {
                        showLoading();
                        Log.e(TAG, "onActivityResult: ?????????size==="+getFileOrFilesSize(pathList.get(0), 2) );
                        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/Silicompressor/videos");
                        if (f.mkdirs() || f.isDirectory())
                            new VideoCompressAsyncTask(PublishEditCircleActivity.this).execute(pathList.get(0), f.getPath());
                    } else {
                        //??????????????? ????????????  ????????????

                        Log.e(TAG, "onActivityResult: ???????????? ??????===" + pathList.get(0));
                        showLoading();
                        picSize = 1;
                        List<String> images = new ArrayList<>();
                        dataList.clear();
                        Log.e(TAG, "onActivityResult: pathList===" + new Gson().toJson(pathList));
                        for (String item : pathList) {

                            images.add(item);
                            dataList.add(item);
                        }

//                        mViewModel.imgItems.addAll(images);
                        Log.e(TAG, "getUploadTokenLiveDataImage: size====" + mViewModel.imgItems.size());

                        mBinding.gridImageView.setImages(images);
                        uoloadMatchImage(pathList.get(0));
                    }
                } else {
                    //????????????
                    List<String> images = new ArrayList<>();
                    dataList.clear();
                    Log.e(TAG, "onActivityResult: pathList===" + new Gson().toJson(pathList));
                    for (String item : pathList) {

                        images.add(item);
                        dataList.add(item);
                    }

//                    mViewModel.imgItems.addAll(images);
                    mBinding.gridImageView.setImages(images);

                    if (flag == true) {
                        showLoading();
                        picSize = dataList.size();
                        uoloadMatchImage(dataList.get(picNum));
                        flag = false;
                    } else {
                        toast("????????????????????????...");
                    }

                }
            } else if (requestCode == 4) {
                //????????????
                Uri uri = data.getData();
                Cursor cursor = this.getContentResolver().query(uri, null, null,
                        null, null);
                if (cursor != null && cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.VideoColumns._ID));
                    String filePath = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Video.VideoColumns.DATA));
                    Log.e(TAG, "onActivityResult: filePath===" + filePath);

                    Log.e(TAG, "getFileOrFilesSize  onActivityResult: " + getFileOrFilesSize(filePath, 2));
                    Log.e(TAG, "onActivityResult: filePath=== length===" + new File(filePath).length());
                    cursor.close();
                    Log.e(TAG, "getFileOrFilesSize  onActivityResult: 1=====" + getFileOrFilesSize(filePath, 2));

                    File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/Silicompressor/videos");
                    if (f.mkdirs() || f.isDirectory())
                        new VideoCompressAsyncTask(PublishEditCircleActivity.this).execute(filePath, f.getPath());


                }


            } else if (requestCode == 9999) {
                int position = data.getIntExtra("position", 0);
                Log.e(TAG, "onActivityResult: position===" + position);

                try {
                    dataList.clear();
                    mViewModel.imgItems.remove(position);
                    mBinding.gridImageView.resetImages(position);
                }catch (Exception e)
                {

                }

            } else if (requestCode == 8888) {
                mViewModel.videoItems.clear();
                mViewModel.videolocalPath.clear();
                mBinding.flVideo.setVisibility(View.GONE);
                mBinding.gridImageView.setVisibility(View.VISIBLE);

            } else if (requestCode == CONSTANTS_FRIEND_LOCATION) {
                MyPoiItems poiItemList = (MyPoiItems) data.getSerializableExtra("datas");
                if (poiItemList.getProviceName() == null) {
                    mBinding.tvAddress.setText("??????");
                    province = "";
                    city = "";
                    area = "";
                    cityCode = "0";
                    detailAddress = "";
                    title = "";
                } else {
                    province = poiItemList.getProviceName();
                    city = poiItemList.getCityName();
                    area = poiItemList.getAreName();
                    cityCode = poiItemList.getCityCode();
                    detailAddress = poiItemList.getDetailAddress();
                    title = "" + poiItemList.getTitle();
                    mBinding.tvAddress.setText("" + poiItemList.getTitle());
                    Log.e(TAG, "onActivityResult: " + province + city + area + "    " + poiItemList.getTitle());
                }

            }
        } else if (resultCode == 1004) {
            if (requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> imagess = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Log.e(TAG, "callBack: " + new Gson().toJson(imagess));
                List<String> images = new ArrayList<>();
                dataList.clear();
                for (ImageItem item : imagess
                ) {
//                    int degree = readPictureDegree(item.path);
//                    Bitmap bitmap = rotateBitmap(BitmapFactory.decodeFile(item.path), degree);
//                    saveBitmap(item.path, bitmap);
                    images.add(item.path);
                    dataList.add(item.path);
                }

//        mViewModel.imgItems.addAll(images);
                mBinding.gridImageView.setImages(images);

                if (flag == true) {
                    picSize = dataList.size();
                    uoloadMatchImage(dataList.get(picNum));
                    flag = false;
                } else {
                    toast("????????????????????????...");
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(FriendCircleEvent friendCircleEvent) {
        Log.e(TAG, "messageEventBus: " + new Gson().toJson(friendCircleEvent));
        MyPoiItems poiItemList = friendCircleEvent.getMyPoiItems();
        province = poiItemList.getProviceName();
        city = poiItemList.getCityName();
        area = poiItemList.getAreName();
        cityCode = poiItemList.getCityCode();
        detailAddress = poiItemList.getDetailAddress();
        mBinding.tvAddress.setText("" + poiItemList.getTitle());
        title = poiItemList.getTitle();
    }


    private int picNum = 0;
    private boolean flag = true;

    @Override
    public void callBack(List<ImageItem> list) {
        //??????
        Log.e(TAG, "callBack: " + new Gson().toJson(list));
        List<String> images = new ArrayList<>();
        dataList.clear();
        for (ImageItem item : list
        ) {
//            int degree = readPictureDegree(item.path);
//            Bitmap bitmap = rotateBitmap(BitmapFactory.decodeFile(item.path), degree);
//            saveBitmap(item.path, bitmap);
            images.add(item.path);
            dataList.add(item.path);
        }

//        mViewModel.imgItems.addAll(images);
        mBinding.gridImageView.setImages(images);

        if (flag == true) {
            picSize = dataList.size();
            uoloadMatchImage(dataList.get(picNum));
            flag = false;
        } else {
            toast("????????????????????????...");
        }

    }

    private void uoloadMatchImage(String path) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 3) {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set) {
            sb.append("" + i);
        }
        imageKey = "android_img_" + sdf.format(new Date()) + sb.toString();
        File file2 = CompressHelper.getDefault(this).compressToFile(new File(path));
        imagePath = file2.getPath();
//        Log.e(TAG, "uploadImg2QiNiu: localPath===" + imagePath);
        mViewModel.getUploadTokenImage(key);
    }

    class VideoCompressAsyncTask extends AsyncTask<String, String, String> {

        Context mContext;

        public VideoCompressAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... paths) {
            String filePath = null;
            try {

                MediaMetadataRetriever media = new MediaMetadataRetriever();
                media.setDataSource(paths[0]);
                int metadataKeyDuration = media.METADATA_KEY_DURATION;
                Log.e(TAG, "doInBackground: ????????????" + metadataKeyDuration);
                Log.e(TAG, "doInBackground: ????????????" + media.METADATA_KEY_VIDEO_WIDTH);
                Log.e(TAG, "doInBackground: ????????????" + media.METADATA_KEY_VIDEO_HEIGHT);
//                SiliCompressor.with(mContext).compressVideo()
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(paths[0]);
                String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH); //???
                String height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT); //???
                String rotation = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);//?????????????????????
                long duration = Long.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) * 1000;//???????????????
                Log.e(TAG, "doInBackground: width===" +width);
                Log.e(TAG, "doInBackground:height "+height );
                Log.e(TAG, "doInBackground: rotation"+rotation );
                Log.e(TAG, "doInBackground: duration"+duration );
                filePath = SiliCompressor.with(mContext).compressVideo(paths[0], paths[1],Integer.parseInt(width),Integer.parseInt(height),1000000);


                Log.e(TAG, "getFileOrFilesSize====doInBackground: " + getFileOrFilesSize(filePath, 2));
                Log.e(TAG, "doInBackground: ???????????????===" + filePath.length());
                Log.e(TAG, "onActivityResult: ?????????ize==="+getFileOrFilesSize(filePath, 2) );

                Random random = new Random();
//                Set<Integer> set = new HashSet<Integer>();
//                while (set.size() < 3) {
//                    int randomInt = random.nextInt(10);
//                    set.add(randomInt);
//                }

                key = "android_" + new File(filePath).getName();
                Log.e(TAG, "uploadVideo: name===" + key);

                videoPath = filePath;
                mViewModel.getUploadTokenVideo(key);
//
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return filePath;

        }


        @Override
        protected void onPostExecute(String compressedFilePath) {
            super.onPostExecute(compressedFilePath);
            File imageFile = new File(compressedFilePath);
            float length = imageFile.length() / 1024f; // Size in KB
            String value;
            if (length >= 1024)
                value = length / 1024f + " MB";
            else
                value = length + " KB";
//            String text = String.format(Locale.US, "%s\nName: %s\nSize: %s", getString(R.string.video_compression_complete), imageFile.getName(), value);
//            compressionMsg.setVisibility(View.GONE);
//            picDescription.setVisibility(View.VISIBLE);
//            picDescription.setText(text);

            Log.i("Silicompressor", "Path: " + compressedFilePath);


        }
    }


    /**
     * ????????????????????????????????????????????????
     *
     * @param filePath ????????????
     * @param sizeType ?????????????????????1???B???2???KB???3???MB???4???GB
     * @return double????????????
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "????????????????????????!");
        }
        Log.e(TAG, "getFileOrFilesSize: size===" + FormetFileSize(blockSize, sizeType));
        return FormetFileSize(blockSize, sizeType);
    }

    /**
     * ????????????????????????
     *
     * @param file
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e(TAG, "???????????????????????????!");
        }
        return size;
    }

    /**
     * ?????????????????????
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * ??????????????????
     *
     * @param fileS
     * @return
     */
    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * ??????????????????,?????????????????????
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    public static final int SIZETYPE_B = 1;//???????????????????????????B???double???
    public static final int SIZETYPE_KB = 2;//???????????????????????????KB???double???
    public static final int SIZETYPE_MB = 3;//???????????????????????????MB???double???
    public static final int SIZETYPE_GB = 4;//???????????????????????????GB???double???

    /*??????????????????*/
    public static String getRealFilePath(final Context context, final Uri uri) {
        try {
            List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
            if (packs != null) {
                String fileProviderClassName = FileProvider.class.getName();
                for (PackageInfo pack : packs) {
                    ProviderInfo[] providers = pack.providers;
                    if (providers != null) {
                        for (ProviderInfo provider : providers) {
                            if (uri.getAuthority().equals(provider.authority)) {
                                if (provider.name.equalsIgnoreCase(fileProviderClassName)) {
                                    Class<FileProvider> fileProviderClass = FileProvider.class;
                                    try {
                                        Method getPathStrategy = fileProviderClass.getDeclaredMethod("getPathStrategy", Context.class, String.class);
                                        getPathStrategy.setAccessible(true);
                                        Object invoke = getPathStrategy.invoke(null, context, uri.getAuthority());
                                        if (invoke != null) {
                                            String PathStrategyStringClass = FileProvider.class.getName() + "$PathStrategy";
                                            Class<?> PathStrategy = Class.forName(PathStrategyStringClass);
                                            Method getFileForUri = PathStrategy.getDeclaredMethod("getFileForUri", Uri.class);
                                            getFileForUri.setAccessible(true);
                                            Object invoke1 = getFileForUri.invoke(invoke, uri);
                                            if (invoke1 instanceof File) {
                                                String filePath = ((File) invoke1).getAbsolutePath();
                                                return filePath;
                                            }
                                        }
                                    } catch (NoSuchMethodException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * ????????????????????????
     */
    public Bitmap getBitmap(String imgPath) {

        if (getAndroidSDKVersion() >= 8) {
            Bitmap bp = ThumbnailUtils.createVideoThumbnail(imgPath,
                    MediaStore.Video.Thumbnails.MINI_KIND);
            return bp;
        } else {
            return null;
        }
    }

    /**
     * ????????????????????????
     *
     * @author liningning
     * @create 2011-11-14
     */
    public int getAndroidSDKVersion() {
        int version;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            System.out.println(e.toString());
            return -1;
        }
        return version;
    }

    /**
     * ??????????????????
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("??????");
            builder.setMessage("?????????????????????????????????\\n\\n?????????\\\"??????\\\"-\\\"??????\\\"-??????????????????");

            // ??????, ????????????
            builder.setNegativeButton("??????",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                finish();
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    });

            builder.setPositiveButton("??????",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                startAppSettings();
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    });

            builder.setCancelable(false);

            builder.show();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * ?????????????????????
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        try {
            Intent intent = new Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
