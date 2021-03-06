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
    private EditText dateInput, descriptionInput;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_symptoms);
        Bmob.initialize(this, "2de9dc3c787359faf54d36e92a2bbfb0");

        saveButton = (Button) findViewById(R.id.saveButton);
        backButton = (Button) findViewById(R.id.BackButton);
        back = (ImageView) findViewById(R.id.back_manualSymptom);

        descriptionInput = (EditText) findViewById(R.id.descriptionInput);
        dateInput = (EditText)  findViewById(R.id.dateInput);

        /*
        RadioButton one = (RadioButton) findViewById(R.id.One);
        RadioButton two = (RadioButton) findViewById(R.id.Two);
        RadioButton three = (RadioButton) findViewById(R.id.Three);
        RadioButton four = (RadioButton) findViewById(R.id.Four);
        RadioButton five = (RadioButton) findViewById(R.id.Five);
         */

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        //int selectedId = 0;

        user = BmobUser.getCurrentUser(MyUser.class);

        //addListenerOnButton();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) findViewById(checkedId);
            }
        });



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get radio button info
                //radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                String dateInputs = dateInput.getText().toString().trim();
                String descriptionInputs = descriptionInput.getText().toString().trim();
                String ratings = radioButton.getText().toString();
                datel = user.getSymptomDates();
                descriptonl = user.getSymptoms();
                ratingl = user.getSymptomRatings();

                if (selectedId == -1) {
                    Toast.makeText(ManualSymptomActivity.this, "Error: Fill in all items", Toast.LENGTH_LONG).show();
                    return;
                }


                //Check if entries are empty
                if (dateInput.getText().toString().trim().matches("") || descriptionInput.getText().toString().trim().matches("")) {
                    Toast.makeText(ManualSymptomActivity.this, "Error: Fill in all items", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!dateInput.getText().toString().trim().matches("^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d$")) {
                    Toast.makeText(ManualSymptomActivity.this, "Date Format Error: Use format MM/DD/YEAR", Toast.LENGTH_LONG).show();
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

                /*Check if radio input is 1 or 2. If so, make notificationViewed from MyUser = false so it displays alertIcon.
                if (radioButton.getText().toString() == "2 (Not Good)" || radioButton.getText().toString() == "1 (Feeling Terrible)") {
                    user.setNotificationViewed("False");
                }*/

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
