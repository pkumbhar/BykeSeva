package com.vecta.nuclearwheels;

import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidsupport.AndroidSupport;
import com.db_adapter.DBAdapter;
import com.db_adapter.DBBackUpAsyncTask;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.table_base.TableBase;

public class Launch_act extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private  NavigationView navigationView;
    private int BACKPRESS_COUNT=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Controller controller =new Controller();
        controller.attachBaseContext(getApplicationContext());
      //  getFCMTokan();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        AsHome();
    }
    private void getFCMTokan(){
        /*FCMSTokanService service=new FCMSTokanService();
        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(true);
        service.onTokenRefresh();*/
        try{
            String ss=  FirebaseInstanceId.getInstance().getToken();
            Log.i("KEY--",""+ss);
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }
    }
    private void AsHome(){
        Mainact fragment = new Mainact();
        Bundle args = new Bundle();
        args.putInt(Mainact.ARG_PLANET_NUMBER, 0);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        // update selected item and title, then close the drawer
//        navigationView.setCheckedItem(0);
        setTitle("HOME");
//        drawer.closeDrawer(navigationView);
    }
    @Override
    public void onBackPressed() {
        BACKPRESS_COUNT++;
        if(BACKPRESS_COUNT==1){
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        }else if(BACKPRESS_COUNT>=2){
            //super.onBackPressed();
            ShowBackAlert();
        }
        new DBBackUpAsyncTask(getApplicationContext()).execute("");
    }
    private void ShowBackAlert(){

        AlertDialog.Builder networkAlert = new AlertDialog.Builder(Launch_act.this);
        networkAlert.setTitle("Nuclear Wheels.!");
        networkAlert.setMessage("Press yes to exit");
        networkAlert.setCancelable(false);
        networkAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
                dialog.dismiss();
            }
        });
        networkAlert.setNegativeButton("No",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert11 = networkAlert.create();
        alert11.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            Mainact fragment = new Mainact();
            Bundle args = new Bundle();
            args.putInt(Mainact.ARG_PLANET_NUMBER, 0);
            fragment.setArguments(args);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            // update selected item and title, then close the drawer
            navigationView.setCheckedItem(0);
            setTitle("HOME");
            drawer.closeDrawer(navigationView);

        } else if (id == R.id.nav_gallery) {
            MyProfile fragment = new MyProfile();
            Bundle args = new Bundle();
            args.putInt(Mainact.ARG_PLANET_NUMBER, 1);
            fragment.setArguments(args);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
             // update selected item and title, then close the drawer
             //  mDrawerList.setItemChecked(position, true);
            //    setTitle(mPlanetTitles[position]);
           //  mDrawerLayout.closeDrawer(mDrawerList);

        } else if (id == R.id.nav_slideshow) {
            History fragment = new History();
            Bundle args = new Bundle();
            args.putInt(Mainact.ARG_PLANET_NUMBER, 2);
            fragment.setArguments(args);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            // update selected item and title, then close the drawer
          //  mDrawerList.setItemChecked(position, true);
            setTitle("HISTORY SERVICES");
          //  mDrawerLayout.closeDrawer(mDrawerList);
        } else if (id == R.id.nav_manage) {
            UserLogin fragment = new UserLogin();
            Bundle args = new Bundle();
            args.putInt(Mainact.ARG_PLANET_NUMBER, 3);
            fragment.setArguments(args);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            // update selected item and title, then close the drawer
           // mDrawerList.setItemChecked(position, true);
            setTitle("SETTING");
          //  mDrawerLayout.closeDrawer(mDrawerList);
        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.vecta.nuclearwheels");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
