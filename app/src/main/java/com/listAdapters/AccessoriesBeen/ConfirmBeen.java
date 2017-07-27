package com.listAdapters.AccessoriesBeen;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 09-February-9-2017.
 */

public class ConfirmBeen implements Parcelable {
    private String productName="";
    private String category="";
    private String quantity="";
    private String price="";
    private String orderId="";
    private String imgPath="";

    public ConfirmBeen() {
    }

    public ConfirmBeen(String productName, String category, String quantity, String price, String orderId, String imgPath) {
        this.productName = productName;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.orderId = orderId;
        this.imgPath = imgPath;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    public static final Parcelable.Creator<ConfirmBeen> CREATOR=new Creator<ConfirmBeen>() {
        @Override
        public ConfirmBeen createFromParcel(Parcel source) {
            return new ConfirmBeen(source);
        }

        @Override
        public ConfirmBeen[] newArray(int size) {
            return new ConfirmBeen[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productName);
        dest.writeString(category);
        dest.writeString(quantity);
        dest.writeString(price);
        dest.writeString(orderId);
        dest.writeString(imgPath);

    }
    public  ConfirmBeen(Parcel in){
        productName=in.readString();
        category=in.readString();
        quantity=in.readString();
        price=in.readString();
        orderId=in.readString();
        imgPath=in.readString();
    }
}
