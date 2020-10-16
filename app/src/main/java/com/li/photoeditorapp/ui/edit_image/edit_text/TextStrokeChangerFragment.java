package com.li.photoeditorapp.ui.edit_image.edit_text;

import android.graphics.Color;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.li.photoeditorapp.R;
import com.li.photoeditorapp.base.BaseFragment;
import com.li.photoeditorapp.databinding.FragmentTextStrokeChangerBinding;
import com.li.photoeditorapp.ui.edit_image.edit_text.adapter.TextStrokeColorAdapter;
import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnItemTextStrokeColorClickListener;


import java.util.ArrayList;
import java.util.List;

public class TextStrokeChangerFragment extends BaseFragment<FragmentTextStrokeChangerBinding>  {
    private List<Integer> colorList;
    private TextStrokeColorAdapter colorAdapter;
    private OnItemTextStrokeColorClickListener listener;

    public TextStrokeChangerFragment(OnItemTextStrokeColorClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_text_stroke_changer;
    }

    @Override
    protected void onViewReady(View view) {
        addTextColor();
        colorAdapter = new TextStrokeColorAdapter(colorList,getLayoutInflater(),listener);
        dataBinding.rvTextStrokeList.setHasFixedSize(true);
        dataBinding.rvTextStrokeList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        dataBinding.rvTextStrokeList.setAdapter(colorAdapter);

    }
    private void addTextColor() {
        colorList = new ArrayList<>();
        colorList.add(Color.WHITE);
        colorList.add(Color.BLACK);
        colorList.add(Color.BLUE);
        colorList.add(Color.CYAN);
        colorList.add(Color.DKGRAY);
        colorList.add(Color.GREEN);
        colorList.add(Color.GRAY);
        colorList.add(Color.LTGRAY);
        colorList.add(Color.MAGENTA);
        colorList.add(Color.RED);
        colorList.add(Color.YELLOW);
    }

}