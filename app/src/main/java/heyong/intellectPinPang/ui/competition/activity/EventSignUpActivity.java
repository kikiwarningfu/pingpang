package heyong.intellectPinPang.ui.competition.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

//import com.tendcloud.tenddata.TCAgent;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.xq.fasterdialog.dialog.CustomDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.OrderBookBean;
import heyong.intellectPinPang.bean.competition.GameMatchBean;
import heyong.intellectPinPang.databinding.ActivityEventSignUpBinding;
import heyong.intellectPinPang.live.activity.YourOrderListActivity;
import heyong.intellectPinPang.ui.club.activity.RegistrationDetailActivity;
import heyong.intellectPinPang.ui.competition.CompetitionViewModel;
import heyong.intellectPinPang.ui.homepage.activity.FileDisplayActivity;
import heyong.intellectPinPang.ui.homepage.activity.GameDetailActivity;
import heyong.intellectPinPang.ui.mine.activity.RealNameImageActivity;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.AppManager;
import heyong.intellectPinPang.utils.MapUtil;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.utils.RxBusSubscriber;
import heyong.intellectPinPang.widget.AlertDialog;

import static heyong.intellectPinPang.ui.MyHomePageActivity.ApplicationInfo;

/**
 * 赛事详情页面UI 1
 */
public class EventSignUpActivity extends BaseActivity<ActivityEventSignUpBinding, CompetitionViewModel> implements View.OnClickListener {


    public static final String SIGNUPID = "sign_up_id";
    private String strSignUpId = "";
    //三种状态  报名中 进行中  已结束
    private int status = 0;
    double laty;
    double latx;
    private String address = "";
    GameMatchBean gameMatchBean;
    private String path = "";
    private String phoneNumber = "";
    public static final String TAG = EventSignUpActivity.class.getSimpleName();
    private AlertDialog myDialog;//弹窗


