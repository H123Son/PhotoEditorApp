package com.li.photoeditor.main.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.li.photoeditor.main.data.local.model.ImageEdited;

import java.util.List;

@Dao
public interface ImageDao {
    @Query("SELECT * From ImageEdited")
    List<ImageEdited> getAll();

    @Query("SELECT * From ImageEdited Where id = :imageID ")
    ImageEdited getImage(int imageID);

    @Query("DELETE FROM ImageEdited")
    void deleteAllUser();

    @Insert
    void insertImage(ImageEdited... imageEditeds);

    @Delete
    void deleteImage(ImageEdited... imageEditeds);


}
