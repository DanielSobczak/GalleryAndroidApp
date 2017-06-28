package com.sample.galleryapp.gallery.views;

import android.os.Bundle;

import com.sample.galleryapp.R;
import com.sample.galleryapp.common.BaseActivity;
import com.sample.galleryapp.common.di.HasComponent;
import com.sample.galleryapp.gallery.DaggerGalleryComponent;
import com.sample.galleryapp.gallery.GalleryComponent;

public class GalleryActivity extends BaseActivity implements HasComponent<GalleryComponent> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, GalleryFragment.create())
                    .commit();
        }
    }

    @Override
    public GalleryComponent getComponent() {
        return DaggerGalleryComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build();
    }
}
