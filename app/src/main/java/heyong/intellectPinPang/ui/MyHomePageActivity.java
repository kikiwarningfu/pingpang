package heyong.intellectPinPang.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import heyong.handong.framework.account.AccountHelper;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.SystemUtil;
import heyong.handongkeji.autoupdata.CheckVersion;
import heyong.intellectPinPang.BuildConfig;
import heyong.intellectPinPang.MyApp;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.homepage.UpdateBean;
import heyong.intellectPinPang.bus.RxBus;
import heyong.intellectPinPang.databinding.ActivityMainBinding;
import heyong.intellectPinPang.event.HotFixAddEvent;
import heyong.intellectPinPang.event.JumpSelectTionEvent;
import heyong.intellectPinPang.permissions.PermissionsActivity;
import heyong.intellectPinPang.ui.competition.fragment.CompetitionFragment;
import heyong.intellectPinPang.ui.homepage.fragment.HomePageFragment;
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel;
import heyong.intellectPinPang.ui.login.activity.LoginActivity;
import heyong.intellectPinPang.ui.mine.fragment.MineFragment;
import heyong.intellectPinPang.ui.club.fragment.ClubFragment;
import heyong.intellectPinPang.ui.shopmall.fragment.ShopMallFragment;
import heyong.intellectPinPang.ui.shopmall.fragment.ShopMallKotlinActivity;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.AppManager;
import heyong.intellectPinPang.utils.ThreadUtils;
import heyong.handong.framework.utils.ToastUtils;
import heyong.intellectPinPang.widget.AlertDialog;
import heyong.intellectPinPang.widget.popupwindow.CommonPopupWindow;

import com.blankj.utilcode.util.FragmentUtils;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.tencent.bugly.beta.Beta;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * queryIntentActivities aaa
 * ????????????????????????????????????????????????????????????????????????????????????.NET BCL??????????????????????????????????????????????????????????????????
 * ???????????????  ????????????????????????????????????????????????????????????????????????????????????ArrayList????????????Object???????????????List??????
 * ?????????????????????????????????ArrayList?????????????????????????????????????????????Dot Net????????????????????????????????????????????????????????????
 * ?????????????????????????????????????????????????????????????????????????????????????????????Dot Net????????????????????????????????????????????????????????????
 * ?????????int???double???float??? ?????? ????????????????????? ?????????????????????????????????????????????C++??????????????????????????????????????????
 * ???C++????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????DotNet??????????????????????????????
 * ?????????????????????????????????IL????????????????????????JIT?????????IL???????????????????????????????????????????????????????????????????????? ?????????
 * ???????????????????????????????????????????????????????????????ArrayList??????????????????????????????????????? ??????????????????????????????????????????
 * ?????????????????????????????????????????????????????????????????????????????????????????????<>?????????????????????????????????????????????????????????????????????
 * ???????????????using????????????????????????????????? ????????????????????????????????????????????????????????????????????????????????????????????????????????????
 * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
 * ??????????????????C++???DotNet?????????????????????????????????????????????????????????Java????????????????????????????????????????????????????????? ???
 * ???????????????????????? ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
 * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????CLR????????????
 * ??????????????????????????????????????????
 */


public class MyHomePageActivity extends BaseActivity<ActivityMainBinding, HomePageViewModel> implements View.OnClickListener {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 102;

    private HomePageFragment homePageFragment;
    private ClubFragment clubFragment;
    private CompetitionFragment competitionFragment;
    private ShopMallFragment shopMallFragment;
    private MineFragment mineFragment;

    private static final String TAG_HOME_PAGE = "HomePageFragment";
    private static final String TAG_BOWLING = "CompetitionFragment";
    private static final String TAG_PARTICIPANT_FRAGMENT = "ShopMallFragment";
    private static final String TAG_DRAFT_FRAGMENT = "MineFragment";
    private static final String TAG_SPONSOR_FRAGMENT = "ClubFragment";

    private int mCurrentItem = 0;
    private int mLastItem = 0;
    private FragmentManager mFragmentManager;

