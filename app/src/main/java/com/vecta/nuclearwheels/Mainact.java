package com.vecta.nuclearwheels;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.app.AlertDialog;;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidsupport.AndroidSupport;
import com.background.DoVehicleDownload;
import com.db_adapter.DBAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.listAdapters.bikeBeens.Post;
import com.preferences_storage.SavePrefe;
import com.table_base.TableBase;

import java.sql.SQLException;


public class Mainact extends android.app.Fragment implements View.OnClickListener {
    public static final String ARG_PLANET_NUMBER = "planet_number";
    private ImageView img_car,img_bike;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    public static int COMPANY_ID_DOWNLOAD_SUCESS_TWO=2;
    public static int COMPANY_ID_DOWNLOAD_SUCESS_FOUR=4;
    public static int COMPANY_ID_DOWNLOAD_UNSUCESS=0;
    public static int VEHICLE_DOWNLOAD_SUCESS=202;
    public static int VEHICLE_DOWNLOAD_UN_SUCESS=404;
    private int PERMISSION_REQUEST_FINE_LOCATION = 1;
    private TextView tv_emailnav;
    private LinearLayout lin_servicingByeSell,lin_byeSell,lin_menuvehicle,lin_back_ground;
    private Button btn_Servicing, btnInsurance,btn_buy,btn_cell,btn_accessories,btn_login;
    private TextView tv_msg_id;
    private Animation animation_right;
    private Animation animation_left;
    private int ACTIVE_BUTTON =0;
    private  int SERVICING=1;
    private int INSURANCE=2;
    private int BUY=3;
    private int SELL=4;
    private int ACCESSRIES=5;
    private Cursor mcursor;
    private String TAG="MainAct Fragment";
    public static String vehicleKey="";
    public  String CLICKED_VEHICLE="";




