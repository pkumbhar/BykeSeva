package com.background;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.interfaces.Listprocessable;
import com.listAdapters.bikeBeens.ShowroomBeens;
import com.listAdapters.bikeBeens.VehicleModelBeens;
import com.requestUrl.ServerUrl;
import com.vecta.nuclearwheels.SubModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Admin on 06-06-2016.
 */
public class DoModelDownload extends AsyncTask<String,Void,String> implements Listprocessable {
    private Context mContext;
    private String vehicle_brand;
    private Handler mHandler;
    private BufferedReader reader;
    private ProgressDialog progressDialog;

    public DoModelDownload(Context mContext, String vehicle_brand, Handler mHandler) {
        this.mContext=mContext;
        this.vehicle_brand=vehicle_brand;
        this.mHandler=mHandler;
    }
    @Override
    protected String doInBackground(String... params) {
        String result="";
        try {
        URL url=new URL(ServerUrl.serverurl+"vehicle/modelByBrand?ID="+vehicle_brand.trim());
        HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
        urlConnection.setRequestMethod("GET");
         urlConnection.setDoInput(true);
     //   urlConnection.setDoOutput(true);
        urlConnection.setUseCaches(false);
        urlConnection.setRequestProperty("Content-Type", "application/json");
        // urlConnection.setRequestProperty("dataType", "json");
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
            if(!getVehicleSubmodel(buffer.toString()).isEmpty()){
                result="1";
            }else {
                result="0";
            }
        }
    } catch (Exception e) {
        progressDialog.dismiss();
        e.printStackTrace();
            result="0";
    }
        return result;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        if(!s.isEmpty()){
            if(s.equals("1")){
                mHandler.obtainMessage(SubModel.RESPONSE_SUCESS).sendToTarget();
            }else if(s.equals("0")){
                mHandler.obtainMessage(SubModel.RESPONSE_UNSUCESS).sendToTarget();
            }
        }
    }

    @Override
    protected void onPreExecute() {
        progressDialog=new ProgressDialog(mContext);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        super.onPreExecute();
    }
    @Override
    public int getVehicleList(String inputstream,String vehicle_brand) {


        return 0;
    }

    @Override
    public ArrayList<VehicleModelBeens> getVehicleSubmodel(String inputstream) {

        if(!inputstream.isEmpty()){
            SubModel.vModelList.clear();
            try{
                JSONArray jbikes=new JSONArray(inputstream);
                for(int i=0;i<jbikes.length();i++){
                    JSONObject joBikes=new JSONObject();
                    joBikes=jbikes.getJSONObject(i);
                    VehicleModelBeens brands=new VehicleModelBeens();
                    brands.setId(joBikes.getString("ID"));
                    brands.setBrand(joBikes.getString("BRAND"));
                    brands.setName(joBikes.getString("NAME"));
                    try {
                        if (i% 2 == 0) {
                            brands.setColorcode("#509CBC");
                        } else {
                            brands.setColorcode("#2B8BB1");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        brands.setColorcode("#2B8BB1");
                    }
                    SubModel.vModelList.add(brands);
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return SubModel.vModelList;
    }
    @Override
    public ArrayList<ShowroomBeens> getServiceCenterList(String inputstream) {
        return null;
    }

    @Override
    public boolean handelServicecenterData(String inputStream, Context mContext) {
        return false;
    }
}
