package com.sample.galleryapp.gallery.services;

import com.sample.galleryapp.common.FlickrApi;
import com.sample.galleryapp.gallery.models.Image;
import com.sample.galleryapp.gallery.models.ImageEntity;
import com.sample.galleryapp.gallery.models.ImageEntityWrapper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class FlickrImageService implements ImageProvider {
    private final FlickrApi flickrApi;

    public FlickrImageService(final FlickrApi flickrApi) {
        this.flickrApi = flickrApi;
    }

    @Override
    public Observable<List<Image>> getImages() {
        return flickrApi.getImages()
                .map(ImageEntityWrapper::getItemsList)
                .map(this::mapList);
    }

    private List<Image> mapList(final List<ImageEntity> imageEntities) {
        List<Image> result = new ArrayList<>(imageEntities.size());
        for (int i = 0, imageEntitiesSize = imageEntities.size(); i < imageEntitiesSize; i++) {
            final ImageEntity imageEntity = imageEntities.get(i);
            result.add(mapItem(imageEntity));
        }
        return result;
    }

    private Image mapItem(final ImageEntity imageEntity) {
        return Image.builder()
                .setAuthorName(imageEntity.getAuthor())
                .setTitle(imageEntity.getTitle())
                .setImageUrl(imageEntity.getMedia().getM())
                .build();
    }
}
