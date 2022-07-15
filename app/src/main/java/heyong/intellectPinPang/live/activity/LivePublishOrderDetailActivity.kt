package heyong.intellectPinPang.live.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import cn.jzvd.JZUtils.hideStatusBar
import com.elvishew.xlog.XLog
import com.xq.fasterdialog.dialog.CustomDialog
import heyong.handong.framework.utils.ImageLoader
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.live.XLzhiboSetOrderInfoBean
import heyong.intellectPinPang.databinding.ActivityLiveOrderPublishDetailBinding
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.soundnet.SingleHostLiveActivity
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.ui.mine.activity.live.OrderCancelActivity
import heyong.intellectPinPang.ui.mine.activity.live.OrderConfirmActivity
import heyong.intellectPinPang.ui.mine.event.OrderStatusChangeEvent
import heyong.intellectPinPang.utils.CommonUtilis
import org.greenrobot.eventbus.EventBus

/**
 * 下单人的订单详情
 * reasoon 是后台给的   tuikuanshuoming 自己提交的
 */
class LivePublishOrderDetailActivity :
    BaseActivity<ActivityLiveOrderPublishDetailBinding, LiveViewModel>(),
    View.OnClickListener {
    private var id = ""
    var xlBean: XLzhiboSetOrderInfoBean? = null
    var orderStatus = ""
    var zhiboId = ""

    companion object {

        var instance: LivePublishOrderDetailActivity? = null
        fun startActivity(context: Context, id: String) {
            val intent = Intent(context, LivePublishOrderDetailActivity::class.java)
            intent.putExtra("id", "" + id)
            context.startActivity(intent)
        }
    }


    //请求数据
    override fun getLayoutRes(): Int {
        return R.layout.activity_live_order_publish_detail
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getXlZhiboSetOrderInfo(id)
    }

    override fun initView(savedInstanceState: Bundle?) {
        id = intent.getStringExtra("id")
        mBinding.listener = this
        instance = this

        mViewModel.cancelOrderData.observe(this, Observer {
            dismissLoading()
            if (it.errorCode.equals("200")) {
                toast("" + it.message)
                //刷新 订单列表的数据
                EventBus.getDefault().post(OrderStatusChangeEvent())
                OrderCancelActivity.startActivity(this, xlBean!!)
                finish()
            } else {
                toast(it.message)
            }
        })
        mViewModel.getXlZhiboSetOrderInfoData.observe(this, Observer {
            if (it.errorCode.equals("200")) {
                mBinding.llContents.visibility = View.VISIBLE
                xlBean = it.data
                zhiboId = xlBean?.zhiboId!!
                var orderStatus = xlBean?.orderStatus
                when (orderStatus) {
                    "0", "1" -> {//已取消
                        //已取消不会有订单详情
                    }
                    "2" -> {//待支付
                        mBinding.llContent.visibility = View.VISIBLE
                        mBinding.llOrderReason.visibility = View.GONE
                        mBinding.llToLive.visibility = View.GONE
                        mBinding.tvPayTwo.visibility = View.VISIBLE
                        mBinding.tvPayTwo.text = "去支付"
                        mBinding.tvPayTwo.setBackgroundResource(R.drawable.shape_order_track_blue)
                        mBinding.tvPayOne.visibility = View.VISIBLE
                        mBinding.tvPayOne.text = "取消订单"
                        mBinding.tvPayOne.setBackgroundResource(R.drawable.shape_order_track_red_bg)
                        mBinding.tvPayTwo.setTextColor(Color.parseColor("#FFFFFF"))
                        mBinding.tvPayOne.setTextColor(Color.parseColor("#FFFFFF"))
                        mBinding.tvOrderStatus.text = "待支付"
                        mBinding.tvOrderStatus.setTextColor(Color.parseColor("#DC3C3C"))
                        mBinding.rlXuzhi.visibility = View.VISIBLE

                        setText()
                    }
                    "3" -> {//未接取
                        mBinding.llContent.visibility = View.VISIBLE
                        mBinding.llOrderReason.visibility = View.GONE
                        mBinding.llToLive.visibility = View.GONE
                        mBinding.tvPayTwo.visibility = View.INVISIBLE
                        mBinding.tvPayTwo.text = "去支付"
                        mBinding.tvPayTwo.setBackgroundResource(R.drawable.shape_order_track_blue)
                        mBinding.tvPayOne.visibility = View.VISIBLE
                        mBinding.tvPayOne.text = "取消订单"
                        mBinding.tvPayOne.setBackgroundResource(R.drawable.shape_order_track_red_bg)
                        mBinding.tvPayTwo.setTextColor(Color.parseColor("#FFFFFF"))
                        mBinding.tvPayOne.setTextColor(Color.parseColor("#FFFFFF"))
                        mBinding.tvOrderStatus.text = "未接取"
                        mBinding.tvOrderStatus.setTextColor(Color.parseColor("#DC3C3C"))
                        mBinding.rlXuzhi.visibility = View.VISIBLE

                        setText()
                    }
                    "4" -> {//4 已接取
                        mBinding.llContent.visibility = View.VISIBLE
                        mBinding.llOrderReason.visibility = View.GONE
                        mBinding.llToLive.visibility = View.GONE
                        mBinding.tvPayTwo.visibility = View.INVISIBLE
                        mBinding.tvPayTwo.text = "去支付"
                        mBinding.tvPayTwo.setBackgroundResource(R.drawable.shape_order_track_blue)
                        mBinding.tvPayOne.visibility = View.VISIBLE
                        mBinding.tvPayOne.text = "取消订单"
                        mBinding.tvPayOne.setBackgroundResource(R.drawable.shape_order_track_red_bg)
                        mBinding.tvPayTwo.setTextColor(Color.parseColor("#FFFFFF"))
                        mBinding.tvPayOne.setTextColor(Color.parseColor("#FFFFFF"))
                        mBinding.tvOrderStatus.text = "待兑现"
                        mBinding.tvOrderStatus.setTextColor(Color.parseColor("#4795ED"))
                        mBinding.rlXuzhi.visibility = View.VISIBLE
                        setText()
                    }
                    "5" -> {//5 比赛中
                        mBinding.llContent.visibility = View.GONE
                        mBinding.llOrderReason.visibility = View.GONE
                        mBinding.tvLiveOrderNo.visibility = View.GONE

                        mBinding.llToLive.visibility = View.VISIBLE
                        mBinding.tvLiveStatus.visibility = View.VISIBLE

                        mBinding.rlTousuReason.visibility = View.GONE
                        mBinding.tvPayTwo.visibility = View.INVISIBLE
                        mBinding.tvPayOne.visibility = View.VISIBLE
                        mBinding.tvPayOne.setBackgroundResource(R.drawable.shape_order_track_gray_bg)
                        mBinding.tvPayOne.setTextColor(Color.parseColor("#FFFFFF"))
                        mBinding.tvPayOne.text = "订单已完成"
                        mBinding.tvLiveStatus.text = "直播中..."
                        mBinding.tvLiveStatus.setTextColor(Color.parseColor("#4795ED"))
                        mBinding.rlXuzhi.visibility = View.VISIBLE

                        ImageLoader.loadImage(
                            mBinding.ivLogo,
                            xlBean?.fengmian,
                            R.drawable.img_live_moren,
                            R.drawable.img_live_moren
                        )
                        mBinding.tvTableNumers.text = "" + xlBean?.tableNum + "台"
                        var itemType = xlBean?.itemType
                        var leftName = ""
                        var rightName = ""
                        if (itemType == "1" || itemType == "2") {
                            if (TextUtils.isEmpty(xlBean?.player1)) {
                                leftName = "虚位待赛"
                            } else {
                                leftName = "" + xlBean?.player1
                            }
                            if (TextUtils.isEmpty(xlBean?.player3)) {
                                rightName = "虚位待赛"
                            } else {
                                rightName = "" + xlBean?.player3
                            }
                        } else {
                            if (TextUtils.isEmpty(xlBean?.player1)) {
                                leftName = "虚位待赛"
                            } else {
                                leftName = "" + xlBean?.player1 + "/" + xlBean?.player2
                            }
                            if (TextUtils.isEmpty(xlBean?.player3)) {
                                rightName = "虚位待赛"
                            } else {
                                rightName = "" + xlBean?.player3 + "/" + xlBean?.player4
                            }
                        }
                        mBinding.tvLiveUserName.text = leftName + " VS " + rightName
                        mBinding.tvTimes.text = xlBean?.hourTime


                    }
                    "6" -> {//6  待确认
                        mBinding.llContent.visibility = View.GONE
                        mBinding.llOrderReason.visibility = View.GONE
                        mBinding.llToLive.visibility = View.VISIBLE
                        mBinding.tvLiveOrderNo.visibility = View.GONE
                        mBinding.tvLiveStatus.visibility = View.VISIBLE
                        mBinding.rlTousuReason.visibility = View.GONE
                        mBinding.tvPayTwo.visibility = View.VISIBLE
                        mBinding.tvPayTwo.setBackgroundResource(R.drawable.shape_order_track_blue)
                        mBinding.tvPayTwo.text = "确认订单"
                        mBinding.tvPayOne.visibility = View.VISIBLE
                        mBinding.tvPayOne.setBackgroundResource(R.drawable.shape_stroke_red_4)
                        mBinding.tvPayOne.text = "投诉"
                        mBinding.tvPayOne.setTextColor(Color.parseColor("#F33E3E"))
                        mBinding.tvLiveStatus.text = "待确认"
                        mBinding.tvLiveStatus.setTextColor(Color.parseColor("#F33E3E"))
                        mBinding.rlXuzhi.visibility = View.VISIBLE

                        ImageLoader.loadImage(
                            mBinding.ivLogo,
                            xlBean?.fengmian,
                            R.drawable.img_live_moren,
                            R.drawable.img_live_moren
                        )
                        mBinding.tvTableNumers.text = "" + xlBean?.tableNum + "台"
                        var itemType = xlBean?.itemType
                        var leftName = ""
                        var rightName = ""
                        if (itemType == "1" || itemType == "2") {
                            if (TextUtils.isEmpty(xlBean?.player1)) {
                                leftName = "虚位待赛"
                            } else {
                                leftName = "" + xlBean?.player1
                            }
                            if (TextUtils.isEmpty(xlBean?.player3)) {
                                rightName = "虚位待赛"
                            } else {
                                rightName = "" + xlBean?.player3
                            }
                        } else {
                            if (TextUtils.isEmpty(xlBean?.player1)) {
                                leftName = "虚位待赛"
                            } else {
                                leftName = "" + xlBean?.player1 + "/" + xlBean?.player2
                            }
                            if (TextUtils.isEmpty(xlBean?.player3)) {
                                rightName = "虚位待赛"
                            } else {
                                rightName = "" + xlBean?.player3 + "/" + xlBean?.player4
                            }
                        }
                        mBinding.tvLiveUserName.text = leftName + " VS " + rightName
                        mBinding.tvTimes.text = xlBean?.hourTime
                    }
                    "7", "11" -> {//7 8 9 异常订单
                        //处理中
                        mBinding.llContent.visibility = View.GONE
                        mBinding.llOrderReason.visibility = View.GONE
                        mBinding.tvLiveStatus.visibility = View.GONE

                        mBinding.llToLive.visibility = View.VISIBLE
                        mBinding.tvLiveOrderNo.visibility = View.VISIBLE

                        mBinding.rlTousuReason.visibility = View.VISIBLE
                        mBinding.rlXuzhi.visibility = View.GONE


                        mBinding.tvPayOne.visibility = View.GONE
                        mBinding.tvPayTwo.visibility = View.VISIBLE
                        mBinding.tvPayTwo.text = "投诉处理中"
                        mBinding.tvPayTwo.setTextColor(Color.parseColor("#FFFFFF"))
                        mBinding.tvPayTwo.setBackgroundResource(R.drawable.shape_order_track_red_bg)
                        mBinding.tvLiveOrderNo.text = "" + xlBean?.orderNum
                        ImageLoader.loadImage(
                            mBinding.ivLogo,
                            xlBean?.fengmian,
                            R.drawable.img_live_moren,
                            R.drawable.img_live_moren
                        )

                        mBinding.tvTableNumers.text = "" + xlBean?.tableNum + "台"
                        var itemType = xlBean?.itemType
                        var leftName = ""
                        var rightName = ""
                        if (itemType == "1" || itemType == "2") {
                            if (TextUtils.isEmpty(xlBean?.player1)) {
                                leftName = "虚位待赛"
                            } else {
                                leftName = "" + xlBean?.player1
                            }
                            if (TextUtils.isEmpty(xlBean?.player3)) {
                                rightName = "虚位待赛"
                            } else {
                                rightName = "" + xlBean?.player3
                            }
                        } else {
                            if (TextUtils.isEmpty(xlBean?.player1)) {
                                leftName = "虚位待赛"
                            } else {
                                leftName = "" + xlBean?.player1 + "/" + xlBean?.player2
                            }
                            if (TextUtils.isEmpty(xlBean?.player3)) {
                                rightName = "虚位待赛"
                            } else {
                                rightName = "" + xlBean?.player3 + "/" + xlBean?.player4
                            }
                        }
                        mBinding.tvLiveUserName.text = leftName + " VS " + rightName
                        mBinding.tvTimes.text = xlBean?.hourTime
//                        mBinding.tvTousuReason.text = xlBean?.tuikuanshuoming
                        mBinding.tvTousuReason.text = "" + xlBean?.reason

                    }
                    "8" -> {
                        //投诉已处理

                        mBinding.llContent.visibility = View.GONE
                        mBinding.llOrderReason.visibility = View.VISIBLE
                        mBinding.llToLive.visibility = View.VISIBLE
                        mBinding.tvLiveOrderNo.visibility = View.VISIBLE
                        mBinding.tvLiveStatus.visibility = View.GONE
                        mBinding.rlXuzhi.visibility = View.GONE
                        mBinding.rlTousuReason.visibility = View.GONE


                        mBinding.tvPayOne.visibility = View.GONE
                        mBinding.tvPayTwo.visibility = View.VISIBLE
                        mBinding.tvPayTwo.text = "投诉已处理"
                        mBinding.tvPayTwo.setTextColor(Color.parseColor("#FFFFFF"))
                        mBinding.tvPayTwo.setBackgroundResource(R.drawable.shape_order_track_gray_bg)

                        mBinding.tvLiveOrderNo.text = "" + xlBean?.orderNum
                        ImageLoader.loadImage(
                            mBinding.ivLogo,
                            xlBean?.fengmian,
                            R.drawable.img_live_moren,
                            R.drawable.img_live_moren
                        )

                        mBinding.tvTableNumers.text = "" + xlBean?.tableNum + "台"
                        var itemType = xlBean?.itemType
                        var leftName = ""
                        var rightName = ""
                        if (itemType == "1" || itemType == "2") {
                            if (TextUtils.isEmpty(xlBean?.player1)) {
                                leftName = "虚位待赛"
                            } else {
                                leftName = "" + xlBean?.player1
                            }
                            if (TextUtils.isEmpty(xlBean?.player3)) {
                                rightName = "虚位待赛"
                            } else {
                                rightName = "" + xlBean?.player3
                            }
                        } else {
                            if (TextUtils.isEmpty(xlBean?.player1)) {
                                leftName = "虚位待赛"
                            } else {
                                leftName = "" + xlBean?.player1 + "/" + xlBean?.player2
                            }
                            if (TextUtils.isEmpty(xlBean?.player3)) {
                                rightName = "虚位待赛"
                            } else {
                                rightName = "" + xlBean?.player3 + "/" + xlBean?.player4
                            }
                        }
                        mBinding.tvLiveUserName.text = leftName + " VS " + rightName
                        mBinding.tvTimes.text = xlBean?.hourTime
                        mBinding.tvChuliBanfa.text =
                            "经审核，此视频在拍摄过程中存在违规行为（视频不完整，视频内容不符）或违反《小亮智乒直播协议》，您的赏金将在72小时" +
                                    "内返还至您的账户，给您带来不好的体验，非常抱歉"
                        mBinding.tvErrorReason.text = "" + xlBean?.tuikuanshuoming
                    }
                    "9" -> {
                        //退款失败  订单已处理

                        mBinding.llContent.visibility = View.VISIBLE
                        mBinding.llOrderReason.visibility = View.VISIBLE
                        mBinding.llToLive.visibility = View.GONE
                        mBinding.tvLiveOrderNo.visibility = View.GONE
                        mBinding.tvLiveStatus.visibility = View.GONE
                        mBinding.rlXuzhi.visibility = View.GONE
                        mBinding.rlTousuReason.visibility = View.GONE

                        mBinding.tvPayOne.visibility = View.GONE
                        mBinding.tvPayTwo.visibility = View.VISIBLE
                        mBinding.tvPayTwo.text = "订单已处理"
                        mBinding.tvPayTwo.setTextColor(Color.parseColor("#FFFFFF"))
                        mBinding.tvPayTwo.setBackgroundResource(R.drawable.shape_order_track_gray_bg)
                        mBinding.tvOrderStatus.text = "已处理"
                        mBinding.tvOrderStatus.setTextColor(Color.parseColor("#999999"))
                        mBinding.tvChuliBanfa.text = "此订单未正常拍摄，视为作废，我们会根据实际情况对接单员做" +
                                "出处罚，你的悬赏金将在72小时内返还至您的账户，给您带来不" +
                                "好的体验，非常抱歉"


                        mBinding.tvErrorReason.text = "" + xlBean?.tuikuanshuoming

                        if (xlBean != null) {
                            mBinding.tvGameName.text = xlBean?.matchTitle
                            mBinding.tvTableNum.text = "" + xlBean?.tableNum + "台"
                            var itemType = xlBean?.itemType
                            var leftName = ""
                            var rightName = ""
                            if (itemType == "1" || itemType == "2") {
                                if (TextUtils.isEmpty(xlBean?.player1)) {
                                    leftName = "虚位待赛"
                                } else {
                                    leftName = "" + xlBean?.player1
                                }
                                if (TextUtils.isEmpty(xlBean?.player3)) {
                                    rightName = "虚位待赛"
                                } else {
                                    rightName = "" + xlBean?.player3
                                }
                            } else {
                                if (TextUtils.isEmpty(xlBean?.player1)) {
                                    leftName = "虚位待赛"
                                } else {
                                    leftName = "" + xlBean?.player1 + "/" + xlBean?.player2
                                }
                                if (TextUtils.isEmpty(xlBean?.player3)) {
                                    rightName = "虚位待赛"
                                } else {
                                    rightName = "" + xlBean?.player3 + "/" + xlBean?.player4
                                }
                            }
                            mBinding.tvNamePlayer.text = leftName + " VS " + rightName
                            mBinding.tvTime.text = xlBean?.hourTime
                            mBinding.tvAddress.text =
                                "比赛地点： " + xlBean?.matchLocal?.replace(" ", "")
                            mBinding.tvXiadanren.text =
                                "下单人： " + xlBean?.name + "    " + xlBean?.acconut
                            mBinding.tvMoney.text = xlBean?.money
                        }


                    }
                    "10" -> {//10  已完成
                        mBinding.llContent.visibility = View.GONE
                        mBinding.llOrderReason.visibility = View.GONE
                        mBinding.llToLive.visibility = View.VISIBLE
                        mBinding.tvLiveOrderNo.visibility = View.GONE
                        mBinding.tvLiveStatus.visibility = View.VISIBLE
                        mBinding.rlXuzhi.visibility = View.GONE
                        mBinding.rlTousuReason.visibility = View.GONE
                        mBinding.tvPayTwo.visibility = View.GONE
                        mBinding.tvPayOne.visibility = View.GONE
                        mBinding.tvLiveStatus.text = "已完成"
                        mBinding.tvLiveStatus.setTextColor(Color.parseColor("#666666"))

                        ImageLoader.loadImage(
                            mBinding.ivLogo,
                            xlBean?.fengmian,
                            R.drawable.img_live_moren,
                            R.drawable.img_live_moren
                        )

                        mBinding.tvTableNumers.text = "" + xlBean?.tableNum + "台"
                        var itemType = xlBean?.itemType
                        var leftName = ""
                        var rightName = ""
                        if (itemType == "1" || itemType == "2") {
                            if (TextUtils.isEmpty(xlBean?.player1)) {
                                leftName = "虚位待赛"
                            } else {
                                leftName = "" + xlBean?.player1
                            }
                            if (TextUtils.isEmpty(xlBean?.player3)) {
                                rightName = "虚位待赛"
                            } else {
                                rightName = "" + xlBean?.player3
                            }
                        } else {
                            if (TextUtils.isEmpty(xlBean?.player1)) {
                                leftName = "虚位待赛"
                            } else {
                                leftName = "" + xlBean?.player1 + "/" + xlBean?.player2
                            }
                            if (TextUtils.isEmpty(xlBean?.player3)) {
                                rightName = "虚位待赛"
                            } else {
                                rightName = "" + xlBean?.player3 + "/" + xlBean?.player4
                            }
                        }
                        mBinding.tvLiveUserName.text = leftName + " VS " + rightName
                        mBinding.tvTimes.text = xlBean?.hourTime
                    }
                }

            } else {
                toast(it.message)
            }

        })

        mViewModel.xlZhiboSetOrderconfirmOrderData.observe(this, Observer {
            dismissLoading()
            if (it.errorCode.equals("200")) {
//              toast(it.message)
                EventBus.getDefault().post(OrderStatusChangeEvent())
                //确认订单
                OrderConfirmActivity.startActivity(this, xlBean!!)
                finish()
            } else {
                toast(it.message)
            }


        })
    }

    private fun setText() {
        if (xlBean != null) {
            mBinding.tvGameName.text = xlBean?.matchTitle
            mBinding.tvTableNum.text = "" + xlBean?.tableNum + "台"
            var itemType = xlBean?.itemType
            var leftName = ""
            var rightName = ""
            if (itemType == "1" || itemType == "2") {
                if (TextUtils.isEmpty(xlBean?.player1)) {
                    leftName = "虚位待赛"
                } else {
                    leftName = "" + xlBean?.player1
                }
                if (TextUtils.isEmpty(xlBean?.player3)) {
                    rightName = "虚位待赛"
                } else {
                    rightName = "" + xlBean?.player3
                }
            } else {
                if (TextUtils.isEmpty(xlBean?.player1)) {
                    leftName = "虚位待赛"
                } else {
                    leftName = "" + xlBean?.player1 + "/" + xlBean?.player2
                }
                if (TextUtils.isEmpty(xlBean?.player3)) {
                    rightName = "虚位待赛"
                } else {
                    rightName = "" + xlBean?.player3 + "/" + xlBean?.player4
                }
            }
            mBinding.tvNamePlayer.text = leftName + " VS " + rightName
            mBinding.tvTime.text = xlBean?.hourTime
            mBinding.tvAddress.text = "比赛地点： " + xlBean?.matchLocal?.replace(" ", "")
            mBinding.tvXiadanren.text = "下单人： " + xlBean?.name + "    " + xlBean?.acconut
//            mBinding.tvXiadanren.text = "下单人： " + ￥{xlBean.name}
            mBinding.tvMoney.text = xlBean?.money
        }

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_see_live -> {
//                exitClubDialog();
                //todo 跳转到看直播
                var orderStatus = xlBean?.orderStatus
                when (orderStatus) {
                    //todo 直播 和回放入口
                    "5" -> {
                        //观看视频 跳转到直播页面
                        SingleHostLiveActivity.goActivity(this, false, "" + zhiboId)
                    }
                    "6" -> {
                        //观看视频 跳转到直播页面
                        WatchLiveRecordActivity.goActivity(this, zhiboId)
                    }
                    "10", "11" -> {
                        //观看视频 跳转到直播页面
                        WatchLiveRecordActivity.goActivity(this, zhiboId)
                    }
                    //观看回放
                    "7" -> {
                        WatchLiveRecordActivity.goActivity(this, zhiboId)
                    }
                    //观看回放
                    "8" -> {
                        WatchLiveRecordActivity.goActivity(this, zhiboId)
                    }
                }

            }
            R.id.tv_pay_two -> {
                var orderStatus = xlBean?.orderStatus

                if (orderStatus.equals("2") || orderStatus.equals("3") || orderStatus.equals("4")) {


                    //取消订单
                    PayLiveOrderActivity.startActivity(
                        this, "" + xlBean?.money, "" + xlBean?.orderType, "" + xlBean?.id
                    )
                } else if (orderStatus.equals("6")) {
                    showLoading()
                    mViewModel.xlZhiboSetOrderconfirmOrder("" + id)

                }


            }
            R.id.tv_pay_one -> {
                var orderStatus = xlBean?.orderStatus
                if (orderStatus.equals("2") || orderStatus.equals("3") || orderStatus.equals("4")) {
//
                    var jiedan = xlBean?.hasJiedan
                    if (jiedan.equals("1")) {
                        exitClubDialog()
                    } else {
                        showLoading()
                        mViewModel.cancelOrder("" + xlBean?.id)
                    }

                } else if (orderStatus.equals("6")) {
                    //投诉
                    ComplainActivity.startActivity(this, "" + xlBean?.id)
                }

            }
            R.id.rl_xuzhi -> {

            }
        }


    }

    var customDialogConfirm: CustomDialog? = null

    private fun exitClubDialog() {
        customDialogConfirm = CustomDialog(this)
        customDialogConfirm!!.setCustomView(R.layout.pop_cancel_order_publish_sure)
        customDialogConfirm!!.show()
        val customView = customDialogConfirm!!.customView
        showAskConfirmView(customView, customDialogConfirm!!)
    }

    private fun showAskConfirmView(
        customView: View?, customDialogConfirm: CustomDialog
    ) {

        val tvSure = customView?.findViewById<TextView>(R.id.tv_sure)
        val tvCancel = customView?.findViewById<TextView>(R.id.tv_cancel)
        val tvServiceCharge = customView?.findViewById<TextView>(R.id.tv_service_charge)
//        tvServiceCharge!!.text="需扣除手续费：¥"+ CommonUtilis.getDecimalServiceCharge("99")
        tvServiceCharge!!.text = "需扣除手续费：¥" + CommonUtilis.getDecimalServiceCharge(xlBean!!.money)
        tvCancel?.setOnClickListener {
            if (customDialogConfirm != null) {
                customDialogConfirm.dismiss()
            }
        }

        tvSure?.setOnClickListener {
            if (customDialogConfirm != null) {
                customDialogConfirm.dismiss()
            }
            showLoading()
            mViewModel.cancelOrder("" + xlBean?.id)
        }


    }

}
