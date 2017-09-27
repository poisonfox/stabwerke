package com.sissimon.gitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Sissy on 20.10.2015.
 */
public class tut_1 extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tut_1);


        ImageButton Zur_Button_1 =(ImageButton) findViewById(R.id.tut_zur_1);

        Zur_Button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageButton Vor_Button_1 = (ImageButton) findViewById(R.id.tut_weiter_1);

        // Capture button clicks
        Vor_Button_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent myIntent = new Intent(tut_1.this,
                        tut_2.class);
                startActivity(myIntent);

                finish();
            }
        });


}}