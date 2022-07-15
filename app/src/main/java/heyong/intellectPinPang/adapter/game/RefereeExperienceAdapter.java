package heyong.intellectPinPang.adapter.game;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.ThroughListBean;
import heyong.intellectPinPang.databinding.ItemRefereeExperienceBinding;

/**
 * 制裁经历
 */
public class RefereeExperienceAdapter extends BaseQuickAdapter<ThroughListBean, BaseViewHolder> {
    public RefereeExperienceAdapter() {
        super(R.layout.item_referee_experience);
    }

    @Override
    protected void convert(BaseViewHolder helper, ThroughListBean item) {
        ItemRefereeExperienceBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvTeamTitle.setText("" + item.getMathTitle());
        binding.tvTime.setText("" + item.getBeginTime());
        ImageLoader.GlideYuanjiao(binding.ivLogo, item.getMatchImgUrl(),R.drawable.icon_test_one);

    }
}
