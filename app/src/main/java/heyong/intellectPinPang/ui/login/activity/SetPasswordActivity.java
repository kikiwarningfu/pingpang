package heyong.intellectPinPang.ui.login.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivitySetPasswordBinding;
import heyong.intellectPinPang.ui.mine.MineViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.CommonUtilis;

/**
 * 设置密码     设置新密码共用一个界面
 */
public class SetPasswordActivity extends BaseActivity<ActivitySetPasswordBinding, MineViewModel> implements View.OnClickListener {
    private boolean isShowOne = false;
    private boolean isShowTwo = false;
    public static final String ACCOUNT = "account";
    private String account = "";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_set_password;
    }

    @Override
    public View[] filterViewByIds() {
        View[] views = {mBinding.ivCloseOne,mBinding.ivCloseTwo};
        return views;
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_password_one, R.id.et_password_two};
        return ids;
    }


    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        mBinding.tvPasswordOneTishi.setVisibility(View.GONE);
        mBinding.tvPasswordTwoTishi.setVisibility(View.GONE);
        account = getIntent().getStringExtra(ACCOUNT);

        /*隐藏 不可见*/
        mBinding.etPasswordOne.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mBinding.etPasswordTwo.setTransformationMethod(PasswordTransformationMethod.getInstance());

        initCodeVisibleOne(mBinding.etPasswordOne, mBinding.ivCloseOne);
        initCodeVisibleTwo(mBinding.etPasswordTwo, mBinding.ivCloseTwo);

        mBinding.etPasswordOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    mBinding.ivCloseOne.setVisibility(View.VISIBLE);
                } else if (s.length() == 0) {
                    mBinding.ivCloseOne.setVisibility(View.GONE);
                }
            }
        });
        mBinding.etPasswordTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    mBinding.ivCloseTwo.setVisibility(View.VISIBLE);
                } else if (s.length() == 0) {
                    mBinding.ivCloseTwo.setVisibility(View.GONE);
                }
            }
        });
        mViewModel.updatePasswordSettingLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    toast("设置成功");
                    finish();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
    }

    private void initCodeVisibleOne(EditText etTelCodeLogin, ImageView ivTelCodeLogisn) {
        etTelCodeLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mBinding.etPasswordOne.getText().toString().length() >= 1) {
                        ivTelCodeLogisn.setVisibility(View.VISIBLE);
                    } else {
                        ivTelCodeLogisn.setVisibility(View.GONE);
                    }
                } else {
                    ivTelCodeLogisn.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initCodeVisibleTwo(EditText etTelCodeLogin, ImageView ivTelCodeLogisn) {
        etTelCodeLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mBinding.etPasswordTwo.getText().toString().length() >= 1) {
                        ivTelCodeLogisn.setVisibility(View.VISIBLE);
                    } else {
                        ivTelCodeLogisn.setVisibility(View.GONE);
                    }
                } else {
                    ivTelCodeLogisn.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:

                finish();
                break;
            case R.id.iv_eyes_one:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                /*如果显示为true*/
                if (isShowOne) {
                    /*开始不可见*/
                    /*设置为可见*/
                    ImageLoader.loadImage(mBinding.ivEyesOne, R.drawable.img_password_invisible);

                    isShowOne = false;
                    mBinding.etPasswordOne.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    ImageLoader.loadImage(mBinding.ivEyesOne, R.drawable.img_password_visible);

                    isShowOne = true;
                    mBinding.etPasswordOne.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

                break;
            case R.id.iv_eyes_two:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (isShowTwo) {
                    isShowTwo = false;
                    ImageLoader.loadImage(mBinding.ivEyesTwo, R.drawable.img_password_invisible);
                    mBinding.etPasswordTwo.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    isShowTwo = true;
                    ImageLoader.loadImage(mBinding.ivEyesTwo, R.drawable.img_password_visible);
                    mBinding.etPasswordTwo.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.tv_login:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                /*一个一个的判断 */
                String etOne = getOneEdiText();
                String etTwo = getTwoEdiText();
                if (etOne.length() < 8) {
                    mBinding.tvPasswordOneTishi.setVisibility(View.VISIBLE);
                    mBinding.tvPasswordOneTishi.setText("密码不能少于8位");
                    return;
                } else {
                    mBinding.tvPasswordOneTishi.setVisibility(View.GONE);
                }
                if (etTwo.length() < 8) {
                    mBinding.tvPasswordTwoTishi.setVisibility(View.VISIBLE);
                    mBinding.tvPasswordTwoTishi.setText("密码不能少于8位");
                    return;
                } else {
                    mBinding.tvPasswordTwoTishi.setVisibility(View.GONE);
                }

                formatJudge(etOne, mBinding.tvPasswordOneTishi);
                formatJudge(etTwo, mBinding.tvPasswordTwoTishi);
                if (etOne.equals(etTwo)) {
                    mBinding.tvPasswordTwoTishi.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(account) && !account.equals("null")) {
                        mViewModel.updatePasswordSetting("" + etOne, "" + account);
                    } else {
                        mViewModel.updatePasswordSetting("" + etOne, "");
                    }
                } else {
                    mBinding.tvPasswordTwoTishi.setText("密码不一致");
                    mBinding.tvPasswordTwoTishi.setVisibility(View.VISIBLE);
                    return;
                }
                break;
            case R.id.iv_close_one:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.etPasswordOne.setText("");
                mBinding.ivCloseOne.setVisibility(View.GONE);

                break;
            case R.id.iv_close_two:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.etPasswordTwo.setText("");
                mBinding.ivCloseTwo.setVisibility(View.GONE);

                break;
        }
    }

    private void formatJudge(String etOne, TextView tishi) {
        if (etOne.length() < 8) {
            tishi.setVisibility(View.VISIBLE);
            tishi.setText("密码不能少于8位");
        } else {
            tishi.setVisibility(View.GONE);
        }
        boolean numeric = CommonUtilis.isNumeric(etOne);
        if (numeric) {
            tishi.setVisibility(View.VISIBLE);
            tishi.setText("密码不能为纯数字");
        } else {
            tishi.setVisibility(View.GONE);
        }

        boolean aChar = CommonUtilis.isChar(etOne);
        if (aChar) {
            tishi.setVisibility(View.VISIBLE);
            tishi.setText("密码不能为纯字母");
        } else {
            tishi.setVisibility(View.GONE);
        }
        return;
    }


    public String getOneEdiText() {
        return mBinding.etPasswordOne.getText().toString().trim();
    }

    public String getTwoEdiText() {
        return mBinding.etPasswordTwo.getText().toString().trim();
    }


}