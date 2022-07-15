package heyong.intellectPinPang.live.activity

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.elvishew.xlog.XLog
import com.xq.fasterdialog.dialog.CustomDialog
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.live.SuspendLiveBroadCastBean
import heyong.intellectPinPang.bean.live.XlZhiboJiedanOrderBean
import heyong.intellectPinPang.databinding.ActivityLiveOrderDetailBinding
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.permissions.PermissionsActivity
import heyong.intellectPinPang.soundnet.LivePrepareActivity
import heyong.intellectPinPang.ui.MyHomePageActivity
import heyong.intellectPinPang.ui.mine.event.OrderStatusChangeEvent
import heyong.intellectPinPang.utils.AppManager
import org.greenrobot.eventbus.EventBus


/**
 * 订单详情  LiveOrderDetailAboutDetailActivity  详情的再次跳转
 */
class LiveOrderDetailActivity : BaseActivity<ActivityLiveOrderDetailBinding, LiveViewModel>(),
    View.OnClickListener {
    var id = ""
    var xlBean: XlZhiboJiedanOrderBean? = null

    companion object {

        fun start(context: Context, id: String) {
            val intent = Intent(context, LiveOrderDetailActivity::class.java)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }

    }

    var bean: SuspendLiveBroadCastBean? = null

    override fun getLayoutRes(): Int {
        return R.layout.activity_live_order_detail
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
        id = intent.getStringExtra("id")

        mViewModel.getXlZhiboJiedanOrder(id)
        mViewModel.cancleJieDanOrderData.observe(this, Observer {
            dismissLoading()
            if (it.errorCode.equals("200")) {
                EventBus.getDefault().post(OrderStatusChangeEvent())
                toast("取消成功")
                finish()
            } else {
                toast("" + it.message)
            }
        })

        mViewModel.endLiveBroadcastData.observe(this, Observer {
            dismissLoading()
            EventBus.getDefault().post(OrderStatusChangeEvent())
            toast(""+it.message)
            finish()
        })

        mViewModel.getXlZhiboJiedanOrderData.observe(this, Observer {

            if (it.errorCode.equals("200")) {
                var data = it.data.xlZhiboSetOrder
                xlBean = it.data
                var orderStatus = xlBean!!.status
                mBinding.tvGameName.text = "" + data.matchTitle
                mBinding.tvTableNum.text = "" + data.tableNum + "台"
                var leftName = ""
                var rightName = ""
                if (data.itemType.equals("1") || data.itemType.equals("2")) {
                    if (TextUtils.isEmpty(data.player1)) {
                        leftName = "虚位待赛"
                    } else {
                        leftName = data.player1
                    }
                    if (TextUtils.isEmpty(data.player3)) {
                        rightName = "虚位待赛"
                    } else {
                        rightName = data.player3
                    }
                } else {
                    if (TextUtils.isEmpty(data.player1)) {
                        leftName = "虚位待赛"
                    } else {
                        leftName = data.player1 + "/" + data.player2
                    }
                    if (TextUtils.isEmpty(data.player3)) {
                        rightName = "虚位待赛"
                    } else {
                        rightName = data.player3 + "/" + data.player4
                    }
                }
                mBinding.tvNamePlayer.text = leftName + "  " + "VS" + "  " + rightName
//                mBinding.tvTime.text = "" + data.matchTime.substring(11)
                var s = "" + data.matchTime.substring(11)
                mBinding.tvTime.text = s.substring(0,5)
                mBinding.tvXiadanren.text = "下单人： " + data.name + "   " + data.acconut
                mBinding.tvOrderNo.text = "订单编号： " + data.orderNum
                mBinding.tvMoney.text = "¥" + data.money

                mBinding.tvCompleteOrder.isEnabled = true
                mBinding.tvStartLive.isEnabled = true
//              orderStatus = "6"
                //1  待直播  2 3 已取消  45  进行中  6 入账中   7 异常订单  8  已完成 我接取的订单
                //入账中  已入账  订单异常（订单异常 系统审核中   订单已处理  ）   订单已取消
                XLog.e(""+orderStatus)
                when (orderStatus) {
                    "1" -> {
                        mBinding.tvTextOrderStatus.text = "待直播"
                        mBinding.tvTextOrderStatus.setTextColor(Color.parseColor("#DC3C3C"))
                        //判断显示直播时间未到
                        mBinding.tvStatusText.visibility = View.VISIBLE
                        mBinding.tvCompleteOrder.text = "取消订单"
                        // todo  注释
//                        if (it.data.isCancel) {
//                            //可以取消
//                            mBinding.tvCompleteOrder.setBackgroundResource(R.drawable.shape_order_track_gray_bg)
//                            mBinding.tvCompleteOrder.isEnabled = false
//                        } else {
//                            //不可以取消
//                            mBinding.tvCompleteOrder.setBackgroundResource(R.drawable.shape_order_track_gray_bg)
//                            mBinding.tvCompleteOrder.isEnabled = true
//                        }
                        mBinding.tvStartLive.text = "开始直播"
                        mBinding.tvCompleteOrder.text = "取消订单"
//                        if (it.data.isHasBegin) {
//                            //可以开始
//                            mBinding.tvStartLive.isEnabled = true
//                            mBinding.tvStartLive.setBackgroundResource(R.drawable.shape_order_track_blue)
//                            mBinding.tvStatusText.visibility = View.GONE
//                        } else {
//                            //不能开始
//                            mBinding.tvStartLive.isEnabled = false
//                            mBinding.tvStartLive.setBackgroundResource(R.drawable.shape_order_track_gray_bg)
//                            mBinding.tvStatusText.visibility = View.VISIBLE
//                        }
                        mBinding.tvExplainText.text = "该订单处于待开播状态，请随时关注比赛时间，准时开播"
                        mBinding.tvExplainText.setTextColor(Color.parseColor("#666666"))
                    }
                //2是接单人取消  3是下单人取消

                    "2" -> {
                        mBinding.tvTextOrderStatus.text = "已取消"
                        mBinding.tvTextOrderStatus.setTextColor(Color.parseColor("#999999"))
                        mBinding.tvStartLive.visibility = View.INVISIBLE
                        mBinding.tvCompleteOrder.visibility = View.VISIBLE
                        mBinding.tvCompleteOrder.text = "订单已取消"
                        mBinding.tvCompleteOrder.setBackgroundResource(R.drawable.shape_order_track_red_bg)
                        mBinding.tvStatusText.visibility = View.GONE
                        mBinding.tvExplainText.text = "该订单已由接单员取消"
                        mBinding.tvExplainText.setTextColor(Color.parseColor("#666666"))
                    }
                    "3"->{
                        mBinding.tvTextOrderStatus.text = "已取消"
                        mBinding.tvTextOrderStatus.setTextColor(Color.parseColor("#999999"))
                        mBinding.tvStartLive.visibility = View.INVISIBLE
                        mBinding.tvCompleteOrder.visibility = View.VISIBLE
                        mBinding.tvCompleteOrder.text = "订单已取消"
                        mBinding.tvCompleteOrder.setBackgroundResource(R.drawable.shape_order_track_red_bg)
                        mBinding.tvStatusText.visibility = View.GONE
                        mBinding.tvExplainText.text = "该订单已由下单人取消"
                        mBinding.tvExplainText.setTextColor(Color.parseColor("#666666"))
                    }
                    "4", "5" -> {
                        mBinding.tvTextOrderStatus.text = "进行中"
                        mBinding.tvTextOrderStatus.setTextColor(Color.parseColor("#DC3C3C"))


                        mBinding.tvStartLive.visibility = View.VISIBLE
                        mBinding.tvStartLive.text = "继续直播"
                        mBinding.tvStartLive.setBackgroundResource(R.drawable.shape_order_track_blue)
                        if (it.data.isComplete) {
                            mBinding.tvCompleteOrder.isEnabled = true
                            mBinding.tvCompleteOrder.visibility = View.VISIBLE
                            mBinding.tvCompleteOrder.text = "完成订单"
                            mBinding.tvCompleteOrder.setBackgroundResource(R.drawable.shape_order_track_red_bg)
                        } else {
                            mBinding.tvCompleteOrder.isEnabled = false
                            mBinding.tvCompleteOrder.visibility = View.VISIBLE
                            mBinding.tvCompleteOrder.text = "完成订单"
                            mBinding.tvCompleteOrder.setBackgroundResource(R.drawable.shape_order_track_gray_bg)
                        }


                        mBinding.tvStatusText.visibility = View.GONE

                        mBinding.tvExplainText.text = "当前比赛正在进行中，请尽快完成直播，如果直播中断，可" +
                                "点击下方继续直播按钮，继续直播"
                        mBinding.tvExplainText.setTextColor(Color.parseColor("#666666"))

                    }
                    "6" -> {
                        mBinding.tvTextOrderStatus.text = "已完成"
                        mBinding.tvTextOrderStatus.setTextColor(Color.parseColor("#999999"))


                        mBinding.tvStartLive.visibility = View.INVISIBLE

                        mBinding.tvCompleteOrder.visibility = View.VISIBLE
                        mBinding.tvCompleteOrder.text = "赏金入账中"
                        mBinding.tvCompleteOrder.setBackgroundResource(R.drawable.shape_order_track_yellow)

                        mBinding.tvStatusText.visibility = View.GONE

                        mBinding.tvExplainText.text = "订单已完成"
                        mBinding.tvExplainText.setTextColor(Color.parseColor("#666666"))
                    }
                    "7" -> {
                        mBinding.tvTextOrderStatus.text = "订单异常"
                        mBinding.tvTextOrderStatus.setTextColor(Color.parseColor("#DC3C3C"))
                        mBinding.tvStartLive.visibility = View.INVISIBLE
                        mBinding.tvCompleteOrder.visibility = View.VISIBLE
                        mBinding.tvCompleteOrder.text = "订单异常，审核中"
                        mBinding.tvCompleteOrder.setBackgroundResource(R.drawable.shape_order_track_red_bg)

                        mBinding.tvStatusText.visibility = View.GONE

                        mBinding.tvExplainText.text = "该订单已被下单用户投诉，请等待处理"
                        mBinding.tvExplainText.setTextColor(Color.parseColor("#DC3C3C"))
                    }
                    "8" -> {
                        mBinding.tvTextOrderStatus.text = "已完成"
                        mBinding.tvTextOrderStatus.setTextColor(Color.parseColor("#999999"))


                        mBinding.tvStartLive.visibility = View.INVISIBLE

                        mBinding.tvCompleteOrder.visibility = View.VISIBLE
                        mBinding.tvCompleteOrder.text = "赏金已入账"
                        mBinding.tvCompleteOrder.setBackgroundResource(R.drawable.shape_order_track_blue)

                        mBinding.tvStatusText.visibility = View.GONE

                        mBinding.tvExplainText.text = "订单已完成"
                        mBinding.tvExplainText.setTextColor(Color.parseColor("#666666"))
                    }
                }


            } else {
                toast(it.message)
            }


        })

    }



    var customDialogConfirm: CustomDialog? = null

    private fun exitClubDialog() {
        customDialogConfirm = CustomDialog(this)
        customDialogConfirm!!.setCustomView(R.layout.pop_cancel_order_sure)
        customDialogConfirm!!.show()
        val customView = customDialogConfirm!!.customView
        showAskConfirmView(customView, customDialogConfirm!!)
    }


    private fun showAskConfirmView(
        customView: View?, customDialogConfirm: CustomDialog
    ) {

        val tvSure = customView?.findViewById<TextView>(R.id.tv_sure)
        val tvCancel = customView?.findViewById<TextView>(R.id.tv_cancel)

        tvCancel?.setOnClickListener {
            if (customDialogConfirm != null) {
                customDialogConfirm.dismiss()
            }
        }

        tvSure?.setOnClickListener {
            if (customDialogConfirm != null) {
                customDialogConfirm.dismiss()
            }
            showLoading()
            mViewModel.cancleJieDanOrder("" + xlBean?.id)
        }


    }

    private val PERMISSION_REQ_ID = 22


    val REQUESTED_PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA
    )
    var status = ""
    override fun onClick(v: View?) {
        status = xlBean?.status!!
        when (v?.id) {
            R.id.tv_start_live -> {
                when (status) {
//                    todo  开直播
                    "1" -> {
                        //开始直播
                        // 获取权限后，初始化 RtcEngine，并加入频道。
                        if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) &&
                            checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID)
                        ) {

                            LivePrepareActivity.goActivity(this,true,id,xlBean)
                        }

                    }
                    //                    todo  开直播
                    "4", "5" -> {
                        //继续直播
                        // 获取权限后，初始化 RtcEngine，并加入频道。
                        if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) &&
                            checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID) ) {

                            LivePrepareActivity.goActivity(this,true,id,xlBean)

                        }

                    }
                }
            }
            R.id.tv_complete_order -> {
                //1  待直播  2 3 已取消  45  进行中  6 入账中   7 异常订单  8  已完成 我接取的订单
                when (status) {
                    "1" -> {
                        //取消订单
                        exitClubDialog()
                    }
                    "2", "3" -> {
                        //订单已取消
                        LiveOrderDetailAboutDetailActivity.start(this, "" + xlBean?.id, "" + status)
                    }
                    "4", "5" -> {
                        //完成订单
                        showLoading()
                        mViewModel.endLiveBroadcast(id)
                    }
                    "6" -> {
                        //赏金入账中
                        LiveOrderDetailAboutDetailActivity.start(this, "" + xlBean?.id, "" + status)
                    }
                    "7", "9" -> {
                        //异常订单
                        LiveOrderDetailAboutDetailActivity.start(this, "" + xlBean?.id, "" + status)
                    }
                    "8" -> {
                        //赏金已入账
                        LiveOrderDetailAboutDetailActivity.start(this, "" + xlBean?.id, "" + status)
                    }
                }


            }
        }
    }



    @TargetApi(23)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQ_ID) {
            var isGranted = true
            for (index in permissions.indices) {
                if (grantResults[index] == PackageManager.PERMISSION_DENIED) {
                    isGranted = false
                }
            }
            if (!isGranted) {
                PermissionsActivity.startActivityForResult(
                    this,
                    PERMISSION_REQ_ID,
                    *REQUESTED_PERMISSIONS
                )
            }
        } else if (requestCode == 1000) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
            } else {
                showMissingPermissionDialog()
            }
        }
    }

    // 显示缺失权限提示
    fun showMissingPermissionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("帮助")
        builder.setMessage("当前应用缺少必要权限。\n请点击\"设置\"-\"权限\"-打开所需权限。\n最后点击两次后退按钮，即可返回。")

        // 拒绝, 退出应用
        builder.setNegativeButton("退出") { dialog, which ->
            AppManager.getAppManager().finishAllActivity()
            AppManager.getAppManager().AppExit()
            System.exit(0)
        }
        builder.setPositiveButton(
            "设置"
        ) { dialog, which ->
            try {
                MyHomePageActivity.GoToSetting(this)
            } catch (e: Exception) {
                MyHomePageActivity.ApplicationInfo(this)
            }
        }
        builder.show()
    }

    private fun checkSelfPermission(permission: String, requestCode: Int): Boolean {
        if (ContextCompat.checkSelfPermission(this, permission) !==
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, requestCode)
            return false
        }
        return true
    }
}