package com.CIS400.fever_detection_app.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.CIS400.fever_detection_app.R;

import org.json.JSONArray;

public class WorkoutInfoActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_info);
        String url = "foo";
        Bundle b = getIntent().getExtras();
        if(b != null) {
            url = b.getString("url");
        }
        ImageView backButton = (ImageView) findViewById(R.id.backWorkoutInfo);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        WebView workoutInfo = (WebView) findViewById(R.id.displayWorkout);
        workoutInfo.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                workoutInfo.setVisibility(View.INVISIBLE);
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView web, String url) {
                web.loadUrl("javascript:var elements = document.getElementsByTagName('nav');" +
                        "while (elements[0]) elements[0].parentNode.removeChild(elements[0]);"+
                        "var thing = document.getElementById('main-sidebar').remove();"+
                        "elements = document.getElementsByClassName('col-md-4 py-3');"+
                        "while (elements[0]) elements[0].parentNode.removeChild(elements[0]);"+
                        "elements = document.getElementsByClassName('bg-wger-light border-top');"+
                        "while (elements[0]) elements[0].parentNode.removeChild(elements[0]);");
                workoutInfo.setVisibility(View.VISIBLE);
            }
        });
        workoutInfo.setVisibility(View.INVISIBLE);
        workoutInfo.getSettings().setJavaScriptEnabled(true);
        workoutInfo.setVerticalScrollBarEnabled(true);
        workoutInfo.setHorizontalScrollBarEnabled(true);
        workoutInfo.loadUrl(url);
    }
}