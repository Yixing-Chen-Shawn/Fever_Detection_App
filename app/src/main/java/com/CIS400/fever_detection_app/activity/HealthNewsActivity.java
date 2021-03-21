package com.CIS400.fever_detection_app.activity;

import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.CIS400.fever_detection_app.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class HealthNewsActivity extends BaseActivity{
    private String jsonUrl = "http://newsapi.org/v2/top-headlines?country=us&catagory=health&apiKey=96addd4bc8af46d1881803c589c42e22";
    public TextView news0;
    public ImageView news0Img;
    public TextView[] news = new TextView[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_news);
        ((TextView) findViewById(R.id.header)).setOnClickListener(null);
        news0 = (TextView) findViewById(R.id.news0);
        news0Img = (ImageView) findViewById(R.id.news0_img);
        news0Img.setOnClickListener(null);
        news0.setText("Getting news");
        news0.setOnClickListener(null);
        news[0] = (TextView) findViewById(R.id.news1);
        news[1] = (TextView) findViewById(R.id.news2);
        news[2] = (TextView) findViewById(R.id.news3);
        news[3] = (TextView) findViewById(R.id.news4);
        for(int i = 0; i < 4; i++){
            news[i].setText("Getting news");
            news[i].setOnClickListener(null);
        }
        getNews();
    }

    public void getNews(){
        RequestQueue queue = Volley.newRequestQueue(this);
        class ListenerParser implements Response.Listener<JSONObject> {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray articles = null;
                try {
                    articles = (JSONArray) response.get("articles");
                    JSONObject a0 = (JSONObject) articles.get(0);
                    String url0 = (String) a0.get("url");
                    String news0ImageUrl = (String) a0.get("urlToImage");
                    news0.setText((String) a0.get("title"));
                    news0.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent(HealthNewsActivity.this, NewsDisplayActivity.class);
                            Bundle b = new Bundle();
                            try {
                                b.putString("news", (String) a0.get("content"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            intent.putExtras(b);
                            startActivity(intent);
                        }
                    });
                    news0Img.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent(HealthNewsActivity.this, NewsDisplayActivity.class);
                            Bundle b = new Bundle();
                            try {
                                b.putString("url", (String) a0.get("url"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            intent.putExtras(b);
                            startActivity(intent);
                        }
                    });

                    for(int i =0; i < 4; ++i){
                        JSONObject articleI = (JSONObject) articles.get(i+1);
                        TextView newsI = news[i];
                        newsI.setText((String) articleI.get("title"));
                        newsI.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                Intent intent = new Intent(HealthNewsActivity.this, NewsDisplayActivity.class);
                                Bundle b = new Bundle();
                                try {
                                    b.putString("url", (String) a0.get("url"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                intent.putExtras(b);
                                startActivity(intent);
                            }
                        });
                    }
                    ImageRequest imageRequest = new ImageRequest(news0ImageUrl, new Response.Listener<android.graphics.Bitmap>(){
                        @Override
                        public void onResponse(android.graphics.Bitmap response) {
                            news0Img.setImageBitmap(response);
                        }
                    }, 1920, 1080,android.graphics.Bitmap.Config.valueOf("ARGB_8888"),new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            news0.setText("cannot get img");
                        }
                    });
                    queue.add(imageRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ListenerParser listener = new ListenerParser();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, this.jsonUrl, null, listener, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        news0.setText("Unable to get news");
                        for(int i =0; i < 4; ++i){
                            news[i].setText("Unable to get news");
                        }

                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("User-Agent", "Mozilla/5.0");
                params.put("Accept-Language", "en");

                return params;
            }
        };
        queue.add(jsonObjectRequest);

    }

}
