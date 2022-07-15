package heyong.intellectPinPang.adapter.friendcircle;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.elvishew.xlog.XLog;

import cn.jpush.android.helper.Logger;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.friend.DynamicListBean;
import heyong.intellectPinPang.databinding.ItemFirendCircleImageBinding;
import heyong.intellectPinPang.databinding.ItemFirendCircleTextBinding;
import heyong.intellectPinPang.databinding.ItemFirendCircleTextImageBinding;
import heyong.intellectPinPang.databinding.ItemFirendCircleTextVideoBinding;
import heyong.intellectPinPang.databinding.ItemFirendCircleVideoBinding;
import heyong.lzy.imagepicker.ui.ImageBroseNewActivity;

/**
 * 朋友圈的适配器
 */
public class FriendCircleAdapter extends BaseQuickAdapter<DynamicListBean.ListBean, BaseViewHolder> {
    //    private ImageWatcher imageWatcher;
    private RequestOptions mRequestOptions;
    private DrawableTransitionOptions mDrawableTransitionOptions;
    private Context context;

    public FriendCircleAdapter(Context context) {
        super(0);
        this.context = context;

//        this.imageWatcher = imageWatcher;
        this.mRequestOptions = new RequestOptions().centerCrop();
        this.mDrawableTransitionOptions = DrawableTransitionOptions.withCrossFade();

        MultiTypeDelegate<DynamicListBean.ListBean> multiTypeDelegate = new MultiTypeDelegate<DynamicListBean.ListBean>() {
            @Override
            protected int getItemType(DynamicListBean.ListBean o) {
                //                Log.e(TAG, "getItemType: type==="+o.getType());
                XLog.e("FriendCircleAdapter", "" + o.getType());
                return o.getType();
            }
        };

        multiTypeDelegate.registerItemType(0, R.layout.item_firend_circle_text);//纯文本
        multiTypeDelegate.registerItemType(1, R.layout.item_firend_circle_text_image);//文本+图片
        multiTypeDelegate.registerItemType(2, R.layout.item_firend_circle_image);//纯图片
        multiTypeDelegate.registerItemType(3, R.layout.item_firend_circle_video);//纯视频
        multiTypeDelegate.registerItemType(4, R.layout.item_firend_circle_text_video);//文本+视频
        setMultiTypeDelegate(multiTypeDelegate);
    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicListBean.ListBean item) {
        int likeCount = item.getLikeCount();
        int commentCount = item.getCommentCount();
        DynamicListBean.ListBean.CurrentUserLikeBean currentUserLike = item.getCurrentUserLike();
//        int likeFlag = item.getCurrentUserLike();
        String province = item.getProvince();
        String city = item.getCity();
        switch (helper.getItemViewType()) {
            case 0://纯文本
                //                ItemMessageNoButtonBinding bindingNoButton = DataBindingUtil.bind(helper.getConvertView());
                //                bindingNoButton.tvContent.setText("" + item.getMessage());
                //                bindingNoButton.tvTime.setText("" + item.getUpdateTime());
                //                bindingNoButton.tvTitle.setText("" + item.getTitle());
                ItemFirendCircleTextBinding textBinding = DataBindingUtil.bind(helper.getConvertView());
                textBinding.tvContent.setText("" + item.getContent());
                helper.addOnClickListener(R.id.iv_user_logo);
                helper.addOnClickListener(R.id.iv_hands_up);

                textBinding.tvLikeNum.setText("" + likeCount);
                textBinding.tvCommentNum.setText("" + commentCount);
                if (currentUserLike == null) {
                    ImageLoader.loadImage(textBinding.ivHandsUp, R.drawable.img_circle_not_agree);
                } else {
                    ImageLoader.loadImage(textBinding.ivHandsUp, R.drawable.img_circle_agree);
                }
                textBinding.tvCommentNum.setText(""+item.getCommentCount());
                helper.addOnClickListener(R.id.ll_share);
                textBinding.tvShareNum.setText("" + item.getShareCount());
                if (!province.equals("null") && !TextUtils.isEmpty(province)) {
                    if (province.equals(city)) {
                        if (!TextUtils.isEmpty(item.getDetailAddr())) {
                            textBinding.tvAddress.setText("" + city + " " + item.getArea() + " " + item.getDetailAddr());
                        } else {
                            textBinding.tvAddress.setText("" + item.getAddress());
                        }
                    } else {

                        if (!TextUtils.isEmpty(item.getDetailAddr())) {
                            textBinding.tvAddress.setText("" + province + " " + city + " " + item.getArea() + " " + item.getDetailAddr());
                        } else {
                            textBinding.tvAddress.setText("" + item.getAddress());
                        }
                    }

                } else {
                    textBinding.tvAddress.setText("未知");
                }

                break;
            case 1:////文本+图片
                ItemFirendCircleTextImageBinding itemFirendCircleTextImageBinding = DataBindingUtil.bind(helper.getConvertView());
                helper.addOnClickListener(R.id.iv_user_logo);
                itemFirendCircleTextImageBinding.tvLikeNum.setText("" + likeCount);
                itemFirendCircleTextImageBinding.tvCommentNum.setText("" + commentCount);
                itemFirendCircleTextImageBinding.tvContent.setText("" + item.getContent());
                helper.addOnClickListener(R.id.iv_hands_up);
                helper.addOnClickListener(R.id.ll_share);
                itemFirendCircleTextImageBinding.tvShareNum.setText("" + item.getShareCount());
                itemFirendCircleTextImageBinding.layoutNine.setSingleImageSize(80, 120);
                if (currentUserLike == null) {
                    ImageLoader.loadImage(itemFirendCircleTextImageBinding.ivHandsUp, R.drawable.img_circle_not_agree);

                } else {
                    ImageLoader.loadImage(itemFirendCircleTextImageBinding.ivHandsUp, R.drawable.img_circle_agree);

                }
                itemFirendCircleTextImageBinding.tvCommentNum.setText(""+item.getCommentCount());

                if (item.getMypictureList() != null && item.getMypictureList().size() > 0) {
                    itemFirendCircleTextImageBinding.layoutNine.setAdapter(new NineImageAdapter(mContext,
                            mRequestOptions, mDrawableTransitionOptions, item.getMypictureList()));
                    itemFirendCircleTextImageBinding.layoutNine.setOnImageClickListener((position, view) -> {
//                        imageWatcher.show((ImageView) view, imageBinding.layoutNine.getImageViews(), item.getImageUriList());

                        Intent intent = new Intent(context, ImageBroseNewActivity.class);
                        intent.putExtra("path", item.getMypictureList().get(position));
                        intent.putExtra("current_position", position);
                        intent.putExtra("is_delete", "0");
                        context.startActivity(intent);
                    });
                }


                if (!province.equals("null") && !TextUtils.isEmpty(province)) {
                    if (province.equals(city)) {
                        if (!TextUtils.isEmpty(item.getDetailAddr())) {
                            itemFirendCircleTextImageBinding.tvAddress.setText("" + city + " " + item.getArea() + " " + item.getDetailAddr());
                        } else {
                            itemFirendCircleTextImageBinding.tvAddress.setText("" + item.getAddress());
                        }
                    } else {

                        if (!TextUtils.isEmpty(item.getDetailAddr())) {
                            itemFirendCircleTextImageBinding.tvAddress.setText("" + province + " " + city + " " + item.getArea() + " " + item.getDetailAddr());
                        } else {
                            itemFirendCircleTextImageBinding.tvAddress.setText("" + item.getAddress());
                        }
                    }

                } else {
                    itemFirendCircleTextImageBinding.tvAddress.setText("未知");
                }

                break;

            case 2://纯图片  item_firend_circle_image
                //多张图片显示
                ItemFirendCircleImageBinding imageBinding = DataBindingUtil.bind(helper.getConvertView());
                helper.addOnClickListener(R.id.iv_user_logo);
                imageBinding.tvLikeNum.setText("" + likeCount);
                imageBinding.tvCommentNum.setText("" + commentCount);
                imageBinding.layoutNine.setSingleImageSize(80, 120);
                imageBinding.tvCommentNum.setText(""+item.getCommentCount());

                if (currentUserLike == null) {
                    ImageLoader.loadImage(imageBinding.ivHandsUp, R.drawable.img_circle_not_agree);
                } else {
                    ImageLoader.loadImage(imageBinding.ivHandsUp, R.drawable.img_circle_agree);
                }
                if (item.getMypictureList() != null && item.getMypictureList().size() > 0) {
                    imageBinding.layoutNine.setAdapter(new NineImageAdapter(mContext, mRequestOptions, mDrawableTransitionOptions, item.getMypictureList()));
                    imageBinding.layoutNine.setOnImageClickListener((position, view) -> {

                        Intent intent = new Intent(context, ImageBroseNewActivity.class);
                        intent.putExtra("path", item.getMypictureList().get(position));
                        intent.putExtra("current_position", position);
                        intent.putExtra("is_delete", "0");
                        context.startActivity(intent);
                    });
                }
                helper.addOnClickListener(R.id.ll_share);
                imageBinding.tvShareNum.setText("" + item.getShareCount());
                helper.addOnClickListener(R.id.iv_hands_up);
                if (!province.equals("null") && !TextUtils.isEmpty(province)) {
                    if (province.equals(city)) {
                        if (!TextUtils.isEmpty(item.getDetailAddr())) {
                            imageBinding.tvAddress.setText("" + city + " " + item.getArea() + " " + item.getDetailAddr());
                        } else {
                            imageBinding.tvAddress.setText("" + item.getAddress());
                        }
                    } else {

                        if (!TextUtils.isEmpty(item.getDetailAddr())) {
                            imageBinding.tvAddress.setText("" + province + " " + city + " " + item.getArea() + " " + item.getDetailAddr());
                        } else {
                            imageBinding.tvAddress.setText("" + item.getAddress());
                        }
                    }

                } else {
                    imageBinding.tvAddress.setText("未知");
                }
                break;
            case 3://纯视频
                //                if (item.getUser_name() != null && !item.getUser_name().equals("")) {
                //                    helper.setText(R.id.tv_name, item.getUser_name());
                //                }
                //
                //                if (item.getCreateon() != null && !item.getCreateon().equals("")) {
                //                    helper.setText(R.id.tv_time, item.getCreateon());
                //                }
                ItemFirendCircleVideoBinding itemFirendCircleVideoBinding = DataBindingUtil.bind(helper.getConvertView());

                helper.addOnClickListener(R.id.video_view);
                helper.addOnClickListener(R.id.iv_user_logo);
                helper.addOnClickListener(R.id.rl_content);
                helper.addOnClickListener(R.id.ll_share);
                itemFirendCircleVideoBinding.tvShareNum.setText("" + item.getShareCount());
                helper.addOnClickListener(R.id.rl_play);
                helper.addOnClickListener(R.id.iv_play);
                ImageView ivVideo = helper.getView(R.id.video_view);
                TextView tvLikeNum = helper.getView(R.id.tv_like_num);
                TextView tvCommentNum = helper.getView(R.id.tv_comment_num);
                tvLikeNum.setText("" + likeCount);
                tvCommentNum.setText("" + commentCount);
                itemFirendCircleVideoBinding.tvCommentNum.setText(""+item.getCommentCount());

                helper.addOnClickListener(R.id.iv_hands_up);

                if (currentUserLike == null) {
                    ImageLoader.loadImage(itemFirendCircleVideoBinding.ivHandsUp, R.drawable.img_circle_not_agree);
                } else {
                    ImageLoader.loadImage(itemFirendCircleVideoBinding.ivHandsUp, R.drawable.img_circle_agree);
                }
                Glide.with(context)
                        .setDefaultRequestOptions(
                                new RequestOptions()
                                        .frame(0)
                                        .centerCrop()
                        )
                        .load(item.getMyvideoList().get(0))
                        .into(ivVideo);
                //视频封面图
//                if (item.getFiles() != null && item.getFiles().size() > 0) {
//                    GlideUtils.loadImg(mContext, item.getFiles().get(0), ivVideo);
//                }
                if (!province.equals("null") && !TextUtils.isEmpty(province)) {
                    if (province.equals(city)) {
                        if (!TextUtils.isEmpty(item.getDetailAddr())) {
                            itemFirendCircleVideoBinding.tvAddress.setText("" + city + " " + item.getArea() + " " + item.getDetailAddr());
                        } else {
                            itemFirendCircleVideoBinding.tvAddress.setText("" + item.getAddress());
                        }
                    } else {

                        if (!TextUtils.isEmpty(item.getDetailAddr())) {
                            itemFirendCircleVideoBinding.tvAddress.setText("" + province + " " + city + " " + item.getArea() + " " + item.getDetailAddr());
                        } else {
                            itemFirendCircleVideoBinding.tvAddress.setText("" + item.getAddress());
                        }
                    }

                } else {
                    itemFirendCircleVideoBinding.tvAddress.setText("未知");
                }
                break;

            case 4://文本+视频
                ItemFirendCircleTextVideoBinding binding = DataBindingUtil.bind(helper.getConvertView());

                helper.addOnClickListener(R.id.video_view);
                helper.addOnClickListener(R.id.rl_play);
                helper.addOnClickListener(R.id.iv_play);
                helper.addOnClickListener(R.id.iv_user_logo);
                helper.addOnClickListener(R.id.rl_content);
                TextView tvLikeNum2 = helper.getView(R.id.tv_like_num);
                TextView tvCommentNum2 = helper.getView(R.id.tv_comment_num);
                tvLikeNum2.setText("" + likeCount);
                tvCommentNum2.setText("" + commentCount);
                ImageView ivVideo1 = helper.getView(R.id.video_view);
                helper.addOnClickListener(R.id.ll_share);
                binding.tvShareNum.setText("" + item.getShareCount());
                helper.addOnClickListener(R.id.iv_hands_up);
                if (currentUserLike == null) {
                    ImageLoader.loadImage(binding.ivHandsUp, R.drawable.img_circle_not_agree);
                } else {
                    ImageLoader.loadImage(binding.ivHandsUp, R.drawable.img_circle_agree);
                }
                binding.tvCommentNum.setText(""+item.getCommentCount());

                //视频封面图
//                if (item.getFiles() != null && item.getFiles().size() > 0) {
//                    GlideUtils.loadImg(mContext, item.getFiles().get(0), ivVideo1);
//                }
                if (item.getMyvideoList().size() > 0) {
                    Glide.with(context)
                            .setDefaultRequestOptions(
                                    new RequestOptions()
                                            .frame(0)
                                            .centerCrop()
                            )
                            .load(item.getMyvideoList().get(0))
                            .into(ivVideo1);
                }
                if (!province.equals("null") && !TextUtils.isEmpty(province)) {
                    if (province.equals(city)) {
                        if (!TextUtils.isEmpty(item.getDetailAddr())) {
                            binding.tvAddress.setText("" + city + " " + item.getArea() + " " + item.getDetailAddr());
                        } else {
                            binding.tvAddress.setText("" + item.getAddress());
                        }
                    } else {

                        if (!TextUtils.isEmpty(item.getDetailAddr())) {
                            binding.tvAddress.setText("" + province + " " + city + " " + item.getArea() + " " + item.getDetailAddr());
                        } else {
                            binding.tvAddress.setText("" + item.getAddress());
                        }
                    }

                } else {
                    binding.tvAddress.setText("未知");
                }
                break;


        }
    }

    /**
     * 定义一个接口
     */
    public interface onOwnMyListener {
        void onMySelfDetail(DynamicListBean.ListBean titleBean);
    }

    /**
     * 定义一个变量储存数据
     */
    private onOwnMyListener myListener;

    /**
     * 提供公共的方法,并且初始化接口类型的数据
     */
    public void setMyListener(onOwnMyListener listener) {
        this.myListener = listener;
    }
}
