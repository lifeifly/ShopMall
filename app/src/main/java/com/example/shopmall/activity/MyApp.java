package com.example.shopmall.activity;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

public class MyApp extends Application {
private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        x.Ext.init(this);
        //数据库跟随项目一并初始化

    }

    public static Context getContext() {
        return context;
    }
}
