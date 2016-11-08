package com.ipq.ipq.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.ipq.ipq.Manifest;


public class GPSActivity implements LocationListener {
    private static final long MIN_DISTANCE = 10; // 10 meters
    private static final long MIN_TIME = 1000 * 30; // 1 minute
    private static int REQUEST_LOCATION=1;
    public static String GPSACTION="ON_GPS_ENABLE_ACTION";
    private static String[] PERMISSIONS_CONTACT = {android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION};
    private LocationManager locationManager;
    public Activity mActivity;
    public Context context;

    public GPSActivity(Context context, Activity activity) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.context = context;
        mActivity=activity;
    }
    public boolean isProviderEnabled()
    {
       boolean isLocaationNetWork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGPSenabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isGPSenabled || isLocaationNetWork;
    }

    @Override
    public void onLocationChanged(Location location)
    {



    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {



    }

    @Override
    public void onProviderEnabled(String provider)
    {

        if(LocationManager.GPS_PROVIDER.equals(provider)){
            Toast.makeText(this.context,"GPS on", Toast.LENGTH_SHORT).show();
        }




    }

    @Override
    public void onProviderDisabled(String provider)
    {
        if(LocationManager.GPS_PROVIDER.equals(provider)){
            Toast.makeText(this.context,"GPS off", Toast.LENGTH_SHORT).show();
        }


    }

    public LatLng GetLocationUser() {
        LatLng latLng = null;
        boolean isLocaationNetWork, isGPSenabled;
        Location userLocationNow = null;
        isLocaationNetWork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        isGPSenabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isLocaationNetWork) {

            userLocationNow = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (userLocationNow != null) {
                latLng = new LatLng(userLocationNow.getLatitude(), userLocationNow.getLongitude());


            }
        }
        if (isGPSenabled)
        {

                if (ActivityCompat.checkSelfPermission(this.context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this.context, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED)
                {
                    RequestLocationPermission();


                }else
                {
                    if (userLocationNow == null)
                    {
                   locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                if (locationManager != null) {
                    userLocationNow = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (userLocationNow != null) {
                        latLng = new LatLng(userLocationNow.getLatitude(), userLocationNow.getLongitude());
                    }
                }
                }
            }
        }
        return latLng;
    }

    public void RequestLocationPermission()
    {
        ActivityCompat.requestPermissions(mActivity,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_LOCATION);
    }
}

