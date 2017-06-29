package com.sample.galleryapp.gallery.models;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class GalleryCellImage {
    public static Builder builder() {
        return new AutoValue_GalleryCellImage.Builder();
    }

    public abstract String imagePreviewUrl();

    public abstract String author();

    public abstract String title();

    public abstract String publishDate();

    public abstract String tags();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setImagePreviewUrl(final String imagePreviewUrl);

        public abstract Builder setAuthor(final String author);

        public abstract Builder setTitle(final String title);

        public abstract Builder setPublishDate(final String date);

        public abstract Builder setTags(final String tags);

        public abstract GalleryCellImage build();
    }
}
