package com.vecta.nuclearwheels;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidsupport.AndroidNetworkSupport;

import com.background.DoBooking;
import com.background.DoForgetPassword;
import com.background.DoLogin;
import com.background.DoUpatePassword;
import com.background.EmailCheck;
import com.db_adapter.DBAdapter;
import com.listAdapters.bikeBeens.UserInfoBeen;
import com.preferences_storage.SavePrefe;
import com.requestUrl.ServerUrl;
import com.table_base.TableBase;

import org.json.JSONObject;

import java.sql.SQLException;
import java.util.Arrays;


public class UserLogin extends android.app.Fragment implements View.OnClickListener {

    private AndroidNetworkSupport devSupport = new AndroidNetworkSupport();
    private Button btn_Sign_in, btn_Sign_up,btn_sendMail_id;
    private EditText ed_UserName, ed_password,ed_checkemail,ed_new_password;

    private TextView tvnwuser,tv_forgetPassword_id,tv_newPassword;
    public static int SERVER_SUSESS = 200;
    public static int SERVER_RECORD_NOT_FOUND = 403;
    public static int SERVER__SUCESS_ERROR = 401;
    public static int UNKNOWNEWERROR = 500;
    private String TAG = "Mainact";
    private String email = "";
    private String name = "";
    private LinearLayout lin_parent_layout,lin_child_layout;
    public static String RECIVED_PASSWORD="";
    public int USERLOGIN=1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* ed_UserName=(EditText)getActivity().findViewById(R.id.ed_username_id);
        ed_password=(EditText)getActivity().findViewById(R.id.ed_password_id);*/

