package com.CIS400.fever_detection_app.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.activity.ActivityCollector;
import com.CIS400.fever_detection_app.activity.CoronaStatsActivity;
import com.CIS400.fever_detection_app.activity.HealthNewsActivity;
import com.CIS400.fever_detection_app.activity.HeartRateLogActivity;
import com.CIS400.fever_detection_app.activity.LoginActivity;
import com.CIS400.fever_detection_app.activity.ManualSymptomActivity;
import com.CIS400.fever_detection_app.activity.NotificationsActivity;
import com.CIS400.fever_detection_app.activity.SymptomsLogActivity;
import com.CIS400.fever_detection_app.activity.WorkoutActivity;
import com.CIS400.fever_detection_app.data.MyUser;

import java.util.ArrayList;
import java.util.List;

public class homeFragment extends Fragment {

    private ImageView alertIcon, homeAlert;
    private Button button, symptom_but, health_but;
    private List<String> bodytemp, heartrate, blood, symptomRatings;
    private MyUser user;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Bmob.initialize(getActivity(), "2de9dc3c787359faf54d36e92a2bbfb0");
        user = BmobUser.getCurrentUser(MyUser.class);
        if(user == null){
            startActivity(new Intent(getActivity(), LoginActivity.class));
            new ActivityCollector().finishAll();
            return view;
        }
        button = (Button) view.findViewById(R.id.button_Covid);
        symptom_but = (Button) view.findViewById(R.id.button_Symptom);
        health_but = (Button) view.findViewById(R.id.button_health);

        alertIcon = (ImageView) view.findViewById(R.id.alert_icon);
        homeAlert = (ImageView) view.findViewById(R.id.home_alert);

        //1. Initially set alertIcon to invisible
        alertIcon.setVisibility(View.INVISIBLE);
        homeAlert.setVisibility(View.VISIBLE);
        bodytemp = user.getBodyTemp();
        heartrate = user.getHeartRate();
        blood = user.getBlood();
        symptomRatings = user.getSymptomRatings();
        if (bodytemp.stream().anyMatch(i-> (!i.equals("Unknown") && (Double.parseDouble(i) < 36.5 || Double.parseDouble(i) > 37)))|| heartrate.stream().anyMatch(a -> (!a.equals("Unknown") && (Double.parseDouble(a) < 60 || Double.parseDouble(a) > 100)))|| blood.stream().anyMatch(b -> !b.equals("Unknown") && (Double.parseDouble(b) < 80 || Double.parseDouble(b) > 120)) || symptomRatings.contains("2 (Not Good)") || symptomRatings.contains("1 (Feeling Terrible)")) {
            alertIcon.setVisibility(View.VISIBLE);
        }else{
            if(alertIcon.getVisibility() == View.VISIBLE){
                alertIcon.setVisibility(View.INVISIBLE);
            }
        }




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CoronaStatsActivity.class);
                startActivity(intent);
            }
        });

        health_but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HeartRateLogActivity.class));
            }
        });


        symptom_but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SymptomsLogActivity.class));
            }
        });


        homeAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setNotificationViewed("True");
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                    }
                });
                Intent intent = new Intent(getActivity(), NotificationsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


}
