package com.androidsupport;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.telephony.TelephonyManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Admin on 10-03-2016.
 */
public  class AndroidSupport extends AndroidNetworkSupport{

/*
get system version;
 */
public int getVersion(Context mContext){

    return  0;
}
    /*
    get Devive Id;
     */
    public String getDeviceId(Context mContext){
        TelephonyManager manager=(TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
        return  manager.getDeviceId();
    }
    /*
    get SimSerial Ni=umber
     */
    public String getSimSerialNumber(Context mContext){
        TelephonyManager manager=(TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getSimSerialNumber();
    }
    public static  Bitmap decodeFile(InputStream in,int IMAGE_SIZE){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) > -1 ) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            InputStream is1 = new ByteArrayInputStream(baos.toByteArray());
            InputStream is2 = new ByteArrayInputStream(baos.toByteArray());

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is1,null,o);

            final int IMAGE_MAX_SIZE=IMAGE_SIZE;

            System.out.println("h:" + o.outHeight + " w:" + o.outWidth);
            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int)Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE /
                        (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            Bitmap bitmap=new BitmapFactory().decodeStream(is2, null, o2);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }
}
