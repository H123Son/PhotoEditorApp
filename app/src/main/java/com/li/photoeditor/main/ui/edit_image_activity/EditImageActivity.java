package com.li.photoeditor.main.ui.edit_image_activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.li.photoeditor.R;
import com.li.photoeditor.databinding.ActivityEditImageBinding;
import com.li.photoeditor.main.base.BaseActivity;
import com.li.photoeditor.main.common.Constanst;
import com.li.photoeditor.main.ui.edit_image_activity.fragment.filter_fragment.callback.FilterListFragmentListener;
import com.li.photoeditor.main.ui.edit_image_activity.callback.OnSeekBarChangeListener;
import com.li.photoeditor.main.data.local.ImgEditedDatabase;
import com.li.photoeditor.main.model.ImageEdited;
import com.li.photoeditor.main.ui.edit_image_activity.fragment.edit_fragment.EditImageFragment;
import com.li.photoeditor.main.ui.edit_image_activity.fragment.filter_fragment.FilterFragment;
import com.li.photoeditor.main.utils.Parser;
import com.li.photoeditor.main.utils.PermissionManager;
import com.yalantis.ucrop.UCrop;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class EditImageActivity extends BaseActivity<ActivityEditImageBinding> implements BottomNavigationView.OnNavigationItemSelectedListener, FilterListFragmentListener, OnSeekBarChangeListener {
    private Uri imageUri;
    private Bitmap originalImage;
    private Bitmap filteredImage;
    private Bitmap finalImage;
    private String imagePath;
    private FilterFragment filterFragment;
    private EditImageFragment editImageFragment;

    private int brightnessFinal = 0;
    private float saturationFinal = 1.0f;
    private float contrastFinal = 1.0f;

    static {
        System.loadLibrary("NativeImageProcessor");
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(dataBinding.tbOption);
        getImage();

        dataBinding.navEdittoolList.setOnNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            filterFragment = new FilterFragment(this);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_content_edit_tool, filterFragment)
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        saveImageEdited();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tb_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tb_save_image:
                saveImage();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_crop:
                startCrop();
                break;
            case R.id.nav_filter:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_content_edit_tool, filterFragment)
                        .commit();
                break;
            case R.id.nav_change:
                editImageFragment = new EditImageFragment(this);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_content_edit_tool, editImageFragment)
                        .commit();
                break;
        }
        return false;
    }

    public void saveImage() {
        BitmapDrawable draw = (BitmapDrawable) dataBinding.imgEdittingImage.getDrawable();
        Bitmap bitmap = draw.getBitmap();

        FileOutputStream outStream = null;
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/PhotoEditor");
        dir.mkdirs();
        String fileName = String.format("%d.jpg", System.currentTimeMillis());
        File outFile = new File(dir, fileName);
        try {
            outStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            Toast.makeText(EditImageActivity.this, "Lưu Thành Công", Toast.LENGTH_LONG).show();
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(EditImageActivity.this, "Không thể lưu ảnh", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(EditImageActivity.this, "Không thể lưu ảnh", Toast.LENGTH_LONG).show();
        }


    }

    public void getImage() {
        Intent intent = getIntent();
        imagePath = intent.getStringExtra("Image Data");
        imageUri = Uri.parse(imagePath);
        try {
            originalImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        finalImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        Glide.with(this).load(originalImage).into(dataBinding.imgEdittingImage);

    }

    public void saveImageEdited() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Uri uri = Parser.getINSTANCE().BitMaptoUri(EditImageActivity.this, finalImage);
                imagePath = uri.toString();
                ImgEditedDatabase.getInstance(EditImageActivity.this).getImageDao().insertImage(new ImageEdited(imagePath));
            }
        });
        thread.start();

    }

    public Bitmap sendMyData() {

        return originalImage;
    }

    private void resetControls() {
        if (editImageFragment != null) {
            editImageFragment.resetControls();
        }
        brightnessFinal = 0;
        saturationFinal = 1.0f;
        contrastFinal = 1.0f;
    }

    @Override
    public void onFilterSelected(Filter filter) {
        resetControls();
        filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        dataBinding.imgEdittingImage.setImageBitmap(filter.processFilter(filteredImage));
        finalImage = filteredImage.copy(Bitmap.Config.ARGB_8888, true);
    }

    @Override
    public void onBrightnessChanged(int brightness) {
        brightnessFinal = brightness;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightness));
        dataBinding.imgEdittingImage.setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));

    }

    @Override
    public void onSaturationChanged(float saturation) {
        saturationFinal = saturation;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new SaturationSubfilter(saturation));
        dataBinding.imgEdittingImage.setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));

    }

    @Override
    public void onContrastChanged(float contrast) {
        contrastFinal = contrast;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new ContrastSubFilter(contrast));
        dataBinding.imgEdittingImage.setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));

    }

    @Override
    public void onEditStarted() {

    }

    @Override
    public void onEditCompleted() {
        final Bitmap bitmap = filteredImage.copy(Bitmap.Config.ARGB_8888, true);

        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightnessFinal));
        myFilter.addSubFilter(new ContrastSubFilter(contrastFinal));
        myFilter.addSubFilter(new SaturationSubfilter(saturationFinal));
        finalImage = myFilter.processFilter(bitmap);
    }

    public void startCrop() {
        if (PermissionManager.getINSTANCE(EditImageActivity.this).checkPermission()) {
            String desfile = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();
            Uri uri = Parser.getINSTANCE().BitMaptoUri(EditImageActivity.this, finalImage);
            UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), desfile)));
            uCrop.start(EditImageActivity.this);
        } else {
            PermissionManager.getINSTANCE(EditImageActivity.this).requestPermission();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            Uri uri = UCrop.getOutput(data);
            imagePath = uri.toString();
            imageUri = uri;
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            originalImage = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            dataBinding.imgEdittingImage.setImageBitmap(originalImage);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constanst.PERMISSION_REQUEST_CODE:
                String desfile = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();
                Uri uri = Parser.getINSTANCE().BitMaptoUri(EditImageActivity.this, finalImage);
                UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), desfile)));
                uCrop.start(EditImageActivity.this);
                break;
        }
    }
}
