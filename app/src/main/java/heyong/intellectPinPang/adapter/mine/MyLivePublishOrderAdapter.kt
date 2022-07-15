package heyong.intellectPinPang.adapter.mine

import android.graphics.Color
import android.text.TextUtils
import android.view.View
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.live.XlZhiboSetOrderListBean
import heyong.intellectPinPang.databinding.ItemMyLivePublishOrderBinding

class MyLivePublishOrderAdapter :
    BaseQuickAdapter<XlZhiboSetOrderListBean.ListBean, BaseViewHolder>(R.layout.item_my_live_publish_order) {

    override fun convert(helper: BaseViewHolder, item: XlZhiboSetOrderListBean.ListBean) {

        val binding = DataBindingUtil.bind<ItemMyLivePublishOrderBinding>(helper.itemView)
//        0 1  已取消   2  待支付  3 未接取  4 已接取 5 比赛中  6  待确认  10  已完成  7 8 9 异常订单
//        订单状态 0取消订单,1取消订单（超时取消）,2待付款,3付款成功,4.待直播,5直播中,6直播完成(待确认) 7退款中 8退款成功 9退款失败 10已完成
        var status = item.orderStatus
        binding!!.tvOrderNo.text = "订单号：" + item.orderNum
        binding!!.tvGameName.text = "" + item.matchTitle
        binding!!.tvTableNum.text = "" + item.tableNum + "台"
        var time=item.matchTime
        binding.tvTime.text=""+time.substring(11).substring(0,5)
        binding.tvDay.text=""+time.substring(IntRange(0,10))
        if (item.itemType.equals("1") || item.itemType.equals("2")) {
            binding.tvLeftUserNameTwo.visibility = View.GONE
            binding.tvRightUserNameTwo.visibility = View.GONE
            if (TextUtils.isEmpty(item.player1)) {
                binding.tvLeftUserNameOne.text = "虚位待赛"
                binding.tvLeftClubName.text = "1号位"
            } else {
                binding.tvLeftUserNameOne.text = item.player1
                binding.tvLeftClubName.text = item.leftClubName
            }
            if (TextUtils.isEmpty(item.player3)) {
                binding.tvRightUserNameOne.text = "虚位待赛"
                binding.tvRightClubName.text = "2号位"
            } else {
                binding.tvRightUserNameOne.text = item.player3
                binding.tvRightClubName.text = item.rightClubName
            }
        } else {

            binding.tvLeftUserNameTwo.visibility = View.VISIBLE
            binding.tvRightUserNameTwo.visibility = View.VISIBLE
            if (TextUtils.isEmpty(item.player1)) {
                binding.tvLeftUserNameOne.text = "虚位待赛"
                binding.tvLeftUserNameTwo.text = "虚位待赛"
                binding.tvLeftClubName.text = "1号位"
            } else {
                binding.tvLeftUserNameOne.text = item.player1
                binding.tvLeftUserNameTwo.text = item.player2
                binding.tvLeftClubName.text = item.leftClubName
            }
            if (TextUtils.isEmpty(item.player3)) {
                binding.tvRightUserNameOne.text = "虚位待赛"
                binding.tvRightUserNameTwo.text = "虚位待赛"
                binding.tvRightClubName.text = "2号位"
            } else {
                binding.tvRightUserNameOne.text = item.player3
                binding.tvRightUserNameTwo.text = item.player4
                binding.tvRightClubName.text = item.rightClubName
            }




        }
        helper.addOnClickListener(R.id.tv_order_detail)
        when (status) {
            "0", "1" -> {//已取消
                binding.tvTextStatus.setTextColor(Color.parseColor("#999999"))
                binding.tvTextStatus.text="下单人取消订单"
                binding.tvOrderDetail.visibility=View.GONE
                binding.llShangjin.visibility=View.GONE
                binding.tvUpOrderStatus.text="已取消"
//                binding.rlCenter.background= "#334795ED"
                binding.tvUpOrderStatus.setTextColor(Color.parseColor("#E34749"))

                binding.rlCenter.setBackgroundResource(R.drawable.shape_write_no_corners_bg)
            }
            "2" -> {//待支付
                binding.tvTextStatus.visibility=View.GONE
                binding.tvOrderDetail.visibility=View.VISIBLE
                binding.llShangjin.visibility=View.VISIBLE
                binding.tvShangjin.text="￥"+item.money
                binding.tvShangjinText.setTextColor(Color.parseColor("#333333"))
                binding.tvShangjin.setTextColor(Color.parseColor("#E34749"))
                binding.tvUpOrderStatus.text="待支付"
                binding.tvUpOrderStatus.setTextColor(Color.parseColor("#E34749"))
                binding.rlCenter.setBackgroundResource(R.drawable.shape_write_no_corners_bg)

            }
            "3" -> {//未接取
                binding.tvTextStatus.visibility=View.GONE
                binding.tvOrderDetail.visibility=View.VISIBLE
                binding.llShangjin.visibility=View.VISIBLE
                binding.tvShangjin.text="￥"+item.money
                binding.tvShangjinText.setTextColor(Color.parseColor("#333333"))
                binding.tvShangjin.setTextColor(Color.parseColor("#E34749"))
                binding.tvUpOrderStatus.text="未接取"
                binding.tvUpOrderStatus.setTextColor(Color.parseColor("#E34749"))
                binding.rlCenter.setBackgroundResource(R.drawable.shape_write_no_corners_bg)

            }
            "4" -> {//4 已接取
                binding.tvTextStatus.visibility=View.GONE
                binding.tvOrderDetail.visibility=View.VISIBLE
                binding.llShangjin.visibility=View.VISIBLE
                binding.tvShangjin.text="￥"+item.money
                binding.tvShangjinText.setTextColor(Color.parseColor("#333333"))
                binding.tvShangjin.setTextColor(Color.parseColor("#E34749"))
                binding.tvUpOrderStatus.text="已接取"
                binding.tvUpOrderStatus.setTextColor(Color.parseColor("#4795ED"))
                binding.rlCenter.setBackgroundResource(R.drawable.shape_write_no_corners_bg)

            }
            "5" -> {//5 比赛中
                binding.tvTextStatus.visibility=View.GONE
                binding.tvOrderDetail.visibility=View.VISIBLE
                binding.llShangjin.visibility=View.VISIBLE
                binding.tvShangjin.text="￥"+item.money
                binding.tvShangjinText.setTextColor(Color.parseColor("#333333"))
                binding.tvShangjin.setTextColor(Color.parseColor("#E34749"))
                binding.tvUpOrderStatus.text="比赛中.."
                binding.tvUpOrderStatus.setTextColor(Color.parseColor("#4795ED"))

                binding.rlCenter.setBackgroundResource(R.drawable.shape_game_no_corners_bg)


            }
            "6" -> {//6  待确认
                binding.tvTextStatus.visibility=View.GONE
                binding.tvOrderDetail.visibility=View.VISIBLE
                binding.llShangjin.visibility=View.VISIBLE
                binding.tvShangjin.text="￥"+item.money
                binding.tvShangjinText.setTextColor(Color.parseColor("#333333"))
                binding.tvShangjin.setTextColor(Color.parseColor("#E34749"))
                binding.tvUpOrderStatus.text="待确认"
                binding.tvUpOrderStatus.setTextColor(Color.parseColor("#E34749"))

                binding.rlCenter.setBackgroundResource(R.drawable.shape_write_no_corners_bg)


            }
            "7", "8", "9","11" -> {//7 8 9 异常订单
                binding.tvTextStatus.visibility=View.GONE
                binding.tvOrderDetail.visibility=View.VISIBLE
                binding.llShangjin.visibility=View.VISIBLE
                binding.tvShangjin.text="￥"+item.money
                binding.tvShangjinText.setTextColor(Color.parseColor("#333333"))
                binding.tvShangjin.setTextColor(Color.parseColor("#E34749"))
                binding.tvUpOrderStatus.text="异常订单"
                binding.tvUpOrderStatus.setTextColor(Color.parseColor("#E34749"))
                binding.rlCenter.setBackgroundResource(R.drawable.shape_write_no_corners_bg)
            }
            "10" -> {//10  已完成
                binding.tvTextStatus.visibility=View.GONE
                binding.tvOrderDetail.visibility=View.VISIBLE
                binding.llShangjin.visibility=View.VISIBLE
                binding.tvShangjin.text="￥"+item.money
                binding.tvShangjinText.setTextColor(Color.parseColor("#999999"))
                binding.tvShangjin.setTextColor(Color.parseColor("#999999"))
                binding.tvUpOrderStatus.text="已完成"
                binding.tvUpOrderStatus.setTextColor(Color.parseColor("#999999"))
                binding.rlCenter.setBackgroundResource(R.drawable.shape_write_no_corners_bg)
            }
        }


    }


}