package com.li.photoeditor.main.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.li.photoeditor.R;
import com.li.photoeditor.main.util.Parser;
import com.li.photoeditor.main.util.PermissionManager;
import com.yalantis.ucrop.UCrop;
import java.io.File;
import java.util.UUID;

public class EditImageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ImageView imgEdittingImage;
    private FrameLayout flContent;
    private BottomNavigationView navEditToolList;
    private Uri imageUri;
    private Bitmap imageBitMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);
        imgEdittingImage = findViewById(R.id.img_editting_image);
        flContent = findViewById(R.id.fl_content_edit_tool);
        navEditToolList = findViewById(R.id.nav_edittool_list);

        getImage();

        navEditToolList.setOnNavigationItemSelectedListener(this);
    }

    public void getImage() {
        Intent intent = getIntent();
        String imageUriString = intent.getStringExtra("Uri Image");
        String imageBitmapString = intent.getStringExtra("Bitmap Image");
        if (imageUriString != null) {
            imageUri = Uri.parse(imageUriString);
            imgEdittingImage.setImageURI(imageUri);
        }
        else if (imageBitmapString != null) {
            imageBitMap = Parser.getINSTANCE().StringToBitMap(imageBitmapString);
            if (Build.VERSION.SDK_INT >= 23) {
                if (PermissionManager.getINSTANCE(this).checkPermission()) {
                    imageUri = Parser.getINSTANCE().BitMaptoUri(EditImageActivity.this, imageBitMap);
                    imgEdittingImage.setImageURI(imageUri);
                } else {
                    PermissionManager.getINSTANCE(this).requestPermission();
                }
            }
            else {
                imageUri = Parser.getINSTANCE().BitMaptoUri(EditImageActivity.this, imageBitMap);
                imgEdittingImage.setImageURI(imageUri);
            }

        }

    }
    public void startCrop() {
        String desfile = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();
        UCrop uCrop = UCrop.of(imageUri, Uri.fromFile(new File(getCacheDir(), desfile)));
        uCrop.start(EditImageActivity.this);
    }

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            Uri uri = UCrop.getOutput(data);
            imgEdittingImage.setImageURI(uri);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionManager.PERMISSION_REQUEST_CODE :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imageUri = Parser.getINSTANCE().BitMaptoUri(EditImageActivity.this, imageBitMap);
                    imgEdittingImage.setImageURI(imageUri);
                } else {
                    Toast.makeText(this,"Không thể tải ảnh lên khi chưa được cấp quyền",Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

}
