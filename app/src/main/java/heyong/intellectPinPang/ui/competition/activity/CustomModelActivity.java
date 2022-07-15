package heyong.intellectPinPang.ui.competition.activity;

import androidx.annotation.Nullable;

import android.os.Bundle;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityCustomModelBinding;

/**
 * 自定义型号
 */
public class CustomModelActivity extends BaseActivity<ActivityCustomModelBinding, BaseViewModel> {



    @Override
    public int getLayoutRes() {
        return R.layout.activity_custom_model;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }
}
