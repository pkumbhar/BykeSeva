package com.vecta.nuclearwheels;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.db_adapter.DBAdapter;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;
import com.listAdapters.AccessoriesBeen.AccessoriesBeen;
import com.requestUrl.ServerUrl;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class ProductDetails extends AppCompatActivity implements View.OnClickListener {
    private TextView tvProductTitle,tvMainPrice,tvQuantity,tvPayableAmount,tvProductType,tvType,tvBrand,tvWeight,tvDescription,tv_Offer,tv_ofrmrp,tv_cartCount;
    private Button btnAddation,btnSubstraction,btnAddToCart,btnBuy;
    private ImageView imgProduct;
    private LinearLayout linearLayout;
    private String mrp="0";
    private String paybleamount="0";
    private int countAdd=1;
    private int countSubstract=1;
    private AccessoriesBeen been;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        expandData();
        setItemCount();
    }

    private void setView(){
        setContentView(R.layout.activity_product_details);
        tvProductTitle=(TextView)findViewById(R.id.tv_acc_productTitle);
        tvQuantity=(TextView)findViewById(R.id.tv_acc_qty_id);
        tvPayableAmount=(TextView)findViewById(R.id.tv_acc_total_payable_id);
        tvDescription=(TextView)findViewById(R.id.tv_acc_description);
        tv_Offer=(TextView)findViewById(R.id.tv_acc_offer_id);
        tvMainPrice=(TextView)findViewById(R.id.tv_acc_main_priceID);
        tvType=(TextView)findViewById(R.id.tv_acc_type);
        tvWeight=(TextView)findViewById(R.id.tv_acc_weight);
        tvProductType=(TextView)findViewById(R.id.tv_acc_product_typeId);
        tvBrand=(TextView)findViewById(R.id.tv_acc_brand_id);
        tvBrand.setOnClickListener(this);
        btnAddation=(Button)findViewById(R.id.btn_acc_addationID);
        btnAddation.setOnClickListener(this);
        btnSubstraction=(Button)findViewById(R.id.btn_acc_substractID);
        btnSubstraction.setOnClickListener(this);
        btnAddToCart=(Button)findViewById(R.id.btn_acc_add_to_cart_id);
        btnAddToCart.setOnClickListener(this);
        btnBuy=(Button)findViewById(R.id.btn_acc_buy_now_cart_id);
        btnBuy.setOnClickListener(this);
        imgProduct=(ImageView)findViewById(R.id.act_acc_imgVIewId);
        linearLayout=(LinearLayout)findViewById(R.id.lin_acc_LinerlayoutId);
        tv_ofrmrp=(TextView)findViewById(R.id.tv_acc_offermrp_id);
        tv_cartCount=(TextView)findViewById(R.id.tv_cart_dtls__id);
    }
    private void expandData(){
        try{
            Intent intent=super.getIntent();
             been=intent.getParcelableExtra("PRODUCT");
            if(been!=null) {
                mrp= been.getMRP().toString();
                tvProductTitle.setText("" + been.getPRODUCTNAME());
                tvQuantity.setText("1");
                tvDescription.setText(been.getDESCRIPTION());
                tvMainPrice.setText(mrp);
                tv_ofrmrp.setText(mrp);
                tv_ofrmrp.setPaintFlags(tv_ofrmrp.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                tv_Offer.setText(been.getDISCOUNT() + "%off");
                tvType.setText(been.getType());
                tvWeight.setText("" + been.getWEIGHT());
                tvProductType.setText("" + been.getTetrTYPE());
                tvBrand.setText(been.getNAME());
                tvPayableAmount.setText(mrp);
                String[] parts = been.getPoster().split(";");
                if(parts.length>0){
                    for(int i=0;i<parts.length; i++){
                       final  ImageView view=new ImageView(this);
                        Picasso.with(getApplicationContext()).load(ServerUrl.imageUral+parts[i]).into(view);
                        Picasso.with(getApplicationContext()).load(ServerUrl.imageUral+parts[i]).into(imgProduct);
                        LinearLayout boxlayout=new LinearLayout(ProductDetails.this);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        layoutParams.gravity= Gravity.CENTER;
                        layoutParams.setMargins(2,2,2,2);

                        LinearLayout.LayoutParams layoutParamsbox = new LinearLayout.LayoutParams(250,ViewGroup.LayoutParams.MATCH_PARENT);
                        layoutParamsbox.gravity=Gravity.CENTER;
                        layoutParamsbox.setMargins(10,10,10,10);
                        boxlayout.setBackgroundResource(R.drawable.row_style_b);

                        boxlayout.setLayoutParams(layoutParamsbox);
                        view.setLayoutParams(layoutParams);
                        view.setId(i);
                        boxlayout.addView(view);
                        linearLayout.addView(boxlayout);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(),"w"+view.getId(),Toast.LENGTH_SHORT).show();
                                Bitmap bitmap= ((BitmapDrawable)view.getDrawable()).getBitmap();
                                imgProduct.setImageBitmap(bitmap);
                                startActivity(new Intent(ProductDetails.this,ZoomImageProduct.class));
                            }
                        });
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_acc_addationID){
            handelAddation();
        }else if(v.getId()==R.id.btn_acc_substractID){
        handelSubstraction();
        }else if(v.getId()==R.id.btn_acc_add_to_cart_id){
            addToCart();
        }else if(v.getId()==R.id.btn_acc_buy_now_cart_id){
            String a=tvQuantity.getText().toString();
            String b=tvPayableAmount.getText().toString();
           Intent intent=new Intent(ProductDetails.this,ConfurmOrder.class);
            been.setQUANTITY(a);
            been.setMRP(b);
           intent.putExtra("Order",(Parcelable) been);
            startActivity(intent);
        }
    }
    private void addToCart(){
        String heading=btnAddToCart.getText().toString();
        DBAdapter adapter=new DBAdapter(getApplicationContext());
        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out=new ObjectOutputStream(bos);
            out.writeObject(been);
            out.close();
            byte[] bytes=bos.toByteArray();
            if(heading.equalsIgnoreCase("Remove from cart")){
                btnAddToCart.setText("Add To Cart");
                btnAddToCart.setTextColor(Color.parseColor("#b8b8b8"));
                if(adapter!=null){
                    //long c=adapter.insertIntoCartDetails(bytes);
                    long c=adapter.checkCart(bytes);
                    if(c>0){
                        tv_cartCount.setText(""+adapter.getCartItems());
                    }
                }
            }else if(heading.equalsIgnoreCase("Add To Cart")){
                btnAddToCart.setText("Remove from cart");
                btnAddToCart.setTextColor(Color.parseColor("#FF05FF22"));
                if(adapter!=null){
                    long c=adapter.insertIntoCartDetails(bytes);
                    if(c>0){
                        tv_cartCount.setText(""+adapter.getCartItems());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private synchronized void handelAddation(){
        try{
            countAdd++;
            float amt=Float.parseFloat(mrp);
            Float payamt=countAdd*amt;
            String pay=String.valueOf(payamt);
            Log.i("AMOUNT-->",""+pay);
            tvQuantity.setText(countAdd+" ");
            tvPayableAmount.setText(pay);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setItemCount(){
        DBAdapter adapter=new DBAdapter(getApplicationContext());
        try{
            tv_cartCount.setText(""+adapter.getCartItems());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void handelSubstraction(){
        countAdd--;
        if(countAdd>0){
            float amt=Float.parseFloat(mrp);
            Float payamt=countAdd*amt;
            String pay=String.valueOf(payamt);
            Log.i("AMOUNT-->",""+pay);
            tvQuantity.setText(countAdd+" ");
            tvPayableAmount.setText(pay);
        }else if(countAdd<0){
            Toast.makeText(getApplicationContext(),"Quantity should be zero",Toast.LENGTH_SHORT).show();
            countAdd=1;
        }
    }
}
