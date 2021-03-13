package com.CIS400.fever_detection_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.data.MyUser;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ManualHealthActivity extends AppCompatActivity {

    private Button save, back;
    private MyUser user;
    private ImageView backButton;

    private RadioButton breakfastRadio;
    private RadioButton lunchRadio;
    private RadioButton dinnerRadio;
    private RadioButton dessertRadio;
    private TextView mealTitle;
    private TextView mealSummary;
    private Button getMeal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_health);
        Bmob.initialize(this, "2de9dc3c787359faf54d36e92a2bbfb0");

        backButton = (ImageView) findViewById(R.id.back_manualHealth);
        breakfastRadio = (RadioButton) findViewById(R.id.breakfast_radio);
        lunchRadio = (RadioButton) findViewById(R.id.lunch_radio);
        dinnerRadio = (RadioButton) findViewById(R.id.dinner_radio);
        dessertRadio = (RadioButton) findViewById(R.id.dessert_radio);
        mealTitle = (TextView) findViewById(R.id.meal_title);
        mealSummary = (TextView) findViewById(R.id.meal_summary);
        getMeal = (Button) findViewById(R.id.getMealButton);

        user = BmobUser.getCurrentUser(MyUser.class);





        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prepareForHttpRequest();
                //httpRequest();
            }
        });
    }

    private void prepareForHttpRequest() {
        Boolean breakfastRadioChecked = breakfastRadio.isChecked();
        Boolean lunchRadioChecked = lunchRadio.isChecked();
        Boolean dinnerRadioChecked = dinnerRadio.isChecked();
        Boolean dessertRadioChecked = dessertRadio.isChecked();

        if (breakfastRadioChecked) {
            httpRequest("&tags=breakfast");
        } else if (lunchRadioChecked) {
            httpRequest("&tags=lunch");
        } else if (dinnerRadioChecked) {
            httpRequest("&tags=dinner");
        } else if (dessertRadioChecked) {
            httpRequest("&tags=dessert");
        } else {
            httpRequest("");
        }
    }

    private void httpRequest(String mealType) {

        // HTTP REQUEST
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.spoonacular.com/recipes/random?number=1" + mealType + "&apiKey=fb66054ae8124ef7970d33b3aafa8c93";
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    ManualHealthActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                getMeals(myResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    private void getMeals(String json) throws JSONException{

        JSONObject newJson = new JSONObject(json);
        JSONArray recipes = newJson.getJSONArray("recipes");
        //names.add(results.getJSONObject(i).getString("name"));
        mealTitle.setText(recipes.getJSONObject(0).getString("title"));
        mealSummary.setText(recipes.getJSONObject(0).getString("instructions"));


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