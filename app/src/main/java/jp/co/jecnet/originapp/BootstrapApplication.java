package jp.co.jecnet.originapp;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;

public class BootstrapApplication extends Application {
    @Override public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
    }
}