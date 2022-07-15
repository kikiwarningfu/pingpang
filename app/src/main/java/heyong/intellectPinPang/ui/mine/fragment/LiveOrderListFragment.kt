package heyong.intellectPinPang.ui.mine.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elvishew.xlog.XLog
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.xq.fasterdialog.dialog.CustomDialog
import heyong.intellectPinPang.ui.LazyloadFragment
import heyong.handong.framework.utils.ImageLoader
import heyong.intellectPinPang.R
import heyong.intellectPinPang.adapter.live.LiveOrderListAdapter
import heyong.intellectPinPang.bean.live.OfferRewardOrderListBean
import heyong.intellectPinPang.databinding.FragmentMyWalletPagerBinding
import heyong.intellectPinPang.event.LiveOrderStatusEvent
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.mine.activity.live.ClerkOrderApplyActivity
import heyong.intellectPinPang.ui.mine.event.TimeStatusChangeEvent
import heyong.intellectPinPang.utils.MessageDialogBuilder
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class LiveOrderListFragment : LazyloadFragment<FragmentMyWalletPagerBinding, LiveViewModel>(),
    OnRefreshListener {
    private var page = 1
    private var pageSize = 10
    private var haveMore = true
    private var matchId = "";
    private var orderMyStatus = ""
    var mutableList: MutableList<OfferRewardOrderListBean.ListBean> = mutableListOf()
    var TAG = LiveOrderListFragment::class.java.simpleName
    var timeCode = ""
    var time = ""
    var customDialogConfirm: CustomDialog? = null

    private fun exitClubDialog(boolean: Boolean) {
        customDialogConfirm = CustomDialog(activity as Context)
        customDialogConfirm!!.setCustomView(R.layout.pop_accept_live_order)
        customDialogConfirm!!.show()
        val customView = customDialogConfirm!!.customView
        showAskConfirmView(customView, customDialogConfirm!!, boolean)
    }


    private fun showAskConfirmView(
        customView: View?, customDialogConfirm: CustomDialog,
        boolean: Boolean
    ) {

        val ivLogo = customView?.findViewById<ImageView>(R.id.iv_order_logo)
        val tvOrderText = customView?.findViewById<TextView>(R.id.tv_order_text)
        val tvSubmit = customView?.findViewById<TextView>(R.id.tv_submit)
        if (boolean) {
            ImageLoader.loadImage(ivLogo, R.drawable.img_accept_success)
            tvOrderText?.text = "您已成功接取此订单，请您按照约定时间" +
                    "完成分拍摄，拍摄完成获得赏金"
        } else {
            ImageLoader.loadImage(ivLogo, R.drawable.img_accept_fail)
            tvOrderText?.text = "此订单已被领走，请尝试接取其他订单"
        }
        tvSubmit?.setOnClickListener {
            if (customDialogConfirm != null) {
                customDialogConfirm.dismiss()
            }
            getData()
        }


    }

    companion object {

        const val WALLET_STATUS = "wallet_status"

        fun newInstance(
            walletStatus: String,
            matchId: String,
            timeCode: String,
            time: String
        ): LiveOrderListFragment {

            val instance = LiveOrderListFragment()
            val bundle = Bundle()
            bundle.putString(WALLET_STATUS, "" + walletStatus)
            bundle.putString("match_id", matchId)
            bundle.putString("timeCode", timeCode)
            bundle.putString("time", time)
            instance.arguments = bundle
            return instance
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 100)
    fun messageLiveOrderStatus(event: LiveOrderStatusEvent) {
        binding.swFresh.setEnableLoadmore(true)
        page = 1
        haveMore = true
        orderMyStatus = event.orderStatus
        getData()
    }

    private val liveOrderListAdapter: LiveOrderListAdapter by lazy { LiveOrderListAdapter() }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_my_wallet_pager
    }

    override fun initView(savedInstanceState: Bundle?) {
        if (binding.swFresh.isRefreshing)
            binding.swFresh.finishRefresh()
        EventBus.getDefault().register(this)

        setUpRecycer()
        Log.e(TAG, "initView: matchTime==" + viewModel.matchTime.get())
        viewModel.receiveRewardOrderData.observe(this, Observer {
            dismissLoading()
            if (it.errorCode.equals("100000-100051")) {
                //你还不是接单员
                MessageDialogBuilder(activity)
                    .setMessage("")
                    .setTvTitle("您还不是接单员")
                    .setTvCancel("取消")
                    .setTvSure("去认证")
                    .setBtnCancleHint(false)
                    .setSureListener { v1: View? ->
                        activity.let {
                            ClerkOrderApplyActivity.startActivity(it!!, 0)
                        }
                    }
                    .show()
            } else if (it.errorCode.equals("100000-100052")) {
                //该订单已经有人接单
                exitClubDialog(false)
            } else if (it.errorCode.equals("200")) {
                exitClubDialog(true)
            } else if (it.errorCode.equals("100000-100072")) {
                MessageDialogBuilder(activity)
                    .setMessage("")
                    .setTvTitle("您还不是接单员")
                    .setTvCancel("取消")
                    .setTvSure("去认证")
                    .setBtnCancleHint(false)
                    .setSureListener { v1: View? ->
                        activity.let {
                            ClerkOrderApplyActivity.startActivity(it!!, 1)

                        }
                    }
                    .show()
            } else {
                toast("" + it.message)
            }


        })
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume: matchTime==" + viewModel.matchTime.get())
    }

    private fun setUpRecycer() {
        binding.rvMyWallet.adapter = liveOrderListAdapter
        binding.rvMyWallet.setLayoutManager(
            LinearLayoutManager(
                activity,
                RecyclerView.VERTICAL,
                false
            )
        )
        binding.swFresh.setOnRefreshListener(this)
        binding.swFresh.setOnLoadmoreListener {
            if (haveMore) {
                page++
                getData()
            } else {
                binding.swFresh.finishLoadmore()
            }
        }
        liveOrderListAdapter.setOnItemClickListener { _, view, position ->
            val item = liveOrderListAdapter.getItem(position)!!

            //调用接口
            MessageDialogBuilder(activity)
                .setMessage("")
                .setTvTitle("是否接取此订单？")
                .setTvCancel("取消")
                .setTvSure("确定")
                .setBtnCancleHint(false)
                .setSureListener { v1: View? ->
                    showLoading()
                    var id = item.id
                    viewModel.receiveRewardOrder("" + id)
                }
                .show()

        }
        liveOrderListAdapter.setOnItemChildClickListener { _, view, position ->
            val item = liveOrderListAdapter.getItem(position)!!

            //调用接口
            MessageDialogBuilder(activity)
                .setMessage("")
                .setTvTitle("是否接取此订单？")
                .setTvCancel("取消")
                .setTvSure("确定")
                .setBtnCancleHint(false)
                .setSureListener { v1: View? ->
                    showLoading()
                    var id = item.id
                    viewModel.receiveRewardOrder("" + id)
                }
                .show()

        }
    }

    //请求接口
    fun getData() {
        XLog.e(viewModel.matchTime.get())
        viewModel.offerRewardOrderList("" + timeCode, "" + matchId, "" + page, "" + pageSize)
        viewModel.OfferRewardOrderListBeanData.observe(this, Observer {

            if (it.errorCode.equals("200")) {

                if (it.data.list != null && it.data.list.size > 0) {
                    if (page == 1) {
                        mutableList.clear()
                    }
                    if (it.data.list != null) {
                        mutableList.addAll(it.data.list)
                        if (it.data.list != null && it.data.list.size < pageSize) {
                            haveMore = false
                            binding.swFresh.setEnableLoadmore(false)
                        } else {
                            binding.swFresh.setEnableLoadmore(true)
                        }
                        liveOrderListAdapter.setNewData(mutableList)
                    }
                    if (binding.swFresh != null) {
                        binding.swFresh.finishRefresh()
                        binding.swFresh.finishLoadmore()
                    }
                } else {
                    if (page == 1) {
                        mutableList.clear()
                    }
                    binding.swFresh.finishRefresh()
                    binding.swFresh.finishLoadmore()
                    liveOrderListAdapter.setNewData(mutableList)
                }


            } else {
                toast("" + it.message)
                if (page == 1) {
                    mutableList.clear()
                }
                liveOrderListAdapter.setNewData(mutableList)
            }
            if (liveOrderListAdapter.data.size > 0) {
                binding.rvMyWallet.visibility=View.VISIBLE
                binding.llEmptyView.visibility=View.GONE
            } else {
                binding.rvMyWallet.visibility=View.GONE
                binding.llEmptyView.visibility=View.VISIBLE
            }
            if (binding.swFresh != null) {
                binding.swFresh.finishRefresh()
            }

        })

    }

    override fun onLazyload() {

        arguments?.let {
            val orderStatuss = it.getString(MyWalletPagerFragment.WALLET_STATUS)
            XLog.e("onLazyLoad orderStatus===" + orderStatuss)
            orderMyStatus = orderStatuss
            matchId = it.getString("match_id")
            time = it.getString("time")
            timeCode = it.getString("timeCode")
            XLog.e("time===" + time)
            XLog.e("timeCode===" + timeCode)
        }
        getData()

    }

    @Subscribe
    fun funOrderUpdateTime(event: TimeStatusChangeEvent) {
        //更改状态 请求接口
        timeCode = event.timeUpdate
        getData()

    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)

    }


    override fun onRefresh(refreshlayout: RefreshLayout?) {

        binding.swFresh.setEnableLoadmore(true)
        page = 1
        haveMore = true
        getData()

    }


}