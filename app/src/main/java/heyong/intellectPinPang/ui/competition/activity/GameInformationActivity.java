package heyong.intellectPinPang.ui.competition.activity;

import androidx.annotation.Nullable;

import android.os.Bundle;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityGameInformationBinding;

/**
 * 打法信息
 */
public class GameInformationActivity extends BaseActivity <ActivityGameInformationBinding, BaseViewModel>{



    @Override
    public int getLayoutRes() {
        return R.layout.activity_game_information;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }
}
