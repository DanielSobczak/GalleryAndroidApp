package com.sample.galleryapp.common.di.component;

import android.content.Context;

import com.sample.galleryapp.common.di.modules.FlickrAPIModule;
import com.sample.galleryapp.common.di.modules.ApplicationModule;
import com.sample.galleryapp.common.FlickrApi;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class}, dependencies = {FlickrAPIModule.class})
public interface ApplicationComponent {

    Context context();

    FlickrApi flickrApi();
}
