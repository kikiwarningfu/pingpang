package heyong.intellectPinPang.adapter.game;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.XlUserInfoListBean;
import heyong.intellectPinPang.databinding.ItemSportsPeopleBinding;

/**
 * 12：51   11点走1111
 * 搜索模块  运动员适配器
 */
public class SearchSportsPeopleAdapter extends BaseQuickAdapter<XlUserInfoListBean.ListBean, BaseViewHolder> {

    public SearchSportsPeopleAdapter() {
        super(R.layout.item_sports_people);
    }

    @Override
    protected void convert(BaseViewHolder helper, XlUserInfoListBean.ListBean item) {
        ItemSportsPeopleBinding binding = DataBindingUtil.bind(helper.getConvertView());
        ImageLoader.loadImage(binding.ivDefaultAvatar, item.getHeadImg(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
        if (item.getName().length() >= 4) {
            String newName = item.getName().substring(0, 3);
            binding.tvName.setText("" + newName + "...");
        } else {
            binding.tvName.setText("" + item.getName());
        }
        binding.tvAllCount.setText(""+item.getTotalCount());
        binding.tvWinCount.setText(""+item.getWinCount());
        binding.tvWinRate.setText(""+item.getWinningProbability());
        String sex = item.getSex();
        if(sex.equals("1"))
        {
            ImageLoader.loadImage(binding.ivSex, R.drawable.img_sex_little_blue);
        } else if (sex.equals("2")) {
            ImageLoader.loadImage(binding.ivSex, R.drawable.img_blue_woman);
        }
    }
}
