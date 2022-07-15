package heyong.intellectPinPang.ui.mine.activity;

import androidx.annotation.Nullable;

import android.os.Bundle;
import android.view.View;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityContactUsBinding;

/**
 * 联系我们
 */
public class ContactUsActivity extends BaseActivity <ActivityContactUsBinding, BaseViewModel> implements View.OnClickListener {


    @Override
    public int getLayoutRes() {
        return R.layout.activity_contact_us;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}