    private AlertDialog myDialog;//????????????
    private CommonPopupWindow mShowUpdateWindow;
    private static final int REQUEST_CODE = 0; // Request Code
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA,
//            Manifest.permission.RECORD_AUDIO,

    };
    public static final String TAG = MyHomePageActivity.class.getSimpleName();
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    public static void start(Context context) {
        Intent intent = new Intent(context, MyHomePageActivity.class);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("currentItem", mCurrentItem);


    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "???????????????" + SystemUtil.getDeviceBrand());
        Log.e(TAG, "???????????????" + SystemUtil.getSystemModel());
        Log.e(TAG, "???????????????????????????" + SystemUtil.getSystemLanguage());
        Log.e(TAG, "Android??????????????????" + SystemUtil.getSystemVersion());
        switch (mCurrentItem) {
            case 4:
                int version = SystemUtil.getVersion();
                if (version >= 6) {
                    heyong.lzy.imagepicker.util.StatusBarUtil.statusBarLightMode(MyHomePageActivity.this);
                    heyong.lzy.imagepicker.util.StatusBarUtil.transparencyBar(MyHomePageActivity.this); //????????????????????????
                }
                break;
            default:
                /*??????????????? ????????????*/
                heyong.lzy.imagepicker.util.StatusBarUtil.statusBarLightMode(MyHomePageActivity.this);
                break;
        }

        initGetMei();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        StatusBarUtil.setLightMode(this);
//        config().setAppId("31b6eef5fb5e41eea48544d9bc1d53a1");
//        application().initEngine("31b6eef5fb5e41eea48544d9bc1d53a1");

//        Log.e(TAG, "initView: " + getIntent().getStringExtra("bus"));
        if (TextUtils.isEmpty(AccountHelper.getToken())) {
            goActivity(LoginActivity.class);
            finish();
        }
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        myDialog = new AlertDialog(this).builder();
        getWindow().setStatusBarColor(Color.TRANSPARENT);
//        mViewModel.liveMatchInit();

        initEvent();
        initFragmentManager();
        initSavedInstanceState(savedInstanceState);
        addFragment(mCurrentItem);

        Glide.with(MyHomePageActivity.this).load(R.drawable.img_saishi).into(mBinding.ivCompetition);

        mViewModel.version();//????????????
        observer();
//        //???????????????  ?????????????????????
//        mViewModel.liveMatchInitLiveData.observe(this, new Observer<ResponseBean<LiveMatchInitBean>>() {
//            @Override
//            public void onChanged(ResponseBean<LiveMatchInitBean> liveMatchInitBeanResponseBean) {
//                if (liveMatchInitBeanResponseBean.getErrorCode().equals("200")) {
//                    AccountHelper.setSpMatchMoney("" + liveMatchInitBeanResponseBean.getData().getWatchMoney());
//                    List<LiveMatchInitBean.PayeeListBean> payeeList = liveMatchInitBeanResponseBean.getData().getPayeeList();
//                    String payeeLists = new Gson().toJson(payeeList);
//                    AccountHelper.setSpMatchPayType("" + payeeLists);
//                    goActivity(ReferenceVisitorMatchActivity.class);
//
//                }
//            }
//        });

        mViewModel.updateLiveData.observe(this, new Observer<ResponseBean<UpdateBean>>() {
            @Override
            public void onChanged(ResponseBean<UpdateBean> bean) {
                if (bean.getErrorCode().equals("200")) {
                    if (bean.getData() != null) {
                        if (mShowUpdateWindow != null && mShowUpdateWindow.isShowing()) {
                            AccountHelper.setIsNeedUpdate("");
                        } else {
                            UpdateBean versionBean = bean.getData();
                            if (versionBean != null) {
                                int versionCode = versionBean.getCode();//?????????
                                String isF = versionBean.getUsable(); // ??????????????????  1 ??? ??????
//                                String versionName = versionBean.getName(); // ????????????
                                String downUrl = versionBean.getDownloadUrl();
                                String upInfo = versionBean.getDescription();
                                int mAppVersionCode = 0;
                                try {
                                    mAppVersionCode = getVersionCode();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                String isNeedUpdate = AccountHelper.getIsNeedUpdate();
                                if (isNeedUpdate.equals("true")) {
                                    if (mAppVersionCode < Integer.valueOf(versionCode)) {
                                        showUpdateWindow(bean.getData());
                                    }
                                }
                            }
                        }
                    }
                }

            }
        });
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Beta.downloadPatch();

                } catch (Exception e) {
                    Log.e(TAG, "run: ====" + e.getMessage());
                }
            }
        }, 2000);

        mViewModel.phpAesLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                ShopMallKotlinActivity.Companion.start(MyHomePageActivity.this, "http://www.xlttsports.com/cwap/?authorize_token=" + responseBean.getData());
