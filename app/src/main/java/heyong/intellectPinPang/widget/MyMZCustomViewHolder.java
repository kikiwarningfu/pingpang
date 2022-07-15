package heyong.intellectPinPang.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ms.banner.holder.BannerViewHolder;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import heyong.handong.framework.utils.ImageLoader;

public class MyMZCustomViewHolder implements MZViewHolder<String> {

    private ImageView mImageView;

    public MyMZCustomViewHolder() {

    }

    @Override
    public View createView(Context context) {
        // 返回mImageView页面布局
        mImageView = new ImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        mImageView.setLayoutParams(params);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return mImageView;
    }

    @Override
    public void onBind(Context context, int position, String data) {

            ImageLoader.loadImage(mImageView, data);


    }
}
