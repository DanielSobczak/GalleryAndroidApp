package com.sample.galleryapp.gallery.services;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.sample.galleryapp.common.FlickrApi;
import com.sample.galleryapp.gallery.models.Image;
import com.sample.galleryapp.gallery.models.ImageEntity;
import com.sample.galleryapp.gallery.models.ImageEntityWrapper;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rx.Observable;

public class FlickrImageService implements ImageProvider {
    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormat.forPattern(TIMESTAMP_FORMAT);
    private static final String TAG_DELIMITER = " ";
    private final FlickrApi flickrApi;

    public FlickrImageService(final FlickrApi flickrApi) {
        this.flickrApi = flickrApi;
    }

    @Override
    public Observable<List<Image>> getImages(final List<String> tags) {
        return flickrApi.getImages(createTagQuery(tags))
                .map(ImageEntityWrapper::getItemsList)
                .map(this::mapList);
    }

    private String createTagQuery(final List<String> tags) {
        return TextUtils.join(",", tags);
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
                .setPublishDate(TIMESTAMP_FORMATTER.parseDateTime(imageEntity.getPublished()))
                .setTags(mapTags(imageEntity.getTags()))
                .build();
    }

    @NonNull
    private List<String> mapTags(final String tags) {
        if (TextUtils.isEmpty(tags)) {
            return Collections.emptyList();
        } else {
            return Arrays.asList(tags.split(TAG_DELIMITER));
        }
    }

}
