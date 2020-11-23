package com.CIS400.fever_detection_app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.activity.ContactsLogActivity;
import com.CIS400.fever_detection_app.activity.HeartRateLogActivity;
import com.CIS400.fever_detection_app.activity.ManualHealthActivity;
import com.CIS400.fever_detection_app.activity.ManualSymptomActivity;
import com.CIS400.fever_detection_app.activity.NotificationsActivity;
import com.CIS400.fever_detection_app.adapters.StateDataAdapter;
import com.CIS400.fever_detection_app.data.MyUser;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class heartRateFragment extends Fragment {
    private ImageView healthAlert, alertIcon;
    private List<String> symptomDescriptions, symptomDates, symptomRatings;
    private MyUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_heart_rate, container, false);
        Bmob.initialize(getActivity(), "2de9dc3c787359faf54d36e92a2bbfb0");
        user = BmobUser.getCurrentUser(MyUser.class);
        Button manualhealth = (Button) view.findViewById(R.id.healthCenter_healthButt);
        Button manualInput = (Button) view.findViewById(R.id.manualInput);
        healthAlert = (ImageView) view.findViewById(R.id.healthCenter_alert);
        alertIcon = (ImageView) view.findViewById(R.id.healthCenter_alert_icon);
        alertIcon.setVisibility(View.INVISIBLE);
        healthAlert.setVisibility(View.VISIBLE);
        symptomDescriptions = user.getSymptoms();
        symptomDates = user.getSymptomDates();
        symptomRatings = user.getSymptomRatings();

        if (symptomRatings.contains("2 (Not Good)") || symptomRatings.contains("1 (Feeling Terrible)")) {
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


        return view;
    }
}
