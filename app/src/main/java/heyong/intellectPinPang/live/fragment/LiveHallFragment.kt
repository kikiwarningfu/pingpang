package heyong.intellectPinPang.live.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.elvishew.xlog.XLog
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import heyong.handong.framework.utils.ImageLoader
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.live.LiveHallBean
import heyong.intellectPinPang.databinding.FragmentLiveHallBinding
import heyong.intellectPinPang.databinding.ItemLiveHallBinding
import heyong.intellectPinPang.event.SwipMessageEvent
import heyong.intellectPinPang.live.activity.WatchLiveRecordActivity
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.soundnet.SingleHostLiveActivity
import heyong.intellectPinPang.ui.BaseFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.ArrayList
//直播大厅
class LiveHallFragment(private val matchIds: String) :
    BaseFragment<FragmentLiveHallBinding, LiveViewModel>(),
    View.OnClickListener, OnRefreshListener {


    var page = 1
    var pageSize = 10
    private var haveMore = true

    var mutableList: MutableList<LiveHallBean.ListBean> = ArrayList()
    var codeType = 0
    val liveOrderListAdapter: liveBroadcastHallAdapter by lazy {
        liveBroadcastHallAdapter()
    }

    inner class liveBroadcastHallAdapter :
        BaseQuickAdapter<LiveHallBean.ListBean, BaseViewHolder>(R.layout.item_live_hall) {
        override fun convert(p0: BaseViewHolder, p1: LiveHallBean.ListBean) {
            val binding = DataBindingUtil.bind<ItemLiveHallBinding>(p0?.itemView!!)

            binding!!.tvMatchTitle.text = "${p1.matchTitle}"
            ImageLoader.loadImage(binding!!.ivLogo,""+p1.fengmian,R.drawable.img_live_dafault,R.drawable.img_live_dafault)
            var itemType = p1.itemType
            binding.tvTime.text=""+p1.beginTime
            var status = p1.status
            if (status.equals("1")) {
                binding.tvOrderStatus.text = "待开播"
                binding.tvOrderStatus.setBackgroundResource(R.drawable.shape_gray_solid_corners)
            } else if (status.equals("2")) {
                binding.tvOrderStatus.text = "直播中"
                p0.addOnClickListener(R.id.tv_order_status)
                binding.tvOrderStatus.setBackgroundResource(R.drawable.shape_club_blue)
            } else {
                binding.tvOrderStatus.text = "看回放"
                p0.addOnClickListener(R.id.tv_order_status)
                binding.tvOrderStatus.setBackgroundResource(R.drawable.shape_orange_jiedan)
            }
            if(!TextUtils.isEmpty(p1.tableNum))
            {
                binding!!.tvTableNum.text=""+p1.tableNum+"台"
            }else
            {
                binding!!.tvTableNum.text=""
            }
            if (itemType.equals("1") || itemType.equals("2")) {
                if (TextUtils.isEmpty(p1.player1)) {
                    binding!!.tvLeftUserName.text = "      未知      "
                    binding!!.tvLeftClubName.visibility = View.GONE
                } else {
                    binding!!.tvLeftUserName.text = "${p1.player1}"
                    binding!!.tvLeftClubName.visibility = View.VISIBLE
                    binding!!.tvLeftClubName.text = "${p1.leftClubName}"
                }
                if (TextUtils.isEmpty(p1.player3)) {
                    binding!!.tvRightUserName.text = "      未知      "
                    binding!!.tvRightClubName.visibility = View.GONE
                } else {
                    binding!!.tvRightUserName.text = "${p1.player3}"
                    binding!!.tvRightClubName.visibility = View.VISIBLE
                    binding!!.tvRightClubName.text = "${p1.rightClubName}"

                }
            } else {

                if (TextUtils.isEmpty(p1.player1)) {
                    binding!!.tvLeftUserName.text = "      未知      "
                    binding!!.tvLeftClubName.visibility = View.GONE
                } else {
                    binding!!.tvLeftUserName.text = "${p1.player1}" + "/n" + "${p1.player2}"
                    binding!!.tvLeftClubName.visibility = View.VISIBLE
                    binding!!.tvLeftClubName.text = "${p1.leftClubName}"
                }
                if (TextUtils.isEmpty(p1.player3)) {
                    binding!!.tvRightUserName.text = "      未知      "
                    binding!!.tvRightClubName.visibility = View.GONE
                } else {
                    binding!!.tvRightUserName.text = "${p1.player3}" + "/n" + "${p1.player4}"
                    binding!!.tvRightClubName.visibility = View.VISIBLE
                    binding!!.tvRightClubName.text = "${p1.rightClubName}"

                }

//                binding!!.tvTableNum.text="${p1.tableNum}台"
//                binding!!.tvTime.text=""+p1.orderTime.substring(11)

                //1未开始2进行中3已结束（回放）




            }
        }

    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_live_hall
    }

    override fun onResume() {
        super.onResume()
        getData()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.listener = this
        EventBus.getDefault().register(this)
        initClearView()
        binding.tvAllLive.setTextColor(Color.parseColor("#666666"))
        binding.viewAllLive.visibility = View.VISIBLE

        if (binding.swFresh.isRefreshing) {
            binding.swFresh.finishRefresh()
        }
        binding.rvLiveOrder.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding.rvLiveOrder.adapter = liveOrderListAdapter
        codeType = 0
        binding.swFresh.setOnRefreshListener(this)
        binding.swFresh.setOnLoadmoreListener {
            if (haveMore) {
                page++
                getData()

            } else {
                binding.swFresh.finishLoadmore()
            }
        }
        getData()

        liveOrderListAdapter.setOnItemChildClickListener { adapter, view, position ->
            var bean: LiveHallBean.ListBean = liveOrderListAdapter.data.get(position)

            when (view.id) {
                R.id.tv_order_status -> {
                    //todo  跳转到看直播 还是直播回放
                    when (bean.status) {

                        "1"->{

                        }
                        "2"->{
                            //直播中   看直播
                            SingleHostLiveActivity.goActivity(activity!!, false, "" + id)

                        }
                        else->{
                            //看回放
                            WatchLiveRecordActivity.goActivity(activity!!, ""+id)

                        }




                    }


                }
            }


        }
        viewModel.liveBroadcastMatchAgainstInfoListtData.observe(this, Observer {
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
            XLog.e("size===="+liveOrderListAdapter.data.size)


        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun messageEvent(event: SwipMessageEvent) {
        page = 1
        getData()

    }

    fun getData() {

        viewModel.liveBroadcastMatchAgainstInfoList(
            matchIds,
            "",
            "" + page,
            "" + pageSize,
            "",
            "" + codeType
        )

    }

    fun initClearView() {
        binding.tvAllLive.setTextColor(Color.parseColor("#666666"))
        binding.viewAllLive.visibility = View.GONE

        binding.tvYigouLive.setTextColor(Color.parseColor("#666666"))
        binding.viewYigouLive.visibility = View.GONE
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.ll_all_live -> {
                initClearView()
                binding.tvAllLive.setTextColor(Color.parseColor("#666666"))
                binding.viewAllLive.visibility = View.VISIBLE
                codeType = 0
                getData()
            }

            R.id.ll_yigou_live -> {
                initClearView()
                binding.tvYigouLive.setTextColor(Color.parseColor("#666666"))
                binding.viewYigouLive.visibility = View.VISIBLE
                codeType = 3
                getData()
            }

        }


    }

    override fun onRefresh(p0: RefreshLayout?) {
        page = 1;
        haveMore = true;
        getData()
    }
}