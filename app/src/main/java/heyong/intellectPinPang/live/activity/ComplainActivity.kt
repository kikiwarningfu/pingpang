package heyong.intellectPinPang.live.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import heyong.intellectPinPang.ui.BaseActivity
import heyong.handong.framework.utils.ImageLoader
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityComplainBinding
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.mine.event.OrderStatusChangeEvent
import heyong.intellectPinPang.utils.MessageDialogBuilder
import org.greenrobot.eventbus.EventBus

/**
 * 投诉
 */
class ComplainActivity : BaseActivity<ActivityComplainBinding, LiveViewModel>(),
    View.OnClickListener {
    var id = ""
    var reason = ""
    var inputType = 0

    companion object {
        fun startActivity(context: Context, id: String) {
            val intent = Intent(context, ComplainActivity::class.java)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_complain

    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
        id = intent.getStringExtra("id")

        clearView()
        mViewModel.complaintOrderData.observe(this, Observer {
            if (it.errorCode.equals("200")) {
                var message = it.message
                MessageDialogBuilder(this)
                    .setMessage("")
                    .setTvTitle(
                        "您的投诉已提交，我们会尽快为您" +
                                "解决，请耐心等待"
                    )
                    .setTvCancel("取消")
                    .setBtnCancleHint(true)
                    .setTvSure("好的")
                    .setSureListener {
                        toast("" + message)
                        //刷新 订单列表的数据
                        EventBus.getDefault().post(OrderStatusChangeEvent())
                        LivePublishOrderDetailActivity.instance?.finish()
                        finish()
                    }
                    .show()
            } else {
                toast("" + it.message)
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_buwanzheng -> {
                clearView()
                ImageLoader.loadImage(mBinding.ivBuwancheng, R.drawable.img_tousu_select)
                reason = "视频不完整"
                inputType = 1
            }
            R.id.ll_xuqiu -> {
                clearView()
                reason = "视频内容与下单需求不符"
                inputType = 2
                ImageLoader.loadImage(mBinding.ivXuqiu, R.drawable.img_tousu_select)
            }
            R.id.ll_not_play -> {
                clearView()
                reason = "视频无法正常播放"
                inputType = 3
                ImageLoader.loadImage(mBinding.ivNotPlay, R.drawable.img_tousu_select)
            }
            R.id.ll_qita -> {
                clearView()
                inputType = 4
                reason = ""
                mBinding.etTextHint.setEnabled(true)
                ImageLoader.loadImage(mBinding.ivQita, R.drawable.img_tousu_select)

            }
            R.id.tv_submit -> {
                MessageDialogBuilder(this)
                    .setMessage("")
                    .setTvTitle("是否提交投诉？")
                    .setTvCancel("取消")
                    .setBtnCancleHint(false)
                    .setTvSure("确定")
                    .setSureListener {
                        showLoading()
                        if (inputType == 4) {
                            if (TextUtils.isEmpty(mBinding.etTextHint.text.toString())) {
                                toast("请输入原因")
                                return@setSureListener
                            }
                            mViewModel.complaintOrder(
                                "" + id,
                                "" + mBinding.etTextHint.text.toString()
                            )
                        } else {
                            mViewModel.complaintOrder("" + id, "" + reason)

                        }
                    }
                    .show()

//
            }
        }
    }

    private fun clearView() {

        ImageLoader.loadImage(mBinding.ivBuwancheng, R.drawable.img_tousu_no_select)
        ImageLoader.loadImage(mBinding.ivNotPlay, R.drawable.img_tousu_no_select)
        ImageLoader.loadImage(mBinding.ivQita, R.drawable.img_tousu_no_select)
        ImageLoader.loadImage(mBinding.ivXuqiu, R.drawable.img_tousu_no_select)
        mBinding.etTextHint.setEnabled(false)
        mBinding.etTextHint.setText("")
    }
}