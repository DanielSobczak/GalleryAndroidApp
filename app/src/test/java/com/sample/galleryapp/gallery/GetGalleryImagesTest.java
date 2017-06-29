package com.sample.galleryapp.gallery;

import android.support.annotation.NonNull;

import com.sample.galleryapp.RobolectricTest;
import com.sample.galleryapp.common.executor.PostExecutionThread;
import com.sample.galleryapp.common.executor.ThreadExecutor;
import com.sample.galleryapp.gallery.models.Image;
import com.sample.galleryapp.gallery.others.GalleryCellImageMapper;
import com.sample.galleryapp.gallery.services.ImageProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import rx.Observable;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetGalleryImagesTest extends RobolectricTest {
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
    public void testImageObtainForEmptyTag() throws Exception {
        List<Image> imageList = Collections.emptyList();
        given(mockImageProvider.getImages(anyList())).willReturn(Observable.just(imageList));

        sut.buildUseCaseObservable(new GetGalleryImages.GetGalleryImagesParams("")).test();

        verify(mockImageProvider).getImages(Matchers.argThat(listIsEmpty()));
        verify(mockGalleryImageMapper).mapToGalleryCellModel(imageList);
        verifyNoMoreInteractions(mockImageProvider);
        verifyZeroInteractions(mockPostExecutionThread);
        verifyZeroInteractions(mockThreadExecutor);
    }

    @Test
    public void testImageObtainForTagAsNull() throws Exception {
        List<Image> imageList = Collections.emptyList();
        given(mockImageProvider.getImages(anyList())).willReturn(Observable.just(imageList));

        sut.buildUseCaseObservable(new GetGalleryImages.GetGalleryImagesParams(null)).test();

        verify(mockImageProvider).getImages(Matchers.argThat(listIsEmpty()));
    }

    @Test
    public void testImageObtainForTags() throws Exception {
        List<Image> imageList = Collections.emptyList();
        given(mockImageProvider.getImages(anyList())).willReturn(Observable.just(imageList));

        sut.buildUseCaseObservable(new GetGalleryImages.GetGalleryImagesParams("one two")).test();

        ArgumentCaptor<List<String>> queryCaptor = ArgumentCaptor.forClass((Class) List.class);
        verify(mockImageProvider).getImages(queryCaptor.capture());
        assertThat(queryCaptor.getValue()).containsExactly("one", "two");
    }

    @NonNull
    private <T> ArgumentMatcher<List<T>> listIsEmpty() {
        return new ArgumentMatcher<List<T>>() {
            @Override
            public boolean matches(final Object argument) {
                return ((List<T>) argument).isEmpty();
            }
        };
    }

}