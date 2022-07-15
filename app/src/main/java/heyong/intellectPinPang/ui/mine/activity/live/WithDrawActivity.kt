package heyong.intellectPinPang.ui.mine.activity.live

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.elvishew.xlog.XLog
import com.xq.fasterdialog.dialog.CustomDialog
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.live.WithDrawMyInfoBean
import heyong.intellectPinPang.databinding.ActivityWithDrawBinding
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.widget.RegionNumberEditText


/**
 * 提现
 */
class WithDrawActivity : BaseActivity<ActivityWithDrawBinding, LiveViewModel>(),
    View.OnClickListener {
    var id: String = ""
    var type: Int = 0
    var myeditText: RegionNumberEditText? = null
    var bean: WithDrawMyInfoBean? = null
    var myMoney = 0

    companion object {
        fun goWithDraw(context: Context, id: String) {
            val intent = Intent(context, WithDrawActivity::class.java)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_with_draw
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
        id = intent.getStringExtra("id")

        mViewModel.withdrawalInfo(id)
        myeditText = findViewById(R.id.et_money)
        myeditText!!.setRegion(200, 1)

        mViewModel.withdrawalInfoData.observe(this, Observer {

            if (it.data != null) {
                bean = it.data
                var money = it.data.mayMoney
                myMoney = money.toDouble().toInt()
                if (myMoney > 200) {
                    myeditText!!.setRegion(200, 1)
                } else {
                    if (myMoney > 1) {
                        myeditText!!.isEnabled = true
                        myeditText!!.isCursorVisible = true
                        myeditText!!.isFocusableInTouchMode = true
                        myeditText!!.setRegion(myMoney, 1)
                        mBinding.tvSubmit.isEnabled = true
                        mBinding.tvSubmit.setBackgroundResource(R.drawable.shape_with_draw)
                    } else {
                        myeditText!!.isEnabled = false
                        myeditText!!.isCursorVisible = false
                        myeditText!!.isFocusableInTouchMode = false
                        mBinding.tvSubmit.isEnabled = false
                        mBinding.tvSubmit.setBackgroundResource(R.drawable.shape_with_draw_gray)
                    }
                }

                myeditText!!.setTextWatcher()
                mBinding.tvWithDrawMoney.text = "￥" + money

            }


        })
        mViewModel.withdrawalData.observe(this, Observer {
            dismissLoading()
            if (it.errorCode.equals("200")) {
                showCommitDialog()
            } else {
                toast(it.message)
            }
        })


    }

    override fun hideSoftByEditViewIds(): IntArray {
        return intArrayOf(R.id.et_money)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.rl_with_draw_wx -> {
                mBinding.rlWithDrawWx.setBackgroundResource(R.drawable.shape_with_draw_select)
                mBinding.rlWithDrawZfb.setBackgroundResource(R.drawable.shape_with_draw_select_no)
                mBinding.ivWx.visibility = View.VISIBLE
                mBinding.ivZfb.visibility = View.GONE
                type = 1
            }
            R.id.rl_with_draw_zfb -> {
                mBinding.rlWithDrawWx.setBackgroundResource(R.drawable.shape_with_draw_select_no)
                mBinding.rlWithDrawZfb.setBackgroundResource(R.drawable.shape_with_draw_select)
                mBinding.ivWx.visibility = View.GONE
                mBinding.ivZfb.visibility = View.VISIBLE
                type = 2
            }
            R.id.tv_with_draws -> {
                if (myMoney > 0) {
                    mBinding.etMoney.setText(""+myMoney)
                } else {
                    toast("可提现金额不足")
                }
            }
            R.id.tv_submit -> {

                when (type) {
                    0 -> {
                        toast("请选择支付方式")
                    }
                    1 -> {
                        var money = mBinding.etMoney.text.toString()
                        if (TextUtils.isEmpty(money)) {
                            toast("请输入提现金额")
                            return
                        }
                        if (bean!!.isWeChat) {
                            //绑定了微信
                            showLoading()
                            mViewModel.withdrawal("2", money)
                        } else {
                            //没有绑定
                            showNeedBindDialog(1)
                        }
                    }
                    2 -> {
                        var money = mBinding.etMoney.text.toString()
                        if (TextUtils.isEmpty(money)) {
                            toast("请输入提现金额")
                            return
                        }
                        if (bean!!.isApay) {
                            //绑定支付宝了
                            showLoading()
                            mViewModel.withdrawal("3", money)

                        } else {
                            //没有绑定
                            showNeedBindDialog(2)
                        }

                    }
                }

            }
        }
    }

    fun showCommitDialog() {
        val customDialogConfirm =
            CustomDialog(this)
        customDialogConfirm.setCustomView(R.layout.pop_with_draw_success)
        customDialogConfirm.show()
        val customView = customDialogConfirm.customView
        val tvSure =
            customView.findViewById<TextView>(R.id.tv_submit)

        tvSure.setOnClickListener {

            customDialogConfirm.dismiss()
            finish()
        }

    }

    fun showNeedBindDialog(type: Int) {
        val customDialogConfirm =
            CustomDialog(this)
        customDialogConfirm.setCustomView(R.layout.pop_to_bind)
        customDialogConfirm.show()
        val customView = customDialogConfirm.customView
        val tvSure = customView.findViewById<TextView>(R.id.tv_submit)
        val tvCancel = customView.findViewById<TextView>(R.id.tv_cancel)
        val tvExplain = customView.findViewById<TextView>(R.id.tv_explain)
        if (type == 1) {
            tvExplain.text = "您尚未绑定微信账号，无法 提现，请先绑定提现账号"
        } else {
            tvExplain.text = "您尚未绑定支付宝账号，无法 提现，请先绑定提现账号"

        }
        tvSure.setOnClickListener {
            customDialogConfirm.dismiss()
            BindAccountActivity.goActivity(this, id)

            finish()
        }
        tvCancel.setOnClickListener {
            customDialogConfirm.dismiss()
        }

    }


}