package heyong.intellectPinPang.live.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import heyong.intellectPinPang.ui.BaseActivity
import heyong.handong.framework.utils.ImageLoader
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.live.XlZhiboOrderPayMentBean
import heyong.intellectPinPang.databinding.ActivityPayLiveOrderResultBinding
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.mine.activity.live.MyLivePublishOrderActivity

/**
 * 支付结果
 */
class PayLiveOrderResultActivity : BaseActivity<ActivityPayLiveOrderResultBinding, LiveViewModel>(),
    View.OnClickListener {
    var orderId: String = ""
    var payType: String = "0"
    var arrangeId:String=""
    var xlzhBean: XlZhiboOrderPayMentBean?=null
    companion object {
        fun startActivity(context: Context, orderId: String, payType: String) {
            val intent = Intent(context, PayLiveOrderResultActivity::class.java)
            intent.putExtra("orderId", orderId)
            intent.putExtra("payType", payType)
            context.startActivity(intent)
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_pay_live_order_result
    }

    override fun onResume() {
        super.onResume()
        mViewModel.xlZhiboSetOrderPayment(orderId)
    }
    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
        orderId = intent.getStringExtra("orderId")
        payType = intent.getStringExtra("payType")

        if (payType == "1") {
            ImageLoader.loadImage(mBinding.payLogo, R.drawable.img_accept_success)
            mBinding.payTextStatus.text = "支付成功"
            mBinding.payReason.visibility = View.GONE
            mBinding.tvReplyPay.text = "返回"
            mBinding.tvReplyPay.setBackgroundResource(R.drawable.shape_order_tack_bg)
        } else {
            mBinding.tvReplyPay.setBackgroundResource(R.drawable.shape_orange_corners_4)
            mBinding.tvReplyPay.text = "重新支付"
            ImageLoader.loadImage(mBinding.payLogo, R.drawable.img_accept_fail)
            mBinding.payTextStatus.text = "支付失败"
            mBinding.payReason.visibility = View.VISIBLE

        }


        mViewModel.xlZhiboSetOrderPaymentData.observe(this, Observer {

            if (it.errorCode.equals("200")) {
                xlzhBean=it.data
                arrangeId=it.data.arrangeId
                mBinding.tvOrderType.text = "订单类型：" + it.data.orderType
                mBinding.tvOrderNo.text = "订单编号：" + it.data.orderNum
                mBinding.tvMoney.text = "订单金额：¥ " + it.data.money
                mBinding.tvXiadanTime.text = "下单时间：" + it.data.orderTime
            } else {
                toast(it.message)
            }


        })


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_reply_pay -> {
                if(payType=="1")
                {
                    finish()
                }else
                {
                    PayLiveOrderActivity.startActivity(this,""+xlzhBean?.money,
                        ""+xlzhBean?.orderType,""+orderId)
                    finish()
                }
            }
            R.id.tv_see_order -> {
                //查看订单
                val intent = Intent(this, MyLivePublishOrderActivity::class.java)
                startActivity(intent)
                finish()

            }
        }
    }
}