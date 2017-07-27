package com.vecta.nuclearwheels;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.vecta.nuclearwheels.Mainact;
import com.vecta.nuclearwheels.R;
import com.vecta.nuclearwheels.UserLogin;
import com.vecta.nuclearwheels.UserRegistration;

public class DialogOwner extends Dialog implements View.OnClickListener {

  public Activity c;
  public Dialog d;
  public TextView tv_ownerName,tv_mobileNumber,tv_email;
  public Button call,close;
  private String mName="",mMobile="",mEmail="";

  public DialogOwner(Activity a,String cName,String cMobile,String cemail) {
    super(a);
    this.c = a;
    this.mName=cName;
    this.mMobile=cMobile;
    this.mEmail=cemail;
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.row_owner_info);
    tv_ownerName=(TextView)findViewById(R.id.row_infoName_id);
    tv_mobileNumber=(TextView)findViewById(R.id.row_info_Number_id);
    tv_email=(TextView)findViewById(R.id.row_info_emailId);
    call=(Button)findViewById(R.id.row_info_call_btn);
    call.setOnClickListener(this);
    close=(Button)findViewById(R.id.row_info_btnClose_Id);
    close.setOnClickListener(this);
    try{
      tv_ownerName.setText("Mr."+mName);
      tv_mobileNumber.setText(mMobile);
      tv_email.setText(mEmail);
    }catch (Exception e){
      e.printStackTrace();
    }
  }
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
    case R.id.row_info_call_btn:
      try{
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
          if(c.checkSelfPermission(Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +mMobile));
            c.startActivity(intent);
          }else {
            c.requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1);
          }
        }else{
          Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +mMobile));
          c.startActivity(intent);
        }
      }catch (SecurityException e){
        e.printStackTrace();
      }
      break;
    case R.id.row_info_btnClose_Id:
      dismiss();
      break;
    default:
      break;
    }
    dismiss();
  }
}