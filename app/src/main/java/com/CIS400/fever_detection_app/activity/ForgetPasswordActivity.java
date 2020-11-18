package com.CIS400.fever_detection_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.data.MyUser;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ForgetPasswordActivity extends BaseActivity {

    private static final String TAG = "ForgetPasswordActivity";
    private Button resetBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Bmob.initialize(this, "2de9dc3c787359faf54d36e92a2bbfb0");
        resetBtn = (Button) findViewById(R.id.reset_Btn);
        backBtn = (Button) findViewById(R.id.back_Btn);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUser user = BmobUser.getCurrentUser(MyUser.class);
                BmobUser.resetPasswordByEmail(user.getEmail(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e == null){
                            Toast.makeText(ForgetPasswordActivity.this, "Request succeeded, please go to " + user.getEmail() + " to reset your password", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ForgetPasswordActivity.this, "Request failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}