package heyong.intellectPinPang.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivitySignInterfaceBinding;
import heyong.intellectPinPang.ui.mine.MineViewModel;
import heyong.intellectPinPang.widget.SignatureView;
import pl.droidsonroids.gif.GifImageView;


/**
 * 签名界面Activity
 */
public class SignInterfaceActivity extends BaseActivity<ActivitySignInterfaceBinding, MineViewModel> {
    private SignatureView signatureView;
    private ImageView ivImage;
    private RelativeLayout rlSure;
    private RelativeLayout rlCancel;
    String path = "";
    String key = "";
    private String finalPath = "";
    public static final String TAG = SignInterfaceActivity.class.getSimpleName();
    private Dialog progressDialog;
    public ImageView ivFinish;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_sign_interface;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        signatureView = findViewById(R.id.sign_view);
        ivImage = findViewById(R.id.iv_logo);
        rlSure = findViewById(R.id.rl_sure);
        rlCancel = findViewById(R.id.rl_cancel);
        ivFinish = findViewById(R.id.iv_finish);
        ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivImage.setBackground(null);
            }
        });
        rlSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(path)) {
                    showLoading();
                    mViewModel.getUploadToken(key);
                } else {
                    toast("请输入签名");
                }
            }
        });
        signatureView.setSignatureCallBack(new SignatureView.ISignatureCallBack() {
            @Override
            public void onSignCompeleted(View view, Bitmap bitmap) {
                Drawable drawable = new BitmapDrawable(bitmap);
                ivImage.setBackground(drawable);
                String dir = getExternalCacheDir() + "signature/";
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
                key = "android_img_" + sdf.format(new Date()) + sb.toString() + "_sign.png";
                path = dir + key;

                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
//                Log.e(TAG, "onSignCompeleted: oneLength==="+file.length());
//                File file2 = BitmapUtils.compressImageToSD(file.getPath(), 200,key);
//                bitmaps.add(bitmap);
//                path=file2.getPath();
//                Log.e(TAG, "onSignCompeleted: twoLength==="+file2.length() );
                try {
                    signatureView.save(path);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "complet onSignCompeleted:error === " + e.getMessage());
                }


            }
        });
        mViewModel.getUploadTokenLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if(responseBean.getErrorCode().equals("200"))
                {
                    UploadManager uploadManager = new UploadManager();
                    // 设置图片名字
                    uploadImage(path, responseBean.getData().toString(), uploadManager, key);
                }else
                {
                    toast("暂无权限");
                }

            }
        });
    }

    private void uploadImage(String picPath, String token, UploadManager uploadManager, String key) {
        Log.e(TAG, "uploadImage: picPath===" + picPath);
        Log.e(TAG, "uploadImage: token===" + token);
        Log.e(TAG, "uploadImage:key ===" + key);
        uploadManager.put(picPath, key, token, new UpCompletionHandler() {
//        uploadManager.put(picPath, key, Auth.create(Constants.AccessKey, Constants.SecretKey).uploadToken("xlttsports"), new UpCompletionHandler() {

            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                // info.error中包含了错误信息，可打印调试
                // 上传成功后将key值上传到自己的服务器
                if (info.isOK()) {
                    Log.e(TAG, "complete: 请求成功 token===" + token);
                    String headpicPath = "http://images.xlttsports.com/" + key;
                    Log.e(TAG, "complete: success== " + headpicPath);

                    finalPath = headpicPath;
                    dismissLoading();
                    Intent intent = new Intent();
                    intent.putExtra("path", finalPath);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    toast("上传失败");
                    Log.e(TAG, "complete: error===" + new Gson().toJson(info));
                }
//                Log.e(TAG, "complete: error===" + new Gson().toJson(info));

            }
        }, null);
    }

    private GifImageView gifImageView;

    public void showLoading() {
//        tipDialog = new QMUITipDialog.Builder(this)
//                .setTipWord(msg)
//                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
//                .create(true);
//        tipDialog.show();
        if (progressDialog == null) {
            progressDialog = new Dialog(this, R.style.progress_dialog);
            progressDialog.setContentView(com.handongkeji.framework.R.layout.dialog_loading);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            gifImageView = progressDialog.findViewById(com.handongkeji.framework.R.id.gv_error);
            gifImageView.setImageResource(com.handongkeji.framework.R.drawable.load_gif);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    //
    public void dismissLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}