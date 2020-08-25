package com.li.photoeditor.main.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.li.photoeditor.R;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.UUID;

public class EditImageActivity extends AppCompatActivity {
    private ImageView imgEdittingImage;
    private FrameLayout flContent;
    private BottomNavigationView navEditToolList;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);
        imgEdittingImage = findViewById(R.id.img_editting_image);
        flContent = findViewById(R.id.fl_content_edit_tool);
        navEditToolList = findViewById(R.id.nav_edittool_list);
        getImage();
        navEditToolList.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_crop:
                        startCrop();

                        break;
                    case R.id.nav_filter:
                        break;
                    case R.id.nav_change:
                        break;
                }
                return false;
            }

        });
    }

    public void getImage() {
        Intent intent = getIntent();
        String imageUriString = intent.getStringExtra("Uri Image");
        String imageBitmapString = intent.getStringExtra("Bitmap Image");
        if (imageUriString != null) {
            imageUri = Uri.parse(imageUriString);
            imgEdittingImage.setImageURI(imageUri);
        } else if (imageBitmapString != null) {
            Bitmap bitmap = StringToBitMap(imageBitmapString);
            imgEdittingImage.setImageBitmap(bitmap);
        }


    }

    public void startCrop() {
        String desfile = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();
        UCrop uCrop = UCrop.of(imageUri, Uri.fromFile(new File(getCacheDir(), desfile)));
        uCrop.start(EditImageActivity.this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            Uri uri = UCrop.getOutput(data);
            imgEdittingImage.setImageURI(uri);

        }
    }

}
