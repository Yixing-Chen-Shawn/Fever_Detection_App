package com.CIS400.fever_detection_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.activity.BaseActivity;
import com.CIS400.fever_detection_app.data.MyUser;
import com.CIS400.fever_detection_app.activity.ForgetPasswordActivity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UpdateListener;

public class LoginActivity extends BaseActivity {
    private EditText emailText, pwdText;
    private Button btnLogin, btnRegister, btnForget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "2de9dc3c787359faf54d36e92a2bbfb0");
        emailText = (EditText) findViewById(R.id.input_Email);
        pwdText = (EditText) findViewById(R.id.input_password);
        btnLogin = (Button)  findViewById(R.id.button1);
        btnRegister = (Button) findViewById(R.id.button2);
        btnForget = (Button) findViewById(R.id.btn_forgetpwd);
        MyUser user = BmobUser.getCurrentUser(MyUser.class);

        /*if(user != null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }*/
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    MyUser.loginByAccount(emailText.getText().toString().trim(), pwdText.getText().toString().trim(), new LogInListener<MyUser>() {
                        @Override
                        public void done(MyUser user, BmobException e) {
                            if(user!=null){
                                if(!user.getEmailVerified()) {
                                    MyUser.requestEmailVerify(emailText.getText().toString().trim(), new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                Toast.makeText(LoginActivity.this, "Your email has not verified yet, We just sent you a verification in your Email.", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Your email has not verified yet, and verification failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }else {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(LoginActivity.this, "Welcome back! " + user.getUsername(), Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(LoginActivity.this, "Login failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}