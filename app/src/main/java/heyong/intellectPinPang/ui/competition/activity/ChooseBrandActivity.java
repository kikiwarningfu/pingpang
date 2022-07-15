package heyong.intellectPinPang.ui.competition.activity;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.Arrays;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.mine.ChooseBrandAdapter;
import heyong.intellectPinPang.databinding.ActivityChooseBrandBinding;

/**
 * 选择品牌（底板）
 */
public class ChooseBrandActivity extends BaseActivity<ActivityChooseBrandBinding, BaseViewModel> {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_choose_brand;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        ChooseBrandAdapter chooseBrandAdapter = new ChooseBrandAdapter();
        mBinding.rvList.setAdapter(chooseBrandAdapter);
        chooseBrandAdapter.setNewData(Arrays.asList("1", "1", "1", "1"));
    }
}
