package com.example.myapplication.App;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    public static BaseApplication baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
    }

    public static Context getContexe() {
        return baseApplication;
    }
    public static BaseApplication getInstance() {
        return baseApplication;
    }
    /**
     * token 过期
     */
    public void tokenExpire() {

    }
}
