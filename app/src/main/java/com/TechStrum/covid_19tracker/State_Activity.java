package com.TechStrum.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class State_Activity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> all_states;
    Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_);
        backButton = findViewById(R.id.backButtonState);
        final ArrayList<HashMap<String,String>> new_list;
        all_states = new ArrayList<String>();
        Intent intent =getIntent();
        all_states=intent.getStringArrayListExtra("allStates");
        new_list =(ArrayList<HashMap<String, String>>)intent.getSerializableExtra("statelist");
        listView =findViewById(R.id.listView);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,all_states);
       listView.setAdapter(adapter);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent intent1 =new Intent(State_Activity.this,ShowActivity.class);
               intent1.putExtra("int",all_states.get(i));
               intent1.putExtra("newlist",new_list);
               startActivity(intent1);
           }

       });



       backButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onBackPressed();
           }
       });
    }
}
