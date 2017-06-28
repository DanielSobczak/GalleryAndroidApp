package com.sample.galleryapp.common;

import android.app.Application;

import com.sample.galleryapp.common.di.component.ApplicationComponent;
import com.sample.galleryapp.common.di.component.DaggerApplicationComponent;
import com.sample.galleryapp.common.di.modules.ApplicationModule;


public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDagger();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void initializeDagger() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
}
