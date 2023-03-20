package com.example.android.e_meds.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.android.e_meds.R;

public class SplashSreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sreen);


        new Handler().postDelayed(() -> startActivity(new Intent(SplashSreen.this, MainActivity.class)), 3000);
    }
}