package com.example.mad_assignment_2_21012011118

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class NewsFullActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_full)

        val url = intent.getStringExtra("url")
        webView = findViewById(R.id.web_view)
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url.toString())
    }

    override fun onBackPressed() {
        if (webView.canGoBack())
            webView.goBack()
        else
            finish()
        //super.onBackPressed()
    }
}