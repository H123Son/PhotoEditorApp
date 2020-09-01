package com.li.photoeditor.main.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.li.photoeditor.R;
import com.li.photoeditor.main.activity.EditImageActivity;
import com.li.photoeditor.main.adapter.FilterImageAdapter;
import com.li.photoeditor.main.callback.FilterListFragmentListener;
import com.li.photoeditor.main.callback.OnImageFilterClick;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.util.ArrayList;
import java.util.List;


public class FilterFragment extends Fragment implements OnImageFilterClick {
    private RecyclerView rvListFilter;
    private List<ThumbnailItem> thumbnailItemList;
    private FilterImageAdapter imageAdapter;
    private FilterListFragmentListener listener;
    private Bitmap imageBitMap;

    public FilterFragment() {
    }

    public FilterFragment(FilterListFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        rvListFilter = view.findViewById(R.id.rv_list_filter);
        EditImageActivity activity = (EditImageActivity) getActivity();
        //set scale image bitmap
        imageBitMap = Bitmap.createScaledBitmap(activity.sendMyData(), 100, 100, false);

        thumbnailItemList = new ArrayList<>();
        addListItemImageFilter();
        imageAdapter = new FilterImageAdapter(thumbnailItemList, getContext(), this);
        rvListFilter.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvListFilter.setHasFixedSize(true);
        rvListFilter.setAdapter(imageAdapter);
        return view;
    }

    private void addListItemImageFilter() {
        ThumbnailsManager.clearThumbs();
        thumbnailItemList.clear();
        ThumbnailItem thumbnailItem = new ThumbnailItem();
        thumbnailItem.image = imageBitMap;
        thumbnailItem.filterName = "Normal";
        ThumbnailsManager.addThumb(thumbnailItem);
        List<Filter> filters = FilterPack.getFilterPack(getContext());
        for (Filter filter : filters) {
            ThumbnailItem item = new ThumbnailItem();
            item.image = imageBitMap;
            item.filter = filter;
            item.filterName = filter.getName();
            ThumbnailsManager.addThumb(item);
        }
        thumbnailItemList.addAll(ThumbnailsManager.processThumbs(getActivity()));


    }

    @Override
    public void onFilterSelected(Filter filter) {
        listener.onFilterSelected(filter);
    }

}
