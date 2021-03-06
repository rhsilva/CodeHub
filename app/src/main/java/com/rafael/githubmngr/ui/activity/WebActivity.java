package com.rafael.githubmngr.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rafael.githubmngr.Constant;
import com.rafael.githubmngr.R;
import com.rafael.lib.utils.CheckValueUtil;
import com.rafael.lib.utils.LogUtil;

import butterknife.Bind;

/**
 * Created by Rafael on 2016/10/29.
 */
public class WebActivity extends BaseGithubMngrToolBarActivity {

    private static final String TAG = "WebActivity";

    @Bind(R.id.webview)
    WebView mWebview;

    public static Intent newIntent(Activity activity, String title, String url) {
        CheckValueUtil.check(url);
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra(Constant.URL, url);
        intent.putExtra(Constant.TITLE, title);
        return intent;
    }


    @Override
    protected int getAttachLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void handleIntent(Intent intent) {
        String title = intent.getStringExtra(Constant.TITLE);
        setTitle(title);

        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().getUserAgentString();
        mWebview.getSettings().setSupportZoom(true);
        mWebview.setWebViewClient(new HubWebClient());
        mWebview.setWebChromeClient(new WebChromeClient());

        //自适应屏幕
        mWebview.getSettings().setSupportMultipleWindows(true);
        mWebview.getSettings().setUseWideViewPort(true);

        mWebview.getSettings().setLoadWithOverviewMode(true);
        String url = getIntent().getStringExtra(Constant.URL);
        mWebview.loadUrl(url);

        LogUtil.d(TAG, url);
    }

    @Override
    public void onBackPressed() {
        if (mWebview.canGoBack()) {
            mWebview.goBack();
            return;
        }
        super.onBackPressed();
    }

    private class HubWebClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showProgressBarLoading(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            showProgressBarLoading(false);
        }
    }

}
