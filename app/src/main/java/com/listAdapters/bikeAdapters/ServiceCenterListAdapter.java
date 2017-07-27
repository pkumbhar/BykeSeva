package com.listAdapters.bikeAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.listAdapters.bikeBeens.JserviceCenterBeen;
import com.vecta.nuclearwheels.R;
import com.vecta.nuclearwheels.ServiceCenterDetails;

import java.security.PublicKey;
import java.util.ArrayList;

/**
 * Created by Admin on 26-05-2016.
 */
public class ServiceCenterListAdapter  extends RecyclerView.Adapter<ServiceCenterListAdapter.ServiceCenterHolder> {
    private  String TAG="serviceCenterListAdapter";
    private ArrayList<JserviceCenterBeen> beensArrayList;
    private Context context;
    private Activity mActivity;

    public ServiceCenterListAdapter(Context mContext, ArrayList<JserviceCenterBeen> s, Activity mActivity) {
        this.beensArrayList=s;
        this.context=mContext;
        this.mActivity=mActivity;
    }
    public class ServiceCenterHolder extends RecyclerView.ViewHolder{
        public TextView tv_serviceCenterName;
        public TextView tv_serviceCenterAddress;
        public Button btn_serviceCenterContact;
        public ImageView img_compose;
        public ServiceCenterHolder(View itemView) {
            super(itemView);
            tv_serviceCenterName=(TextView)itemView.findViewById(R.id.tv_cl_servicenterNamename_id);
            tv_serviceCenterAddress=(TextView)itemView.findViewById(R.id.tv_sl_servicecenterAddress_id);
            btn_serviceCenterContact=(Button) itemView.findViewById(R.id.btn_sl_call_id);
            img_compose=(ImageView)itemView.findViewById(R.id.img_compose_id);

        }
    }
    @Override
    public void onBindViewHolder(final ServiceCenterHolder holder, int position) {
        final JserviceCenterBeen beens=beensArrayList.get(position);
        holder.tv_serviceCenterName.setText(beens.getGarageName().toUpperCase());
        holder.tv_serviceCenterAddress.setText(beens.getSuburbanArea()+", "+beens.getCity()+", "+beens.getAddress1()+" "+beens.getAddress2());


        holder.btn_serviceCenterContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("tel:" +beens.getContactNumber()));
                context.startActivity(intent);

            }
        });
        holder.tv_serviceCenterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,ServiceCenterDetails.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("GARAGE_NAME",holder.tv_serviceCenterName.getText().toString().trim());
                intent.putExtra("GARAGE_ID",beens.getGarageID());
                context.startActivity(intent);
            }
        });
        holder.img_compose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,ServiceCenterDetails.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("GARAGE_NAME",holder.tv_serviceCenterName.getText().toString().trim());
                intent.putExtra("GARAGE_ID",beens.getGarageID());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public ServiceCenterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.print(" Executing------onCreateViewHolder------->>"+TAG);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.showroomlist_row,parent,false);
        return new ServiceCenterHolder(itemView);
    }
    @Override
    public int getItemCount() {
        return beensArrayList.size();
    }
}
