package block.com.blockchain.fragment;


import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import block.com.blockchain.R;
import block.com.blockchain.request.HttpConstant;
import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/12.
 */

public class HelpFragment extends BaseFragment {

    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.parent_layout)
    LinearLayout parentLayout;

    public static final String URL = "file:///android_asset/userNotice.html";

    @Override
    public int getResView() {
        return R.layout.fragment_help;
    }

    @Override
    public void onRefresh() {
        parentLayout.setPadding(0, HttpConstant.PhoneInfo.STATUS_HEIGHT, 0, 0);
        // 兼容http和https混淆访问
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 设置js可用
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.getSettings().setGeolocationEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.loadUrl(URL);
    }

    @Override
    public void onNetCanUse() {

    }

    final static class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            WebView.HitTestResult result=view.getHitTestResult();
//            if (!TextUtils.isEmpty(url) && result == null) {
//            }else{
//                view.loadUrl(url);
//            }

            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            new AtTheFirstTime().setCookie(ShopWebViewActivity.this, currentUrl);
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(final WebView view, String url) {


        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.i("WebView", "onReceivedError" + failingUrl);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            Log.i("WebView", "onReceivedSslError" + handler.toString() + " _ " + error.toString());
            handler.proceed();
        }
    }
}
