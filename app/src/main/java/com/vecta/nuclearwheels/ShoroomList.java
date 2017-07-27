package com.vecta.nuclearwheels;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.design.widget.NavigationView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidsupport.AndroidSupport;
import com.androidsupport.BikeLatLong;
import com.androidsupport.GPSTracker;
import com.background.DoServiceProviderDow;
import com.db_adapter.DBAdapter;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.listAdapters.bikeAdapters.ServiceCenterListAdapter;
import com.listAdapters.bikeBeens.JserviceCenterBeen;
import com.listAdapters.bikeBeens.ShowroomBeens;
import com.table_base.TableBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ShoroomList extends Activity implements OnMapReadyCallback,NavigationView.OnNavigationItemSelectedListener,PlaceSelectionListener{

    private RecyclerView showroomList;
    private ServiceCenterListAdapter serviceCenterListAdapter;
    private GoogleMap map;
    private GPSTracker gpsTracker;
    public  static int SHOWROOM_SUCESS_RESULT=200;
    public  static int SHOWROOMUN_SUCESS_RESULT=100;
    public  static int ADDRESS_SUCESS_RESULT=201;
    public  static int ADDRESS_UNSUCESS_RESULT=101;
    private int REQUEST_STATUES_SUCESS=200;
    private int REQUEST_STATUES_FAIL=100;
    private String name="", postalCode="",vtype="";
    String picode="";
    private static final int REQUEST_SELECT_PLACE = 1000;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds( new LatLng(18.5204, 73.8567), new LatLng(18.5204, 73.8567));
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoroom_list);
        showroomList=(RecyclerView) findViewById(R.id.recycler_view_th_id);
        trackLocation();
        gpsTracker = new GPSTracker(ShoroomList.this);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(ShoroomList.this);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(ShoroomList.this);
        autocompleteFragment.setHint("Search a Location");
        autocompleteFragment.setBoundsBias(BOUNDS_MOUNTAIN_VIEW);
        try{
            Intent intent=super.getIntent();
            vtype=intent.getStringExtra("vtype");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ShoroomList() {
        super();
    }

    @Override
    public void onError(Status status) {
        Log.e("place", "onError: Status = " + status.toString());
        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onPlaceSelected(Place place) {
        Log.i("place", "Place Selected: " + place.getName());
        LatLng latLng = place.getLatLng();
        double lat = latLng.latitude;
        double lng = latLng.longitude;
      //  Toast.makeText(getApplicationContext(),""+place.getName().toString(),Toast.LENGTH_SHORT).show();
        AndroidSupport support=new AndroidSupport();
        if(support.isConnected(ShoroomList.this)){
            new Addrssslines(mHandler,
                    String.valueOf(lat),
                    String.valueOf(lng))
                    .execute("");
        }else {
            support.connectToNetWork(ShoroomList.this);
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PLACE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                this.onPlaceSelected(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                this.onError(status);
            }
            super.onActivityResult(requestCode, resultCode, data);

        }
    }


    /*
            check or track location #trackLocation
             */
    private boolean trackLocation(){
        boolean b=false;
        LocationManager locationManager = (LocationManager) getApplicationContext()
                .getSystemService(LOCATION_SERVICE);
        locationManager.isProviderEnabled("NWTWORK");

        AndroidSupport support=new AndroidSupport();
        if(support.isConnected(ShoroomList.this)){
            new Addrssslines(mHandler,
                    String.valueOf(BikeLatLong.latitude(ShoroomList.this)),
                    String.valueOf(BikeLatLong.longitude(ShoroomList.this)))
                    .execute("");
        }else {
            support.connectToNetWork(ShoroomList.this);
        }
        return b;
    }
    /*
    set Alert of Location
     */
    private void showDialog(String msg,int requestcode) {
        AlertDialog.Builder networkAlert = new AlertDialog.Builder(ShoroomList.this);
        networkAlert.setTitle("Alert .!");
        if (requestcode == 0) {
            networkAlert.setMessage(msg);
            networkAlert.setCancelable(false);
            networkAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    AndroidSupport support = new AndroidSupport();
                    if (support.isConnected(ShoroomList.this)) {
                        /*new Addrssslines(mHandler,
                                String.valueOf(BikeLatLong.latitude(ShoroomList.this)),
                                String.valueOf(BikeLatLong.longitude(ShoroomList.this)))
                                .execute("");*/
                    } else {
                        support.connectToNetWork(ShoroomList.this);
                    }
                }
            });
        } else if (requestcode == 1) {
            networkAlert.setMessage(msg);
            networkAlert.setCancelable(false);
            AndroidSupport support = new AndroidSupport();
            if (support.isConnected(ShoroomList.this)) {
                networkAlert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        new DoServiceProviderDow(ShoroomList.this,mHandler,"",picode,"",vtype).execute("");
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
        }
        AlertDialog alert11 = networkAlert.create();
        alert11.show();
    }
    /*
    set ListAdapter
     */
    private void doListReady(){
        map.clear();
        serviceCenterListAdapter=new ServiceCenterListAdapter(getApplicationContext(),showroomBeensArrayList(),ShoroomList.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        showroomList.setLayoutManager(mLayoutManager);
        showroomList.setItemAnimator(new DefaultItemAnimator());
        showroomList.setAdapter(serviceCenterListAdapter);
        onMapReady(map);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        try{
            Cursor cursor;
            DBAdapter adapter1=new DBAdapter(getApplicationContext());
            cursor=adapter1.getTableInfo(TableBase.Table.GarageAddress.GARAGE_ADDRESS);
            ArrayList<JserviceCenterBeen> list=showroomBeensArrayList();
            if(showroomBeensArrayList().size()>0){
                if (cursor!=null){
                    cursor.moveToFirst();
                    if(cursor.getCount()>0){
                        for(JserviceCenterBeen mk:list){
                            try{
                                if(!mk.getLatitude().equals(1.0)&&(!mk.getLongitude().equals(1.0))){
                                    LatLng ligng=new LatLng(mk.getLatitude(),mk.getLongitude());
                                    MarkerOptions markerOptions = new MarkerOptions();
                                    markerOptions.position(ligng);
                                    markerOptions.title(mk.getGarageName());
                                    markerOptions.snippet(mk.address1+""+mk.getSuburbanArea()+""+mk.getContactNumber());
                                    map.addMarker(markerOptions);
                                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(ligng,13));
                                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mk.getLatitude(),mk.getLongitude()), 13.5f), 1800, null);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                    cursor.close();
                }
                Circle circle = map.addCircle(new CircleOptions()
                        .center(new LatLng(BikeLatLong.latitude(ShoroomList.this), BikeLatLong.longitude(ShoroomList.this)))
                        .radius(5000)
                        .strokeColor(Color.parseColor("#FFEC3939")).strokeWidth(1)
                        .fillColor(Color.parseColor("#FFD6E9FF")));
                LatLng MyPlace = new LatLng(BikeLatLong.latitude(ShoroomList.this), BikeLatLong.longitude(ShoroomList.this));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(MyPlace);
                markerOptions.title("Its me.!");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.man));
                map.addMarker(markerOptions);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
    list
     */
    private ArrayList<JserviceCenterBeen>  showroomBeensArrayList(){
        ArrayList<JserviceCenterBeen> arrayList=new ArrayList<JserviceCenterBeen>();
        DBAdapter adapter=new DBAdapter(getApplicationContext());
        try{
            arrayList.clear();
            arrayList=adapter.getServiceCenterAdapter();
        }catch (Exception e){
            e.printStackTrace();
            arrayList=null;
        }
        return  arrayList;
    }
    /*
    Locatiion tracking class
     */
    class Addrssslines extends AsyncTask<String,Void,String> {

        private Handler handler;
        private String lat="";
        private String lang="";

        private  Addrssslines(Handler mHandler,String lat,String lang){
            this.handler=mHandler;
            this.lat=lat;
            this.lang=lang;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                if(s.equals("SUCESS")){
                    handler.obtainMessage(201).sendToTarget();
                }else  if(s.equals("FAIL")){
                    handler.obtainMessage(101).sendToTarget();}else{
                    handler.obtainMessage(101).sendToTarget();
                }
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String RETURN = null;
            BufferedReader reader = null;
            try {
               URL url=new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lang+"&sensor=true");
//                /
                //  lat/lng: (18.6137829,73.7664355)
                //URL url=new URL(" http://maps.googleapis.com/maps/api/geocode/json?latlng=18.6137829,73.7664355&sensor=true");
                Log.i("URL__>"," "+url.toString());
                HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("POST");
                //urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setUseCaches(false);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("dataType", "json");
                urlConnection.connect();
                int status = urlConnection.getResponseCode();
                BufferedInputStream in;
                if (status >= 400 ) {
                    in = new BufferedInputStream( urlConnection.getErrorStream() );
                    Log.i("",""+in);
                } else {
                    in = new BufferedInputStream( urlConnection.getInputStream() );
                    Log.i("",""+in);
                }
                //input stream
                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(in));
                String inputLine;
                while ((inputLine = reader.readLine()) != null){
                    buffer.append(inputLine + "\n");
                }

                if(ADDRESS_SUCESS_RESULT==handelAddress(buffer.toString())){
                    RETURN="SUCESS";
                    progressDialog.dismiss();
                }else if(ADDRESS_UNSUCESS_RESULT==handelAddress(buffer.toString())){
                    RETURN="FAIL";
                    progressDialog.dismiss();
                }
            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
                progressDialog.dismiss();
            }
            progressDialog.dismiss();
            return RETURN;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(ShoroomList.this);
            progressDialog.setTitle("Tracking  Location...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }
    public  int  handelAddress(String ins){
        ArrayList<String> pincodes=new ArrayList<String>();
        int res=0;
        try {
            Log.i("INPUT SRSULT"," "+ins);
            JSONObject object=new JSONObject(ins);
            object.length();
            JSONArray jsonArray=object.getJSONArray("results");
            for(int mainarray=0;mainarray<jsonArray.length();mainarray++) {
                JSONObject objectzero = jsonArray.getJSONObject(mainarray);
                        JSONArray addressArray = objectzero.getJSONArray("address_components");
                        for (int a = 0; a < addressArray.length(); a++) {
                                JSONObject jsonObjectaddress = addressArray.getJSONObject(a);
                                if (jsonObjectaddress.has("types")) {
                                    JSONArray pinarray=jsonObjectaddress.getJSONArray("types");
                                    String trmp=pinarray.getString(0);
                                    if(trmp.equals("postal_code")){
                                        postalCode = jsonObjectaddress.getString("long_name");
                                        pincodes.add(postalCode);
                                        Log.i("POSTALCODE:", postalCode);
                                    }
                                    try {

                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                }
                        }
            }
            for(int l=0;l<pincodes.size();l++){
                Log.i("VALUSE***"," "+pincodes.get(l));
                String temp=pincodes.get(l);
                if(temp.length()==6){
                    try{
                        int code=Integer.parseInt(temp);
                        postalCode=String.valueOf(code);
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return  201;
    }
    /*
    send Request To fetch Shoroom Location
     */
    private void requestToGarages(String PIN){
        new DoServiceProviderDow(ShoroomList.this,mHandler,"",postalCode,"",vtype).execute("");
    }
    /*
    Handler To handel locatioln and other services
     */
    public Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==SHOWROOM_SUCESS_RESULT){
                doListReady();
            }else  if(msg.what==101){
                showDialog("Location track my take some time \n please try once again..! ",1);
            }else if(msg.what==ADDRESS_SUCESS_RESULT){
                requestToGarages(postalCode);
            }else if (msg.what==REQUEST_STATUES_FAIL){
                showDialog("Data Not Found ",0);
            }
        }
    };

}
