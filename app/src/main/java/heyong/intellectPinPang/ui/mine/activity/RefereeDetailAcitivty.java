package heyong.intellectPinPang.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.MyBaseInfoBean;
import heyong.intellectPinPang.bean.competition.RefereeInfoBean;
import heyong.intellectPinPang.databinding.ActivityRefreeDetailAcitivtyBinding;
import heyong.intellectPinPang.ui.mine.MineViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;

/**
 * 裁判员详情
 */
public class RefereeDetailAcitivty extends BaseActivity<ActivityRefreeDetailAcitivtyBinding, MineViewModel> implements View.OnClickListener {
    RefereeInfoBean data;

    //    裁判员   3  是第二个    1 是第二个  2 是第三个
    @Override
    public int getLayoutRes() {
        return R.layout.activity_refree_detail_acitivty;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.refereeInfo();
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);

        mViewModel.refereeInfoLiveData.observe(this, new Observer<ResponseBean<RefereeInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<RefereeInfoBean> responseBean) {
//                if (responseBean.isSuccess()) {
                    if(responseBean.getErrorCode().equals("200")){

                        data = responseBean.getData();
                    if (data != null) {
                        ImageLoader.loadImage(mBinding.ivLogo, data.getHeadImg(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                        String sex = data.getSex();

                        if (sex.equals("1")) {
                            ImageLoader.loadImage(mBinding.ivSex, R.drawable.img_sex_man_blue);
                        } else {
                            ImageLoader.loadImage(mBinding.ivSex, R.drawable.img_sex_woman);
                        }
                        mBinding.tvName.setText("" + data.getName() + "【" + data.getAge()+"岁" + "】");
                        mBinding.tvAddress.setText("" + data.getAddress());
                        mBinding.tvMatchCount.setText("" + data.getRefereeCount() + "分");

                        switch (data.getStatus()) {
                            case "1"://申请中 不可点击申请成为裁判员
                                mBinding.llCaipanCertificate.setVisibility(View.GONE);
                                mBinding.tvApplyCaipan.setBackgroundResource(R.drawable.shape_club_gray_little_ccc);
                                mBinding.tvApplyCaipan.setEnabled(false);
                                mBinding.tvApplyCaipan.setText("申请成为裁判员");
                                mBinding.tvApplyStatus.setText("系统审核中");

                                break;
                            case "2"://已通过
                                mBinding.tvApplyCaipan.setText("更改裁判员等级");
                                mBinding.tvApplyCaipan.setBackgroundResource(R.drawable.shape_club_blue);
                                mBinding.tvApplyCaipan.setOnClickListener(RefereeDetailAcitivty.this);
                                mBinding.tvApplyCaipan.setEnabled(true);
                                mBinding.llCaipanCertificate.setVisibility(View.VISIBLE);
                                ImageLoader.loadImage(mBinding.ivCaipanDetail, data.getIntroduce());
                                mBinding.tvApplyStatus.setText("" + data.getRole());

                                break;
                            case "3"://未通过 或者没有申请过呢
                            default:
                                mBinding.tvApplyStatus.setText("您还不是裁判员");
                                mBinding.tvApplyCaipan.setText("申请成为裁判员");
                                mBinding.llCaipanCertificate.setVisibility(View.GONE);
                                mBinding.tvApplyCaipan.setEnabled(true);
                                mBinding.tvApplyCaipan.setBackgroundResource(R.drawable.shape_club_blue);
                                mBinding.tvApplyCaipan.setOnClickListener(RefereeDetailAcitivty.this);
                                break;
                        }
                    } else {
                        mBinding.llCaipanCertificate.setVisibility(View.GONE);
                        mViewModel.myBaseInfo();
                    }
                }
            }
        });
        mViewModel.myBaseInfoLiveData.observe(this, new Observer<ResponseBean<MyBaseInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<MyBaseInfoBean> myBaseInfoBeanResponseBean) {
                if (myBaseInfoBeanResponseBean.getErrorCode().equals("200")) {
                    MyBaseInfoBean data = myBaseInfoBeanResponseBean.getData();
                    if (data != null) {
                        if (TextUtils.isEmpty(data.getSex())) {
                            mBinding.ivSex.setVisibility(View.GONE);
                        } else if (data.getSex().equals("1")) {
                            mBinding.ivSex.setVisibility(View.VISIBLE);
                            ImageLoader.loadImage(mBinding.ivSex, R.drawable.img_sex_man_blue);
                        } else if (data.getSex().equals("2")) {
                            mBinding.ivSex.setVisibility(View.VISIBLE);
                            ImageLoader.loadImage(mBinding.ivSex, R.drawable.img_sex_woman);
                        }
                        mBinding.tvName.setText("" + data.getNickName());
                        if (!TextUtils.isEmpty(data.getHeadImg())) {
                            ImageLoader.loadImage(mBinding.ivLogo, data.getHeadImg());
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_apply_caipan:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (data != null) {
                    switch (data.getStatus()) {
                        case "1":
                        case "3":
                            goActivity(ApplyForRefereeActivity.class);
                            break;
                        case "2":
                            long roleId = data.getRoleId();
                            Intent intent = new Intent(RefereeDetailAcitivty.this, ApplyForRefereeActivity.class);
                            intent.putExtra(ApplyForRefereeActivity.UPDATE_ID, "" + roleId);
                            startActivity(intent);
                            break;

                    }
                } else {
                    goActivity(ApplyForRefereeActivity.class);
                }
                break;
            case R.id.tv_caipan_jingli:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                goActivity(RefereeExperienceActivity.class);

                break;
        }
    }
}