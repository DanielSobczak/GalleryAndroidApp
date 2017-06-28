package com.sample.galleryapp.common.di.modules;

import android.content.Context;

import com.sample.galleryapp.common.AndroidApplication;
import com.sample.galleryapp.common.executor.JobExecutor;
import com.sample.galleryapp.common.executor.PostExecutionThread;
import com.sample.galleryapp.common.executor.ThreadExecutor;
import com.sample.galleryapp.common.executor.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(final AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application.getBaseContext();
    }

    @Provides
    @Singleton
    protected ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    protected PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

}