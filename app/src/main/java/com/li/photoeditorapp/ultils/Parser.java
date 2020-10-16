package com.li.photoeditorapp.ultils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

public class Parser {
    private static Parser instance;

    public static Parser getInstance() {
        if (instance == null) {
            instance = new Parser();
        }
        return instance;
    }

    public String bitMapToString(Activity inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            path = MediaStore.Images.Media.insertImage(
                    inContext.getContentResolver(),
                    inImage,
                    "Title" + " - " + (Calendar.getInstance().getTime()),
                    null);
        }
        return path;
    }
    }




