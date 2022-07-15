package heyong.intellectPinPang.ui.mine.activity.live

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import heyong.intellectPinPang.ui.BaseActivity
import heyong.handong.framework.base.ResponseBean
import heyong.handong.framework.utils.SystemUtil
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.live.OrderCenterBean
import heyong.intellectPinPang.databinding.ActivityOrderCenterBinding
import heyong.intellectPinPang.live.activity.TeachFilmsActivity
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.utils.MessageDialogBuilder
import heyong.lzy.imagepicker.util.StatusBarUtil

/**
 * 接单员中心
 */
class OrderCenterActivity : BaseActivity<ActivityOrderCenterBinding, LiveViewModel>(),
    View.OnClickListener {


    var orderStatus: String = "3";
    var orderCenterBean: OrderCenterBean? = null
    override fun getLayoutRes(): Int {
        return R.layout.activity_order_center
    }

    override fun onResume() {
        super.onResume()
        mViewModel.orderReceivingCenter()
    }

    override fun initView(savedInstanceState: Bundle?) {
        val version = SystemUtil.getVersion()
        if (version >= 6) {
            StatusBarUtil.statusBarLightMode(this)
            StatusBarUtil.transparencyBar(this) //设置状态栏全透明
        }
        mBinding.listener = this
        mViewModel.orderReceivingCenterData.observe(this, Observer {
            if (it.errorCode.equals("100000-100065") || it.errorCode.equals("100000-100064")) {
                dealData(it)
            } else if (it.errorCode.equals("200")) {
                dealData(it)
            } else {
                toast("" + it.message)
            }

        })
    }

    private fun dealData(it: ResponseBean<OrderCenterBean>) {
        if (it.data != null) {
            var data = it.data
            orderCenterBean = it.data
            var status = data.status
//            1正常2封禁3待审核
            orderStatus = data.status
            //1正常2封禁3待审核4审核未通过5冻结

            mBinding.tvMoney.text = "¥" + data.mayMoney
            if (status.equals("3")) {
                mBinding.llNotJiedan.visibility = View.VISIBLE
                mBinding.llAlreadyJiedan.visibility = View.GONE
                mBinding.tvJiedanStatus.setBackgroundResource(R.drawable.shape_club_blue_15)
                mBinding.tvJiedanStatus.text = "申请成为接单员"
                mBinding.llWallet.visibility = View.GONE
                mBinding.llMyOwnWallet.visibility = View.GONE
            } else if (status.equals("5")) {
                mBinding.llNotJiedan.visibility = View.VISIBLE
                mBinding.llAlreadyJiedan.visibility = View.GONE
                mBinding.tvJiedanStatus.setBackgroundResource(R.drawable.shape_club_orange_15)
                mBinding.tvJiedanStatus.text = "身份冻结中"
                mBinding.llWallet.visibility = View.VISIBLE
                mBinding.llMyOwnWallet.visibility = View.VISIBLE

            } else if (status.equals("1")) {
                mBinding.llNotJiedan.visibility = View.GONE
                mBinding.llAlreadyJiedan.visibility = View.VISIBLE
                mBinding.tvJiedanStatus.setBackgroundResource(R.drawable.shape_club_orange_15)
                mBinding.tvJiedanStatus.text = "接单员设置"
                mBinding.tvUserName.text = "" + data.name
                mBinding.tvTel.text = data.account
                mBinding.tvPlayNum.text = "累计直播：" + data.zhiboCounts + "场"
                mBinding.llWallet.visibility = View.VISIBLE
                mBinding.llMyOwnWallet.visibility = View.VISIBLE


            }


        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.tv_jiedan_status -> {
                //      1正常2封禁3待审核4审核未通过5冻结

                when (orderStatus) {
                    "1" -> {
                        if (orderCenterBean != null) {
                            var id = orderCenterBean?.id
                            var name = orderCenterBean?.name
                            var account = orderCenterBean?.account
                            var deposit = orderCenterBean?.deposit
                            OrderTackerSettingActivity.start(
                                this, "" + id,
                                "" + name, "" + account, "" + deposit,
                            ""+orderCenterBean?.mayMoney
                            )
                        }
                    }
                    "5" -> {
                        //恢复身份
                        startActivity(Intent(this, FreezeOrderTackerActivity::class.java))
                    }
                    "3" -> {
                        //申请成为接单员
                        ClerkOrderApplyActivity.startActivity(this, 0)
                    }
                }
            }
            R.id.iv_finish -> {
                finish()
            }
            R.id.ll_wallet -> {
                if (orderStatus.equals("1")) {
                    startActivity(Intent(this, MyWalletActivity::class.java))
                } else {
                    MessageDialogBuilder(this)
                        .setMessage("")
                        .setTvTitle(
                            "您已解除接单员身份，此功能暂不可用" +
                                    "请回复接单员身份后使用此功能"
                        )
                        .setTvCancel("取消")
                        .setBtnCancleHint(true)
                        .setTvSure("确定")
                        .show()
                }
            }
            R.id.ll_mine_order -> {

                MyLiveOrderActivity.start(this)


            }
            R.id.ll_paishe_jiaoxue -> {
                toast("暂未开放")
//                TeachFilmsActivity.start(this)
            }
        }
    }
}