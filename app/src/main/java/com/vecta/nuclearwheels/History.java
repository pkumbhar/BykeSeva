package com.vecta.nuclearwheels;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidsupport.AndroidNetworkSupport;
import com.androidsupport.AndroidSupport;
import com.background.DoHistoryDownload;
import com.db_adapter.DBAdapter;
import com.listAdapters.bikeAdapters.ServiceHistoryAdapter;
import com.listAdapters.bikeBeens.HistoryBeen;
import com.table_base.TableBase;
import com.vecta.nuclearwheels.dummy.DummyContent.DummyItem;

import java.util.ArrayList;


public class History extends android.app.Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    public static ArrayList<HistoryBeen> historyBeens=new ArrayList<HistoryBeen>();
    private LinearLayout linHistory;


    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView  recyclerView;
    private ServiceHistoryAdapter  serviceHistoryListAdapter;
    public static  int HISTORY_SUCESS=200;
    public static  int HISTORY_UN_SUCESS=403;
    public static  int HISTORY_BADREQUEST_SUCESS=401;



    public History() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
   /* public static History newInstance(int columnCount) {
        History fragment = new History();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        AndroidNetworkSupport support=new AndroidSupport();
        if(support.isConnected(getActivity())){
            checkUserAndHistry();
        }else {
            support.connectToNetWork(getActivity());
        }
    }
/*
getDatabseDetailks
 */
    private void checkUserAndHistry(){
        try{
            DBAdapter  adapter=new DBAdapter(getActivity());
            Cursor mCursor=adapter.getTableInfo(TableBase.Table.USERTABLE.USER);
            if(mCursor!=null){
                mCursor.moveToFirst();
                if(mCursor.getCount()>0){
                    String userId="";
                    userId=mCursor.getString(mCursor.getColumnIndex(TableBase.Table.USERTABLE.SER_USER_ID));
                    if((userId!=null)&&(!userId.isEmpty())){
                        new DoHistoryDownload(getActivity(),mHandler,userId).execute("");
                    }else {
                        showSucessMsg(1);
                    }
                }else {
                    showSucessMsg(2);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.row_recycle_list);
        linHistory=(LinearLayout)view.findViewById(R.id.linHistory_id);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
    private void showSucessMsg(int type) {
        if(type==1){
            DBAdapter adapter=new DBAdapter(getActivity());
            try{
                adapter.deleteTable(TableBase.Table.USERTABLE.USER);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(type==2){
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.warning);
        builder.setTitle("Alert from Nucler Wheels.!");
        builder.setMessage("you are not authorized user of Nucler Wheels \n please continue using following options");
        builder.setCancelable(false);
        builder.setPositiveButton("LOGIN/REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getActivity(),LoginTwo.class));
            }
        }).setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //startActivity(new Intent(getActivity(),UserRegistration.class));
            }
        }).show();
    }

    public Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==HISTORY_SUCESS){
                linHistory.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                loadDataToView();
            }else if(msg.what==HISTORY_UN_SUCESS){
                Toast.makeText(getActivity(),"Service history not found",Toast.LENGTH_SHORT).show();
                linHistory.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }else if(msg.what==HISTORY_BADREQUEST_SUCESS){
                Toast.makeText(getActivity(),"code:401  ",Toast.LENGTH_SHORT).show();
                linHistory.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        }
    };
    /*
    private load History
     */
private void loadDataToView(){
    serviceHistoryListAdapter=new ServiceHistoryAdapter(getActivity(),historyBeens);
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(serviceHistoryListAdapter);
}
}
