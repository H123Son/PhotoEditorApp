package com.li.photoeditorapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.li.photoeditorapp.R;
import com.li.photoeditorapp.base.BaseActivity;
import com.li.photoeditorapp.common.Constanst;
import com.li.photoeditorapp.databinding.ActivitySplashBinding;
import com.li.photoeditorapp.ui.choose_image.ChooseImageActivity;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
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
