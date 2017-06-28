package com.sample.galleryapp.common;

import android.app.Application;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.sample.galleryapp.common.di.component.ApplicationComponent;
import com.sample.galleryapp.common.di.component.DaggerApplicationComponent;
import com.sample.galleryapp.common.di.modules.ApplicationModule;

import okhttp3.OkHttpClient;


public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDagger();
        initializeFresco();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void initializeDagger() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void initializeFresco() {
        final DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(getCacheDir())
                .setBaseDirectoryName("cache")
                .setMaxCacheSize(20 * 1024 * 1024)
                .setMaxCacheSizeOnLowDiskSpace(2 * 1024 * 1024)
                .setMaxCacheSizeOnVeryLowDiskSpace(1024 * 1024)
                .build();
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .build();
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(this, httpClient)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();
        Fresco.initialize(this, config);
    }
}
