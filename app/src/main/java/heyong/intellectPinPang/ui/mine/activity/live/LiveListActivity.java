package heyong.intellectPinPang.ui.mine.activity.live;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.event.PayEvent;
import heyong.intellectPinPang.event.PlayBackEvent;
import heyong.intellectPinPang.event.ToBuyEvent;
import heyong.intellectPinPang.event.ToLiveEvent;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.LivePagerAdapter;
import heyong.intellectPinPang.bus.RxBus;
import heyong.intellectPinPang.databinding.ActivityLiveListBinding;
import heyong.intellectPinPang.event.AllLiveEvent;
import heyong.intellectPinPang.event.AmateurEvent;
import heyong.intellectPinPang.event.DoubleLiveEvent;
import heyong.intellectPinPang.event.MajorEvent;
import heyong.intellectPinPang.event.RecommendEvent;
import heyong.intellectPinPang.event.SingleLiveEvent;
import heyong.intellectPinPang.event.TeamLiveEvent;
import heyong.intellectPinPang.live.model.LiveViewModel;
import heyong.intellectPinPang.ui.homepage.fragment.live.PlayBackFragment;
import heyong.intellectPinPang.ui.homepage.fragment.live.ToBuyFragment;
import heyong.intellectPinPang.ui.homepage.fragment.live.ToLiveFragment;
import heyong.intellectPinPang.ui.mine.event.LiveHallChangeEvent;
import heyong.intellectPinPang.ui.mine.event.TimeStatusChangeEvent;
import heyong.intellectPinPang.utils.AntiShakeUtils;

/**
 * 直播列表界面   回放 直播 已购买
 */
public class LiveListActivity extends BaseActivity<ActivityLiveListBinding, LiveViewModel> implements View.OnClickListener {
    public static final String TAG = LiveListActivity.class.getSimpleName();
    TextView indicator;
    TextView tv_tab;
    int orderStatus = 0;
    public int type = 0;

    private List<Fragment> fragments;
    private int currentPosition = 0;
    String strMatchId = "";
    private int gameType = 1;//赛事类别 1 全部 2  专业  3 业余
    public String searchTitle = "";

    public static void goActivity(Context context, String matchId) {
        Intent intent = new Intent(context, LiveListActivity.class);
        intent.putExtra("matchId", matchId);
        context.startActivity(intent);
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_name};
        return ids;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_live_list;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        strMatchId = getIntent().getStringExtra("matchId");
        initViewPager();

    }


    private void initViewPager() {
        //设置Adapter
        fragments = new ArrayList<>();

        fragments.add(new PlayBackFragment(strMatchId));
        fragments.add(new ToLiveFragment(strMatchId));
        fragments.add(new ToBuyFragment(strMatchId));


        LivePagerAdapter adapter = new LivePagerAdapter(getSupportFragmentManager(), fragments);
        mBinding.viewPager.setAdapter(adapter);
        mBinding.viewPager.setOffscreenPageLimit(fragments.size());
        mBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        playOne();
                        currentPosition = 0;
                        break;
                    case 1:
                        PlayTwo();
                        currentPosition = 1;
                        break;
                    case 2:
                        playThree();
                        currentPosition = 2;
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBinding.viewPager.setCurrentItem(1);
        currentPosition = 1;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_search:
            case R.id.et_name:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
//                goActivity(SearchLiveListActivity.class);
                Intent intent = new Intent(this, SearchLiveListActivity.class);
                startActivityForResult(intent, 1);

                break;
            case R.id.iv_finish:

                finish();
                break;

            case R.id.ll_play_back://回放
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.viewPager.setCurrentItem(0);
                playOne();
                currentPosition = 0;

                break;
            case R.id.ll_to_live://直播
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.viewPager.setCurrentItem(1);
                PlayTwo();
                currentPosition = 1;
                break;
            case R.id.ll_to_buy://已购买
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.viewPager.setCurrentItem(2);
                currentPosition = 2;
                playThree();
                break;
            case R.id.iv_clear:
                mBinding.ivLogo.setVisibility(View.VISIBLE);
                mBinding.tvName.setText("");
                mBinding.ivClear.setVisibility(View.GONE);
                searchTitle = "";
                if (currentPosition == 0) {
                    RxBus.getDefault().post(new PlayBackEvent(searchTitle));
                } else if (currentPosition == 1) {
                    RxBus.getDefault().post(new ToLiveEvent(searchTitle));

                } else {
                    RxBus.getDefault().post(new ToBuyEvent(searchTitle));

                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                searchTitle = data.getStringExtra("textData");
                XLog.e("" + searchTitle);
                if (!TextUtils.isEmpty(searchTitle)) {
                    mBinding.ivLogo.setVisibility(View.GONE);
                    mBinding.tvName.setText("" + searchTitle);
                    mBinding.ivClear.setVisibility(View.VISIBLE);
                    if (currentPosition == 0) {
                        RxBus.getDefault().post(new PlayBackEvent(searchTitle));
                    } else if (currentPosition == 1) {
                        RxBus.getDefault().post(new ToLiveEvent(searchTitle));

                    } else {
                        RxBus.getDefault().post(new ToBuyEvent(searchTitle));

                    }

                }
            }
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

    public void updateStatus(int number) {
        type = number;
        mBinding.viewPager.setCurrentItem(type);
//        RxBus.getDefault().post(number);
//        Log.e(TAG, "updateStatus: post" );
    }

    /**
     * 用来改变tabLayout选中后的字体大小及颜色
     *
     * @param tab      TabLayout.Tab
     * @param isSelect 是否选中
     */
    private void updateTabView(TabLayout.Tab tab, boolean isSelect) {
        //找到自定义视图的控件ID
        tv_tab = tab.getCustomView().findViewById(R.id.tab_item_textview);
        indicator = tab.getCustomView().findViewById(R.id.tab_item_indicator);
        /*选中*/
        if (isSelect) {
            indicator.setVisibility(View.VISIBLE);
            //设置标签选中
            tv_tab.setSelected(true);
            //选中后字体变大
            tv_tab.setTextSize(15);
            //选中加粗
            TextPaint paint = tv_tab.getPaint();
            paint.setFakeBoldText(true);
            //选中变色
            tv_tab.setTextColor(Color.parseColor("#4795ED"));
        } else {
            indicator.setVisibility(View.GONE);
            //设置标签取消选中
            tv_tab.setSelected(false);
            //恢复为默认字体大小
            tv_tab.setTextSize(15);
            TextPaint paint = tv_tab.getPaint();
            paint.setFakeBoldText(false);
            tv_tab.setTextColor(Color.parseColor("#333333"));
        }
    }
}