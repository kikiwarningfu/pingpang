package heyong.intellectPinPang.ui.competition.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MatchOrderInfoBean;
import heyong.intellectPinPang.databinding.ActivityOutRaceOfDetailBinding;
import heyong.intellectPinPang.ui.mine.MineViewModel;

/**
 * 退赛详情
 */
public class OutRaceOfDetailActivity extends BaseActivity<ActivityOutRaceOfDetailBinding, MineViewModel> {
    public static final String ORDERID = "order_id";
    private String orderId = "";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_out_race_of_detail;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra(ORDERID);

        mViewModel.matchOrderInfo(orderId);

        mViewModel.matchOrderInfoLiveData.observe(this, new Observer<ResponseBean<MatchOrderInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<MatchOrderInfoBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    MatchOrderInfoBean data = responseBean.getData();
                    if (data != null) {
                        int freeStatus = Integer.parseInt(data.getFreeStatus());
                        if (freeStatus == 3 || freeStatus == 4) {
                            String updateTime = data.getUpdateTime();
                            mBinding.tvContent.setText("您于" + updateTime + "选择退出比赛，报名费" +
                                    "会在退赛后的三个工作日内退还给您。");
                            //退款成功
                        } else {
                            mBinding.tvTitle.setText("退款中");
                            mBinding.tvContent.setVisibility(View.GONE);
                        }
                    } else {
                        toast("" + responseBean.getMessage());
                    }
                    //1 团体 2 单打  3 双打   4 混双（和业余双打一样）
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
    }

}