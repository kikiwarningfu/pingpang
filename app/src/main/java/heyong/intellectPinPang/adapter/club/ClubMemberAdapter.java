package heyong.intellectPinPang.adapter.club;

import android.content.Context;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.XlClubInfoBean;
import heyong.intellectPinPang.databinding.ItemClubMemberBinding;

/**
 * 俱乐部会员
 */
public class ClubMemberAdapter extends BaseQuickAdapter<XlClubInfoBean.MemberListBean, BaseViewHolder> {
    private Context context;
    private int wdith;

    public ClubMemberAdapter(Context context, int width) {
        super(R.layout.item_club_member);
        this.context = context;
        this.wdith = width;
    }

    @Override
    protected void convert(BaseViewHolder helper, XlClubInfoBean.MemberListBean item) {
        ItemClubMemberBinding binding = DataBindingUtil.bind(helper.getConvertView());
        ImageLoader.loadImage(binding.ivLogo, item.getHeadImg(),R.drawable.img_default_avatar,R.drawable.img_default_avatar);
        binding.tvName.setText("" + item.getName());
//        binding.tvIdentify.setText("" + item.getMemberType());
        int i = Integer.parseInt(item.getMemberType());
        if (i == 1) {
            binding.tvIdentify.setText("负责人");
        } else if (i == 2) {
            binding.tvIdentify.setText("教练员");
        } else if (i == 3) {
            binding.tvIdentify.setText("普通会员");
        }

    }
}
