package com.sample.galleryapp.gallery;

import com.sample.galleryapp.gallery.models.GalleryCellImage;
import com.sample.galleryapp.gallery.models.Image;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class GalleryCellImageMapper {

    private static final String TIMESTAMP_FORMAT = "dd/MM HH:mm:ss";
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormat.forPattern(TIMESTAMP_FORMAT);
    private static final String TAG_SIGN = "#";
    private static final String TAG_DELIMETER = " ";
    private static final String EMPTY_TAG = "";

    List<GalleryCellImage> mapToGalleryCellModel(final List<Image> images) {
        List<GalleryCellImage> result = new ArrayList<GalleryCellImage>(images.size());
        for (final Image image : images) {
            result.add(mapToCellImage(image));
        }
        return result;
    }

    private GalleryCellImage mapToCellImage(final Image image) {
        return GalleryCellImage.builder()
                .imagePreviewUrl(image.imageUrl())
                .author(image.authorName())
                .title(image.title())
                .date(image.publishDate().toString(TIMESTAMP_FORMATTER))
                .tags(formatTags(image.tags()))
                .build();
    }

    private String formatTags(final List<String> tags) {
        if (tags.isEmpty()) return EMPTY_TAG;
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0, tagsSize = tags.size(); i < tagsSize; i++) {
            stringBuilder.append(TAG_SIGN);
            stringBuilder.append(tags.get(i));
            stringBuilder.append(TAG_DELIMETER);
        }
        int length = stringBuilder.length();
        return stringBuilder.delete(length - TAG_DELIMETER.length(), length).toString();
    }
}