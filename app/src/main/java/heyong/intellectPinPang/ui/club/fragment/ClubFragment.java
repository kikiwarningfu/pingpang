package heyong.intellectPinPang.ui.club.fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.android.material.tabs.TabLayout;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseFragment;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.HomeFragmentPagerAdapter;
import heyong.intellectPinPang.bus.RxBus;
import heyong.intellectPinPang.databinding.FragmentBowlingBinding;
import heyong.intellectPinPang.event.AllClubEmptyEvent;
import heyong.intellectPinPang.event.AllClubEvent;
import heyong.intellectPinPang.event.AssociationEmptyEvent;
import heyong.intellectPinPang.event.AssociationEvent;
import heyong.intellectPinPang.event.CityEvent;
import heyong.intellectPinPang.event.CityEventSet;
import heyong.intellectPinPang.event.ClubChildEmptyEvent;
import heyong.intellectPinPang.event.ClubChildEvent;
import heyong.intellectPinPang.event.EventBusCityEvent;
import heyong.intellectPinPang.event.FreedomGroupEmptyEvent;
import heyong.intellectPinPang.event.FreedomGroupEvent;
import heyong.intellectPinPang.event.SportsAcademyEmptyEvent;
import heyong.intellectPinPang.event.SportsAcademyEvent;
import heyong.intellectPinPang.ui.club.fragment.child.AllClubFragment;
import heyong.intellectPinPang.ui.club.fragment.child.AssociationFragment;
import heyong.intellectPinPang.ui.club.fragment.child.ClubChildFragment;
import heyong.intellectPinPang.ui.club.fragment.child.FreedomGroupFragment;
import heyong.intellectPinPang.ui.club.fragment.child.SportsAcademyFragment;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.ui.homepage.HomePageCommonManager;
import heyong.intellectPinPang.widget.AlertDialog;
import heyong.lzy.imagepicker.util.StatusBarUtil;

/**
 * @Name：weiying
 * @Description：俱乐部 01俱乐部界面
 * @Author：whf 修改人：whf
 * 修改备注：
 */
public class ClubFragment extends BaseFragment<FragmentBowlingBinding, ClubViewModel> implements View.OnClickListener, AMapLocationListener {
    private List<Fragment> fragments;
    public static final String TAG = ClubFragment.class.getSimpleName();
    public int type = 0;
    TextView indicator;
    TextView tv_tab;
    int orderStatus = 0;

