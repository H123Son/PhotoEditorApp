package com.li.photoeditorapp.ui.edit_image.edit_image_parameters.callback;

public interface OnSeekBarChangeListener {
    void onBrightnessChanged(int brightness);

    void onSaturationChanged(float saturation);

    void onContrastChanged(float contrast);

    void onEditStarted();

    void onEditCompleted();
}
