package com.background;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.db_adapter.DBAdapter;
import com.listAdapters.bikeBeens.UserInfoBeen;
import com.requestUrl.ServerUrl;
import com.vecta.nuclearwheels.LoginTwo;
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
public class DoLogin extends AsyncTask<String,Void,String> {
    private Context mContext;
    private Handler mHandler;
    private String userName="";
    private String password="";
    private UserInfoBeen infoBeen;
    private ProgressDialog progressDialog;
    private int REQUEST_STATUES_SUCESS=200;
    private int REQUEST_STATUES_FAIL=100;
    private int LOGINTWO=3;
    private int USER_LOGIN=2;
    private int classname=0;



    public DoLogin(Context mContext, Handler mhandler,UserInfoBeen infoBeen ,int classname) {
        this.mContext=mContext;
        this.mHandler=mhandler;
        this.infoBeen=infoBeen;
        this.classname=classname;
    }

    @Override
    protected String doInBackground(String... params) {
        String RETURN = null;
        BufferedReader reader = null;
        try {
            URL url=new URL(ServerUrl.serverurl+"User/loginByMobile?USERNAME="+infoBeen.getEmail().trim()+"&PASSWORD="+infoBeen.getPassword().trim());
            Log.i("LOGIN_URL->"," "+url.toString());
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
           // urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("dataType", "json");
            urlConnection.connect();
           /* Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            writer.write(JsonDATA);
            writer.close();
            // json data
            //writer.close();*/
            int status = urlConnection.getResponseCode();
            Log.i("Login Status"," "+status);
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
            int rs=handelUserLogin(buffer.toString());
            if(rs==200){
                RETURN="200";
            }if(rs==403){
                RETURN="403";
            }if(rs==400){
                RETURN="400";
            }if(rs==404){
                RETURN="404";
            }if(rs==401){
                RETURN="401";
            }if(rs==405){
                RETURN="405";
            }

        } catch (Exception e) {
            progressDialog.dismiss();
            e.printStackTrace();
        }
        progressDialog.dismiss();
        return RETURN;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i("Request--->>>>>>>>>>>> ",""+s);
        try{


        if(!s.isEmpty()){
            if(classname==USER_LOGIN){
                Log.i("Request from "," USER_LOGIN");
                if(s.equals("200")){
                    Log.i("Request from ","200");
                    mHandler.obtainMessage(UserLogin.SERVER_SUSESS).sendToTarget();
                }else if(s.equals("100")){
                    Log.i("Request from ","100");
                    mHandler.obtainMessage(UserLogin.UNKNOWNEWERROR).sendToTarget();
                }else if(s.equals("403")){
                    Log.i("Request from ","403");
                    mHandler.obtainMessage(UserLogin.SERVER_RECORD_NOT_FOUND).sendToTarget();
                }else if(s.equals("401")){
                    Log.i("Request from ","401");
                    mHandler.obtainMessage(UserLogin.SERVER__SUCESS_ERROR).sendToTarget();
                }else if(s.equals("405")){

                }
            }else if(classname==LOGINTWO){
                Log.i("Request from "," LOGINTWO");
                if(s.equals("200")){
                    Log.i("Request from ","200");
                    mHandler.obtainMessage(LoginTwo.SERVER_SUSESS).sendToTarget();
                }else if(s.equals("100")){
                    Log.i("Request from ","100");
                    mHandler.obtainMessage(LoginTwo.UNKNOWNEWERROR).sendToTarget();
                }else if(s.equals("401")){
                    Log.i("Request from ","401");
                    mHandler.obtainMessage(LoginTwo.SERVER__SUCESS_ERROR).sendToTarget();
                }else if(s.equals("403")){
                    Log.i("Request from ","403");
                    mHandler.obtainMessage(LoginTwo.SERVER_RECORD_NOT_FOUND).sendToTarget();
                }else if(s.equals("405")){
                    Log.i("Request from ","405");
                    mHandler.obtainMessage(LoginTwo.UNKNOWNEWERROR).sendToTarget();
                }
            }
        }
        }catch (Exception e){
            e.printStackTrace();
            if(classname==LOGINTWO){
                mHandler.obtainMessage(LoginTwo.UNKNOWNEWERROR).sendToTarget();
            }else if(classname==USER_LOGIN){
                mHandler.obtainMessage(UserLogin.UNKNOWNEWERROR).sendToTarget();
            }
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(mContext);
        progressDialog.setTitle("Login Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    /*
    process User
    Data
     */
    private int  handelUserLogin(String ins){
        int rtn=0;
        UserInfoBeen  ubin=new UserInfoBeen();
        Log.i("RESPONCES,",""+ins);
        try{
            JSONObject object=new JSONObject(ins);
            if(object.length()>0){
                if (object.has("STATUS")){
                    if (object.getString("STATUS").equals("200")){
                        rtn=200;
                        if(object.has("DEVICE")){
                            ubin.setDevice_id(object.getString("DEVICE"));
                        }if(object.has("NAME")){

                            String name=object.getString("NAME");
                            try{
                                String parts[]=name.split("[*]]");
                                ubin.setFirstName(parts[0]);
                                ubin.setLastName(parts[0]);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }if(object.has("AREA")){
                            ubin.setArea(object.getString("AREA"));
                        }if(object.has("MOBILE")){
                            ubin.setContactNumber(object.getString("MOBILE"));
                        }if(object.has("ID")){
                            ubin.setServer_id(object.getString("ID"));
                        }if(object.has("EMAIL")){
                            ubin.setEmail(object.getString("EMAIL"));
                        }if(object.has("CITY")){
                            ubin.setCity(object.getString("CITY"));
                        }if(object.has("STATUS")){

                        }if(object.has("ADDLINE2")){
                            ubin.setAddress2(object.getString("ADDLINE2"));
                        }if(object.has("PASSWORD")){
                            ubin.setPassword(object.getString("PASSWORD"));
                        }if(object.has("ADDLINE1")){
                            ubin.setAddress1(object.getString("ADDLINE1"));
                        }
                    }
                    if (object.getString("STATUS").equals("403")){
                        rtn=403;
                    }
                    if (object.getString("STATUS").equals("400")){
                        rtn=400;
                    }
                    if (object.getString("STATUS").equals("404")){
                        rtn=404;
                    }
                    if (object.getString("STATUS").equals("401")){
                        rtn=401;
                    }
                    if (object.getString("STATUS").equals("405")){
                        rtn=405;
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        DBAdapter adapter=new DBAdapter(mContext);
        if(rtn==200){
            try{
                long l=0;
                l=adapter.insertIntoUserMaster(ubin);
                if(l>0){
                    Log.i("LOGIN SUCESSFULL"," UPDATED ROWS"+l);
                }
            }catch (Exception e){
                e.printStackTrace();
                rtn=100;
            }
        }
      return  rtn;
    }
}
