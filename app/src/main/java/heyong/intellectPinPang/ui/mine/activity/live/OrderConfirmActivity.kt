package heyong.intellectPinPang.ui.mine.activity.live

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import heyong.handong.framework.utils.SystemUtil
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.live.XLzhiboSetOrderInfoBean
import heyong.intellectPinPang.databinding.ActivityOrderConfirmBinding
import heyong.intellectPinPang.live.activity.LivePublishOrderDetailActivity
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.ui.mine.event.OrderStatusChangeEvent
import heyong.lzy.imagepicker.util.StatusBarUtil
import org.greenrobot.eventbus.EventBus

/**
 * 订单已确认   百分之2手续费
 */
class OrderConfirmActivity : BaseActivity<ActivityOrderConfirmBinding, LiveViewModel>(),
    View.OnClickListener {
    var id = ""
    var xlBean: XLzhiboSetOrderInfoBean? = null

    companion object {
        fun startActivity(context: Context, xlBean: XLzhiboSetOrderInfoBean) {
            val intent = Intent(context, OrderConfirmActivity::class.java)
            intent.putExtra("xlBean", xlBean)
            context.startActivity(intent)
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_order_confirm
    }

    override fun initView(savedInstanceState: Bundle?) {
        xlBean = intent.getSerializableExtra("xlBean") as XLzhiboSetOrderInfoBean
        mBinding.listener = this
        id = "" + xlBean?.id
//        mBinding.tvOrderType.text = xlBean?.orderType
        val version = SystemUtil.getVersion()
        if (version >= 6) {
            StatusBarUtil.statusBarLightMode(this)
            StatusBarUtil.transparencyBar(this) //设置状态栏全透明
        }

        mBinding.tvOrderMoney.text = "¥  " + xlBean?.money
        mBinding.tvCreateTime.text = xlBean?.orderTime
        mBinding.tvOrderNo.text = xlBean?.orderNum
        if (xlBean?.freeType.equals("1")) {
            mBinding.tvPayWay.text = "微信支付"
        } else {
            mBinding.tvPayWay.text = "支付宝支付"
        }


        mViewModel.cancelOrderData.observe(this, Observer {
            dismissLoading()
            if (it.errorCode.equals("200")) {
                toast("" + it.message)
                //刷新 订单列表的数据
                EventBus.getDefault().post(OrderStatusChangeEvent())
                LivePublishOrderDetailActivity.instance?.finish()
                finish()
            } else {
                toast(it.message)
            }

        })


    }

    override fun onClick(v: View?) {
        when (v?.id) {
//            R.id.tv_cancel_order -> {
//                showLoading()
//                mViewModel.cancelOrder("" + id)
//            }
            R.id.tv_submit -> {
                finish()
            }
            R.id.iv_finish -> {
                finish()
            }
        }
    }
}