package heyong.intellectPinPang.live.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.xq.fasterdialog.dialog.CustomDialog
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.live.SuspendLiveBroadCastBean
import heyong.intellectPinPang.databinding.ActivityLiveEndBinding
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.soundnet.SingleHostLiveActivity
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.ui.mine.activity.live.MyLiveOrderActivity
import heyong.intellectPinPang.utils.AntiShakeUtils

/**
 * 直播结束 1
 */
class LiveEndActivity : BaseActivity<ActivityLiveEndBinding, LiveViewModel>(),
    View.OnClickListener {
    var id: String = ""
    var bean: SuspendLiveBroadCastBean? = null
    var type: String = ""

    companion object {
        fun start(context: Context, id: String, type: Int) {
            val intent = Intent(context, LiveEndActivity::class.java)
            intent.putExtra("id", "" + id)
            intent.putExtra("type", "" + type)
            context.startActivity(intent)
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_live_end
    }


    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
        id = intent.getStringExtra("id")
        type = intent.getStringExtra("type")
        mViewModel.suspendLiveBroadcast(id)
        if (type.equals("1")) {
//            tv_continue_to_live
            mBinding.tvContinueToLive.visibility = View.VISIBLE
            mBinding.tvCompleteLive.visibility = View.VISIBLE
        } else {
            mBinding.tvContinueToLive.visibility = View.GONE
            mBinding.tvCompleteLive.visibility = View.GONE
        }
        mViewModel.suspendLiveBroadcastData.observe(this, Observer {
            dismissLoading()
            if (it.errorCode.equals("200")) {
                bean = it.data as SuspendLiveBroadCastBean
                heyong.handong.framework.utils.ImageLoader.loadImage(mBinding.ivLogo, bean?.headImg,R.drawable.morentouxiang
                ,R.drawable.morentouxiang)
                mBinding.tvMostStar.text = "" + bean?.mostPopular
                mBinding.tvSeeMostStar.text = "累计观看人数  " + bean?.mostPopular
                mBinding.tvLiveTime.text = "" + bean?.liveBroadcastDuration
                mBinding.tvName.text=""+bean?.name
            } else {
                toast("" + it.message)
            }


        })
        mViewModel.endLiveBroadcastData.observe(this, Observer {
            dismissLoading()
            if (it.errorCode.equals("200")) {
                var text = "确认完成此订单吗？订单确认完成后" +
                        "则无法更改拍摄，待客户确认订单后" +
                        "赏金会自动转入您的账户"
                showFinishMarking(text, 1)
            } else if (it.errorCode.equals("100000-100070")) {
                var text = "系统检测您的直播时长小于3分钟，此" +
                        "订单未正常完成，提前结束此订单则按" +
                        "照异常订单处理，赏金会自动返还给下" +
                        "单人，是否结束？"
                showFinishMarking(text, 2)
            } else {
                toast("" + it.message)
            }


        })
        mViewModel.endLiveBroadcastForceData.observe(this, Observer {
            dismissLoading()
            if (it.errorCode.equals("200")) {
                if (customDialogConfirm != null) {
                    customDialogConfirm!!.dismiss()
                }
                //跳转到订单页面
                MyLiveOrderActivity.start(this)
                finish()
            } else {
                toast(it.message)
            }

        })


    }

    var customDialogConfirm: CustomDialog? = null
    fun showFinishMarking(texts: String, type: Int) {

        customDialogConfirm = CustomDialog(this)
        customDialogConfirm!!.setCustomView(R.layout.pop_finish_marking)
        customDialogConfirm!!.show()
        val customView = customDialogConfirm!!.customView
        val tvSure = customView.findViewById<TextView>(R.id.tv_submit)
        val tvBackFinish = customView.findViewById<TextView>(R.id.tv_back_finish)
        val tvText = customView.findViewById<TextView>(R.id.tv_text)
        tvText.text = texts
        tvBackFinish.setOnClickListener(View.OnClickListener { v ->
            if (AntiShakeUtils.isInvalidClick(v))
                return@OnClickListener
            if (customDialogConfirm != null) {
                customDialogConfirm!!.dismiss()
            }
        })

        tvSure.setOnClickListener(View.OnClickListener { v ->
            if (AntiShakeUtils.isInvalidClick(v))
                return@OnClickListener
            if (type == 1) {
                if (customDialogConfirm != null) {
                    customDialogConfirm!!.dismiss()
                }
                //跳转到订单页面
                MyLiveOrderActivity.start(this)
                finish()
            } else {
                showLoading()
                mViewModel.endLiveBroadcastForce(id, "true")
            }
        })
    }


    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.tv_continue_to_live -> {
                //继续直播
                SingleHostLiveActivity.goActivity(this, true, id)
                finish()
            }

            R.id.tv_complete_live -> {
                //完成直播
                showLoading()
                mViewModel.endLiveBroadcast(id)
            }
        }

    }
}