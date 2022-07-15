package heyong.intellectPinPang.ui.mine.activity.live

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityRemoveOrderTackBinding
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.utils.MessageDialogBuilder

/**
 * 解除接单员身份
 */
class RemoveOrderTackerActivity : BaseActivity<ActivityRemoveOrderTackBinding, LiveViewModel>(),
    View.OnClickListener {

    companion object {

        fun start(context: Context, id: String, money: String,maymoney:String) {
            val intent = Intent(context, RemoveOrderTackerActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("money", money)
            intent.putExtra("maymoney", maymoney)
            context.startActivity(intent)
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_remove_order_tack
    }

    var id: String = ""
    var money: String = ""
    var maymoney: String = ""
    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
        id = intent.getStringExtra("id")
        money = intent.getStringExtra("money")
        maymoney = intent.getStringExtra("maymoney")
        mBinding.llSubmitExplain.visibility = View.VISIBLE
        mBinding.llContainerResult.visibility = View.GONE
        mBinding.tvCashPledge.text = "¥ " + money

        mViewModel.relieveJiedanRoleData.observe(this, Observer {
            //添加两个状态
//            当有未完成订单时：不能直接解除接单员身份，弹窗提示“您有未完成的订单，不能解除，请先解除接单员身份”
//            账户有未提现余额时：提示“您当前账户有未体现余额，解除身份则不可以完成，是否解除”，点是正常解除


//            您有未完成的订单，暂不可解除身份，
//            请先完成或取消订单


            //您当前账户中有未提现余额，解除身份
            //则不可以完成，是否解除？
            if (it.errorCode.equals("200")) {
                MessageDialogBuilder(this)
                    .setMessage("")
                    .setTvTitle(
                        "您已成功提交解除接单员身份申请，届时您的" +
                                "接单员身份将被冻结，我们会在1～3个工作日" +
                                "之内退回您的押金"
                    )
                    .setTvCancel("取消")
                    .setBtnCancleHint(true)
                    .setTvSure("好的")
                    .setSureListener { v1: View? ->
                        //请求接口
                        dealData();
                    }
                    .show()
                //您有未完成的订单，不能解除
            } else if (it.errorCode.equals("100000-100074")) {
                MessageDialogBuilder(this)
                    .setMessage("")
                    .setTvTitle(
                        "您有未完成的订单，暂不可解除身份，" +
                                "请先完成或取消订单"
                    )
                    .setTvCancel("取消")
                    .setBtnCancleHint(true)
                    .setTvSure("确定")
                    .setSureListener { v1: View? ->
                        //请求接口

                    }
                    .show()
            } else if (it.errorCode.equals("100000-100075"))
            {

            }else{
                toast("" + it.message)
            }

        })


    }


    private fun dealData() {
        mBinding.llSubmitExplain.visibility = View.GONE
        mBinding.llContainerResult.visibility = View.VISIBLE
        OrderTackerSettingActivity.instance?.finish()
        mBinding.tvSubmit.visibility = View.GONE

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_submit -> {
                var eidiText = mBinding.etExplain.text.toString()
                if (TextUtils.isEmpty(eidiText)) {
                    toast("请填写解除原因")
                } else {
                    var toDouble = maymoney.toDouble()

                    if(toDouble>0)
                    {
                        MessageDialogBuilder(this)
                            .setMessage("")
                            .setTvTitle(
                                "系统检测到您有未提现余额，解除身份后，账户将冻结，确认解除？"
                            )
                            .setTvCancel("取消")
                            .setBtnCancleHint(false)
                            .setTvSure("确定")
                            .setSureListener { v1: View? ->
                                //请求接口
                                mViewModel.relieveJiedanRole("" + id, "" + eidiText)
                            }
                    }else{
                        MessageDialogBuilder(this)
                            .setMessage("")
                            .setTvTitle("是否提交申请？")
                            .setTvCancel("取消")
                            .setBtnCancleHint(false)
                            .setTvSure("确定")
                            .setSureListener { v1: View? ->
                                //请求接口
                                mViewModel.relieveJiedanRole("" + id, "" + eidiText)
                            }
                            .show()
                    }

                }
            }
        }
    }
}