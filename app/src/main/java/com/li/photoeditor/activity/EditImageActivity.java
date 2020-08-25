package com.li.photoeditor.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

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

    public void getImage() {
        Intent intent = getIntent();

        String imageUriString = intent.getStringExtra("Uri Image");
        String imageBitmapString = intent.getStringExtra("Bitmap Image");
        if (imageUriString != null) {
            imgEdittingImage.setImageURI(Uri.parse(imageUriString));
        } else if (imageBitmapString != null) {
            Bitmap bitmap = StringToBitMap(imageBitmapString);
            imgEdittingImage.setImageBitmap(bitmap);
        }


    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
