package com.abstractsupport;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.androidsupport.AndroidSupport;
import com.background.DoModelDownload;
import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.db_adapter.DBAdapter;
import com.listAdapters.bikeBeens.VehicleModelBeens;
import com.preferences_storage.SavePrefe;
import com.requestUrl.ServerUrl;
import com.table_base.TableBase;
import com.vecta.nuclearwheels.R;
import com.vecta.nuclearwheels.SubModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



/**
 * Created by Admin on 30-September-30-2016.
 */
public abstract class  Seller extends AppCompatActivity implements View.OnClickListener{
    private EditText eVehicleRegNo,eKMSDriven,ePrice,eCity,eLocility,especialInstruction;
    private String strBrandName,strModelName,strVehicleRegNo,strYearOfModel,strKMSDriven,strFualType,strAddress,strInsuranceDate,strPrice,strCity,strLocility,strOwner,strSpecialInstruction;
    private Button btnSubmit,btn_postAdd;
    private Spinner spBrand,spModel,spFuel,spOwnerProraty,spyearOfmodel,spInsuranceAvl;
    private ScrollView spSellerInformation,spPersonalInformation;
    private ImageView imgCemara,imgphoto,img_testImg;
    private static int RESULT_LOAD_IMG = 2;
     private  String imgDecodableString;
    private Activity mActivity;
    private CalendarView btn_validity;
    private TextView tv_insurance;
    private TextInputLayout input_eVehicleRegNo;
    private RelativeLayout relBrandName,relBrandModel,relModelYear,relInsurance,relOwnertype,relFuel;
    private String vehicleKey="";
    private   JSONObject sJObj=new JSONObject();
    private int BACKPROUNG=0;
    private int PICK_IMAGE_MULTIPLE=2000;
    private int PICK_CAMARA_IMAGE=2;
    private int PICK_CROP_IMAGE=3;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private ImageView mImageView;


   /* private String imageEncoded;
  static   private List<String> imagesEncodedList;*/
    private JSONObject imgObject;
    private int on=1;
    private int REQUEST_POST_IMAGES=2;
    private int REQUEST_POST_ADD=1;
    private LinearLayout img_liner;
    static private ArrayList<String> photoPathList =new ArrayList<String>();
    private String base64Str="";
   static  ArrayList<String> mArrayUri = new ArrayList<String>();
    private int PERMISSION_REQUEST_FINE_LOCATION=1;
    private String TAG="Sellere";



