package com.listAdapters.bikeBeens;

import java.io.Serializable;

/**
 * Created by Admin on 09-06-2016.
 */
public class JserviceCenterBeen implements Serializable,Cloneable {
    public String garageID="";
    public String garageName="";
    public String suburbanArea="";
    public String contactNumber="";
    public String city="";
    public String address1="";
    public String address2="";
    public String opentime="";
    public String closetime="";
    public String address_id="";
    public Double latitude;
    public Double longitude;


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getGarageID() {
        return garageID;
    }

    public void setGarageID(String garageID) {
        this.garageID = garageID;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getGarageName() {
        return garageName;
    }

    public void setGarageName(String garageName) {
        this.garageName = garageName;
    }

    public String getSuburbanArea() {
        return suburbanArea;
    }

    public void setSuburbanArea(String suburbanArea) {
        this.suburbanArea = suburbanArea;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public String getClosetime() {
        return closetime;
    }

    public void setClosetime(String closetime) {
        this.closetime = closetime;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
