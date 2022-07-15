package heyong.intellectPinPang.ui.friendcircle;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.friendcircle.CircleUserDetailAdapter;
import heyong.intellectPinPang.databinding.ActivityPublishFriendCircleListBinding;

/**
 * 发布
 */
public class PublishFriendCircleListActivity extends BaseActivity<ActivityPublishFriendCircleListBinding, BaseViewModel> {
    CircleUserDetailAdapter mCircleUserDetailAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_publish_friend_circle_list;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mCircleUserDetailAdapter=new CircleUserDetailAdapter();
        mBinding.mRecyclerView.setAdapter(mCircleUserDetailAdapter);
        mBinding.mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        mCircleUserDetailAdapter.setNewData(Arrays.asList("1","1","1"));

    }
}
