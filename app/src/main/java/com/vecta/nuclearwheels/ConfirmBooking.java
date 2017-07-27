package com.vecta.nuclearwheels;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidsupport.AndroidSupport;
import com.androidsupport.BikeLatLong;
import com.androidsupport.GPSTracker;
import com.background.DoBooking;
import com.background.DoLogin;
import com.db_adapter.DBAdapter;
import com.db_adapter.DBBackUpAsyncTask;
import com.listAdapters.bikeBeens.JserviceCenterBeen;
import com.listAdapters.bikeBeens.UserInfoBeen;
import com.preferences_storage.SavePrefe;
import com.requestUrl.ServerUrl;
import com.table_base.TableBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class ConfirmBooking extends Activity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {
    private TextView tv_title, tv_address, tv_dateinformation;

    private EditText ed_vehicle_number, ed_mobileNumber;
    private Button btn_confirm, btn_cancel;
    private String userid = "";
    private JSONObject jsonObject = new JSONObject();
    private String time = "", date = "";
    private String locality = "",extraone="cHECK";
    private LinearLayout mlinChekbox;
    private UserInfoBeen info=new UserInfoBeen();
    private JserviceCenterBeen centerBeen;
    public static int BOOKING_COMPLEATED_SUCESSFULLY=200;
    public static int BOOKING_COMPLEATED_UNSUCESSFULLY=100;
    public static  int CONFIRM_LOGIN_SERVER_SUSESS=200;
    public static  int CONFIRM_LOGIN_RESPONSE_RECORD_NOT_FOUND=201;
    public static  int CONFIRM_LOGIN_SERVER_UNKNOWN_ERROR=202;
    public static int CONFIRM_LOGIN_FORGET_PASSWORD_SUCESS=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);
        tv_title = (TextView) findViewById(R.id.tv_conf_maintitle_id);
        tv_address = (TextView) findViewById(R.id.tv_conf_address_id);
        tv_dateinformation = (TextView) findViewById(R.id.tv_Conf_datestrip_id);
        ed_vehicle_number = (EditText) findViewById(R.id.ed_conf_vehiclenumber);
        btn_confirm = (Button) findViewById(R.id.btn_cong_congirmId);
        btn_cancel = (Button) findViewById(R.id.btn_conf_cancelId);
        mlinChekbox=(LinearLayout)findViewById(R.id.linConfirm_ser_chakboxId);
        btn_confirm.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        setselectedServices();
        getSavedGarage ();
        processRequest();
        geoisAvailable();

    }
    /*
    HAndeler
     */
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==BOOKING_COMPLEATED_SUCESSFULLY){
                showSucessMsg();
            }else if(msg.what==BOOKING_COMPLEATED_UNSUCESSFULLY){
                //TODO UNSUCESS

            }else if(msg.what==CONFIRM_LOGIN_SERVER_SUSESS){

            }else if(msg.what==CONFIRM_LOGIN_RESPONSE_RECORD_NOT_FOUND){

            }else if(msg.what==CONFIRM_LOGIN_SERVER_UNKNOWN_ERROR){

            }
        }
    };
    /*
    private setData
     */
    private void setData(JserviceCenterBeen  been){
        tv_title.setText(been.getGarageName());
        tv_address.setText(been.getSuburbanArea()+","+been.getAddress1()+","+been.getAddress2()+","+been.getCity());
    }
    /*
    setSelected Services
     */
    private void setselectedServices(){
        try{
            SavePrefe prefe=new SavePrefe();
            Set<String> strings=prefe.getServicesByName(ConfirmBooking.this);
            if(strings!=null){
                if(strings.size()>0){
                    for(String s:strings){
                        TextView view=new TextView(this);
                        view.setText(s);
                        view.setTextSize(16);
                        view.setTextColor(Color.parseColor("#FFFFFFFF"));
                        view.setPadding(5,5,0,0);
                        mlinChekbox.addView(view);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*
    private Booking Dialog box
     */
    private void showSucessMsg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmBooking.this);
        builder.setIcon(R.drawable.icon_bike);
        builder.setTitle("Booking Status.!");
        builder.setMessage("Booking Done Sucessfully..!");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(ConfirmBooking.this,Launch_act.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                new DBBackUpAsyncTask(ConfirmBooking.this).execute("");
            }
        }).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton cv, boolean isChecked) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_cong_congirmId) {
            AndroidSupport support=new AndroidSupport();
            try{
                if(support.isConnected(ConfirmBooking.this)){
                    if(ed_vehicle_number.getText().toString().length()>2){
                        if(isRegisreration()){
                            new DoBooking(ConfirmBooking.this,handler,getBookingObject()).execute("");
                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmBooking.this);
                            builder.setTitle("Nuclear Wheels.!");
                            builder.setIcon(R.drawable.ntwo);
                            builder.setMessage("You are not valid user,").setNegativeButton("LOGIN", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SavePrefe prefe=new SavePrefe();
                                    prefe.savePendingOrder(getBookingObject().toString(),ConfirmBooking.this);
                                    startActivity(new Intent(ConfirmBooking.this,LoginTwo.class));
                                }
                            }).setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SavePrefe prefe=new SavePrefe();
                                    prefe.savePendingOrder(getBookingObject().toString(),ConfirmBooking.this);
                                    startActivity(new Intent(ConfirmBooking.this,UserRegistration.class));
                                }
                            }).show();
                        }
                    }else {
                        ed_vehicle_number.setError("Enter Vehicle Number");
                    }
                }else {
                    support.connectToNetWork(ConfirmBooking.this);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.btn_conf_cancelId) {
            this.finish();

        }
    }
    private boolean isRegisreration() {
        String userId = "";
        Cursor cursor;
        boolean r = false;
        DBAdapter adapter = new DBAdapter(getApplicationContext());
        try {
            cursor = adapter.getTableInfo(TableBase.Table.USERTABLE.USER);
            if (cursor != null) {
               if(cursor.getCount()>0){
                   r = true;
                   cursor.moveToFirst();
                   userId = cursor.getString(cursor.getColumnIndex(TableBase.Table.USERTABLE.SER_USER_ID));
               }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            r = false;
        }
        return  r;
    }
    /*
    show login dialog
     */

    public  Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    /*
    getUserId
     */
    private String  getUserId(){
        String userId="";
        Cursor cursor;
        DBAdapter adapter = new DBAdapter(getApplicationContext());
        try{
            cursor=adapter.getTableInfo(TableBase.Table.USERTABLE.USER);
            if(cursor!=null){
                cursor.moveToFirst();
                userId=cursor.getString(cursor.getColumnIndex(TableBase.Table.USERTABLE.SER_USER_ID));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
     return  userId;
    }

    /*
    getBundelDATA
     */
    private void getSavedGarage (){

        JserviceCenterBeen jbin;
        SavePrefe savePrefe=new SavePrefe();
        String garajeName= savePrefe.getGarageNamae(ConfirmBooking.this);
        DBAdapter adapter=new DBAdapter(getApplicationContext());
        try{
            jbin=adapter.getServiceCenterDetails(garajeName);
            Log.i(" ",jbin.getGarageName());
            centerBeen=(JserviceCenterBeen)jbin.clone();
            setData(jbin);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*
 private class to return city using lat and long;
  */
    private boolean geoisAvailable(){
        boolean b=false;
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(statusOfGPS) {
            Geocoder geocoder=new Geocoder(getApplicationContext());
            try{
                List<Address> addresses=geocoder.getFromLocation(BikeLatLong.latitude(ConfirmBooking.this),
                        BikeLatLong.longitude(ConfirmBooking.this), 3);
                if( addresses.size()>0){
                    Address add;
                    add=addresses.get(0);
                    locality=add.getLocality();
                    b = true;
                    System.out.print(" Locality--->"+locality);
                }
            }catch(Exception e){
                e.printStackTrace();
                b=false;
            }
        }
        return  b;
    }

    private void processRequest() {
        try {
            SavePrefe prefe = new SavePrefe();
            tv_dateinformation.setText("Date:"+prefe.getDate(ConfirmBooking.this)+" Time:"+prefe.getTime(ConfirmBooking.this));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*
    ******************************
     */
    private JSONObject getBookingObject() {

        try {
            SavePrefe prefe=new SavePrefe();
            jsonObject.put("BDATE",prefe.getDate(ConfirmBooking.this));
            jsonObject.put("BTIME",prefe.getTime(ConfirmBooking.this));
            jsonObject.put("DELIVERY", "09:00:AM");
            jsonObject.put("ADDID",centerBeen.getAddress_id());
            jsonObject.put("VNUMBER", getVehicleNumber());
            jsonObject.put("VMODELID",getModelId());
            jsonObject.put("GARAGEID",centerBeen.getGarageID());
            jsonObject.put("EXSERVICE", extraone);
            jsonObject.put("SERVICES", servicearray());
            if(isRegisreration()){
                jsonObject.put("RESERVATIONID", reservationobj());
            }{
                Log.i("User Id Not available"," ConfirmBooking");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    /*
    method to get vehicleModel
     */
    private String getModelId(){
        SavePrefe prefe=new SavePrefe();
        return prefe.getModel(ConfirmBooking.this);
    }
    /*
    method to get saved extraservices
     */
    private String getExtraServices(){
        String extraservices="";
        SavePrefe prefe=new SavePrefe();
        extraservices=prefe.getPendingOrder(ConfirmBooking.this);
        return extraservices;
    }
    /*

     */
    private String getVehicleNumber(){
        return ed_vehicle_number.getText().toString();
    }

    private JSONArray servicearray() {
        JSONArray jsonArray = new JSONArray();
        try {
            SavePrefe prefe=new SavePrefe();
            Set<String> GSAID=prefe.getExtraServices(ConfirmBooking.this);
            if(GSAID!=null){
                if(GSAID.size()>0){
                    for(String s:GSAID){
                        jsonArray.put(s);
                    }
                }
            }
        } catch (Exception e) {
            String s=e.getMessage();
            e.printStackTrace();
        }
        return jsonArray;
    }

    private String reservationobj() {
        String userId ="";
        try {
            userId=getUserId();
        } catch (Exception r) {
            r.printStackTrace();
        }

        return userId;
    }


}