//                Intent intentWeb = new Intent(MyHomePageActivity.this, ShopMallActivity.class);
//                intentWeb.putExtra(ShopMallActivity.URLS, "http://www.xlttsports.com/cwap/?authorize_token=" + responseBean.getData());
                Log.e(TAG, "onChanged:=== " + "http://www.xlttsports.com/cwap/?authorize_token=" + responseBean.getData());
//                startActivity(intentWeb);
            }
        });


//        config().setVersionInfo(response.data);
//        config().setAppId(response.data.config.appId);
//        application().initEngine(response.data.config.appId);
//        mAppIdTryCount = 0;
//        login();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(HotFixAddEvent event) {
        Beta.applyDownloadedPatch();
    }


    /*??????????????? ??????????????????????????????*/
    private void observer() {
        RxBus.getDefault().toObservable(JumpSelectTionEvent.class).subscribe(tagEvents -> {
            int selectionPosition = tagEvents.getSelectionPosition();
            mCurrentItem = 0;
            setLastImageViewNormal(mLastItem);

            addFragment(mCurrentItem);
            mBinding.ivHomePage.setImageResource(R.drawable.rb_homepage_selected);
        });

    }

    private void initGetMei() {
        if (isNotificationEnabled(MyHomePageActivity.this)) {
            /*????????????????????????????????????*/
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "initGetMei: ?????????");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            } else {

                Log.e(TAG, "initGetMei: ????????????");
            }
//            new RxPermissions(this)
//                    .requestEach(PERMISSIONS,
//                          )
//                    .subscribe(permission -> { // will emit 2 Permission objects
//                        if (permission.granted) {
//                            // `permission.name` is granted !
//                        } else if (permission.shouldShowRequestPermissionRationale) {
//                            // Denied permission without ask never again
//                            PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
//                        } else {
//                            // Denied permission with ask never again
//                            // Need to go to the settings
//                            showMissingPermissionDialog();
//                        }
//                    });

        } else {
            int version = SystemUtil.getVersion();
            Log.e(TAG, "initView: " + SystemUtil.getSystemVersion());
            Log.e(TAG, "initView: " + version);
//            if (version >= 6) {
            myDialog.setGone().setTitle("APP?????????????????????").setMsg("??????????????????????????????????????????????????????????????????????????????").setNegativeButton("?????????",
                    null).setPositiveButton("?????????", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                        intent.putExtra("android.provider.extra.APP_PACKAGE", MyHomePageActivity.this.getPackageName());
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //5.0
                        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                        intent.putExtra("app_package", MyHomePageActivity.this.getPackageName());
                        intent.putExtra("app_uid", MyHomePageActivity.this.getApplicationInfo().uid);
                        startActivity(intent);
                    } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {  //4.4
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setData(Uri.parse("package:" + MyHomePageActivity.this.getPackageName()));
                    } else if (Build.VERSION.SDK_INT >= 15) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        intent.setData(Uri.fromParts("package", MyHomePageActivity.this.getPackageName(), null));
                    }
                    startActivity(intent);

                }
            }).show();
