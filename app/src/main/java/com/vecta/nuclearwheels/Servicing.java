package com.vecta.nuclearwheels;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.androidsupport.AndroidSupport;
import com.androidsupport.GPSTracker;
import com.background.DoServiceProviderDow;
import com.db_adapter.DBAdapter;
import com.listAdapters.bikeAdapters.SerChooserAdapter;
import com.listAdapters.bikeBeens.ServiceNames;
import com.preferences_storage.SavePrefe;
import com.table_base.TableBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Servicing extends FragmentActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{


    private Calendar calendardate,calendertime;
    private ImageView imdate,imtime;
    private int year, month, day;
    private Button  btn_book;
    private TextView tv_date;
    private static TextView _time;
    private EditText ed_ServiceInstruction;
    private LinearLayout mlayoutservices;
    private Set<String> GSAID=new HashSet<String>();
    private Set<String> mGarageServicesNameSet=new HashSet<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicing);
        imdate = (ImageView) findViewById(R.id.im_date_chose_id);
        imtime = (ImageView) findViewById(R.id.im_time_chose_id);
        tv_date=(TextView)findViewById(R.id.tv_date_id);
        _time=(TextView)findViewById(R.id.tv_time_id);
        ed_ServiceInstruction=(EditText)findViewById(R.id.ed_serviceinst_id);
        mlayoutservices=(LinearLayout)findViewById(R.id.lin_chk_servicesId);
        imdate.setOnClickListener(this);
        imtime.setOnClickListener(this);
        calendardate = Calendar.getInstance();
        calendertime = Calendar.getInstance();
        year = calendardate.get(Calendar.YEAR);
        month = calendardate.get(Calendar.MONTH);
        day = calendardate.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
        btn_book=(Button)findViewById(R.id.btn_servicing_showlist_id);
        serviceNames();
        btn_book.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AndroidSupport  support=new AndroidSupport();
            if(support.isConnected(Servicing.this)){
                if(ifDate()){
                    if (ifTime()){
                        try{
                            SavePrefe prefe=new SavePrefe();
                            prefe.saveExtraServices(GSAID,Servicing.this);
                            prefe.saveServicesByName(mGarageServicesNameSet,Servicing.this);
                            prefe.saveTime(_time.getText().toString(),Servicing.this);
                            prefe.saveDate(tv_date.getText().toString(),Servicing.this);
                            Intent intent=new Intent(Servicing.this,ConfirmBooking.class);
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Network Problem",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Selected Time Not Available",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Date Not Selected.",Toast.LENGTH_SHORT).show();
                }
            }else {
                support.connectToNetWork(Servicing.this);
            }
        }
    });
    }
    private boolean ifDate(){
        boolean b=false;
        String dt=tv_date.getText().toString();
        if(!dt.isEmpty()){
            b=true;
        }else {
            b=false;
        }
        return b;
    }
    private  boolean ifTime(){
        boolean b=false;
        if((!_time.getText().toString().equals("selected time"))&&(!_time.getText().equals("selected time not avalible"))){
            String tm=_time.getText().toString();
            b=true;
        }else {
            b=false;
        }
        return b;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.im_date_chose_id){
            showDialog(999);
        }else if(v.getId()==R.id.im_time_chose_id){
            DialogFragment dialogFragment = new DisplayTimeFragment();
            dialogFragment.show(getSupportFragmentManager(), "DatePicker");
        }

    }
    private void serviceNames(){
        DBAdapter dbAdapter=new DBAdapter(getApplicationContext());

        try{

            SavePrefe prefe=new SavePrefe();
            String garajID=prefe.getGarajeId(Servicing.this);
            Cursor mCursor=dbAdapter.getGarajeServices(TableBase.Table.GarageService.GARAGE_SERVICE,garajID);
            if(mCursor!=null){
                if(mCursor.getCount()>0){
                    mCursor.moveToFirst();
                    while (mCursor.isAfterLast()==false){
                        final String gsaId=mCursor.getString(mCursor.getColumnIndex(TableBase.Table.GarageService.GARAGE_SERVICE_ID));
                        String gsaServceName=mCursor.getString(mCursor.getColumnIndex(TableBase.Table.GarageService.GARAGE_SERVICE_NAME));
                        final CheckBox checkBox=new CheckBox(this);
                        checkBox.setText(gsaServceName);
                        checkBox.setTextColor(Color.parseColor("#FFFFFFFF"));
                        checkBox.setPadding(5,5,0,0);
                        checkBox.setTag(gsaId);
                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                String gsid=(String)checkBox.getTag();
                                if(checkBox.isChecked()){
                                   // Toast.makeText(getApplicationContext()," ADD-"+gsid,Toast.LENGTH_SHORT).show();
                                    GSAID.add(gsid);
                                    mGarageServicesNameSet.add(checkBox.getText().toString());
                                }else{
                                    //Toast.makeText(getApplicationContext(),"REMOVE "+gsid,Toast.LENGTH_SHORT).show();
                                    GSAID.remove(gsid);
                                    mGarageServicesNameSet.remove(checkBox.getText().toString());
                                }
                            }
                        });
                        checkBox.setButtonDrawable(R.drawable.checkbox_selecter);
                        checkBox.setTextSize(18);
                        mlayoutservices.addView(checkBox);
                        mCursor.moveToNext();
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void setSelectedTime(int hr, int min) {
        String am_pm = "";
        Calendar datetime = Calendar.getInstance();
        datetime.set(Calendar.HOUR_OF_DAY, hr);
        datetime.set(Calendar.MINUTE, min);
        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
            am_pm = "AM";
        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";
        String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ?"12":Integer.toString( datetime.get(Calendar.HOUR) );
        _time.setText(strHrsToShow+":"+datetime.get(Calendar.MINUTE)+":"+am_pm);
    }

    public static class  DisplayTimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            int AMPM=c.get(Calendar.AM_PM);
            return new TimePickerDialog(getActivity(), this, hour, minute,DateFormat.is24HourFormat(getActivity()));
        }
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            setSelectedTime(hourOfDay,minute);
        }
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        StringBuilder  time=new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year);
        Log.i("TIME",time.toString());
        tv_date.setText(time.toString());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
      /*  if(buttonView.getId()==R.id.cbox_lowmilage_id){

        }
*/
    }
}
