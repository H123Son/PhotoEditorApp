package com.li.photoeditor.main.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.li.photoeditor.R;
import com.li.photoeditor.main.callback.OnItemImageEditedClick;
import com.li.photoeditor.main.model.ImageEdited;

import java.util.List;

public class ImageEditedAdapter extends RecyclerView.Adapter<ImageEditedAdapter.ViewHolder> {
    private Context context;
    private List<ImageEdited> imageEditedList;
    private OnItemImageEditedClick listener;

    public ImageEditedAdapter(Context context, List<ImageEdited> imageEditedList, OnItemImageEditedClick listener) {
        this.context = context;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_edtited, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ImageEdited imageEdited = imageEditedList.get(position);
        Uri uri = Uri.parse(imageEdited.getImageData());
        holder.imgEdited.setImageURI(uri);
        holder.imgEdited.setOnClickListener(new View.OnClickListener() {
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
        ImageView imgEdited;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgEdited = itemView.findViewById(R.id.img_image_edited);
        }
    }
}
