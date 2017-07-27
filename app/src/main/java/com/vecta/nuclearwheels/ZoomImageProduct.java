package com.vecta.nuclearwheels;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.imageZoom.Zoom;

public class ZoomImageProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Zoom(this));
    }
}
