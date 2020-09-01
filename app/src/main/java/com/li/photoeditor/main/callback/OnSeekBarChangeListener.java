package com.li.photoeditor.main.callback;

public interface  OnSeekBarChangeListener {
    void onBrightnessChanged(int brightness);

    void onSaturationChanged(float saturation);

    void onContrastChanged(float contrast);

    void onEditStarted();

    void onEditCompleted();
}
