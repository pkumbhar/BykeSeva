package com.vecta.nuclearwheels;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Launcher_act extends AppCompatActivity {

private ImageView logo;
    private Button btn_servicing,btn_bye,btn_cell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_selection);

        TimeTrack time=new TimeTrack();
       new Thread(time).start();
    }

    private class TimeTrack implements Runnable{
        @Override
        public void run() {
            Thread thread=Thread.currentThread();
            try{
                for (int i=0;i<3;i++){
                  Thread.sleep(1000);
                    if(i==0){
                        startActivity(new Intent(Launcher_act.this,Launch_act.class));
                        finish();
                        break;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        private  void start(){
            Thread thread=new Thread(this,"ok");
            thread.start();
        }

    }
}
