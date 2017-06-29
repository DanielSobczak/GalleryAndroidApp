package com.sample.galleryapp.gallery.views;

import com.sample.galleryapp.common.exceptions.ErrorBundle;
import com.sample.galleryapp.gallery.models.GalleryCellImage;

import java.util.List;

public interface GalleryView {
    void renderGalleryImages(List<GalleryCellImage> galleryImages);

    void showLoading();

    void showError(ErrorBundle errorBundle);
}