        Log.i("onCreated", "onCreated");
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.tv_newuser) {
            startActivity(new Intent(getActivity(), UserRegistration.class));
        } else if (v.getId() == R.id.btn_userlogin_id) {
                if (btn_Sign_in.getText().toString().equalsIgnoreCase("LOGIN")) {
                    if((ed_UserName.getText().toString().length()>0)&&(ed_password.getText().toString().length()>0)){
                        if(isemailvalid(ed_UserName.getText().toString())){
                            loginToUser();
                        }
                        else {
                            ed_UserName.setError("Enter proper email address");
                        }
                    }else {
                        ed_UserName.setError("Enter Email address");
                        ed_password.setError("Enter Password");
                    }
                } else if (btn_Sign_in.getText().toString().equalsIgnoreCase("LOGOUT")) {
                    DBAdapter adapter = new DBAdapter(getActivity());
                    long l = adapter.deleteTable(TableBase.Table.USERTABLE.USER);
                    if (l > 0) {
                        btn_Sign_in.setText("LOGIN");
                        ed_UserName.setFocusable(true);
                        ed_password.setFocusable(true);
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(ed_password, InputMethodManager.SHOW_IMPLICIT);
                        imm.showSoftInput(ed_UserName, InputMethodManager.SHOW_IMPLICIT);
                    }
                }


        }else if(v.getId()==R.id.tv_usrlogin_forgetpass_id){
            lin_parent_layout.setVisibility(View.GONE);
        }else if(v.getId()==R.id.btn_usrlogin_sendemail_id){
            processEmail();
        }
    }
    /*
    email validation
     */
    private boolean isemailvalid(String email){
        boolean b=false;
        return email.matches("^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}");
    }

    /*
    setting
     */
    private void checkDisplay() {
        DBAdapter adapter = new DBAdapter(getActivity());
        try {
            Cursor cursor = adapter.getTableInfo(TableBase.Table.USERTABLE.USER);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    btn_Sign_in.setText("LOGOUT");
                    ed_UserName.setFocusable(false);
                    ed_password.setFocusable(false);
                } else {
                    btn_Sign_in.setText("LOGIN");
                    ed_UserName.setFocusable(true);
                    ed_UserName.setFocusable(true);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(ed_password, InputMethodManager.SHOW_IMPLICIT);
                    imm.showSoftInput(ed_UserName, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            btn_Sign_in.setText("LOGIN");
            ed_UserName.setFocusable(true);
            ed_UserName.setFocusable(true);
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        tvnwuser.setOnClickListener(this);
        btn_Sign_in.setOnClickListener(this);
        tv_forgetPassword_id.setOnClickListener(this);
        checkDisplay();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Toast.makeText(getActivity(), "REsult", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ed_UserName = (EditText) view.findViewById(R.id.ed_username_id);
        ed_password = (EditText) view.findViewById(R.id.ed_password_id);
        tv_forgetPassword_id=(TextView)view.findViewById(R.id.tv_usrlogin_forgetpass_id);
        lin_parent_layout=(LinearLayout)view.findViewById(R.id.lin_usr_parant_id);
        btn_sendMail_id=(Button)view.findViewById(R.id.btn_usrlogin_sendemail_id);
        lin_child_layout=(LinearLayout)view.findViewById(R.id.lin_usr_emailchk_id);
        ed_checkemail=(EditText)view.findViewById(R.id.ed_usr_email_check_id);
        ed_new_password=(EditText)view.findViewById(R.id.ed_usr_newpassword_id);
        tv_newPassword=(TextView)view.findViewById(R.id.tv_usrchk_newpass_id);
        btn_sendMail_id.setOnClickListener(this);
        tvnwuser.setOnClickListener(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.user_login_fragment, container, false);
        btn_Sign_in = (Button) v.findViewById(R.id.btn_userlogin_id);
        tvnwuser = (TextView) v.findViewById(R.id.tv_newuser);
        return v;
    }


    private void saveUserData(String name, String email) {
        try {
            new EmailCheck(getActivity(), email, mHandler).execute("");
        } catch (Exception e) {
        }
    }

    /*
login to server
 */
    private void loginToUser() {
        if (devSupport.isConnected(getActivity())) {
            String[][] param = new String[][]{{"1"}, {ServerUrl.serverurl + ServerUrl.GET + ServerUrl.APIKEY}};
            UserInfoBeen info = new UserInfoBeen();
            info.setEmail(ed_UserName.getText().toString());
            info.setPassword(ed_password.getText().toString());
            //Network check
            new DoLogin(getActivity(), mHandler, info, ServerUrl.USER_LOGIN).execute("");
        } else {
            devSupport.connectToNetWork(getActivity());
        }
    }

    /*
   Handler to handel result
    */
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("MSG FROM HANDLER"," "+msg.what);
            if (msg.what == SERVER_SUSESS) {
                processPendingOrder();
            } else if (msg.what == SERVER_RECORD_NOT_FOUND) {
                Toast.makeText(getActivity(), "User not Found", Toast.LENGTH_SHORT).show();
            } else if (msg.what == SERVER__SUCESS_ERROR) {
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
            }else if(msg.what==500){
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        }
    };
    /*'
    get Dialog
     */
    private void  processEmail(){
        if((ed_checkemail.getText().toString().length()>0)&&(ed_new_password.getText().toString().length()>0)){
            String email=ed_checkemail.getText().toString();
            String password=ed_new_password.getText().toString();
            if(!email.isEmpty()){
                Log.i("UserLogin To"," DoUpatePassword");
            new DoUpatePassword(getActivity(),updatePasswordHandler,email,password,USERLOGIN).execute("");
            }else {
                //Todo
            }
        }else if(ed_checkemail.getText().toString().length()>0){
            String email=ed_checkemail.getText().toString();
            Log.i("UserLogin To"," DoForgetPassword");
            new DoForgetPassword(getActivity(),emailHander,email).execute("");
        }
    }
    public Handler emailHander=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==200){
                ed_new_password.setVisibility(View.VISIBLE);
                tv_newPassword.setVisibility(View.VISIBLE);
                tv_newPassword.setText("Enetr New Password");
            }else if(msg.what==403){
                ed_new_password.setVisibility(View.GONE);
                tv_newPassword.setVisibility(View.VISIBLE);
                tv_newPassword.setText(getResources().getText(R.string.user_not_found));
            }
        }
    };
    public Handler updatePasswordHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==200){
                Toast.makeText(getActivity(),"Password Updated Sucessfully please Login",Toast.LENGTH_SHORT).show();
                lin_child_layout.setVisibility(View.GONE);
                lin_parent_layout.setVisibility(View.VISIBLE);
            }else if(msg.what==403){
                Toast.makeText(getActivity(),"Error While Updating Password",Toast.LENGTH_SHORT).show();
            }
        }
    };
    /*
    check Pending Order
     */
    private void processPendingOrder() {
        Log.i("Pending order->" + TAG, " ");
        SavePrefe prefe = new SavePrefe();
        DBAdapter adapter = new DBAdapter(getActivity());
        String pendingOrder = prefe.getPendingOrder(getActivity());
        String userID = "";
        try {
            Cursor cursor = adapter.getTableInfo(TableBase.Table.USERTABLE.USER);
            if (cursor != null) {
                cursor.moveToFirst();
                userID = cursor.getString(cursor.getColumnIndex(TableBase.Table.USERTABLE.SER_USER_ID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!userID.isEmpty()) {
            try {
                JSONObject object = new JSONObject(pendingOrder);
                JSONObject resid = new JSONObject();
                resid.put("ID", userID);
                object.put("RESERVATIONID", resid);
                new DoBooking(getActivity(), getmHandler, object).execute("");
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Logn Sucessfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), Launch_act.class));
            }
        }
    }
    public Handler getmHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 200) {
                showSucessMsg();
            } else if (msg.what == 100) {

            }

        }
    };

    private void showSucessMsg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.icon_bike);
        builder.setMessage("Booking Done Sucessfully..!");

       builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getActivity(),Launch_act.class));
            }
        }).show();
    }
}



