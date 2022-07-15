package heyong.intellectPinPang.ui.mine.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bus.SystemHelper;
import heyong.intellectPinPang.databinding.ActivityRealNameImageBinding;
import heyong.intellectPinPang.ui.mine.MineViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.ImagePickerUtil;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.utils.RxBusSubscriber;
import heyong.lzy.imagepicker.ImagePicker;
import heyong.lzy.imagepicker.bean.ImageItem;
import heyong.lzy.imagepicker.view.CropImageView;

/**
 * 实名认证  上传图片
 */
public class RealNameImageActivity extends BaseActivity<ActivityRealNameImageBinding, MineViewModel> implements View.OnClickListener, ImagePickerUtil.OnImageCallback {
    private ImagePickerUtil imagePickerUtil;
    public static final int REQUEST_CODE_TAKE_PHOTO = 2;   //拍照-相机
    public static final String TAG = RealNameImageActivity.class.getSimpleName();
    String key = "";
    String picPath = "";
    private String qualificationImgUrl = "";
    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_name, R.id.et_identify};
        return ids;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.activity_real_name_image;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        imagePickerUtil = new ImagePickerUtil();

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
        mViewModel.verifyMaterialLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                /**
                 * 认证成功一个小弹窗   失败一个小弹窗
                 */
//                if(responseBean.isSuccess()) {
                    if(responseBean.getErrorCode().equals("200")){

                    new MessageDialogBuilder(RealNameImageActivity.this)
                            .setTvTitle("认证成功")
                            .setMessage("")
                            .setTvSure("确定")
                            .setBtnCancleHint(true)
                            .setSureListener(v1 ->
                                    goAnother()
                            )
                            .show();
                }else
                {
                    new MessageDialogBuilder(RealNameImageActivity.this)
                            .setTvTitle("认证失败")
                            .setMessage("请检查身份信息是否正确")
                            .setTvSure("修改信息")
                            .setBtnCancleHint(true)
                            .setSureListener(v1 ->
                                    finish()
                            )
                            .show();
                }
            }
        });
    }

    private void goAnother() {
        goActivity(RealNameSuccessActivity.class);
        dismissLoading();
        finish();
    }

    private void uploadImage(String picPath, String token, UploadManager uploadManager, String key) {
        Log.e(TAG, "uploadImage: picPath===" + picPath);
        Log.e(TAG, "uploadImage: token===" + token);
        Log.e(TAG, "uploadImage:key ===" + key);
        uploadManager.put(picPath, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                // info.error中包含了错误信息，可打印调试
                // 上传成功后将key值上传到自己的服务器
                if (info.isOK()) {
                    Log.i(TAG, "token===" + token);
                    String headpicPath = "http://images.xlttsports.com/" + key;
                    Log.i(TAG, "complete: " + headpicPath);
                    dismissLoading();
                    ImageLoader.loadImage(mBinding.ivLogo, headpicPath);
                    qualificationImgUrl = "" + headpicPath;
                    Log.e(TAG, "complete: " + qualificationImgUrl);
                } else {
                    toast("上传失败");
                    Log.e(TAG, "complete: error===" + new Gson().toJson(info));
                }
                Log.e(TAG, "complete: error===" + new Gson().toJson(info));

            }
        }, null);
    }

    public static boolean isShenFen(String phone) {
        Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
        //通过Pattern获得Matcher
        Matcher idNumMatcher = idNumPattern.matcher(phone);
        //判断用户输入是否为身份证号
        if (idNumMatcher.matches()) {
            System.out.println("您的出生年月日是：");
            //如果是，定义正则表达式提取出身份证中的出生日期
            Pattern birthDatePattern = Pattern.compile("\\d{6}(\\d{4})(\\d{2})(\\d{2}).*");//身份证上的前6位以及出生年月日
            //通过Pattern获得Matcher
            Matcher birthDateMather = birthDatePattern.matcher(phone);
            //通过Matcher获得用户的出生年月日
            if (birthDateMather.find()) {
                String year = birthDateMather.group(1);
                String month = birthDateMather.group(2);
                String date = birthDateMather.group(3);
                Log.e(TAG, "isShenFen() returned: " + year + " ---" + month + "---" + date);
            }
            return true;
        } else {
            return false;
        }
    }

    //判断性别
    public static int isSex(String idCard) {
        if (!TextUtils.isEmpty(idCard) && idCard.length() == 18) {
            if (Integer.parseInt(idCard.substring(16, 17)) % 2 == 0) {
                return 2;
            } else {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                //请求接口
                String strName = mBinding.etName.getText().toString();
                if (TextUtils.isEmpty(strName)) {
                    toast("请输入姓名");
                    return;
                }
                String strIdCard = mBinding.etIdentify.getText().toString().trim();
                if (TextUtils.isEmpty(strIdCard)) {
                    toast("请输入身份证号");
                    return;
                }
                if(TextUtils.isEmpty(qualificationImgUrl))
                {
                    toast("请上传头像");
                }
                showLoading();
                mViewModel.verifyMaterial(""+qualificationImgUrl,""+strName,""+strIdCard);

                break;
            case R.id.iv_logo:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showLoading();
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(new RxBusSubscriber<Boolean>() {
                            @Override
                            protected void onEvent(Boolean aBoolean) {
                                if (aBoolean) {
                                    //申请的权限全部允许
                                    //图片- 拍照
                                    ImagePicker imagePicker = ImagePicker.getInstance();
                                    imagePicker.setMultiMode(false);
                                    imagePicker.setShowCamera(false);
                                    imagePicker.setCrop(true);
                                    imagePicker.setSaveRectangle(false); //是否按矩形区域保存
                                    imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
                                    imagePicker.setFocusWidth(250);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
                                    imagePicker.setFocusHeight(250);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
                                    imagePicker.setOutPutX(250);//保存文件的宽度。单位像素
                                    imagePicker.setOutPutY(250);//保存文件的高度。单位像素
                                    imagePicker.setFocusHeight(SystemHelper.getScreenInfo(RealNameImageActivity.this).heightPixels);
                                    imagePicker.setFocusWidth(SystemHelper.getScreenInfo(RealNameImageActivity.this).widthPixels);
                                    imagePickerUtil.takePhoto(RealNameImageActivity.this, REQUEST_CODE_TAKE_PHOTO, RealNameImageActivity.this);
                                } else {
                                    //只要有一个权限被拒绝，就会执行
                                    toast("未授权权限，部分功能不能使用");
                                }
                            }
                        });

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePickerUtil.handResult(requestCode, resultCode, data);


    }


    @Override
    public void callBack(List<ImageItem> list) {
        Log.e(TAG, "callBack: " + list.get(0));

//        ImageLoader.loadImage(mBinding.ivLogo, list.get(0).path);
//        File file = BitmapUtils.compressImageToSD(list.get(0).path, 30);
        uploadImg2QiNiu(list.get(0).path);
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
        Log.e(TAG, "uploadImg2QiNiu: localPath===" + localpicPath);
        mViewModel.getUploadToken(key);

    }


}