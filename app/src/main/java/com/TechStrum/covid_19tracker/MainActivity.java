package com.TechStrum.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    ArrayList<HashMap<String, String>> state_List;
    HashMap<String,String > total_list;
    Intent intent3;
    TextView total_case;
    TextView confirmed_case;
    TextView active_case;
    TextView recovered_case;
    TextView deaths_case;
    TextView delta_confirmed;
    TextView delta_recovered;
    TextView delta_deaths;
    TextView updateDate;
    ArrayList<String> allStates;
    public void stateWise(View view){
        Intent intent =new Intent(getApplicationContext(),State_Activity.class);
        intent.putStringArrayListExtra("allStates",allStates);
        intent.putExtra("statelist",state_List);
        startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allStates=new ArrayList<>();
        state_List = new ArrayList<>();
        total_list =new HashMap<>();
        total_case= findViewById(R.id.total_case);
        confirmed_case=findViewById(R.id.confirmed_case);
        active_case=findViewById(R.id.active_case);
        recovered_case=findViewById(R.id.recovered_case);
        deaths_case=findViewById(R.id.deaths_case);
        updateDate=findViewById(R.id.updateDate);
        delta_confirmed=findViewById(R.id.delta_confimed);
        delta_deaths=findViewById(R.id.delta_deaths);
        delta_recovered=findViewById(R.id.delta_recovered);
        new DownloadTask().execute();

    }
    private class DownloadTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"DATA IS LOADING",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh =new HttpHandler();
            String url = "https://api.covid19india.org/data.json";
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray stateList = jsonObj.getJSONArray("statewise");

                    // looping through All Contacts
                    for (int i = 0;i< stateList.length() ; i++) {

                        JSONObject c = stateList.getJSONObject(i);
                        String state = c.getString("state");
                        String active = c.getString("active");
                        String confirmed = c.getString("confirmed");
                        String deaths = c.getString("deaths");
                        String recovered = c.getString("recovered");
                        String updateDate =c.getString("lastupdatedtime");
                        String deltaconfirmed = c.getString("deltaconfirmed");
                        String deltadeaths = c.getString("deltadeaths");
                        String deltarecovered = c.getString("deltarecovered");

                        // tmp hash map for single contact
                        HashMap<String, String> state_map = new HashMap<>();
                        if(i==0) {
                            // adding each child node to HashMap key => value
                            total_list.put("state", state);
                            total_list.put("confirmed", confirmed);
                            total_list.put("active", active);
                            total_list.put("deaths", deaths);
                            total_list.put("recovered", recovered);
                            total_list.put("lastupdatedtime",updateDate);
                            total_list.put("deltaconfirmed",deltaconfirmed);
                            total_list.put("deltadeaths", deltadeaths);
                            total_list.put("deltarecovered", deltarecovered);
                            Log.i("Total", confirmed);

                        }else {

                            state_map.put("state", state);
                            state_map.put("confirmed", confirmed);
                            state_map.put("active", active);
                            state_map.put("deaths", deaths);
                            state_map.put("recovered", recovered);
                            state_map.put("lastupdatedtime",updateDate);
                            state_map.put("deltaconfirmed",deltaconfirmed);
                            state_map.put("deltadeaths", deltadeaths);
                            state_map.put("deltarecovered", deltarecovered);
                            allStates.add(state);
                            state_List.add(state_map);
                        }
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            total_case.setText(total_list.get("confirmed"));
            confirmed_case.setText(total_list.get("confirmed"));
            active_case.setText(total_list.get("active"));
            deaths_case.setText(total_list.get("deaths"));
            recovered_case.setText(total_list.get("recovered"));
            updateDate.setText(total_list.get("lastupdatedtime"));
            delta_confirmed.setText("+"+total_list.get("deltaconfirmed"));
            delta_recovered.setText("+"+total_list.get("deltarecovered"));
            delta_deaths.setText(("+"+total_list.get("deltadeaths")));
        }
    }
        //MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.aboutUs){
           Intent about=new Intent(getApplicationContext(),AboutActivity.class);
           startActivity(about);
        }
        else if(item.getItemId()==R.id.refresh){
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
        else if(item.getItemId()==R.id.state_wise){
            Intent intent =new Intent(getApplicationContext(),State_Activity.class);
            intent.putStringArrayListExtra("allStates",allStates);
            intent.putExtra("statelist",state_List);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
