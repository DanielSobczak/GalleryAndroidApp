package com.sample.galleryapp.common.di.component;

import android.content.Context;

import com.sample.galleryapp.common.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    Context context();

}
