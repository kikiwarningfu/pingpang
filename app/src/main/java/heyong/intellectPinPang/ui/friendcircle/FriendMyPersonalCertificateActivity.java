package heyong.intellectPinPang.ui.friendcircle;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityFriendMyPersonalCertificateBinding;

/**
 * 朋友圈我的认证
 */
public class FriendMyPersonalCertificateActivity extends BaseActivity<ActivityFriendMyPersonalCertificateBinding, BaseViewModel> implements View.OnClickListener {



    @Override
    public int getLayoutRes() {
        return R.layout.activity_friend_my_personal_certificate;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
    }

    @Override
    public void onClick(View v) {


    }
}
