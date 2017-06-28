package com.sample.galleryapp.common;

import com.sample.galleryapp.gallery.models.ImageEntityWrapper;

import retrofit2.http.GET;
import rx.Observable;

public interface FlickrApi {
    public static final String BASE_URL = "https://api.flickr.com";

    @GET("/services/feeds/photos_public.gne\\?format\\=json\\&nojsoncallback\\=1")
    Observable<ImageEntityWrapper> getImages();
}
