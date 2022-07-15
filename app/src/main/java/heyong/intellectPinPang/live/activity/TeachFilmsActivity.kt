package heyong.intellectPinPang.live.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import heyong.intellectPinPang.ui.BaseActivity
import heyong.handong.framework.base.BaseViewModel
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityWithDrawBinding

/**
 * 拍摄教学
 */
class TeachFilmsActivity : BaseActivity<ActivityWithDrawBinding, BaseViewModel>() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, TeachFilmsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_teach_films
    }

    override fun initView(savedInstanceState: Bundle?) {
    }
}