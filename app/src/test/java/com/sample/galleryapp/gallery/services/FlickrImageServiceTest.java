package com.sample.galleryapp.gallery.services;

import com.github.javafaker.Faker;
import com.sample.galleryapp.common.FlickrApi;
import com.sample.galleryapp.gallery.models.Image;
import com.sample.galleryapp.gallery.models.ImageEntity;
import com.sample.galleryapp.gallery.models.ImageEntityWrapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.observers.AssertableSubscriber;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class FlickrImageServiceTest {
    private static Faker faker = new Faker();
    @Mock
    FlickrApi mockFlickrApi;

    private FlickrImageService sut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new FlickrImageService(mockFlickrApi);
    }

    @Test
    public void testImagesReceivedFromApiAreParsed() throws Exception {
        ImageEntity imageEntity = createEntity();
        List<ImageEntity> imageEntities = Arrays.asList(imageEntity);
        given(mockFlickrApi.getImages()).willReturn(Observable.just(ImageEntityWrapper.create(imageEntities)));
        AssertableSubscriber<List<Image>> assertableSubscriber = sut.getImages().test();
        List<List<Image>> onNextEvents = assertableSubscriber.getOnNextEvents();
        assertableSubscriber.assertValueCount(1);
        Image actualImage = onNextEvents.get(0).get(0);
        assertThat(actualImage.imageUrl()).isEqualTo(imageEntity.getMedia().getM());
        assertThat(actualImage.title()).isEqualTo(imageEntity.getTitle());
        assertThat(actualImage.authorName()).isEqualTo(imageEntity.getAuthor());
    }

    @Test
    public void testReceivingExactSameNumberOfObjectsAfterParsing() throws Exception {
        List<ImageEntity> imageEntities = Arrays.asList(createEntity(), createEntity(), createEntity(), createEntity());
        given(mockFlickrApi.getImages()).willReturn(Observable.just(ImageEntityWrapper.create(imageEntities)));
        AssertableSubscriber<List<Image>> assertableSubscriber = sut.getImages().test();
        List<List<Image>> onNextEvents = assertableSubscriber.getOnNextEvents();
        assertThat(onNextEvents.get(0)).hasSameSizeAs(imageEntities);
    }

    private ImageEntity createEntity() {
        return ImageEntity.create(faker.book().author(),
                faker.app().version(),
                faker.date().toString(),
                faker.book().genre(),
                faker.internet().image(),
                faker.internet().image(),
                faker.date().toString(),
                faker.internet().domainName(),
                faker.book().title());
    }

}