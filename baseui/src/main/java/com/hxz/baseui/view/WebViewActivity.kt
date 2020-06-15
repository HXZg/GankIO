package com.hxz.baseui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * @title com.hxz.baseui.view  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des WebViewActivity
 * @DATE 2020/5/29  9:58 星期五
 */
class WebViewActivity : BaseActivity() {

    companion object {
        fun startWebView(context: Context,url: String) {
            context.startActivity(Intent(context,WebViewActivity::class.java).apply {
                putExtra("web_url",url)
            })
        }
    }

    private val wv by lazy { WebView(this) }
    private val url by lazy { intent?.getStringExtra("web_url") ?: "" }

    override fun bindLayout(): Int {
        setContentView(wv)
        return 0
    }

    override fun beforeBindLayout(saveInstanceState: Bundle?) {

    }

    override fun initData() {
        wv.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }
        wv.loadUrl(url)

    }
}