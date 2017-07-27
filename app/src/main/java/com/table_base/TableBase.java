 package com.table_base;

/**
 * Created by Admin on 09-03-2016.
 */
public class TableBase {


    public static class Table{
        public static class USERTABLE{
            public static String USER="USER";
            public static String id="id";
            public static String FIRSTNAME="FIRSTNAME";
            public static String LASTNAME="LASTNAME";
            public static String MOBILENUMBER="MOBILENUMBER";
            public static String SUBURBAN="SUBURBAN";
            public static String DEVICEID="DEVICEID";
            public static String EMAIL="EMAIL";
            public static String PASSWORD="PASSWORD";
            public static String SER_USER_ID="SER_USER_ID";
            public static String CITY="CITY";
            public static String CITY_ID="CITY_ID";
            public static String ADDRESS_LINE1="ADDRESS_LINE1";
            public static String ADDRESS_LINE2="ADDRESS_LINE2";
        }


        public static class BookMaster{
            public static String BOOK_MASTER="BOOK_MASTER";
            public static String ID="ID";
            public static String BOOK_MASTER_ID="BOOK_MASTER_ID";
            public static  String BOOK_DATE="BOOK_DATE";
            public static String BOOK_TIME="BOOK_TIME";
            public static String VEHICLE_NUMBER="VEHICLE_NUMBER";
            public static String EXPECTED_DELIVERY="EXPECTED_DELIVERY";
            public static String EXTERNAL_SERVICES="EXTERNAL_SERVICES";
            public static String VEHICLE_MODEL_ID="VEHICLE_MODEL_ID";
            public static String GARAGE_ID="GARAGE_ID";
            public static String RESERVATION_ID="RESERVATION_ID";
        }

        public static class BookVehicle{
            public static String BOOK_VEHICLE="BOOK_VEHICLE";
            public static String ID="ID";
            public static String BOOK_VEHICLE_ID="BOOK_VEHICLE_ID";
            public static  String GARAGE_SERVICE_ID="GARAGE_SERVICE_ID";
            public static String BOOK_MASTER_ID="BOOK_MASTER_ID";

        }
        public static class City{
            public static String CITY="CITY";
            public static String ID="ID";
            public static String CITY_ID="CITY_ID";
            public static  String CITY_NAME="CITY_NAME";
            public static String STATE_ID="STATE_ID";
        }
        public static class Country{
            public static String COUNTRY="COUNTRY";
            public static String ID="ID";
            public static String COUNTRY_ID="COUNTRY_ID";
            public static  String COUNTRY_NAME="COUNTRY_NAME";
        }
        public static class Employee{
            public static String EMPLOYEE="EMPLOYEE";
            public static String ID="ID";
            public static String EMPLOYEE_ID="EMPLOYEE_ID";
            public static  String NAME="NAME";
            public static String CONTACT_NUMBER="CONTACT_NUMBER";
            public static String USER_NAME="USER_NAME";
            public static String PASSWORD="PASSWORD";
            public static String IS_LOGIN="IS_LOGIN";
            public static String IS_CLOSE="IS_CLOSE";
            public static String GARAGE_ID="GARAGE_ID";
            public static String CREATED_ON="CREATED_ON";
        }
        public static class Garage{
            public static String GARAGE="GARAGE";
            public static String ID="ID";
            public static String GARAGE_ID="GARAGE_ID";
            public static String CONTACT_NUMBER="CONTACT_NUMBER";
            public static String GARAGE_NAME="GARAGE_NAME";
            public static String ALTERNATIVE_CONTACT_NUMBER="ALTERNATIVE_CONTACT_NUMBER";
            public static String OPEN_TIME="OPEN_TIME";
            public static String CLOSE_TIME="CLOSE_TIME";
            public static String RATING="RATING";
            public static String STATUS ="STATUS";
            public static String IS_CLOSE="IS_CLOSE";
            public static String GARAGE_OWNER_ID="GARAGE_OWNER_ID";
        }
        public static class GarageAddress{
            public static String GARAGE_ADDRESS="GARAGE_ADDRESS";
            public static String ID="ID";
            public static String GARAGE_ID="GARAGE_ID";
            public static  String CITY_ID="CITY_ID";
            public static String PIN="PIN";
            public static String SUBURBAN_NAME="SUBURBAN_NAME";
            public static String ADDRESS_LINE1="ADDRESS_LINE1";
            public static String ADDRESS_LINE2="ADDRESS_LINE2";
            public static String GARAGE_ADDRESS_ID="GARAGE_ADDRESS_ID";
            public static String LAT="LAT";
            public static String LONG ="LONG";
            public static String IS_CLOSE="IS_CLOSE";
        }
        public static class GarageFacility{
            public static String GARAGE_FACILITY="GARAGE_FACILITY";
            public static String ID="ID";
            public static String GARAGE_FACILITY_ID="GARAGE_FACILITY_ID";
            public static  String GARAGE_FACILITY_NAME="GARAGE_FACILITY_NAME";
            public static String GARAGE_ID="GARAGE_ID";
            public static String IS_CLOSE="IS_CLOSE";
        }

