package heyong.intellectPinPang.ui.mine.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elvishew.xlog.XLog
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.xq.fasterdialog.dialog.CustomDialog
import heyong.intellectPinPang.R
import heyong.intellectPinPang.adapter.mine.MyWalletAdapter
import heyong.intellectPinPang.bean.live.MoneyDetailListBean
import heyong.intellectPinPang.databinding.FragmentMyWalletPagerBinding
import heyong.intellectPinPang.event.LiveOrderStatusEvent
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.LazyloadFragment
import heyong.intellectPinPang.ui.competition.activity.EventSignUpActivity
import heyong.intellectPinPang.ui.mine.event.TimeStatusChangeEvent
import heyong.intellectPinPang.utils.AntiShakeUtils
import heyong.intellectPinPang.widget.AlertDialog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MyWalletPagerFragment : LazyloadFragment<FragmentMyWalletPagerBinding, LiveViewModel>(),
    OnRefreshListener {

    private var page = 1
    private var pageSize = 10
    private var haveMore = true
    private var matchId = "";
    private var orderMyStatus = ""
    var mutableList: MutableList<MoneyDetailListBean.ListBean> = mutableListOf()
    var TAG = LiveOrderListFragment::class.java.simpleName
    var customDialogConfirm: CustomDialog? = null


    companion object {

        const val WALLET_STATUS = "wallet_status"

        fun newInstance(
            walletStatus: String,
            matchId: String

        ): MyWalletPagerFragment {

            val instance = MyWalletPagerFragment()
            val bundle = Bundle()
            bundle.putString(WALLET_STATUS, "" + walletStatus)
            bundle.putString("match_id", matchId)
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

    private val liveOrderListAdapter: MyWalletAdapter by lazy { MyWalletAdapter() }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_my_wallet_pager
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView(savedInstanceState: Bundle?) {
        if (binding.swFresh.isRefreshing)
            binding.swFresh.finishRefresh()
        EventBus.getDefault().register(this)
        myDialog = AlertDialog(activity!!).builder()

        setUpRecycer()
        Log.e(TAG, "initView: matchTime==" + viewModel.matchTime.get())

    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume: matchTime==" + viewModel.matchTime.get())
    }

    @RequiresApi(Build.VERSION_CODES.M)
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

        }
        liveOrderListAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.iv_fail_logo -> {
                    val bean: MoneyDetailListBean.ListBean = liveOrderListAdapter.data.get(position)
                    when (bean.status) {
                        "0" -> {
                            //失败
                            showNeedBindDialog(
                                "该提现未通过或不符合提现规则，提" +
                                        "现失败您可以尝试重新提现，对此订" +
                                        "单有疑问可联系客服"
                            )
                        }
                        "1" -> {

                        }
                        "2" -> {
                            //审核中
                            showNeedBindDialog("提现审核中，预计1-3个工作日到账")
                        }
                    }


                }
            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun showNeedBindDialog(text: String) {

        val customDialogConfirm =
            CustomDialog(activity!!)
        customDialogConfirm.setCustomView(R.layout.pop_to_bind)
        customDialogConfirm.show()
        val customView = customDialogConfirm.customView
        val tvSure = customView.findViewById<TextView>(R.id.tv_submit)
        val tvCancel = customView.findViewById<TextView>(R.id.tv_cancel)
        val tvExplain = customView.findViewById<TextView>(R.id.tv_explain)
        tvSure.text = "我知道了"
        tvCancel.text = "联系客服"
        tvExplain.text = "" + text
        tvSure.setOnClickListener {
            customDialogConfirm.dismiss()
        }
        tvCancel.setOnClickListener {
            customDialogConfirm.dismiss()
            if (TextUtils.isEmpty(phoneNumber)) {
                toast("呼叫电话不能为空")
            } else {
                callPhoneDialog()
            }
        }

    }

    var customDialogConfirmCall: CustomDialog? = null

    @RequiresApi(Build.VERSION_CODES.M)
    private fun callPhoneDialog() {
        customDialogConfirmCall = CustomDialog(activity!!)
        customDialogConfirmCall!!.setCustomView(R.layout.pop_call_phone)
        customDialogConfirmCall!!.show()
        val customView: View = customDialogConfirmCall!!.getCustomView()
        showAskConfirmView(customView, customDialogConfirmCall)
    }

    val CALL_PHONE = 1002 //Call permission code
    var phoneNumber = "";

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showAskConfirmView(customView: View, customDialog: CustomDialog?) {
        val tvCancel = customView.findViewById<TextView>(R.id.tv_cancel)
        val llCallPhone = customView.findViewById<LinearLayout>(R.id.ll_call_phone)
        val tvCallNumber = customView.findViewById<TextView>(R.id.tv_exit_club)
        tvCallNumber.text = "呼叫 $phoneNumber"
        llCallPhone.setOnClickListener(View.OnClickListener { v ->
            if (AntiShakeUtils.isInvalidClick(v)) return@OnClickListener
            //                toast("拨打电话");
            if (ContextCompat.checkSelfPermission(
                    activity!!,
                    Manifest.permission.CALL_PHONE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), CALL_PHONE)
            } else {
                callPhone("" + phoneNumber)
                customDialog?.dismiss()
            }
        })
        tvCancel.setOnClickListener(View.OnClickListener { v ->
            if (AntiShakeUtils.isInvalidClick(v)) return@OnClickListener
            customDialog?.dismiss()
        })
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    fun callPhone(phoneNum: String) {
        val intent = Intent(Intent.ACTION_CALL)
        val data = Uri.parse("tel:$phoneNum")
        intent.data = data
        startActivity(intent)
    }

    private var myDialog //弹窗
            : AlertDialog? = null

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == EventSignUpActivity.CALL_PHONE) {
            for (grant in grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    if (customDialogConfirm != null) {
                        customDialogConfirm!!.dismiss()
                    }
                    EventSignUpActivity.showMissingDialog(myDialog, activity!!)
                } else {
                    callPhone("" + phoneNumber)
                    if (customDialogConfirm != null) {
                        customDialogConfirm!!.dismiss()
                    }
                }
            }
        }
    }

    //请求接口
    fun getData() {
        XLog.e(viewModel.matchTime.get())
        viewModel.getXlZhiboMoneyDetailsList(
            "" + matchId,
            "" + orderMyStatus,
            "" + page,
            "" + pageSize
        )
        viewModel.getXlZhiboMoneyDetailsListData.observe(this, Observer {

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
            if (binding.swFresh != null) {
                binding.swFresh.finishRefresh()
            }
            if (liveOrderListAdapter.data.size > 0) {
                binding.rvMyWallet.visibility = View.VISIBLE
                binding.llEmptyView.visibility = View.GONE
            } else {
                binding.rvMyWallet.visibility = View.GONE
                binding.llEmptyView.visibility = View.VISIBLE
            }

        })

    }

    override fun onLazyload() {

        arguments?.let {
            val orderStatuss = it.getString(MyWalletPagerFragment.WALLET_STATUS)
            XLog.e("onLazyLoad orderStatus===" + orderStatuss)
            orderMyStatus = orderStatuss
            matchId = it.getString("match_id")

        }
        getData()

    }

    @Subscribe
    fun funOrderUpdateTime(event: TimeStatusChangeEvent) {
        //更改状态 请求接口
//        timeCode = event.timeUpdate
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