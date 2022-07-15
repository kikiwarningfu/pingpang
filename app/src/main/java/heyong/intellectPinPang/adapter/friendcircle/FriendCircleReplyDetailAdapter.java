package heyong.intellectPinPang.adapter.friendcircle;

import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.Locale;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.friend.ReplyPageBean;
import heyong.intellectPinPang.databinding.ItemFriendCircleReplyDeitalBinding;
import heyong.intellectPinPang.utils.SpanStringUtils;

import static heyong.intellectPinPang.utils.EmotionUtils.EMOTION_CLASSIC_TYPE;

public class FriendCircleReplyDetailAdapter extends BaseQuickAdapter<ReplyPageBean.ListBean, BaseViewHolder> {

    public FriendCircleReplyDetailAdapter() {
        super(R.layout.item_friend_circle_reply_deital);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReplyPageBean.ListBean item) {
        ItemFriendCircleReplyDeitalBinding binding = DataBindingUtil.bind(helper.getConvertView());
//        binding.tvContents.setText("" + item.getContent());
        binding.tvContents.setText(SpanStringUtils.getEmotionContent(EMOTION_CLASSIC_TYPE,
                mContext, binding.tvContents, item.getContent()));
//        String dynamicLikeList = item.getDynamicLikeList();
        helper.addOnClickListener(R.id.iv_dianzan);
        binding.tvDianzanNum.setText("" + item.getLikeCount());
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm",
                Locale.getDefault());
        String time1 = sdf.format(item.getAddtime());
        helper.addOnClickListener(R.id.tv_reply);
        if (!TextUtils.isEmpty(item.getPicture())) {
            binding.ivPicture.setVisibility(View.VISIBLE);
            ImageLoader.loadImage(binding.ivPicture, item.getPicture(), R.drawable.ic_default_image, R.drawable.ic_default_image);
        } else {
            binding.ivPicture.setVisibility(View.GONE);
        }
        ReplyPageBean.ListBean.CurrentUserLikeBean currentUserLike = item.getCurrentUserLike();
        if (currentUserLike == null) {
            ImageLoader.loadImage(binding.ivDianzan, R.drawable.img_circle_not_agree);
        } else {
            ImageLoader.loadImage(binding.ivDianzan, R.drawable.img_circle_agree);
        }
        if (item.isHasParent()) {
            //true  显示那个布局
            binding.llContent.setVisibility(View.VISIBLE);
            ReplyPageBean.ListBean.ReplyParentBean replyParent = item.getReplyParent();
            if (replyParent != null) {
                String content = replyParent.getContent();
                binding.tvReplyContent.setText(SpanStringUtils.getEmotionContent(EMOTION_CLASSIC_TYPE,
                        mContext, binding.tvReplyContent, content));
            } else {
                binding.llContent.setVisibility(View.GONE);
            }
        } else {
            binding.llContent.setVisibility(View.GONE);
        }

        binding.tvTime.setText("" + time1);
        if (getData().size() == 1) {
            binding.viewDivider.setVisibility(View.GONE);
        } else {
            binding.viewDivider.setVisibility(View.VISIBLE);
        }


    }
}
