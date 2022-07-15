package heyong.intellectPinPang.adapter.live

import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.live.OfferRewardOrderListBean
import heyong.intellectPinPang.databinding.ItemLiveOrderListBinding
import java.text.SimpleDateFormat

class LiveOrderListAdapter : BaseQuickAdapter<OfferRewardOrderListBean.ListBean,
        BaseViewHolder>(R.layout.item_live_order_list) {

    private val yearFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private val format: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")

    override fun convert(helper: BaseViewHolder, item: OfferRewardOrderListBean.ListBean) {
        val binding = DataBindingUtil.bind<ItemLiveOrderListBinding>(helper.itemView)
        binding?.tvTableNum!!.text = "" + item.tableNum + "台"
        helper.addOnClickListener(R.id.tv_acccept_order)


        if (item.itemType==1 || item.itemType==2) {
            if (TextUtils.isEmpty(item.player1)) {
                binding.tvLeftUserName.text = "虚位待赛"
                binding.tvLeftTeamName.text = "1号位"
            } else {
                binding.tvLeftUserName.text = item.player1
                binding.tvLeftTeamName.text = item.leftClubName
            }
            if (TextUtils.isEmpty(item.player3)) {
                binding.tvRightUserName.text = "虚位待赛"
                binding.tvRightTeamName.text = "2号位"
            } else {
                binding.tvRightUserName.text = item.player3
                binding.tvRightTeamName.text = item.rightClubName
            }
        } else {
            if (TextUtils.isEmpty(item.player1)) {
                binding.tvLeftUserName.text = "虚位待赛"
                binding.tvLeftTeamName.text = "1号位"
            } else {
                binding.tvLeftUserName.text = item.player1 + "/" + item.player2
                binding.tvLeftTeamName.text = item.leftClubName
            }
            if (TextUtils.isEmpty(item.player3)) {
                binding.tvRightUserName.text = "虚位待赛"
                binding.tvRightTeamName.text = "2号位"
            } else {
                binding.tvRightUserName.text = item.player3 + "/" + item.player4
                binding.tvRightTeamName.text = item.rightClubName
            }
        }
        binding.tvMoney.text = "赏金：¥" + item.money
        var s = "" + item.matchTime.substring(11)
        binding.tvGameTime.text = s.substring(0,5)

        binding?.executePendingBindings()

    }
}