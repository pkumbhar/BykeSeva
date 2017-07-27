package com.listAdapters.bikeBeens;

import java.io.Serializable;

/**
 * Created by Admin on 08-06-2016.
 */
public class FacilityBeen implements Serializable {
    public String facilityName="";
    public String facilityId="";
    public boolean isclosed=false;
    public String garageid="";

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }
}
