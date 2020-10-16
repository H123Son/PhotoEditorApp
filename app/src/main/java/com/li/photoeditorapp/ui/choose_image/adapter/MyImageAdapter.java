package com.li.photoeditorapp.ui.choose_image.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.li.photoeditorapp.R;
import com.li.photoeditorapp.data.local.model.ImageEdited;
import com.li.photoeditorapp.databinding.ItemMyImageBinding;
import com.li.photoeditorapp.ui.choose_image.call_back.OnItemMyImageClickListener;

import java.util.List;

public class MyImageAdapter extends RecyclerView.Adapter<MyImageAdapter.ViewHolder> {
    private List<ImageEdited> itemMyImagesList;
    private LayoutInflater inflater;
    private OnItemMyImageClickListener listener;

    public MyImageAdapter(List<ImageEdited> itemMyImagesList, LayoutInflater inflater, OnItemMyImageClickListener listener) {
        this.itemMyImagesList = itemMyImagesList;
        this.inflater = inflater;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMyImageBinding itemMyImageBinding = DataBindingUtil.inflate(inflater, R.layout.item_my_image, parent, false);
        return new ViewHolder(itemMyImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(itemMyImagesList.get(position));
        holder.binding.imgMyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMyImageClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemMyImagesList.size();
    }

    public void setImageEditedList(List<ImageEdited> imageEditedList) {
        this.itemMyImagesList = imageEditedList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMyImageBinding binding;

        public ViewHolder(@NonNull ItemMyImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ImageEdited itemMyImage) {
            binding.setMyImage(itemMyImage);
            binding.executePendingBindings();


        }
    }
}
