package com.li.photoeditor.main.ui.image_edited_activity.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.li.photoeditor.R;
import com.li.photoeditor.databinding.ItemImageEdtitedBinding;
import com.li.photoeditor.main.ui.image_edited_activity.adapter.callback.OnItemImageEditedClick;
import com.li.photoeditor.main.model.ImageEdited;

import java.util.List;

public class ImageEditedAdapter extends RecyclerView.Adapter<ImageEditedAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<ImageEdited> imageEditedList;
    private OnItemImageEditedClick listener;

    public ImageEditedAdapter(LayoutInflater inflater, List<ImageEdited> imageEditedList, OnItemImageEditedClick listener) {
        this.inflater = inflater;
        this.imageEditedList = imageEditedList;
        this.listener = listener;
    }

    public void setImageEditedList(List<ImageEdited> imageEditedList) {
        this.imageEditedList = imageEditedList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImageEdtitedBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.item_image_edtited, parent, false);
        return new ViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ImageEdited imageEdited = imageEditedList.get(position);
        Uri uri = Uri.parse(imageEdited.getImageData());
        holder.dataBinding.imgImageEdited.setImageURI(uri);
        holder.dataBinding.imgImageEdited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageEditedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemImageEdtitedBinding dataBinding;

        public ViewHolder(@NonNull ItemImageEdtitedBinding dataBinding) {
            super(dataBinding.getRoot());
            this.dataBinding = dataBinding;
        }
    }
}
