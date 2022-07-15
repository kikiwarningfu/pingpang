package heyong.intellectPinPang.ui.friendcircle;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.friendcircle.AttentionFriendCircleAdapter;
import heyong.intellectPinPang.databinding.ActivityAttentionFriendCircleBinding;

/**
 * 关注
 */
public class AttentionFriendCircleActivity extends BaseActivity <ActivityAttentionFriendCircleBinding, BaseViewModel>{
    AttentionFriendCircleAdapter mAttentionFriendCircleAdapter;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_attention_friend_circle;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mAttentionFriendCircleAdapter=new AttentionFriendCircleAdapter();
        mBinding.mRecyclerView.setAdapter(mAttentionFriendCircleAdapter);
        mBinding.mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        mAttentionFriendCircleAdapter.setNewData(Arrays.asList("1","1","1"));


    }
}
