package com.vecta.nuclearwheels;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidsupport.AndroidSupport;
import com.db_adapter.DBAdapter;

import com.listAdapters.bikeBeens.VehicleBuyBeen;
import com.requestUrl.ServerUrl;
import com.table_base.TableBase;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleInformationDetails extends AppCompatActivity {
    private String id="";
    private ImageView imageViewModel;
    private LinearLayout linImageLayout;
    private TextView tv_modelName,tv_modelPostdate,tv_kmsDriven,tv_fuel,tv_ownertype,tv_vehiclePrice,tv_vehicleDescription;
    private Button btnContact;
    private  String photoLinks="";
    private String OwnerId="";
    private int imId=0;
    private List<Integer> idList=new ArrayList<Integer>();
    private VehicleBuyBeen buyBeen;
    private CollapsingToolbarLayout collapsingToolbar;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    private AppBarLayout appBarLayout;
    private NestedScrollView nestedScrollView;
    public static String SELCECTED_VEHICLE_TYPE="";
    private Animator mCurrentAnimatorEffect;
    private int mShortAnimationDurationEffect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_information_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout=(AppBarLayout)findViewById(R.id.appBarlayout_ID);
        nestedScrollView=(NestedScrollView)findViewById(R.id.nested_scroll_id);
        imageViewModel=(ImageView)findViewById(R.id.imageViewModelId);
        linImageLayout=(LinearLayout)findViewById(R.id.linImageLayoutID);
        tv_modelName=(TextView)findViewById(R.id.tv_modelNameId);
        tv_modelPostdate=(TextView)findViewById(R.id.tv_modelPostdateID);
        tv_vehicleDescription=(TextView)findViewById(R.id.tv_vehicleDescriptionID);
        tv_kmsDriven=(TextView)findViewById(R.id.tv_kmsDrivenID);
        tv_fuel=(TextView)findViewById(R.id.tv_fuelID);
        tv_ownertype=(TextView)findViewById(R.id.tv_ownertypeID);
        tv_vehiclePrice=(TextView)findViewById(R.id.tv_vehiclePriceID);
        btnContact=(Button)findViewById(R.id.btn_vmarketDetailsID);
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showOwnerDialog();
            }
        });
        try{
            Intent intent=super.getIntent();
            Bundle bundle=intent.getExtras();
            buyBeen=(VehicleBuyBeen) bundle.getParcelable("v_data");
            getVehicleDetails(buyBeen);
            Toast.makeText(getApplicationContext(),""+id,Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }

        imageViewModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_SHORT).show();
                // nestedScrollView.setVisibility(View.GONE);
                //appBarLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                //imageViewModel.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                //Bitmap bitmap=imageViewModel.get
          //      zoomImageFromThumb(imageViewModel, R.drawable.car);
            }
        });
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    Toast.makeText(this, "left2right swipe", Toast.LENGTH_SHORT).show ();
                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void getVehicleDetails(VehicleBuyBeen vid){

        try{

            if(vid!=null){
                    tv_modelName.setText(vid.getBRAND_NAME()+" "
                            +vid.getMODEL_NAME());
                //collapsingToolbar.setTitle(tv_modelName.getText().toString());
             //   collapsingToolbar.setTitle("");
                collapsingToolbar.setTitleEnabled(false);
                    tv_modelPostdate.setText("Post "+vid.getPOSTDATE());
                    tv_vehicleDescription.setText(""+vid.getDESCRIPTION());
                    tv_kmsDriven.setText(""+vid.getTOTAL_KM());
                    tv_fuel.setText(""+vid.getFUEL_TYPE());
                    tv_ownertype.setText(""+vid.getONUM());
                    tv_vehiclePrice.setText("Rs."+vid.getYOUR_PRICE()+"/-");
                    photoLinks=vid.getLINK();
                    setImageView(photoLinks);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void showOwnerDialog(){

        try {
                    DialogOwner dialogOwner=new DialogOwner(VehicleInformationDetails.this,buyBeen.getO_NAME(),buyBeen.getO_NUMBER(),buyBeen.getO_EMAIL());
                    dialogOwner.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(VehicleInformationDetails.this,VehicleMarket.class));
        finish();
    }

    private void setImageView(String photoLinks){
        String[] links=photoLinks.split(",");
        int l=links.length;
        for(int a=0;a<l;a++){
            new LoadImages(links[a]).execute("");
        }
    }
    public class LoadImages extends AsyncTask<String,Void,Bitmap>{
    private String link="";
        String RETURN="";
        BufferedInputStream inputStream=null;
        BufferedReader reader = null;
        private LoadImages(String link){
            this.link=link;
        }
        @Override
        protected Bitmap doInBackground(String... params) {

            Bitmap myBitmap=null;
                try{
                   // String imgurl="http://www.nuclearwheels.com/".concat(link);
                    String imgurl= ServerUrl.imageUral.concat(link);
                    URL url=new URL(imgurl);
                    Log.i("URL=",""+link);
                    HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                    urlConnection.connect();
                    int status = urlConnection.getResponseCode();
                    if(status==200) {
                        InputStream input = urlConnection.getInputStream();
                       myBitmap= AndroidSupport.decodeFile(input,256);
                    }
                    }catch (Exception e){
                    e.printStackTrace();
                }

            return myBitmap;
        }
        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);
            //imageViewModel.setImageBitmap(s);
            final ImageView imageView=new ImageView(VehicleInformationDetails.this);

            imId=imId+1;
            LinearLayout boxlayout=new LinearLayout(VehicleInformationDetails.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity= Gravity.CENTER;
            layoutParams.setMargins(2,2,2,2);

            LinearLayout.LayoutParams layoutParamsbox = new LinearLayout.LayoutParams(400,ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParamsbox.gravity=Gravity.CENTER;
            layoutParamsbox.setMargins(10,10,10,10);
            boxlayout.setBackgroundResource(R.drawable.row_style_b);

            boxlayout.setLayoutParams(layoutParamsbox);
            imageView.setLayoutParams(layoutParams);
            imageView.setId(imId);
            imageView.setImageBitmap(s);

            boxlayout.addView(imageView);
            linImageLayout.addView(boxlayout);
            imageViewModel.setImageBitmap(s);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
                    imageViewModel.setImageBitmap(bitmap);
                }
            });
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }


}