        public static class GarageOwner{
            public static String GARAGE_OWNER="GARAGE_OWNER";
            public static String ID="ID";
            public static String FIRST_NAME="FIRST_NAME";
            public static  String LAST_NAME="LAST_NAME";
            public static String CONTACT_NUMBER="CONTACT_NUMBER";
            public static String EMAIL_ID="EMAIL_ID";
            public static String ALTERNATIVE_CONTACT_NUMBER="ALTERNATIVE_CONTACT_NUMBER";
            public static String ALTERNATIVE_EMAIL_ID="ALTERNATIVE_EMAIL_ID";
            public static String ADDRESS_LINE1="ADDRESS_LINE1";
            public static String ADDRESS_LINE2="ADDRESS_LINE2";
            public static String SUB_URBAN_NAME ="SUB_URBAN_NAME";
            public static String PIN="PIN";
            public static String CITY="CITY";
            public static String STATE="STATE";
            public static String COUNTRY="COUNTRY";
            public static String GARAGE_OWNER_ID="GARAGE_OWNER_ID";
            public static String IS_CLOSED="IS_CLOSED";
        }
        public static class GarageService{
            public static String GARAGE_SERVICE="GARAGE_SERVICE";
            public static String ID="ID";
            public static String GARAGE_SERVICE_ID="GARAGE_SERVICE_ID";
            public static  String GARAGE_SERVICE_NAME="GARAGE_SERVICE_NAME";
            public static String GARAGE_ID="GARAGE_ID";
            public static String IS_CLOSE="IS_CLOSE";
        }
        public static class Vehicle{
            public static String VEHICLE="VEHICLE";
            public static String ID="ID";
            public static String VEHICLE_ID="VEHICLE_ID";
            public static  String VEHICLE_NAME="VEHICLE_NAME";
            public static String VEHICLE_TYPE_ID="VEHICLE_TYPE_ID";
            public static String BRAND_POSTER="BRAND_POSTER";

        }
        public static class VehicleType{
            public static String VEHICLE_TYPE="VEHICLE_TYPE";
            public static String ID ="ID";
            public static String VEHICLE_TYPE_ID="VEHICLE_TYPE_ID";
            public static  String VEHICLE_TYPE_NAME="VEHICLE_TYPE_NAME";

        }

        public static class Reservation{
            public static String RESERVATION="RESERVATION";
            public static String ID ="ID";
            public static String RESERVATION_ID="RESERVATION_ID";
            public static  String TOTAL_NO_OF_VEHICLE="TOTAL_NO_OF_VEHICLE";
            public static  String TOTAL_PRICE="TOTAL_PRICE";
            public static  String RESERVATION_DATE="RESERVATION_DATE";
            public static  String USER_ID="USER_ID";
        }

