package com.CIS400.fever_detection_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.data.MyUser;


import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";
    private EditText nameText, emailText, pwdText, rpwdText, ageText, phoneText;
    private String gender;
    private Button createBtn, link_Login;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String pwd1, pwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bmob.initialize(this, "2de9dc3c787359faf54d36e92a2bbfb0");
        nameText = (EditText) findViewById(R.id.input_name);
        emailText = (EditText) findViewById(R.id.input_Email);
        pwdText = (EditText) findViewById(R.id.input_password);
        rpwdText = (EditText) findViewById(R.id.input_reEnterPassword);
        ageText = (EditText) findViewById(R.id.input_age);
        phoneText = (EditText) findViewById(R.id.input_mobile);
        radioGroup = (RadioGroup) findViewById(R.id.radioSex);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        gender = radioButton.getText().toString();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRadioButton = (RadioButton) findViewById(i);
                gender = checkedRadioButton.getText().toString();
                Toast.makeText(getApplicationContext(), "Changed to " + gender, Toast.LENGTH_SHORT).show();
            }
        });
        createBtn = (Button) findViewById(R.id.btn_signup);
        link_Login = (Button) findViewById(R.id.link_login);
        link_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwd1 = pwdText.getText().toString().trim();
                pwd2 = rpwdText.getText().toString().trim();

                if (TextUtils.isEmpty(nameText.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(emailText.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Net ID cannot be empty", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(ageText.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Age cannot be empty", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(phoneText.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Phone number cannot be empty", Toast.LENGTH_SHORT).show();
                }

                if (gender.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please select your gender", Toast.LENGTH_SHORT).show();
                }
                if (pwd1.equals("") || pwd2.equals("")) {
                    Toast.makeText(getApplicationContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (pwd1.equals(pwd2)) {
                    doRegistration();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else if (pwd1.compareTo(pwd2) != 0) {
                    Toast.makeText(getApplication(), "Two password inputs are not same, Please retry", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void doRegistration() {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        MyUser user = new MyUser();
        user.setUsername(nameText.getText().toString().trim());
        user.setEmail(emailText.getText().toString().trim());
        user.setPassword(pwdText.getText().toString().trim());
        user.setAge(Integer.parseInt(ageText.getText().toString().trim()));
        user.setPhoneNum(phoneText.getText().toString().trim());
        user.setGender(gender);
        user.signUp(new SaveListener<MyUser>(){
            @Override
            public void done(MyUser s, BmobException e) {
                if (e == null) {
                        Toast.makeText(getApplicationContext(), "Hello! " + s.getUsername(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Registration failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}