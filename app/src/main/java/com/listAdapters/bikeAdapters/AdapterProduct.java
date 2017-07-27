package com.listAdapters.bikeAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.db_adapter.DBAdapter;
import com.listAdapters.AccessoriesBeen.AccessoriesBeen;
import com.requestUrl.ServerUrl;
import com.squareup.picasso.Picasso;
import com.vecta.nuclearwheels.Accessories;
import com.vecta.nuclearwheels.ProductDetails;
import com.vecta.nuclearwheels.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 01-February-1-2017.
 */

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ProductView> {
    public Context mContext;
    public Activity mActivity;
    public ArrayList<AccessoriesBeen> listProduct;
    private int a=0;
    private int b=0;
    public AdapterProduct(Context mContext, Activity mActivity,ArrayList<AccessoriesBeen> listProduct) {
        this.mContext=mContext;
        this.mActivity=mActivity;
        this.listProduct=listProduct;
    }
    public class ProductView extends RecyclerView.ViewHolder{
        public LinearLayout mainLayout;
        public TextView tvProductName;
        public TextView tvPrice;
        public ImageView imMain;
        public Button btn_addTocart;
        public Button btn_Details;

        public ProductView(View itemView) {
            super(itemView);
            mainLayout=(LinearLayout)itemView.findViewById(R.id.linMainId);
            tvProductName=(TextView)itemView.findViewById(R.id.tv_acProductTitleId);
            tvPrice=(TextView)itemView.findViewById(R.id.tv_ac_price);
            imMain=(ImageView)itemView.findViewById(R.id.inside_imageview);
            btn_addTocart=(Button)itemView.findViewById(R.id.btn_addTocartId);
            btn_Details=(Button)itemView.findViewById(R.id.btn_details_id);
        }
    }
    @Override
    public void onBindViewHolder(final ProductView holder, int position) {
        final AccessoriesBeen product=listProduct.get(position);
        holder.tvProductName.setText(product.getPRODUCTNAME());
        holder.tvPrice.setText("Rs. "+product.getMRP()+"/-");
        String[] ph=product.getPoster().split(";");
        Picasso.with(mContext).load(ServerUrl.imageUral+ph[0].trim()).into(holder.imMain);
        holder.btn_addTocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String heading=holder.btn_addTocart.getText().toString();
                DBAdapter adapter=new DBAdapter(mContext);
                try{
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream out=new ObjectOutputStream(bos);
                    out.writeObject(product);
                    out.close();
                    byte[] bytes=bos.toByteArray();
                    if(heading.equalsIgnoreCase("Remove from cart")){
                        holder.btn_addTocart.setText("Add To Cart");
                        holder.btn_addTocart.setTextColor(Color.parseColor("#FFFFFFFF"));
                        if(adapter!=null){
                            //long c=adapter.insertIntoCartDetails(bytes);
                            long c=adapter.checkCart(bytes);
                            if(c>0){
                                Accessories.tv_cart.setText(""+adapter.getCartItems());
                            }
                        }
                    }else if(heading.equalsIgnoreCase("Add To Cart")){
                        holder.btn_addTocart.setText("Remove from cart");
                        holder.btn_addTocart.setTextColor(Color.parseColor("#FF05FF22"));
                        if(adapter!=null){
                            long c=adapter.insertIntoCartDetails(bytes);
                            if(c>0){
                                Accessories.tv_cart.setText(""+adapter.getCartItems());
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        holder.btn_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mActivity,ProductDetails.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("PRODUCT",(Parcelable) product);
                mContext.startActivity(intent);
            }
        });
        }

    @Override
    public ProductView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_accessories,parent,false);
        return new ProductView(view);
    }
    @Override
    public int getItemCount() {
        if(listProduct!=null){
            return listProduct.size();
        }
        return 0;
    }
    public void addProductNotify(AccessoriesBeen been){
        listProduct.add(getItemCount(),been);
        this.notifyItemChanged(getItemCount());
    }
}
