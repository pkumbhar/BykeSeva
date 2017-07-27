package com.interfaces;

import android.content.Context;

import com.listAdapters.bikeBeens.BrandBeen;
import com.listAdapters.bikeBeens.ShowroomBeens;
import com.listAdapters.bikeBeens.VehicleModelBeens;

import java.util.ArrayList;

/**
 * Created by Admin on 06-06-2016.
 */
public interface Listprocessable {
    public   int getVehicleList(String inputstream,String vehicletype);
    public ArrayList<VehicleModelBeens> getVehicleSubmodel(String inputstream);
    public ArrayList<ShowroomBeens> getServiceCenterList(String inputstream);
    /*
    handeling Data From Three tables
     */
    public   boolean handelServicecenterData(String inputStream, Context mContext);
}
