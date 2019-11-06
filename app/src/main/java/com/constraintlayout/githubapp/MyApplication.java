package com.constraintlayout.githubapp;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static MyApplication myApplication;


    public static Context getContext() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }
}
