package heyong.intellectPinPang.live.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import heyong.intellectPinPang.ui.BaseActivity
import heyong.handong.framework.utils.ImageLoader
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityPayLiveOrderBinding
import heyong.intellectPinPang.factory.PayFactory
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.wxapi.WXPayEntryActivity

/**
 * 支付
 */
class PayLiveOrderActivity : BaseActivity<ActivityPayLiveOrderBinding, LiveViewModel>(),
    View.OnClickListener, WXPayEntryActivity.WXListener {

    var strMoney: String = ""
    var payOrderType: String = ""
    var orderId: String = ""
    var inputType: Int = 0

    companion object {
        fun startActivity(context: Context, money: String, payOrderType: String, orderId: String) {
            val intent = Intent(context, PayLiveOrderActivity::class.java)
            intent.putExtra("money", money)
            intent.putExtra("payOrderType", payOrderType)
            intent.putExtra("orderId", orderId)
            context.startActivity(intent)
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_pay_live_order
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
        strMoney = intent.getStringExtra("money")
        payOrderType = intent.getStringExtra("payOrderType")
        orderId = intent.getStringExtra("orderId")
        WXPayEntryActivity.setWXListener(this)
//
//       PayLiveOrderResultActivity
        if (payOrderType != null && !TextUtils.isEmpty(payOrderType)) {
            mBinding.tvPayOrderType.text = payOrderType
        }
        mBinding.tvMoney.text = "￥" + strMoney + "元"
        mViewModel.zhifuOrderWXData.observe(this, Observer {
            dismissLoading()
            if (it.errorCode.equals("200")) {
                val payFactory = PayFactory()
                payFactory.WxPay(this, it.data)
            } else {
                toast("" + it.message)
            }

        })

        mViewModel.zhifuOrderZFBData.observe(this, Observer {

            dismissLoading()
            if (it.errorCode.equals("200")) {
                val payFactory = PayFactory()
                payFactory.AliPay(it.data as String, this) { s, isOk ->
                    var payType = "0"
                    if (isOk) {
                        payType = "1"
                    } else {
                        payType = "0"
                    }
                    PayLiveOrderResultActivity.startActivity(this, orderId, payType)
                    finish()
                }
            } else {
                toast("" + it.message)
            }
        })


    }

    fun clearView() {
        ImageLoader.loadImage(mBinding.loWx, R.drawable.img_pay_no_select)
        ImageLoader.loadImage(mBinding.loZfb, R.drawable.img_pay_no_select)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_wx -> {
                clearView();
                inputType = 1
                ImageLoader.loadImage(mBinding.loWx, R.drawable.img_pay_select)
            }
            R.id.rl_zfb -> {
                clearView();
                inputType = 2
                ImageLoader.loadImage(mBinding.loZfb, R.drawable.img_pay_select)


            }
            R.id.tv_submit -> {
                if (inputType == 0) {
                    toast("请选择支付方式")
                    return
                }

                if (inputType == 1) {
                    mViewModel.zhifuOrderWX("" + orderId, "" + inputType)
                } else {
                    mViewModel.zhifuOrderZFB("" + orderId, "" + inputType)
                }


            }
        }
    }

    override fun wxCallBack(code: Int) {
        when (code) {
            0 -> {
                PayLiveOrderResultActivity.startActivity(this, orderId, "1")
                finish()
            }
            else -> {
                PayLiveOrderResultActivity.startActivity(this, orderId, "0")
                finish()
            }

        }

    }
}