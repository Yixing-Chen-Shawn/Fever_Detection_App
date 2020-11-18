package com.CIS400.fever_detection_app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.CIS400.fever_detection_app.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UpdateListener;

public class MeChangePasswordActivity extends BaseActivity {

    private EditText emailText;
    private Button resetButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_change_password);
        Bmob.initialize(this, "2de9dc3c787359faf54d36e92a2bbfb0");


        emailText = (EditText) findViewById(R.id.input_Reset_Email);
        resetButton = (Button) findViewById(R.id.reset_Password_Button);

        resetButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {

                BmobUser.resetPasswordByEmail(emailText.getText().toString().trim(), new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(MeChangePasswordActivity.this, "Reset Password Sent To Email.", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MeChangePasswordActivity.this, MainActivity.class));
                        }else{
                            Toast.makeText(MeChangePasswordActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });



            }

        });
    }


}