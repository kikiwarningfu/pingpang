package heyong.intellectPinPang.adapter.live;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.live.LiveListBean;

public class ToBuyChildAdapter extends BaseQuickAdapter<LiveListBean.ListBean, BaseViewHolder> {
    Context context;

    public ToBuyChildAdapter(Context context) {
        super(R.layout.item_to_buy_to_child);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveListBean.ListBean item) {
//        ImageLoader.displayLocalCornersImage(getActivity(),data.getMatchImgUrl(),binding.ivLogo,5);

    }
}
