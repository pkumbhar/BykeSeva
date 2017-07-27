package com.vecta.nuclearwheels;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.preferences_storage.SavePrefe;

public class CarBrands extends Activity implements View.OnClickListener{

    private Button button;
    private ImageView hondacar,audi,bmw,chevorlet,fiat,ford,hundai,jaguar,landrover,mahindra,maruti,
      mazda,mbenz,mitsubihi,nisaan,opel,porche,renault,toyota,sakoda,volvo,tata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_brands);
        hondacar=(ImageView)findViewById(R.id.img_car_hondacar_id);
        chevorlet=(ImageView)findViewById(R.id.img_car_chevorlet_id);
        audi=(ImageView)findViewById(R.id.img_car_audi_id);
        bmw=(ImageView)findViewById(R.id.img_car_bmw_id);
        fiat=(ImageView)findViewById(R.id.img_car_fiat_id);
        ford=(ImageView)findViewById(R.id.img_car_ford_id);
        hundai=(ImageView)findViewById(R.id.img_car_hundai_id);
        landrover=(ImageView)findViewById(R.id.img_car_landrover_id);
        mahindra=(ImageView)findViewById(R.id.img_car_mahindra_id);
        maruti=(ImageView)findViewById(R.id.img_car_maruti_id);
        mazda=(ImageView)findViewById(R.id.img_car_mazda_id);
        mbenz=(ImageView)findViewById(R.id.img_car_mbenz_id);
        nisaan=(ImageView)findViewById(R.id.img_car_nisaan_id);
        mitsubihi=(ImageView)findViewById(R.id.img_car_mitsubishi_id);
        renault=(ImageView)findViewById(R.id.img_car_renault_id);
        opel=(ImageView)findViewById(R.id.img_car_opel_id);
        porche=(ImageView)findViewById(R.id.img_car_porsche_id);
        toyota=(ImageView)findViewById(R.id.img_car_toyoto_id);
        sakoda=(ImageView)findViewById(R.id.img_car_skoda_id);
        volvo=(ImageView)findViewById(R.id.img_car_volvo_id);
        tata=(ImageView)findViewById(R.id.img_car_tata_id);
        hondacar.setOnClickListener(this);
        audi.setOnClickListener(this);
        bmw.setOnClickListener(this);
        chevorlet.setOnClickListener(this);
        fiat.setOnClickListener(this);
        hundai.setOnClickListener(this);
        mazda.setOnClickListener(this);
        mahindra.setOnClickListener(this);
        maruti.setOnClickListener(this);
        opel.setOnClickListener(this);
        porche.setOnClickListener(this);
        landrover.setOnClickListener(this);
        mbenz.setOnClickListener(this);
        nisaan.setOnClickListener(this);
        mitsubihi.setOnClickListener(this);
        renault.setOnClickListener(this);
        ford.setOnClickListener(this);
        toyota.setOnClickListener(this);
        volvo.setOnClickListener(this);
        sakoda.setOnClickListener(this);
        tata.setOnClickListener(this);
        button=new Button(this);
        getMsg1();
        getMsg();
        System.out.print("Priyatam");



    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(CarBrands.this,ShoroomList.class);
        intent.putExtra("vtype","four");
        Bundle bundle=new Bundle();
        if (v.getId() == R.id.img_car_tata_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-102",CarBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_car_maruti_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-106",CarBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_car_hondacar_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-23",CarBrands.this);
            startActivity(intent);
            //this.finish();
        } else if (v.getId() == R.id.img_car_hundai_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-26",CarBrands.this);
            bundle.putString("B_ID","V-VHC-2016-26");
            startActivity(intent);
        } else if (v.getId() == R.id.img_car_ford_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-24",CarBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_car_fiat_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-25",CarBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_car_mbenz_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-9",CarBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_car_mazda_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-109",CarBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_car_mahindra_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-112",CarBrands.this);
            startActivity(intent);
        }else if (v.getId() == R.id.img_car_bmw_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-113",CarBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_car_chevorlet_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-108",CarBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_car_audi_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-8",CarBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_car_porsche_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-103",CarBrands.this);
            startActivity(intent);
        } else if (v.getId() == R.id.img_car_landrover_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-107",CarBrands.this);
            startActivity(intent);
        }else if (v.getId() == R.id.img_car_toyoto_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-29",CarBrands.this);
            startActivity(intent);
            this.finish();
        }else if (v.getId() == R.id.img_car_volvo_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-28",CarBrands.this);
            intent.putExtra("ID",bundle);
            startActivity(intent);
            this.finish();
        }else if (v.getId() == R.id.img_car_renault_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-10",CarBrands.this);
            startActivity(intent);
            this.finish();
        }else if (v.getId() == R.id.img_car_mitsubishi_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-105",CarBrands.this);
            startActivity(intent);
            this.finish();
        }else if (v.getId() == R.id.img_car_nisaan_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-104",CarBrands.this);
            startActivity(intent);
            this.finish();
        }else if (v.getId() == R.id.img_car_opel_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-110",CarBrands.this);
            startActivity(intent);
            this.finish();
        }else if (v.getId() == R.id.img_car_skoda_id) {
            SavePrefe prefe=new SavePrefe();
            prefe.saveBikeid("V-VHC-2016-11",CarBrands.this);
            startActivity(intent);
            this.finish();
        }
    }
    private  void getMsg(){
        System.out.print("Hello");
    }
    private void getcrossCheck() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ((manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) || (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
        } else {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CarBrands.this);
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

    private  void getMsg1(){
        LocationManager manager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if((manager.isProviderEnabled(LocationManager.GPS_PROVIDER))||(manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
            Log.i("ON LOC"," LOC ");
        }else {
            getcrossCheck();
        }
    }

    }

