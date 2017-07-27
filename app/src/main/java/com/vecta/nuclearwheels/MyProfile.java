package com.vecta.nuclearwheels;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.background.DoHistoryDownload;
import com.db_adapter.DBAdapter;
import com.preferences_storage.SavePrefe;
import com.table_base.TableBase;


public class MyProfile extends android.app.Fragment {

  private EditText ed_userName,ed_password,ed_email_id,ed_contactNumber,ed_addres,ed_addresss2;
    private AlertDialog.Builder builder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);
        ed_userName=(EditText)rootView.findViewById(R.id.ed_myp_userName);
        ed_password=(EditText)rootView.findViewById(R.id.ed_myp_password_id);
        ed_email_id=(EditText)rootView.findViewById(R.id.ed_myp_email_id);
        ed_contactNumber=(EditText)rootView.findViewById(R.id.ed_myp_contact_number_id);
        ed_addres=(EditText)rootView.findViewById(R.id.ed_myp_address_add1_id);
        ed_addresss2=(EditText)rootView.findViewById(R.id.ed_myp_address_add1_id);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        checkUserAndHistry();
    }

    @Override
    public void onResume() {
        super.onResume();
        /*Toast.makeText(getActivity(), "In On Resume", Toast.LENGTH_SHORT).show();
        SavePrefe prefe=new SavePrefe();
        try{
            String st=prefe.getFragmentstatus(getActivity());
            if(st.equals("1")){
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }

    /*
        getRegisteration Details
         */
    private void checkUserAndHistry(){
        try{
            DBAdapter  adapter=new DBAdapter(getActivity());
            Cursor mCursor=adapter.getTableInfo(TableBase.Table.USERTABLE.USER);
            if(mCursor!=null){
                mCursor.moveToFirst();
                if(mCursor.getCount()>0){
                    String userId="";
                    userId=mCursor.getString(mCursor.getColumnIndex(TableBase.Table.USERTABLE.SER_USER_ID));
                    if((userId!=null)&&(!userId.isEmpty())){
                        setUserData();
                    }else {
                        showSucessMsg(1);
                    }
                }else {
                    showSucessMsg(2);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void showSucessMsg(int type) {
        if(type==1){
            DBAdapter adapter=new DBAdapter(getActivity());
            try{
                adapter.deleteTable(TableBase.Table.USERTABLE.USER);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(type==2){
        }
         builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.warning);
        builder.setTitle("Alert from Nucler Wheels.!");
        builder.setMessage("you are not authorized user of Nucler Wheels \n please continue using following options");
        builder.setCancelable(false);
        builder.setPositiveButton("LOGIN/REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(getActivity(),LoginTwo.class));
              //  getActivity().getFragmentManager().beginTransaction().remove(MyProfile.this).commit();
            }
        }).setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //startActivity(new Intent(getActivity(),UserRegistration.class));
              //  getActivity().getFragmentManager().beginTransaction().remove(MyProfile.this).commit();

            }
        }).show();
    }
    private void setUserData(){
        DBAdapter adapter=new DBAdapter(getActivity());
        try{
            Cursor mcursor= adapter.getTableInfo(TableBase.Table.USERTABLE.USER);
            if(mcursor!=null){
                if(mcursor.getCount()>0){
                    mcursor.moveToFirst();
                    ed_userName.setText(mcursor.getString(mcursor.getColumnIndex(TableBase.Table.USERTABLE.FIRSTNAME)));
                    ed_password.setText(mcursor.getString(mcursor.getColumnIndex(TableBase.Table.USERTABLE.PASSWORD)));
                    ed_email_id.setText(mcursor.getString(mcursor.getColumnIndex(TableBase.Table.USERTABLE.EMAIL)));
                    ed_contactNumber.setText(mcursor.getString(mcursor.getColumnIndex(TableBase.Table.USERTABLE.MOBILENUMBER)));
                    ed_addres.setText(mcursor.getString(mcursor.getColumnIndex(TableBase.Table.USERTABLE.ADDRESS_LINE1)));
                    ed_addresss2.setText(mcursor.getString(mcursor.getColumnIndex(TableBase.Table.USERTABLE.ADDRESS_LINE2)));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
