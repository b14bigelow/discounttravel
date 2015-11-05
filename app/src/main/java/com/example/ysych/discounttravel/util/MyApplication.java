package com.example.ysych.discounttravel.util;

import android.app.Application;

import com.example.ysych.discounttravel.data.HelperFactory;

/**
 * Created by ysych on 05.11.2015.
 */
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
