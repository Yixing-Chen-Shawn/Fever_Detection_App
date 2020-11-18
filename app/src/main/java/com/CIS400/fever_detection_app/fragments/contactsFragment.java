package com.CIS400.fever_detection_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.CIS400.fever_detection_app.R;

public class contactsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        /*TextView txt = (TextView) view.findViewById(R.id.txt2);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("dd","dddd");
                Toast.makeText(getActivity(), "当前页面：2", Toast.LENGTH_SHORT).show();
            }
        });*/
        return view;
    }
}
