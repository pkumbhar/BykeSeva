package com.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.db_adapter.DBAdapter;
import com.listAdapters.bikeBeens.UserInfoBeen;
import com.requestUrl.ServerUrl;
import com.vecta.nuclearwheels.UserLogin;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Priyaram Kumbhar on 15-04-2016.
 */
public class EmailCheck extends AsyncTask<String,Void,String> {

    private String email="email";
    private  Activity mActivity=null;
    private String METHOD_AND_APIKEY= "user.valid?API=VECTA-555-NU-666";
    private String RETURN="";
    private BufferedReader reader=null;
    private  StringBuffer buffer = new StringBuffer();
    private ProgressDialog progressDialog=null;
    private String RESPONSE_STATUES_SUCESS="200";
    private String  RESPONSE_RECORD_NOT_FOUND="201";
    private String RESPONSE_UNKNOWN_ERROE="202";
    private String TAG="EmailCheck";
    private Handler mHandler;
    public EmailCheck(Activity mActivity, String email, Handler mHandler){
        this.email=email;
        this.mActivity=mActivity;
        this.mHandler=mHandler;
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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (!s.isEmpty()) {
            sendresponse(s);
        }
    }
    private void sendresponse(String s){
        //send msg to Login Activity
            if(s.equals(RESPONSE_STATUES_SUCESS)){
                Log.i(TAG,"Data inserted in server database");
                mHandler.obtainMessage(UserLogin.SERVER_SUSESS).sendToTarget();
            }else if(s.equals(RESPONSE_RECORD_NOT_FOUND)){
                Log.i(TAG, "Record Not fount at server database");
                mHandler.obtainMessage(UserLogin.SERVER_RECORD_NOT_FOUND).sendToTarget();
            }else if(s.equals(RESPONSE_UNKNOWN_ERROE)){
                mHandler.obtainMessage(UserLogin.UNKNOWNEWERROR).sendToTarget();
     }
    }
    @Override
    protected String doInBackground(String... params) {
        String response="";
        String result="";
        JSONObject data=new JSONObject();
        DBAdapter adapter=new DBAdapter(mActivity);
        try {

            URL url=new URL(ServerUrl.serverurl+METHOD_AND_APIKEY+"&EMAIL="+email);
            Log.i("URL+", " " + url.toString());
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.connect();
            RETURN =urlConnection.getResponseMessage();
            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String inputLine;
            while ((inputLine = reader.readLine()) != null){
                buffer.append(inputLine + "\n");
            }
            System.out.print("Res------->>>" + buffer.toString());
            progressDialog.dismiss();
        } catch (Exception e) {
            progressDialog.dismiss();
            e.printStackTrace();
            result =RESPONSE_UNKNOWN_ERROE;;//
        }
        long l=0;
        try{
            JSONArray array=new JSONArray(buffer.toString());
            UserInfoBeen uData=new UserInfoBeen();
            if((array!=null)&&(array.length()>=0)){
                for(int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    if( object.has("STATUS")){
                        if(object.getString("STATUS").equals("200")){//Sucess
                            if(object.has("FNAME")){
                                uData.setFirstName(object.getString("FNAME"));
                                uData.setLastName(object.getString("LNAME"));                                                             //DEVICE":"000000000000000",
                                                                                       // "FNAME":"pratim","CLOSE":false,
                                uData.setEmail(object.getString("EMAIL"));                                                                     //"SIM":"89014103211118510720",
                                uData.setContactNumber(object.getString("NUMBER"));                                             //  "ID":"V-USR-2016-58",
                                uData.setLat_long(object.getString("LOC"));                                                                      // "EMAIL":"pratim@gmail.com",
                                uData.setPassword(object.getString("PASSWORD"));                                                 //   "PASSWORD":"123",
                                uData.setServer_id(object.getString("ID"));                                                               // "NUMBER":"1234567890",
                                uData.setDevice_id(object.getString("DEVICE"));                                                               //  "LOC":"0101010101"}]
                                if(uData!=null){
                                    l=adapter.insertIntoUserMaster(uData);
                                    adapter.close();
                                    if(l>0){
                                        result= RESPONSE_STATUES_SUCESS;
                                    }else {
                                        result =RESPONSE_UNKNOWN_ERROE;
                                    }
                                }
                            }
                        }else if(object.getString("STATUS").equals("201")){//unSucess
                            result=RESPONSE_RECORD_NOT_FOUND;

                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
