package com.li.photoeditor.main.utils;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.HandlerThread;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

public class ImageUtils {

    public static Bitmap getImageBitMap(Uri uri, Context context) {
        Bitmap bitmap = null;
        try {
            InputStream is = context.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;

    }
    public static Bitmap copyBitMap(Bitmap originBitMap)
    {
        final CountDownLatch latch = new CountDownLatch(1);
        final Bitmap[] value = new Bitmap[1];
        Thread uiThread = new HandlerThread("UIHandler"){
            @Override
            public void run(){
                value[0] = originBitMap.copy(Bitmap.Config.ARGB_8888, true);
                latch.countDown(); // Release await() in the test thread.
            }
        };
        uiThread.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return value[0];
    }


}
