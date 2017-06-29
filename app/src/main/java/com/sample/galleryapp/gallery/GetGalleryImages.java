package com.sample.galleryapp.gallery;

import android.text.TextUtils;

import com.sample.galleryapp.common.UseCase;
import com.sample.galleryapp.common.executor.PostExecutionThread;
import com.sample.galleryapp.common.executor.ThreadExecutor;
import com.sample.galleryapp.gallery.models.GalleryCellImage;
import com.sample.galleryapp.gallery.others.GalleryCellImageMapper;
import com.sample.galleryapp.gallery.services.ImageProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetGalleryImages extends UseCase<List<GalleryCellImage>, GetGalleryImages.GetGalleryImagesParams> {
    private static final String TAG_DELIMITER = " ";
    private final ImageProvider imageProvider;
    private final GalleryCellImageMapper galleryImageMapper;

    @Inject
    protected GetGalleryImages(final ThreadExecutor threadExecutor, final PostExecutionThread postExecutionThread, final ImageProvider imageProvider, final GalleryCellImageMapper galleryImageMapper) {
        super(threadExecutor, postExecutionThread);
        this.imageProvider = imageProvider;
        this.galleryImageMapper = galleryImageMapper;
    }

    @Override
    protected Observable<List<GalleryCellImage>> buildUseCaseObservable(final GetGalleryImagesParams imagesParams) {
        return imageProvider.getImages(tags(imagesParams))
                .map(galleryImageMapper::mapToGalleryCellModel);
    }

    private List<String> tags(final GetGalleryImagesParams imagesParams) {
        String query = imagesParams.getTagQuery();
        if (TextUtils.isEmpty(query)) {
            return Collections.emptyList();
        } else {
            return Arrays.asList(query.split(TAG_DELIMITER));
        }
    }

    public static class GetGalleryImagesParams {
        private String tagQuery;

        public GetGalleryImagesParams(final String tagQuery) {
            this.tagQuery = tagQuery;
        }

        public String getTagQuery() {
            return tagQuery;
        }
    }
}
