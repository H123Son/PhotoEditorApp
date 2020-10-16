package com.li.photoeditorapp.ui.edit_image.edit_text.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.li.photoeditorapp.R;
import com.li.photoeditorapp.databinding.ItemTextStrokeBinding;
import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnItemTextStrokeColorClickListener;

import java.util.List;

public class TextStrokeColorAdapter extends RecyclerView.Adapter<TextStrokeColorAdapter.ViewHolder> {
    private List<Integer> colorList;
    private LayoutInflater inflater;
    private OnItemTextStrokeColorClickListener listener;
    private int selectedIteam;

    public TextStrokeColorAdapter(List<Integer> colorList, LayoutInflater inflater, OnItemTextStrokeColorClickListener listener) {
        this.colorList = colorList;
        this.inflater = inflater;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTextStrokeBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_text_stroke,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.imgTextColor.setBackgroundColor(colorList.get(position));
        holder.binding.imgTextColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedIteam = position;
                listener.onStrokeColorCLick(colorList.get(position));
                notifyDataSetChanged();
            }
        });
        if(selectedIteam == position){
            if(position == 0)
            {
                holder.binding.loTextStrokeColor.setBackgroundColor(inflater.getContext().getColor(R.color.tabBackGroundColor));
            }
            else
                holder.binding.loTextStrokeColor.setBackgroundColor(inflater.getContext().getColor(R.color.tabTextColor));
        }
        else
        {
            holder.binding.loTextStrokeColor.setBackgroundColor(inflater.getContext().getColor(R.color.background_app_color));
        }

    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemTextStrokeBinding binding;
        public ViewHolder(@NonNull ItemTextStrokeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
