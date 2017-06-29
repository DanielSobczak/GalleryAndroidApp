package com.sample.galleryapp.gallery;

import com.github.javafaker.Faker;
import com.sample.galleryapp.gallery.models.GalleryCellImage;
import com.sample.galleryapp.gallery.models.Image;
import com.sample.galleryapp.gallery.others.GalleryCellImageMapper;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class GalleryCellImageMapperTest {
    private static final Faker faker = new Faker();

    private GalleryCellImageMapper sut;

    @Before
    public void setUp() throws Exception {
        sut = new GalleryCellImageMapper();
    }

    @Test
    public void testMappingImage() throws Exception {
        Image image = Image.builder()
                .setAuthorName(faker.book().author())
                .setTitle(faker.book().title())
                .setImageUrl(faker.internet().image())
                .setTags(Arrays.asList("cats", "kittens"))
                .setPublishDate(new DateTime())
                .build();
        List<Image> imagesList = Arrays.asList(image);
        List<GalleryCellImage> actualGalleryList = sut.mapToGalleryCellModel(imagesList);
        assertThat(actualGalleryList).hasSameSizeAs(imagesList);
        GalleryCellImage galleryCellImage = actualGalleryList.get(0);
        assertThat(galleryCellImage.title()).isEqualTo(image.title());
        assertThat(galleryCellImage.author()).isEqualTo(image.authorName());
        assertThat(galleryCellImage.imagePreviewUrl()).isEqualTo(image.imageUrl());
        assertThat(galleryCellImage.tags()).isEqualTo("#cats #kittens");

    }
}