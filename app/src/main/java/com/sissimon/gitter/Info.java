package com.sissimon.gitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class Info extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        TextView email_Text = (TextView) findViewById(R.id.email_text);

        email_Text.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"stabwerke@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Stabwerke App");
                i.putExtra(Intent.EXTRA_TEXT, " ");
                try {
                    startActivity(Intent.createChooser(i, "E-Mail senden"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Info.this, "Es sind keine Apps zum Verschicken von Mails vorhanden.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView email_Text_imes = (TextView) findViewById(R.id.email_text_imes);

        email_Text_imes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"app@imes.uni-hannover.de"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Stabwerke App");
                i.putExtra(Intent.EXTRA_TEXT   , " ");
                try {
                    startActivity(Intent.createChooser(i, "E-Mail senden"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Info.this, "Es sind keine Apps zum Verschicken von Mails vorhanden.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        final Context context = getApplicationContext();

        ImageButton email_Button =(ImageButton) findViewById(R.id.email);
        email_Button.setOnClickListener(new OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"stabwerke@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Stabwerke App");
                i.putExtra(Intent.EXTRA_TEXT   , " ");
                try {
                    startActivity(Intent.createChooser(i, "E-Mail senden"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Info.this, "Es sind keine Apps zum Verschicken von Mails vorhanden.", Toast.LENGTH_SHORT).show();
                }
        }
        });

        ImageButton email_Button_imes =(ImageButton) findViewById(R.id.email_imes);
        email_Button_imes.setOnClickListener(new OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"app@imes.uni-hannover.de"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Stabwerke App");
                i.putExtra(Intent.EXTRA_TEXT   , " ");
                try {
                    startActivity(Intent.createChooser(i, "E-Mail senden"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Info.this, "Es sind keine Apps zum Verschicken von Mails vorhanden.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ImageButton back_Button = (ImageButton) findViewById(R.id.back);


        // Capture button clicks
        back_Button.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                onBackPressed();
            }
        });


    }
    public static void sendEmail(Context context, String[] recipientList,
                                 String title, String subject, String body) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipientList);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(emailIntent, title));
    }
    private void makeToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}