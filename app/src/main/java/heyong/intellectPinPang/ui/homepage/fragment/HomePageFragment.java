package heyong.intellectPinPang.ui.homepage.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.elvishew.xlog.XLog;
import com.google.gson.Gson;
import com.google.zxing.activity.CaptureActivity;
import com.ms.banner.BannerConfig;
import com.ms.banner.holder.BannerViewHolder;
import com.ms.banner.holder.HolderCreator;
import com.ms.banner.listener.OnBannerClickListener;
import com.superluo.textbannerlibrary.ITextBannerItemClickListener;
import com.xq.fasterdialog.dialog.CustomDialog;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import heyong.intellectPinPang.bean.homepage.AppAdvertisementBean;
import heyong.intellectPinPang.ui.BaseFragment;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.handong.framework.utils.ToastUtils;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.HomePageAdapter;
import heyong.intellectPinPang.bean.competition.ArrangeDrawBean;
import heyong.intellectPinPang.bean.competition.ArrangeDrawDataBean;
import heyong.intellectPinPang.bean.competition.JudgeUnCompletePageBean;
import heyong.intellectPinPang.bean.competition.MatchBaseInfoBean;
import heyong.intellectPinPang.bean.competition.NearFetureBean;
import heyong.intellectPinPang.bean.competition.XlMatchInfoBlueDataBean;
import heyong.intellectPinPang.bus.RxBus;
import heyong.intellectPinPang.databinding.FragmentHomePageBinding;
import heyong.intellectPinPang.event.EventBusCityEvent;
import heyong.intellectPinPang.event.JumpSelectTionEvent;
import heyong.intellectPinPang.ui.club.activity.CoachDrawLotsHostAndGusetActivity;
import heyong.intellectPinPang.ui.club.activity.CoachFillMatchFormActivity;
import heyong.intellectPinPang.ui.club.activity.CreateClubActivity;
import heyong.intellectPinPang.ui.club.activity.TeamResultsOfficialActivity;
import heyong.intellectPinPang.ui.competition.activity.DoubleDetailOfficialActivity;
import heyong.intellectPinPang.ui.competition.activity.DrawForHostAndVistors;
import heyong.intellectPinPang.ui.competition.activity.EventSignUpActivity;
import heyong.intellectPinPang.ui.competition.activity.SingleFirstOptionActivity;
import heyong.intellectPinPang.ui.competition.activity.SinglesDetailOfficialActivity;
import heyong.intellectPinPang.ui.homepage.activity.CompetitionNewsActivity;
import heyong.intellectPinPang.ui.homepage.activity.CreateEventsActivity;
import heyong.intellectPinPang.ui.mine.activity.live.LiveGameListActivity;
import heyong.intellectPinPang.ui.homepage.activity.NewsDetailActivity;
import heyong.intellectPinPang.ui.homepage.activity.SearchActivity;
import heyong.intellectPinPang.ui.homepage.viewmodel.HomePageViewModel;
import heyong.intellectPinPang.ui.mine.activity.ConfirmationResultsActivity;
import heyong.intellectPinPang.ui.mine.activity.SportsConfirmationResultsActivity;
import heyong.intellectPinPang.ui.shopmall.fragment.ShopMallKotlinActivity;
import heyong.intellectPinPang.widget.AlertDialog;
import heyong.intellectPinPang.widget.MyCustomViewHolder;
import heyong.intellectPinPang.widget.MyMZCustomViewHolder;

import static heyong.intellectPinPang.ui.BaseActivity.isFastClick;

/**
 * @Name：weiying
 * @Description：首页
 * @Author：whf 修改人：whf
 * 修改备注：
 */
public class HomePageFragment extends BaseFragment<FragmentHomePageBinding, HomePageViewModel> implements View.OnClickListener, AMapLocationListener, MZBannerView.BannerPageClickListener {

