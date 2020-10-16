package com.li.photoeditorapp.ui.edit_image.edit_text;

import android.view.View;
import android.widget.SeekBar;

import com.li.photoeditorapp.R;
import com.li.photoeditorapp.base.BaseFragment;
import com.li.photoeditorapp.databinding.FragmentTextOpacityBinding;
import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnTextOpacityChangeListener;

public class TextOpacityFragment extends BaseFragment<FragmentTextOpacityBinding> {
    private OnTextOpacityChangeListener listener;

    public TextOpacityFragment(OnTextOpacityChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_text_opacity;
    }

    @Override
    protected void onViewReady(View view) {
        dataBinding.sbTextOpacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                listener.onSeekBarChange(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void resetControl() {
        dataBinding.sbTextOpacity.setProgress(255);
    }
}