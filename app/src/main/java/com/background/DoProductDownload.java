package com.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import com.abstractsupport.ProductProcessable;
import com.listAdapters.AccessoriesBeen.AccessoriesBeen;
import com.listAdapters.bikeAdapters.AdapterProduct;
import com.requestUrl.ServerUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Admin on 31-January-31-2017.
 */

public class DoProductDownload extends AsyncTask<String,Void,String> implements ProductProcessable {

    private Context mContext;
    private Activity mActivity;
    private Handler mHandler;
    private ProgressDialog progressDialog;
    private String caterotyType="";


    public DoProductDownload(Context mContext, Activity mActivity, Handler mHandler, String caterotyType) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mHandler = mHandler;

        this.caterotyType = caterotyType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(mActivity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Items");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        URL url=null;
        HttpURLConnection urlConnection=null;
        String line="";
        StringBuilder builder=null;
        try{
            if(caterotyType.equalsIgnoreCase("Default")){
                 url=new URL(ServerUrl.serverurl+"accessories/topAccessories");
            }else{
                url=new URL(ServerUrl.serverurl+"accessories/subcategoriesAccessories?categories="+caterotyType);
            }
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("content-type","application/json");
            urlConnection.setRequestProperty("dataType","json");
            urlConnection.connect();

        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            int rsCode=urlConnection.getResponseCode();
            if(rsCode==200){
                InputStream stream=urlConnection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(stream));
                while((line=reader.readLine())!=null){
                    builder.append(line);
                }
            }else if(rsCode>400){
                InputStream stream=urlConnection.getErrorStream();
                progressDialog.dismiss();

            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return builder.toString();
    }

    @Override
    protected void onPostExecute(String stream) {
        super.onPostExecute(stream);
        if(stream!=null){
            processAccessories(stream);
        }
    }

    @Override
    public String processDefaultCategory(InputStream inputStream) {
        return null;
    }

    @Override
    public String processAccessories(String inputStream) {

        if(inputStream.toString().isEmpty()){

        }else{
            try{
                JSONArray jsonArray=new JSONArray(inputStream.toString());
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    if(jsonObject!=null){
                        if(jsonObject.length()>0){
                            AccessoriesBeen asbin=new AccessoriesBeen();
                            asbin.setID(jsonObject.getString("ID"));
                            asbin.setPTYPE(jsonObject.getString("PTYPE"));
                            asbin.setCATEGORIESSUBNAME(jsonObject.getString("CATEGORIESSUBNAME"));
                            asbin.setVAT(jsonObject.getString("VAT"));
                            asbin.setPRESENT(jsonObject.getString("PRESENT"));
                            asbin.setMRP(jsonObject.getString("MRP"));
                            asbin.setCLOSE(jsonObject.getString("CLOSE"));
                            asbin.setPRODUCTID(jsonObject.getString("PRODUCTID"));
                            asbin.setLIVEOFFERS(jsonObject.getString("LIVEOFFERS"));
                            JSONArray delArray=jsonObject.getJSONArray("DELIVERY");
                            if(delArray!=null){
                                for(int a=0;a<delArray.length();a++){
                                    JSONObject delvryObj=delArray.getJSONObject(a);
                                    if(delvryObj.length()>0){
                                        asbin.setDelveryPROCESS(delvryObj.getString("PROCESS"));
                                        asbin.setDelveryPRESENT(delvryObj.getString("PRESENT"));
                                        asbin.setDelveryID(delvryObj.getString("ID"));
                                        asbin.setDelveryPINS(delvryObj.getString("PINS"));
                                        asbin.setDelveryCHARGE(delvryObj.getString("CHARGE"));
                                        asbin.setDelveryDESC(delvryObj.getString("DESC"));
                                        asbin.setDelveryCHARGEBLE(delvryObj.getString("CHARGEBLE"));                                    }

                                }
                            }


                            asbin.setCATEGORIESNAME(jsonObject.getString("CATEGORIESNAME"));
                            JSONArray ofrJarry=jsonObject.getJSONArray("OFFERES");
                            if(ofrJarry!=null){
                                if(ofrJarry.length()>0){
                                    for(int a=0;a<ofrJarry.length();a++ ){
                                        JSONObject ofrObject=ofrJarry.getJSONObject(a);
                                        asbin.setOfrLIMITED(ofrObject.getString("LIMITED"));
                                        asbin.setOfrSDATE(ofrObject.getString("SDATE"));
                                        asbin.setOfrID(ofrObject.getString("ID"));
                                        asbin.setOfrCLOSE(ofrObject.getString("CLOSE"));
                                        asbin.setOfrOFFERS(ofrObject.getString("OFFERS"));
                                        asbin.setOfrendEDATE(ofrObject.getString("EDATE"));
                                        asbin.setOfrDISCOUNT(ofrObject.getString("DISCOUNT"));
                                    }
                                }
                            }



                            asbin.setSTATUS(jsonObject.getString("STATUS"));
                            asbin.setDESCRIPTION(jsonObject.getString("DESCRIPTION"));
                            asbin.setCOLOR(jsonObject.getString("COLOR"));
                            asbin.setID(jsonObject.getString("ID"));
                            asbin.setDOE(jsonObject.getString("DOE"));
                            asbin.setWEIGHT(jsonObject.getString("WEIGHT"));

                            JSONArray posterary=jsonObject.getJSONArray("POSTERS");
                            StringBuilder poster=new StringBuilder();
                            for(int p=0;p<posterary.length();p++){
                                poster.append((String)posterary.get(p)).append(";");

                            }

                            asbin.setType(jsonObject.getString("TYPE"));
                            asbin.setPUSEDFOR(jsonObject.getString("PUSEDFOR"));
                            asbin.setPRODUCTNAME(jsonObject.getString("PRODUCTNAME"));

                            JSONObject usrobj=jsonObject.getJSONObject("USER");
                            if(usrobj!=null){
                                asbin.setUid(usrobj.getString("UID"));
                                asbin.setUidrmdl(usrobj.getString("ID"));
                            }
                            asbin.setUPRICE(jsonObject.getString("UPRICE"));
                            asbin.setNAME(jsonObject.getString("NAME"));
                            asbin.setDISCOUNT(jsonObject.getString("DISCOUNT"));
                            asbin.setBRAND(jsonObject.getString("BRAND"));
                            asbin.setTPRICE(jsonObject.getString("TPRICE"));
                            asbin.setUNAME(jsonObject.getString("UNAME"));
                            asbin.setVEHICLEUSED(jsonObject.getString("VEHICLEUSED"));
                            asbin.setWARRANTY(jsonObject.getString("WARRANTY"));
                            asbin.setVERSION(jsonObject.getString("VERSION"));
                            asbin.setMANU(jsonObject.getString("MANU"));
                            JSONArray fetarryObj=jsonObject.getJSONArray("FEATURE");

                            for(int f=0;f<fetarryObj.length();f++){
                                JSONObject fetrobj=fetarryObj.getJSONObject(f);
                                asbin.setFetrFEATURE(fetrobj.getString("FEATURE"));
                                asbin.setFetrID(fetrobj.getString("ID"));
                                asbin.setTetrTYPE(fetrobj.getString("TYPE"));
                            }
                            JSONArray revArry=jsonObject.getJSONArray("REVIEW");
                            if(revArry.length()>0){
                                for(int r=0;r<revArry.length();r++){
                                    JSONObject revJobj=revArry.getJSONObject(r);
                                }


                            }

                        }
                    }
                }

            }catch (JSONException e){
                e.printStackTrace();
            }

        }
        return null;
    }
}