    View contentView;
    List<String> bannerList = new ArrayList<>();
    private HomePageAdapter homePageAdapter;
    public static final String TAG = HomePageFragment.class.getSimpleName();
    private AMapLocationClient mlocationClient;//定位监听
    private AMapLocationClientOption mLocationOption = null;//定位监听
    int locationNum = 0;//判断定位的次数
    private String city = "";//location city
    public static final int LOCATION_CODE_CITY = 1003;//Location access to the city
    private AlertDialog myDialog;//弹窗
    List<NearFetureBean> data;
    List<String> myData;
    //ali打开扫描界面请求码
    private int ALI_REQUEST_CODE = 99;
    public static final int CALL_LOGIN = 1002;//Call permission code
    List<AppAdvertisementBean> dataAdvertiseData = new ArrayList<>();
    private long matchId;
    private int clickPosition = 0;

    public HomePageFragment() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initLocation();
            heyong.lzy.imagepicker.util.StatusBarUtil.statusBarLightMode(getActivity());
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initLocation();
            heyong.lzy.imagepicker.util.StatusBarUtil.statusBarLightMode(getActivity());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: HomePageFragment");
//        heyong.lzy.imagepicker.util.StatusBarUtil.statusBarLightMode(getActivity());

//        StatusBarUtil.statusBarLightMode(getActivity());
        viewModel.theNearFuture();
//        viewModel.blueData();
        viewModel.appAdvertisement();
        initLocation();
        binding.mMarqueeView.startViewAnimator();


    }

    public static HomePageFragment newInstance() {

        Bundle args = new Bundle();

        HomePageFragment fragment = new HomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home_page;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        contentView = LayoutInflater.from(getActivity()).inflate(R.layout.header_homepage_view, null);
        binding.setListener(this);
        viewModel.judgeUnCompletePage();
        heyong.lzy.imagepicker.util.StatusBarUtil.statusBarLightMode(getActivity());
        initAllBanner();
        myDialog = new AlertDialog(getActivity()).builder();
        viewModel.judgeUnCompletePageLiveData.observe(this, new Observer<ResponseBean<JudgeUnCompletePageBean>>() {
            @Override
            public void onChanged(ResponseBean<JudgeUnCompletePageBean> responseBean) {
                if (responseBean.getErrorCode().equals("100000-100044")) {
                    if (responseBean.getData() != null) {
                        JudgeUnCompletePageBean data = responseBean.getData();
                        int page = Integer.parseInt(data.getPage());
//                        APP运行中，退出APP，再进入时，恢复到原页面：
//                        1、裁判员：主客队抽签页面
//                        2、裁判员单打，双打优先权抽签页面
//                        3、教练员查看抽签结果页面
//                        4、教练员填写对阵名单页面
//                        5、裁判员填写对阵名单页面
//                        6、裁判员：输入团体比赛成绩页面
//                        7、裁判员：输入单打、双打成绩页面
                        String createTime = data.getCreateTime();

                        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                Locale.CHINA);
                        Date date;
                        String times = null;
                        //1616567840000
                        //1618364686787  当前
                        try {
                            date = sdr.parse(createTime);
                            long l = date.getTime();
                            String stf = String.valueOf(l);
                            times = stf;
                            Log.e(TAG, "onChanged: " + times);
                            Log.e(TAG, "onChanged: 转换后的数据===" + Long.parseLong(times));
                            Log.e(TAG, "onChanged: 当前时间戳" + System.currentTimeMillis());
                            if (Long.parseLong(times) >= System.currentTimeMillis()) {
                                Log.e(TAG, "onChanged: 大于");
                                goData(data, page);
                            } else {
                                Log.e(TAG, "onChanged: 小于");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG, "onChanged: " + e.getMessage());
                        }


                    } else {
                        toast("" + responseBean.getMessage());
                    }
                }
            }
        });
        initLocation();
        initBanner(new ArrayList<>());


        homePageAdapter = new HomePageAdapter(getActivity());
        binding.mRecyclerView.setAdapter(homePageAdapter);
        binding.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        homePageAdapter.setNewData(Arrays.asList("1", "1", "1", "1"));
        homePageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    goActivity(NewsDetailActivity.class);
                }
            }
        });
        //设置数据
        viewModel.phpAesLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                AppAdvertisementBean appAdvertisementBean = dataAdvertiseData.get(clickPosition);
                ShopMallKotlinActivity.Companion.start(getActivity(), appAdvertisementBean.getLinkUrl() + responseBean.getData());
            }
        });

        viewModel.checkUserPowerLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {

                    goActivity(CreateEventsActivity.class);
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        viewModel.judgeMyInfoLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {

                    Intent intent = new Intent(getActivity(), CreateClubActivity.class);
                    intent.putExtra(CreateClubActivity.CLUB_TYPE, 0);
                    startActivity(intent);
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        viewModel.appScanCodeLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    toast("" + responseBean.getMessage());
                } else {
                    toast("" + responseBean.getMessage());
                }

            }
        });

        //首页近期比赛
        viewModel.theNearFutureLiveData.observe(this, new Observer<ResponseBean<List<NearFetureBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<NearFetureBean>> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    binding.llAgentGame.setVisibility(View.VISIBLE);
                    data = responseBean.getData();
                    myData = new ArrayList<>();
                    if (data != null && data.size() > 0) {
                        for (int i = 0; i < data.size(); i++) {
                            myData.add("" + data.get(i).getMatchImgUrl());
//                            myData.add("" + data.get(i).getMatchImgUrl());
                        }
                        NearFetureBean nearFetureBean = data.get(0);
                        binding.tvJinqiZhubanfang.setText("主办方：" + nearFetureBean.getSponsor());
                        binding.tvJinqiTime.setText("" + nearFetureBean.getBeginTime());
                        binding.tvJinqiAddress.setText("" + nearFetureBean.getHoldCity() + " " + nearFetureBean.getVenue());
                        binding.tvJinqiTitle.setText("" + nearFetureBean.getMatchTitle());

                        binding.mineBanner
                                .setPages(myData, new HolderCreator<BannerViewHolder>() {
                                    @Override
                                    public BannerViewHolder createViewHolder() {
                                        return new MyCustomViewHolder();
                                    }
                                })
                                .setAutoPlay(true)
                                .setDelayTime(3000)
                                .start();
                        binding.mineBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            }

                            @Override
                            public void onPageSelected(int position) {
                                NearFetureBean nearFetureBean1 = data.get(position);
                                binding.tvJinqiZhubanfang.setText("主办方：" + nearFetureBean1.getSponsor());
                                binding.tvJinqiTime.setText("" + nearFetureBean1.getBeginTime());
                                binding.tvJinqiAddress.setText("" + nearFetureBean1.getHoldCity() + " " + nearFetureBean1.getVenue());
                                binding.tvJinqiTitle.setText("" + nearFetureBean1.getMatchTitle());

                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });


                    } else {
                        binding.llAgentGame.setVisibility(View.GONE);
                    }
                } else {
                    binding.llAgentGame.setVisibility(View.GONE);
                }

                viewModel.myJoinMatch();
            }
        });
        //我的比赛
        viewModel.myJoinMatchLiveData.observe(this, new Observer<ResponseBean<NearFetureBean>>() {
            @Override
            public void onChanged(ResponseBean<NearFetureBean> responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    binding.llMygame.setVisibility(View.VISIBLE);
                    NearFetureBean data = responseBean.getData();
                    if (data != null) {
//                        ImageLoader.displayLocalCornersImage(binding.ivLogo, data.getMatchImgUrl(), R.drawable.ic_default_image_write, R.drawable.ic_default_image_write);
                        ImageLoader.displayLocalCornersImage(getActivity(), data.getMatchImgUrl(), binding.ivLogo, 5);

                        binding.tvZhubanfang.setText("主办方：" + data.getSponsor());
                        binding.tvTime.setText("" + data.getBeginTime());
                        binding.tvAddress.setText("" + data.getHoldCity() + " " + data.getVenue());
                        binding.tvTitle.setText("" + data.getMatchTitle());
                        matchId = data.getId();
                    } else {
                        binding.llMygame.setVisibility(View.GONE);
                    }
                } else {
                    binding.llMygame.setVisibility(View.GONE);
                }


            }
        });

        viewModel.appAdvertisementData.observe(this, new Observer<ResponseBean<List<AppAdvertisementBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<AppAdvertisementBean>> responseBean) {
                if (responseBean.getData() != null && responseBean.getData().size() > 0) {
                    dataAdvertiseData.clear();
                    dataAdvertiseData = responseBean.getData();

//                    initBanner(responseBean.getData());
                }
            }
        });

        viewModel.joinBymessageLiveData.observe(this, new Observer<ResponseBean<MatchBaseInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<MatchBaseInfoBean> matchBaseInfoBeanResponseBean) {
                if (matchBaseInfoBeanResponseBean.getErrorCode().equals("200")) {
                    MatchBaseInfoBean data = matchBaseInfoBeanResponseBean.getData();
                    if (data != null) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), CoachDrawLotsHostAndGusetActivity.class);
                        intent.putExtra("ids", "" + data.getArrangeId());
                        startActivity(intent);
                    } else {
                        toast("" + matchBaseInfoBeanResponseBean.getMessage());
                    }
                } else {
                    toast("" + matchBaseInfoBeanResponseBean.getMessage());
                }
            }
        });
        //小蓝条数据
        viewModel.blueDataLiveData.observe(this, new Observer<ResponseBean<List<XlMatchInfoBlueDataBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<XlMatchInfoBlueDataBean>> nearFetureBeanResponseBean) {
                if (nearFetureBeanResponseBean.getData() != null && nearFetureBeanResponseBean.getData().size() > 0) {
                    binding.llContent.setVisibility(View.VISIBLE);
                    List<XlMatchInfoBlueDataBean> data = nearFetureBeanResponseBean.getData();
                    List<String> containerList = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        containerList.add(data.get(i).getTitle());
                    }
                    binding.mMarqueeView.setDatas(containerList);
//设置TextBannerView点击监听事件，返回点击的data数据, 和position位置
                    binding.mMarqueeView.setItemOnClickListener(new ITextBannerItemClickListener() {
                        @Override
                        public void onItemClick(String datas, int position) {
                            XlMatchInfoBlueDataBean xlMatchInfoBlueDataBean = data.get(position);
                            int category = Integer.parseInt(xlMatchInfoBlueDataBean.getCategory());
                            long id = xlMatchInfoBlueDataBean.getId();
                            long relationId = xlMatchInfoBlueDataBean.getRelationId();
                            String message = xlMatchInfoBlueDataBean.getMessage();
                            String updateTime = xlMatchInfoBlueDataBean.getUpdateTime();
                            String title = xlMatchInfoBlueDataBean.getTitle();
                            if (category == 3)//跳转到教练员抽签界面
                            {
                                viewModel.joinBymessage("" + id, "" + relationId);
                            } else if (category == 2)//运动员评分
                            {
                                Intent intent = new Intent();
                                intent.setClass(getActivity(), SportsConfirmationResultsActivity.class);
                                intent.putExtra("ids", "" + id);
                                intent.putExtra("relationId", "" + relationId);
                                intent.putExtra("message", "" + message);
                                intent.putExtra("updateTime", "" + updateTime);
                                intent.putExtra("title", "" + title);
                                intent.putExtra("status", "" + xlMatchInfoBlueDataBean.getStatus());
                                startActivity(intent);
                            } else if (category == 4)//教练员评分
                            {
                                Intent intent = new Intent();
                                intent.setClass(getActivity(), ConfirmationResultsActivity.class);
                                intent.putExtra("ids", "" + id);
                                intent.putExtra("relationId", "" + relationId);
                                intent.putExtra("message", "" + message);
                                intent.putExtra("updateTime", "" + updateTime);
                                intent.putExtra("title", "" + title);
                                intent.putExtra("status", "" + xlMatchInfoBlueDataBean.getStatus());
                                startActivity(intent);
                            }


                        }
                    });
                } else {
                    binding.llContent.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initAllBanner() {
        binding.banner.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 10);
            }
        });
        binding.banner.setClipToOutline(true);
        binding.banner.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 10);
            }
        });
        binding.banner.setClipToOutline(true);


