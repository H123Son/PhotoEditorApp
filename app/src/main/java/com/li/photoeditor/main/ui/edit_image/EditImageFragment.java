package com.li.photoeditor.main.ui.edit_image;

import android.view.View;
import android.widget.SeekBar;

import com.li.photoeditor.R;
import com.li.photoeditor.databinding.FragmentEditImageBinding;
import com.li.photoeditor.main.base.BaseFragment;
import com.li.photoeditor.main.ui.edit_image.callback.OnSeekBarChangeListener;

public class EditImageFragment extends BaseFragment<FragmentEditImageBinding> implements SeekBar.OnSeekBarChangeListener {
    private OnSeekBarChangeListener listener;

    public EditImageFragment() {
    }


    public EditImageFragment(OnSeekBarChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_edit_image;
    }

    @Override
    protected void onViewReady(View view) {
        dataBinding.sbBrightness.setOnSeekBarChangeListener(this);
        dataBinding.sbContrast.setOnSeekBarChangeListener(this);
        dataBinding.sbSaturation.setOnSeekBarChangeListener(this);
    }

    public void resetControls() {
        dataBinding.sbBrightness.setProgress(100);
        dataBinding.sbContrast.setProgress(0);
        dataBinding.sbSaturation.setProgress(10);
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
