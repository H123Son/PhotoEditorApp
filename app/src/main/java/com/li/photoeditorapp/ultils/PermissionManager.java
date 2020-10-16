package com.li.photoeditorapp.ultils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.li.photoeditorapp.common.Constanst;

public class PermissionManager {
    private static PermissionManager Instance;


    private static Context context;

    public static PermissionManager getInstance(Context subContext)
    {
        if(Instance == null)
        {
            Instance.context = subContext;
            Instance = new PermissionManager();
        }
        return Instance;
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
        ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constanst.PERMISSION_REQUEST_CODE);
    }

}
