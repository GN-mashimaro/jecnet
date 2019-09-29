package com.goggles.androidtvtestapp;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;

public class BootstrapApplication extends Application {
    @Override public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
    }
}