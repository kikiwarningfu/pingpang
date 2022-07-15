package heyong.intellectPinPang.ui.mine.activity.live;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.NewLivePageAdapter;
import heyong.intellectPinPang.databinding.ActivityLiveSearchNewListBinding;
import heyong.intellectPinPang.ui.homepage.fragment.live.PlayBackFragment;
import heyong.intellectPinPang.ui.homepage.fragment.live.ToBuyFragment;
import heyong.intellectPinPang.ui.homepage.fragment.live.ToLiveFragment;
import heyong.intellectPinPang.utils.AntiShakeUtils;

public class LiveSearchNewListActivity extends BaseActivity<ActivityLiveSearchNewListBinding, BaseViewModel>
        implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private int loadType = 0;
    private int itemType = -1;
    private List<Fragment> fragments;
    private String etSeatchText = "";
    private int gameType = 1;//赛事类别 1 全部 2  专业  3 业余

    public static void goActivity(Context context, String etSearch) {
        Intent intent = new Intent(context, LiveSearchNewListActivity.class);
        intent.putExtra("name", etSearch);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_live_search_new_list;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        etSeatchText = getIntent().getStringExtra("name");
        mBinding.etName.setText(""+etSeatchText);
        mBinding.viewPager.setCurrentItem(0);
        playOne();

        initViewPager();



//        mBinding.llSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toast("111");
//                finish();
//            }
//        });
//        mBinding.etName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toast("2222");
//                finish();
//            }
//        });

    }


    private void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new PlayBackFragment(""));
        fragments.add(new ToLiveFragment(""));
        fragments.add(new ToBuyFragment(""));
        mBinding.viewPager.setAdapter(new NewLivePageAdapter(getSupportFragmentManager(), fragments));
        mBinding.viewPager.addOnPageChangeListener(this);
        mBinding.viewPager.setCurrentItem(0);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_search:
            case R.id.iv_seach_logo:
            case R.id.et_name:

            case R.id.iv_finish:
                finish();



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
                gameType=1;
                initClearGameView();
                mBinding.tvAll.setBackgroundResource(R.drawable.shape_blue_corners_12);
                mBinding.tvAll.setTextColor(Color.parseColor("#4795ED"));
                //请求接口
                break;
            case R.id.tv_zhuanye://专业
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                gameType=2;
                initClearGameView();
                mBinding.tvZhuanye.setBackgroundResource(R.drawable.shape_blue_corners_12);
                mBinding.tvZhuanye.setTextColor(Color.parseColor("#4795ED"));
                break;
            case R.id.tv_yeyu://业余
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                gameType=3;
                initClearGameView();
                mBinding.tvYeyu.setBackgroundResource(R.drawable.shape_blue_corners_12);
                mBinding.tvYeyu.setTextColor(Color.parseColor("#4795ED"));
                break;
        }
    }

    /*清空赛事的view*/
    private void initClearGameView() {
        mBinding.tvAll.setBackgroundResource(R.drawable.shape_gray_corners_12);
        mBinding.tvZhuanye.setBackgroundResource(R.drawable.shape_gray_corners_12);
        mBinding.tvYeyu.setBackgroundResource(R.drawable.shape_gray_corners_12);

        mBinding.tvAll.setTextColor(Color.parseColor("#999999"));
        mBinding.tvZhuanye.setTextColor(Color.parseColor("#999999"));
        mBinding.tvYeyu.setTextColor(Color.parseColor("#999999"));
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


    @Override
    public void onPageScrollStateChanged(int state) {

    }
}