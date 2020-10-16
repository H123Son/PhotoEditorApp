package com.li.photoeditorapp.ui.edit_image.edit_text;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.li.photoeditorapp.R;
import com.li.photoeditorapp.base.BaseFragment;
import com.li.photoeditorapp.databinding.FragmentListEditToolBinding;
import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnAddTextListener;

public class ListEditToolFragment extends BaseFragment<FragmentListEditToolBinding> {
    private OnAddTextListener listener;

    public ListEditToolFragment(OnAddTextListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_list_edit_tool;
    }

    @Override
    protected void onViewReady(View view) {
        dataBinding.navEditTextDemo.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_text:
                        dataBinding.navEditTextDemo.getMenu().findItem(R.id.nav_text).setChecked(true);
                        listener.onAddTextClick();
                        break;
                    case R.id.nav_sticker:
                        dataBinding.navEditTextDemo.getMenu().findItem(R.id.nav_sticker).setChecked(true);
                        break;
                    case R.id.nav_stroke_demo:
                        dataBinding.navEditTextDemo.getMenu().findItem(R.id.nav_stroke_demo).setChecked(true);
                        break;
                    case R.id.nav_border_demo:
                        dataBinding.navEditTextDemo.getMenu().findItem(R.id.nav_border_demo).setChecked(true);
                        break;
                    case R.id.nav_model:
                        dataBinding.navEditTextDemo.getMenu().findItem(R.id.nav_model).setChecked(true);
                        break;
                }
                return false;
            }
        });

    }
}