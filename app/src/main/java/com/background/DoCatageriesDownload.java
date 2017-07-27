package com.background;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.TextView;

import com.db_adapter.DBAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.listAdapters.AccessoriesBeen.ProductBrandBeen;
import com.listAdapters.AccessoriesBeen.ProductCatageoryBeen;
import com.listAdapters.AccessoriesBeen.ProductSubCategoryBeen;
import com.requestUrl.ServerUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 25-January-25-2017.
 */

public class DoCatageriesDownload extends AsyncTask<String,Void,String> {
    private Context mContext;
    private Activity mActivity;
    private ProgressDialog progressDialog;
    private Handler handler;

    public DoCatageriesDownload(Context mContext, Activity mActivity, ProgressDialog progressDialog,Handler handler) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.progressDialog = progressDialog;
        this.handler=handler;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
    @Override
    protected String doInBackground(String... params) {
        InputStream stream=null;
        StringBuilder builder=new StringBuilder();
        try{
            URL url=new URL(ServerUrl.serverurl.concat("accessories/allCSBTree"));
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
           // urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            //urlConnection.setRequestProperty("dataType", "json");
            urlConnection.connect();
            int resCode=urlConnection.getResponseCode();
            if(resCode==200){
                 stream=urlConnection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(stream));
                String line="";
                while ((line=reader.readLine())!=null){
                    builder.append(line+"\n");
                }
                handelCategoried(builder.toString());

            }else if(resCode>400){

                stream=urlConnection.getErrorStream();
            }

        }catch (Exception e){
            progressDialog.show();
            e.printStackTrace();
        }
        progressDialog.show();
        return "";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading Items");
        progressDialog.show();
    }
    private String handelCategoried(String stream){
        List<ProductBrandBeen> productBrandList=new ArrayList<ProductBrandBeen>();
        List<ProductCatageoryBeen> productCatageoryList=new ArrayList<ProductCatageoryBeen>();
        List<ProductSubCategoryBeen> productSubCategoryList=new ArrayList<ProductSubCategoryBeen>();
        DBAdapter adapter=new DBAdapter(mContext);
        if (stream.isEmpty()){

        }else {
            try{
                JSONArray jsonArray=new JSONArray(stream);
                if(jsonArray.length()>0){
                    for(int a=0;a<jsonArray.length();a++){
                        JSONObject jsonObject=jsonArray.getJSONObject(a);
                        if(jsonObject.length()>0) {
                            ProductCatageoryBeen prodectCB=new ProductCatageoryBeen();
                            prodectCB.setID(jsonObject.getString("ID"));
                            prodectCB.setNAME(jsonObject.getString("NAME"));
                            productCatageoryList.add(prodectCB);
                            try{
                                adapter.insertIntoProductCategory(prodectCB);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            JSONArray jsubcat = jsonObject.getJSONArray("SUBCAT");
                            if (jsubcat.length() > 0) {
                            for (int sb = 0; sb < jsubcat.length(); sb++) {
                                JSONObject subCatagery = jsubcat.getJSONObject(sb);
                                if(subCatagery.length()>0){
                                    ProductSubCategoryBeen subCatB=new ProductSubCategoryBeen();
                                    subCatB.setID(subCatagery.getString("ID"));
                                    subCatB.setSUB_CATEGORY_NAME(subCatagery.getString("NAME"));
                                    subCatB.setCATEGORY_ID(subCatagery.getString("CID"));
                                    try{
                                        adapter.insertIntoProductSubCategory(subCatB);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    JSONArray jrryBrand = subCatagery.getJSONArray("BRAND");
                                    if (jrryBrand.length() > 0) {
                                        for (int b=0;b<jrryBrand.length();b++){
                                            JSONObject jBrand=jrryBrand.getJSONObject(b);
                                            if(jBrand.length()>0){
                                                ProductBrandBeen brBn=new ProductBrandBeen();
                                                brBn.setID(jBrand.getString("ID"));
                                                brBn.setNAME(jBrand.getString("NAME"));
                                                brBn.setPRODUCT_SUB_CATEGORY_ID(jBrand.getString("SCID"));
                                                try{
                                                    adapter.insertIntoProdectBrand(brBn);
                                                }catch (Exception e){
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                       }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();

            }
        }
        return "";
    }
}
