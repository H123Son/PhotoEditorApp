package com.li.photoeditor.main.ui.choose_image_activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import com.li.photoeditor.R;
import com.li.photoeditor.databinding.ActivityChooseImageBinding;
import com.li.photoeditor.main.ui.image_edited_activity.ImageEditedActivity;
import com.li.photoeditor.main.base.BaseActivity;
import com.li.photoeditor.main.common.Constanst;
import com.li.photoeditor.main.ui.edit_image_activity.EditImageActivity;

import java.io.File;

public class ChooseImageActivity extends BaseActivity<ActivityChooseImageBinding> implements View.OnClickListener {
    private File file;
    private Uri fileUri;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_image;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPermissionToCreateFile();
        setUpActionBar();
        dataBinding.lnGallery.setOnClickListener(this);
        dataBinding.lnCamera.setOnClickListener(this);
        dataBinding.lnImageEditted.setOnClickListener(this);
    }

    private void setPermissionToCreateFile() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    private void setUpActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_gallery);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (dataBinding.loDrawer.isDrawerOpen(GravityCompat.START)) {
                dataBinding.loDrawer.closeDrawer(GravityCompat.START);
            } else {
                dataBinding.loDrawer.openDrawer(GravityCompat.START);
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
                startActivityForResult(intent, Constanst.GALLERY_REQUEST);
                break;

            case R.id.ln_camera:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = new File(this.getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
                fileUri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, Constanst.CAMERA_REQUEST);
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
        if (requestCode == Constanst.GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            Intent intent = new Intent(ChooseImageActivity.this, EditImageActivity.class);
            intent.putExtra("Image Data", imageUri.toString());
            startActivity(intent);

        } else if (requestCode == Constanst.CAMERA_REQUEST && resultCode == RESULT_OK) {
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