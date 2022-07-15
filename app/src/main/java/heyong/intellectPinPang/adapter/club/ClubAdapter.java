package heyong.intellectPinPang.adapter.club;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.XlClubInfoListBean;
import heyong.intellectPinPang.databinding.ItemClubDescribeBinding;
import heyong.intellectPinPang.databinding.ItemClubDescribeTwoBinding;

/**
 * 俱乐部的适配器
 */
public class ClubAdapter extends BaseQuickAdapter<XlClubInfoListBean.ListBean, BaseViewHolder> {
    private Context context;

    public ClubAdapter(Context context) {
        super(R.layout.item_club_describe);
        this.context = context;
    }

    public ClubAdapter() {
        super(R.layout.item_club_describe);
    }

    @Override
    protected void convert(BaseViewHolder helper, XlClubInfoListBean.ListBean item) {
        ItemClubDescribeBinding binding = DataBindingUtil.bind(helper.getConvertView());

        switch (Integer.parseInt(item.getTeamType())) {
            case 1:
                if (helper.getAdapterPosition() == 0 || helper.getAdapterPosition() == 1) {
                    binding.viewDivider.setVisibility(View.VISIBLE);
                } else {
                    binding.viewDivider.setVisibility(View.GONE);

                }
                binding.llTeam.setVisibility(View.VISIBLE);
                binding.llSingle.setVisibility(View.GONE);
                if (item.getUserImage() == null || item.getUserImage().size() == 0) {
                    ImageLoader.loadImage(binding.ivLogoOne, "", R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(binding.ivLogoTwo, "", R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(binding.ivLogoThree, "", R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    ImageLoader.loadImage(binding.ivLogoFour, "", R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                } else {
                    if (item.getUserImage().size() == 4) {
                        ImageLoader.loadImage(binding.ivLogoOne, item.getUserImage().get(0), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                        ImageLoader.loadImage(binding.ivLogoTwo, item.getUserImage().get(1), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                        ImageLoader.loadImage(binding.ivLogoThree, item.getUserImage().get(2), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                        ImageLoader.loadImage(binding.ivLogoFour, item.getUserImage().get(3), R.drawable.img_default_avatar, R.drawable.img_default_avatar);

                    } else if (item.getUserImage().size() == 3) {
                        ImageLoader.loadImage(binding.ivLogoOne, item.getUserImage().get(0), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                        ImageLoader.loadImage(binding.ivLogoTwo, item.getUserImage().get(1), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                        ImageLoader.loadImage(binding.ivLogoThree, item.getUserImage().get(2), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                        ImageLoader.loadImage(binding.ivLogoFour, "", R.drawable.img_default_avatar, R.drawable.img_default_avatar);

                    } else if (item.getUserImage().size() == 2) {
                        ImageLoader.loadImage(binding.ivLogoOne, item.getUserImage().get(0), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                        ImageLoader.loadImage(binding.ivLogoTwo, item.getUserImage().get(1), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                        ImageLoader.loadImage(binding.ivLogoThree, "", R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                        ImageLoader.loadImage(binding.ivLogoFour, "", R.drawable.img_default_avatar, R.drawable.img_default_avatar);

                    } else if (item.getUserImage().size() == 1) {
                        ImageLoader.loadImage(binding.ivLogoOne, item.getUserImage().get(0), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                        ImageLoader.loadImage(binding.ivLogoTwo, "", R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                        ImageLoader.loadImage(binding.ivLogoThree, "", R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                        ImageLoader.loadImage(binding.ivLogoFour, "", R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    }
                }
                binding.tvTeamTitleOne.setText("" + item.getTeamTitle());
                binding.tvNumberOne.setText("" + item.getMemberCount());
                binding.tvAddressOne.setText("" + item.getCity());
                break;
            case 2:
            case 3:
            case 4:
                if (helper.getAdapterPosition() == 0 || helper.getAdapterPosition() == 1) {
                    binding.viewDivider.setVisibility(View.VISIBLE);
                } else {
                    binding.viewDivider.setVisibility(View.GONE);

                }
                binding.llTeam.setVisibility(View.GONE);
                binding.llSingle.setVisibility(View.VISIBLE);
                binding.tvTeamTitle.setText("" + item.getTeamTitle());
                binding.tvNumberCount.setText("" + item.getMemberCount());
                binding.tvAddress.setText("" + item.getCity());
//                Log.e(TAG, "convert: TeamType===" + item.getTeamType() + "title===" + item.getTeamTitle()
//                        + "userImage===" + item.getUserImage() + "size====" + item.getUserImage().size() +
//                        "  clubImgUrl===" + item.getClubImgUrl());
                ImageLoader.loadImage(binding.ivLogo, ""+item.getClubImgUrl(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
//                RequestListener mRequestListener = new RequestListener() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
//                        Log.e(TAG, "onException: " + e.toString() + "  model:" + model + " isFirstResource: " + isFirstResource);
//                        binding.ivLogo.setImageResource(R.mipmap.ic_launcher);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
//                        Log.e(TAG, "model:" + model + " isFirstResource: " + isFirstResource);
//                        return false;
//                    }
//                };
//                Glide.with(context).load("http://images.xlttsports.com/android_img_20210224140739368")
//                        .listener(mRequestListener)
//                        .into(binding.ivLogo);


                break;

        }

    }
}
