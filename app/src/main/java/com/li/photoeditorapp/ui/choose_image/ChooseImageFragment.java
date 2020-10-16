package com.li.photoeditorapp.ui.choose_image;

import android.view.View;
import android.view.ViewParent;

import androidx.viewpager.widget.ViewPager;

import com.li.photoeditorapp.R;
import com.li.photoeditorapp.base.BaseFragment;
import com.li.photoeditorapp.databinding.FragmentChooseImageBinding;
import com.li.photoeditorapp.model.ItemImageRecent;
import com.li.photoeditorapp.ui.choose_image.adapter.RecentImageAdapter;
import com.li.photoeditorapp.ui.choose_image.call_back.OnItemRecentImageClickListener;

import java.util.ArrayList;
import java.util.List;

public class ChooseImageFragment extends BaseFragment<FragmentChooseImageBinding>{

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_choose_image;
    }

    @Override
    protected void onViewReady(View view) {
        dataBinding.btnStartChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewById = getActivity().findViewById(R.id.vp_image_list);
                viewById.setCurrentItem(0);
            }
        });


    }

}
