package heyong.lzy.imagepicker.loader;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧 Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：ImageLoader抽象类，外部需要实现这个类去加载图片， 尽力减少对第三方库的依赖，所以这么干了
 * 修订历史：
 * ================================================
 */
public interface ImageLoader extends Serializable {

    interface OnImageResultListener{
        void onSuccess();
        void onFailed();
    }

    void displayImage(Activity activity, String path, ImageView imageView, int width, int height,final OnImageResultListener listener);
    void displayImage(Activity activity, String path, ImageView imageView, int width, int height);

    void displayImage(Context context,String path,ImageView imageView);

    void clearMemoryCache();

    void displayCornersImage(Context context,String path,ImageView imageView);

    void displayLocalCornersImage(Context context,int id,ImageView imageView);


}
