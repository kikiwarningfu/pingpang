package heyong.intellectPinPang.ui.club.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.RegistrationDetailAdapter;
import heyong.intellectPinPang.bean.competition.RegistrationBean;
import heyong.intellectPinPang.databinding.ActivityRegistrationDetailBinding;
import heyong.intellectPinPang.ui.competition.CompetitionViewModel;

/**
 * 报名详情
 */
public class RegistrationDetailActivity extends BaseActivity<ActivityRegistrationDetailBinding, CompetitionViewModel> {

    RegistrationDetailAdapter mRegistrationDetailAdapter;
    public static final String IDS = "ids";
    private String strIds = "";


    public static void goActivity(Context context, String ids) {
        Intent intent = new Intent(context, RegistrationDetailActivity.class);
        intent.putExtra(RegistrationDetailActivity.IDS, "" + ids);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_registration_detail;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        strIds = getIntent().getStringExtra(IDS);
        mViewModel.RegistrationDetails(strIds);
        mRegistrationDetailAdapter = new RegistrationDetailAdapter(RegistrationDetailActivity.this);
        mBinding.rvCoachDetail.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvCoachDetail.setAdapter(mRegistrationDetailAdapter);
        mViewModel.RegistrationDetailsLiveData.observe(this, new Observer<ResponseBean<List<RegistrationBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<RegistrationBean>> listResponseBean) {
                if (listResponseBean.getErrorCode().equals("200")) {
                    List<RegistrationBean> data = listResponseBean.getData();
                    if (data != null) {
                        List<RegistrationBean> removeData = new ArrayList<>();
                        for (int m = 0; m < data.size(); m++) {
                            RegistrationBean registrationBean = data.get(m);
                            if (registrationBean.getPersonCount() == 0) {
                                removeData.add(data.get(m));
                            }
                        }
                        data.removeAll(removeData);
                        mRegistrationDetailAdapter.setNewData(data);

                    } else {
                        mRegistrationDetailAdapter.setNewData(new ArrayList<>());
                    }
                } else {
                    mRegistrationDetailAdapter.setNewData(new ArrayList<>());
                }
            }
        });


    }
}
