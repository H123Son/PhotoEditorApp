package com.li.photoeditor.main.ui.edit_image_activity.callback;

public interface  OnSeekBarChangeListener {
    void onBrightnessChanged(int brightness);

    void onSaturationChanged(float saturation);

    void onContrastChanged(float contrast);

    void onEditStarted();

    void onEditCompleted();
}
