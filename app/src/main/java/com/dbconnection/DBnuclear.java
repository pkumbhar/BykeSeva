package com.dbconnection;

import android.telecom.Connection;
import android.util.Log;

import java.sql.DriverManager;

/**
 * Created by Admin on 13-January-13-2017.
 */

public class DBnuclear  {
//con=DriverManager.getConnection("jdbc:mysql://localhost:3306/userdatabase","root","admin");
    private static DBnuclear dBnuclear;
    public static String databaseName="garage";
    public static String connectionString="jdbc:mysql://localhost:3306/"+databaseName;
    public static String userName="root";
    public static String password="admin";
    private String TAG="DBnuclear";
    private DBnuclear(){
        Log.i("Loading Driver"," "+TAG);
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e){
        }
    }

    public static DBnuclear getInstance(){
        if(dBnuclear==null){
            dBnuclear=new DBnuclear();
        }
        return dBnuclear;
    }
    public java.sql.Connection connection(){
        java.sql.Connection conn=null;
        try{
            conn= DriverManager.getConnection(connectionString,userName,password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }

}
