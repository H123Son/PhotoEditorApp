package com.li.photoeditorapp.ui.choose_image;

import android.view.View;
import com.li.photoeditorapp.R;
import com.li.photoeditorapp.base.BaseFragment;
import com.li.photoeditorapp.databinding.FragmentCheckPermissionBinding;
import com.li.photoeditorapp.ultils.PermissionManager;

public class CheckPermissionFragment extends BaseFragment<FragmentCheckPermissionBinding> {


    public CheckPermissionFragment() {
    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_check_permission;
    }

    @Override
    protected void onViewReady(View view) {
        dataBinding.btnCheckPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionManager.getInstance(getContext()).requestPermission();
            }
        });

    }

}
