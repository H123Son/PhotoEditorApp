package com.li.photoeditor.main.ui.edit_image_activity.fragment.filter_fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.li.photoeditor.R;
import com.li.photoeditor.databinding.ItemImageFilterBinding;
import com.li.photoeditor.main.ui.edit_image_activity.fragment.filter_fragment.callback.OnImageFilterClick;
import com.zomato.photofilters.utils.ThumbnailItem;

import java.util.List;
public class FilterImageAdapter extends RecyclerView.Adapter<FilterImageAdapter.ViewHolder> {
    private List<ThumbnailItem> itemImageFilterList;
    private LayoutInflater inflater;
    private OnImageFilterClick listener;

    public FilterImageAdapter(List<ThumbnailItem> itemImageFilterList, LayoutInflater inflater, OnImageFilterClick listener) {
        this.itemImageFilterList = itemImageFilterList;
        this.inflater = inflater;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImageFilterBinding binding = DataBindingUtil.inflate(inflater,R.layout.item_image_filter,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ThumbnailItem thumbnailItem = itemImageFilterList.get(position);
        holder.dataBinding.tvFilterName.setText(thumbnailItem.filterName);
        holder.dataBinding.imgItemImageFilter.setImageBitmap(thumbnailItem.image);

        //Glide.with(holder.dataBinding.imgItemImageFilter).load(thumbnailItem.image).into(holder.dataBinding.imgItemImageFilter);
        holder.dataBinding.imgItemImageFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFilterSelected(thumbnailItem.filter);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemImageFilterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemImageFilterBinding dataBinding;

        public ViewHolder(@NonNull ItemImageFilterBinding dataBinding) {
            super(dataBinding.getRoot());
            this.dataBinding = dataBinding;
        }
    }
    }
