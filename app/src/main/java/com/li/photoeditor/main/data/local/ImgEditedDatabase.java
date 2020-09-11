package com.li.photoeditor.main.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.li.photoeditor.main.common.Constanst;
import com.li.photoeditor.main.data.local.model.ImageEdited;

@Database(entities = {ImageEdited.class}, version = 6,exportSchema = false)
public abstract class ImgEditedDatabase extends RoomDatabase {
    private static ImgEditedDatabase appDatabase;
    public static ImgEditedDatabase getInstance(Context context){
        if(appDatabase==null){
            appDatabase= Room.databaseBuilder(
                    context,
                    ImgEditedDatabase.class,
                    Constanst.DATABASE_NAME
            )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return appDatabase;
    }
    public abstract ImageDao getImageDao();
}
