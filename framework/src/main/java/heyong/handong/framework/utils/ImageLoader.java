package heyong.handong.framework.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.handongkeji.framework.R;

import heyong.handong.framework.widget.GlideRoundTransform;

/**
 * Created by Administrator on 2017/8/24 0024.
 */

public class ImageLoader {

    public static void loadImage(ImageView imageView, String imagePth) {
        loadImage(imageView, imagePth, 0);
    }

    public static void loadImage(ImageView imageView, String imagePath, @DrawableRes int placeHolder) {
        loadImage(imageView, imagePath, placeHolder, placeHolder);
    }

    public static void loadImages(ImageView imageView, int imagePath) {

        RequestOptions options = new RequestOptions();

        options.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(imageView.getContext())
                .asBitmap()
                .apply(options)
                .load(imagePath)
                .into(imageView);

    }

    public static void loadImage(ImageView imageView, String imagePath, @DrawableRes int placeHolder, @DrawableRes int errRes) {
        if (CommonUtils.isStringNull(imagePath)) {
            imageView.setImageResource(errRes);
        } else {
            RequestOptions options = new RequestOptions();
            options.centerCrop();
            if (placeHolder > 0) {
                options.placeholder(placeHolder);
            }
            if (errRes > 0) {
                options.error(errRes);
            }
            options.diskCacheStrategy(DiskCacheStrategy.ALL);

            imagePath = fixUrl(imagePath);
            Glide.with(imageView.getContext())
                    .asBitmap()
                    .apply(options)
                    .load(imagePath)
                    .into(imageView);
        }
    }

    public static String fixUrl(String url) {

        if (!TextUtils.isEmpty(url) && url.startsWith("http://") && url.contains("\\")) {
            url = url.substring(7);
            url = url.replaceAll("\\\\", "/");
            url = "http://".concat(url);
            return url;
        }
        return url;
    }

    public static void loadImage(ImageView imageView, int resid) {
        Glide.with(imageView.getContext()).load(resid).into(imageView);
    }


    /**
     * 圆角
     *
     * @param imageView
     * @param imagePath
     * @param placeholderid 占位图资源id
     */
    public static void GlideYuanjiao(final ImageView imageView, String imagePath, int placeholderid) {


        RoundedCorners roundedCorners = new RoundedCorners(6);
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).error(placeholderid);

        Glide.with(imageView.getContext()).load(imagePath).apply(options).into(imageView);
//        imagePath = fixUrl(imagePath);
//        Glide.with(imageView.getContext())
//                .asBitmap()
//                .apply(options)
//                .load(imagePath)
//                .into(new BitmapImageViewTarget(imageView) {
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        super.setResource(resource);
//                        RoundedBitmapDrawable circularBitmapDrawable =
//                                RoundedBitmapDrawableFactory.create(imageView.getContext().getResources(), resource);
//                        circularBitmapDrawable.setCornerRadius(20); //设置圆角弧度
//                        imageView.setImageDrawable(circularBitmapDrawable);
//                    }
//                });
    }

    /**
     * 加再圆形图
     *
     * @param imageView
     * @param imagePath
     * @param placeholderid 占位图资源id
     */
    public static void loadRoundImage(final ImageView imageView, String imagePath, int placeholderid) {
        //imageView.setImageResource(placeholderid);

        RequestOptions options = new RequestOptions()
                .placeholder(placeholderid)
                .error(placeholderid)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        imagePath = fixUrl(imagePath);
        Glide.with(imageView.getContext())
                .asBitmap()
                .apply(options)
                .load(imagePath)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(imageView.getResources(), resource);
                        roundedBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(roundedBitmapDrawable);
                    }
                });


    }

    /**
     * 圆角
     *
     * @param imageView
     * @param imagePath
     * @param placeholderid 占位图资源id
     */
    public static void loadRoundImage1(final ImageView imageView, String imagePath, int placeholderid) {

        RequestOptions options = new RequestOptions()
                .placeholder(placeholderid)
                .error(placeholderid)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        imagePath = fixUrl(imagePath);
        Glide.with(imageView.getContext())
                .asBitmap()
                .apply(options)
                .load(imagePath)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(imageView.getContext().getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(10); //设置圆角弧度
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    //  加载头像
    public static void loadHeadImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.morentouxiang)
                .error(R.drawable.morentouxiang)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        url = fixUrl(url);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);

    }

    public static void loadImage(Context context, ImageView imageView, String imagePath, int width, int height) {
        RequestOptions options = new RequestOptions()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .override(width, height);

        Glide.with(context)
                .load(imagePath)
                .apply(options)
                .into(imageView);
    }

    public static void displayCornersImage(Context context, String path, ImageView imageView, int corners) {
        RequestOptions options = new RequestOptions().transform(new GlideRoundTransform(context, corners))
                .error(R.drawable.ic_default_image)
                .placeholder(R.drawable.ic_default_image);
        Glide.with(context).load(path).apply(options).into(imageView);
    }


    public static void displayLocalCornersImage(Context context, int id, ImageView imageView, int corners) {
        RequestOptions options = new RequestOptions().transform(new GlideRoundTransform(context, corners))
                .error(R.drawable.ic_default_image)
                .placeholder(R.drawable.ic_default_image);
        Glide.with(context).load(id).apply(options).into(imageView);

    }

    public static void displayLocalCornersImage(Context context, String path, ImageView imageView, int corners) {
        RequestOptions options = new RequestOptions().transform(new GlideRoundTransform(context, corners))
                .error(R.drawable.ic_default_image)
                .placeholder(R.drawable.ic_default_image);
        Glide.with(context).load(path).apply(options).into(imageView);

    }
}
