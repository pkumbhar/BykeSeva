package com.background;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Response;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Admin on 12-September-12-2016.
 */
public class checkappVersion extends AsyncTask<String,Void,String> {

    private Context mContext;
    private Handler mHandler;
    private String url="http://carreto.pt/tools/android-store-version/?package=com.vecta.nuclearwheels;";
    private JSONObject JbookData;

    public checkappVersion(Context mContext, Handler mHandler) {
        this.mHandler=mHandler;
        this.mContext=mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {
        String RETURN="";

try{
    URL url1=new URL(url);
    HttpURLConnection urlConnection=(HttpURLConnection)url1.openConnection();
    urlConnection.setRequestMethod("GET");
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
}catch (Exception e){
    e.printStackTrace();
}
  return RETURN;
    }
}