    private int currentPosition = 0;
    private AMapLocationClient mlocationClient;//定位监听
    private AMapLocationClientOption mLocationOption = null;//定位监听
    int locationNum = 0;//判断定位的次数
    private String city = "";//location city
    public static final int LOCATION_CODE_CITY = 1003;//Location access to the city
    private AlertDialog myDialog;//弹窗

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        StatusBarUtil.statusBarLightMode(getActivity());

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        StatusBarUtil.statusBarLightMode(getActivity());
    }

    public ClubFragment() {
    }


    public static ClubFragment newInstance() {

        Bundle args = new Bundle();

        ClubFragment fragment = new ClubFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_bowling;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        /*掉接口*/

        StatusBarUtil.statusBarLightMode(getActivity());

        initViewPager();
        myDialog = new AlertDialog(getActivity()).builder();

        initLocation();
        binding.setListener(this);

        RxBus.getDefault().toObservable(CityEvent.class).subscribe(tagEvents -> {
            Log.e(TAG, "initView: " + tagEvents.getName());
            viewModel.localCity.set("" + tagEvents.getName());
            if(TextUtils.isEmpty(tagEvents.getName()))
            {
                binding.tvLocation.setText("定位失败" );
            }else {
                binding.tvLocation.setText("" + tagEvents.getName());
            }
            RxBus.getDefault().post(new CityEventSet("" + tagEvents.getName()));

        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(CityEvent event) {
        //        Log.e(TAG, "messageEventBus: ===="+isBackground(this) );
        //        if(!isBackground(this)) {
        //            showCoatchDemo(event);
        //        }
        Log.e(TAG, "initView: " + event.getName());
        if(TextUtils.isEmpty(event.getName()))
        {
            binding.tvLocation.setText("定位失败" );
            viewModel.localCity.set("" + event.getName());
            RxBus.getDefault().post(new CityEventSet(""));
        }else
        {
            binding.tvLocation.setText("" + event.getName());
            viewModel.localCity.set("" + event.getName());
            RxBus.getDefault().post(new CityEventSet("" + event.getName()));
        }


    }

    private void initLocation() {
        mlocationClient = new AMapLocationClient(getActivity());
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //mLocationOption.setInterval(2000);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setNeedAddress(true);
        mlocationClient.setLocationOption(mLocationOption);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //启动定位
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_CODE_CITY);
        } else {
            mlocationClient.startLocation();
        }
    }

    private void initViewPager() {
        //设置Adapter
        fragments = new ArrayList<>();

        List<String> titlePager = new ArrayList<>();
        titlePager.add("全部");
        titlePager.add("自由团体");
        titlePager.add("俱乐部");
        titlePager.add("体育院校");
        titlePager.add("协会");

        fragments.add(new AllClubFragment());
        fragments.add(new FreedomGroupFragment());
        fragments.add(new ClubChildFragment());
        fragments.add(new SportsAcademyFragment());
        fragments.add(new AssociationFragment());

        HomeFragmentPagerAdapter adapter = new HomeFragmentPagerAdapter(getChildFragmentManager(), fragments, titlePager);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(fragments.size());
        binding.tabLayout.setupWithViewPager(binding.viewPager);//TabLayout绑定ViewPager
        binding.tabLayout.setTabsFromPagerAdapter(adapter);//TabLayout的标签从Adapter中获取


        //用来循环适配器中的视图总数
        for (int i = 0; i < adapter.getCount(); i++) {
            //获取每一个tab对象
            TabLayout.Tab tabAt = binding.tabLayout.getTabAt(i);
            //将每一个条目设置我们自定义的视图
            tabAt.setCustomView(R.layout.layout_tab_deal);
            //默认选中第一个
            if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                tabAt.getCustomView().findViewById(R.id.tab_item_textview).setSelected(true);//第一个tab被选中
                //设置选中标签的文字大小
                TextView textView = ((TextView) tabAt.getCustomView().findViewById(R.id.tab_item_textview));
                TextPaint paint = textView.getPaint();
                paint.setFakeBoldText(true);
                textView.setTextSize(16);
                textView.setTextColor(Color.parseColor("#4795ED"));
                ((TextView) tabAt.getCustomView().findViewById(R.id.tab_item_indicator)).
                        setBackgroundColor(Color.parseColor("#4795ED"));
                ((TextView) tabAt.getCustomView().findViewById(R.id.tab_item_indicator)).setVisibility(View.VISIBLE);

            }
            //通过tab对象找到自定义视图的ID
            TextView textView = (TextView) tabAt.getCustomView().findViewById(R.id.tab_item_textview);
            textView.setText(titlePager.get(i));//设置tab上的文字
        }

        //当不同tab被选中时显示不同效果
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                indicator = (TextView) tab.getCustomView().findViewById(R.id.tab_item_indicator);
                updateTabView(tab, true);
                Log.e(TAG, "onTabSelected: " + tab.getPosition());
                currentPosition = tab.getPosition();
                String etClubName = binding.etClubName.getText().toString();
                Log.e(TAG, "okhttp  onTabSelected: " + etClubName);
                if (!TextUtils.isEmpty(etClubName)) {
                    viewModel.localTeamTitle.set(etClubName);
                    switch (currentPosition) {
                        case 0:
                            RxBus.getDefault().post(new AllClubEvent(etClubName));
                            break;
                        case 1:
                            RxBus.getDefault().post(new FreedomGroupEvent(etClubName));
                            break;
                        case 2:
                            RxBus.getDefault().post(new ClubChildEvent(etClubName));
                            break;
                        case 3:
                            RxBus.getDefault().post(new SportsAcademyEvent(etClubName));
                            break;
                        case 4:
                            RxBus.getDefault().post(new AssociationEvent(etClubName));
                            break;
                    }
                } else {
                    viewModel.localTeamTitle.set("");
                    switch (currentPosition) {
                        case 0:
                            RxBus.getDefault().post(new AllClubEvent(""));
                            break;
                        case 1:
                            RxBus.getDefault().post(new FreedomGroupEvent(""));
                            break;
                        case 2:
                            RxBus.getDefault().post(new ClubChildEvent(""));
                            break;
                        case 3:
                            RxBus.getDefault().post(new SportsAcademyEvent(""));
                            break;
                        case 4:
                            RxBus.getDefault().post(new AssociationEvent(""));
                            break;
                    }
                }
                binding.viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                updateTabView(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //        if (orderStatus == 0) {
        //            binding.viewPager.setCurrentItem(0);
        //        } else {
        //            binding.viewPager.setCurrentItem(orderStatus);
        //        }
        binding.etClubName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    viewModel.localTeamTitle.set("");
                    switch (currentPosition) {
                        case 0:
                            RxBus.getDefault().post(new AllClubEmptyEvent(""));
                            break;
                        case 1:
                            RxBus.getDefault().post(new FreedomGroupEmptyEvent(""));
                            break;
                        case 2:
                            RxBus.getDefault().post(new ClubChildEmptyEvent(""));
                            break;
                        case 3:
                            RxBus.getDefault().post(new SportsAcademyEmptyEvent(""));
                            break;
                        case 4:
                            RxBus.getDefault().post(new AssociationEmptyEvent(""));
                            break;
                    }
                }
            }
        });
        binding.etClubName.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘
                String etClubName = binding.etClubName.getText().toString();
                if (TextUtils.isEmpty(etClubName)) {
                    toast("请输入俱乐部名称");
                    return true;
                }
                viewModel.localTeamTitle.set(etClubName);
                switch (currentPosition) {
                    case 0:
                        RxBus.getDefault().post(new AllClubEvent(etClubName));
                        break;
                    case 1:
                        RxBus.getDefault().post(new FreedomGroupEvent(etClubName));
                        break;
                    case 2:
                        RxBus.getDefault().post(new ClubChildEvent(etClubName));
                        break;
                    case 3:
                        RxBus.getDefault().post(new SportsAcademyEvent(etClubName));
                        break;
                    case 4:
                        RxBus.getDefault().post(new AssociationEvent(etClubName));
                        break;
                }
                return true;
            }
            return false;
        });

        binding.etClubName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    viewModel.localTeamTitle.set("");
                }
            }
        });
        updateStatus(orderStatus);

    }

    public void updateStatus(int number) {
        type = number;
        binding.viewPager.setCurrentItem(type);
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
            tv_tab.setTextSize(16);
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
            tv_tab.setTextSize(16);
            TextPaint paint = tv_tab.getPaint();
            paint.setFakeBoldText(false);
            tv_tab.setTextColor(Color.parseColor("#333333"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_location:
                CityPicker.from1(getActivity())
                        .setLocatedCity(null)
                        .setHotCities(new ArrayList<>())
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                viewModel.localCity.set("" + data.getName());
                                binding.tvLocation.setText("" + data.getName());
                                RxBus.getDefault().post(new CityEventSet("" + data.getName()));
                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(getActivity(), "取消选择", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onLocate() {
                                //开始定位，这里模拟一下定位
                                //                                new Handler().postDelayed(new Runnable() {
                                //                                    @Override
                                //                                    public void run() {
                                //                                        CityPicker.from(MainActivity.this).locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
                                //                                    }
                                //                                }, 3000);
                            }
                        })
                        .show();
                //                goActivity(ClubChoiceCityActivity.class);
                break;
            case R.id.tv_search:
                String etClubName = binding.etClubName.getText().toString();
                if (TextUtils.isEmpty(etClubName)) {
                    toast("请输入俱乐部名称");
                    return;
                }
                viewModel.localTeamTitle.set(etClubName);
                switch (currentPosition) {
                    case 0:
                        RxBus.getDefault().post(new AllClubEvent(etClubName));
                        break;
                    case 1:
                        RxBus.getDefault().post(new FreedomGroupEvent(etClubName));
                        break;
                    case 2:
                        RxBus.getDefault().post(new ClubChildEvent(etClubName));
                        break;
                    case 3:
                        RxBus.getDefault().post(new SportsAcademyEvent(etClubName));
                        break;
                    case 4:
                        RxBus.getDefault().post(new AssociationEvent(etClubName));
                        break;
                }

                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                amapLocation.getLocationType();
                amapLocation.getAccuracy();
                city = amapLocation.getCity().replace("市", "");


            } else {
                if (locationNum < 2) {
                    locationNum++;
                    mlocationClient.startLocation();
                } else {
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
                city = "定位失败";

            }
            Log.e(TAG, "onLocationChanged: "+city );
            if (city.equals("定位失败")) {
                viewModel.localCity.set("");
                binding.tvLocation.setText("定位失败");
                RxBus.getDefault().post(new CityEventSet(""));
            } else {
                viewModel.localCity.set("" + city);
                binding.tvLocation.setText("" + city);
                RxBus.getDefault().post(new CityEventSet("" + city));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageCityEvent(EventBusCityEvent event) {
        city = event.getName();
        Log.e(TAG, "messageCityEvent: city===" + city);
        viewModel.localCity.set("" + city);
        if(TextUtils.isEmpty(city))
        {
            binding.tvLocation.setText("定位失败" );
        }else {
            binding.tvLocation.setText("" + city);
        }
        RxBus.getDefault().post(new CityEventSet("" + city));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_CODE_CITY) {
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {

                    HomePageCommonManager.getInstance().showMissingDialog(myDialog, getActivity());
                } else {
                    mlocationClient.startLocation();
                }
            }
        }
    }
}