//            }

        }
    }

    /**
     * ????????????window
     */
    public void showUpdateWindow(UpdateBean datas) {
        dismissLoading();
        mShowUpdateWindow = new CommonPopupWindow.Builder(MyHomePageActivity.this)
                .setView(R.layout.pop_update_window)
                .setBackGroundLevel(0.5f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        if (AntiShakeUtils.isInvalidClick(view))
                            return;
                        TextView tvCancel = view.findViewById(R.id.no);
                        TextView tvSure = view.findViewById(R.id.yes);
                        String isF = datas.getUsable();
                        TextView tvMessage = view.findViewById(R.id.message);
                        tvMessage.setText("" + datas.getDescription());
                        if (TextUtils.equals(isF, "1")) {
                            View viewDivider = view.findViewById(R.id.view_divider);
                            viewDivider.setVisibility(View.GONE);
                            tvCancel.setVisibility(View.GONE);
                        }

                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (AntiShakeUtils.isInvalidClick(view))
                                    return;
                                mShowUpdateWindow.dismiss();
                                AccountHelper.setIsNeedUpdate("");

                            }
                        });
                        tvSure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (AntiShakeUtils.isInvalidClick(v))
                                    return;
                                ToastUtils.showToast(MyHomePageActivity.this, "????????????");
                                CheckVersion.updateApp(datas.getDownloadUrl(), MyHomePageActivity.this);
                                mShowUpdateWindow.dismiss();
                                AccountHelper.setIsNeedUpdate("");

                            }
                        });
                    }
                })
                .setOutsideTouchable(true)//????????????????????????
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                .create();
        View view1 = View.inflate(MyHomePageActivity.this, R.layout.activity_main, null);
        String isF = datas.getUsable();
        if (TextUtils.equals(isF, "1")) {
            mShowUpdateWindow.setOutsideTouchable(false);
        } else {
            mShowUpdateWindow.setOutsideTouchable(true);
        }
        if (mShowUpdateWindow == null || !mShowUpdateWindow.isShowing()) {
            mShowUpdateWindow.setFocusable(false);// ???????????????
            mShowUpdateWindow.showAtLocation(view1, Gravity.CENTER, 0, 0);
            AccountHelper.setIsNeedUpdate("");

        }
    }

    /*??????????????????????????????*/
    private boolean isNotificationEnabled(Context context) {
        boolean isOpened = false;
        try {
            isOpened = NotificationManagerCompat.from(context).areNotificationsEnabled();
        } catch (Exception e) {
            e.printStackTrace();
            isOpened = false;
        }
        return isOpened;

    }


    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            boolean isGranted = true;
            for (int index = 0; index < permissions.length; index++) {
                if (grantResults[index] == PackageManager.PERMISSION_DENIED) {
                    isGranted = false;
                }
            }
            if (!isGranted) {
                PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
            }
        } else if (requestCode == 1000) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            } else {
                showMissingPermissionDialog();
            }
        }
    }

    // ????????????????????????
    public void showMissingPermissionDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MyHomePageActivity.this);
        builder.setTitle("??????");
        builder.setMessage("?????????????????????????????????\n?????????\"??????\"-\"??????\"-?????????????????????\n????????????????????????????????????????????????");

        // ??????, ????????????
        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppManager.getAppManager().finishAllActivity();
                AppManager.getAppManager().AppExit();
                System.exit(0);
            }
        });

        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    GoToSetting(MyHomePageActivity.this);
                } catch (Exception e) {
                    ApplicationInfo(MyHomePageActivity.this);
                }
            }
        });

        builder.show();
    }

    /**
     * ??????????????????
     *
     * @param activity
     */
    public static void ApplicationInfo(Activity activity) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
        }
        activity.startActivity(localIntent);
    }

    private void initEvent() {
        mBinding.llHomePage.setOnClickListener(this);
        mBinding.llClub.setOnClickListener(this);
        mBinding.llMine.setOnClickListener(this);
        mBinding.llCompetition.setOnClickListener(this);
        mBinding.llShopMall.setOnClickListener(this);


        homePageFragment = new HomePageFragment();
        clubFragment = new ClubFragment();
        competitionFragment = new CompetitionFragment();
        shopMallFragment = new ShopMallFragment();
        mineFragment = new MineFragment();
    }

    private void initFragmentManager() {
        mFragmentManager = getSupportFragmentManager();
    }

    private void initSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentItem = savedInstanceState.getInt("currentItem");
            homePageFragment = (HomePageFragment) mFragmentManager.findFragmentByTag(TAG_HOME_PAGE);
            clubFragment = (ClubFragment) mFragmentManager.findFragmentByTag(TAG_SPONSOR_FRAGMENT);
            competitionFragment = (CompetitionFragment) mFragmentManager.findFragmentByTag(TAG_BOWLING);
            shopMallFragment = (ShopMallFragment) mFragmentManager.findFragmentByTag(TAG_PARTICIPANT_FRAGMENT);
            mineFragment = (MineFragment) mFragmentManager.findFragmentByTag(TAG_DRAFT_FRAGMENT);
        } else {
            homePageFragment = HomePageFragment.newInstance();
            clubFragment = ClubFragment.newInstance();
            competitionFragment = CompetitionFragment.newInstance();
            shopMallFragment = ShopMallFragment.newInstance();
            mineFragment = MineFragment.newInstance();
        }
    }


    /**
     * ???????????????item???????????????
     */
    private void setTextViewDefaultColor() {
        //????????????????????????????????????
        mBinding.tvHomePage.setTextColor(getResources().getColor(R.color.colorBlack));
        mBinding.tvClub.setTextColor(getResources().getColor(R.color.colorBlack));
        mBinding.tvCompetition.setTextColor(getResources().getColor(R.color.colorBlack));
        mBinding.tvShopMall.setTextColor(getResources().getColor(R.color.colorBlack));
        mBinding.tvMine.setTextColor(getResources().getColor(R.color.colorBlack));
    }
