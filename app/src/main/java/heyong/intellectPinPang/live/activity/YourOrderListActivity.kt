package heyong.intellectPinPang.live.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import heyong.intellectPinPang.R
import heyong.intellectPinPang.adapter.game.NewLivePageAdapter
import heyong.intellectPinPang.databinding.ActivityYourOrderListBinding
import heyong.intellectPinPang.event.SwipMessageEvent
import heyong.intellectPinPang.live.fragment.LiveHallFragment
import heyong.intellectPinPang.live.fragment.NotChooseEventFragment
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.BaseActivity
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * 下单列表
 */
class YourOrderListActivity : BaseActivity<ActivityYourOrderListBinding, LiveViewModel>(),
    ViewPager.OnPageChangeListener, View.OnClickListener {

    private var fragments: MutableList<Fragment>? = null
    var matchId = ""

    companion object {
        fun startActivity(context: Context, matchId: String) {
            val intent = Intent(context, YourOrderListActivity::class.java)
            intent.putExtra("matchid", matchId)
            context.startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_your_order_list
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
        matchId = intent.getStringExtra("matchid")
        fragments = ArrayList<Fragment>()
        fragments!!.add(NotChooseEventFragment(matchId))
        fragments!!.add(LiveHallFragment(matchId))
        mBinding.listener = this
        mBinding.viewPager.setAdapter(NewLivePageAdapter(supportFragmentManager, fragments))
        mBinding.viewPager.addOnPageChangeListener(this)
        mBinding.viewPager.setCurrentItem(0)


        initClearView()
        playOne()

    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
    }

    private fun initClearView() {
        mBinding.tvNotChooseEvent.setTextColor(Color.parseColor("#333333"))
        mBinding.tvNotChooseEvent.textSize = 15F

        mBinding.tvLiveBroadcastHall.setTextColor(Color.parseColor("#333333"))
        mBinding.tvLiveBroadcastHall.textSize = 15F


    }

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> playOne()
            1 -> PlayTwo()
        }
    }

    private fun PlayTwo() {

        initClearView()
        mBinding.tvLiveBroadcastHall.setTextColor(Color.parseColor("#4B5CC4"))
        mBinding.tvLiveBroadcastHall.textSize = 18F
        EventBus.getDefault().post(SwipMessageEvent())
    }

    private fun playOne() {

        initClearView()
        mBinding.tvNotChooseEvent.setTextColor(Color.parseColor("#4B5CC4"))
        mBinding.tvNotChooseEvent.textSize = 18F
    }

    override fun onPageScrollStateChanged(p0: Int) {




    }

    //MyFriendCircleActivity

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_not_choose_event -> {
                //未选赛事
                playOne()
                mBinding.viewPager.setCurrentItem(0)

            }
            R.id.tv_live_broadcast_hall -> {
                //直播大厅
                PlayTwo()
                mBinding.viewPager.setCurrentItem(1)

            }
            R.id.iv_finish -> {
                finish()
            }
        }

    }


}