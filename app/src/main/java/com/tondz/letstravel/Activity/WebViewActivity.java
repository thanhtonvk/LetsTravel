package com.tondz.letstravel.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.annotation.Nullable;
import com.tondz.letstravel.Activity.User.Fragment.MenuTabFragment;
import com.tondz.letstravel.R;

public class WebViewActivity extends Activity {
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.web_view);
        super.onCreate(savedInstanceState);
        initView();
        webView.loadUrl(MenuTabFragment.link);
        WebSettings webSettings= webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
    private void initView(){
        webView = findViewById(R.id.webview);
    }
}
