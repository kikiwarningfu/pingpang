package heyong.intellectPinPang.ui.mine.activity.live;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.NewLivePageAdapter;
import heyong.intellectPinPang.databinding.ActivityLiveNewListChildBinding;
import heyong.intellectPinPang.ui.homepage.fragment.newlive.NewHuiFangChildFragment;
import heyong.intellectPinPang.ui.homepage.fragment.newlive.NewToBuyFragment;
import heyong.intellectPinPang.ui.homepage.fragment.newlive.NewZhiboChildFragment;
import heyong.intellectPinPang.utils.AntiShakeUtils;

/**
 * 赛事直播子界面
 */
public class LiveNewListChildActivity extends BaseActivity<ActivityLiveNewListChildBinding, BaseViewModel> implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private int loadType = 0;
    private int itemType = 0;
    private List<Fragment> fragments;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_live_new_list_child;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

        initViewPager();
        mBinding.viewPager.setCurrentItem(0);
        playOne();


    }

    private void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new NewZhiboChildFragment());
        fragments.add(new NewHuiFangChildFragment());
        fragments.add(new NewToBuyFragment());
        mBinding.viewPager.setAdapter(new NewLivePageAdapter(getSupportFragmentManager(), fragments));
        mBinding.viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_finish:

                finish();
                break;
            case R.id.ll_play_back://回放
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.viewPager.setCurrentItem(0);
                playOne();

                break;
            case R.id.ll_to_live://直播
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.viewPager.setCurrentItem(1);
                PlayTwo();
                break;
            case R.id.ll_to_buy://已购买
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.viewPager.setCurrentItem(2);
                playThree();
                break;

            case R.id.tv_all://全部
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                itemType = 0;
                clearView();
                mBinding.tvAll.setBackgroundResource(R.drawable.shape_blue_corners_12);
                mBinding.tvAll.setTextColor(Color.parseColor("#ff4795ed"));

                break;
            case R.id.tv_danda://单打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                itemType = 1;
                clearView();
                mBinding.tvDanda.setBackgroundResource(R.drawable.shape_blue_corners_12);
                mBinding.tvDanda.setTextColor(Color.parseColor("#ff4795ed"));
                break;

            case R.id.tv_yeyu://双打
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                itemType = 2;
                clearView();
                mBinding.tvShuangda.setBackgroundResource(R.drawable.shape_blue_corners_12);
                mBinding.tvShuangda.setTextColor(Color.parseColor("#ff4795ed"));

                break;
            case R.id.tv_hunshuang://混双
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                itemType = 3;
                clearView();
                mBinding.tvHunshuang.setBackgroundResource(R.drawable.shape_blue_corners_12);
                mBinding.tvHunshuang.setTextColor(Color.parseColor("#ff4795ed"));
                break;
            case R.id.tv_tuanti:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                itemType = 4;
                clearView();
                mBinding.tvTuanti.setBackgroundResource(R.drawable.shape_blue_corners_12);
                mBinding.tvTuanti.setTextColor(Color.parseColor("#ff4795ed"));
                break;
        }
    }

    public void clearView() {
        mBinding.tvAll.setTextColor(Color.parseColor("#FF999999"));
        mBinding.tvDanda.setTextColor(Color.parseColor("#FF999999"));
        mBinding.tvShuangda.setTextColor(Color.parseColor("#FF999999"));
        mBinding.tvHunshuang.setTextColor(Color.parseColor("#FF999999"));
        mBinding.tvTuanti.setTextColor(Color.parseColor("#FF999999"));

        mBinding.tvAll.setBackgroundResource(R.drawable.shape_gray_corners_12);
        mBinding.tvDanda.setBackgroundResource(R.drawable.shape_gray_corners_12);
        mBinding.tvShuangda.setBackgroundResource(R.drawable.shape_gray_corners_12);
        mBinding.tvHunshuang.setBackgroundResource(R.drawable.shape_gray_corners_12);
        mBinding.tvTuanti.setBackgroundResource(R.drawable.shape_gray_corners_12);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        switch (position) {
            case 0:
                playOne();
                break;
            case 1:
                PlayTwo();
                break;
            case 2:
                playThree();
                break;
        }
    }

    private void playThree() {
        initClearView();
        setTextBold(mBinding.tvToBuy);
        mBinding.viewToBuy.setVisibility(View.VISIBLE);
        mBinding.tvToBuy.setTextSize(19);
        mBinding.tvToBuy.setTextColor(Color.parseColor("#4795ED"));
    }

    private void PlayTwo() {
        initClearView();
        setTextBold(mBinding.tvToLive);
        mBinding.viewToLive.setVisibility(View.VISIBLE);
        mBinding.tvToLive.setTextSize(19);
        mBinding.tvToLive.setTextColor(Color.parseColor("#4795ED"));
    }

    private void playOne() {
        initClearView();
        setTextBold(mBinding.tvPlayBack);
        mBinding.viewPlayBack.setVisibility(View.VISIBLE);
        mBinding.tvPlayBack.setTextSize(19);
        mBinding.tvPlayBack.setTextColor(Color.parseColor("#4795ED"));
    }

    private void setTextBold(TextView textView) {
        //android中为textview动态设置字体为粗体
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    private void setTextUnBold(TextView textView) {
        //设置不为加粗
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
    }

    private void initClearView() {
        setTextUnBold(mBinding.tvPlayBack);
        setTextUnBold(mBinding.tvToLive);
        setTextUnBold(mBinding.tvToBuy);
        mBinding.tvPlayBack.setTextSize(16);
        mBinding.tvToLive.setTextSize(16);
        mBinding.tvToBuy.setTextSize(16);
        mBinding.tvPlayBack.setTextColor(Color.parseColor("#333333"));
        mBinding.tvToLive.setTextColor(Color.parseColor("#333333"));
        mBinding.tvToBuy.setTextColor(Color.parseColor("#333333"));
        mBinding.viewPlayBack.setVisibility(View.GONE);
        mBinding.viewToLive.setVisibility(View.GONE);
        mBinding.viewToBuy.setVisibility(View.GONE);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}