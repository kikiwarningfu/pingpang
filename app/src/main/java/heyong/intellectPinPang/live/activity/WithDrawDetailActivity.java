package heyong.intellectPinPang.live.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.live.LiveUserWithDrawalDetailBean;
import heyong.intellectPinPang.databinding.ActivityWithDrawDetailBinding;
import heyong.intellectPinPang.live.model.LiveViewModel;
import heyong.intellectPinPang.utils.CommonUtilis;

/**
 * 提现详情界面UI
 */
public class WithDrawDetailActivity extends BaseActivity<ActivityWithDrawDetailBinding, LiveViewModel> {

    public static final String REQUEST_ID = "request_id";
    private String strRequestId = "";

    public static void goWithDrawDetail(Context context, String strRequestId) {
        Intent intent = new Intent(context, WithDrawDetailActivity.class);
        intent.putExtra(WithDrawDetailActivity.REQUEST_ID, "" + strRequestId);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_with_draw_detail;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        strRequestId = getIntent().getStringExtra(REQUEST_ID);
        mViewModel.liveUserWithDrawalDetail("" + strRequestId);

        mViewModel.liveUserWithDrawalDetailData.observe(this, new Observer<ResponseBean<LiveUserWithDrawalDetailBean>>() {
            @Override
            public void onChanged(ResponseBean<LiveUserWithDrawalDetailBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    LiveUserWithDrawalDetailBean data = responseBean.getData();
                    if (data != null) {
                        int status = data.getStatus();
                        mBinding.tvWithDrawMoney.setText("¥"+ CommonUtilis.getTwoDecimal(String.valueOf(data.getMoney())));
                        //"status":"int //提现状态  0=提现中 1=提现完成 2=提现驳回",
                        mBinding.tvApplyTime.setText(""+data.getCreateTime());
                        if (status == 0) {
                            //提现中
                            mBinding.tvRefundMoney.setText("发起提现");
                            mBinding.tvTimeOne.setText("" + data.getCreateTime());

                            mBinding.tvShenhe.setText("提现中");
                            mBinding.tvShenhe.setTextColor(Color.parseColor("#ff4795ed"));
                            mBinding.tvTimeTwo.setText("" + data.getApplyingTime());
                            mBinding.tvTimeTwo.setTextColor(Color.parseColor("#ff4795ed"));
                            mBinding.tvTimeThree.setVisibility(View.GONE);
                            mBinding.tvRefundSuccess.setText("提现成功");
                            mBinding.tvRefundSuccess.setTextColor(Color.parseColor("#CCCCCC"));

                        } else if (status == 1) {
                            mBinding.tvRefundMoney.setText("发起提现");
                            mBinding.tvTimeOne.setText("" + data.getCreateTime());

                            mBinding.tvShenhe.setText("提现中");
                            mBinding.tvShenhe.setTextColor(Color.parseColor("#ff4795ed"));
                            mBinding.tvTimeTwo.setText("" + data.getApplyingTime());
                            mBinding.tvTimeTwo.setTextColor(Color.parseColor("#ff4795ed"));
                            mBinding.tvRefundSuccess.setText("提现成功");
                            mBinding.tvRefundSuccess.setTextColor(Color.parseColor("#ff4795ed"));
                            mBinding.tvTimeThree.setText(""+data.getEndTime());

                        } else if (status == 2) {
                            mBinding.tvRefundMoney.setText("发起提现");
                            mBinding.tvTimeOne.setText("" + data.getCreateTime());

                            mBinding.tvShenhe.setText("提现中");
                            mBinding.tvShenhe.setTextColor(Color.parseColor("#ff4795ed"));
                            mBinding.tvTimeTwo.setText("" + data.getApplyingTime());
                            mBinding.tvTimeTwo.setTextColor(Color.parseColor("#ff4795ed"));
                            mBinding.tvRefundSuccess.setText("提现失败");
                            mBinding.tvRefundSuccess.setTextColor(Color.parseColor("#ff4795ed"));
                            mBinding.tvTimeThree.setText(""+data.getEndTime());
                        }

                    } else {
                        toast("" + responseBean.getMessage());
                    }
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
    }
}