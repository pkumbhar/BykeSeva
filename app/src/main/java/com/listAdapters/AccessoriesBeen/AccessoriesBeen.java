package com.listAdapters.AccessoriesBeen;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Admin on 31-January-31-2017.
 */

public class AccessoriesBeen implements Parcelable,Cloneable,Serializable {


    public String PTYPE="PTYPE";
    public String CATEGORIESSUBNAME="CATEGORIESSUBNAME";
    public String VAT="VAT";
    public String PRESENT="PRESENT";
    public String MRP="MRP";
    public String CLOSE="CLOSE";
    public String PRODUCTID="PRODUCTID";
    public String LIVEOFFERS="LIVEOFFERS";
    public String delveryPROCESS="delveryPROCESS";
    public String delveryPRESENT="delveryPRESENT";
    public String delveryID="delveryID";
    public String delveryPINS="delveryPINS";
    public String delveryCHARGE="delveryCHARGE";
    public String delveryDESC="delveryDESC";
    public String delveryCHARGEBLE="delveryCHARGEBLE";
    public String CATEGORIESNAME="CATEGORIESNAME";
    public String ofrLIMITED="ofrLIMITED";
    public String ofrSDATE="ofrSDATE";
    public String ofrID="ofrID";
    public String ofrCLOSE="ofrCLOSE";
    public String ofrOFFERS="ofrOFFERS";
    public String ofrendEDATE="ofrendEDATE";
    public String ofrDISCOUNT="ofrDISCOUNT";
    public String STATUS="STATUS";
    public String DESCRIPTION="DESCRIPTION";
    public String COLOR="COLOR";
    public String ID="ID";
    public String DOE="DOE";
    public String WEIGHT="WEIGHT";
    public String poster="poster";
    public String type="type";
    public String PUSEDFOR="PUSEDFOR";
    public String PRODUCTNAME="PRODUCTNAME";
    public String uid="uid";
    public String uidrmdl="uidrmdl";
    public String UPRICE="UPRICE";
    public String NAME="NAME";
    public String DISCOUNT="DISCOUNT";
    public String BRAND="BRAND";
    public String TPRICE="TPRICE";
    public String UNAME="UNAME";
    public String VEHICLEUSED="VEHICLEUSED";
    public String WARRANTY="WARRANTY";
    public String VERSION="VERSION";
    public String MANU="MANU";
    public String fetrFEATURE="fetrFEATURE";
    public String fetrID="fetrID";
    public String tetrTYPE="tetrTYPE";
    public String REVIEW="REVIEW";
    public String QUANTITY="QUANTITY";

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public AccessoriesBeen() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CATEGORIESSUBNAME);
        dest.writeString(VAT);
        dest.writeString(PRESENT);
        dest.writeString(MRP);
        dest.writeString(CLOSE);
        dest.writeString(PRODUCTID);
        dest.writeString(LIVEOFFERS);
        dest.writeString(delveryPROCESS);
        dest.writeString(delveryPRESENT);
        dest.writeString(delveryPINS);
        dest.writeString(delveryID);
        dest.writeString(delveryCHARGEBLE);
        dest.writeString(delveryDESC);
        dest.writeString(CATEGORIESNAME);
        dest.writeString(ofrCLOSE);
        dest.writeString(ofrDISCOUNT);
        dest.writeString(ofrendEDATE);
        dest.writeString(ofrID);
        dest.writeString(ofrLIMITED);
        dest.writeString(ofrOFFERS);
        dest.writeString(ofrSDATE);
        dest.writeString(STATUS);
        dest.writeString(DESCRIPTION);
        dest.writeString(COLOR);
        dest.writeString(ID);
        dest.writeString(WEIGHT);
        dest.writeString(poster);
        dest.writeString(PUSEDFOR);
        dest.writeString(type);
        dest.writeString(PRODUCTNAME);
        dest.writeString(uid);
        dest.writeString(uidrmdl);
        dest.writeString(UPRICE);
        dest.writeString(DISCOUNT);
        dest.writeString(NAME);
        dest.writeString(BRAND);
        dest.writeString(TPRICE);
        dest.writeString(VEHICLEUSED);
        dest.writeString(UNAME);
        dest.writeString(WARRANTY);
        dest.writeString(VERSION);
        dest.writeString(WARRANTY);
        dest.writeString(fetrFEATURE);
        dest.writeString(fetrID);
        dest.writeString(MANU);
        dest.writeString(tetrTYPE);
        dest.writeString(REVIEW);
        dest.writeString(QUANTITY);
    }

    public static final Parcelable.Creator<AccessoriesBeen> CREATOR=new Parcelable.Creator<AccessoriesBeen>(){

        @Override
        public AccessoriesBeen createFromParcel(Parcel in) {
            return new AccessoriesBeen(in);
        }

        @Override
        public AccessoriesBeen[] newArray(int size) {
            return new AccessoriesBeen[size];

        }

    };

    public AccessoriesBeen(Parcel in) {
        CATEGORIESSUBNAME=in.readString();
        VAT=in.readString();
        PRESENT=in.readString();
        MRP=in.readString();
        CLOSE=in.readString();
        PRODUCTID=in.readString();
        LIVEOFFERS=in.readString();
        delveryPROCESS=in.readString();
        delveryPRESENT=in.readString();
        delveryPINS=in.readString();
        delveryID=in.readString();
        delveryCHARGEBLE=in.readString();
        delveryDESC=in.readString();
        CATEGORIESNAME=in.readString();
        ofrCLOSE=in.readString();
        ofrDISCOUNT=in.readString();
        ofrendEDATE=in.readString();
        ofrID=in.readString();
        ofrLIMITED=in.readString();
        ofrOFFERS=in.readString();
        ofrSDATE=in.readString();
        STATUS=in.readString();
        DESCRIPTION=in.readString();
        COLOR=in.readString();
        ID=in.readString();
        WEIGHT=in.readString();
        poster=in.readString();
        PUSEDFOR=in.readString();
        type=in.readString();
        PRODUCTNAME=in.readString();
        uid=in.readString();
        uidrmdl=in.readString();
        UPRICE=in.readString();
        DISCOUNT=in.readString();
        NAME=in.readString();
        BRAND=in.readString();
        TPRICE=in.readString();
        VEHICLEUSED=in.readString();
        UNAME=in.readString();
        WARRANTY=in.readString();
        VERSION=in.readString();
        WARRANTY=in.readString();
        fetrFEATURE=in.readString();
        fetrID=in.readString();
        MANU=in.readString();
        tetrTYPE=in.readString();
        REVIEW=in.readString();
        QUANTITY=in.readString();

    }


    @Override
    public int describeContents() {
        return 0;
    }

    public String getPTYPE() {
        return PTYPE;
    }

    public void setPTYPE(String PTYPE) {
        this.PTYPE = PTYPE;
    }

    public String getCATEGORIESSUBNAME() {
        return CATEGORIESSUBNAME;
    }

    public void setCATEGORIESSUBNAME(String CATEGORIESSUBNAME) {
        this.CATEGORIESSUBNAME = CATEGORIESSUBNAME;
    }

    public String getVAT() {
        return VAT;
    }

    public void setVAT(String VAT) {
        this.VAT = VAT;
    }

    public String getPRESENT() {
        return PRESENT;
    }

    public void setPRESENT(String PRESENT) {
        this.PRESENT = PRESENT;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public String getCLOSE() {
        return CLOSE;
    }

    public void setCLOSE(String CLOSE) {
        this.CLOSE = CLOSE;
    }

    public String getPRODUCTID() {
        return PRODUCTID;
    }

    public void setPRODUCTID(String PRODUCTID) {
        this.PRODUCTID = PRODUCTID;
    }

    public String getLIVEOFFERS() {
        return LIVEOFFERS;
    }

    public void setLIVEOFFERS(String LIVEOFFERS) {
        this.LIVEOFFERS = LIVEOFFERS;
    }

    public String getDelveryPROCESS() {
        return delveryPROCESS;
    }

    public void setDelveryPROCESS(String delveryPROCESS) {
        this.delveryPROCESS = delveryPROCESS;
    }

    public String getDelveryPRESENT() {
        return delveryPRESENT;
    }

    public void setDelveryPRESENT(String delveryPRESENT) {
        this.delveryPRESENT = delveryPRESENT;
    }

    public String getDelveryID() {
        return delveryID;
    }

    public void setDelveryID(String delveryID) {
        this.delveryID = delveryID;
    }

    public String getDelveryPINS() {
        return delveryPINS;
    }

    public void setDelveryPINS(String delveryPINS) {
        this.delveryPINS = delveryPINS;
    }

    public String getDelveryCHARGE() {
        return delveryCHARGE;
    }

    public void setDelveryCHARGE(String delveryCHARGE) {
        this.delveryCHARGE = delveryCHARGE;
    }

    public String getDelveryDESC() {
        return delveryDESC;
    }

    public void setDelveryDESC(String delveryDESC) {
        this.delveryDESC = delveryDESC;
    }

    public String getDelveryCHARGEBLE() {
        return delveryCHARGEBLE;
    }

    public void setDelveryCHARGEBLE(String delveryCHARGEBLE) {
        this.delveryCHARGEBLE = delveryCHARGEBLE;
    }

    public String getCATEGORIESNAME() {
        return CATEGORIESNAME;
    }

    public void setCATEGORIESNAME(String CATEGORIESNAME) {
        this.CATEGORIESNAME = CATEGORIESNAME;
    }

    public String getOfrLIMITED() {
        return ofrLIMITED;
    }

    public void setOfrLIMITED(String ofrLIMITED) {
        this.ofrLIMITED = ofrLIMITED;
    }

    public String getOfrSDATE() {
        return ofrSDATE;
    }

    public void setOfrSDATE(String ofrSDATE) {
        this.ofrSDATE = ofrSDATE;
    }

    public String getOfrID() {
        return ofrID;
    }

    public void setOfrID(String ofrID) {
        this.ofrID = ofrID;
    }

    public String getOfrCLOSE() {
        return ofrCLOSE;
    }

    public void setOfrCLOSE(String ofrCLOSE) {
        this.ofrCLOSE = ofrCLOSE;
    }

    public String getOfrOFFERS() {
        return ofrOFFERS;
    }

    public void setOfrOFFERS(String ofrOFFERS) {
        this.ofrOFFERS = ofrOFFERS;
    }

    public String getOfrendEDATE() {
        return ofrendEDATE;
    }

    public void setOfrendEDATE(String ofrendEDATE) {
        this.ofrendEDATE = ofrendEDATE;
    }

    public String getOfrDISCOUNT() {
        return ofrDISCOUNT;
    }

    public void setOfrDISCOUNT(String ofrDISCOUNT) {
        this.ofrDISCOUNT = ofrDISCOUNT;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getCOLOR() {
        return COLOR;
    }

    public void setCOLOR(String COLOR) {
        this.COLOR = COLOR;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDOE() {
        return DOE;
    }

    public void setDOE(String DOE) {
        this.DOE = DOE;
    }

    public String getWEIGHT() {
        return WEIGHT;
    }

    public void setWEIGHT(String WEIGHT) {
        this.WEIGHT = WEIGHT;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPUSEDFOR() {
        return PUSEDFOR;
    }

    public void setPUSEDFOR(String PUSEDFOR) {
        this.PUSEDFOR = PUSEDFOR;
    }

    public String getPRODUCTNAME() {
        return PRODUCTNAME;
    }

    public void setPRODUCTNAME(String PRODUCTNAME) {
        this.PRODUCTNAME = PRODUCTNAME;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUidrmdl() {
        return uidrmdl;
    }

    public void setUidrmdl(String uidrmdl) {
        this.uidrmdl = uidrmdl;
    }

    public String getUPRICE() {
        return UPRICE;
    }

    public void setUPRICE(String UPRICE) {
        this.UPRICE = UPRICE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getDISCOUNT() {
        return DISCOUNT;
    }

    public void setDISCOUNT(String DISCOUNT) {
        this.DISCOUNT = DISCOUNT;
    }

    public String getBRAND() {
        return BRAND;
    }

    public void setBRAND(String BRAND) {
        this.BRAND = BRAND;
    }

    public String getTPRICE() {
        return TPRICE;
    }

    public void setTPRICE(String TPRICE) {
        this.TPRICE = TPRICE;
    }

    public String getUNAME() {
        return UNAME;
    }

    public void setUNAME(String UNAME) {
        this.UNAME = UNAME;
    }

    public String getVEHICLEUSED() {
        return VEHICLEUSED;
    }

    public void setVEHICLEUSED(String VEHICLEUSED) {
        this.VEHICLEUSED = VEHICLEUSED;
    }

    public String getWARRANTY() {
        return WARRANTY;
    }

    public void setWARRANTY(String WARRANTY) {
        this.WARRANTY = WARRANTY;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public String getMANU() {
        return MANU;
    }

    public void setMANU(String MANU) {
        this.MANU = MANU;
    }

    public String getFetrFEATURE() {
        return fetrFEATURE;
    }

    public void setFetrFEATURE(String fetrFEATURE) {
        this.fetrFEATURE = fetrFEATURE;
    }

    public String getFetrID() {
        return fetrID;
    }

    public void setFetrID(String fetrID) {
        this.fetrID = fetrID;
    }

    public String getTetrTYPE() {
        return tetrTYPE;
    }

    public void setTetrTYPE(String tetrTYPE) {
        this.tetrTYPE = tetrTYPE;
    }

    public String getREVIEW() {
        return REVIEW;
    }

    public void setREVIEW(String REVIEW) {
        this.REVIEW = REVIEW;
    }

    public String getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(String QUANTITY) {
        this.QUANTITY = QUANTITY;
    }
}
