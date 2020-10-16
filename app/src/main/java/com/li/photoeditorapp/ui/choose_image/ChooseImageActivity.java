package com.li.photoeditorapp.ui.choose_image;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.li.photoeditorapp.R;
import com.li.photoeditorapp.base.BaseActivity;
import com.li.photoeditorapp.common.Constanst;
import com.li.photoeditorapp.databinding.ActivityChooseImageBinding;
import com.li.photoeditorapp.ui.SettingActivity;
import com.li.photoeditorapp.ui.choose_image.adapter.ChooseImagePagerAdapter;
import com.li.photoeditorapp.ui.edit_image.EditImageActivity;
import com.li.photoeditorapp.ultils.PermissionManager;

import java.io.File;

public class ChooseImageActivity extends BaseActivity<ActivityChooseImageBinding> {
    private ChooseImagePagerAdapter chooseImagePagerAdapter;
    private File imageFile;
    private Uri imageUri;
    private CheckPermissionFragment checkPermissionFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        setPermissionToCreateFile();
        setSupportActionBar(dataBinding.tbToolList);
        onLogoToolBarClick();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_choose_image_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tb_camera:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageFile = new File(this.getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
                imageUri = Uri.fromFile(imageFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, Constanst.CAMERA_REQUEST);
        }
        return true;
    }
    private void setPermissionToCreateFile() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }
    private void onLogoToolBarClick() {
        View view = dataBinding.tbToolList.getChildAt(1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseImageActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkPermission() {
        if (PermissionManager.getInstance(this).checkPermission()) {
            setUpTabLayout();
            dataBinding.vpImageList.setCurrentItem(0);
        }
        else{
            checkPermissionFragment = new CheckPermissionFragment();
            dataBinding.tbToolList.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().add(R.id.fr_vp_containner,checkPermissionFragment).commit();
            dataBinding.tabChooseImage.setSelectedTabIndicatorColor(getResources().getColor(R.color.tabBackGroundColor));
        }
    }

    private void setUpTabLayout() {
        chooseImagePagerAdapter = new ChooseImagePagerAdapter(getSupportFragmentManager(), ChooseImageActivity.this);
        dataBinding.vpImageList.setAdapter(chooseImagePagerAdapter);
        dataBinding.tabChooseImage.setupWithViewPager(dataBinding.vpImageList);
        dataBinding.tabChooseImage.getTabAt(0).setIcon(R.drawable.ic_arrow_up);
        dataBinding.tabChooseImage.getTabAt(1).setIcon(R.drawable.ic_rename);
    }

    private void onDenyPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cho phép Lumii truy cập ảnh và phương tiện trên thiết bị của bạn?");
        builder.setPositiveButton("HỦY BỎ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PermissionManager.getInstance(ChooseImageActivity.this).requestPermission();
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constanst.PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dataBinding.tbToolList.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().remove(checkPermissionFragment).commit();
                dataBinding.tabChooseImage.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
                setUpTabLayout();
                dataBinding.vpImageList.setCurrentItem(0);
            } else {
                onDenyPermission();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constanst.CAMERA_REQUEST && resultCode == RESULT_OK) {
            Intent intent = new Intent(ChooseImageActivity.this, EditImageActivity.class);
            intent.putExtra("Image Data", imageUri.toString());
            startActivity(intent);

        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();

    }
}
