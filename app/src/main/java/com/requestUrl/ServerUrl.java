package com.requestUrl;

/**
 * Created by Admin on 03-06-2016.
 */
public class ServerUrl {
    /***********************************DOWNLOAD***********************************************
     *                             COMMON DOMAINS URL THIS CHANGE WILL EFFECT            **
     ***********************************************************************************************/
//http://192.168.0.107:9999//
    public static String protocol="http://";
    public static String domain="www.nuclearwheels.com/vectarestapi/";
    public static String locationOfresource="http";
    public static String POST="POST.user";
    public static String GET="GET.user";
    public static String PUT="PUT.user";
    public static String DELETE="DELETE.user";
    public static String APIKEY="?API=VECTA-555-NU-666";



    /***********************************SERVER URLS***********************************************
     *                              URL STRING TO GET THE DATA FROM WEB SERVER  OR LOCAL HOST         **
     ***********************************************************************************************/
   // public static String imageUral=protocol.concat("www.nuclearwheels.com/");
    public static String imageUral="http://192.168.0.115:8888/LatestWheels/";

   // public static String  serverurl=protocol+domain;
   public static String serverurl="http://192.168.0.115:8888/LatestWheels/vectarestapi/";

    /*
    for LocalHost
    class DoService Provider Download Must Change in class at Line Number 83,84

     */


    public  static int USER_LOGIN=2;
}
/*Post Add History
http://192.168.0.113:8888/NuclearWheels/vectarestapi/vehicle/loadUserPostVehicles?ID=USR-2016-028
 */
