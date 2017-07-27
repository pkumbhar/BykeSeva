package com.listAdapters.bikeBeens;

import java.io.Serializable;

/**
 * Created by Admin on 08-06-2016.
 */
public class GaragAddress implements Serializable {
    public String garageAddressID="";
    public String subUrbanArea="";
    public String pin="";
    public String ciry="";
    public String addressline1="";
    public boolean isclosed=false;
    public String addressLine2="";
    public String addressID="";
    public String lat="";
    public String longue="";
    public  String state="";
    public String country="";


    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }



    public String getGarageAddressID() {
        return garageAddressID;
    }

    public void setGarageAddressID(String garageAddressID) {
        this.garageAddressID = garageAddressID;
    }

    public String getSubUrbanArea() {
        return subUrbanArea;
    }

    public void setSubUrbanArea(String subUrbanArea) {
        this.subUrbanArea = subUrbanArea;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCiry() {
        return ciry;
    }

    public void setCiry(String ciry) {
        this.ciry = ciry;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    public boolean isclosed() {
        return isclosed;
    }

    public void setIsclosed(boolean isclosed) {
        this.isclosed = isclosed;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getaddressID() {
        return addressID;
    }

    public void setaddressID(String garagID) {
        this.addressID = garagID;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongue() {
        return longue;
    }

    public void setLongue(String longue) {
        this.longue = longue;
    }
}
