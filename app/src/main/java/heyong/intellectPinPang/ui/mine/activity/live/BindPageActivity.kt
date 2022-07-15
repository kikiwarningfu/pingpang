package heyong.intellectPinPang.ui.mine.activity.live

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.umeng.socialize.UMAuthListener
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareConfig
import com.umeng.socialize.bean.SHARE_MEDIA
import com.xq.fasterdialog.dialog.CustomDialog
import heyong.handong.framework.utils.ImageLoader
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityBindPageBinding
import heyong.intellectPinPang.live.activity.BindZFBActivity
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.utils.AntiShakeUtils
import kotlin.concurrent.timer

class BindPageActivity : BaseActivity<ActivityBindPageBinding, LiveViewModel>(),
    View.OnClickListener {

    private var mShareAPI: UMShareAPI? = null

    var type: Int = 0;//0 微信 1支付宝
    var bindType: Int = 0;//0 绑定 1未绑定
    var id: String = ""


    override fun getLayoutRes(): Int {
        return R.layout.activity_bind_page
    }


    companion object {
        fun goActivity(context: Context, type: Int, bindType: Int, id: String) {
            val intent = Intent(context, BindPageActivity::class.java)
            intent.putExtra("paytype", type)
            intent.putExtra("bindType", bindType)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        id = intent.getStringExtra("id")
        type = intent.getIntExtra("paytype", 0)
        bindType = intent.getIntExtra("bindType", 0)
        mViewModel.withdrawalInfo(id)
    }

    override fun initView(savedInstanceState: Bundle?) {

        mShareAPI = UMShareAPI.get(this)

        mBinding.listener = this

        mViewModel.withdrawalInfoData.observe(this, Observer {
            if (it.data != null) {
                type = intent.getIntExtra("paytype", 0)
                bindType = intent.getIntExtra("bindType", 0)

                if (type == 0) {
                    mBinding.topBar.setCenterText("微信")
                    ImageLoader.loadImage(mBinding.ivWx, R.drawable.img_bind_wx)
                    mBinding.tvBindText.text = "绑定到微信账户"
                    mBinding.tvBindExplain.text = "将您的账户绑定到微信后，可以提现到微信"
                    mBinding.tvSubmit.text = "绑定微信"
                } else {
                    mBinding.topBar.setCenterText("支付宝")
                    ImageLoader.loadImage(mBinding.ivWx, R.drawable.img_bind_zfb)
                    mBinding.tvBindText.text = "绑定到支付宝账户"
                    mBinding.tvBindExplain.text = "将您的账户绑定到支付宝后，可以提现到支付宝"
                    mBinding.tvSubmit.text = "绑定支付宝"
                }
                if (bindType == 0) {
                    //已绑定
                    ImageLoader.loadImage(
                        mBinding.ivLogoBindStatus,
                        R.drawable.img_explore_unbind_gray
                    )
                    mBinding.tvTextBindStatus.text = "已绑定"
                    if (type == 0) {
                        mBinding.tvBindText.text = "微信账户：" + it.data.wxName
                    } else {
                        mBinding.tvBindText.text = "支付宝账户：" + it.data.apayAccount
                    }
                    mBinding.tvSubmit.setBackgroundResource(R.drawable.shape_color_red_conres)
                    mBinding.tvSubmit.setText("解除绑定")
                } else {
                    //未绑定
                    ImageLoader.loadImage(mBinding.ivLogoBindStatus, R.drawable.img_explore_bind)
                    mBinding.tvTextBindStatus.text = "未绑定"
                    mBinding.tvSubmit.setBackgroundResource(R.drawable.shape_club_blue)
                    if (type == 0) {
                        mBinding.tvSubmit.setText("绑定微信")
                    } else {
                        mBinding.tvSubmit.setText("绑定支付宝")
                    }
                }


            }

        })
        //解绑
        mViewModel.unbindAccountData.observe(this, Observer {
            dismissLoading()
            if (it.errorCode.equals("200")) {
                showUnBindSuccess()
            } else {
                toast("解除绑定失败")
            }


        })
        mViewModel.bindingWithdrawalAccountData.observe(this, Observer {
            if (it.errorCode.equals("200")) {
                showBindSuccess()
            } else {
                showBindFail()
            }
        })


    }

    var customDialogConfirmFail: CustomDialog? = null

    //绑定失败
    private fun showBindFail() {
        customDialogConfirmFail = CustomDialog(this)
        customDialogConfirmFail!!.setCustomView(R.layout.pop_bind_fail)
        customDialogConfirmFail!!.show()
        val customView: View = customDialogConfirmFail!!.getCustomView()
        val tvCancel = customView.findViewById<TextView>(R.id.tv_cancel)
        val tvBindRetry = customView.findViewById<TextView>(R.id.tv_bind_retry)

        tvCancel.setOnClickListener {
            if (customDialogConfirmFail != null) {
                customDialogConfirmFail!!.dismiss()
            }
        }
        tvBindRetry.setOnClickListener {
            if (customDialogConfirmFail != null) {
                customDialogConfirmFail!!.dismiss()
            }

            showLoading()
            if (type == 0) {
                //微信
                mViewModel.unbindAccount(id, "0", "")
            } else {
                mViewModel.unbindAccount(id, "", "0")
            }
        }
    }

    var customDialogConfirmSuccess: CustomDialog? = null

    fun showBindSuccess() {
        customDialogConfirmSuccess = CustomDialog(this)
        customDialogConfirmSuccess!!.setCustomView(R.layout.pop_bind_success)
        customDialogConfirmSuccess!!.show()

        object : CountDownTimer(1500, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                if (customDialogConfirmSuccess != null) {
                    customDialogConfirmSuccess!!.dismiss()
                }

                finish()

            }
        }.start()

    }

    var customDialogConfirmUnBindSuccess: CustomDialog? = null

    fun showUnBindSuccess() {
        customDialogConfirmUnBindSuccess = CustomDialog(this)
        customDialogConfirmUnBindSuccess!!.setCustomView(R.layout.pop_bind_success)
        customDialogConfirmUnBindSuccess!!.show()
        val customView: View = customDialogConfirmUnBindSuccess!!.getCustomView()
        var tvBindText = customView.findViewById<TextView>(R.id.tv_bind_text)
        tvBindText.text = "解除绑定成功"
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                if (customDialogConfirmUnBindSuccess != null) {
                    customDialogConfirmUnBindSuccess!!.dismiss()
                    finish()
                }
            }
        }.start()

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_submit -> {

                if (bindType == 0) {
                    //解除绑定
                    //已绑定
                    if (type == 0) {
                        //微信
                        showUnBind(0)
                    } else {
                        //支付宝
                        showUnBind(1)
                    }


                } else {
                    //未绑定
                    if (type == 0) {
                        //微信
                        showBind(0)
                    } else {
                        //支付宝
                        showBind(1)
                    }
                }
            }
        }
    }

    var authListener: UMAuthListener = object : UMAuthListener {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        override fun onStart(platform: SHARE_MEDIA) {}

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        override fun onComplete(
            platform: SHARE_MEDIA,
            action: Int,
            data: Map<String, String>
        ) {
            Logger.d(data)
            Log.e(TAG, "onComplete: 授权返回数据" + Gson().toJson(data))
            if (platform == SHARE_MEDIA.WEIXIN) {
                val sex: String
                sex = try {
                    if (data["gender"] != null) {
                        if ("男" == data["gender"]) "1" else "0"
                    } else {
                        "1"
                    }
                } catch (e: Exception) {
                    "1"
                }
                val iconurl = data["iconurl"]
                val openid = data["openid"]
                val screen_name = data["screen_name"]

                mViewModel.bindingWithdrawalAccount(
                    "" + id,
                    "" + screen_name,
                    "" + openid,
                    "1",
                    "",
                    "",
                    ""
                )
            }
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        override fun onError(platform: SHARE_MEDIA, action: Int, t: Throwable) {
            toast("失败：" + t.message)
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        override fun onCancel(platform: SHARE_MEDIA, action: Int) {
            toast("取消了")

        }
    }
    var customDialogConfirmBind: CustomDialog? = null

    fun showBind(type: Int) {
        customDialogConfirmBind = CustomDialog(this)
        customDialogConfirmBind!!.setCustomView(R.layout.pop_to_bind)
        customDialogConfirmBind!!.show()
        val customView = customDialogConfirmBind!!.customView
        var tvExplain = customView.findViewById<TextView>(R.id.tv_explain)
        var tvCancel = customView.findViewById<TextView>(R.id.tv_cancel)
        var tvSubmit = customView.findViewById<TextView>(R.id.tv_submit)
        if (type == 0) {
            tvExplain.text = "您尚未绑定微信账号，无法" +
                    "提现，请先绑定提现账号"
        } else {
            tvExplain.text = "您尚未绑定支付宝账号，无法" +
                    "提现，请先绑定提现账号"
        }
        tvCancel.setOnClickListener {
            if (customDialogConfirmBind != null) {
                customDialogConfirmBind!!.dismiss()
            }

        }
        tvSubmit.setOnClickListener {
            if (type == 0) {
                if (customDialogConfirmBind != null) {
                    customDialogConfirmBind!!.dismiss()
                }
                //微信
                val config = UMShareConfig()
                config.isNeedAuthOnGetUserInfo(true)
                UMShareAPI.get(this).setShareConfig(config)
                mShareAPI?.getPlatformInfo(this, SHARE_MEDIA.WEIXIN, authListener)
            } else {
                //跳转到绑定支付宝页面
                BindZFBActivity.goActivity(this, "" + id)
                finish()
            }
        }

    }

    var customDialogConfirm: CustomDialog? = null
    fun showUnBind(type: Int) {

        customDialogConfirm = CustomDialog(this)
        customDialogConfirm!!.setCustomView(R.layout.pop_explore_unbind)
        customDialogConfirm!!.show()
        val customView = customDialogConfirm!!.customView
        val tvCancel = customView.findViewById<TextView>(R.id.tv_cancel)
        val tvUnBind = customView.findViewById<TextView>(R.id.tv_un_bind)
        val tvCancelText = customView.findViewById<TextView>(R.id.tv_cancel_text)
        if (type == 0) {
            tvCancelText.text = "解除微信绑定后，将无法提现钱包余额" +
                    "到微信，您确定要解除绑定吗？"
        } else {
            tvCancelText.text = "解除支付宝绑定后，将无法提现钱包余额" +
                    "到支付宝，您确定要解除绑定吗？"
        }

        tvCancel.setOnClickListener(View.OnClickListener { v ->
            if (AntiShakeUtils.isInvalidClick(v))
                return@OnClickListener
            if (customDialogConfirm != null) {
                customDialogConfirm!!.dismiss()
            }
        })

        tvUnBind.setOnClickListener(View.OnClickListener { v ->
            if (AntiShakeUtils.isInvalidClick(v))
                return@OnClickListener
            if (customDialogConfirm != null) {
                customDialogConfirm!!.dismiss()
            }
            showLoading()
            if (type == 0) {
                //微信
                mViewModel.unbindAccount(id, "0", "")
            } else {
                mViewModel.unbindAccount(id, "", "0")
            }
        })
    }

}