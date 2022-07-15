package heyong.intellectPinPang.ui.friendcircle;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.friendcircle.FansFriendCircleAdapter;
import heyong.intellectPinPang.databinding.ActivityFansFriendCircleBinding;

public class FansFriendCircleActivity extends BaseActivity<ActivityFansFriendCircleBinding, BaseViewModel> {
    FansFriendCircleAdapter mFansFriendCircleAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_fans_friend_circle;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mFansFriendCircleAdapter = new FansFriendCircleAdapter();
        mBinding.mRecyclerView.setAdapter(mFansFriendCircleAdapter);
        mBinding.mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mFansFriendCircleAdapter.setNewData(Arrays.asList("1", "1", "1", "1"));
    }
}
