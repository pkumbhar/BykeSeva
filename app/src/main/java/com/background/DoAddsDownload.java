package com.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.db_adapter.DBAdapter;
import com.listAdapters.bikeBeens.VehicleBuyBeen;
import com.listAdapters.bikeBeens.VehicleModelBeens;
import com.listAdapters.bikeBeens.VehicleOwnerBeen;
import com.requestUrl.ServerUrl;
import com.table_base.TableBase;

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
import java.util.Scanner;

/**
 * Created by Admin on 12-October-12-2016.
 */
public class DoAddsDownload  extends AsyncTask<String,Void,String>{
    private Activity mActivity;
    private Context mContext;
    private  String mtype;
    private ProgressDialog progressDialog;
    private Handler handler;

    public DoAddsDownload(Activity mActivity, Context mContext,String vehicleType,Handler mHandler) {
        this.mActivity=mActivity;
        this.mContext=mContext;
        this.mtype=vehicleType;
        this.handler=mHandler;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(mContext);
        progressDialog.setTitle("Looking Vehicles.");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    protected String doInBackground(String... params) {
        String RETURN="";
        BufferedInputStream inputStream=null;
        BufferedReader reader = null;
        try {

            URL url=new URL(ServerUrl.serverurl+"vehicle/loadUsedVehicles?ID="+mtype);
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();

            urlConnection.connect();
            int status = urlConnection.getResponseCode();
           if(status==200){
                inputStream=new BufferedInputStream(urlConnection.getInputStream());
                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String inputLine;
                while ((inputLine = reader.readLine()) != null){
                    buffer.append(inputLine + "\n");
                }
               Log.i("RES OK"," "+buffer.toString());

               if(handelVehicleData(buffer.toString())==1){
                   RETURN="202";
               }

            }else if(status>400){
                inputStream=new BufferedInputStream(urlConnection.getErrorStream());

                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String inputLine;
                while ((inputLine = reader.readLine()) != null){
                    buffer.append(inputLine + "\n");
                }
                Log.i("RES"," "+buffer.toString());
               RETURN="404";
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            e.printStackTrace();
            RETURN="100";
        }
        progressDialog.dismiss();
        return RETURN;
    }
    private int  handelVehicleData(String ins){
        int r=0;
        ArrayList<VehicleBuyBeen> vehicleModelBeenses=new ArrayList<VehicleBuyBeen>();
        ArrayList<VehicleOwnerBeen> vehicleOwnerBeens=new ArrayList<VehicleOwnerBeen>();
       if(!(ins.isEmpty())){
           try{
               JSONArray jsonArray=new JSONArray(ins);
               int l=jsonArray.length();
               if(l>0){
                   for(int i=0;i<l;i++){
                       VehicleBuyBeen vmb=new VehicleBuyBeen();
                       VehicleOwnerBeen oNb = new VehicleOwnerBeen();
                       JSONObject js= jsonArray.getJSONObject(i);
                       if(js.has("KM")){
                           vmb.setTOTAL_KM(js.getString("KM").toString());
                       }
                       if(js.has("PRICE")){
                           String[] parts;
                           String rate="-";
                           try{
                                parts=js.getString("PRICE").toString().split(",");
                               Log.i("PRICE__",""+parts);
                               int ln=parts.length;
                               for(int a=0;a<ln;a++){
                                   rate.contains(parts[a]);
                               }
                           }catch (Exception e){

                           }
                           vmb.setYOUR_PRICE(rate);
                       } if(js.has("POST")){
                           vmb.setPOSTDATE(js.getString("POST").toString());
                       } if(js.has("ONAME")){
                           oNb.setNAME(js.getString("ONAME").toString());
                       }if(js.has("EXPIRY")){
                              vmb.setINSURANCE_EXPIRE(js.getString("EXPIRY").toString());
                       } if(js.has("OID")){
                           String ownerId=js.getString("OID").toString();
                           oNb.setOWNERID(ownerId);
                           vmb.setOWNER_ID(ownerId);
                       } if(js.has("MODEL")){
                           vmb.setMODEL_NAME(js.getString("MODEL").toString());
                       }if(js.has("OEM")){
                           oNb.setEMAIL(js.getString("OEM").toString());
                       }
                       if(js.has("DESC")){
                           vmb.setDESCRIPTION(js.getString("DESC").toString());
                       } if(js.has("ONUM")){
                            oNb.setNUMBER(js.getString("ONUM").toString());
                       } if(js.has("CITY")){
                           vmb.setVEHICLE_CITY(js.getString("CITY").toString());
                       } if(js.has("REGNO")){
                           vmb.setVEHICLE_REG_NO(js.getString("REGNO").toString());
                       } if(js.has("NOW")){
                           vmb.setONUM(js.getString("NOW").toString());
                       } if(js.has("LINK")){
                           //vmb.setLINK(js.getString("LINK").toString());
                           JSONArray linkArray=new JSONArray(js.getString("LINK").toString());
                           StringBuilder builder=new StringBuilder();
                           for(int a=0;a<linkArray.length();a++) {
                               String object = (String) linkArray.get(a);
                               builder.append(object).append(",");
                           }
                           vmb.setLINK(builder.toString());
                       }if(js.has("TITLE")){
                           vmb.setBRAND_NAME(js.getString("TITLE").toString());
                       }if(js.has("FUEL")){
                           vmb.setFUEL_TYPE(js.getString("FUEL").toString());
                       }if(js.has("YEAR")){
                           vmb.setYEAR_OF_MODEL(js.getString("YEAR").toString());
                       }if(js.has("AREA")){
                           vmb.setVEHICLE_LOCATION(js.getString("AREA").toString());
                       }
                       vehicleModelBeenses.add(vmb);
                       vehicleOwnerBeens.add(oNb);

                       if(!vehicleModelBeenses.isEmpty()){
                           DBAdapter adapter=new DBAdapter(mContext);//FUEL//REGNO
                           try{
                               adapter.deleteTable(TableBase.Table.OWNER_VEHICLE_INFO.OWNER_VEHICLE_INFO);
                               adapter.deleteTable(TableBase.Table.OWNER.OWNER);
                               adapter.insertIntoVehicle_info(vehicleModelBeenses);
                               adapter.insertIntoOwner(vehicleOwnerBeens);
                               r=1;
                           }catch (Exception e){
                               e.printStackTrace();
                               r=0;
                           }
                       }

                   }
                   /*if(!vehicleOwnerBeens.isEmpty()){
                       DBAdapter adapter=new DBAdapter(mContext);
                       try{
                           adapter.insertIntoVehicle_info(vehicleModelBeenses);
                           adapter.insertIntoOwner(vehicleOwnerBeens);
                       }catch (Exception e){
                           e.printStackTrace();
                       }
                   }*/
               }else{
                   Log.i("DATA IS NO IN ARRAY@@@"," ");
               }
           }catch (JSONException e){
               e.printStackTrace();
               r=0;
           }
       }
        return  r;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
       if (!s.isEmpty()){
            if(s.equals("202")){
                handler.obtainMessage(202).sendToTarget();
            }else if(s.equals("404")){
                handler.obtainMessage(404).sendToTarget();
            }
        }
    }
}
