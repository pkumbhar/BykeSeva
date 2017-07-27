package com.vecta.nuclearwheels;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;

import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.androidsupport.AndroidSupport;
import com.background.DoCatageriesDownload;
import com.db_adapter.DBAdapter;
import com.dbconnection.DBnuclear;
import com.google.android.gms.vision.text.Text;
import com.listAdapters.bikeAdapters.AdapterAccessories;
import com.listAdapters.bikeAdapters.ExpandableListData;
import com.table_base.TableBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Accessories extends AppCompatActivity implements ExpandableListView.OnChildClickListener {
    private DrawerLayout mDrawerLayout;
    private ExpandableListView mListView;
    private List<String> plist;
    private LinkedHashMap<String,List<String>> clist;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    public static  TextView tv_actitle_id,tv_cart,tv_fevrate;
    private ActionBarDrawerToggle toggle;
    private TextView tvCart;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessories);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_accessories_id);
        mListView=(ExpandableListView) findViewById(R.id.left_drawer);
        mListView.setGroupIndicator(null);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_acc);
        setSupportActionBar(toolbar);
        tv_actitle_id=(TextView)findViewById(R.id.tv_actitle_id);
        layout=(LinearLayout)findViewById(R.id.lin_linCartId);
        tv_cart=(TextView)findViewById(R.id.tv_cart_accessories_id);
        toggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close){
        };
        mDrawerLayout.addDrawerListener(toggle);
        //toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

//        getActionBar().setHomeButtonEnabled(true);

        startFragment("Default",1);
        checkcategories();
        setCart();
        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return false;
            }
        });
        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//Buffers Cleaner
                List<String> s=clist.get(plist.get(groupPosition));
                String child=s.get(childPosition);
                Log.i("CATEGORY"," "+child);
                startFragment(child,0);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            }
        });
        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                List<String> s=clist.get(plist.get(groupPosition));
                if(s.size()>0){
                    Log.i("SIZE not zero"," "+plist.get(groupPosition));
                }else{
                    String category=plist.get(groupPosition);
                    Log.i("CATEGORY--->"," "+category);
                    startFragment(category,1);
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                }
            }
        });
        mListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

    }
    private void checkcategories(){
        DBAdapter dbAdapter=new DBAdapter(getApplicationContext());
        try{

            Cursor mCursor= dbAdapter.getTableInfo(TableBase.Table.PRODUCT_CATEGORIES.PRODUCT_CATEGORIES);
            if(mCursor!=null){
             mCursor.getColumnCount();
                if(mCursor.getCount()>0){
                    loadList();

                }else{
                    AndroidSupport support=new AndroidSupport();
                    if(support.isConnected(Accessories.this)){
                        ProgressDialog dialog=new ProgressDialog(Accessories.this);
                        new DoCatageriesDownload(getApplicationContext(),Accessories.this,dialog,handler).execute("");
                    }else{
                        support.connectToNetWork(Accessories.this);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                loadList();
            }
        }
    };
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return false;
    }

    private void loadList(){
        ExpandableListData listData=new ExpandableListData();
        clist= listData.getData(getApplicationContext());
        plist=new ArrayList<String>(clist.keySet());
        AdapterAccessories accessories=new AdapterAccessories(getApplicationContext(),plist,clist);
        mListView.setAdapter(accessories);

    }
    private void startFragment(String title,int rq){
        Log.i("##--",title);
        StringBuilder builder=new StringBuilder(title);
        int a=builder.length();
        String limt="";
        try{
            limt=builder.substring(0,7);
        }catch (Exception e){
            e.printStackTrace();
        }
        tv_actitle_id.setText(limt+"...");
        FragmentAccessories accessories=new FragmentAccessories();
        Bundle bundle=new Bundle();
        bundle.putString(FragmentAccessories.ARG_PARAM1,title);
        bundle.putString(FragmentAccessories.ARG_PARAM2,String.valueOf(rq));
        accessories.setArguments(bundle);
        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_accessories_id,accessories).commit();
        setTitle(title);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle("");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.accessories_menu, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
    synchronized  private void setCart(){
        DBAdapter adapter=new DBAdapter(getApplicationContext());

        try{
            int a=adapter.getCartItems();
            String str=String.valueOf(a);
            tv_cart.setText(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Accessories.this,ConfurmOrder.class);
                startActivity(intent);

            }
        });
    }

}
