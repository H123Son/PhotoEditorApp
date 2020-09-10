package com.li.photoeditor.main.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.li.photoeditor.R;
import com.li.photoeditor.main.common.Constanst;
import com.li.photoeditor.main.ui.choose_image.ChooseImageActivity;
import com.li.photoeditor.main.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_default;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        Handler handler = new Handler();
       handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent intent = new Intent(SplashActivity.this, ChooseImageActivity.class);
               startActivity(intent);
           }
       }, Constanst.DELAY_TIME);


    }


    }

