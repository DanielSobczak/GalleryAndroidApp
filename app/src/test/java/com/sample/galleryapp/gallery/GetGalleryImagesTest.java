package com.sample.galleryapp.gallery;

import com.sample.galleryapp.common.executor.PostExecutionThread;
import com.sample.galleryapp.common.executor.ThreadExecutor;
import com.sample.galleryapp.gallery.models.Image;
import com.sample.galleryapp.gallery.services.ImageProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetGalleryImagesTest {
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;
    @Mock
    private ImageProvider mockImageProvider;
    @Mock
    private GalleryCellImageMapper mockGalleryImageMapper;

    private GetGalleryImages sut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new GetGalleryImages(mockThreadExecutor, mockPostExecutionThread, mockImageProvider, mockGalleryImageMapper);
    }

    @Test
    public void testImageObtain() throws Exception {
        List<Image> imageList = Collections.emptyList();
        given(mockImageProvider.getImages()).willReturn(Observable.just(imageList));

        sut.buildUseCaseObservable(null).test();

        verify(mockImageProvider).getImages();
        verify(mockGalleryImageMapper).mapToGalleryCellModel(imageList);
        verifyNoMoreInteractions(mockImageProvider);
        verifyZeroInteractions(mockPostExecutionThread);
        verifyZeroInteractions(mockThreadExecutor);
    }

}