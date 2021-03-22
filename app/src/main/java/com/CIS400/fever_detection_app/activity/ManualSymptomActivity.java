package com.CIS400.fever_detection_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.data.MyUser;
import com.CIS400.fever_detection_app.data.Symptoms;
import com.CIS400.fever_detection_app.fragments.meFragment;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class ManualSymptomActivity extends BaseActivity{


    private RadioButton radioButton;
    private Button saveButton, backButton;
    private RadioGroup radioGroup;
    private MyUser user;
    private List datel, descriptonl, ratingl;
    private Fragment mFrag1;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private TextView dateInput;
    private EditText descriptionInput;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_symptoms);
        Bmob.initialize(this, "2de9dc3c787359faf54d36e92a2bbfb0");

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        saveButton = (Button) findViewById(R.id.saveButton);
        backButton = (Button) findViewById(R.id.BackButton);
        back = (ImageView) findViewById(R.id.back_manualSymptom);
        descriptionInput = (EditText) findViewById(R.id.descriptionInput);
        dateInput = (TextView) findViewById(R.id.dateInput);
        dateInput.setText("Current Date: " + date);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        user = BmobUser.getCurrentUser(MyUser.class);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) findViewById(checkedId);
            }
        });



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                String dateInputs = dateInput.getText().toString().substring(14).trim();
                String descriptionInputs = descriptionInput.getText().toString().trim();
                String ratings = radioButton.getText().toString();
                datel = user.getSymptomDates();
                descriptonl = user.getSymptoms();
                ratingl = user.getSymptomRatings();

                if (selectedId == -1) {
                    Toast.makeText(ManualSymptomActivity.this, "Error: Fill in all items", Toast.LENGTH_LONG).show();
                    return;
                }

                if(datel.contains(dateInputs)){
                    int idx = datel.indexOf(dateInputs);
                    datel.set(idx, dateInputs);
                    descriptonl.set(idx, descriptionInputs);
                    ratingl.set(idx, ratings);
                    displayToast("Record on " + dateInputs + " has been overwritten.");
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                Toast.makeText(ManualSymptomActivity.this, "Successful!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ManualSymptomActivity.this, SymptomsLogActivity.class));

                            }else{
                                Toast.makeText(ManualSymptomActivity.this, "User Update failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    return;
                }

                //Add inputs
                user.addSymptom(descriptionInput.getText().toString().trim());
                user.addSymptomDate(dateInput.getText().toString().trim());
                user.addSymptomRating(radioButton.getText().toString());

                //Check to see if we reached the limit (10 item logs). If so delete the first symptom input
                List<String> symptoms = user.getSymptoms();
                Integer size = symptoms.size();
                if (size > 10) {
                    user.deleteSymptom();
                }

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                mFrag1 = new meFragment();


                //Update Database
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e == null){
                            Toast.makeText(ManualSymptomActivity.this, "Successful!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ManualSymptomActivity.this, SymptomsLogActivity.class));

                        }else{
                            Toast.makeText(ManualSymptomActivity.this, "User Update failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });



            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManualSymptomActivity.this, SymptomsLogActivity.class));
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

}
