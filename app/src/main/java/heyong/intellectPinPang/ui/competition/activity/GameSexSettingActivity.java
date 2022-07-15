package heyong.intellectPinPang.ui.competition.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityGameSexSettingBinding;

/**
 * 性别
 */
public class GameSexSettingActivity extends BaseActivity<ActivityGameSexSettingBinding, BaseViewModel> implements View.OnClickListener {
    private int sexType = 0;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_game_sex_setting;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_sex_man://男
                sexType = 1;
                mBinding.ivSexMan.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_sex_woman://女

                sexType = 2;
                clearView();
                mBinding.ivSexWoman.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_save:
                if (sexType == 0) {
                    toast("请选择性别");
                    return;
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("sexType", "" + sexType);
                    setResult(RESULT_OK, intent);
                    finish();
                }

                break;
        }
    }

    public void clearView() {
        mBinding.ivSexMan.setVisibility(View.GONE);
        mBinding.ivSexWoman.setVisibility(View.GONE);
    }
}
