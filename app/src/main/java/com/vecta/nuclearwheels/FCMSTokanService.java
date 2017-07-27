package com.vecta.nuclearwheels;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FCMSTokanService extends FirebaseInstanceIdService {
private Context mActivity;
    public FCMSTokanService( ) {

    }


    @Override
    public void onCreate() {
        try{
            String CurrentToken = FirebaseInstanceId.getInstance().getToken();
            Log.i("FCM KEY______________"," "+CurrentToken);
        }catch (Exception  e){
            e.printStackTrace();
        }

        super.onCreate();
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        try{
            String CurrentToken = FirebaseInstanceId.getInstance().getToken();
            Log.i("FCM KEY______________"," "+CurrentToken);
          //  sendRegistrationToServer(refreshedToken);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
