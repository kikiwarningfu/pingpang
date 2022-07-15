package heyong.intellectPinPang.ui.mine.activity.live

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityLiveGameListBinding
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.ui.mine.event.TimeStatusChangeEvent
import heyong.intellectPinPang.ui.mine.fragment.LiveGameListFragment
import heyong.intellectPinPang.ui.mine.fragment.MyLivePublishOrderFragment
import kotlinx.android.synthetic.main.activity_my_wallet.*
import org.greenrobot.eventbus.EventBus

/**
 * 所有直播列表-赛事
 */
class LiveGameListActivity : BaseActivity<ActivityLiveGameListBinding, LiveViewModel>(),
    View.OnClickListener {

    var inputType = 0
    var searchTitle = ""


    override fun getLayoutRes(): Int {
        return R.layout.activity_live_game_list
    }

    override fun initView(savedInstanceState: Bundle?) {

        mBinding.listener = this
        mBinding.viewPager.offscreenPageLimit = 3
        val pagerAdapter: PagerAdapter = MyLiveOrderPagerAdapter(supportFragmentManager)
        mBinding.viewPager.adapter = pagerAdapter
        mBinding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        initClearView()
                        playOne()
                    }
                    1 -> {
                        initClearView()
                        playTwo()
                    }
                    2 -> {
                        initClearView()
                        playThree()
                    }

                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })

        initClearView()
        playOne()
        mBinding.viewPager.setCurrentItem(0)


    }


    fun initClearView() {
        mBinding.tvAll.setTextColor(Color.parseColor("#333333"))
        mBinding.viewAll.visibility = View.GONE
        mBinding.tvZhuanye.setTextColor(Color.parseColor("#333333"))
        mBinding.viewZhuanye.visibility = View.GONE
        mBinding.tvYeyu.setTextColor(Color.parseColor("#333333"))
        mBinding.viewYeyu.visibility = View.GONE
    }


    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.ll_search -> {

                val intent = Intent(this, SearchLiveGameListActivity::class.java)
                startActivityForResult(intent, 1)
            }

            R.id.ll_all -> {
                initClearView()
                playOne()
                mBinding.viewPager.setCurrentItem(0)
            }
            R.id.ll_zhuanye -> {
                initClearView()
                playTwo()
                mBinding.viewPager.setCurrentItem(1)
            }
            R.id.ll_yeyu -> {
                initClearView()
                playThree()
                mBinding.viewPager.setCurrentItem(2)
            }
            R.id.iv_clear -> {
                mBinding.ivLogo.visibility = View.VISIBLE
                mBinding.tvName.text = ""
                mBinding.ivClear.visibility = View.GONE
                searchTitle = ""
                EventBus.getDefault()
                    .post(TimeStatusChangeEvent(""))
            }
            R.id.iv_finish -> {
                finish()
            }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {
                searchTitle = data!!.getStringExtra("textData")
                if (!TextUtils.isEmpty(searchTitle)) {
                    mBinding.ivLogo.visibility = View.GONE
                    mBinding.tvName.text = "${searchTitle}"
                    mBinding.ivClear.visibility = View.VISIBLE
                    EventBus.getDefault()
                        .post(TimeStatusChangeEvent(searchTitle))
                }
            }
        }
    }

    private fun playThree() {
        inputType = 2
        mBinding.tvYeyu.setTextColor(Color.parseColor("#4795ED"))
        mBinding.viewYeyu.visibility = View.VISIBLE
    }

    private fun playTwo() {
        inputType = 1
        mBinding.tvZhuanye.setTextColor(Color.parseColor("#4795ED"))
        mBinding.viewZhuanye.visibility = View.VISIBLE
    }

    private fun playOne() {
        inputType = 0
        mBinding.tvAll.setTextColor(Color.parseColor("#4795ED"))
        mBinding.viewAll.visibility = View.VISIBLE
    }

    private inner class MyLiveOrderPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        val titles = arrayOf("全部", "专业级", "业余级")

        override fun getItem(p0: Int): Fragment {
            return LiveGameListFragment.newInstance(p0)
        }

        override fun getCount(): Int {
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return ""
        }
    }
}