        public static class SeqGen{
            public static String SEQGEN="SEQGEN";
            public static String ID ="ID";
            public static String NAME="NAME";
            public static  String YEAR="YEAR";
            public static  String COUNT="COUNT";
            public static  String INIT_SEQ="INIT_SEQ";
        }

        public static class State{
            public static String STATE="STATE";
            public static String ID ="ID";
            public static String STATE_ID="STATE_ID";
            public static  String YEAR="YEAR";
            public static  String STATE_NAME="STATE_NAME";
            public static  String COUNTRY_ID ="COUNTRY_ID";
        }

        public static class UserReview{
            public static String USER_REVIEW="USER_REVIEW";
            public static String ID ="ID";
            public static String REVIEW_TITLE="REVIEW_TITLE";
            public static  String DESCRIPTION="DESCRIPTION";
            public static  String USER_ID="USER_ID";
            public static  String GARAGE_ID ="GARAGE_ID";
        }
        public static class VehicleGarage{
            public static String VEHICLE_GARAGE="VEHICLE_GARAGE";
            public static String ID ="ID";
            public static String VEHICLE_GARAGE_ID="VEHICLE_GARAGE_ID";
            public static  String VEHICLE_MODEL_ID="VEHICLE_MODEL_ID";
            public static  String GARAGE_ID ="GARAGE_ID";
        }
        public static class VehicleModel{
            public static String VEHICLE_MODEL="VEHICLE_MODEL";
            public static String ID ="ID";
            public static String VEHICLE_MODEL_NAME="VEHICLE_MODEL_NAME";
            public static  String VEHICLE_MODEL_ID="VEHICLE_MODEL_ID";
            public static  String IS_CLOSE ="IS_CLOSE";
            public static  String VEHICLE_ID ="VEHICLE_ID";
        }
        public static class OWNER_VEHICLE_INFO{
            public static String OWNER_VEHICLE_INFO="OWNER_VEHICLE_INFO";
            public static String VEHICLE_ID="VEHICLE_ID";
            public static String BRAND_NAME="BRAND_NAME";
            public static String MODEL_NAME="MODEL_NAME";
            public static String VEHICLE_REG_NO="VEHICLE_REG_NO";
            public static String YEAR_OF_MODEL="YEAR_OF_MODEL";
            public static String TOTAL_KM="TOTAL_KM";
            public static String FUEL_TYPE="FUEL_TYPE";
            public static String COMPANY_PRICE="COMPANY_PRICE";
            public static String YOUR_PRICE="YOUR_PRICE";
            public static String COLOR="COLOR";
            public static String INSURANCE_EXPIRE="INSURANCE_EXPIRE";
            public static String VEHICLE_LOCATION="VEHICLE_LOCATION";
            public static String VEHICLE_CITY="VEHICLE_CITY";
            public static String TERMS_AND_CONDITION="TERMS_AND_CONDITION";
            public static String DESCRIPTION="DESCRIPTION";
            public static String OWNER_PRIROTY="OWNER_PRIROTY";
            public static String NUMBER_OF_OWNER="NUMBER_OF_OWNER";
            public static String POSTED_DATE="POSTED_DATE";
            public static String DATE_REG="DATE_REG";
            public static String OWNER_ID="OWNER_ID";
            public static String LINKS="LINKS";
            public static String ISCLOSED="ISCLOSED";
        }
        public static class OWNER{
            public static String OWNER="OWNER";
            public static String OWNERID="OWNERID";
            public static String NAME="NAME";
            public static String NUMBER="NUMBER";
            public static String EMAIL="EMAIL";
            public static String ADDRESS="ADDRESS";
            public static String DATE_OF_REG="DATE_OF_REG";
            public static String STATUS="STATUS";
            public static String PASSWORD="PASSWORD";
            public static String YOU_ARE="YOU_ARE";
        }
        public static class PRODUCT_CATEGORIES{
            public static String PRODUCT_CATEGORIES="PRODUCT_CATEGORIES";
            public static String ID="ID";
            public static String NAME="NAME";
        }
        public static class PRODUCT_SUB_CATEGORIES{
            public static String PRODUCT_SUB_CATEGORIES="PRODUCT_SUB_CATEGORIES";
            public static String ID="ID";
            public static String SUB_CATEGORIES_NAME="SUB_CATEGORIES_NAME";
            public static String PRODUCT_CATEGORIES_ID="PRODUCT_CATEGORIES_ID";
        }
        public static class PRODUCT_BRAND{
            public static String PRODUCT_BRAND="PRODUCT_BRAND";
            public static String ID="ID";
            public static String NAME="NAME";
            public static String PRODUCT_SUB_CATEGORIES_ID="PRODUCT_SUB_CATEGORIES_ID";
        }
        public static class CART_DTLS{
            public static String ID="ID";
            public static String CART_DETLS="CART_DETLS";
            public static String PRODUCT="PRODUCT";
            public static String FIELD1="FIELD1";
            public static String FIELD2="FIELD2";
        }
    }


