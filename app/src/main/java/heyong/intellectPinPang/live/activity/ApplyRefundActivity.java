package heyong.intellectPinPang.live.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityApplyRefundBinding;
import heyong.intellectPinPang.live.model.LiveViewModel;

/**
 * 申请退款
 */
public class ApplyRefundActivity extends BaseActivity<ActivityApplyRefundBinding, LiveViewModel> implements View.OnClickListener {

    private static final List<String> options1Items = new ArrayList<>();
    private String strSelectReason = "";
    public static final String RefundMoney = "refund_money";
    public static final String RequestId = "request_id";

    private String strRefundMoney = "";
    private String strRequestId = "";
    public static void goApplyRefundActivity(Context context,String refundMoney,String requestId)
    {
        Intent intent=new Intent(context,ApplyRefundActivity.class);
        intent.putExtra(RefundMoney,""+refundMoney);
        intent.putExtra(RequestId,""+requestId);
        context.startActivity(intent);
    }


    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_explain};
        return ids;
    }



    @Override
    public int getLayoutRes() {
        return R.layout.activity_apply_refund;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        strRefundMoney = getIntent().getStringExtra(RefundMoney);
        strRequestId = getIntent().getStringExtra(RequestId);

        mBinding.tvRefundMoney.setText("¥" + strRefundMoney);

        options1Items.add("直播未开始，取消悬赏并退款");
        options1Items.add("直播画面不清晰");
        options1Items.add("直播画面不完整");
        options1Items.add("直播中途结束");
        options1Items.add("直播中出现违规内容");
        options1Items.add("其他");

        mViewModel.liveRefundApplyLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_select_reason:
                showType();
                break;
            case R.id.tv_commit://提交申请
                String etExplain = mBinding.etExplain.getText().toString();
                if (TextUtils.isEmpty(strSelectReason)) {
                    toast("请选择退款原因");
                    finish();
                }
                if (TextUtils.isEmpty(etExplain)) {
                    toast("请输入退款原因");
                    return;
                }
                mViewModel.liveUserRefund(""+strRequestId,""+strSelectReason,""+etExplain);

                break;
        }
    }

    private void showType() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(ApplyRefundActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String s = options1Items.get(options1);
                strSelectReason = s;
                mBinding.tvSelectReason.setText("" + strSelectReason);
                mBinding.tvSelectReason.setTextColor(Color.BLACK);
            }
        })
                .setSubCalSize(20)//确定和取消文字大小
                .setTextColorCenter(Color.parseColor("#333333"))//设置选中项的颜色
                .setTextColorOut(Color.parseColor("#666666"))
                .build();
        pvOptions.setPicker(options1Items);
        pvOptions.show();
    }
}