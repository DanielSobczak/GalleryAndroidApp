package com.sample.galleryapp.gallery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageEntityWrapper {
    @SerializedName("items")
    @Expose
    private List<ImageEntity> itemsList;

    public static ImageEntityWrapper create(final List<ImageEntity> imageEntities) {
        ImageEntityWrapper imageEntityWrapper = new ImageEntityWrapper();
        imageEntityWrapper.itemsList = imageEntities;
        return imageEntityWrapper;
    }

    public List<ImageEntity> getItemsList() {
        return itemsList;
    }
}
