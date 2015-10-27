package com.example.s198569.mylocation;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    Location location;
    String provider;
    TextView tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tw = (TextView) findViewById(R.id.myLocationText);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.GPS_PROVIDER;

        try{
            location = locationManager.getLastKnownLocation(provider);
            updateMyLocation(location);
        }catch(SecurityException e){
            Log.e("Error", e.getMessage());
        }
    }

    public void updateMyLocation(Location location){
        if(location != null){

            double lo = location.getLongitude();
            double lat = location.getLatitude();
            String latlongtext = "Longtitude: " + lo + "\n" + "Latitude: " + lat;
            tw.setText(latlongtext);
        }else {
            tw.setText("No location found");
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        updateMyLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        updateMyLocation(location);
    }

    @Override
    public void onProviderEnabled(String provider) {
        updateMyLocation(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
        updateMyLocation(location);
    }
}
