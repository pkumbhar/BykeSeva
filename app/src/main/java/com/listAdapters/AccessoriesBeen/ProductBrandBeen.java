package com.listAdapters.AccessoriesBeen;

/**
 * Created by Admin on 27-January-27-2017.
 */

public class ProductBrandBeen implements Cloneable{
    public String ID="ID";
    public String NAME="NAME";
    public String PRODUCT_SUB_CATEGORY_ID="PRODUCT_SUB_CATEGORY_ID";

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

    public String getPRODUCT_SUB_CATEGORY_ID() {
        return PRODUCT_SUB_CATEGORY_ID;
    }

    public void setPRODUCT_SUB_CATEGORY_ID(String PRODUCT_SUB_CATEGORY_ID) {
        this.PRODUCT_SUB_CATEGORY_ID = PRODUCT_SUB_CATEGORY_ID;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
