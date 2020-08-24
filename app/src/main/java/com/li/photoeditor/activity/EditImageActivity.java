package com.li.photoeditor.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.li.photoeditor.R;

public class EditImageActivity extends AppCompatActivity {
    private ImageView imgEdittingImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);
        imgEdittingImage = findViewById(R.id.img_editting_image);
        getImage();
    }
    public void getImage()
    {
        Intent intent = getIntent();
        String imageString = intent.getStringExtra("Uri Image");

        imgEdittingImage.setImageURI(Uri.parse(imageString));

    }
}
