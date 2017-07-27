package com.db_adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.listAdapters.AccessoriesBeen.ProductBrandBeen;
import com.listAdapters.AccessoriesBeen.ProductCatageoryBeen;
import com.listAdapters.AccessoriesBeen.ProductSubCategoryBeen;
import com.listAdapters.bikeBeens.BrandBeen;
import com.listAdapters.bikeBeens.FacilityBeen;
import com.listAdapters.bikeBeens.GaragAddress;
import com.listAdapters.bikeBeens.GarageBeen;
import com.listAdapters.bikeBeens.JserviceCenterBeen;
import com.listAdapters.bikeBeens.ServiceBeen;
import com.listAdapters.bikeBeens.UserInfoBeen;
import com.listAdapters.bikeBeens.VehicleBuyBeen;
import com.listAdapters.bikeBeens.VehicleOwnerBeen;
import com.table_base.TableBase;

import java.math.BigInteger;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by priyatam on 09-03-2016.
 */
public class DBAdapter {
    private String DATABASENAME = "BikeSeva";
    //private int DATABASE_VERSION = 19;
    private int DATABASE_VERSION = 21;
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Context mContext;
    private  String TAG="DBADAPTER";
    /*----------------
    Static Values for Some Product
     */
    private static int REQUEST_FAVORITE=0;
    private static int REQUEST_CART_CHECK=1;

    public DBAdapter(Context mContext) {
        this.mContext = mContext;
        dbHelper = new DBHelper(mContext, DATABASENAME, null, DATABASE_VERSION);
        Log.i(TAG," In Cunstru...!!!");
    }

    public class DBHelper extends SQLiteOpenHelper {


        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(TableBase.CREATETABLE.USER);
            db.execSQL(TableBase.CREATETABLE.BOOK_MASTER);
            db.execSQL(TableBase.CREATETABLE.VEHICLE);
            db.execSQL(TableBase.CREATETABLE.VEHICLE_TYPE);
            db.execSQL(TableBase.CREATETABLE.BOOK_VEHICLE);
            db.execSQL(TableBase.CREATETABLE.CITY);
            db.execSQL(TableBase.CREATETABLE.COUNTRY);
            db.execSQL(TableBase.CREATETABLE.EMPLOYEE);
            db.execSQL(TableBase.CREATETABLE.GARAGE);
            db.execSQL(TableBase.CREATETABLE.GARAGE_ADDRESS);
            db.execSQL(TableBase.CREATETABLE.GARAGE_FACILITY);
            db.execSQL(TableBase.CREATETABLE.GARAGE_OWNER);
            db.execSQL(TableBase.CREATETABLE.GARAGE_SERVICE);
            db.execSQL(TableBase.CREATETABLE.RESERVATION);
            db.execSQL(TableBase.CREATETABLE.SEQGEN);
            db.execSQL(TableBase.CREATETABLE.STATE);
            db.execSQL(TableBase.CREATETABLE.USER_REVIEW);
            db.execSQL(TableBase.CREATETABLE.VEHICLE_GARAGE);
            db.execSQL(TableBase.CREATETABLE.VEHICLE_MODEL);
            db.execSQL(TableBase.CREATETABLE.OWNER_VEHICLE_INFO);
            db.execSQL(TableBase.CREATETABLE.PRODUCT_BRAND);
            db.execSQL(TableBase.CREATETABLE.PRODUCT_CATEGORIES);
            db.execSQL(TableBase.CREATETABLE.PRODUCT_SUB_CATEGORIES);
            db.execSQL(TableBase.CREATETABLE.CART_DETLS);
            Log.i("Table created..!!", ">>" + db.getPath());
            db.execSQL(TableBase.CREATETABLE.OWNER);
            Log.i("Table created..!!", ">>" + db.getPath());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String SQLSTMT = "DROP TABLE IF EXISTS ";
            db.execSQL(SQLSTMT + TableBase.Table.BookMaster.BOOK_MASTER);
            Log.i("TABLE UPDATED", " TABLE UPDATED");
            // db.execSQL(SQLSTMT+TableBase.Table.USERTABLE.USER);
           // db.execSQL(SQLSTMT + TableBase.Table.Vehicle.VEHICLE);
         //   db.execSQL(SQLSTMT + TableBase.Table.BookVehicle.BOOK_VEHICLE);
          //  db.execSQL(SQLSTMT + TableBase.Table.City.CITY);
            //db.execSQL(SQLSTMT + TableBase.Table.Employee.EMPLOYEE);
           // db.execSQL(SQLSTMT + TableBase.Table.Country.COUNTRY);
           // db.execSQL(SQLSTMT + TableBase.Table.Garage.GARAGE);
          //  db.execSQL(SQLSTMT + TableBase.Table.GarageAddress.GARAGE_ADDRESS);
          //  db.execSQL(SQLSTMT + TableBase.Table.GarageService.GARAGE_SERVICE);
          //  db.execSQL(SQLSTMT + TableBase.Table.GarageFacility.GARAGE_FACILITY);
         //   db.execSQL(SQLSTMT + TableBase.Table.GarageOwner.GARAGE_OWNER);
         //   db.execSQL(SQLSTMT + TableBase.Table.VehicleGarage.VEHICLE_GARAGE);
         //   db.execSQL(SQLSTMT + TableBase.Table.VehicleModel.VEHICLE_MODEL);
         //   db.execSQL(SQLSTMT + TableBase.Table.VehicleType.VEHICLE_TYPE);
            db.execSQL(SQLSTMT + TableBase.Table.SeqGen.SEQGEN);
            db.execSQL(SQLSTMT + TableBase.Table.Reservation.RESERVATION);
            db.execSQL(SQLSTMT + TableBase.Table.State.STATE);
            db.execSQL(SQLSTMT + TableBase.Table.UserReview.USER_REVIEW);
            db.execSQL(SQLSTMT + TableBase.Table.OWNER_VEHICLE_INFO.OWNER_VEHICLE_INFO);
            Log.i("TABLE DELETED","@@@@@@ ");

