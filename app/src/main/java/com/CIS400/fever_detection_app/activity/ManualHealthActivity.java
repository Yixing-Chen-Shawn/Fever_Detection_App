package com.CIS400.fever_detection_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.data.MyUser;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ManualHealthActivity extends AppCompatActivity {
    private EditText date, heartrate, contacts, bodytemp, blood;
    private String sdate, sheartrate, scontacts, sbodytemp, sblood;
    private List<String> datel, heartratel, contactl, bodytempl, bloodl;
    private int size;
    private Button save, back;
    private MyUser user;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_health);
        Bmob.initialize(this, "2de9dc3c787359faf54d36e92a2bbfb0");
        date = (EditText) findViewById(R.id.dateInput_date);
        heartrate = (EditText) findViewById(R.id.dateInput_heart_rate);
        contacts = (EditText) findViewById(R.id.dateInput_contacts);
        bodytemp = (EditText) findViewById(R.id.dateInput_bodyTemp);
        blood = (EditText) findViewById(R.id.dateInput_bloody_pressure);
        save = (Button) findViewById(R.id.saveButton_health);
        back = (Button) findViewById(R.id.BackButton_health);
        backButton = (ImageView) findViewById(R.id.back_manualHealth);
        user = BmobUser.getCurrentUser(MyUser.class);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sdate = date.getText().toString().trim();
                sheartrate = heartrate.getText().toString().trim();
                scontacts = contacts.getText().toString().trim();
                sbodytemp = bodytemp.getText().toString().trim();
                sblood = blood.getText().toString().trim();
                datel = user.getHrdates();
                heartratel = user.getHeartRate();
                contactl = user.getContacts();
                bodytempl = user.getBodyTemp();
                bloodl = user.getBlood();

                if (sdate.matches("") || sheartrate.matches("") || scontacts.matches("") || sbodytemp.matches("") || sblood.matches("")) {
                    Toast.makeText(ManualHealthActivity.this, "Error: Fill in all items", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!sdate.matches("^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d$")) {
                    Toast.makeText(ManualHealthActivity.this, "Date Format Error: Use format MM/DD/YEAR", Toast.LENGTH_LONG).show();
                    return;
                }

                if(Integer.parseInt(sheartrate) < 0){
                    sheartrate = "Unknown";
                }

                if(Integer.parseInt(scontacts) < 0){
                    scontacts = "Unknown";
                }


                if(Integer.parseInt(sbodytemp) < 0){
                    sbodytemp = "Unknown";
                }


                if(Integer.parseInt(sblood) < 0){
                    sblood = "Unknown";
                }

                if(datel.contains(sdate)){
                    int idx = datel.indexOf(sdate);
                    heartratel.set(idx, sheartrate);
                    contactl.set(idx, scontacts);
                    bodytempl.set(idx, sbodytemp);
                    bloodl.set(idx, sblood);
                    displayToast("Record on " + sdate + " has been overwritten.");
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                Toast.makeText(ManualHealthActivity.this, "Successful!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ManualHealthActivity.this, HeartRateLogActivity.class));

                            }else{
                                Toast.makeText(ManualHealthActivity.this, "User Update failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    return;
                }

                user.setHrdates(sdate);
                user.setHeartRate(sheartrate);
                user.setContacts(scontacts);
                user.setBodyTemp(sbodytemp);
                user.setBlood(sblood);

                datel = user.getHrdates();
                size = datel.size();
                if(size > 31) user.deleteHealthStats();

                //Update Database
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e == null){
                            Toast.makeText(ManualHealthActivity.this, "Successful!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ManualHealthActivity.this, HeartRateLogActivity.class));

                        }else{
                            Toast.makeText(ManualHealthActivity.this, "User Update failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManualHealthActivity.this, HeartRateLogActivity.class));
                finish();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
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

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

}