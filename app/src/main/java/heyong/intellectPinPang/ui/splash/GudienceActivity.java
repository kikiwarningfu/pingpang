package heyong.intellectPinPang.ui.splash;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import heyong.handong.framework.account.AccountHelper;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityGudienceBinding;
import heyong.intellectPinPang.ui.login.activity.LoginActivity;

public class GudienceActivity extends BaseActivity<ActivityGudienceBinding, BaseViewModel>implements ViewPager.OnClickListener {

    //实例化图片资源
    private int []imageIdArray = new int[]{R.drawable.img_splash_one,R.drawable.img_splash_two,
            R.drawable.img_splash_three};//图片资源的数组
    //底部小店图片
    private ImageView[] dots ;
    //    图片列表数据源
    private List<View> viewList = new ArrayList<>();
    //    小圆点id
    private int[] ids={R.id.iv_guide_dot1,R.id.iv_guide_dot2,R.id.iv_guide_dot3};
    //    立即体验按钮
    private TextView btn_guide_start;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_gudience;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

        //        加载页面
        loadPage();
//        初始化View
        initView();
        //加载ViewPager
        initViewPager();
        //初始化底部小点
        initDots();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
    }

    /**
     * 是否加载欢迎页
     */
    private void loadPage() {
//        AccountHelper.setSpIsFirstApp("false");
    }

    /**
     * 初始化View
     */
    private void initView() {
        btn_guide_start = findViewById(R.id.btn_guide_start);
        btn_guide_start.setOnClickListener(this);

    }




    /**
     * 加载图片ViewPager
     */
    private void initViewPager() {
        ViewPager vp_guide = findViewById(R.id.vp_guide);
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        //循环创建View并加入到集合中
        for (int anImageIdArray : imageIdArray) {
            //new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setImageResource(anImageIdArray);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //将ImageView加入到集合中
            viewList.add(imageView);
        }

        //View集合初始化好后，设置Adapter
        vp_guide.setAdapter(new GuidePageAdapter(viewList));
        //设置滑动监听
        vp_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 设置底部小点选中状态
                for(int i = 0;i<ids.length;i ++){
                    if(position==i){
                        dots[i].setImageResource(R.drawable.img_splash_select);
                    }else {
                        dots[i].setImageResource(R.drawable.img_splash_unselect);
                    }
                }
                //判断是否是最后一页，若是则显示按钮
                if (position == imageIdArray.length - 1){
                    btn_guide_start.setVisibility(View.VISIBLE);
                }else {
                    btn_guide_start.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    /**
     * 初始化小圆点的id
     */
    private void initDots() {  //对点的图片进行初始化（找控件）
        dots = new ImageView[viewList.size()];
        for (int i=0;i< viewList.size();i++){
            dots[i]=findViewById(ids[i]);
        }
    }


    /**
     * 马上体验的点击事件
     */
    @Override
    public void onClick(View view) {
//        if(sp.getBoolean("isFirst", isFirst)){
//            // 首次加载
//            isFirst = false;
//            Intent intent = new Intent();
//            sp.edit().putBoolean("isFirst", isFirst).apply();
//            intent.setClass(context,ChooseLoginTypeActivity.class);
//            startActivity(intent);
//        }
        AccountHelper.setSpIsFirstApp("false");
        goActivity(LoginActivity.class);
        finish();


    }

    public class GuidePageAdapter extends PagerAdapter {

        private List<View> viewList;

        public GuidePageAdapter(List<View> viewList) {
            this.viewList = viewList;
        }

        /**
         */
        @Override
        public int getCount() {
            if (viewList != null){
                return viewList.size();
            }
            return 0;
        }

        /**
         */
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        /**
         * 初始化position位置的界面
         */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(viewList.get(position));
        }




    }
}

