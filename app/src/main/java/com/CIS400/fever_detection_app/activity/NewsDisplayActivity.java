package com.CIS400.fever_detection_app.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Patterns;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.CIS400.fever_detection_app.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class NewsDisplayActivity extends BaseActivity {
    public WebView news0;
    public String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display);
        Button back = (Button) findViewById(R.id.saveButton_health);
        Bundle b = getIntent().getExtras();
        String url = "notUrl";
        ((ImageView) findViewById(R.id.backNews)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        news0 = (WebView) findViewById(R.id.displayNews);
        news0.setWebViewClient(new WebViewClient());
        news0.getSettings().setJavaScriptEnabled(true);
        news0.setVerticalScrollBarEnabled(true);
        news0.setHorizontalScrollBarEnabled(true);
        if(b != null) {
            url = b.getString("url");
        }

        if (Patterns.WEB_URL.matcher(url).matches()){
            news0.loadUrl(url);
        }
        else {
            news0.loadData("<html><title> Bad URL </title> <body> Invalid URL </body></html>", "text/html; charset=utf-8", "utf-8");
        }
        title = news0.getTitle();
    }
    public String getPageTitle(){
        return title;
    }
}
