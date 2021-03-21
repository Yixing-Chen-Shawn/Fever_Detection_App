package com.CIS400.fever_detection_app.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.constraintlayout.helper.widget.Layer;
import androidx.core.content.ContextCompat;

import com.CIS400.fever_detection_app.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class WorkoutActivity extends BaseActivity {
    private String muscleUrl = "https://wger.de/api/v2/muscle/", exerciseUrl = "https://wger.de/api/v2/exercise/";
    public TableLayout exerciseTable;
    public JSONArray exerciseList;
    public TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        RequestQueue queue = Volley.newRequestQueue(this);
        exerciseTable = (TableLayout) findViewById(R.id.exerciseTable);
        params.setMargins(30, 0, 30, 0);
        TableRow title = (TableRow) findViewById(R.id.wTitle);

        GradientDrawable border = new GradientDrawable();
        border.setStroke(2, 0xFF000000);
        Drawable[] layers = {border};
        LayerDrawable bottom = new LayerDrawable(layers);
        bottom.setLayerInset(0, -2, -2, -2, 0);
        title.setBackground(bottom);

        class ListenerParser implements Response.Listener<JSONObject> {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray muscles = null;
                try {
                    muscles = (JSONArray) response.get("results");
                    for(int i = 0; i < muscles.length(); ++i){
                        int rowAdded = 0;
                        TextView muscleName = new TextView(getApplicationContext());
                        TableRow row = new TableRow(getApplicationContext());
                        exerciseTable.addView(row);
                        int id = (int) ((JSONObject) muscles.get(i)).get("id");
                        row.addView(muscleName);
                        row.setLayoutParams(params);
                        muscleName.setText(((String) ((JSONObject) muscles.get(i)).get("name")) + "    ");
                        muscleName.setTextSize(17);
                        TextView exercise;
                        for(int j = 0; j < exerciseList.length(); j++) {
                            int language = (int) ((JSONObject) exerciseList.get(j)).get("language");
                            if (language == 2) {
                                Boolean hasId = false;
                                JSONArray primary = (JSONArray) ((JSONObject) exerciseList.get(j)).get("muscles");
                                JSONArray secondary = (JSONArray) ((JSONObject) exerciseList.get(j)).get("muscles_secondary");
                                for (int k = 0; k < primary.length() + secondary.length(); ++k) {
                                    if (k < primary.length()) {if ((int) primary.get(k) == id) { hasId = true; rowAdded+=1; k = primary.length() + secondary.length();}}
                                    else {{if ((int) secondary.get(k-primary.length()) == id) {hasId = true; rowAdded+=1; k = primary.length() + secondary.length();}}}
                                }
                                if (hasId) {
                                    if (rowAdded!=1){
                                        row = new TableRow(getApplicationContext());
                                        exerciseTable.addView(row);
                                        row.addView(new Space(getApplicationContext()));
                                    }
                                    row.setLayoutParams(params);
                                    exercise = new TextView(getApplicationContext());
                                    exercise.setTextSize(17);
                                    row.addView(exercise);
                                    exercise.setText((String) ((JSONObject) exerciseList.get(j)).get("name"));
                                }
                            }
                        }
                        if (rowAdded==0){
                            exercise = new TextView(getApplicationContext());
                            exercise.setTextSize(17);
                            exercise.setText("No exercise found");
                            row.addView(exercise);
                        }

                        row.setBackground(bottom);

                    }

                } catch (Exception e) {
                }
            }
        }
        ListenerParser listener = new ListenerParser();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, this.muscleUrl, null, listener, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Token 4123325a4b028fa34f81d9733fbb49733f3fbb09");
                return params;
            }
        };

        JsonObjectRequest exerciseListRequest = new JsonObjectRequest
                (Request.Method.GET, exerciseUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    exerciseList = (JSONArray) response.get("results");
                                    queue.add(jsonObjectRequest);
                                } catch (JSONException e) {
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError e) {
                            }
                        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Token 4123325a4b028fa34f81d9733fbb49733f3fbb09");
                return params;
            }
        };
        queue.add(exerciseListRequest);

    }

}
