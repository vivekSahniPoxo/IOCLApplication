
package com.example.ioclapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class SplashScreen extends AppCompatActivity {
    ImageView imageView;
    TextView t1,t2;
    boolean isExpired =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView = findViewById(R.id.imageView2);
        t1=findViewById(R.id.textView35);
        t2=findViewById(R.id.textView37);

        GregorianCalendar expDate = new GregorianCalendar(2022, 11, 1); // midnight
        GregorianCalendar now = new GregorianCalendar();//Note: Months are 0-based. January = 0, December = 11.

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String currentDateandTime = sdf.format(new Date());


         isExpired = now.after(expDate);
        if (isExpired) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dailogbox = LayoutInflater.from(this).inflate(R.layout.expirealertdialog, null);
            builder.setView(dailogbox);
            builder.setCancelable(false);
            builder.show();

        } else {
            imageView.setVisibility(View.VISIBLE);
            t1.setVisibility(View.VISIBLE);
            t2.setVisibility(View.VISIBLE);
            // Using handler with postDelayed called runnable run method
            new Handler().postDelayed(() -> {

                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();

            }, 3 * 1000);
        }


    }
}