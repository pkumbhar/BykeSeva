package com.background;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.listAdapters.bikeBeens.HistoryBeen;
import com.listAdapters.bikeBeens.UserInfoBeen;
import com.requestUrl.ServerUrl;
import com.vecta.nuclearwheels.History;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Admin on 09-06-2016.
 */
public class DoHistoryDownload extends AsyncTask<String,Void,String> {
    private Context mContext;
    private Handler mHandler;
    private String userId="";
    private UserInfoBeen infoBeen;
    private ProgressDialog progressDialog;
    private int REQUEST_STATUES_SUCESS=200;
    private int REQUEST_STATUES_FAIL=100;
    private int LOGINTWO=3;
    private int USER_LOGIN=2;
    private int classname=0;



    public DoHistoryDownload(Context mContext, Handler mhandler, String userId) {
        this.mContext=mContext;
        this.mHandler=mhandler;
        this.userId=userId;

    }

    @Override
    protected String doInBackground(String... params) {
        String RETURN = null;
        BufferedReader reader = null;
        try {
            String urlm=ServerUrl.serverurl+"User/userHistory?ID="+userId;
            Log.i("HISTORY->",""+urlm);
            //URL url=new URL("http://192.168.0.102:8888/NuclearWheels/vectarestapi/User/userHistory?ID=V-USR-2016-1");
            URL url=new URL(urlm);
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
            int rs=handelUserHistory(buffer.toString());
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
                Log.i("Request from "," History...!");
                if(s.equals("200")){
                    Log.i("Request from ","200");
                    mHandler.obtainMessage(History.HISTORY_SUCESS).sendToTarget();
                }else if(s.equals("403")){
                    Log.i("Request from ","403");
                    mHandler.obtainMessage(History.HISTORY_UN_SUCESS).sendToTarget();
                }
                else if(s.equals("401")){
                    Log.i("Request from ","100");
                    mHandler.obtainMessage(History.HISTORY_BADREQUEST_SUCESS).sendToTarget();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            mHandler.obtainMessage(History.HISTORY_UN_SUCESS).sendToTarget();
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
    private int  handelUserHistory(String ins){
        History.historyBeens.clear();
        int rtn=0;
        UserInfoBeen  ubin=new UserInfoBeen();
        Log.i("RESPONCES,",""+ins);
        try{
            JSONObject object=new JSONObject(ins);
            if(object.length()>0){
                if (object.has("STATUS")){
                    if (object.getString("STATUS").equals("200")){
                        if (object.has("RESERVATION")) {
                            JSONArray reservationarry = object.getJSONArray("RESERVATION");
                            int len = reservationarry.length();
                            if (len > 0) {
                            for (int l = 0; l < len; l++) {
                                rtn = 200;
                                JSONObject reservation = new JSONObject(reservationarry.get(l).toString());
                                HistoryBeen historyBeen = new HistoryBeen();
                                try {
                                    if (l % 2 == 0) {
                                        historyBeen.setColordode("#509CBC");
                                    } else {
                                        historyBeen.setColordode("#2B8BB1");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    historyBeen.setColordode("#2B8BB1");
                                }

                                if (reservation.has("RDATE")) {
                                    historyBeen.setDate(reservation.getString("RDATE"));
                                }
                                if (reservation.has("STREET")) {
                                    historyBeen.setAddressstreat(reservation.getString("STREET"));
                                }
                                if (reservation.has("ES")) {
                                    historyBeen.setSercives(reservation.getString("ES"));
                                }
                                if (reservation.has("CITY")) {
                                    historyBeen.setCity(reservation.getString("CITY"));
                                }
                                if (reservation.has("PIN")) {
                                    historyBeen.setPin(reservation.getString("PIN"));
                                }
                                if (reservation.has("DATE")) {
                                    historyBeen.setDate(reservation.getString("DATE"));
                                }
                                if (reservation.has("VN")) {
                                    historyBeen.setVehicle(reservation.getString("VN"));
                                }
                                if (reservation.has("VMID")) {
                                    historyBeen.setVehicleName(reservation.getString("VMID"));
                                }//GNAME
                                if (reservation.has("SERVICE")) {

                                    JSONArray serviceArray = reservation.getJSONArray("SERVICE");
                                    if (serviceArray.length() > 0) {
                                        String services="";
                                        StringBuilder builder=new StringBuilder();
                                        for(int js=0;js<serviceArray.length();js++){
                                            JSONObject jserviceObject = serviceArray.getJSONObject(js);
                                            builder.append(jserviceObject.getString("NAME")).append("\n");
                                        }
                                        historyBeen.setServiceing(builder.toString());

                                    }
                                }
                                if (reservation.has("GNAME")) {
                                    historyBeen.setGarageName(reservation.getString("GNAME"));
                                }

                                History.historyBeens.add(historyBeen);
                            }
                        }else {
                                rtn=403;
                            }
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
      return  rtn;
    }
}
