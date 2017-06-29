package com.sample.galleryapp.gallery.views;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.sample.galleryapp.gallery.models.GalleryCellImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryImagesAdapter extends RecyclerView.Adapter<GalleryImageViewHolder> {
    private List<GalleryCellImage> images = Collections.emptyList();

    public void setImages(final List<GalleryCellImage> images) {
        this.images = new ArrayList<>(images);
    }

    @Override
    public GalleryImageViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return GalleryImageViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(final GalleryImageViewHolder holder, final int position) {
        holder.setImageData(getItem(position));
    }

    private GalleryCellImage getItem(final int position) {
        return images.get(position);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
