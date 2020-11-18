package com.CIS400.fever_detection_app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.data.MyUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class NotificationsActivity extends BaseActivity{

    MyUser user = BmobUser.getCurrentUser(MyUser.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        TextView newNotification = (TextView) findViewById(R.id.newNotificationLabel);
        TextView notificationDescription = (TextView) findViewById(R.id.notificationDescription);
        ImageView notificationDot = (ImageView) findViewById(R.id.notificationDot);
        Button suHealthAndWellnessButton = (Button) findViewById(R.id.suHealthAndWellness);
        Button suHealthCare = (Button) findViewById(R.id.suHealthCare);
        Button suPharmacy = (Button) findViewById(R.id.suPharmacy);

        List<String> symptomDates = (user.getSymptomDates() != null) ? user.getSymptomDates() : new ArrayList<>();
        List<String> symptomRatings = (user.getSymptomRatings() != null) ? user.getSymptomRatings() : new ArrayList<>();

        //Check if any ratings were a two or a one
        if (symptomRatings.contains("2 (Not Good)") || symptomRatings.contains("1 (Feeling Terrible)")) {
            newNotification.setText("You have a new notification");
            notificationDescription.setText(
                    "Hi! We've noticed in your symptoms log you didn't feel well recently, and we're sorry to hear that!" + "\n\n" +
                    "Here is a list of resources to help you out to get you feeling better!" + "\n\n" +
                    "");
            notificationDescription.setVisibility(View.VISIBLE);
            notificationDot.setVisibility(View.VISIBLE);

        } else {
            newNotification.setText("You have no new notifications");
            notificationDescription.setVisibility(View.INVISIBLE);
            notificationDot.setVisibility(View.INVISIBLE);
            suHealthAndWellnessButton.setVisibility(View.INVISIBLE);
            suHealthCare.setVisibility(View.INVISIBLE);
            suPharmacy.setVisibility(View.INVISIBLE);



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


    }

}
