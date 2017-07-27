package com.vecta.nuclearwheels;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.Preference;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidsupport.AndroidNetworkSupport;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.db_adapter.DBAdapter;
import com.google.gson.Gson;
import com.listAdapters.bikeAdapters.ServiceCenterListAdapter;
import com.listAdapters.bikeAdapters.VehicleMarktAdapter;
import com.listAdapters.bikeBeens.VehicleBuyBeen;
import com.listAdapters.bikeBeens.VehicleOwnerBeen;
import com.preferences_storage.SavePrefe;
import com.requestUrl.ServerUrl;
import com.table_base.TableBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class VehicleMarket extends AppCompatActivity {
private RecyclerView recyclerView;
    private VehicleMarktAdapter marktAdapter;
    private FloatingActionButton actionButton;
    public ImageView imgReload;
    public ProgressBar progressBar;
    public static  int INT_ALL_KEY=1;
    public static int INT_BM_KEY=2;
    public static int INT_BMY_KEY=3;
    public static int INT_BMYP_KEY=4;
    public static int INT_BMYPF_KEY=5;
    public static int INT_BY_KEY=6;
    public static int INT_BYP_KEY=7;
    public static int INT_BYPF_KEY=8;
    public static int INT_BP_KEY=9;
    public static int INT_BPF_KEY=10;
    public static int INT_MODEL_KEY=11;
    public static int INT_PRICE_KEY=12;
    public static int INT_FUEL_KEY=13;
    public static int INT_MODEL_YEAR_PRICE_FUEL_KEY=14;
    public static int INT_MODEL_YEAR_PRICE__KEY=15;
    public static int INT_MODEL_YEAR_FUEL_KEY=16;
    public static int INT_PRICE_FUEL_KEY=17;
    private String VEHICLETYPE="";
    private  ArrayList<VehicleBuyBeen> vehicleModelClone=new ArrayList<VehicleBuyBeen>();
    private ArrayList<VehicleBuyBeen> vehicleModelBeenses=new ArrayList<VehicleBuyBeen>();
    private LinearLayout linRefresh;
    private int START=1;
    private int LOAD=2;
    private int RESTART=3;
    private Button btn_Refresh;
    private Spinner sp_City;
    private String TAG="VehicleMarket";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_filter);
        actionButton=(FloatingActionButton)findViewById(R.id.fab);
        imgReload=(ImageView)findViewById(R.id.img_relodId);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_vehicleMarket_id);
        progressBar=(ProgressBar)findViewById(R.id.progrss_vehicleMarket_id);
        linRefresh=(LinearLayout)findViewById(R.id.lin_refresh_id);
        btn_Refresh=(Button)findViewById(R.id.btn_refreshID);
        linRefresh.setVisibility(View.GONE);
        sp_City=(Spinner)findViewById(R.id.sp_filterCity);
        recyclerView.setVisibility(View.VISIBLE);
       doListReady(null,START);
        setCitySpinner();


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VehicleMarket.this,FilterationOfVehicle.class));
            }
        });
        imgReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworkSupport  support=new AndroidNetworkSupport();
                if(support.isConnected(VehicleMarket.this)){

                    linRefresh.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    marktAdapter.clearData();
                    new LoadDynamicData(VEHICLETYPE,(String)sp_City.getSelectedItem()).execute("");
                }else{
                    support.connectToNetWork(VehicleMarket.this);
                }
            }
        });
        btn_Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidNetworkSupport  support=new AndroidNetworkSupport();
                if(support.isConnected(VehicleMarket.this)){
                    linRefresh.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.removeAllViews();
                    new LoadDynamicData(VEHICLETYPE,(String)sp_City.getSelectedItem()).execute("");
                }else{
                    support.connectToNetWork(VehicleMarket.this);
                }
            }
        });
        sp_City.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                AndroidNetworkSupport  support=new AndroidNetworkSupport();
                if(support.isConnected(VehicleMarket.this)){
                    try {
                        SavePrefe prefe=new SavePrefe();
                        VEHICLETYPE=prefe.getVehicleType(VehicleMarket.this);
                        synchronized (this){
                            try{
                               marktAdapter.clearData();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        new LoadDynamicData(VEHICLETYPE,(String)sp_City.getItemAtPosition(i)).execute("");
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else{
                    support.connectToNetWork(VehicleMarket.this);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setCitySpinner(){
        String[] city=getResources().getStringArray(R.array.city_name);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(VehicleMarket.this,R.layout.row_city,city);
        sp_City.setAdapter(adapter);
    }
    private void doListReady(ArrayList<VehicleBuyBeen> list,int rq) {
       if (list != null) {


            linRefresh.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            marktAdapter = new VehicleMarktAdapter(getApplicationContext(), list, VehicleMarket.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(marktAdapter);
        } else if (list == null) {
           ArrayList<VehicleBuyBeen> list1 = new ArrayList<VehicleBuyBeen>();
           list1.clear();
           marktAdapter = new VehicleMarktAdapter(getApplicationContext(), list1, VehicleMarket.this);
           RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
           recyclerView.setLayoutManager(mLayoutManager);
           recyclerView.setItemAnimator(new DefaultItemAnimator());
           recyclerView.setAdapter(marktAdapter);
           if(rq==START){
               linRefresh.setVisibility(View.GONE);
               recyclerView.setVisibility(View.VISIBLE);
           }else if(rq==RESTART){
               linRefresh.setVisibility(View.VISIBLE);
               recyclerView.setVisibility(View.GONE);
           }else if(rq==LOAD){
               linRefresh.setVisibility(View.VISIBLE);
               recyclerView.setVisibility(View.GONE);
           }
        }
    }

    private ArrayList<VehicleBuyBeen> buyBeenArrayList(){
        ArrayList<VehicleBuyBeen> arrayList=new ArrayList<VehicleBuyBeen>();
        return arrayList;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {
        Bundle b=null;
        try{
            b = FilterationOfVehicle.data;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        if(b!=null){
            if(b.size()>0){
                setRestaredData(b);
            }else {
                Toast.makeText(getApplicationContext(),"No Data Found",Toast.LENGTH_SHORT).show();
            }

        }
        super.onRestart();
    }
    private void setRestaredData(Bundle b){
        ArrayList<VehicleBuyBeen> jList=vehicleModelClone;
        ArrayList<VehicleBuyBeen> sortedItem=new ArrayList<VehicleBuyBeen>();
        int ct=jList.size();
        Log.i("C*****",""+ct);
       if(FilterationOfVehicle.KEY== INT_ALL_KEY){
               try{
                   Bundle allData = b.getBundle("AllK");
                   String str_brand=allData.getString("BRAND");
                   String str_Model=allData.getString("MODEL");
                   String strYear=allData.getString("YEAR");
                   String strPrice=allData.getString("PRICE");
                   String strPetrol=allData.getString("PETROL");
                   for (VehicleBuyBeen  been:jList) {
                       if((been.getBRAND_NAME().equals(str_brand))&&(been.getMODEL_NAME().equals(str_Model))&&(been.getYEAR_OF_MODEL().equals(strYear))&&(been.getFUEL_TYPE().equalsIgnoreCase(strPetrol))){

                           Long rate=Long.parseLong(been.getYOUR_PRICE());
                           if(strPrice.equals("50000 animation_right")){
                                   try{
                                       if(rate<=50000){
                                           sortedItem.add(been);
                                       }
                                   }catch (Exception e){
                                       e.printStackTrace();
                                   }
                           }else if(strPrice.equals("50000-1 Lakh")){
                                   try{
                                       if((rate>50000)&&(rate<100000)){
                                           sortedItem.add(been);
                                       }
                                   }catch (Exception e){
                                       e.printStackTrace();
                                   }

                           }else if(strPrice.equals("1 Lakh-2 Lakh")){
                               try{
                                   if((rate>100000)&&(rate<200000)){
                                       sortedItem.add(been);
                                   }
                               }catch (Exception e){
                                   e.printStackTrace();
                               }
                           }else if(strPrice.equals("2 Lakh-3 Lakh")){
                               try{
                                   if((rate>200000)&&(rate<300000)){
                                       sortedItem.add(been);

                                   }
                               }catch (Exception e){
                                   e.printStackTrace();
                               }
                           }else if(strPrice.equals("3 Lakh-4 Lakh")){
                               try{
                                   if((rate>300000)&&(rate<400000)){
                                       sortedItem.add(been);
                                   }
                               }catch (Exception e){
                                   e.printStackTrace();
                               }
                           }else if(strPrice.equals("Above 5 Lakh")){
                               try{
                                   if(rate>500000){
                                       sortedItem.add(been);
                                   }
                               }catch (Exception e){
                                   e.printStackTrace();
                               }
                           }
                       }else {
                           for (int i=0;i<marktAdapter.getItemCount();i++){
                               marktAdapter.clearView(i);
                           }


                       }
                   }
               }catch (NullPointerException e){
                   e.printStackTrace();
               }

       }else if(FilterationOfVehicle.KEY== INT_BM_KEY){
               try{
                   Bundle allData = b.getBundle("BMK");
                   String str_brand=allData.getString("BRAND");
                   String str_Model=allData.getString("MODEL");

                   Log.i("**",""+str_brand+"$=$"+str_Model);
                       for (VehicleBuyBeen been:jList) {
                           if((been.getBRAND_NAME().equals(str_brand))&&(been.getMODEL_NAME().equals(str_Model))){
                               //marktAdapter.AddNotifiy(been);
                               sortedItem.add(been);
                           }else {
                               //marktAdapter.clearView(been);
                           }
                       }
                   if (!sortedItem.isEmpty()){
                       for(int i=0;i<marktAdapter.getItemCount();i++){
                           marktAdapter.clearView(i);
                       }
                       doListReady(sortedItem,RESTART);
                   }else{
                       doListReady(null,RESTART);
                   }
               }catch (NullPointerException e){
                   e.printStackTrace();
                   doListReady(null,RESTART);
               }

       }else if(FilterationOfVehicle.KEY== INT_BMY_KEY){
               try{

               }catch (NullPointerException e){
                   e.printStackTrace();
               }

       }else if(FilterationOfVehicle.KEY== INT_BMYP_KEY){
               try{

               }catch (NullPointerException e){
                   e.printStackTrace();
               }

       }else if(FilterationOfVehicle.KEY== INT_BMYPF_KEY){
           try{
               Bundle allData = b.getBundle("BRANDK");
               String str_brand=allData.getString("BRAND");
               for (VehicleBuyBeen been:jList) {
                   if(been.getBRAND_NAME().equals(str_brand)){
                       //marktAdapter.AddNotifiy(been);
                       sortedItem.add(been);
                   }else {
                       //marktAdapter.clearView(been);
                   }
               }
               if (!sortedItem.isEmpty()){
                   for(int i=0;i<marktAdapter.getItemCount();i++){
                       marktAdapter.clearView(i);
                   }
                   doListReady(sortedItem,RESTART);
               }else {
                   doListReady(null,RESTART);
               }
           }catch (NullPointerException e){
               e.printStackTrace();
               doListReady(null,RESTART);
           }

       }else if(FilterationOfVehicle.KEY== INT_BY_KEY){
               try{

               }catch (NullPointerException e){
                   e.printStackTrace();
               }

       }else if(FilterationOfVehicle.KEY== INT_BYP_KEY){
               try{

               }catch (NullPointerException e){
                   e.printStackTrace();
               }

       }else if(FilterationOfVehicle.KEY== INT_BYPF_KEY){
               try{

               }catch (NullPointerException e){
                   e.printStackTrace();
               }

       }else if(FilterationOfVehicle.KEY== INT_BP_KEY){
               try{

               }catch (NullPointerException e){
                   e.printStackTrace();
               }

       }else if(FilterationOfVehicle.KEY== INT_BPF_KEY){
               try{

               }catch (NullPointerException e){
                   e.printStackTrace();
               }

       }else if(FilterationOfVehicle.KEY== INT_MODEL_KEY){
               try{

               }catch (NullPointerException e){
                   e.printStackTrace();
               }

       }else if(FilterationOfVehicle.KEY== INT_PRICE_KEY){
               try{

               }catch (NullPointerException e){
                   e.printStackTrace();
               }

       }else if(FilterationOfVehicle.KEY== INT_FUEL_KEY){
               try{

               }catch (NullPointerException e){
                   e.printStackTrace();
               }

       }else if(FilterationOfVehicle.KEY== INT_MODEL_YEAR_PRICE_FUEL_KEY){
               try{

               }catch (NullPointerException e){
                   e.printStackTrace();
               }

       }else if(FilterationOfVehicle.KEY== INT_MODEL_YEAR_PRICE__KEY){
               try{

               }catch (NullPointerException e){
                   e.printStackTrace();
               }

       }else if(FilterationOfVehicle.KEY== INT_MODEL_YEAR_FUEL_KEY){
               try{

               }catch (NullPointerException e){
                   e.printStackTrace();
               }

       }else if(FilterationOfVehicle.KEY== INT_PRICE_FUEL_KEY){
               try{

               }catch (NullPointerException e){
                   e.printStackTrace();
               }
       }
    }

    /*Inner Class*/
    private  class LoadDynamicData  extends AsyncTask<String,ArrayList<VehicleBuyBeen>,String> {
        private  String mtype="";
        private String mcity="";
        public LoadDynamicData(String vehicleType,String city) {
            this.mtype=vehicleType;
            this.mcity=city;

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            imgReload.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... params) {
          //  doListReady();

            String RETURN="";
            BufferedInputStream inputStream=null;
            BufferedReader reader = null;
            try {
                //URL url=new URL("http://www.nuclearwheels.com/vectarestapi/vehicle/loadUsedVehicles?ID="+mtype);
                String strur=ServerUrl.serverurl+"vehicle/loadUsedVehiclesBYCITY?ID="+mtype+"&city="+mcity;
                URL url=new URL(strur);
                Log.i("URL"," "+strur);

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
                    RETURN=buffer.toString();
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
           //     progressDialog.dismiss();
                e.printStackTrace();
                RETURN="100";
            }
         //   progressDialog.dismiss();
            return RETURN;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(handelVehicleData(s.toString())==1){
                //  RETURN="202";
               // doListReady();
            }
        }
        private int  handelVehicleData(String ins){
            int r=0;
            vehicleModelBeenses.clear();
             vehicleModelClone.clear();
            if(!(ins.isEmpty())){
                try{
                    JSONArray jsonArray=new JSONArray(ins);
                    int l=jsonArray.length();
                    if(l>0){
                        for(int i=0;i<l;i++){
                            VehicleBuyBeen vmb=new VehicleBuyBeen();

                            JSONObject js= jsonArray.getJSONObject(i);
                            if(js.has("KM")){
                                vmb.setTOTAL_KM(js.getString("KM").toString());
                            }
                            if(js.has("PRICE")){
                                String[] parts;
                                String rate="";
                                try{
                                    parts=js.getString("PRICE").toString().split(",");
                                    Log.i("PRICE__",""+parts);
                                    int ln=parts.length;
                                    for(int a=0;a<ln;a++){
                                        rate=rate.concat(parts[a]);
                                    }
                                }catch (Exception e){
                                    rate="";
                                }
                                vmb.setYOUR_PRICE(rate);
                            } if(js.has("POST")){
                                vmb.setPOSTDATE(js.getString("POST").toString());
                            } if(js.has("ONAME")){
                                vmb.setO_NAME(js.getString("ONAME").toString());
                            }if(js.has("EXPIRY")){
                                vmb.setINSURANCE_EXPIRE(js.getString("EXPIRY").toString());
                            } if(js.has("OID")){
                                String ownerId=js.getString("OID").toString();
                                vmb.setO_ID(ownerId);
                                vmb.setOWNER_ID(ownerId);
                            } if(js.has("MODEL")){
                                vmb.setMODEL_NAME(js.getString("MODEL").toString());
                            }if(js.has("OEM")){
                                vmb.setO_EMAIL(js.getString("OEM").toString());
                            }
                            if(js.has("DESC")){
                                vmb.setDESCRIPTION(js.getString("DESC").toString());
                            } if(js.has("ONUM")){
                                vmb.setO_NUMBER(js.getString("ONUM").toString());
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
                          marktAdapter.AddNotifiy(vmb);
                        }
                        vehicleModelClone=(ArrayList<VehicleBuyBeen>) vehicleModelBeenses.clone();
                        recyclerView.setVisibility(View.VISIBLE);
                        linRefresh.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        imgReload.setVisibility(View.VISIBLE);
                    }else{
                        Log.i("DATA IS NO IN ARRAY@@@"," ");
                        recyclerView.setVisibility(View.GONE);
                        linRefresh.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        imgReload.setVisibility(View.VISIBLE);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    recyclerView.setVisibility(View.GONE);
                    linRefresh.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    imgReload.setVisibility(View.VISIBLE);
                    r=0;
                }

            }
            return  r;
        }
    }
}
