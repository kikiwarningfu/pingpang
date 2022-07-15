package heyong.intellectPinPang.adapter.mine

import android.graphics.Color
import android.text.TextUtils
import android.view.View
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.live.XlZBjieDanOrdeListBean
import heyong.intellectPinPang.databinding.ItemMyLiveOrderBinding

class MyLiveOrderAdapter :
    BaseQuickAdapter<XlZBjieDanOrdeListBean.ListBean, BaseViewHolder>(R.layout.item_my_live_order) {

    override fun convert(helper: BaseViewHolder, item: XlZBjieDanOrdeListBean.ListBean?) {
        val binding=DataBindingUtil.bind<ItemMyLiveOrderBinding>(helper.itemView)
        val bean=item?.xlZhiboSetOrder

        var status = item?.status
        binding!!.tvOrderNo.text="订单号："+bean?.orderNum
        binding!!.tvGameName.text=bean?.matchTitle
        binding!!.tvTableNum.text=""+bean?.tableNum+"台"
        binding!!.tvTime.text=""+bean?.matchTime?.substring(11)!!.substring(0,5)
        binding!!.tvDate.text=""+bean?.matchTime?.substring(IntRange(0,10))

        if (bean?.itemType.equals("1") || bean?.itemType.equals("2")) {

            if (TextUtils.isEmpty(bean?.player1)) {
                binding.tvLeftUserName.text = "虚位待赛"
                binding.tvLeftClubName.text = "1号位"
            } else {
                binding.tvLeftUserName.text = bean?.player1
                binding.tvLeftClubName.text = bean?.leftClubName
            }
            if (TextUtils.isEmpty(bean?.player3)) {
                binding.tvRightUserName.text = "虚位待赛"
                binding.tvRightClubName.text = "2号位"
            } else {
                binding.tvRightUserName.text = bean?.player3
                binding.tvRightClubName.text = bean?.rightClubName
            }
        } else {


            if (TextUtils.isEmpty(bean?.player1)) {
                binding.tvLeftUserName.text = "虚位待赛"
                binding.tvLeftClubName.text = "1号位"
            } else {
                binding.tvLeftUserName.text = bean?.player1+"/"+bean?.player2
                binding.tvLeftClubName.text = bean?.leftClubName
            }
            if (TextUtils.isEmpty(bean?.player3)) {
                binding.tvRightUserName.text = "虚位待赛"
                binding.tvRightClubName.text = "2号位"
            } else {
                binding.tvRightUserName.text = bean?.player3+"/"+bean?.player4
                binding.tvRightClubName.text = bean?.rightClubName
            }



        }
        helper.addOnClickListener(R.id.tv_order_detail)
//        1  待直播  2 3 已取消  456  进行中  7 异常订单  8  已完成
        when (status) {
            "1"->{
                binding!!.llShangjin.visibility=View.VISIBLE
                binding!!.tvTextStatus.visibility=View.GONE
                binding!!.tvMoney.text="¥"+bean?.money
                binding.tvOrderStatus.text="待直播"
                binding.tvOrderStatus.setTextColor(Color.parseColor("#E34749"))

            }
            "2","3"->{
                binding!!.llShangjin.visibility=View.VISIBLE
                binding!!.tvTextStatus.visibility=View.GONE
                binding!!.tvMoney.text="¥"+bean?.money
                binding.tvOrderStatus.text="已取消"
                binding.tvOrderStatus.setTextColor(Color.parseColor("#E34749"))

            }
            "4","5"->{
                binding!!.llShangjin.visibility=View.VISIBLE
                binding!!.tvTextStatus.visibility=View.GONE
                binding!!.tvMoney.text="¥"+bean?.money
                binding.tvOrderStatus.text="进行中"
                binding.tvOrderStatus.setTextColor(Color.parseColor("#4795ED"))

            }
            "6"->{
                //入账中
                binding!!.llShangjin.visibility=View.GONE
                binding!!.tvTextStatus.visibility=View.VISIBLE
                binding.tvOrderStatus.text="已完成"
                binding.tvOrderStatus.setTextColor(Color.parseColor("#999999"))
                binding.tvTextStatus.setTextColor(Color.parseColor("#F4BE21"))
                binding.tvTextStatus.text="赏金入账中"
            }
            "7","9"->{
                binding!!.llShangjin.visibility=View.GONE
                binding!!.tvTextStatus.visibility=View.VISIBLE
                binding!!.tvMoney.text="¥"+bean?.money
                binding.tvOrderStatus.text="异常订单"
                binding.tvOrderStatus.setTextColor(Color.parseColor("#E34749"))
                binding.tvTextStatus.setTextColor(Color.parseColor("#999999"))
                binding.tvTextStatus.text="直播违规或被投诉"
            }
            "8"->{
                //已完成
                binding!!.llShangjin.visibility=View.GONE
                binding!!.tvTextStatus.visibility=View.VISIBLE
                binding.tvOrderStatus.text="已完成"
                binding.tvOrderStatus.setTextColor(Color.parseColor("#999999"))
                binding.tvTextStatus.setTextColor(Color.parseColor("#999999"))
                binding.tvTextStatus.text="赏金已入账"

            }
        }

    }


}