package com.li.photoeditorapp.common;

import android.app.Application;

public class ApplicationClass extends Application {

    //load libary to edit image
    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
