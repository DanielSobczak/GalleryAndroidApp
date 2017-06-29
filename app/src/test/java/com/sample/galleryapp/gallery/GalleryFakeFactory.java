package com.sample.galleryapp.gallery;

import com.github.javafaker.Faker;
import com.sample.galleryapp.gallery.models.GalleryCellImage;
import com.sample.galleryapp.gallery.models.Image;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GalleryFakeFactory {
    private static final Faker faker = new Faker();

    public static List<GalleryCellImage> createGalleryImagesList(final int size) {
        List<GalleryCellImage> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(createGalleryCellImage());
        }
        return result;
    }

    public static GalleryCellImage createGalleryCellImage() {
        return GalleryCellImage.builder()
                .setAuthor(faker.book().author())
                .setImagePreviewUrl(faker.internet().image())
                .setTitle(faker.book().title())
                .setTags(faker.lorem().word())
                .setPublishDate(faker.date().toString())
                .build();
    }

    public static List<Image> createImagesList(final int size) {
        List<Image> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(createImage());
        }
        return result;
    }

    public static Image createImage() {
        return Image.builder()
                .setAuthorName(faker.book().author())
                .setTitle(faker.book().title())
                .setImageUrl(faker.internet().image())
                .setTags(Arrays.asList(faker.lorem().word(), faker.lorem().word()))
                .setPublishDate(new DateTime())
                .build();
    }
}
