package heyong.intellectPinPang.ui.coachdisplay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.DisplayBeginArrangeBean;
import heyong.intellectPinPang.databinding.ActivitySingleFirstOptionBinding;
import heyong.intellectPinPang.utils.AntiShakeUtils;


/**
 * 裁判员  显示单打抽签界面UI
 */
public class DisplaySingleFirstOptionActivity extends BaseActivity<ActivitySingleFirstOptionBinding, BaseViewModel> implements View.OnClickListener {
    List<DisplayBeginArrangeBean.ChessBean> chessBeanList = new ArrayList<>();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_single_first_option;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        ImageLoader.loadImage(mBinding.ivLeftOneLogo, "https://img0.baidu.com/it/u=307273849,3381047345&fm=26&fmt=auto&gp=0.jpg");
        ImageLoader.loadImage(mBinding.ivRightLogoOne, "https://img1.baidu.com/it/u=1438419357,3159337688&fm=26&fmt=auto&gp=0.jpg");
        mBinding.tvStartCompetition.setEnabled(false);

        mBinding.tvLeftOneName.setText("郭德纲");
        mBinding.tvRightOneName.setText("于谦");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start://开始抽签
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                ObjectrotationAnim(90 + 360 * 8 + 180);
                break;
            case R.id.tv_start_competition://开始比赛
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
//                BeginArrangeBean
                chessBeanList.clear();
                for (int i = 0; i < 3; i++) {
                    DisplayBeginArrangeBean.ChessBean chessBean = new DisplayBeginArrangeBean.ChessBean();
                    chessBean.setRightCount("");
                    chessBean.setLeftCount("");
                    chessBean.setLeftWavier(0);
                    chessBean.setRightWavier(0);
                    chessBean.setStatus("1");
                    chessBeanList.add(chessBean);
                }
                DisplayBeginArrangeBean beginArrangeBean = new DisplayBeginArrangeBean();
                beginArrangeBean.setClub1Name("逗哏俱乐部");
                beginArrangeBean.setPlayer1("郭德纲");
                beginArrangeBean.setPlayer3("于谦");
                beginArrangeBean.setHeadImg1("https://img0.baidu.com/it/u=307273849,3381047345&fm=26&fmt=auto&gp=0.jpg");
                beginArrangeBean.setHeadImg3("https://img1.baidu.com/it/u=1438419357,3159337688&fm=26&fmt=auto&gp=0.jpg");
                beginArrangeBean.setChess(chessBeanList);
                beginArrangeBean.setLeftCount(0);
                beginArrangeBean.setRightCount(0);
                beginArrangeBean.setLeftWaiver(0);
                beginArrangeBean.setLeftSupend(0);
                beginArrangeBean.setRightSupend(0);
                beginArrangeBean.setStatus("1");
                beginArrangeBean.setRightWaiver(0);
                beginArrangeBean.setClub2Name("捧哏俱乐部");
                beginArrangeBean.setMethond("1");
                Intent intent = new Intent(DisplaySingleFirstOptionActivity.this, DisplaySingleDetailOfficialActivity.class);
                intent.putExtra("data", beginArrangeBean);
                intent.putExtra("chouqian", "chouqian");
                startActivity(intent);
                finish();
//

                break;
        }
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

                mBinding.tvFistName.setText("郭德纲");
                mBinding.tvHouName.setText("于谦");

                mBinding.tvStartCompetition.setEnabled(true);
                mBinding.tvStartCompetition.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvStartCompetition.setTextColor(Color.parseColor("#FFFFFF"));
                mBinding.tvStartCompetition.setClickable(true);

            }
        });
    }
}
