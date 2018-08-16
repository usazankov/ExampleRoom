package com.example.dell.exampleormlite;

import android.app.Application;

/**
 * Created by dell on 15.08.2018.
 */

public class MyApplication extends Application {
    private static MyApplication app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
    public static MyApplication getApp(){
        return app;
    }
}