//        binding.banner.setIndicatorRes(R.drawable.banner_indicator_shop_selected, R.drawable.banner_indicator_shop_unselected);
        binding.banner.updateBannerStyle(BannerConfig.NOT_INDICATOR);

        binding.mineBanner.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 10);
            }
        });
        binding.mineBanner.setClipToOutline(true);


        binding.mineBanner.setIndicatorRes(R.drawable.banner_indicator_shop_selected,
                R.drawable.banner_indicator_shop_unselected);
        binding.mineBanner.updateBannerStyle(BannerConfig.CUSTOM_INDICATOR);

        binding.banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                XLog.e("position banner===="+position);
            }
        });
        binding.mineBanner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                XLog.e("position mineBanner===="+position);

                long id1 = data.get(position).getId();
                Intent intent = new Intent(getActivity(), EventSignUpActivity.class);
                intent.putExtra(EventSignUpActivity.SIGNUPID, "" + id1);
                startActivity(intent);
            }
        });
    }

    private void initBanner(List<AppAdvertisementBean> data) {
        XLog.e("bannList===size===" + data.size());
        bannerList.clear();
        if (data.size() > 0) {
            for (int m = 0; m < data.size(); m++) {
                bannerList.add(data.get(m).getImgUrl());
                XLog.e("+++ position===" + m + "url===" + data.get(m).getImgUrl());
            }
        } else {
            bannerList.add("http://images.xlttsports.com/banner_5.jpg");
            bannerList.add("http://images.xlttsports.com/banner4.jpg");
            bannerList.add("http://images.xlttsports.com/banner2.jpg");
            bannerList.add("http://images.xlttsports.com/banner3.jpg");
        }
        XLog.e("bannList===" + bannerList.size());


        binding.banner
                .setPages(bannerList, new HolderCreator<BannerViewHolder>() {
                    @Override
                    public BannerViewHolder createViewHolder() {
                        return new MyCustomViewHolder();
                    }
                })
                .setAutoPlay(true)
                .setDelayTime(3000)
                .start();


    }


    private void goData(JudgeUnCompletePageBean data, int page) {
        String param = data.getParam();
        if (page == 1)//裁判员 主客队抽签界面
        {
            ArrangeDrawBean matchBaseInfoBean = new Gson().fromJson(param, ArrangeDrawBean.class);
            ArrangeDrawDataBean arrangeDrawDataBean = new ArrangeDrawDataBean("" + matchBaseInfoBean.getTitle(),
                    "" + matchBaseInfoBean.getItemType(), "" + matchBaseInfoBean.getArrangeId(), ""
                    + matchBaseInfoBean.getLeftId(), "" + matchBaseInfoBean.getLeft1Name(), "" + matchBaseInfoBean.getLeft1Img(),
                    "" + matchBaseInfoBean.getLeft2Name(), "" + matchBaseInfoBean.getLeft2Img(),
                    "" + matchBaseInfoBean.getRightId(), "" + matchBaseInfoBean.getRight1Name(), ""
                    + matchBaseInfoBean.getRight1Img(), "" + matchBaseInfoBean.getRight2Name()
                    , "" + matchBaseInfoBean.getRight2Img(), "" + matchBaseInfoBean.getMatchId());
            Intent intent = new Intent();
            intent.setClass(getActivity(), DrawForHostAndVistors.class);
            intent.putExtra("data", (Serializable) arrangeDrawDataBean);
            startActivity(intent);
        } else if (page == 2)//裁判员单打，双打优先权抽签界面
        {
//                            SingleFirstOptionActivity   ArrangeDrawBean arrangeDrawBean
            ArrangeDrawBean matchBaseInfoBean = new Gson().fromJson(param, ArrangeDrawBean.class);
            ArrangeDrawDataBean arrangeDrawDataBean2 = new ArrangeDrawDataBean("" + matchBaseInfoBean.getTitle(),
                    "" + matchBaseInfoBean.getItemType(), "" + matchBaseInfoBean.getArrangeId(), ""
                    + matchBaseInfoBean.getLeftId(), "" + matchBaseInfoBean.getLeft1Name(), "" + matchBaseInfoBean.getLeft1Img(),
                    "" + matchBaseInfoBean.getLeft2Name(), "" + matchBaseInfoBean.getLeft2Img(),
                    "" + matchBaseInfoBean.getRightId(), "" + matchBaseInfoBean.getRight1Name(), ""
                    + matchBaseInfoBean.getRight1Img(), "" + matchBaseInfoBean.getRight2Name()
                    , "" + matchBaseInfoBean.getRight2Img(), "" + matchBaseInfoBean.getMatchId());
            Intent intent = new Intent();
            intent.setClass(getActivity(), SingleFirstOptionActivity.class);
            intent.putExtra("data", (Serializable) arrangeDrawDataBean2);
            startActivity(intent);
        } else if (page == 3)//教练员查看抽签结果页面
        {
//                            CoachDrawLotsHostAndGusetActivity  MatchBaseInfoBean matchBaseInfoBean
            MatchBaseInfoBean matchBaseInfoBean = new Gson().fromJson(param, MatchBaseInfoBean.class);
            Intent intent = new Intent();
            intent.setClass(getActivity(), CoachDrawLotsHostAndGusetActivity.class);
            intent.putExtra("ids", "" + matchBaseInfoBean.getArrangeId());
            startActivity(intent);
        } else if (page == 4)//教练员填写对阵名单界面
        {
//                            CoachFillMatchFormActivity    MatchBaseInfoBean matchBaseInfoBean
            Gson gson = new Gson();
            MatchBaseInfoBean matchBaseInfoBean = gson.fromJson(param, MatchBaseInfoBean.class);

            CoachFillMatchFormActivity.goActivity(getActivity(), matchBaseInfoBean);
        } else if (page == 5)//裁判员填写对阵名单页面
        {
//                            CoachFillMatchFormActivity    MatchBaseInfoBean matchBaseInfoBean
            Gson gson = new Gson();
            MatchBaseInfoBean matchBaseInfoBean = gson.fromJson(param, MatchBaseInfoBean.class);
            CoachFillMatchFormActivity.goActivity(getActivity(), matchBaseInfoBean);

        } else if (page == 6)//输入团体比赛成绩页面
        {
            Intent intent = new Intent();
            Gson gson = new Gson();
            MatchBaseInfoBean matchBaseInfoBean = gson.fromJson(param, MatchBaseInfoBean.class);
            intent.setClass(getActivity(), TeamResultsOfficialActivity.class);
            intent.putExtra("ids", "" + matchBaseInfoBean.getArrangeId());
            startActivity(intent);
        } else if (page == 7)//输入单打 成绩页面
        {
            //需要判断是单打还是双打
            Intent intent2 = new Intent();
            MatchBaseInfoBean matchBaseInfoBean = new Gson().fromJson(param, MatchBaseInfoBean.class);
            intent2.setClass(getActivity(), SinglesDetailOfficialActivity.class);
            intent2.putExtra("ids", "" + matchBaseInfoBean.getArrangeId());
            startActivity(intent2);
        } else if (page == 8)//双打
        {
            MatchBaseInfoBean matchBaseInfoBean = new Gson().fromJson(param, MatchBaseInfoBean.class);
            Intent intent3 = new Intent();
            intent3.setClass(getActivity(), DoubleDetailOfficialActivity.class);
            intent3.putExtra("ids", "" + matchBaseInfoBean.getArrangeId());
//                            if(TextUtils.isEmpty(matchBaseInfoBean))
            startActivity(intent3);
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
//            Log.e(TAG, "initLocation: 去获取定位权限");
            Log.e(TAG, "initGetMei: 启动定位fragment 获取权限");
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_CODE_CITY);
        } else {
            Log.e(TAG, "initGetMei: 开始启动定位fragment 获取权限");

            mlocationClient.startLocation();
//            Log.e(TAG, "initLocation: 又再次获取位置以后 直接启动定位");

        }


    }


    @Override
    public void onStop() {
        super.onStop();
        binding.mMarqueeView.stopViewAnimator();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_qr_code:
//
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CALL_LOGIN);
                } else {
                    Intent aliIntent = new Intent();
                    aliIntent.setClass(getActivity(), CaptureActivity.class);
                    startActivityForResult(aliIntent, ALI_REQUEST_CODE);
                }
