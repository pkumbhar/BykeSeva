package com.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.db_adapter.DBAdapter;
import com.listAdapters.bikeBeens.UserInfoBeen;
import com.requestUrl.ServerUrl;
import com.vecta.nuclearwheels.UserRegistration;

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
 * Created by Admin on 03-06-2016.
 */
public class Doregistration extends AsyncTask<String,Void,String> {


    private Activity mActivity;
    private Context mContext;
    private Handler mHandler;
    private ProgressDialog progressDialog;
    private int REQUEST_STATUES_SUCESS=1;
    private int REQUEST_STATUES_FAIL=0;
    private UserInfoBeen bin;


    public Doregistration(Activity mActivity,Context mContext,UserInfoBeen bin,Handler mHandler) {
        this.mActivity=mActivity;
        this.mContext=mContext;
        this.mHandler=mHandler;
        this.bin=bin;
    }


    @Override
    protected String doInBackground(String... params) {
        String RETURN = null;
        String JsonDATA = params[0];
        BufferedReader reader = null;
        try {
            URL url=new URL(ServerUrl.serverurl+"User/addNewUsers");
            Log.i("Doregistration-> ",""+url.toString());
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
           //urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("dataType", "json");
            urlConnection.connect();
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            writer.write(JsonDATA);
            writer.close();
            // json data
            //writer.close();
            int status = urlConnection.getResponseCode();
            Log.i("RESPONSE"," "+status);
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
            if(status==200){
               int result= vechileList(buffer.toString());
                if(result==200){
                    RETURN="200";
                }else if(result==404){
                    RETURN="404";
                }
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            e.printStackTrace();
        }
        progressDialog.dismiss();
        return RETURN;
    }
    /*
    returning list
     */
    public int  vechileList(String jarry){
       int i=0;
Log.i("RESPONSE",jarry);
      //  {"ID":"2016-USNM-032","STATUS":200}
        ArrayList<UserInfoBeen> list=new ArrayList<UserInfoBeen>();
        try{
            JSONObject no_of_values=new JSONObject(jarry);
            if(no_of_values.length()>0){
                Log.i("LENGTH---->>>", "" + no_of_values.length());
                UserRegistration.userdata.clear();
                String result=no_of_values.getString("STATUS");
                if(result.equals("404")){
                    i=404;
                }else if (result.equals("200")){
                    bin.setServer_id(no_of_values.getString("ID"));
                    UserRegistration.userdata.add(bin);
                    DBAdapter adapter=new DBAdapter(mContext);
                    adapter.insertIntoUserMaster(bin);
                    i=200;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
     return  i;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try{
            if(s.equals("200")){
                mHandler.obtainMessage(200).sendToTarget();
            }else if(s.equals("404")){
                mHandler.obtainMessage(404).sendToTarget();
            }else if(s.equals("401")){
                mHandler.obtainMessage(401).sendToTarget();
            }
        }catch (Exception e){
            e.printStackTrace();
            mHandler.obtainMessage(500).sendToTarget();
        }
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(mActivity);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }
}
