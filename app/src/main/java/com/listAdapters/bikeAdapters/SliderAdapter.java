package com.listAdapters.bikeAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.listAdapters.bikeBeens.SliderBeen;
import com.vecta.nuclearwheels.MyProfile;
import com.vecta.nuclearwheels.R;
import com.vecta.nuclearwheels.UserLogin;

import java.util.ArrayList;

/**
 * Created by Admin on 31-05-2016.
 */
public class SliderAdapter extends ArrayAdapter<SliderBeen> {
private Context context;
private ArrayList<SliderBeen> sliderBeens;
private LayoutInflater mInflater;


public SliderAdapter(Context context, int resources, ArrayList<SliderBeen> objects) {
        super(context, resources, objects);
        this.context=context;
        this.sliderBeens=objects;
        }
public class ViewHilder{
    public LinearLayout layout;
    public TextView tv_home;
    public TextView tv_serviceHistory;
    public TextView tv_profile;
    public TextView tv_login;
}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHilder  holder = null;
        int type = getItemViewType(position);
        SliderBeen been=getItem(position);
        if(convertView==null){
            holder = new ViewHilder();
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.slider_row, null);
            holder.layout=(LinearLayout)convertView.findViewById(R.id.slid_layout);
            holder.tv_home = (TextView)convertView.findViewById(R.id.tv_sl_home_id);
            holder.tv_serviceHistory=(TextView)convertView.findViewById(R.id.tv_sl_servicehistory);
            holder.tv_profile=(TextView)convertView.findViewById(R.id.tv_sl_profile_id);
            holder.tv_login=(TextView)convertView.findViewById(R.id.tv_sl_login_id);
            convertView.setTag(holder);
        }
        ViewHilder hilder=(ViewHilder)convertView.getTag();
        holder.tv_home.setText(been.getHome());
        holder.tv_serviceHistory.setText(been.getHistory());
        holder.tv_profile.setText(been.getUserprofile());
        holder.tv_login.setText(been.getLogin());
        //home
        holder.tv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.tv_serviceHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        //
        holder.tv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MyProfile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });
        holder.tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent=new Intent(context,UserLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
        return convertView;

    }
}
