package com.li.photoeditorapp.ui.edit_image.edit_image_parameters;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.li.photoeditorapp.R;
import com.li.photoeditorapp.base.BaseFragment;
import com.li.photoeditorapp.databinding.FragmentEditImageBinding;
import com.li.photoeditorapp.ui.edit_image.edit_image_parameters.callback.OnSeekBarChangeListener;

public class EditImageFragment extends BaseFragment<FragmentEditImageBinding> implements BottomNavigationView.OnNavigationItemSelectedListener {
    private OnSeekBarChangeListener listener;
    private BrightnessFragment brightnessFragment;
    private ConstrastFragment constrastFragment;
    private SaturationFragment saturationFragment;

    public EditImageFragment(OnSeekBarChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_edit_image;
    }

    @Override
    protected void onViewReady(View view) {
        setUpFragment();
        dataBinding.navEditImage.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        dataBinding.navEditImage.getMenu().findItem(R.id.nav_brightness).setChecked(true);

    }

    private void setUpFragment() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fr_sb_container, new BrightnessFragment(listener))
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_brightness:
                dataBinding.navEditImage.getMenu().findItem(R.id.nav_brightness).setChecked(true);
                brightnessFragment = new BrightnessFragment(listener);
                replaceFragment(brightnessFragment);
                break;
            case R.id.nav_constrast:
                dataBinding.navEditImage.getMenu().findItem(R.id.nav_constrast).setChecked(true);
                constrastFragment = new ConstrastFragment(listener);
                replaceFragment(constrastFragment);
                break;
            case R.id.nav_saturation:
                dataBinding.navEditImage.getMenu().findItem(R.id.nav_saturation).setChecked(true);
                saturationFragment = new SaturationFragment(listener);
                replaceFragment(saturationFragment);
                break;

        }
        return false;
    }

    private void replaceFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fr_sb_container, fragment)
                .commit();
    }
    public void resetControls() {
        if(brightnessFragment!=null)
        {
            brightnessFragment.resetControls();
        }
        else if(saturationFragment!=null)
        {
            saturationFragment.resetControls();
        }
        else if(constrastFragment!=null){
            constrastFragment.resetControls();
        }

    }
}
