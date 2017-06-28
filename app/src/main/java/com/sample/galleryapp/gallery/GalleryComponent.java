package com.sample.galleryapp.gallery;

import com.sample.galleryapp.common.di.PerActivity;
import com.sample.galleryapp.common.di.component.ApplicationComponent;
import com.sample.galleryapp.common.di.modules.ActivityModule;
import com.sample.galleryapp.gallery.views.GalleryFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface GalleryComponent {
    void inject(GalleryFragment galleryFragment);
}
