package com.li.photoeditor.main.ui.image_edited_activity;

import androidx.annotation.NonNull;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.li.photoeditor.R;
import com.li.photoeditor.databinding.ActivityImageEditedBinding;
import com.li.photoeditor.main.base.BaseActivity;
import com.li.photoeditor.main.ui.image_edited_activity.adapter.callback.OnItemImageEditedClick;
import com.li.photoeditor.main.data.local.ImgEditedDatabase;
import com.li.photoeditor.main.model.ImageEdited;
import com.li.photoeditor.main.ui.image_edited_activity.adapter.ImageEditedAdapter;
import com.li.photoeditor.main.ui.show_image_activity.ShowImageActivity;

import java.util.ArrayList;
import java.util.List;

public class ImageEditedActivity extends BaseActivity<ActivityImageEditedBinding> implements OnItemImageEditedClick,Runnable {
    private List<ImageEdited> imageEditedList;
    private ImageEditedAdapter imageEditedAdapter;
    private Thread thread;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_edited;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(dataBinding.tbOptionImageEdited);
        thread = new Thread(this);
        thread.start();


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

    @Override
    public void run() {
        imageEditedList = new ArrayList<>();
        List<ImageEdited> myDataLists = ImgEditedDatabase.getInstance(ImageEditedActivity.this).getImageDao().getAll();
        imageEditedList.addAll(myDataLists);
        imageEditedAdapter = new ImageEditedAdapter(getLayoutInflater(), imageEditedList, this);
        dataBinding.rvImageEdited.setAdapter(imageEditedAdapter);
        imageEditedAdapter.setImageEditedList(imageEditedList);
    }
}
