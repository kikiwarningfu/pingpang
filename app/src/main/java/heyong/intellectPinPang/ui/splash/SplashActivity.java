package heyong.intellectPinPang.ui.splash;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.xq.fasterdialog.dialog.CustomDialog;

import heyong.handong.framework.account.AccountHelper;
import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivitySplashBinding;
import heyong.intellectPinPang.ui.MyHomePageActivity;
import heyong.intellectPinPang.ui.login.activity.LoginActivity;

import static heyong.intellectPinPang.ui.competition.activity.EventSignUpActivity.getNewContent;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, BaseViewModel> implements View.OnClickListener {

    private int widths;

    //由于CountDownTimer有一定的延迟，所以这里设置3400
    private CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mBinding.spJumpBtn.setText("跳过(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            mBinding.spJumpBtn.setText("跳过(" + 0 + "s)");
            countDownTimer.cancel();
            gotoMainActivity();
        }
    };

    @Override
    public int getLayoutRes() {
        return R.layout.activity_splash;
    }

    WebView wvMv;

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

        AccountHelper.setIsNeedUpdate("true");
        widths = getWindowManager().getDefaultDisplay().getWidth();
//        pop_policy.xml  协议布局
        String spIsFirstOpen = AccountHelper.getSpIsFirstOpen();
        if (TextUtils.isEmpty(spIsFirstOpen)) {
            CustomDialog customDialogConfirm = new CustomDialog(SplashActivity.this);
            customDialogConfirm.setCustomView(R.layout.pop_policy);
            customDialogConfirm.setCancelable(false);
            customDialogConfirm.setCanceledOnTouchOutside(false);
            customDialogConfirm.show();

            View customView = customDialogConfirm.getCustomView();
            TextView tvResuse = customView.findViewById(R.id.tv_refuse);
            TextView tvAgree = customView.findViewById(R.id.tv_agree);
            wvMv = customView.findViewById(R.id.wv_mv);
            wvMv.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_BACK && wvMv.canGoBack()) { // 表示按返回键
                            // 时的操作
                            wvMv.goBack(); // 后退
                            // webview.goForward();//前进
                            return true; // 已处理
                        }
                    }
                    return false;
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                wvMv.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
            wvMv.setWebViewClient(new WebViewClient());
            wvMv.getSettings().setJavaScriptEnabled(true);
            wvMv.getSettings().setAllowFileAccess(false);
            wvMv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            //信任任何链接
            wvMv.setWebViewClient(new WebViewClient() {
                //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
//           loadText(wvMv,"aaaaa");
            wvMv.loadUrl("" + "http://backstage.xlttsports.com/appDownLoad/用户协议与隐私保护.html");

            tvResuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            tvAgree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AccountHelper.setSpIsFirstOpen("1111");
                    if (TextUtils.isEmpty(AccountHelper.getSpIsFirstApp())) {
                        gotoGudienceActivity();
                    } else {

                        startClock();
                    }

                    mBinding.spJumpBtn.setOnClickListener(this);
                }
            });
        } else {
            AccountHelper.setSpIsFirstOpen("1111");
            if (TextUtils.isEmpty(AccountHelper.getSpIsFirstApp())) {
                gotoGudienceActivity();
            } else {

                startClock();
            }

            mBinding.spJumpBtn.setOnClickListener(this);
        }


    }

    /**
     * 加载富文本
     */
    private void loadText(WebView webView, String path) {
        webView.loadData(getNewContent(path), "text/html; charset=UTF-8", null);
//        }

        //设置编码
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        //支持js
        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setAllowFileAccess(true);// 设置允许访问文件数据
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        //映射.可以调用js里面的方法
//        webView.addJavascriptInterface(new EventSignUpActivity.JavaScriptInterface(), "jsi");
        webView.setWebChromeClient(new WebChromeClient());// 设置浏览器可弹窗

        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void gotoGudienceActivity() {
//        Intent intent = new Intent(this, GudienceActivity.class);
//        startActivity(intent);
//        finish();

        if (!TextUtils.isEmpty(AccountHelper.getToken())) {
            goActivity(MyHomePageActivity.class);
            finish();
        } else {
            goActivity(LoginActivity.class);
            finish();
        }
    }

    private void gotoMainActivity() {
        if (!TextUtils.isEmpty(AccountHelper.getToken())) {
            goActivity(MyHomePageActivity.class);
            finish();
        } else {
            goActivity(LoginActivity.class);
            finish();
        }
    }

    private void gotoLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }


    private void startClock() {
        mBinding.spJumpBtn.setVisibility(View.GONE);
        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null)
            countDownTimer.cancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sp_jump_btn:

                gotoMainActivity();

                break;
        }
    }
}