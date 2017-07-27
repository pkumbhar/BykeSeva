package com.vecta.nuclearwheels;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidsupport.AndroidSupport;
import com.background.DoBooking;
import com.background.DoForgetPassword;
import com.background.DoLogin;
import com.background.DoUpatePassword;
import com.db_adapter.DBAdapter;
import com.listAdapters.bikeBeens.UserInfoBeen;
import com.preferences_storage.SavePrefe;
import com.table_base.TableBase;

import org.json.JSONObject;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginTwo extends Activity implements View.OnClickListener {
    private EditText ed_useremail,ed_password,ed_loginTwomail,ed_newPassword;
    private Button btn_login,btn_sendEmail;
    private TextView tv_forgetPassword,tv_newuser,tv_enterNewPassword;
    public static  int SERVER_SUSESS=200;
    public static  int SERVER_RECORD_NOT_FOUND=403;
    public static int SERVER__SUCESS_ERROR=401;
    public static int UNKNOWNEWERROR=500;
    private   String   TAG="LoginTwo";
    private LinearLayout lin_child_layout,lin_parent_layout;
    public int LOGINTWO=2;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
    private Matcher matcher;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(LoginTwo.this,Launch_act.class));
        //finish();
       /* SavePrefe prefe=new SavePrefe();
        prefe.clearFragmentstatus(LoginTwo.this);
        prefe.saveFragmentstatus("1",LoginTwo.this);*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_two);
        ed_useremail=(EditText)findViewById(R.id.ed_l2username_id);
        ed_password=(EditText)findViewById(R.id.ed_l2password_id);
        tv_forgetPassword=(TextView)findViewById(R.id.tv_l2_id);
        tv_enterNewPassword=(TextView) findViewById(R.id.tv_logintwo_newpass_id);
        tv_newuser=(TextView)findViewById(R.id.tv_l2newuser);
        lin_parent_layout=(LinearLayout)findViewById(R.id.lin_login_two_id);
        lin_child_layout=(LinearLayout)findViewById(R.id.lin_login_two_child);
        btn_login=(Button)findViewById(R.id.btn_l2userlogin_id);
        ed_loginTwomail=(EditText)findViewById(R.id.ed_logintwo_email_check_id);
        ed_newPassword=(EditText)findViewById(R.id.ed_logintwo_newpassword_id);
        btn_sendEmail=(Button)findViewById(R.id.btn_logintwo_sendemail_id);
        btn_login.setOnClickListener(this);
        tv_newuser.setOnClickListener(this);
        tv_forgetPassword.setOnClickListener(this);
        btn_sendEmail.setOnClickListener(this);
    }
     private boolean isemailvalid(String email){
        boolean b=false;
        return email.matches("^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}");
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_l2userlogin_id){
            AndroidSupport support=new AndroidSupport();
            try{
                if(support.isConnected(LoginTwo.this)){
                    if((ed_useremail.getText().toString().length()>2)&&(ed_password.getText().toString().length()>2)){
                        if(isemailvalid(ed_useremail.getText().toString())){
                            UserInfoBeen been=new UserInfoBeen();
                            been.setEmail(ed_useremail.getText().toString());
                            been.setPassword(ed_password.getText().toString());
                            new DoLogin(LoginTwo.this,mHandler,been,3).execute("");
                        }else {
                            ed_useremail.setError("Enter valid email");
                        }
                    }else {
                        ed_useremail.setError("Enter valid email");
                        ed_password.setError("password");
                    }
                }else {
                    support.connectToNetWork(LoginTwo.this);
                }
            }catch (Exception e){
                Log.i("ERROR in"," "+TAG);
                e.printStackTrace();
            }


        }else if(v.getId()==R.id.tv_l2newuser){
            startActivity(new Intent(getApplicationContext(), UserRegistration.class));
        }else if(v.getId()==R.id.tv_l2_id){
            lin_parent_layout.setVisibility(View.GONE);
        }else if(v.getId()==R.id.btn_logintwo_sendemail_id){
            processEmail();
        }
    }
    private void  processEmail(){
        if((ed_loginTwomail.getText().toString().length()>0)&&(ed_newPassword.getText().toString().length()>0)){
            String email=ed_loginTwomail.getText().toString();
            String password=ed_newPassword.getText().toString();
            if(isemailvalid(email)){
                if(!email.isEmpty()){
                    Log.i("UserLogin To"," DoUpatePassword");
                    new DoUpatePassword(LoginTwo.this,updatePasswordHandler,email,password,LOGINTWO).execute("");
                }else {
                    //Todo
                }
            }else {
                ed_loginTwomail.setError("Enter valied email ");
            }

        }else if(ed_loginTwomail.getText().toString().length()>0){
            String email=ed_loginTwomail.getText().toString();
            if(isemailvalid(email)){
                Log.i("UserLogin To"," DoForgetPassword");
                new DoForgetPassword(LoginTwo.this,emailHander,email).execute("");
            }else {
                ed_loginTwomail.setError("Enter valied email ");
            }

        }else {
            ed_loginTwomail.setError("Enter valied email ");
        }
    }
    /*
    handler
     */
    public Handler updatePasswordHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==200){
                Toast.makeText(LoginTwo.this,"Password Updated Sucessfully please Login",Toast.LENGTH_SHORT).show();
                lin_child_layout.setVisibility(View.GONE);//lin_child_layout//lin_parent_layout
                lin_parent_layout.setVisibility(View.VISIBLE);
            }else if(msg.what==403){
                Toast.makeText(LoginTwo.this,"Error While Updating Password",Toast.LENGTH_SHORT).show();
            }
        }
    };
    public Handler emailHander=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==200){
                ed_newPassword.setVisibility(View.VISIBLE);
                tv_enterNewPassword.setVisibility(View.VISIBLE);
            }else if(msg.what==403){
                ed_newPassword.setVisibility(View.GONE);
              //  tv_enterNewPassword.setVisibility(View.GONE);
                tv_enterNewPassword.setText(getResources().getText(R.string.user_not_found));
            }
        }
    };
    public Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("RESPONSE->>"+TAG," "+msg.what);
            if(msg.what==SERVER_SUSESS){
                processPendingOrder();
            }else if(msg.what==SERVER_RECORD_NOT_FOUND){
                Toast.makeText(getApplicationContext(),"User not Found",Toast.LENGTH_SHORT).show();
            }else if(msg.what==SERVER__SUCESS_ERROR){
                Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT).show();
            }
        }
    };
    private void processPendingOrder(){
        Log.i("Checking Order->"," "+TAG);
        SavePrefe  prefe=new SavePrefe();
        DBAdapter adapter=new DBAdapter(getApplicationContext());
        String pendingOrder=prefe.getPendingOrder(LoginTwo.this);
        String userID="";
        try{
            Cursor cursor=adapter.getTableInfo(TableBase.Table.USERTABLE.USER);
            if(cursor!=null){
                cursor.moveToFirst();
                userID=cursor.getString(cursor.getColumnIndex(TableBase.Table.USERTABLE.SER_USER_ID));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        if(!userID.isEmpty()){
            try{
                JSONObject object=new JSONObject(pendingOrder);
                JSONObject resid=new JSONObject();
                resid.put("ID",userID);
                object.put("RESERVATIONID",resid);
                new DoBooking(LoginTwo.this,getmHandler,object).execute("");
            }catch (Exception e){
                e.printStackTrace();
            //    startActivity(new Intent(LoginTwo.this,Launch_act.class));
                Intent  intent=new Intent(LoginTwo.this,Launch_act.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }

    }
    public Handler getmHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("MSG->PENDING"," "+msg.what);
            if(msg.what==100){
Toast.makeText(getApplicationContext(),"Oops Some thing went wrong, please try letter",Toast.LENGTH_SHORT).show();
            }else if(msg.what==200){
                showSucessMsg();
            }
        }
    };
    private void showSucessMsg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginTwo.this);
        builder.setIcon(R.drawable.icon_bike);
        builder.setTitle("Booking Status.!");
        builder.setMessage("Booking Done Sucessfully..!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent  intent=new Intent(LoginTwo.this,Launch_act.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).show();
    }
}
