package com.ahmednafe3.photoweather;

import android.app.Application;
import android.content.Context;

public class PhotoWeatherApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        PhotoWeatherApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return PhotoWeatherApp.context;
    }

}
