package com.vecta.nuclearwheels;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidsupport.GPSTracker;
import com.background.DoServiceProviderDow;
import com.preferences_storage.SavePrefe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServiceType extends Activity implements View.OnClickListener {
    private Button generalService,repairing,emergencyAccident,insurancerenual;
    public  static int SHOWROOM_SUCESS_RESULT=200;
    public  static int SHOWROOMUN_SUCESS_RESULT=100;
    public  static int ADDRESS_SUCESS_RESULT=201;
    public  static int ADDRESS_UNSUCESS_RESULT=101;
    private GPSTracker gpsTracker;
    private String name="", postalCode="";
    private String pincode="";
    private  Button btn_insurance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_type);
        generalService=(Button)findViewById(R.id.btn_general_servicing_id);
        generalService.setOnClickListener(this);
        getvehicleModel();
        gpsTracker = new GPSTracker(ServiceType.this);
        btn_insurance=(Button)findViewById(R.id.btn_insurance_id);
        btn_insurance.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_general_servicing_id){
          // callToServicing();
            startActivity(new Intent(ServiceType.this,SubModel.class));
        }else if(v.getId()==R.id.btn_insurance_id){
            startActivity(new Intent(ServiceType.this,InsuranceBAJAJ.class));
        }
    }

    private String getvehicleModel(){
        //Call From the SubModels Class
        String name="";
        try{
            Bundle bundle=getIntent().getBundleExtra("MODELS");
            name=bundle.getString("MODELID");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  name;
    }
}
