package com.sissimon.gitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Sissy on 21.10.2015.
 */
public class tut_5 extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tut_5);


        ImageButton Zur_Button =(ImageButton) findViewById(R.id.tut_zur_5);

        Zur_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(tut_5.this,
                        tut_4.class);
                startActivity(myIntent);
                finish();
            }
        });

        ImageButton Vor_Button = (ImageButton) findViewById(R.id.tut_weiter_5);

        // Capture button clicks
        Vor_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent myIntent = new Intent(tut_5.this,
                        tut_6.class);
                startActivity(myIntent);
                finish();
            }
        });


    }}
