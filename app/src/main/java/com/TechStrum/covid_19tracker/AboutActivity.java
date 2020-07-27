package com.TechStrum.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    public void instaPage(View view){
        Uri uri = Uri.parse("http://instagram.com/_u/vaibhav_1306");


        Intent i= new Intent(Intent.ACTION_VIEW,uri);

        i.setPackage("com.instagram.android");

        try {
            startActivity(i);
        } catch (ActivityNotFoundException e) {

            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/vaibhav_1306/")));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent about =getIntent();
        setContentView(R.layout.activity_about);
        TextView textView3=findViewById(R.id.textView3);
        textView3.setText("THIS APP WAS CREATED JUST FOR FUN AND LEARNING THIS APP USED AN API FROM A WEBSITE (www.api.covid19india.org) THIS APP IS CREATED BY :- VAIBHAV CHANDOLIA");
    }
}
