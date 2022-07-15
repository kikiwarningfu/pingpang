package heyong.intellectPinPang.ui.mine.activity;

import androidx.annotation.Nullable;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.databinding.ActivityWebViewBinding;

public class WebViewActivity extends BaseActivity <ActivityWebViewBinding, BaseViewModel>{
    final public static String TITLE = "title";
    private String title = "";
    final public static String URLS = "url";
    private String url;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

        title = getIntent().getStringExtra(TITLE + "");
        url = this.getIntent().getStringExtra(URLS);

        if (url.startsWith("http")) {
            mBinding.wvMv.loadUrl(url);
        } else {

            mBinding.wvMv.loadData(getNewContent(url), "text/html; charset=UTF-8", null);
        }
        mBinding.topBar.setCenterText(title);
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
//        mBinding.wvMv.addJavascriptInterface(new JavaScriptInterface(), "jsi");
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


        mBinding.wvMv.setWebChromeClient(new WebChromeClient());
    }
    private String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }

        Log.d("VACK", doc.toString());
        return doc.toString();
    }

    final Handler myHandler = new Handler();

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mBinding.pbProgress.setVisibility(View.GONE);
            } else {
                if (mBinding.pbProgress.getVisibility() == View.GONE)
                    mBinding.pbProgress.setVisibility(View.VISIBLE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}