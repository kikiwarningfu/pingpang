package heyong.intellectPinPang.adapter.game;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MessageInfoListBean;
import heyong.intellectPinPang.databinding.ItemMessageBinding;

/**
 * 我的消息
 */
public class MessageAdapter extends BaseQuickAdapter<MessageInfoListBean.MessagesBean, BaseViewHolder> {
    public MessageAdapter() {
        super(R.layout.item_message);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageInfoListBean.MessagesBean item) {
        ItemMessageBinding binding = DataBindingUtil.bind(helper.getConvertView());

        RelativeLayout rlContainer = helper.getView(R.id.rl_container);
        LinearLayout llDel = helper.getView(R.id.ll_del);
        helper.addOnClickListener(R.id.rl_delete);
        helper.addOnClickListener(R.id.rl_zhiding);
        MessageInfoListBean.MessagesBean.TitleBean title = item.getTitle();
        if (title.getCounts() == 0) {
            binding.tvUnreadNum.setVisibility(View.GONE);
        } else {
            binding.tvUnreadNum.setVisibility(View.VISIBLE);
            binding.tvUnreadNum.setText("" + title.getCounts());
        }
        helper.addOnClickListener(R.id.rl_container);
        switch (title.getMsgType()) {
            case "1":
                switch (title.getLogoType()) {
                    case "1"://赛事消息
                        ImageLoader.loadImage(binding.ivLogo, R.drawable.img_xiaoxi_saishi);
                        binding.tvTitle.setText("" + title.getTitle());
                        binding.tvContent.setText("" + title.getMessage());
                        break;
                    case "2"://俱乐部
                        ImageLoader.loadImage(binding.ivLogo, R.drawable.img_xiaoxi_gonggao);
                        binding.tvTitle.setText("系统公告");
                        binding.tvContent.setText("" + title.getMessage());
                        break;
                    case "3"://系统公告
                        ImageLoader.loadImage(binding.ivLogo, R.drawable.img_xiaoxi_gonggao);
                        binding.tvTitle.setText("系统公告");
                        binding.tvContent.setText("" + title.getMessage());
                        break;
                }
                break;
            case "2":
                String coverLogo = item.getTitle().getCoverLogo();
                ImageLoader.loadImage(binding.ivLogo, coverLogo);
                binding.tvTitle.setText("" + title.getTitle());
                binding.tvContent.setText("" + title.getMessage());
                break;
        }
        binding.tvTime.setText("" + title.getTime());
        String isTop = title.getIsTop();
        if (Integer.parseInt(isTop) == 0) {
            //显示置顶
            binding.tvCancelZhiding.setText("置顶");
        } else {
            //取消置顶
            binding.tvCancelZhiding.setText("取消置顶");
        }
        rlContainer.post(new Runnable() {
            @Override
            public void run() {
                int height = rlContainer.getHeight();
                ViewGroup.LayoutParams layoutParams = llDel.getLayoutParams();
                layoutParams.height = height;
                llDel.setLayoutParams(layoutParams);
            }
        });

    }
}
