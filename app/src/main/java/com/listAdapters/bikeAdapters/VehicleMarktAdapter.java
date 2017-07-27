package com.listAdapters.bikeAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidsupport.AndroidSupport;
import com.listAdapters.bikeBeens.VehicleBuyBeen;
import com.squareup.picasso.Picasso;
import com.vecta.nuclearwheels.FilterationOfVehicle;
import com.vecta.nuclearwheels.R;
import com.vecta.nuclearwheels.VehicleInformationDetails;
import com.vecta.nuclearwheels.VehicleMarket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Admin on 13-October-13-2016.
 */
public class VehicleMarktAdapter extends RecyclerView.Adapter<VehicleMarktAdapter.VehicleMarktAdapterHolder> {
    private String TAG="";
    private Context mContext;
    private ArrayList<VehicleBuyBeen> beens;
    private Activity mActivity;
    private int INT_ALLK=1;
    private int INT_BPY=2;
    private int INT_BMK=3;
    private int INT_BPK=4;
    private int INT_BRANDK=5;
    private int INT_FEULK=6;
    private int INT_YEARK=7;
    private int INT_PRICEK=8;
    private int INT_BMY=9;


    public VehicleMarktAdapter(Context mContext, ArrayList<VehicleBuyBeen> beens,Activity mActivity) {
        this.mContext=mContext;
        this.beens=beens;
        this.mActivity=mActivity;
    }
    public class VehicleMarktAdapterHolder extends RecyclerView.ViewHolder{
       public ImageView img_Brand;
        public TextView tv_title;
        public TextView tv_information;
        public TextView tv_postedDate;
        public TextView tv_price;
        public ImageView img_msg_icon;
        public Button btn_ViewDtls;

        public VehicleMarktAdapterHolder(View itemView) {
            super(itemView);
            img_Brand=(ImageView)itemView.findViewById(R.id.img_BrandId);
            tv_title=(TextView)itemView.findViewById(R.id.tv_titleId);
            tv_information=(TextView)itemView.findViewById(R.id.tv_informationId);
            tv_postedDate=(TextView) itemView.findViewById(R.id.tv_postedDate);
            tv_price=(TextView)itemView.findViewById(R.id.tv_priceId);
            img_msg_icon=(ImageView)itemView.findViewById(R.id.img_msgId);
            btn_ViewDtls=(Button) itemView.findViewById(R.id.btn_ViewDtlsId);
        }
    }
    @Override
    public void onBindViewHolder(VehicleMarktAdapterHolder holder, final int position) {
        final VehicleBuyBeen data=beens.get(position);
        //new LoadImages(data.getLINK(),holder.img_Brand).execute("");
        try{
            String[] ph=data.getLINK().split(",");
            if (ph.length>0){
                Picasso.with(mContext).load("http://www.nuclearwheels.com/"+ph[0].trim()).into(holder.img_Brand);
            }else {
                /*
                todo if image not avalible
                 */
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.tv_title.setText(data.getBRAND_NAME()+" "+data.getMODEL_NAME());
        holder.tv_information.setText(data.getTOTAL_KM()+"kms/"+data.getFUEL_TYPE());
        holder.tv_postedDate.setText("   |   Posted:"+data.getPOSTDATE());
        holder.tv_price.setText("Rs. "+data.getYOUR_PRICE()+"/-");
        holder.btn_ViewDtls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String id=data.getId();
                Intent intent=new Intent(mActivity, VehicleInformationDetails.class);
                VehicleBuyBeen vehicleBuyBeen = new VehicleBuyBeen(
                        data.getId(),
                        data.getVEHICLE_ID(),
                        data.getBRAND_NAME(),
                        data.getMODEL_NAME(),
                        data.getVEHICLE_REG_NO(),
                        data.getYEAR_OF_MODEL(),
                        data.getTOTAL_KM(),
                        data.getFUEL_TYPE(),
                        data.getYOUR_PRICE(),
                        data.getINSURANCE_EXPIRE(),
                        data.getVEHICLE_CITY(),
                        data.getVEHICLE_LOCATION(),
                        data.getDESCRIPTION(),
                        data.getDATE_REG(),
                        data.getOWNER_ID(),
                        data.getPOSTDATE(),
                        data.getONUM(),
                        data.getLINK(),
                        data.getYOUR_PRICE(),
                        data.getO_NAME(),
                        data.getO_ID(),
                        data.getO_EMAIL(),
                        data.getO_NUMBER());
               intent.putExtra("v_data",vehicleBuyBeen);
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public VehicleMarktAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.print(" Executing------onCreateViewHolder------->>"+TAG);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_vehicle_market,parent,false);
        return new VehicleMarktAdapterHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return beens.size();
    }


    public void AddNotifiy(VehicleBuyBeen been) {
        beens.add(getItemCount(),been);
        Log.i("COUNT--"," "+getItemCount());

        this.notifyItemChanged(getItemCount(),beens);
    }
    public void  clearView(int i){
        try{
            beens.remove(i);
            this.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void clearData() {
        int size = this.beens.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.beens.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
}
