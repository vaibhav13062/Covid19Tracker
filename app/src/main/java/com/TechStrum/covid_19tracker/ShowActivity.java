package com.TechStrum.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ArrayList<HashMap<String,String>>  finalList;
        Intent intent1 =getIntent();
        str=intent1.getStringExtra("int");
        finalList=(ArrayList<HashMap<String, String>>)intent1.getSerializableExtra("newlist");
        state_name=findViewById(R.id.state_name);
        confirm=findViewById(R.id.confirm);
        active=findViewById(R.id.active);
        recovered=findViewById(R.id.recovered);
        deaths=findViewById(R.id.deaths);
        delta_confirmed=findViewById(R.id.delta_confimed);
        delta_deaths=findViewById(R.id.delta_deaths);
        delta_recovered=findViewById(R.id.delta_recovered);
        state_name.setText(str);
        for(int i=0;i<finalList.size();i++){
            HashMap<String,String> hashMap=finalList.get(i);
            if(hashMap.get("state").equals(str)){
                confirm.setText(hashMap.get("confirmed"));
                active.setText(hashMap.get("active"));
                recovered.setText(hashMap.get("recovered"));
                deaths.setText(hashMap.get("deaths"));
                delta_confirmed.setText("+"+hashMap.get("deltaconfirmed"));
                delta_deaths.setText("+"+hashMap.get("deltadeaths"));
                delta_recovered.setText("+"+hashMap.get("deltarecovered"));
            }
        }

    }
}
