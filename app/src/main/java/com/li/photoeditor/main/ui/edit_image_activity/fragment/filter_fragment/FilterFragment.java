package com.li.photoeditor.main.ui.edit_image_activity.fragment.filter_fragment;

import android.graphics.Bitmap;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.li.photoeditor.R;
import com.li.photoeditor.databinding.FragmentFilterBinding;
import com.li.photoeditor.main.base.BaseFragment;
import com.li.photoeditor.main.ui.edit_image_activity.fragment.filter_fragment.callback.FilterListFragmentListener;
import com.li.photoeditor.main.ui.edit_image_activity.fragment.filter_fragment.callback.OnImageFilterClick;
import com.li.photoeditor.main.ui.edit_image_activity.EditImageActivity;
import com.li.photoeditor.main.ui.edit_image_activity.fragment.filter_fragment.adapter.FilterImageAdapter;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.util.ArrayList;
import java.util.List;


public class FilterFragment extends BaseFragment<FragmentFilterBinding> implements OnImageFilterClick {
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
    protected int getFragmentId() {
        return R.layout.fragment_filter;
    }

    @Override
    protected void onViewReady(View view) {
        EditImageActivity activity = (EditImageActivity) getActivity();
        //set scale image bitmap
        imageBitMap = Bitmap.createScaledBitmap(activity.sendMyData(), 100, 100, false);
        thumbnailItemList = new ArrayList<>();
        addListItemImageFilter();
        imageAdapter = new FilterImageAdapter(thumbnailItemList, getLayoutInflater(), this);
        dataBinding.rvListFilter.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        dataBinding.rvListFilter.setHasFixedSize(true);
        dataBinding.rvListFilter.setAdapter(imageAdapter);
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
