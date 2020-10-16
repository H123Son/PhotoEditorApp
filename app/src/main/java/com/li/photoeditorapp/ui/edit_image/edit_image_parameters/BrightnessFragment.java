package com.li.photoeditorapp.ui.edit_image.edit_image_parameters;


import android.view.View;
import android.widget.SeekBar;

import com.li.photoeditorapp.R;
import com.li.photoeditorapp.base.BaseFragment;
import com.li.photoeditorapp.databinding.FragmentBrightnessBinding;
import com.li.photoeditorapp.ui.edit_image.edit_image_parameters.callback.OnSeekBarChangeListener;

public class BrightnessFragment extends BaseFragment<FragmentBrightnessBinding> {

    private OnSeekBarChangeListener listener;

    public BrightnessFragment(OnSeekBarChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_brightness;
    }

    @Override
    protected void onViewReady(View view) {
        dataBinding.sbBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                listener.onBrightnessChanged(progress - 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                listener.onEditCompleted();
            }
        });

    }
    public void resetControls() {
        dataBinding.sbBrightness.setProgress(100);
    }
}