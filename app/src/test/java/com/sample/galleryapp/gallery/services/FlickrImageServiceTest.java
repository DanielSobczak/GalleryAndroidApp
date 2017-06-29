package com.sample.galleryapp.gallery.services;

import com.github.javafaker.Faker;
import com.sample.galleryapp.RobolectricTest;
import com.sample.galleryapp.common.FlickrApi;
import com.sample.galleryapp.gallery.models.Image;
import com.sample.galleryapp.gallery.models.ImageEntity;
import com.sample.galleryapp.gallery.models.ImageEntityWrapper;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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

public class FlickrImageServiceTest extends RobolectricTest {
    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormat
            .forPattern(TIMESTAMP_FORMAT);
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
        ImageEntity imageEntity = createEntity("2017-06-28T21:03:33Z");
        List<ImageEntity> imageEntities = Arrays.asList(imageEntity);
        given(mockFlickrApi.getImages()).willReturn(Observable.just(ImageEntityWrapper.create(imageEntities)));
        AssertableSubscriber<List<Image>> assertableSubscriber = sut.getImages().test();
        List<List<Image>> onNextEvents = assertableSubscriber.getOnNextEvents();
        assertableSubscriber.assertValueCount(1);
        Image actualImage = onNextEvents.get(0).get(0);
        assertThat(actualImage.imageUrl()).isEqualTo(imageEntity.getMedia().getM());
        assertThat(actualImage.title()).isEqualTo(imageEntity.getTitle());
        assertThat(actualImage.authorName()).isEqualTo(imageEntity.getAuthor());
        DateTime publishDate = actualImage.publishDate().withZone(DateTimeZone.UTC);
        assertThat(publishDate.getDayOfMonth()).isEqualTo(28);
        assertThat(publishDate.getMonthOfYear()).isEqualTo(6);
        assertThat(publishDate.getYear()).isEqualTo(2017);
        assertThat(publishDate.getHourOfDay()).isEqualTo(21);
        assertThat(publishDate.getMinuteOfHour()).isEqualTo(3);
        assertThat(publishDate.getSecondOfMinute()).isEqualTo(33);
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
        return createEntity(new DateTime().toString(TIMESTAMP_FORMATTER));
    }

    private ImageEntity createEntity(final String publishedDate) {
        return ImageEntity.create(faker.book().author(),
                faker.app().version(),
                faker.date().toString(),
                faker.book().genre(),
                faker.internet().image(),
                faker.internet().image(),
                publishedDate,
                faker.internet().domainName(),
                faker.book().title());
    }

}