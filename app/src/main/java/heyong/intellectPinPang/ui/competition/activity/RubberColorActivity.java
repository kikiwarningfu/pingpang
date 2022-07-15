package heyong.intellectPinPang.ui.competition.activity;

import androidx.annotation.Nullable;

import android.os.Bundle;
import android.view.View;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityRubberColorBinding;

/**
 * 套胶颜色
 */
public class RubberColorActivity extends BaseActivity <ActivityRubberColorBinding, BaseViewModel> implements View.OnClickListener {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_rubber_color;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
