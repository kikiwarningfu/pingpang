package heyong.lzy.imagepicker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.github.chrisbanes.photoview.PhotoView;
import com.lzy.imagepicker.R;

import java.util.ArrayList;
import java.util.List;

import heyong.lzy.imagepicker.ImagePicker;
import heyong.lzy.imagepicker.loader.ImageLoader;
import heyong.lzy.imagepicker.util.Utils;
import heyong.lzy.imagepicker.view.ViewPagerFixed;


/**
 * 图片浏览
 *
 * @ClassName:ImageBroseActivity
 * @PackageName:com.bluemobi.wenwanstyle.activities.common
 * @Create On 2017/9/20 0020   09:25
 * @Site:http://www.handongkeji.com
 * @author:xuchuanting
 * @Copyrights 2017/9/20 0020 handongkeji All rights reserved.
 */
public class ImageBroseNewActivity extends AppCompatActivity {

    public static final String TAG = ImageBroseNewActivity.class.getSimpleName();
    public static final String CURRENT_POSITION = "current_position";
    public static final String PATH = "path";
    private int position;
    private int screenWidth;
    private int screenHeight;
    private ImageView ivDelete;
    private String localPath = "";
    private String isDelete = "";
    private static final long CLICK_INTERVAL_TIME = 300;
    private static long lastClickTime = 0;
    private ImageView ivFinish;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_brose_new);
//        StatusBarUtil.setColor(this,0xff666666);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();

        localPath = intent.getStringExtra(PATH);
        position = intent.getIntExtra(CURRENT_POSITION, 0);
        isDelete = getIntent().getStringExtra("is_delete");
        ivDelete = findViewById(R.id.iv_delete);
        ivFinish = findViewById(R.id.iv_finish);
        ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: 删除路径" + localPath + " position===" + position);
                Intent intent = new Intent();
                intent.putExtra("position", position);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        if (isDelete.equals("1")) {
            ivDelete.setVisibility(View.VISIBLE);
        } else {
            ivDelete.setVisibility(View.GONE);

        }


        DisplayMetrics dm = Utils.getScreenPix(this);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        progressBar=  findViewById(R.id.progress_bar);

        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取系统当前毫秒数，从开机到现在的毫秒数(手机睡眠时间不包括在内)
                long currentTimeMillis = SystemClock.uptimeMillis();
                //两次点击间隔时间小于300ms代表双击
                if (currentTimeMillis - lastClickTime < CLICK_INTERVAL_TIME) {
                    Log.d("btn listener:", "btn is doubleClicked!");
                    finish();
                    return;
                }
                lastClickTime = currentTimeMillis;
                Log.d("btn listener:", "btn is clicked!");

            }
        });
        progressBar.setVisibility(View.VISIBLE);
        ImagePicker.getInstance()
                .getImageLoader()
//                .displayImage(ImageBroseNewActivity.this, localPath, photoView);
                .displayImage(ImageBroseNewActivity.this, localPath, photoView, screenWidth,
                        screenHeight, new ImageLoader.OnImageResultListener() {
                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailed() {
                                progressBar.setVisibility(View.GONE);
                            }
                        });


//        String poiInfo = (position + 1) + "/" + pics.size();

//        viewPager.setAdapter(new ImageAdapter(pics));
//        viewPager.setCurrentItem(position);
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                String poiInfo = (position + 1) + "/" + pics.size();
////                tvTitle.setText(poiInfo);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

    }

    public static void start(Activity context, String path, int position) {


    }


    @Override
    public void onBackPressed() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//        ActivityCompat.finishAfterTransition(this);
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
