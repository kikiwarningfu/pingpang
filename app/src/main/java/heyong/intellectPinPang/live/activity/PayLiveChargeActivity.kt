package heyong.intellectPinPang.live.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import heyong.handong.framework.utils.ImageLoader
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityPayLiveChargeBinding
import heyong.intellectPinPang.event.SwipMessageEvent
import heyong.intellectPinPang.factory.PayFactory
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.wxapi.WXPayEntryActivity
import org.greenrobot.eventbus.EventBus

class PayLiveChargeActivity : BaseActivity<ActivityPayLiveChargeBinding, LiveViewModel>(),
    View.OnClickListener, WXPayEntryActivity.WXListener {
    var payType: Int = 0;
    var arragneId: String = ""

    companion object {
        fun startActivity(context: Context, id: String) {
            val intent = Intent(context, PayLiveChargeActivity::class.java)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_pay_live_charge;
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
        arragneId = intent.getStringExtra("id")
        WXPayEntryActivity.setWXListener(this)

        mViewModel.buyCompleteVideoWxData.observe(this, Observer {
            dismissLoading()
            if (it.errorCode.equals("200")) {
                val payFactory = PayFactory()
                payFactory.WxPay(this, it.data)

            } else {
                toast("" + it.message)
            }

        })

        mViewModel.buyCompleteVideoZFBData.observe(this, Observer {

            dismissLoading()
            if (it.errorCode.equals("200")) {
                val payFactory = PayFactory()
                payFactory.AliPay(it.data as String, this) { s, isOk ->
                    if (isOk) {
                        EventBus.getDefault().post(SwipMessageEvent())
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

    fun clearView() {
        ImageLoader.loadImage(mBinding.loWx, R.drawable.img_pay_no_select)
        ImageLoader.loadImage(mBinding.loZfb, R.drawable.img_pay_no_select)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_submit -> {

                if (payType == 0) {
                    toast("请选择支付方式")
                } else {
                    showLoading()
                    mViewModel.buyCompleteVideo(arragneId, "" + payType)
                }
            }
            R.id.rl_wx -> {
                clearView();
                payType = 1
                ImageLoader.loadImage(mBinding.loWx, R.drawable.img_pay_select)
                ImageLoader.loadImage(mBinding.loZfb, R.drawable.img_pay_no_select)
            }
            R.id.rl_zfb -> {
                clearView();
                payType = 2
                ImageLoader.loadImage(mBinding.loWx, R.drawable.img_pay_no_select)
                ImageLoader.loadImage(mBinding.loZfb, R.drawable.img_pay_select)
            }
        }

    }

    override fun wxCallBack(code: Int) {
        when (code) {
            0 -> {
                EventBus.getDefault().post(SwipMessageEvent())
                finish()
            }
            -1 -> {
                toast("支付异常");
            }
            -2 -> {
                toast("用户取消");
            }
        }
    }

}