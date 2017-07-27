package com.preferences_storage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.util.Log;

import java.util.Set;

/**
 * Created by Admin on 14-06-2016.
 */
public class SavePrefe {
    public void saveCheckedItemsTimeAndDate(String items, Activity mContext){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("ITEMS", mContext.MODE_PRIVATE).edit();
        editor.putString("Val", items);
        editor.commit();
    }
    /*
    getShairedVales if cheked item and date and time
     */
    public String getCheckedItemsTimeAndDate(Activity mContext){
        String result="";
        SharedPreferences prefs = mContext.getSharedPreferences("ITEMS", mContext.MODE_PRIVATE);
        result= prefs.getString("Val", null);
       return result;
    }
    /*
    save Bike ID
     */
    public void saveBikeid(String items, Activity mContext){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("BIKE_ID", mContext.MODE_PRIVATE).edit();
        editor.putString("B_ID", items);
        editor.commit();
    }
    /*
    getShairedVales if cheked item and date and time
     */
    public String getBikeId(Activity mContext){
        String result="";
        SharedPreferences prefs = mContext.getSharedPreferences("BIKE_ID", mContext.MODE_PRIVATE);
        result= prefs.getString("B_ID", null);
        return result;
    }
    /*
    Save Date
     */
    public void saveDate(String items, Activity mContext){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("SDATE", mContext.MODE_PRIVATE).edit();
        editor.putString("DATE", items);
        editor.commit();
    }
    /*
    Get Saved Date
     */
    public String getDate(Activity mContext){
        String result="";
        SharedPreferences prefs = mContext.getSharedPreferences("SDATE", mContext.MODE_PRIVATE);
        result= prefs.getString("DATE", null);
        return result;
    }
    /*
    Save Time
     */
    public void saveTime(String items, Activity mContext){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("TIME_ID", mContext.MODE_PRIVATE).edit();
        editor.putString("TIME", items);
        editor.commit();
    }
    /*
    GetTime
     */
    public String getTime(Activity mContext){
        String result="";
        SharedPreferences prefs = mContext.getSharedPreferences("TIME_ID", mContext.MODE_PRIVATE);
        result= prefs.getString("TIME", null);
        return result;
    }

    /*
    Save Serilaize Vehicle Object
     */
    public void saveVehicleObject(String object, Activity mContext){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("ADD_ID", mContext.MODE_PRIVATE).edit();
        editor.putString("ADDS_ID", object);
        editor.commit();
    }
    /*
    get  Saved Object
     */
    public String getVehicleObject(Activity mContext){
        String result="";
        SharedPreferences prefs = mContext.getSharedPreferences("ADD_ID", mContext.MODE_PRIVATE);
        result= prefs.getString("ADDS_ID", null);
        return result;
    }


    /*
    save garage Name
     */
    public void saveGarageName(String items, Activity mContext){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("GARAGE", mContext.MODE_PRIVATE).edit();
        editor.putString("GNAME", items);
        editor.commit();
    }
    /*
    save Garaje Id
     */
    public void saveGarageId(String items, Activity mContext){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("GARAGEID", mContext.MODE_PRIVATE).edit();
        editor.putString("GARAGEID", items);
        editor.commit();
    }
    /*
    get garaje Id
     */
    public String getGarajeId(Activity mContext){
        String result="";
        SharedPreferences prefs = mContext.getSharedPreferences("GARAGEID", mContext.MODE_PRIVATE);
        result= prefs.getString("GARAGEID", null);
        return result;
    }
    /*
    Save Order Id
     */
    public void saveOrderId(String items, Activity mContext){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("OWNER_ID", mContext.MODE_PRIVATE).edit();
        editor.putString("O_ID", items);
        editor.commit();
    }
    /*
    getOrder Id
     */
    public String getOrderId(Activity mContext){
        String result="";
        SharedPreferences prefs = mContext.getSharedPreferences("OWNER_ID", mContext.MODE_PRIVATE);
        result= prefs.getString("O_ID", null);
        return result;
    }

