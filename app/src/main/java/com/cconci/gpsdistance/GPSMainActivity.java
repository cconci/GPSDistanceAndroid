package com.cconci.gpsdistance;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GPSMainActivity extends AppCompatActivity {

    private LocationHandler locationHandler;

    private Location locationA;
    private Location locationB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsmain);

        locationHandler = new LocationHandler(this,this);

        if(locationHandler.getPermissions() == true) {

            //we have permission
            locationHandler.initLocationManager();

            System.out.println(this.getClass().getSimpleName()+": Permissions granted");
        }
        else {
            //requesting permission 'onRequestPermissionsResult()'
            System.out.println(this.getClass().getSimpleName()+": Permissions Needed");
        }
    }


    /*
    UI Buttons
    */
    public void buttonClickSetGPSPointA(View v) {
        Toast.makeText(this, "buttonClickSetGPSPointA", Toast.LENGTH_LONG).show();

        this.locationA = this.locationHandler.getLastLocation();

        ((TextView) findViewById(R.id.textViewLocationAinfo)).setText(
                "Latitude:"
                + this.locationA.getLatitude()
                + " : "
                + "Longitude:"
                + this.locationA.getLongitude());

    }
    public void buttonClickSetGPSPointB(View v) {
        Toast.makeText(this, "buttonClickSetGPSPointB", Toast.LENGTH_LONG).show();

        this.locationB = this.locationHandler.getLastLocation();

        ((TextView) findViewById(R.id.textViewLocationBinfo)).setText(
                "Latitude:"
                        + this.locationB.getLatitude()
                        + " : "
                        + "Longitude:"
                        + this.locationB.getLongitude());
    }

    /*
    <http://developer.android.com/training/permissions/requesting.html>
    */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LocationHandler.MY_PERMISSION_ACCESS_COURSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted,

                    locationHandler.initLocationManager();

                } else {

                    // permission denied,

                    //show user mesg that the app is now useless
                }
                return;
            }


        }
    }
}
