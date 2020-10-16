package com.li.photoeditorapp.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.li.photoeditorapp.R;
import com.li.photoeditorapp.base.BaseActivity;
import com.li.photoeditorapp.databinding.ActivitySettingBinding;
import com.li.photoeditorapp.ui.choose_image.ChooseImageActivity;

import java.util.Set;

public class SettingActivity extends BaseActivity<ActivitySettingBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(dataBinding.tbSetting);
        onLogoToolBarClick();
    }
    private void onLogoToolBarClick(){
        View view = dataBinding.tbSetting.getChildAt(1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,ChooseImageActivity.class);
                startActivity(intent);
            }
        });
    }
}
