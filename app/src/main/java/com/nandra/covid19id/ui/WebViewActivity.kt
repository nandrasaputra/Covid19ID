package com.nandra.covid19id.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nandra.covid19id.R
import com.nandra.covid19id.utils.Constant
import kotlinx.android.synthetic.main.activity_web_view.*
import java.net.URLEncoder

class WebViewActivity : AppCompatActivity() {

    private var extraWebsite = "https://www.google.com/"
    private var extraWebTitle = "COVID-19 ID"
    private var currentUrl = "www.google.com"

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
        onBackNavigationPressed()
    }

    private fun onBackNavigationPressed() {
        if (activity_web_view_container.canGoBack()) {
            activity_web_view_container.goBack()
        } else {
            finish()
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
            onBackNavigationPressed()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
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

            currentUrl = url ?: "www.google.com"

            activity_web_view_progress_bar.visibility = View.GONE

            activity_web_view_toolbar_sub_title.apply {
                visibility = View.VISIBLE
                text = url
            }

            super.onPageFinished(view, url)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

            currentUrl = url ?: "www.google.com"

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_web_view_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.activity_web_view_toolbar_share_menu_item -> {
                val contentText = "$extraWebTitle :\n$currentUrl\n\nDownload Aplikasi COVID-19 ID"
                val sharingIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "COVID-19 ID")
                    putExtra(Intent.EXTRA_TEXT, contentText)
                }
                startActivity(Intent.createChooser(sharingIntent, "Share Website Ini"))
                true
            }
            R.id.activity_web_view_toolbar_translate_menu_item -> {
                translatePage(currentUrl)
                true
            }
            R.id.activity_web_view_toolbar_browser_menu_item -> {
                val openBrowserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(currentUrl))
                startActivity(openBrowserIntent)
                true
            }
            R.id.activity_web_view_toolbar_close_menu_item -> {
                finish()
                true
            }
            else -> {false}
        }
    }

    private fun translatePage(url: String) {
        val baseIdToEnTranslateUrl = "https://translate.google.com/translate?hl=en&tab=wT&sl=en&tl=id&u="
        if (currentUrl.contains("translate.googleusercontent.com", true)) {
            Toast.makeText(this, "Website Telah Di Translate", Toast.LENGTH_SHORT).show()
            return
        } else if (currentUrl == "https://www.google.com/") {
            return
        }
        val encodedUrl = URLEncoder.encode(url, "utf-8")
        val completeUrl = baseIdToEnTranslateUrl + encodedUrl
        activity_web_view_container.loadUrl(completeUrl)
    }
}