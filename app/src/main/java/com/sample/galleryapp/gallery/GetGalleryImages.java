package com.sample.galleryapp.gallery;

import com.sample.galleryapp.common.UseCase;
import com.sample.galleryapp.common.executor.PostExecutionThread;
import com.sample.galleryapp.common.executor.ThreadExecutor;
import com.sample.galleryapp.gallery.models.GalleryCellImage;
import com.sample.galleryapp.gallery.services.ImageProvider;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetGalleryImages extends UseCase<List<GalleryCellImage>, Void> {
    private final ImageProvider imageProvider;
    private final GalleryCellImageMapper galleryImageMapper;

    @Inject
    protected GetGalleryImages(final ThreadExecutor threadExecutor, final PostExecutionThread postExecutionThread, final ImageProvider imageProvider, final GalleryCellImageMapper galleryImageMapper) {
        super(threadExecutor, postExecutionThread);
        this.imageProvider = imageProvider;
        this.galleryImageMapper = galleryImageMapper;
    }

    @Override
    protected Observable<List<GalleryCellImage>> buildUseCaseObservable(final Void aVoid) {
        return imageProvider.getImages()
                .map(galleryImageMapper::mapToGalleryCellModel);
    }
}
