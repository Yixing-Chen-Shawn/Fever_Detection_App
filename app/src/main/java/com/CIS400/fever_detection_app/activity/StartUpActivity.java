package com.CIS400.fever_detection_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.CIS400.fever_detection_app.R;

public class StartUpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
        startActivity(new Intent(StartUpActivity.this, MainActivity.class));
        finish();

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartUpActivity.this, LoginActivity.class));
                finish();
            }
        }, 2500);*/
    }
}