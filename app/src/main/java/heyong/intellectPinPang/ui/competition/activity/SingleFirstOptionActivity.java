package heyong.intellectPinPang.ui.competition.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.ArrangeDrawDataBean;
import heyong.intellectPinPang.databinding.ActivitySingleFirstOptionBinding;
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;

/**
 * 单打优先选择权   裁判员的主客队抽签
 */
public class SingleFirstOptionActivity extends BaseActivity<ActivitySingleFirstOptionBinding, HomePageViewModel> implements View.OnClickListener {
    ArrangeDrawDataBean arrangeDrawBean;
    ArrangeDrawDataBean data;
    private String arrangeId = "";
    int mItemType;
    private String cliclkPosition="";
//"121"  陈建斌
    @Override
    public int getLayoutRes() {
        return R.layout.activity_single_first_option;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        arrangeDrawBean = (ArrangeDrawDataBean) getIntent().getSerializableExtra("data");
        cliclkPosition=getIntent().getStringExtra("cliclkPosition");
        if (arrangeDrawBean != null) {
            mViewModel.arrangeDraw(arrangeDrawBean);
            mItemType = Integer.parseInt(arrangeDrawBean.getItemType());
            mBinding.tvTitle.setText(""+arrangeDrawBean.getTitle());
            if (mItemType == 1) {


            } else if (mItemType == 2)//单打
            {
                mBinding.llLeftTwo.setVisibility(View.GONE);
                mBinding.llRightTwo.setVisibility(View.INVISIBLE);
                ImageLoader.loadImage(mBinding.ivLeftOneLogo, arrangeDrawBean.getLeft1Img(),
                        R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                ImageLoader.loadImage(mBinding.ivRightLogoOne, arrangeDrawBean.getRight1Img(),
                        R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                mBinding.tvLeftOneName.setText("" + arrangeDrawBean.getLeft1Name());
                mBinding.tvRightOneName.setText("" + arrangeDrawBean.getRight1Name());

            } else//双打
            {
                mBinding.llLeftTwo.setVisibility(View.VISIBLE);
                mBinding.llRightTwo.setVisibility(View.VISIBLE);
                ImageLoader.loadImage(mBinding.ivLeftOneLogo, arrangeDrawBean.getLeft1Img(),
                        R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                mBinding.tvLeftOneName.setText("" + arrangeDrawBean.getLeft1Name());
                ImageLoader.loadImage(mBinding.ivRightLogoOne, arrangeDrawBean.getRight1Img(),
                        R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                mBinding.tvRightOneName.setText("" + arrangeDrawBean.getRight1Name());
                ImageLoader.loadImage(mBinding.ivLeftTwoLogo, arrangeDrawBean.getLeft2Img(),
                        R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                mBinding.tvLeftTwoName.setText("" + arrangeDrawBean.getLeft2Name());
                ImageLoader.loadImage(mBinding.ivRightTwoLogo, arrangeDrawBean.getRight2Img(),
                        R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                mBinding.tvRightTwoName.setText("" + arrangeDrawBean.getRight2Name());
            }
        }
        mBinding.tvStartCompetition.setEnabled(false);
        mViewModel.arrangeDrawLiveData.observe(this, new Observer<ResponseBean<ArrangeDrawDataBean>>() {
            @Override
            public void onChanged(ResponseBean<ArrangeDrawDataBean> arrangeDrawDataBeanResponseBean) {
                if (arrangeDrawDataBeanResponseBean.getErrorCode().equals("200")) {
                    data = arrangeDrawDataBeanResponseBean.getData();
                    if (data != null) {
                        arrangeId = "" + data.getArrangeId();
                    } else {
                        toast("" + arrangeDrawDataBeanResponseBean.getMessage());
                    }

                } else {
                    toast("" + arrangeDrawDataBeanResponseBean.getMessage());
                }
            }
        });
    }


    //旋转动画
    //实现先顺时针360度旋转然后逆时针360度旋转动画功能
    private void ObjectrotationAnim(int angle) {
        Float aFloat = Float.valueOf(String.valueOf(angle));
        //构造ObjectAnimator对象的方法
        ObjectAnimator animator = ObjectAnimator.ofFloat(mBinding.imgZhizhen, "rotation",
                0.0F, aFloat
        );//设置先顺时针360度旋转然后逆时针360度旋转动画
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(1000);//设置旋转时间
        animator.start();//开始执行动画（顺时针旋转动画）
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 1是先手   2是后手
//                int leftType = Integer.parseInt(data.getLeftType());
//                if (leftType == 1) {
                    mBinding.tvFirst.setText("先");
//                } else if (leftType == 2) {
//                    mBinding.tvFirst.setText("后");
//                }

//                int rightType = Integer.parseInt(data.getRightType());
//                if (rightType == 1) {
//                    mBinding.tvHou.setText("先");
//                } else if (leftType == 2) {
                    mBinding.tvHou.setText("后");
//                }
                int mItemType = Integer.parseInt(data.getItemType());
                if (mItemType == 1) {

                } else if (mItemType == 2) {
//                    if (leftType == 1) {
                        //单打
                        mBinding.tvFistName.setText("" + data.getLeft1Name());
                        mBinding.tvHouName.setText("" + data.getRight1Name());
//                    }
//                    if (rightType == 1) {
////                        单打
//                        mBinding.tvFistName.setText("" + data.getRight1Name());
//                        mBinding.tvHouName.setText("" + data.getLeft1Name());
//                    }
                } else {
                    //双打
//                    if (leftType == 1) {
                        //单打
                        mBinding.tvFistName.setText("" + data.getLeft1Name() + "/" + data.getLeft2Name());
                        mBinding.tvHouName.setText("" + data.getRight1Name() + "/" + data.getRight2Name());
//                    }
//                    if (rightType == 1) {
//                        //单打
//                        mBinding.tvFistName.setText("" + data.getRight1Name() + "/" + data.getRight2Name());
//                        mBinding.tvHouName.setText("" + data.getLeft1Name() + "/" + data.getLeft2Name());
//                    }
                }

                mBinding.tvStartCompetition.setEnabled(true);
                mBinding.tvStartCompetition.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvStartCompetition.setTextColor(Color.parseColor("#FFFFFF"));
                mBinding.tvStartCompetition.setClickable(true);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start://开始抽签
                if(data!=null)
                {
                    if (AntiShakeUtils.isInvalidClick(v))
                        return;
                    ObjectrotationAnim(data.getAngle() + 360 * 8+180);

                }else
                {
                    toast("数据异常");
                }

                break;
            case R.id.tv_start_competition://开始比赛
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (mItemType == 2) {
                    //跳转到单打界面
//                    SinglesDetailOfficialActivity
                    Intent intent = new Intent();
                    intent.setClass(SingleFirstOptionActivity.this, SinglesDetailOfficialActivity.class);
                    intent.putExtra("ids", "" + arrangeId);
                    intent.putExtra("cliclkPosition", "" + cliclkPosition);
                    startActivity(intent);
                    finish();

                } else if (mItemType == 3 || mItemType == 4) {
                    //跳转到双打界面
//                    DoubleDetailOfficialActivity
                    Intent intent = new Intent();
                    intent.setClass(SingleFirstOptionActivity.this, DoubleDetailOfficialActivity.class);
                    intent.putExtra("ids", "" + arrangeId);
                    intent.putExtra("cliclkPosition", "" + cliclkPosition);
                    startActivity(intent);
                    finish();
                }

                break;
        }
    }
}