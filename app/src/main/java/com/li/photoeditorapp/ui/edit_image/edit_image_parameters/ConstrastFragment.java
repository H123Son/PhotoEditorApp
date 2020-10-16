package com.li.photoeditorapp.ui.edit_image.edit_image_parameters;

import android.view.View;
import android.widget.SeekBar;

import com.li.photoeditorapp.R;
import com.li.photoeditorapp.base.BaseFragment;
import com.li.photoeditorapp.databinding.FragmentConstrastBinding;
import com.li.photoeditorapp.ui.edit_image.edit_image_parameters.callback.OnSeekBarChangeListener;

public class ConstrastFragment extends BaseFragment<FragmentConstrastBinding> {
    private OnSeekBarChangeListener listener;

    public ConstrastFragment(OnSeekBarChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_constrast;
    }

    @Override
    protected void onViewReady(View view) {
        dataBinding.sbConstrast.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress += 10;
                float floatVal = .10f * progress;
                listener.onContrastChanged(floatVal);
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
        dataBinding.sbConstrast.setProgress(0);

    }
}