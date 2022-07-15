package heyong.intellectPinPang.ui.mine.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import heyong.handong.framework.account.AccountHelper
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityAccountNumberSecurityBinding
import heyong.intellectPinPang.ui.login.activity.SetPasswordActivity
import heyong.intellectPinPang.ui.mine.MineViewModel
import heyong.intellectPinPang.utils.AntiShakeUtils

/**
 * 账号和安全
 */
class AccountNumberSecurityKotlinActivity:
    BaseActivity<ActivityAccountNumberSecurityBinding, MineViewModel>(),
    View.OnClickListener {

    override fun onResume() {
        super.onResume()
        mViewModel.myBaseInfo()
    }
    override fun onClick(v: View?) {
        when(v?.id)
        {
            //绑定手机号
            R.id.ll_bind_phone->
                bindPhone(v)
            //跳转到登录密码界面
            R.id.login_password->
                loginPassword(v)
        }
    }

    private fun loginPassword(v: View) {
        if (AntiShakeUtils.isInvalidClick(v))
            return
        goActivity(SetPasswordActivity::class.java)

    }

    private fun bindPhone(v:View) {
        if (AntiShakeUtils.isInvalidClick(v))
            return
        goActivity(AlReadyBindPhoneActivity::class.java)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener=this
        mViewModel.myBaseInfoLiveData.observe(this, Observer {
            if(it.data!=null)
            {
                AccountHelper.setTelephone(""+it.data.account)
                mBinding.tvTel.text=it.data.account
            }
        })
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_account_number_security
    }
}