package com.sample.galleryapp.gallery.models;

import com.google.auto.value.AutoValue;

import org.joda.time.DateTime;

import java.util.List;

@AutoValue
public abstract class Image {
    public static Builder builder() {
        return new AutoValue_Image.Builder();
    }

    public abstract String imageUrl();

    public abstract String title();

    public abstract String authorName();

    public abstract DateTime publishDate();

    public abstract List<String> tags();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setImageUrl(String imageUrl);

        public abstract Builder setTitle(String title);

        public abstract Builder setAuthorName(String authorName);

        public abstract Builder setPublishDate(DateTime publishDate);

        public abstract Builder setTags(List<String> tags);

        public abstract Image build();
    }
}
