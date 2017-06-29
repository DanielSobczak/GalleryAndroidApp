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

    public abstract String date();

    public abstract String tags();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder imagePreviewUrl(final String imagePreviewUrl);

        public abstract Builder author(final String author);

        public abstract Builder title(final String title);

        public abstract Builder date(final String date);

        public abstract Builder tags(final String tags);

        public abstract GalleryCellImage build();
    }
}