//                startActivity(new Intent(getActivity(), MyFriendCircleActivity.class));


                break;
            case R.id.ll_mygame:
//                long id1 = data.get(position).getId();
                Intent intent = new Intent(getActivity(), EventSignUpActivity.class);
                intent.putExtra(EventSignUpActivity.SIGNUPID, "" + matchId);
                startActivity(intent);


                break;
            case R.id.ll_create_game://创建比赛
                showLoading();
                viewModel.checkUserPower();


                break;
            case R.id.ll_create_club://创建俱乐部
                showLoading();
                viewModel.judgeMyInfo();

                break;
            case R.id.ll_search://搜索
                goActivity(SearchActivity.class);

                break;
            case R.id.ll_ranking_list:
                ToastUtils.showToast(getActivity(), "暂未开放");
//                goActivity(RankingListActivity.class);
                break;
            case R.id.ll_saishi://赛事新闻
                goActivity(CompetitionNewsActivity.class);

                break;
            case R.id.ll_live://直播
                goActivity(LiveGameListActivity.class);

                break;
            case R.id.ll_recent_more:

                RxBus.getDefault().post(new JumpSelectTionEvent(2));
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
                city = "";

            }
            binding.tvCity.setText("" + city);
//            Log.e(TAG, "onLocationChanged:city===1111111111=================== " + city);
            EventBus.getDefault().post(new EventBusCityEvent(city));
        }
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ALI_REQUEST_CODE) {

            if (data != null) {
                Bundle bundle = data.getExtras();
                final String scanResult = bundle.getString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);
//                ToastUtils.showToast(getActivity(), "" + scanResult);
                showQueryTable(scanResult);
//            counterPresenter.toPayAli(scanResult, tvAmountInput.getAmountText().toString());
            }
        }

    }

    private void showQueryTable(String scanResult) {
        if (!TextUtils.isEmpty(scanResult)) {
            CustomDialog customDialogConfirm = new CustomDialog(getActivity());
            customDialogConfirm.setCustomView(R.layout.pop_query_bg);
            customDialogConfirm.setCancelable(false);
            customDialogConfirm.setCanceledOnTouchOutside(false);
            customDialogConfirm.show();

            View customView = customDialogConfirm.getCustomView();
            TextView tvSubmit = customView.findViewById(R.id.tv_submit);
            ImageView ivClose = customView.findViewById(R.id.iv_close);
            tvSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoading();
                    viewModel.appScanCode(scanResult);
                    customDialogConfirm.dismiss();
                }
            });
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialogConfirm.dismiss();
                }
            });


        } else {
            toast("请扫描登录后台");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_CODE_CITY) {
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
//                    HomePageCommonManager.getInstance().showMissingDialog(myDialog, getActivity());
                } else {
                    mlocationClient.startLocation();
                }
            }
        } else if (requestCode == CALL_LOGIN) {
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
//                    HomePageCommonManager.getInstance().showMissingDialog(myDialog, getActivity());
                } else {
                    Intent aliIntent = new Intent();
                    aliIntent.setClass(getActivity(), CaptureActivity.class);
                    startActivityForResult(aliIntent, ALI_REQUEST_CODE);
                }
            }
        }
    }

    @Override
    public void onPageClick(View view, int position) {
        if (dataAdvertiseData.size() > 0) {
            showLoading();
            viewModel.phpAes();
            clickPosition = position;
        } else {

        }
    }
}
