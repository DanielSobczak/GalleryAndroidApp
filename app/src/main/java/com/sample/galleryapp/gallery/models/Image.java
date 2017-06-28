package com.sample.galleryapp.gallery.models;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Image {
    public static Builder builder() {
        return new AutoValue_Image.Builder();
    }

    public abstract String imageUrl();

    public abstract String title();

    public abstract String authorName();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setImageUrl(String imageUrl);

        public abstract Builder setTitle(String title);

        public abstract Builder setAuthorName(String authorName);

        public abstract Image build();
    }
}