//
//    private void hideAllFragment(FragmentTransaction transaction) {
//        try {
//            transaction
//                    .hide(homePageFragment)
//                    .hide(clubFragment)
//                    .hide(competitionFragment)
//                    .hide(shopMallFragment)
//                    .hide(mineFragment);
//        } catch (Exception e) {
//            Log.e(TAG, "hideAllFragment: " + e.getMessage());
//        }
//
//    }

//    private void showFragment(int index) {
//        try {
//
//
//            FragmentTransaction transaction = mFragmentManager.beginTransaction();
//            setTextViewDefaultColor();
//            switch (index) {
//                case 0:
//                    mBinding.tvHomePage.setTextColor(getResources().getColor(R.color.colorBlue));
//                    if (homePageFragment != null && !homePageFragment.isAdded()) {
//                        ActivityUtils.addFragmentToActivityWithTag(mFragmentManager, homePageFragment,
//                                R.id.fl_main, TAG_HOME_PAGE);
//                    }
//                    hideAllFragment(transaction);
//                    transaction.show(homePageFragment);
//                    break;
//                case 1:
//                    mBinding.tvClub.setTextColor(getResources().getColor(R.color.colorBlue));
////                mSponsorImg.setImageResource(R.mipmap.bule_two_circle);
//                    if (clubFragment != null && !clubFragment.isAdded()) {
//                        ActivityUtils.addFragmentToActivityWithTag(mFragmentManager, clubFragment,
//                                R.id.fl_main, TAG_SPONSOR_FRAGMENT);
//                    }
//                    hideAllFragment(transaction);
//                    transaction.show(clubFragment);
//                    break;
//                case 2:
//                    mBinding.tvCompetition.setTextColor(getResources().getColor(R.color.colorBlue));
////                mAddImg.setImageResource(R.mipmap.bule_two_circle);
//                    if (competitionFragment != null && !competitionFragment.isAdded()) {
//                        ActivityUtils.addFragmentToActivityWithTag(mFragmentManager, competitionFragment,
//                                R.id.fl_main, TAG_BOWLING);
//                    }
//                    hideAllFragment(transaction);
//                    transaction.show(competitionFragment);
//                    break;
//                case 3:
////                mBinding.tvShopMall.setTextColor(getResources().getColor(R.color.colorBlue));
////                mPartImg.setImageResource(R.mipmap.bule_two_circle);
//                    if (shopMallFragment != null && !shopMallFragment.isAdded()) {
//                        ActivityUtils.addFragmentToActivityWithTag(mFragmentManager, shopMallFragment,
//                                R.id.fl_main, TAG_PARTICIPANT_FRAGMENT);
//                    }
//                    hideAllFragment(transaction);
//                    transaction.show(shopMallFragment);
//                    break;
//                case 4:
//
//                    mBinding.tvMine.setTextColor(getResources().getColor(R.color.colorBlue));
////                mDraftImg.setImageResource(R.mipmap.bule_two_circle);
//                    if (mineFragment != null && !mineFragment.isAdded()) {
//                        ActivityUtils.addFragmentToActivityWithTag(mFragmentManager, mineFragment,
//                                R.id.fl_main, TAG_DRAFT_FRAGMENT);
//                    }
//                    hideAllFragment(transaction);
//                    transaction.show(mineFragment);
//                    break;
//            }
//
//            transaction.commit();
//        } catch (Exception e) {
//            Log.e(TAG, "showFragment: " + e.getMessage());
//        }
//    }

    /**
     * ??????fragment??????
     */
    private void addFragment(int fragment) {
        // ????????????????????????FragmentTransaction?????????
        mCurrentItem = fragment;
        setTextViewDefaultColor();
        for (Fragment fragment1 : FragmentUtils.getFragments(fragmentManager)) {
            FragmentUtils.hide(fragment1);
        }
        switch (fragment) {
            case 0:
                mBinding.tvHomePage.setTextColor(getResources().getColor(R.color.colorBlue));
                Fragment homefragment = FragmentUtils.findFragment(fragmentManager, HomePageFragment.class);

                if (homefragment != null) {
                    FragmentUtils.show(homefragment);
                } else {

                    if (homefragment != null) {
                        if (homefragment.isAdded()) {
                            FragmentUtils.show(homefragment);
                        } else {
                            FragmentUtils.add(fragmentManager, homePageFragment, R.id.fl_main);
                        }
                    } else {
                        homePageFragment = new HomePageFragment();
                        FragmentUtils.add(fragmentManager, homePageFragment, R.id.fl_main);
                    }
                }

                break;
            case 1:
                mBinding.tvClub.setTextColor(getResources().getColor(R.color.colorBlue));
                Fragment reportfragment = FragmentUtils.findFragment(fragmentManager, ClubFragment.class);

                if (reportfragment != null) {
                    FragmentUtils.show(reportfragment);
                } else {
                    if (reportfragment != null) {
                        if (reportfragment.isAdded()) {
                            FragmentUtils.show(reportfragment);
                        } else {
                            FragmentUtils.add(fragmentManager, clubFragment, R.id.fl_main);
                        }
                    } else {
                        clubFragment = new ClubFragment();
                        FragmentUtils.add(fragmentManager, clubFragment, R.id.fl_main);
                    }
                }

                break;
            case 2:
                mBinding.tvCompetition.setTextColor(getResources().getColor(R.color.colorBlue));
                Fragment musicfragment = FragmentUtils.findFragment(fragmentManager, CompetitionFragment.class);

                if (musicfragment != null) {
                    FragmentUtils.show(musicfragment);
                } else {
                    if (musicfragment != null) {
                        if (musicfragment.isAdded()) {
                            FragmentUtils.show(musicfragment);
                        } else {
                            FragmentUtils.add(fragmentManager, competitionFragment, R.id.fl_main);

                        }
                    } else {
                        competitionFragment = new CompetitionFragment();
                        FragmentUtils.add(fragmentManager, competitionFragment, R.id.fl_main);

                    }
                }

                break;
            case 3:
                Fragment shopMallFragment1 = FragmentUtils.findFragment(fragmentManager, ShopMallFragment.class);

                if (shopMallFragment1 != null) {
                    FragmentUtils.show(shopMallFragment1);
                } else {
                    if (shopMallFragment1 != null) {
                        if (shopMallFragment1.isAdded()) {
                            FragmentUtils.show(shopMallFragment1);

                        } else {
                            FragmentUtils.add(fragmentManager, shopMallFragment, R.id.fl_main);

                        }
                    } else {
                        shopMallFragment = new ShopMallFragment();
                        FragmentUtils.add(fragmentManager, shopMallFragment, R.id.fl_main);
                    }
                }
                break;
            case 4:
                mBinding.tvMine.setTextColor(getResources().getColor(R.color.colorBlue));
                Fragment minefragment = FragmentUtils.findFragment(fragmentManager, MineFragment.class);

                if (minefragment != null) {
                    FragmentUtils.show(minefragment);
                } else {
                    if (minefragment != null) {
                        if (minefragment.isAdded()) {
                            FragmentUtils.show(minefragment);
                        } else {
                            FragmentUtils.add(fragmentManager, mineFragment, R.id.fl_main);

                        }
                    } else {
                        minefragment = new MineFragment();
                        FragmentUtils.add(fragmentManager, minefragment, R.id.fl_main);
                    }
                }

                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        mLastItem = mCurrentItem;
        switch (id) {
            case R.id.ll_home_page:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    mCurrentItem = 0;
                    setLastImageViewNormal(mLastItem);

                    addFragment(mCurrentItem);
                    mBinding.ivHomePage.setImageResource(R.drawable.rb_homepage_selected);
                } catch (Exception e) {

                }

                break;
            case R.id.ll_club:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    mCurrentItem = 1;
                    setLastImageViewNormal(mLastItem);

                    addFragment(mCurrentItem);
                    mBinding.ivClub.setImageResource(R.drawable.rb_club_selectd);
                } catch (Exception e) {

                }

                break;
            case R.id.ll_competition:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                try {
                    mCurrentItem = 2;
                    setLastImageViewNormal(mLastItem);

                    addFragment(mCurrentItem);
                    mBinding.ivCompetition.setImageResource(R.drawable.rb_competion_selected);
                } catch (Exception e) {

                }

                break;
            case R.id.ll_shop_mall:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
//                mCurrentItem = 3;
//                showFragment(mCurrentItem);
                showLoading();
                mViewModel.phpAes();

//                Intent intentWeb = new Intent(this, ShopMallActivity.class);
//                intentWeb.putExtra(ShopMallActivity.URLS, "http://www.xlttsports.com/cwap/");
//                startActivity(intentWeb);


//                goActivity(ShopMallActivity.class);
//                mBinding.ivShopMall.setImageResource(R.drawable.rb_shopmall_unselected);
                break;
            case R.id.ll_mine:
                try {
                    if (AntiShakeUtils.isInvalidClick(v))
                        return;
                    mCurrentItem = 4;
                    setLastImageViewNormal(mLastItem);
                    addFragment(mCurrentItem);
                    mBinding.ivMine.setImageResource(R.drawable.rb_mine_selected);
                } catch (Exception e) {

                }

                break;
        }
    }

    /**
     * ????????????????????????item?????????????????????????????????
     *
     * @param lastItem
     */
    private void setLastImageViewNormal(int lastItem) {
        switch (lastItem) {
            case 0://??????
                mBinding.ivHomePage.setImageResource(R.drawable.rb_homepage_unselected);
                break;
            case 1://?????????
                mBinding.ivClub.setImageResource(R.drawable.rb_club_unselected);
                break;
            case 2://??????
                mBinding.ivCompetition.setImageResource(R.drawable.rb_competition_unselected);
                break;
            case 3:
                mBinding.ivShopMall.setImageResource(R.drawable.rb_shopmall_unselected);
                break;
            case 4:
                mBinding.ivMine.setImageResource(R.drawable.rb_mine_unselected);
                break;

        }
    }

    /**
     * ???????????????????????????
     *
     * @param activity
     */
    public static void GoToSetting(Activity activity) {
        switch (Build.MANUFACTURER) {
            case "Huawei":
                Huawei(activity);
                break;
            case "Meizu":
                Meizu(activity);
                break;
            case "Xiaomi":
                Xiaomi(activity);
                break;
            case "Sony":
                Sony(activity);
                break;
            case "OPPO":
                OPPO(activity);
                break;
            case "LG":
                LG(activity);
                break;
            case "Letv":
                Letv(activity);
                break;
            default:
                ApplicationInfo(activity);
//                SystemConfig(activity);
//                LogUtlis.i("???????????????????????????");
                break;
        }
    }

    public static void Huawei(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    public static void Meizu(Activity activity) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        activity.startActivity(intent);
    }

    private static String getMiuiVersion() {
        String propName = "ro.miui.ui.version.name";
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return line;
    }

    public static void Xiaomi(Activity activity) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.setComponent(componentName);
        intent.putExtra("extra_pkgname", BuildConfig.APPLICATION_ID);
        activity.startActivity(intent);
    }

    public static void Sony(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    public static void OPPO(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    public static void LG(Activity activity) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    public static void Letv(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    /**
     * ?????????????????????????????????
     *
     * @param activity
     */
    public static void _360(Activity activity) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private Integer getVersionCode() throws Exception {
        // ??????packagemanager?????????
        PackageManager packageManager = getPackageManager();
        // getPackageName()???????????????????????????0???????????????????????????
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        int version = packInfo.versionCode;
        return version;
    }
}
