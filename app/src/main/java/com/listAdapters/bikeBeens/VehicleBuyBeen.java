package com.listAdapters.bikeBeens;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import com.listAdapters.bikeAdapters.VehicleMarktAdapter;

/**
 * Created by Admin on 12-October-12-2016.
 */
public class VehicleBuyBeen implements Parcelable ,Cloneable{
    public String id="id";
    public String VEHICLE_ID="VEHICLE_ID";
    public String  BRAND_NAME="BRAND_NAME";
    public String  MODEL_NAME="MODEL_NAME";
    public String  VEHICLE_REG_NO="VEHICLE_REG_NO";
    public String  YEAR_OF_MODEL="YEAR_OF_MODEL";
    public String  TOTAL_KM="TOTAL_KM";
    public String  FUEL_TYPE="FUEL_TYPE";
    public String  COMPANY_PRICE="COMPANY_PRICE";
    public String INSURANCE_EXPIRE="INSURANCE_EXPIRE";
    public String VEHICLE_CITY="VEHICLE_CITY";
    public String VEHICLE_LOCATION="VEHICLE_LOCATION";
    public String DESCRIPTION="DESCRIPTION";
    public String DATE_REG="DATE_REG";
    public String  OWNER_ID="OWNER_ID";
    public String POSTDATE="POSTDATE";
    public String ONUM="ONUM";
    public String LINK="LINK";
    public String YOUR_PRICE="YOUR_PRICE";
    public String O_NAME="O_NAME";
    public String O_ID="O_ID";
    public String O_EMAIL="O_EMAIL";
    public String O_NUMBER="O_NUMBER";


    public VehicleBuyBeen(){

    }

    public VehicleBuyBeen(String id, String VEHICLE_ID, String BRAND_NAME, String MODEL_NAME, String VEHICLE_REG_NO, String YEAR_OF_MODEL, String TOTAL_KM,
                          String FUEL_TYPE, String COMPANY_PRICE, String INSURANCE_EXPIRE, String VEHICLE_CITY, String VEHICLE_LOCATION, String DESCRIPTION,
                          String DATE_REG, String OWNER_ID, String POSTDATE, String ONUM, String LINK, String YOUR_PRICE, String o_NAME, String o_ID, String o_EMAIL,
                          String o_NUMBER) {
        this.id = id;
        this.VEHICLE_ID = VEHICLE_ID;
        this.BRAND_NAME = BRAND_NAME;
        this.MODEL_NAME = MODEL_NAME;
        this.VEHICLE_REG_NO = VEHICLE_REG_NO;
        this.YEAR_OF_MODEL = YEAR_OF_MODEL;
        this.TOTAL_KM = TOTAL_KM;
        this.FUEL_TYPE = FUEL_TYPE;
        this.COMPANY_PRICE = COMPANY_PRICE;
        this.INSURANCE_EXPIRE = INSURANCE_EXPIRE;
        this.VEHICLE_CITY = VEHICLE_CITY;
        this.VEHICLE_LOCATION = VEHICLE_LOCATION;
        this.DESCRIPTION = DESCRIPTION;
        this.DATE_REG = DATE_REG;
        this.OWNER_ID = OWNER_ID;
        this.POSTDATE = POSTDATE;
        this.ONUM = ONUM;
        this.LINK = LINK;
        this.YOUR_PRICE = YOUR_PRICE;
        this.O_NAME = o_NAME;
        this.O_ID = o_ID;
        this.O_EMAIL = o_EMAIL;
        this.O_NUMBER = o_NUMBER;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(VEHICLE_ID);
        dest.writeString(BRAND_NAME);
        dest.writeString(MODEL_NAME);
        dest.writeString(VEHICLE_REG_NO);
        dest.writeString(YEAR_OF_MODEL);
        dest.writeString(TOTAL_KM);
        dest.writeString(FUEL_TYPE);
        dest.writeString(COMPANY_PRICE);
        dest.writeString(INSURANCE_EXPIRE);
        dest.writeString(VEHICLE_CITY);
        dest.writeString(VEHICLE_LOCATION);
        dest.writeString(DESCRIPTION);
        dest.writeString(DATE_REG);
        dest.writeString(OWNER_ID);
        dest.writeString(POSTDATE);
        dest.writeString(ONUM);
        dest.writeString(LINK);
        dest.writeString(YOUR_PRICE);
        dest.writeString(O_NAME);
        dest.writeString(O_ID);
        dest.writeString(O_EMAIL);
        dest.writeString(O_NUMBER);

    }
    public static final Parcelable.Creator<VehicleBuyBeen> CREATOR=new Parcelable.Creator<VehicleBuyBeen>(){
        public VehicleBuyBeen createFromParcel(Parcel in){
            return new VehicleBuyBeen(in);
        }
        public VehicleBuyBeen[] newArray(int size){
            return new VehicleBuyBeen[size];
        }
    };
    private VehicleBuyBeen(Parcel in){
        id = in.readString();
        VEHICLE_ID = in.readString();
        BRAND_NAME = in.readString();
        MODEL_NAME = in.readString();
        VEHICLE_REG_NO = in.readString();
        YEAR_OF_MODEL = in.readString();
        TOTAL_KM = in.readString();
        FUEL_TYPE = in.readString();
        COMPANY_PRICE = in.readString();
        INSURANCE_EXPIRE = in.readString();
        VEHICLE_CITY = in.readString();
        VEHICLE_LOCATION = in.readString();
        DESCRIPTION = in.readString();
        DATE_REG = in.readString();
        OWNER_ID = in.readString();
        POSTDATE = in.readString();
        ONUM = in.readString();
        LINK = in.readString();
        YOUR_PRICE = in.readString();
        O_NAME = in.readString();
        O_ID = in.readString();
        O_EMAIL = in.readString();
        O_NUMBER = in.readString();
    }


