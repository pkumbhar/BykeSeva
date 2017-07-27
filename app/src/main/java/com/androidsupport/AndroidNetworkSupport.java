package com.androidsupport;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.text.Html;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Priyatam on 10-03-2016.
 */
public   class AndroidNetworkSupport {

    /*
    check Network Connection
     */
    public boolean isConnected(Activity mContext){
        boolean conn=false;
        ConnectivityManager con=(ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network=con.getActiveNetworkInfo();

        try{
           if(network.isAvailable()) {
               if (network.isConnected()) {
                   conn = true;
               }
           }
        }catch (Exception e){
            e.printStackTrace();
            conn = false;
        }
     return conn;
    }
    /*
    netWork Alert msg
     */
    public boolean connectToNetWork( final Activity mContext){
        AlertDialog.Builder networkAlert = new AlertDialog.Builder(mContext);
        networkAlert.setTitle("Internet Required.!");
        networkAlert.setMessage("Connect To Network");
        networkAlert.setCancelable(false);

        networkAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mContext.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                dialog.dismiss();
            }
        });
        networkAlert.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = networkAlert.create();
        alert11.show();
        return  true;
    }
    /*
    network toastmsg
     */
    public boolean connectionToast(Activity mContext){
        ConnectivityManager connectivityManager=(ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();
        try{
            if(networkInfo.isAvailable()){
                if(networkInfo.isConnected()){

                    Toast toast= Toast.makeText(mContext,
                            Html.fromHtml( "<font color='#3CFF33'' ><b>" +"Connected..!"+ "</b></font>"), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            Toast toast= Toast.makeText(mContext,
                    Html.fromHtml( "<font color='#FE2E2E'' ><b>" +"No Internet Connection"+ "</b></font>"), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return false;
    }
}
