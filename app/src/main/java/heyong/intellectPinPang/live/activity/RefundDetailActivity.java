package heyong.intellectPinPang.live.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.live.LiveUserRefundDetailBean;
import heyong.intellectPinPang.databinding.ActivityRefundDetailBinding;
import heyong.intellectPinPang.live.model.LiveViewModel;
import heyong.intellectPinPang.utils.CommonUtilis;

/**
 * 退款详情
 */
public class RefundDetailActivity extends BaseActivity<ActivityRefundDetailBinding, LiveViewModel> {
    public static final String REQUEST_ID = "request_id";
    private String strRequestId = "";

    public static void goRefundDetailActivity(Context context, String RequestId) {
        Intent intent = new Intent(context, RefundDetailActivity.class);
        intent.putExtra(REQUEST_ID, "" + RequestId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_refund_detail;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        strRequestId = getIntent().getStringExtra(REQUEST_ID);

        mViewModel.liveUserRefundDetail(strRequestId);

        mViewModel.liveUserRefundDetailLiveData.observe(this, new Observer<ResponseBean<LiveUserRefundDetailBean>>() {
            @Override
            public void onChanged(ResponseBean<LiveUserRefundDetailBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    LiveUserRefundDetailBean data = responseBean.getData();
                    if (data != null) {
                        //"status":"int //状态 1=申请退款中，2=申请同意，3=申请驳回",
                        int status = data.getStatus();
                        mBinding.tvReundMoneys.setText("¥" + data.getMoney());
                        mBinding.tvReundMoneys.setText("¥" + CommonUtilis.doubleMoney( data.getMoney()));
                        mBinding.tvRefundTitle.setText("退款原因：" + data.getComplainTitle());
                        mBinding.tvRefundInformation.setText("退款金额：¥" + data.getMoney());
                        mBinding.tvApplyTime.setText("申请时间：" + data.getReFundTime());
                        if (status == 1) {
                            mBinding.llAuditResult.setVisibility(View.GONE);
                            mBinding.tvApplyStatus.setText("系统审核中");
                            mBinding.tvTimeThree.setVisibility(View.GONE);
                            mBinding.tvTime.setText("" + data.getReFundTime());
                            mBinding.tvTimeOne.setText("" + data.getReFundTime());
                            mBinding.tvTimeTwo.setText("" + data.getCheckIngTime());
                            mBinding.tvTimeThree.setText("" + data.getReFundTime());

                        } else if (status == 2) {
                            mBinding.llAuditResult.setVisibility(View.GONE);
                            mBinding.tvApplyStatus.setText("退款成功");
                            mBinding.tvTime.setText("" + data.getAdminTime());
                            mBinding.tvTimeOne.setText("" + data.getReFundTime());
                            mBinding.tvTimeTwo.setText("" + data.getCheckIngTime());
                            mBinding.tvTimeThree.setText("" + data.getAdminTime());
                        } else {
                            mBinding.llAuditResult.setVisibility(View.VISIBLE);
                            mBinding.tvApplyStatus.setText("退款失败");
                            mBinding.tvTime.setText("" + data.getAdminTime());
                            mBinding.tvTimeOne.setText("" + data.getReFundTime());
                            mBinding.tvTimeTwo.setText("" + data.getCheckIngTime());
                            mBinding.tvTimeThree.setText("" + data.getAdminTime());
                            mBinding.tvRefundResult.setText("" + data.getComplain());
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