package heyong.intellectPinPang.ui.shopmall.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK
import android.view.View
import android.webkit.*
import heyong.intellectPinPang.ui.BaseActivity
import heyong.handong.framework.base.BaseViewModel
import heyong.intellectPinPang.R
import heyong.intellectPinPang.databinding.ActivityShopMallBinding
import org.jsoup.Jsoup

class ShopMallKotlinActivity:
    BaseActivity<ActivityShopMallBinding, BaseViewModel>(),
    View.OnClickListener {
    val URLS = "url"
    var url:String=""

    companion object{
        fun start(context:Context,urls:String)
        {
            val intent=Intent(context,ShopMallKotlinActivity::class.java)
            intent.putExtra("url",urls)
            context.startActivity(intent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener=this
        url=  intent.getStringExtra(URLS)
        if(url.startsWith("http"))
        {
            mBinding.wvMv.loadUrl(url)
        }else {
            mBinding.wvMv.loadData(getNewContent(url), "text/html; charset=UTF-8", null)
        }
        //设置编码
        mBinding.wvMv.settings.defaultTextEncodingName = "utf-8"
        //支持js
        mBinding.wvMv.settings.javaScriptEnabled = true

        mBinding.wvMv.settings.allowFileAccess = true// 设置允许访问文件数据
        mBinding.wvMv.settings.setSupportZoom(false)
        mBinding.wvMv.settings.builtInZoomControls = false
        mBinding.wvMv.settings.javaScriptCanOpenWindowsAutomatically = true
        mBinding.wvMv.settings.cacheMode = WebSettings.LOAD_DEFAULT
        mBinding.wvMv.settings.domStorageEnabled = true
        mBinding.wvMv.settings.databaseEnabled = true
        mBinding.wvMv.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                //H5调起微信app支付方法二（可使用）

                if (url.startsWith("weixin://wap/pay?") || url.startsWith("alipays://")) {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                    return true
                }
                // 默认行为
                return false
            }

            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                handler.proceed()
            }
        }
        //映射.可以调用js里面的方法
        mBinding.wvMv.webChromeClient = WebChromeClient()// 设置浏览器可弹窗

    }
    private fun getNewContent(htmltext: String): String {
        val doc = Jsoup.parse(htmltext)
        val elements = doc.getElementsByTag("img")
        for (element in elements) {
            element.attr("width", "100%").attr("height", "auto")
        }

        Log.d("VACK", doc.toString())
        return doc.toString()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KEYCODE_BACK && mBinding.wvMv.canGoBack()) {
            mBinding.wvMv.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun getLayoutRes(): Int {
        return  R.layout.activity_shop_mall
    }


    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.iv_finish->finish()
        }
    }
}