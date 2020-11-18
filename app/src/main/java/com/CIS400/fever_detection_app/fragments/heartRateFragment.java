package com.CIS400.fever_detection_app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.activity.ContactsLogActivity;
import com.CIS400.fever_detection_app.activity.HeartRateLogActivity;
import com.CIS400.fever_detection_app.activity.ManualHealthActivity;
import com.CIS400.fever_detection_app.activity.ManualSymptomActivity;

public class heartRateFragment extends Fragment {
    private Button manualInput, manualhealth;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_heart_rate, container, false);

        manualhealth = (Button) view.findViewById(R.id.healthCenter_healthButt);
        manualInput = (Button) view.findViewById(R.id.manualInput);

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
