package heyong.intellectPinPang.ui.friendcircle;

import android.os.Bundle;

import androidx.annotation.Nullable;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityHomePageUserBinding;

/**
 * 个人主页
 */
public class HomePageUserActivity extends BaseActivity<ActivityHomePageUserBinding, PublishEditCircleViewModel> {


    @Override
    public int getLayoutRes() {
        return R.layout.activity_home_page_user;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }
}
