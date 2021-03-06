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
 * ??????????????????UI 1
 */
public class EventSignUpActivity extends BaseActivity<ActivityEventSignUpBinding, CompetitionViewModel> implements View.OnClickListener {


    public static final String SIGNUPID = "sign_up_id";
    private String strSignUpId = "";
    //????????????  ????????? ?????????  ?????????
    private int status = 0;
    double laty;
    double latx;
    private String address = "";
    GameMatchBean gameMatchBean;
    private String path = "";
    private String phoneNumber = "";
    public static final String TAG = EventSignUpActivity.class.getSimpleName();
    private AlertDialog myDialog;//??????


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
                    //0????????????   1?????????
                    mBinding.tvCompetotonTime.setText("???????????????" + gameMatchBean.getBeginTime() + "??????" + gameMatchBean.getEndTime());
                    status = Integer.parseInt(gameMatchBean.getMatchStatus());
//                    mBinding.tvCurrentPeopleNum.setText("" + gameMatchBean.getJoinCount());
                    mBinding.tvEndTime.setText("???????????????" + gameMatchBean.getRegistrationDeadline());
                    mBinding.tvConnectName.setText("????????????" + gameMatchBean.getLinkPerson());
                    mBinding.tvConnectTell.setText("" + gameMatchBean.getLinkNum());
                    phoneNumber = gameMatchBean.getLinkNum();
                    mBinding.tvAddress.setText("" + gameMatchBean.getHoldCity() + " " + gameMatchBean.getVenue());
                    mBinding.tvZhubanfang.setText("????????????" + gameMatchBean.getSponsor());
                    mBinding.tvChengbanfang.setText("????????????" + gameMatchBean.getOrganizer());
                    String lngLat = gameMatchBean.getLngLat();
                    /*?????????????????????*/
                    latx = Double.parseDouble(lngLat.substring(0, lngLat.indexOf(",")));
                    /*?????????????????????*/
                    laty = Double.parseDouble(lngLat.substring(lngLat.indexOf(",")).replace(",", ""));
                    address = gameMatchBean.getHoldCity() + " " + gameMatchBean.getVenue();
                    Log.e(TAG, "onChanged: " + new Gson().toJson(gameMatchBean));
                    switch (status) {
                        case 1://?????????  ?????????????????? ????????????????????????
                            mBinding.tvSignUpDetailText.setText("????????????");
                            mBinding.tvTextStatusOrder.setText("?????????");
//                            mBinding.llRegistrationOf.setVisibility(View.VISIBLE);
//                            mBinding.llProceedAndEnd.setVisibility(View.GONE);
//                            mBinding.llWantSignUp.setBackgroundResource(R.drawable.shape_club_blue);
//                            mBinding.llWantSignUp.setEnabled(true);
//                            mBinding.tvPlayDetail.setEnabled(false);
//                            mBinding.tvPlayDetail.setText("????????????");
//                            mBinding.tvPlayDetail.setBackgroundResource(R.drawable.shape_club_gray_littele);
//                            mBinding.tvResults.setEnabled(false);
//                            mBinding.tvResults.setBackgroundResource(R.drawable.shape_club_gray_littele);
//                            mBinding.tvProgram.setEnabled(false);
//                            mBinding.tvProgram.setBackgroundResource(R.drawable.shape_club_gray_littele);
//                            mBinding.tvSignUpStatus.setBackgroundResource(R.drawable.shape_club_blue);
//                            mBinding.tvSignUpStatus.setText("?????????");
//                            mBinding.tvSigunUpMoney.setText("??" + gameMatchBean.getReplaceCharge() + "/???");
//                            String replaceCharge = gameMatchBean.getReplaceCharge();
//                            int iReplaceCharge = Integer.parseInt(replaceCharge);
//                            if (iReplaceCharge == 0) {
//                                //???????????????
//                                mBinding.tvSigunUpMoney.setText("??????");
//                            } else {
//                                //????????????
//                                int ownFree = gameMatchBean.getOwnFree();
//                                if (ownFree == 0) {
//                                    mBinding.tvSigunUpMoney.setText("??????????????????");
//                                } else {
//                                    mBinding.tvSigunUpMoney.setText("??" + "" + ownFree + "/???");
//                                }
//                            }
                            break;
                        case 2://?????????
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
//                            mBinding.tvSignUpStatus.setText("?????????");
//                            mBinding.tvPlayDetail.setText("????????????");
                            mBinding.tvTextStatusOrder.setText("?????????");
                            mBinding.tvSignUpDetailText.setText("????????????");


                            break;
                        case 3://?????????
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
//                            mBinding.tvSignUpStatus.setText("?????????");
//                            mBinding.tvPlayDetail.setText("????????????");

                            mBinding.tvTextStatusOrder.setText("?????????");

                            mBinding.tvSignUpDetailText.setText("????????????");


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
                    //??????
                    mBinding.ivLogo.setVisibility(View.GONE);
                } else {
                    mBinding.ivLogo.setVisibility(View.VISIBLE);
                }

