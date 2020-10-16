package com.li.photoeditorapp.ui.edit_image.edit_text.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.li.photoeditorapp.R;
import com.li.photoeditorapp.databinding.ItemTextFontBinding;
import com.li.photoeditorapp.model.ItemTextFont;
import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnItemTextFontClickListener;

import java.util.List;

public class FontAdapter extends RecyclerView.Adapter<FontAdapter.ViewHolder> {
    private List<ItemTextFont> itemTextFontList;
    private LayoutInflater inflater;
    private OnItemTextFontClickListener listener;
    private int selectedIteam;

    public FontAdapter(List<ItemTextFont> itemTextFontList, LayoutInflater inflater, OnItemTextFontClickListener listener) {
        this.itemTextFontList = itemTextFontList;
        this.inflater = inflater;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTextFontBinding fontBinding = DataBindingUtil.inflate(inflater, R.layout.item_text_font, parent, false);
        return new ViewHolder(fontBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemTextFont itemTextFont = itemTextFontList.get(position);
        holder.binding.tvFont.setText(itemTextFont.getFontName());
        holder.binding.tvFont.setTypeface(itemTextFont.getTextFont());
        holder.binding.tvFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedIteam = position;
                listener.onFontClick(itemTextFont.getTextFont());
                notifyDataSetChanged();

            }
        });
        if(selectedIteam == position){
            holder.binding.tvFont.setTextColor(inflater.getContext().getColor(R.color.colorSelected));
        }
        else
        {
            holder.binding.tvFont.setTextColor(inflater.getContext().getColor(R.color.tabTextColor));
        }
    }

    @Override
    public int getItemCount() {
        return itemTextFontList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemTextFontBinding binding;

        public ViewHolder(@NonNull ItemTextFontBinding itemTextFontBinding) {
            super(itemTextFontBinding.getRoot());
            this.binding = itemTextFontBinding;
        }
    }
}
