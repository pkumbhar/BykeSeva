package com.background;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.requestUrl.ServerUrl;

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

/**
 * Created by Admin on 09-06-2016.
 */
public class DoBooking extends AsyncTask<String,Void,String> {
    private Context mContext;
    private Handler mHandler;
    private JSONObject JbookData;
    private ProgressDialog progressDialog;
    private int REQUEST_STATUES_SUCESS=200;
    private int REQUEST_STATUES_FAIL=100;


    public DoBooking(Context mContext, Handler mhandler, JSONObject jBook) {
        this.mContext=mContext;
        this.mHandler=mhandler;
        this.JbookData=jBook;
    }

    @Override
    protected String doInBackground(String... params) {
        String RETURN = null;
        BufferedReader reader = null;
        try {
            URL url=new URL(ServerUrl.serverurl+"booking/bookBikeGarageFromMobile");
            Log.i("DoBooking-0>",""+url.toString());
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            //urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("dataType", "json");
            urlConnection.connect();
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            Log.i("DATA"," "+JbookData.toString());
            writer.write(JbookData.toString());
            writer.close();
            int status = urlConnection.getResponseCode();
            if(status==200){
                RETURN="200";
            }else if(status>400){
                RETURN="100";
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            e.printStackTrace();
            RETURN="100";
        }
        progressDialog.dismiss();
        return RETURN;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i("BOOKING status",""+s);
        if(s.equals("200")){
            mHandler.obtainMessage(200).sendToTarget();
        }else if(s.equals("100")){
            mHandler.obtainMessage(100).sendToTarget();
        }
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(mContext);
        progressDialog.setTitle(" Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    /*
    process User
    Data
     */
    private int  handelUserLogin(String jsonObject){

      return  0;
    }
}
