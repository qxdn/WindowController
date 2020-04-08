package com.sunbest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import interfaces.heweather.com.interfacesmodule.view.HeConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HeConfig.init("uname","key");
    }
}
