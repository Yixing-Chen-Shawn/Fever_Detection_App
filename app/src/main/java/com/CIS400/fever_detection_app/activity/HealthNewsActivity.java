package com.CIS400.fever_detection_app.activity;

import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.CIS400.fever_detection_app.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class HealthNewsActivity extends BaseActivity{
    private String rssUrl = "https://news.google.com/rss?hl=en-US&gl=US&ceid=US:en";
    public TextView news1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_news);
        news1 = (TextView) findViewById(R.id.rssLine0);
        news1.setText("Getting news");
        getNews();
    }

    public void getNews(){
        RequestQueue queue = Volley.newRequestQueue(this);
        class ListenerParser implements Response.Listener<String> {
            private Boolean status = false;
            @Override
            public void onResponse(String response) {
                try{
                    int[] idx = parseRss("<title>","</title>",response);
                    news1.setText(response.substring(idx[1],idx[3]));
                    idx = parseRss("<link>","</link>",response);

                    int[] finalIdx = idx;
                    news1.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v){
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.substring(finalIdx[1], finalIdx[3])));
                            startActivity(browserIntent);
                        }
                    });
                } catch (Exception e){
                    news1.setText("There is someting wrong with the RSS feed");
                }
            }

            public int[] parseRss(String startName, String endName, String data){
                int i = 0, dataLen = data.length(), startLen = startName.length(), endLen = endName.length(),added=0;
                int[] idx = new int[4];
                while(i<(dataLen-startLen-1)&&added<2){
                    if(data.substring(i,i+startLen).equals(startName)){
                        idx[added] = i+startLen;
                        added++;
                    }
                    i++;
                }
                i = 0;
                while(i<(dataLen-endLen-1)&&added<4){
                    if (data.substring(i,i+endLen).equals(endName)){
                        idx[added] = i;
                        added++;
                    }
                    i++;
                }
                return idx;
            }
        };

        ListenerParser listener = new ListenerParser();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.rssUrl, listener
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                news1.setText("Unable to get news.");
            }
        });
        queue.add(stringRequest);

    }

}
