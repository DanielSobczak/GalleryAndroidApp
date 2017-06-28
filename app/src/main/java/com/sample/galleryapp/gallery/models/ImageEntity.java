package com.sample.galleryapp.gallery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageEntity {
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("author_id")
    @Expose
    private String authorId;
    @SerializedName("date_taken")
    @Expose
    private String dateTaken;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("media")
    @Expose
    private Media media;
    @SerializedName("published")
    @Expose
    private String published;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("title")
    private String title;

    public static ImageEntity create(final String author, final String authorId, final String dateTaken, final String description, final String link, final String m, final String published, final String tags, final String title) {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.author = author;
        imageEntity.authorId = authorId;
        imageEntity.dateTaken = dateTaken;
        imageEntity.description = description;
        imageEntity.link = link;
        Media media = new Media();
        media.m = m;
        imageEntity.media = media;
        imageEntity.published = published;
        imageEntity.tags = tags;
        imageEntity.title = title;
        return imageEntity;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public Media getMedia() {
        return media;
    }

    public String getPublished() {
        return published;
    }

    public String getTags() {
        return tags;
    }

    public String getTitle() {
        return title;
    }

    public static class Media {

        @SerializedName("m")
        @Expose
        private String m;

        public String getM() {
            return m;
        }
    }
}
