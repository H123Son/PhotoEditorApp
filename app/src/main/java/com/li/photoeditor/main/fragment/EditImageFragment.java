package com.li.photoeditor.main.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.li.photoeditor.R;
import com.li.photoeditor.main.callback.OnSeekBarChangeListener;

public class EditImageFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    private OnSeekBarChangeListener listener;
    private SeekBar seekBarBrightness, seekBarContrast, seekBarSaturation;


    public EditImageFragment() {
    }

    public void setListener(OnSeekBarChangeListener listener) {
        this.listener = listener;
    }

    public EditImageFragment(OnSeekBarChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_image, container, false);
        seekBarBrightness = view.findViewById(R.id.sb_brightness);
        seekBarContrast = view.findViewById(R.id.sb_contrast);
        seekBarSaturation = view.findViewById(R.id.sb_saturation);
        //set default for seekbar
        seekBarBrightness.setMax(200);
        seekBarBrightness.setProgress(100);
        seekBarContrast.setMax(20);
        seekBarContrast.setProgress(0);
        seekBarSaturation.setMax(30);
        seekBarSaturation.setProgress(10);

        seekBarBrightness.setOnSeekBarChangeListener(this);
        seekBarContrast.setOnSeekBarChangeListener(this);
        seekBarSaturation.setOnSeekBarChangeListener(this);
        return view;
    }

    public void resetControls() {
        seekBarBrightness.setProgress(100);
        seekBarContrast.setProgress(0);
        seekBarSaturation.setProgress(10);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (listener != null) {
            if (seekBar.getId() == R.id.sb_brightness) {
                listener.onBrightnessChanged(progress - 100);
            }
            if (seekBar.getId() == R.id.sb_contrast) {
                progress += 10;
                float floatVal = .10f * progress;
                listener.onContrastChanged(floatVal);
            }
            if (seekBar.getId() == R.id.sb_saturation) {
                float floatVal = .10f * progress;
                listener.onSaturationChanged(floatVal);
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (listener != null) {
            listener.onEditStarted();
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (listener != null) {
            listener.onEditCompleted();
        }
    }
}
