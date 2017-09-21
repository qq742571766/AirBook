package com.air.airbook.base;

import android.Manifest;
import android.app.Application;

import com.yanzhenjie.permission.AndPermission;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndPermission.with(this)
                .requestCode(100)
                .permission(Manifest.permission.CAMERA,//相机
                        Manifest.permission.INTERNET,//网络
                        Manifest.permission.VIBRATE)//震动
                .start();
    }
}