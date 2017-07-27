package com.vecta.nuclearwheels;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.abstractsupport.Seller;
import com.androidsupport.AndroidSupport;
import com.background.DoModelDownload;
import com.background.DoVehicleDownload;
import com.db_adapter.DBAdapter;
import com.listAdapters.bikeBeens.VehicleModelBeens;
import com.table_base.TableBase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FilterationOfVehicle extends AppCompatActivity implements View.OnClickListener {

    private Spinner spBrand,spFuel,spYear,spPrice,spBrandModel;
    private Button btnSearch;
    public static Bundle data=null;
    public static int KEY=0;
    private String vehicle="";
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
    }
    private void setView(){
        setContentView(R.layout.activity_filteration_of_vehicle);
        spBrand=(Spinner)findViewById(R.id.sp_filterBrandSelection);
        spFuel=(Spinner)findViewById(R.id.sp_filterFuelId);
        spYear=(Spinner)findViewById(R.id.sp_filtermodelyearId);
        spPrice=(Spinner)findViewById(R.id.sp_filterPriceSelectionId);
        btnSearch=(Button)findViewById(R.id.btnFilterSearchID);
        spBrandModel=(Spinner)findViewById(R.id.sp_filterBrandModelId);
        btnSearch.setOnClickListener(this);
        setBrandSpinner();
        setYearSpinner();
        setFuelSpinner();
        setPriceSpinner();
        spBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                count=0;
                String brand= (String)parent.getItemAtPosition(position);
                if(!(brand.equals("--Select Brand--")||(brand.equals("-ALL-")))){
                    dowonloadModel(brand.trim());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void setBrandSpinner(){
        if(Mainact.vehicleKey.equals("two")){
            String brans[] =getResources().getStringArray(R.array.byke_brands);
            ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),R.layout.row,brans);
            spBrand.setAdapter(adapter);
        }else if(Mainact.vehicleKey.equals("four")){
            String brans[] =getResources().getStringArray(R.array.car_brands);
            ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),R.layout.row,brans);
            spBrand.setAdapter(adapter);
        }
    }
    private void setYearSpinner(){
        ArrayList<String> yearList=new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        yearList.add("-Select Year of Model-");
        yearList.add("-ALL-");
        for (int i=15;i>0;i--){
            yearList.add(String.valueOf(year));
            year--;
        }
        ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),R.layout.row,yearList);
        spYear.setAdapter(adapter);
    }
    private void setFuelSpinner(){
        String fuel[] =getResources().getStringArray(R.array.fuel);
        ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),R.layout.row,fuel);
        spFuel.setAdapter(adapter);
    }
    private void setPriceSpinner(){
        String fuel[] =getResources().getStringArray(R.array.vehicle_price);
        ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),R.layout.row,fuel);
        spPrice.setAdapter(adapter);
    }
    private void downloadAllsubModel(String v_id ){
        AndroidSupport support=new AndroidSupport();
        try{
            if(support.isConnected(FilterationOfVehicle.this)){
                new DoModelDownload(FilterationOfVehicle.this,v_id,mHandler).execute("");
            }else {
                support.connectToNetWork(FilterationOfVehicle.this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void dowonloadModel(String bike){
        count++;
        Log.i("COUNT-->>"," "+count);
        if(count<2){
            DBAdapter adapter=new DBAdapter(getApplicationContext());
            if(Mainact.vehicleKey.equalsIgnoreCase("two")){
                try{
                    String v_id=adapter.getBrandId(TableBase.Table.Vehicle.VEHICLE,bike,"two");
                    if(!v_id.isEmpty()){
                        downloadAllsubModel(v_id);
                    }else {
                        vehicle="two";
                        new DoVehicleDownload(FilterationOfVehicle.this,"two",mHandler,1).execute("");
                    }
                }catch (Exception e){

                    vehicle="two";
                    new DoVehicleDownload(FilterationOfVehicle.this,"two",mHandler,1).execute("");
                    e.printStackTrace();
                }
            }else if(Mainact.vehicleKey.equalsIgnoreCase("four")){
                try{
                    String v_id=adapter.getBrandId(TableBase.Table.Vehicle.VEHICLE,bike,"four");
                    if(!v_id.isEmpty()){
                        downloadAllsubModel(v_id);
                    }else{
                        vehicle="four";
                        new DoVehicleDownload(FilterationOfVehicle.this,"four",mHandler,1).execute("");
                    }
                }catch (Exception e){

                    new DoVehicleDownload(FilterationOfVehicle.this,"four",mHandler,1).execute("");
                    vehicle="four";
                    e.printStackTrace();
                }
            }
        }else{
            Toast.makeText(getApplicationContext(),"Pleace Try Another Brand",Toast.LENGTH_SHORT).show();
            ArrayList<String> list=new ArrayList<String>();
            ArrayAdapter<String> adapterBrand=new ArrayAdapter(FilterationOfVehicle.this,R.layout.row,list);
            spBrandModel.setAdapter(adapterBrand);
        }

    }
    /*
    Handler
     */
    public Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(getApplication(),"DataNot Found ",Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    ArrayList<String> list=new ArrayList<String>();
                    list.add("-Select Model-");
                    list.add("-ALL-");
                    for(VehicleModelBeens beens:SubModel.vModelList){
                        list.add(beens.getName());
                    }
                    setpinnerOfModel(list);
                    break;
                case 2://Two Wheelre
                   String  bibrand= (String)spBrand.getSelectedItem();
                    Log.i("selected #2"," "+bibrand);
                    dowonloadModel(bibrand);
                    break;
                case 4://Two Wheelre
                    String cbrand= (String)spBrand.getSelectedItem();
                    Log.i("selected #4"," "+cbrand);
                    dowonloadModel(cbrand);
                    break;
            }
        }
    };
    private void setpinnerOfModel(ArrayList<String> list){
        //  String[] fual=getResources().getStringArray(R.array.fuel);
        ArrayAdapter<String> adapterBrand=new ArrayAdapter(FilterationOfVehicle.this,R.layout.row,list);
        spBrandModel.setAdapter(adapterBrand);
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnFilterSearchID){
            doFilterProcess();
        }
    }
    private void doFilterProcess(){
        int posBrand=spBrand.getSelectedItemPosition();
        int posPetrol=spFuel.getSelectedItemPosition();
        int posYear=spYear.getSelectedItemPosition();
        int posPrice=spPrice.getSelectedItemPosition();
        int posModel=spBrandModel.getSelectedItemPosition();
        Bundle bundle=new Bundle();
        if ((posBrand>0)&&(posPetrol>0)&&(posYear>0)&&(posPrice>0)&&(posModel>0)){
            String strBrand=(String)spBrand.getItemAtPosition(posBrand);
            String strmodel=(String)spBrandModel.getItemAtPosition(posModel);
            String strPetrol=(String)spFuel.getItemAtPosition(posPetrol);
            String strYear=(String)spYear.getItemAtPosition(posYear);
            String strPrice=(String)spPrice.getItemAtPosition(posPrice);
            bundle.putString("BRAND",strBrand);
            bundle.putString("MODEL",strmodel);
            bundle.putString("PETROL",strPetrol);//BRAND/PETROL/YEAR/PRICE
            bundle.putString("YEAR",strYear);
            bundle.putString("PRICE",strPrice);
            Bundle  bundle1=new Bundle();
            bundle1.putBundle("AllK",bundle);
            KEY=1;
            Log.i("SAVED KEY"," "+KEY);
            saveToBundel(bundle1);
        }else if((posBrand>0)&&(posModel>0)&&(posPetrol>0)){
            String strBrand=(String)spBrand.getItemAtPosition(posBrand);
            String strPetrol=(String)spBrandModel.getItemAtPosition(posModel);
            String strYear=(String)spYear.getItemAtPosition(posYear);
            bundle.putString("BRAND",strBrand);
            bundle.putString("PETROL",strPetrol);
            bundle.putString("YEAR",strYear);
            Bundle  bundle1=new Bundle();
            bundle1.putBundle("BPY",bundle);
            KEY=2;
            Log.i("SAVED KEY"," "+KEY);
            saveToBundel(bundle1);
        }else if((posBrand>0)&&(posModel>0)){
            String strbrand=(String)spBrand.getItemAtPosition(posBrand);
            String strbrandModel=(String)spBrandModel.getItemAtPosition(posModel);
            bundle.putString("BRAND",strbrand);
            bundle.putString("MODEL",strbrandModel);
            Bundle  bundle1=new Bundle();
            bundle1.putBundle("BMK",bundle);
            KEY=2;
            Log.i("SAVED KEY"," "+KEY);
            saveToBundel(bundle1);
        }else if ((posBrand>0)&&(posPetrol>0)){
            String strBrand=(String)spBrand.getItemAtPosition(posBrand);
            String strPetrol=(String)spFuel.getItemAtPosition(posPetrol);
            bundle.putString("BRAND",strBrand);
            bundle.putString("PETROL",strPetrol);
            Bundle  bundle1=new Bundle();
            bundle1.putBundle("BPK",bundle);
            KEY=4;
            Log.i("SAVED KEY"," "+KEY);
            saveToBundel(bundle1);
        }else if((posBrand>0)){
            String strBrand=(String)spBrand.getItemAtPosition(posBrand);
            bundle.putString("BRAND",strBrand);
            Bundle  bundle1=new Bundle();
            bundle1.putBundle("BRANDK",bundle);
            KEY=5;
            Log.i("SAVED KEY"," "+KEY);
            saveToBundel(bundle1);
        }else if(posPetrol>0){
            String strPetrol=(String)spFuel.getItemAtPosition(posPetrol);
            bundle.putString("PETROL",strPetrol);
            Bundle  bundle1=new Bundle();
            bundle1.putBundle("FEULK",bundle);
            KEY=6;
            Log.i("SAVED KEY"," "+KEY);
            saveToBundel(bundle1);
        }else if(posYear>0){
            String strYear=(String)spYear.getItemAtPosition(posYear);
            bundle.putString("YEAR",strYear);
            saveToBundel(bundle);
            Bundle  bundle1=new Bundle();
            bundle1.putBundle("YEARK",bundle);
            KEY=7;
            Log.i("SAVED KEY"," "+KEY);
            saveToBundel(bundle1);
        }else if(posPrice>0){
            String strPrice=(String)spPrice.getItemAtPosition(posPrice);
            bundle.putString("PRICE",strPrice);
            Bundle  bundle1=new Bundle();
            bundle1.putBundle("PRICEK",bundle);
            KEY=8;
            Log.i("SAVED KEY"," "+KEY);
            saveToBundel(bundle1);
        }
    }
    private void saveToBundel(Bundle b){
        data=b;
       /* if(data.isEmpty()){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Nuclear Wheels alert").setMessage("Search result not found for this brand, You may select  another  vehicle brand and search for better result ");
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            Dialog dialog= builder.create();
            dialog.show();

        }else {*/
            this.finish();
        //}
    }
}
