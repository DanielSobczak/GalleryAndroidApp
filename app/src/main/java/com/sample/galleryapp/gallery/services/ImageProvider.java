package com.sample.galleryapp.gallery.services;

import com.sample.galleryapp.gallery.models.Image;

import java.util.List;

import rx.Observable;

public interface ImageProvider {
    Observable<List<Image>> getImages();
}
