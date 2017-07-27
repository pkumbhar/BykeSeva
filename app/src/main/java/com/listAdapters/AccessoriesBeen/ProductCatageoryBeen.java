package com.listAdapters.AccessoriesBeen;

/**
 * Created by Admin on 27-January-27-2017.
 */

public class ProductCatageoryBeen implements Cloneable {

    public String ID="";
    public String NAME="NAME";

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
