package heyong.intellectPinPang.ui.competition.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityPersonalInformationNewBinding;

/**
 * 完善个人资料
 */
public class PersonalInformationNewActivity extends BaseActivity<ActivityPersonalInformationNewBinding, BaseViewModel> implements View.OnClickListener {


    @Override
    public int getLayoutRes() {
        return R.layout.activity_personal_information_new;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

        mBinding.setListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.rl_avatar://头像

                break;
            case R.id.rl_nick_name://昵称
                startActivity(new Intent(PersonalInformationNewActivity.this,SetUpNickNameActivity.class));

                break;
            case R.id.rl_sign://个性签名
                startActivity(new Intent(PersonalInformationNewActivity.this,SetUpPersonalSignActivity.class));

                break;
                case R.id.rl_sign_information://参赛信息
                    Intent intent=new Intent(PersonalInformationNewActivity.this,FillGameInformationActivity.class);
                startActivity(intent);

                break;
            case R.id.rl_qicai://器材信息填写

                startActivity(new Intent(PersonalInformationNewActivity.this,FillEquipmentInformationActivity.class));
                break;
            case R.id.rl_dafa://打法信息
                startActivity(new Intent(PersonalInformationNewActivity.this,GameInformationActivity.class));

                break;
            case R.id.rl_shoose://鞋服信息
                startActivity(new Intent(PersonalInformationNewActivity.this,ShoesClothesInformationActivity.class));

                break;
            case R.id.rl_shouxie_sign://签名
                startActivity(new Intent(PersonalInformationNewActivity.this,EnterSignatureActivity.class));

                break;
        }
    }


}
