package com.li.photoeditorapp.ui.edit_image.edit_text;

import android.graphics.Color;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.li.photoeditorapp.R;
import com.li.photoeditorapp.base.BaseFragment;
import com.li.photoeditorapp.databinding.FragmentTextColorChangerBinding;
import com.li.photoeditorapp.ui.edit_image.edit_text.adapter.FontAdapter;
import com.li.photoeditorapp.ui.edit_image.edit_text.adapter.TextColorAdapter;
import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnItemTextColorClickListener;

import java.util.ArrayList;
import java.util.List;

public class TextColorChangerFragment extends BaseFragment<FragmentTextColorChangerBinding>  {
    private List<Integer> colorList;
    private TextColorAdapter colorAdapter;
    private OnItemTextColorClickListener listener;

    public TextColorChangerFragment(OnItemTextColorClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_text_color_changer;
    }

    @Override
    protected void onViewReady(View view) {
        addTextColor();
        colorAdapter = new TextColorAdapter(colorList,getLayoutInflater(),listener);
        dataBinding.rvTextColorList.setHasFixedSize(true);
        dataBinding.rvTextColorList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        dataBinding.rvTextColorList.setAdapter(colorAdapter);

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