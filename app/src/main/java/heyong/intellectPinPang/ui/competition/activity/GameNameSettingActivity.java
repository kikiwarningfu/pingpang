package heyong.intellectPinPang.ui.competition.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityGameNameSettingBinding;

/**
 * 姓名
 */
public class GameNameSettingActivity extends BaseActivity<ActivityGameNameSettingBinding, BaseViewModel> implements View.OnClickListener {



    @Override
    public int getLayoutRes() {
        return R.layout.activity_game_name_setting;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
