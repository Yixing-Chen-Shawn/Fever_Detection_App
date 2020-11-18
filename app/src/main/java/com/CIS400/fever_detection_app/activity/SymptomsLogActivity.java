package com.CIS400.fever_detection_app.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.data.MyUser;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class SymptomsLogActivity extends BaseActivity {

    private MyUser user;
    private List<String> symptomDescriptions;
    private List<String> symptomDates;
    private List<String> symptomRatings;
    private TextInputEditText searchEditText;
    private MaterialCardView searchCard;
    private String searchText = "";
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptomslog);
        Bmob.initialize(this, "2de9dc3c787359faf54d36e92a2bbfb0");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchEditText = findViewById(R.id.searchDeletion);
        searchCard = findViewById(R.id.searchCardDelete);
        searchCard.setVisibility(View.GONE);
        user = BmobUser.getCurrentUser(MyUser.class);

        TextView symptomsList = (TextView) findViewById(R.id.descriptionOne);
        @SuppressLint("WrongViewCast") LinearLayout myLinearLayout = (LinearLayout) findViewById(R.id.LinearLayout_Descriptions);
        confirm = (Button) findViewById(R.id.button_Confirm);

        //Used to *temporarily* clear symptoms list. May include a function to let user clear their log using this.
        //user.clearSymptoms();
        //updateSymptoms();

        //First: Grab the list of symptoms as well as its size from database. Reverse the list as well to display correctly.
        symptomDescriptions = user.getSymptoms();
        symptomDates = user.getSymptomDates();
        symptomRatings = user.getSymptomRatings();

        int size = symptomDescriptions.size();
        //Reverse Arrays
        Collections.reverse(symptomDates);
        Collections.reverse(symptomDescriptions);
        Collections.reverse(symptomRatings);

        //Second: For loop to display the data

        TextView temp;
        TextView temp2;


        for (int i = 0; i < size; i++) {
            temp = new TextView(this);
            temp2 = new TextView(this);

            temp.setTextSize(18);
            temp.setTypeface(null, Typeface.BOLD);
            temp.setGravity(Gravity.CENTER);

            temp2.setTextSize(14);
            temp2.setGravity(Gravity.CENTER);

            temp.setText("\n" + symptomDates.get(i) + " - " + symptomRatings.get(i) + "\n");
            temp2.setText(symptomDescriptions.get(i) + "\n" + "\n" + "_______________________________________" + "\n");

            myLinearLayout.addView(temp);
            myLinearLayout.addView(temp2);
        }

        symptomsList.setText("You have symptom logs for the past " + size + " day(s)");
        symptomsList.setTextColor(Color.parseColor("#d44500"));;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_symptoms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                displayToast(getString(R.string.action_add));
                startActivity(new Intent(SymptomsLogActivity.this, ManualSymptomActivity.class));
                break;

            case R.id.action_delete:
                displayToast(getString(R.string.action_delete));
                initDeleteSymptom();
                break;

            case R.id.action_clear:
                displayToast(getString(R.string.action_clear));
                user.clearSymptoms();
                updateSymptoms();
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(getIntent());
                        finish();
                    }
                }, 500);
                break;
            case R.id.action_exit:
                displayToast("Exit from log");
                startActivity(new Intent(SymptomsLogActivity.this, MainActivity.class));
                break;
            default:
                //DO NOTHING
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    public void initDeleteSymptom() {

        if (searchCard.getVisibility() == View.VISIBLE) {
            searchEditText.setText("");
            searchCard.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }else{
            searchCard.setVisibility(View.VISIBLE);
            searchEditText.requestFocus();
            searchEditText.setText("");
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
            }
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchEditText.setVisibility(View.GONE);
                    confirm.setVisibility(View.GONE);
                    searchText = searchEditText.getText().toString().trim();
                    int ret = user.deleteSymptom(searchText);
                    if(ret == -1) displayToast("You do not have record on that day.");
                    updateSymptoms();
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(getIntent());
                            finish();
                        }
                    }, 500);
                }
            });
        }
    }


    //USED TO UPDATE SYMPTOMS AFTER DELETING
    public void updateSymptoms() {
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {

                } else {

                }
            }
        });
    }
}
