package com.listAdapters.bikeBeens;

import java.io.Serializable;

/**
 * Created by Admin on 08-06-2016.
 */
public class GarageBeen implements Serializable,Cloneable {
    public String garageid="";
    public String garageName="";
    public String garageOwerId="";
    public String garageOwnerContactNumber="";
    public boolean status=false;
    public boolean isclosed=false;
    public String opentime="";
    public String closeTime="";
    public String reating="";
    public String alternetMobileNumber="";


    public String getAlternetMobileNumber() {
        return alternetMobileNumber;
    }

    public void setAlternetMobileNumber(String alternetMobileNumber) {
        this.alternetMobileNumber = alternetMobileNumber;
    }

    public String getGarageid() {
        return garageid;
    }

    public void setGarageid(String garageid) {
        this.garageid = garageid;
    }

    public String getGarageName() {
        return garageName;
    }

    public void setGarageName(String garageName) {
        this.garageName = garageName;
    }

    public String getGarageOwerId() {
        return garageOwerId;
    }

    public void setGarageOwerId(String garageOwerId) {
        this.garageOwerId = garageOwerId;
    }

    public String getGarageOwnerContactNumber() {
        return garageOwnerContactNumber;
    }

    public void setGarageOwnerContactNumber(String garageOwnerContactNumber) {
        this.garageOwnerContactNumber = garageOwnerContactNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isclosed() {
        return isclosed;
    }

    public void setIsclosed(boolean isclosed) {
        this.isclosed = isclosed;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getReating() {
        return reating;
    }

    public void setReating(String reating) {
        this.reating = reating;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
