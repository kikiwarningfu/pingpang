package heyong.intellectPinPang.ui.mine.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.elvishew.xlog.XLog
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import heyong.intellectPinPang.ui.LazyloadFragment
import heyong.intellectPinPang.R
import heyong.intellectPinPang.adapter.mine.MyLiveOrderAdapter
import heyong.intellectPinPang.bean.live.XlZBjieDanOrdeListBean
import heyong.intellectPinPang.databinding.FragmentMyLiveOrderBinding
import heyong.intellectPinPang.live.activity.LiveOrderDetailActivity
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.mine.event.OrderStatusChangeEvent
import heyong.intellectPinPang.ui.mine.event.TimeStatusChangeEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.text.SimpleDateFormat
import java.util.*

/**
 * 1  待直播  2 3 已取消  456  进行中  7 异常订单  8  已完成 我接取的订单
 */
class MyLiveOrderFragment : LazyloadFragment<FragmentMyLiveOrderBinding, LiveViewModel>(),
    OnRefreshListener {
    private var page = 1
    private var pageSize = 10
    private var haveMore = true
    var orderStatus: Int? = 0;
    private var orderTime = ""
    var list: MutableList<XlZBjieDanOrdeListBean.ListBean> = ArrayList()

    companion object {

        const val WALLET_STATUS = "wallet_status"

        fun newInstance(walletStatus: Int): MyLiveOrderFragment {

            val instance = MyLiveOrderFragment()
            val bundle = Bundle()
            bundle.putInt(WALLET_STATUS, walletStatus)
            instance.arguments = bundle
            return instance
        }
    }

    private val myLiveOrderAdapter: MyLiveOrderAdapter by lazy { MyLiveOrderAdapter() }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_my_live_order
    }

    override fun initView(savedInstanceState: Bundle?) {
        if (binding.swFresh.isRefreshing)
            binding.swFresh.finishRefresh()
        EventBus.getDefault().register(this)
        val simpledateFormat= SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date= Date(System.currentTimeMillis())
        orderTime=simpledateFormat.format(date)
        orderStatus = arguments?.let {
            var int = it.getInt(WALLET_STATUS, 0)
            if (int == 0) -2 else int - 1
        }
        if (orderStatus != null) {
            viewModel.orderStatus = orderStatus as Int
        }
        XLog.e(orderStatus)
        setUpRecycer()
        viewModel.getXlZhiboJiedanOrderListData.observe(this, Observer {
            if (it.errorCode.equals("200")) {
                if (it.data.list != null && it.data.list.size > 0) {
                    if (page == 1) {
                        list.clear()
                    }
                    list.addAll(it.data.list)

                    if (it.data.list != null && it.data.list.size < pageSize) {
                        haveMore = false
                        binding.swFresh.isEnableLoadmore = false
                    } else {
                        binding.swFresh.isEnableLoadmore = true
                    }
                    binding.rvMyWallet.layoutManager= LinearLayoutManager(
                        activity,
                        RecyclerView.VERTICAL, false
                    )
                    binding.rvMyWallet.adapter=myLiveOrderAdapter
                    myLiveOrderAdapter.setNewData(list)
                    if (binding.swFresh != null) {
                        binding.swFresh.finishRefresh()
                        binding.swFresh.finishLoadmore()
                    }
                    myLiveOrderAdapter.setOnItemChildClickListener(BaseQuickAdapter.OnItemChildClickListener { baseQuickAdapter, view, i ->

                        var data= myLiveOrderAdapter.data.get(i) as XlZBjieDanOrdeListBean.ListBean

                        activity?.let { it1 -> LiveOrderDetailActivity.start(it1,""+data.id) }
                    })
                } else {
                    if (page == 1) {
                        list.clear()
                    }
                    binding.swFresh.finishRefresh()
                    binding.swFresh.finishLoadmore()
                    myLiveOrderAdapter.setNewData(list)
                }

            } else {
                toast(it.message)
                if (page == 1) {
                    list.clear()
                }
                myLiveOrderAdapter.setNewData(list)
            }
            if (binding.swFresh != null) {
                binding.swFresh.finishRefresh()
            }


        })
    }


    private fun setUpRecycer() {
        binding.rvMyWallet.adapter = myLiveOrderAdapter
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
        myLiveOrderAdapter.setOnItemChildClickListener { _, view, position ->
            val item = myLiveOrderAdapter.getItem(position)!! as XlZBjieDanOrdeListBean.ListBean
            when(view?.id)
            {
                R.id.tv_order_detail->{
                    activity.let {
                        LiveOrderDetailActivity.start(it!!,""+item.id)

                    }
                }
            }
        }
//        myLiveOrderAdapter.setOnItemClickListener { _, view, position ->
//            val item = myLiveOrderAdapter.getItem(position)!!
//            activity.let {
//                //跳转
////                item.tableNum
//            }
//        }
    }

    //请求接口
    fun getData() {
        XLog.e("orderStatus=====" + orderStatus)
        if (orderStatus == -2) {
            viewModel.getXlZhiboJiedanOrderList("", "" + page, "" + pageSize, ""+orderTime)
        } else {
            viewModel.getXlZhiboJiedanOrderList(
                "" + (orderStatus!! + 1),
                "" + page,
                "" + pageSize,
                ""+orderTime
            )
        }


    }
    @Subscribe
    fun funOrderUpdateTime(event: TimeStatusChangeEvent) {
        //更改状态 请求接口
//        XLog.e(event.timeUpdate)
        orderTime=event.timeUpdate
        getData()
    }
    override fun onLazyload() {


        getData()

    }



    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onOrderStatusChange(event: OrderStatusChangeEvent) {
        //更改状态 请求接口
        getData()
    }

    override fun onRefresh(refreshlayout: RefreshLayout?) {
        binding.swFresh.setEnableLoadmore(true)
        page = 1
        haveMore = true
        getData()
        XLog.e(orderStatus)

    }

}