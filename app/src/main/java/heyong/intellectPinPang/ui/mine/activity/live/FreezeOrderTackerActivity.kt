package heyong.intellectPinPang.ui.mine.activity.live

import android.os.Bundle
import android.view.View
import heyong.intellectPinPang.ui.BaseActivity
import heyong.handong.framework.base.BaseViewModel
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityFreezeOrderTackerBinding

/**
 * 解除接单员身份
 */
class FreezeOrderTackerActivity:
    BaseActivity<ActivityFreezeOrderTackerBinding, BaseViewModel>(),
    View.OnClickListener {
    override fun getLayoutRes(): Int {
        return R.layout.activity_freeze_order_tacker
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener=this
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.tv_submit->{
                ClerkOrderApplyActivity.startActivity(this,1)
                finish()
            }
        }

    }
}