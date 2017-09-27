package com.sissimon.gitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Sissy on 20.07.2015.
 */
public class hilfe_gelenke extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hilfe_gelenk);


        ImageButton back_Button = (ImageButton) findViewById(R.id.zurueck);

        // Capture button clicks
        back_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                onBackPressed();
            }
        });

        ImageButton home_Button = (ImageButton) findViewById(R.id.home_2);

        // Capture button clicks
        home_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent myIntent = new Intent(hilfe_gelenke.this,
                        SplashScreen2.class);
                startActivity(myIntent);

            }
        });
    }
}