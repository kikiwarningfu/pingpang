package heyong.intellectPinPang.ui.friendcircle;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.friendcircle.CircleUserDetailAdapter;
import heyong.intellectPinPang.databinding.ActivityCircleUserDetailBinding;

/**
 * 朋友圈个人详情
 */
public class CircleUserDetailActivity extends BaseActivity<ActivityCircleUserDetailBinding, BaseViewModel> implements View.OnClickListener {

    CircleUserDetailAdapter mCircleUserDetailAdapter;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_circle_user_detail;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mCircleUserDetailAdapter=new CircleUserDetailAdapter();
        mBinding.rvUserList.setAdapter(mCircleUserDetailAdapter);
        mBinding.rvUserList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        mCircleUserDetailAdapter.setNewData(Arrays.asList("1","1"));
        mBinding.setListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_publish:


                break;
        }
    }
}
