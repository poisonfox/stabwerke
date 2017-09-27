package com.sissimon.gitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Sissy on 21.10.2015.
 */
public class tut_10 extends Activity {

    private boolean tutorial;
    FileOutputStream outputStream;
    public static String filename="File";
    private SplashScreen2 splashScreen2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tut_10);

        splashScreen2 = new SplashScreen2();
        tutorial = splashScreen2.tutorial;

        ImageButton Zur_Button =(ImageButton) findViewById(R.id.tut_zur_10);

        Zur_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(tut_10.this,
                        tut_9.class);
                startActivity(myIntent);
                finish();
            }
        });

        ImageButton Vor_Button = (ImageButton) findViewById(R.id.tut_weiter_10);

        // Capture button clicks
        Vor_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                try{
                    outputStream=openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write("öffnen".getBytes());
                    outputStream.close();
                } catch (Exception e){
                    e.printStackTrace();
                }

                if (tutorial==false){
                Intent myIntent = new Intent(tut_10.this,
                        MainActivity.class);
                startActivity(myIntent);

                finish();
                }
                else{
                    Toast.makeText(tut_10.this,"muhh",Toast.LENGTH_LONG).show();
                    onBackPressed();

                }

            }
        });

    }}