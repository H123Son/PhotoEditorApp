package com.li.photoeditor.main.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.li.photoeditor.R;
import com.li.photoeditor.main.adapter.ImageEditedAdapter;
import com.li.photoeditor.main.callback.OnItemImageEditedClick;
import com.li.photoeditor.main.database.ImgEditedDatabase;
import com.li.photoeditor.main.model.ImageEdited;

import java.util.ArrayList;
import java.util.List;

public class ImageEditedActivity extends AppCompatActivity implements OnItemImageEditedClick {
    private Toolbar tbOptiont;
    private RecyclerView rvImage;
    private List<ImageEdited> imageEditedList;
    private ImageEditedAdapter imageEditedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_edited);
        tbOptiont = findViewById(R.id.tb_option_image_edited);
        rvImage = findViewById(R.id.rv_image_edited);
        setSupportActionBar(tbOptiont);

        imageEditedList = new ArrayList<>();
        List<ImageEdited> myDataLists = ImgEditedDatabase.getInstance(ImageEditedActivity.this).getImageDao().getAll();
        imageEditedList.addAll(myDataLists);
        imageEditedAdapter = new ImageEditedAdapter(this, imageEditedList, this);
        rvImage.setAdapter(imageEditedAdapter);
        imageEditedAdapter.setImageEditedList(imageEditedList);

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
}
