package heyong.intellectPinPang.ui.friendcircle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import heyong.handong.framework.account.AccountHelper;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.NewLivePageAdapter;
import heyong.intellectPinPang.databinding.ActivityMyFriendCircleBinding;
import heyong.intellectPinPang.event.AttentionFriendEvent;
import heyong.intellectPinPang.event.RecommendFriendEvent;
import heyong.intellectPinPang.ui.friendcircle.fragment.FriendCirlceAttentionFragment;
import heyong.intellectPinPang.ui.friendcircle.fragment.FriendCirlceRecommendFragment;
import heyong.intellectPinPang.ui.mine.activity.live.LiveSearchNewListActivity;
import heyong.intellectPinPang.utils.AntiShakeUtils;

public class MyFriendCircleActivity extends BaseActivity<ActivityMyFriendCircleBinding, PublishEditCircleViewModel> implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private List<Fragment> fragments;
    private String etSeatchText = "";
    private int hotTye = 1;
    private int allType = 1;

    public static void goActivity(Context context, String etSearch) {
        Intent intent = new Intent(context, LiveSearchNewListActivity.class);
        intent.putExtra("name", etSearch);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_my_friend_circle;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        etSeatchText = getIntent().getStringExtra("name");
        Log.e(TAG, "initView: 自己的userId===="+ AccountHelper.getUserId());
        initViewPager();
        initClearHotView();
        hotTye = 1;
        mBinding.tvHot.setTextColor(Color.parseColor("#F72424"));
        setTextUnBold(mBinding.tvHot);
    }

    //初始化 热门
    private void initClearHotView() {
        mBinding.tvHot.setTextColor(Color.parseColor("#666666"));
        mBinding.tvCityLocal.setTextColor(Color.parseColor("#666666"));
        mBinding.tvVideo.setTextColor(Color.parseColor("#666666"));
        setTextUnBold(mBinding.tvHot);
        setTextUnBold(mBinding.tvCityLocal);
        setTextUnBold(mBinding.tvVideo);
    }

    private void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new FriendCirlceRecommendFragment());
        fragments.add(new FriendCirlceAttentionFragment());
        mBinding.viewPager.setAdapter(new NewLivePageAdapter(getSupportFragmentManager(), fragments));
        mBinding.viewPager.addOnPageChangeListener(this);
        mBinding.viewPager.setCurrentItem(0);


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
        setTextUnBold(mBinding.tvRecommend);
        setTextUnBold(mBinding.tvAttention);
        mBinding.tvRecommend.setTextSize(16);
        mBinding.tvAttention.setTextSize(16);
        mBinding.tvRecommend.setTextColor(Color.parseColor("#FF666666"));
        mBinding.tvAttention.setTextColor(Color.parseColor("#FF666666"));
        mBinding.viewRecommend.setVisibility(View.GONE);
        mBinding.viewAttention.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_recommend://推荐
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                allType = 1;
                EventBus.getDefault().post(new RecommendFriendEvent(hotTye));
                mBinding.llRecommendFirst.setVisibility(View.VISIBLE);
                mBinding.viewPager.setCurrentItem(0);
                playOne();
                if (againRequestCountListener != null) {
                    againRequestCountListener.updateRecommend(hotTye);
                }

                break;
            case R.id.ll_attention://关注
                allType = 2;
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                EventBus.getDefault().post(new AttentionFriendEvent(hotTye));
                mBinding.llRecommendFirst.setVisibility(View.GONE);

                mBinding.viewPager.setCurrentItem(1);
                PlayTwo();

                break;
            case R.id.tv_hot:
                initClearHotView();
                hotTye = 1;
                mBinding.tvHot.setTextColor(Color.parseColor("#F72424"));
                setTextUnBold(mBinding.tvHot);
                if (againRequestCountListener != null) {
                    againRequestCountListener.updateRecommend(hotTye);
                }

                break;
            case R.id.tv_city_local:
                initClearHotView();
                hotTye = 2;
                mBinding.tvCityLocal.setTextColor(Color.parseColor("#F72424"));
                setTextUnBold(mBinding.tvCityLocal);

                if (againRequestCountListener != null) {
                    againRequestCountListener.updateRecommend(hotTye);
                }

                break;
            case R.id.tv_video:
                initClearHotView();
                hotTye = 3;
                mBinding.tvVideo.setTextColor(Color.parseColor("#F72424"));
                setTextUnBold(mBinding.tvVideo);

                if (againRequestCountListener != null) {
                    againRequestCountListener.updateRecommend(hotTye);
                }

                break;
            case R.id.iv_fabu:
                //需要判断是否完善个人信息
                startActivity(new Intent(this, PublishEditCircleActivity.class));
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    private void PlayTwo() {
        initClearView();
        setTextBold(mBinding.tvAttention);
        mBinding.viewAttention.setVisibility(View.VISIBLE);
        mBinding.tvAttention.setTextSize(17);
        mBinding.tvAttention.setTextColor(Color.parseColor("#FF333333"));
    }

    private void playOne() {
        initClearView();
        setTextBold(mBinding.tvRecommend);
        mBinding.viewRecommend.setVisibility(View.VISIBLE);
        mBinding.tvRecommend.setTextSize(17);
        mBinding.tvRecommend.setTextColor(Color.parseColor("#FF333333"));
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
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public static AgainRequestCountListener againRequestCountListener;

    public static void setAgainRequestCountListener(AgainRequestCountListener againRequestCountListener) {
        MyFriendCircleActivity.againRequestCountListener = againRequestCountListener;
    }

    public interface AgainRequestCountListener {
        void updateRecommend(int hotType);

        void updateAttention(int hotType);
    }

}
