package com.vecta.nuclearwheels;

import android.*;
import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidsupport.AndroidSupport;
import com.androidsupport.BikeLatLong;
import com.background.DoBooking;
import com.background.Doregistration;
import com.db_adapter.DBAdapter;
import com.listAdapters.bikeBeens.UserInfoBeen;
import com.preferences_storage.SavePrefe;
import com.table_base.TableBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Permission;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserRegistration extends Activity {
    public static  int SERVER_SUSESS=200;
    public static  int RECORD_EXISTS=404;
    public static  int MAIL_NOT_VALIED=401;
    private  static  int INTERNAL_SARVER_STATE=500;
    private String possibleEmail="",mailId="";
    private int GMAIL_ACOUNT_REQUEST=1;
    private EditText ed_firstName,ed_lastName,ed_email_id,ed_edcontactNumber,ed_password,edconfirmpassword,ed_address1,ed_address2;
    private Button btn_submit;
    public String locilaty="",sublocality="",postalcode="",area="";
    public static ArrayList<UserInfoBeen> userdata=new ArrayList<UserInfoBeen>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        getaccounts();
    }
    private void setView(){
        setContentView(R.layout.activity_user_registration);
        ed_firstName=(EditText)findViewById(R.id.ed_usreg_fname_id);
        ed_lastName=(EditText)findViewById(R.id.ed_usreg_lname_id);
        ed_email_id=(EditText)findViewById(R.id.ed_usreg_email_id);
        ed_edcontactNumber=(EditText)findViewById(R.id.ed_usreg_contact_id);
        ed_password=(EditText)findViewById(R.id.ed_usreg_psw_id);
        btn_submit=(Button)findViewById(R.id.btn_usersubmit_id);
        edconfirmpassword=(EditText)findViewById(R.id.ed_usreg_cnfpsw_id);
        ed_address1=(EditText)findViewById(R.id.ed_usreg__Address1_id);
        ed_address2=(EditText)findViewById(R.id.ed_usreg_Address2_id);
        getMsg();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AndroidSupport support=new AndroidSupport();
                try{
                    if(support.isConnected(UserRegistration.this)){
                        geoisAvailable();
                    }else{
                      support.connectToNetWork(UserRegistration.this);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    /*
    Handler
     */
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==SERVER_SUSESS){
                checkPendingOrder();
            }else if(msg.what==RECORD_EXISTS){
                Toast.makeText(getApplicationContext(),"This Mail is already exist",Toast.LENGTH_SHORT).show();
            }else if(msg.what==MAIL_NOT_VALIED){
                Toast.makeText(getApplicationContext(),"email not valied",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==INTERNAL_SARVER_STATE){
                Toast.makeText(getApplicationContext(),"Error Code 500",Toast.LENGTH_SHORT).show();
            }
        }
    };
    private void checkPendingOrder(){
        SavePrefe prefe=new SavePrefe();
        String order=prefe.getPendingOrder(UserRegistration.this);
        if(order!=null) {
            if (!order.isEmpty()) {
                Log.i("PRNDING ORDER", ""+order.toString());
                Log.i("IN Pending Order", "UserRegistration");
                try {
                    JSONObject object = new JSONObject(order);
                    DBAdapter adapter=new DBAdapter(getApplicationContext());
                    Cursor cursor=adapter.getTableInfo(TableBase.Table.USERTABLE.USER);
                    if(cursor!=null){
                        if(cursor.getCount()>0){
                            cursor.moveToFirst();
                            JSONObject resId=new JSONObject();
                            String str=cursor.getString(cursor.getColumnIndex(TableBase.Table.USERTABLE.SER_USER_ID));
                            Log.i("SERVER USER ID"," "+str);
                            resId.put("ID",str);
                            object.put("RESERVATIONID",resId);
                        }
                    }
                    new DoBooking(UserRegistration.this, getHandler, object).execute("");
                } catch (JSONException e) {
                    e.printStackTrace();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            } else {
                Log.i("No Pending Order", "UserRegistration");
            }
        }else {
            Log.i("null pending order", "UserRegistration");
            /*startActivity(new Intent(UserRegistration.this,Launch_act.class));
            finish();*/
            Intent intent=new Intent(UserRegistration.this,Launch_act.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
    /*
    get Location
     */
    public  Handler getHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==200){
                showSucessMsg();
            }else if(msg.what==100){
                Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
            }
        }
    };
    private void showSucessMsg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserRegistration.this);
        builder.setIcon(R.drawable.icon_bike);
        builder.setTitle("Booking Status.!");
        builder.setMessage("Booking Done Sucessfully..!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //startActivity(new Intent(UserRegistration.this,Launch_act.class));
                Intent  intent=new Intent(UserRegistration.this,Launch_act.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).show();
    }
    private boolean geoisAvailable(){
        boolean b=false;
        if(isvalid()){
                try{
                        UserInfoBeen bin=new UserInfoBeen();
                        b = true;
                        AndroidSupport support=new AndroidSupport();
                        JSONObject post_data = new JSONObject();
                        post_data.accumulate("FNAME" ,ed_firstName.getText().toString());
                        bin.setFirstName(ed_firstName.getText().toString());
                        post_data.accumulate("LNAME" ,ed_lastName.getText().toString());
                        bin.setLastName(ed_lastName.getText().toString());
                        post_data.accumulate("MOBILE" ,ed_edcontactNumber.getText().toString());
                        bin.setContactNumber(ed_edcontactNumber.getText().toString());
                        post_data.accumulate("EMAIL" ,ed_email_id.getText().toString());
                        bin.setEmail(ed_email_id.getText().toString());
                    try{
                        post_data.accumulate("DEVICE" ,support.getDeviceId(getApplicationContext()) );
                        bin.setDevice_id(support.getDeviceId(getApplicationContext()));
                    }catch (Exception e){
                        post_data.accumulate("DEVICE" ,("0000000000") );
                        bin.setDevice_id("0000000000");
                        e.printStackTrace();
                    }
                        post_data.accumulate("ADDLINE1" ,ed_address1.getText().toString());bin.setAddress1(ed_address1.getText().toString());
                        post_data.accumulate("ADDLINE2" ,"NOT");bin.setAddress2("NOT");
                        post_data.accumulate("AREA" ,sublocality);bin.setArea(sublocality);
                        post_data.accumulate("CITY" ,ed_address2.getText().toString());bin.setCity(ed_address2.getText().toString());
                        post_data.accumulate("PASSWORD" ,ed_password.getText().toString());bin.setPassword(ed_password.getText().toString());
                        new Doregistration(UserRegistration.this,getApplicationContext(),bin,handler).execute(post_data.toString());
                }catch(Exception e){
                    e.printStackTrace();
                    Log.i("ERROR IN LOCATON FING"," ");
                    doRepeatRegisteration();
                    b=false;
                //}
            }
        }
        return  b;
    }
    private void  doRepeatRegisteration(){
        AndroidSupport support=new AndroidSupport();
        JSONObject post_data = new JSONObject();
        UserInfoBeen bin=new UserInfoBeen();
        try{
            post_data.accumulate("FNAME" ,ed_firstName.getText().toString());
            bin.setFirstName(ed_firstName.getText().toString());
            post_data.accumulate("LNAME" ,ed_lastName.getText().toString());
            bin.setLastName(ed_lastName.getText().toString());
            post_data.accumulate("MOBILE" ,ed_edcontactNumber.getText().toString());
            bin.setContactNumber(ed_edcontactNumber.getText().toString());
            post_data.accumulate("EMAIL" ,ed_email_id.getText().toString());
            bin.setEmail(ed_email_id.getText().toString());
            try{
                post_data.accumulate("DEVICE" ,support.getDeviceId(getApplicationContext()) );
            }catch (Exception e){
                post_data.accumulate("DEVICE" ,"0000000000" );
                e.printStackTrace();
            }

            bin.setDevice_id(support.getDeviceId(getApplicationContext()));
            post_data.accumulate("ADDLINE1" ,"Not available");bin.setAddress1("Not available");
            post_data.accumulate("ADDLINE2" ,"Not available");bin.setAddress2("Not available");
            post_data.accumulate("AREA" ,sublocality);bin.setArea(sublocality);
            post_data.accumulate("CITY" ,"Not available");bin.setCity("Not available");
            post_data.accumulate("PASSWORD" ,ed_password.getText().toString());bin.setPassword(ed_password.getText().toString());
            new Doregistration(UserRegistration.this,getApplicationContext(),bin,handler).execute(post_data.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /*
    On Restore instance state
     */
    @Override
    protected void onRestoreInstanceState(Bundle ins) {
        super.onRestoreInstanceState(ins);
        Log.i("In onRestoreIns  Block"," <<<<------------------------------");
        ed_firstName.setText(ins.getString("fname"));
        ed_lastName.setText(ins.getString("lname"));
        ed_email_id.setText(ins.getString("email"));
        ed_password.setText(ins.getString("password"));
    }
    /*
    get email
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("In Save Instance Block"," <<<<------------------------------");
        outState.putString("fname",ed_firstName.getText().toString());
        outState.putString("lname",ed_lastName.getText().toString());
        outState.putString("email",ed_email_id.getText().toString());
        outState.putString("password",ed_password.getText().toString());

    }
    private void getaccounts() {
        Log.i("Device Current SDK=",""+Build.VERSION.SDK_INT);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.GET_ACCOUNTS}, GMAIL_ACOUNT_REQUEST);
            } else {
                setEmailToText();
            }
        }else {
            setEmailToText();
        }

    }
    private  void setEmailToText(){
        try {
            Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");
            for (Account account : accounts) {
                possibleEmail += " --> " + account.name + " : " + account.type + " , \n";
                mailId = account.name;
                possibleEmail += " \n\n";
            }
            //emails=account.name;
        } catch (Exception e) {
            Log.i("Exception", "Exception:" + e);
        }
        try {
            Account[] accounts = AccountManager.get(this).getAccounts();
            for (Account account : accounts) {

                possibleEmail += " --> " + account.name + " : " + account.type + " , \n";
                possibleEmail += " \n";
            }
        } catch (Exception e) {
            Log.i("Exception", "Exception:" + e);
        }
        ed_email_id.setText(mailId);
    }
    /*
    validate Data
     */
    private boolean isvalid(){
        boolean b=false;

            if (ed_firstName.getText().toString().length()>0){
                b=true;
            }else {
                b=false;
                ed_firstName.setError("enter Name");
            }
            if (ed_lastName.getText().toString().length()>0){
                b=true;
            }else {
                b=false;
                ed_lastName.setError("enter last Name");
            }
            if (ed_email_id.getText().toString().length()>0){
                b=true;
            }else {
                b=false;
                ed_email_id.setError("enter email");
            }
            if (ed_edcontactNumber.getText().toString().length()>0){
                b=true;
            }else {
                b=false;
                ed_edcontactNumber.setError("contact Number");
            }
            if (edconfirmpassword.getText().toString().length()>0){
                b=true;
            }else {
                b=false;
                edconfirmpassword.setError("enter password");
            }


        return b;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==GMAIL_ACOUNT_REQUEST){
            if (permissions.length>0){
                if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.GET_ACCOUNTS)==PackageManager.PERMISSION_GRANTED){
                    getaccounts();
                }else {
                    Toast.makeText(getApplicationContext(),"Permission Not Granted",Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(getApplicationContext(),"Permission denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
        User
         */
    private void getcrossCheck() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ((manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) || (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
        } else {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UserRegistration.this);
            builder.setIcon(R.drawable.ntwo);
            builder.setTitle("Nuclear Booking");
            builder.setMessage("please enable your Location");
            builder.setCancelable(false);
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            android.app.AlertDialog dialog;
            dialog = builder.create();
            dialog.show();
        }
    }
    /*
    @ returns current lat long
     */

    private  void getMsg(){
        LocationManager manager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if((manager.isProviderEnabled(LocationManager.GPS_PROVIDER))||(manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
            Log.i("ON LOC"," LOC ");

        }else {
            getcrossCheck();
        }
    }
}
