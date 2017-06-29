package com.sample.galleryapp.gallery;

import com.github.javafaker.Faker;
import com.sample.galleryapp.RobolectricTest;
import com.sample.galleryapp.common.exceptions.DefaultErrorBundle;
import com.sample.galleryapp.gallery.models.GalleryCellImage;
import com.sample.galleryapp.gallery.views.GalleryView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import rx.Subscriber;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class GalleryPresenterTest extends RobolectricTest {
    private final Faker faker = new Faker();

    @Mock
    private GetGalleryImages mockGetGalleryImages;
    @Mock
    private GalleryView mockGalleryView;

    private GalleryPresenter sut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new GalleryPresenter(mockGetGalleryImages);
        sut.setView(mockGalleryView);
    }

    @Test
    public void testGalleryPresenterInitializeForSuccess() throws Exception {
        List<GalleryCellImage> images = GalleryFakeFactory.createGalleryImagesList(5);
        sut.initialise();

        verify(mockGalleryView).showLoading();
        getRegisteredImageSubscriber().onNext(images);
        verify(mockGalleryView).renderGalleryImages(images);
    }

    @Test
    public void testGalleryPresenterInitializeForError() throws Exception {
        sut.initialise();

        verify(mockGalleryView).showLoading();
        getRegisteredImageSubscriber().onError(new RuntimeException());
        verify(mockGalleryView).showError(any(DefaultErrorBundle.class));
    }

    @Test
    public void testRetryButtonSubscribeToGetImages() throws Exception {
        sut.onRetryClicked();
        assertThat(getRegisteredImageSubscriber()).isNotNull();
    }


    private Subscriber<List<GalleryCellImage>> getRegisteredImageSubscriber() {
        ArgumentCaptor<Subscriber<List<GalleryCellImage>>> argumentCaptor = ArgumentCaptor.forClass((Class) Subscriber.class);
        verify(mockGetGalleryImages).execute(argumentCaptor.capture(), any(Void.class));
        return argumentCaptor.getValue();
    }

}