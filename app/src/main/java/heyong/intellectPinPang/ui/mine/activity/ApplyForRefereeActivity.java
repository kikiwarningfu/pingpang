package heyong.intellectPinPang.ui.mine.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nanchen.compresshelper.CompressHelper;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.RefreRoleNameListAdapter;
import heyong.intellectPinPang.bean.competition.CoashRoleListBean;
import heyong.intellectPinPang.databinding.ActivityApplyForRefreeBinding;
import heyong.intellectPinPang.ui.mine.MineViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.ImagePickerUtil;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.utils.MessageDialogTitleSpannerBuilder;
import heyong.intellectPinPang.utils.RxBusSubscriber;
import heyong.intellectPinPang.widget.PhotoSelDialog;
import heyong.intellectPinPang.widget.popupwindow.CommonPopupWindow;
import heyong.lzy.imagepicker.ImagePicker;
import heyong.lzy.imagepicker.bean.ImageItem;
import heyong.lzy.imagepicker.ui.ImageGridActivity;

/**
 * xx
 * 申请成为裁判员
 */
public class ApplyForRefereeActivity extends BaseActivity<ActivityApplyForRefreeBinding, MineViewModel> implements View.OnClickListener, PhotoSelDialog.OnSelectListener, ImagePickerUtil.OnImageCallback {
    private CommonPopupWindow mMovieTicketWindow;
    private String longId = "";
    private RefreRoleNameListAdapter refreRoleNameListAdapter;
    List<CoashRoleListBean> data;
    public static final int REQUEST_CODE_SELECT_PHOTO = 1; //图片-相册
    public static final int REQUEST_CODE_TAKE_PHOTO = 2;   //拍照-相机
    private ImagePickerUtil imagePickerUtil;
    private String strMatchImgUrl = "";
    public static final String UPDATE_ID = "update_id";
    private String updateId = "";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_apply_for_refree;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        imagePickerUtil = new ImagePickerUtil();
        updateId = getIntent().getStringExtra(UPDATE_ID);
        mViewModel.coashRoleListBeanLiveData.observe(this, new Observer<ResponseBean<List<CoashRoleListBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<CoashRoleListBean>> coashRoleListBeanResponseBean) {
                data = coashRoleListBeanResponseBean.getData();

                dismissLoading();
                if (mMovieTicketWindow != null && mMovieTicketWindow.isShowing()) {

                } else {
                    showPopMatchForm();
                }
            }
        });

        mViewModel.getUploadTokenLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
//                if (responseBean.isSuccess()) {
                    if(responseBean.getErrorCode().equals("200")){

                        UploadManager uploadManager = new UploadManager();
                    // 设置图片名字
                    uploadImage(picPath, responseBean.getData().toString(), uploadManager, key);
                } else {
                    toast("暂无权限");
                }
            }
        });

        mViewModel.updateRefereeInfoLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
