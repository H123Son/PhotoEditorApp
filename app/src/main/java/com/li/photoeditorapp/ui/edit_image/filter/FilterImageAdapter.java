package com.li.photoeditorapp.ui.edit_image.filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.li.photoeditorapp.R;
import com.li.photoeditorapp.databinding.ItemFilterBinding;
import com.li.photoeditorapp.ui.edit_image.filter.callback.OnImageFilterClick;
import com.zomato.photofilters.utils.ThumbnailItem;

import java.util.List;

public class FilterImageAdapter extends RecyclerView.Adapter<FilterImageAdapter.ViewHolder> {
    private List<ThumbnailItem> itemImageFilterList;
    private LayoutInflater inflater;
    private OnImageFilterClick listener;
    private int selectedIteam;

    public FilterImageAdapter(List<ThumbnailItem> itemImageFilterList, LayoutInflater inflater, OnImageFilterClick listener) {
        this.itemImageFilterList = itemImageFilterList;
        this.inflater = inflater;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFilterBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_filter,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ThumbnailItem thumbnailItem = itemImageFilterList.get(position);
        holder.dataBinding.tvFilterName.setText(thumbnailItem.filterName);
        holder.dataBinding.imgFilterItem.setImageBitmap(thumbnailItem.image);
        holder.dataBinding.imgFilterItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedIteam = position;
                listener.onFilterSelected(thumbnailItem.filter);
                notifyDataSetChanged();
            }
        });
        if(selectedIteam == position){
            holder.dataBinding.lnFilter.setBackgroundResource(R.drawable.shape_item_filter_on);
            holder.dataBinding.tvFilterName.setTextColor(inflater.getContext().getColor(R.color.tabBackGroundColor));
        }
        else
        {
            holder.dataBinding.lnFilter.setBackgroundResource(R.drawable.shape_item_filter_off);
            holder.dataBinding.tvFilterName.setTextColor(inflater.getContext().getColor(R.color.tabTextColor));
        }

    }

    @Override
    public int getItemCount() {
        return itemImageFilterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemFilterBinding dataBinding;

        public ViewHolder(@NonNull ItemFilterBinding dataBinding) {
            super(dataBinding.getRoot());
            this.dataBinding = dataBinding;
        }
    }
    }

