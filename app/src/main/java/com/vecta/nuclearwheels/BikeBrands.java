package com.vecta.nuclearwheels;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidsupport.GPSTracker;
import com.background.DoModelDownload;
import com.listAdapters.bikeBeens.VehicleModelBeens;
import com.preferences_storage.SavePrefe;
import com.requestUrl.ServerUrl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BikeBrands extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private ImageView honda,bajaj,tvs,royal,suzuki,vespa,yamaha,hero,bmw,dsk,
            ktm,kawasaki,mahindra,harley,ducati;
    private Animation fade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_brands);
        honda=(ImageView)findViewById(R.id.img_bk_honda_id);
        bajaj=(ImageView)findViewById(R.id.img_bk_bajaj_id);
        tvs=(ImageView)findViewById(R.id.img_bk_tvs_id);
        royal=(ImageView)findViewById(R.id.img_bk_royal_id);
        suzuki=(ImageView)findViewById(R.id.img_bk_suzuki_id);
        vespa=(ImageView)findViewById(R.id.img_bk_vespa_id);
        yamaha=(ImageView)findViewById(R.id.img_bk_yamaha_id);
        hero=(ImageView)findViewById(R.id.img_bk_hero_id);
        dsk=(ImageView)findViewById(R.id.img_bk_dsk_id);
        bmw=(ImageView)findViewById(R.id.img_bk_bmw_id);
        ktm=(ImageView)findViewById(R.id.img_bk_ktm_id);
        kawasaki=(ImageView)findViewById(R.id.img_bk_kawasaki_id);
        harley=(ImageView)findViewById(R.id.img_bk_harley_id);
        ducati=(ImageView)findViewById(R.id.img_bk_ducati_id);
        mahindra=(ImageView)findViewById(R.id.img_bk_mahindra_id);
        honda.setOnClickListener(this);
        bajaj.setOnClickListener(this);
        tvs.setOnClickListener(this);
        royal.setOnClickListener(this);
        suzuki.setOnClickListener(this);
        vespa.setOnClickListener(this);
        yamaha.setOnClickListener(this);
        hero.setOnClickListener(this);
        dsk.setOnClickListener(this);
        bmw.setOnClickListener(this);
        kawasaki.setOnClickListener(this);
        ktm.setOnClickListener(this);
        harley.setOnClickListener(this);
        ducati.setOnClickListener(this);
        mahindra.setOnClickListener(this);
        button=new Button(this);
        getMsg();
        setAnimatyionToView();
        loadIcons();
    }
    private void loadIcons(){
        Picasso.with(getApplicationContext()).load(ServerUrl.imageUral+"icons/two/honda.png").into(honda);
        Picasso.with(getApplicationContext()).load(ServerUrl.imageUral+"icons/two/bajaj.png").into(bajaj);
        Picasso.with(getApplicationContext()).load(ServerUrl.imageUral+"icons/two/royal.png").into(royal);
        Picasso.with(getApplicationContext()).load(ServerUrl.imageUral+"icons/two/ktm.png").into(ktm);
        Picasso.with(getApplicationContext()).load(ServerUrl.imageUral+"icons/two/yamaha.png").into(yamaha);
        Picasso.with(getApplicationContext()).load(ServerUrl.imageUral+"icons/two/kawasaki.png").into(kawasaki);
        Picasso.with(getApplicationContext()).load(ServerUrl.imageUral+"icons/two/mahindra.png").into(mahindra);
        Picasso.with(getApplicationContext()).load(ServerUrl.imageUral+"icons/two/hero.png").into(hero);
        Picasso.with(getApplicationContext()).load(ServerUrl.imageUral+"icons/two/suzuki.png").into(suzuki);
        Picasso.with(getApplicationContext()).load(ServerUrl.imageUral+"icons/two/vespa.png").into(vespa);
        Picasso.with(getApplicationContext()).load(ServerUrl.imageUral+"icons/two/TVS.png").into(tvs);
        Picasso.with(getApplicationContext()).load(ServerUrl.imageUral+"icons/two/harley.png").into(harley);
        Picasso.with(getApplicationContext()).load(ServerUrl.imageUral+"icons/two/dsk.png").into(dsk);
        Picasso.with(getApplicationContext()).load(ServerUrl.imageUral+"icons/two/bmw.png").into(bmw);
        Picasso.with(getApplicationContext()).load(ServerUrl.imageUral+"icons/two/ducati.png").into(ducati);
    }
    private void setAnimatyionToView(){
        fade= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        hero.setAnimation(fade);
        hero.startAnimation(fade);

        honda.setAnimation(fade);
        honda.startAnimation(fade);

        bajaj.setAnimation(fade);
        bajaj.startAnimation(fade);

        royal.setAnimation(fade);
        royal.startAnimation(fade);

        ktm.setAnimation(fade);
        ktm.startAnimation(fade);

        yamaha.setAnimation(fade);
        yamaha.startAnimation(fade);

        kawasaki.setAnimation(fade);
        kawasaki.startAnimation(fade);

        mahindra.setAnimation(fade);
        mahindra.startAnimation(fade);

        hero.setAnimation(fade);
        hero.startAnimation(fade);

        suzuki.setAnimation(fade);
        suzuki.startAnimation(fade);

        tvs.setAnimation(fade);
        tvs.startAnimation(fade);

        harley.setAnimation(fade);
        harley.startAnimation(fade);

        dsk.setAnimation(fade);
        dsk.startAnimation(fade);

        bmw.setAnimation(fade);
        bmw.startAnimation(fade);

        vespa.setAnimation(fade);
        vespa.startAnimation(fade);

        ducati.setAnimation(fade);
        ducati.startAnimation(fade);

    }
    
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(BikeBrands.this,ShoroomList.class);
        intent.putExtra("vtype","two");

        if (v.getId() == R.id.img_bk_honda_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-4",BikeBrands.this);
            startActivity(intent);

        } else if (v.getId() == R.id.img_bk_bajaj_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-3",BikeBrands.this);
            startActivity(intent);

        } else if (v.getId() == R.id.img_bk_tvs_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-1",BikeBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_bk_suzuki_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-13",BikeBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_bk_yamaha_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-16",BikeBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_bk_royal_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-19",BikeBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_bk_vespa_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-31",BikeBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_bk_hero_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-12",BikeBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_bk_dsk_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-32",BikeBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_bk_ktm_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-18",BikeBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_bk_bmw_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-17",BikeBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_bk_kawasaki_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-15",BikeBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_bk_harley_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-V-VHC-2016-14",BikeBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_bk_ducati_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-14",BikeBrands.this);
            startActivity(intent);
        }else  if(v.getId()==R.id.img_bk_mahindra_id){
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-20",BikeBrands.this);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  Toast.makeText(getApplicationContext(),"Resume...... ",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(getApplicationContext(),"onRestart...... ",Toast.LENGTH_SHORT).show();
        getcrossCheck();
    }

    private void getcrossCheck() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ((manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) || (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
        } else {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(BikeBrands.this);
            builder.setIcon(R.drawable.ntwo);
            builder.setTitle("Nuclear Booking");
            builder.setMessage("please enable your Location");
            builder.setCancelable(false);
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    finish();
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
