package com.li.photoeditorapp.ui.edit_image.edit_text.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.li.photoeditorapp.R;
import com.li.photoeditorapp.databinding.ItemTextColorBinding;

import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnItemTextColorClickListener;


import java.util.List;

public class TextColorAdapter extends RecyclerView.Adapter<TextColorAdapter.ViewHolder> {
    private List<Integer> colorList;
    private LayoutInflater inflater;
    private OnItemTextColorClickListener listener;
    private int selectedIteam;

    public TextColorAdapter(List<Integer> colorList, LayoutInflater inflater, OnItemTextColorClickListener listener) {
        this.colorList = colorList;
        this.inflater = inflater;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTextColorBinding  binding = DataBindingUtil.inflate(inflater,R.layout.item_text_color,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.binding.imgTextColor.setBackgroundColor(colorList.get(position));
        holder.binding.imgTextColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedIteam = position;
                listener.onColorClick(colorList.get(position));
                notifyDataSetChanged();
            }
        });
        if(selectedIteam == position){
             if(position == 0)
            {
                holder.binding.lnTextColor.setBackgroundColor(inflater.getContext().getColor(R.color.tabBackGroundColor));
            }
             else
            holder.binding.lnTextColor.setBackgroundColor(inflater.getContext().getColor(R.color.tabTextColor));
        }
        else
        {
            holder.binding.lnTextColor.setBackgroundColor(inflater.getContext().getColor(R.color.background_app_color));
        }

    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemTextColorBinding binding;
        public ViewHolder(@NonNull ItemTextColorBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
