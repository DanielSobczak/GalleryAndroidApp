package com.sample.galleryapp.gallery;

import com.sample.galleryapp.RobolectricTest;
import com.sample.galleryapp.common.exceptions.DefaultErrorBundle;
import com.sample.galleryapp.gallery.models.GalleryCellImage;
import com.sample.galleryapp.gallery.views.GalleryView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;

import java.util.List;

import rx.Subscriber;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class GalleryPresenterTest extends RobolectricTest {
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
        String expectedQueryParam = null;
        List<GalleryCellImage> images = GalleryFakeFactory.createGalleryImagesList(5);
        sut.initialise();

        verify(mockGalleryView).showLoading();
        getRegisteredImageSubscriber(expectedQueryParam).onNext(images);
        verify(mockGalleryView).renderGalleryImages(images);
    }

    @Test
    public void testGalleryPresenterInitializeForError() throws Exception {
        String expectedQueryParam = null;
        sut.initialise();

        verify(mockGalleryView).showLoading();
        getRegisteredImageSubscriber(expectedQueryParam).onError(new RuntimeException());
        verify(mockGalleryView).showError(any(DefaultErrorBundle.class));
    }

    @Test
    public void testRetryButtonSubscribeToGetImages() throws Exception {
        String expectedQueryParam = null;
        sut.onRetryClicked();
        assertThat(getRegisteredImageSubscriber(expectedQueryParam)).isNotNull();
    }


    @Test
    public void testRetryButtonUsesLastKnownQueryToGetImages() throws Exception {
        String expectedQueryParam = "test";
        sut.onQueryChanged(expectedQueryParam);
        sut.onRetryClicked();
        ArgumentCaptor<GetGalleryImages.GetGalleryImagesParams> paramsCaptor = ArgumentCaptor.forClass(GetGalleryImages.GetGalleryImagesParams.class);
        verify(mockGetGalleryImages, new Times(2)).execute(any(Subscriber.class), paramsCaptor.capture());
        assertThat(paramsCaptor.getValue().getTagQuery()).isEqualTo(expectedQueryParam);
    }


    private Subscriber<List<GalleryCellImage>> getRegisteredImageSubscriber(final String expectedQueryParam) {
        ArgumentCaptor<Subscriber<List<GalleryCellImage>>> subscriberCaptor = ArgumentCaptor.forClass((Class) Subscriber.class);
        ArgumentCaptor<GetGalleryImages.GetGalleryImagesParams> paramsCaptor = ArgumentCaptor.forClass(GetGalleryImages.GetGalleryImagesParams.class);
        verify(mockGetGalleryImages).execute(subscriberCaptor.capture(), paramsCaptor.capture());
        assertThat(paramsCaptor.getValue().getTagQuery()).isEqualTo(expectedQueryParam);
        return subscriberCaptor.getValue();
    }

}