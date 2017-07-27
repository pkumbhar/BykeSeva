package com.listAdapters.bikeAdapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.listAdapters.bikeBeens.ServiceNames;
import com.vecta.nuclearwheels.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 27-December-27-2016.
 */
public class SerChooserAdapter  extends ArrayAdapter<String> {

    private Context mContext;
    private Activity mActivity;
    private ArrayList<ServiceNames> names;


    public SerChooserAdapter(Context context, int resource, List<String> objects, Context mContext, Activity mActivity, ArrayList<ServiceNames> names) {
        super(context, resource, objects);
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.names = names;
    }

    @Override
public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
        }

public View getCustomView(int position, View convertView, ViewGroup parent) {
        ServiceNames s=names.get(position);
        LayoutInflater inflater=mActivity.getLayoutInflater();
        View row=inflater.inflate(R.layout.row_choice_box, parent, false);
        TextView label=(TextView)row.findViewById(R.id.tv_setchooserId);
        CheckBox box=(CheckBox)row.findViewById(R.id.chk_ser_id) ;
        label.setText(s.getServceName());
        return row;
        }
}