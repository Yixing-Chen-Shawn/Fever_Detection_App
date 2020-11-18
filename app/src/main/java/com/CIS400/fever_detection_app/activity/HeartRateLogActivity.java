package com.CIS400.fever_detection_app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.adapters.HrRecyclerViewAdapter;
import com.CIS400.fever_detection_app.data.MyUser;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class HeartRateLogActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MyUser user;
    private List<String> HeartRateDates, HearRate, Contacts, BodyTemp, Blood;
    private TextInputEditText searchEditText;
    private MaterialCardView searchCard;
    private String searchText = "";
    private Button confirm;
    private TextView HealthList;
    private int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartratelog);
        Bmob.initialize(this, "2de9dc3c787359faf54d36e92a2bbfb0");
        user = BmobUser.getCurrentUser(MyUser.class);
        Toolbar toolbar = findViewById(R.id.toolbar_HR);
        setSupportActionBar(toolbar);

        searchEditText = findViewById(R.id.searchDeletionHR);
        searchCard = findViewById(R.id.searchCardDeleteHR);
        searchCard.setVisibility(View.GONE);
        confirm = (Button) findViewById(R.id.button_ConfirmHR);
        HeartRateDates = user.getHrdates();
        HearRate = user.getHeartRate();
        Contacts = user.getContacts();
        BodyTemp = user.getBodyTemp();
        Blood = user.getBlood();
        Collections.reverse(HeartRateDates);
        Collections.reverse(HearRate);
        Collections.reverse(Contacts);
        Collections.reverse(BodyTemp);
        Collections.reverse(Blood);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_hr);
        HealthList = (TextView) findViewById(R.id.description_HeartRate);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        size = HeartRateDates.size();
        recyclerView.setLayoutManager(layoutManager);
        List<List<String>> input = new ArrayList<>();
        for(int i = 0; i < size; i++){
            input.add(List.of(HeartRateDates.get(i), HearRate.get(i), Contacts.get(i), BodyTemp.get(i), Blood.get(i)));
        }
        HealthList.setText("You have health record for the past " + size + " day(s)");
        HealthList.setTextColor(Color.parseColor("#d44500"));
        mAdapter = new HrRecyclerViewAdapter(input);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_health, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                displayToast(getString(R.string.action_addH));
                startActivity(new Intent(HeartRateLogActivity.this, ManualHealthActivity.class));
                break;

            case R.id.action_delete:
                displayToast(getString(R.string.action_deleteH));
                initDeleteHealthStats();
                break;

            case R.id.action_clear:
                displayToast(getString(R.string.action_clearH));
                user.clearHealthStats();
                updateSymptoms();
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(getIntent());
                        finish();
                    }
                }, 500);
                break;
            case R.id.action_exit:
                displayToast("Exit from log");
                startActivity(new Intent(HeartRateLogActivity.this, MainActivity.class));
                break;
            default:
                //DO NOTHING
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    public void initDeleteHealthStats() {

        if (searchCard.getVisibility() == View.VISIBLE) {
            searchEditText.setText("");
            searchCard.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }else{
            searchCard.setVisibility(View.VISIBLE);
            searchEditText.requestFocus();
            searchEditText.setText("");
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
            }
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchEditText.setVisibility(View.GONE);
                    confirm.setVisibility(View.GONE);
                    searchText = searchEditText.getText().toString().trim();
                    int ret = user.deleteHealthStats(searchText);
                    if(ret == -1) displayToast("You do not have record on that day.");
                    updateSymptoms();
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(getIntent());
                            finish();
                        }
                    }, 500);
                }
            });
        }
    }


    //USED TO UPDATE SYMPTOMS AFTER DELETING
    public void updateSymptoms() {
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {

                } else {

                }
            }
        });
    }
}