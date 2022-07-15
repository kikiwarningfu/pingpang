package heyong.intellectPinPang.ui.competition.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xq.fasterdialog.dialog.CustomDialog;
import com.ycuwq.datepicker.date.DatePicker;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityFillGameInformationBinding;

/**
 * 参赛信息填写
 */
public class FillGameInformationActivity extends BaseActivity<ActivityFillGameInformationBinding, BaseViewModel> implements View.OnClickListener {


    @Override
    public int getLayoutRes() {
        return R.layout.activity_fill_game_information;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);

    }

    CustomDialog customDialogConfirm11;
    String time = "";


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_birthday:
                showPopBirthDay();

                break;
            case R.id.rl_sex://性别
                Intent intent = new Intent(FillGameInformationActivity.this, GameSexSettingActivity.class);
                startActivityForResult(intent, 1);

                break;
            case R.id.rl_nick_name://姓名
                startActivity(new Intent(FillGameInformationActivity.this, GameNameSettingActivity.class));
                break;
            case R.id.rl_address://地址


                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                String sexType = data.getStringExtra("sexType");
                Log.e(TAG, "onActivityResult: " + sexType);

            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {


            }
        }
    }

    /*点击确定和修改比分弹窗*/
    private void showPopBirthDay() {
        customDialogConfirm11 = new CustomDialog(FillGameInformationActivity.this);
        customDialogConfirm11.setCustomView(R.layout.pop_birth_day);
        customDialogConfirm11.show();
        View customView = customDialogConfirm11.getCustomView();
        TextView tvSure = customView.findViewById(R.id.tv_sure);
        TextView tvCancel = customView.findViewById(R.id.tv_cancel);
        DatePicker datePicker = customView.findViewById(R.id.datePicker);


        datePicker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day) {
//                dateTv.setText(year + "-" + month + "-" + day);
                time = "" + year + "-" + month + "-" + day;
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " + time);
                customDialogConfirm11.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogConfirm11.dismiss();
            }
        });


    }
}
