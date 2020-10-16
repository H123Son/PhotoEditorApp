package com.li.photoeditorapp.ui.edit_image.edit_image_parameters;

import android.view.View;
import android.widget.SeekBar;

import com.li.photoeditorapp.R;
import com.li.photoeditorapp.base.BaseFragment;
import com.li.photoeditorapp.databinding.FragmentSaturationBinding;
import com.li.photoeditorapp.ui.edit_image.edit_image_parameters.callback.OnSeekBarChangeListener;

public class SaturationFragment extends BaseFragment<FragmentSaturationBinding> {
    private OnSeekBarChangeListener listener;

    public SaturationFragment(OnSeekBarChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_saturation;
    }

    @Override
    protected void onViewReady(View view) {
        dataBinding.sbSaturation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float floatVal = .10f * progress;
                listener.onSaturationChanged(floatVal);
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
        dataBinding.sbSaturation.setProgress(10);
    }
}