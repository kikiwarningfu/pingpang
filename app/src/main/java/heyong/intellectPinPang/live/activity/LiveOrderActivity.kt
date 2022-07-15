package heyong.intellectPinPang.live.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import heyong.handong.framework.utils.ImageLoader
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.live.XlZhiboSetOrder
import heyong.intellectPinPang.databinding.ActivityLiveOrderBinding
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.utils.CommonUtilis

/**
 * 直播下单
 */
class LiveOrderActivity : BaseActivity<ActivityLiveOrderBinding, LiveViewModel>(),
    View.OnClickListener {

    var itemTitle = ""
    var arrangeId = ""
    var inputType: Int = 0
    var leftName: String = ""
    var rightName: String = ""
    var leftUserOneName: String = ""
    var rightUserOneName: String = ""
    var viewAngle: Int = 0
    var xlSeOrderBean: XlZhiboSetOrder? = null

    companion object {
        fun startActivity(
            context: Context,
            itemtitle: String,
            arrangeId: String,
            leftUserOneName: String,
            rightUserOneName: String
        ) {
            val intent = Intent(context, LiveOrderActivity::class.java)
            intent.putExtra("itemtitle", itemtitle)
            intent.putExtra("arrangeId", arrangeId)
            intent.putExtra("leftUserOneName", leftUserOneName)
            intent.putExtra("rightUserOneName", rightUserOneName)
            context.startActivity(intent)

        }
    }

    //pop_xiadan_xuzhi.xml 下单须知
    override fun getLayoutRes(): Int {
        return R.layout.activity_live_order
    }

    override fun initView(savedInstanceState: Bundle?) {

        itemTitle = intent.getStringExtra("itemtitle")
        arrangeId = intent.getStringExtra("arrangeId")
        leftUserOneName = intent.getStringExtra("leftUserOneName")
        rightUserOneName = intent.getStringExtra("rightUserOneName")
        mViewModel.getXlZhiboSetOrder(arrangeId, itemTitle)
        mBinding.listener = this
        mViewModel.getXlZhiboSetOrderData.observe(this, Observer {

            if (it.errorCode.equals("200")) {
                val data = it.data
                xlSeOrderBean = it.data
                mBinding.tvMatchTitle.text = data.matchTitle
                mBinding.tvTableNum.text = "" + data.tableNum + "台"
                mBinding.tvTime.text = data.hourTime
                mBinding.tvDate.text = data.dayTime
                mBinding.tvItemTitle.text = data.itemTitle
//              mBinding.tvFilmingMoney.text = "¥" + data.money
                if (data.itemType == "1" || data.itemType == "2") {
                    if (TextUtils.isEmpty(data.player1)) {
                        mBinding.tvLeftUserNameOne.text = "虚位待赛"
                        mBinding.tvLeftClubName.text = "" + leftUserOneName
                        leftName = ""
                    } else {
                        mBinding.tvLeftUserNameOne.text = data.player1
                        mBinding.tvLeftClubName.text = data.leftClubName
                        leftName = data.player1
                    }
                    if (TextUtils.isEmpty(data.player3)) {
                        mBinding.tvRightUserName.text = "虚位待赛"
                        mBinding.tvRightClubName.text = "" + rightUserOneName
                        rightName = ""
                    } else {
                        mBinding.tvRightUserName.text = data.player3
                        mBinding.tvRightClubName.text = data.rightClubName

                        rightName = data.player3
                    }
                } else {
                    if (TextUtils.isEmpty(data.player1)) {
                        mBinding.tvLeftUserNameOne.text = "虚位待赛"
                        mBinding.tvLeftClubName.text = "" + leftUserOneName
                        mBinding.tvLeftUserNameTwo.text = "虚位待赛"
                        leftName = ""
                    } else {
                        mBinding.tvLeftUserNameOne.text = data.player1
                        mBinding.tvLeftClubName.text = data.leftClubName
                        mBinding.tvLeftUserNameTwo.text = data.player2
                        leftName = data.player1 + "\n" + data.player3
                    }
                    if (TextUtils.isEmpty(data.player3)) {
                        mBinding.tvRightUserName.text = "虚位待赛"
                        mBinding.tvRightUserNameTwo.text = "虚位待赛"
                        mBinding.tvRightClubName.text = "" + rightUserOneName
                        rightName = ""
                    } else {
                        mBinding.tvRightUserName.text = data.player3
                        mBinding.tvRightUserNameTwo.text = data.player4
                        mBinding.tvRightClubName.text = data.rightClubName
                        rightName = data.player1 + "\n" + data.player3
                    }
                }
                mBinding.tvLeftNames.text = leftName
                mBinding.tvRightNames.text = rightName
                if (TextUtils.isEmpty(leftName) && TextUtils.isEmpty(rightName)) {
                    mBinding.llZhushijiao.visibility = View.GONE
                    viewAngle = 1
                } else {
                    mBinding.llZhushijiao.visibility = View.VISIBLE

                }


            } else {
                toast(it.message)
            }


        })
        mViewModel.getOrderMoneyData.observe(this, Observer {
            dismissLoading()
            if (it.errorCode.equals("200")) {
                var money = it.data.money
                mBinding.tvFilmingMoney.text =
                    "¥" + CommonUtilis.getTwoDecimal(money.toDouble().toString())
                xlSeOrderBean?.money = money
            } else {
                toast("" + it.message)
            }


        })
        mViewModel.createXlZhiboSetOrderData.observe(this, Observer {
            dismissLoading()
            if (it.errorCode.equals("200")) {
                var orderId = xlSeOrderBean?.id
                var money = xlSeOrderBean?.money
                PayLiveOrderActivity.startActivity(
                    this,
                    "" + money,
                    "下单直播",
                    "" + orderId
                )
                finish()
            } else {
                toast(it.message)
            }


        })
    }

    fun clearView() {
        mBinding.tvRewardFilming.setBackgroundResource(R.drawable.shape_stroke_orangegray)
        mBinding.tvMyselefFilming.setBackgroundResource(R.drawable.shape_stroke_orangegray)

        mBinding.tvRewardFilming.setTextColor(Color.parseColor("#666666"))
        mBinding.tvMyselefFilming.setTextColor(Color.parseColor("#666666"))
    }

    fun clearAngleView() {
        ImageLoader.loadImage(mBinding.ivLeftNames, R.drawable.img_video_no_select)
        ImageLoader.loadImage(mBinding.ivRightNames, R.drawable.img_video_no_select)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            //悬赏拍摄
            R.id.tv_reward_filming -> {
                clearView();
                inputType = 2
                mBinding.tvRewardFilming.setBackgroundResource(R.drawable.shape_stroke_orange)
                mBinding.tvRewardFilming.setTextColor(Color.parseColor("#ffff7500"))
                xlSeOrderBean?.orderType = "2"
                var itemtype = xlSeOrderBean?.itemType
                //掉接口  orderType  1自己录制  2 悬赏拍摄
                showLoading()
                mViewModel.getOrderMoney("" + itemtype, "2", "" + xlSeOrderBean?.arrangeId)
            }
            //自己拍摄
            R.id.tv_myselef_filming -> {
                clearView();

                inputType = 1
                mBinding.tvMyselefFilming.setBackgroundResource(R.drawable.shape_stroke_orange)
                mBinding.tvMyselefFilming.setTextColor(Color.parseColor("#ffff7500"))
                xlSeOrderBean?.orderType = "1"
                var itemtype = xlSeOrderBean?.itemType
                showLoading()
                mViewModel.getOrderMoney("" + itemtype, "1", "" + xlSeOrderBean?.arrangeId)


            }
            R.id.tv_submit_order -> {
                if (inputType == 0) {
                    toast("请选择拍摄方式")
                    return
                }
                if (viewAngle == 0) {
                    toast("请选择主视角")
                    return
                }
                mViewModel.createXlZhiboSetOrder(xlSeOrderBean)


            }
            R.id.ll_left_names -> {
                clearAngleView()
                viewAngle = 1
                ImageLoader.loadImage(mBinding.ivLeftNames, R.drawable.img_video_select)
                xlSeOrderBean?.mainDirection = "1"
            }
            R.id.ll_right_names -> {
                clearAngleView()
                viewAngle = 2
                ImageLoader.loadImage(mBinding.ivRightNames, R.drawable.img_video_select)
                xlSeOrderBean?.mainDirection = "2"

            }


        }
    }
}