package heyong.intellectPinPang.ui.mine.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer

import heyong.handong.framework.account.AccountHelper
import heyong.intellectPinPang.ui.BaseActivity
import heyong.handongkeji.autoupdata.CheckVersion
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.homepage.UpdateBean
import heyong.intellectPinPang.databinding.ActivityAboutUsBinding
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel
import heyong.intellectPinPang.utils.AntiShakeUtils
import heyong.intellectPinPang.utils.CommonUtilis
import heyong.intellectPinPang.utils.MessageDialogBuilder
import heyong.intellectPinPang.widget.popupwindow.CommonPopupWindow

/**
 * 关于我们
 */
class AboutUsKotlinActivity : BaseActivity<ActivityAboutUsBinding, HomePageViewModel>(),
    View.OnClickListener {

    private var mShowUpdateWindow: CommonPopupWindow? = null

    private// 获取packagemanager的实例
    // getPackageName()是你当前类的包名，0代表是获取版本信息
    val versionCode: Int
        @Throws(Exception::class)
        get() {
            val packageManager = packageManager
            val packInfo = packageManager.getPackageInfo(packageName, 0)
            return packInfo.versionCode
        }

    override fun getLayoutRes(): Int {
        return R.layout.activity_about_us
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
//        val localVersion = CommonUtilis.getLocalVersion(this)
        val localVersionName = CommonUtilis.getLocalVersionName(this)
        //        tv_version_date
        mBinding.tvVersionDate.text = "版本：$localVersionName"
        mViewModel.updateLiveData.observe(this,
            Observer { bean ->
                if (bean.errorCode == "200") {
                    if (bean.data != null) {
                        if (mShowUpdateWindow != null && mShowUpdateWindow!!.isShowing) {
                            AccountHelper.setIsNeedUpdate("")
                        } else {
                            val versionBean = bean.data
                            if (versionBean != null) {
                                val versionCode = versionBean.code//版本号
                                val isF = versionBean.usable // 是否强制更新  1 为 强制
                                //                                String versionName = versionBean.getName(); // 版本名称
                                val downUrl = versionBean.downloadUrl
                                val upInfo = versionBean.description
                                //                                mBinding.tvVersionDate.setText("版本：" + versionCode);
                                var mAppVersionCode = 0
                                try {
                                    mAppVersionCode = getVersionCode()!!
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }

                                val isNeedUpdate = AccountHelper.getIsNeedUpdate()
                                if (isNeedUpdate == "true") {
                                    if (mAppVersionCode < Integer.valueOf(versionCode)) {
                                        showUpdateWindow(bean.data)
                                    } else if (mAppVersionCode == Integer.valueOf(versionCode)) {
                                        MessageDialogBuilder(this)
                                            .setMessage("")
                                            .setTvTitle("当前已经是最新")
                                            .setTvCancel("取消")
                                            .setBtnCancleHint(true)
                                            .setTvSure("确定")
                                            .show()
                                    }
                                } else if (TextUtils.isEmpty(isNeedUpdate)) {
                                    if (mAppVersionCode < Integer.valueOf(versionCode)) {
                                        showUpdateWindow(bean.data)
                                    } else if (mAppVersionCode == Integer.valueOf(versionCode)) {
                                        MessageDialogBuilder(this)
                                            .setMessage("")
                                            .setTvTitle("当前已经是最新")
                                            .setTvCancel("取消")
                                            .setBtnCancleHint(true)
                                            .setTvSure("确定")
                                            .show()
                                    }
                                }
                            }
                        }
                    } else {
                        MessageDialogBuilder(this)
                            .setMessage("")
                            .setTvTitle("当前已经是最新")
                            .setTvCancel("取消")
                            .setBtnCancleHint(true)
                            .setTvSure("确定")
                            .show()
                    }
                }
            })

    }


    @Throws(Exception::class)
    private fun getVersionCode(): Int? {
        // 获取packagemanager的实例
        val packageManager = packageManager
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        val packInfo = packageManager.getPackageInfo(packageName, 0)
        return packInfo.versionCode
    }

    /**
     * 版本更新window
     */
    fun showUpdateWindow(datas: UpdateBean) {
        dismissLoading()
        mShowUpdateWindow = CommonPopupWindow.Builder(this)
            .setView(R.layout.pop_update_window)
            .setBackGroundLevel(0.5f)
            .setViewOnclickListener(CommonPopupWindow.ViewInterface { view, layoutResId ->
                if (AntiShakeUtils.isInvalidClick(view)) {
                    return@ViewInterface
                }
                val tvCancel = view.findViewById<TextView>(R.id.no)
                val tvSure = view.findViewById<TextView>(R.id.yes)
                val isF = datas.usable
                val tvMessage = view.findViewById<TextView>(R.id.message)
                tvMessage.text = datas.description
                if (TextUtils.equals(isF, "1")) {
                    val viewDivider = view.findViewById<View>(R.id.view_divider)
                    viewDivider.visibility = View.GONE
                    tvCancel.visibility = View.GONE
                }
                tvCancel.setOnClickListener {
                    if (AntiShakeUtils.isInvalidClick(view)) {
                        return@setOnClickListener
                    }
                    mShowUpdateWindow!!.dismiss()
                    AccountHelper.setIsNeedUpdate("")
                }
                tvSure.setOnClickListener {
                    if (AntiShakeUtils.isInvalidClick(view)) {
                        return@setOnClickListener
                    }
                    toast("开始下载")
                    CheckVersion.updateApp(datas.downloadUrl, this)
                    mShowUpdateWindow!!.dismiss()
                    AccountHelper.setIsNeedUpdate("")
                }

            })
            //设置外层不可点击
            .setOutsideTouchable(true)
            .setWidthAndHeight(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            .create()
        val view1 = View.inflate(this, R.layout.activity_main, null)
        val isF = datas.usable
        if (TextUtils.equals(isF, IS_UPDATE)) {
            mShowUpdateWindow!!.isOutsideTouchable = false
        } else {
            mShowUpdateWindow!!.isOutsideTouchable = true
        }
        if (mShowUpdateWindow == null || !mShowUpdateWindow!!.isShowing) {
            assert(mShowUpdateWindow != null)
            mShowUpdateWindow!!.isFocusable = false
            mShowUpdateWindow!!.showAtLocation(view1, Gravity.CENTER, 0, 0)
            AccountHelper.setIsNeedUpdate("")
        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_version_update//版本更新
            -> {
                if (AntiShakeUtils.isInvalidClick(v)) {
                    return
                }
                //请求接口
                mViewModel.version()//版本更新
            }
            else -> {
            }
        }
    }

    companion object {
        val TAG = AboutUsKotlinActivity::class.java.simpleName
        val IS_UPDATE = "1"
    }
}