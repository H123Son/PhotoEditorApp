package com.li.photoeditorapp.data.local.model;

import android.net.Uri;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;

import java.io.Serializable;

@Entity
public class ImageEdited implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int imageId;
    @ColumnInfo(name = "image path")
    private String imageData;

    public ImageEdited(String imageData) {
        this.imageData = imageData;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
    @BindingAdapter("profileImage")
    public static void loadImage(ImageView view, String imageUrl) {

        Glide.with(view.getContext())
                .load(Uri.parse(imageUrl))
                .into(view);
    }
}

