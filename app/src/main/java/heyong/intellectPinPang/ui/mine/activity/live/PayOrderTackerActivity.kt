package heyong.intellectPinPang.ui.mine.activity.live

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import heyong.intellectPinPang.ui.BaseActivity
import heyong.handong.framework.utils.ImageLoader
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityPayOrderTackerBinding
import heyong.intellectPinPang.factory.PayFactory
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.wxapi.WXPayEntryActivity

/**
 * 接单人支付页面
 */
class PayOrderTackerActivity : BaseActivity<ActivityPayOrderTackerBinding, LiveViewModel>(),
    View.OnClickListener, WXPayEntryActivity.WXListener {
    var id: String = ""

    companion object {

        fun startActivity(context: Context, id: String) {
            val intent = Intent(context, PayOrderTackerActivity::class.java)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }
    }

    var payType: Int = 0;
    override fun getLayoutRes(): Int {
        return R.layout.activity_pay_order_tacker

    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
        id = intent.getStringExtra("id")
        WXPayEntryActivity.setWXListener(this)

        mViewModel.payYaJinWxData.observe(this, Observer {
            dismissLoading()
            if (it.errorCode.equals("200")) {
                val payFactory = PayFactory()
                payFactory.WxPay(this, it.data)
            } else {
                toast("" + it.message)
            }

        })
        mViewModel.payYaJinZfbData.observe(this, Observer {
            dismissLoading()
            if (it.errorCode.equals("200")) {
                val payFactory = PayFactory()
                payFactory.AliPay(it.data as String, this) { s, isOk ->
                    if (isOk) {
                        toast("支付成功")
                        finish()

                    } else {

                        toast("" + s)
                    }
                }
            } else {
                toast("" + it.message)
            }
        })


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_submit -> {
                if (payType == 0) {
                    toast("请选择支付方式")
                } else {
                    showLoading()
                    mViewModel.payYaJin(id, "" + payType)
                }
            }
            R.id.rl_wx -> {
                payType = 1
                ImageLoader.loadImage(mBinding.loWx, R.drawable.img_pay_select)
                ImageLoader.loadImage(mBinding.loZfb, R.drawable.img_pay_no_select)
            }
            R.id.rl_zfb -> {
                payType = 2
                ImageLoader.loadImage(mBinding.loWx, R.drawable.img_pay_no_select)
                ImageLoader.loadImage(mBinding.loZfb, R.drawable.img_pay_select)
            }
        }
    }

    override fun wxCallBack(code: Int) {
        when (code) {
            0 -> {
                toast("支付成功")
                finish()
            }
            -1 -> {
                toast("支付异常")
            }
            2 -> {
                toast("用户取消")
            }
        }
    }
}