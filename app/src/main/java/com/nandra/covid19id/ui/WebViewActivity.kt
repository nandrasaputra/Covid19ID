package com.nandra.covid19id.ui

import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.nandra.covid19id.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

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

    private fun loadUrl(url: String) {

    }

    private fun setupToolbar() {
        setSupportActionBar(activity_web_view_toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        activity_web_view_toolbar.setNavigationIcon(R.drawable.ic_close_toolbar)
        activity_web_view_toolbar.setNavigationOnClickListener {
            finish()
        }
        //activity_web_view_toolbar.text = subjectName
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
        activity_web_view_container.webChromeClient = WebChromeClient()
        activity_web_view_container.loadUrl("https://bnpb-inacovid19.hub.arcgis.com/app/d2595853cbc849ab9e9a790b4345ba38")
    }

    private inner class CustomWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            activity_web_view_progress_bar.visibility = View.GONE
            super.onPageFinished(view, url)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            activity_web_view_progress_bar.visibility = View.VISIBLE
            Log.d("WAKWAW", Uri.parse(url).toString())
            super.onPageStarted(view, url, favicon)
        }
    }
}