    @Override
    public int getLayoutRes() {
        return R.layout.activity_event_sign_up;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.gameMatch("" + strSignUpId);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        strSignUpId = getIntent().getStringExtra(SIGNUPID);
        mViewModel.gameMatch("" + strSignUpId);
        myDialog = new AlertDialog(EventSignUpActivity.this).builder();
        AppManager.getAppManager().addActivity(this);
        mBinding.setListener(this);
        mViewModel.gameMatchLiveData.observe(this, new Observer<ResponseBean<GameMatchBean>>() {
            @Override
            public void onChanged(ResponseBean<GameMatchBean> listResponseBean) {
                if (listResponseBean.getData() != null) {
                    gameMatchBean = listResponseBean.getData();
                    mBinding.tvTitle.setText("" + gameMatchBean.getMatchTitle());
                    ImageLoader.loadImage(mBinding.ivSaishiBg, gameMatchBean.getMatchImgUrl());
                    //0是不收费   1是收费
                    mBinding.tvCompetotonTime.setText("比赛时间：" + gameMatchBean.getBeginTime() + "——" + gameMatchBean.getEndTime());
                    status = Integer.parseInt(gameMatchBean.getMatchStatus());
//                    mBinding.tvCurrentPeopleNum.setText("" + gameMatchBean.getJoinCount());
                    mBinding.tvEndTime.setText("报名截止：" + gameMatchBean.getRegistrationDeadline());
                    mBinding.tvConnectName.setText("联系人：" + gameMatchBean.getLinkPerson());
                    mBinding.tvConnectTell.setText("" + gameMatchBean.getLinkNum());
                    phoneNumber = gameMatchBean.getLinkNum();
                    mBinding.tvAddress.setText("" + gameMatchBean.getHoldCity() + " " + gameMatchBean.getVenue());
                    mBinding.tvZhubanfang.setText("主办方：" + gameMatchBean.getSponsor());
                    mBinding.tvChengbanfang.setText("承办方：" + gameMatchBean.getOrganizer());
                    String lngLat = gameMatchBean.getLngLat();
                    /*截取逗号之前的*/
                    latx = Double.parseDouble(lngLat.substring(0, lngLat.indexOf(",")));
                    /*截取逗号之后的*/
                    laty = Double.parseDouble(lngLat.substring(lngLat.indexOf(",")).replace(",", ""));
                    address = gameMatchBean.getHoldCity() + " " + gameMatchBean.getVenue();
                    Log.e(TAG, "onChanged: " + new Gson().toJson(gameMatchBean));
                    switch (status) {
                        case 1://报名中  我要报名还有 比赛详情可以点击
                            mBinding.tvSignUpDetailText.setText("报名参赛");
                            mBinding.tvTextStatusOrder.setText("报名中");
//                            mBinding.llRegistrationOf.setVisibility(View.VISIBLE);
//                            mBinding.llProceedAndEnd.setVisibility(View.GONE);
//                            mBinding.llWantSignUp.setBackgroundResource(R.drawable.shape_club_blue);
//                            mBinding.llWantSignUp.setEnabled(true);
//                            mBinding.tvPlayDetail.setEnabled(false);
//                            mBinding.tvPlayDetail.setText("比赛详情");
//                            mBinding.tvPlayDetail.setBackgroundResource(R.drawable.shape_club_gray_littele);
//                            mBinding.tvResults.setEnabled(false);
//                            mBinding.tvResults.setBackgroundResource(R.drawable.shape_club_gray_littele);
//                            mBinding.tvProgram.setEnabled(false);
//                            mBinding.tvProgram.setBackgroundResource(R.drawable.shape_club_gray_littele);
//                            mBinding.tvSignUpStatus.setBackgroundResource(R.drawable.shape_club_blue);
//                            mBinding.tvSignUpStatus.setText("报名中");
//                            mBinding.tvSigunUpMoney.setText("¥" + gameMatchBean.getReplaceCharge() + "/人");
//                            String replaceCharge = gameMatchBean.getReplaceCharge();
//                            int iReplaceCharge = Integer.parseInt(replaceCharge);
//                            if (iReplaceCharge == 0) {
//                                //不收报名费
//                                mBinding.tvSigunUpMoney.setText("免费");
//                            } else {
//                                //收报名费
//                                int ownFree = gameMatchBean.getOwnFree();
//                                if (ownFree == 0) {
//                                    mBinding.tvSigunUpMoney.setText("由主办方收取");
//                                } else {
//                                    mBinding.tvSigunUpMoney.setText("¥" + "" + ownFree + "/人");
//                                }
//                            }
                            break;
                        case 2://进行中
//                            mBinding.llRegistrationOf.setVisibility(View.GONE);
//                            mBinding.llProceedAndEnd.setVisibility(View.VISIBLE);
//                            mBinding.llWantSignUp.setBackgroundResource(R.drawable.shape_club_gray_littele);
//                            mBinding.llWantSignUp.setEnabled(false);
//                            mBinding.tvPlayDetail.setEnabled(true);
//                            mBinding.tvPlayDetail.setBackgroundResource(R.drawable.shape_club_yellow);
//                            mBinding.tvResults.setEnabled(false);
//                            mBinding.tvResults.setBackgroundResource(R.drawable.shape_club_gray_littele);
//                            mBinding.tvProgram.setEnabled(true);
//                            mBinding.tvProgram.setBackgroundResource(R.drawable.shape_club_blue);
//                            mBinding.tvSignUpStatus.setBackgroundResource(R.drawable.shape_club_yellow);
//                            mBinding.tvSignUpStatus.setText("进行中");
//                            mBinding.tvPlayDetail.setText("比赛详情");
                            mBinding.tvTextStatusOrder.setText("进行中");
                            mBinding.tvSignUpDetailText.setText("赛事详情");


                            break;
                        case 3://已结束
//                            mBinding.llRegistrationOf.setVisibility(View.GONE);
//                            mBinding.llProceedAndEnd.setVisibility(View.VISIBLE);
//                            mBinding.llWantSignUp.setBackgroundResource(R.drawable.shape_club_gray_littele);
//                            mBinding.llWantSignUp.setEnabled(false);
//                            mBinding.tvPlayDetail.setEnabled(true);
//                            mBinding.tvPlayDetail.setBackgroundResource(R.drawable.shape_club_yellow);
//                            mBinding.tvResults.setEnabled(true);
//                            mBinding.tvResults.setBackgroundResource(R.drawable.shape_club_yellow);
//                            mBinding.tvProgram.setEnabled(true);
//                            mBinding.tvProgram.setBackgroundResource(R.drawable.shape_club_blue);
//                            mBinding.tvSignUpStatus.setBackgroundResource(R.drawable.shape_club_gray_littele);
//                            mBinding.tvSignUpStatus.setText("已结束");
//                            mBinding.tvPlayDetail.setText("赛事回顾");

                            mBinding.tvTextStatusOrder.setText("已结束");

                            mBinding.tvSignUpDetailText.setText("赛事回顾");


                            break;
                    }
                    path = gameMatchBean.getMatchRules();
                    if (path.startsWith("http")) {
                        setWebView();
                    } else {
                        loadText();
                    }
                } else {
                    toast("" + listResponseBean.getMessage());
                }
            }
        });
        mBinding.scrollview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (mBinding.scrollview.getScrollY() == 0) {
                    //顶部
                    mBinding.ivLogo.setVisibility(View.GONE);
                } else {
                    mBinding.ivLogo.setVisibility(View.VISIBLE);
                }

                View contentView = mBinding.scrollview.getChildAt(0);
                if (contentView != null && contentView.getMeasuredHeight() == (mBinding.scrollview.getScrollY() + mBinding.scrollview.getHeight())) {
                    //底部
                }

            }
        });

        mViewModel.applyMatchLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                String errorCode = responseBean.getErrorCode();
                if (errorCode.equals("100000-100012")) {
                    //是否完善个人信息
                    showNoPersonMessage();
                } else if (errorCode.equals("100000-100011")) {
                    //是否实名认证
                    showNoReal();
                } else if (errorCode.equals("100000-100026")) {
                    //当天有没有别的比赛
                    showTipDialog();
                } else {
                    //跳转到赛事报名界面
                    Intent intent = new Intent(EventSignUpActivity.this, EventRegisterActivity.class);
                    intent.putExtra("data", gameMatchBean);
                    startActivity(intent);
                }
            }
        });
        mViewModel.getOrderBookLiveData.observe(this, new Observer<ResponseBean<OrderBookBean>>() {
            @Override
            public void onChanged(ResponseBean<OrderBookBean> responseBean) {
                dismissLoading();
                if (responseBean.getErrorCode().equals("200")) {
                    OrderBookBean data = responseBean.getData();
                    String bookUrl = data.getBookUrl();
                    if(!TextUtils.isEmpty(bookUrl))
                    {
                        if (data != null) {
                            RxPermissions rxPermissions = new RxPermissions(EventSignUpActivity.this);

                            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                                    .subscribe(new RxBusSubscriber<Boolean>() {
                                        @Override
                                        protected void onEvent(Boolean aBoolean) {
                                            if (aBoolean) {
                                                String bookUrl = data.getBookUrl();
//                        goActivity(FileDisplayActivity.class);
                                                //获取权限
                                                Intent intent = new Intent(EventSignUpActivity.this, FileDisplayActivity.class);
                                                intent.putExtra(FileDisplayActivity.FILE_URL, "" + bookUrl);
                                                intent.putExtra(FileDisplayActivity.TOTAL_URL, "" + bookUrl);
                                                startActivity(intent);
                                            } else {
                                                //只要有一个权限被拒绝，就会执行
                                                toast("未授权权限，部分功能不能使用");
                                                showMissingPermissionDialog();
                                            }
                                        }
                                    });

                        } else {
                            toast("" + responseBean.getMessage());
                        }
                    }else
                    {
                        toast("秩序册未上传，敬请期待");
                    }

                } else {
                    toast("秩序册未上传，敬请期待");
                }
            }
        });

    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        try {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("当前应用缺少必要权限。\\n\\n请点击\\\"设置\\\"-\\\"权限\\\"-打开所需权限");

            // 拒绝, 退出应用
            builder.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                finish();
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    });

            builder.setPositiveButton("设置",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                startAppSettings();
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    });

            builder.setCancelable(false);

            builder.show();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        try {
            Intent intent = new Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void showNoReal() {
        new MessageDialogBuilder(EventSignUpActivity.this)
                .setMessage("")
                .setTvTitle("本次比赛主办方要求运动员进行实名" +
                        "认证，您还没有进行实名认证，请先" +
                        "去实名认证")
                .setTvCancel("取消")
                .setTvSure("去认证")
                .setBtnCancleHint(true)
                .setSureListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goActivity(RealNameImageActivity.class);
                    }
                })
                .show();
    }

    private void showNoPersonMessage() {
        new MessageDialogBuilder(EventSignUpActivity.this)
                .setMessage("")
                .setTvTitle("报名比赛需要完善个人信息，您还没有" +
                        "完善您的个人信息，请先完善个人信息\n" +
                        "                            ")
                .setTvCancel("取消")
                .setTvSure("确定")
                .setBtnCancleHint(false)
                .setSureListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goActivity(PersonalInformationActivity.class);
                    }
                })
                .show();
    }

    private void showTipDialog() {
        new MessageDialogBuilder(EventSignUpActivity.this)
                .setMessage("")
                .setTvTitle("您在本次比赛日期当天已经报名另一" +
                        "场比赛，是否继续报名本次比赛？")
                .setTvCancel("取消")
                .setTvSure("确定")
                .setBtnCancleHint(false)
                .setSureListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(EventSignUpActivity.this, EventRegisterActivity.class);
                        intent.putExtra("data", gameMatchBean);
                        startActivity(intent);
                    }
                })
                .show();
    }

    private void setWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBinding.wvMv.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mBinding.wvMv.setWebViewClient(new WebViewClient());
        mBinding.wvMv.getSettings().setJavaScriptEnabled(true);
        mBinding.wvMv.getSettings().setAllowFileAccess(false);
        mBinding.wvMv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //信任任何链接
        mBinding.wvMv.setWebViewClient(new WebViewClient() {
            //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mBinding.wvMv.loadUrl(path);
    }

    /**
     * 加载富文本
     */
    private void loadText() {
        mBinding.wvMv.loadData(getNewContent(path), "text/html; charset=UTF-8", null);
        //        }

        //设置编码
        mBinding.wvMv.getSettings().setDefaultTextEncodingName("utf-8");
        //支持js
        mBinding.wvMv.getSettings().setJavaScriptEnabled(true);

        mBinding.wvMv.getSettings().setAllowFileAccess(true);// 设置允许访问文件数据
        mBinding.wvMv.getSettings().setSupportZoom(false);
        mBinding.wvMv.getSettings().setBuiltInZoomControls(false);
        mBinding.wvMv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mBinding.wvMv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mBinding.wvMv.getSettings().setDomStorageEnabled(true);
        mBinding.wvMv.getSettings().setDatabaseEnabled(true);
        //映射.可以调用js里面的方法
        mBinding.wvMv.addJavascriptInterface(new JavaScriptInterface(), "jsi");
        mBinding.wvMv.setWebChromeClient(new WebChromeClient());// 设置浏览器可弹窗

        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mBinding.wvMv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    final Handler myHandler = new Handler();

    private final class JavaScriptInterface {
        @JavascriptInterface
        public void close() {
            myHandler.post(new Runnable() {
                @Override
                public void run() {// 执行UI线程
                    //                    Intent intent = new Intent();
                    //                    WebViewActivity.this.setResult(1, intent);
                    EventSignUpActivity.this.finish();
                }
            });
        }
    }

    public static String getNewContent(String htmltext) {

        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }

        return doc.toString();
    }

    CustomDialog customDialogConfirm;

    private void callPhoneDialog() {
        Map<String, Object> gamegrade = new HashMap<String, Object>();
        gamegrade.put("eventSignUpPage_callPhone", "拨打电话");//自定义参数：音乐类型，值：流行
        MobclickAgent.onEventObject(EventSignUpActivity.this, "拨打电话", gamegrade);
//        TCAgent.onEvent(EventSignUpActivity.this, "eventSignUpPage_callPhone");

        customDialogConfirm = new CustomDialog(EventSignUpActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_call_phone);
        customDialogConfirm.show();
        View customView = customDialogConfirm.getCustomView();
        showAskConfirmView(customView, customDialogConfirm);
    }

    public static final int CALL_PHONE = 1002;//Call permission code

    private void showAskConfirmView(View customView, CustomDialog customDialog) {
        TextView tvCancel = customView.findViewById(R.id.tv_cancel);
        LinearLayout llCallPhone = customView.findViewById(R.id.ll_call_phone);
        TextView tvCallNumber = customView.findViewById(R.id.tv_exit_club);
        tvCallNumber.setText("呼叫 " + phoneNumber);
        llCallPhone.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                //                toast("拨打电话");
                if (ContextCompat.checkSelfPermission(EventSignUpActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE);
                } else {
                    callPhone("" + phoneNumber);
                    if (customDialog != null) {
                        customDialog.dismiss();
                    }
                }


            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialog != null) {
                    customDialog.dismiss();
                }

            }
        });

    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_finish:

                finish();
                break;
            case R.id.iv_logo:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.scrollview.smoothScrollTo(0, 0);

                break;
            case R.id.ll_call_phone://呼叫电话  弹窗 和实现效果
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (TextUtils.isEmpty(phoneNumber)) {
                    toast("呼叫电话不能为空");
                    return;
                } else {
                    callPhoneDialog();
                }

                break;
            case R.id.ll_address:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Map<String, Object> gamegrade = new HashMap<String, Object>();
                gamegrade.put("eventSignUpPage_location", "地图定位");//自定义参数：音乐类型，值：流行
                MobclickAgent.onEventObject(EventSignUpActivity.this, "地图定位", gamegrade);
