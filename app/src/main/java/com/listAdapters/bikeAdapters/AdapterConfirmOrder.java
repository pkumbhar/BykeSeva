package com.listAdapters.bikeAdapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.listAdapters.AccessoriesBeen.ConfirmBeen;
import com.requestUrl.ServerUrl;
import com.squareup.picasso.Picasso;
import com.vecta.nuclearwheels.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 09-February-9-2017.
 */

public class AdapterConfirmOrder extends RecyclerView.Adapter<AdapterConfirmOrder.ConfirmOrderHolder> {

    private Context mContext;
    private Activity mActivity;
    private ArrayList<ConfirmBeen> mConfirmBeen;

    public AdapterConfirmOrder(Context mContext, Activity mActivity, ArrayList<ConfirmBeen> mConfirmBeen) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mConfirmBeen = mConfirmBeen;
    }

    public class ConfirmOrderHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView tvProlductName;
        private TextView tvQuantity;
        private TextView tvPrice;
        private TextView tvOrderId;
        private TextView tvCategory;

        public ConfirmOrderHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.row_Img_orderId);
            tvProlductName=(TextView)itemView.findViewById(R.id.row_tv_product_title_order);
            tvQuantity=(TextView)itemView.findViewById(R.id.row_tv_quantity_orderId);
            tvCategory=(TextView)itemView.findViewById(R.id.row_tv_productcategory_orderId);
            tvPrice=(TextView)itemView.findViewById(R.id.row_tv_price_orderId);
        }
    }

    @Override
    public void onBindViewHolder(ConfirmOrderHolder holder, int position) {
        ConfirmBeen been=mConfirmBeen.get(position);
        holder.tvProlductName.setText(been.getProductName());
        holder.tvCategory.setText(been.getCategory());
        holder.tvQuantity.setText("Qty "+been.getQuantity());
        holder.tvPrice.setText("MRP - "+been.getPrice());
        try{
            String[] ph=been.getImgPath().split(";");
            Picasso.with(mContext).load(ServerUrl.imageUral+ph[0].trim()).into(holder.imageView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return mConfirmBeen.size();
    }
    @Override
    public ConfirmOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.row_order_details,parent,false);
        return new ConfirmOrderHolder(view);
    }
}
