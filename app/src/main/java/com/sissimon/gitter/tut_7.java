package com.sissimon.gitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Sissy on 21.10.2015.
 */
public class tut_7 extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tut_7);


        ImageButton Zur_Button =(ImageButton) findViewById(R.id.tut_zur_7);

        Zur_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(tut_7.this,
                        tut_6.class);
                startActivity(myIntent);
                finish();
            }
        });

        ImageButton Vor_Button = (ImageButton) findViewById(R.id.tut_weiter_7);

        // Capture button clicks
        Vor_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent myIntent = new Intent(tut_7.this,
                        tut_8.class);
                startActivity(myIntent);
                finish();
            }
        });


    }}
