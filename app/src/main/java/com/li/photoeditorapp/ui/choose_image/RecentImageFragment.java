package com.li.photoeditorapp.ui.choose_image;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.li.photoeditorapp.R;
import com.li.photoeditorapp.base.BaseFragment;
import com.li.photoeditorapp.databinding.FragmentRecentImageBinding;
import com.li.photoeditorapp.model.ItemImageRecent;
import com.li.photoeditorapp.ui.choose_image.adapter.RecentImageAdapter;
import com.li.photoeditorapp.ui.choose_image.call_back.OnItemRecentImageClickListener;
import com.li.photoeditorapp.ui.edit_image.EditImageActivity;

import java.util.ArrayList;
import java.util.List;

public class RecentImageFragment extends BaseFragment<FragmentRecentImageBinding> implements OnItemRecentImageClickListener {
    private List<ItemImageRecent> itemImageRecentList;
    private RecentImageAdapter recentImageAdapter;

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_recent_image;
    }

    @Override
    protected void onViewReady(View view) {
        LoadImageTask myTask = new LoadImageTask();
        myTask.execute();
    }


    @Override
    public void onItemImageClick(int position) {
        Intent intent = new Intent(getActivity(), EditImageActivity.class);
        intent.putExtra("Image Data", "file://" + itemImageRecentList.get(position).getImagePath());
        startActivity(intent);

    }

    private class LoadImageTask extends AsyncTask<Void, Void, List<ItemImageRecent>> {
        @Override
        protected List<ItemImageRecent> doInBackground(Void... voids) {
            List<ItemImageRecent> tempList = new ArrayList<>();
            List<String> pathImage = new ArrayList<>();
            pathImage = getAllShownImagesPath(getActivity());
            for (int i = 0; i < pathImage.size(); i++) {
                ItemImageRecent itemImageRecent = new ItemImageRecent(pathImage.get(i));
                tempList.add(itemImageRecent);
            }
            Log.e("imageData: ", String.valueOf(tempList.size()));
            return tempList;
        }

        @Override
        protected void onPostExecute(List<ItemImageRecent> strings) {
            super.onPostExecute(strings);
            itemImageRecentList = new ArrayList<>();
            itemImageRecentList.addAll(strings);
            recentImageAdapter = new RecentImageAdapter(itemImageRecentList, getLayoutInflater(), RecentImageFragment.this::onItemImageClick);
            dataBinding.rvRecentImage.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
            dataBinding.rvRecentImage.setLayoutManager(layoutManager);
            dataBinding.rvRecentImage.setAdapter(recentImageAdapter);
        }
    }

    private ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }
}
