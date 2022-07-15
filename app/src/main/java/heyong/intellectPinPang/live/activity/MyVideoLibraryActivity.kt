package heyong.intellectPinPang.live.activity

import android.os.Bundle
import android.view.View
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.R
import heyong.intellectPinPang.adapter.live.MyVideoLibraryAdapter
import heyong.intellectPinPang.databinding.ActivityMyVideoLibraryBinding
import heyong.intellectPinPang.databinding.ActivityWithDrawZfbBinding
import heyong.intellectPinPang.live.model.LiveViewModel

/**
 * 我的视频库
 */
class MyVideoLibraryActivity : BaseActivity<ActivityMyVideoLibraryBinding, LiveViewModel>(),
    View.OnClickListener {

    override fun getLayoutRes(): Int {
        return R.layout.activity_my_video_library
    }
    val myvideoLibraryAdapter: MyVideoLibraryAdapter by lazy {
        MyVideoLibraryAdapter()
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener=this



    }

    override fun onClick(v: View?) {

    }
}