    public String getO_NAME() {
        return O_NAME;
    }

    public void setO_NAME(String o_NAME) {
        O_NAME = o_NAME;
    }

    public String getO_ID() {
        return O_ID;
    }

    public void setO_ID(String o_ID) {
        O_ID = o_ID;
    }

    public String getO_EMAIL() {
        return O_EMAIL;
    }

    public void setO_EMAIL(String o_EMAIL) {
        O_EMAIL = o_EMAIL;
    }

    public String getO_NUMBER() {
        return O_NUMBER;
    }

    public void setO_NUMBER(String o_NUMBER) {
        O_NUMBER = o_NUMBER;
    }

    public String getYOUR_PRICE() {
        return YOUR_PRICE;
    }

    public void setYOUR_PRICE(String YOUR_PRICE) {
        this.YOUR_PRICE = YOUR_PRICE;
    }

    public String getLINK() {
        return LINK;
    }

    public void setLINK(String LINK) {
        this.LINK = LINK;
    }

    public String getONUM() {
        return ONUM;
    }

    public void setONUM(String ONUM) {
        this.ONUM = ONUM;
    }

    public String getPOSTDATE() {
        return POSTDATE;
    }

    public void setPOSTDATE(String POSTDATE) {
        this.POSTDATE = POSTDATE;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVEHICLE_ID() {
        return VEHICLE_ID;
    }

    public void setVEHICLE_ID(String VEHICLE_ID) {
        this.VEHICLE_ID = VEHICLE_ID;
    }

    public String getBRAND_NAME() {
        return BRAND_NAME;
    }

    public void setBRAND_NAME(String BRAND_NAME) {
        this.BRAND_NAME = BRAND_NAME;
    }

    public String getMODEL_NAME() {
        return MODEL_NAME;
    }

    public void setMODEL_NAME(String MODEL_NAME) {
        this.MODEL_NAME = MODEL_NAME;
    }

    public String getVEHICLE_REG_NO() {
        return VEHICLE_REG_NO;
    }

    public void setVEHICLE_REG_NO(String VEHICLE_REG_NO) {
        this.VEHICLE_REG_NO = VEHICLE_REG_NO;
    }

    public String getYEAR_OF_MODEL() {
        return YEAR_OF_MODEL;
    }

    public void setYEAR_OF_MODEL(String YEAR_OF_MODEL) {
        this.YEAR_OF_MODEL = YEAR_OF_MODEL;
    }

    public String getTOTAL_KM() {
        return TOTAL_KM;
    }

    public void setTOTAL_KM(String TOTAL_KM) {
        this.TOTAL_KM = TOTAL_KM;
    }

    public String getFUEL_TYPE() {
        return FUEL_TYPE;
    }

    public void setFUEL_TYPE(String FUEL_TYPE) {
        this.FUEL_TYPE = FUEL_TYPE;
    }

    public String getCOMPANY_PRICE() {
        return COMPANY_PRICE;
    }

    public void setCOMPANY_PRICE(String COMPANY_PRICE) {
        this.COMPANY_PRICE = COMPANY_PRICE;
    }

    public String getINSURANCE_EXPIRE() {
        return INSURANCE_EXPIRE;
    }

    public void setINSURANCE_EXPIRE(String INSURANCE_EXPIRE) {
        this.INSURANCE_EXPIRE = INSURANCE_EXPIRE;
    }

    public String getVEHICLE_CITY() {
        return VEHICLE_CITY;
    }

    public void setVEHICLE_CITY(String VEHICLE_CITY) {
        this.VEHICLE_CITY = VEHICLE_CITY;
    }

    public String getVEHICLE_LOCATION() {
        return VEHICLE_LOCATION;
    }

    public void setVEHICLE_LOCATION(String VEHICLE_LOCATION) {
        this.VEHICLE_LOCATION = VEHICLE_LOCATION;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getDATE_REG() {
        return DATE_REG;
    }

    public void setDATE_REG(String DATE_REG) {
        this.DATE_REG = DATE_REG;
    }

    public String getOWNER_ID() {
        return OWNER_ID;
    }

    public void setOWNER_ID(String OWNER_ID) {
        this.OWNER_ID = OWNER_ID;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof VehicleBuyBeen)
            return ((VehicleBuyBeen)(VehicleBuyBeen) obj).getBRAND_NAME().equals(this.getBRAND_NAME());
            return false;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
