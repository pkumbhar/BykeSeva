package com.background;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.requestUrl.ServerUrl;
import com.vecta.nuclearwheels.UserLogin;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Admin on 09-06-2016.
 */
public class DoUpatePassword extends AsyncTask<String,Void,String> {
    private Context mContext;
    private Handler mHandler;
    private String email="",newPassword;
    private ProgressDialog progressDialog;
    private int REQUEST_STATUES_SUCESS=200;
    private int REQUEST_STATUES_FAIL=100;
    private int requestCode;


    public DoUpatePassword(Context mContext, Handler mhandler, String email,String newPassword,int requestCode) {
        this.mContext=mContext;
        this.mHandler=mhandler;
        this.email=email;
        this.newPassword=newPassword;
        this.requestCode=requestCode;
    }

    @Override
    protected String doInBackground(String... params) {
        String RETURN = null;
        BufferedReader reader = null;
        BufferedInputStream ins=null;
        try {
            //http://192.168.0.102:8888/NuclearWheels/vectarestapi/User/savepassword?data1=pratim.kumbhar4@gmail.com&data2=124
            URL url=new URL(ServerUrl.serverurl+"User/savepassword?data1="+email+"&data2="+newPassword);
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            //  urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            //  urlConnection.setRequestProperty("Content-Type", "application/json");
            //    urlConnection.setRequestProperty("dataType", "json");
            urlConnection.connect();

            int status = urlConnection.getResponseCode();
            if(status==200){
                ins=new BufferedInputStream(urlConnection.getInputStream());
            }else if(status>400){
                ins=new BufferedInputStream(urlConnection.getErrorStream());
            }
            StringBuffer buffer=new StringBuffer();
            reader=new BufferedReader(new InputStreamReader(ins));
            String inputLine;
            while ((inputLine=reader.readLine())!=null){
                buffer.append(inputLine+"\n");
            }
            if(!buffer.toString().isEmpty()){
              int rtn=  handelInputStream(buffer.toString());
                if(rtn==200){
                    RETURN="200";
                }else if(rtn==403){
                    RETURN="403";
                }else if(rtn==500){
                    RETURN="500";
                }
            }

        } catch (Exception e) {
            progressDialog.dismiss();
            e.printStackTrace();
            RETURN="100";
        }
        progressDialog.dismiss();
        return RETURN;
    }
    /*
    handel inputStream
     */
    private int  handelInputStream(String ins){
        int rtn=0;
        try{
            JSONObject object=new JSONObject(ins);
            if(object.has("STATUS")){
                String stauts="";
                stauts=object.getString("STATUS");
                String responce="";
                if(stauts.equals("200")){
                    responce=object.getString("RESID");
                    UserLogin.RECIVED_PASSWORD=responce;
                    rtn=200;
                }else if(stauts.equals("403")){
                    responce=object.getString("RESID");
                    rtn=403;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            rtn=403;
        }
        return rtn;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i("BOOKING status",""+s);
        if(requestCode==1){
            try
            {
                if(s.equals("200")){
                    mHandler.obtainMessage(200).sendToTarget();
                }else if(s.equals("100")){
                    mHandler.obtainMessage(100).sendToTarget();
                }else if(s.equals("403")){
                    mHandler.obtainMessage(403).sendToTarget();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(requestCode==2){
            try
            {
                if(s.equals("200")){
                    mHandler.obtainMessage(200).sendToTarget();
                }else if(s.equals("100")){
                    mHandler.obtainMessage(100).sendToTarget();
                }else if(s.equals("403")){
                    mHandler.obtainMessage(403).sendToTarget();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
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
}
