package com.sample.galleryapp.gallery;

import com.sample.galleryapp.common.UseCase;
import com.sample.galleryapp.common.executor.PostExecutionThread;
import com.sample.galleryapp.common.executor.ThreadExecutor;
import com.sample.galleryapp.gallery.models.Image;
import com.sample.galleryapp.gallery.services.ImageProvider;

import java.util.List;

import rx.Observable;

public class GetImages extends UseCase<List<Image>, Void> {
    private final ImageProvider imageProvider;

    protected GetImages(final ThreadExecutor threadExecutor, final PostExecutionThread postExecutionThread, final ImageProvider imageProvider) {
        super(threadExecutor, postExecutionThread);
        this.imageProvider = imageProvider;
    }

    @Override
    protected Observable<List<Image>> buildUseCaseObservable(final Void aVoid) {
        return imageProvider.getImages();
    }
}
