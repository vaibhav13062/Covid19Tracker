package com.TechStrum.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowActivity extends AppCompatActivity {
    TextView confirm;
    TextView active;
    TextView recovered;
    TextView deaths;
    String str;
    TextView state_name;
    TextView delta_confirmed;
    TextView delta_recovered;
    TextView delta_deaths;
    Button backButton;
    TextView noUpdate;
    AdView stateResultAdd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ArrayList<HashMap<String,String>>  finalList;
        Intent intent1 =getIntent();
        str=intent1.getStringExtra("int");
        finalList=(ArrayList<HashMap<String, String>>)intent1.getSerializableExtra("newlist");
        backButton = findViewById(R.id.backbuttonStateresult);
        noUpdate =findViewById(R.id.noRecent_update);
        state_name=findViewById(R.id.state_name);
        confirm=findViewById(R.id.confirm);
        active=findViewById(R.id.active);
        recovered=findViewById(R.id.recovered);
        deaths=findViewById(R.id.deaths);
        delta_confirmed=findViewById(R.id.delta_confimed);
        delta_deaths=findViewById(R.id.delta_deaths);
        delta_recovered=findViewById(R.id.delta_recovered);
        stateResultAdd=findViewById(R.id.adView);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        stateResultAdd.loadAd(adRequest);

        state_name.setText(str);
        for(int i=0;i<finalList.size();i++){
            HashMap<String,String> hashMap=finalList.get(i);
            if(hashMap.get("state").equals(str)){
                confirm.setText(hashMap.get("confirmed"));
                active.setText(hashMap.get("active"));
                recovered.setText(hashMap.get("recovered"));
                deaths.setText(hashMap.get("deaths"));
                delta_confirmed.setText("+" + hashMap.get("deltaconfirmed"));
                delta_deaths.setText("+" + hashMap.get("deltadeaths"));
                delta_recovered.setText("+" + hashMap.get("deltarecovered"));

                if (hashMap.get("deltaconfirmed").equals("0") && hashMap.get("deltadeaths").equals("0") && hashMap.get("deltarecovered").equals("0")){
                    noUpdate.setVisibility(View.VISIBLE);
                }else {
                    noUpdate.setVisibility(View.INVISIBLE);
                }
            }
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
