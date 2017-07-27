package com.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.requestUrl.ServerUrl;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Admin on 08-February-8-2017.
 */

public class DoConfurmOrder extends AsyncTask<String,Void,String> {
    private Activity mActivity;
    private Context mContext;
    private JSONObject mJsonObject;
    private ProgressDialog progressDialog;

    public DoConfurmOrder(Activity mActivity, Context mContext, JSONObject mJsonObject) {
        this.mActivity = mActivity;
        this.mContext = mContext;
        this.mJsonObject = mJsonObject;
    }

    @Override
    protected String doInBackground(String... params) {
        String RETURN = null;
        BufferedReader reader = null;
        try {
            URL url=new URL(ServerUrl.serverurl+"accessories/bookAccessoriesOrderByModile");
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
            Log.i("DATA"," "+mJsonObject.toString());
            writer.write(mJsonObject.toString());
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
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(mActivity);
        progressDialog.setTitle(" Confurm order Process..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
