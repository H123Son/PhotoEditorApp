package com.li.photoeditor.main.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionManager {
    public static final int PERMISSION_REQUEST_CODE = 1;
    private static PermissionManager INSTANCE;
    private static Context context;

    public static PermissionManager getINSTANCE(Context subContext)
    {
        if(INSTANCE == null)
        {
            INSTANCE.context = subContext;
            INSTANCE = new PermissionManager();
        }
        return INSTANCE;
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    public void requestPermission() {
        ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

}
