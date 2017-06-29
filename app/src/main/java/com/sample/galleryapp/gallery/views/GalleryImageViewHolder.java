package com.sample.galleryapp.gallery.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sample.galleryapp.R;
import com.sample.galleryapp.gallery.models.GalleryCellImage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryImageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img_preview)
    SimpleDraweeView imagePreview;
    @BindView(R.id.txt_publish_date)
    TextView publishDate;
    @BindView(R.id.txt_title)
    TextView title;
    @BindView(R.id.txt_author)
    TextView author;
    @BindView(R.id.txt_tags)
    TextView tags;

    public GalleryImageViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public static GalleryImageViewHolder create(final ViewGroup parent) {
        Context context = parent.getContext();
        return new GalleryImageViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_image_data, parent, false));
    }

    public void setImageData(final GalleryCellImage galleryCellImage){
        imagePreview.setImageURI(galleryCellImage.imagePreviewUrl());
        publishDate.setText(galleryCellImage.date());
        title.setText(galleryCellImage.title());
        author.setText(galleryCellImage.author());
        tags.setText(galleryCellImage.tags());
    }

}
