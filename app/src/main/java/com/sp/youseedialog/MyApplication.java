package com.sp.youseedialog;

import android.app.Application;

import com.orhanobut.logger.Logger;

/**
 * Created by songyuan on 2016/9/5.
 */
public class MyApplication extends Application{
    private final static String LOG_TAG = "";


    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init().methodCount(1).hideThreadInfo();
    }
}
