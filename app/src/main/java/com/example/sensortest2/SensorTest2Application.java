package com.example.sensortest2;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

public class SensorTest2Application extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
