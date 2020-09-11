package com.li.photoeditor.main.ui.show_image;

import androidx.annotation.NonNull;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.li.photoeditor.R;
import com.li.photoeditor.databinding.ActivityShowImageBinding;
import com.li.photoeditor.main.base.BaseActivity;
import com.li.photoeditor.main.data.local.model.ImageEdited;
import com.li.photoeditor.main.utils.ImageUtils;

import java.io.IOException;

public class ShowImageActivity extends BaseActivity<ActivityShowImageBinding> implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Uri imageUri;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_image;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding.navToolList.setOnNavigationItemSelectedListener(this);
        getData();
    }

    private void getData() {
        Intent getdata = getIntent();
        Bundle bundle = getdata.getExtras();
        ImageEdited imageEdited = (ImageEdited) bundle.getSerializable("Image Edited");
        imageUri = Uri.parse(imageEdited.getImageData());
        Glide.with(dataBinding.imgShowImage).load(imageUri).into(dataBinding.imgShowImage);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("image/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(Intent.createChooser(sharingIntent, "Share Apps"));

                break;
            case R.id.nav_device_screen:
                try {
                    Bitmap bitmap = ImageUtils.getImageBitMap(imageUri,this);
                    WallpaperManager.getInstance(ShowImageActivity.this).setBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Snackbar.make(dataBinding.imgShowImage, "Cài hình nền thành công", Snackbar.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
