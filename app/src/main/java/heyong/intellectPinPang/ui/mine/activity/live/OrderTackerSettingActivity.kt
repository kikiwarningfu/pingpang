package heyong.intellectPinPang.ui.mine.activity.live

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityOrderTackerSettingBinding
import heyong.intellectPinPang.live.model.LiveViewModel

/**
 * 接单员设置页面
 */
class OrderTackerSettingActivity : BaseActivity<ActivityOrderTackerSettingBinding, LiveViewModel>(),
    View.OnClickListener {

    companion object {

        var instance: OrderTackerSettingActivity? = null
        fun start(
            context: Context,
            id: String,
            name: String,
            account: String,
            deposit: String,
            maymoney: String
        ) {
            val intent = Intent(context, OrderTackerSettingActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("name", name)
            intent.putExtra("account", account)
            intent.putExtra("deposit", deposit)
            intent.putExtra("maymoney", maymoney)
            context.startActivity(intent)
        }
    }

    var id = ""
    var name = ""
    var account = ""
    var deposit = ""
    var maymoney = ""

    override fun getLayoutRes(): Int {
        return R.layout.activity_order_tacker_setting
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
        instance = this
        id = intent.getStringExtra("id")
        name = intent.getStringExtra("name")
        account = intent.getStringExtra("account")
        deposit = intent.getStringExtra("deposit")
        maymoney = intent.getStringExtra("maymoney")
        mBinding.tvNickName.text = name
        mBinding.tvTel.text = account
        mBinding.tvCashPledge.text = "押金（元）：¥ " + deposit


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_unbind_identify -> {
                RemoveOrderTackerActivity.start(this, "" + id, "" + deposit, "" + maymoney)
            }
        }
    }


}