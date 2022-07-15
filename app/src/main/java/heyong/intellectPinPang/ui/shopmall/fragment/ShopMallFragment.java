package heyong.intellectPinPang.ui.shopmall.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import heyong.intellectPinPang.ui.BaseFragment;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.FragmentShopMallBinding;

/**
 * @Name：eyoubao
 * @Description：商城  http://www.xlttsports.com/cwap/
 * @Author：whf 修改人：whf
 * 修改备注：
 */
public class ShopMallFragment extends BaseFragment
        <FragmentShopMallBinding, BaseViewModel> {

    public static ShopMallFragment newInstance() {

        Bundle args = new Bundle();

        ShopMallFragment fragment = new ShopMallFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_shop_mall;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }
}
