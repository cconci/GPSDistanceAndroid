package com.cconci.gpsdistance;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by cconci on 6/03/16.
 */
public class LocationHandler implements LocationListener {

    private LocationManager locationManager;
    private Location lastLocation;

    private Context context;
    private android.app.Activity actvity;

    public static final int MY_PERMISSION_ACCESS_COURSE_LOCATION = 1;

    private static final int LOCATION_UPDATE_MIN_TIME_MS = 2000;
    private static final int LOCATION_UPDATE_MIN_DISTANCE_M = 1;


    public LocationHandler(Context context,Activity activity) {

        this.context = context;
        this.actvity = activity;

    }

    public boolean getPermissions() {

        if ( ContextCompat.checkSelfPermission(this.context , android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this.actvity, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSION_ACCESS_COURSE_LOCATION);

            /*This will call back to 'onRequestPermissionsResult()'*/
            return false;
        }
        else {
            //we already have the permissions we want
            return true;
        }
    }

    public void initLocationManager() {

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            /*
            we do not have ther permissions we need
            */
            this.getPermissions(); //try asking for them

            return  ;
        }

        //We have ther permissions we need, set up a LocationManager

        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // Get GPS and network status
        boolean currentGPSStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(currentGPSStatus == false) {

            //we have no GPS - no location for us
        }
        else {

            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    this.LOCATION_UPDATE_MIN_TIME_MS, this.LOCATION_UPDATE_MIN_DISTANCE_M, this);

        }


    }

    public Location getLastLocation() {
        return this.lastLocation;
    }

    /*
    * LocationListener
    * <http://developer.android.com/reference/android/location/LocationListener.html>
    * */

    @Override
    public void onLocationChanged(Location location) {
        this.lastLocation = location;

        System.out.println(this.getClass().getSimpleName()+": onLocationChanged() - Lat:" +this.lastLocation.getLatitude()+", Long:"+this.lastLocation.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }


}

