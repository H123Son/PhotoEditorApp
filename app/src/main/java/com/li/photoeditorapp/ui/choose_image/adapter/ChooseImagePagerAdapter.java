package com.li.photoeditorapp.ui.choose_image.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.li.photoeditorapp.data.local.ImgEditedDatabase;
import com.li.photoeditorapp.data.local.model.ImageEdited;
import com.li.photoeditorapp.ui.choose_image.ChooseImageFragment;
import com.li.photoeditorapp.ui.choose_image.MyImageFragment;
import com.li.photoeditorapp.ui.choose_image.RecentImageFragment;

import java.util.ArrayList;
import java.util.List;

public class ChooseImagePagerAdapter extends FragmentPagerAdapter {
    private String[] tabItemName = new String[]{"GẦN ĐÂY", "ĐÃ CHỈNH SỬA"};
    private Context context;
    public ChooseImagePagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RecentImageFragment();
            case 1:
                if (checkEmptyListMyImageSize()) {
                    return new ChooseImageFragment();
                } else return new MyImageFragment();

        }
        return null;

    }

    private boolean checkEmptyListMyImageSize() {
        List<ImageEdited> imageEditedList = new ArrayList<>();
        imageEditedList = ImgEditedDatabase.getInstance(context).getImageDao().getAll();
        if (imageEditedList.size() != 0) {
            return false;
        } else return true;
    }

    @Override
    public int getCount() {
        return tabItemName.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabItemName[position];
    }

}
