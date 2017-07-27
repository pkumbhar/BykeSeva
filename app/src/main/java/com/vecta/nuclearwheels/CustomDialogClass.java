package com.vecta.nuclearwheels;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener {

  public Activity c;
  public Dialog d;
  public Button yes, no,cancel;
  public CustomDialogClass(Activity a) {
    super(a);
    // TODO Auto-generated constructor stub
    this.c = a;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.custom_dialog);
    yes = (Button) findViewById(R.id.btn_yes);
    no = (Button) findViewById(R.id.btn_no);
    cancel=(Button)findViewById(R.id.btn_nor);
    cancel.setOnClickListener(this);
    yes.setOnClickListener(this);
    no.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
    case R.id.btn_yes:
      Intent intent=new Intent(c,UserRegistration.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.putExtra("key","REGISTER");
      c.startActivity(intent);
      break;
    case R.id.btn_no:
      UserLogin fragment = new UserLogin();
      Bundle args = new Bundle();
      args.putInt(Mainact.ARG_PLANET_NUMBER, 3);
      fragment.setArguments(args);
      FragmentManager fragmentManager = c.getFragmentManager();
      fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
      break;
      case R.id.btn_nor:
      dismiss();
    default:
      break;
    }
    dismiss();
  }
}