            onCreate(db);
        }
    }

    /*
     get  Information of table in cursor #Should pass the table name in given parameter
     @@@@pamam Constructor for two usaes
     */
    public Cursor getTableInfo(String tableName) throws SQLException{
        Cursor mCursor;
        db=dbHelper.getWritableDatabase();
        mCursor=db.query(false,tableName,null,null,null,null,null,null,null);

        return  mCursor;
    }
    public Cursor getTableInfo(String tableName,String where) throws SQLException{
        Cursor mCursor;
        db=dbHelper.getWritableDatabase();
        if(tableName.equals(TableBase.Table.GarageAddress.GARAGE_ADDRESS)){
            mCursor=db.query(false,tableName,null,TableBase.Table.GarageAddress.GARAGE_ID+"=?",new String[]{where},null,null,null,null);
        }else {
            mCursor=db.query(false,tableName,null,TableBase.Table.Vehicle.VEHICLE_TYPE_ID+"=?",new String[]{where},null,null,null,null);
        }
        return  mCursor;
    }
    /*
    getGarageServices
     */
    public Cursor getGarajeServices(String tableName,String where) throws SQLException{
        Cursor mCursor;
        db=dbHelper.getWritableDatabase();
            mCursor=db.query(false,tableName,null,TableBase.Table.GarageService.GARAGE_ID+"=?",new String[]{where},null,null,null,null);
        return  mCursor;
    }
    /*
    get Owner Inormation
     */
    public  Cursor getOwnerInfo(String tableName,String id){
        Cursor mCursor=null;
        db=dbHelper.getWritableDatabase();
        mCursor=db.query(false,tableName,null, TableBase.Table.OWNER.OWNERID+"=?",new String[]{id},null,null,null,null);
        if(mCursor!=null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public Cursor getvehicleById(String tableName,String where) throws SQLException{
        Cursor mCursor;
        db=dbHelper.getWritableDatabase();
            mCursor=db.query(false,tableName,null,"id=?",new String[]{where},null,null,null,null);
        return  mCursor;
    }
    public ArrayList<String> getvehicleList(String tableName,String where) throws SQLException{
        ArrayList<String> arrayList=new ArrayList<String>();
        Cursor mCursor;
        db=dbHelper.getWritableDatabase();
        mCursor=db.query(false,tableName,null, TableBase.Table.Vehicle.VEHICLE_TYPE_ID+"=?",new String[]{where},null,null,null,null);
        if(mCursor!=null){
            mCursor.moveToFirst();
            arrayList.add("-Select Brand-");
            while (mCursor.isAfterLast()==false){
                arrayList.add(mCursor.getString(mCursor.getColumnIndex(TableBase.Table.Vehicle.VEHICLE_NAME)));
                mCursor.moveToNext();
            }
        }
        return  arrayList;
    }
                /*----------------------------*
                *-------Accessories part----- *
                *-----------------------------*/
    /*
    Insert values into PRODUCTCATEGORY
     */
    public long insertIntoProductCategory(ProductCatageoryBeen sb) throws SQLDataException{

        long l=0;
        ContentValues cv = new ContentValues();
        cv.put(TableBase.Table.PRODUCT_CATEGORIES.ID,sb.getID());
        cv.put(TableBase.Table.PRODUCT_CATEGORIES.NAME,sb.getNAME());
        db=dbHelper.getWritableDatabase();
        l=db.insert(TableBase.Table.PRODUCT_CATEGORIES.PRODUCT_CATEGORIES,null,cv);
        if(l>0){
            return l;
        }
        return l;
    }
    /*
    Insert Values in Product Barand Table
     */

    public  long insertIntoProdectBrand(ProductBrandBeen pb) throws SQLException{
        long l=0;
        ContentValues cv=new ContentValues();
        cv.put(TableBase.Table.PRODUCT_BRAND.ID,pb.getID());
        cv.put(TableBase.Table.PRODUCT_BRAND.NAME,pb.getNAME());
        cv.put(TableBase.Table.PRODUCT_BRAND.PRODUCT_SUB_CATEGORIES_ID,pb.getPRODUCT_SUB_CATEGORY_ID());
        db=dbHelper.getWritableDatabase();
        l=db.insert(TableBase.Table.PRODUCT_BRAND.PRODUCT_BRAND,null,cv);
        if(l>0){
            return l;
        }
        return l;
    }
    /*
    Insert Values in Product Sub Categorey pass the Model class of ProductSubCategoryBeen
     */
    public long insertIntoProductSubCategory(ProductSubCategoryBeen psb) throws SQLException{

        long l=0;
        ContentValues cv=new ContentValues();
        cv.put(TableBase.Table.PRODUCT_SUB_CATEGORIES.ID,psb.getID());
        cv.put(TableBase.Table.PRODUCT_SUB_CATEGORIES.SUB_CATEGORIES_NAME,psb.getSUB_CATEGORY_NAME());
        cv.put(TableBase.Table.PRODUCT_SUB_CATEGORIES.PRODUCT_CATEGORIES_ID,psb.getCATEGORY_ID());
        db=dbHelper.getWritableDatabase();
        l=db.insert(TableBase.Table.PRODUCT_SUB_CATEGORIES.PRODUCT_SUB_CATEGORIES,null,cv);
        if(l>0){
            return l;
        }
        return l;
    }
    /*
    get Item from product_sub_category
     */
    public Cursor getSubCategorys(String id) throws SQLException{
        Cursor mCursor=null;
        db=dbHelper.getWritableDatabase();
        mCursor=db.query(false,TableBase.Table.PRODUCT_SUB_CATEGORIES.PRODUCT_SUB_CATEGORIES,null,TableBase.Table.PRODUCT_SUB_CATEGORIES.PRODUCT_CATEGORIES_ID+"=?",new String[]{id},null,null,null,null);
        return mCursor;
    }

    /* Method return a filterd Result of  vehicle  i.e type two or type four
     */

    public  Cursor getFilterdResult(String brandNae,String modelName,String fuelType,String yearofModel,int fromprice,int toPrice,int single,int requestCode){
        int ALL_TYPE_OF_VEHICLE=1;

        int BMK=3;
        int BRAND_AND_MODEL=4;
        int BRANDK=5;
        int BRAND_AND_MODEL_YEAR_PRICE=6;
        int BRAND_AND_MODEL_YEAR_PRICE_FEUL=7;
        int BRAND_AND_MODEL_YEAR_PRICE_FEUL_KMS=8;
        int ALL_TYPE_OF_REQ=10;
        int ALL_TYPE_OF_REQQ=11;
        Cursor mCursor=null;
        db=dbHelper.getWritableDatabase();
        //select * from owner_vehicle_info where BRAND_NAME='Honda' AND MODEL_NAME='Activa 125' AND FUEL_TYPE='petrol' AND YOUR_PRICE BETWEEN 1000 AND 3000


        if(requestCode==ALL_TYPE_OF_VEHICLE){

            if(single==1){
                String sqlstmt="select * from owner_vehicle_info where BRAND_NAME='"+brandNae+"' AND MODEL_NAME='"+modelName+"' AND FUEL_TYPE LIKE '"+
                        fuelType+ "%' AND YEAR_OF_MODEL='"+yearofModel+"' AND YOUR_PRICE <"+fromprice;
                mCursor= db.rawQuery(sqlstmt,null);
            }else if(single==2){
                String sqlstmt="select * from owner_vehicle_info where BRAND_NAME='"+brandNae+"' AND MODEL_NAME='"+modelName+"' AND FUEL_TYPE LIKE '"+
                        fuelType+ "%' AND YEAR_OF_MODEL='"+yearofModel+"' AND YOUR_PRICE > "+toPrice;
                mCursor= db.rawQuery(sqlstmt,null);
            }else if (single==0){
                String sqlstmt="select * from owner_vehicle_info where BRAND_NAME='"+brandNae+"' AND MODEL_NAME='"+modelName+"' AND FUEL_TYPE LIKE '"+
                        fuelType+ "%' AND YEAR_OF_MODEL='"+yearofModel+"' AND YOUR_PRICE>"+fromprice;
                mCursor= db.rawQuery(sqlstmt,null);
            }
        }else if(requestCode==BRANDK){
            String sqlstmt="select * from owner_vehicle_info where BRAND_NAME='"+brandNae+"'";
            mCursor= db.rawQuery(sqlstmt,null);
        }else if(requestCode==BMK){
            String sqlstmt="select * from owner_vehicle_info where BRAND_NAME='"+brandNae+"' AND MODEL_NAME='"+modelName+"'";
            mCursor= db.rawQuery(sqlstmt,null);

        }else if(requestCode==BRAND_AND_MODEL_YEAR_PRICE){

        }else if(requestCode==BRAND_AND_MODEL_YEAR_PRICE_FEUL){

        }else if(requestCode==BRAND_AND_MODEL_YEAR_PRICE_FEUL_KMS){

        }else if(requestCode==ALL_TYPE_OF_REQ){

        }else if(requestCode==ALL_TYPE_OF_REQQ){

        }
        return mCursor;
    }
    public String  getBrandId(String tableName,String where,String type) throws SQLException{
        Cursor mCursor;
        String id="";
        db=dbHelper.getWritableDatabase();
        mCursor=db.query(false,tableName,null, TableBase.Table.Vehicle.VEHICLE_NAME+"=? AND "+ TableBase.Table.Vehicle.VEHICLE_TYPE_ID+"=?",new String[]{where,type},null,null,null,null);
        if(mCursor!=null){
            mCursor.moveToFirst();
            id=mCursor.getString(mCursor.getColumnIndex(TableBase.Table.Vehicle.VEHICLE_ID));
        }
        return  id;
    }

    /*
    Insert data into vehicle Table
     */
    public  long insertIntoVehicle(ArrayList<BrandBeen> list){
        long l=0;
        db=dbHelper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        for(BrandBeen bn:list){
            cv.put(TableBase.Table.Vehicle.VEHICLE_ID,bn.getBrandId());
            cv.put(TableBase.Table.Vehicle.VEHICLE_NAME,bn.getBrandName());
            cv.put(TableBase.Table.Vehicle.VEHICLE_TYPE_ID,bn.getBrandType());
            l = db.insert(TableBase.Table.Vehicle.VEHICLE, null, cv);
            System.out.print("Number Of Rows Updated in  VEHICLE-->>"+l);
        }
        return l;
    }
    /**Insert into Owner Vehicle*/
    public long insertIntoVehicle_info(ArrayList<VehicleBuyBeen> list){
        long l=0;
        for(VehicleBuyBeen been:list){
            ContentValues cv=new ContentValues();
            cv.put("TOTAL_KM",been.getTOTAL_KM());
            cv.put("YOUR_PRICE",been.getYOUR_PRICE());
            cv.put("POSTED_DATE",been.getPOSTDATE());
            cv.put("INSURANCE_EXPIRE",been.getINSURANCE_EXPIRE());
            cv.put("MODEL_NAME",been.getMODEL_NAME());
            cv.put("DESCRIPTION",been.getDESCRIPTION());
            cv.put("VEHICLE_CITY",been.getVEHICLE_CITY());
            cv.put("VEHICLE_REG_NO",been.getVEHICLE_REG_NO());
            cv.put("NUMBER_OF_OWNER",been.getONUM());
            cv.put("OWNER_ID",been.getOWNER_ID());
            cv.put("LINKS",been.getLINK());
            cv.put("FUEL_TYPE",been.getFUEL_TYPE());
            cv.put("YEAR_OF_MODEL",been.getYEAR_OF_MODEL());
            cv.put("VEHICLE_LOCATION",been.getVEHICLE_LOCATION());
            cv.put("BRAND_NAME",been.getBRAND_NAME());
            //cv.put("KM",been.getTOTAL_KM());
            db=dbHelper.getWritableDatabase();
            l = db.insert(TableBase.Table.OWNER_VEHICLE_INFO.OWNER_VEHICLE_INFO, null, cv);
            if(l>0){
                Log.i("UPDATED VEHICLE INFO"," "+l);
            }else{
                Log.i("CRASHED VEHICLE INFO"," "+l);
            }

        }
        return l;
    }
    public long insertIntoOwner(ArrayList<VehicleOwnerBeen> list) throws SQLException{
        long l=0;
        for(VehicleOwnerBeen been:list){
            ContentValues cv=new ContentValues();
            cv.put(TableBase.Table.OWNER.OWNERID,been.getOWNERID());
            cv.put(TableBase.Table.OWNER.NAME,been.getNAME());
            cv.put(TableBase.Table.OWNER.NUMBER,been.getNUMBER());
            cv.put(TableBase.Table.OWNER.EMAIL,been.getEMAIL());
            db=dbHelper.getWritableDatabase();
            l= db.insert(TableBase.Table.OWNER.OWNER,null,cv);
            System.out.print("## OWNER TABLE"+l);

        }
        return l;
    }
    /* Inserte into
    /*
    insert data into garageaddress table pass arryList;
     */
    public long insertIntoGarageAddress(ArrayList<GaragAddress> adressList,String garagId) throws SQLException{
        long l=0;
        for(GaragAddress gbin:adressList){
            ContentValues cv=new ContentValues();
            cv.put(TableBase.Table.GarageAddress.GARAGE_ADDRESS_ID,gbin.getaddressID());
            cv.put(TableBase.Table.GarageAddress.SUBURBAN_NAME,gbin.getSubUrbanArea());
            cv.put(TableBase.Table.GarageAddress.GARAGE_ID,garagId);
            cv.put(TableBase.Table.GarageAddress.CITY_ID,gbin.getCiry());
            cv.put(TableBase.Table.GarageAddress.PIN,gbin.getPin());
            cv.put(TableBase.Table.GarageAddress.ADDRESS_LINE1,gbin.getAddressline1());
            cv.put(TableBase.Table.GarageAddress.ADDRESS_LINE2,gbin.getAddressLine2());
            cv.put(TableBase.Table.GarageAddress.IS_CLOSE,"");
            cv.put(TableBase.Table.GarageAddress.LAT,gbin.getLat());
            cv.put(TableBase.Table.GarageAddress.LONG,gbin.getLongue());
            db=dbHelper.getWritableDatabase();
           l= db.insert(TableBase.Table.GarageAddress.GARAGE_ADDRESS,null,cv);
            System.out.print("Updated Rows in GaragAddress-->"+l);
        }
        return l;
    }
    /*
    insert into facilaty table
     */
    public long insertIntoFacility(ArrayList<FacilityBeen> facilityList,String garagId) throws SQLException{
        long i=0;
        for(FacilityBeen fbin:facilityList){
            ContentValues cv=new ContentValues();
            cv.put(TableBase.Table.GarageFacility.GARAGE_FACILITY_ID,fbin.getFacilityId());
            cv.put(TableBase.Table.GarageFacility.GARAGE_FACILITY_NAME,fbin.getFacilityName());
            cv.put(TableBase.Table.Garage.GARAGE_ID,garagId);
            db=dbHelper.getWritableDatabase();
           i= db.insert(TableBase.Table.GarageFacility.GARAGE_FACILITY,null,cv);
            System.out.print("Updated Rows in facility-->"+i);
        }
        return i;
    }
    public long insertinto_garage(ArrayList<GarageBeen> garagList) throws SQLException{
        long l=0;
        for(GarageBeen gbin:garagList){
            ContentValues cv = new ContentValues();
            cv.put(TableBase.Table.Garage.GARAGE_ID,gbin.getGarageid());
            cv.put(TableBase.Table.Garage.GARAGE_NAME,gbin.getGarageName());
            cv.put(TableBase.Table.Garage.CONTACT_NUMBER,gbin.getGarageOwnerContactNumber());
            cv.put(TableBase.Table.Garage.OPEN_TIME,gbin.getOpentime());
            cv.put(TableBase.Table.Garage.ALTERNATIVE_CONTACT_NUMBER,gbin.getAlternetMobileNumber());
            cv.put(TableBase.Table.Garage.RATING,gbin.getReating());
            cv.put(TableBase.Table.Garage.CLOSE_TIME,gbin.getCloseTime());
            cv.put(TableBase.Table.Garage.GARAGE_OWNER_ID,gbin.getGarageOwerId());
            cv.put(TableBase.Table.Garage.GARAGE_ID,gbin.getGarageid());
            db=dbHelper.getWritableDatabase();
            l=db.insert(TableBase.Table.Garage.GARAGE,null,cv);
            System.out.print("Updated Rows in Garage-->"+l);
        }
        return l;
    }


    public void close(){
        dbHelper.close();
    }

    /* insertintoTadabase*/
    public long insertIntoUserMaster(UserInfoBeen unifo) throws SQLException{
        db=dbHelper.getWritableDatabase();
        long l=0;
        ContentValues cv=new ContentValues();
        cv.put(TableBase.Table.USERTABLE.FIRSTNAME,unifo.getFirstName());
        cv.put(TableBase.Table.USERTABLE.LASTNAME,unifo.getLastName());
        cv.put(TableBase.Table.USERTABLE.EMAIL,unifo.getEmail());
        cv.put(TableBase.Table.USERTABLE.PASSWORD,unifo.getPassword());
        cv.put(TableBase.Table.USERTABLE.DEVICEID,unifo.getDevice_id());
        cv.put(TableBase.Table.USERTABLE.ADDRESS_LINE1,unifo.getAddress1());
        cv.put(TableBase.Table.USERTABLE.ADDRESS_LINE2,unifo.getAddress2());
        cv.put(TableBase.Table.USERTABLE.SUBURBAN,unifo.getArea());
        cv.put(TableBase.Table.USERTABLE.MOBILENUMBER,unifo.getContactNumber());
        cv.put(TableBase.Table.USERTABLE.SER_USER_ID,unifo.getServer_id());
        l=db.insert(TableBase.Table.USERTABLE.USER, null, cv);
        if (l>0){
            return l;
        }
        return 0;
    }
    /*
    insert into servvices.. table!!!
     */
    public long insertIntoServies(ArrayList<ServiceBeen> sbin,String garageid) throws SQLException{
        long l=0;
        for(ServiceBeen gbin:sbin ){
            ContentValues cv=new ContentValues();
            cv.put(TableBase.Table.GarageService.GARAGE_SERVICE_ID,gbin.getServiceId());
            cv.put(TableBase.Table.GarageService.IS_CLOSE,gbin.isclosed);
            cv.put(TableBase.Table.GarageService.GARAGE_SERVICE_NAME,gbin.getServiveName());
            cv.put(TableBase.Table.GarageService.GARAGE_ID,garageid);
            l=db.insert(TableBase.Table.GarageService.GARAGE_SERVICE, null, cv);
            Log.i("SERVICES-->"," "+l);
        }
        return l;
    }
    /*
    if (l>0){
                return l;
            }
     */
    /*
    return the list adapter of service Center List
     */
    public ArrayList<JserviceCenterBeen> getServiceCenterAdapter() throws  SQLException{
        ArrayList<JserviceCenterBeen> jlist=new ArrayList<JserviceCenterBeen>();
        String sqlstmt="select distinct  g.garage_name, g.contact_number, gd.suburban_name, gd.GARAGE_ID, gd.city_id, gd.address_line1, gd.address_line2,gd.LAT, gd.LONG from garage g,  garage_address gd where g.garage_id=gd.garage_id;";
        db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery(sqlstmt,null);
        if(cursor!=null){
            cursor.moveToFirst();
            while (cursor.isAfterLast()==false){
                JserviceCenterBeen jserviceCenterBeen = new JserviceCenterBeen();
                jserviceCenterBeen.setGarageName(cursor.getString(cursor.getColumnIndex(TableBase.Table.Garage.GARAGE_NAME)));
                jserviceCenterBeen.setContactNumber(cursor.getString(cursor.getColumnIndex(TableBase.Table.Garage.CONTACT_NUMBER)));
                jserviceCenterBeen.setSuburbanArea(cursor.getString(cursor.getColumnIndex(TableBase.Table.GarageAddress.SUBURBAN_NAME)));
                jserviceCenterBeen.setGarageID(cursor.getString(cursor.getColumnIndex(TableBase.Table.GarageAddress.GARAGE_ID)));
                jserviceCenterBeen.setCity(cursor.getString(cursor.getColumnIndex(TableBase.Table.GarageAddress.CITY_ID)));
                jserviceCenterBeen.setAddress1(cursor.getString(cursor.getColumnIndex(TableBase.Table.GarageAddress.ADDRESS_LINE1)));
                jserviceCenterBeen.setAddress2(cursor.getString(cursor.getColumnIndex(TableBase.Table.GarageAddress.ADDRESS_LINE2)));
                try{
                    jserviceCenterBeen.setLatitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex(TableBase.Table.GarageAddress.LAT))));
                    jserviceCenterBeen.setLongitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex(TableBase.Table.GarageAddress.LONG))));

                }catch (Exception e){
                    e.printStackTrace();
                    jserviceCenterBeen.setLatitude(Double.parseDouble("1.0"));
                    jserviceCenterBeen.setLongitude(Double.parseDouble("1.0"));
                }

                jlist.add(jserviceCenterBeen);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return jlist;
    }
    /*
    RETurn Model Class..!!
     */
    public  JserviceCenterBeen getServiceCenterDetails(String servicecenterName) throws SQLException{
        JserviceCenterBeen jsbin=new JserviceCenterBeen();
        String sqlstmt="select g.garage_id,g.garage_name, g.contact_number, g.close_time, g.open_time,gd.garage_address_id, gd.suburban_name, gd.city_id, gd.address_line1, " +
                "gd.address_line2 from garage g,  garage_address gd where g.garage_id='"+getGaragid(servicecenterName)+"';";
Cursor mCursor;
        db=dbHelper.getWritableDatabase();
        mCursor=db.rawQuery(sqlstmt,null);
        if(mCursor!=null){
            mCursor.moveToFirst();
            jsbin.setGarageID(mCursor.getString(mCursor.getColumnIndex(TableBase.Table.Garage.GARAGE_ID)));
            jsbin.setGarageName(mCursor.getString(mCursor.getColumnIndex(TableBase.Table.Garage.GARAGE_NAME)));
            jsbin.setContactNumber(mCursor.getString(mCursor.getColumnIndex(TableBase.Table.Garage.CONTACT_NUMBER)));
            jsbin.setClosetime(mCursor.getString(mCursor.getColumnIndex(TableBase.Table.Garage.CLOSE_TIME)));
            jsbin.setOpentime(mCursor.getString(mCursor.getColumnIndex(TableBase.Table.Garage.OPEN_TIME)));
            jsbin.setAddress_id(mCursor.getString(mCursor.getColumnIndex(TableBase.Table.GarageAddress.GARAGE_ADDRESS_ID)));
            jsbin.setSuburbanArea(mCursor.getString(mCursor.getColumnIndex(TableBase.Table.GarageAddress.SUBURBAN_NAME)));
            jsbin.setCity(mCursor.getString(mCursor.getColumnIndex(TableBase.Table.GarageAddress.CITY_ID)));
            jsbin.setAddress1(mCursor.getString(mCursor.getColumnIndex(TableBase.Table.GarageAddress.ADDRESS_LINE1)));
            jsbin.setAddress2(mCursor.getString(mCursor.getColumnIndex(TableBase.Table.GarageAddress.ADDRESS_LINE2)));
        }
        db.close();
        return jsbin;
    }
    /*
    return garajId
     */
    private String getGaragid(String servicecenterName){
        String garajid="";
        Cursor mCursor;
        db=dbHelper.getWritableDatabase();
        mCursor=db.query(false,TableBase.Table.Garage.GARAGE,new String[]{TableBase.Table.Garage.GARAGE_ID},
                TableBase.Table.Garage.GARAGE_NAME+"=?",new String[]{servicecenterName},null,null,null,"1");
        if(mCursor!=null){
         mCursor.moveToFirst();
            garajid=mCursor.getString(mCursor.getColumnIndex(TableBase.Table.Garage.GARAGE_ID));
            Log.i("",""+garajid);

        }
        db.close();
        return garajid;
    }
    /*
    Delete given table
     */
    public long deleteTable(String tableName){
        long l=0;
        db=dbHelper.getWritableDatabase();
        l=db.delete(tableName,null,null);
        Log.i("DELETED"," .."+tableName+" "+l);
        db.close();
        return l;
    }
    /*
    chekProduct is to chek the cart staus and Fevarite product
    -----------------------------------
    hare req  code 0 favorite items ;
    hare req  code 1 Cart Item checking ;
     --------------------------------
     */
    public int checkProduct(int req,String productId) throws SQLException{
        db=dbHelper.getWritableDatabase();
        Cursor mCursor=null;
        if(req==REQUEST_FAVORITE){
            //mCursor=db.query(false,)
        }else if(req==REQUEST_CART_CHECK){


        }
        return  0;
    }
    public long insertIntoCartDetails(byte[] bytes) throws  SQLException{
        long l=0;
        db=dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableBase.Table.CART_DTLS.PRODUCT,bytes);
        l=db.insert(TableBase.Table.CART_DTLS.CART_DETLS,null,cv);
        return l;
    }

    public int getCartItems() throws  SQLException{
        int a=0;
        db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(false, TableBase.Table.CART_DTLS.CART_DETLS,null,null,null,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
            a= cursor.getCount();
        }
        return a;
    }
    public Cursor getCartItems(String where) throws  SQLException{
        Cursor mCursor=null;
        db=dbHelper.getWritableDatabase();
        mCursor=db.query(false, TableBase.Table.CART_DTLS.CART_DETLS,null,null,null,null,null,null,null);
        if(mCursor!=null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public int checkCart(byte[] b) throws  SQLException{
        int a=0;
        db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(false, TableBase.Table.CART_DTLS.CART_DETLS,null,null,null,null,null,null,null);
        byte[] array=new BigInteger(b).toByteArray();
        if(cursor!=null){
            cursor.moveToFirst();
            while (cursor.isAfterLast()==false){
                byte[] iobject= cursor.getBlob(1);
                byte[] currentobject=new BigInteger(iobject).toByteArray();
                int  id=cursor.getInt(0);
                if(Arrays.equals(array,currentobject)){
                    String id1=String.valueOf(id);
                    a=db.delete(TableBase.Table.CART_DTLS.CART_DETLS, TableBase.Table.CART_DTLS.ID+"=?",new String[]{id1});
                }
                cursor.moveToNext();
            }
        }
        return a;
    }
}

