package com.sissimon.gitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Sissy on 21.10.2015.
 */
public class tut_3 extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tut_3);


        ImageButton Zur_Button =(ImageButton) findViewById(R.id.tut_zur_3);

        Zur_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(tut_3.this,
                        tut_2.class);
                startActivity(myIntent);
                finish();
            }
        });

        ImageButton Vor_Button = (ImageButton) findViewById(R.id.tut_weiter_3);

        // Capture button clicks
        Vor_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent myIntent = new Intent(tut_3.this,
                        tut_4.class);
                startActivity(myIntent);
                finish();
            }
        });


    }}