    public static class CREATETABLE{
        /*
        Table To Save a Serialze object. for my cart option
         */
        public static String CART_DETLS="create table if not exists CART_DETLS(ID integer primary key autoincrement, "
                +Table.CART_DTLS.PRODUCT+" BLOB, "
                +Table.CART_DTLS.FIELD1+" text, "
                +Table.CART_DTLS.FIELD2+" text);";

        /*
        Accessorices Creation of table
         */

        public static String PRODUCT_CATEGORIES="create table if not exists PRODUCT_CATEGORIES (ID TEXT,"
                +Table.PRODUCT_CATEGORIES.NAME+" text);";

        public static String PRODUCT_SUB_CATEGORIES="create table if not exists PRODUCT_SUB_CATEGORIES(ID TEXT, SUB_CATEGORIES_NAME text,PRODUCT_CATEGORIES_ID text);";
        public static String PRODUCT_BRAND="create table if not exists PRODUCT_BRAND(ID TEXT, NAME text,PRODUCT_SUB_CATEGORIES_ID text);";

        public static String OWNER_VEHICLE_INFO="create table if not exists "
                +Table.OWNER_VEHICLE_INFO.OWNER_VEHICLE_INFO
                +" (id integer primary key autoincrement, "+Table.OWNER_VEHICLE_INFO.VEHICLE_ID+" text, "
                +Table.OWNER_VEHICLE_INFO.BRAND_NAME+" text,"
                +Table.OWNER_VEHICLE_INFO.MODEL_NAME+" text,"
                +Table.OWNER_VEHICLE_INFO.VEHICLE_REG_NO+" text,"
                +Table.OWNER_VEHICLE_INFO.YEAR_OF_MODEL+" text,"
                +Table.OWNER_VEHICLE_INFO.TOTAL_KM+" text,"
                +Table.OWNER_VEHICLE_INFO.FUEL_TYPE+" text,"
                +Table.OWNER_VEHICLE_INFO.YOUR_PRICE+" text,"
                +Table.OWNER_VEHICLE_INFO.COMPANY_PRICE+" text,"
                +Table.OWNER_VEHICLE_INFO.INSURANCE_EXPIRE+" text,"
                +Table.OWNER_VEHICLE_INFO.VEHICLE_CITY+" text,"
                +Table.OWNER_VEHICLE_INFO.VEHICLE_LOCATION+" text,"
                +Table.OWNER_VEHICLE_INFO.DESCRIPTION+" text,"
                +Table.OWNER_VEHICLE_INFO.DATE_REG+" text,"
                +Table.OWNER_VEHICLE_INFO.POSTED_DATE+" text,"
                +Table.OWNER_VEHICLE_INFO.NUMBER_OF_OWNER+" text,"
                +Table.OWNER_VEHICLE_INFO.LINKS+" text,"
                +Table.OWNER_VEHICLE_INFO.OWNER_ID+" text "+");";