                View contentView = mBinding.scrollview.getChildAt(0);
                if (contentView != null && contentView.getMeasuredHeight() == (mBinding.scrollview.getScrollY() + mBinding.scrollview.getHeight())) {
                    //??????
                }

            }
        });

        mViewModel.applyMatchLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                String errorCode = responseBean.getErrorCode();
                if (errorCode.equals("100000-100012")) {
                    //????????????????????????
                    showNoPersonMessage();
                } else if (errorCode.equals("100000-100011")) {
                    //??????????????????
                    showNoReal();
                } else if (errorCode.equals("100000-100026")) {
                    //???????????????????????????
                    showTipDialog();
                } else {
                    //???????????????????????????
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
                                                //????????????
                                                Intent intent = new Intent(EventSignUpActivity.this, FileDisplayActivity.class);
                                                intent.putExtra(FileDisplayActivity.FILE_URL, "" + bookUrl);
                                                intent.putExtra(FileDisplayActivity.TOTAL_URL, "" + bookUrl);
                                                startActivity(intent);
                                            } else {
                                                //?????????????????????????????????????????????
                                                toast("??????????????????????????????????????????");
                                                showMissingPermissionDialog();
                                            }
                                        }
                                    });

                        } else {
                            toast("" + responseBean.getMessage());
                        }
                    }else
                    {
                        toast("?????????????????????????????????");
                    }

                } else {
                    toast("?????????????????????????????????");
                }
            }
        });

    }

    /**
     * ??????????????????
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        try {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("??????");
            builder.setMessage("?????????????????????????????????\\n\\n?????????\\\"??????\\\"-\\\"??????\\\"-??????????????????");

            // ??????, ????????????
            builder.setNegativeButton("??????",
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

            builder.setPositiveButton("??????",
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
     * ?????????????????????
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
                .setTvTitle("????????????????????????????????????????????????" +
                        "????????????????????????????????????????????????" +
                        "???????????????")
                .setTvCancel("??????")
                .setTvSure("?????????")
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
                .setTvTitle("???????????????????????????????????????????????????" +
                        "???????????????????????????????????????????????????\n" +
                        "                            ")
                .setTvCancel("??????")
                .setTvSure("??????")
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
                .setTvTitle("????????????????????????????????????????????????" +
                        "?????????????????????????????????????????????")
                .setTvCancel("??????")
                .setTvSure("??????")
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
        //??????????????????
        mBinding.wvMv.setWebViewClient(new WebViewClient() {
            //?????????webView?????????????????????????????????????????????,?????????????????????????????????
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mBinding.wvMv.loadUrl(path);
    }

    /**
     * ???????????????
     */
    private void loadText() {
        mBinding.wvMv.loadData(getNewContent(path), "text/html; charset=UTF-8", null);
        //        }

        //????????????
        mBinding.wvMv.getSettings().setDefaultTextEncodingName("utf-8");
        //??????js
        mBinding.wvMv.getSettings().setJavaScriptEnabled(true);

        mBinding.wvMv.getSettings().setAllowFileAccess(true);// ??????????????????????????????
        mBinding.wvMv.getSettings().setSupportZoom(false);
        mBinding.wvMv.getSettings().setBuiltInZoomControls(false);
        mBinding.wvMv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mBinding.wvMv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mBinding.wvMv.getSettings().setDomStorageEnabled(true);
        mBinding.wvMv.getSettings().setDatabaseEnabled(true);
        //??????.????????????js???????????????
        mBinding.wvMv.addJavascriptInterface(new JavaScriptInterface(), "jsi");
        mBinding.wvMv.setWebChromeClient(new WebChromeClient());// ????????????????????????

        // ??????WebView?????????????????????????????????????????????????????????????????????????????????WebView??????
        mBinding.wvMv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // ????????????true??????????????????WebView????????????false??????????????????????????????????????????
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
                public void run() {// ??????UI??????
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
        gamegrade.put("eventSignUpPage_callPhone", "????????????");//?????????????????????????????????????????????
        MobclickAgent.onEventObject(EventSignUpActivity.this, "????????????", gamegrade);
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
        tvCallNumber.setText("?????? " + phoneNumber);
        llCallPhone.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                //                toast("????????????");
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
     * ????????????????????????????????????
     *
     * @param phoneNum ????????????
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
            case R.id.ll_call_phone://????????????  ?????? ???????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (TextUtils.isEmpty(phoneNumber)) {
                    toast("????????????????????????");
                    return;
                } else {
                    callPhoneDialog();
                }

                break;
            case R.id.ll_address:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                Map<String, Object> gamegrade = new HashMap<String, Object>();
                gamegrade.put("eventSignUpPage_location", "????????????");//?????????????????????????????????????????????
                MobclickAgent.onEventObject(EventSignUpActivity.this, "????????????", gamegrade);
//                TCAgent.onEvent(EventSignUpActivity.this, "eventSignUpPage_location");
                showMapRoute();
                break;


            case R.id.ll_zhixuce://?????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
//                toast("????????????");
                showLoading();
                mViewModel.getOrderBook("" + strSignUpId);
                //                toast("?????????");
                break;
//            case R.id.tv_results://?????????
//                if (AntiShakeUtils.isInvalidClick(v))
//                    return;
//                //                toast("?????????");
//                toast("????????????");
//                break;
            case R.id.ll_cansai_people:

                RegistrationDetailActivity.goActivity(EventSignUpActivity.this, strSignUpId);


                break;
            case R.id.ll_to_live://????????????
//                YourOrderListActivity.isBackground()
                if(status==1)
                {
//    <2>??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                    toast("????????????????????????????????????????????????");
                }else {
                    YourOrderListActivity.Companion.startActivity(this, "" + gameMatchBean.getId());
                }

                break;

            case R.id.ll_sign_up_detail://????????????
                switch (status) {
                    //?????????  ?????????  ?????????
                    case 1:
//                        ????????????
                        if (AntiShakeUtils.isInvalidClick(v))
                            return;
                        Map<String, Object> gametoGame = new HashMap<String, Object>();
                        gametoGame.put("eventSignUpPage_toGame", "????????????");//?????????????????????????????????????????????
                        MobclickAgent.onEventObject(EventSignUpActivity.this, "????????????", gametoGame);
//                TCAgent.onEvent(EventSignUpActivity.this, "eventSignUpPage_toGame");
                        if (TextUtils.isEmpty(gameMatchBean.getId())) {
                            toast("????????????");
                        } else {
                            showLoading();
                            mViewModel.applyMatch(gameMatchBean.getId());
                        }
                        break;
                    case 2:
                    case 3:
                        //????????????
                        if (AntiShakeUtils.isInvalidClick(v))
                            return;
                        try {
                            if (TextUtils.isEmpty(gameMatchBean.getId())) {
                                toast("????????????");
                            } else {
                                Map<String, Object> gamedetail = new HashMap<String, Object>();
                                gamedetail.put(" eventSignUpPage_matchDetail", "????????????");//?????????????????????????????????????????????
                                MobclickAgent.onEventObject(EventSignUpActivity.this, "????????????", gamedetail);
//                        TCAgent.onEvent(EventSignUpActivity.this, "eventSignUpPage_matchDetail");
                                Intent intent = new Intent(EventSignUpActivity.this, GameDetailActivity.class);
                                intent.putExtra(GameDetailActivity.MATCH_ID, "" + gameMatchBean.getId());
                                intent.putExtra(GameDetailActivity.MATCH_TITLE, "" + gameMatchBean.getMatchTitle());
                                startActivity(intent);
                            }
                        } catch (Exception e) {

                        }
                        break;

                    //????????????
//                if (AntiShakeUtils.isInvalidClick(v))
//                    return;
//                try {
//                    if (TextUtils.isEmpty(gameMatchBean.getId())) {
//                        toast("????????????");
//                    } else {
//                        Map<String, Object> gamedetail = new HashMap<String, Object>();
//                        gamedetail.put(" eventSignUpPage_matchDetail", "????????????");//?????????????????????????????????????????????
//                        MobclickAgent.onEventObject(EventSignUpActivity.this, "????????????", gamedetail);
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

    /*?????????????????????????????????*/
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
                        Toast.makeText(EventSignUpActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(EventSignUpActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(EventSignUpActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
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
        myDialog.setGone().setTitle("??????")
                .setMsg("???????????????????????????????????????????????? ??????-???????????? -?????????????????????????????????????????????????????????????????????")
                .setNegativeButton("??????",
                        null).setPositiveButton("?????????", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationInfo((Activity) context);
            }
        }).show();
    }
}