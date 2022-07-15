package heyong.intellectPinPang.ui.mine.activity.live

import android.os.Bundle
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityWithDrawZfbBinding
import heyong.intellectPinPang.live.model.LiveViewModel

/**
 * 提现到支付宝
 */
class WithDrawZFBActivity:
    BaseActivity<ActivityWithDrawZfbBinding, LiveViewModel>() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_with_draw_zfb
    }

    override fun initView(savedInstanceState: Bundle?) {



    }
}