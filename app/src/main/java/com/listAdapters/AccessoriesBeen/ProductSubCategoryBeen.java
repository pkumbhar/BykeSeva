package com.listAdapters.AccessoriesBeen;

/**
 * Created by Admin on 27-January-27-2017.
 */

public class ProductSubCategoryBeen implements Cloneable {
    public String ID="ID";
    public String SUB_CATEGORY_NAME="SUB_CATEGORY_NAME";
    public String CATEGORY_ID="CATEGORY_ID";


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSUB_CATEGORY_NAME() {
        return SUB_CATEGORY_NAME;
    }

    public void setSUB_CATEGORY_NAME(String SUB_CATEGORY_NAME) {
        this.SUB_CATEGORY_NAME = SUB_CATEGORY_NAME;
    }

    public String getCATEGORY_ID() {
        return CATEGORY_ID;
    }

    public void setCATEGORY_ID(String CATEGORY_ID) {
        this.CATEGORY_ID = CATEGORY_ID;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
