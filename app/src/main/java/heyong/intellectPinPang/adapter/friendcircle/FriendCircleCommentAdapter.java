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
import heyong.intellectPinPang.bean.friend.ReplyCommentPageBean;
import heyong.intellectPinPang.databinding.ItemFriendCircleCommentBinding;
import heyong.intellectPinPang.utils.SpanStringUtils;

import static heyong.intellectPinPang.utils.EmotionUtils.EMOTION_CLASSIC_TYPE;

public class FriendCircleCommentAdapter extends BaseQuickAdapter<ReplyCommentPageBean.ListBean, BaseViewHolder> {

    public FriendCircleCommentAdapter() {
        super(R.layout.item_friend_circle_comment);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReplyCommentPageBean.ListBean item) {
        ItemFriendCircleCommentBinding binding = DataBindingUtil.bind(helper.getConvertView());

        binding.tvContent.setText(SpanStringUtils.getEmotionContent(EMOTION_CLASSIC_TYPE,
                mContext, binding.tvContent, item.getContent()));


        helper.addOnClickListener(R.id.iv_dianzan);
        helper.addOnClickListener(R.id.tv_content);
        helper.addOnClickListener(R.id.ll_content);
        int replyCount = item.getReplyCount();
        if (!TextUtils.isEmpty(item.getPicture())) {
            binding.ivPicture.setVisibility(View.VISIBLE);
            ImageLoader.loadImage(binding.ivPicture, item.getPicture(), R.drawable.ic_default_image, R.drawable.ic_default_image);
        } else {
            binding.ivPicture.setVisibility(View.GONE);
        }
        if (replyCount == 0) {
            binding.tvReplyNum.setText("回复＞");
//            binding.llContent.setVisibility(View.GONE);
        } else {
//            binding.llContent.setVisibility(View.VISIBLE);
            binding.tvReplyNum.setText("共" + replyCount + "条回复＞");
        }
        if (getData().size() == 1) {
            binding.viewDivider.setVisibility(View.GONE);
        } else {
            binding.viewDivider.setVisibility(View.VISIBLE);
        }
        long createTime = item.getAddtime();

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm",
                Locale.getDefault());

        String time1 = sdf.format(createTime);
        binding.tvTime.setText("" + time1);
        binding.tvLikeNum.setText("" + item.getLikeCount());
        ReplyCommentPageBean.ListBean.CurrentUserLikeBean currentUserLike = item.getCurrentUserLike();
        if (currentUserLike == null) {
            ImageLoader.loadImage(binding.ivDianzan, R.drawable.img_circle_not_agree);
        } else {
            ImageLoader.loadImage(binding.ivDianzan, R.drawable.img_circle_agree);
        }
        binding.tvLikeNum.setText("" + item.getLikeCount());
    }
}
