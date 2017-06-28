package com.sample.galleryapp.gallery;

import com.sample.galleryapp.common.executor.PostExecutionThread;
import com.sample.galleryapp.common.executor.ThreadExecutor;
import com.sample.galleryapp.gallery.services.ImageProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetImagesTest {
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;
    @Mock
    private ImageProvider mockImageProvider;

    private GetImages sut;

    @Before
    public void setUp() throws Exception {
        sut = new GetImages(mockThreadExecutor, mockPostExecutionThread, mockImageProvider);
    }

    @Test
    public void testImageOtain() throws Exception {
        sut.buildUseCaseObservable(null);
        verify(mockImageProvider).getImages();
        verifyNoMoreInteractions(mockImageProvider);
        verifyZeroInteractions(mockPostExecutionThread);
        verifyZeroInteractions(mockThreadExecutor);
    }

}