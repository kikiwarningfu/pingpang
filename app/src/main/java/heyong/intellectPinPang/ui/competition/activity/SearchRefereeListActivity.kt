package heyong.intellectPinPang.ui.competition.activity


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elvishew.xlog.XLog
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.xq.fasterdialog.dialog.CustomDialog
import heyong.intellectPinPang.R
import heyong.intellectPinPang.adapter.SearchRefereeListAdapter
import heyong.intellectPinPang.adapter.game.RefereeDisplayAdapter
import heyong.intellectPinPang.bean.SearchRefreeListBean
import heyong.intellectPinPang.bean.gsonbean.RefereeDisplayTimeBean
import heyong.intellectPinPang.databinding.ActivitySearchRefreeListBinding
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel
import heyong.intellectPinPang.ui.mine.activity.ReferenceAllMatchActivity
import heyong.intellectPinPang.utils.AntiShakeUtils
import heyong.intellectPinPang.utils.MyDateUtils
import heyong.intellectPinPang.utils.SearchHelper
import heyong.intellectPinPang.widget.CenterLinearLayoutManager
import heyong.intellectPinPang.widget.MatchRadioTrunsationView
import heyong.intellectPinPang.widget.MyDividerItemDecoration
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader
import kotlinx.android.synthetic.main.fragment_bowling.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * 裁判员调台
 */
