package com.nandra.covid19id.ui

import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.nandra.covid19id.R
import com.nandra.covid19id.utils.Constant
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    var extraWebsite = "https://www.google.com/"
    var extraWebTitle = "COVID-19 ID"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        extraWebsite = intent.getStringExtra(Constant.EXTRA_WEB_SITE) ?: "https://www.google.com/"
        extraWebTitle = intent.getStringExtra(Constant.EXTRA_WEB_TITLE) ?: "COVID-19 ID"

        setupToolbar()
        setupWebView()
    }

    //Workaround of a bug with appcompat 1.1.0 - https://issuetracker.google.com/issues/141132133
    override fun applyOverrideConfiguration(overrideConfiguration: Configuration) {
        if (Build.VERSION.SDK_INT in 21..22) {
            return
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }

    override fun onBackPressed() {
        if (activity_web_view_container.canGoBack()) {
            activity_web_view_container.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(activity_web_view_toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        activity_web_view_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_toolbar)
        activity_web_view_toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupWebView() {
        activity_web_view_container.settings.apply {
            javaScriptEnabled = true
            loadsImagesAutomatically = true
            setSupportMultipleWindows(true)
            allowFileAccessFromFileURLs = true
            allowUniversalAccessFromFileURLs = true
        }
        activity_web_view_container.webViewClient = CustomWebViewClient()
        activity_web_view_container.webChromeClient = CustomWebChromeClient()
        activity_web_view_container.loadUrl(extraWebsite)
    }

    private inner class CustomWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            activity_web_view_progress_bar.visibility = View.GONE
            super.onPageFinished(view, url)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            activity_web_view_toolbar_sub_title.apply {
                visibility = View.VISIBLE
                text = url
            }

            activity_web_view_toolbar_title.text = extraWebTitle

            activity_web_view_progress_bar.visibility = View.VISIBLE
            super.onPageStarted(view, url, favicon)
        }
    }

    private inner class CustomWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            if (activity_web_view_progress_bar.visibility == View.VISIBLE) {
                activity_web_view_progress_bar.progress = newProgress
            }
        }
    }
}