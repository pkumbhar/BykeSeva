package com.vecta.nuclearwheels;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidsupport.AndroidSupport;
import com.background.DoModelDownload;
import com.background.DoServiceProviderDow;
import com.listAdapters.bikeAdapters.ServiceHistoryAdapter;
import com.listAdapters.bikeAdapters.SubmodelListAdapter;
import com.listAdapters.bikeBeens.VehicleModelBeens;
import com.listAdapters.bikeBeens.VehicleSubModel;
import com.preferences_storage.SavePrefe;

import java.util.ArrayList;

public class SubModel extends Activity {
    public  static ArrayList<VehicleModelBeens>  vModelList=new ArrayList<VehicleModelBeens>();
    public static int RESPONSE_SUCESS=1;
    public static int RESPONSE_UNSUCESS=0;
    private SubmodelListAdapter  submodelListAdapter;
    private RecyclerView  recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_model);
        recyclerView=(RecyclerView)findViewById(R.id.list_submodel);
        downloadAllsubModel();

    }
    /*
    Download submodel
     */
    private void downloadAllsubModel(){
        AndroidSupport support=new AndroidSupport();
        try{
            if(support.isConnected(SubModel.this)){
                SavePrefe prefe=new SavePrefe();
                String v_id=prefe.getBikeId(SubModel.this);
                new DoModelDownload(SubModel.this,v_id,mHandler).execute("");
            }else {
                support.connectToNetWork(SubModel.this);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*
    Handler
     */
    public Handler  mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(getApplication(),"DataNot Found ",Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    loadDataToView();
                    break;
            }
        }
    };
    private void loadDataToView(){
        submodelListAdapter=new SubmodelListAdapter(SubModel.this,list(),SubModel.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SubModel.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(submodelListAdapter);
    }

    /*
    set ListAdapter
     */
    public ArrayList<VehicleSubModel>  list(){
        ArrayList<VehicleSubModel> bikeList=new ArrayList<VehicleSubModel>();
        for(VehicleModelBeens bin:vModelList){
            VehicleSubModel model=new VehicleSubModel();
            model.setV_id(bin.getId());
            model.setVehicleName(bin.getName());
            model.setColorcode(bin.getColorcode());
            bikeList.add(model);
        }
        return bikeList;
    }
}
