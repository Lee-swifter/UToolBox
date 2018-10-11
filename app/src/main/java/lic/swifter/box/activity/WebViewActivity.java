package lic.swifter.box.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.baidu.mobstat.StatService;

import lic.swifter.box.R;

public class WebViewActivity extends AppCompatActivity {

    public static final String INTENT_URL = "lic.swifter.box.activity.WebViewActivity.INTENT_URL";
    public static final String INTENT_TITLE = "lic.swifter.box.activity.WebViewActivity.INTENT_TITLE";

    private static final String NET_EASY_URL = "http://www.163.com";

    ImageView closeImage;
    Toolbar toolbar;
    WebView webView;
    ProgressBar progressBar;

    private String originalUrl;
    private String originalTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        closeImage = findViewById(R.id.web_view_close);
        toolbar = findViewById(R.id.web_view_toolbar);
        webView = findViewById(R.id.activity_web_view);
        progressBar = findViewById(R.id.web_view_progress);

        if (savedInstanceState != null) {
            originalUrl = savedInstanceState.getString(INTENT_URL);
            originalTitle = savedInstanceState.getString(INTENT_TITLE);
        } else {
            originalUrl = getIntent().getStringExtra(INTENT_URL);
            originalTitle = getIntent().getStringExtra(INTENT_TITLE);
        }

        if (TextUtils.isEmpty(originalUrl)) {
            originalUrl = NET_EASY_URL;
        }

        toolbar.setTitle(originalTitle);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setupWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                if (view.canGoBack() && !closeImage.isShown())
                    closeImage.setVisibility(View.VISIBLE);
                if (!view.canGoBack() && closeImage.isShown())
                    closeImage.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                toolbar.setTitle(title);
                super.onReceivedTitle(view, title);
            }
        });

        webView.loadUrl(originalUrl);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(INTENT_URL, originalUrl);
        outState.putString(INTENT_TITLE, originalTitle);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        StatService.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        StatService.onPause(this);
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        webView.clearHistory();
        webView.clearCache(true);
        webView.destroy();

        super.onDestroy();
    }
}
