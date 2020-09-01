package com.li.photoeditor.main.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.li.photoeditor.main.model.ImageEdited;

@Database(entities = {ImageEdited.class}, version = 4,exportSchema = false)
public abstract class ImgEditedDatabase extends RoomDatabase {
    private static ImgEditedDatabase appDatabase;
    public static ImgEditedDatabase getInstance(Context context){
        if(appDatabase==null){
            appDatabase= Room.databaseBuilder(
                    context,
                    ImgEditedDatabase.class,
                    "Image Database"
            )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return appDatabase;
    }
    public abstract ImageDAO getImageDao();
}
