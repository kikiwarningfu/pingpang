package heyong.intellectPinPang.adapter.live

import android.graphics.Color
import android.view.View
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.live.OfferRewardOrderListConditionBean
import heyong.intellectPinPang.databinding.ItemOfferRewardOrderListConditionBinding

class OfferRewardOrderListConditionAdapter :
    BaseQuickAdapter<OfferRewardOrderListConditionBean, BaseViewHolder>
        (R.layout.item_offer_reward_order_list_condition) {

    override fun convert(helper: BaseViewHolder, item: OfferRewardOrderListConditionBean) {
        val layoutBinding =
            DataBindingUtil.bind<ItemOfferRewardOrderListConditionBinding>(helper.itemView)!!
        helper.addOnClickListener(R.id.ll_onClick)
        if (item.isSelect) {
            layoutBinding.tvName.setTextColor(Color.parseColor("#4795ED"))
            layoutBinding.viewDivider.visibility = View.VISIBLE
        } else {
            layoutBinding.tvName.setTextColor(Color.parseColor("#333333"))
            layoutBinding.viewDivider.visibility = View.INVISIBLE
        }
        layoutBinding.tvName.text = "" + item.value
        layoutBinding.executePendingBindings()


    }

}