package com.CIS400.fever_detection_app.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.activity.HealthNewsActivity;
import com.CIS400.fever_detection_app.activity.HeartRateLogActivity;
import com.CIS400.fever_detection_app.activity.ManualHealthActivity;
import com.CIS400.fever_detection_app.activity.ManualSymptomActivity;
import com.CIS400.fever_detection_app.activity.MapsActivity;
import com.CIS400.fever_detection_app.activity.NotificationsActivity;
import com.CIS400.fever_detection_app.data.MyUser;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class heartRateFragment extends Fragment {
    private ImageView healthAlert, alertIcon;
    private List<String> symptomDescriptions, symptomDates, symptomRatings, bodytemp, heartrate, blood;
    private MyUser user;
    private Button manualhealth, manualInput, findHospitals, health_but;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_heart_rate, container, false);
        Bmob.initialize(getActivity(), "2de9dc3c787359faf54d36e92a2bbfb0");
        user = BmobUser.getCurrentUser(MyUser.class);
        manualhealth = (Button) view.findViewById(R.id.healthCenter_healthButt);
        manualInput = (Button) view.findViewById(R.id.manualInput);
        findHospitals = (Button) view.findViewById(R.id.hospital_butt);
        health_but = (Button) view.findViewById(R.id.healthNews_Butt);

        healthAlert = (ImageView) view.findViewById(R.id.healthCenter_alert);
        alertIcon = (ImageView) view.findViewById(R.id.healthCenter_alert_icon);
        alertIcon.setVisibility(View.INVISIBLE);
        healthAlert.setVisibility(View.VISIBLE);
        symptomDescriptions = user.getSymptoms();
        symptomDates = user.getSymptomDates();
        symptomRatings = user.getSymptomRatings();
        bodytemp = user.getBodyTemp();
        heartrate = user.getHeartRate();
        blood = user.getBlood();

        if (bodytemp.stream().anyMatch(i-> (!i.equals("Unknown") && (Double.parseDouble(i) < 36.5 || Double.parseDouble(i) > 37)))|| heartrate.stream().anyMatch(a -> (!a.equals("Unknown") && (Integer.parseInt(a) < 60 || Integer.parseInt(a) > 100)))|| blood.stream().anyMatch(b -> !b.equals("Unknown") && (Double.parseDouble(b) < 80 || Double.parseDouble(b) > 120)) ||symptomRatings.contains("2 (Not Good)") || symptomRatings.contains("1 (Feeling Terrible)")) {
            alertIcon.setVisibility(View.VISIBLE);
        }else{
            if(alertIcon.getVisibility() == View.VISIBLE){
                alertIcon.setVisibility(View.INVISIBLE);
            }
        }

        healthAlert.setOnClickListener(new View.OnClickListener() {
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

        manualhealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ManualHealthActivity.class));
            }
        });

        manualInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ManualSymptomActivity.class));
            }
        });

        findHospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapsActivity.class));
            }
        });

        health_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HealthNewsActivity.class));
            }
        });

        return view;
    }
}
