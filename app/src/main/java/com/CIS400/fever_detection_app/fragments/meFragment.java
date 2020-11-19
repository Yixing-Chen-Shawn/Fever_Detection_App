package com.CIS400.fever_detection_app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.activity.ContactsLogActivity;
import com.CIS400.fever_detection_app.activity.HeartRateLogActivity;
import com.CIS400.fever_detection_app.activity.LoginActivity;
import com.CIS400.fever_detection_app.activity.MeChangePasswordActivity;
import com.CIS400.fever_detection_app.activity.MeChangePhoneNumActivity;
import com.CIS400.fever_detection_app.activity.NotificationsActivity;
import com.CIS400.fever_detection_app.activity.SymptomsLogActivity;
import com.CIS400.fever_detection_app.data.MyUser;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class meFragment extends Fragment {
    private ImageView alertIcon, meAlert;
    private MyUser user;
    private List<String> symptomRatings;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        Bmob.initialize(getActivity(), "2de9dc3c787359faf54d36e92a2bbfb0");
        RelativeLayout heart_rate = (RelativeLayout) view.findViewById(R.id.RelativeLayout_Heart_Rate);
        RelativeLayout contacts = (RelativeLayout) view.findViewById(R.id.RelativeLayout_Symptoms);
        RelativeLayout change_password = (RelativeLayout) view.findViewById(R.id.RelativeLayout_Change_Password);
        RelativeLayout change_phoneNum = (RelativeLayout) view.findViewById(R.id.RelativeLayout_Change_PhoneNum);
        RelativeLayout symptoms = (RelativeLayout) view.findViewById(R.id.RelativeLayout_Symptoms);
        Button logout  = (Button) view.findViewById(R.id.Button_Logout);
        user = BmobUser.getCurrentUser(MyUser.class);
        alertIcon = (ImageView) view.findViewById(R.id.me_alert_icon);
        meAlert = (ImageView) view.findViewById(R.id.me_alert);
        alertIcon.setVisibility(View.INVISIBLE);
        symptomRatings = user.getSymptomRatings();
        if (symptomRatings.contains("2 (Not Good)") || symptomRatings.contains("1 (Feeling Terrible)")) {
            alertIcon.setVisibility(View.VISIBLE);
        }

        meAlert.setOnClickListener(new View.OnClickListener() {
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

        heart_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HeartRateLogActivity.class));
            }
        });

        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ContactsLogActivity.class));
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MeChangePasswordActivity.class));
            }
        });

        change_phoneNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MeChangePhoneNumActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobUser.logOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                Toast.makeText(getContext() , "You have logged out", Toast.LENGTH_LONG).show();
            }
        });

        symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SymptomsLogActivity.class));
            }
        });
        return view;
    }
}
