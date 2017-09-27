package com.sissimon.gitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


/**
 * Created by Sissy on 21.10.2015.
 */
public class tut_2 extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tut_2);


        ImageButton Zur_Button_2 =(ImageButton) findViewById(R.id.tut_zur_2);

        Zur_Button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(tut_2.this,
                        tut_1.class);
                startActivity(myIntent);
                finish();
            }
        });

        ImageButton Vor_Button_1 = (ImageButton) findViewById(R.id.tut_weiter_2);

        // Capture button clicks
        Vor_Button_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent myIntent = new Intent(tut_2.this,
                        tut_3.class);
                startActivity(myIntent);
                finish();
            }
        });


    }}
