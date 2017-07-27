package com.listAdapters.bikeBeens;

import java.io.Serializable;

/**
 * Created by Admin on 08-06-2016.
 */
public class ServiceBeen implements Serializable,Cloneable {
    public String serviceId="";
    public String serviveName="";
    public String garageId="";
    public boolean isclosed=false;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiveName() {
        return serviveName;
    }

    public void setServiveName(String serviveName) {
        this.serviveName = serviveName;
    }

    public String getGarageId() {
        return garageId;
    }

    public void setGarageId(String garageId) {
        this.garageId = garageId;
    }

    public boolean isclosed() {
        return isclosed;
    }

    public void setIsclosed(boolean isclosed) {
        this.isclosed = isclosed;
    }

    @Override
    public  Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
