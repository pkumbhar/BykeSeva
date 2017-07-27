package com.listAdapters.bikeAdapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.listAdapters.bikeBeens.HistoryBeen;
import com.vecta.nuclearwheels.R;

import java.util.ArrayList;

/**
 * Created by Admin on 26-05-2016.
 */
public class ServiceHistoryAdapter extends RecyclerView.Adapter<ServiceHistoryAdapter.ServiceCenterHolder> {
    private  String TAG="serviceCenterListAdapter";
    private ArrayList<HistoryBeen> beensArrayList;
    private Context context;

    public ServiceHistoryAdapter(Context mContext, ArrayList<HistoryBeen> s) {
        this.beensArrayList=s;
        this.context=mContext;
    }
    public class ServiceCenterHolder extends RecyclerView.ViewHolder{
        public TextView tv_date;
        public TextView tv_vechicle_number;
        public  TextView tv_address;
        public  TextView tv_services;
        public LinearLayout layout ;
        public ServiceCenterHolder(View itemView) {
            super(itemView);
            layout=(LinearLayout)itemView.findViewById(R.id.lin_service_history_id) ;
            tv_date=(TextView)itemView.findViewById(R.id.tv_row_history_date_id);
            tv_vechicle_number=(TextView)itemView.findViewById(R.id.tv_row_history_vechilenumber_id);
            tv_address=(TextView)itemView.findViewById(R.id.tv_row_history_address_id);
            tv_services=(TextView)itemView.findViewById(R.id.tv_row_history_services_id);
        }
    }
    @Override
    public void onBindViewHolder(final ServiceCenterHolder holder, int position) {
        final HistoryBeen beens=beensArrayList.get(position);
        holder.layout.setBackgroundColor(Color.parseColor(beens.getColordode()));
        holder.tv_date.setText("DATE:"+beens.getDate());
        holder.tv_vechicle_number.setText("VEHICLE NO."+beens.getVehicle());
        holder.tv_address.setText(Html.fromHtml("<i><font color='BLUE'>"+beens.getGarageName()+"</font></i>")+","+beens.getAddressstreat()+","+beens.getCity()+" "+beens.getTime());
        holder.tv_services.setText(beens.getServiceing());

    }
    @Override
    public ServiceCenterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.print(" Executing------onCreateViewHolder------->>"+TAG);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history,parent,false);
        return new ServiceCenterHolder(itemView);
    }
    @Override
    public int getItemCount() {
        return beensArrayList.size();
    }
}
