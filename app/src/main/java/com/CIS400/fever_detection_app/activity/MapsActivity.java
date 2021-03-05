package com.CIS400.fever_detection_app.activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Iterator;


import com.CIS400.fever_detection_app.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private Location mLastLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback locationCallback;

    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<Double> latitudes = new ArrayList<Double>();
    private ArrayList<Double> longitudes = new ArrayList<Double>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        mLastLocation = location;
                    }
                    if (mFusedLocationClient != null) {
                        mFusedLocationClient.removeLocationUpdates(locationCallback);
                    }
                }
            }
        };
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        } else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        mLastLocation = location;
                        Log.i("Loc: ", mLastLocation.getLatitude() + " " + mLastLocation.getLongitude());
                        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.map);
                        mapFragment.getMapAsync(MapsActivity.this);
                    }
                }
            });
        }
    }

    private void httpRequest() {

        // HTTP REQUEST
        OkHttpClient client = new OkHttpClient();
        String test = "Hello " + "Kanoa";
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + mLastLocation.getLatitude() + "," + mLastLocation.getLongitude() + "&radius=16093&types=hospital&key=AIzaSyDT98N1bS6B2JY4zPrG_xNoc8PnbxgOP2Q";
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
                if(response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    MapsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Log.d("myTag", myResponse);
                            try {
                                getNearbyHospitals(myResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });



    }

    private void getNearbyHospitals(String json) throws JSONException {


        JSONObject newJson = new JSONObject(json);
        JSONArray results = newJson.getJSONArray("results");


        for(int i = 0; i < results.length(); i++) {

            names.add(results.getJSONObject(i).getString("name"));

            //Log.d("tester", "" + results.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat") + "");
            latitudes.add(results.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat") );
            longitudes.add(results.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
        }

        rePinMap();


        //Log.d("lat", "" + latitudes.size() + "");


    }

    private void rePinMap() {
        LatLng latLng;
        Log.d("size", "" + names.size() + "");

        for (int i = 0; i < names.size(); i++) {
            latLng = new LatLng(latitudes.get(i), longitudes.get(i));
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(names.get(i)));

        }

    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("I am here!"));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        httpRequest();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                } else {
                    Toast.makeText(this, R.string.location_permission_denied, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}