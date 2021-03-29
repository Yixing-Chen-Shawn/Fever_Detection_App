package com.CIS400.fever_detection_app.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.constraintlayout.helper.widget.Layer;
import androidx.core.content.ContextCompat;

import com.CIS400.fever_detection_app.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class WorkoutActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        String url = "https://wger.de/en/exercise/overview/";
        WebView workoutInfo = (WebView) findViewById(R.id.displayAllExercises);
        ImageView backButton = (ImageView) findViewById(R.id.backWorkout);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        /*workoutInfo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent = new Intent(WorkoutActivity.this, WorkoutInfoActivity.class);
                Bundle b = new Bundle();
                b.putString("url", url);
                intent.putExtras(b);
                startActivity(intent);
                return true;
            }*/
           /* @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                workoutInfo.setVisibility(View.INVISIBLE);
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished (WebView web, String url) {
                web.loadUrl("javascript:var elements = document.getElementsByTagName('nav');" +
                        "while (elements[0]) elements[0].parentNode.removeChild(elements[0]);"+
                        "document.getElementById('main-sidebar').remove();"+
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
        workoutInfo.loadUrl(url);*/
    }

}
