package heyong.intellectPinPang.ui.mine.activity.live

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import heyong.intellectPinPang.ui.BaseActivity
import heyong.handong.framework.utils.SystemUtil
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.live.ZhiboJiedanUserBaseInfoBean
import heyong.intellectPinPang.databinding.ActivityClerkOrderApplyBinding
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.mine.activity.RealNameImageActivity
import heyong.intellectPinPang.utils.MessageDialogBuilder
import heyong.lzy.imagepicker.util.StatusBarUtil

/**
 * 接单员申请
 */
class ClerkOrderApplyActivity : BaseActivity<ActivityClerkOrderApplyBinding, LiveViewModel>(),
    View.OnClickListener {

    val TAG = ClerkOrderApplyActivity::class.java.simpleName
    var isHaveAuth: Boolean = false;
    var bean: ZhiboJiedanUserBaseInfoBean? = null
    var phoneModel: String = "";

    override fun getLayoutRes(): Int {
        return R.layout.activity_clerk_order_apply
    }

    var inputType: Int = 0

    companion object {
        fun startActivity(context: Context, intputType: Int) {
            val intent = Intent(context, ClerkOrderApplyActivity::class.java)
            intent.putExtra("intputType", intputType)
            context.startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.zhiboJiedanUserBaseInfo()
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
        inputType = intent.getIntExtra("intputType", 0)
        val version = SystemUtil.getVersion()
        if (version >= 6) {
            StatusBarUtil.statusBarLightMode(this)
            StatusBarUtil.transparencyBar(this) //设置状态栏全透明
        }

        mViewModel.zhiboJiedanUserBaseInfoData.observe(this, Observer {
            if (it.errorCode.equals("200")) {
                mBinding.tvDeviceType.text = "" + SystemUtil.getSystemModel()
                phoneModel = "" + SystemUtil.getSystemModel()
                isHaveAuth = it.data.isHasNeedDeposit
                bean = it.data
                mBinding.tvCashPledge.text = "¥ " + it.data.deposit
                //如果实名认证
                if (it.data.isAuthentication) {
                    mBinding.tvSubmit.isEnabled = true
                    mBinding.tvSubmit.setBackgroundResource(R.drawable.shape_order_tack_bg)
                    mBinding.llNoAlreadyAuth.visibility = View.GONE
                    mBinding.tvAlreadyAuth.visibility = View.VISIBLE
                    mBinding.llPersonalContainer.visibility = View.VISIBLE
                    mBinding.tvUserIdCard.text = it.data.idCardNum
                    mBinding.tvUserName.text = it.data.name
                    if (it.data.sex.equals("1")) {
                        mBinding.tvUserSex.text = "男"
                    } else {
                        mBinding.tvUserSex.text = "女"
                    }
                } else {
                    mBinding.tvSubmit.isEnabled = false
                    mBinding.tvSubmit.setBackgroundResource(R.drawable.shape_order_track_gray_bg)
                    mBinding.llNoAlreadyAuth.visibility = View.VISIBLE
                    mBinding.tvAlreadyAuth.visibility = View.GONE
                    mBinding.llPersonalContainer.visibility = View.GONE
                }
            } else {
                toast("" + it.message)
            }


        })
        mViewModel.createXlZhiboJiedanUserData.observe(this, Observer {
            if (it.errorCode.equals("200")) {
                var hasNeedDeposit = bean?.isHasNeedDeposit
                if (hasNeedDeposit == true) {
                    PayOrderTackerActivity.startActivity(this, "" + bean?.id)
                } else {
                    toast("申请成功")
                    finish()
                }
            } else {
                toast("" + it.message)
            }
        })
        mViewModel.xlZhiboJiedanUserData.observe(this, Observer {
            if (it.errorCode.equals("200")) {
                var hasNeedDeposit = bean?.isHasNeedDeposit
                if (hasNeedDeposit == true) {
                    PayOrderTackerActivity.startActivity(this, "" + bean?.id)
                } else {
                    toast("恢复接单员身份成功")
                    finish()
                }

            } else {
                toast("" + it.message)
            }

        })

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            //去实名认证
            R.id.ll_no_already_auth -> {
                //未实名
                goActivity(RealNameImageActivity::class.java)
            }
            R.id.iv_finish -> {
                finish()
            }
            R.id.tv_submit -> {
                if (bean != null) {
                    var authentication = bean?.isAuthentication
                    if (authentication == true) {
                        //判断是否是回复账号   恢复冻结账号
                        if (inputType == 1) {
                            mViewModel.xlZhiboJiedanUser("" + bean?.id)
                        } else {
                            //创建账号
                            mViewModel.createXlZhiboJiedanUser(
                                "" + bean?.id,
                                "" + bean?.name,
                                "" + bean?.sex,
                                "" + bean?.idCardNum,
                                "" + bean?.deposit,
                                "" + phoneModel
                            )
                        }

                    } else {
                        MessageDialogBuilder(this)
                            .setMessage("")
                            .setTvTitle("请先实名认证")
                            .setTvCancel("取消")
                            .setBtnCancleHint(true)
                            .setTvSure("好的")
                            .setSureListener { v1: View? ->
                                //请求接口
                                goActivity(RealNameImageActivity::class.java)
                            }
                            .show()
                    }


                }


            }
        }
    }
}