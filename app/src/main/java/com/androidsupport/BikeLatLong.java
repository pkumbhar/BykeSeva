package com.androidsupport;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;

/**
 * Created by Admin on 22-06-2016.
 */
public class BikeLatLong {

    public static  double longitude(Activity activity) {
        double longitude = 00.0f;
        LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if ((manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) || (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
            GPSTracker gpsTracker = new GPSTracker(activity);
            longitude = gpsTracker.getLongitude();
        }
        return longitude;
    }

    /*
    #return longitude of current location
     */
    public  static  double latitude(Activity activity) {
        double latitude = 00.0f;
        LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if ((manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) || (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
            GPSTracker gpsTracker = new GPSTracker(activity);
            latitude = gpsTracker.getLatitude();
        }
        return latitude;
    }
}
