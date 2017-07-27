package com.vecta.nuclearwheels;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class InsuranceBAJAJ extends AppCompatActivity {
private WebView  webView;
    private String url="https://www.bajajallianz.com/Corp/motor-insurance/two-wheeler-insurance.jsp?gclid=CIO-3Lvk_84CFcEQaAodQZYOLw";
    @Override
    //https://general.bajajallianz.com/MotorInsurance/onlineportal/motorNew/index.jsp?src=CBM_0977&utm_source=GoogleBrand&gclid=CKCu6rLk_84CFVYfaAodlOQHbg
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_bajaj);
        webView=(WebView)findViewById(R.id.wb_insurance_id);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}
