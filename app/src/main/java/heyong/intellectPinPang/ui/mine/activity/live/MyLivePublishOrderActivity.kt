package heyong.intellectPinPang.ui.mine.activity.live

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bigkoo.pickerview.TimePickerView
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityMyLivePublishOrderBinding
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.mine.event.TimeStatusChangeEvent
import heyong.intellectPinPang.ui.mine.fragment.MyLivePublishOrderFragment
import kotlinx.android.synthetic.main.activity_my_wallet.*
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*

/**
 * 我发布的订单
 * 	0取消订单,1取消订单,2待付款,3付款成功,4.待直播,5直播中,6直播完成(待确认) 7退款中 8退款成功 9退款失败 10已完成
 * 0 1  已取消   2  待支付  3 未接取  4 已接取 5 比赛中  6  待确认  10  已完成  7 8 9 异常订单
 */
class MyLivePublishOrderActivity : BaseActivity<ActivityMyLivePublishOrderBinding, LiveViewModel>(),
    View.OnClickListener {


    override fun getLayoutRes(): Int {
        return R.layout.activity_my_live_publish_order
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MyLivePublishOrderActivity::class.java)
            context.startActivity(intent)


        }

    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
        initViewPager();
        val simpledateFormat=SimpleDateFormat("yyyy-MM")
        val date=Date(System.currentTimeMillis())
        mBinding.tvDayMonth.text=simpledateFormat.format(date)
    }

    fun initViewPager() {
        //设置Adapter
        viewPager.offscreenPageLimit = 5
        val pagerAdapter: PagerAdapter = MyLiveOrderPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> playOne()
                    1 -> PlayTwo()
                    2 -> playThree()
                    3 -> playFour()
                    4 -> playFive()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })

        playOne()
        mBinding.viewPager.setCurrentItem(0)

    }


    fun clearView() {
        mBinding.viewAll.visibility = View.GONE
        mBinding.viewToPay.visibility = View.GONE
        mBinding.viewToBeCompleted.visibility = View.GONE
        mBinding.viewToBeConfirmed.visibility = View.GONE
        mBinding.viewCompleted.visibility = View.GONE
        mBinding.tvAll.setTextColor(Color.parseColor("#666666"))
        mBinding.tvCompleted.setTextColor(Color.parseColor("#666666"))
        mBinding.tvToBeCompleted.setTextColor(Color.parseColor("#666666"))
        mBinding.tvToBeConfirmed.setTextColor(Color.parseColor("#666666"))
        mBinding.tvToPay.setTextColor(Color.parseColor("#666666"))

    }

    private fun playFive() {
        clearView()
        mBinding.viewCompleted.visibility = View.VISIBLE
        mBinding.tvCompleted.setTextColor(Color.parseColor("#4795ED"))
    }

    private fun playFour() {

        clearView()
        mBinding.viewToBeConfirmed.visibility = View.VISIBLE
        mBinding.tvToBeConfirmed.setTextColor(Color.parseColor("#4795ED"))

    }

    private fun playThree() {

        clearView()
        mBinding.viewToBeCompleted.visibility = View.VISIBLE
        mBinding.tvToBeCompleted.setTextColor(Color.parseColor("#4795ED"))
    }

    private fun PlayTwo() {
        clearView()
        mBinding.viewToPay.visibility = View.VISIBLE
        mBinding.tvToPay.setTextColor(Color.parseColor("#4795ED"))
    }

    private fun playOne() {
        clearView()
        mBinding.viewAll.visibility = View.VISIBLE
        mBinding.tvAll.setTextColor(Color.parseColor("#4795ED"))
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_all -> {
                playOne()
                mBinding.viewPager.setCurrentItem(0)
            }
            R.id.ll_to_pay -> {
                PlayTwo()
                mBinding.viewPager.setCurrentItem(1)
            }
            R.id.ll_to_be_completed -> {
                playThree()
                mBinding.viewPager.setCurrentItem(2)
            }
            R.id.ll_to_be_confirmed -> {
                playFour()
                mBinding.viewPager.setCurrentItem(3)
            }
            R.id.ll_completed -> {
                playFive()
                mBinding.viewPager.setCurrentItem(4)
            }
            R.id.ll_date -> {

                val pvTime = TimePickerView.Builder(
                    this
                ) { dateMax, v ->
                    val time = getTimeMinute(dateMax)
                    val WanZhengtime = getWanZhengTimeMinute(dateMax)
                    mBinding.tvDayMonth.text = time
                    EventBus.getDefault().post(TimeStatusChangeEvent(WanZhengtime))
                }
                    .setType(TimePickerView.Type.YEAR_MONTH) //默认全部显示
                    .setCancelText("取消") //取消按钮文字
                    .setSubmitText("确定") //确认按钮文字
                    .setContentSize(20) //滚轮文字大小
                    .setTitleSize(20) //标题文字大小
                    //                        .setTitleText("请选择时间")//标题文字
                    .setOutSideCancelable(true) //点击屏幕，点在控件外部范围时，是否取消显示
                    .isCyclic(true) //是否循环滚动
                    .setTextColorCenter(Color.parseColor("#FDD363")) //设置选中项的颜色
                    .setTextColorOut(Color.parseColor("#666666"))
                    .setTitleColor(Color.BLACK) //标题文字颜色
                    .setSubmitColor(Color.BLUE) //确定按钮文字颜色
                    .setCancelColor(Color.BLUE) //取消按钮文字颜色
                    .setLoop(false)
                    .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                    //                        .isDialog(true)//是否显示为对话框样式
                    .build()
                pvTime.setDate(Calendar.getInstance()) //注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。

                pvTime.show()

            }
        }


    }

    fun getTimeMinute(date: Date?): String? { //可根据需要自行截取数据显示
        val format = SimpleDateFormat("yyyy-MM")
        return format.format(date)
    }

    fun getWanZhengTimeMinute(date: Date?): String? { //可根据需要自行截取数据显示
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return format.format(date)
    }

    private inner class MyLiveOrderPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        val titles = arrayOf("全部", "待直播", "进行中", "已完成", "")

        override fun getItem(p0: Int): Fragment {
            return MyLivePublishOrderFragment.newInstance(p0)
        }

        override fun getCount(): Int {
            return 5
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return ""
        }
    }
}