package com.sample.galleryapp.common.di.component;

import android.app.Activity;

import com.sample.galleryapp.common.di.PerActivity;
import com.sample.galleryapp.common.di.modules.ActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {

    Activity activity();

}
