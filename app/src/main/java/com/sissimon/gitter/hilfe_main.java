package com.sissimon.gitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Sissy on 20.07.2015.
 */
public class hilfe_main extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hilfe_main);


        ImageButton back_Button = (ImageButton) findViewById(R.id.back);

        // Capture button clicks
        back_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                onBackPressed();
            }
        });


        ImageButton Lager_Hinweis_Button = (ImageButton) findViewById(R.id.Hinweis_Lager);

        // Capture button clicks
        Lager_Hinweis_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent myIntent = new Intent(hilfe_main.this,
                        hilfe_lager.class);
                startActivity(myIntent);

            }
        });

        ImageButton Stab_Hinweis_Button = (ImageButton) findViewById(R.id.Hinweis_Stab);

        // Capture button clicks
        Stab_Hinweis_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent myIntent = new Intent(hilfe_main.this,
                        hilfe_stab.class);
                startActivity(myIntent);

            }
        });

        ImageButton Kraft_Hinweis_Button = (ImageButton) findViewById(R.id.Hinweis_Kraft);

        // Capture button clicks
        Kraft_Hinweis_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent myIntent = new Intent(hilfe_main.this,
                        hilfe_kraft.class);
                startActivity(myIntent);

            }
        });

        ImageButton Sonstiges_Hinweis_Button = (ImageButton) findViewById(R.id.Hinweis_Sonstiges);

        // Capture button clicks
        Sonstiges_Hinweis_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent myIntent = new Intent(hilfe_main.this,
                        hilfe_sonstiges.class);
                startActivity(myIntent);

            }
        });
    }
    }





