package heyong.intellectPinPang.adapter.mine

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import heyong.handong.framework.utils.ImageLoader
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.live.LiveBroadCastMatchListBean
import heyong.intellectPinPang.databinding.ItemLiveGameListBinding
import heyong.intellectPinPang.utils.Utils

class LiveGameListAdapter :
    BaseQuickAdapter<LiveBroadCastMatchListBean.ListBean, BaseViewHolder>(R.layout.item_live_game_list) {

    override fun convert(helper: BaseViewHolder, item: LiveBroadCastMatchListBean.ListBean) {

        val binding = DataBindingUtil.bind<ItemLiveGameListBinding>(helper.itemView)
//        0 1  已取消   2  待支付  3 未接取  4 已接取 5 比赛中  6  待确认  10  已完成  7 8 9 异常订单
//        订单状态 0取消订单,1取消订单（超时取消）,2待付款,3付款成功,4.待直播,5直播中,6直播完成(待确认) 7退款中 8退款成功 9退款失败 10已完成

        ImageLoader.loadImage(binding!!.ivLogo,item.matchImgUrl)
        binding.tvGameName.text=""+item.matchTitle
        binding.tvSeeNum.text=""+Utils.getShowVisitor(item.numberOfVisitors.toDouble())


    }


}