package com.li.photoeditorapp.ui;


import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.li.photoeditorapp.R;
import com.li.photoeditorapp.base.BaseActivity;
import com.li.photoeditorapp.databinding.ActivityShowImageBinding;
import com.li.photoeditorapp.ui.choose_image.ChooseImageActivity;
import com.li.photoeditorapp.ui.edit_image.EditImageActivity;
import com.li.photoeditorapp.ultils.ImageUtils;

import java.io.IOException;

public class ShowImageActivity extends BaseActivity<ActivityShowImageBinding> implements View.OnClickListener {
    private Uri imageUri;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(dataBinding.tbShowImage);
        onLogoToolBarClick();
        Intent intent = getIntent();
        String imageData = intent.getStringExtra("Image Data");
        imageUri = Uri.parse(imageData);
        dataBinding.imgMyImage.setImageURI(imageUri);
        dataBinding.tvSetAsBackground.setOnClickListener(this::onClick);
        dataBinding.tvShare.setOnClickListener(this::onClick);
    }

    private void onLogoToolBarClick() {
        View view = dataBinding.tbShowImage.getChildAt(1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowImageActivity.this, ChooseImageActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_set_as_background:
                try {
                    Bitmap bitmap = ImageUtils.getImageBitMap(imageUri, this);
                    WallpaperManager.getInstance(ShowImageActivity.this).setBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Snackbar.make(dataBinding.imgMyImage, "Cài hình nền thành công", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.tv_share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("image/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(Intent.createChooser(sharingIntent, "Share Apps"));
                break;


        }

    }
}
