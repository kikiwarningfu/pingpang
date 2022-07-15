package heyong.intellectPinPang.adapter.game;


import android.content.Context;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ItemHomePageItemBinding;

public class HomePageAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
    private Context context;

    public HomePageAdapter(Context context) {
        super(R.layout.item_home_page_item);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        ItemHomePageItemBinding binding = DataBindingUtil.bind(helper.getConvertView());
        ImageLoader.displayLocalCornersImage(context,R.drawable.icon_test_one,binding.ivLogo,10);
//        Glide.with(context).load(item.getMovie_image()).apply(options).into(binding.ivLogo);
    }
}
