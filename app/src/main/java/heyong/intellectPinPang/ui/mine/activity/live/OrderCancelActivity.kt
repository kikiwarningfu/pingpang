package heyong.intellectPinPang.ui.mine.activity.live

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import heyong.handong.framework.utils.SystemUtil
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.live.XLzhiboSetOrderInfoBean
import heyong.intellectPinPang.databinding.ActivityOrderCancelBinding
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.BaseActivity
import heyong.lzy.imagepicker.util.StatusBarUtil

class OrderCancelActivity : BaseActivity<ActivityOrderCancelBinding, LiveViewModel>(),
    View.OnClickListener {
    var id = ""
    var xlBean: XLzhiboSetOrderInfoBean? = null

    companion object {
        fun startActivity(context: Context, xlBean: XLzhiboSetOrderInfoBean) {
            val intent = Intent(context, OrderCancelActivity::class.java)
            intent.putExtra("xlBean", xlBean)
            context.startActivity(intent)
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_order_cancel
    }

    override fun initView(savedInstanceState: Bundle?) {
        xlBean = intent.getSerializableExtra("xlBean") as XLzhiboSetOrderInfoBean
        mBinding.listener = this
        id = "" + xlBean?.id
        val version = SystemUtil.getVersion()
        if (version >= 6) {
            StatusBarUtil.statusBarLightMode(this)
            StatusBarUtil.transparencyBar(this) //设置状态栏全透明
        }

//        mBinding.tvOrderType.text = xlBean?.orderType
        mBinding.tvOrderMoney.text = "¥  " + xlBean?.money
        mBinding.tvCreateTime.text = xlBean?.orderTime
        mBinding.tvOrderNo.text = xlBean?.orderNum
        mBinding.tvCancelOrderTime.text = xlBean?.updateTime



        if (xlBean?.freeType.equals("1")) {
            mBinding.tvPayWay.text = "微信支付"
        } else {
            mBinding.tvPayWay.text = "支付宝支付"
        }


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
            R.id.iv_finish->{
                finish()
            }
        }
    }
}