    protected void setContentViewOfSeller(final Activity mActivity,String vehicleKey){
        mActivity.setContentView(R.layout.activity_sell_vehicle);
        this.mActivity=mActivity;
        this.vehicleKey=vehicleKey;
        relBrandName=(RelativeLayout)findViewById(R.id.relBrandNameID);
        relBrandModel=(RelativeLayout)findViewById(R.id.relBrandModelID);
        relModelYear=(RelativeLayout)findViewById(R.id.relSelectYearID);
        relInsurance=(RelativeLayout)findViewById(R.id.relInsuranceID);
        tv_insurance=(TextView)findViewById(R.id.txt_insuranceValidDate);
        relOwnertype=(RelativeLayout)findViewById(R.id.relOwnerID);
        relFuel=(RelativeLayout)findViewById(R.id.relFualID);
        eKMSDriven=(EditText)findViewById(R.id.eKMSDrivenID);
        btn_postAdd=(Button)findViewById(R.id.btn_PostAddImgesID);
        btn_postAdd.setOnClickListener(this);
        img_liner=(LinearLayout)findViewById(R.id.lin_img_Id);
        spSellerInformation=(ScrollView)findViewById(R.id.spSellerInformationID);
        spPersonalInformation=(ScrollView)findViewById(R.id.spPersonalInformationID);
        eVehicleRegNo=(EditText)mActivity.findViewById(R.id.eVehicleRegNoID) ;
        input_eVehicleRegNo=(TextInputLayout)findViewById(R.id.input_eVehicleRegNoID) ;
        spBrand=(Spinner)mActivity.findViewById(R.id.sp_BrandSelection);
        spModel=(Spinner)mActivity.findViewById(R.id.sp_BrandModel);
        spFuel=(Spinner)mActivity.findViewById(R.id.sp_Fueltypel);
        spOwnerProraty=(Spinner)mActivity.findViewById(R.id.sp_ownerPriroty);
        btnSubmit=(Button)mActivity.findViewById(R.id.btnSubmitID);
        imgphoto=(ImageView)findViewById(R.id.ImgPhotoID);
        imgCemara=(ImageView)findViewById(R.id.ImgCameraID) ;
        spyearOfmodel=(Spinner)findViewById(R.id.sp_select_year);
        spInsuranceAvl=(Spinner)mActivity.findViewById(R.id.spInsuranceAvlId);
        btn_validity=(CalendarView) findViewById(R.id.btn_validityID);
        eCity=(EditText) findViewById(R.id.eVichleCityId);
        eLocility=(EditText)findViewById(R.id.eVehicleLocilatyId);
        ePrice=(EditText)findViewById(R.id.ePriceID);
        especialInstruction=(EditText)findViewById(R.id.especialInstructionId);
        btn_validity.setVisibility(View.GONE);
        tv_insurance.setVisibility(View.GONE);
        imgphoto.setOnClickListener(this);
        imgCemara.setOnClickListener(this);
        img_liner.removeAllViews();
        setpinnerOfBrand();
        setpinnerFuel();
        setpinnerOfOwner();
        setListner();
        setYearSpinner();
        setInsuranceSpinner();
        spSellerInformation.setVisibility(View.VISIBLE);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    if(isValied()){
                        getData();
                    }else {
                        Toast.makeText(mActivity.getApplicationContext(),"NOT DONE",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(validateApiLolipop()){
                        getData();
                    }else {
                        Toast.makeText(mActivity.getApplicationContext(),"NOT DONE",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        spBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               String brand= (String)parent.getItemAtPosition(position);
                if(!(brand.equals("--Select Brand--"))){
                    Toast.makeText(getApplicationContext(),""+brand,Toast.LENGTH_SHORT).show();
                    dowonloadModel(brand.trim());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        tv_insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_validity.setVisibility(View.VISIBLE);
            }
        });
        spInsuranceAvl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override//btn_validity
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item=(String)parent.getItemAtPosition(position);
                if(item.equals("Insurance Available")){
                    btn_validity.setVisibility(View.VISIBLE);
                    tv_insurance.setVisibility(View.VISIBLE);
                }else{
                    btn_validity.setVisibility(View.GONE);
                    tv_insurance.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_validity.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Log.i("DATE"," "+year+","+month+" "+dayOfMonth);
                tv_insurance.setText(""+dayOfMonth+":"+month+":"+year);
                btn_validity.setVisibility(View.GONE);
                tv_insurance.setTextSize(20);
            }
        });
    }
    private void dowonloadModel(String bike){
        DBAdapter adapter=new DBAdapter(getApplicationContext());
        if(vehicleKey.equalsIgnoreCase("bike")){
            try{
                String v_id=adapter.getBrandId(TableBase.Table.Vehicle.VEHICLE,bike,"two");
                if(!v_id.isEmpty()){
                    downloadAllsubModel(v_id);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(vehicleKey.equalsIgnoreCase("car")){
            try{
                String v_id=adapter.getBrandId(TableBase.Table.Vehicle.VEHICLE,bike,"four");
                if(!v_id.isEmpty()){
                    downloadAllsubModel(v_id);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    /*
    Download Model
     */
    private void downloadAllsubModel(String v_id ){
        AndroidSupport support=new AndroidSupport();
        try{
            if(support.isConnected(mActivity)){
                new DoModelDownload(mActivity,v_id,mHandler).execute("");
            }else {
                support.connectToNetWork(mActivity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*
    Handler
     */
    public Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(getApplication(),"DataNot Found ",Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    ArrayList<String> list=new ArrayList<String>();
                    list.add("-Select Model-");
                    for(VehicleModelBeens beens:SubModel.vModelList){
                        list.add(beens.getName());
                    }
                    setpinnerOfModel(list);
                    break;
            }
        }
    };
    private void setpinnerOfBrand(){
        ArrayList<String> list=new ArrayList<String>();
        DBAdapter adapter=new DBAdapter(getApplicationContext());

        if(vehicleKey.equalsIgnoreCase("car")){
            try{
                list= adapter.getvehicleList(TableBase.Table.Vehicle.VEHICLE,"four");
                ArrayAdapter<String> adapterBrand=new ArrayAdapter(mActivity,R.layout.row,list);
                spBrand.setAdapter(adapterBrand);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            try{
                list= adapter.getvehicleList(TableBase.Table.Vehicle.VEHICLE,"two");
                ArrayAdapter<String> adapterBrand=new ArrayAdapter(mActivity,R.layout.row,list);
                spBrand.setAdapter(adapterBrand);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private void setpinnerFuel(){
        String[] bykeBrand=getResources().getStringArray(R.array.fuel);
        ArrayAdapter<String> adapterBrand=new ArrayAdapter(mActivity,R.layout.row,bykeBrand);
        spFuel.setAdapter(adapterBrand);
    }
    private void setpinnerOfModel(ArrayList<String> list){
      //  String[] fual=getResources().getStringArray(R.array.fuel);
        ArrayAdapter<String> adapterBrand=new ArrayAdapter(mActivity,R.layout.row,list);
        spModel.setAdapter(adapterBrand);
    }
    private void setpinnerOfOwner(){
        String[] bykeBrand=getResources().getStringArray(R.array.spOwnerPrioraty);
        ArrayAdapter<String> adapterBrand=new ArrayAdapter(mActivity,R.layout.row,bykeBrand);
        spOwnerProraty.setAdapter(adapterBrand);
    }//spyearOfmodel
    private void setYearSpinner(){
        String[] year=getResources().getStringArray(R.array.MODEL_YEAR);
        ArrayAdapter<String> adapterBrand=new ArrayAdapter(mActivity,R.layout.row,year);
        spyearOfmodel.setAdapter(adapterBrand);
    }
    private void setInsuranceSpinner(){
        String[] insurance=getResources().getStringArray(R.array.insurance_status);
        ArrayAdapter<String> adapterBrand=new ArrayAdapter(mActivity,R.layout.row,insurance);
        spInsuranceAvl.setAdapter(adapterBrand);
    }
    /*private void setYearSpinner(){
        String[] year=getResources().getStringArray(R.array.MODEL_YEAR);
        ArrayAdapter<String> adapterBrand=new ArrayAdapter(mActivity,R.layout.row,year);
        spyearOfmodel.setAdapter(adapterBrand);
    }*/


    private void setListner(){
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.ImgCameraID){
            takeCamaraImage();
        }else if(v.getId()==R.id.ImgPhotoID){
           /* Intent intent = new Intent();
            intent.setType("image*//*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image*//*");
            intent.putExtra("crop", "true");
            intent.putExtra("scale", true);
            intent.putExtra("outputX", 256);
            intent.putExtra("outputY", 256);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("return-data", true);
            startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);*/
            Intent intent = new Intent(this, AlbumSelectActivity.class);
//set limit on number of images that can be selected, default is 10
            intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 4);
            startActivityForResult(intent, Constants.REQUEST_CODE);
        }else if(v.getId()==R.id.btn_PostAddImgesID){
           sendImagesToServer();
        }
    }

    protected  boolean isValied(){
            boolean v=true;
         strBrandName=(String)spBrand.getSelectedItem();
        if(!(strBrandName.equals("--Select Brand--"))){
            v=true;
            relBrandName.setBackground(getDrawable(R.drawable.myrectangle));
        }else{
            v=false;
            relBrandName.setBackground(getDrawable(R.drawable.spinnererror));
        }
        try{
            strModelName=(String)spModel.getSelectedItem();
            if(!(strModelName.equals("-Select Model-"))){
                v=true;
                relBrandModel.setBackground(getDrawable(R.drawable.myrectangle));
            }else{
                relBrandModel.setBackground(getDrawable(R.drawable.spinnererror));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if (eVehicleRegNo.getText().toString().length() > 3) {
            v=true;
        }else{
         /*   input_eVehicleRegNo.setErrorEnabled(true);
            input_eVehicleRegNo.setError("Enter Vehicle Number");*/
        }

        strYearOfModel=(String)spyearOfmodel.getSelectedItem();
        if(!(strYearOfModel.equals("--select Year of model --"))){
            v=true;
            relModelYear.setBackground(getDrawable(R.drawable.myrectangle));
        }else{
            relModelYear.setBackground(getDrawable(R.drawable.spinnererror));
        }
        strPrice=ePrice.getText().toString();
        if(!(strPrice.isEmpty())){
            v=true;
        }else{
            //TODO ERROR
        }
      /*  if(spInsuranceAvl.getItemAtPosition(0).equals("--Insurance Status--")){
            v=false;
            relInsurance.setBackground(getDrawable(R.drawable.spinnererror));
        }else{
           v=true;
            relInsurance.setBackground(getDrawable(R.drawable.myrectangle));
        }*/
        if(eCity.getText().toString().length()>2){
            v=true;
        }else{
            v=false;
        }
        if(eLocility.getText().toString().length()>2){
            v=true;
        }else{
            v=false;
        }
        if(especialInstruction.getText().toString().length()>2){
            v=true;
        }else{
            v=false;
        }
        String fuel=(String)spFuel.getSelectedItem();
        if(fuel.equals("Select Fuel Type-")){
            v=false;
            relFuel.setBackground(getDrawable(R.drawable.spinnererror));
        }else{
            v=true;
            relFuel.setBackground(getDrawable(R.drawable.myrectangle));

        }
        String owner=(String)spOwnerProraty.getSelectedItem();
       /* if(owner.equals("Select Owner Type")){
            v=true;
            relOwnertype.setBackground(getDrawable(R.drawable.myrectangle));
        }else{
            v=false;
            relOwnertype.setBackground(getDrawable(R.drawable.spinnererror));
        }*/
            return v;
        }

    /*
    pravte vol2
     */
    protected  boolean validateApiLolipop(){
        boolean v=true;
        strBrandName=(String)spBrand.getSelectedItem();
        if(!(strBrandName.equals("--Select Brand--"))){
            v=true;
            relBrandName.setBackground(getResources().getDrawable(R.drawable.myrectangle));
        }else{
            v=false;
            relBrandName.setBackground(getResources().getDrawable(R.drawable.spinnererror));
        }
        try{
            strModelName=(String)spModel.getSelectedItem();
            if(!(strModelName.equals("-Select Model-"))){
                v=true;

                relBrandModel.setBackground(getResources().getDrawable(R.drawable.myrectangle));
            }else{
                relBrandModel.setBackground(getResources().getDrawable(R.drawable.spinnererror));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if (eVehicleRegNo.getText().toString().length() > 3) {
            v=true;
        }else{
         /*   input_eVehicleRegNo.setErrorEnabled(true);
            input_eVehicleRegNo.setError("Enter Vehicle Number");*/
        }

        strYearOfModel=(String)spyearOfmodel.getSelectedItem();
        if(!(strYearOfModel.equals("--select Year of model --"))){
            v=true;
            relModelYear.setBackground(getResources().getDrawable(R.drawable.myrectangle));
        }else{
            relModelYear.setBackground(getResources().getDrawable(R.drawable.spinnererror));
        }
        strPrice=ePrice.getText().toString();
        if(!(strPrice.isEmpty())){
            v=true;
        }else{
            //TODO ERROR
        }
      /*  if(spInsuranceAvl.getItemAtPosition(0).equals("--Insurance Status--")){
            v=false;
            relInsurance.setBackground(getDrawable(R.drawable.spinnererror));
        }else{
           v=true;
            relInsurance.setBackground(getDrawable(R.drawable.myrectangle));
        }*/
        if(eCity.getText().toString().length()>2){
            v=true;
        }else{
            v=false;
        }
        if(eLocility.getText().toString().length()>2){
            v=true;
        }else{
            v=false;
        }
        if(especialInstruction.getText().toString().length()>2){
            v=true;
        }else{
            v=false;
        }
        String fuel=(String)spFuel.getSelectedItem();
        if(fuel.equals("Select Fuel Type-")){
            v=false;
            relFuel.setBackground(getResources().getDrawable(R.drawable.spinnererror));
        }else{
            v=true;
            relFuel.setBackground(getResources().getDrawable(R.drawable.myrectangle));

        }
        String owner=(String)spOwnerProraty.getSelectedItem();
       /* if(owner.equals("Select Owner Type")){
            v=true;
            relOwnertype.setBackground(getDrawable(R.drawable.myrectangle));
        }else{
            v=false;
            relOwnertype.setBackground(getDrawable(R.drawable.spinnererror));
        }*/
        return v;
    }


    private void getData(){
        strBrandName=(String)spBrand.getSelectedItem();Log.i("BrandName",""+strModelName);
        strModelName=(String)spModel.getSelectedItem();Log.i("BrandName",""+strModelName);
        strFualType=(String)spFuel.getSelectedItem();Log.i("BrandName",""+strFualType);
        strYearOfModel=(String)spyearOfmodel.getSelectedItem();Log.i("BrandName",""+strYearOfModel);
        strOwner=(String)spOwnerProraty.getSelectedItem();Log.i("BrandName",""+strOwner);
        strKMSDriven=eKMSDriven.getText().toString();Log.i("BrandName",""+strKMSDriven);
        strPrice=ePrice.getText().toString();Log.i("BrandName",""+strPrice);
        strCity=eCity.getText().toString();Log.i("BrandName",""+strCity);
        strLocility=eLocility.getText().toString();Log.i("BrandName",""+strLocility);
        strInsuranceDate="0000";Log.i("BrandName",""+strInsuranceDate);
        strVehicleRegNo=eVehicleRegNo.getText().toString();Log.i("BrandName",""+strVehicleRegNo);
        strSpecialInstruction=especialInstruction.getText().toString();Log.i("Instruction",""+strSpecialInstruction);

        try{
            sJObj.put("type",vehicleKey);
            sJObj.put("brand",strBrandName);
            sJObj.put("model",strModelName);
            sJObj.put("rno",strVehicleRegNo);
            sJObj.put("year",strYearOfModel);
            sJObj.put("iex","false");
            sJObj.put("date","2016");
            sJObj.put("km",strKMSDriven);
            sJObj.put("fuel",strFualType);
            sJObj.put("price",strPrice);
            sJObj.put("now","1");
            sJObj.put("city",strCity);
            sJObj.put("area",strLocility);
            sJObj.put("desc",strSpecialInstruction);
            sJObj.put("uid",getUserId());
          // sJObj.put("uid","USR-2016-028");
            new  RegisterAdd(REQUEST_POST_ADD).execute("");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    /*
    getUser Id
     */
    public String getUserId(){
        String user="";
        try {
            DBAdapter adapter=new DBAdapter(getApplicationContext());
            Cursor mCursor=adapter.getTableInfo(TableBase.Table.USERTABLE.USER);
            if(mCursor!=null){
                mCursor.moveToFirst();
                while (mCursor.isAfterLast()==false){
                     user=mCursor.getString(mCursor.getColumnIndex(TableBase.Table.USERTABLE.SER_USER_ID));
//                    Toast.makeText(getApplicationContext()," "+user,Toast.LENGTH_SHORT).show();
                    mCursor.moveToNext();
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    /*
    Add Id
     */
    public String getAddId(){
        String user="";
        try {
            DBAdapter adapter=new DBAdapter(getApplicationContext());
            Cursor mCursor=adapter.getTableInfo(TableBase.Table.USERTABLE.USER);
            if(mCursor!=null){
                mCursor.moveToFirst();
                while (mCursor.isAfterLast()==false){
                    user=mCursor.getString(mCursor.getColumnIndex(TableBase.Table.USERTABLE.SER_USER_ID));
                    Toast.makeText(getApplicationContext()," "+user,Toast.LENGTH_SHORT).show();
                    mCursor.moveToNext();
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if((requestCode==PICK_IMAGE_MULTIPLE)&&(resultCode==Activity.RESULT_OK)){
            try{
                //image_uris = data.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
                ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
                int s=images.size();
                photoPathList.clear();
                for(int a=0;a<s;a++){

                  Image in= images.get(a);
                  //  Log.i("LL"," "+in.path.toString());
                    photoPathList.add(in.path.toString());
                }
                for(int a = 0; a< photoPathList.size(); a++){
                    File file=new File(photoPathList.get(a));
                    Bitmap bmImg=decodeFile(file,60,60);
                    ImageView view=new ImageView(getApplicationContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.setMargins(4,4,4,4);
                    view.setLayoutParams(layoutParams);
                    view.setImageBitmap(bmImg);
                    img_liner.addView(view);
                }
                if(!photoPathList.isEmpty()){
                    btn_postAdd.setVisibility(View.VISIBLE);
                }else{
                    btn_postAdd.setVisibility(View.GONE);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
         }else if((requestCode==PICK_CAMARA_IMAGE)&&(resultCode==Activity.RESULT_OK)){
            photoPathList.add(mCurrentPhotoPath);
            File file=new File(mCurrentPhotoPath);
            Bitmap bmImg=decodeFile(file,125,125);
            ImageView view=new ImageView(getApplicationContext());
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(250, ViewGroup.LayoutParams.MATCH_PARENT);
            param.setMargins(4,4,4,4);
            view.setLayoutParams(param);
            view.setImageBitmap(bmImg);
            img_liner.addView(view);
            if(!photoPathList.isEmpty()){
                btn_postAdd.setVisibility(View.VISIBLE);
            }else{
                btn_postAdd.setVisibility(View.GONE);
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private static Bitmap decodeFile(File f,int WIDTH,int HIGHT){
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(new FileInputStream(f),null,o);

            //The new size we want to scale to
            final int REQUIRED_WIDTH=WIDTH;
            final int REQUIRED_HIGHT=HIGHT;
            //Find the correct scale value. It should be the power of 2.
            int scale=1;
            while(o.outWidth/scale/2>=REQUIRED_WIDTH && o.outHeight/scale/2>=REQUIRED_HIGHT)
                scale*=2;

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e1){
            e1.printStackTrace();
        }
        return null;
    }
    /*
    image Url
     */

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null,
                    null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    private void sendImagesToServer(){
        try{

            imgObject=null;
            imgObject=new JSONObject();
            SavePrefe prefe=new SavePrefe();
            String ONVID=prefe.getOrderId(mActivity);
            imgObject.put("id", ONVID);
            imgObject.put("img",photoPathList);
            new RegisterAdd(RESULT_LOAD_IMG).execute("");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private class RegisterAdd extends AsyncTask<String,Void,String>{
        private ProgressDialog progressDialog;
        private BufferedReader reader = null;
        private int REQUEST_POST;

        private RegisterAdd(int request){
            this.REQUEST_POST=request;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("200")){
                progressDialog.dismiss();
                spPersonalInformation.setVisibility(View.VISIBLE);
                spSellerInformation.setVisibility(View.GONE);
                btn_postAdd.setVisibility(View.INVISIBLE);

            }else if(s.equals("400")){
                progressDialog.dismiss();
                spPersonalInformation.setVisibility(View.GONE);
                spSellerInformation.setVisibility(View.VISIBLE);
                btn_postAdd.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"Error While Posting Add please try letter..!",Toast.LENGTH_SHORT).show();
            }else if(s.equals("11")){
                Toast.makeText(getApplicationContext(),"Your add Request is Submited Sucessfully..! ",Toast.LENGTH_SHORT).show();
                finish();
                mActivity.finish();
            }else if(s.equals("12")){
                Toast.makeText(getApplicationContext(),"Request error please try after Some time..! ",Toast.LENGTH_SHORT).show();
                mActivity.finish();
            }
        }
        @Override
        protected String doInBackground(String... params) {
            String returnval="";
            if(REQUEST_POST==1){
                try {
                  //  URL url=new URL(ServerUrl.LOCAL_HOST+"booking/saveSellVehicle");
                    URL url=new URL(ServerUrl.serverurl+"booking/saveSellVehicle");
                    HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    //urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    urlConnection.setUseCaches(false);
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("dataType", "json");
                    urlConnection.connect();
                    Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
                    writer.write(sJObj.toString());
                    writer.close();
                    int status = urlConnection.getResponseCode();
                    Log.i("RESPONSE"," "+status);
                    BufferedInputStream in;
                    if (status >= 400 ) {
                        returnval="400";
                        in = new BufferedInputStream( urlConnection.getErrorStream() );
                        Log.i("",""+in);
                    } else {
                        in = new BufferedInputStream( urlConnection.getInputStream() );
                        Log.i("",""+in);
                    }
                    if(status==200){
                        StringBuffer buffer = new StringBuffer();
                        reader = new BufferedReader(new InputStreamReader(in));
                        String inputLine;
                        while ((inputLine = reader.readLine()) != null){
                            buffer.append(inputLine + "\n");
                        }
                     if( handelAdd(buffer.toString())==1){
                         returnval="200";
                         Log.i("ID",""+in.toString());
                     }else {
                         returnval="400";
                         Log.i("ID",""+in.toString());
                     }
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }

            }else if(REQUEST_POST==REQUEST_POST_IMAGES){
                try {
                  int a=  sendMultiPart(progressDialog,mActivity);
                    if(a==1){
                        returnval="11";
                    }else {
                        a=0;
                        returnval="12";
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
            return returnval;
        }
        /*
        Insert Add Id
         */

        private int handelAdd(String response){
            Log.i("#####3"," "+response);
            int i=0;
            try{
                JSONObject object=new JSONObject(response);
                if(object.has("STATUS")){
                    //{"MSG":"Success/BPOVH-2017-0167","STATUS":200}

                    String st=object.getString("STATUS");
                    if(st.equals("200")){
                        if(object.has("MSG")){
                            String re=object.getString("MSG");
                            String[] parts=re.split("/");
                            String sucessflag=parts[0];
                            String oid=parts[1];
                            SavePrefe  prefe=new SavePrefe();
                            prefe.saveOrderId(oid,mActivity);
                            i= 1;
                        }
                    }else{
                        i= 0;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return i;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(mActivity);
            progressDialog.setTitle("Posting Your  Add..!");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }
    public static int sendMultiPart(ProgressDialog progressDialog,Activity mActivity) throws Exception {
        String names="";
        int i=0;
        for (String file:photoPathList) {
            File ff=new File(file);
            if(i==0){
                names=ff.getName();
            }else{
                names+=","+ff.getName();
            }
            i++;
        }
        SavePrefe prefe=new SavePrefe();
        String ONVID=prefe.getOrderId(mActivity);
        Log.i("## ID->"," "+ONVID);
        final String end = "\r\n";
        final String twoHyphens = "--";
        final String boundary = "*****++++++************++++++++++++";
        URL url=new URL(ServerUrl.serverurl+"booking/saveSellVehicleImages?id="+ONVID+"&names="+names);//SERVER TEST
      //  URL url = new URL(ServerUrl.LOCAL_HOST+"booking/saveSellVehicleImages?id="+ONVID+"&names="+names);//LOCAL TEST
        Log.i("URL POST IMAGES"," "+url.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        /* setRequestProperty */
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
        DataOutputStream ds = null;
        FileInputStream fStream = null;
        byte[] buffer = null;
        int bufferSize = 1024;
        for (String file:photoPathList) {
            ds = new DataOutputStream(conn.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"from\"" + end + end + "auto" + end);
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"to\"" + end + end + "ja" + end);
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"uploadedFile\";filename=\"" +file+ "\"" + end);
            ds.writeBytes(end);
            fStream = new FileInputStream(file);
            buffer = new byte[bufferSize];
            int length = -1;

            while ((length = fStream.read(buffer)) != -1) {
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
        }
        /* close streams */
        fStream.close();
        ds.flush();
        ds.close();
        StringBuffer b = new StringBuffer();
        InputStream is = conn.getInputStream();
        byte[] data = new byte[bufferSize];
        int leng = -1;
        while ((leng = is.read(data)) != -1) {
            b.append(new String(data, 0, leng));
        }
        String result = b.toString();
        Log.i("Result Is *****","" + result);
        progressDialog.dismiss();
    return 1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>1){
            if((grantResults[0]== PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(getApplicationContext(),"Camara Premission Granted..!!",Toast.LENGTH_SHORT).show();
                takeCamaraImage();
            }else {
                requestToPermission();

            }
        }

    }
    private boolean doesUserHavePermission()
    {
        int result = mActivity.checkCallingOrSelfPermission(Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    private void takeCamaraImage(){
        if(doesUserHavePermission()){
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    Log.i(TAG, "IOException");
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(cameraIntent, PICK_CAMARA_IMAGE);
                }
            }
        }else {
                requestToPermission();
        }
    }
    /*
    Create File
     */
    private File createImageFile() throws IOException {
        // Create an image file name
         String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath =  image.getAbsolutePath();
        return image;
    }
    private void requestToPermission() {
        final String[] permissions = {Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(mActivity, permissions, PERMISSION_REQUEST_FINE_LOCATION);
    }

}

