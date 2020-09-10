package com.li.photoeditor.main.ui.image_edited;

import androidx.annotation.NonNull;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import com.li.photoeditor.R;
import com.li.photoeditor.databinding.ActivityImageEditedBinding;
import com.li.photoeditor.main.base.BaseActivity;
import com.li.photoeditor.main.ui.edit_image.EditImageActivity;
import com.li.photoeditor.main.ui.image_edited.adapter.callback.OnItemImageEditedClick;
import com.li.photoeditor.main.data.local.ImgEditedDatabase;
import com.li.photoeditor.main.data.local.model.ImageEdited;
import com.li.photoeditor.main.ui.image_edited.adapter.ImageEditedAdapter;
import com.li.photoeditor.main.ui.show_image.ShowImageActivity;

import java.util.ArrayList;
import java.util.List;

public class ImageEditedActivity extends BaseActivity<ActivityImageEditedBinding> implements OnItemImageEditedClick {
    private List<ImageEdited> imageEditedList;
    private ImageEditedAdapter imageEditedAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_edited;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(dataBinding.tbOptionImageEdited);
        GetImageTask getImageTask = new GetImageTask();
        getImageTask.execute();
       // ImgEditedDatabase.getInstance(this).getImageDao().deleteAllUser();

    }

    @Override
    public void onClick(int position) {
        ImageEdited imageEdited = imageEditedList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Image Edited", imageEdited);
        Intent intent = new Intent(ImageEditedActivity.this, ShowImageActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class GetImageTask extends AsyncTask<Void,Void,List<ImageEdited>>{


        @Override
        protected List<ImageEdited> doInBackground(Void... voids) {
            List<ImageEdited> imageEditeds = new ArrayList<>();
            imageEditeds = ImgEditedDatabase.getInstance(ImageEditedActivity.this).getImageDao().getAll();
            return imageEditeds;
        }

        @Override
        protected void onPostExecute(List<ImageEdited> imageEditeds) {
            super.onPostExecute(imageEditeds);
            imageEditedList = new ArrayList<>();
            imageEditedList.addAll(imageEditeds);
            imageEditedAdapter = new ImageEditedAdapter(getLayoutInflater(), imageEditedList, ImageEditedActivity.this::onClick);
            dataBinding.rvImageEdited.setAdapter(imageEditedAdapter);
            imageEditedAdapter.setImageEditedList(imageEditedList);

        }
    }
    }