    /*
    getGarageName
     */
    public String getGarageNamae(Activity mContext){
        String result="";
        SharedPreferences prefs = mContext.getSharedPreferences("GARAGE", mContext.MODE_PRIVATE);
        result= prefs.getString("GNAME", null);
        return result;
    }
    /*
    save Booking Order
     */
    public void savePendingOrder(String jsonarray, Activity mContext){
        Log.i("SAVED ORDER------>>>>"," "+jsonarray);
        SharedPreferences.Editor editor = mContext.getSharedPreferences("BOOKING", mContext.MODE_PRIVATE).edit();
        editor.putString("VAL", jsonarray);
        editor.commit();
    }
    /*
    get Saved Ordert
     */
    public String getPendingOrder( Activity mContext){
        String result="";
        SharedPreferences prefs = mContext.getSharedPreferences("BOOKING", mContext.MODE_PRIVATE);
        result= prefs.getString("VAL", null);
        return result;
    }
    /*
    save Extra services
     */
    public void saveExtraServices(Set<String> GarajeServiceId, Activity mContext){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("EXSERVICES", mContext.MODE_PRIVATE).edit();
        editor.putStringSet("GSID",GarajeServiceId);
        editor.commit();
    }
    /*
    get Saved extra Services
     */
    public Set<String> getExtraServices(Activity mContext){
        Set<String> gsid;
        SharedPreferences prefs = mContext.getSharedPreferences("EXSERVICES", mContext.MODE_PRIVATE);
        gsid= prefs.getStringSet("GSID",null);
        return gsid;
    }
    /*
    save Extra services
     */
    public void saveServicesByName(Set<String> GarajeServiceId, Activity mContext){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("SERVNAME", mContext.MODE_PRIVATE).edit();
        editor.putStringSet("SRNAME",GarajeServiceId);
        editor.commit();
    }
    /*
    get Saved extra Services
     */
    public Set<String> getServicesByName(Activity mContext){
        Set<String> gsid;
        SharedPreferences prefs = mContext.getSharedPreferences("SERVNAME", mContext.MODE_PRIVATE);
        gsid= prefs.getStringSet("SRNAME",null);
        return gsid;
    }
    /*
  save Model Id
   */
    public void saveModel(String extraservice, Activity mContext){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("MODELID", mContext.MODE_PRIVATE).edit();
        editor.putString("MODELID", extraservice);
        editor.commit();
    }
    /*
    get Saved Model Id
     */
    public String getModel(Activity mContext){
        String result="";
        SharedPreferences prefs = mContext.getSharedPreferences("MODELID", mContext.MODE_PRIVATE);
        result= prefs.getString("MODELID", null);
        return result;
    }
    /*
    Test List
     */
    public void saveModelhistory(String extraservice, Activity mContext){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("MODELID", mContext.MODE_PRIVATE).edit();
        editor.putString("MODELID", extraservice);
        editor.commit();
    }
    /*
    Starting Fragments
     */
    public void saveFragmentstatus(String extraservice, Activity mContext){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("FID", mContext.MODE_PRIVATE).edit();
        editor.putString("FID", extraservice);
        editor.commit();
    }
    /*
    get Saved Model Id
     */
    public String getFragmentstatus(Activity mContext){
        String result="";
        SharedPreferences prefs = mContext.getSharedPreferences("FID", mContext.MODE_PRIVATE);
        result= prefs.getString("FID", null);
        return result;
    }
    //preferences.edit().remove("key").commit()
    public String clearFragmentstatus(Activity mContext){
        String result="";
        SharedPreferences prefs = mContext.getSharedPreferences("FID", mContext.MODE_PRIVATE);
        prefs.edit().remove("FID").commit();
        return result;
    }
    //------------------------------------------
    public void saveVehicleType(String extraservice, Activity mContext){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("VEHICLE_KEY", mContext.MODE_PRIVATE).edit();
        editor.putString("VEHICLE_KEY", extraservice);
        editor.commit();
    }
    /*
    get Saved Model Id
     */
    public String getVehicleType(Activity mContext){
        String result="";
        SharedPreferences prefs = mContext.getSharedPreferences("VEHICLE_KEY", mContext.MODE_PRIVATE);
        result= prefs.getString("VEHICLE_KEY", null);
        return result;
    }
    public void saveProduct(String extraservice, Activity mContext){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("VEHICLE_KEY", mContext.MODE_PRIVATE).edit();
        editor.putString("VEHICLE_KEY", extraservice);
        editor.commit();
    }
}
