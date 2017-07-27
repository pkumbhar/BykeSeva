package com.vecta.nuclearwheels;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidsupport.AndroidNetworkSupport;
import com.androidsupport.AndroidSupport;
import com.background.DoModelDownload;
import com.background.DoVehicleDownload;
import com.db_adapter.DBAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.listAdapters.bikeBeens.VehicleModelBeens;
import com.requestUrl.ServerUrl;
import com.table_base.TableBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsurancePolicy extends AppCompatActivity implements View.OnClickListener{
    private EditText eFirstName,eLastName,econtactNumber,eEmailAddress,elocalAddtess;
    private String strFirstName="",strLastName="",strContactNumber="",strrMail="",strAddtess="",strInsuranceType="",strins_vehicle_type="",Model="",Brand="";
    private Button btnSubmit;
    private RadioGroup rdGroup_Insurance_policy;
    private Spinner sp_InsuranceBrand,sp_InsuranceModel;
    private LinearLayout linSpinner;
    private int VEHICLE_TYPE=0;
    private JSONObject jb=new JSONObject();
    private String VEHICLETYPE="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_policy);
        eFirstName=(EditText)findViewById(R.id.ed_usreg_Insurencefname_id);
        eLastName=(EditText)findViewById(R.id.ed_insurance_Lname_id);
        econtactNumber=(EditText)findViewById(R.id.ed_insurance_contactl_id);
        eEmailAddress=(EditText)findViewById(R.id.ed_insuranceEmail_contact_id);
        elocalAddtess=(EditText)findViewById(R.id.ed_insuranceAddress__id);
        rdGroup_Insurance_policy=(RadioGroup)findViewById(R.id.radio_insurance_policyType);

        btnSubmit=(Button)findViewById(R.id.btn_insurancesubmit_id);
        btnSubmit.setOnClickListener(this);
        sp_InsuranceModel=(Spinner)findViewById(R.id.sp_lin_InsuranceModelID);
        sp_InsuranceBrand=(Spinner)findViewById(R.id.sp_lin_InsuranceBrandID);
        doChakedProcess();
        sp_InsuranceBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

                SubModel.vModelList.clear();
                ArrayList<String>  arrayList=new ArrayList<String>();
                arrayList.clear();
                setpinnerOfModel(arrayList);
                if(VEHICLETYPE.equalsIgnoreCase("Car")){
                    VEHICLE_TYPE=4;
                    String brand= (String)parent.getItemAtPosition(position);
                    if(!(brand.equals("--Select Brand--"))){
                        dowonloadModel(brand.trim());
                    }
                } if(VEHICLETYPE.equalsIgnoreCase("Bike")){
                    VEHICLE_TYPE=2;
                    String brand= (String)parent.getItemAtPosition(position);
                    if(!(brand.equals("--Select Brand--"))){
                            try{
                                    dowonloadModel(brand.trim());
                            }catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),"Please try Latter  ",Toast.LENGTH_SHORT).show();
                            }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void doChakedProcess(){
        AndroidNetworkSupport support=new AndroidNetworkSupport();
        if(support.isConnected(InsurancePolicy.this)){
            try {
                Intent intent=super.getIntent();
                VEHICLETYPE= intent.getStringExtra("VKEY");//Returns Vehicle Type(VKEY) i.e bike or Car From Mainact.java LINE NO 379
                if(VEHICLETYPE.equalsIgnoreCase("Car")){
                    setBrands(4);
                }else if(VEHICLETYPE.equalsIgnoreCase("bike")){
                    setBrands(2);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            support.connectToNetWork(InsurancePolicy.this);
        }
    }

    private void dowonloadModel(String bike){
        DBAdapter adapter=new DBAdapter(getApplicationContext());
        if(VEHICLE_TYPE==2){
            try{
                String v_id=adapter.getBrandId(TableBase.Table.Vehicle.VEHICLE,bike,"two");
                if(!v_id.isEmpty()){
                    downloadAllsubModel(v_id);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        } if(VEHICLE_TYPE==4){
            try{
                String v_id=adapter.getBrandId(TableBase.Table.Vehicle.VEHICLE,bike,"four");
                if(!v_id.isEmpty()){
                    downloadAllsubModel(v_id);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private void downloadAllsubModel(String v_id ){
        AndroidSupport support=new AndroidSupport();
        try{
            if(support.isConnected(InsurancePolicy.this)){
                new DoModelDownload(InsurancePolicy.this,v_id,mHandler).execute("");
            }else {
                support.connectToNetWork(InsurancePolicy.this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
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
                    for(VehicleModelBeens beens:SubModel.vModelList){
                        list.add(beens.getName());
                    }
                    setpinnerOfModel(list);
                    break;
                case 200:
                    showDialog("Your details submited sucessfully ");

                    break;
                case 420:
                    showDialog("some thing went wrong \n please try latter..  ");

                    break;
                case 500:
                    showDialog("Error with internet  please try latter.. ");

                    break;
            }
        }
    };
    private void showDialog(String msg){
        AlertDialog alertDialog = new AlertDialog.Builder(InsurancePolicy.this).create();
        alertDialog.setTitle("Nuclear Wheels");
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.show();
    }

    private void setpinnerOfModel(ArrayList<String> list){
        //  String[] fual=getResources().getStringArray(R.array.fuel);
        try{
            if((list!=null)&&(list.size()>0)){
                ArrayAdapter<String> adapterBrand=new ArrayAdapter(InsurancePolicy.this,R.layout.row,list);
                sp_InsuranceModel.setAdapter(adapterBrand);
            }else {
                ArrayAdapter<String> adapterBrand=new ArrayAdapter(InsurancePolicy.this,R.layout.row,list);
                sp_InsuranceModel.setAdapter(adapterBrand);
            }
        }catch (Exception e){
            e.printStackTrace();
            ArrayAdapter<String> adapterBrand=new ArrayAdapter(InsurancePolicy.this,R.layout.row,list);
            sp_InsuranceModel.setAdapter(adapterBrand);
        }
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_insurancesubmit_id){
            Log.i("CLIVK","--");
            if(validate()){
                sendEmail();
            }

        }
    }
    private void setBrands(int request) {
        if (request == 2) {
            String[] bykeBrand = getResources().getStringArray(R.array.byke_brands);
            List<String> ls = new ArrayList<String>();
            ls = Arrays.asList(bykeBrand);

            ArrayAdapter<String> adapterBrand = new ArrayAdapter(InsurancePolicy.this, R.layout.row, ls);
            sp_InsuranceBrand.setAdapter(adapterBrand);
        } else if (request == 4) {
            String[] bykeBrand = getResources().getStringArray(R.array.car_brands);
            ArrayAdapter<String> adapterBrand = new ArrayAdapter(InsurancePolicy.this, R.layout.row, bykeBrand);
            sp_InsuranceBrand.setAdapter(adapterBrand);
        }
    }
    private void sendEmail(){
        int rdid=rdGroup_Insurance_policy.getCheckedRadioButtonId();
        RadioButton radioButton=(RadioButton)findViewById(rdid);
            try {
                jb.put("name",strFirstName+","+strLastName);
                jb.put("email",strrMail);
                jb.put("mobile",strContactNumber);
                jb.put("vehicle",VEHICLETYPE);
                jb.put("brand",Brand);
                jb.put("model",Model);
                jb.put("policy",radioButton.getText().toString());
                jb.put("address",strAddtess);
                new SendMailToServer().execute("");
            } catch (JSONException ex) {
                ex.printStackTrace();
        }
    }
    private boolean validate(){
        boolean b=true;
        if(eFirstName.getText().toString().length()==0){
            eFirstName.setError("Enter your first name");
            eFirstName.setBackgroundResource(R.drawable.editerror);
            b= false;
        }
        if(eLastName.getText().toString().length()==0){
            eLastName.setError("Enter your last name");
            eLastName.setBackgroundResource(R.drawable.editerror);
            b=false;
        }

        if(econtactNumber.getText().toString().length()==0){
            econtactNumber.setError("Enter mobile number");
            econtactNumber.setBackgroundResource(R.drawable.editerror);
            b= false;
        }
        //---------------------
        if(eEmailAddress.getText().toString().length()==0){
            eEmailAddress.setError("Enter valied email ex@gmail.com");
            eEmailAddress.setBackgroundResource(R.drawable.editerror);
            b=false;
        }else {
            if(isValidEmailAddress(eEmailAddress.getText().toString())){
            Log.i("EMAIL is VALIED","--");
                eEmailAddress.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            }else {
                eEmailAddress.setError("Enter valid email  address ex@gmail.com");
                eEmailAddress.setBackgroundResource(R.drawable.editerror);
                b= false;
            }
        }
        //-----------
        if(elocalAddtess.getText().toString().length()==0){
            elocalAddtess.setError("Your address");
            elocalAddtess.setBackgroundResource(R.drawable.editerror);
            b=false;
        }
        if(sp_InsuranceBrand.getSelectedItemPosition()==0){
            sp_InsuranceBrand.setBackgroundResource(R.drawable.editerror);
            Log.i("Brand Not Selected","");
            b=false;
        }
        if(sp_InsuranceModel.getSelectedItemPosition()==0){
            sp_InsuranceModel.setBackgroundResource(R.drawable.editerror);
            Log.i("Model Not Selected","");
            b=false;
        }
        return b;
    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    private class SendMailToServer extends AsyncTask<String,Void,String>{
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(InsurancePolicy.this);
            progressDialog.setTitle("Sending Mail");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s!=null){
                Log.i("Customer Result->",""+s);
                if (s.equals("200")){
                    progressDialog.dismiss();
                    mHandler.obtainMessage(200).sendToTarget();

                }else if(s.equals("420")){
                    progressDialog.dismiss();
                    mHandler.obtainMessage(420).sendToTarget();
                }else if(s.equals("NETWORK_ERR")){
                    progressDialog.dismiss();
                    mHandler.obtainMessage(500).sendToTarget();
                }
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            String SERVER_URL= ServerUrl.serverurl+"booking/sendMails";
            //String LOCAL_HOST= ServerUrl.LOCAL_HOST+"booking/sendMails";
            String str="";
            InputStream stream=null;
            BufferedReader reader = null;
            try{
                URL url=new URL(SERVER_URL);
                HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");

                OutputStream out = urlConnection.getOutputStream();
                out.write(jb.toString().getBytes());
                out.flush();
                out.close();
                int rscode=urlConnection.getResponseCode();
                String rscodestr=urlConnection.getResponseMessage();
                StringBuilder stringBuilder=new StringBuilder();
                if(rscode==200){
                    BufferedReader reader1 = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String output = "";
                    while ((output = reader1.readLine()) != null) {
                        stringBuilder.append(output);
                    }
                    JSONObject object=new JSONObject(stringBuilder.toString());
                   if(object.has("STATUS")){

                       return object.getString("STATUS");
                   }
                }else if(rscode>=400){
                    return "NETWORK_ERR";
                }
                urlConnection.disconnect();
            }catch (Exception e){
                progressDialog.dismiss();
                e.printStackTrace();
                return "NETWORK_ERR";
            }
            return null;
        }
    }
}
