package com.CIS400.fever_detection_app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.data.MyUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class NotificationsActivity extends BaseActivity{

    private MyUser user;
    private ImageView back;
    private List<String> bodytemp, heartrate, blood, symptomRatings, healthdate;
    private TextView newNotification, notificationDescription, notibodytemp, notiheartrate, notiblood;
    private ImageView notidotbodytemp, notidotheartrate, notidotblood, notidotrating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        newNotification = (TextView) findViewById(R.id.newNotificationLabel);
        notificationDescription = (TextView) findViewById(R.id.notificationDescription);
        notibodytemp = (TextView) findViewById(R.id.notificationDescription_bodyTemp);
        notiheartrate = (TextView) findViewById(R.id.notificationDescription_heartrate);
        notiblood = (TextView) findViewById(R.id.notificationDescription_blood);
        notidotbodytemp = (ImageView) findViewById(R.id.notificationDot);
        notidotheartrate = (ImageView) findViewById(R.id.notificationDot_one);
        notidotblood = (ImageView) findViewById(R.id.notificationDot_two);
        notidotrating = (ImageView) findViewById(R.id.notificationDot_three);


        Button suHealthAndWellnessButton = (Button) findViewById(R.id.suHealthAndWellness);
        Button suHealthCare = (Button) findViewById(R.id.suHealthCare);
        Button suPharmacy = (Button) findViewById(R.id.suPharmacy);
        user = BmobUser.getCurrentUser(MyUser.class);
        back = (ImageView) findViewById(R.id.back_notification);

        healthdate = user.getHrdates();
        bodytemp = user.getBodyTemp();
        heartrate = user.getHeartRate();
        blood = user.getBlood();
        symptomRatings = user.getSymptomRatings();
        notificationDescription.setVisibility(View.INVISIBLE);
        notidotrating.setVisibility(View.INVISIBLE);
        suHealthAndWellnessButton.setVisibility(View.INVISIBLE);
        suHealthCare.setVisibility(View.INVISIBLE);
        suPharmacy.setVisibility(View.INVISIBLE);
        notibodytemp.setVisibility(View.INVISIBLE);
        notidotbodytemp.setVisibility(View.INVISIBLE);
        notiheartrate.setVisibility(View.INVISIBLE);
        notidotheartrate.setVisibility(View.INVISIBLE);
        notiblood.setVisibility(View.INVISIBLE);
        notidotblood.setVisibility(View.INVISIBLE);
        notificationDescription.setVisibility(View.INVISIBLE);
        notidotrating.setVisibility(View.INVISIBLE);


        for(String bt : bodytemp){
            if(!bt.equals("Unknown")){
                double temp = Double.parseDouble(bt);
                if(temp < 36.5 || temp > 37){
                    newNotification.setText("You have a new notification");
                    notibodytemp.setText("Hi! We've noticed your body temperature is not within normal range: " + temp + "°C.");
                    notibodytemp.setVisibility(View.VISIBLE);
                    notidotbodytemp.setVisibility(View.VISIBLE);
                    notificationDescription.setVisibility(View.VISIBLE);
                }
            }
        }

        if(notibodytemp.getVisibility() == View.INVISIBLE){
            newNotification.setText("You have no new notifications");
            notibodytemp.setVisibility(View.INVISIBLE);
            notidotbodytemp.setVisibility(View.INVISIBLE);
        }else{
            notificationDescription.setVisibility(View.VISIBLE);
            notidotrating.setVisibility(View.VISIBLE);
            suHealthAndWellnessButton.setVisibility(View.VISIBLE);
            suHealthCare.setVisibility(View.VISIBLE);
            suPharmacy.setVisibility(View.VISIBLE);
        }

        for(String hr : heartrate){
            if(!hr.equals("Unknown")){
                double rate = Double.parseDouble(hr);
                if(rate < 60 || rate > 100){
                    newNotification.setText("You have a new notification");
                    notiheartrate.setText("Hi! We've noticed your heart rate per minute is not within normal range: " + rate + "bpm.");
                    notiheartrate.setVisibility(View.VISIBLE);
                    notidotheartrate.setVisibility(View.VISIBLE);
                    notificationDescription.setVisibility(View.VISIBLE);
                }
            }
        }

        if(notiheartrate.getVisibility() == View.INVISIBLE){
            newNotification.setText("You have no new notifications");
            notiheartrate.setVisibility(View.INVISIBLE);
            notidotheartrate.setVisibility(View.INVISIBLE);
        }else{
            notificationDescription.setVisibility(View.VISIBLE);
            notidotrating.setVisibility(View.VISIBLE);
            suHealthAndWellnessButton.setVisibility(View.VISIBLE);
            suHealthCare.setVisibility(View.VISIBLE);
            suPharmacy.setVisibility(View.VISIBLE);
        }


        for(String b : blood){
            if(!b.equals("Unknown")){
                double bp = Double.parseDouble(b);
                if(bp < 80 || bp > 120){
                    newNotification.setText("You have a new notification");
                    notiblood.setText("Hi! We've noticed your blood pressure is not within normal range: " + bp + "mmHg.");
                    notiblood.setVisibility(View.VISIBLE);
                    notidotblood.setVisibility(View.VISIBLE);
                    notificationDescription.setVisibility(View.VISIBLE);
                }
            }
        }

        if(notiblood.getVisibility() == View.INVISIBLE){
            newNotification.setText("You have no new notifications");
            notiblood.setVisibility(View.INVISIBLE);
            notidotblood.setVisibility(View.INVISIBLE);
        }else{
            notificationDescription.setVisibility(View.VISIBLE);
            notidotrating.setVisibility(View.VISIBLE);
            suHealthAndWellnessButton.setVisibility(View.VISIBLE);
            suHealthCare.setVisibility(View.VISIBLE);
            suPharmacy.setVisibility(View.VISIBLE);
        }
        //Check if any ratings were a two or a one
        if (notibodytemp.getVisibility() == View.VISIBLE || notiheartrate.getVisibility() == View.VISIBLE || notiblood.getVisibility() == View.VISIBLE || symptomRatings.contains("2 (Not Good)") || symptomRatings.contains("1 (Feeling Terrible)")) {
            newNotification.setText("You have a new notification");
            notificationDescription.setText(
                    "Hi! We've noticed in your symptoms log or health stats you didn't feel well recently, and we're sorry to hear that!" + "\n" +
                    "Here is a list of resources to help you out to get you feeling better!" +
                    "");
            notificationDescription.setVisibility(View.VISIBLE);
            notidotrating.setVisibility(View.VISIBLE);
            suHealthAndWellnessButton.setVisibility(View.VISIBLE);
            suHealthCare.setVisibility(View.VISIBLE);
            suPharmacy.setVisibility(View.VISIBLE);

        } else {
            newNotification.setText("You have no new notifications");
            suHealthAndWellnessButton.setVisibility(View.INVISIBLE);
            suHealthCare.setVisibility(View.INVISIBLE);
            suPharmacy.setVisibility(View.INVISIBLE);
            notificationDescription.setVisibility(View.INVISIBLE);
            notidotrating.setVisibility(View.INVISIBLE);
        }

        suHealthAndWellnessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.syracuse.edu/life/services-support/health/"));
                startActivity(browserIntent);
            }
        });
        suHealthCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ese.syr.edu/bewell/primary-health-care/"));
                startActivity(browserIntent);
            }
        });
        suPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ese.syr.edu/bewell/pharmacy/"));
                startActivity(browserIntent);
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

}
