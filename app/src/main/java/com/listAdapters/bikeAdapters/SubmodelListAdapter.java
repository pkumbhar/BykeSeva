package com.listAdapters.bikeAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.listAdapters.bikeBeens.HistoryBeen;
import com.listAdapters.bikeBeens.VehicleSubModel;
import com.preferences_storage.SavePrefe;
import com.vecta.nuclearwheels.R;
import com.vecta.nuclearwheels.ServiceType;
import com.vecta.nuclearwheels.Servicing;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Admin on 26-05-2016.
 */
public class SubmodelListAdapter extends RecyclerView.Adapter<SubmodelListAdapter.ServiceCenterHolder> {
    private  String TAG="serviceCenterListAdapter";
    private ArrayList<VehicleSubModel> beensArrayList;
    private Context context;
    private Activity  mActivity;

    public SubmodelListAdapter(Context mContext, ArrayList<VehicleSubModel> s,Activity mActivity) {
        this.beensArrayList=s;
        this.context=mContext;
        this.mActivity=mActivity;
    }
    public class ServiceCenterHolder extends RecyclerView.ViewHolder{
        public TextView tv_vechicle_Name,tv_vehicle_id;

        public LinearLayout layout ;
        public ServiceCenterHolder(View itemView) {
            super(itemView);
            layout=(LinearLayout)itemView.findViewById(R.id.lin_submodel_id) ;
            tv_vechicle_Name=(TextView)itemView.findViewById(R.id.tv_row_vehiclename_id);
            tv_vehicle_id=(TextView)itemView.findViewById(R.id.tv_row_vehiclename_id_code);
        }
    }
    @Override
    public void onBindViewHolder(final ServiceCenterHolder holder, int position) {
        final VehicleSubModel vmodel=beensArrayList.get(position);
        holder.layout.setBackgroundColor(Color.parseColor(vmodel.getColorcode()));
        holder.tv_vechicle_Name.setText(vmodel.getVehicleName());
        holder.tv_vehicle_id.setText(vmodel.getV_id());
        holder.tv_vechicle_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String vmodel=holder.tv_vehicle_id.getText().toString();
               Intent intent=new Intent(context,Servicing.class);
                Bundle bundle=new Bundle();
                bundle.putString("MODELID",vmodel);
                SavePrefe prefe=new SavePrefe();
                prefe.saveModel(vmodel,mActivity);
                intent.putExtra("MODELS",bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mActivity.startActivity(intent);
               // mActivity.finish();
               // Toast.makeText(context,"clicked",Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public ServiceCenterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.print(" Executing------onCreateViewHolder------->>"+TAG);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bike,parent,false);
        return new ServiceCenterHolder(itemView);
    }
    @Override
    public int getItemCount() {
        return beensArrayList.size();
    }
}
