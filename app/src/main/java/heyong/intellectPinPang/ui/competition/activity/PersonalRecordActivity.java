package heyong.intellectPinPang.ui.competition.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.mine.PersonalRecordAdapter;
import heyong.intellectPinPang.databinding.ActivityPersonalRecordBinding;

/**
 * 个人战绩
 */
public class PersonalRecordActivity extends BaseActivity<ActivityPersonalRecordBinding, BaseViewModel> {
    PersonalRecordAdapter mPersonalRecordAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_personal_record;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }
}
