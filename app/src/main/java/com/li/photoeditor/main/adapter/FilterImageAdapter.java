package com.li.photoeditor.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.li.photoeditor.R;
import com.li.photoeditor.main.callback.OnImageFilterClick;
import com.zomato.photofilters.utils.ThumbnailItem;

import java.util.List;
public class FilterImageAdapter extends RecyclerView.Adapter<FilterImageAdapter.ViewHolder> {
    private List<ThumbnailItem> itemImageFilterList;
    private Context context;
    private OnImageFilterClick listener;


    public FilterImageAdapter(List<ThumbnailItem> itemImageFilterList, Context context, OnImageFilterClick listener) {
        this.itemImageFilterList = itemImageFilterList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_filter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ThumbnailItem thumbnailItem = itemImageFilterList.get(position);
        holder.tvFilterName.setText(thumbnailItem.filterName);
        holder.itemImage.setImageBitmap(thumbnailItem.image);
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
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
        ImageView itemImage;
        TextView tvFilterName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.img_item_image_filter);
            tvFilterName = itemView.findViewById(R.id.tv_filter_name);

        }
    }
}
