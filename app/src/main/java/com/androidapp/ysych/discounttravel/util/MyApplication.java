package com.androidapp.ysych.discounttravel.util;

import android.app.Application;

import com.androidapp.ysych.discounttravel.data.HelperFactory;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());

    }
    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}