class SearchRefereeListActivity :
    BaseActivity<ActivitySearchRefreeListBinding, HomePageViewModel>(),
    View.OnClickListener, OnRefreshListener {
    var itemType = "2"//默认显示单打  1团体 2单打 34双打

    var listDanDa = listOf<String>("双打", "团体")
    var listTuanTi = listOf<String>("单打", "双打")
    var listShuangDa = listOf<String>("单打", "团体")
    var clickPosition = 0

    var mRefreshAnimHeader: MyRefreshAnimHeader? = null
    private var pageNum = 1
    private val pageSize = 10
    private var haveMore = true

    private var strMatchId = ""
    private var timeCodeDisplay = ""
    var list: MutableList<SearchRefreeListBean.ArrangesBean> = ArrayList()

    var beginCalendar: Calendar? = null
    var endCalendar: Calendar? = null
    private var times = ""
    var mSixDiD: MyDividerItemDecoration? = null
    private var strTableNum = ""
    private var searchText = ""
    private var startTimes = ""
    private var myOwnId = ""

    private val searchRefefreeListAdapter by lazy {
        SearchRefereeListAdapter()
    }

    private val refereeDisplayAdapter by lazy {
        RefereeDisplayAdapter()
    }

    companion object {
        fun startActivity(
            context: Context,
            matchId: String,
            timeCodeDisplay: String,
            strTableNum: String, times: String
        ) {
            val intent = Intent(context, SearchRefereeListActivity::class.java)
            intent.putExtra("matchId", matchId)
            intent.putExtra("timeCodeDisplay", timeCodeDisplay)
            intent.putExtra("strTableNum", strTableNum)
            intent.putExtra("times", times)
            context.startActivity(intent)
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_search_refree_list
    }

    var scrollPosition = 0
    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
        strMatchId = intent.getStringExtra("matchId")
        timeCodeDisplay = intent.getStringExtra("timeCodeDisplay")
        strTableNum = intent.getStringExtra("strTableNum")
        startTimes = intent.getStringExtra("times")

        XLog.e("times===" + startTimes)
        mSixDiD = MyDividerItemDecoration(
            this, 5,
            resources.getColor(R.color.white)
        )
        mRefreshAnimHeader = MyRefreshAnimHeader(this)
        if (mBinding.swFresh.isRefreshing) {
            mBinding.swFresh.finishRefresh()
        }
        mBinding.swFresh.setOnRefreshListener(this)
        mBinding.swFresh.setOnLoadmoreListener {
            if (haveMore) {
                pageNum++
                mViewModel.searchTableNumArrange(
                    "" + searchText, "" + strMatchId,
                    "" + itemType, "" + startTimes, "" + pageNum, "" + pageSize
                );

            } else {
                mBinding.swFresh.finishLoadmore()
            }
        }

        mViewModel.dateLimit("" + strMatchId)
        mViewModel.dateLimitLiveData.observe(this, Observer {
            if (it.errorCode.equals("200")) {
                var data = it.data
                if (data != null) {
                    val beginTime = data.beginTime
                    val endTime = data.endTime
                    val beginStrTime = MyDateUtils.getDateToString(beginTime)
                    val endStrTime = MyDateUtils.getDateToString(endTime)
                    Log.e(
                        ReferenceAllMatchActivity.TAG,
                        "onChanged: beginTime==$beginStrTime===endTime$endStrTime"
                    )
                    beginCalendar = MyDateUtils.getCalendar(beginStrTime)
                    endCalendar = MyDateUtils.getCalendar(endStrTime)
                    val datesBetweenTwoDate: MutableList<Date>
                    if (beginCalendar == endCalendar) {
                        datesBetweenTwoDate = ArrayList()
                        datesBetweenTwoDate.add(beginCalendar!!.getTime())
                    } else {
                        datesBetweenTwoDate = MyDateUtils.getDatesBetweenTwoDate(
                            beginCalendar!!.getTime(),
                            endCalendar!!.getTime()
                        )
                    }
                    Log.e(
                        ReferenceAllMatchActivity.TAG,
                        "onChanged: " + Gson().toJson(datesBetweenTwoDate)
                    )
                    Log.e(
                        ReferenceAllMatchActivity.TAG, "onChanged: beginCalendar===" + beginCalendar
                                + "endCalendar===" + endCalendar
                    )
                    val refereeDisplayTimeBeanList: MutableList<RefereeDisplayTimeBean> =
                        ArrayList()
                    for (i in datesBetweenTwoDate.indices) {
                        val refereeDisplayTimeBean = RefereeDisplayTimeBean()
                        val date = datesBetweenTwoDate[i]
                        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                        val transformDate = simpleDateFormat.format(date)
                        val simpleDateFormat2 = SimpleDateFormat("MM月dd日")
                        val transformDate2 = simpleDateFormat2.format(date)

                        if (timeCodeDisplay != null && !TextUtils.isEmpty(timeCodeDisplay)) {
//                            XLog.e("timeCodeDisplay===" + timeCodeDisplay)
//                            XLog.e("transformDate2===" + transformDate2)
                            if (transformDate2 == timeCodeDisplay) {
                                times = transformDate
                                refereeDisplayTimeBean.isSelect = true
                                scrollPosition = i
                            } else {
                                refereeDisplayTimeBean.isSelect = false
                            }

                        } else {
                            if (i == 0) {
                                times = transformDate
                                refereeDisplayTimeBean.isSelect = true
                            } else {
                                refereeDisplayTimeBean.isSelect = false
                            }
                        }
                        refereeDisplayTimeBean.time = transformDate2
                        refereeDisplayTimeBean.upTime = transformDate
                        refereeDisplayTimeBeanList.add(refereeDisplayTimeBean)
                    }
                    refereeDisplayAdapter.setNewData(refereeDisplayTimeBeanList)
                    smoothMoveToPosition(mBinding.rvTime, scrollPosition)

                }

            } else {
                toast("" + it.message)
            }


        })
        mBinding.rvTime.layoutManager =
            CenterLinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        mBinding.rvTime.adapter = refereeDisplayAdapter
        refereeDisplayAdapter.setOnItemClickListener { adapter, view, position ->
            val fastClick = isFastClick()
            if (!fastClick) {
                val data1 = refereeDisplayAdapter.data
                for (i in refereeDisplayAdapter.data.indices) {
                    val refereeDisplayTimeBean1 = data1[i]
                    refereeDisplayTimeBean1.isSelect = false
                    data1[i] = refereeDisplayTimeBean1
                }
                data1[position].isSelect = true
                refereeDisplayAdapter.setNewData(data1)
                times = data1[position].upTime
                pageNum = 1
                haveMore = true
                startTimes = times + " 00:00:00"
                XLog.e("times====" + times + " startTimes====" + startTimes)
                showLoading()
                mViewModel.searchTableNumArrange(
                    "" + searchText, "" + strMatchId,
                    "" + itemType, "" + startTimes, "" + pageNum, "" + pageSize
                );
            }
        }
        mBinding.rvRefereeAllMatch.layoutManager = LinearLayoutManager(
            this, RecyclerView.VERTICAL,
            false
        )
        mViewModel.searchTableNumArrangeLiveData.observe(this, Observer {
            dismissLoading()
            if (it.errorCode.equals("200")) {
                var arranges = it.data.arranges
                if (it.data.arranges != null && it.data.arranges.size > 0) {
                    if (pageNum == 1) {
                        list.clear()
                    }
                    if (arranges != null) {
                        for (item in arranges) {
                            if (item.itemType.equals("1") || item.itemType.equals("2")) {
                                item.itemType = "0"
                            } else {
                                item.itemType = "1"
                            }
                        }
                        list.addAll(arranges)
                        if (arranges != null && arranges.size < pageSize) {
                            haveMore = false
                            mBinding.swFresh.isEnableLoadmore = false
                        } else {
                            mBinding.swFresh.isEnableLoadmore = true
                        }

                        searchRefefreeListAdapter!!.setNewData(list)
                    }
                    if (mBinding.swFresh != null) {
                        mBinding.swFresh.finishRefresh()
                        mBinding.swFresh.finishLoadmore()
                    }
                } else {
                    if (pageNum == 1) {
                        list.clear()
                    }
                    mBinding.swFresh.finishRefresh()
                    mBinding.swFresh.finishLoadmore()

                    searchRefefreeListAdapter!!.setNewData(list)
                }

            } else {
                toast("" + it.message)
                if (pageNum == 1) {
                    list.clear()
                }
                searchRefefreeListAdapter.setNewData(list)
            }

            if (mBinding.swFresh != null) {
                mBinding.swFresh.finishRefresh()
            }
            if (searchRefefreeListAdapter.data.size > 0) {
                mBinding.llEmptyView.visibility = View.GONE
                mBinding.llHaveList.visibility = View.VISIBLE
            } else {
                mBinding.llEmptyView.visibility = View.VISIBLE
                mBinding.llHaveList.visibility = View.GONE
            }
        })
        mBinding.rvRefereeAllMatch.adapter = searchRefefreeListAdapter
        searchRefefreeListAdapter.setLocalTableNum(strTableNum)
        XLog.e("strTableNum====" + strTableNum)
        searchRefefreeListAdapter.setOnItemChildClickListener { baseQuickAdapter, view, i ->
            val fastClick = isFastClick()
            if (!fastClick) {
                when (view.id) {
                    R.id.tv_game_end_status -> {
                        var bean =
                            searchRefefreeListAdapter.data.get(i) as SearchRefreeListBean.ArrangesBean
                        myOwnId=""+bean.id
                        if (bean.tableNum != strTableNum.toInt()) {
                            showPopWindow(ArrayList())

                        }
                    }
                }

            }


        }




        mViewModel.tuningStationLiveData.observe(this, Observer {
            if (it.errorCode.equals("200")) {
                toast("调台成功")
                pageNum = 1
                haveMore = true
                mBinding.rvRefereeAllMatch.scrollTo(0, 0)
                mViewModel.searchTableNumArrange(
                    "" + searchText, "" + strMatchId,
                    "" + itemType, "" + startTimes, "" + pageNum, "" + pageSize
                );
            } else {
                toast("" + it.message)
            }
        })
        mBinding.etText.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchText = mBinding.etText.text.toString()
                haveMore = true
                pageNum = 1
                mViewModel.searchTableNumArrange(
                    "" + searchText, "" + strMatchId,
                    "" + itemType, "" + startTimes, "" + pageNum, "" + pageSize
                );
                hideSoftKeyBoard(this)

                true
            }
            false
        })

    }

    var customDialogConfirm: CustomDialog? = null
    fun showFinishMarking(type: String) {

        customDialogConfirm = CustomDialog(this)
        customDialogConfirm!!.setCustomView(R.layout.pop_mode_select_coach)
        customDialogConfirm!!.show()
        val customView = customDialogConfirm!!.customView
        var rlModeOne = customView.findViewById<RelativeLayout>(R.id.rl_mode_one)
        var rlModeTwo = customView.findViewById<RelativeLayout>(R.id.rl_mode_two)
        var tvModeOne = customView.findViewById<TextView>(R.id.tv_mode_one)
        var tvModeTwo = customView.findViewById<TextView>(R.id.tv_mode_two)
        var llClose = customView.findViewById<LinearLayout>(R.id.ll_close)
        if (type.equals("1")) {
            tvModeOne.text = listTuanTi[0]
            tvModeTwo.text = listTuanTi[1]
        } else if (type.equals("2")) {
            tvModeOne.text = listDanDa[0]
            tvModeTwo.text = listDanDa[1]
        } else {
            tvModeOne.text = listShuangDa[0]
            tvModeTwo.text = listShuangDa[1]
        }
        llClose.setOnClickListener(View.OnClickListener { v ->
            if (AntiShakeUtils.isInvalidClick(v))
                return@OnClickListener

            if (customDialogConfirm != null) {
                customDialogConfirm!!.dismiss()
            }
        })
        rlModeOne.setOnClickListener(View.OnClickListener { v ->
            if (AntiShakeUtils.isInvalidClick(v))
                return@OnClickListener
            if (type.equals("1")) {
                //单打
                itemType = "2"
                mBinding.tvModeText.text = "单打"
            } else if (type.equals("2")) {
                //shuangda
                itemType = "3"
                mBinding.tvModeText.text = "双打"

            } else {
//                danda
                itemType = "2"
                mBinding.tvModeText.text = "单打"
            }
            searchText = mBinding.etText.text.toString()
            if (!TextUtils.isEmpty(searchText)) {
                haveMore = true
                pageNum = 1
                mViewModel.searchTableNumArrange(
                    "" + searchText, "" + strMatchId,
                    "" + itemType, "" + startTimes, "" + pageNum, "" + pageSize
                );
                hideSoftKeyBoard(this)
            }
            if (customDialogConfirm != null) {
                customDialogConfirm!!.dismiss()
            }
        })
        rlModeTwo.setOnClickListener(View.OnClickListener { v ->
            if (AntiShakeUtils.isInvalidClick(v))
                return@OnClickListener
            if (type.equals("1")) {
                //shuangda
                itemType = "3"
                mBinding.tvModeText.text = "双打"
            } else if (type.equals("2")) {
                //tuanti
                itemType = "1"
                mBinding.tvModeText.text = "团体"
            } else {
                //tuanti
                itemType = "1"
                mBinding.tvModeText.text = "团体"
            }

            searchText = mBinding.etText.text.toString()
            if (!TextUtils.isEmpty(searchText)) {
                haveMore = true
                pageNum = 1
                mViewModel.searchTableNumArrange(
                    "" + searchText, "" + strMatchId,
                    "" + itemType, "" + startTimes, "" + pageNum, "" + pageSize
                );
                hideSoftKeyBoard(this)
            }
            if (customDialogConfirm != null) {
                customDialogConfirm!!.dismiss()
            }
        })

    }


    private fun showPopWindow(data: List<String>) {
        val pvTime = MatchRadioTrunsationView.Builder(
            strTableNum, data, mSixDiD, wdith, this
        ) { time, v, item ->
            Log.e(ReferenceAllMatchActivity.TAG, "onTimeSelect: $time")
//            if (TextUtils.isEmpty(item) || item == null) {
//                toast("请选择台号")
//            } else {
                //调台  选择时间判断
                mViewModel.tuningStation(
                    "" + strMatchId,
                    "$times $time:00",
                    "" + myOwnId,
                    "" + strTableNum
                )
//            }
        }
            .setCancelText("取消") //取消按钮文字
            .setSubmitText("确定") //确认按钮文字
            .setContentSize(13) //滚轮文字大小
            .setTitleSize(13) //标题文字大小
            //                        .setTitleText("请选择时间")//标题文字
            .setOutSideCancelable(true) //点击屏幕，点在控件外部范围时，是否取消显示
            .isCyclic(true) //是否循环滚动
            .setTextColorCenter(Color.parseColor("#333333")) //设置选中项的颜色
            .setTextColorOut(Color.parseColor("#666666"))
            .setTitleColor(Color.BLACK) //标题文字颜色
            .setSubmitColor(Color.BLUE) //确定按钮文字颜色
            .setCancelColor(Color.BLUE) //取消按钮文字颜色
            .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
            //                        .isDialog(true)//是否显示为对话框样式
            .build()
        pvTime.setDate(Calendar.getInstance()) //注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_search_click -> {

                //调用接口
                searchText = mBinding.etText.text.toString()
                haveMore = true
                pageNum = 1
                mViewModel.searchTableNumArrange(
                    "" + searchText, "" + strMatchId,
                    "" + itemType, "" + startTimes, "" + pageNum, "" + pageSize
                );
                hideSoftKeyBoard(this)

            }
            R.id.ll_danda -> {
                showFinishMarking(itemType)
            }
            R.id.iv_finish -> {
                finish()
            }
        }
    }

    /**
     * 隐藏软键盘
     */
    private fun hideSoftKeyBoard(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val inputMethodManager =
                activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    override fun onRefresh(p0: RefreshLayout?) {
        haveMore = true
        pageNum = 1
        mViewModel.searchTableNumArrange(
            "" + searchText, "" + strMatchId,
            "" + itemType, "" + startTimes, "" + pageNum, "" + pageSize
        );

    }

    //目标项是否在最后一个可见项之后
    var mShouldScroll = false

    //记录目标项位置
    var mToPosition = 0
    fun smoothMoveToPosition(mRecyclerView: RecyclerView, position: Int) {
        // 第一个可见位置
        val firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0))
        // 最后一个可见位置
        val lastItem =
            mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.childCount - 1))
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前，使用smoothScrollToPosition
            mRecyclerView.smoothScrollToPosition(position)
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后，最后一个可见项之前
            val movePosition = position - firstItem
            if (movePosition >= 0 && movePosition < mRecyclerView.childCount) {
                val top = mRecyclerView.getChildAt(movePosition).top
                // smoothScrollToPosition 不会有效果，此时调用smoothScrollBy来滑动到指定位置
                mRecyclerView.smoothScrollBy(0, top)
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position)
            mToPosition = position
            mShouldScroll = true
        }
    }
}