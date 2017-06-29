package com.sample.galleryapp.gallery;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;
import com.sample.galleryapp.common.DefaultSubscriber;
import com.sample.galleryapp.common.Presenter;
import com.sample.galleryapp.common.exceptions.DefaultErrorBundle;
import com.sample.galleryapp.gallery.models.GalleryCellImage;
import com.sample.galleryapp.gallery.views.GalleryView;

import java.util.List;

import timber.log.Timber;

public class GalleryPresenter implements Presenter {

    private final GetGalleryImages getGalleryImages;

    private GalleryView view;

    public GalleryPresenter(final GetGalleryImages getGalleryImages) {
        this.getGalleryImages = getGalleryImages;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getGalleryImages.unsubscribe();
        view = null;
    }

    public void setView(final GalleryView view) {
        this.view = view;
    }

    public void initialise() {
        fetchImages();
    }

    private void fetchImages() {
        getGalleryImages.unsubscribe();
        view.showLoading();
        getGalleryImages.execute(new ImagesSubscriber(), null);
    }

    private void onImagesReceived(final List<GalleryCellImage> images) {
        view.renderGalleryImages(images);
    }

    private void onErrorReceived(final Exception e) {
        view.showError(new DefaultErrorBundle(e));
    }

    public void onRetryClicked() {
        fetchImages();
    }

    @RxLogSubscriber
    private final class ImagesSubscriber extends DefaultSubscriber<List<GalleryCellImage>> {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(final Throwable e) {
            Timber.e(e, "image subscriber error");
            onErrorReceived((Exception) e);
        }

        @Override
        public void onNext(final List<GalleryCellImage> images) {
            onImagesReceived(images);
        }
    }
}
