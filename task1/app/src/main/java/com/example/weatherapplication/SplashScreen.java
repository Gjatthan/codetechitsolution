package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ViewAnimator;

public class SplashScreen extends AppCompatActivity {
    ViewAnimator va;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getSupportActionBar().hide();

        va=findViewById(R.id.va);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                va.showNext();
            }
        },4000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this,ActivityMain.class));
                finish();
            }
        },6000);
    }
}