package com.CIS400.fever_detection_app.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.CIS400.fever_detection_app.R;

public class CalorieTrackerActivity extends AppCompatActivity {

    private Button calculate;
    private RadioButton male, female, littleActive, slightlyActive, moderatelyActive, veryActive;
    private TextView age, height, caloriesShownText;
    private Double total;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorietracker);

        calculate = (Button) findViewById(R.id.calculateButton);
        male = (RadioButton) findViewById(R.id.maleRadioButton);
        female = (RadioButton) findViewById(R.id.femaleRadioButton);
        littleActive = (RadioButton) findViewById(R.id.littleExerciseRadioButton);
        slightlyActive = (RadioButton) findViewById(R.id.slightlyActiveRadioButton);
        moderatelyActive = (RadioButton) findViewById(R.id.moderatelyActiveRadioButton);
        veryActive = (RadioButton) findViewById(R.id.veryActiveRadioButton);
        age = (TextView) findViewById(R.id.ageTextField);
        height = (TextView) findViewById(R.id.heightTextField);
        caloriesShownText = (TextView) findViewById(R.id.numberOfCaloriesText) ;


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCalorieCalculation();

            }
        });


    }

    private void getCalorieCalculation() {
        // Check for empty cases, if empty then return
        if ((age.getText().toString() == "" || age.getText().toString() == null) || (height.getText().toString() == "" || height.getText().toString() == null) ) {
            return;
        }

        // FOR MALES
        if (male.isChecked()) {

            if (littleActive.isChecked()) {
                total = (66 + (6.2 * (Integer.parseInt(height.getText().toString()))) + (12.7 * (Integer.parseInt(height.getText().toString()))) - (6.76 * (Integer.parseInt(age.getText().toString())))) * 1.2;

            }
            else if (slightlyActive.isChecked()) {
                total = (66 + (6.2 * (Integer.parseInt(height.getText().toString()))) + (12.7 * (Integer.parseInt(height.getText().toString()))) - (6.76 * (Integer.parseInt(age.getText().toString())))) * 1.37;
            }
            else if (moderatelyActive.isChecked()) {
                total = (66 + (6.2 * (Integer.parseInt(height.getText().toString()))) + (12.7 * (Integer.parseInt(height.getText().toString()))) - (6.76 * (Integer.parseInt(age.getText().toString())))) * 1.55;
            }
            else if (veryActive.isChecked()) {
                total = (66 + (6.2 * (Integer.parseInt(height.getText().toString()))) + (12.7 * (Integer.parseInt(height.getText().toString()))) - (6.76 * (Integer.parseInt(age.getText().toString())))) * 1.725;
            }


        }

        // FOR FEMALES
        else if (female.isChecked()) {

            if (littleActive.isChecked()) {
                total = (655.1 + (4.35 * (Integer.parseInt(height.getText().toString()))) + (4.7 * (Integer.parseInt(height.getText().toString()))) - (4.7 * (Integer.parseInt(age.getText().toString())))) * 1.2;
            }
            else if (slightlyActive.isChecked()) {
                total = (655.1 + (4.35 * (Integer.parseInt(height.getText().toString()))) + (4.7 * (Integer.parseInt(height.getText().toString()))) - (4.7 * (Integer.parseInt(age.getText().toString())))) * 1.37;
            }
            else if (moderatelyActive.isChecked()) {
                total = (655.1 + (4.35 * (Integer.parseInt(height.getText().toString()))) + (4.7 * (Integer.parseInt(height.getText().toString()))) - (4.7 * (Integer.parseInt(age.getText().toString())))) * 1.55;
            }
            else if (veryActive.isChecked()) {
                total = (655.1 + (4.35 * (Integer.parseInt(height.getText().toString()))) + (4.7 * (Integer.parseInt(height.getText().toString()))) - (4.7 * (Integer.parseInt(age.getText().toString())))) * 1.725;
            }

        }

        if (total != null) {
            caloriesShownText.setText("Calories needed daily is: " + String.valueOf(total) + "\n\n" + "Calories needed to lose weight is: " + String.valueOf(total - 500));
        } else {
            caloriesShownText.setText("Please input all values");
        }

    }


}
