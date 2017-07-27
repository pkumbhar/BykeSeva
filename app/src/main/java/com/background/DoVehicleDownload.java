package com.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.db_adapter.DBAdapter;
import com.interfaces.Listprocessable;
import com.listAdapters.bikeBeens.BrandBeen;
import com.listAdapters.bikeBeens.ShowroomBeens;
import com.listAdapters.bikeBeens.VehicleModelBeens;
import com.requestUrl.ServerUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Admin on 06-06-2016.
 */
public class DoVehicleDownload extends AsyncTask<String,Void,String>  implements Listprocessable{

    private Context mContext;
    private Activity mActivity;
    private String vehicletype="";
    private ProgressDialog progressDialog;
    private BufferedReader reader;
    private Handler mHandler;
    int ACTIVE_BUTTON=0;

    public DoVehicleDownload(Activity mActivity, String vehicle_Type, Handler mHandler,int ACTIVE_BUTTON) {
        this.mActivity=mActivity;
        this.vehicletype=vehicle_Type;
        this.mHandler=mHandler;
        this.ACTIVE_BUTTON=ACTIVE_BUTTON;
    }

    @Override
    protected String doInBackground(String... params) {
        String result="";
        try {
            Log.i("REQUESTING---->>",'"'+vehicletype);
            URL url=new URL(ServerUrl.serverurl+"vehicle/brandByTypeByMobile");
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
          //  urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Content-Type", "application/json");
           // urlConnection.setRequestProperty("dataType", "json");
            urlConnection.connect();
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            writer.write(vehicletype);
            writer.close();
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
                int a=getVehicleList(buffer.toString(),vehicletype);
                if(a==2){
                    result="2";
                }else if(a==4){
                    result="4";
                }
            }
            progressDialog.dismiss();
        } catch (Exception e) {
            progressDialog.dismiss();
            result="0";
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(ACTIVE_BUTTON==1){
            if(s.equals("2")){
                mHandler.obtainMessage(2).sendToTarget();
            }else if(s.equals("4")){
                mHandler.obtainMessage(4).sendToTarget();
            }else if(s.equals("0")){
                mHandler.obtainMessage(0).sendToTarget();
            }
        }else if(ACTIVE_BUTTON==2){
            if(s.equals("2")){
                mHandler.obtainMessage(2).sendToTarget();
            }else if(s.equals("4")){
                mHandler.obtainMessage(4).sendToTarget();
            }else if(s.equals("0")){
                mHandler.obtainMessage(0).sendToTarget();
            }
        }else if(ACTIVE_BUTTON==3){
            if(s.equals("2")){
                mHandler.obtainMessage(2).sendToTarget();
            }else if(s.equals("4")){
                mHandler.obtainMessage(4).sendToTarget();
            }else if(s.equals("0")){
                mHandler.obtainMessage(0).sendToTarget();
            }
        }else if (ACTIVE_BUTTON==4){
            if(s.equals("2")){
                mHandler.obtainMessage(22).sendToTarget();
            }else if(s.equals("4")){
                mHandler.obtainMessage(44).sendToTarget();
            }else if(s.equals("0")){
                mHandler.obtainMessage(0).sendToTarget();
            }
        }


    }

    @Override
    protected void onPreExecute() {
        progressDialog=new ProgressDialog(mActivity);
        progressDialog.setTitle("Please Wait ..!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        super.onPreExecute();
    }
    @Override
    public int getVehicleList(String inputstream, String vehicletype ) {
        int a=0;
        ArrayList<BrandBeen> beens=new ArrayList<BrandBeen>();
        if(!inputstream.isEmpty()){
            try{
                JSONArray  jbikes=new JSONArray(inputstream);
                for(int i=0;i<jbikes.length();i++){
                    JSONObject joBikes=new JSONObject();
                    joBikes=jbikes.getJSONObject(i);
                        BrandBeen brands=new BrandBeen();
                        brands.setBrandName(joBikes.getString("NAME"));
                        brands.setBrandId(joBikes.getString("ID"));
                        brands.setBrandType(joBikes.getString("TYPE"));
                        beens.add(brands);
                }
                DBAdapter dbAdapter;
                dbAdapter=new DBAdapter(mActivity);
                dbAdapter.insertIntoVehicle(beens);
            }catch(JSONException e){
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
       if (vehicletype.equals("two")){
           a=2;
       }else if (vehicletype.equals("four")){
           a=4;
       }
        return a;
    }
    @Override
    public ArrayList<VehicleModelBeens> getVehicleSubmodel(String inputstream) {
        return null;
    }

    @Override
    public ArrayList<ShowroomBeens> getServiceCenterList(String inputstream) {
        return null;
    }

    @Override
    public boolean handelServicecenterData(String inputStream,Context mContext) {
        return false;
    }
}
