package heyong.intellectPinPang.ui.mine.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.IdentifyBean;
import heyong.intellectPinPang.databinding.ActivityRealNameSuccessBinding;
import heyong.intellectPinPang.ui.mine.MineViewModel;
import heyong.intellectPinPang.widget.GlideCircleTransform;

/**
 * 实名认证成功
 */
public class RealNameSuccessActivity extends BaseActivity<ActivityRealNameSuccessBinding, MineViewModel> {

    public static final String TAG = RealNameSuccessActivity.class.getSimpleName();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_real_name_success;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mViewModel.getIdentityInfo();

        mViewModel.getIdentityInfoLiveData.observe(this, new Observer<ResponseBean<IdentifyBean>>() {
            @Override
            public void onChanged(ResponseBean<IdentifyBean> identifyBeanResponseBean) {
//                if (identifyBeanResponseBean.isSuccess()) {
                if (identifyBeanResponseBean.getErrorCode().equals("200")) {

                    IdentifyBean data = identifyBeanResponseBean.getData();

                    if (data != null) {
                        RequestOptions options = new RequestOptions();
                        options.dontAnimate();
                        options.transform(new GlideCircleTransform(RealNameSuccessActivity.this, 2, Color.parseColor("#4795ED")));
                        options.error(R.drawable.morentouxiang);
                        Glide.with(RealNameSuccessActivity.this)
                                .load(data.getOwnImg())
                                .apply(options)
                                .into(mBinding.ivLogo);
                        mBinding.tvName.setText("" + data.getName());
                        String identificationNum = data.getIdentificationNum();
                        if (!TextUtils.isEmpty(identificationNum)) {
                            String showNumber = identificationNum.substring(0, 3) + "********" + identificationNum.substring(11);
                            mBinding.tvIdentifyNumber.setText("" + showNumber);
                        } else {
                            mBinding.tvIdentifyNumber.setText("");
                        }
                        if (data.getSex().equals("1")) {
                            mBinding.tvSex.setText("男");
                        } else {
                            mBinding.tvSex.setText("女");
                        }
                        String bornDate = data.getBornDate();
                        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM月-日 HH:mm:ss",
                                Locale.CHINA);
                        Date date;
                        try {
                            date = sdr.parse(bornDate);
                            long l = date.getTime();
                            String stf = String.valueOf(l);
                            SimpleDateFormat sdr2 = new SimpleDateFormat("yyyy-MM-dd");
                            long lcc = Long.valueOf(stf);
                            String time = sdr2.format(new Date(lcc));
                            mBinding.tvBirth.setText("" + time);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG, "onChanged: " + e.getMessage());
                        }
                    } else {
                        toast("" + identifyBeanResponseBean.getMessage());
                    }
                } else {
                    toast("" + identifyBeanResponseBean.getMessage());
                }
            }
        });


    }
}