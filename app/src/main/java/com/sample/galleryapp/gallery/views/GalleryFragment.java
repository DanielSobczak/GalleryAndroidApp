package com.sample.galleryapp.gallery.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.sample.galleryapp.R;
import com.sample.galleryapp.common.BaseFragment;
import com.sample.galleryapp.gallery.GalleryComponent;

public class GalleryFragment extends BaseFragment {

    public static Fragment create() {
        return new GalleryFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_gallery;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getComponent(GalleryComponent.class).inject(this);
    }
}