        /**Owner Info table**/
        public static  String OWNER="create table if not exists "+Table.OWNER.OWNER+" (id integer primary key autoincrement, "+Table.OWNER.OWNERID+" text, "
                +Table.OWNER.NAME+" text, "
                +Table.OWNER.NUMBER+" text,"
                +Table.OWNER.EMAIL+" text,"
                +Table.OWNER.ADDRESS+" text, "
                +Table.OWNER.DATE_OF_REG+" text,"
                +Table.OWNER.STATUS+" text,"
                +Table.OWNER.PASSWORD+" text,"
                +Table.OWNER.YOU_ARE+" text "+");";
        /** creating table for user login*/
        public static String USER="CREATE TABLE IF NOT EXISTS "+ Table.USERTABLE.USER+" ("+ Table.USERTABLE.id+
                " integer primary key autoincrement,"+ Table.USERTABLE.SER_USER_ID+" text,"+
                Table.USERTABLE.FIRSTNAME+" text,"+
                Table.USERTABLE.LASTNAME+" text,"+ Table.USERTABLE.MOBILENUMBER+" text, "+
                Table.USERTABLE.EMAIL+" text, "+
                Table.USERTABLE.SUBURBAN+" text, "+
                Table.USERTABLE.DEVICEID+" text ,"+
                Table.USERTABLE.ADDRESS_LINE1+ " text," +
                Table.USERTABLE.ADDRESS_LINE2+ " text," +
                Table.USERTABLE.CITY+ " text," +
                Table.USERTABLE.CITY_ID+ " text," +
                Table.USERTABLE.PASSWORD+" text"+");";
        /*
        move table query ----
         */
        public static  String BOOK_MASTER="CREATE TABLE IF NOT EXISTS "+ Table.BookMaster.BOOK_MASTER+" ("+ Table.BookMaster.ID+
                "  integer primary key autoincrement, "+ Table.BookMaster.BOOK_DATE+" text, "+
                Table.BookMaster.BOOK_TIME+" text,"+
                Table.BookMaster.BOOK_MASTER_ID+" text,"+
                Table.BookMaster.VEHICLE_NUMBER+" text,"+
                Table.BookMaster.EXPECTED_DELIVERY+" text,"+
                Table.BookMaster.EXTERNAL_SERVICES+" text,"+
                Table.BookMaster.VEHICLE_MODEL_ID+" text,"+
                Table.BookMaster.GARAGE_ID+" text,"+
                Table.BookMaster.RESERVATION_ID+" text"+");";

        public static  String VEHICLE="CREATE TABLE IF NOT EXISTS "+ Table.Vehicle.VEHICLE+" ("+ Table.Vehicle.ID +
                " integer primary key autoincrement, "+ Table.Vehicle.VEHICLE_ID+ " text,"+
                Table.Vehicle.VEHICLE_NAME+ " text,"+
                Table.Vehicle.BRAND_POSTER+ " text,"+
                Table.Vehicle.VEHICLE_TYPE_ID+ " text"+");";

        public static  String VEHICLE_TYPE="CREATE TABLE IF NOT EXISTS "+ Table.VehicleType.VEHICLE_TYPE+" ("+ Table.VehicleType.ID +
                " integer primary key autoincrement, "+ Table.VehicleType.VEHICLE_TYPE_ID+ " text,"+
                Table.VehicleType.VEHICLE_TYPE_NAME+ " text"+");";

        public static  String BOOK_VEHICLE="CREATE TABLE IF NOT EXISTS "+ Table.BookVehicle.BOOK_VEHICLE+" ("+ Table.BookVehicle.ID +
                " integer primary key autoincrement, "+ Table.BookVehicle.BOOK_VEHICLE_ID+ " text,"+
                Table.BookVehicle.BOOK_MASTER_ID+ " text,"+
                Table.BookVehicle.GARAGE_SERVICE_ID+ " text"+");";

        public static  String CITY="CREATE TABLE IF NOT EXISTS "+ Table.City.CITY+" ("+ Table.City.ID +
                " integer primary key autoincrement, "+ Table.City.CITY_ID+ " text,"+
                Table.City.CITY_NAME+ " text,"+
                Table.City.STATE_ID+ " text"+");";

