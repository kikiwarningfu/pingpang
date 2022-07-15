package heyong.intellectPinPang.live.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.xq.fasterdialog.dialog.CustomDialog
import heyong.intellectPinPang.ui.BaseActivity
import heyong.handong.framework.utils.ImageLoader
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityLiveDetailAboutDetailsBinding
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.competition.activity.EventSignUpActivity
import heyong.intellectPinPang.utils.AntiShakeUtils
import heyong.intellectPinPang.widget.AlertDialog
import java.util.*

/**
 * 订单详情 的 明细表   LiveOrderDetailActivity    LiveOrderDetailAboutDetailActivity
 */
class LiveOrderDetailAboutDetailActivity :
    BaseActivity<ActivityLiveDetailAboutDetailsBinding, LiveViewModel>(),
    View.OnClickListener {
    var id = ""
    var orderStatus = ""

    companion object {
        fun start(context: Context, id: String, orderStatus: String) {
            val intent = Intent(context, LiveOrderDetailAboutDetailActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("orderStatus", orderStatus)
            context.startActivity(intent)
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_live_detail_about_details
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
        id = intent.getStringExtra("id")
        myDialog = AlertDialog(this).builder()

        orderStatus = intent.getStringExtra("orderStatus")

        mViewModel.getXlZhiboJiedanOrder(id)

        mViewModel.getXlZhiboJiedanOrderData.observe(this, Observer {

            if (it.errorCode.equals("200")) {
//                phoneNumber=it.data.xlZhiboSetOrder.acconut
                when (orderStatus) {

                    "2", "3" -> {
                        //订单已取消
                        mBinding.llContainerNormal.visibility = View.VISIBLE
                        mBinding.tvExplainText.visibility = View.GONE
                        mBinding.llUnusualOrder.visibility = View.GONE
                        mBinding.llCancelOrder.visibility = View.VISIBLE
                        mBinding.tvDealTime.text = "订单取消时间：" + it.data.xlZhiboSetOrder.orderTime
                        ImageLoader.loadImage(mBinding.ivLogo, R.drawable.img_order_yiquxiao)
                        mBinding.tvTextStatus.text = "该订单已取消"
                        mBinding.tvTextStatus.setTextColor(Color.parseColor("#4795ED"))

                    }

                    "6" -> {
                        //赏金入账中
                        mBinding.tvExplainText.visibility = View.VISIBLE
                        mBinding.llContainerNormal.visibility = View.VISIBLE
                        mBinding.llUnusualOrder.visibility = View.GONE
                        mBinding.llCancelOrder.visibility = View.GONE
                        mBinding.tvDealTime.text = "订单完成时间:" + it.data.xlZhiboSetOrder.orderTime
                        ImageLoader.loadImage(mBinding.ivLogo, R.drawable.img_ruzhangzhong)
                        mBinding.tvTextStatus.text = "订单已完成，赏金入账中"
                        mBinding.tvTextStatus.setTextColor(Color.parseColor("#FFA400"))
                        mBinding.tvExplainText.text = "赏金会在3个工作日之内，发放到您的钱包中，请耐心等待"
                        mBinding.tvExplainText.setTextColor(Color.parseColor("#999999"))
                    }
                    "7" -> {
                        //异常订单   出来中
                        mBinding.llContainerNormal.visibility = View.GONE
                        mBinding.llUnusualOrder.visibility = View.VISIBLE
                        mBinding.tvReason.text = "" + it.data.xlZhiboSetOrder.reason
                    }
                    "9" -> {//异常订单 审核完成
                        mBinding.tvExplainText.visibility = View.VISIBLE
                        mBinding.llContainerNormal.visibility = View.VISIBLE
                        mBinding.llUnusualOrder.visibility = View.GONE
                        mBinding.llCancelOrder.visibility = View.GONE
                        mBinding.tvDealTime.text = "处理时间:" + it.data.xlZhiboSetOrder.orderTime
                        ImageLoader.loadImage(mBinding.ivLogo, R.drawable.img_order_yichuli)
                        mBinding.tvTextStatus.text = "订单已处理"
                        mBinding.tvTextStatus.setTextColor(Color.parseColor("#4795ED"))
                        mBinding.tvExplainText.text = "您的订单未正常完成，赏金已返还至下单用户账户"
                        mBinding.tvExplainText.setTextColor(Color.parseColor("#999999"))
                    }
                    "8" -> {
                        //赏金已入账
                        mBinding.tvExplainText.visibility = View.VISIBLE
                        mBinding.llContainerNormal.visibility = View.VISIBLE
                        mBinding.llUnusualOrder.visibility = View.GONE
                        mBinding.llCancelOrder.visibility = View.GONE
                        mBinding.tvDealTime.text = "订单完成时间:" + it.data.xlZhiboSetOrder.orderTime
                        ImageLoader.loadImage(mBinding.ivLogo, R.drawable.img_ruzhang_chenggong)
                        mBinding.tvTextStatus.text = "入账成功"
                        mBinding.tvTextStatus.setTextColor(Color.parseColor("#00CB7A"))
                        mBinding.tvExplainText.text = "您的赏金已入账，请注意查收"
                        mBinding.tvExplainText.setTextColor(Color.parseColor("#999999"))
                    }

                }


            } else {
                toast(it.message)
            }


        })


    }

    var customDialogConfirm: CustomDialog? = null

    @RequiresApi(Build.VERSION_CODES.M)
    private fun callPhoneDialog() {
        customDialogConfirm = CustomDialog(this)
        customDialogConfirm!!.setCustomView(R.layout.pop_call_phone)
        customDialogConfirm!!.show()
        val customView: View = customDialogConfirm!!.getCustomView()
        showAskConfirmView(customView, customDialogConfirm)
    }

    val CALL_PHONE = 1002 //Call permission code
    var phoneNumber = "";

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showAskConfirmView(customView: View, customDialog: CustomDialog?) {
        val tvCancel = customView.findViewById<TextView>(R.id.tv_cancel)
        val llCallPhone = customView.findViewById<LinearLayout>(R.id.ll_call_phone)
        val tvCallNumber = customView.findViewById<TextView>(R.id.tv_exit_club)
        tvCallNumber.text = "呼叫 $phoneNumber"
        llCallPhone.setOnClickListener(View.OnClickListener { v ->
            if (AntiShakeUtils.isInvalidClick(v)) return@OnClickListener
            //                toast("拨打电话");
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), CALL_PHONE)
            } else {
                callPhone("" + phoneNumber)
                customDialog?.dismiss()
            }
        })
        tvCancel.setOnClickListener(View.OnClickListener { v ->
            if (AntiShakeUtils.isInvalidClick(v)) return@OnClickListener
            customDialog?.dismiss()
        })
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    fun callPhone(phoneNum: String) {
        val intent = Intent(Intent.ACTION_CALL)
        val data = Uri.parse("tel:$phoneNum")
        intent.data = data
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_tel -> {
                if (AntiShakeUtils.isInvalidClick(v!!)) return
                if (TextUtils.isEmpty(phoneNumber)) {
                    toast("呼叫电话不能为空")
                    return
                } else {
                    callPhoneDialog()
                }
            }
        }
    }
    private var myDialog //弹窗
            : AlertDialog? = null

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == EventSignUpActivity.CALL_PHONE) {
            for (grant in grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    if (customDialogConfirm != null) {
                        customDialogConfirm!!.dismiss()
                    }
                    EventSignUpActivity.showMissingDialog(myDialog, this)
                } else {
                    callPhone("" + phoneNumber)
                    if (customDialogConfirm != null) {
                        customDialogConfirm!!.dismiss()
                    }
                }
            }
        }
    }

}