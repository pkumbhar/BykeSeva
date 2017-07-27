package com.listAdapters.bikeBeens;

import java.io.Serializable;

/**
 * Created by Admin on 07-06-2016.
 */
public class VehicleModelBeens  implements Serializable{
    public String name="";
    public String id="";
    public String brand="";
    public String colorcode="";

    public VehicleModelBeens( ){

    }

    public VehicleModelBeens(String name, String id, String brand) {
        this.name = name;
        this.id = id;
        this.brand = brand;
    }

    public String getColorcode() {
        return colorcode;
    }

    public void setColorcode(String colorcode) {
        this.colorcode = colorcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}
