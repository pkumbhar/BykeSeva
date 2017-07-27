package com.vecta.nuclearwheels;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.background.DoBooking;
import com.db_adapter.DBAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.listAdapters.bikeBeens.JserviceCenterBeen;
import com.preferences_storage.SavePrefe;
import com.table_base.TableBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceCenterDetails extends Activity implements View.OnClickListener,OnMapReadyCallback{
   private TextView tv_servicecentertitle,tv_addressline,tv_opentime,tv_closetime;
    private Button btn_call,btn_book_now;
    private String contactNumber="0000000000";
    private GoogleMap map;
    Bundle b=null;
    private ImageView img_revert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_center_details);
        tv_servicecentertitle=(TextView)findViewById(R.id.tv_serD_SCName_id);
        tv_addressline=(TextView)findViewById(R.id.tv_serD_SCaddresslineID);
        tv_closetime=(TextView)findViewById(R.id.tv_serD_SCclosetimeID);
        tv_opentime=(TextView)findViewById(R.id.tv_serD_SCopentimeID);
        btn_call=(Button)findViewById(R.id.btn_serD_SCcalld);
        img_revert=(ImageView)findViewById(R.id.img_revert_id);
        btn_book_now=(Button)findViewById(R.id.btn_serD_SCbookid);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_servicecenterDetails_id);
        mapFragment.getMapAsync(ServiceCenterDetails.this);
        btn_book_now.setOnClickListener(this);
        btn_call.setOnClickListener(this);
        img_revert.setOnClickListener(this);
        b=getIntent().getExtras();
        getServiceCenterName();
    }
    /*
    getBundel
     */
    private String  getServiceCenterName(){
        String serviceCenterName="";
        JserviceCenterBeen jbin;
//GARAGE_ID
        if(b!=null){
            serviceCenterName=b.getString("GARAGE_NAME");
            if(!serviceCenterName.isEmpty()){
                SavePrefe savePrefe=new SavePrefe();
                savePrefe.saveGarageName(serviceCenterName,ServiceCenterDetails.this);
                DBAdapter adapter=new DBAdapter(getApplicationContext());
                try{
                    jbin=adapter.getServiceCenterDetails(serviceCenterName);
                    contactNumber=jbin.getContactNumber();
                    Log.i(" ",jbin.getGarageName());
                    setData(jbin);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else {
         Toast.makeText(getApplicationContext(),"Some Thing Went Wrong..!",Toast.LENGTH_SHORT).show();
        }
        return serviceCenterName;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        Double lat=0.0;
        Double longi=0.0;
        String add1="";
        String add2="";
        if(b!=null){
            String garagte_id=b.getString("GARAGE_ID");
            SavePrefe savePrefe=new SavePrefe();
            savePrefe.saveGarageId(garagte_id,ServiceCenterDetails.this);
            if(!garagte_id.isEmpty()){
                DBAdapter adapter=new DBAdapter(getApplicationContext());
                try {
                    Cursor mCursor;
                    mCursor=adapter.getTableInfo(TableBase.Table.GarageAddress.GARAGE_ADDRESS,garagte_id);
                    if(mCursor!=null){
                        mCursor.moveToFirst();
                        if(mCursor.getCount()>0){
                            try{
                                lat=Double.parseDouble(mCursor.getString(mCursor.getColumnIndex(TableBase.Table.GarageAddress.LAT)));
                                longi=Double.parseDouble(mCursor.getString(mCursor.getColumnIndex(TableBase.Table.GarageAddress.LONG)));
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            add1=mCursor.getString(mCursor.getColumnIndex(TableBase.Table.GarageAddress.ADDRESS_LINE1));
                            add2=mCursor.getString(mCursor.getColumnIndex(TableBase.Table.GarageAddress.ADDRESS_LINE2));
                            LatLng ligng=new LatLng(lat,longi);
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(ligng);
                           markerOptions.title(add1+"\n"+add2);
                            //markerOptions.snippet(add1+"\n"+add2);
                            map.addMarker(markerOptions);
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(ligng,13));
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,longi), 16.5f), 4000, null);
                        }
                    }
                    mCursor.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }else {
            Toast.makeText(getApplicationContext(),"Map Error..!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_serD_SCcalld){
            Toast.makeText(getApplicationContext(),contactNumber,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("tel:" +contactNumber));
            startActivity(intent);

        }else if(v.getId()==R.id.btn_serD_SCbookid){
            Intent intent=new Intent(ServiceCenterDetails.this,ServiceType.class);
            startActivity(intent);
           // this.finish();

        }else if(v.getId()==R.id.img_revert_id){
            finish();
            //startActivity(new Intent(ServiceCenterDetails.this,ShoroomList.class));
        }
    }


    private void setData(JserviceCenterBeen  been){
        tv_servicecentertitle.setText(been.getGarageName());
        tv_addressline.setText(been.getSuburbanArea()+","+been.getAddress1()+","+been.getAddress2()+","+been.getCity());
        tv_opentime.setText(been.getOpentime());
        tv_closetime.setText(been.getClosetime());
        contactNumber=been.getContactNumber();
    }
}
