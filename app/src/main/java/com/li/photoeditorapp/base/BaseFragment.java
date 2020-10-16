package com.li.photoeditorapp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<BD extends ViewDataBinding> extends Fragment {
    protected BD dataBinding;
    protected abstract int getFragmentId();
    protected abstract void onViewReady(View view);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater,getFragmentId(),container,false);
        onViewReady(dataBinding.getRoot());
        return dataBinding.getRoot();
    }
}
