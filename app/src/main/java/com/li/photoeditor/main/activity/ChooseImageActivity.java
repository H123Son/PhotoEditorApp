package com.li.photoeditor.main.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.li.photoeditor.R;

import java.io.File;

public class ChooseImageActivity extends AppCompatActivity implements View.OnClickListener {
    private final int GALLERY_REQUEST = 123;
    private final int CAMERA_REQUEST = 124;
    private LinearLayout lnGallery, lnCamera, lnEditedImage;
    private NavigationView navChoice;
    private DrawerLayout drawerLayout;
    File file;
    Uri fileUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_image);
        //Cap quyen tao file,lay duong dan file luu anh tu camera
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        lnGallery = findViewById(R.id.ln_gallery);
        lnCamera = findViewById(R.id.ln_camera);
        lnEditedImage = findViewById(R.id.ln_image_editted);
        navChoice = findViewById(R.id.nv_choose_image);
        drawerLayout = findViewById(R.id.lo_drawer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_gallery);

        lnGallery.setOnClickListener(this);
        lnCamera.setOnClickListener(this);
        lnEditedImage.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ln_gallery:
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST);
                break;

            case R.id.ln_camera:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = new File(this.getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
                fileUri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, CAMERA_REQUEST);
                break;

            case R.id.ln_image_editted:
                intent = new Intent(ChooseImageActivity.this, ImageEditedActivity.class);
                startActivity(intent);
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
            intent.putExtra("Image Data", imageUri.toString());
            startActivity(intent);

        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Intent intent = new Intent(ChooseImageActivity.this, EditImageActivity.class);
            intent.putExtra("Image Data", fileUri.toString());
            startActivity(intent);

        }
    }

    @Override
    public void onBackPressed() {
        onDestroy();
        super.onBackPressed();
    }

}