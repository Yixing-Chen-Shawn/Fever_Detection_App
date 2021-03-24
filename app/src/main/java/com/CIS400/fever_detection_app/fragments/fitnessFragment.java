package com.CIS400.fever_detection_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.activity.CalorieTrackerActivity;
import com.CIS400.fever_detection_app.activity.MealPlanner;
import com.CIS400.fever_detection_app.activity.StepCountActivity;
import com.CIS400.fever_detection_app.activity.WorkoutActivity;

public class fitnessFragment extends Fragment {
    private Button workoutPlanner, stepCounter, calorieTracker, mealPlanner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fitness, container, false);
        workoutPlanner = (Button) view.findViewById(R.id.workoutPlanButton);
        stepCounter = (Button) view.findViewById(R.id.step_counter_btn);
        calorieTracker = (Button) view.findViewById(R.id.calorieTrackerButton);
        mealPlanner = (Button) view.findViewById(R.id.mealPlanButton);

        workoutPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WorkoutActivity.class));
            }
        });

        mealPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MealPlanner.class));
            }
        });

        stepCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), StepCountActivity.class));
            }
        });

        calorieTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CalorieTrackerActivity.class));
            }
        });
        return view;
    }
}