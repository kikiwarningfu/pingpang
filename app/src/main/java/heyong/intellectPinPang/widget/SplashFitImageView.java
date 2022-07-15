package heyong.intellectPinPang.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import heyong.intellectPinPang.utils.ScreenUtils;


/**
 * describe ：
 * author ：hg
 * data ：2018/2/25
 * version ：
 */

public class SplashFitImageView extends AppCompatImageView {

    private int screenWidth;
    private int screenHeight;
    private int displayWidth;
    private int displayHeight;
    private Context context;

    public SplashFitImageView(Context context) {
        this(context, null);
    }

    public SplashFitImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplashFitImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        init();
    }

    private void init() {

        screenWidth = ScreenUtils.getDisplayWidth(getContext());

        if (ScreenUtils.checkDeviceHasNavigationBar(context)) {

            if (ScreenUtils.getHeightOfNavigationBar(context) > 0) {

                screenHeight = ScreenUtils.getDisplayHeight(getContext()) - ScreenUtils.getHeightOfNavigationBar(context) / 2;

            } else {

                screenHeight = ScreenUtils.getDisplayHeight(getContext());

            }

        } else {

            screenHeight = ScreenUtils.getDisplayHeight(getContext());

        }

    }

    public void setSize(int bitmapWidth, int bitmapHeight) {
        displayWidth = screenWidth;
        displayHeight = screenHeight;

        //计算出按比例拉伸后的宽度和高度
        displayWidth = screenHeight * bitmapWidth / bitmapHeight;
        displayHeight = screenWidth * bitmapHeight / bitmapWidth;
        //判断如果以图片高度拉伸到屏幕的高度,按照相应的拉伸比是否能够拉伸超过屏幕宽度或者等于屏幕宽度,否则以另一边为基准

        if (displayWidth >= screenWidth) {
            displayHeight = screenHeight;
        } else {
            displayWidth = screenWidth;
        }


        //拉伸后截取中间的部分
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
        if (lp != null) {
            lp.leftMargin = (screenWidth - displayWidth) / 2;
            lp.topMargin = (screenHeight - displayHeight) / 2 - 10;
            setLayoutParams(lp);
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(displayWidth, displayHeight + 10);

    }


}
