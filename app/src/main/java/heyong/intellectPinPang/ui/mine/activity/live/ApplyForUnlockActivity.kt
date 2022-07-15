package heyong.intellectPinPang.ui.mine.activity.live

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.xq.fasterdialog.dialog.CustomDialog
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityApplyForUnlockBinding
import heyong.intellectPinPang.databinding.ActivityLiveEndBinding
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.ui.competition.activity.EventSignUpActivity
import heyong.intellectPinPang.utils.AntiShakeUtils
import heyong.intellectPinPang.widget.AlertDialog

class ApplyForUnlockActivity : BaseActivity<ActivityApplyForUnlockBinding, LiveViewModel>(),
    View.OnClickListener {
    val CALL_PHONE = 1002 //Call permission code
    var phoneNumber = "";


    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
        myDialog = AlertDialog(this).builder()

    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_apply_for_unlock
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_connect_tell -> {

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

    var customDialogConfirm: CustomDialog? = null

    @RequiresApi(Build.VERSION_CODES.M)
    private fun callPhoneDialog() {
        customDialogConfirm = CustomDialog(this)
        customDialogConfirm!!.setCustomView(R.layout.pop_call_phone)
        customDialogConfirm!!.show()
        val customView: View = customDialogConfirm!!.getCustomView()
        showAskConfirmView(customView, customDialogConfirm)
    }


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