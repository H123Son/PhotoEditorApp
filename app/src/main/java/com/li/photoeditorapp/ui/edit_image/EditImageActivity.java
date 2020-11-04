package com.li.photoeditorapp.ui.edit_image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.iambedant.text.OutlineTextView;
import com.li.photoeditorapp.R;
import com.li.photoeditorapp.base.BaseActivity;
import com.li.photoeditorapp.databinding.ActivityEditImageBinding;
import com.li.photoeditorapp.ui.choose_image.ChooseImageActivity;
import com.li.photoeditorapp.ui.edit_image.edit_image_parameters.EditImageFragment;
import com.li.photoeditorapp.ui.edit_image.edit_image_parameters.callback.OnSeekBarChangeListener;
import com.li.photoeditorapp.ui.edit_image.edit_text.EditTextFragment;
import com.li.photoeditorapp.ui.edit_image.edit_text.ListEditToolFragment;
import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnAddTextListener;
import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnItemTextColorClickListener;
import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnItemTextFontClickListener;
import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnItemTextStrokeColorClickListener;
import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnTextDoneListener;
import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnTextOpacityChangeListener;
import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnWritingTextListener;
import com.li.photoeditorapp.ui.edit_image.filter.FilterFragment;
import com.li.photoeditorapp.ui.edit_image.filter.callback.FilterListFragmentListener;
import com.li.photoeditorapp.ultils.ImageUtils;
import com.li.photoeditorapp.ultils.Parser;
import com.yalantis.ucrop.UCrop;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EditImageActivity extends BaseActivity<ActivityEditImageBinding> implements FilterListFragmentListener
        , BottomNavigationView.OnNavigationItemSelectedListener
        , OnSeekBarChangeListener, OnItemTextFontClickListener
        , View.OnTouchListener, OnTextDoneListener
        , OnWritingTextListener, OnItemTextStrokeColorClickListener
        , OnItemTextColorClickListener, OnAddTextListener, OnTextOpacityChangeListener {
    private Bitmap originalImage;
    private Bitmap filteredImage;
    private Bitmap finalImage;
    private Bitmap processImage;
    private Uri imageUri;
    private String imagePath;
    private FilterFragment filterFragment;
    private EditImageFragment editImageFragment;
    private ListEditToolFragment listEditToolFragment;
    private int brightnessFinal = 0;
    private float saturationFinal = 1.0f;
    private float contrastFinal = 1.0f;
    private int xDelta;
    private int yDelta;
    private String inputText;
    private List<View> textInImageList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textInImageList = new ArrayList<>();
        getImage();
        setSupportActionBar(dataBinding.tbEditImage);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        onLogoToolBarClick();
        dataBinding.navEditToolList.getMenu().findItem(R.id.nav_filter).setChecked(true);
        dataBinding.navEditToolList.setOnNavigationItemSelectedListener(this);
        setUpFragmnet(savedInstanceState);
    }

    private void setUpFragmnet(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            filterFragment = new FilterFragment(this);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fr_edit_tool, filterFragment)
                    .commit();
        }
    }

    private Bitmap getFinalImageBitmap() {
        Bitmap finalBitmap;
        if (textInImageList.size() == 0) {
            BitmapDrawable draw = (BitmapDrawable) dataBinding.imgImageEditing.getDrawable();
            finalBitmap = draw.getBitmap();
        } else {
            finalBitmap = ImageUtils.viewToImage(dataBinding.frImageEditting);
        }
        return finalBitmap;
    }


    private void onLogoToolBarClick() {
        View view = dataBinding.tbEditImage.getChildAt(0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditImageActivity.this, ChooseImageActivity.class);
                ImageUtils.saveImageEdited(EditImageActivity.this, getFinalImageBitmap());
                startActivity(intent);
            }
        });
    }

    public void getImage() {
        Intent intent = getIntent();
        String imagePath = intent.getStringExtra("Image Data");
        Uri imageUri = Uri.parse(imagePath);
        originalImage = ImageUtils.getImageBitMap(imageUri, EditImageActivity.this);
        processImage = ImageUtils.copyBitMap(originalImage);
        finalImage = ImageUtils.copyBitMap(originalImage);
        Glide.with(EditImageActivity.this).load(imageUri).into(dataBinding.imgImageEditing);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_image_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tb_save:
                ImageUtils.saveImage(this, getFinalImageBitmap());
        }
        return true;

    }

    @Override
    protected void onDestroy() {
        Toast.makeText(this,"lien",Toast.LENGTH_LONG).show();
        ImageUtils.saveImageEdited(this, getFinalImageBitmap());
        super.onDestroy();
    }

    private void resetControls() {
        if (editImageFragment != null) {
            editImageFragment.resetControls();
        }
        brightnessFinal = 0;
        saturationFinal = 1.0f;
        contrastFinal = 1.0f;
    }

    public Bitmap sendMyData() {

        return originalImage;
    }

    public void startCrop() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String desfile = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();
                Uri uri = Uri.parse(Parser.getInstance().bitMapToString(EditImageActivity.this, originalImage));
                UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), desfile)));
                uCrop.start(EditImageActivity.this);
            }
        });
        thread.start();

    }

    private void addTextToView() {
        View textInImage = getLayoutInflater().inflate(R.layout.view_text, null);
        textInImageList.add(textInImage);
        OutlineTextView txtText = textInImage.findViewById(R.id.tv_PhotoText);
        txtText.setGravity(Gravity.CENTER);
        txtText.setTextColor(Color.BLACK);
        ImageView imgClose = textInImage.findViewById(R.id.img_PhotoClose);
        ImageView imgScale = textInImage.findViewById(R.id.img_scale);
        FrameLayout frmBorder = textInImage.findViewById(R.id.frmBorder);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        textInImage.setLayoutParams(params);
        textInImage.setOnTouchListener(EditImageActivity.this);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textInImage.setVisibility(View.GONE);
            }
        });
        txtText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBorderTextInImage(imgClose, frmBorder, imgScale);
            }
        });
        dataBinding.frImageEditting.addView(textInImage);
        imgScale.setOnTouchListener(new View.OnTouchListener() {
            float centerX, centerY, startR, startScale, startX, startY;

            public boolean onTouch(View v, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    centerX = (textInImage.getLeft() + textInImage.getRight()) / 2f;
                    centerY = (textInImage.getTop() + textInImage.getBottom()) / 2f;
                    startX = e.getRawX() - textInImage.findViewById(R.id.img_scale).getX() + centerX;
                    startY = e.getRawY() - textInImage.findViewById(R.id.img_scale).getY() + centerY;
                    startR = (float) Math.hypot(e.getRawX() - startX, e.getRawY() - startY);
                    startScale = textInImage.getScaleY();
                } else if (e.getAction() == MotionEvent.ACTION_MOVE) {
                    float newR = (float) Math.hypot(e.getRawX() - startX, e.getRawY() - startY);
                    float newScale = newR / startR * startScale;
                    textInImage.setScaleX(newScale);
                    textInImage.setScaleY(newScale);
                } else if (e.getAction() == MotionEvent.ACTION_UP) {
                }
                return true;
            }
        });
    }

    private void showBorderTextInImage(ImageView imgClose, FrameLayout frmBorder, ImageView imgScale) {
        if (imgClose.getVisibility() == View.VISIBLE) {
            frmBorder.setBackgroundColor(Color.TRANSPARENT);
            imgClose.setVisibility(View.GONE);
            imgScale.setVisibility(View.GONE);
        } else {
            frmBorder.setBackground(getDrawable(R.drawable.rounded_border_tv));
            imgClose.setVisibility(View.VISIBLE);
            imgScale.setVisibility(View.VISIBLE);
        }
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fr_edit_tool, fragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_crop:
                dataBinding.navEditToolList.getMenu().findItem(R.id.nav_crop).setChecked(true);
                startCrop();
                break;
            case R.id.nav_filter:
                hideEditImageFragment();
                dataBinding.navEditToolList.getMenu().findItem(R.id.nav_filter).setChecked(true);
                replaceFragment(filterFragment);
                break;
            case R.id.nav_edit:
                dataBinding.navEditToolList.getMenu().findItem(R.id.nav_edit).setChecked(true);
                showEditImageFragment();
                editImageFragment = new EditImageFragment(this);
                replaceFragment(editImageFragment);
                break;
            case R.id.nav_text:
                hideEditImageFragment();
                dataBinding.navEditToolList.getMenu().findItem(R.id.nav_text).setChecked(true);
                listEditToolFragment = new ListEditToolFragment(this::onAddTextClick);
                replaceFragment(listEditToolFragment);

        }
        return false;
    }


    @Override
    public void onFilterSelected(Filter filter) {
        resetControls();
        filteredImage = ImageUtils.copyBitMap(processImage);
        Glide.with(this).load(filter.processFilter(filteredImage)).into(dataBinding.imgImageEditing);
        finalImage = ImageUtils.copyBitMap(filteredImage);
    }

    private void hideEditImageFragment() {
        if (dataBinding.frSbContainer.getVisibility() == View.VISIBLE) {
            dataBinding.frSbContainer.setVisibility(View.GONE);
        }
    }

    private void showEditImageFragment() {
        if (dataBinding.frSbContainer.getVisibility() == View.GONE) {
            dataBinding.frSbContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            Uri uri = UCrop.getOutput(data);
            imagePath = uri.toString();
            imageUri = uri;
            Bitmap bitmap = ImageUtils.getImageBitMap(uri, EditImageActivity.this);
            processImage = ImageUtils.copyBitMap(bitmap);
            Glide.with(dataBinding.imgImageEditing).load(processImage).into(dataBinding.imgImageEditing);
            dataBinding.navEditToolList.getMenu().findItem(R.id.nav_filter).setChecked(true);
            replaceFragment(filterFragment);
            hideEditImageFragment();

        }
    }


    @Override
    public void onBrightnessChanged(int brightness) {
        brightnessFinal = brightness;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightness));
        dataBinding.imgImageEditing.setImageBitmap(myFilter.processFilter(ImageUtils.copyBitMap(finalImage)));