    public Mainact() {
        // Empty constructor required for fragment subclasses

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.act_mainmenu, container, false);
        int i = getArguments().getInt(ARG_PLANET_NUMBER);
        String planet = getResources().getStringArray(R.array.profile)[i];
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        img_car=(ImageView)rootView.findViewById(R.id.img_car_id);
        img_car.setOnClickListener(this);
        lin_back_ground=(LinearLayout)rootView.findViewById(R.id.lin_baack_id);
        btn_accessories=(Button)rootView.findViewById(R.id.btn_ACCESSORIES_id);
        btn_accessories.setOnClickListener(this);
        img_bike=(ImageView)rootView.findViewById(R.id.img_bike_id) ;
        img_bike.setOnClickListener(this);
        lin_servicingByeSell=(LinearLayout)rootView.findViewById(R.id.lin_buy_cell_id);
        btn_login=(Button)rootView.findViewById(R.id.btn_LOGINMAIN_id);
        btn_login.setOnClickListener(this);
        btn_Servicing=(Button)rootView.findViewById(R.id.btn_in_serciving_id);
        btn_buy=(Button)rootView.findViewById(R.id.btn__by_id);
        btnInsurance =(Button)rootView.findViewById(R.id.btn_insurance_id);
        btn_cell=(Button)rootView.findViewById(R.id.btn_sell_id);
        btn_cell.setOnClickListener(this);
        btnInsurance.setOnClickListener(this);
        tv_msg_id=(TextView)rootView.findViewById(R.id.tv_msg_id);
        lin_menuvehicle=(LinearLayout)rootView.findViewById(R.id.lin_bmenu_id);
        img_car.setVisibility(View.VISIBLE);
        img_bike.setVisibility(View.VISIBLE);
        tv_msg_id.setVisibility(View.GONE);
        animation_right= AnimationUtils.loadAnimation(getActivity(),R.anim.fade_out);
        animation_left=AnimationUtils.loadAnimation(getActivity(),R.anim.fade_out);
        //  startServices()
        return rootView;
    }
    private void sizes(){
        WindowManager manager=(WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display=getActivity().getWindowManager().getDefaultDisplay();
        Point size=new Point();
        display.getSize(size);

        int w=size.x;
        int h=size.y;

        btn_Servicing.setHeight(120);
        btn_Servicing.setWidth(100);

        btnInsurance.setHeight(120);
        btnInsurance.setWidth(100);

        btn_buy.setHeight(120);
        btn_buy.setWidth(100);

        btn_cell.setHeight(120);
        btn_cell.setWidth(100);

        btn_accessories.setHeight(120);
        btn_accessories.setWidth(100);

        btn_login.setHeight(120);
        btn_login.setWidth(100);



    }
    private void setAnimation(){
        Animation slidein;
        slidein = AnimationUtils.loadAnimation(getActivity(),R.anim.slidedoen);
        btn_Servicing.setAnimation(slidein);
        btn_buy.setAnimation(slidein);
        btnInsurance.setAnimation(slidein);
        btn_cell.setAnimation(slidein);
        btn_accessories.setAnimation(slidein);
        btn_login.setAnimation(slidein);
        btn_Servicing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACTIVE_BUTTON=SERVICING;
                btn_buy.setAnimation(animation_left);
                btn_buy.startAnimation(animation_left);

                btn_Servicing.setAnimation(animation_left);
                btn_Servicing.startAnimation(animation_left);

                btnInsurance.setAnimation(animation_right);
                btnInsurance.startAnimation(animation_right);

                btn_cell.setAnimation(animation_right);
                btn_cell.startAnimation(animation_right);

                btn_login.setAnimation(animation_right);
                btn_login.startAnimation(animation_right);

                btn_accessories.setAnimation(animation_left);
                btn_accessories.startAnimation(animation_left);
                doanimitation();
            }
        });
        btnInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ACTIVE_BUTTON=INSURANCE;
                btnInsurance.setAnimation(animation_left);
                btnInsurance.startAnimation(animation_left);

                btn_buy.startAnimation(animation_left);
                btn_buy.setAnimation(animation_left);

                btn_cell.setAnimation(animation_right);
                btn_cell.startAnimation(animation_right);

                btn_Servicing.setAnimation(animation_left);
                btn_Servicing.startAnimation(animation_left);

                btn_login.setAnimation(animation_right);
                btn_login.startAnimation(animation_right);

                btn_accessories.setAnimation(animation_left);
                btn_accessories.startAnimation(animation_left);
                doanimitation();

            }
        });
        btn_cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ACTIVE_BUTTON =SELL;
                btn_cell.setAnimation(animation_left);
                btn_cell.startAnimation(animation_left);

                btn_Servicing.setAnimation(animation_left);
                btn_Servicing.startAnimation(animation_left);

                btn_buy.setAnimation(animation_left);
                btn_buy.startAnimation(animation_left);

                btnInsurance.setAnimation(animation_right);
                btnInsurance.startAnimation(animation_right);

                btn_login.setAnimation(animation_right);
                btn_login.startAnimation(animation_right);

                btn_accessories.setAnimation(animation_left);
                btn_accessories.startAnimation(animation_left);
                doanimitation();

            }
        });
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACTIVE_BUTTON=BUY;
                btn_buy.setAnimation(animation_left);
                btn_buy.startAnimation(animation_left);

                btn_Servicing.setAnimation(animation_left);
                btn_Servicing.startAnimation(animation_left);

                btn_cell.setAnimation(animation_right);
                btn_cell.startAnimation(animation_right);

                btnInsurance.setAnimation(animation_right);
                btnInsurance.startAnimation(animation_right);

                btn_login.setAnimation(animation_right);
                btn_login.startAnimation(animation_right);

                btn_accessories.setAnimation(animation_left);
                btn_accessories.startAnimation(animation_left);
                doanimitation();
            }
        });
        btn_accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACTIVE_BUTTON=ACCESSRIES;
                btn_buy.setAnimation(animation_left);
                btn_buy.startAnimation(animation_left);

                btn_Servicing.setAnimation(animation_left);
                btn_Servicing.startAnimation(animation_left);

                btn_cell.setAnimation(animation_right);
                btn_cell.startAnimation(animation_right);

                btnInsurance.setAnimation(animation_right);
                btnInsurance.startAnimation(animation_right);

                btn_login.setAnimation(animation_right);
                btn_login.startAnimation(animation_right);

                btn_accessories.setAnimation(animation_left);
                btn_accessories.startAnimation(animation_left);
                doanimitation();
            }
        });
        animation_right.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
         //Toast.makeText(getActivity(),"start",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if(ACTIVE_BUTTON==SERVICING) {

                    btn_Servicing.setVisibility(View.GONE);
                    lin_back_ground.setBackgroundColor(Color.TRANSPARENT);
                    btn_buy.setVisibility(View.GONE);
                    btnInsurance.setVisibility(View.GONE);
                    btn_cell.setVisibility(View.GONE);
                    img_car.setVisibility(View.VISIBLE);
                    img_bike.setVisibility(View.VISIBLE);
                    tv_msg_id.setVisibility(View.VISIBLE);
                    lin_menuvehicle.setVisibility(View.VISIBLE);
                    btn_accessories.setVisibility(View.GONE);
                    btn_login.setVisibility(View.GONE);

                }else if (ACTIVE_BUTTON==INSURANCE){
                    tv_msg_id.setVisibility(View.VISIBLE);
                    tv_msg_id.setGravity(Gravity.CENTER);
                    lin_back_ground.setBackgroundColor(Color.TRANSPARENT);

                        btn_accessories.setVisibility(View.GONE);
                        btn_login.setVisibility(View.GONE);
                        btn_Servicing.setVisibility(View.GONE);
                        btn_buy.setVisibility(View.GONE);
                        btnInsurance.setVisibility(View.GONE);
                        btn_cell.setVisibility(View.GONE);
                        img_car.setVisibility(View.VISIBLE);
                        img_bike.setVisibility(View.VISIBLE);
                        lin_menuvehicle.setVisibility(View.VISIBLE);

                    /*-------------------------------------------------------*/
                    /*-------------------------------------------------------*/
                }else if (ACTIVE_BUTTON==SELL){
                    tv_msg_id.setVisibility(View.VISIBLE);
                    tv_msg_id.setGravity(Gravity.CENTER);
                    lin_back_ground.setBackgroundColor(Color.TRANSPARENT);

                    btn_Servicing.setVisibility(View.GONE);
                    btn_buy.setVisibility(View.GONE);
                    btnInsurance.setVisibility(View.GONE);
                    btn_cell.setVisibility(View.GONE);
                    img_car.setVisibility(View.VISIBLE);
                    img_bike.setVisibility(View.VISIBLE);
                    lin_menuvehicle.setVisibility(View.VISIBLE);
                }else if(ACTIVE_BUTTON==BUY){
                    btn_buy.setVisibility(View.GONE);
                    tv_msg_id.setVisibility(View.VISIBLE);
                    tv_msg_id.setGravity(Gravity.CENTER);
                    lin_back_ground.setBackgroundColor(Color.TRANSPARENT);

                    btn_Servicing.setVisibility(View.GONE);

                    btnInsurance.setVisibility(View.GONE);
                    btn_cell.setVisibility(View.GONE);
                    img_car.setVisibility(View.VISIBLE);
                    img_bike.setVisibility(View.VISIBLE);
                    lin_menuvehicle.setVisibility(View.VISIBLE);
                }
                else if(ACTIVE_BUTTON==ACCESSRIES){
/*
                    btn_accessories.setVisibility(View.GONE);
                    btn_login.setVisibility(View.GONE);
                    btn_buy.setVisibility(View.GONE);

                    tv_msg_id.setVisibility(View.VISIBLE);
                    tv_msg_id.setGravity(Gravity.CENTER);
                    lin_back_ground.setBackgroundColor(Color.TRANSPARENT);
                    btn_Servicing.setVisibility(View.GONE);

                    btnInsurance.setVisibility(View.GONE);
                    btn_cell.setVisibility(View.GONE);
                    img_car.setVisibility(View.VISIBLE);
                    img_bike.setVisibility(View.VISIBLE);
                    lin_menuvehicle.setVisibility(View.VISIBLE);
                    doanimitation();*/
                    startActivity(new Intent(getActivity(),Accessories.class));


                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void doanimitation(){
        Animation carByke;
        carByke= AnimationUtils.loadAnimation(getActivity(),R.anim.fade_in);
        lin_menuvehicle.setAnimation(carByke);
        lin_menuvehicle.startAnimation(carByke);
        Animation flip;
        flip = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
        tv_msg_id.setAnimation(flip);
        tv_msg_id.startAnimation(flip);
    }
    @Override
    public void onStart() {
        super.onStart();
        img_bike.setOnClickListener(this);
        img_car.setOnClickListener(this);
        setAnimation();
        sizes();

    }
    /*
    onClick
     */
    @Override
    public void onClick(View v) {
        AndroidSupport support=new AndroidSupport();
        if(v.getId()==R.id.img_bike_id) {
            CLICKED_VEHICLE="bike";
            doVehicle("two");

        }else if(v.getId()==R.id.img_car_id){
            CLICKED_VEHICLE="Car";
            doVehicle("four");
        }else if(v.getId()==R.id.btn_sell_id){

        }else if(v.getId()==R.id.btn_ACCESSORIES_id){

           // startActivity(new Intent(getActivity(),Accessories.class));
        }else if(v.getId()==R.id.btn_LOGINMAIN_id){


        }
       // new DBBackUpAsyncTask(getActivity()).execute("");
    }
    private void checkData(){

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>1){
            if((grantResults[0]== PackageManager.PERMISSION_GRANTED)
                    &&(grantResults[1]==PackageManager.PERMISSION_GRANTED)
                    &&(grantResults[2]==PackageManager.PERMISSION_GRANTED)
                    &&(grantResults[2]==PackageManager.PERMISSION_GRANTED))
            {
            }else {
                requestToPermission();
            }
        }
    }

    /*
 requesting permission
  */

    private void requestToPermission() {

        final String[] permissions = {android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE,Manifest.permission.CALL_PHONE};

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle("Location required")
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), permissions, PERMISSION_REQUEST_FINE_LOCATION);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            android.app.AlertDialog alert11 = builder.create();
            alert11.show();
    }

    /*
    #doVehicleIdDownload perform the action of checking network
    #connection and download the vehicle id
     */
    private  int doVehicleIdDownload(String vehicletype){

        if(ACTIVE_BUTTON==2){
            if(ifVehicleAvailable(vehicletype)){
                if(vehicletype.equals("two")){
                    Intent  intent=new Intent(getActivity(),InsurancePolicy.class);
                    intent.putExtra("VKEY",CLICKED_VEHICLE);
                    startActivity(intent);
                    Log.i("Sending VKY"," "+CLICKED_VEHICLE);
                }else if(vehicletype.equals("four")){
                    Intent  intent=new Intent(getActivity(),InsurancePolicy.class);
                    intent.putExtra("VKEY",CLICKED_VEHICLE);
                    startActivity(intent);
                    Log.i("Sending VKY"," "+CLICKED_VEHICLE);
                }
            }else {
                new DoVehicleDownload(getActivity(),vehicletype,handler,ACTIVE_BUTTON).execute("");
            }
        }else {
            if(ifVehicleAvailable(vehicletype)){
                if(vehicletype.equals("two")){
                    startActivity(new Intent(getActivity(), BikeBrands.class));
                }else if(vehicletype.equals("four")){
                    startActivity(new Intent(getActivity(), CarBrands.class));
                }
            }else {
                new DoVehicleDownload(getActivity(),vehicletype,handler,ACTIVE_BUTTON).execute("");
            }
        }

        return  0;
    }
    private void doVehicle(String type){
        AndroidSupport support=new AndroidSupport();
        if(support.isConnected(getActivity())){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                if(ContextCompat.checkSelfPermission(getActivity(),android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    if (ACTIVE_BUTTON==SERVICING){//Garage Service
                        doVehicleIdDownload(type.trim());
                    } else if (ACTIVE_BUTTON == INSURANCE) {//Inserance
                                if(ifVehicleAvailable(type)){
                                    postAdd(INSURANCE,type);
                                }else {
                                    doVehicleIdDownload(type.trim());
                                }
                    }else if(ACTIVE_BUTTON==BUY){//Buy
                        vehicleKey=type;
                        Log.i("Values Places->>>>"," "+vehicleKey);
                        postAdd(BUY,type);
                    }else if(ACTIVE_BUTTON==SELL){//Sell
                        if(ifVehicleAvailable(type)){
                            postAdd(SELL,type);
                        }else {
                            doVehicleIdDownload(type.trim());
                        }
                    }
                }else {
                    requestToPermission();
                }
            }else {
               // doVehicleIdDownload(type.trim());
                if (ACTIVE_BUTTON==1){//Garage Service
                    doVehicleIdDownload(type.trim());
                } else if (ACTIVE_BUTTON == 2) {//Inserance
                    if(ifVehicleAvailable(type)){
                        postAdd(2,type);
                    }else {
                        doVehicleIdDownload(type.trim());
                    }
                }else if(ACTIVE_BUTTON==3){//Buy
                    postAdd(3,type);
                }else if(ACTIVE_BUTTON==4){//Sell
                    if(ifVehicleAvailable(type)){
                        postAdd(4,type);
                    }else {
                        doVehicleIdDownload(type.trim());
                    }
                }
            }
        }else {
            support.connectToNetWork(getActivity());
        }
    }
    private void postAdd(int i,String vehicleType){
        DBAdapter adapter=new DBAdapter(getActivity());
        if(i==BUY){//BUY VEHICLE
            Log.i(TAG,"VEHICLE MARKET..!!!!");
            if(vehicleType.equals("two")){
               // new DoAddsDownload(getActivity(),getActivity(),"bike",handler).execute("");
                Intent intent=new Intent(getActivity(),VehicleMarket.class);
                SavePrefe prefe=new SavePrefe();
                prefe.saveVehicleType("bike",getActivity());
                intent.putExtra("VT","bike");
                startActivity(intent);
            }else if (vehicleType.equals("four")){
                //new DoAddsDownload(getActivity(),getActivity(),"Car",handler).execute("");
                Intent intent=new Intent(getActivity(),VehicleMarket.class);
                SavePrefe prefe=new SavePrefe();
                prefe.saveVehicleType("Car",getActivity());
                intent.putExtra("VT","Car");
                startActivity(intent);
            }
        }else if(i==INSURANCE) {//INSURANCE
            if(vehicleType.equals("two")){
                Log.i("Sending..VKEY--","bike");
                Intent intent=new Intent(getActivity(),InsurancePolicy.class);
                intent.putExtra("VKEY",CLICKED_VEHICLE);
                startActivity(intent);
            }else if(vehicleType.equals("four")){
                Log.i("Sending..VKEY--","car");
                Intent intent=new Intent(getActivity(),InsurancePolicy.class);
                intent.putExtra("VKEY",CLICKED_VEHICLE);
                startActivity(intent);
            }

        }else{
            try{
                mcursor=null;
                mcursor=adapter.getTableInfo("USER");
                if(mcursor!=null){
                    mcursor.moveToFirst();
                    if (mcursor.getCount()>0){
                        if(i==BUY){
                            //startActivity(new Intent(getActivity(),VehicleMarket.class));
                        }else if(i==SELL){
                            if(vehicleType.equals("two")){
                                Intent intent=new Intent(getActivity(),SellVehicle.class);
                                intent.putExtra("VKEY","bike");
                                startActivity(intent);
                            }else if(vehicleType.equals("four")){
                                Intent intent=new Intent(getActivity(),SellVehicle.class);
                                intent.putExtra("VKEY","car");
                                startActivity(intent);
                            }
                        }
                    }else {
                        showAlert();
                    }
                }
            }catch (SQLException e ){
                e.printStackTrace();
            }
        }
    }
    private void showAlert(){
        CustomDialogClass dialogClass=new CustomDialogClass(getActivity());
        dialogClass.show();
    }
    /*
    Handeler
     */
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==COMPANY_ID_DOWNLOAD_SUCESS_TWO){
                    if(ACTIVE_BUTTON==INSURANCE){
                        Log.i("Sending VKY"," "+CLICKED_VEHICLE);
                        Intent intent=new Intent(getActivity(),InsurancePolicy.class);
                        intent.putExtra("VKEY",CLICKED_VEHICLE);
                        startActivity(intent);

                    }else {
                        startActivity(new Intent(getActivity(), BikeBrands.class));
                    }
            }else if(msg.what==COMPANY_ID_DOWNLOAD_SUCESS_FOUR){
                    if(ACTIVE_BUTTON==INSURANCE){
                        Log.i("Sending VKY"," "+CLICKED_VEHICLE);
                        Intent intent=new Intent(getActivity(),InsurancePolicy.class);
                        intent.putExtra("VKEY",CLICKED_VEHICLE);
                        startActivity(intent);

                    }else {
                        startActivity(new Intent(getActivity(), CarBrands.class));
                    }

            }else if(msg.what==COMPANY_ID_DOWNLOAD_UNSUCESS) {
                //TODO ERROR
            }else if(msg.what==VEHICLE_DOWNLOAD_SUCESS){
             //   startActivity(new Intent(getActivity(),VehicleMarket.class));
               // new DBBackUpAsyncTask(getActivity()).execute("");
            }else if(msg.what==VEHICLE_DOWNLOAD_UN_SUCESS){
                Toast.makeText(getActivity(),"Connection slow",Toast.LENGTH_SHORT).show();

            }else if(msg.what==22){
                postAdd(4,"two");
            }else if(msg.what==44){
                postAdd(4,"four");
            }
        }
    };
    /*
    @method is used to check the avaliblity of vehicle id(Data);
     */
    private boolean ifVehicleAvailable(String vehicleType){
        boolean b=false;
        Cursor cursor=null;
        DBAdapter adaptera=new DBAdapter(getActivity());
        try{
            cursor=adaptera.getTableInfo(TableBase.Table.Vehicle.VEHICLE,vehicleType);
            if(cursor!=null){
                cursor.moveToFirst();
                if(cursor.getCount()>0){
                    b=true;
                }
            }else {
                b=false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
        return b;
    }


}