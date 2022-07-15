package heyong.intellectPinPang.ui.mine.activity.live

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.live.WithDrawMyInfoBean
import heyong.intellectPinPang.databinding.ActivityBindAccountBinding
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.BaseActivity

/**
 * 绑定账户
 */
class BindAccountActivity : BaseActivity<ActivityBindAccountBinding, LiveViewModel>(),
    View.OnClickListener {
    var id: String = ""
    var bean: WithDrawMyInfoBean? = null

    companion object {
        fun goActivity(context: Context, id: String) {
            val intent = Intent(context, BindAccountActivity::class.java)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        id = intent.getStringExtra("id")
        mViewModel.withdrawalInfo(id)
    }
    override fun getLayoutRes(): Int {
        return R.layout.activity_bind_account
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this


        mViewModel.withdrawalInfoData.observe(this, Observer {
            if (it.data != null) {
                bean = it.data
                if (it.data.isWeChat) {
                    mBinding.tvWxBind.text = "已绑定"
                } else {
                    mBinding.tvWxBind.text = "未绑定"
                }
                if (it.data.isApay) {
                    mBinding.tvZfbBind.text = "已绑定"
                } else {
                    mBinding.tvZfbBind.text = "未绑定"

                }

            }


        })


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_bind_zfb -> {
                //绑定支付宝
                if (bean!!.isApay) {
                    BindPageActivity.goActivity(this,1,0,""+id)
                } else {
                    BindPageActivity.goActivity(this,1,1,""+id)

                }
            }
            R.id.ll_bind_wx -> {
                //绑定微信
                if (bean!!.isWeChat) {
                    BindPageActivity.goActivity(this,0,0,""+id)
                } else {
                    BindPageActivity.goActivity(this,0,1,""+id)
                }
            }
        }
    }


}