//        Glide.with(this).load(myFilter.processFilter(ImageUtils.copyBitMap(processImage))).into(dataBinding.imgImageEditing);
    }

    @Override
    public void onSaturationChanged(float saturation) {
        saturationFinal = saturation;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new SaturationSubfilter(saturation));
        dataBinding.imgImageEditing.setImageBitmap(myFilter.processFilter(ImageUtils.copyBitMap(finalImage)));
    }

    @Override
    public void onContrastChanged(float contrast) {
        contrastFinal = contrast;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new ContrastSubFilter(contrast));
        dataBinding.imgImageEditing.setImageBitmap(myFilter.processFilter(ImageUtils.copyBitMap(finalImage)));

    }

    @Override
    public void onEditStarted() {

    }

    @Override
    public void onEditCompleted() {
        Bitmap bitmap = ImageUtils.copyBitMap(processImage);
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightnessFinal));
        myFilter.addSubFilter(new ContrastSubFilter(contrastFinal));
        myFilter.addSubFilter(new SaturationSubfilter(saturationFinal));
        finalImage = myFilter.processFilter(bitmap);
    }

    @Override
    public void onFontClick(Typeface typeface) {
        OutlineTextView outlineTextView = textInImageList.get(textInImageList.size() - 1).findViewById(R.id.tv_PhotoText);
        outlineTextView.setTypeface(typeface);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) v.getLayoutParams();
                xDelta = X - lParams.leftMargin;
                yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_MOVE:
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) v.getLayoutParams();
                layoutParams.leftMargin = X - xDelta;
                layoutParams.topMargin = Y - yDelta;
                layoutParams.rightMargin = -250;
                layoutParams.bottomMargin = -250;
                v.setLayoutParams(layoutParams);
                break;
        }
        dataBinding.frImageEditting.invalidate();

        return true;
    }

    @Override
    public void onTextDone() {
        dataBinding.tbEditImage.setVisibility(View.VISIBLE);
        dataBinding.navEditToolList.setVisibility(View.VISIBLE);
        replaceFragment(new ListEditToolFragment(this::onAddTextClick));
        setUpTextBorder();
    }

    private void setUpTextBorder() {
        ImageView imgClose = textInImageList.get(textInImageList.size() - 1).findViewById(R.id.img_PhotoClose);
        imgClose.setVisibility(View.GONE);
        ImageView imgScale = textInImageList.get(textInImageList.size() - 1).findViewById(R.id.img_scale);
        imgScale.setVisibility(View.GONE);
        FrameLayout frameLayout = textInImageList.get(textInImageList.size() - 1).findViewById(R.id.frmBorder);
        frameLayout.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onTextChange(String content) {
        inputText = content;
        OutlineTextView outlineTextView = textInImageList.get(textInImageList.size() - 1).findViewById(R.id.tv_PhotoText);
        outlineTextView.setText(inputText);

    }

    @Override
    public void onColorClick(int color) {
        OutlineTextView outlineTextView = textInImageList.get(textInImageList.size() - 1).findViewById(R.id.tv_PhotoText);
        outlineTextView.setTextColor(color);
    }

    @Override
    public void onStrokeColorCLick(int color) {
        OutlineTextView outlineTextView = textInImageList.get(textInImageList.size() - 1).findViewById(R.id.tv_PhotoText);
        outlineTextView.setStrokeColor(color);
        outlineTextView.setStrokeWidth(0.1f);
        outlineTextView.invalidate();
    }

    @Override
    public void onAddTextClick() {
        dataBinding.navEditToolList.setVisibility(View.GONE);
        dataBinding.tbEditImage.setVisibility(View.GONE);
        EditTextFragment editTextFragment = new EditTextFragment(this::onFontClick, this::onColorClick
                , this::onStrokeColorCLick, this::onTextDone
                , this::onTextChange, this::onSeekBarChange);
        replaceFragment(editTextFragment);
        addTextToView();
    }

    @Override
    public void onSeekBarChange(int process) {
        OutlineTextView outlineTextView = textInImageList.get(textInImageList.size() - 1).findViewById(R.id.tv_PhotoText);
        outlineTextView.setAlpha((float) process / 255);

    }
}
