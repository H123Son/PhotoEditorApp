package com.li.photoeditor.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.li.photoeditor.R;

import java.io.ByteArrayOutputStream;

public class ChooseImageActivity extends AppCompatActivity implements View.OnClickListener {
    private final int GALLERY_REQUEST = 123;
    private final int CAMERA_REQUEST = 124;
    private final int EDITED_IMAGE_REQUEST = 125;
    private Intent intent;
    private LinearLayout lnGallery, lnCamera, lnEditedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_image);
        lnGallery = findViewById(R.id.ln_gallery);
        lnCamera = findViewById(R.id.ln_camera);
        lnEditedImage = findViewById(R.id.ln_image_editted);

        lnGallery.setOnClickListener(this);
        lnCamera.setOnClickListener(this);
        lnEditedImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ln_gallery:
                intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST);
                break;

            case R.id.ln_camera:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
                break;

            case R.id.ln_image_editted:
                intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, EDITED_IMAGE_REQUEST);
                break;
            default:
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            Intent intent = new Intent(ChooseImageActivity.this, EditImageActivity.class);
            intent.putExtra("Uri Image", imageUri.toString());
            startActivity(intent);


        }
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && data != null) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            String imageString = bitMapToString(image);
            Intent intent = new Intent(ChooseImageActivity.this, EditImageActivity.class);
            intent.putExtra("Bitmap Image", imageString);
            startActivity(intent);

        }
        if (requestCode == EDITED_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {

        }

    }

    public String bitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
