package com.vecta.nuclearwheels;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.abstractsupport.Seller;


public class SellVehicle extends Seller{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            Intent intent=super.getIntent();
            Bundle bundle=intent.getExtras();
            String key=bundle.getString("VKEY");
            if(key.equals("bike")){
                setContentViewOfSeller(SellVehicle.this,key);
            }else if(key.equals("car")){
                setContentViewOfSeller(SellVehicle.this,key);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
