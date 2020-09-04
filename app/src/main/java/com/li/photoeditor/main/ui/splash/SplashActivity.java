package com.li.photoeditor.main.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.li.photoeditor.R;
import com.li.photoeditor.main.ui.choose_image_activity.ChooseImageActivity;
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
        CountDownTimer s = new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(SplashActivity.this, ChooseImageActivity.class);
                startActivity(intent);


            }
        };
        s.start();
    }


    }