//                if (responseBean.isSuccess()) {
                    if(responseBean.getErrorCode().equals("200")){

                        SpannableString spannableString = new SpannableString("等待系统审核，请注意关注 我的-消息 \n" +
                            "查看审核结果");
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff4795ed")), 12, spannableString.length() - 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    new MessageDialogTitleSpannerBuilder(ApplyForRefereeActivity.this)
                            .setMessage(spannableString)
                            .setTvCancel("取消")
                            .setBtnCancleHint(true)
                            .setTvSure("确定")
                            .setSureListener(v1 ->
                                    cloth()
                            )
                            .show();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });


    }

    private void cloth() {
        finish();
    }

    public void showPopMatchForm() {
        mMovieTicketWindow = new CommonPopupWindow.Builder(ApplyForRefereeActivity.this)
                .setView(R.layout.pop_referee_grade)
                .setBackGroundLevel(1f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        if (AntiShakeUtils.isInvalidClick(view))
                            return;
                        refreRoleNameListAdapter = new RefreRoleNameListAdapter();
                        RecyclerView rvRoleList = view.findViewById(R.id.rv_refree_list);
                        rvRoleList.setLayoutManager(new LinearLayoutManager(ApplyForRefereeActivity.this, RecyclerView.VERTICAL, false));
                        rvRoleList.setAdapter(refreRoleNameListAdapter);
                        refreRoleNameListAdapter.setNewData(data);
                        refreRoleNameListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                if (AntiShakeUtils.isInvalidClick(view))
                                    return;
                                boolean fastClick = isFastClick();
                                if (!fastClick) {
                                    CoashRoleListBean coashRoleListBean = data.get(position);
                                    longId = "" + coashRoleListBean.getId();
                                    mBinding.tvCaipanLevel.setText("" + coashRoleListBean.getRoleName());
                                    mBinding.tvCaipanLevel.setTextColor(Color.BLACK);
                                    mMovieTicketWindow.dismiss();
                                }
                            }
                        });

                    }
                })
                .setOutsideTouchable(true)//设置外层不可点击
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
        if (mMovieTicketWindow == null || !mMovieTicketWindow.isShowing()) {
            mMovieTicketWindow.setFocusable(true);// 这个很重要
            mMovieTicketWindow.showAsDropDown(mBinding.rlRefereeLevel);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_referee_level:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showLoading();
                mViewModel.getCoachRoleList("1");
                break;
            case R.id.iv_upload://上传图片
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                PhotoSelDialog dialog2 = new PhotoSelDialog(this);
                dialog2.setClickListener(this);
                dialog2.show();

                break;
            case R.id.tv_commit://提交
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (TextUtils.isEmpty(longId)) {
                    toast("请选择裁判员等级");
                    return;
                }
                String string = mBinding.tvCaipanLevel.getText().toString();
                if (!string.contains("业余")) {
                    if (TextUtils.isEmpty(strMatchImgUrl)) {
                        toast("请上传裁判员证书图片");
                        return;
                    }
                }

                new MessageDialogBuilder(ApplyForRefereeActivity.this)
                        .setMessage("")
                        .setTvTitle("是否提交裁判员申请？")
                        .setTvSure("确定")
                        .setBtnCancleHint(false)
                        .setSureListener(v1 ->
                                getLoadData()
                        )
                        .show();

                break;
        }
    }

    private void getLoadData() {
        showLoading();
        if (updateId != null && !TextUtils.isEmpty(updateId)) {
            mViewModel.updateRefereeInfo("" + updateId, "" + longId, "" + strMatchImgUrl);
        } else {
            mViewModel.updateRefereeInfo("", "" + longId, "" + strMatchImgUrl);

        }
    }
    private static final int IMAGE_PICKER = 10001;
    private static final int REQUEST_CODE_SELECT = 10002;
    @Override
    public void takePhoto() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new RxBusSubscriber<Boolean>() {
                    @Override
                    protected void onEvent(Boolean aBoolean) {
                        if (aBoolean) {
                            //申请的权限全部允许
                            //图片- 拍照
//                            ImagePicker imagePicker = ImagePicker.getInstance();
//                            imagePicker.setMultiMode(false);
//                            imagePicker.setShowCamera(false);
//                            imagePicker.setCrop(false);
//                            imagePicker.setFocusHeight(SystemHelper.getScreenInfo(ApplyForRefereeActivity.this).heightPixels);
//                            imagePicker.setFocusWidth(SystemHelper.getScreenInfo(ApplyForRefereeActivity.this).widthPixels);
//                            imagePickerUtil.takePhoto(ApplyForRefereeActivity.this, REQUEST_CODE_TAKE_PHOTO, ApplyForRefereeActivity.this);
                            Intent intent = new Intent(ApplyForRefereeActivity.this, ImageGridActivity.class);
                            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                            startActivityForResult(intent, REQUEST_CODE_SELECT);
                        } else {
                            //只要有一个权限被拒绝，就会执行
                            toast("未授权权限，部分功能不能使用");
                        }
                    }
                });
    }

    @Override
    public void selectPhoto() {
//        ImagePicker imagePicker = ImagePicker.getInstance();
//        imagePicker.setMultiMode(true);
//        imagePicker.setCrop(true);
//        imagePicker.setShowCamera(true);
//        if (needPicNum < 0) {
//            needPicNum = 1;
//        }
//        imagePicker.setSelectLimit(1);
//        imagePicker.setCrop(false);
//        imagePicker.setMultiMode(true);
//        imagePickerUtil.selectPic(ApplyForRefereeActivity.this, REQUEST_CODE_SELECT_PHOTO, ApplyForRefereeActivity.this);
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, IMAGE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        imagePickerUtil.handResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                //选择相册
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                String path = images.get(0).path;
                File file2 = CompressHelper.getDefault(this).compressToFile(new File(path));
                showLoading();
                uploadImg2QiNiu(file2.getPath());

            } else if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);//
                String path = images.get(0).path;
                File file2 = CompressHelper.getDefault(this).compressToFile(new File(path));
                showLoading();
                uploadImg2QiNiu(file2.getPath());


            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        } else {
//            Log.e(TAG, "onActivityResult: signIn签名");
//            if(data!=null) {
//                signPath = data.getStringExtra("path");
//                Log.e(TAG, "onActivityResult: ===" + signPath + "newPath===" + data.getStringExtra("path"));
//                mBinding.rlContent.setVisibility(View.GONE);
//                mBinding.ivHardSignature.setVisibility(View.VISIBLE);
//                mBinding.ivHardSignature.setBackground(null);
//                mBinding.ivHardSignature.setBackgroundResource(0);
//                ImageLoader.loadImage(mBinding.ivHardSignature, signPath);
//                updateXlUserInfoBean.setAutographImg(signPath);
//            }
        }
    }

    @Override
    public void callBack(List<ImageItem> list) {
        List<String> images = new ArrayList<>();

        showLoading();
        File file2 = CompressHelper.getDefault(this).compressToFile(new File(list.get(0).path));

//        File file2 = BitmapUtils.compressImageToSD(list.get(0).path, 30);
        uploadImg2QiNiu(file2.getPath());

        for (ImageItem item : list
        ) {
//            int degree = readPictureDegree(item.path);
//            Bitmap bitmap = rotateBitmap(BitmapFactory.decodeFile(item.path), degree);
//            saveBitmap(item.path, bitmap);
            images.add(item.path);
        }
    }

    public static final String TAG = ApplyForRefereeActivity.class.getSimpleName();
    String key = "";
    String picPath = "";

    private void uploadImage(String picPath, String token, UploadManager uploadManager, String key) {
        uploadManager.put(picPath, key, token, new UpCompletionHandler() {
//         uploadManager.put(picPath, key, Auth.create(Constants.AccessKey, Constants.SecretKey).uploadToken("xlttsports"), new UpCompletionHandler() {

                @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                // info.error中包含了错误信息，可打印调试
                // 上传成功后将key值上传到自己的服务器
                if (info.isOK()) {
                    Log.i(TAG, "token===" + token);
                    String headpicPath = "http://images.xlttsports.com/" + key;
                    Log.i(TAG, "complete: " + headpicPath);
                    ImageLoader.loadImage(mBinding.ivUpload, headpicPath);
                    dismissLoading();
                    strMatchImgUrl = "" + headpicPath;
                } else {
                    toast("上传失败");
                    dismissLoading();
                }
            }
        }, null);
    }

    private void uploadImg2QiNiu(String localpicPath) {
        // 设置图片名字
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
        key = "android_img_" + sdf.format(new Date()) + sb.toString();
        picPath = localpicPath;
        mViewModel.getUploadToken(key);

    }


}