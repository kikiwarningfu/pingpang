package heyong.intellectPinPang.ui.mine.activity.live

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import heyong.intellectPinPang.ui.BaseActivity
import heyong.handong.framework.base.BaseViewModel
import heyong.handong.framework.utils.SystemUtil
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityMyWalletBinding
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.mine.fragment.MyWalletPagerFragment
import heyong.lzy.imagepicker.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_my_wallet.*

/**
 * 钱包
 */
class MyWalletActivity : BaseActivity<ActivityMyWalletBinding, LiveViewModel>(),
    View.OnClickListener {
    var id: String = ""

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MyWalletActivity::class.java)
            context.startActivity(intent)
        }

    }

    //month money
    //day money
    // all money  zongjine
    //可提现  maymoney
    //累计收益 total  money
    override fun getLayoutRes(): Int {
        return R.layout.activity_my_wallet
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
        val version = SystemUtil.getVersion()
        if (version >= 6) {
            StatusBarUtil.statusBarLightMode(this)
            StatusBarUtil.transparencyBar(this) //设置状态栏全透明
        }

        mViewModel.walletInfo()



        mViewModel.walletInfoData.observe(this, Observer {
            if (it.data != null) {
                id = "" + it.data.id;
                mBinding.tvAllMoney.text = "" + it.data.allMoney
                mBinding.tvDayMoney.text = "" + it.data.dayMoney
                mBinding.tvMonthMoney.text = "" + it.data.monthMoney
                mBinding.tvTotalMoney.text = "" + it.data.totalMoney
                mBinding.tvMayMoney.text = "可提现余额：" + "${it.data.mayMoney}" + "元"
                initViewPager();
            }
        })

    }

    fun initViewPager() {
        //设置Adapter

        viewPager.offscreenPageLimit = 3
        val pagerAdapter: PagerAdapter = MyWalletPagerAdapter(supportFragmentManager)
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
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
        playOne()
        mBinding.viewPager.setCurrentItem(0)

    }

    private fun playThree() {
        mBinding.viewWithdrawRecord.visibility = View.VISIBLE
        mBinding.viewAll.visibility = View.GONE
        mBinding.viewRecordGet.visibility = View.GONE

    }

    private fun PlayTwo() {
        mBinding.viewWithdrawRecord.visibility = View.GONE
        mBinding.viewAll.visibility = View.GONE
        mBinding.viewRecordGet.visibility = View.VISIBLE

    }

    private fun playOne() {
        mBinding.viewWithdrawRecord.visibility = View.GONE
        mBinding.viewAll.visibility = View.VISIBLE
        mBinding.viewRecordGet.visibility = View.GONE

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_with_draw_record -> {
                playThree()
                mBinding.viewPager.setCurrentItem(2)
            }

            R.id.rl_get_record -> {
                PlayTwo()
                mBinding.viewPager.setCurrentItem(1)
            }
            R.id.rl_all -> {
                playOne()
                mBinding.viewPager.setCurrentItem(0)
            }
            R.id.tv_with_draw -> {
                if (!TextUtils.isEmpty(id)) {
                    WithDrawActivity.goWithDraw(this, id)
                }
            }
            R.id.iv_finish -> {
                finish()
            }
            R.id.tv_bind_account -> {
                //绑定账户
                BindAccountActivity.goActivity(this,id)
//                goActivity(BindAccountActivity::class.java)
            }
        }

    }

    val titles = arrayOf("全部", "收益记录", "提现记录")

    private inner class MyWalletPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {


        override fun getItem(p0: Int): Fragment {
            return MyWalletPagerFragment.newInstance("" + p0, id)
        }

        override fun getCount(): Int {
            return titles.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }
}