        public static  String COUNTRY="CREATE TABLE IF NOT EXISTS "+ Table.Country.COUNTRY+" ("+ Table.Country.ID +
                " integer primary key autoincrement, "+ Table.Country.COUNTRY_ID+ " text,"+
                Table.Country.COUNTRY_NAME+ " text"+");";

        public static  String EMPLOYEE="CREATE TABLE IF NOT EXISTS "+ Table.Employee.EMPLOYEE+" ("+ Table.Employee.ID+
                "  integer primary key autoincrement, "+ Table.Employee.EMPLOYEE_ID+" text, "+
                Table.Employee.NAME+" text,"+
                Table.Employee.CONTACT_NUMBER+" text,"+
                Table.Employee.CREATED_ON+" text,"+
                Table.Employee.IS_CLOSE+" text,"+
                Table.Employee.IS_LOGIN+" text,"+
                Table.Employee.USER_NAME+" text,"+
                Table.Employee.GARAGE_ID+" text,"+
                Table.Employee.PASSWORD+" text"+");";

        public static  String GARAGE="CREATE TABLE IF NOT EXISTS "+ Table.Garage.GARAGE+" ("+ Table.Garage.ID+
                "  integer primary key autoincrement, "+ Table.Garage.GARAGE_ID+" text, "+
                Table.Garage.GARAGE_NAME+" text,"+
                Table.Garage.GARAGE_OWNER_ID+" text,"+
                Table.Garage.CONTACT_NUMBER+" text,"+
                Table.Garage.ALTERNATIVE_CONTACT_NUMBER+" text,"+
                Table.Garage.STATUS+" text,"+
                Table.Garage.IS_CLOSE+" text,"+
                Table.Garage.CLOSE_TIME+" text,"+
                Table.Garage.OPEN_TIME+" text,"+
                Table.Garage.RATING+" text"+");";

        public static  String GARAGE_ADDRESS="CREATE TABLE IF NOT EXISTS "+ Table.GarageAddress.GARAGE_ADDRESS+" ("+ Table.GarageAddress.ID+
                "  integer primary key autoincrement, "+ Table.GarageAddress.GARAGE_ADDRESS_ID+" text, "+
                Table.GarageAddress.SUBURBAN_NAME+" text,"+
                Table.GarageAddress.GARAGE_ID+" text,"+
                Table.GarageAddress.PIN+" text,"+
                Table.GarageAddress.CITY_ID+" text,"+
                Table.GarageAddress.ADDRESS_LINE1+" text,"+
                Table.GarageAddress.IS_CLOSE+" text,"+
                Table.GarageAddress.ADDRESS_LINE2+" text,"+
                Table.GarageAddress.LAT+" text,"+
                Table.GarageAddress.LONG+" text"+");";

        public static  String GARAGE_FACILITY="CREATE TABLE IF NOT EXISTS "+ Table.GarageFacility.GARAGE_FACILITY+" ("+ Table.GarageFacility.ID +
                " integer primary key autoincrement, "+ Table.GarageFacility.GARAGE_FACILITY_ID+ " text,"+
                Table.GarageFacility.GARAGE_FACILITY_NAME+ " text,"+
                Table.GarageFacility.IS_CLOSE+ " text,"+
                Table.GarageFacility.GARAGE_ID+ " text"+");";

        public static  String GARAGE_OWNER="CREATE TABLE IF NOT EXISTS "+ Table.GarageOwner.GARAGE_OWNER+" ("+ Table.GarageOwner.ID+
                "  integer primary key autoincrement, "+ Table.GarageOwner.GARAGE_OWNER_ID+" text, "+
                Table.GarageOwner.FIRST_NAME+" text,"+
                Table.GarageOwner.IS_CLOSED+" text,"+
                Table.GarageOwner.CONTACT_NUMBER+" text,"+
                Table.GarageOwner.ALTERNATIVE_CONTACT_NUMBER+" text,"+
                Table.GarageOwner.LAST_NAME+" text,"+
                Table.GarageOwner.EMAIL_ID+" text,"+
                Table.GarageOwner.ALTERNATIVE_EMAIL_ID+" text,"+
                Table.GarageOwner.ADDRESS_LINE1+" text,"+
                Table.GarageOwner.PIN+" text,"+
                Table.GarageOwner.CITY+" text,"+
                Table.GarageOwner.STATE+" text,"+
                Table.GarageOwner.COUNTRY+" text,"+
                Table.GarageOwner.SUB_URBAN_NAME+" text,"+
                Table.GarageOwner.ADDRESS_LINE2+" text"+");";



