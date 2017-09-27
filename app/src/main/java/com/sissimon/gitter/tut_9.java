package com.sissimon.gitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Sissy on 20.10.2015.
 */
public class tut_9 extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tut_9);


        ImageButton Zur_Button =(ImageButton) findViewById(R.id.tut_zur_9);

        Zur_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(tut_9.this,
                        tut_8.class);
                startActivity(myIntent);
                finish();
            }
        });

        ImageButton Vor_Button = (ImageButton) findViewById(R.id.tut_weiter_9);

        // Capture button clicks
        Vor_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent myIntent = new Intent(tut_9.this,
                        tut_10.class);
                startActivity(myIntent);
                finish();
            }
        });


    }}