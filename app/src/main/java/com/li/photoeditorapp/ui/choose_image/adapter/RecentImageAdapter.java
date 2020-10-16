package com.li.photoeditorapp.ui.choose_image.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.li.photoeditorapp.R;
import com.li.photoeditorapp.databinding.ItemRecentImageBinding;
import com.li.photoeditorapp.model.ItemImageRecent;
import com.li.photoeditorapp.ui.choose_image.call_back.OnItemRecentImageClickListener;
import java.util.List;

public class RecentImageAdapter extends RecyclerView.Adapter<RecentImageAdapter.ViewHolder> {
    private List<ItemImageRecent> itemImageRecents;
    private LayoutInflater inflater;
    private OnItemRecentImageClickListener listener;

    public RecentImageAdapter(List<ItemImageRecent> itemImageRecents, LayoutInflater inflater, OnItemRecentImageClickListener listener) {
        this.itemImageRecents = itemImageRecents;
        this.inflater = inflater;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecentImageBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_recent_image,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(itemImageRecents.get(position));
        holder.binding.imgRecentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemImageClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemImageRecents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRecentImageBinding binding;
        public ViewHolder(@NonNull ItemRecentImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(ItemImageRecent itemImageRecent){
            binding.setImageRecent(itemImageRecent);
            binding.executePendingBindings();


        }
    }
}
