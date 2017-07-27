package com.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.abstractsupport.HandelServiceCenter;
import com.abstractsupport.ProcessServiceCenter;
import com.interfaces.Listprocessable;
import com.listAdapters.bikeBeens.BrandBeen;
import com.listAdapters.bikeBeens.ShowroomBeens;
import com.listAdapters.bikeBeens.VehicleModelBeens;
import com.requestUrl.ServerUrl;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Admin on 07-06-2016.
 */
public class DoServiceProviderDow extends AsyncTask<String,Void,String> {
    private Context mContext;
    private String vehicleType;
    private Handler mHandler;
    private BufferedReader reader;
    private String model="";
    private ProgressDialog progressDialog;
    private String postalcode="";
    private String area="";

    public DoServiceProviderDow(Context mContext,Handler mHandler,String model,String postalcode,String area,String vehicleType){
        this.mContext=mContext;
        this.mHandler=mHandler;
        this.model=model.trim();
       this.postalcode=postalcode;
        this.area=area.trim();
        this.vehicleType=vehicleType;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s.equals("1")){
                    mHandler.obtainMessage(200).sendToTarget();
        }else if(s.equals("0")){
            mHandler.obtainMessage(100).sendToTarget();
            Toast.makeText(mContext,"SERVICE PROVIDER NOT FOUND, ERROR_CODE 100",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String result="";
        try {
           URL url=new URL(ServerUrl.serverurl+"vehicle/garagesByPinNType?PIN="+postalcode+"&TYPE="+vehicleType);
        //   URL url=new URL(ServerUrl.serverurl+"vehicle/garagesByPinNType?PIN=411014&TYPE="+vehicleType);
            Log.i("DoServiceProviderDow",""+url.toString());


            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
//           urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Content-Type", "application/json");
//           urlConnection.setRequestProperty("dataType", "json");
         /*   Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            writer.write(object.toString());
            writer.close();*/
            urlConnection.connect();
            int status = urlConnection.getResponseCode();
            Log.i("ResponceCode"," "+status);
            BufferedInputStream in;
            if (status >= 400 ) {
                in = new BufferedInputStream( urlConnection.getErrorStream() );
                Log.i("",""+in);
            } else {
                in = new BufferedInputStream( urlConnection.getInputStream() );
                Log.i("",""+in);
            }
            //input stream
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(in));
            String inputLine;
            while ((inputLine = reader.readLine()) != null){
                buffer.append(inputLine + "\n");
            }
            if(!buffer.toString().isEmpty()){
                Log.i("Response",""+buffer.toString());
                ProcessServiceCenter serviceCenter=new ProcessServiceCenter();
               boolean a=  serviceCenter.handelServicecenterData(buffer.toString(),mContext);
                if(a==true){
                    result="1";
                }else if(a==false){
                    result="0";
                }
            }


        } catch (Exception e) {
            progressDialog.dismiss();
            e.printStackTrace();
            result="0";
        }
            progressDialog.dismiss();
        return result;
    }


    @Override
    protected void onPreExecute() {
        progressDialog=new ProgressDialog(mContext);
        progressDialog.setTitle("Please Wait.. ");
        progressDialog.setCancelable(false);
        progressDialog.show();
        super.onPreExecute();
    }
/*******************************************************************************************************
 * ****************************************************************************************
 ************************************************/

}
