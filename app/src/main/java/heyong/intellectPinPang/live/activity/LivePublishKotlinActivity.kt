package heyong.intellectPinPang.live.activity

import android.os.Bundle
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityLiveBinding
import heyong.intellectPinPang.live.model.LiveViewModel

class LivePublishKotlinActivity : BaseActivity<ActivityLiveBinding, LiveViewModel>()
{

    override fun initView(savedInstanceState: Bundle?) {


    }

    override fun getLayoutRes(): Int {
       return R.layout.activity_live
    }

}