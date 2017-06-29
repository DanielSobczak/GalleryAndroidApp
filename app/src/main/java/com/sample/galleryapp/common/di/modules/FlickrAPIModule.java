package com.sample.galleryapp.common.di.modules;

import com.sample.galleryapp.common.FlickrApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class FlickrAPIModule {

    @Provides
    @Singleton
    public FlickrApi provideFlickApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FlickrApi.BASE_URL)
                .client(new OkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(FlickrApi.class);
    }
}