//                TCAgent.onEvent(EventSignUpActivity.this, "eventSignUpPage_location");
                showMapRoute();
                break;


            case R.id.ll_zhixuce://秩序册
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
//                toast("暂未开放");
                showLoading();
                mViewModel.getOrderBook("" + strSignUpId);
                //                toast("秩序册");
                break;
//            case R.id.tv_results://成绩册
//                if (AntiShakeUtils.isInvalidClick(v))
//                    return;
//                //                toast("成绩册");
//                toast("暂未开放");
//                break;
            case R.id.ll_cansai_people:

                RegistrationDetailActivity.goActivity(EventSignUpActivity.this, strSignUpId);


                break;
            case R.id.ll_to_live://去看直播
//                YourOrderListActivity.isBackground()
                if(status==1)
                {
//    <2>、当赛事状态为“报名中”时，点击弹窗提示“赛事还未编排，暂时不能下单看直播”
                    toast("赛事还未编排，暂时不能下单看直播");
                }else {
                    YourOrderListActivity.Companion.startActivity(this, "" + gameMatchBean.getId());
                }

                break;

            case R.id.ll_sign_up_detail://报名详情
                switch (status) {
                    //报名中  进行中  已结束
                    case 1:
//                        我要报名
                        if (AntiShakeUtils.isInvalidClick(v))
                            return;
                        Map<String, Object> gametoGame = new HashMap<String, Object>();
                        gametoGame.put("eventSignUpPage_toGame", "我要报名");//自定义参数：音乐类型，值：流行
                        MobclickAgent.onEventObject(EventSignUpActivity.this, "我要报名", gametoGame);
//                TCAgent.onEvent(EventSignUpActivity.this, "eventSignUpPage_toGame");
                        if (TextUtils.isEmpty(gameMatchBean.getId())) {
                            toast("赛事有误");
                        } else {
                            showLoading();
                            mViewModel.applyMatch(gameMatchBean.getId());
                        }
                        break;
                    case 2:
                    case 3:
                        //比赛详情
                        if (AntiShakeUtils.isInvalidClick(v))
                            return;
                        try {
                            if (TextUtils.isEmpty(gameMatchBean.getId())) {
                                toast("赛事有误");
                            } else {
                                Map<String, Object> gamedetail = new HashMap<String, Object>();
                                gamedetail.put(" eventSignUpPage_matchDetail", "比赛详情");//自定义参数：音乐类型，值：流行
                                MobclickAgent.onEventObject(EventSignUpActivity.this, "比赛详情", gamedetail);
//                        TCAgent.onEvent(EventSignUpActivity.this, "eventSignUpPage_matchDetail");
                                Intent intent = new Intent(EventSignUpActivity.this, GameDetailActivity.class);
                                intent.putExtra(GameDetailActivity.MATCH_ID, "" + gameMatchBean.getId());
                                intent.putExtra(GameDetailActivity.MATCH_TITLE, "" + gameMatchBean.getMatchTitle());
                                startActivity(intent);
                            }
                        } catch (Exception e) {

                        }
                        break;

                    //比赛详情
//                if (AntiShakeUtils.isInvalidClick(v))
//                    return;
//                try {
//                    if (TextUtils.isEmpty(gameMatchBean.getId())) {
//                        toast("赛事有误");
//                    } else {
//                        Map<String, Object> gamedetail = new HashMap<String, Object>();
//                        gamedetail.put(" eventSignUpPage_matchDetail", "比赛详情");//自定义参数：音乐类型，值：流行
//                        MobclickAgent.onEventObject(EventSignUpActivity.this, "比赛详情", gamedetail);
////                        TCAgent.onEvent(EventSignUpActivity.this, "eventSignUpPage_matchDetail");
//                        Intent intent = new Intent(EventSignUpActivity.this, GameDetailActivity.class);
//                        intent.putExtra(GameDetailActivity.MATCH_ID, "" + gameMatchBean.getId());
//                        intent.putExtra(GameDetailActivity.MATCH_TITLE, "" + gameMatchBean.getMatchTitle());
//                        startActivity(intent);
//                    }
//                } catch (Exception e) {
//
//                }


                }


                break;
        }
    }

    /*点击确定和修改比分弹窗*/
    private void showMapRoute() {
        CustomDialog customDialogConfirm = new CustomDialog(EventSignUpActivity.this);
        customDialogConfirm.setCustomView(R.layout.pop_map_route);
        customDialogConfirm.show();
        View customView = customDialogConfirm.getCustomView();
        TextView tvMapGaode = customView.findViewById(R.id.tv_map_gaode);
        TextView tvMapTent = customView.findViewById(R.id.tv_map_tent);
        TextView tvMapBaidu = customView.findViewById(R.id.tv_map_baidu);

        tvMapGaode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialogConfirm != null) {
                    if (MapUtil.isGdMapInstalled()) {
                        MapUtil.openGaoDeNavi(EventSignUpActivity.this, 0, 0, "", laty, latx, address);
                    } else {
                        Toast.makeText(EventSignUpActivity.this, "尚未安装高德地图", Toast.LENGTH_SHORT).show();
                    }
                    customDialogConfirm.dismiss();
                }
            }
        });
        tvMapTent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialogConfirm != null) {
                    if (MapUtil.isTencentMapInstalled()) {
                        MapUtil.openTencentMap(EventSignUpActivity.this, 0, 0, "", laty, latx, address);
                    } else {
                        Toast.makeText(EventSignUpActivity.this, "尚未安装腾讯地图", Toast.LENGTH_SHORT).show();
                    }
                    customDialogConfirm.dismiss();
                }
            }
        });

        tvMapBaidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialogConfirm != null) {
                    if (MapUtil.isBaiduMapInstalled()) {
                        MapUtil.openBaiDuNavi(EventSignUpActivity.this, 0, 0, "", laty, latx, address);
                    } else {
                        Toast.makeText(EventSignUpActivity.this, "尚未安装百度地图", Toast.LENGTH_SHORT).show();
                    }
                    customDialogConfirm.dismiss();
                }

            }
        });

        TextView tvCancel = customView.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialogConfirm != null) {
                    customDialogConfirm.dismiss();
                }

            }
        });
    }

    private static final int REQUEST_CODE = 0; // Request Code

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PHONE) {
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    if (customDialogConfirm != null) {
                        customDialogConfirm.dismiss();
                    }
                    showMissingDialog(myDialog, EventSignUpActivity.this);
                } else {

                    callPhone("" + phoneNumber);
                    if (customDialogConfirm != null) {
                        customDialogConfirm.dismiss();
                    }
                }
            }
        }
    }

    public static void showMissingDialog(AlertDialog myDialog, Context context) {
        myDialog.setGone().setTitle("帮助")
                .setMsg("当前应用缺少拨打电话权限。请点击 设置-权限管理 -打开所需权限。最后点击两次后退按钮，即可返回。")
                .setNegativeButton("取消",
                        null).setPositiveButton("去设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationInfo((Activity) context);
            }
        }).show();
    }
}