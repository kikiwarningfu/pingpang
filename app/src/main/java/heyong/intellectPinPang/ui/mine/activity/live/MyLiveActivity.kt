package heyong.intellectPinPang.ui.mine.activity.live

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import heyong.handong.framework.utils.SystemUtil
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityMyLiveBinding
import heyong.intellectPinPang.live.activity.MyVideoLibraryActivity
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.utils.MessageDialogBuilder
import heyong.lzy.imagepicker.util.StatusBarUtil

/**
 * 我的直播界面
 */
class MyLiveActivity : BaseActivity<ActivityMyLiveBinding?, LiveViewModel?>(),
    View.OnClickListener {
    var orderStatus: String = "3";

    override fun onResume() {
        super.onResume()
        mViewModel?.orderReceivingCenter()
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_my_live
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding!!.listener = this
        //白底黑字
//        ToastUtils.showToast(getActivity(), "版本2");
        val version = SystemUtil.getVersion()
        if (version >= 6) {
            StatusBarUtil.statusBarLightMode(this)
            StatusBarUtil.transparencyBar(this) //设置状态栏全透明
        }
        mViewModel?.orderReceivingCenterData?.observe(this, Observer {
            if (it.errorCode.equals("100000-100065") || it.errorCode.equals("100000-100064")) {
                if (it.data != null) {
                    var data = it.data
//            1正常2封禁3待审核
                    orderStatus = data.status
                }
            } else if (it.errorCode.equals("200")) {
                var data = it.data
                //  1正常2封禁3待审核
                orderStatus = data.status
            } else {
                toast("" + it.message)
            }
            if (orderStatus.equals("2")) {
                mBinding?.tvFreezing?.visibility = View.VISIBLE
            } else {
                mBinding?.tvFreezing?.visibility = View.GONE
            }

        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_jiedan_center -> {
                //1正常2封禁3待审核4审核未通过5冻结
                if (orderStatus.equals("2")) {
                    MessageDialogBuilder(this)
                        .setMessage(
                            "您的找好已被管理员封禁，您若想继续" +
                                    "使用此功能，可以提起申诉"
                        )
                        .setTvTitle("通知")
                        .setCancelAble(false)
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setTvSure("申请解封")
                        .setSureListener {
                            goActivity(ApplyForUnlockActivity::class.java)
                        }
                        .show()
                } else {
                    //接单员中心
                    val intent = Intent(this, OrderCenterActivity::class.java)
                    startActivity(intent)
                }
            }
            //直播订单
            R.id.ll_jiequ_order -> {
                val intent = Intent(this, MyLivePublishOrderActivity::class.java)
                startActivity(intent)
            }
            //视频库
            R.id.ll_my_self_order -> {
                toast("暂未开放")
//                val intent = Intent(this, MyVideoLibraryActivity::class.java)
//                startActivity(intent)
            }
            R.id.iv_finish -> {
                finish()
            }
        }
    }
}