        public static  String GARAGE_SERVICE="CREATE TABLE IF NOT EXISTS "+ Table.GarageService.GARAGE_SERVICE+" ("+ Table.GarageService.ID +
                " integer primary key autoincrement, "+ Table.GarageService.GARAGE_SERVICE_ID+ " text,"+
                Table.GarageService.IS_CLOSE+ " text,"+
                Table.GarageService.GARAGE_SERVICE_NAME+ " text,"+
                Table.GarageService.GARAGE_ID+ " text"+");";

        public static  String RESERVATION="CREATE TABLE IF NOT EXISTS "+ Table.Reservation.RESERVATION+" ("+ Table.Reservation.ID +
                " integer primary key autoincrement, "+ Table.Reservation.RESERVATION_DATE+ " text,"+
                Table.Reservation.RESERVATION_ID+ " text,"+
                Table.Reservation.TOTAL_NO_OF_VEHICLE+ " text,"+
                Table.Reservation.USER_ID+ " text,"+
                Table.Reservation.TOTAL_PRICE+ " text"+");";

        public static  String SEQGEN="CREATE TABLE IF NOT EXISTS "+ Table.SeqGen.SEQGEN+" ("+ Table.SeqGen.ID +
                " integer primary key autoincrement, "+ Table.SeqGen.COUNT+ " text,"+
                Table.SeqGen.INIT_SEQ+ " text,"+
                Table.SeqGen.NAME+ " text,"+
                Table.SeqGen.YEAR+ " text"+");";

        public static  String STATE="CREATE TABLE IF NOT EXISTS "+ Table.State.STATE+" ("+ Table.State.ID +
                " integer primary key autoincrement, "+ Table.State.STATE_ID+ " text,"+
                Table.State.STATE_NAME+ " text,"+
                Table.State.COUNTRY_ID+ " text,"+
                Table.State.YEAR+ " text"+");";

        public static  String USER_REVIEW="CREATE TABLE IF NOT EXISTS "+ Table.UserReview.USER_REVIEW+" ("+ Table.UserReview.ID +
                " integer primary key autoincrement, "+ Table.UserReview.DESCRIPTION+ " text,"+
                Table.UserReview.GARAGE_ID+ " text,"+
                Table.UserReview.REVIEW_TITLE+ " text,"+
                Table.UserReview.USER_ID+ " text"+");";

        public static  String VEHICLE_GARAGE="CREATE TABLE IF NOT EXISTS "+ Table.VehicleGarage.VEHICLE_GARAGE+" ("+ Table.VehicleGarage.ID +
                " integer primary key autoincrement, "+ Table.VehicleGarage.GARAGE_ID+ " text,"+
                Table.VehicleGarage.VEHICLE_GARAGE_ID+ " text,"+
                Table.VehicleGarage.VEHICLE_MODEL_ID+ " text"+");";

        public static  String VEHICLE_MODEL="CREATE TABLE IF NOT EXISTS "+ Table.VehicleModel.VEHICLE_MODEL+" ("+ Table.VehicleModel.ID +
                " integer primary key autoincrement, "+ Table.VehicleModel.VEHICLE_ID+ " text,"+
                Table.VehicleModel.VEHICLE_MODEL_NAME+ " text,"+
                Table.VehicleModel.IS_CLOSE+ " text,"+
                Table.VehicleModel.VEHICLE_MODEL_ID+ " text"+");";

    }
}
