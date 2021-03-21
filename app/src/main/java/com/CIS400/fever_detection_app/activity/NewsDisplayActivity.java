package com.CIS400.fever_detection_app.activity;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.CIS400.fever_detection_app.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class NewsDisplayActivity extends BaseActivity {
    public WebView news0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display);
        Bundle b = getIntent().getExtras();
        String url = "foo";
        if(b != null) {
            url = b.getString("url");
        }
        news0 = (WebView) findViewById(R.id.displayNews);
        news0.setWebViewClient(new WebViewClient());
        news0.getSettings().setJavaScriptEnabled(true);
        news0.setVerticalScrollBarEnabled(true);
        news0.setHorizontalScrollBarEnabled(true);
        news0.loadUrl(url);

    }
}
