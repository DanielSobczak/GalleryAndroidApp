package com.sample.galleryapp.gallery;

import com.sample.galleryapp.common.FlickrApi;
import com.sample.galleryapp.gallery.services.FlickrImageService;
import com.sample.galleryapp.gallery.services.ImageProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class GalleryModule {

    @Provides
    ImageProvider providesImageProvider(final FlickrApi flickrApi) {
        return new FlickrImageService(flickrApi);
    }

    @Provides
    GalleryCellImageMapper providesGalleryCellImageMapper() {
        return new GalleryCellImageMapper();
    }

    @Provides
    GalleryPresenter providesGalleryPresenter(final GetGalleryImages getGalleryImages) {
        return new GalleryPresenter(getGalleryImages);
    }

}
