package heyong.intellectPinPang.adapter.live;

import android.content.Context;

import androidx.databinding.DataBindingUtil;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.live.LiveHallBean;
import heyong.intellectPinPang.bean.live.LiveListBean;
import heyong.intellectPinPang.databinding.ItemToLiveAndPlayBinding;

/**
 * description  live and playback adapter
 */
public class PlayBackLiveAdapter extends BaseQuickAdapter<LiveHallBean.ListBean, BaseViewHolder> {

    Context context;
    public PlayBackLiveAdapter(Context context) {
        super(R.layout.item_to_live_and_play);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveHallBean.ListBean item) {
        ItemToLiveAndPlayBinding binding = DataBindingUtil.bind(helper.getConvertView());
//        ImageLoader.displayLocalCornersImage(getActivity(),data.getMatchImgUrl(),binding.ivLogo,5);




    }
}
