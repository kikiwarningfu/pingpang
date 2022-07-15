package heyong.intellectPinPang.live.activity

import android.os.Bundle
import android.view.View
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityLiveTeachResultBinding
import heyong.intellectPinPang.live.model.LiveViewModel

/**
 * 试拍认证
 */
class LiveTeachResultActivity : BaseActivity<ActivityLiveTeachResultBinding, LiveViewModel>() {
    var inputType = 0;
    override fun getLayoutRes(): Int {

        return R.layout.activity_live_teach_result
    }

    override fun initView(savedInstanceState: Bundle?) {

        when (inputType) {
            //审核中
            1 -> {
                mBinding.llAudit.visibility = View.VISIBLE
                mBinding.llRealFail.visibility = View.GONE
            }
            //审核成功
            2 -> {
                mBinding.llAudit.visibility = View.GONE
                mBinding.llRealFail.visibility = View.VISIBLE
                mBinding.llSuccess.visibility = View.VISIBLE
                mBinding.llFail.visibility = View.GONE
                mBinding.tvSubmit.visibility = View.GONE
            }
            //审核失败
            3 -> {
                mBinding.llAudit.visibility = View.GONE
                mBinding.llRealFail.visibility = View.VISIBLE
                mBinding.llSuccess.visibility = View.GONE
                mBinding.llFail.visibility = View.VISIBLE
                mBinding.tvSubmit.visibility = View.VISIBLE
            }
        }
    }
}