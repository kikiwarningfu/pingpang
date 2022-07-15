package heyong.intellectPinPang.ui.mine.activity.live

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.R

import heyong.intellectPinPang.adapter.live.LiveOrderListAdapter
import heyong.intellectPinPang.adapter.live.OfferRewardOrderListConditionAdapter
import heyong.intellectPinPang.bean.live.OfferRewardOrderListConditionBean
import heyong.intellectPinPang.databinding.ActivityLiveOrderListallBinding
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.mine.event.TimeStatusChangeEvent
import heyong.intellectPinPang.ui.mine.fragment.LiveOrderListFragment
import org.greenrobot.eventbus.EventBus

/**
 * 直播订单列表
 */
class LiveOrderListActivity : BaseActivity<ActivityLiveOrderListallBinding, LiveViewModel>(),
    View.OnClickListener {
    var mMatchId = "";

    //    pop_accept_live_order.xml   接单弹窗
    companion object {
        fun startActivity(context: Context, matchId: String) {
            val intent = Intent(context, LiveOrderListActivity::class.java)
            intent.putExtra("matchId", matchId)
            context.startActivity(intent)
        }
    }


    val conditionAdapter: OfferRewardOrderListConditionAdapter by lazy {
        OfferRewardOrderListConditionAdapter()
    }
    val adapter: LiveOrderListAdapter by lazy {
        LiveOrderListAdapter()
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_live_order_listall
    }


    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
        mMatchId = intent.getStringExtra("matchId")

        mViewModel.offerRewardOrderListCondition(mMatchId)

        mViewModel.offerRewardOrderListConditionData.observe(this, Observer {

            if (it.errorCode.equals("200")) {
                if (it.data.size > 0) {
                    for (index in 0 until it.data.size) {
                        if (index == 0) {
                            it.data[index].isSelect = true
                        } else {
                            it.data[index].isSelect = false
                        }
                    }
                    mViewModel.matchTime.set("" + it.data[0].value)
                    mViewModel.matchTimeCode.set("" + it.data[0].code)
                    mBinding.rvLiveOrderList.adapter = conditionAdapter
                    mBinding.rvLiveOrderList.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    conditionAdapter.setNewData(it.data)

                    conditionAdapter.setOnItemChildClickListener { _, view, position ->
                        val item =
                            conditionAdapter.data.get(position) as OfferRewardOrderListConditionBean
                        when (view.id) {
                            R.id.ll_onClick -> {
                                //设置选中状态 刷新数据 请求
                                for (index in 0 until it.data.size) {
                                    it.data[index].isSelect = false
                                }
                                item.isSelect = true
                                mViewModel.matchTime.set("" + it.data[position].value)
                                mViewModel.matchTimeCode.set("" + it.data[position].code)
                                conditionAdapter.data.set(position, item)
                                conditionAdapter.setNewData(conditionAdapter.data)

                                mBinding.viewPager.setCurrentItem(position)
                            }
                        }
                    }

                    mBinding.viewPager.offscreenPageLimit = 0
                    val pagerAdapter: PagerAdapter =
                        MyLiveOrderListPageAdapter(supportFragmentManager)
                    mBinding.viewPager.adapter = pagerAdapter
                    mBinding.viewPager.addOnPageChangeListener(object :
                        ViewPager.OnPageChangeListener {
                        override fun onPageScrolled(
                            position: Int,
                            positionOffset: Float,
                            positionOffsetPixels: Int
                        ) {

                        }

                        override fun onPageSelected(position: Int) {
                            val item1 =
                                conditionAdapter.data.get(position) as OfferRewardOrderListConditionBean
                            for (index in 0 until it.data.size) {
                                it.data[index].isSelect = false
                            }
                            item1.isSelect = true
                            conditionAdapter.data.set(position, item1)
                            conditionAdapter.setNewData(conditionAdapter.data)
                            mViewModel.codeTime = conditionAdapter.data.get(position).value
                            EventBus.getDefault()
                                .post(TimeStatusChangeEvent(conditionAdapter.data.get(position).code))
//                            EventBus.getDefault().postSticky(LiveOrderStatusEvent(""+conditionAdapter.data.get(position).code))
                        }

                        override fun onPageScrollStateChanged(state: Int) {
                        }

                    })
                    mViewModel.codeTime = ""
//                    EventBus.getDefault().postSticky(LiveOrderStatusEvent(""))

                    mBinding.viewPager.setCurrentItem(0)
                }

            }


        })

    }


    private inner class MyLiveOrderListPageAdapter(
        fm: FragmentManager

    ) : FragmentPagerAdapter(fm) {


        override fun getItem(p0: Int): Fragment {
            val item = conditionAdapter.data.get(p0) as OfferRewardOrderListConditionBean
            val timeCode = item.code
            val time = item.value
            return LiveOrderListFragment.newInstance(item.code, mMatchId, "" + timeCode, "" + time)
        }

        override fun getCount(): Int {

            return conditionAdapter.data.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return ""
        }
    }

    override fun onClick(v: View?) {

    }


}