package com.sissimon.gitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class SplashScreen2 extends Activity {

    public boolean tutorial;
    FileOutputStream outputStream;
    FileInputStream inputStream;
    public static String filename="File";



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_2);

        tutorial=false;


        ImageButton Start_Button = (ImageButton) findViewById(R.id.button_start);

        // Capture button clicks
        Start_Button.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                try{
                    inputStream=openFileInput(filename);
                    inputStream.close();

                    Intent myIntent = new Intent(SplashScreen2.this, MainActivity.class);
                    startActivity(myIntent);
                }
                catch (Exception e){
                    e.printStackTrace();

                    Intent myIntent = new Intent(SplashScreen2.this, tut_1.class);
                    startActivity(myIntent);
                }



                // Start NewActivity.class

            }
        });


        ImageButton Tutorial_Button = (ImageButton) findViewById(R.id.button_tutorial);

        // Capture button clicks
        Tutorial_Button.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                tutorial = true;
                Intent myIntent = new Intent(SplashScreen2.this,
                        tut_1.class);
                startActivity(myIntent);
            }
        });



        ImageButton Hinweise_Button = (ImageButton) findViewById(R.id.button_hinweise);

        // Capture button clicks
        Hinweise_Button.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(SplashScreen2.this,
                        hilfe_main.class);
                startActivity(myIntent);
            }
        });


                ImageButton Info_Button = (ImageButton) findViewById(R.id.button_info);

                // Capture button clicks
                Info_Button.setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {

                        // Start NewActivity.class
                        Intent myIntent = new Intent(SplashScreen2.this,
                                Info.class);
                        startActivity(myIntent);
                    }
                });


            }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.menu_main, menu);
                return true;
            }
        }



