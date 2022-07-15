package heyong.intellectPinPang.utils;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.bumptech.glide.util.Preconditions.checkNotNull;

/**
 * @author wesley
 * @date: 2017/3/13
 * @Description: Fragment添加到Activity上的工具类
 */
public class ActivityUtils {

    private static final String TAG = "ActivityUtils";

    private ActivityUtils() {

    }

    /**
     * 把Fragment添加到Activity上
     *
     * @param manager         FragmentManager的实例
     * @param fragment        Fragment的实例
     * @param containerViewId 布局id
     */
    public static void addFragmentToActivity(FragmentManager manager, Fragment fragment,
                                             int containerViewId) {

        if (manager == null || fragment == null || fragment.isAdded()) {

            return;
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(containerViewId, fragment);
        transaction.commit();
    }

    /**
     * 把Fragment添加到Activity上
     *
     * @param manager         FragmentManager的实例
     * @param fragment        Fragment的实例
     * @param containerViewId 布局id
     * @param tag             索引
     */
    public static void addFragmentToActivityWithTag(FragmentManager manager, Fragment fragment,
                                                    int containerViewId, String tag) {
        if (manager == null || fragment == null || tag == null || fragment.isAdded()) {

            return;
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(containerViewId, fragment, tag);
        transaction.commit();
    }





    /**
     * 测量View的宽高
     *
     * @param view View
     */
    public static void measureWidthAndHeight(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
    }

}
