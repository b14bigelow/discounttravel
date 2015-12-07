package com.androidapp.ysych.discounttravel.util;

import android.app.Application;

import com.androidapp.ysych.discounttravel.data.HelperFactory;
import com.parse.Parse;
import com.parse.ParseInstallation;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
        Parse.initialize(this, "DW5q8wI2sA43IirOPbl2xmgrjZcnJTtrgtwN02cW", "dw6r95m8lHKqgdrpTsznZfcBUl9KtWenTVIQBjVB");
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }

    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}
