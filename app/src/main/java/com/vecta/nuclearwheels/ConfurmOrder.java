package com.vecta.nuclearwheels;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.background.DoConfurmOrder;
import com.db_adapter.DBAdapter;
import com.google.gson.JsonObject;
import com.listAdapters.AccessoriesBeen.AccessoriesBeen;
import com.listAdapters.AccessoriesBeen.ConfirmBeen;
import com.listAdapters.bikeAdapters.AdapterConfirmOrder;
import com.listAdapters.bikeAdapters.AdapterProduct;
import com.table_base.TableBase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ConfurmOrder extends AppCompatActivity {
private AccessoriesBeen been=null;
    private String limited="",sdate="",id="",close="",offers="",edate="",disscount="",process="",present="",pins="",charge="",desc="",chargable="",feature="",type="";
    private Button btnConfirm;
    private RecyclerView mRecyclerView;
    private AdapterConfirmOrder product;
    private TextView tvSubtotal,tvItemSubTotal;
    private EditText  edAddressOne,edAddressTwo,edCity,edState,edPostCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btnConfirm=(Button)findViewById(R.id.btn_orderConfirmId);
        tvSubtotal=(TextView)findViewById(R.id.tvsubtotalItemID);
        mRecyclerView=(RecyclerView)findViewById(R.id.recycler_ordreId);
        tvItemSubTotal=(TextView)findViewById(R.id.tvItemSubtotalID);


        try{
            Intent intent=super.getIntent();
            been=intent.getParcelableExtra("Order");
            if(been!=null){
                limited=been.getOfrLIMITED();
                sdate=been.getOfrSDATE();
                id=been.getID();
                close=been.getCLOSE();
                offers=been.getOfrOFFERS();
                edate=been.getOfrendEDATE();
                disscount=been.getDISCOUNT();
                process=been.getDelveryPROCESS();
                present=been.getPRESENT();
                pins=been.getDelveryPINS();
                charge=been.getDelveryCHARGE();
                desc=been.getDESCRIPTION();
                chargable=been.getDelveryCHARGEBLE();
                feature=been.getFetrFEATURE();
                type=been.getType();
                AccessoriesBeen accessoriesBeen=(AccessoriesBeen) been.clone();
                setOrderList(accessoriesBeen);
            }else if(been==null){
                setOrderList(null);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DoConfurmOrder(ConfurmOrder.this,getApplicationContext(),orderObject()).execute("");
            }
        });
    }
    private JSONObject orderObject(){


        JSONObject mainItemCart=new JSONObject();
        try{
            StringBuilder builder=new StringBuilder();
            String s= builder.append(edAddressOne.getText().toString()).append(",").append(edAddressTwo.getText().toString()).append(",").append(edCity.getText().toString()).append(",").append(edState.getText().toString()).append(",").append(edState.getText().toString()).toString();
            String s1=String.valueOf(s);
            mainItemCart.put("ADDRESS",s1);
            mainItemCart.put("USER","USR-2016-063");
            mainItemCart.put("ORDER",itemArray());
        }catch (Exception e){
            e.printStackTrace();
        }
        return mainItemCart;
    }
    private JSONArray itemArray(){
        JSONObject  itemDetails=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        try{
            itemDetails.put("PTYPE","Perfumes");
            itemDetails.put("CATEGORIESSUBNAME",been.getCATEGORIESSUBNAME());
            itemDetails.put("VAT",been.getVAT());
            itemDetails.put("TOTALQTY","2");
            itemDetails.put("PRESENT",been.getPRESENT());
            itemDetails.put("MRP",been.getMRP());
            itemDetails.put("CLOSE",been.getCLOSE());
            itemDetails.put("PRODUCTID",been.getPRODUCTID());
            itemDetails.put("LIVEOFFERS",been.getLIVEOFFERS());
            itemDetails.put("DELIVERY",getdelevaryArray());
            itemDetails.put("CATEGORIESNAME",been.getCATEGORIESNAME());
            itemDetails.put("OFFERES",getOffersArray());
            itemDetails.put("STATUS",been.getSTATUS());
            itemDetails.put("DESCRIPTION",been.getDESCRIPTION());
            itemDetails.put("COLOR",been.getCOLOR());
            itemDetails.put("ID",been.getID());
            itemDetails.put("DOE",been.getDOE());
            itemDetails.put("WEIGHT",been.getWEIGHT());
            itemDetails.put("POSTERS",getPosterArray());
            itemDetails.put("TYPE",been.getType());
            itemDetails.put("PUSEDFOR",been.getPUSEDFOR());
            itemDetails.put("PRODUCTNAME",been.getPRODUCTNAME());
            itemDetails.put("USER",getUserObject());
            itemDetails.put("UPRICE",been.getUPRICE());
            itemDetails.put("NAME",been.getNAME());
            itemDetails.put("DISCOUNT",been.getDISCOUNT());
            itemDetails.put("BRAND",been.getBRAND());
            itemDetails.put("TPRICE",been.getTPRICE());
            itemDetails.put("UNAME",been.getUNAME());
            itemDetails.put("VEHICLEUSED",been.getVEHICLEUSED());
            itemDetails.put("WARRANTY",been.getWARRANTY());
            itemDetails.put("VERSION",been.getVERSION());
            itemDetails.put("MANU",been.getMANU());
            itemDetails.put("FEATURE",getFeaterArray());
            itemDetails.put("COMBOOFFER",false);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonArray.put(itemDetails);
    }
    private JSONArray getOffersArray(){
        JSONArray objectArray=new JSONArray();
        JSONObject object=new JSONObject();
        try{
            object.put("LIMITED",limited);
            object.put("SDATE",sdate);
            object.put("ID",id);
            object.put("CLOSE",close);
            object.put("OFFERS",offers);
            object.put("EDATE",edate);
            object.put("DISCOUNT",disscount);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  objectArray.put(object);
    }

    private JSONArray getdelevaryArray(){
        JSONArray object=new JSONArray();
        JSONObject delevaryItem=new JSONObject();
        try{
            delevaryItem.put("PROCESS",process);
            delevaryItem.put("PRESENT",present);
            delevaryItem.put("ID",id);
            delevaryItem.put("PINS",pins);
            delevaryItem.put("CHARGE",charge);
            delevaryItem.put("DESC",desc);
            delevaryItem.put("CHARGEBLE",chargable);

        }catch (Exception e){
            e.printStackTrace();
        }
        return  object.put(delevaryItem);
    }

    private JSONArray getPosterArray(){
        JSONArray object=new JSONArray();
        object.put(been.getPoster());
        return  object;
    }

    private JSONObject getUserObject(){
        JSONArray objectArray=new JSONArray();
        JSONObject object=new JSONObject();
        try{
            object.put("UID","USR-2016-063");
            object.put("ID","USRPRMDL-2017-05");

        }catch (Exception e){
            e.printStackTrace();
        }
        return object;
    }
    private JSONArray getFeaterArray(){
        JSONArray object=new JSONArray();
        JSONObject ftr=new JSONObject();
        try{
            ftr.put("FEATURE",feature);
            ftr.put("ID",been.getFetrID());
            ftr.put("TYPE",been.getType());
        }catch (Exception e){
            e.printStackTrace();
        }

        return  object;
    }
private void setOrderList(AccessoriesBeen data){
    ArrayList<ConfirmBeen> bin=new ArrayList<ConfirmBeen>();
    ConfirmBeen been=null;
    if(been!=null){
        been=new ConfirmBeen();
        been.setProductName(data.getPRODUCTNAME());
        been.setCategory(data.getCATEGORIESSUBNAME());
        String qantity=data.getQUANTITY();
        String price=data.getMRP();
        been.setQuantity(qantity);
        been.setPrice(price);
        been.setImgPath(data.getPoster());
        tvSubtotal.setText(price);
        tvItemSubTotal.setText("Subtotal ("+qantity+"item)");
        bin.add(been);
    }
    try{
        DBAdapter adapter=new DBAdapter(getApplicationContext());
        Cursor mCursor= adapter.getCartItems("");//pass the where condition
        if(mCursor!=null){
            ArrayList<AccessoriesBeen>  beens=new ArrayList<AccessoriesBeen>();
            mCursor.moveToFirst();
            while (mCursor.isAfterLast()==false){
            byte[] bytes=mCursor.getBlob(mCursor.getColumnIndex(TableBase.Table.CART_DTLS.PRODUCT));
            if(bytes!=null) {
                InputStream stream = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(stream);
                AccessoriesBeen carttemsb = (AccessoriesBeen) ois.readObject();
                if (carttemsb != null) {
                    been = new ConfirmBeen();
                    been = new ConfirmBeen();
                    been.setProductName(carttemsb.getPRODUCTNAME());
                    been.setCategory(carttemsb.getCATEGORIESSUBNAME());
                    String qantity = carttemsb.getQUANTITY();
                    String price = carttemsb.getMRP();
                    been.setQuantity(qantity);
                    String mrp = carttemsb.getMRP();
                    been.setPrice(mrp);
                    been.setImgPath(carttemsb.getPoster());
                    tvSubtotal.setText(mrp);
                    tvItemSubTotal.setText("Subtotal (" + qantity + "item)");
                    bin.add(been);
                }
            }
                mCursor.moveToNext();
            }
        }
    }catch (Exception e){
        e.printStackTrace();
    }
    product=new AdapterConfirmOrder(getApplicationContext(),ConfurmOrder.this,bin);
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ConfurmOrder.this);
    mRecyclerView.setLayoutManager(mLayoutManager);
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    mRecyclerView.setAdapter(product);
    }


}
