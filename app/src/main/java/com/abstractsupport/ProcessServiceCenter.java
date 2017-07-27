package com.abstractsupport;

import android.content.Context;

import com.db_adapter.DBAdapter;
import com.listAdapters.bikeBeens.BrandBeen;
import com.listAdapters.bikeBeens.FacilityBeen;
import com.listAdapters.bikeBeens.GaragAddress;
import com.listAdapters.bikeBeens.GarageBeen;
import com.listAdapters.bikeBeens.ServiceBeen;
import com.listAdapters.bikeBeens.ShowroomBeens;
import com.listAdapters.bikeBeens.VehicleModelBeens;
import com.table_base.TableBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Admin on 08-06-2016.
 */
public class ProcessServiceCenter extends HandelServiceCenter {
    GarageBeen garCloneaddress,garClonefacility,garagcloneservice;
    @Override
    public int  getVehicleList(String inputstream,String s) {
        return 0;
    }

    @Override
    public ArrayList<VehicleModelBeens> getVehicleSubmodel(String inputstream) {
        return null;
    }

    @Override
    public ArrayList<ShowroomBeens> getServiceCenterList(String inputstream) {
        return null;
    }

    @Override
    public boolean handelServicecenterData(String inputStream, Context mContext) {
        DBAdapter  adapter=new DBAdapter(mContext);
        adapter.deleteTable(TableBase.Table.Garage.GARAGE);
        adapter.deleteTable(TableBase.Table.GarageAddress.GARAGE_ADDRESS);
        adapter.deleteTable(TableBase.Table.GarageFacility.GARAGE_FACILITY);
        adapter.deleteTable(TableBase.Table.GarageService.GARAGE_SERVICE);
        boolean result=false;
        ArrayList<FacilityBeen> facilityBeens=new ArrayList<FacilityBeen>();
        ArrayList<GaragAddress> addressesList=new ArrayList<GaragAddress>();
        ArrayList<GarageBeen> garagList=new ArrayList<GarageBeen>();
        ArrayList<ServiceBeen> servicebinList=new ArrayList<ServiceBeen>();
        try {
            JSONArray jsonArray=new JSONArray(inputStream);
            int len=jsonArray.length();
            for(int mainjson=0;mainjson<jsonArray.length();mainjson++){
                facilityBeens.clear();
                addressesList.clear();
                garagList.clear();
               servicebinList.clear();
                JSONObject jsonObject=jsonArray.getJSONObject(mainjson);
                if(jsonObject.length()>0){
                    //TODO stub
                        GarageBeen garageBeen=new GarageBeen();
                        try{
                            if(jsonObject.has("FACILITYS")){
                                JSONArray facilityJOb=jsonObject.getJSONArray("FACILITYS");
                                if(facilityJOb.length()>0){
                                    for(int i=0;i<facilityJOb.length();i++){
                                        JSONObject fob=facilityJOb.getJSONObject(i);
                                        if(fob.length()>0){
                                                FacilityBeen fbin=new FacilityBeen();
                                                if(fob.has("FACILITY")){
                                                    fbin.setFacilityName(fob.getString("FACILITY"));
                                                }if (fob.has("ID")){
                                                    fbin.setFacilityId(fob.getString("ID"));
                                                }
                                                facilityBeens.add(fbin);
                                        }
                                    }
                                }
                            }
                            if(jsonObject.has("NAME")){
                                garageBeen.setGarageName(jsonObject.getString("NAME").trim().toUpperCase());
                            } if(jsonObject.has("MOBILE")){
                                garageBeen.setGarageOwnerContactNumber(jsonObject.getString("MOBILE"));
                            } if(jsonObject.has("SERVICES")){
                                JSONArray jServiceArray=jsonObject.getJSONArray("SERVICES");
                                if(jServiceArray.length()>0){

                                    for(int i=0;i<jServiceArray.length();i++){
                                        ServiceBeen sBin=new ServiceBeen();
                                        JSONObject jServiceOBJ=jServiceArray.getJSONObject(i);
                                        if(jServiceOBJ.length()>0){
                                            if(jServiceOBJ.has("SERVICE")){
                                                sBin.setServiveName(jServiceOBJ.getString("SERVICE"));
                                            }if(jServiceOBJ.has("ID")){
                                                sBin.setServiceId(jServiceOBJ.getString("ID"));
                                            }
                                            servicebinList.add(sBin);
                                        }
                                    }
                                }
                            } if(jsonObject.has("OTIME")){
                                garageBeen.setOpentime("OTIME");
                            } if(jsonObject.has("ADSRESSES")){
                                try{
                                    JSONArray jAddressArray=jsonObject.getJSONArray("ADSRESSES");
                                    if(jAddressArray.length()>0) {
                                        for (int i = 0; i < jAddressArray.length(); i++) {
                                            JSONObject jObjectArray = jAddressArray.getJSONObject(i);
                                            if (jObjectArray.length() > 0) {
                                                GaragAddress garagAddressbin = new GaragAddress();
                                                if (jObjectArray.has("STATE")) {
                                                    garagAddressbin.setState(jObjectArray.getString("STATE"));
                                                }
                                                if (jObjectArray.has("AREA")) {
                                                    garagAddressbin.setSubUrbanArea(jObjectArray.getString("AREA"));
                                                }
                                                if(jObjectArray.has("GOOGLEADD")){
                                                    String latlong=jObjectArray.getString("GOOGLEADD");
                                                    try{
                                                        if((!latlong.isEmpty())&&(latlong.length()>5)){
                                                            String[] location=latlong.split("[,]");
                                                            garagAddressbin.setLat(location[0]);
                                                            garagAddressbin.setLongue(location[1]);
                                                        }
                                                    }catch (Exception e){
                                                        e.printStackTrace();
                                                        garagAddressbin.setLat("00");
                                                        garagAddressbin.setLongue("00");
                                                    }
                                                }

                                                if (jObjectArray.has("COUNTRY")) {
                                                    garagAddressbin.setCountry(jObjectArray.getString("COUNTRY"));
                                                }
                                                if (jObjectArray.has("ID")) {
                                                    garagAddressbin.setaddressID(jObjectArray.getString("ID"));
                                                }if(jObjectArray.has("PIN")){
                                                    garagAddressbin.setPin(jObjectArray.getString("PIN"));
                                                }if(jObjectArray.has("CITY")){
                                                    garagAddressbin.setCiry(jObjectArray.getString("CITY"));
                                                }if(jObjectArray.has("ADDL1")){
                                                    garagAddressbin.setAddressline1(jObjectArray.getString("ADDL1"));
                                                }if(jObjectArray.has("ADDL2")){
                                                    garagAddressbin.setAddressLine2(jObjectArray.getString("ADDL2"));
                                                }
                                                addressesList.add(garagAddressbin);
                                            }
                                        }
                                    }
                            }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }if(jsonObject.has("ALMOBILE")){
                                garageBeen.setAlternetMobileNumber(jsonObject.getString("ALMOBILE"));
                            }if(jsonObject.has("RATING")){
                                garageBeen.setReating(jsonObject.getString("RATING"));
                            }if(jsonObject.has("CTIME")){
                                garageBeen.setCloseTime(jsonObject.getString("CTIME"));
                            }if(jsonObject.has("OTIME")){
                                garageBeen.setOpentime(jsonObject.getString("OTIME"));
                            }
                            if(jsonObject.has("ID")){
                                garageBeen.setGarageid(jsonObject.getString("ID"));
                                garCloneaddress=(GarageBeen) garageBeen.clone();
                                garClonefacility=(GarageBeen) garageBeen.clone();
                                garagcloneservice=(GarageBeen) garageBeen.clone();
                            }
                            garagList.add(garageBeen);
                        }catch (Exception j){
                            j.printStackTrace();
                            result=false;
                        }
                }else {
                    //TODO of jsonObject
                }
                adapter.insertIntoGarageAddress(addressesList,garCloneaddress.getGarageid());
                System.out.print("DATA Inserted into GARAGADDRESS");
                adapter.insertIntoFacility(facilityBeens,garClonefacility.getGarageid());
                System.out.print("DATA Inserted into FACILITY");
                adapter.insertinto_garage(garagList);
                System.out.print("DATA Inserted into GARAGE");
                adapter.insertIntoServies(servicebinList,garagcloneservice.getGarageid());
                System.out.print("DATA Inserted into GARAGE_SERVICES");
                result=true;
            }
        }catch (Exception e){
            e.printStackTrace();
            result=false;
        }
        return result;
    }
}
